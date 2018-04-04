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
            absApp: any;
            application: any;
            recApp: any;
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
            deferredLettleColor: KnockoutObservable<string> = ko.observable('');
            pickUpComment: KnockoutObservable<string> = ko.observable('');
            pickUpBold: KnockoutObservable<boolean> = ko.observable(false);
            pickUpLettleColor: KnockoutObservable<string> = ko.observable('');
            deferredWorkTimeSelect: KnockoutObservable<number> = ko.observable(0);
            simulAppliReq: KnockoutObservable<number> = ko.observable(0);
            permissionDivision: KnockoutObservable<any> = ko.observable(null);
            constructor(drawalReqSet) {
                if (drawalReqSet) {
                    this.deferredComment(drawalReqSet.deferredComment || '');
                    this.deferredBold(drawalReqSet.deferredBold || false);
                    this.pickUpComment(drawalReqSet.pickUpComment || '');
                    this.pickUpBold(drawalReqSet.pickUpBold || false);
                    this.deferredWorkTimeSelect(drawalReqSet.deferredWorkTimeSelect || 0);
                    this.simulAppliReq(drawalReqSet.simulAppliReq || 0);
                    this.permissionDivision(drawalReqSet.permissionDivision != undefined ? drawalReqSet.permissionDivision : 1);
                    this.deferredLettleColor(drawalReqSet.deferredLettleColor);
                    this.pickUpLettleColor(drawalReqSet.pickUpLettleColor);
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
            appDate: KnockoutObservable<Date> = ko.observable(null);
            changeWorkHoursType: KnockoutObservable<any> = ko.observable(null);

            constructor() {
                let self = this;
                self.wkTypeCD.subscribe((newWkType) => {
                    let vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                    if (!vm.screenModeNew()) { return; }
                    let changeWkTypeParam = {
                        wkTypeCD: newWkType,
                        wkTimeCD: self.wkTimeCD()
                    };

                    block.invisible();
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
                self.wkTypes.subscribe((item) => {
                    if (item.length) {
                        self.wkTypeCD(item[0].workTypeCode);
                    }
                });

                self.appDate.subscribe((newDate) => {
                    let vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'],
                        changeDateParam = {
                            holidayDate: vm.absWk().appDate(),
                            takingOutDate: vm.recWk().appDate(),
                            comType: vm.appComSelectedCode(),
                            uiType: 0

                        }
                    if (!vm.screenModeNew() || !newDate || new Date(newDate.toString()).toString() == "Invalid Date" || newDate.toString().length != 10) { return; }
                    block.invisible();
                    service.changeDay(changeDateParam).done((data: IHolidayShipment) => {
                        vm.recWk().wkTypes(data.recWkTypes || []);
                        vm.absWk().wkTypes(data.absWkTypes || []);
                        vm.kaf000_a.start("", 1, 10, moment(data.refDate).format("YYYY/MM/DD")).done(() => {
                        });
                    }).always(() => {
                        block.clear();
                    });;
                });
            }

            enableWkTime() {
                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (!self.changeWorkHoursType() || vm.drawalReqSet().permissionDivision() == 0) {
                    return false;
                }

                return true;

            }

            showWorkTimeZone() {
                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (self.wkType().workAtr() == 0 || vm.drawalReqSet().deferredWorkTimeSelect() == 0) {
                    return false;
                }

                let morningType = self.wkType().morningCls(),
                    afternoonType = self.wkType().afternoonCls(),
                    Pause = 8,
                    Attendance = 0;

                if (self.wkType().workAtr() == 1) {
                    if (vm.drawalReqSet().deferredWorkTimeSelect() == 1) {
                        if ((afternoonType == Attendance && morningType == Pause) || (afternoonType == Pause && morningType == Attendance)) {
                            return true;
                        } else {
                            let wktype = "1234569";
                            if ((wktype.indexOf(afternoonType) != -1 && morningType == Pause) || (wktype.indexOf(morningType) != -1 && afternoonType == Pause)) {
                                return true;
                            } else {
                                return false;
                            }

                        }
                    }
                }

                if (self.wkType().workAtr() == 2) {
                    if (vm.drawalReqSet().deferredWorkTimeSelect() == 1) {
                        if (afternoonType != 0 && morningType != 0) {
                            return false;
                        }
                    }

                }
                return true;
            }

            showWorkingTime1() {
                let self = this, vm: nts.uk.at.view.kaf011.a.screenModel.ViewModel = __viewContext['viewModel'];
                if (self.wkType().workAtr() == 0) {
                    return false;
                }
                if (self.wkType().workAtr() == 1) {
                    if (vm.drawalReqSet().deferredWorkTimeSelect() == 0) {
                        return false;
                    }
                    if (vm.drawalReqSet().deferredWorkTimeSelect() != 1) {
                        return true;
                    }

                }
                if (self.wkType().workAtr() == 2) {
                    if (vm.drawalReqSet().deferredWorkTimeSelect() == 0) {
                        return false;
                    }
                    if (vm.drawalReqSet().deferredWorkTimeSelect() != 1) {
                        return true;
                    }
                }
                return true;
            }

            parseText(date) {
                return nts.uk.time.formatDate(date(), "YYYY/MM/DD");
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
            workAtr: KnockoutObservable<any> = ko.observable(null);
            afternoonCls: KnockoutObservable<any> = ko.observable(null);
            morningCls: KnockoutObservable<any> = ko.observable(null);
            constructor(wkType?) {
                if (wkType) {
                    this.workAtr(wkType.workAtr);
                    this.afternoonCls(wkType.afternoonCls);
                    this.morningCls(wkType.morningCls);
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
            appReasonText: string;
            applicationReason: string;
            prePostAtr: number;
            enteredPersonSID: string;
            appVersion: number;
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