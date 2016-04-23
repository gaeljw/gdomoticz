
var app = angular.module('gdomoticzApp', ['ngResource']);

// Create service for accessing temperature
app.factory("temperatureFactory", function($resource) {
    return $resource('/data/temperature');
});


// Data controller
app.controller("DataCtrl", function($scope, $interval, temperatureFactory) {

    function updateTemperature(temperature) {
        $scope.temperature = temperature;
    }

    temperatureFactory.get(updateTemperature);
    $interval(function() {
        temperatureFactory.get(updateTemperature);
    }, 5000);

});