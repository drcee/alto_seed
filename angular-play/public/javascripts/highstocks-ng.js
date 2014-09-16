'use strict';

angular.module('highstocks-ng', [])
    .directive('highstock', function () {
        var chart;
        function drawChart(config) {
            chart = new Highcharts.StockChart(config);
            //console.info('chart redrawn');
        }

        return {
            restrict: 'E',
            require: ['ngModel'],
            scope: {
                ngModel: '='
            },
            link: function(scope, element, attrs) {
                if (!scope.ngModel.hasOwnProperty('chart')) {
                    //console.info('added empty chart to model');
                    scope.ngModel.chart = {};
                }
                scope.ngModel.chart.renderTo = element[0];

                if (!scope.ngModel.hasOwnProperty('series')) {
                    //console.info('added empty series to model');
                    scope.ngModel.series = [];
                }

                scope.$watch('ngModel', function(newModel, oldModel) {
                    console.info('model changed', oldModel, '=>', newModel);
                    drawChart(scope.ngModel);
                });

                scope.$watch('ngModel.series', function(newSeries, oldSeries) {
                    console.info('series changed', oldSeries, '=>', newSeries);
                    if (newSeries != null) {
                        drawChart(scope.ngModel);
                    }
                });
            }
        }
    });
