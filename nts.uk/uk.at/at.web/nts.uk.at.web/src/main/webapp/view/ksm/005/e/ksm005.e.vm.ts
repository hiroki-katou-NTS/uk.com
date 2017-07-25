module nts.uk.at.view.ksm005.e {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import UserInfoDto = service.model.UserInfoDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import MonthlyPatternSettingBatch = service.model.MonthlyPatternSettingBatch;
    import BusinessDayClassification = service.model.BusinessDayClassification;
    import WeeklyWorkSettingDto = service.model.WeeklyWorkSettingDto;
    import KeyMonthlyPatternSettingBatch = service.model.KeyMonthlyPatternSettingBatch;
    export module viewmodel {

        export class ScreenModel {
            startYearMonth: KnockoutObservable<number>;
            endYearMonth: KnockoutObservable<number>;
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
            }


            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllWorkType().done(function(dataWorkType) {
                    service.findAllWorkTime().done(function(dataWorkTime) {
                       self.getMonthlyPatternSettingBatch(BusinessDayClassification.WorkDays).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoWorkDays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoWorkDays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                                self.monthlyPatternSettingBatchWorkDays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.StatutoryHolidays).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoStatutoryHolidays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                                self.monthlyPatternSettingBatchStatutoryHolidays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.NoneStatutoryHolidays).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoNoneStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoNoneStatutoryHolidays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                                self.monthlyPatternSettingBatchNoneStatutoryHolidays(monthlyBatch);
                            }
                        });
                        self.getMonthlyPatternSettingBatch(BusinessDayClassification.PublicHolidays).done(function(monthlyBatch) {
                            if (monthlyBatch != undefined && monthlyBatch != null) {
                                self.worktypeInfoPublicHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                                self.worktimeInfoPublicHolidays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
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
            public findNameWorkTimeCode(siftCode: string, data: WorkTimeDto[]) {
                var workTimeName: string = '';
                for (var worktime: WorkTypeDto of data) {
                    if (siftCode == worktime.code) {
                        workTimeName = worktime.name;
                    }
                }
                return workTimeName;
            }

            /**
             * function check setting monthly pattern setting batch
             */
            public checkMonthlyPatternSettingBatchVal(val: MonthlyPatternSettingBatch){
                if (val.workTypeCode != undefined && val.workTypeCode != null && val.workTypeCode.length > 0
                    && val.siftCode != undefined && val.siftCode != null && val.siftCode.length > 0) {
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
                var self = this;
                // check error
                if (self.checkMonthlyPatternSettingBatch()) {
                    //return;
                }
                self.monthlyPatternSettingBatchWorkDays().workTypeCode = '001';
                self.monthlyPatternSettingBatchWorkDays().siftCode = 'AAA';
                self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode = '002';
                self.monthlyPatternSettingBatchStatutoryHolidays().siftCode = 'AAA';
                self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode = '002';
                self.monthlyPatternSettingBatchNoneStatutoryHolidays().siftCode = 'AAA';
                self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = '002';
                self.monthlyPatternSettingBatchPublicHolidays().siftCode = 'AAA';
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.WorkDays, self.monthlyPatternSettingBatchWorkDays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.StatutoryHolidays, self.monthlyPatternSettingBatchStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.NoneStatutoryHolidays, self.monthlyPatternSettingBatchNoneStatutoryHolidays());
                self.saveMonthlyPatternSettingBatchService(BusinessDayClassification.PublicHolidays, self.monthlyPatternSettingBatchPublicHolidays());
                service.checkWeeklyWorkSetting(new Date()).done(function(data) {
                    console.log(data);
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
                    key = {companyId: userinfo.companyId, employeeId: userinfo.employeeId, businessDayClassification: businessDayClassification};
                    service.saveMonthlyPatternSettingBatch(key,data);
                });
            }
        }

    }
}