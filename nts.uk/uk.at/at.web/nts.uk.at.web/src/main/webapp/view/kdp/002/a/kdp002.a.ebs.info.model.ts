
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
        { headerText: nts.uk.resource.getText('KDP002_30'), key: 'stampDate', width: 130 }, 
        { headerText: nts.uk.resource.getText('KSM015_31'), key: 'stampHowAndTime', width: 80 }, 
        { headerText: nts.uk.resource.getText('KSM015_32'), key: 'timeStampType', width: 90}
    ]);
    currentCode: KnockoutObservable<any> = ko.observable({});
    items: KnockoutObservableArray<StampHistoryInfo>;

    constructor() {
        let self = this;
        self.items = ko.observableArray([]);
    }
}

interface StampHistoryInfo {
    code: string;
    stampDate: string;
    stampHowAndTime: string;
    timeStampType: string;
}