
var app = angular.module('gdomoticzApp', ['ngResource']);

// Create service for accessing temperature
app.factory("temperatureFactory", function($resource) {
    return $resource('/data/temperature');
});


// Data controller
app.controller("DataCtrl", function($scope, $interval, temperatureFactory) {

    function updateTemperature(data) {
        $scope.temperature = data;
        var chart = $('#temperature-gauge').highcharts();
        if (chart) {
            var point = chart.series[0].points[0];
            point.update(data.temperature);
        }
    }

    temperatureFactory.get(updateTemperature);
    $interval(function() {
        temperatureFactory.get(updateTemperature);
    }, 5000);

});