module qmm020.b.viewmodel {
    export class ScreenModel {
        listItems: KnockoutObservableArray<ListModel> = ko.observableArray([]);
        selectedId: KnockoutObservable<string> = ko.observable(undefined);
        selectedItem: KnockoutObservable<ListModel> = ko.observable(new ListModel({ historyId: '', startDate: 197001, endDate: 999912 }));

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
            let self = this;
            service.getAllotCompanyList()
                .done(function(resp: Array<IListModel>) {
                    if (resp.length > 0) {
                        // clear all old item
                        self.listItems.removeAll();

                        // order by endDate with desc type and push to list source
                        _.orderBy(resp, ['endDate'], ['desc']).map((m) => {
                            self.listItems.push(new ListModel(m));
                        });

                        // select first item
                        self.selectedId(self.listItems()[0].historyId);

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
                    }
                })
                .fail(function(mes) { alert(mes); });

            // subscrible change value function
            self.selectedId.valueHasMutated();
        }

        // event calling by saveData event in view A
        saveData() {
            let self = this;

            if (self.selectedItem().historyId.indexOf("_NEW_") > -1) {
                service.insertComAllot(ko.toJS(self.selectedItem))
                    .fail(function(res) {
                        alert(res);
                    });
            } else {
                service.updateComAllot(ko.toJS(self.selectedItem))
                    .fail(function(res) {
                        alert(res);
                    });
            }
        }

        //Open dialog Add History
        openJDialog() {
            var self = this;
            // check dirty at here

            // update current item to max item has end date
            self.listItems().map((m) => {
                if (m.isMaxDate) {
                    self.selectedId(m.historyId);
                    self.selectedId.valueHasMutated();
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
                            if (oldItem && model.selectedMode == 1) {
                                oldItem.isMaxDate = false;
                                oldItem.endDate = parseInt(moment.utc(Date.UTC(startDate.year, startDate.month - 2, 1)).format("YYYYMM"));
                                oldItem.update();

                                // copy mode
                                newItem.bonusDetailCode = oldItem.bonusDetailCode;
                                newItem.bonusDetailName = oldItem.bonusDetailName;
                                newItem.paymentDetailCode = oldItem.paymentDetailCode;
                                newItem.paymentDetailName = oldItem.paymentDetailName;
                            } else {
                                // start new data
                                newItem.bonusDetailCode = '';
                                newItem.bonusDetailName = '';
                                newItem.paymentDetailCode = '';
                                newItem.paymentDetailName = '';
                            }

                            // update data list on view
                            self.listItems().push(newItem);
                            self.listItems(_.orderBy(self.listItems(), ['endDate'], ['desc']));

                            // selected new item
                            self.selectedId(id);
                            self.selectedId.valueHasMutated();
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
                        if (model.selectedMode == 1) {
                            let index = _.findIndex(self.listItems(), function(m) { return m.historyId == self.selectedId(); });

                            // call service delete item at here
                            self.listItems.splice(index, 1);
                            // call start function
                            //self.start();
                        } else {
                            // modify

                        }
                    }
                    // clear shared data
                    nts.uk.ui.windows.setShared("K_DATA", undefined);
                    nts.uk.ui.windows.setShared("K_RETURN", undefined);
                });
        }

        //Open L Dialog
        openLDialog() {
            alert('2017');
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

    //Previous Month 
    function previousYM(sYm: string) {
        var preYm: number = 0;
        if (sYm.length == 6) {
            let sYear: string = sYm.substr(0, 4);
            let sMonth: string = sYm.substr(4, 2);
            //Trong truong hop thang 1 thi thang truoc la thang 12
            if (sMonth == "01") {
                preYm = parseInt((parseInt(sYear) - 1).toString() + "12");
                //Truong hop con lai thi tru di 1      
            } else {
                preYm = parseInt(sYm) - 1;
            }
        }
        return preYm;
    }
}
