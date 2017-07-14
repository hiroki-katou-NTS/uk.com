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
            //current Date
            //            self.currentYear = ko.observable(moment(new Date()).format("YYYY"));
            //            self.currentMonth = ko.observable(moment(new Date()).format("MM"));
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
                alert(value);
            })
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //let processMonth: number = 1900;
            let isUse: number = 1;
            let arrOptionaDates: Array<OptionalDate> = [];
            service.getSpecificDateByIsUse(isUse).done(function(lstSpecifiDate: any) {
                if (lstSpecifiDate.length > 0) {
                    //get Company Start Day
                    service.getCompanyStartDay().done(function(startDayComapny: any) {
                        self.firstDay(startDayComapny.startDay);
                        //Fill data to Calendar 
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                        dfd.reject();
                    });
                    //
                    //set parameter to caledar
                    let lstBoxCheck: Array<BoxModel> = [];
                    _.forEach(lstSpecifiDate, function(item) {
                        lstBoxCheck.push(new BoxModel(item.specificDateItemNo, item.specificName));
                    });
                    _.orderBy(lstBoxCheck, ['id'], ['desc']);
                    self.itemList(lstBoxCheck);
                    //Get end of Month
                    let endMonth: number = moment(self.yearMonthPicked()).endOf('month').date();
                    //Array Name 
                    let arrName: Array<string> = [];
                    let specDay: string = '00';
                    let processDay : number = '19000101';
                    //get data from StartDate to EndDate
                    //get List Text 
                    //specDay = _.padStart(i, 2, '0');
                    //processDate = Number(self.yearMonthPicked());
                    service.getCompanySpecificDateByCompanyDateWithName(self.yearMonthPicked(), isUse).done(function(lstComSpecDate: any) {
                        //debugger;
                        if (lstComSpecDate.length > 0) {
//                            for(let i=0; i<endMonth;i++){
//                                processDay = Number(self.yearMonthPicked()+_.padStart(i, 2, '0'));
//                               _.find(lstComSpecDate,function(item){
//                                    if()    
//                               })
//                            }
//                            _.orderBy(lstComSpecDate,'specificDate','asc');
//                            _.forEach(lstComSpecDate, function(itemSpec) {
//                                arrName.push(itemSpec.specificDateItemName);
//                            });
//                            if (arrName.length > 0) {
//                                arrOptionaDates.push(new OptionalDate(moment(self.yearMonthPicked()).format("YYYY-MM-") + specDay, 'red', 'white', arrName));
//                            };
                        }
                        //
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                        dfd.reject();
                    });
                    //fill data to Calendar
                }
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