module nts.uk.at.view.kmr002.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import service = nts.uk.at.view.kmr002.a.service;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        date: KnockoutObservable<string> = ko.observable(moment(new Date()).format("YYYYMMDD"));
        yearMonth: KnockoutObservable<number> = ko.observable(200002);
        lunch: KnockoutObservable<string> =  ko.observable('昼');
        dinner: KnockoutObservable<string> = ko.observable('夜');
        sum: KnockoutObservable<string> = ko.observable('合計');
        priceLunch: KnockoutObservable<number> =  ko.observable(0);
        priceDinner: KnockoutObservable<number> = ko.observable(0);
        priceSum: KnockoutObservable<number> = ko.observable(0);
        mealSelected: KnockoutObservable<number> = ko.observable(0);
        menuLunch: KnockoutObservableArray<any> = ko.observableArray([]);
        menuDinner: KnockoutObservableArray<any> = ko.observableArray([]);
        isUpdate: KnockoutObservable<boolean> = ko.observable(false);
        optionMenu: KnockoutObservableArray<any> = ko.observableArray([]);
        startTime: KnockoutObservable<string> = ko.observable('6:00');
        endTime: KnockoutObservable<string> = ko.observable('10:00');
        amount: KnockoutObservable<number> = ko.observable(1);
        menuOrderLunchs: KnockoutObservableArray<any> = ko.observableArray([]);
        menuOrderDinners: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
            self.cssRangerYM = {
                2000: [{ 1: "round-green" }, { 5: "round-yellow" }],
                2002: [1, { 5: "round-gray" }]
            };
            self.cssRangerYMD = {
                2000: { 1: [{ 11: "round-green" }, { 12: "round-orange" }, { 15: "rect-pink" }], 3: [{ 1: "round-green" }, { 2: "round-purple" }, 3] },
                2002: { 1: [{ 11: "round-green" }, { 12: "round-green" }, { 15: "round-green" }], 3: [{ 1: "round-green" }, { 2: "round-green" }, { 3: "round-green" }] }
            }
            self.menuLunch = ko.observableArray([{ code: 10, name: 'ハヤシライス', price: 1000, numberOrder:  ko.observable(1),status: ko.observable(true) },
                         { code: 11, name: 'ハヤシライス', price: 2000, numberOrder:  ko.observable(2),status: ko.observable(true) },
                         { code: 21, name: 'ハヤシライス', price: 3000, numberOrder:  ko.observable(5),status: ko.observable(true) },
                         { code: 31, name: 'ハヤシライス', price: 4000, numberOrder: 1,status: ko.observable(true) },
                         { code: 41, name: 'ハヤシライス', price: 5000, numberOrder: 1,status: ko.observable(true) },
                         { code: 51, name: 'ハヤシライス', price: 6000, numberOrder: 1,status: ko.observable(true) },
                         { code: 52, name: 'ハヤシライス', price: 7000, numberOrder:  ko.observable(1),status: ko.observable(true) },
                         { code: 53, name: 'ハヤシライス', price: 8000, numberOrder:  ko.observable(2),status: ko.observable(true) },
                         { code: 54, name: 'ハヤシライス', price: 9000, numberOrder:  ko.observable(5),status: ko.observable(true) },
                         { code: 55, name: 'ハヤシライス', price: 10000, numberOrder: 1,status: ko.observable(true) },
                         { code: 56, name: 'ハヤシライス', price: 11000, numberOrder: 1,status: ko.observable(true) },
                         { code: 57, name: 'ハヤシライス', price: 12000, numberOrder: 1,status: ko.observable(true) },
                         { code: 58, name: 'ハヤシライス', price: 13000, numberOrder:  ko.observable(5),status: ko.observable(true) },
                         { code: 59, name: 'ハヤシライス', price: 14000, numberOrder: 1,status: ko.observable(true) },
                         { code: 60, name: 'ハヤシライス', price: 15000, numberOrder: 1,status: ko.observable(true) },
                         { code: 61, name: 'ハヤシライス', price: 16000, numberOrder: 1,status: ko.observable(true) },
                         { code: 62, name: 'ハヤシライス', price: 17000, numberOrder: 1,status: ko.observable(true) },
                         { code: 63, name: 'ハヤシライス', price: 18000, numberOrder:  ko.observable(5),status: ko.observable(true) },
                         { code: 64, name: 'ハヤシライス', price: 19000, numberOrder: 1,status: ko.observable(true) },
                         { code: 65, name: 'ハヤシライス', price: 20000, numberOrder: 1,status: ko.observable(true) },
                         { code: 66, name: 'ハヤシライス', price: 21000, numberOrder: 1,status: ko.observable(true) }
            ]);
            
            self.menuOrderLunchs = ko.observableArray([{ code: 10, name: 'ハヤシライス', price: 1000, numberOrder:  ko.observable(1),status: ko.observable(true) },
                         { code: 11, name: 'ハヤシライス', price: 2000, numberOrder:  ko.observable(2),status: ko.observable(true) },
                         { code: 21, name: 'ハヤシライス', price: 3000, numberOrder:  ko.observable(5),status: ko.observable(true) }]);
            self.menuDinner = ko.observableArray([{ code: 0, name: 'ハライス', price: 1000, numberOrder: ko.observable(1),status: ko.observable(true) },
                         { code: 1, name: 'ハヤシス', price: 2000, numberOrder:  ko.observable(2),status: ko.observable(true) },
                          { code: 2, name: 'ハヤシライス', price: 2000, numberOrder: ko.observable(5),status: ko.observable(true) },
                         { code: 3, name: 'ハヤシライス', price: 1000, numberOrder:  ko.observable(1),status: ko.observable(true) },
                         { code: 4, name: 'ハヤシライス', price: 2000, numberOrder: 1,status: ko.observable(true) },
                         { code: 5, name: 'ハヤシライス', price: 1000, numberOrder: 1,status: ko.observable(true) }
            ]);
            self.optionMenu = [
                                  { code: 0, name: '昼' },
                                  { code: 1, name: '夜' }
             ];
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            console.log(self.date() + self.mealSelected() + 'a');
            service.startScreen({
                date: self.date(), 
                closingTimeFrame: self.mealSelected()}).done((data) => {
                console.log('a');
            });
            dfd.resolve();
            return dfd.promise();
        }
        
        public updateLunch(code: number, numberOd: number) :void {
           let self = this;
           _.each(self.menuOrderLunchs(), function(item){
            if((item.code == code && numberOd != -1) || (item.code == code && numberOd == -1 && item.numberOrder() > 1)){
                item.numberOrder(item.numberOrder() + numberOd);
                 self.priceLunch( self.priceLunch() + numberOd*item.price);
                 self.priceSum(self.priceLunch() + self.priceDinner());
            }    
           });
        }
        
         public updateDinner(code: number, numberOd: number) :void {
           let self = this;
           _.each(self.menuOrderDinners(), function(item){
            if((item.code == code && numberOd != -1) || (item.code == code && numberOd == -1 && item.numberOrder() > 1)){
                item.numberOrder(item.numberOrder() + numberOd);
                self.priceDinner(self.priceDinner() + numberOd*item.price); 
                self.priceSum(self.priceLunch() + self.priceDinner());
            }    
           });
        }
        
        public updateOrderLunch(code: number) :void{
            let self = this;
           _.each(self.menuLunch(), function(item){
            if(item.code == code && item.status()){
                item.status(false);
                self.menuOrderLunchs().push(item);
                self.menuOrderLunchs.valueHasMutated();
            }    
           });
        }
        
        public updateOrderDinner(code: number) :void{
            let self = this;
           _.each(self.menuDinner(), function(item){
            if(item.code == code && item.status()){
                item.status(false);
                self.menuOrderDinners.push(item);
                self.menuOrderDinners.valueHasMutated();
            }    
           });
        }

        public cancelLunch(code: number) :void {
           let self = this;
            _.each(self.menuLunch(), function(item){
            if(item.code == code){
                item.status(true);
                self.menuOrderDinners.delete(item);
                console.log(self.menuOrderDinners());
            }
          });
        }
        
        public registerFood() :void {
            let self = this;
            
        }
        
        public outputData() :void {
            let self = this;

        }
        
        public cancelDinner(code: number) :void {
            let self = this; 
            console.log('h');   
//             _.each(self.menuDinner(), function(item){
//            if(item.code == code){
//                item.status(true);
//            }
//        }
    }

}

