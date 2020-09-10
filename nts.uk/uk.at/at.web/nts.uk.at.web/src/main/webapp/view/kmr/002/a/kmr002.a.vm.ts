module nts.uk.at.view.kmr002.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import service = nts.uk.at.view.kmr002.a.service;
    export class ScreenModel {
        date: KnockoutObservable<string> = ko.observable('');
        lunch: KnockoutObservable<string> = ko.observable('');
        dinner: KnockoutObservable<string> = ko.observable('');
        sum: KnockoutObservable<string> = ko.observable('');
        lunchCount: KnockoutObservable<number> = ko.observable(0);
        dinnerCount: KnockoutObservable<number> = ko.observable(0);
        priceLunch: KnockoutObservable<number> = ko.observable(0);
        priceDinner: KnockoutObservable<number> = ko.observable(0);
        txtPriceLunch: KnockoutObservable<string> = ko.observable('');
        txtPriceDinner: KnockoutObservable<string> = ko.observable('');
        txtPriceSum: KnockoutObservable<string> = ko.observable('');
        mealSelected: KnockoutObservable<number> = ko.observable(1);
        menuLunch: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        menuDinner: KnockoutObservableArray<BentoMenu> = ko.observableArray([]);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        optionMenu: KnockoutObservableArray<any> = ko.observableArray([]);
        startTime: KnockoutObservable<any> = ko.observable();
        endTime: KnockoutObservable<any> = ko.observable();
        amount: KnockoutObservable<number> = ko.observable(1);
        listBentoOrderLunch: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        listBentoOrderDinner: KnockoutObservableArray<BentoOrder> = ko.observableArray([]);
        isError: KnockoutObservable<boolean> = ko.observable(false);
        lunchText: KnockoutObservable<string> = ko.observable('');
        dinnerText: KnockoutObservable<string> = ko.observable('');
        isEnable: KnockoutObservable<boolean> = ko.observable(true);
        isEnableLunch: KnockoutObservable<boolean> = ko.observable(true);
        isEnableDinner: KnockoutObservable<boolean> = ko.observable(true);
        isVisible: KnockoutObservable<boolean> = ko.observable(false);
        isVisibleDinner: KnockoutObservable<boolean> = ko.observable(false);
        isVisibleLunch: KnockoutObservable<boolean> = ko.observable(false);
        textError: KnockoutObservable<string> = ko.observable(getText('KMR002_6'));
        startLunch: KnockoutObservable<number> = ko.observable(0);
        finishLunch: KnockoutObservable<number> = ko.observable(0);
        startDinner: KnockoutObservable<number> = ko.observable(0);
        finishDinner: KnockoutObservable<number> = ko.observable(0);

        workLocationCode: KnockoutObservable<string> = ko.observable(null);

        constructor() {
            let self = this;
        }

        public startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred<any>(), oldValue;

            self.date.subscribe((value) => {
                
                let momentDate = moment(value);
                if (momentDate instanceof moment && !momentDate.isValid()) {
                    self.isEnable(false);
                    return;
                }
                nts.uk.ui.block.invisible();
                service.startScreen({

                    date: moment(self.date()).format("YYYY/MM/DD"),

                    closingTimeFrame: self.mealSelected()

                }).done((data) => {
                    nts.uk.ui.block.clear();

                    self.clearData();

                    self.initData(data);

                    nts.uk.ui.block.clear();
                }).fail(() => {

                    error({ messageId: "Msg_1604" }).then(() => {

                        uk.request.jumpToTopPage();

                    });

                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            });
            if (self.date() == '') {
                self.date((new Date()).toISOString().substr(0,10) + 'T00:00:00.000Z');
            }
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
            self.workLocationCode(data.workLocationCode);
            self.startLunch(data.bentoMenuByClosingTimeDto.closingTime1.start);
            self.finishLunch(data.bentoMenuByClosingTimeDto.closingTime1.finish);
            self.startDinner(data.bentoMenuByClosingTimeDto.closingTime2.start);
            self.finishDinner(data.bentoMenuByClosingTimeDto.closingTime2.finish);
            self.lunchText(data.bentoMenuByClosingTimeDto.closingTime1.reservationTimeName);
            self.dinnerText(data.bentoMenuByClosingTimeDto.closingTime2.reservationTimeName);
            self.optionMenu.push({ code: 1, name: self.lunchText() });
            self.optionMenu.push({ code: 2, name: self.dinnerText() });
            self.optionMenu.valueHasMutated();
            self.isUpdate(data.listOrder.length > 0 ? true : false);
            self.initTime(data, self.setIndex(data, 1));
            if (data.bentoMenuByClosingTimeDto.menu1.length > 0) {
                self.optionMenu().clear();
                self.optionMenu.push({ code: 1, name: self.lunchText() });
                _.forEach(data.bentoMenuByClosingTimeDto.menu1, (item) => {
                    let self = this, status = false;
                    status = self.checkLengthOrder(self.setIndex(data, 1), data, item);
                    self.menuLunch().push(new BentoMenu({ frameNo: item.frameNo, name: item.name, price: item.amount1, status: status, unit: getText('KMR002_11', [item.amount1]) }));
                });
                self.menuLunch.valueHasMutated();
            }
            if (data.bentoMenuByClosingTimeDto.menu2.length > 0) {
                self.optionMenu().clear();
                self.optionMenu.push({ code: 1, name: self.lunchText() });
                self.optionMenu.push({ code: 2, name: self.dinnerText() });
                _.forEach(data.bentoMenuByClosingTimeDto.menu2, (item) => {
                    let self = this, status = false;
                    status = self.checkLengthOrder(self.setIndex(data, 2), data, item);
                    self.menuDinner().push(new BentoMenu({ frameNo: item.frameNo, name: item.name, price: item.amount1, status: status, unit: getText('KMR002_11', [item.amount1]) }));
                });
                self.menuDinner.valueHasMutated();
            }
            if ((self.mealSelected() == 1 && data.bentoMenuByClosingTimeDto.menu1.length == 0)
                || (self.mealSelected() == 2 && data.bentoMenuByClosingTimeDto.menu2.length == 0)) {
                error({ messageId: "Msg_1604" });
            }

            self.mealSelected.subscribe((value) => {
                self.initTime(data, self.setIndex(data, value));
            });

            self.isVisibleLunch(false);
            self.isVisibleDinner(false);

            for (let i = 0; i < data.listOrder.length; i++) {
                if (data.listOrder[i].reservationClosingTimeFrame == 1) {
                    let name = '', price = 0, unit = '', listOrderLunch = data.listOrder[i].listBentoReservationDetail;
                    self.isVisibleLunch(listOrderLunch.length == 0 ? false : true);
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

                if (data.listOrder[i].reservationClosingTimeFrame == 2) {
                    let name = '', price = 0, unit = '', listOrderDinner = data.listOrder[i].listBentoReservationDetail;
                    self.isVisibleDinner(listOrderDinner.length == 0 ? false : true);
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
            self.lunchCount(0);
            self.txtPriceLunch(getText('KMR002_11', [0]));
            self.lunch(getText('KMR002_10', [self.lunchText(), 0]));
            if (self.priceLunch() == 0 && self.priceDinner() == 0) {
                self.caculatorSum(0, 0);
            } else if (self.listBentoOrderLunch().length == 0) {
                self.caculatorSum(self.priceDinner(), self.dinnerCount());
            }

            _.forEach(self.listBentoOrderLunch(), (item) => {
                self.priceLunch(self.priceLunch() + item.price() * item.bentoCount());
                self.lunchCount(self.lunchCount() + item.bentoCount());
                self.txtPriceLunch(getText('KMR002_11', [self.priceLunch()]));
                self.lunch(getText('KMR002_10', [self.lunchText(), self.lunchCount()]));
                self.caculatorSum(self.priceLunch() + self.priceDinner(), self.lunchCount() + self.dinnerCount());
            });
        }

        public caculatorDinner(): void {
            let self = this;
            self.priceDinner(0);
            self.dinnerCount(0);
            self.txtPriceDinner(getText('KMR002_11', [0]));
            self.dinner(getText('KMR002_10', [self.dinnerText(), 0]));
            if (self.priceLunch() == 0 && self.priceDinner() == 0) {
                self.sum('');
                self.caculatorSum(0, 0);
            } else if (self.listBentoOrderDinner().length == 0) {
                self.caculatorSum(self.priceLunch(), self.lunchCount());
            }

            _.forEach(self.listBentoOrderDinner(), (item) => {
                self.priceDinner(self.priceDinner() + item.price() * item.bentoCount());
                self.dinnerCount(self.dinnerCount() + item.bentoCount());
                self.txtPriceDinner(getText('KMR002_11', [self.priceDinner()]));
                self.dinner(getText('KMR002_10', [self.dinnerText(), self.dinnerCount()]));
                self.caculatorSum(self.priceLunch() + self.priceDinner(), self.lunchCount() + self.dinnerCount());
            });
        }

        public caculatorSum(price: number, count: number): void {
            let self = this;
            self.txtPriceSum(getText('KMR002_11', [price]));
            self.sum(getText('KMR002_12', [count]));

        }

        public initTime(data: any, index: number) {
            let self = this, dateSelect = moment(self.date()).format("YYYY/MM/DD"),
                dateNow = moment(new Date()).format("YYYY/MM/DD"), timeNow = (new Date()).getHours() * 60 + (new Date()).getMinutes(),
                start = self.mealSelected() == 1 ? self.startLunch() : self.startDinner(),
                end = self.mealSelected() == 1 ? self.finishLunch() : self.finishDinner();
            self.startTime(start != null ? moment.utc(moment.duration(start, 'm').asMilliseconds()).format("HH:mm") : '');
            self.endTime(end != null ? moment.utc(moment.duration(end, 'm').asMilliseconds()).format("HH:mm") : '');
            let timeSt = start != null ? start : 0;
            if (data.listOrder.length > index && data.listOrder[index].ordered) {
                self.textError(getText('KMR002_6'));
                self.setDisPlay(false);
            } else if (dateSelect < dateNow) {
                self.textError(getText('KMR002_9'));
                self.setDisPlay(false);
            } else if (dateSelect == dateNow) {
                if (timeSt <= timeNow && timeNow <= end) {
                    self.isVisible(false);
                    self.isError(false);
                } else {
                    self.isVisible(true);
                    self.isError(false);
                }
                self.isEnableLunch((self.startLunch() <= timeNow && timeNow <= self.finishLunch()) ? true : false);
                self.isEnableDinner((self.startDinner() <= timeNow && timeNow <= self.finishDinner()) ? true : false);
                self.isEnable((!self.isEnableLunch() && !self.isEnableDinner()) ? false : true);

            } else {
                self.setDisPlay(true);
            }

            self.startTime.valueHasMutated();
            self.endTime.valueHasMutated();
        }

        public setDisPlay(value: boolean) {
            let self = this;
            self.isVisible(!value);
            self.isError(!value);
            self.isEnableLunch(value);
            self.isEnableDinner(value);
            self.isEnable(value);
        }

        public updateOrderLunch(frameNo: number): void {
            let self = this;
            self.lunchCount(self.lunchCount() + 1);
            self.isVisibleLunch(true);
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
            self.isVisibleDinner(true);
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
                _.each(self.listBentoOrderLunch(), (value) => {
                    if (value && value.frameNo() == frameNo()) {
                        self.listBentoOrderLunch.remove(value);
                    }
                });
            }

            if (self.listBentoOrderLunch().length == 0) {
                self.isVisibleLunch(false);
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
                _.each(self.listBentoOrderDinner(), (value) => {
                    if (value && value.frameNo() == frameNo()) {
                        self.listBentoOrderDinner.remove(value);
                    }
                });
            }

            if (self.listBentoOrderDinner().length == 0) {
                self.isVisibleDinner(false);
            }

        }

        public registerFood(): void {
            let self = this, dateSelect = moment(self.date()).format("YYYY/MM/DD"),
                dateNow = moment(new Date()).format("YYYY/MM/DD"), timeNow = (new Date()).getHours() * 60 + (new Date()).getMinutes(),
                detailLst = [];
            if (dateNow > dateSelect) {
                error({ messageId: "Msg_1584" });
                return;
            }

            _.forEach(self.listBentoOrderLunch(), (item) => {
                let value = { closingTimeFrame: 1, frameNo: item.frameNo(), bentoCount: item.bentoCount() };
                detailLst.push(value);
            });
            _.forEach(self.listBentoOrderDinner(), (item) => {
                let value = { closingTimeFrame: 2, frameNo: item.frameNo(), bentoCount: item.bentoCount() };
                detailLst.push(value);
            });
            self.register(detailLst);
        }

        public register(detailLst: any): void {

            let self = this, command = { workLocationCode: self.workLocationCode(), date: self.date(), details: detailLst };
            nts.uk.ui.block.invisible();
            if (self.isUpdate() && self.date()) {
                service.update(command).done((data) => {
                    info({ messageId: "Msg_15" });
                    if (detailLst.length == 0) {
                        self.isUpdate(false);
                        return;
                    }
                }).fail(() => {
                    error({ messageId: "Msg_1585" });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            if (!self.isUpdate() && self.date()) {
                if (detailLst.length == 0) {
                    error({ messageId: "Msg_1605" });
                    nts.uk.ui.block.clear();
                    return;
                }
                service.register(command).done((data) => {
                    info({ messageId: "Msg_15" });
                    self.isUpdate(true);
                }).fail(() => {
                    error({ messageId: "Msg_1585" });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });

            }
        }

        public outputData(): void {
            let self = this;
            nts.uk.ui.block.invisible();
            service.print().done().fail((res: any) => {
                error({ messageId: res.messageId });
            }).always(() => {
                nts.uk.ui.block.clear();
            });
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

