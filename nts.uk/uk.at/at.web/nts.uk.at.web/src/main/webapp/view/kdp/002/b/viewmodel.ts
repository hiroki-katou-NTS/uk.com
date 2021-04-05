/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const kDP002RequestUrl = {

    getAllStampingResult: "at/record/workrecord/stamp/management/getAllStampingResult/",
    getInfo: 'ctx/sys/auth/grant/rolesetperson/getempinfo/',
    NOTIFICATION_STAMP: 'screen/at/kdp002/b/notification_by_stamp',
    SETTING_NIKONIKO: 'screen/at/kdp002/b/setting_emoji_stamp'
}

interface TimeClock {
    tick: number;
    now: KnockoutObservable<Date>;
    style: KnockoutObservable<string>;
    displayTime: KnockoutObservable<number>;
}

const initTime = (): TimeClock => ({
    tick: -1,
    now: ko.observable(new Date()),
    style: ko.observable(''),
    displayTime: ko.observable(10)
});

@bean()
class KDP002BViewModel extends ko.ViewModel {

    modeNikoNiko: KnockoutObservable<boolean> = ko.observable(true);
    // B2_2
    employeeCodeName: KnockoutObservable<string> = ko.observable("基本給");
    // B3_2
    dayName: KnockoutObservable<string> = ko.observable("基本給");
    // B3_3
    timeName: KnockoutObservable<string> = ko.observable("基本給");
    // G4_2
    checkHandName: KnockoutObservable<string> = ko.observable("基本給");
    // G5_2
    numberName: KnockoutObservable<string> = ko.observable("基本給");
    // G6_2
    laceName: KnockoutObservable<string> = ko.observable("基本給");

    time: TimeClock = initTime();

    items: KnockoutObservableArray<ItemModels> = ko.observableArray([]);
    columns2: KnockoutObservableArray<any> = ko.observableArray([
        { headerText: "id", key: 'id', width: 100, hidden: true },
        { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_45") + "</div>", key: 'stampDate', width: 130 },
        { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_46") + "</div>", key: 'stampHowAndTime', width: 90 },
        { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_47") + "</div>", key: 'timeStampType', width: 180 }
    ]);
    currentCode: KnockoutObservable<any> = ko.observable();
    currentCodeList: KnockoutObservableArray<any>;

    listStampRecord: KnockoutObservableArray<any> = ko.observableArray([]);
    currentDate: KnockoutObservable<string> = ko.observable(moment(new Date()).add(-3, 'days').format("YYYY/MM/DD") + " ～ " + moment(new Date()).format("YYYY/MM/DD"));
    currentStampData: KnockoutObservable<any> = ko.observable({ stampDate: null, stampTime: null, stampArtName: null, cardNumberSupport: null, workLocationCD: null });
    resultDisplayTime: KnockoutObservable<number> = ko.observable(0);
    disableResultDisplayTime: KnockoutObservable<boolean> = ko.observable(true);
    interval: KnockoutObservable<number> = ko.observable(0);
    infoEmpFromScreenA: any;
    notificationStamp: KnockoutObservableArray<IMsgNotices> = ko.observableArray([]);
    modeShowPointNoti: KnockoutObservable<boolean> = ko.observable(false);

    constructor() {
        super();
    }

    created(params: any) {

        const vm = this;
        vm.$window.shared("resultDisplayTime").done(displayTime => {
            vm.resultDisplayTime(displayTime);

            vm.$window.shared("infoEmpToScreenB").done(infoEmp => {

                vm.infoEmpFromScreenA = infoEmp;
                vm.disableResultDisplayTime(vm.resultDisplayTime() > 0 ? true : false);

                vm.startPage();
            });
        });;

        vm.$ajax(kDP002RequestUrl.SETTING_NIKONIKO)
            .then((data: boolean) => {
                vm.modeNikoNiko(data);
            });
    }

    startPage(): JQueryPromise<any> {
        let self = this,
            dfd = $.Deferred();
        let dfdGetAllStampingResult = self.getAllStampingResult();
        let dfdGetEmpInfo = self.getEmpInfo();
        self.getNotification();
        $.when(dfdGetAllStampingResult, dfdGetEmpInfo).done((dfdGetAllStampingResultData, dfdGetEmpInfoData) => {
            if (self.resultDisplayTime() > 0) {
                if (!ko.unwrap(self.modeNikoNiko)) {
                    setInterval(self.closeDialog, self.resultDisplayTime() * 1000);
                }
                setInterval(() => {
                    self.resultDisplayTime(self.resultDisplayTime() - 1);
                }, 1000);
            }
            dfd.resolve();
        });
        return dfd.promise();
    }

    getDataById(id: any) {
        let self = this;
        for (let j = 0; j < _.size(self.items()); j++) {
            if (self.items()[j].id == id) {
                for (let i = 0; i < _.size(self.listStampRecord()); i++) {
                    if (self.listStampRecord()[i].stampDate == self.items()[j].date && self.listStampRecord()[i].stampTime == self.items()[j].time) {
                        self.currentStampData(self.listStampRecord()[i]);
                        break;
                    }
                }
                break;
            }
        }
    }

    getAllStampingResult(): JQueryPromise<any> {
        const vm = this;
        let dfd = $.Deferred();
        let sid = vm.infoEmpFromScreenA.employeeId;

        vm.$ajax("at", kDP002RequestUrl.getAllStampingResult + sid).then(function (data) {
            _.forEach(data, (a) => {
                let items = _.orderBy(a.stampDataOfEmployeesDto.stampRecords, ['stampTimeWithSec'], ['desc']);
                _.forEach(items, (sr) => {
                    vm.listStampRecord.push(sr);
                });
            });
            if (_.size(vm.listStampRecord()) > 0) {
                vm.laceName(data[0].workPlaceName);
                vm.listStampRecord(_.orderBy(vm.listStampRecord(), ['stampTimeWithSec'], ['desc']));
                let items = vm.items();
                _.forEach(vm.listStampRecord(), (sr) => {

                    let changeClockArtDisplay = vm.getTextAlign(sr);

                    let dateDisplay = nts.uk.time.applyFormat("Short_YMDW", sr.stampDate);
                    if (moment(sr.stampDate).day() == 6) {
                        dateDisplay = "<span class='color-schedule-saturday' >" + dateDisplay + "</span>";
                        sr.stampDate = "<span class='color-schedule-saturday' >" + sr.stampDate + "</span>";
                    } else if (moment(sr.stampDate).day() == 0) {
                        dateDisplay = "<span class='color-schedule-sunday'>" + dateDisplay + "</span>";
                        sr.stampDate = "<span class='color-schedule-sunday'>" + sr.stampDate + "</span>";
                    }
                    items.push(new ItemModels(
                        dateDisplay,
                        "<div class='inline-bl'>" + sr.stampHow + "</div>" + sr.stampTime,
                        changeClockArtDisplay,
                        sr.stampDate,
                        sr.stampTime
                    ));

                });

                vm.items(items);
                vm.getDataById(vm.items()[0].id);
                dfd.resolve();
            } else {
                nts.uk.ui.dialog.alertError("Stamp Data Not Found!!!").then(() => {
                    nts.uk.ui.windows.close();
                });

            }
        });
        return dfd.promise();
    }

    getTextAlign(sr: any): string {

        let value = sr.buttonValueType;
        if (ButtonType.GOING_TO_WORK == value || ButtonType.RESERVATION_SYSTEM == value) {

            return `<div class='full-width' style='text-align: left' >` + sr.stampArtName + '</div>';

        }

        if (ButtonType.WORKING_OUT == value) {

            return `<div class='full-width' style='text-align: right'>` + sr.stampArtName + '</div>';

        }

        return sr.stampArtName ? `<div class='full-width' style='text-align: center'>` + sr.stampArtName + '</div>' : '';

    }

    getNotification() {
        const vm = this;

        const param = {
            startDate: vm.$date.now(),
            endDate: vm.$date.now(),
            sid: vm.infoEmpFromScreenA.employeeId
        }

        vm.$blockui('invisible')
            .then(() => {
                vm.$ajax(kDP002RequestUrl.NOTIFICATION_STAMP, param)
                    .done((data: IMsgNotices[]) => {

                        vm.notificationStamp(data);

                        var isShow = 0;
                        _.forEach(data, ((value) => {
                            _.forEach(value, ((value1) => {
                                if (value1.flag) {
                                    isShow++;
                                }
                            }));
                        }));
                        vm.notificationStamp(data);

                        if (isShow > 0) {
                            vm.modeShowPointNoti(true);
                        }
                    });
            })
            .always(() => {
                vm.$blockui('clear');
            });
    }

    getEmpInfo(): JQueryPromise<any> {
        const vm = this;
        let dfd = $.Deferred();
        let employeeId = vm.infoEmpFromScreenA.employeeId;
        vm.$ajax('com', kDP002RequestUrl.getInfo + employeeId).done(function (data) {
            vm.employeeCodeName(data.employeeCode + " " + data.personalName);
            dfd.resolve();
        });
        return dfd.promise();
    }

    openDialogU() {
        const vm = this;
        const params = { sid: vm.infoEmpFromScreenA.employeeId, data: ko.unwrap(vm.notificationStamp) };
        vm.$window.modal('/view/kdp/002/u/index.xhtml', params);
    }

    public closeDialog(): void {
        nts.uk.ui.windows.close();
    }

}

class ItemModels {
    id: string;
    stampDate: string;
    stampHowAndTime: string;
    timeStampType: string;
    date: string;
    time: string
    constructor(stampDate: string, stampHowAndTime: string, timeStampType: string, date: string, time: string) {
        this.id = nts.uk.util.randomId();
        this.stampDate = stampDate;
        this.stampHowAndTime = stampHowAndTime;
        this.timeStampType = timeStampType;
        this.date = date;
        this.time = time;
    }
}

interface IMsgNotices {
    creator: string;
    flag: boolean;
    message: IEmployeeIdSeen;
}

interface IEmployeeIdSeen {
    endDate: string,
    inputDate: Date
    modifiedDate: Date
    notificationMessage: string
    startDate: Date
    targetInformation: ITargetInformation
}

interface ITargetInformation {
    destination: string;
    targetSIDs: string;
    targetWpids: string[];
}

enum ButtonType {
    // 系

    GOING_TO_WORK = 1,
    // 系

    WORKING_OUT = 2,
    // "外出系"

    GO_OUT = 3,
    // 戻り系

    RETURN = 4,
    // 予約系

    RESERVATION_SYSTEM = 5
}