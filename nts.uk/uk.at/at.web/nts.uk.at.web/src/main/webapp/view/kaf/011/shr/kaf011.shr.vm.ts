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

        export class Comment {
            deferredComment: KnockoutObservable<string> = ko.observable('');
            deferredBold: KnockoutObservable<boolean> = ko.observable(false);
            pickUpComment: KnockoutObservable<string> = ko.observable('');
            pickUpBold: KnockoutObservable<boolean> = ko.observable(false);
            constructor(commentSetting) {
                if (commentSetting) {
                    this.deferredComment(commentSetting.deferredComment);
                    this.deferredBold(commentSetting.deferredBold);
                    this.pickUpComment(commentSetting.deferredComment);
                    this.pickUpBold(commentSetting.deferredBold);
                }
            }
        }
        export class WorkItems {
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
                        block.grayout();
                        let changeWkTypeParam = {
                            wkTypeCD: newWkType,
                            wkTimeCD: self.wkTimeCD()

                        };
                        service.changeWkType(changeWkTypeParam).done((data: IChangeWorkType) => {
                            if (data) {
                                if (data.timezoneUseDtos) {
                                    self.wkTime1().startTime(data.timezoneUseDtos[0].start);
                                    self.wkTime1().endTime(data.timezoneUseDtos[0].end);
                                }
                            }
                        }).always(() => {
                            block.clear();
                        });
                    }
                });
            }

            parseText(date) {
                return nts.uk.time.formatDate(new Date(date()), "yyyy/MM/dd");
            }

            parseTime(value) {
                return nts.uk.time.parseTime(value, true).format()
            }
            genWorkingText(childData: common.IWorkTime) {
                let self = this,
                    result = childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName;
                if (childData.first) {
                    result += ' ' + self.parseTime(childData.first.start) + '~' + self.parseTime(childData.first.end);
                    if (childData.second) {

                    }
                }
                return result;

            }

            openKDL003() {
                let self = this,
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
                            self.wkText(self.genWorkingText(childData));
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