module nts.uk.at.view.kaf011.shr {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;

    export module common {


        export interface IWorkingHour {
            startTime: Date;
            endTime: Date;
            startType: number;
            endType: number;
        }
        export class WorkingHour {
            startTime: KnockoutObservable<number> = ko.observable(null);
            endTime: KnockoutObservable<number> = ko.observable(null);
            startTypes: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, text: text('KAF011_39') },
                { code: 1, text: text('KAF011_40') }
            ]);
            startType: KnockoutObservable<number> = ko.observable(1);
            endTypes: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, text: text('KAF011_42') },
                { code: 1, text: text('KAF011_43') }
            ]);
            endType: KnockoutObservable<number> = ko.observable(1);

            timeOption = ko.mapping.fromJS({
                timeWithDay: true,
                width: "70px"
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
        export interface IWorkTime {
            selectedWorkTypeCode: string;
            first: any;
            second: any;
            selectedWorkTimeCode: string;
            selectedWorkTimeName: string;

        }

        export interface IHolidayShipment {
            applicationSetting: any;
            appEmploymentSettings: Array<any>;
            approvalFunctionSetting: any;
            refDate: string;
            recWkTypes: Array<any>;
            absWkTypes: Array<any>;
            preOrPostType: any;
            appReasons: Array<any>;
            employeeID: string;
            employeeName: string;
            drawalReqSet: any;
            appTypeSet: any;
        }

        export interface IAppTypeSet {
            appType: number;
            canClassificationChange: number;
            companyId: string;
            displayAppReason: number;
            displayFixedReason: number;
            displayInitialSegment: number;
            sendMailWhenApproval: number;
            sendMailWhenRegister: number;

        }

        export class AppTypeSet {
            appType: KnockoutObservable<number> = ko.observable(0);
            canClassificationChange: KnockoutObservable<number> = ko.observable(0);
            companyId: KnockoutObservable<string> = ko.observable('');
            displayAppReason: KnockoutObservable<number> = ko.observable(0);
            displayFixedReason: KnockoutObservable<number> = ko.observable(0);
            displayInitialSegment: KnockoutObservable<number> = ko.observable(0);
            sendMailWhenApproval: KnockoutObservable<number> = ko.observable(0);
            sendMailWhenRegister: KnockoutObservable<number> = ko.observable(0);
            constructor(data: IAppTypeSet) {
                if (data) {
                    this.appType(data.appType);
                    this.canClassificationChange(data.canClassificationChange);
                    this.companyId(data.companyId);
                    this.displayAppReason(data.displayAppReason);
                    this.displayFixedReason(data.displayFixedReason);
                    this.displayInitialSegment(data.displayInitialSegment);
                    this.sendMailWhenApproval(data.sendMailWhenApproval);
                    this.sendMailWhenRegister(data.sendMailWhenRegister);
                }

            }
        }

        export interface IWorkType {
            /* 勤務種類コード */
            workTypeCode: string;
            /* 勤務種類名称 */
            name: string;
        }
        export interface ICallerParameter {
            workTypeCodes: Array<string>;
            selectedWorkTypeCode: string;
            workTimeCodes: Array<string>;
            selectedWorkTimeCode: string;
        }

        export class DrawalReqSet {
            deferredComment: KnockoutObservable<string> = ko.observable('');
            deferredBold: KnockoutObservable<boolean> = ko.observable(false);
            pickUpComment: KnockoutObservable<string> = ko.observable('');
            pickUpBold: KnockoutObservable<boolean> = ko.observable(false);
            deferredWorkTimeSelect: KnockoutObservable<number> = ko.observable(0);
            simulAppliReq: KnockoutObservable<number> = ko.observable(0);
            permissionDivision: KnockoutObservable<number> = ko.observable(0);
            constructor(drawalReqSet) {
                if (drawalReqSet) {
                    this.deferredComment(drawalReqSet.deferredComment || '');
                    this.deferredBold(drawalReqSet.deferredBold || false);
                    this.pickUpComment(drawalReqSet.deferredComment || '');
                    this.pickUpBold(drawalReqSet.deferredBold || false);
                    this.deferredWorkTimeSelect(drawalReqSet.deferredWorkTimeSelect || 0);
                    this.simulAppliReq(drawalReqSet.simulAppliReq || 0);
                    this.permissionDivision(drawalReqSet.permissionDivision || 0);

                    let comItems;
                    if (this.simulAppliReq() == 1) {
                        comItems = [
                            { code: 0, text: text('KAF011_19') },
                            { code: 2, text: text('KAF011_21') },
                        ]

                    } else {
                        comItems = [
                            { code: 0, text: text('KAF011_19') },
                            { code: 1, text: text('KAF011_20') },
                            { code: 2, text: text('KAF011_21') },
                        ]

                    }

                    let vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                    vm.appComItems(comItems);
                }
            }
        }
        export class AppItems {
            appID: KnockoutObservable<string> = ko.observable('');
            wkTypes: KnockoutObservableArray<IWorkType> = ko.observableArray([]);
            wkType: KnockoutObservable<WkType> = ko.observable(new WkType(null));
            wkTypeCD: KnockoutObservable<string> = ko.observable(null);
            wkTimeCD: KnockoutObservable<string> = ko.observable('');
            wkTimeName: KnockoutObservable<string> = ko.observable('');
            wkTime1: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());
            wkTime2: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());
            wkText: KnockoutObservable<string> = ko.observable('');
            appDate: KnockoutObservable<String> = ko.observable(formatDate(moment().toDate(), "yyyy/MM/dd").format());
            changeWorkHoursType: KnockoutObservable<number> = ko.observable(0);

            constructor() {
                let self = this;
                self.wkTypeCD.subscribe((newWkType) => {
                    block.invisible();
                    let changeWkTypeParam = {
                        wkTypeCD: newWkType,
                        wkTimeCD: self.wkTimeCD()

                    };
                    service.changeWkType(changeWkTypeParam).done((data: IChangeWorkType) => {
                        if (data) {
                            if (data.timezoneUseDtos) {
                                let timeZone1 = data.timezoneUseDtos[0];
                                let timeZone2 = data.timezoneUseDtos[1];
                                if (timeZone1) {
                                    self.wkTime1().startTime(timeZone1.start);
                                    self.wkTime1().endTime(timeZone1.end);
                                    self.wkTime1().startType(timeZone1.useAtr);
                                    self.wkTime1().endType(timeZone1.useAtr);
                                } else {
                                    self.wkTime1(new WorkingHour());
                                }
                                if (timeZone2) {
                                    self.wkTime2().startTime(timeZone2.start);
                                    self.wkTime2().endTime(timeZone2.end);
                                    self.wkTime2().startType(timeZone2.useAtr);
                                    self.wkTime2().startType(timeZone2.useAtr);
                                } else {
                                    self.wkTime2(new WorkingHour());
                                }
                            } else {
                                self.wkTime1(new WorkingHour());
                                self.wkTime2(new WorkingHour());
                            }
                            self.updateWorkingText();
                            self.wkType().workAtr(data.wkType.workAtr);
                        }
                    }).always(() => {
                        block.clear();
                    });

                });

                self.appDate.subscribe((newDate) => {
                    if (newDate.length > 10) {
                        return;
                    }
                    self.changeDate();
                });
            }

            enableWkTime() {
                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (self.changeWorkHoursType() == 0 || vm.drawalReqSet().permissionDivision() == 0) {
                    return false;
                }

                return true;

            }

            showWorkTimeZone() {
                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (self.wkType().workAtr() == 0 || vm.drawalReqSet().deferredWorkTimeSelect() == 0) {
                    return false;
                }
                return true;
            }

            showWorkingTime1() {

                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (self.wkType().workAtr() == 0 || vm.drawalReqSet().deferredWorkTimeSelect() != 0) {
                    return false;
                }
                return true;
            }

            changeDate() {
                block.invisible();
                let vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'],
                    changeDateParam = {
                        holidayDate: vm.absWk().appDate(),
                        takingOutDate: vm.recWk().appDate(),
                        comType: vm.appComSelectedCode(),
                        uiType: 0

                    }
                service.changeDay(changeDateParam).done((data) => {
                    vm.employeeID(data.employeeID);
                    vm.prePostSelectedCode(data.preOrPostType);
                    vm.manualSendMailAtr(data.applicationSetting.manualSendMailAtr);
                }).always(() => {
                    block.clear();
                });;
            }

            parseText(date) {
                return nts.uk.time.formatDate(new Date(date()), "yyyy/MM/dd");
            }

            parseTime(value) {
                return nts.uk.time.parseTime(value, true).format()
            }
            updateWorkingText() {
                let self = this,
                    text = self.wkTimeCD() + ' ' + self.wkTimeName();
                if (self.wkTime1().startTime()) {
                    text += ' ' + self.parseTime(self.wkTime1().startTime()) + '~' + self.parseTime(self.wkTime1().endTime());
                }
                self.wkText(text);

            }


            openCDialog() {
                let self = this,
                    vm = nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                nts.uk.ui.windows.setShared('KAF_011_PARAMS', {
                    prePostSelectedCode: vm.appComSelectedCode(),
                    appReasons: vm.appReasons(),
                    reason: vm.reason(),
                    appReasonSelectedID: vm.appReasonSelectedID(),
                    appDate: self.appDate()
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kaf/011/c/index.xhtml').onClosed(function(): any {

                });

            }

            openKDL003() {
                let self = this,
                    workTypeCodes = self.wkTypes().map(function(x) { return x.workTypeCode; }),
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
                        self.wkTimeCD(childData.selectedWorkTimeCode);
                        if (childData.first) {
                            self.wkTypeCD(childData.selectedWorkTypeCode);
                            self.wkTime1().startTime(childData.first.start);
                            self.wkTime1().endTime(childData.first.end);
                            self.wkTimeName(childData.selectedWorkTimeName);
                        }
                        if (childData.selectedWorkTimeCode && childData.selectedWorkTimeName) {
                            self.updateWorkingText();
                        }


                    }
                });

            }
        }
        export class WkType {
            workAtr: KnockoutObservable<number> = ko.observable(0);
            constructor(wkType) {
                if (wkType) {
                    this.workAtr(wkType.workAtr);
                }
            }
        }
        export interface ISaveHolidayShipmentCommand {
            recCmd: IRecruitmentAppCommand;
            absCmd: IAbsenceLeaveAppCommand;
            comType: number;
            usedDays: number;
            appCmd: IApplicationCommand;
        }

        export interface IRecruitmentAppCommand {
            appDate: string;
            wkTypeCD: string;
            workLocationCD: string;
            wkTime1: IWkTimeCommand;
            wkTime2: IWkTimeCommand;
        }
        export interface IWkTimeCommand {
            startTime: number;
            startType: number;
            endTime: number;
            endType: number;
            wkTimeCD: string;
        }

        export interface IAbsenceLeaveAppCommand {
            appDate: string;
            wkTypeCD: string;
            changeWorkHoursType: number;
            workLocationCD: string;
            wkTime1: IWkTimeCommand;
            wkTime2: IWkTimeCommand;
        }

        export interface IApplicationCommand {
            appReasonID: string;
            applicationReason: string;
            prePostAtr: number;
            enteredPersonSID: string;
            version: number;
        }
        export interface IChangeWorkType {
            timezoneUseDtos: Array<ITimezoneUse>;
            wkType: any;
        }

        export interface ITimezoneUse {
            useAtr: number;
            workNo: number;
            start: number;
            end: number;
        }


        export interface IApprovalSetting {
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
}