class EmbossGridInfo {
    
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    currentCode: KnockoutObservable<any> = ko.observable({});
    items: KnockoutObservableArray<any> = ko.observableArray([]);
    displayMethod: KnockoutObservable<any>;
    displayType: any = {HIDE: 0, DISPLAY: 1, SHOW_TIME_CARD: 2};
    dateValue: KnockoutObservable<any>;

    constructor(start: IStartPage) {
        let self = this;
        let setting = start.stampSetting;
        self.displayMethod = ko.observable(setting.historyDisplayMethod);
        self.dateValue = ko.observable({startDate: moment().add(-3, 'days').format('YYYY/MM/DD'), endDate: moment().format('YYYY/MM/DD')});

        if(self.displayMethod() == self.displayType.DISPLAY) {
            self.columns([
                { headerText: 'コード', key: 'code', width:100, hidden: true },
                { headerText: nts.uk.resource.getText('KDP002_30'), key: 'stampDate', width: 130 }, 
                { headerText: nts.uk.resource.getText('KDP002_31'), key: 'stampHowAndTime', width: 80 }, 
                { headerText: nts.uk.resource.getText('KDP002_32'), key: 'timeStampType', width: 90}
            ]);
            self.bindItemData(start.stampDataOfEmployees);
        } else if (self.displayMethod() == self.displayType.SHOW_TIME_CARD) {
            self.columns([
                { headerText: 'コード', key: 'code', width:100, hidden: true },
                { headerText: nts.uk.resource.getText('KDP002_30'), key: 'date', width: 60 }, 
                { headerText: nts.uk.resource.getText('KDP002_33', ['#Com_WorkIn']), key: 'workIn1', width: 60 }, 
                { headerText: nts.uk.resource.getText('KDP002_34', ['#Com_WorkOut']), key: 'workOut1', width: 60 },
                { headerText: nts.uk.resource.getText('KDP002_35', ['#Com_WorkIn']) + '2', key: 'workIn2', width: 60 }, 
                { headerText: nts.uk.resource.getText('KDP002_36', ['#Com_WorkOut']) + '2', key: 'workOut2', width: 60 }
            ]);
            self.bindItemData(start.timeCard.listAttendances);
        }

        self.items = ko.observableArray([]);
    }

    bindItemData(items: Array<any>) {
        let self = this;
        if (self.displayMethod() == self.displayType.DISPLAY) {
            let idx = 1;
            items.forEach(stampData => {
                stampData.code = ++idx;
                stampData.stampDate = nts.uk.time.applyFormat("Short_YMDW", stampData.stampDate);
                stampData.stampHowAndTime = "<div class='inline-bl'>" + stampData.stampHow + "</div>" + stampData.stampTime;
            });

            self.items(items);
        } else if (self.displayMethod() == self.displayType.SHOW_TIME_CARD) {

        }
    }

}

interface IStartPage {
    timeCard: ITimeCard;
    stampSetting: IStampSettingPerson;
    stampDataOfEmployees: IStampHistoryInfo;
}

interface ITimeCard {
    employeeId: string;
    IAttendanceOneDay: Array<IAttendanceOneDay>;
}

interface IAttendanceOneDay {
    date: string;
    attendance1: ITimeActualStamp;
    leavingStamp1: ITimeActualStamp;
    attendance2: ITimeActualStamp;
    leavingStamp2: ITimeActualStamp;
}

interface ITimeActualStamp {
    numberOfReflectionStamp: number;
    
    actualAfterRoundingTime: string;
    actualTimeWithDay: string;
    actualLocationCode: string;
    actualStampSourceInfo: number;

    stampAfterRoundingTime: string;
    stampTimeWithDay: string;
    stampLocationCode: string;
    stampStampSourceInfo: number;
}

interface IStampHistoryInfo {
    code: string;
    stampDate: string;
    stampHowAndTime: string;
    timeStampType: string;
}

interface IStampSettingPerson {
    buttonEmphasisArt: boolean;
    historyDisplayMethod: number;
    correctionInterval: number;
    textColor: string;
    backGroundColor: string;
}