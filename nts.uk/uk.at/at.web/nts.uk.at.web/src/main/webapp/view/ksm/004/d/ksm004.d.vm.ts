module ksm004.d.viewmodel {
    export class ScreenModel {
        //checkHoliday
        checkHoliday: KnockoutObservable<boolean>;
        //checkOverwrite
        checkOverwrite: KnockoutObservable<boolean>;

        // start and end month , typeClass
        startMonth: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        typeClass: KnockoutObservable<number>;

        // select day
        workdayGroup: KnockoutObservableArray<any>;
        selectedMon: KnockoutObservable<number>;
        selectedTue: KnockoutObservable<number>;
        selectedWed: KnockoutObservable<number>;
        selectedThu: KnockoutObservable<number>;
        selectedFri: KnockoutObservable<number>;
        selectedSat: KnockoutObservable<number>;
        selectedSun: KnockoutObservable<number>;
        //dateId,workingDayAtr
        dateId: KnockoutObservable<number>;
        workingDayAtr: KnockoutObservable<number>;
        //classId
        classId: KnockoutObservable<string>;
        //WorkPlaceId
        constructor() {
            var self = this;
            //start and end month
            nts.uk.ui.windows.getShared('classification');
            nts.uk.ui.windows.getShared('workPlaceId');
            nts.uk.ui.windows.getShared('classCD');
            nts.uk.ui.windows.getShared('startTime');
            nts.uk.ui.windows.getShared('endTime');
            
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
        startPage() {

        }//end startPage

        /**
         * function btn decition
         */
        decition() {
            var self = this;

            let startYM = moment(self.startMonth(), 'YYYYMM');
            let endYM = moment(self.endMonth(), 'YYYYMM');

            while (startYM.month() <= endYM.month()) //value : 0-11
            {
                let dateOfMonth = startYM.date(); //value : 1-31

                let dateOfWeek = startYM.day();   //value : 0-6

                let date = parseInt(startYM.format("YYYYMMDD"));
                self.dateId(date);
                switch(dateOfWeek){
                    case 0: self.workingDayAtr(self.selectedMon());break;
                    case 1: self.workingDayAtr(self.selectedTue());break;
                    case 2: self.workingDayAtr(self.selectedWed());break;
                    case 3: self.workingDayAtr(self.selectedThu());break;
                    case 4: self.workingDayAtr(self.selectedFri());break;
                    case 5: self.workingDayAtr(self.selectedSat());break;
                    case 6: self.workingDayAtr(self.selectedSun());break;
                    default : break;   
                }
                //self.workingDayAtr()
                // if D3_2 : neu la ngay thuong
                if (dateOfWeek != 5 && dateOfWeek != 6) {
                    if (self.checkHoliday() == true) {
                        self.checkHoli(date).done(function(data) {
                            if (data.present == true) {
                                self.workingDayAtr(2);
                            }

                        });
                    }
                }
                // if typeclass : company
                if (self.typeClass() == 0) {
                    //if check overwrite = true
                    if (self.checkOverwrite() == true) {
                        self.updateCalendarCompany();
                    } else {
                        self.addCalendarCompany();
                    }
                }
                startYM.add(1, 'd');
            }

        }//end decition

        checkHoli(date: number) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getHolidayByDate(date).done(function(data) {
                dfd.resolve(data);
            });
            return dfd.promise();
        }


        /**
         * add calendar company
         */
        addCalendarCompany() {
            var self = this;
            var calendarCompany = new model.CalendarCompany(self.dateId(), self.workingDayAtr());
            service.addCalendarCompany(calendarCompany);
        }

        /**
         * update calendar company
         */
        updateCalendarCompany() {
            var self = this;
            var calendarCompany = new model.CalendarCompany(self.dateId(), self.workingDayAtr());
            service.updateCalendarCompany(calendarCompany);
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
            classId: string;
            dateId: number;
            workingDayAtr: number;
            constructor(classId: string, dateId: number, workingDayAtr: number) {
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