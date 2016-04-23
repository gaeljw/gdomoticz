
var app = angular.module('gdomoticzApp', ['ngResource']);

// Create service for accessing temperature
app.factory("temperatureFactory", function($resource) {
    return $resource('/data/temperatures');
});


// Data controller
app.controller("DataCtrl", function($scope, $interval, temperatureFactory) {

    function updateTemperature(data) {
        $scope.temperatures = data;
//        data.forEach(function(item) {
//            var chart = $('#temperature-gauge-' + item.id).highcharts();
//            if (chart) {
//                var point = chart.series[0].points[0];
//                point.update(item.temperature);
//            }
//        })
    }

    temperatureFactory.query(updateTemperature);
    $interval(function() {
        temperatureFactory.query(updateTemperature);
    }, 30000);

});

app.directive('gdzTemperatureGauge', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var divId = element[0].id;
            var chart = new Highcharts.Chart(Highcharts.merge(gaugeDefaultOptions, {
                chart: {
                    renderTo: divId
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
        }
    }
});