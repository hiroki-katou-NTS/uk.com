module nts.uk.at.view.kaf011.shr.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;

    export class ViewModel {



        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);
        appComSelectedCode: KnockoutObservable<number> = ko.observable(1);

        appDate: KnockoutObservable<String> = ko.observable(formatDate(moment().toDate(), "yyyy/MM/dd").format());

        takingOutWk: KnockoutObservable<WorkItems> = ko.observable(new WorkItems());

        holidayWk: KnockoutObservable<WorkItems> = ko.observable(new WorkItems());

        appReasons = ko.observableArray([]);

        appReasonSelectedType: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        employeeID: KnockoutObservable<string> = ko.observable('');

        manualSendMailAtr: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            let self = this;
            self.takingOutWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });
            self.holidayWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });



        }
        changeDate() {

            let vm: ViewModel = __viewContext['viewModel'],
                changeDateParam = {
                    holidayDate: vm.holidayWk().appDate(),
                    takingOutDate: vm.takingOutWk().appDate(),
                    comType: vm.appComSelectedCode(),
                    uiType: 1

                }
            service.changeDay(changeDateParam).done((data) => {
                vm.employeeID(data.employeeID);
            });
        }

        start(): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: self.appDate(),
                    uiType: 1
                };

            service.start(startParam).done((data: IHolidayShipment) => {
                self.setDataFromStart(data);


            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                dfd.resolve();

            });
            return dfd.promise();
        }
        genWorkingText(childData: IWorkTime) {
            let self = this,
                result = childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName;
            if (childData.first) {
                result += ' ' + self.parseTime(childData.first.start) + '~' + self.parseTime(childData.first.end);
                if (childData.second) {

                }
            }
            return result;

        }
        parseTime(value) {
            return nts.uk.time.parseTime(value, true).format()
        }

        setDataFromStart(data: IHolidayShipment) {
            let self = this;
            if (data) {
                self.prePostSelectedCode(data.preOrPostType);
                self.takingOutWk().wkTypes(data.takingOutWkTypes || []);
                self.holidayWk().wkTypes(data.holidayWkTypes || []);
                self.appReasons(data.appReasons || []);
                self.employeeID(data.employeeID);
                self.manualSendMailAtr(data.applicationSetting.manualSendMailAtr);
            }
        }

        register() {
            let self = this,

                saveCmd: ISaveHolidayShipmentCommand = {
                    recCmd: ko.mapping.toJS(self.takingOutWk()),
                    absCmd: ko.mapping.toJS(self.holidayWk()),
                    comType: self.appComSelectedCode(),
                    usedDays: 1,
                    appCmd: {
                        appReasonID: self.appReasonSelectedType(),
                        applicationReason: self.reason(),
                        prePostAtr: self.prePostSelectedCode(),
                        enteredPersonSID: self.employeeID(),
                    }
                };

            saveCmd.absCmd.changeWorkHoursType = saveCmd.absCmd.changeWorkHoursType ? 1 : 0;

            service.save(saveCmd).done(() => {

            }).fail((error) => {
                dialog({ messageId: error.messageId });

            });
        }

        openKDL009() {
            //chưa có màn hình KDL009
            //            nts.uk.ui.windows.sub.modal('/view/kdl/009/a/index.xhtml').onClosed(function(): any {
            //
            //            });

        }


    }

    interface IWorkingHour {
        startTime: Date;
        endTime: Date;
        startType: number;
        endType: number;
    }
    export class WorkingHour {
        startTime: KnockoutObservable<number> = ko.observable(null);
        endTime: KnockoutObservable<number> = ko.observable(null);
        startTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, text: text('KAF011_39') },
            { code: 2, text: text('KAF011_40') }
        ]);
        startType: KnockoutObservable<number> = ko.observable(1);
        endTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, text: text('KAF011_42') },
            { code: 2, text: text('KAF011_43') }
        ]);
        endType: KnockoutObservable<number> = ko.observable(1);

        timeOption = ko.mapping.fromJS({
            timeWithDay: true,
            width: "130px"
        });
        constructor(IWorkingHour?) {
            if (IWorkingHour) {
                this.startTime = ko.observable(IWorkingHour.startTime || null);
                this.endTime = ko.observable(IWorkingHour.endTime || null);
                this.startType = ko.observable(IWorkingHour.startType || 1);
                this.endType = ko.observable(IWorkingHour.endType || 1);
            }
        }
    }
    interface IWorkTime {
        selectedWorkTypeCode: string;
        first: any;
        second: any;
        selectedWorkTimeCode: string;
        selectedWorkTimeName: string;

    }

    interface IHolidayShipment {
        applicationSetting: any;
        appEmploymentSettings: Array<any>;
        approvalFunctionSetting: any;
        refDate: string;
        takingOutWkTypes: Array<any>;
        holidayWkTypes: Array<any>;
        preOrPostType: any;
        appReasons: Array<any>;
        employeeID: string;
    }
    interface IWorkType {
        /* 勤務種類コード */
        workTypeCode: string;
        /* 勤務種類名称 */
        name: string;
    }
    interface ICallerParameter {
        workTypeCodes: Array<string>;
        selectedWorkTypeCode: string;
        workTimeCodes: Array<string>;
        selectedWorkTimeCode: string;
    }
    class WorkItems {
        wkTypes: KnockoutObservableArray<IWorkType> = ko.observableArray([]);
        wkTypeCD: KnockoutObservable<string> = ko.observable('');
        wkTimeCD: KnockoutObservable<string> = ko.observable('');
        wkTime1: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());
        wkTime2: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());
        wkText: KnockoutObservable<string> = ko.observable('');
        appDate: KnockoutObservable<String> = ko.observable(formatDate(moment().toDate(), "yyyy/MM/dd").format());
        workLocationCD: KnockoutObservable<string> = ko.observable('');
        changeWorkHoursType: KnockoutObservable<number> = ko.observable(1);

        constructor() {
            let self = this;
            self.wkTypeCD.subscribe((newWkType) => {
                if (self.wkTimeCD()) {
                    let changeWkTypeParam = {
                        wkTypeCD: newWkType,
                        wkTimeCD: self.wkTimeCD()

                    };
                    service.changeWkType(changeWkTypeParam).done((data: IChangeWorkType) => {
                        if (data) {
                            if (data.timezoneUseDtos.length > 0) {
                                self.wkTime1().startTime(data.timezoneUseDtos[0].start);
                                self.wkTime1().endTime(data.timezoneUseDtos[0].end);
                            }
                        }
                    });
                }
            });


        }

        openKDL003() {
            let self = this,
                vm: ViewModel = __viewContext['viewModel'],
                workTypeCodes = self.wkTypes(),
                selectedWorkTypeCode = self.wkTypeCD(),
                WorkTimeCd = self.wkTimeCD();

            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: selectedWorkTypeCode,
                workTimeCodes: [],
                selectedWorkTimeCode: WorkTimeCd,
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData: IWorkTime = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    if (childData.selectedWorkTimeCode && childData.selectedWorkTimeName) {
                        self.wkText(vm.genWorkingText(childData));
                    }
                    self.wkTimeCD(childData.selectedWorkTimeCode);
                    if (childData.first) {
                        self.wkTypeCD(childData.selectedWorkTypeCode);
                        self.wkTime1().startTime(childData.first.start);
                        self.wkTime1().endTime(childData.first.end);
                    }
                }
            });

        }
    }
    export interface ISaveHolidayShipmentCommand {
        recCmd: IRecruitmentAppCommand;
        absCmd: IAbsenceLeaveAppCommand;
        comType: number;
        usedDays: number;
        appCmd: IApplicationCommand;
    }

    interface IRecruitmentAppCommand {
        appDate: string;
        wkTypeCD: string;
        workLocationCD: string;
        wkTime1: IWkTimeCommand;
        wkTime2: IWkTimeCommand;
    }
    interface IWkTimeCommand {
        startTime: number;
        startType: number;
        endTime: number;
        endType: number;
        wkTimeCD: string;
    }

    interface IAbsenceLeaveAppCommand {
        appDate: string;
        wkTypeCD: string;
        changeWorkHoursType: number;
        workLocationCD: string;
        wkTime1: IWkTimeCommand;
        wkTime2: IWkTimeCommand;
    }

    interface IApplicationCommand {
        appReasonID: string;
        applicationReason: string;
        prePostAtr: number;
        enteredPersonSID: string;
    }
    interface IChangeWorkType {
        timezoneUseDtos: Array<ITimezoneUse>;
        wkType: any;
    }

    interface ITimezoneUse {
        useAtr: number;
        workNo: number;
        start: number;
        end: number;
    }


    interface IApprovalSetting {
        bottomComment: boolean;
        bottomCommentFontColor: string;
        bottomCommentFontWeight: boolean;
        companyID: string;
        resultDisp: number;
        stampAtr_Care_Disp: number;
        stampAtr_Child_Care_Disp: number;
        stampAtr_GoOut_Disp: number;
        stampAtr_Sup_Disp: number;
        stampAtr_Work_Disp: number;
        stampGoOutAtr_Compensation_Disp: number;
        stampGoOutAtr_Private_Disp: number;
        stampGoOutAtr_Public_Disp: number;
        stampGoOutAtr_Union_Disp: number;
        stampPlaceDisp: number;
        supFrameDispNO: number;
        topComment: string;
        topCommentFontColor: string;
        topCommentFontWeight: boolean;
    }
}