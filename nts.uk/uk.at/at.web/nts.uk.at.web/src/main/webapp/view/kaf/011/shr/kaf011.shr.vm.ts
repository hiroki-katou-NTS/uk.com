module nts.uk.at.view.kaf011.shr.screenModel {

    import dialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    export class ViewModel {

        constructor() {

        }

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

        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());

        takingOutWk: KnockoutObservable<WorkItems> = ko.observable(new WorkItems());

        holidayWk: KnockoutObservable<WorkItems> = ko.observable(new WorkItems());

        stereoTypes = ko.observableArray([]);

        stereoSelectedType: KnockoutObservable<number> = ko.observable(0);

        reason: KnockoutObservable<string> = ko.observable('');

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        start(): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: nts.uk.time.formatDate(self.appDate(), "yyyy/MM/dd").format(),
                    uiType: 1
                };

            service.start(startParam).done((data: IHolidayShipment) => {
                self.setDataFromStart(data);


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
            }
        }

        register() {
            let self = this;
            dialog.alertError({ messageId: "register" });
        }

        openKDL003(isTakingOut) {
            let self = this,
                vm: ViewModel = __viewContext['viewModel'],
                workItems: WorkItems = isTakingOut === true ? self.takingOutWk() : self.holidayWk(),
                workTypeCodes = workItems.wkTypes(),
                selectedWorkTypeCode = workItems.wkCD(),
                WorkTimeCd = workItems.wkTimeCD();

            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: selectedWorkTypeCode,
                workTimeCodes: [],
                selectedWorkTimeCode: WorkTimeCd,
            }, true);
            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData: IWorkTime = nts.uk.ui.windows.getShared('childData');

                workItems.wkText(vm.genWorkingText(childData));
                workItems.wkTimeCD(childData.selectedWorkTimeCode);
                if (childData.first) {
                    workItems.wkCD(childData.selectedWorkTypeCode);
                    workItems.wkTime().startTime(childData.first.start);
                    workItems.wkTime().endTime(childData.first.end);
                }
                //フォーカス制御
                //self.changeFocus('#inpStartTime1');
            });
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
        wkCD: KnockoutObservable<string> = ko.observable('');
        wkTimeCD: KnockoutObservable<string> = ko.observable('');
        wkTime: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());
        wkText: KnockoutObservable<string> = ko.observable('');
        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());
        constructor() {
            let self = this;
            self.wkCD.subscribe((newWkType) => {
                if (self.wkTimeCD()) {
                    let changeWkTypeParam = {
                        wkTypeCD: newWkType,
                        wkTimeCD: self.wkTimeCD()

                    };
                    service.changeWkType(changeWkTypeParam).done((data: Array<ITimezoneUse>) => {
                        if (data.length > 0) {
                            self.wkTime().startTime(data[0].start);
                            self.wkTime().endTime(data[0].end);
                        }
                    });
                }
            });

            self.appDate.subscribe((newDate) => {
                service.changeDay(nts.uk.time.formatDate(newDate, "yyyy/MM/dd").format()).done((data) => {

                })
            });
        }
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