module ksm002.a.viewmodel {
    export class ScreenModel {
        //MODE
        isNew: KnockoutObservable<boolean>;
        // PANE
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<number>;
        enable: KnockoutObservable<boolean>;
        //current Date
        currentYear: KnockoutObservable<String>;
        currentMonth: KnockoutObservable<String>;
        endDateOfMonth: KnockoutObservable<number>;
        //Calendar
        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<any>;
        firstDay: KnockoutObservable<number>;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;
        eventDisplay: KnockoutObservable<boolean>;
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean>;
        workplaceName: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([]);
            self.selectedIds = ko.observableArray([1, 2]);
            self.enable = ko.observable(true);
            //Calendar
            self.yearMonthPicked = ko.observable(moment(new Date()).format("YYYYMM"));
            self.cssRangerYM = {};
            self.optionDates = ko.observableArray([]);

            self.firstDay = ko.observable(1);
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(true);
            self.eventUpdatable = ko.observable(true);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(true);
            nts.uk.at.view.kcp006.a.CellClickEvent = function(date) {
                alert('sssss');
            };
            self.yearMonthPicked.subscribe(function(value) {
                let arrOptionaDates: Array<OptionalDate> = [];
                self.getDataToOneMonth(value).done(function(arrOptionaDates){
                    self.optionDates(arrOptionaDates);
                })
            })
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            let isUse: number = 1;
            let arrOptionaDates: Array<OptionalDate> = [];
            service.getSpecificDateByIsUse(isUse).done(function(lstSpecifiDate: any) {
                if (lstSpecifiDate.length > 0) {
                    //Set Start Day of Company
                    self.getComStartDay().done(function(startDay:number){
                        self.firstDay(startDay);
                    });
                    //set parameter to calendar
                    let lstBoxCheck: Array<BoxModel> = [];
                    _.forEach(lstSpecifiDate, function(item) {
                        lstBoxCheck.push(new BoxModel(item.specificDateItemNo, item.specificName));
                    });
                    _.orderBy(lstBoxCheck, ['id'], ['desc']);
                    self.itemList(lstBoxCheck);
                    //Set data to calendar
                    self.getDataToOneMonth(self.yearMonthPicked()).done(function(arrOptionaDates) {
                        if(arrOptionaDates.length>0)
                            self.isNew(false);
                        self.optionDates(arrOptionaDates);    
                    })
                    dfd.resolve();
                }
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        
        /**Fill data to each month*/
        // Return Array of data in day
        getDataToOneMonth(processMonth: string) : JQueryPromise<Array<OptionalDate>> {
            let dfd = $.Deferred<any>();
            let endOfMonth: number = moment(processMonth).endOf('month').date();
            let isUse: number = 1;
            let arrOptionaDates : Array<OptionalDate> = [];
            //Array Name to fill on  one Date
            let arrName: Array<string> = [];
            service.getCompanySpecificDateByCompanyDateWithName(processMonth, isUse).done(function(lstComSpecDate: any) {
                if (lstComSpecDate.length > 0) {
                    for (let j = 1; j <= endOfMonth; j++) {
                        let processDay: string = processMonth + _.padStart(j, 2, '0');
                        arrName = [];
                        //Loop in each Day
                        _.forEach(lstComSpecDate, function(comItem) {
                            if (comItem.specificDate == Number(processDay)) {
                                arrName.push(comItem.specificDateItemName);
                            };
                        });
                        arrOptionaDates.push(new OptionalDate(moment(processDay).format('YYYY-MM-DD'), 'red', 'white', arrName));
                    };
                    //Return Array of Data in Month
                    dfd.resolve(arrOptionaDates);
                }
               
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        /** get Start Day of Company*/
        getComStartDay(): JQueryPromise<number>{
            let dfd = $.Deferred<any>();
            //get Company Start Day
            service.getCompanyStartDay().done(function(startDayComapny: number) {
                //self.firstDay();
                dfd.resolve(startDayComapny.startDay);
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        
    }
    
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class SpecItem {
        specItemNo: number;
        specItemName: string;
        constructor(specItemNo, specItemName) {
            var self = this;
            self.specItemNo = specItemNo;
            self.specItemName = specItemName;
        }
    }

    class OptionalDate {
        start: string;
        textColor: string;
        backgroundColor: string;
        listText: Array<string>;
        constructor(start, textColor, backgroundColor, listText) {
            var self = this;
            self.start = start;
            self.textColor = textColor;
            self.backgroundColor = backgroundColor;
            self.listText = listText;
        }
    }
}