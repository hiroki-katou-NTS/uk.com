module kml001.a.viewmodel {
    export class ScreenModel {
        gridPersonCostList: KnockoutObservableArray<GridPersonCostCalculation>;
        currentGridPersonCost: KnockoutObservable<GridPersonCostCalculation>;
        personCostList: KnockoutObservableArray<PersonCostCalculation>;
        currentPersonCost: KnockoutObservable<PersonCostCalculation>;
        constructor() {
            var self = this;
            self.personCostList = ko.observableArray([
                new PersonCostCalculation('','7','7',0,'2011/3/3','2012/3/3'),
                new PersonCostCalculation('','8','8',1,'2012/3/3','2013/3/3'),
                new PersonCostCalculation('','9','9',2,'2013/3/3','2014/3/3'),
                new PersonCostCalculation('','10','10',3,'2014/3/3','2015/3/3'),
                new PersonCostCalculation('','11','11',4,'2015/3/3','2016/3/3'),
                new PersonCostCalculation('','12','12',0,'2016/3/3','9999/12/31')
            ]);
            self.currentPersonCost = ko.observable(_.first(self.personCostList()));
            self.gridPersonCostList = ko.observableArray([]);
            self.personCostList().forEach(function(item) { self.gridPersonCostList.push({dateRange: item.startDate()+" ~ "+item.endDate()})});
            self.currentGridPersonCost = ko.observable(self.currentPersonCost().startDate()+" ~ "+self.currentPersonCost().endDate()); 
            self.currentGridPersonCost.subscribe(function(value){
                self.currentPersonCost(self.getPersonCostCalculationInfo(_.split(value, ' ', 1)[0]));
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            return dfd.promise();
        }
        
        premiumDialog() {
            nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定" });
        }

        createDialog() {
            var self = this;
            let personCostCalc: PersonCostCalculationInterface = self.getPersonCostCalculationInfo();
            nts.uk.ui.windows.setShared('lastestStartDate', _.last(self.personCostList()).startDate());
            nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function() {
                let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                if(newStartDate!=null) {
                    let lastestHistory = _.last(self.personCostList());
                    lastestHistory.endDate(self.getOneDayBefore(newStartDate));
                    self.gridPersonCostList.replace(_.last(self.gridPersonCostList()),{dateRange: lastestHistory.startDate()+" ~ "+lastestHistory.endDate()});
                    let copyDataFlag: boolean = nts.uk.ui.windows.getShared('copyDataFlag'); 
                    if(!copyDataFlag) {
                        self.personCostList.push(new PersonCostCalculation('','','',0,newStartDate,"9999/12/31"));   
                    } else {
                        self.personCostList.push(new PersonCostCalculation(
                            self.currentPersonCost().companyID(),
                            self.currentPersonCost().historyID(),
                            self.currentPersonCost().memo(),
                            self.currentPersonCost().unitPrice(),
                            newStartDate,
                            "9999/12/31"));    
                    }        
                    self.gridPersonCostList.push({dateRange: newStartDate+" ~ 9999/12/31"});
                    self.currentGridPersonCost(_.last(self.gridPersonCostList()).dateRange);       
                }
            });
        }

        editDialog() {
            var self = this;
            let personCostCalc: PersonCostCalculationInterface = self.getPersonCostCalculationInfo();
            nts.uk.ui.windows.setShared('lastestStartDate', _.last(self.personCostList()).startDate());
            nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function() {
                let isUpdate: boolean = nts.uk.ui.windows.getShared('isUpdate');
                if(isUpdate==true) {
                    let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                    let lastIndex = _.findLastIndex(self.personCostList());
                    self.personCostList()[lastIndex-1].endDate(self.getOneDayBefore(newStartDate));
                    _.last(self.personCostList()).startDate(newStartDate);
                    self.gridPersonCostList.replace(self.gridPersonCostList()[lastIndex-1],{dateRange: self.personCostList()[lastIndex-1].startDate()+" ~ "+self.personCostList()[lastIndex-1].endDate()});
                    self.gridPersonCostList.replace(_.last(self.gridPersonCostList()),{dateRange: _.last(self.personCostList()).startDate()+" ~ "+_.last(self.personCostList()).endDate()});
                    
                    self.currentGridPersonCost(_.last(self.gridPersonCostList()).dateRange);
                } else if(isUpdate==false) {
                    self.personCostList.remove(_.last(self.personCostList()));
                    let lastestHistory = _.last(self.personCostList());
                    lastestHistory.endDate("9999/12/31");
                    self.gridPersonCostList.remove(_.last(self.gridPersonCostList()));
                    self.gridPersonCostList.replace(_.last(self.gridPersonCostList()),{dateRange: lastestHistory.startDate()+" ~ "+lastestHistory.endDate()});
                    self.currentGridPersonCost(_.last(self.gridPersonCostList()).dateRange);
                }        
            });;
        }
        
        getPersonCostCalculationInfo(startDate: string): PersonCostCalculationInterface{
            var self = this;
            return _.find(self.personCostList(), function(o) { return o.startDate() == startDate; });
        }
        
        getOneDayBefore(date: string){
            let numberDate = Date.parse(date);    
            let dayBefore = new Date(numberDate-24*60*60*1000);
            return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
        }
    }
    
    class GridPersonCostCalculation {
        historyID: string;
        dateRange: string;  
        constructor(historyID: string, dateRange: string) {
            var self = this;
            self.historyID = historyID;
            self.dateRange = dateRange;
        }  
    }

    interface PersonCostCalculationInterface {
        companyID: string;
        historyID: string;
        memo: string;
        unitPrice: number;
        startDate: string;
        endDate: string;
    }

    class PersonCostCalculation {
        companyID: KnockoutObservable<string>;
        historyID: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        unitPrice: KnockoutObservable<number>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        constructor(companyID: string, historyID: string, memo: string, unitPrice: number, startDate: string, endDate: string) {
            var self = this;
            self.companyID = ko.observable(companyID);
            self.historyID = ko.observable(historyID);
            self.memo = ko.observable(memo);
            self.unitPrice = ko.observable(unitPrice);
            self.startDate = ko.observable(startDate);
            self.endDate = ko.observable(endDate);
        }
        static converToObject(object: PersonCostCalculationInterface): PersonCostCalculation {
            return new PersonCostCalculation(
                object.companyID, 
                object.historyID, 
                object.memo, 
                object.unitPrice, 
                object.startDate, 
                object.endDate);
        }
    }
}