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
        //date,workingDayAtr
        date: KnockoutObservable<number>;
        workingDayAtr: KnockoutObservable<number>;
        //classId
        classId: KnockoutObservable<string>;
        //WorkPlaceId
        workPlaceId: KnockoutObservable<string>;
        //list 
        list: KnockoutObservableArray<any>;
        //list holiday
        listHoliday: KnockoutObservableArray<Holiday>;
        itemHoliday: KnockoutObservable<Holiday>;
        //date value
        dateValue: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //start and end month
            //get data Form : A
            let param: IData = nts.uk.ui.windows.getShared('KSM004_D_PARAM') || { classification: 0, yearMonth: 20170101, workPlaceId: null, classCD: null };
            
            //date value
            self.dateValue = ko.observable({startDate: param.yearMonth.toString(), endDate: param.yearMonth.toString()});
            self.startMonth = ko.observable(self.dateValue().startDate);
            self.endMonth = ko.observable(self.dateValue().endDate);
            
            //classId
            self.classId = ko.observable(param.classCD);
            //workPlaceId
            self.workPlaceId = ko.observable(param.workPlaceId);
            self.typeClass = ko.observable(param.classification);
            //checkHoliday
            self.checkHoliday = ko.observable(true);
            //checkUpdate
            self.checkOverwrite = ko.observable(true);
            //date , workingDayAtr
            self.date = ko.observable(0);
            self.workingDayAtr = ko.observable(0);

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

            //list 
            self.list = ko.observableArray(null);

            //list holiday
            self.listHoliday = ko.observableArray(null);
            self.itemHoliday = ko.observable(null);
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
            nts.uk.ui.block.invisible();
            self.startMonth(self.dateValue().startDate);
            self.endMonth(self.dateValue().endDate);
            let startYM = moment(self.startMonth(), 'YYYYMM');
            let endYM = moment(self.endMonth(), 'YYYYMM');
            self.getAllHoliday().done(function(){
                self.list([]);
            endYM.add(1, 'M');
            
            //$('.nts-input').trigger("validate");
            _.defer(() => {
                if (!$('.nts-editor').ntsError("hasError")) {
                    // startYM < endYM
                        while (startYM.format("YYYYMMDD") < endYM.format("YYYYMMDD")) //value : 0-11
                        {
                            //date of month
                            let dateOfMonth = startYM.date(); //value : 1-31
                            //date or week
                            let dateOfWeek = startYM.day();   //value : 0-6
                            let date = (startYM.format("YYYYMMDD"));

                            // set value workingDayAtr
                            switch (dateOfWeek) {
                                case 0: self.workingDayAtr(self.selectedSun()); break;
                                case 1: self.workingDayAtr(self.selectedMon()); break;
                                case 2: self.workingDayAtr(self.selectedTue()); break;
                                case 3: self.workingDayAtr(self.selectedWed()); break;
                                case 4: self.workingDayAtr(self.selectedThu()); break;
                                case 5: self.workingDayAtr(self.selectedFri()); break;
                                case 6: self.workingDayAtr(self.selectedSat()); break;
                                default: break;
                            }
                            //self.workingDayAtr()
                            // if D3_2 : workingDayAtr = 0
                            if (self.workingDayAtr() == 0 && self.checkHoliday() == true) {
                                //get holiday by date in list
                                let holiday = _.find(self.listHoliday(), function(item: Holiday) {
                                    return item.date == moment(date).format("YYYY/MM/DD");
                                });
                                //kt xem co chon check ngay nghỉ    
                                if (holiday) {
                                    self.workingDayAtr(2);
                                }
                            }
                            let objTest = {
                                date: moment(date).format("YYYY/MM/DD"),
                                workingDayAtr: self.workingDayAtr()
                            };
                            self.list().push(objTest);
                            startYM.add(1, 'd');
                        }//end while
                        //if calendar company
                        if (self.typeClass() == 0) {
                            //if check overwrite = true
                            if (self.checkOverwrite() == true) {
                                self.updateCalendarCompany(self.list());
                            } else {
                                self.addCalendarCompany(self.list());
                            }
                            //if calendar class
                        } else if (self.typeClass() == 2) {
                            // add classId in list
                            _.forEach(self.list(), function(value) {
                                value.classId = self.classId();
                            });
                            //if check overwrite = true
                            if (self.checkOverwrite() == true) {

                                self.updateCalendarClass(self.list());

                            } else {
                                self.addCalendarClass(self.list());
                            }
                            //if calendar workplace   
                        } else if (self.typeClass() == 1) {
                            // add workPlaceId in list
                            _.forEach(self.list(), function(value) {
                                value.workPlaceId = self.workPlaceId();
                            });
                            //if check overwrite = true
                            if (self.checkOverwrite() == true) {
                                self.updateCalendarWorkplace(self.list());
                            } else {
                                self.addCalendarWorkplace(self.list());
                            }
                        }
                    
                    }//end : !$('.nts-editor').ntsError("hasError")
                    
                });
            });
        }//end decition

        /**
         * get all holiday : return listHoliday
         */
        getAllHoliday() {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllHoliday().done(function(data) {
                self.listHoliday(data);
                dfd.resolve(data);
            });
            return dfd.promise();
        }
        /**
         * add calendar company
         */
        addCalendarCompany(list) {
            var self = this;
            service.addCalendarCompany(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                nts.uk.ui.windows.close();     
                });
                
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
            });
        }

        /**
         * update calendar company
     */
        updateCalendarCompany(list) {
            var self = this;
            service.updateCalendarCompany(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                    nts.uk.ui.windows.close();  
                });   
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
            });
        }

        /**
         * add calendar class
         */
        addCalendarClass(list) {
            var self = this;
            service.addCalendarClass(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                    nts.uk.ui.windows.close();  
                });   
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
            });
        }

        /**
         * update calendar class
         */
        updateCalendarClass(list) {
            var self = this;
            service.updateCalendarClass(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                    nts.uk.ui.windows.close();  
                });   
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
            });
        }

        /**
         * add calendar workplace
         */
        addCalendarWorkplace(list) {
            var self = this;
            service.addCalendarWorkplace(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                    nts.uk.ui.windows.close();  
                });   
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
            });
        }

        /**
         * update calendar workplace
         */
        updateCalendarWorkplace(list) {
            var self = this;
            service.updateCalendarWorkplace(list).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                    nts.uk.ui.windows.close();  
                });   
            }).fail(function(res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert(res.mesage);
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
            date: any;
            workingDayAtr: number;
            constructor(date: any, workingDayAtr: number) {
                this.date = date;
                this.workingDayAtr = workingDayAtr;
            }
        }
        // class calendar class
        export class CalendarClass {
            classId: string;
            date: any;
            workingDayAtr: number;
            constructor(classId: string, date: any, workingDayAtr: number) {
                this.classId = classId;
                this.date = date;
                this.workingDayAtr = workingDayAtr;
            }
        }
        //class calendar workplace
        export class CalendarWorkplace {
            workPlaceId: string;
            date: any;
            workingDayAtr: number;
            constructor(workPlaceId: string, date: any, workingDayAtr: number) {
                this.workPlaceId = workPlaceId;
                this.date = date;
                this.workingDayAtr = workingDayAtr;
            }
        }
        //class holiday
        export class Holiday {
            date: any;
            holidayName: string;
            constructor(date: any, holidayName: string) {
                this.date = date;
                this.holidayName = holidayName;
            }
        }
        interface IData {
            classification: number,
            yearMonth: number,
            workPlaceId: string,
            classCD: string
        }
    }

}