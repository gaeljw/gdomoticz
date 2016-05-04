Highcharts.setOptions({
    global: {
        useUTC: false
    },
    lang: {
        months: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',  'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
        weekdays: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
        shortMonths: [ "Jan" , "Fév" , "Mar" , "Avr" , "Mai" , "Jui" , "Jui" , "Aou" , "Sep" , "Oct" , "Nov" , "Déc"]
    },
    colors: ['#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1'],
});


var gaugeDefaultOptions = {
    chart: {
        type: 'solidgauge'
    },
    title: null,
    pane: {
        center: ['50%', '85%'],
        size: '140%',
        startAngle: -90,
        endAngle: 90,
        background: {
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
            innerRadius: '60%',
            outerRadius: '100%',
            shape: 'arc'
        }
    },
    tooltip: {
        enabled: false
    },
    yAxis: {
        tickPositions: [-20, 50],
        stops: [
            [0, '#0033CC'],
            [0.35, '#00DDCC'],
            [0.4, '#00DD99'],
            [0.6, '#CCDD00'],
            [0.7, '#FFDD00'],
            [0.8, '#FF8800'],
            [0.9, '#FF0000']
        ],
        lineWidth: 0,
        minorTickInterval: null,
        tickPixelInterval: 400,
        tickWidth: 0,
        labels: {
            y: 16
        }
    },
    plotOptions: {
        solidgauge: {
            dataLabels: {
                y: 5,
                borderWidth: 0,
                useHTML: true
            }
        }
    },
    credits: {
        enabled: false
    }
};