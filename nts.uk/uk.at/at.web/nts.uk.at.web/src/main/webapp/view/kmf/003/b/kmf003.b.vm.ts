module nts.uk.at.view.kmf003.b.viewmodel {
    export class ScreenModel {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        conditionValue: KnockoutObservable<string>;
        dateSelected: KnockoutObservable<string>;
        standardDate: KnockoutObservable<string>;
        items: KnockoutObservableArray<Item>;
        baseDateOptions: KnockoutObservableArray<BaseDateOption>;
        payDayCalculate: KnockoutObservable<string>;
        displayDateSelected: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KMF003_CONDITION_NO");
            self.code = ko.observable(data.code);
            self.name = ko.observable(data.name);            
            self.conditionValue = ko.observable(nts.uk.resource.getText("KMF003_37", [data.conditionValue.option, data.conditionValue.value, data.conditionValue.afterValue]));
            self.dateSelected = ko.observable(data.dateSelected);
            
            if(data.dateSelected === "") {
                self.displayDateSelected = ko.observable(false);
            } else {
                self.displayDateSelected = ko.observable(true);
            }
            
            self.standardDate = ko.observable("");
            self.items = ko.observableArray([]);
            
            self.baseDateOptions = ko.observableArray([
                new BaseDateOption('1', '入社日'),
                new BaseDateOption('2', '年休付与基準日')
            ]);
            
            self.payDayCalculate = ko.observable("");
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            for(var i=0; i< 20; i++) {
                var item : IItem = {
                    year: i,
                    month: i,
                    restedDaysInYear: i,
                    numberOfDays: i,
                    limitedTimes: i,
                    baseDate: 1,
                    allowPay: false     
                };
                self.items.push(new Item(item));    
            }
            
            dfd.resolve();

            return dfd.promise();
        }
        
        calculate() {
            
        }
        
        submit() {
            
        }
        
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
    
    export class Item {
        year: KnockoutObservable<number>;
        month: KnockoutObservable<number>;
        restedDaysInYear: KnockoutObservable<number>;
        numberOfDays: KnockoutObservable<number>;
        limitedTimes: KnockoutObservable<number>;
        baseDate: KnockoutObservable<number>;
        allowPay: KnockoutObservable<boolean>;
        
        constructor(param: IItem) {
            var self = this;
            self.year = ko.observable(param.year);
            self.month = ko.observable(param.month);
            self.restedDaysInYear = ko.observable(param.restedDaysInYear);
            self.numberOfDays = ko.observable(param.numberOfDays);
            self.limitedTimes = ko.observable(param.limitedTimes);
            self.baseDate = ko.observable(param.baseDate);
            self.allowPay = ko.observable(param.allowPay);       
        }
    }
    
    export interface IItem {
        year: number;
        month: number;
        restedDaysInYear: number;
        numberOfDays: number;
        limitedTimes: number;
        baseDate: number;
        allowPay: boolean;        
    }
     
    export class BaseDateOption {
        code: string;
        name: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}