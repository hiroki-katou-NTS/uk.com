module nts.uk.at.view.ksm005.b {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import UserInfoDto = service.model.UserInfoDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import MonthlyPatternSettingBatch = service.model.MonthlyPatternSettingBatch;
    import BusinessDayClassification = service.model.BusinessDayClassification;
    import WeeklyWorkSettingDto = service.model.WeeklyWorkSettingDto;
    import KeyMonthlyPatternSettingBatch = service.model.KeyMonthlyPatternSettingBatch;
    import MonthlyPatternSettingBatchDto = service.model.MonthlyPatternSettingBatchDto;
    import getText = nts.uk.resource.getText;
    
    export module viewmodel {

        export class ScreenModel {
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
            worktimeInfoStatutoryHolidays: KnockoutObservable<string>;
            worktypeInfoNoneStatutoryHolidays: KnockoutObservable<string>;
            worktimeInfoNoneStatutoryHolidays: KnockoutObservable<string>;
            worktypeInfoPublicHolidays: KnockoutObservable<string>;
            worktimeInfoPublicHolidays: KnockoutObservable<string>;


            constructor() {
                var self = this;
                self.startYearMonth = ko.observable(nts.uk.ui.windows.getShared("yearmonth"));
                self.endYearMonth = ko.observable(nts.uk.ui.windows.getShared("yearmonth"));
                self.overwirte = ko.observable(true);
                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');
                self.worktypeInfoStatutoryHolidays = ko.observable('');
                self.worktimeInfoStatutoryHolidays = ko.observable('');
                self.worktypeInfoNoneStatutoryHolidays = ko.observable('');
                self.worktimeInfoNoneStatutoryHolidays = ko.observable('');
                self.worktypeInfoPublicHolidays = ko.observable('');
                self.worktimeInfoPublicHolidays = ko.observable('');
                self.monthlyPatternSettingBatchWorkDays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchNoneStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchPublicHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternCode = nts.uk.ui.windows.getShared("monthlyPatternCode");
                self.monthlyPatternName = nts.uk.ui.windows.getShared("monthlyPatternName");
            }


            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllWorkType().done(function(dataWorkType) {
                    service.findAllWorkTime().done(function(dataWorkTime) {
                       self.getMonthlyPatternSettingBatch(BusinessDayClassification.WORK_DAYS).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoWorkDays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoWorkDays(monthlyBatch.workingCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.workingCode, dataWorkTime));
                                self.monthlyPatternSettingBatchWorkDays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.STATUTORY_HOLIDAYS).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoStatutoryHolidays(monthlyBatch.workingCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.workingCode, dataWorkTime));
                                self.monthlyPatternSettingBatchStatutoryHolidays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.NONE_STATUTORY_HOLIDAYS).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoNoneStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoNoneStatutoryHolidays(monthlyBatch.workingCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.workingCode, dataWorkTime));
                                self.monthlyPatternSettingBatchNoneStatutoryHolidays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.PUBLIC_HOLIDAYS).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoPublicHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoPublicHolidays(monthlyBatch.workingCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.workingCode, dataWorkTime));
                                self.monthlyPatternSettingBatchPublicHolidays(monthlyBatch);
                            }
                        });

                    });
                });


                dfd.resolve(self);
                return dfd.promise();
            }
            /**
             * find by work type code of data 
             */
            public findNameByWorktypeCode(workTypeCode: string, data: WorkTypeDto[]) {
                var workTypeName: string = getText('KSM005_43');
                for (var worktype of data) {
                    if (workTypeCode == worktype.workTypeCode) {
                        workTypeName = worktype.name;
                    }
                }
                return workTypeName;
            }
            /**
             * find by work time code of data
             */
            public findNameWorkTimeCode(worktimeCode: string, data: WorkTimeDto[]) {
                var worktype = _.find(data, function(item) {
                    return item.code == worktimeCode;
                });
                if (!worktype) {
                    return getText('KSM005_43');
                }
                return worktype.name;
            }

            /**
             * function check setting monthly pattern setting batch
             */
            public checkMonthlyPatternSettingBatchVal(val: MonthlyPatternSettingBatch){
                if (val.workTypeCode) {
                    return false;
                }    
                return true;
            }

            /**
             * function get between month start month to end month
             */
            private getbetweenMonth(endMonth: number, startMonth: number): number {
                var endYear: number = Math.floor(endMonth/100);
                var startYear: number = Math.floor(startMonth/100);
                var numberMonthStart: number = startYear * 12 + startMonth % 100;
                var numberMonthEnd: number = endYear * 12 + endMonth % 100;
                return numberMonthEnd - numberMonthStart + 1;
            }
            /**
             * function check error by click button
             */
            public checkMonthlyPatternSettingBatch(): boolean {
                var self = this;
                if ($('.yearmonthInput').ntsError("hasError") == true) {
                   return true;
                }
                //check start month and end month
                if (self.getbetweenMonth(self.endYearMonth(),self.startYearMonth()) > 12) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_149" }).then(function() {
                    });
                    return true;
                }
                //check start month and end month
                if (self.startYearMonth() > self.endYearMonth()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_150" }).then(function() {
                    });
                    return true;
                }
                
                if(self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchWorkDays())
                || self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchStatutoryHolidays())
                || self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchNoneStatutoryHolidays())
                ||    self.checkMonthlyPatternSettingBatchVal(self.monthlyPatternSettingBatchPublicHolidays())){
                         nts.uk.ui.dialog.alertError({ messageId: "Msg_151" }).then(function() {
                    });
                    return true;
                }
                return false;
            }
            /**
             * save monthly pattern setting batch when click button
             */
            public saveMonthlyPatternSettingBatch(): void {
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
                service.batchWorkMonthlySetting(self.collectData()).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        nts.uk.ui.windows.setShared("isCancelSave", false);
                        nts.uk.ui.windows.close();
                    });

                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
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
                key = { companyId: userinfo.companyId, employeeId: userinfo.employeeId, businessDayClassification: businessDayClassification };
                service.findMonthlyPatternSettingBatch(key).done(function(dataRes) {
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
                var key: KeyMonthlyPatternSettingBatch = {companyId: userinfo.companyId, employeeId: userinfo.employeeId, businessDayClassification: businessDayClassification};
                service.saveMonthlyPatternSettingBatch(key ,data);
            }
            
            /**
             * collect data to call service batch monthly pattern setting
             */
            public collectData(): MonthlyPatternSettingBatchDto{
                var dto: MonthlyPatternSettingBatchDto;
                var self = this;
                dto = {
                    settingWorkDays: self.monthlyPatternSettingBatchWorkDays(),
                    settingStatutoryHolidays: self.monthlyPatternSettingBatchStatutoryHolidays(),
                    settingNoneStatutoryHolidays: self.monthlyPatternSettingBatchNoneStatutoryHolidays(),
                    settingPublicHolidays: self.monthlyPatternSettingBatchPublicHolidays(),
                    overwrite: self.overwirte(),
                    startYearMonth: self.startYearMonth(),
                    endYearMonth: self.endYearMonth(),
                    monthlyPatternCode: self.monthlyPatternCode,
                    monthlyPatternName: self.monthlyPatternName
                };    
                return dto;
            }
            /**
             * function by click button cancel save monthly pattern setting batch
             */
            public cancelSaveMonthlyPatternSetting(): void{
                nts.uk.ui.windows.setShared("isCancelSave", true);
                nts.uk.ui.windows.close();    
            }
            /**
             * get user login
             */
            public getUserLogin(): UserInfoDto {
                var userinfo: UserInfoDto = { companyId: '000000000000-0001', employeeId: '000426a2-181b-4c7f-abc8-6fff9f4f983a' };
                return userinfo;

            }
            
            
            /**
             * open dialog KDL003 by Work Days
             */
            public openDialogWorkDays(): void {
                var self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectedWorkTypeCode: self.monthlyPatternSettingBatchWorkDays().workTypeCode,
                    selectedWorkTimeCode: self.monthlyPatternSettingBatchWorkDays().workingCode
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.monthlyPatternSettingBatchWorkDays().workTypeCode = childData.selectedWorkTypeCode;
                        if (childData.selectedWorkTypeCode) {
                            self.worktypeInfoWorkDays(childData.selectedWorkTypeCode + ' ' + childData.selectedWorkTypeName);
                        }
                        self.monthlyPatternSettingBatchWorkDays().workingCode = childData.selectedWorkTimeCode;

                        if (childData.selectedWorkTimeCode) {
                            self.worktimeInfoWorkDays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        } else {
                            self.worktimeInfoWorkDays('');
                        }
                    }  
                });
            }
            
             /**
             * open dialog KDL003 statutory holidays
             */
            public openDialogStatutoryHolidays(): void {
                var self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectedWorkTypeCode: self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode,
                    selectedWorkTimeCode: self.monthlyPatternSettingBatchStatutoryHolidays().workingCode
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if(childData){
                        self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode = childData.selectedWorkTypeCode;
                        if (childData.selectedWorkTypeCode) {
                            self.worktypeInfoStatutoryHolidays(childData.selectedWorkTypeCode + ' ' + childData.selectedWorkTypeName);
                        }
                        self.monthlyPatternSettingBatchStatutoryHolidays().workingCode = childData.selectedWorkTimeCode;
                        if (childData.selectedWorkTimeCode) {
                            self.worktimeInfoStatutoryHolidays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        } else {
                            self.worktimeInfoStatutoryHolidays('');
                        }
                    }
                });
            }
             /**
             * open dialog KDL003 none statutory holidays
             */
            public openDialogNoneStatutoryHolidays(): void {
                var self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectedWorkTypeCode: self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode,
                    selectedWorkTimeCode: self.monthlyPatternSettingBatchNoneStatutoryHolidays().workingCode
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode = childData.selectedWorkTypeCode;

                        if (childData.selectedWorkTypeCode) {
                            self.worktypeInfoNoneStatutoryHolidays(childData.selectedWorkTypeCode + ' ' + childData.selectedWorkTypeName);
                        }

                        self.monthlyPatternSettingBatchNoneStatutoryHolidays().workingCode = childData.selectedWorkTimeCode;

                        if (childData.selectedWorkTimeCode) {
                            self.worktimeInfoNoneStatutoryHolidays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        } else {
                            self.worktimeInfoNoneStatutoryHolidays('');
                        }
                    }
                });
            }
             /**
             * open dialog KDL003 public holiday
             */
            public openDialogPublicHolidays(): void {
                var self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectedWorkTypeCode: self.monthlyPatternSettingBatchPublicHolidays().workTypeCode,
                    selectedWorkTimeCode: self.monthlyPatternSettingBatchPublicHolidays().workingCode
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = childData.selectedWorkTypeCode;

                        if (childData.selectedWorkTypeCode) {
                            self.worktypeInfoPublicHolidays(childData.selectedWorkTypeCode + ' ' + childData.selectedWorkTypeName);
                        }
                        self.monthlyPatternSettingBatchPublicHolidays().workingCode = childData.selectedWorkTimeCode;
                        if (childData.selectedWorkTimeCode) {
                            self.worktimeInfoPublicHolidays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        } else {
                            self.worktimeInfoPublicHolidays('');
                        }
                    }
                });
            }
        }
    }
}