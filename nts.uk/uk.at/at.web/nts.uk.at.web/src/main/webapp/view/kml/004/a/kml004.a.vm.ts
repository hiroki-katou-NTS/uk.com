import setSharedA = nts.uk.ui.windows.setShared;
import getSharedA = nts.uk.ui.windows.getShared;
module nts.uk.at.view.kml004.a.viewmodel {

    export class ScreenModel {
        // list Category A2_1
        lstCate: KnockoutObservableArray<ITotalCategory>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code 
        selectedCode: KnockoutObservable<string>;
        // selected item
        selectedOption: KnockoutObservable<TotalCategory>;
        // check update or insert true: insert, false: update
        checkUpdate: KnockoutObservable<boolean>;
        // list eval item in the left
        items: KnockoutObservableArray<IEvalOrder>;
        // column order in the right
        newColumns: KnockoutObservableArray<any>;
        // columns in the left
        columns: KnockoutObservableArray<any>;
        // list ben trai sau khi loai bo nhung phan tu trong list phai
        evalItemList: Array<IEvalOrder>;
        // list CalDaySet
        calDaySetList: KnockoutObservableArray<CalDaySet>;
        // cnt set received from dialog D
        cntSetls: KnockoutObservableArray<any>;
        // list cnt set 
        cntSetAll: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_6"), key: 'categoryCode', width: 50 },
                { headerText: nts.uk.resource.getText("KML004_7"), key: 'categoryName', width: 250, formatter: _.escape }
            ]);

            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_14"), key: 'totalItemNo', width: 70, hidden: true },
                { headerText: nts.uk.resource.getText("KML004_15"), key: 'totalItemName', width: 150, formatter: _.escape }
            ]);

            self.newColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_17"), key: 'totalItemNo', width: 1, hidden: true },
                { headerText: nts.uk.resource.getText("KML004_18"), key: 'totalItemName', width: 200, formatter: _.escape },
                {
                    headerText: nts.uk.resource.getText(""), key: 'totalItemName', width: 70, unbound: true, dataType: "string",
                    template: '{{if ${totalItemNo} == "3" || ${totalItemNo} == "21" || ${totalItemNo} == "22"}} <button class="setting" onclick="openDlg(this)" data-code="${totalItemNo}" data-name="${totalItemName}" style="margin-left: 7px;">設定</button> {{/if}}',
                }
            ]);

            self.lstCate = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            let param = {
                categoryCode: "",
                categoryName: "",
                memo: "",
                totalEvalOrders: [],
            }
            self.selectedOption = ko.observable(new TotalCategory(param));
            self.checkUpdate = ko.observable(false);
            self.items = ko.observableArray([]);
            self.evalItemList = [];
            self.calDaySetList = ko.observableArray([]);
            self.cntSetAll = ko.observableArray([]);
            self.selectedCode.subscribe((value) => {
                self.items([]);
                let lstTemp = [];
                if (value) {
                    let foundItem = _.find(self.lstCate(), (item: ITotalCategory) => {
                        return item.categoryCode == value;
                    });
                    nts.uk.ui.errors.clearAll();
                    self.checkUpdate(false);
                    self.selectedOption().categoryCode(foundItem.categoryCode);
                    self.selectedOption().categoryName(foundItem.categoryName);
                    self.selectedOption().memo(foundItem.memo);
                    self.selectedOption().totalEvalOrders(foundItem.totalEvalOrders);
                    // right grid
                    var totalItemOrders = [];
                    let sortedData = _.orderBy(foundItem.totalEvalOrders, ['dispOrder'], ['asc']);
                    _.forEach(sortedData, function(item: IEvalOrder) {
                        let foundItemOrder: IEvalOrder = _.find(self.evalItemList, (itemDB: IEvalOrder) => {
                            return itemDB.totalItemNo == item.totalItemNo;
                        });
                        item.totalItemName = foundItemOrder.totalItemName;
                        var evalOrder = new EvalOrder(item);
                        totalItemOrders.push(evalOrder);
                    });
                    self.selectedOption().totalEvalOrders(totalItemOrders);

                    // left grid
                    var totalItemNoList = _.map(totalItemOrders, function(item) { return item.totalItemNo; });
                    self.items(_.filter(self.evalItemList, function(item: IEvalOrder) {
                        return _.indexOf(totalItemNoList, item.totalItemNo) < 0;
                    }));
                }
                $("#nameCtg").focus();
            });
        }

        /** get data to list **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done((lstData: Array<ITotalCategory>) => {
                let sortedData: Array<ITotalCategory> = _.orderBy(lstData, ['totalItemNo'], ['asc']);
                self.lstCate(sortedData);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
            });
            return dfd.promise();
        }

        /** get list eval item **/
        getEvalItem(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getItem().done((lstItem: Array<IEvalOrder>) => {
                if (lstItem.length == 0) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_458" }).then(()=> {
                        $("#swap-list-grid1").igGrid("container").focus();
                        nts.uk.ui.windows.close();        
                    });
                } else {
                    let sortedData: Array<IEvalOrder> = _.orderBy(lstItem, ['totalItemNo'], ['asc']);
                    self.items(sortedData);
                    self.evalItemList = lstItem;
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** get cal day set */
        getCal(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getSet().done((lstSet) => {
                self.calDaySetList(lstSet);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** get all cnt set */
        getCNT(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getCNT().done((lstCnt) => {
                self.cntSetAll(lstCnt);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            let self = this;
            let dfd = $.Deferred();
            let array = [];
            let sortedData = [];
            $.when(self.getCal(), self.getCNT(), self.getEvalItem(), self.getData()).done(function() {
                if (self.lstCate().length == 0) {
                    self.clearForm();
                } else {
                    self.selectedCode(self.lstCate()[0].categoryCode);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            }).always(()=> {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        /** update or insert data when click button register **/
        register() {
            nts.uk.ui.block.invisible();
            let self = this;
            let part = [];
            $("#code-text").trigger("validate");
            $("#nameCtg").trigger("validate");
            $("#memo").trigger("validate");
            var i = 0;
            _.forEach(self.selectedOption().totalEvalOrders(), function(item) {
                part.push({
                    categoryCode: self.selectedOption().categoryCode(),
                    totalItemNo: item.totalItemNo,
                    totalItemName: item.totalItemName,
                    dispOrder: i,
                    horiCalDaysSet: item.horiCalDaysSet,
                    cntSetls: item.cntSetls,
                });
                i = i + 1;
            });
            self.selectedOption().totalEvalOrders(part);
            let param: ITotalCategory = {
                categoryCode: self.selectedOption().categoryCode(),
                categoryName: self.selectedOption().categoryName(),
                memo: self.selectedOption().memo(),
                totalEvalOrders: ko.toJS(self.selectedOption().totalEvalOrders()),
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            _.defer(() => {
                // update item to list  
                if (self.checkUpdate() == false) {
                    service.update(ko.toJS(param)).done(function() {
                        self.lstCate([]);
                        self.getData().done(function() {
                            self.selectedCode(null);
                            self.selectedCode(param.categoryCode);
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            $("#nameCtg").focus();
                        });
                    }).fail(function(res) {
                        if(res.messageId == 'Msg_363'){
                            $('#swap-list-grid2').igGrid("container").focus();
                        }
                        nts.uk.ui.dialog.alertError(res.message);
                    }).always(()=>{
                        nts.uk.ui.block.clear();    
                    });;
                }
                else {
                    // insert item to list
                    service.add(ko.toJS(param)).done(function() {
                        self.lstCate([]);
                        self.getData().done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.selectedCode(null);
                            self.selectedCode(param.categoryCode);
                            $("#nameCtg").focus();
                        });
                    }).fail(function(res) {
                        if(res.messageId == 'Msg_3'){
                            $('#code-text').ntsError('set', res);
                        }
                        if(res.messageId == 'Msg_363'){
                            $('#swap-list-grid2').igGrid("container").focus();
                        }
                        nts.uk.ui.dialog.alertError(res.message);
                    }).always(()=>{
                        nts.uk.ui.block.clear();    
                    });
                }
            });
        }
        //  new mode  
        newMode() {
            let self = this;
            $("#code-text").ntsError('clear');
            self.clearForm();
            self.items(_.clone(self.evalItemList));
        }

        clearForm() {
            let self = this;
            self.selectedOption().categoryCode("");
            self.selectedOption().categoryName("");
            self.selectedOption().memo("");
            self.selectedOption().totalEvalOrders([]);
            self.checkUpdate(true);
            self.selectedCode("");
            $("#code-text").focus();
            nts.uk.ui.errors.clearAll();
        }

        /** remove item from list **/
        remove() {
            nts.uk.ui.block.invisible();
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstCate().length; i++) {
                if (self.lstCate()[i].categoryCode == self.selectedCode()) {
                    count = i;
                    break;
                }
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                var dataTranfer = {
                    categoryCode: self.selectedOption().categoryCode()
                };
                service.remove(dataTranfer).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                        self.lstCate([]);
                        self.getData().done(function() {
                            // if number of item from list after delete == 0 
                            if (self.lstCate().length == 0) {
                                self.newMode();
                                return;
                            }
                            // delete the last item
                            if (count == ((self.lstCate().length))) {
                                self.selectedCode(self.lstCate()[count - 1].categoryCode);
                                return;
                            }
                            // delete the first item
                            if (count == 0) {
                                self.selectedCode(self.lstCate()[0].categoryCode);
                                return;
                            }
                            // delete item at mediate list 
                            else if (count > 0 && count < self.lstCate().length) {
                                self.selectedCode(self.lstCate()[count].categoryCode);
                                return;
                            }
                        });
                    });
                }).always(()=>{
                    nts.uk.ui.block.clear();    
                });
            }).ifCancel(() => {
                nts.uk.ui.block.clear();
            });
        }

        /** click close button **/
        closeDialog() {
            nts.uk.ui.windows.close();
            var t1 = performance.now();
        }

        /** click 設定 button **/
        openBDialog(itemNo: number) {
            let self = this;
            var horiCalDaysSet: CalDaySet = null;
            var currentEvalOrder: EvalOrder = _.find(self.selectedOption().totalEvalOrders(), function(item) {
                return item.totalItemNo == itemNo;
            });
            if (!currentEvalOrder || !currentEvalOrder.hasOwnProperty("horiCalDaysSet") || !currentEvalOrder.horiCalDaysSet()) {
                horiCalDaysSet = new CalDaySet(self.selectedOption().categoryCode(), itemNo, 0, 0, 0, 0);
            } else {
                horiCalDaysSet = currentEvalOrder.horiCalDaysSet();
            }

            setSharedA('KML004A_DAY_SET', horiCalDaysSet);
            nts.uk.ui.windows.sub.modal('/view/kml/004/b/index.xhtml').onClosed(function(): any {
                var daySet = getSharedA("KML004B_DAY_SET");
                if (!daySet) {
                    return;
                }
                var totalEvalOrders = [];
                var dispOrder = 0;
                _.forEach(self.selectedOption().totalEvalOrders(), function(item) {
                    var evalOrder = item instanceof EvalOrder ? item : new EvalOrder(item);
                    evalOrder.dispOrder = dispOrder;
                    if (item.totalItemNo == itemNo) {
                        evalOrder.horiCalDaysSet(daySet);
                    }
                    totalEvalOrders.push(evalOrder);
                    dispOrder++;
                });
                self.selectedOption().totalEvalOrders(totalEvalOrders);
            });
        }

        /** click 設定 button **/
        openDDialog(itemNo: number) {
            let self = this;
            var currentEvalOrder: EvalOrder = _.find(self.selectedOption().totalEvalOrders(), function(item) {
                return item.totalItemNo == itemNo;
            });
            if (!currentEvalOrder.hasOwnProperty("horiCalDaysSet")) {
                var dataTranfer = {
                    totalItemNo: itemNo,
                    categoryCode: self.selectedOption().categoryCode(),
                    cntSetls: [],
                };
            } else {
                var dataTranfer = {
                    totalItemNo: itemNo,
                    categoryCode: self.selectedOption().categoryCode(),
                    cntSetls: currentEvalOrder.cntSetls(),
                };
            }
            setSharedA("KML004A_CNT_SET", dataTranfer)
            nts.uk.ui.windows.sub.modal('/view/kml/004/d/index.xhtml').onClosed(function(): any {
                var sets = getSharedA("KML004D_CNT_SET");
                if (!sets) {
                    return;
                }
                var totalEvalOrders = [];
                var dispOrder = 0;
                _.forEach(self.selectedOption().totalEvalOrders(), function(item) {
                    var evalOrder = item instanceof EvalOrder ? item : new EvalOrder(item);
                    evalOrder.dispOrder = dispOrder;
                    if (item.totalItemNo == itemNo) {
                        var cntSetList = [];
                        _.forEach(sets, function(itemCntSet) {
                            cntSetList.push(itemCntSet);
                        });
                        evalOrder.cntSetls(cntSetList);
                    }
                    totalEvalOrders.push(evalOrder);
                });
                self.selectedOption().totalEvalOrders(totalEvalOrders);
            });
        }

        openDialog(id, name) {
            var self = this;
            if ("3" == id) {
                self.openBDialog(id);
            } else if ("21" == id || "22" == id) {
                self.openDDialog(id);
            }
        }
        private exportExcel(): void {
            var self = this;
            nts.uk.ui.block.grayout();
            let langId = "ja";
            service.saveAsExcel(langId).done(function() {
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId });
            }).always(function() {
                nts.uk.ui.block.clear();
            });
         }
    }

 
    export interface ITotalCategory {
        categoryCode: string;
        categoryName: string;
        memo: string;
        totalEvalOrders: Array<EvalOrder>;
    }

    export class TotalCategory {
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        totalEvalOrders: KnockoutObservableArray<any>;
        constructor(param: ITotalCategory) {
            let self = this;
            this.categoryCode = ko.observable(param.categoryCode);
            this.categoryName = ko.observable(param.categoryName);
            this.memo = ko.observable(param.memo);
            this.totalEvalOrders = ko.observableArray(param.totalEvalOrders);

        }
    }


    export interface IEvalOrder {
        categoryCode: string;
        totalItemNo: number;
        totalItemName: string;
        dispOrder: number;
        horiCalDaySet: ICalDaySet;
        cntSetls: Array<any>;
    }

    export class EvalOrder {
        categoryCode: string;
        totalItemNo: number;
        totalItemName: string;
        dispOrder: number;
        horiCalDaysSet: KnockoutObservable<any> = ko.observable(null);
        cntSetls: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor(param: IEvalOrder) {
            this.categoryCode = param.categoryCode;
            this.totalItemNo = param.totalItemNo;
            this.totalItemName = param.totalItemName;
            this.dispOrder = param.dispOrder;
            this.horiCalDaysSet(param.horiCalDaySet);
            this.cntSetls(param.cntSetls);
        }
    }

    export interface ICalDaySet {
        categoryCode: string;
        totalItemNo: number;
        halfDay: number;
        yearHd: number;
        specialHoliday: number;
        heavyHd: number;
    }

    export class CalDaySet {
        categoryCode: string;
        totalItemNo: number;
        halfDay: number;
        yearHd: number;
        specialHoliday: number;
        heavyHd: number;
        constructor(categoryCode: string, totalItemNo: number, halfDay: number, yearHd: number, specialHoliday: number, heavyHd: number) {
            this.categoryCode = categoryCode;
            this.totalItemNo = totalItemNo;
            this.halfDay = halfDay;
            this.yearHd = yearHd;
            this.specialHoliday = specialHoliday;
            this.heavyHd = heavyHd;
        }
    }
}

function openDlg(element) {
    var itemNo = $(element).data("code");
    var itemName = $(element).data("name");
    nts.uk.ui._viewModel.content.openDialog(itemNo, itemName);
}



