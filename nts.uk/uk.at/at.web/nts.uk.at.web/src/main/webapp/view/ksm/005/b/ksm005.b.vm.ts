module nts.uk.at.view.ksm005.b {

    import UserInfoDto = service.model.UserInfoDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import MonthlyPatternSettingBatch = service.model.MonthlyPatternSettingBatch;
    import BusinessDayClassification = service.model.BusinessDayClassification;
    import WeeklyWorkSettingDto = service.model.WeeklyWorkSettingDto;
    import KeyMonthlyPatternSettingBatch = service.model.KeyMonthlyPatternSettingBatch;
    import MonthlyPatternSettingBatchDto = service.model.MonthlyPatternSettingBatchDto;
    import WeeklyWork = service.model.WeeklyWork;
    import getText = nts.uk.resource.getText;
    import getMessage = nts.uk.resource.getMessage;

    export module viewmodel {

        export class ScreenModel {
            dateValue: KnockoutObservable<any>;
            monthlyPatternCode: string;
            monthlyPatternName: string;
            startYearMonth: KnockoutObservable<number>;
            endYearMonth: KnockoutObservable<number>;
            overwirte: KnockoutObservable<boolean>;
            monthlyPatternSettingBatchWorkDays: KnockoutObservable<MonthlyPatternSettingBatch>;
            monthlyPatternSettingBatchStatutoryHolidays: KnockoutObservable<MonthlyPatternSettingBatch>;
            monthlyPatternSettingBatchNoneStatutoryHolidays: KnockoutObservable<MonthlyPatternSettingBatch>;
            monthlyPatternSettingBatchPublicHolidays: KnockoutObservable<MonthlyPatternSettingBatch>;
            worktypeInfoWorkDays: KnockoutObservable<string>;
            worktimeInfoWorkDays: KnockoutObservable<string>;
            worktypeInfoStatutoryHolidays: KnockoutObservable<string>;
            worktypeInfoNoneStatutoryHolidays: KnockoutObservable<string>;
            worktypeInfoPublicHolidays: KnockoutObservable<string>;

            settingForHolidays: KnockoutObservable<boolean>;
            lstSelectableCode: KnockoutObservableArray<string>;
            columnHolidayPatterns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            lstHolidaysPattern: KnockoutObservableArray<MonthlyPattern>;
            selectHolidayPattern: KnockoutObservable<string>;

            hasWorkingDays: KnockoutObservable<boolean> = ko.observable(false);
            hasNonStatutoryHolidays: KnockoutObservable<boolean> = ko.observable(false);
            hasLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);
            visibleHolidaySetting: KnockoutObservable<boolean> = ko.observable(false);
            visibleRegister: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                const self = this;
                self.dateValue = ko.observable({
                    startDate: nts.uk.ui.windows.getShared("yearmonth"),
                    endDate: nts.uk.ui.windows.getShared("yearmonth")
                });
                self.monthlyPatternCode = nts.uk.ui.windows.getShared("monthlyPatternCode");
                self.monthlyPatternName = nts.uk.ui.windows.getShared("monthlyPatternName");

                self.overwirte = ko.observable(true);
                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');
                self.worktypeInfoStatutoryHolidays = ko.observable('');
                self.worktypeInfoNoneStatutoryHolidays = ko.observable('');
                self.worktypeInfoPublicHolidays = ko.observable('');
                self.monthlyPatternSettingBatchWorkDays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchNoneStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchPublicHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.settingForHolidays = ko.observable(false);
                self.lstSelectableCode = ko.observableArray([]);

                self.columnHolidayPatterns = ko.observableArray([
                    {headerText: getText("KSM005_92"), key: 'code', width: 50},
                    {headerText: getText("KSM005_93"), key: 'name', width: 100, formatter: _.escape}
                ]);
                self.lstHolidaysPattern = ko.observableArray([]);
                self.selectHolidayPattern = ko.observable(null);
                self.settingForHolidays.subscribe(data => {
                    if (data) {
                        self.visibleHolidaySetting(true);
                    } else {
                        self.visibleHolidaySetting(false);
                        //self.worktypeInfoPublicHolidays(null);
                        //self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = null;
                    }
                    self.monthlyPatternSettingBatchPublicHolidays().isHolidayPriority = self.settingForHolidays();
                });

                // Init
                $(".popup-b72").ntsPopup({
                    trigger: ".showDialogB72",
                    position: {
                        my: "left top",
                        at: "left bottom",
                        of: ".showDialogB72"
                    },
                    showOnStart: false,
                    dismissible: true
                });
                self.getWeeklyWorkPattern();
                $("#fixed-table").ntsFixedTable({height: 190, width: 600});
            }

            /**
             * start page data
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                service.findAllWorkType().done(function (dataWorkType) {
                    service.findAllWorkTime().done(function (dataWorkTime) {
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.WORK_DAYS).done(function (monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoWorkDays(monthlyBatch.workTypeCode ? monthlyBatch.workTypeCode + '   ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType) : '');
                                self.worktimeInfoWorkDays(monthlyBatch.workingCode ? monthlyBatch.workingCode + '   ' + self.findNameWorkTimeCode(monthlyBatch.workingCode, dataWorkTime) : '');
                                self.monthlyPatternSettingBatchWorkDays(monthlyBatch);
                            }
                        });

                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.STATUTORY_HOLIDAYS).done(function (monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoStatutoryHolidays(monthlyBatch.workTypeCode ? monthlyBatch.workTypeCode + '   ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType) : '');
                                self.monthlyPatternSettingBatchStatutoryHolidays(monthlyBatch);
                            }
                        });

                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.NONE_STATUTORY_HOLIDAYS).done(function (monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoNoneStatutoryHolidays(monthlyBatch.workTypeCode ? monthlyBatch.workTypeCode + '   ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType) : '');
                                self.monthlyPatternSettingBatchNoneStatutoryHolidays(monthlyBatch);
                            }
                        });

                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.PUBLIC_HOLIDAYS).done(function (monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.settingForHolidays(monthlyBatch.isHolidayPriority)
                                self.worktypeInfoPublicHolidays(monthlyBatch.workTypeCode ? monthlyBatch.workTypeCode + '   ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType) : '');
                                self.monthlyPatternSettingBatchPublicHolidays(monthlyBatch);
                            }
                        });

                    });
                });
                nts.uk.ui.block.clear();
                dfd.resolve(self);
                return dfd.promise();
            }

            /**
             * find by work type code of data
             */
            public findNameByWorktypeCode(workTypeCode: string, data: WorkTypeDto[]) {
                let workTypeName: string = '';
                for (let worktype of data) {
                    if (workTypeCode == worktype.workTypeCode) {
                        workTypeName = worktype.name;
                    }
                }
                if(workTypeName) {
                    return workTypeName;
                } else {
                    return getText('KSM005_84');
                }
            }

            /**
             * find by work time code of data
             */
            public findNameWorkTimeCode(worktimeCode: string, data: WorkTimeDto[]) {
                var worktype = _.find(data, function (item) {
                    return item.code == worktimeCode;
                });
                if(worktype && worktype.name) {
                    return worktype.name;
                }
                return getText('KSM005_84');
            }

            /**
             * function check setting monthly pattern setting batch
             */
            public checkMonthlyPatternSettingBatchVal(val: MonthlyPatternSettingBatch) {
                if (val.workTypeCode) {
                    return false;
                }
                return true;
            }

            /**
             * function get between month start month to end month
             */
            private getbetweenMonth(endMonth: number, startMonth: number): number {
                var endYear: number = Math.floor(endMonth / 100);
                var startYear: number = Math.floor(startMonth / 100);
                var numberMonthStart: number = startYear * 12 + startMonth % 100;
                var numberMonthEnd: number = endYear * 12 + endMonth % 100;
                return numberMonthEnd - numberMonthStart + 1;
            }

            /**
             * function check error by click button
             */
            public checkMonthlyPatternSettingBatch(): boolean {
                var self = this;
       
                if (self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchWorkDays())
                    || self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchStatutoryHolidays())
                    || self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchNoneStatutoryHolidays())
                    || (self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchPublicHolidays())
                        && self.settingForHolidays())) {
                    nts.uk.ui.dialog.alertError({messageId: "Msg_151"}).then(function () {
                    });
                    return true;
                }
                return false;
            }

            /**
             * save monthly pattern setting batch when click button
             */
            public saveMonthlyPatternSettingBatch(): void {
                if(nts.uk.ui.errors.hasError()) {
                    return;
                }
                nts.uk.ui.block.invisible();
                var self = this;
                // check error
                if (self.checkMonthlyPatternSettingBatch()) {
                    nts.uk.ui.block.clear();
                    return;
                }
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.WORK_DAYS, self.monthlyPatternSettingBatchWorkDays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.STATUTORY_HOLIDAYS, self.monthlyPatternSettingBatchStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.NONE_STATUTORY_HOLIDAYS, self.monthlyPatternSettingBatchNoneStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.PUBLIC_HOLIDAYS, self.monthlyPatternSettingBatchPublicHolidays());
                service.batchWorkMonthlySetting(self.collectData()).done(function () {
                    // show message 15
                    nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function () {
                        nts.uk.ui.windows.setShared("isCancelSave", false);
                        nts.uk.ui.windows.close();
                    });

                }).fail(function (error) {
                    nts.uk.ui.dialog.info({messageId: error.messageId});
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * get monthly pattern setting batch
             */
            public getMonthlyPatternSettingBatch(businessDayClassification: BusinessDayClassification): JQueryPromise<MonthlyPatternSettingBatch> {
                var dfd = $.Deferred();
                var self = this;
                var userinfo: UserInfoDto = self.getUserLogin();
                var key: KeyMonthlyPatternSettingBatch;
                key = {
                    companyId: userinfo.companyId,
                    employeeId: userinfo.employeeId,
                    businessDayClassification: businessDayClassification
                };
                service.findMonthlyPatternSettingBatch(key).done(function (dataRes) {
                    dfd.resolve(dataRes);
                });
                return dfd.promise();
            }

            /**
             * call service save monthly pattern setting batch
             */
            public saveMonthlyPatternSettingBatchService(businessDayClassification: BusinessDayClassification, data: MonthlyPatternSettingBatch): void {
                var self = this;
                var userinfo: UserInfoDto = self.getUserLogin();
                var key: KeyMonthlyPatternSettingBatch = {
                    companyId: userinfo.companyId,
                    employeeId: userinfo.employeeId,
                    businessDayClassification: businessDayClassification
                };
                service.saveMonthlyPatternSettingBatch(key, data);
            }

            /**
             * collect data to call service batch monthly pattern setting
             */
            public collectData(): MonthlyPatternSettingBatchDto {
                var dto: MonthlyPatternSettingBatchDto;
                var self = this;
                dto = {
                    settingWorkDays: self.lstHolidaysPattern().length > 0 ? self.monthlyPatternSettingBatchWorkDays() : null,
                    settingStatutoryHolidays: self.monthlyPatternSettingBatchStatutoryHolidays(),
                    settingNoneStatutoryHolidays: self.monthlyPatternSettingBatchNoneStatutoryHolidays(),
                    settingPublicHolidays: self.settingForHolidays() ? self.monthlyPatternSettingBatchPublicHolidays() : null,
                    overwrite: self.overwirte(),
                    startYearMonth: Number(self.dateValue().startDate.toString().substring(0, 6)),
                    endYearMonth: Number(self.dateValue().endDate.toString().substring(0, 6)),
                    monthlyPatternCode: self.monthlyPatternCode,
                    monthlyPatternName: self.monthlyPatternName
                };
                return dto;
            }

            /**
             * function by click button cancel save monthly pattern setting batch
             */
            public cancelSaveMonthlyPatternSetting(): void {
                nts.uk.ui.windows.setShared("isCancelSave", true);
                nts.uk.ui.windows.close();
            }

            /**
             * get user login
             */
            public getUserLogin(): UserInfoDto {
                var userinfo: UserInfoDto = {
                    companyId: __viewContext.user.companyId,
                    employeeId: __viewContext.user.employeeId
                };
                return userinfo;
            }

            /**
             * open dialog KDL003 by Work Days
             */
            public openDialogWorkDays(): void {
                const self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectedWorkTypeCode: self.monthlyPatternSettingBatchWorkDays().workTypeCode,
                    selectedWorkTimeCode: self.monthlyPatternSettingBatchWorkDays().workingCode
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function () {
                    let childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.monthlyPatternSettingBatchWorkDays().workTypeCode = childData.selectedWorkTypeCode;
                        if (childData.selectedWorkTypeCode) {
                            if(childData.selectedWorkTypeName) {
                                self.worktypeInfoWorkDays(childData.selectedWorkTypeCode + '   ' + childData.selectedWorkTypeName);
                            } else {
                                self.worktypeInfoWorkDays(childData.selectedWorkTypeCode + '   ' + getText('KSM005_84'));
                            }

                        }
                        self.monthlyPatternSettingBatchWorkDays().workingCode = childData.selectedWorkTimeCode;

                        if (childData.selectedWorkTimeCode){
                            if(childData.selectedWorkTimeName) {
                                self.worktimeInfoWorkDays(childData.selectedWorkTimeCode + '   ' + childData.selectedWorkTimeName);
                            } else {
                                self.worktimeInfoWorkDays(childData.selectedWorkTimeCode + '   ' + getText('KSM005_84'));
                            }
                        } else {
                            self.worktimeInfoWorkDays("");
                        }

                    }
                });
            }

            /**
             * open dialog KDL003 statutory holidays
             */
            public openDialogStatutoryHolidays(): void {
                let self = this,
                    workTypeCode = self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode;
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', self.lstSelectableCode(), true);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', workTypeCode, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', {
                    title: '乖離時間の登録＞対象項目',
                    width: 700,
                    height: 520
                }).onClosed(function (): any {
                    let lstNewData = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    if (lstNewData) {
                        self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode = lstNewData[0].code;
                        if (nts.uk.util.isNullOrEmpty(lstNewData.code)) {
                            if(lstNewData[0].name) {
                                self.worktypeInfoStatutoryHolidays(lstNewData[0].code + '   ' + lstNewData[0].name);
                            } else {
                                self.worktypeInfoStatutoryHolidays(lstNewData[0].code + '   ' + getText('KSM005_84'));
                            }
                        }
                    }
                });
            }

            /**
             * open dialog KDL003 none statutory holidays
             */
            public openDialogNoneStatutoryHolidays(): void {
                let self = this,
                    workTypeCode = self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode;
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', self.lstSelectableCode(), true);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', workTypeCode, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', {
                    title: '乖離時間の登録＞対象項目',
                    width: 700,
                    height: 520
                }).onClosed(function (): any {
                    let lstNewData = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    if (lstNewData) {
                        self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode = lstNewData[0].code;
                        if (nts.uk.util.isNullOrEmpty(lstNewData.code)) {
                            if(lstNewData[0].name) {
                                self.worktypeInfoNoneStatutoryHolidays(lstNewData[0].code + '   ' + lstNewData[0].name);
                            } else {
                                self.worktypeInfoNoneStatutoryHolidays(lstNewData[0].code + '   ' + getText('KSM005_84'));
                            }

                        }
                    }
                });
            }

            /**
             * open dialog KDL003 public holiday
             */
            public openDialogPublicHolidays(): void {
                let self = this,
                    workTypeCode = self.monthlyPatternSettingBatchPublicHolidays().workTypeCode;
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', self.lstSelectableCode(), true);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', workTypeCode, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', {
                    title: '乖離時間の登録＞対象項目',
                    width: 700,
                    height: 520
                }).onClosed(function (): any {
                    let lstNewData = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    if (lstNewData) {
                        self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = lstNewData[0].code;
                        if (nts.uk.util.isNullOrEmpty(lstNewData.code)) {
                            if(lstNewData[0].name) {
                                self.worktypeInfoPublicHolidays(lstNewData[0].code + '   ' + lstNewData[0].name);
                            } else {
                                self.worktypeInfoPublicHolidays(lstNewData[0].code + '   ' + getText('KSM005_84'));
                            }

                        }
                    }
                });
            }

            public showDialogKDL002(i: number): void {
                const self = this;
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', self.lstSelectableCode(), true);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', null, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', {
                    title: '乖離時間の登録＞対象項目',
                    width: 700,
                    height: 520
                }).onClosed(function (): any {
                    let lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                });
            }

            public getWeeklyWorkPattern() {
                const self = this;
                let weeklyWork = {listWorkTypeCd: [], worktimeCode: null};

                service.getWeeklyWork(weeklyWork).done(function (data) {
                    //非稼働日（法内）
                    if (data.weeklyWorkDayPatternDtos.length > 0) {
                        data.weeklyWorkDayPatternDtos.map((item) => {
                            let day = item.dayOfWeek;
                            let des;
                            day = day.substring(0, 1);
                            if(item.typeColor == 0) {
                                des = nts.uk.resource.getText('KSM004_46');
                            } else if (item.typeColor == 1) {
                                des = nts.uk.resource.getText('KSM004_47');
                            } else {
                                des =nts.uk.resource.getText('KSM004_48')
                            }
                            let isSat = day == '土';
                            let isSun = day == '日';
                            let isHolidayOrWeekend = item.typeColor == 1 || item.typeColor == 2;
                            let dayHoliday = {
                                code: day,
                                name: des,
                                isSat,
                                isSun,
                                isHolidayOrWeekend,
                                isHalfDayWork: false
                            };
                            self.lstHolidaysPattern.push(dayHoliday);
                            if(item.typeColor == 1) {
                                self.hasLegalHoliday(true);
                            }
                            if(item.typeColor == 2) {
                                self.hasNonStatutoryHolidays(true);
                            }
                            if(item.typeColor == 0) {
                                self.hasWorkingDays(true);
                            }
                        });
                    }
                    self.lstSelectableCode(data.workTypeCode);
                });
            }

            /*
            * 法定外休日
            * */
            public openDialogNonStatutoryHolidays() {
                let self = this;
                self.showDialogKDL002(3);
            }

            /*
            * 法定休日
            * */
            public openDialogLegalHolidays() {
                let self = this;
                self.showDialogKDL002(2);
            }

            /*
            * 定休日
            * */
            public openDialogRegularHolidays() {
                let self = this;
                self.showDialogKDL002(1);
            }

        }

        interface MonthlyPattern {
            code: string,
            name: string,
            isSat: boolean,
            isSun: boolean,
            isHolidayOrWeekend: boolean,
            isHalfDayWork: boolean
        }
    }
}