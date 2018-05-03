module nts.uk.at.view.ktg029.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        currentMonth: KnockoutObservable<period>;
        nextMonth: KnockoutObservable<period>;
        switchDate: KnockoutObservable<boolean>;
        txtDatePeriod: KnockoutObservable<string>;
        btnSwitch: KnockoutObservable<string>;
        
        displayOvertime: KnockoutObservable<boolean>; 
        displayHoliInstruct: KnockoutObservable<boolean>;
        displayApproved: KnockoutObservable<boolean>;
        displayUnApproved: KnockoutObservable<boolean>;
        displayDenied: KnockoutObservable<boolean>;
        displayRemand: KnockoutObservable<boolean>;
        displayDeadlineMonth: KnockoutObservable<boolean>;
        displayPresenceDailyPer: KnockoutObservable<boolean>;
        displayWorkRecord: KnockoutObservable<boolean>;
        displayOverTimeHours: KnockoutObservable<boolean>;
        displayFlexTime: KnockoutObservable<boolean>;
        displayRestTime: KnockoutObservable<boolean>;
        displayNightWorkHours: KnockoutObservable<boolean>;
        displayLateOrEarlyRetreat: KnockoutObservable<boolean>;
        /* A17 */
        displayYearlyHoliDay: KnockoutObservable<boolean>;
        /* A18 */
        displayYearRemainNo: KnockoutObservable<boolean>;
        // A19
        displayPlannedYearHoliday: KnockoutObservable<boolean>;
        // A20
        displayRemainAlternationNo: KnockoutObservable<boolean>;
        // A21 
        displayRemainsLeft: KnockoutObservable<boolean>;
        displayPublicHDNo: KnockoutObservable<boolean>;
        displayHDRemainNo: KnockoutObservable<boolean>;
        displayCareLeaveNo: KnockoutObservable<boolean>;
        displaySPHDRamainNo: KnockoutObservable<boolean>;
        displaySixtyhExtraRest: KnockoutObservable<boolean>;
        
        
        
        constructor() {
            var self = this;
            self.switchDate = ko.observable(true);      
            self.currentMonth = ko.observable(new period("",""));
            self.nextMonth = ko.observable(new period("",""));
            self.txtDatePeriod = ko.observable("");
            self.btnSwitch = ko.observable(getText('KTG029_6'));
            
            self.displayOvertime = ko.observable(false);
            self.displayHoliInstruct = ko.observable(false);
            self.displayApproved = ko.observable(false);
            self.displayUnApproved = ko.observable(false);
            self.displayDenied = ko.observable(false);
            self.displayRemand = ko.observable(false);
            self.displayDeadlineMonth = ko.observable(false);
            self.displayPresenceDailyPer = ko.observable(false);
            self.displayWorkRecord = ko.observable(false);
            self.displayOverTimeHours = ko.observable(false);
            self.displayFlexTime = ko.observable(false);
            self.displayRestTime = ko.observable(false);
            self.displayNightWorkHours = ko.observable(false);
            self.displayLateOrEarlyRetreat = ko.observable(false);
            self.displayYearlyHoliDay = ko.observable(false);
            self.displayYearRemainNo = ko.observable(false);
            self.displayPlannedYearHoliday = ko.observable(false);
            self.displayRemainAlternationNo = ko.observable(false); 
            self.displayRemainsLeft = ko.observable(false);
            self.displayPublicHDNo = ko.observable(false);
            self.displayHDRemainNo = ko.observable(false);
            self.displayCareLeaveNo = ko.observable(false);
            self.displaySPHDRamainNo = ko.observable(false);
            self.displaySixtyhExtraRest = ko.observable(false);
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            var topPagePartCode = $(location).attr('search').split('=')[1];            
            new service.Service().getOptionalWidgetDisplay(topPagePartCode).done(function(data: any){
                if(data!=null){
                    self.excuteDisplay(data.optionalWidgetImport);
                    self.getDate(data.datePeriodDto);
                }    
                block.clear();
            });           
            dfd.resolve();

            return dfd.promise();
        }
        private excuteDisplay(data: any):void{
            var self = this;
            if(data==null)
                return;
            var listDisplayItems = data.widgetDisplayItemExport;
            listDisplayItems.forEach(function (item){
                if(item.displayItemType == widgetDisplayItem.OVERTIME_WORK_NO){
                    self.displayOvertime(item.notUseAtr == 1 ? true: false); 
                }else if(item.displayItemType == widgetDisplayItem.INSTRUCTION_HD_NO){
                    self.displayHoliInstruct(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.APPROVED_NO){
                    self.displayApproved(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.UNAPPROVED_NO){
                    self.displayUnApproved(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.DENIED_NO){
                    self.displayDenied(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.REMAND_NO){
                    self.displayRemand(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.APP_DEADLINE_MONTH){
                    self.displayDeadlineMonth(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.PRESENCE_DAILY_PER){
                    self.displayPresenceDailyPer(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.REFER_WORK_RECORD){
                    self.displayWorkRecord(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.OVERTIME_HOURS){
                    self.displayOverTimeHours(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.FLEX_TIME){
                    self.displayFlexTime(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.REST_TIME){
                    self.displayRestTime(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.NIGHT_WORK_HOURS){
                    self.displayNightWorkHours(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.LATE_OR_EARLY_RETREAT){
                    self.displayLateOrEarlyRetreat(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.YEARLY_HD){
                    /* A17 */
                    self.displayYearlyHoliDay(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.RESERVED_YEARS_REMAIN_NO){
                    self.displayYearRemainNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.PLANNED_YEAR_HOLIDAY){
                    self.displayPlannedYearHoliday(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.REMAIN_ALTERNATION_NO){
                    self.displayRemainAlternationNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.REMAINS_LEFT){
                    self.displayRemainsLeft(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.PUBLIC_HD_NO){
                    self.displayPublicHDNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.HD_REMAIN_NO){
                    self.displayHDRemainNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.CARE_LEAVE_NO){
                    self.displayCareLeaveNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.SPHD_RAMAIN_NO){
                    self.displaySPHDRamainNo(item.notUseAtr == 1 ? true: false);
                }else if(item.displayItemType == widgetDisplayItem.SIXTYH_EXTRA_REST){
                    self.displaySixtyhExtraRest(item.notUseAtr == 1 ? true: false);
                }
            });
        }
        
        private getDate(data: any) :void{
            var self = this;
            if(data==null)
                return;
            self.currentMonth = ko.observable(new period(data.strCurrentMonth, data.endCurrentMonth));
            self.nextMonth = ko.observable(new period(data.strNextMonth, data.endNextMonth));
            self.switchMonth();
        }
        private switchMonth():void{
            var self = this;
            if(self.switchDate()){
                var month = self.currentMonth().endMonth.getMonth()+1;
                if(month<10){
                     month='0'+month; 
                } 
                var lastMonth = self.currentMonth().endMonth.getDate();
                self.txtDatePeriod(month+'/01'+getText('KTG029_3')+month+'/'+lastMonth+getText('KTG029_5'));
                self.switchDate(false);
            }else{
                var month = self.nextMonth().endMonth.getMonth()+1;
                if(month<10){
                     month='0'+month; 
                }
                var lastMonth = self.nextMonth().endMonth.getDate();
                self.txtDatePeriod(month+'/01'+getText('KTG029_3')+month+'/'+lastMonth+getText('KTG029_5'));
                self.switchDate(true);
            }
        }
        
        openKAF015Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kaf/015/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openCMM045Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/cmm/045/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openKDW003Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kdw/003/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openKDL033Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kdl/033/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openKDL029Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kdl/029/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openKDL009Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kdl/009/a/index.xhtml').onClosed(function(): any {
//            });

        }
        
        openKDL017Dialog() {
            let self = this;
//            nts.uk.ui.windows.sub.modal('/view/kdl/017/a/index.xhtml').onClosed(function(): any {
//            });

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

