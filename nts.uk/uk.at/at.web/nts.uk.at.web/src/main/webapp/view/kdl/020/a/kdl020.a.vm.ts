module nts.uk.at.view.kdl020.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;


    export class ViewModel {

        value: KnockoutObservable<string> = ko.observable('');

        total: KnockoutObservable<string> = ko.observable('');

        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;
        baseDate = ko.observable(new Date());
        reNumAnnLeave: KnockoutObservable<ReNumAnnLeaReferenceDate> = ko.observable(new ReNumAnnLeaReferenceDate());
        displayAnnualLeaveGrant: KnockoutObservable<DisplayAnnualLeaveGrant> = ko.observable(new DisplayAnnualLeaveGrant());
        constructor() {
            let self = this;
            self.selectedCode = ko.observable('');
            self.selectedName = ko.observable('');
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                { code: '1', isAlreadySetting: true },
                { code: '2', isAlreadySetting: true }
            ]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(false);
            self.isShowWorkPlaceName = ko.observable(false);
            self.isShowSelectAllButton = ko.observable(false);
            this.employeeList = ko.observableArray<any>([
            ]);
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton()
            };
            self.selectedCode.subscribe((newCode) => {
                let self = this;
                if (newCode) {
                    let selectedID = _.find(self.employeeList(), ['code', newCode]).id,
                        changeIDParam = {
                            employeeId: selectedID,
                            baseDate: self.baseDate()
                        };
                    self.selectedName(_.find(self.employeeList(), ['code', newCode]).name);
                    block.invisible();
                    service.changeID(changeIDParam).done((data) => {
                        self.changeData(data);
                    }).fail((error) => {
                        dialog({ messageId: error.messageId });
                    }).always(() => {
                        block.clear();
                    });;

                }

            });

            $("#holiday-info_table").ntsFixedTable({ height: 120, width: 600 });
            $("#holiday-use_table").ntsFixedTable({ height: 240, width: 600 });
        }
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            let data: any = getShared('KDL020A_PARAM');


            let startParam = {
                selectMode: self.isMultiSelect() ? 1 : 0,
                baseDate: self.baseDate(),
                employeeIds: data
            }
            block.invisible();
            service.startPage(startParam).done((data: IAnnualHoliday) => {
                if (data) {
                    let mappedList =
                        _.map(data.employees, item => {
                            return { id: item.sid, code: item.scd, name: item.bussinessName };
                        });
                    self.employeeList(mappedList);
                    self.selectedCode(mappedList[0].code);
                    self.changeData(data);
                }
            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                $('#component-items-list').ntsListComponent(self.listComponentOption);
                block.clear();
                dfd.resolve();
            });

            return dfd.promise();

        }

        genGrantDaysText(grantDays) {

            if (grantDays() == null) {
                return '';
            }
            return grantDays() + "&nbsp;" + text('KDL020_14');
        }

        genRemainDays(remainDays, remainMinutes) {
            if (remainDays() == null) {
                return '';
            }

            return remainDays() + "&nbsp;" + text('KDL020_14') + "&nbsp;&nbsp;" + formatById('Clock_Short_HM', remainMinutes());
        }

        genDaysUsedNo(daysUsedNo, usedMinutes) {
            if (daysUsedNo() == null) {
                return '';
            }

            return daysUsedNo() + "&nbsp;" + text('KDL020_14') + "&nbsp;&nbsp;" + formatById('Clock_Short_HM', usedMinutes());
        }
        genGrantNumber(grantNumber) {
            if (grantNumber() == null) {
                return '';
            }

            return grantNumber() + "&nbsp;" + text('KDL020_14');
        }
        genUsedNo(daysUsedNo, usedMinutes) {

            if (daysUsedNo() != null) {
                return daysUsedNo() + ' ' + text('KDL020_14')
            }
            if (usedMinutes() != null) {
                return formatById("Clock_Short_HM", usedMinutes());
            }

            return '';
        }
        genScheduleRecordText(scheduleRecordAtr) {
            if (scheduleRecordAtr() == null) {
                return '';
            }
            if (scheduleRecordAtr() == 0) {
                return '未反映状態'
            }

            if (scheduleRecordAtr() == 1) {
                return '実績'
            }
            if (scheduleRecordAtr() == 2) {
                return 'スケジュール'
            }

            return '';
        }
        changeData(data) {
            let self = this;
            self.reNumAnnLeave(new ReNumAnnLeaReferenceDate(data.reNumAnnLeave));
            self.displayAnnualLeaveGrant(new DisplayAnnualLeaveGrant(data.annualLeaveGrant[0]));
            self.setTotal();
        }

        setTotal() {
            let self = this,
                grants = self.reNumAnnLeave().annualLeaveGrants(),
                totalDays = 0,
                totalTimes = 0;
            _.forEach(grants, function(item) {
                totalDays += item.remainDays;
                totalTimes += item.remainMinutes;
            });

            self.total(totalDays + "&nbsp;" + text('KDL020_14') + "&nbsp;&nbsp;" + formatById('Clock_Short_HM', totalTimes));

        }

        close() {
            nts.uk.ui.windows.close();
        }    }

    export class DisplayAnnualLeaveGrant {
        /** 付与年月日 */
        grantDate: KnockoutObservable<String> = ko.observable("");
        /** 付与日数 */
        grantDays: number = ko.observable(null);
        /** 回数 */
        times: number = ko.observable(null);
        /** 時間年休上限日数 */
        timeAnnualLeaveMaxDays: number = ko.observable(null);
        /** 時間年休上限時間 */
        timeAnnualLeaveMaxTime: number = ko.observable(null);
        /** 半日年休上限回数 */
        halfDayAnnualLeaveMaxTimes: number = ko.observable(null);
        constructor(data?) {
            if (data) {
                this.grantDate(data.grantDate);
                this.grantDays(data.grantDays);
                this.times(data.times);
                this.timeAnnualLeaveMaxDays(data.timeAnnualLeaveMaxDays);
                this.timeAnnualLeaveMaxTime(data.timeAnnualLeaveMaxTime);
                this.halfDayAnnualLeaveMaxTimes(data.halfDayAnnualLeaveMaxTimes);

            }
        }

    }

    export class ReNumAnnLeaReferenceDate {

        annualLeaveRemainNumber: KnockoutObservable<AnnualLeaveRemainingNumber> = ko.observable(new AnnualLeaveRemainingNumber());

        annualLeaveGrants: KnockoutObservableArray<AnnualLeaveGrant> = ko.observableArray([]);

        annualLeaveManageInfors: KnockoutObservableArray<AnnualLeaveManageInfor> = ko.observableArray([]);

        constructor(data?) {
            if (data) {
                this.annualLeaveRemainNumber(new AnnualLeaveRemainingNumber(data.annualLeaveRemainNumberExport));
                this.annualLeaveGrants(_.map(data.annualLeaveGrantExports, x => {
                    return new AnnualLeaveGrant(x);
                }));
                this.annualLeaveManageInfors(_.map(data.annualLeaveManageInforExports, x => {
                    return new AnnualLeaveManageInfor(x);
                }));
            }
        }
    }
    export class AnnualLeaveRemainingNumber {
        annualLeaveGrantPreDay: KnockoutObservable<number> = ko.observable(null);
        annualLeaveGrantPreTime: KnockoutObservable<number> = ko.observable(null);
        numberOfRemainGrantPre: KnockoutObservable<number> = ko.observable(null);
        timeAnnualLeaveWithMinusGrantPre: KnockoutObservable<number> = ko.observable(null);
        annualLeaveGrantPostDay: KnockoutObservable<number> = ko.observable(null);
        annualLeaveGrantPostTime: KnockoutObservable<number> = ko.observable(null);
        numberOfRemainGrantPost: KnockoutObservable<number> = ko.observable(null);
        timeAnnualLeaveWithMinusGrantPost: KnockoutObservable<number> = ko.observable(null);
        attendanceRate: KnockoutObservable<number> = ko.observable(null);
        workingDays: KnockoutObservable<number> = ko.observable(null);
        constructor(data?) {
            if (data) {
                this.annualLeaveGrantPreDay(data.annualLeaveGrantPreDay);
                this.annualLeaveGrantPreDay(data.annualLeaveGrantPreDay);
                this.annualLeaveGrantPreTime(data.annualLeaveGrantPreTime);
                this.numberOfRemainGrantPre(data.numberOfRemainGrantPre);
                this.timeAnnualLeaveWithMinusGrantPre(data.timeAnnualLeaveWithMinusGrantPre);
                this.annualLeaveGrantPostDay(data.annualLeaveGrantPostDay);
                this.annualLeaveGrantPostTime(data.annualLeaveGrantPostTime);
                this.numberOfRemainGrantPost(data.numberOfRemainGrantPost);
                this.timeAnnualLeaveWithMinusGrantPost(data.timeAnnualLeaveWithMinusGrantPost);
                this.attendanceRate(data.attendanceRate);
                this.workingDays(data.workingDays);

            }
        }
    }

    export class AnnualLeaveGrant {
        grantDate: KnockoutObservable<String> = ko.observable("");
        grantNumber: KnockoutObservable<number> = ko.observable(null);
        daysUsedNo: KnockoutObservable<number> = ko.observable(null);
        usedMinutes: KnockoutObservable<number> = ko.observable(null);
        remainDays: KnockoutObservable<number> = ko.observable(null);
        remainMinutes: KnockoutObservable<number> = ko.observable(null);
        deadline: KnockoutObservable<String> = ko.observable("");
        constructor(data?) {
            if (data) {
                this.grantDate(data.grantDate);
                this.grantNumber(data.grantNumber);
                this.daysUsedNo(data.daysUsedNo);
                this.usedMinutes(data.usedMinutes);
                this.remainDays(data.remainDays);
                this.remainMinutes(data.remainMinutes);
                this.deadline(data.deadline);
            }

        }
    }
    export class AnnualLeaveManageInfor {
        ymd: KnockoutObservable<Date> = ko.observable(moment().toDate());
        daysUsedNo: KnockoutObservable<number> = ko.observable(null);
        usedMinutes: KnockoutObservable<number> = ko.observable(null);
        scheduleRecordAtr: KnockoutObservable<number> = ko.observable(null);
        constructor(data?) {
            if (data) {
                this.ymd(data.ymd);
                this.daysUsedNo(data.daysUsedNo);
                this.usedMinutes(data.usedMinutes);
                this.scheduleRecordAtr(data.scheduleRecordAtr);
            }
        }
    }

    export interface IAnnualHoliday {
        employees: Array<any>;
        annualLeaveGrant: Array<any>;
        attendNextHoliday: any;
        reNumAnnLeave: IReNumAnnLeaReferenceDateImport;
    }



    export interface IReNumAnnLeaReferenceDateImport {

        annualLeaveRemainNumberExport: IAnnualLeaveRemainingNumberImport;

        annualLeaveGrantExports: Array<IAnnualLeaveGrantImport>;

        annualLeaveManageInforExports: Array<IAnnualLeaveManageInforImport>;
    }

    export interface IAnnualLeaveRemainingNumberImport {
        /* 年休残数（付与前）日数*/
        annualLeaveGrantPreDay: number;
        /*年休残数（付与前）時間*/
        annualLeaveGrantPreTime: number;
        /* 半休残数（付与前）回数 */
        numberOfRemainGrantPre: number;
        /* 時間年休上限（付与前）*/
        timeAnnualLeaveWithMinusGrantPre: number;
        /* 年休残数（付与後）日数*/
        annualLeaveGrantPostDay: number;
        /* 年休残数（付与後）時間*/
        annualLeaveGrantPostTime: number;
        /* 半休残数（付与後）回数 */
        numberOfRemainGrantPost: number;
        /* 時間年休上限（付与後））*/
        timeAnnualLeaveWithMinusGrantPost: number;
        /* 出勤率*/
        attendanceRate: number;
        /*労働日数*/
        workingDays: number;
    }

    export interface IAnnualLeaveGrantImport {
        /* 付与日*/
        grantDate: Date;
        /* 付与数*/
        grantNumber: number;
        /* 使用日数*/
        daysUsedNo: number;
        /* 使用時間*/
        usedMinutes: number;
        /* 残日数*/
        remainDays: number;
        /* 残時間*/
        remainMinutes: number;
        /* 期限 */
        deadline: Date;

    }

    export interface IAnnualLeaveManageInforImport {
        /* 年月日*/
        ymd: Date;
        /* 使用日数*/
        daysUsedNo: number;
        /* 使用時間*/
        usedMinutes: number;
        /* 予定実績区分*/
        scheduleRecordAtr: number;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
}