
var app = angular.module('gdomoticzApp', ['ngResource']);

// Create service for accessing temperature
app.factory("temperatureFactory", function($resource) {
    return $resource('/data/temperatures');
});

// Create service for accessing system data
app.factory("systemFactory", function($resource) {
    return $resource('/system/memory');
});


// Data controller
app.controller("DataCtrl", function($scope, $interval, temperatureFactory) {

    function updateTemperatures(data) {
        $scope.temperatures = data;
    }

    temperatureFactory.query(updateTemperatures);
    $interval(function() {
        temperatureFactory.query(updateTemperatures);
    }, 30000);

});

// System controller
app.controller("SystemCtrl", function($scope, $interval, systemFactory) {

    function updateMemory(data) {
        $scope.memory = data;
    }

    systemFactory.get(updateMemory);
    $interval(function() {
        systemFactory.get(updateMemory);
    }, 30000);

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
               }
           });
        }
    }
});