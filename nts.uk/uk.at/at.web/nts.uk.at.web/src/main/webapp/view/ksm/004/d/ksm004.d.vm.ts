module ksm004.d.viewmodel{
    export class ScreenModel{
        //checkHoliday
        checkHoliday :KnockoutObservable<boolean>;
        //checkOverwrite
        checkOverwrite :KnockoutObservable<boolean>;
                      
        // start and end month , typeClass
        startMonth : KnockoutObservable<number>;
        endMonth : KnockoutObservable<number>;
        typeClass : KnockoutObservable<number>;
        
        // select day
        workdayGroup : KnockoutObservableArray<any>;
        selectedMon : KnockoutObservable<number>;
        selectedTue : KnockoutObservable<number>;
        selectedWed : KnockoutObservable<number>;
        selectedThu : KnockoutObservable<number>;
        selectedFri : KnockoutObservable<number>;
        selectedSat : KnockoutObservable<number>;
        selectedSun : KnockoutObservable<number>;
        //dateId,workingDayAtr
        dateId : KnockoutObservable<number>;
        workingDayAtr : KnockoutObservable<number>;
        //classId
        classId : KnockoutObservable<string>;
        //WorkPlaceId
        workPlaceId : 
        
        
        constructor(){
            var self = this;
            //start and end month
            self.startMonth = ko.observable(201602);
            self.endMonth = ko.observable(201602);
            self.typeClass = ko.observable(0);
            //checkHoliday
            self.checkHoliday = ko.observable(true);
            //checkUpdate
            self.checkOverwrite = ko.observable(true);
            
            //date , workingDayAtr
            self.dateId = ko.observable(0);
            self.workingDayAtr = ko.observable(0);
            //classId
            self.classId = ko.observable('');
            //workdayGroup
            self.workdayGroup = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KSM004_46') },
                { code: 1, name: nts.uk.resource.getText('KSM004_47') },
                { code: 2, name: nts.uk.resource.getText('KSM004_48') }
            ]);
            // day
            self.selectedMon = ko.observable(0);
            self.selectedTue = ko.observable(0);
            self.selectedWed = ko.observable(0);
            self.selectedThu = ko.observable(0);
            self.selectedFri = ko.observable(0);
            self.selectedSat = ko.observable(2);
            self.selectedSun = ko.observable(1);

        }//end constructor
        
         /**
         * function startPage
         */
        startPage(){
            
        }//end startPage
        
        /**
         * function btn decition
         */
        decition(){
            var self = this;
            
            let startYM = moment(self.startMonth(),'YYYYMM');
            let endYM = moment(self.endMonth(),'YYYYMM');
            
            while(startYM.month() <= endYM.month()) //value : 0-11
            {
                let dateOfMonth = startYM.date(); //value : 1-31 
                let dateOfWeek = startYM.day();   //value : 0-6
                alert(dateOfMonth);
                if(self.typeClass ==0){
                }
                startYM.add(1,'d');
            }
            
        }//end decition
        
        /**
         * add calendar company
         */
        addCalendarCompany() {
            var self = this;
            var calendarCompany = new model.CalendarCompany(self.dateId(),self.workingDayAtr());
            service.addCalendarCompany(calendarCompany).done(function(){
                //nts.uk.ui.dialog.info({ messageId: "Msg_15" });    
            });    
        }
        /**
         * update calendar company
         */
        updateCalendarCompany(){
            var self = this;
            var calendarCompany = new model.CalendarCompany(self.dateId(),self.workingDayAtr());
            service.updateCalendarCompany(calendarCompany).done(function(){
                //nts.uk.ui.dialog.info({ messageId: "Msg_15" });    
            });    
        }
        
        
    }//end screen model

    //model
    export module model {
        //class calendar company
        export class CalendarCompany {
            dateId: number;
            workingDayAtr: number;
            constructor(dateId: number, workingDayAtr: number) {
                this.dateId = dateId;
                this.workingDayAtr = workingDayAtr;
            }
        }
        // class calendar class
        export class CalendarClass {
            classId : string;
            dateId: number;
            workingDayAtr: number;
            constructor(classId:string, dateId: number, workingDayAtr: number) {
                this.classId = classId;
                this.dateId = dateId;
                this.workingDayAtr = workingDayAtr;
            }
        }
        //class calendar workplace
        export class CalendarWorkplace {
            dateId: number;
            workingDayAtr: number;
            constructor(dateId: number, workingDayAtr: number) {
                this.dateId = dateId;
                this.workingDayAtr = workingDayAtr;
            }
        }
    }
    
}