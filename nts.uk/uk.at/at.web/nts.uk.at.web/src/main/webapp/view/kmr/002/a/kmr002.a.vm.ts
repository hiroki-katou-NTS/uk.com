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
            
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            self.date.subscribe(() => {
                service.startScreen({
                    date: moment(self.date()).format("YYYY/MM/DD"), 
                    closingTimeFrame: self.mealSelected()}).done((data) => {
                     self.menuLunch().clear();
                     self.menuLunch.valueHasMutated();
                     self.menuDinner().clear();
                     self.menuDinner.valueHasMutated();
                     console.log(self.menuLunch().length + self.menuDinner().length);
                     self.initData(data);
                });
            });
            dfd.resolve();
            return dfd.promise();
        }

        public initData(data: any) {
            let self = this, start = data.bentoMenuByClosingTimeDto.closingTime1.start,
                end = data.bentoMenuByClosingTimeDto.closingTime1.finish;;
            self.optionMenu.push({ code: 1, name: data.bentoMenuByClosingTimeDto.closingTime1.reservationTimeName });
            self.optionMenu.push({ code: 2, name: data.bentoMenuByClosingTimeDto.closingTime2.reservationTimeName }); 
            self.optionMenu.valueHasMutated();
            self.initTime(start, end, data);
            self.mealSelected.subscribe(() => {
                if (self.mealSelected() == 1) {
                    start = data.bentoMenuByClosingTimeDto.closingTime1.start;
                    end = data.bentoMenuByClosingTimeDto.closingTime1.finish;
                    self.initTime(start, end, data);
                } else {
                    start = data.bentoMenuByClosingTimeDto.closingTime2.start;
                    end = data.bentoMenuByClosingTimeDto.closingTime2.finish;
                    self.initTime(start, end, data);
                }
            });
            _.forEach(data.bentoMenuByClosingTimeDto.menu1, (item) => {
                let unit = item.amount1 + item.unit;
                self.menuLunch().push(new BentoMenu({frameNo: item.frameNo, name: item.name, price: item.amount1, status: false, unit: unit}));
            });
            self.menuLunch.valueHasMutated();
        
            _.forEach(data.bentoMenuByClosingTimeDto.menu2, (item) => {
                let unit = item.amount1 + item.unit;
                 self.menuDinner().push(new BentoMenu({frameNo: item.frameNo, name: item.name, price: item.amount1, status: false, unit: unit}));
            });
            self.menuDinner.valueHasMutated();
            
            for(let i = 0; i < data.listOder.length; i++ ){
                if(data.listOder[i].reservationClosingTimeFrame == 1){
                    let name = '', price = 0, unit = '';
                    _.forEach(data.listOder[i].listBentoReservationDetail, (item) => {
                        _.each(self.menuLunch(), (benTo) => {
                            if(benTo.frameNo == item.frameNo) {
                                name = benTo.name;
                                price = benTo.amount1;
                                unit =  benTo.amount1 + benTo.unit;
                            }    
                        });
                        self.listBentoOrderLunch().push(new BentoOrder({frameNo: item.frameNo, bentoCount : item.bentoCount, name: name, price: price, unit: unit}));
                    });
                    self.listBentoOrderLunch.valueHasMutated();
                }
                
                if (data.listOder[i].reservationClosingTimeFrame == 2) {
                    let name = '', price = 0, unit = '';
                    _.forEach(data.listOder[i].listBentoReservationDetail, (item) => {
                         _.each(self.menuDinner(), (benTo) => {
                            if(benTo.frameNo == item.frameNo) {
                                name = benTo.name;
                                price = benTo.amount1;
                                unit =  benTo.amount1 + benTo.unit;
                            }    
                        });
                        self.listBentoOrderDinner().push(new BentoOrder({frameNo: item.frameNo, bentoCount : item.bentoCount, name: name, price: price, unit: unit}));
                    });
                    self.listBentoOrderDinner.valueHasMutated();
                }
            }

            self.listBentoOrderLunch.subscribe(() => {
                self.priceLunch(0);
                _.forEach(self.listBentoOrderLunch(), (item) => {
                    self.priceLunch(self.priceLunch() + item.price()*item.bentoCount());
                    self.priceSum(self.priceLunch() + self.priceDinner());
                });
            });
            self.listBentoOrderDinner.subscribe(() => {
                self.priceDinner(0);
                _.forEach(self.listBentoOrderDinner(), (item) => {
                    self.priceDinner(self.priceDinner() + item.price()*item.bentoCount());
                    self.priceSum(self.priceLunch() + self.priceDinner());
                });
            });
        }
        
        public initTime(start: number, end: number, data: any) {
            let self = this;
            self.startTime(start > 0 ? (_.floor(start/60) + ":" + (start%60)) : '');
            self.endTime(end > 0 ? (_.floor(end/60) + ":" + (end%60)) : '');
            if (data.listOder.length > 0 && (data.listOrder[0].ordered || self.date() < new Date())) {
                 self.isError(true);
            } else {
                 self.isError(false);
            }
            self.startTime.valueHasMutated();
            self.endTime.valueHasMutated();
        }
        
        public updateOrderLunch(frameNo: number) :void{
            let self = this;
           _.each(self.menuLunch(), (item) => {
            if(item.frameNo == frameNo && !item.status()){
                item.status(true);
                self.listBentoOrderLunch().push(new BentoOrder({frameNo: item.frameNo(), bentoCount : 1, name: item.name(), price: item.price(), unit: item.unit()}));
            }    
           });
            self.listBentoOrderLunch.valueHasMutated();
        }
        
        public updateOrderDinner(frameNo: number) :void{
            let self = this;
           _.each(self.menuDinner(), (item) => {
            if(item.frameNo == frameNo && !item.status()){
                item.status(true);
                self.listBentoOrderDinner().push(new BentoOrder({frameNo: item.frameNo(), bentoCount : 1, name: item.name(), price: item.price(), unit: item.unit()}));
            }    
           });
             self.listBentoOrderDinner.valueHasMutated();
        }
        
        public updateCountOrderLunch(frameNo: number, count: number) :void {
            let self = this;
            _.each(self.listBentoOrderLunch(), (item) => {
                let bentoCount = item.bentoCount() + count;
                if(item.frameNo == frameNo && !item.status && bentoCount > 0){
                    item.bentoCount(bentoCount);
                }
           });
            self.listBentoOrderLunch.valueHasMutated();
        }
        
        public updateCountOrderDinner(frameNo: number, count: number) :void {
            let self = this;
            _.each(self.listBentoOrderDinner(), (item) => {
                let bentoCount = item.bentoCount() + count;
                if(item.frameNo == frameNo && !item.status && bentoCount > 0){
                    item.bentoCount(bentoCount);
                }
           });
            self.listBentoOrderDinner.valueHasMutated();
        }

        public cancelLunch(frameNo: number) :void {
           let self = this;
            _.each(self.menuLunch(), (item) => {
                if(item.frameNo() == frameNo()) {
                    item.status(false);
                }    
            });
             self.menuLunch.valueHasMutated();
            if (self.listBentoOrderLunch().length > 0) {
                  _.each(self.listBentoOrderLunch(), (item) => {
                if(item.frameNo == frameNo){
                    self.listBentoOrderLunch.remove(item);
                }
            });
            }
   
        }
        
          public cancelDinner(frameNo: number) :void {
           let self = this;
            _.each(self.menuDinner(), (item) => {
                if(item.frameNo() == frameNo()) {
                    item.status(false);
                }    
            });
             self.menuLunch.valueHasMutated();
            if (self.listBentoOrderDinner().length > 0) {
                  _.each(self.listBentoOrderDinner(), (item) => {
                if(item.frameNo == frameNo){
                    self.listBentoOrderDinner.remove(item);
                }
            });
            }
   
        }
        
        public registerFood() :void {
            let self = this, detailLst = [];
             _.forEach(self.listBentoOrderLunch(), (item) => {
                detailLst.push({closingTimeFrame: 1, frameNo: item.frameNo(), bentoCount: item.bentoCount()});
            });
             _.forEach(self.listBentoOrderDinner(), (item) => {
                detailLst.push({closingTimeFrame: 2, frameNo: item.frameNo(), bentoCount: item.bentoCount()});
            });
            let  bentoReservation = { date: self.date(), details: detailLst };
            if (self.isUpdate()) {
                service.register(bentoReservation).done((data) => {
                 
                });
            } else {
                service.update(bentoReservation).done((data) => {
                 
                });
            }
        }
        
        public outputData() :void {
            let self = this;

        }

     }
    
    
    export interface IBentoOrder{
        bentoCount: number;
        frameNo: number;
        name: string;
        price: number;
        unit: string;
    }
    
    export interface IBentoMenu {
        frameNo: number;
        name: string;
        price: number;
        unit: string;
        status: boolean;
    }
    
    export class BentoOrder {
        bentoCount: KnockoutObservable<number>;
        frameNo: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        price: KnockoutObservable<number>;
        unit: KnockoutObservable<string>;
        constructor(param: IBentoOrder) {
            let self = this;
            self.bentoCount = ko.observable(param.bentoCount);
            self.frameNo = ko.observable(param.frameNo);
            self.name = ko.observable(param.name);
            self.price = ko.observable(param.price);
            self.unit = ko.observable(param.unit);
        }
    }
    
    export class BentoMenu {
        frameNo: KnockoutObservable<number>;
        name: KnockoutObservable<string>;
        price: KnockoutObservable<number>;
        status: KnockoutObservable<boolean>;
        unit: KnockoutObservable<string>;
        constructor(param: IBentoMenu) {
            let self = this;
            self.frameNo = ko.observable(param.frameNo);
            self.name = ko.observable(param.name);
            self.price = ko.observable(param.price);
            self.status = ko.observable(param.status);
            self.unit = ko.observable(param.unit);
        }
    }
}

