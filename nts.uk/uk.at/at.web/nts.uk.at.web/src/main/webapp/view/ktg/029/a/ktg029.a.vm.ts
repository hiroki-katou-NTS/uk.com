module nts.uk.at.view.ktg029.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        currentMonth: KnockoutObservable<period>;
        nextMonth: KnockoutObservable<period>;
        switchDate: KnockoutObservable<boolean>;
        txtDatePeriod: KnockoutObservable<string>;
        
        displayOvertime: KnockoutObservable<boolean>; 
        
        constructor() {
            var self = this;
            self.switchDate = ko.observable(true);      
            self.currentMonth = ko.observable(new period("",""));
            self.nextMonth = ko.observable(new period("",""));
            self.txtDatePeriod = ko.observable("");
            
            self.displayOvertime = ko.observable(false);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            var topPagePartCode = $(location).attr('search').split('=')[1];            
            new service.Service().getOptionalWidget(topPagePartCode).done(function(data: any){
                if(data!=null){
                    self.excuteDisplay(data);
                }    
                self.getDate();
                block.clear();
            });           
            dfd.resolve();

            return dfd.promise();
        }
        private excuteDisplay(data: any):void{
            var self = this;
            
            
        }
        
        private getDate() :void{
            var self = this;
            block.invisible();
            new service.Service().getPeriod().done(function(data: any){
                if(data!=null){
                    self.currentMonth = ko.observable(new period(data.strCurrentMonth, data.endCurrentMonth));
                    self.nextMonth = ko.observable(new period(data.strNextMonth, data.endNextMonth));
                    self.switchMonth();
                }  
                block.clear();
            });
        }
        private switchMonth():void{
            var self = this;
            if(self.switchDate()){
                var month = self.currentMonth().endMonth.getMonth()+1;
                var lastMonth = self.currentMonth().endMonth.getDate();
                self.txtDatePeriod(month+'/01'+getText('KTG029_3')+month+'/'+lastMonth+getText('KTG029_5'));
                self.switchDate(false);
            }else{
                var month = self.nextMonth().endMonth.getMonth()+1;
                var lastMonth = self.nextMonth().endMonth.getDate();
                self.txtDatePeriod(month+'/01'+getText('KTG029_3')+month+'/'+lastMonth+getText('KTG029_5'));
                self.switchDate(true);
            }
        }
        
        createCurrentMonth(){
            var self = this;
            var date = new Date();
            var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
            
            self.currentMonth = ko.observable(new period(lastDay.getFullYear()+'/'+(lastDay.getMonth() + 1) + '/1',
                                                        lastDay.getFullYear()+'/'+(lastDay.getMonth() + 1) + '/' + lastDay.getDate()));
            
            if (date.getMonth() == 11) {
               var lastDayNextMonth = new Date(date.getFullYear() + 1, 0, 0);
            } else {
               var lastDayNextMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0);
            }
            self.nextMonth = ko.observable(new period(lastDayNextMonth.getFullYear()+'/'+(lastDayNextMonth.getMonth() + 1) + '/1',
                                                     lastDayNextMonth.getFullYear()+'/'+(lastDayNextMonth.getMonth() + 1) + '/' + lastDayNextMonth.getDate()));    
        }
        
        openKAF015Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kaf/015/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openCMM045Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmm/045/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDW003Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdw/003/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL033Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/033/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL029Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/029/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL009Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/009/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL017Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/017/a/index.xhtml').onClosed(function(): any {
            });

        }
    }
    export class period{
        strMonth: Date;
        endMonth: Date;
        constructor(strMonth: string, endMonth: string){
            this.strMonth = new Date(strMonth);
            this.endMonth = new Date(endMonth);
            
        }    
    }
    export enum widgetDisplayItem{
        OVERTIME_WORK_NO = 0,
        INSTRUCTION_HD_NO = 1,
        APPROVED_NO = 2,
        UNAPPROVED_NO = 3,
        DENIED_NO = 4,
        REMAND_NO = 5,
        APP_DEADLINE_MONTH = 6,
        PRESENCE_DAILY_PER = 7,
        REFER_WORK_RECORD = 8,
        OVERTIME_HOURS = 9,
        FLEX_TIME = 10,
        REST_TIME = 11,
        NIGHT_WORK_HOURS = 12,
        LATE_OR_EARLY_RETREAT = 13,
        YEARLY_HD = 14,
        HAFT_DAY_OFF = 15,
        HOURS_OF_HOLIDAY_UPPER_LIMIT = 16,
        RESERVED_YEARS_REMAIN_NO = 17,
        PLANNED_YEAR_HOLIDAY = 18,
        REMAIN_ALTERNATION_NO = 19,
        REMAINS_LEFT = 20,
        PUBLIC_HD_NO = 21,
        HD_REMAIN_NO = 22,
        CARE_LEAVE_NO = 23,
        SPHD_RAMAIN_NO = 24,
        SIXTYH_EXTRA_REST = 25
    }
    export enum NotUseAtr{
        USE = 1,
        NOT_USE =0    
    }
}

