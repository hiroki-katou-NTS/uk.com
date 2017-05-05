module kml001.a.viewmodel {
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        gridPersonCostList: KnockoutObservableArray<GridPersonCostCalculation>;
        currentGridPersonCost: KnockoutObservable<GridPersonCostCalculation>;
        personCostList: KnockoutObservableArray<PersonCostCalculation>;
        currentPersonCost: KnockoutObservable<PersonCostCalculation>;
        extraTimeItemList: KnockoutObservableArray<ExtraTimeItem>;
        premiumSettingList: KnockoutObservableArray<PremiumSetting>;
        constructor() {
            var self = this;
            self.personCostList = ko.observableArray([
//                new PersonCostCalculation('','','0',0,"2014/3/3","2015/3/2"),
//                new PersonCostCalculation('','','1',1,"2015/3/3","2016/3/2"),
//                new PersonCostCalculation('','','2',2,"2016/3/3","2017/3/2"),
//                new PersonCostCalculation('','','3',3,"2017/3/3","2018/3/2"),
//                new PersonCostCalculation('','','4',4,"2018/3/3","9999/12/31")
            ]);
            self.currentPersonCost = ko.observable((_.first(self.personCostList())==null)?new PersonCostCalculation('','','',0,"",""):_.first(self.personCostList()));
            self.gridPersonCostList = ko.observableArray([]);
            self.personCostList().forEach(function(item) { self.gridPersonCostList.push({dateRange: item.startDate()+" ~ "+item.endDate()})});
            self.currentGridPersonCost = ko.observable(self.currentPersonCost().startDate()+" ~ "+self.currentPersonCost().endDate()); 
            self.currentGridPersonCost.subscribe(function(value){
                self.currentPersonCost(self.getPersonCostCalculationInfo(_.split(value, ' ', 1)[0]));
            });
            self.extraTimeItemList = ko.observableArray([
                new ExtraTimeItem('','1','Item1','0001',1),
                new ExtraTimeItem('','2','Item2','0002',1),
                new ExtraTimeItem('','3','Item3','0003',1),
                new ExtraTimeItem('','4','Item4','0004',0),
                new ExtraTimeItem('','5','Item5','0005',1),
                new ExtraTimeItem('','6','Item6','0006',1),
                new ExtraTimeItem('','7','Item7','0007',0),
                new ExtraTimeItem('','8','Item8','0008',0),
                new ExtraTimeItem('','9','Item9','0009',1),
                new ExtraTimeItem('','10','Item10','0010',0)
            ]);
            self.premiumSettingList = ko.observableArray([
                new PremiumSetting('1',15,['01','02','03']),
                new PremiumSetting('2',15,['11','12','13']),
                new PremiumSetting('3',15,['21','22','23']),
                new PremiumSetting('4',15,['31','32','33']),
                new PremiumSetting('5',15,['41','42','43']),
                new PremiumSetting('6',15,['51','52','53']),
                new PremiumSetting('7',15,['61','62','63']),
                new PremiumSetting('8',15,['71','72','73']),
                new PremiumSetting('9',15,['81','82','83']),
                new PremiumSetting('10',15,['91','92','93'])
            ]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            return dfd.promise();
        }
        
        /**
         * 
         */
        premiumDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('extraTimeItemList', self.extraTimeItemList());
            nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                        
            });
        }
        
        /**
         * 
         */
        createDialog() {
            var self = this;
            let lastestHistory = _.last(self.personCostList());
            nts.uk.ui.windows.setShared('lastestStartDate', lastestHistory==null?"1900/1/1":lastestHistory.startDate());
            nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function() {
                let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                if(newStartDate!=null) {
                    if(lastestHistory!=null) {
                        lastestHistory.endDate(vmbase.DateTimeProcess.getOneDayBefore(newStartDate));
                        self.gridPersonCostList.replace(_.last(self.gridPersonCostList()),{dateRange: lastestHistory.startDate()+" ~ "+lastestHistory.endDate()});
                    }
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
        
        /**
         * 
         */
        editDialog() {
            var self = this;
            let beforeIndex = _.findIndex(self.personCostList(), function(o) { return o.startDate() == self.currentPersonCost().startDate(); })-1;
            let size = _.size(self.personCostList());
            nts.uk.ui.windows.setShared('size', size);
            nts.uk.ui.windows.setShared('beforeStartDate', (beforeIndex>=0)?self.personCostList()[beforeIndex].startDate():"1900/1/1");
            nts.uk.ui.windows.setShared('currentEndDate', (size>0)?self.currentPersonCost().endDate():"9999/12/31");
            nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function() {
                let isUpdate: boolean = nts.uk.ui.windows.getShared('isUpdate');
                if(isUpdate==true) {
                    let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                    self.personCostList()[beforeIndex+1].startDate(newStartDate);
                    if(beforeIndex >= 0) self.personCostList()[beforeIndex].endDate(vmbase.DateTimeProcess.getOneDayBefore(newStartDate));
                    self.gridPersonCostList.replace(self.gridPersonCostList()[beforeIndex+1],{dateRange: self.personCostList()[beforeIndex+1].startDate()+" ~ "+self.personCostList()[beforeIndex+1].endDate()});
                    if(beforeIndex >= 0) self.gridPersonCostList.replace(self.gridPersonCostList()[beforeIndex],{dateRange: self.personCostList()[beforeIndex].startDate()+" ~ "+self.personCostList()[beforeIndex].endDate()});
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
    
    class ExtraTimeItem {
        companyID: KnockoutObservable<string>;
        extraItemID: KnockoutObservable<string>; 
        name: KnockoutObservable<string>;
        timeItemID: KnockoutObservable<string>;
        useClassification: KnockoutObservable<number>;
        constructor(companyID: string, extraItemID: string, name: string, timeItemID: string, useClassification: number) {
            var self = this;
            self.extraItemID = ko.observable(extraItemID);
            self.companyID = ko.observable(companyID);
            self.useClassification = ko.observable(useClassification);
            self.timeItemID = ko.observable(timeItemID);
            self.name = ko.observable(name);
        }
    }
    
    class PremiumSetting {
        attendanceID: KnockoutObservable<string>;
        premiumRate: KnockoutObservable<number>;
        timeItemID: KnockoutObservableArray<string>;
        constructor(attendanceID: string, premiumRate: number, timeItemID: Array<string>) {
            var self = this;
            self.attendanceID = ko.observable(attendanceID);
            self.premiumRate = ko.observable(premiumRate);
            self.timeItemID = ko.observable(timeItemID);
        }
    }
}