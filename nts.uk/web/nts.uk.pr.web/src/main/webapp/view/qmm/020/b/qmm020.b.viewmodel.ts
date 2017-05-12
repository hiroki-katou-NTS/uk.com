module qmm020.b.viewmodel {
    export class ScreenModel {
        listItems: KnockoutObservableArray<ListModel> = ko.observableArray([]);
        selectedId: KnockoutObservable<string> = ko.observable(undefined);
        selectedItem: KnockoutObservable<ListModel> = ko.observable(new ListModel({ historyId: '', startDate: 197001, endDate: 999912 }));

        dirty: IDirty = {
            selectedId: new nts.uk.ui.DirtyChecker(this.selectedId),
            selectedItem: new nts.uk.ui.DirtyChecker(this.selectedItem),
            isDirty: function() {
                return this.selectedItem.isDirty();
            },
            reset: function() {
                this.selectedId.reset();
                this.selectedItem.reset();
            }
        };
        constructor() {
            var self = this;

            // change selectedItem when selectedId is changed
            self.selectedId.subscribe(function(v) {
                self.selectedItem(_.find(self.listItems(), function(m) { return m.historyId == v; }));
            });

            // call init data function
            self.start();
        }

        // start function
        start() {
            let self = this, dfd = $.Deferred();
            // clear all old item
            self.listItems.removeAll();

            service.getAllotCompanyList()
                .done(function(resp: Array<IListModel>) {
                    if (resp.length > 0) {
                        // order by endDate with desc type and push to list source
                        _.orderBy(resp, ['endDate'], ['desc']).map((m) => {
                            self.listItems.push(new ListModel(m));
                        });

                        // select first item
                        self.selectedId(self.listItems()[0].historyId);

                        // subscrible change value function
                        self.selectedId.valueHasMutated();
                        self.selectedItem.valueHasMutated();

                        // find item has max date value
                        service.getAllotCompanyMaxDate().done(function(item: IListModel) {
                            let maxItem = _.find(self.listItems(), function(m) { return m.historyId == item.historyId; });
                            if (maxItem) {
                                maxItem.isMaxDate = true;
                                self.listItems().map((m) => { if (m.historyId != maxItem.historyId) m.isMaxDate = false; });
                            }
                        }).fail(function(res) {
                            alert(res);
                        });

                        // reset dirty
                        self.dirty.reset();
                    }
                })
                .fail(function(mes) { alert(mes); });

            return dfd.promise();
        }

        // event calling by saveData event in view A
        saveData() {
            let self = this, model: Array<ListModel> = ko.toJS(self.listItems);
            // push data to server by service
            // change function?
            let addItem = _.find(model, function(o){return o.historyId == 'NEW'});
            debugger;
            service.saveData(model);
        }

        //Open dialog Add History
        openJDialog() {
            var self = this;
            // check dirty at here

            // update current item to max item has end date
            self.listItems().map((m) => {
                if (m.isMaxDate) {
                    self.selectedId(m.historyId);
                }
            });

            let oldItem: any = self.selectedItem() || {};

            // set init data for j dialog
            nts.uk.ui.windows.setShared("J_DATA", {
                displayMode: 1,
                startYm: oldItem.startDate || 197001,
                endYm: oldItem.endDate || 999912
            });

            nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { width: 485, height: 550, title: '履歴の追加', dialogClass: "no-close" })
                .onClosed(function() {
                    let model: any = nts.uk.ui.windows.getShared("J_RETURN");
                    if (model) {
                        let oldItem: ListModel = self.selectedItem(),
                            startDate = nts.uk.time.parseYearMonth(model.startDate);

                        if (startDate.success) {
                            let id: string = '_NEW_' + self.listItems().length + '_' + model.startDate,
                                newItem: ListModel = new ListModel({ historyId: id, startDate: model.startDate, endDate: 999912 });

                            // update data
                            newItem.isMaxDate = true;
                            if (oldItem) {
                                oldItem.isMaxDate = false;
                                oldItem.endDate = parseInt(moment.utc(Date.UTC(startDate.year, startDate.month - 2, 1)).format("YYYYMM"));
                                oldItem.update();

                                if (model.selectedMode == 1) {
                                    // copy mode
                                    newItem.bonusDetailCode = oldItem.bonusDetailCode;
                                    newItem.bonusDetailName = oldItem.bonusDetailName;
                                    newItem.paymentDetailCode = oldItem.paymentDetailCode;
                                    newItem.paymentDetailName = oldItem.paymentDetailName;
                                }
                            }

                            // update data list on view
                            self.listItems().push(newItem);
                            self.listItems(_.orderBy(self.listItems(), ['endDate'], ['desc']));

                            // selected new item
                            self.selectedId(id);
                            self.selectedItem.valueHasMutated();
                        }
                        else {
                            alert(startDate.msg);
                        }

                    }
                    // clear shared data
                    nts.uk.ui.windows.setShared("J_DATA", undefined);
                    nts.uk.ui.windows.setShared("J_RETURN", undefined);
                });
        }

        //Open dialog Edit History
        openKDialog() {
            var self = this;

            //update current model
            self.selectedId.valueHasMutated();

            // set shared data for k screen
            nts.uk.ui.windows.setShared("K_DATA", { displayMode: 1, startYm: self.selectedItem().startDate, endYm: self.selectedItem().endDate });
            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { width: 485, height: 550, title: '履歴の編集', dialogClass: "no-close" })
                .onClosed(() => {
                    let model: any = nts.uk.ui.windows.getShared("K_RETURN");
                    if (model) {
                        // search index of current item
                        let index = _.findIndex(self.listItems(), function(m) { return m.historyId == self.selectedId(); });
                        if (model.selectedMode == 1) {

                            // call service delete item at here
                            //                            self.listItems.splice(index, 1);
                            let dele = self.selectedItem();
                            service.deleteData(dele).done(function(data: ListModel) {
                                self.listItems.valueHasMutated();
                                // select to next item
                                while (index >= self.listItems().length) {
                                    index--;
                                }

                                if (!self.listItems()[index]) {
                                    self.selectedId(undefined);
                                } else {
                                    self.selectedId(self.listItems()[index].historyId);
                                }

                                self.selectedItem.valueHasMutated();
                                debugger;
                            }).fail(function(res) {
                                alert(res);
                            });



                            // call start function
                            //self.start();
                        } else {
                            // modify
                            let startDate = nts.uk.time.parseYearMonth(model.startYm);
                            if (startDate.success) {
                                let current: ListModel = self.selectedItem(), neighbor = self.listItems()[index + 1];
                                current.startDate = parseInt(moment.utc(Date.UTC(startDate.year, startDate.month - 1, 1)).format("YYYYMM"));
                                current.update();
                                if (!!neighbor) {
                                    neighbor.endDate = parseInt(moment.utc(Date.UTC(startDate.year, startDate.month - 2, 1)).format("YYYYMM"));
                                    neighbor.update();
                                }

                                // update view data
                                // fkc?
                                self.listItems.push(self.listItems.pop());
                                self.selectedId.valueHasMutated();
                                self.selectedItem.valueHasMutated();
                            } else {
                                alert(startDate.msg);
                            }
                        }
                    }
                    // clear shared data
                    nts.uk.ui.windows.setShared("K_DATA", undefined);
                    nts.uk.ui.windows.setShared("K_RETURN", undefined);
                });
        }

        //Click to button Select Payment
        openPaymentMDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('M_BASEYM', self.selectedItem().startDate);

            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                .onClosed(() => {
                    //get selected code from M dialog
                    let selectedCode: string = nts.uk.ui.windows.getShared('M_RETURN');

                    self.selectedItem().paymentDetailCode = selectedCode;

                    //get payment Name
                    service.getAllotLayoutName(selectedCode)
                        .done(function(stmtName: string) {
                            self.selectedItem().paymentDetailName = stmtName;
                            // update value
                            self.selectedId.valueHasMutated();
                        }).fail(function(res) {
                            alert(res);
                        });
                });
        }

        //Click to button Select Bonus
        openBonusMDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('M_BASEYM', self.selectedItem().startDate);

            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                .onClosed(() => {
                    //get selected code from M dialog
                    let selectedCode: string = nts.uk.ui.windows.getShared('M_RETURN');

                    self.selectedItem().bonusDetailCode = selectedCode;
                    //get payment Name
                    service.getAllotLayoutName(selectedCode)
                        .done(function(stmtName: string) {
                            self.selectedItem().bonusDetailName = stmtName;
                            // update value
                            self.selectedId.valueHasMutated();
                        }).fail(function(res) {
                            alert(res);
                        });
                });
        }
    }

    interface IListModel {
        bonusDetailCode?: string;
        bonusDetailName?: string;
        endDate: number;
        historyId: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
        startDate: number;
    }

    // list view model
    class ListModel {
        bonusDetailCode: string;
        bonusDetailName: string;
        endDate: number;
        historyId: string;
        paymentDetailCode: string;
        paymentDetailName: string;
        startDate: number;
        text: string;
        isMaxDate: boolean = false;

        constructor(param: IListModel) {
            this.historyId = param.historyId;
            this.endDate = param.endDate;
            this.startDate = param.startDate;

            this.bonusDetailCode = param.bonusDetailCode || '';
            this.bonusDetailName = param.bonusDetailName || '';

            this.paymentDetailCode = param.paymentDetailCode || '';
            this.paymentDetailName = param.paymentDetailName || '';
            this.update();
        }

        update() {
            this.text = nts.uk.time.formatYearMonth(this.startDate) + " ~ " + nts.uk.time.formatYearMonth(this.endDate);
        }
    }

    interface IDirty {
        selectedId: nts.uk.ui.DirtyChecker,
        selectedItem: nts.uk.ui.DirtyChecker,
        isDirty: any,
        reset: any
    }
}
