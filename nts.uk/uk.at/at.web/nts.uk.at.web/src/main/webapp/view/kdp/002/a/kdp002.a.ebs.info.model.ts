const DATE_AND_DAY_FORMAT = 'D(ddd)';
class EmbossGridInfo {
    
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    currentCode: KnockoutObservable<any> = ko.observable({});
    items: KnockoutObservableArray<any> = ko.observableArray([]);
    displayMethod: KnockoutObservable<any>;
    displayType: any = {HIDE: 0, DISPLAY: 1, SHOW_TIME_CARD: 2};
    dateValue: KnockoutObservable<any>;
    yearMonth: KnockoutObservable<any>;
    
    constructor(start: IStartPage) {
        let self = this;
        let setting = start.stampSetting;
        self.displayMethod = ko.observable(setting.historyDisplayMethod);
        self.dateValue = ko.observable({startDate: moment().add(-3, 'days').format('YYYY/MM/DD'), endDate: moment().format('YYYY/MM/DD')});
        self.yearMonth = ko.observable(moment().format('YYYY/MM'));
        
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
                { headerText: nts.uk.resource.getText('KDP002_33', ['#Com_WorkIn']), key: 'workIn1', width: 80 }, 
                { headerText: nts.uk.resource.getText('KDP002_34', ['#Com_WorkOut']), key: 'workOut1', width: 80 },
                { headerText: nts.uk.resource.getText('KDP002_35', ['#Com_WorkIn']), key: 'workIn2', width: 80 }, 
                { headerText: nts.uk.resource.getText('KDP002_36', ['#Com_WorkOut']), key: 'workOut2', width: 100 }
            ]);
            self.bindItemData(start.timeCard.listAttendances);
        }
    }

    bindItemData(items: Array<any>) {
        let self = this;
        if (self.displayMethod() == self.displayType.DISPLAY) {
            let idx = 1;
            items.forEach(stampData => {
                stampData.code = ++idx;
                stampData.stampDate = nts.uk.time.applyFormat("Short_YMDW", stampData.stampDate);
                stampData.stampHowAndTime = "<div class='inline-bl'>" + stampData.stampHow + "</div>" + stampData.stampTime;
                if(stampData.changeClockArt == 0) {
                    stampData.timeStampType = `<div class='full-width' style='text-align: left'>` + stampData.timeStampType + '</div>';
                } else if (stampData.changeClockArt == 1) {
                    stampData.timeStampType = `<div class='full-width' style='text-align: right'>` + stampData.timeStampType + '</div>';
                } else {
                    stampData.timeStampType = `<div class='full-width' style='text-align: center'>` + stampData.timeStampType + '</div>';
                }
            });

            self.items(items);
        } else if (self.displayMethod() == self.displayType.SHOW_TIME_CARD) {
            let idx = 1;
            items.forEach(timeCard => {
                timeCard.code = ++idx;
                timeCard.date = moment(timeCard.date).format(DATE_AND_DAY_FORMAT);
                timeCard.workIn1 = timeCard.workIn1 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workIn1) : null;
                timeCard.workOut1 =  timeCard.workOut1 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workOut1) : null;
                timeCard.workIn2 = timeCard.workIn2 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workIn2) : null;
                timeCard.workOut2 = timeCard.workOut2 ?  nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workOut2) : null;
            });
            console.log(items);
            self.items(items);
        }
    }

}

interface IStartPage {
    timeCard: ITimeCard;
    stampSetting: IStampSettingPerson;
    stampDataOfEmployees: IStampHistoryInfo;
    stampToSuppress: IStampToSuppress;
    stampResultDisplay: IStampResultDisplay;

}

interface IStampResultDisplay {
    companyId: string;
    notUseAttr: number;
    displayItemId: Array<number>;
}

interface IStampToSuppress {
    isUse: boolean;
    goingToWork: boolean;
    departure: boolean;
    goOut: boolean;
    turnBack: boolean;
}

interface ITimeCard {
    employeeId: string;
    IAttendanceOneDay: Array<IAttendanceOneDay>;
}

interface IAttendanceOneDay {
    date: string;
    workIn1: string;
    workOut1: string;
    workIn2: string;
    workOut2: string;
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