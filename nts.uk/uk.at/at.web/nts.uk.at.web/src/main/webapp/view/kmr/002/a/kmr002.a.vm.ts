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
        date: KnockoutObservable<string> = ko.observable(new Date());
        yearMonth: KnockoutObservable<number> = ko.observable(200002);
        lunch: KnockoutObservable<string> =  ko.observable('昼');
        dinner: KnockoutObservable<string> = ko.observable('夜');
        sum: KnockoutObservable<string> = ko.observable('合計');
        priceLunch: KnockoutObservable<number> =  ko.observable(0);
        priceDinner: KnockoutObservable<number> = ko.observable(0);
        priceSum: KnockoutObservable<number> = ko.observable(0);
        mealSelected: KnockoutObservable<number> = ko.observable(1);
        menuLunch: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        menuDinner: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        isUpdate: KnockoutObservable<boolean> = ko.observable(false);
        optionMenu: KnockoutObservableArray<any> = ko.observableArray([]);
        startTime: KnockoutObservable<string> = ko.observable();
        endTime: KnockoutObservable<string> = ko.observable();
        amount: KnockoutObservable<number> = ko.observable(1);
        menuOrderLunchs: KnockoutObservableArray<any> = ko.observableArray([]);
        menuOrderDinners: KnockoutObservableArray<any> = ko.observableArray([]);
        listBentoOrderLunch: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        listBentoOrderDinner: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        isError: KnockoutObservable<boolean> = ko.observable(false);
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
            
            self.menuOrderLunchs = ko.observableArray([{ code: 10, name: 'ハヤシライス', price: 1000, numberOrder:  ko.observable(1),status: ko.observable(true) },
                         { code: 11, name: 'ハヤシライス', price: 2000, numberOrder:  ko.observable(2),status: ko.observable(true) },
                         { code: 21, name: 'ハヤシライス', price: 3000, numberOrder:  ko.observable(5),status: ko.observable(true) }]);
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            console.log(self.date() + self.mealSelected() + 'a');
            service.startScreen({
                date: moment(new Date()).format("YYYY/MM/DD"), 
                closingTimeFrame: self.mealSelected()}).done((data) => {
                self.initData(data);
            });
            dfd.resolve();
            return dfd.promise();
        }

        public initData(data: any) {
            let self = this;
            self.optionMenu.push({ code: 1, name: data.bentoMenuByClosingTimeDto.closingTime1.reservationTimeName });
            self.optionMenu.push({ code: 2, name: data.bentoMenuByClosingTimeDto.closingTime2.reservationTimeName }); 
            self.optionMenu.valueHasMutated();
            if (self.mealSelected() == 1) {
                self.startTime(data.bentoMenuByClosingTimeDto.closingTime1.start);
                self.endTime(data.bentoMenuByClosingTimeDto.closingTime1.finish);
                if (data.listOder.length > 0 && (data.listOrder[0].ordered || self.date() < new Date())) {
                    self.isError(true);
                } else {
                    self.isError(false);
                }
             } else {
                self.startTime(data.bentoMenuByClosingTimeDto.closingTime2.start);
                self.endTime(data.bentoMenuByClosingTimeDto.closingTime2.finish);
                if (data.listOder.length > 1 && (data.listOrder[1].ordered || self.date() < new Date())) {
                    self.isError(true);
                } else {
                    self.isError(false);
                }
            }
            
            self.startTime.valueHasMutated();
            self.endTime.valueHasMutated();
            _.forEach(data.bentoMenuByClosingTimeDto.menu1, (item) => {
                self.menuLunch().push(new BentoMenu({frameNo: item.frameNo, name: item.name, price: item.amount1, status: false}));
            });
            console.log(self.menuLunch());
            self.menuLunch.valueHasMutated();
        
            _.forEach(data.bentoMenuByClosingTimeDto.menu2, (item) => {
                 self.menuDinner().push(new BentoMenu({frameNo: item.frameNo, name: item.name, price: item.amount1, status: false}));
            });
            self.menuDinner.valueHasMutated();
            
            for(let i = 0; i < data.listOder.length; i++ ){
                if(data.listOder[i].reservationClosingTimeFrame == 1){
                    let name = '', price = 0;
                    _.forEach(data.listOder[i].listBentoReservationDetail, (item) => {
                        _.each(self.menuLunch(), (benTo) => {
                            if(benTo.frameNo == item.frameNo) {
                                name = benTo.name;
                                price = benTo.amount1;
                            }    
                        });
                        self.listBentoOrderLunch().push(new BentoOrder({frameNo: item.frameNo, bentoCount : item.bentoCount, name: name, price: price}));
                    });
                    self.listBentoOrderLunch.valueHasMutated();
                }
                
                if (data.listOder[i].reservationClosingTimeFrame == 2) {
                    let name = '', price = 0;
                    _.forEach(data.listOder[i].listBentoReservationDetail, (item) => {
                         _.each(self.menuDinner(), (benTo) => {
                            if(benTo.frameNo == item.frameNo) {
                                name = benTo.name;
                                price = benTo.amount1;
                            }    
                        });
                        self.listBentoOrderDinner().push(new BentoOrder({frameNo: item.frameNo, bentoCount : item.bentoCount, name: name, price: price}));
                    });
                    self.listBentoOrderDinner.valueHasMutated();
                }
            }
            
            _.forEach(self.listBentoOrderLunch(), (item) => {
                 self.priceLunch += item.listBentoOrderLunch().price*item.listBentoOrderLunch().bentoCount;
            });
             _.forEach(self.listBentoOrderDinner(), (item) => {
                 self.priceLunch += item.listBentoOrderDinner().price*item.listBentoOrderDinner().bentoCount;
            });
        }
        
        public updateOrderLunch(frameNo: number) :void{
            let self = this;
            console.log(frameNo);
           _.each(self.menuLunch(), (item) => {
               console.log(item.frameNo);
            if(item.frameNo == frameNo && !item.status()){
                item.status(true);
                self.listBentoOrderLunch().push(new BentoOrder({frameNo: item.frameNo(), bentoCount : 1, name: item.name(), price: item.price()}));
                self.listBentoOrderLunch.valueHasMutated();
            }    
           });
        }
        
        public updateOrderDinner(code: number) :void{
            let self = this;
           _.each(self.menuDinner(), (item) => {
            if(item.code == code && item.status()){
                item.status(false);
                self.menuOrderDinners.push(item);
                self.menuOrderDinners.valueHasMutated();
            }    
           });
        }

//        public cancelLunch(code: number) :void {
//           let self = this;
//            _.each(self.menuLunch(), function(item){
//            if(item.code == code){
//                item.status(true);
//                self.menuOrderDinners.delete(item);
//                console.log(self.menuOrderDinners());
//            }
//          });
//        }
        
        public registerFood() :void {
            let self = this, detailLst = [];
             _.forEach(self.listBentoOrderLunch(), (item) => {
                detailLst.push({closingTimeFrame: 1, frameNo: item.frameNo(), bentoCount: item.bentoCount()});
            });
             _.forEach(self.listBentoOrderDinner(), (item) => {
                detailLst.push({closingTimeFrame: 2, frameNo: item.frameNo(), bentoCount: item.bentoCount()});
            });
            let  bentoReservation = { date: self.date(), details: detailLst };
            service.register(bentoReservation).done((data) => {
                 console.log(data);
            });
        }
        
        public outputData() :void {
            let self = this;

        }
        
//        public cancelDinner(code: number) :void {
//            let self = this; 
//            console.log('h');   
//        }

     }
    
    
    export interface IBentoOrder{
        bentoCount: number;
        frameNo: number;
        name: string;
        price: number;
    }
    
    export interface IBentoMenu {
        frameNo: number;
        name: string;
        price: number;
        status: boolean;
    }
    
    export class BentoOrder {
        bentoCount: KnockoutObservable<number>;
        frameNo: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        price: KnockoutObservable<number>;
        constructor(param: IBentoOrder) {
            let self = this;
            self.bentoCount = ko.observable(param.bentoCount);
            self.frameNo = ko.observable(param.frameNo);
            self.name = ko.observable(param.name);
            self.price = ko.observable(param.price);
        }
        
        addOrder(): void {
            let self = this, bentoCount_ = self.bentoCount() + 1;
            self.bentoCount(bentoCount_ );
        }
        
        preOrder(): void {
            let self = this, bentoCount_ = self.bentoCount() - 1;
            if (bentoCount_ > 1) {
                self.bentoCount(bentoCount_ );
            }
        }
        
        cancelOrder(): void {
            let self = this;
            
        }
    }
    
    export class BentoMenu {
        frameNo: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        price: KnockoutObservable<number>;
        status: KnockoutObservable<boolean>;
        constructor(param: IBentoMenu) {
            let self = this;
            self.frameNo = ko.observable(param.frameNo);
            self.name = ko.observable(param.name);
            self.price = ko.observable(param.price);
            self.status = ko.observable(param.status);
        }
        
        reservation(): void {
            let self = this;
       //     self.status(true);
            self.status(true);
        }
        
    }
}

