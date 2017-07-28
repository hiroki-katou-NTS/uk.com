module nts.uk.at.view.ksm005.e {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import UserInfoDto = service.model.UserInfoDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import MonthlyPatternSettingBatch = service.model.MonthlyPatternSettingBatch;
    import BusinessDayClassification = service.model.BusinessDayClassification;
    import WeeklyWorkSettingDto = service.model.WeeklyWorkSettingDto;
    import KeyMonthlyPatternSettingBatch = service.model.KeyMonthlyPatternSettingBatch;
    import MonthlyPatternSettingBatchDto = service.model.MonthlyPatternSettingBatchDto;
    
    export module viewmodel {

        export class ScreenModel {
            monthlyPatternCode: string;
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
                self.startYearMonth = ko.observable(201701);
                self.endYearMonth = ko.observable(201712);
                self.overwirte = ko.observable(true);
                self.worktypeInfoWorkDays = ko.observable('001 A');
                self.worktimeInfoWorkDays = ko.observable('001 A');
                self.worktypeInfoStatutoryHolidays = ko.observable('001 A');
                self.worktimeInfoStatutoryHolidays = ko.observable('001 A');
                self.worktypeInfoNoneStatutoryHolidays = ko.observable('001 A');
                self.worktimeInfoNoneStatutoryHolidays = ko.observable('001 A');
                self.worktypeInfoPublicHolidays = ko.observable('001 A');
                self.worktimeInfoPublicHolidays = ko.observable('001 A');
                self.monthlyPatternSettingBatchWorkDays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchNoneStatutoryHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternSettingBatchPublicHolidays = ko.observable(new MonthlyPatternSettingBatch());
                self.monthlyPatternCode = nts.uk.ui.windows.getShared("monthlyPatternCode");
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
                var workTypeName: string = '';
                for (var worktype: WorkTypeDto of data) {
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
                    return '';
                }
                return worktype.name;
            }

            /**
             * function check setting monthly pattern setting batch
             */
            public checkMonthlyPatternSettingBatchVal(val: MonthlyPatternSettingBatch){
                if (val.workTypeCode && val.workingCode) {
                    return false;
                }    
                return true;
            }

            /**
             * function check error by click button
             */
            public checkMonthlyPatternSettingBatch(): boolean {
                var self = this;
                //check start month and end month
                if (self.endYearMonth() - self.startYearMonth() > 12) {
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
                    return;
                }
                self.monthlyPatternSettingBatchWorkDays().workTypeCode = '001';
                self.monthlyPatternSettingBatchWorkDays().workingCode = '002';
                self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode = '001';
                self.monthlyPatternSettingBatchStatutoryHolidays().workingCode = '001';
                self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode = '001';
                self.monthlyPatternSettingBatchNoneStatutoryHolidays().workingCode = '001';
                self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = '001';
                self.monthlyPatternSettingBatchPublicHolidays().workingCode = '001';
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.WORK_DAYS, self.monthlyPatternSettingBatchWorkDays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.STATUTORY_HOLIDAYS, self.monthlyPatternSettingBatchStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.NONE_STATUTORY_HOLIDAYS, self.monthlyPatternSettingBatchNoneStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.PUBLIC_HOLIDAYS, self.monthlyPatternSettingBatchPublicHolidays());
                service.batchWorkMonthlySetting(self.collectData()).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        nts.uk.ui.windows.close();
                    });

                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error).then(function() {
                        nts.uk.ui.windows.close();
                    });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * get monthly pattern setting batch
             */
            public getMonthlyPatternSettingBatch(businessDayClassification: BusinessDayClassification): JQueryPromise<MonthlyPatternSettingBatch> {
                var dfd = $.Deferred();
                service.getUserInfo().done(function(userinfo: UserInfoDto) {
                    var key: KeyMonthlyPatternSettingBatch;
                    key = {companyId: userinfo.companyId, employeeId: userinfo.employeeId, businessDayClassification: businessDayClassification};
                    service.findMonthlyPatternSettingBatch(key).done(function(dataRes){
                        dfd.resolve(dataRes);
                    });
                });
                return dfd.promise();
            }
            
            /**
             * call service save monthly pattern setting batch
             */
            public saveMonthlyPatternSettingBatchService(businessDayClassification: BusinessDayClassification, data: MonthlyPatternSettingBatch): void {
                service.getUserInfo().done(function(userinfo: UserInfoDto) {
                    var key: KeyMonthlyPatternSettingBatch = {companyId: userinfo.companyId, employeeId: userinfo.employeeId, businessDayClassification: businessDayClassification};
                    service.saveMonthlyPatternSettingBatch(key ,data);
                });
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
                    monthlyPatternCode: self.monthlyPatternCode
                };    
                return dto;
            }
            /**
             * function by click button cancel save monthly pattern setting batch
             */
            public cancelSaveMonthlyPatternSetting(): void{
                nts.uk.ui.windows.close();    
            }
        }

    }
}