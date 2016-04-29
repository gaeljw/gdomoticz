
var app = angular.module('gdomoticzApp', ['ngResource']);

// Create service for accessing temperature
app.factory("temperatureFactory", function($resource) {
    return $resource('/data/temperatures');
});
app.factory("temperatureHistoryFactory", function($resource) {
    return $resource('/data/history/temperatures');
});
app.factory("temperaturesMonthHistoryFactory", function($resource) {
    return $resource('/data/history/temperatures/month');
});

// Create service for accessing system metrics
app.factory("systemFactory", function($resource) {
    return $resource('/system/metrics');
});


// Data controller
app.controller("DataCtrl", function($scope, $interval, temperatureFactory) {

    function updateTemperatures(data) {
        $scope.temperatures = data;
    }

    temperatureFactory.query(updateTemperatures);
    $interval(function() {
        temperatureFactory.query(updateTemperatures);
    }, 30000); // 30s

});

app.controller("HistoryCtrl", function($scope, $interval, temperatureHistoryFactory, temperaturesMonthHistoryFactory) {

    function updateGraph(data) {
        $scope.points = data;
    }

    function updateGraphMonth(data) {
        $scope.pointsMonth = data;
    }

    temperatureHistoryFactory.query(updateGraph);
    temperaturesMonthHistoryFactory.query(updateGraphMonth);
    $interval(function() {
        temperatureHistoryFactory.query(updateGraph);
        temperaturesMonthHistoryFactory.query(updateGraphMonth);
    }, 300000); //5mn

});

// System controller
app.controller("SystemCtrl", function($scope, $interval, systemFactory) {

    function updateMemory(data) {
        $scope.metrics = data;
    }

    systemFactory.get(updateMemory);
    $interval(function() {
        systemFactory.get(updateMemory);
    }, 30000); // 30s

});

app.directive('gdzTemperatureGauge', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var divId = element[0].id;
            var tId = attrs.id;
            // Create gauge inside div
            var chart = new Highcharts.Chart(Highcharts.merge(gaugeDefaultOptions, {
                chart: {
                    renderTo: divId // TODO verifier si ca marche bien avec plusieurs temps
                },
                yAxis: {
                    min: -20,
                    max: 50
                },
                series: [{
                    name: 'Température',
                    data: [scope.t.temperature],
                    dataLabels: {
                        format: '<div style="text-align:center"><span style="font-size:2em;color:' +
                            ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                    '<span style="font-size:12px;color:silver">°C</span></div>'
                    }
                }]
            }));

            // Watch on temperature to update
            scope.$watch(function() {
                return scope.t.temperature;
            }, function(temperature) {
               var chart = $('#temperature-gauge-' + scope.t.id).highcharts();
               if (chart) {
                   var point = chart.series[0].points[0];
                   point.update(temperature);
               } else {
                console.log("La jauge pour " + scope.t.id + " n'existe pas");
                var chart = new Highcharts.Chart(Highcharts.merge(gaugeDefaultOptions, {
                    chart: {
                        renderTo: 'temperature-gauge-' + scope.t.id
                    },
                    yAxis: {
                        min: -20,
                        max: 50
                    },
                    series: [{
                        name: 'Température',
                        data: [temperature],
                        dataLabels: {
                            format: '<div style="text-align:center"><span style="font-size:2em;color:' +
                                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                        '<span style="font-size:12px;color:silver">°C</span></div>'
                        }
                    }]
                }));
               }
           });
        }
    }
});

app.directive('gdzTemperaturesHistory', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var divId = element[0].id;

            var chart = new Highcharts.Chart({
                chart: {
                    type: 'spline',
                    renderTo: divId
                },
                title: {
                    text: 'Historique des températures'
                },
                xAxis: {
                    type: 'datetime',
                    title: {
                        text: 'Heure'
                    }
                },
                yAxis: {
                    title: {
                        text: 'Température (°C)'
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Hh%M', this.x) + ' : ' +
                            Highcharts.numberFormat(this.y, 2) + '°C';
                    }
                },

                plotOptions: {
                    spline: {
                        marker: {
                            enabled: false
                        }
                    }
                },

                series: []
            });


            // Watch on points
            scope.$watch(function() {
                return scope.points;
            }, function(temperature) {
               var chart = $('#temperaturesHistory').highcharts();

               if (chart) {
                    console.log('Graph exists');
                    chart.series.forEach(function(s) {
                        s.remove();
                    });
                    scope.points.forEach(function(device) {
                       var serie = {};
                       serie.name = 'Device ' + device.idDevice;
                       serie.data = [];
                       device.temperatures.forEach(function(point) {
                           var p = [Date.parse(point.date), point.temperature];
                           serie.data.push(p);
                       });
                    chart.addSeries(serie, true);
                   });
               } else {
               console.log('test');
               }
           });
        }
    }
});

app.directive('gdzTemperaturesHistoryMonth', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var divId = element[0].id;

            var chart = new Highcharts.Chart({
                chart: {
                    renderTo: divId,
                    type: 'arearange',
                    zoomType: 'x'
                },
                title: {
                    text: 'Temperature variation by day'
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                tooltip: {
                    crosshairs: true,
                    shared: true,
                    valueSuffix: '°C'
                },
                legend: {
                    enabled: false
                },
                series: []
            });

            // Watch on points
            scope.$watch(function() {
                return scope.pointsMonth;
            }, function() {
               var chart = $('#temperaturesHistoryMonth').highcharts();

               if (chart) {
                    console.log('Graph exists');
                    chart.series.forEach(function(s) {
                        s.remove();
                    });
                    scope.pointsMonth.forEach(function(device) {
                       var serie = {};
                       serie.name = 'Device ' + device.idDevice;
                       serie.data = [];
                       device.points.forEach(function(point) {
                           var p = [Date.parse(point.date), point.min, point.max];
                           serie.data.push(p);
                       });
                    chart.addSeries(serie, true);
                   });
               } else {
                    console.log('test');
               }
           });
        }
    }
});