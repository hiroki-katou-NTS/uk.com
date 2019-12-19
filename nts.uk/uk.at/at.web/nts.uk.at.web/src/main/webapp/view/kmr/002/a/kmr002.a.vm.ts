module nts.uk.at.view.kmr002.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import error = nts.uk.ui.dialog.error;
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
        lunch: KnockoutObservable<string> = ko.observable('');
        dinner: KnockoutObservable<string> = ko.observable('');
        sum: KnockoutObservable<string> = ko.observable('');
        lunchCount: KnockoutObservable<number> = ko.observable(0);
        dinnerCount: KnockoutObservable<number> = ko.observable(0);
        sumCount: KnockoutObservable<number> = ko.observable(0);
        priceLunch: KnockoutObservable<number> = ko.observable(0);
        priceDinner: KnockoutObservable<number> = ko.observable(0);
        priceSum: KnockoutObservable<number> = ko.observable(0);
        txtPriceLunch: KnockoutObservable<string> = ko.observable('');
        txtPriceDinner: KnockoutObservable<string> = ko.observable('');
        txtPriceSum: KnockoutObservable<string> = ko.observable('');
        mealSelected: KnockoutObservable<number> = ko.observable(1);
        menuLunch: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        menuDinner: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        optionMenu: KnockoutObservableArray<any> = ko.observableArray([]);
        startTime: KnockoutObservable<string> = ko.observable();
        endTime: KnockoutObservable<string> = ko.observable();
        amount: KnockoutObservable<number> = ko.observable(1);
        listBentoOrderLunch: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        listBentoOrderDinner: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        isError: KnockoutObservable<boolean> = ko.observable(false);
        lunchText: KnockoutObservable<string> = ko.observable('');
        dinnerText: KnockoutObservable<string> = ko.observable('');
        isEnable: KnockoutObservable<boolean> = ko.observable(true);
        isEnableLunch: KnockoutObservable<boolean> = ko.observable(true);
        isEnableDinner: KnockoutObservable<boolean> = ko.observable(true);
        start: KnockoutObservable<number> = ko.observable(0);
        end: KnockoutObservable<number> = ko.observable(0);
        isVisible: KnockoutObservable<boolean> = ko.observable(false);
        isVisibleDinner: KnockoutObservable<boolean> = ko.observable(false);
        isVisibleLunch: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            self.date.subscribe(() => {
                if (self.date()) {
                    service.startScreen({
                        date: moment(self.date()).format("YYYY/MM/DD"),
                        closingTimeFrame: self.mealSelected()
                    }).done((data) => {
                        self.clearData();
                        self.initData(data);
                    });
                }
            });
            dfd.resolve();
            return dfd.promise();
        }

        public clearData(): void {
            let self = this;
            self.menuLunch().clear();
            self.menuLunch.valueHasMutated();
            self.menuDinner().clear();
            self.menuDinner.valueHasMutated();
            self.listBentoOrderLunch().clear();
            self.listBentoOrderLunch.valueHasMutated();
            self.listBentoOrderDinner().clear();
            self.listBentoOrderDinner.valueHasMutated();
            self.lunch('');
            self.dinner('');
            self.sum('');
            self.txtPriceLunch('');
            self.txtPriceDinner('');
            self.txtPriceSum('');
        }

        public initData(data: any): void {
            let self = this;
            self.start(data.bentoMenuByClosingTimeDto.closingTime1.start);
            self.end(data.bentoMenuByClosingTimeDto.closingTime1.finish);
            self.lunchText(data.bentoMenuByClosingTimeDto.closingTime1.reservationTimeName);
            self.dinnerText(data.bentoMenuByClosingTimeDto.closingTime2.reservationTimeName);
            self.optionMenu.push({ code: 1, name: self.lunchText() });
            self.optionMenu.push({ code: 2, name: self.dinnerText() });
            self.optionMenu.valueHasMutated();
            if (data.listOrder.length > 0) {
                self.isUpdate(true);
            } else {
                self.isUpdate(false);
            }
            self.initTime(data, 0);
            if (self.mealSelected() == 1 && data.bentoMenuByClosingTimeDto.menu1.length > 0) {
                _.forEach(data.bentoMenuByClosingTimeDto.menu1, (item) => {
                    let self = this, unit = item.amount1 + item.unit, status = false;
                    status = self.checkLengthOrder(self.setIndex(data, 1), data, item);
                    self.menuLunch().push(new BentoMenu({ frameNo: item.frameNo, name: item.name, price: item.amount1, status: status, unit: unit }));
                });
                self.menuLunch.valueHasMutated();
            }
            if (self.mealSelected() == 2 && data.bentoMenuByClosingTimeDto.menu2.length > 0) {
                _.forEach(data.bentoMenuByClosingTimeDto.menu2, (item) => {
                    let self = this, unit = item.amount1 + item.unit, status = false;
                    status = self.checkLengthOrder(self.setIndex(data, 2), data, item);
                    self.menuDinner().push(new BentoMenu({ frameNo: item.frameNo, name: item.name, price: item.amount1, status: status, unit: unit }));
                });
                self.menuDinner.valueHasMutated();
            } else if (self.mealSelected() == 2 && data.bentoMenuByClosingTimeDto.menu2.length == 0) {
                error({ messageId: "Msg_1589" });
            }

            if (self.mealSelected() == 1 && data.bentoMenuByClosingTimeDto.menu1.length == 0) {
                error({ messageId: "Msg_1589" });
            }
            self.menuLunch.valueHasMutated();

            self.mealSelected.subscribe(() => {
                if (self.mealSelected() == 1) {
                    self.start(data.bentoMenuByClosingTimeDto.closingTime1.start);
                    self.end(data.bentoMenuByClosingTimeDto.closingTime1.finish);
                    self.initTime(data, self.setIndex(data, 1));

                } else {
                    self.start(data.bentoMenuByClosingTimeDto.closingTime2.start);
                    self.end(data.bentoMenuByClosingTimeDto.closingTime2.finish);
                    self.initTime(data, self.setIndex(data, 2));
                }
            });

            if (data.listOrder.length <= 1) {
                self.isVisibleLunch(false);
                self.isVisibleDinner(false);
            }

            for (let i = 0; i < data.listOrder.length; i++) {
                if (data.listOrder[i].reservationClosingTimeFrame == 1) {
                    let name = '', price = 0, unit = '', listOrderLunch = data.listOrder[i].listBentoReservationDetail;
                    if (listOrderLunch.length == 0) {
                        self.isVisibleLunch(false);
                    } else {
                        self.isVisibleLunch(true);
                        _.forEach(listOrderLunch, (item) => {
                            _.each(self.menuLunch(), (benTo) => {
                                if (benTo.frameNo() == item.frameNo) {
                                    name = benTo.name();
                                    price = benTo.price();
                                    unit = benTo.unit();
                                }
                            });
                            self.listBentoOrderLunch().push(new BentoOrder({ frameNo: item.frameNo, bentoCount: item.bentoCount, name: name, price: price, unit: unit }));
                        });
                        self.listBentoOrderLunch.valueHasMutated();
                    }
                }

                if (data.listOrder[i].reservationClosingTimeFrame == 2) {
                    let name = '', price = 0, unit = '', listOrderDinner = data.listOrder[i].listBentoReservationDetail;
                    if (listOrderDinner.length == 0) {
                        self.isVisibleDinner(false);
                    } else {
                        self.isVisibleDinner(true);
                        _.forEach(listOrderDinner, (item) => {
                            _.each(self.menuDinner(), (benTo) => {
                                if (benTo.frameNo() == item.frameNo) {
                                    name = benTo.name();
                                    price = benTo.price();
                                    unit = benTo.unit();
                                }
                            });
                            self.listBentoOrderDinner().push(new BentoOrder({ frameNo: item.frameNo, bentoCount: item.bentoCount, name: name, price: price, unit: unit }));
                        });
                        self.listBentoOrderDinner.valueHasMutated();
                    }
                }
            }
            if (!self.isVisibleLunch() && !self.isVisibleDinner()) {
                self.isVisible(false);
            } else {
                self.isVisible(true);
            }
            self.caculatorLunch();
            self.caculatorDinner();

            self.listBentoOrderLunch.subscribe(() => {
                self.caculatorLunch();
            });
            self.listBentoOrderDinner.subscribe(() => {
                self.caculatorDinner();
            });
        }

        public setIndex(data: any, value: number): number {
            let index;
            if (data.listOrder.length > 0 && data.listOrder[0].reservationClosingTimeFrame == value) {
                index = 0;
            }
            if (data.listOrder.length > 1 && data.listOrder[1].reservationClosingTimeFrame == value) {
                index = 1;
            }
            return index;
        }

        public checkLengthOrder(index: number, data: any, item: any): boolean {
            let check = false, listBento = [];
            if (data.listOrder.length > index) {
                let i = 0;
                listBento = data.listOrder[index].listBentoReservationDetail;
                while (i < listBento.length && check == false) {
                    if (listBento[i].frameNo == item.frameNo) {
                        check = true;
                    }
                    i++;
                };
                return check;
            }
        }

        public caculatorLunch(): void {
            let self = this;
            self.priceLunch(0);
            self.priceSum(0);
            self.lunchCount(0);
            self.sumCount(0);
            _.forEach(self.listBentoOrderLunch(), (item) => {
                self.priceLunch(self.priceLunch() + item.price() * item.bentoCount());
                self.priceSum(self.priceLunch() + self.priceDinner());
                self.lunchCount(self.lunchCount() + item.bentoCount());
                self.sumCount(self.lunchCount() + self.dinnerCount());
                self.txtPriceLunch(getText('KMR002_11', [self.priceLunch()]));
                self.lunch(getText('KMR002_10', [self.lunchText(), self.lunchCount()]));
                console.log(self.lunch());
                self.sum(getText('KMR002_12', [self.sumCount()]));
                self.txtPriceSum(getText('KMR002_11', [self.priceSum()]));
            });
        }

        public caculatorDinner(): void {
            let self = this;
            self.priceDinner(0);
            self.priceSum(0);
            self.dinnerCount(0);
            self.sumCount(0);
            _.forEach(self.listBentoOrderDinner(), (item) => {
                self.priceDinner(self.priceDinner() + item.price() * item.bentoCount());
                self.priceSum(self.priceLunch() + self.priceDinner());
                self.dinnerCount(self.dinnerCount() + item.bentoCount());
                self.sumCount(self.lunchCount() + self.dinnerCount());
                self.txtPriceDinner(getText('KMR002_11', [self.priceDinner()]));
                self.txtPriceSum(getText('KMR002_11', [self.priceSum()]));
                self.dinner(getText('KMR002_10', [self.dinnerText(), self.dinnerCount()]));
                self.sum(getText('KMR002_12', [self.sumCount()]));
            });
        }

        public initTime(data: any, index: number) {
            let self = this, dateSelect = moment(self.date()).format("YYYY/MM/DD"), dateNow = moment(new Date()).format("YYYY/MM/DD"), timeNow = (new Date()).getHours() * 60 + (new Date()).getMinutes();
            if (self.start() >= 0) {
                let startHour = _.floor(self.start() / 60), startMi = self.start() % 60;
                self.startTime(((startHour >= 10) ? startHour : ('0' + startHour)) + ':' + ((startMi >= 10) ? startMi : ('0' + startMi)));
            } else {
                self.startTime('');
            }
            if (self.end() >= 0) {
                let endHour = _.floor(self.end() / 60), endMi = self.end() % 60;
                self.endTime(((endHour >= 10) ? endHour : ('0' + endHour)) + ':' + ((endMi >= 10) ? endMi : ('0' + endMi)));
            } else {
                self.endTime('');
            }
            if (dateSelect < dateNow || (dateSelect == dateNow && self.end() < timeNow) || (data.listOrder.length > index && data.listOrder[index].ordered)) {
                self.isError(true);
                if (self.mealSelected() == 1) {
                    self.isEnableLunch(false);
                } else {
                    self.isEnableDinner(false);
                }
            } else {
                self.isError(false);
                if (self.mealSelected() == 1) {
                    self.isEnableLunch(true);
                } else {
                    self.isEnableDinner(true);
                }
            }
            self.startTime.valueHasMutated();
            self.endTime.valueHasMutated();
        }

        public updateOrderLunch(frameNo: number): void {
            let self = this;
            self.lunchCount(self.lunchCount() + 1);
            self.sumCount(self.sumCount() + 1);
            _.each(self.menuLunch(), (item) => {
                if (item.frameNo == frameNo && !item.status()) {
                    item.status(true);
                    self.listBentoOrderLunch().push(new BentoOrder({ frameNo: item.frameNo(), bentoCount: 1, name: item.name(), price: item.price(), unit: item.unit() }));
                }
            });
            self.listBentoOrderLunch.valueHasMutated();
        }

        public updateOrderDinner(frameNo: number): void {
            let self = this;
            self.dinnerCount(self.dinnerCount() + 1);
            self.sumCount(self.sumCount() + 1);
            _.each(self.menuDinner(), (item) => {
                if (item.frameNo == frameNo && !item.status()) {
                    item.status(true);
                    self.lunch();
                    self.listBentoOrderDinner().push(new BentoOrder({ frameNo: item.frameNo(), bentoCount: 1, name: item.name(), price: item.price(), unit: item.unit() }));
                }
            });
            self.listBentoOrderDinner.valueHasMutated();
        }

        public updateCountOrderLunch(frameNo: number, count: number): void {
            let self = this;
            _.each(self.listBentoOrderLunch(), (item) => {
                let bentoCount = item.bentoCount() + count;
                if (item.frameNo == frameNo && !item.status && bentoCount > 0) {
                    item.bentoCount(bentoCount);
                }
            });
            self.listBentoOrderLunch.valueHasMutated();
        }

        public updateCountOrderDinner(frameNo: number, count: number): void {
            let self = this;
            _.each(self.listBentoOrderDinner(), (item) => {
                let bentoCount = item.bentoCount() + count;
                if (item.frameNo == frameNo && !item.status && bentoCount > 0) {
                    item.bentoCount(bentoCount);
                }
            });
            self.listBentoOrderDinner.valueHasMutated();
        }

        public cancelLunch(frameNo: number): void {
            let self = this;
            _.each(self.menuLunch(), (item) => {
                if (item.frameNo() == frameNo()) {
                    item.status(false);
                }
            });
            self.menuLunch.valueHasMutated();
            if (self.listBentoOrderLunch().length > 0) {
                _.each(self.listBentoOrderLunch(), (item) => {
                    if (item.frameNo == frameNo) {
                        self.listBentoOrderLunch.remove(item);
                    }
                });
            }

        }

        public cancelDinner(frameNo: number): void {
            let self = this;
            _.each(self.menuDinner(), (item) => {
                if (item.frameNo() == frameNo()) {
                    item.status(false);
                }
            });
            self.menuLunch.valueHasMutated();
            if (self.listBentoOrderDinner().length > 0) {
                _.each(self.listBentoOrderDinner(), (item) => {
                    if (item.frameNo == frameNo) {
                        self.listBentoOrderDinner.remove(item);
                    }
                });
            }

        }

        public registerFood(): void {
            let self = this, dateSelect = moment(self.date()).format("YYYY/MM/DD"),
                dateNow = moment(new Date()).format("YYYY/MM/DD"), timeNow = (new Date()).getHours() * 60 + (new Date()).getMinutes();
            if (dateNow > dateSelect) {
                error({ messageId: "Msg_1584" });
            } else if (timeNow < self.start() && timeNow > self.end()) {
                error({ messageId: "Msg_1585" });
            } else {
                self.register();
            }

        }

        public register(): void {
            let self = this, detailLst = [];
            _.forEach(self.listBentoOrderLunch(), (item) => {
                detailLst.push({ closingTimeFrame: 1, frameNo: item.frameNo(), bentoCount: item.bentoCount() });
            });
            _.forEach(self.listBentoOrderDinner(), (item) => {
                detailLst.push({ closingTimeFrame: 2, frameNo: item.frameNo(), bentoCount: item.bentoCount() });
            });
            if (detailLst.length == 0) {
                error({ messageId: "Msg_1589" });
            } else {
                let bentoReservation = { date: self.date(), details: detailLst };
                if (self.isUpdate()) {
                    service.update(bentoReservation).done((data) => {
                        if (detailLst.length == 0) {
                            self.isUpdate(false);
                        }
                    });
                } else {
                    service.register(bentoReservation).done((data) => {
                        self.isUpdate(true);
                    });
                }
            }
        }

        public outputData(): void {
            let self = this;

        }

    }


    export interface IBentoOrder {
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

