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
        workPlaceId : KnockoutObservable<string>;
        //list company 
        listCompany: KnockoutObservableArray<CalendarCompany>;
        //list class
        listClass: KnockoutObservableArray<CalendarClass>;
        //list workplace 
        listWorkplace: KnockoutObservableArray<CalendarWorkplace>;
        constructor() {
            var self = this;
            //start and end month
            nts.uk.ui.windows.getShared('classification');
            nts.uk.ui.windows.getShared('workPlaceId');
            nts.uk.ui.windows.getShared('classCD');
            nts.uk.ui.windows.getShared('startTime');
            nts.uk.ui.windows.getShared('endTime');

            self.startMonth = ko.observable(201701);
            self.endMonth = ko.observable(201702);
            self.typeClass = ko.observable(2);
            //checkHoliday
            self.checkHoliday = ko.observable(true);
            //checkUpdate
            self.checkOverwrite = ko.observable(true);
            //date , workingDayAtr
            self.dateId = ko.observable(0);
            self.workingDayAtr = ko.observable(0);
            //classId
            self.classId = ko.observable('0000000001');
            //workPlaceId
            self.workPlaceId = ko.observable('38400000-8cf0-11bd-b23e-10b96e4ef00d');
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

            //list company
            self.listCompany = ko.observableArray(null);
            //list class 
            self.listClass = ko.observableArray(null);
            //list workplace 
            self.listWorkplace = ko.observableArray(null);


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
            self.listCompany([]);
            self.listClass([]);
            self.listWorkplace([]);
            endYM.add(1, 'M');
            nts.uk.ui.block.invisible();
            $('.nts-input').trigger("validate");
            _.defer(() => {
                if (!$('.nts-editor').ntsError("hasError")) {
                    // startYM < endYM 
                    while (startYM.format("YYYYMMDD") < endYM.format("YYYYMMDD")) //value : 0-11
                    {
                        //date of month
                        let dateOfMonth = startYM.date(); //value : 1-31
                        //date or week
                        let dateOfWeek = startYM.day();   //value : 0-6
                        let date = parseInt(startYM.format("YYYYMMDD"));
                        self.dateId(date);
                        // set value workingDayAtr
                        switch (dateOfWeek) {
                            case 0: self.workingDayAtr(self.selectedMon()); break;
                            case 1: self.workingDayAtr(self.selectedTue()); break;
                            case 2: self.workingDayAtr(self.selectedWed()); break;
                            case 3: self.workingDayAtr(self.selectedThu()); break;
                            case 4: self.workingDayAtr(self.selectedFri()); break;
                            case 5: self.workingDayAtr(self.selectedSat()); break;
                            case 6: self.workingDayAtr(self.selectedSun()); break;
                            default: break;
                        }
                        //self.workingDayAtr()
                        // if D3_2 : neu la ngay thuong
                        if (dateOfWeek != 5 && dateOfWeek != 6) {
                            //kt xem co chon check ngay nghỉ k
                            if (self.checkHoliday() == true) {
                                self.checkHoli(date).done(function(data) {
                                    // nếu là ngày nghỉ thì set workingDayAtr = 2
                                    if (data.present == true) {
                                        self.workingDayAtr(2);
                                    }

                                });
                            }
                         }
                         // if typeclass : company
                       if (self.typeClass() == 0) {
                            let objCompany = {
                                dateId: date,
                                workingDayAtr: self.workingDayAtr()
                            };
                            //add  obj company to list
                            self.listCompany().push(objCompany);
                        
                       // if typeclass : class
                       }else if(self.typeClass() == 1) {
                            let objClass = {
                               classId:self.classId(),     
                               dateId: date,
                               workingDayAtr: self.workingDayAtr()
                            };
                            self.listClass().push(objClass);
                       }else if(self.typeClass() ==2){
                            let objWorkplave = {
                               workPlaceId:self.workPlaceId(),     
                               dateId: date,
                               workingDayAtr: self.workingDayAtr()
                            };
                            self.listWorkplace().push(objWorkplave);     
                       }    
                     startYM.add(1, 'd');
                 }
            
                 nts.uk.ui.block.clear();
                //if calendar company
                if(self.typeClass() == 0) {
                    //if check overwrite = true
                    if (self.checkOverwrite() == true) {
                        self.updateCalendarCompany();
                    } else {
                        self.addCalendarCompany();
                    }
                //if calendar class
                } else if (self.typeClass() == 1) {
                    //if check overwrite = true
                    if (self.checkOverwrite() == true) {
                        self.updateCalendarClass();
                    } else {
                        self.addCalendarClass();
                    }
                //if calendar workplace   
                } else if (self.typeClass() == 2) {
                    //if check overwrite = true
                    if (self.checkOverwrite() == true) {
                        self.updateCalendarWorkplace();
                    } else {
                        self.addCalendarWorkplace();
                    }
                }
            
//             nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            }
    
        });
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
             service.addCalendarCompany(self.listCompany()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
         }
    
            /**
             * update calendar company
         */ 
         updateCalendarCompany() {
             var self = this;
             service.updateCalendarCompany(self.listCompany()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
         }
    
            /**
             * add calendar class
             */
         addCalendarClass() {
             var self = this;
             service.addCalendarClass(self.listClass()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
         }
    
            /**
             * update calendar class
             */
        updateCalendarClass() {
             var self = this;
             service.updateCalendarClass(self.listClass()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
        }
    
            /**
             * add calendar workplace
             */
            addCalendarWorkplace() {
            var self = this;
                 service.addCalendarWorkplace(self.listWorkplace()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
            }
    
            /**
             * update calendar workplace
             */
            updateCalendarWorkplace() {
            var self = this;
                 service.updateCalendarWorkplace(self.listWorkplace()).done(function(){
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                 });
                         
            }
    
            /**
             * close dialog
             */ 
            closeDialog() {
                nts.uk.ui.windows.close();
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
             workPlaceId: string;
             dateId: number;
             workingDayAtr: number;
             constructor(workPlaceId: string, dateId: number, workingDayAtr: number) {
                 this.workPlaceId = workPlaceId;
                 this.dateId = dateId;
                 this.workingDayAtr = workingDayAtr;
             }
        }
    }

}