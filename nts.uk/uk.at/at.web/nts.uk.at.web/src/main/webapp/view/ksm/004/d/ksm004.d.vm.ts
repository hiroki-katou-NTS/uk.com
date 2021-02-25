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
        selectedMon: KnockoutObservable<number> = ko.observable(0);
        selectedTue: KnockoutObservable<number> = ko.observable(0);
        selectedWed: KnockoutObservable<number> = ko.observable(0);
        selectedThu: KnockoutObservable<number> = ko.observable(0);
        selectedFri: KnockoutObservable<number> = ko.observable(0);
        selectedSat: KnockoutObservable<number> = ko.observable(0);
        selectedSun: KnockoutObservable<number> = ko.observable(0);
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
        listHoliday: KnockoutObservableArray<model.Holiday>;
        itemHoliday: KnockoutObservable<model.Holiday>;
        //date value
        dateValue: KnockoutObservable<any>;

        constructor() {
            const self = this;
            //start and end month
            //get data Form : A
            let param: model.IData = nts.uk.ui.windows.getShared('KSM004_D_PARAM') || { classification: 0, yearMonth: 20170101, workPlaceId: null, classCD: null };

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

            //list
            self.list = ko.observableArray(null);

            //list holiday
            self.listHoliday = ko.observableArray(null);
            self.itemHoliday = ko.observable(null);
            $("#table-bottom").ntsFixedTable({ });
        }//end constructor

        /**
        * function startPage
        */
        startPage(): JQueryPromise<any> {
            const self = this;
            nts.uk.ui.block.invisible();
            let dfd = $.Deferred();
            service.getWeeklyWorkDay().done(function(data){
                if(data && data.workdayPatternItemDtoList.length > 0) {
                    data.workdayPatternItemDtoList.forEach(item => {
                        switch (item.dayOfWeek) {
                            case 1:
                                self.selectedMon(Number(item.workdayDivision));
                                break;
                            case 2:
                                self.selectedTue(item.workdayDivision);
                                break;
                            case 3:
                                self.selectedWed (item.workdayDivision);
                                break;
                            case 4:
                                self.selectedThu (item.workdayDivision);
                                break;
                            case 5:
                                self.selectedFri(item.workdayDivision);
                                break;
                            case 6:
                                self.selectedSat(item.workdayDivision);
                                break;
                            case 7:
                                self.selectedSun(item.workdayDivision);
                                break;
                        }
                    });
                }
                dfd.resolve();
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }//end startPage

        /**
         * function btn decition
         */
        decition() {
            const self = this;
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
                                let holiday = _.find(self.listHoliday(), function(item: model.Holiday) {
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
                    
                    }
                    
                });
            });
        }
        /**
         * get all holiday : return listHoliday
         */
        getAllHoliday() {
            const self = this;
            let dfd = $.Deferred<any>();
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

        //interface WorkdayPatternItem
        export interface WeeklyWorkDay {
            companyId: string;
            listWorkdayPatternItem: Array<WorkdayPatternItem>
        }

        //interface WorkdayPatternItem
        interface WorkdayPatternItem {
            dayOfWeek: number;
            workdayDivision: number;
        }

        export interface IData {
            classification: number,
            yearMonth: number,
            workPlaceId: string,
            classCD: string
        }
    }

}