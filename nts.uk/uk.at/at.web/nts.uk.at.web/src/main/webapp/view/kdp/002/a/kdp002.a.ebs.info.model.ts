
class EmbossInfoModel {
    dateValue: KnockoutObservable<any>;
    constructor() {
        let self = this;
        self.dateValue = ko.observable({startDate: moment().add(-3, 'days').format('YYYY/MM/DD'), endDate: moment().format('YYYY/MM/DD')});
    }
}

class EmbossGridInfo {
    columns: KnockoutObservableArray<any> = ko.observable([
        { headerText: 'コード', key: 'code', width:100, hidden: true },
        { headerText: nts.uk.resource.getText('KDP002_30'), key: 'stampDate', width: 120 }, 
        { headerText: nts.uk.resource.getText('KSM015_31'), key: 'stampHowAndTime', width: 80 }, 
        { headerText: nts.uk.resource.getText('KSM015_32'), key: 'timeStampType', width: 100}
    ]);
    currentCode: KnockoutObservable<any> = ko.observable({});
    items: KnockoutObservableArray<StampHistoryInfo>;

    constructor() {
        let self = this;
        self.items = ko.observable([
            {code: '1', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: left'> sang </div>`},
            {code: '2', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: center'> trua </div>`},
            {code: '3', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '4', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '5', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '6', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '7', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '8', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '9', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '10', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '11', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '12', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '13', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '14', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
            {code: '15', stampDate: '2020/18/18', stampHowAndTime: 'w 18:30' , timeStampType: `<div class="full-width" style='text-align: right'> toi </div>`},
        ]);
    }
}

interface StampHistoryInfo {
    code: string;
    stampDate: string;
    stampHowAndTime: string;
    timeStampType: string;
}