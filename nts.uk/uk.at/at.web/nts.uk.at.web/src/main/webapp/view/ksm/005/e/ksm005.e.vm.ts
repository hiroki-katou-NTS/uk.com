module nts.uk.at.view.ksm005.e {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import MonthlyPatternSettingBatch = service.model.MonthlyPatternSettingBatch;
    import BusinessDayClassification = service.model.BusinessDayClassification;
    import WeeklyWorkSettingDto = service.model.WeeklyWorkSettingDto;
    export module viewmodel {

        export class ScreenModel {
            yearMonth: KnockoutObservable<number>;                  
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
               self.yearMonth = ko.observable(201705);
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
                       service.findMonthlyPatternSettingBatch(BusinessDayClassification.WorkDays).done(function(monthlyBatch) {
                           if (monthlyBatch != undefined && monthlyBatch != null) {
                               self.worktypeInfoWorkDays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                               self.worktimeInfoWorkDays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                               self.monthlyPatternSettingBatchWorkDays(monthlyBatch);
                           }
                       });
                       service.findMonthlyPatternSettingBatch(BusinessDayClassification.StatutoryHolidays).done(function(monthlyBatch) {
                           if (monthlyBatch != undefined && monthlyBatch != null) {
                               self.worktypeInfoStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                               self.worktimeInfoStatutoryHolidays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                               self.monthlyPatternSettingBatchStatutoryHolidays(monthlyBatch);
                           }
                       });
                       service.findMonthlyPatternSettingBatch(BusinessDayClassification.NoneStatutoryHolidays).done(function(monthlyBatch) {
                           if (monthlyBatch != undefined && monthlyBatch != null) {
                               self.worktypeInfoNoneStatutoryHolidays(monthlyBatch.workTypeCode + ' ' + self.findNameByWorktypeCode(monthlyBatch.workTypeCode, dataWorkType));
                               self.worktimeInfoNoneStatutoryHolidays(monthlyBatch.siftCode + ' ' + self.findNameWorkTimeCode(monthlyBatch.siftCode, dataWorkTime));
                               self.monthlyPatternSettingBatchNoneStatutoryHolidays(monthlyBatch);
                           }
                       });
                       service.findMonthlyPatternSettingBatch(BusinessDayClassification.PublicHolidays).done(function(monthlyBatch) {
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
            public findNameByWorktypeCode(workTypeCode: string, data: WorkTypeDto[]){
                var workTypeName: string = '';
                for(var worktype: WorkTypeDto of data){
                    if(workTypeCode == worktype.workTypeCode){
                        workTypeName = worktype.name;
                    }
                }
                return workTypeName;    
            }
            /**
             * find by work time code of data
             */
            public findNameWorkTimeCode(siftCode: string, data: WorkTimeDto[]){
                var workTimeName: string = '';
                for (var worktime: WorkTypeDto of data) {
                    if (siftCode == worktime.code) {
                        workTimeName = worktime.name;
                    }
                }
                return workTimeName;      
            }
            /**
             * save monthly pattern setting batch when click button
             */
            
           public saveMonthlyPatternSettingBatch(): void {
               var self = this;
               self.monthlyPatternSettingBatchWorkDays().workTypeCode = '002';
               self.monthlyPatternSettingBatchWorkDays().siftCode = 'AAA';
               self.monthlyPatternSettingBatchStatutoryHolidays().workTypeCode = '002';
               self.monthlyPatternSettingBatchStatutoryHolidays().siftCode = 'AAA';
               self.monthlyPatternSettingBatchNoneStatutoryHolidays().workTypeCode = '002';
               self.monthlyPatternSettingBatchNoneStatutoryHolidays().siftCode = 'AAA';
               self.monthlyPatternSettingBatchPublicHolidays().workTypeCode = '002';
               self.monthlyPatternSettingBatchPublicHolidays().siftCode = 'AAA';
               service.saveMonthlyPatternSettingBatch(BusinessDayClassification.WorkDays,self.monthlyPatternSettingBatchWorkDays());
               service.saveMonthlyPatternSettingBatch(BusinessDayClassification.StatutoryHolidays,self.monthlyPatternSettingBatchStatutoryHolidays());
               service.saveMonthlyPatternSettingBatch(BusinessDayClassification.NoneStatutoryHolidays,self.monthlyPatternSettingBatchNoneStatutoryHolidays());
               service.saveMonthlyPatternSettingBatch(BusinessDayClassification.PublicHolidays,self.monthlyPatternSettingBatchPublicHolidays());
               console.log('YES');
               service.checkWeeklyWorkSetting(new Date()).done(function(data){
                  console.log(data); 
               });
           }
        }

    }
}