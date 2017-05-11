module qmm020.c.viewmodel {
    export class ScreenModel {
        gridColumns: Array<any> = [];
        model: KnockoutObservable<Model> = ko.observable(undefined);


        constructor() {
            let self = this;
            self.gridColumns = [
                { headerText: "コード", key: "employmentCode", dataType: "string", width: "100px", template: '${employmentCode}' },
                { headerText: "名称", key: "employmentName", dataType: "string", width: "200px", template: '${employmentName}' },
                {
                    headerText: "給与明細書", key: "paymentDetailCode", dataType: "string", width: "250px",
                    template: '<button onclick="__viewContext.viewModel.viewmodelC.openMDialogPay()">選択</button><span>${paymentDetailCode} ${paymentDetailName}</span>'
                },
                {
                    headerText: 'Detail Name', key: 'paymentDetailName', dataType: 'string', hidden: true
                },
                {
                    headerText: "賞与明細書", key: "bonusDetailCode", dataType: "string", width: "20%",
                    template: '<button onclick="__viewContext.viewModel.viewmodelC.openMDialogBo()">選択</button><span>${bonusDetailCode} ${bonusDetailName}</span>'
                },
                {
                    headerText: 'Detail Name', key: 'bonusDetailName', dataType: 'string', hidden: true
                }
            ];

            self.model(new Model({ ListItems: [], GridItems: [] }));

            // call start function
            self.start();
        }

        start() {
            let self = this;

            // clear all data for first load
            self.model().ListItems.removeAll();
            self.model().GridItems.removeAll();

            // get list history data
            service.getEmployeeAllotHeaderList().done(function(data: Array<IListModel>) {
                if (data.length > 0) {
                    data = _.orderBy(data, ['endYm'], ['desc']);
                    data.map((m) => { self.model().ListItems.push(new ListModel(m)); });
                }
                // get itemMax of ListItem
                service.getAllotEmployeeMaxDate().done(function(itemMax: number) {
                    let maxDate: IListModel = _.find(self.model().ListItems(), function(obj) { return obj.endYm == itemMax; });
                    self.model().ListItems().map((m) => {
                        if (m.historyId == maxDate.historyId) {
                            m.isMaxEnYm = true;
                        } else {
                            m.isMaxEnYm = false;
                        }
                    });
                });
            }).fail(function(res) {
                alert(res);
            });

            service.getEmployeeName().done(function(data: Array<IGridModel>) {
                if (data && data.length > 0) {
                    data.map((m) => { self.model().GridItems.push(new GridModel(m)); });
                }
            }).fail(function(res) {
                alert(res);
            });

            self.model().ListItems.valueHasMutated();
            self.model().GridItems.valueHasMutated();

            self.model().ListItemSelected.valueHasMutated();
            self.model().GridItemSelected.valueHasMutated();
        }

        openJDialog() {
            let self = this;
            // get item has property endDate is max value
            let oldItem: any = _.find(self.model().ListItems(), function(m) { return m.isMaxEnYm == true; }) || {};

            nts.uk.ui.windows.setShared("J_DATA", { displayMode: 1, startYm: oldItem.startYm || 197001, endYm: oldItem.endYm || 999912 });
            nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { width: 485, height: 550, title: '履歴の追加', dialogClass: "no-close" })
                .onClosed(function() {
                    nts.uk.ui.windows.setShared("J_DATA", null);
                    let value: any = nts.uk.ui.windows.getShared('J_RETURN');
                    if (value) {
                        let oldItem: ListModel = _.find(self.model().ListItems(), function(m) { return m.isMaxEnYm == true; });
                        let startDate = nts.uk.time.parseYearMonth(value.startDate);
                        if (startDate.success) {
                            let id: string = '_NEW_' + self.model().ListItems.length + '_' + value.startDate,
                                newItem: ListModel = new ListModel({ historyId: id, startYm: value.startDate, endYm: 999912 });

                            if (oldItem) {
                                oldItem.isMaxEnYm = false;
                                oldItem.endYm = parseInt(moment.utc(Date.UTC(startDate.year, startDate.month - 2, 1)).format("YYYYMM"));
                                oldItem.update();
                            }
                            self.model().ListItems.push(newItem);
                            self.model().ListItems(_.orderBy(self.model().ListItems(), ['endYm'], ['desc']));

                            // store old grid data
                            let temp = self.model().GridItems();

                            // selected new id (item)
                            self.model().ListItemSelected(id);

                            // copy or new mode
                            if (value.selectedMode == 1) {
                                //self.model().GridItems().map((f) => {
                                //    f.bonusDetailCode = '';
                                //    f.bonusDetailName = '';    
                                //    f.paymentDetailCode = '';
                                //    f.paymentDetailName = '';
                                //});
                                //dirty.reset();
                                self.model().GridItems(temp);
                                self.model().updateData();
                                debugger;
                            } else {
                                self.model().GridItems().map((f) => {
                                    f.bonusDetailCode = '';
                                    f.bonusDetailName = '';
                                    f.paymentDetailCode = '';
                                    f.paymentDetailName = '';
                                });
                            }
                        }

                    }
                });
        }

        openKDialog() {
            let self = this;

            self.model().GridItemSelected.valueHasMutated;
            // set shared data for k screen
            nts.uk.ui.windows.setShared("K_DATA", { displayMode: 1, startYm: self.selectedItem().startDate, endYm: self.selectedItem().endDate });

            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { width: 485, height: 550, title: '履歴の編集', dialogClass: "no-close" })
                .onClosed(() => {
                    let value: any = nts.uk.ui.windows.getShared("K_RETURN");
                    if (value) {
                        if (value.selectedMode == 1) {
                            return null;
                        } else { }
                    }

                }

        }
        // get paymentDetailName
        openMDialogPay() {
            let self = this, currentItemList = self.model().currentItemList();
            if (!!currentItemList) {
                nts.uk.ui.windows.setShared('M_BASEYM', currentItemList.startYm);

                nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                    .onClosed(function() {
                        let currentItemGrid = self.model().currentItemGrid();
                        if (currentItemGrid) {
                            let stmtCode = nts.uk.ui.windows.getShared('M_RETURN');
                            currentItemGrid.paymentDetailCode = stmtCode;
                            if (stmtCode != undefined) {
                                service.getAllotLayoutName(stmtCode).done(function(stmtName: string) {
                                    currentItemGrid.paymentDetailName = stmtName;
                                    //update date to igGrid
                                    self.model().updateData();
                                }).fail(function(res) {
                                    alert(res);
                                });
                            }
                        }
                    });
            }
        }
        //get bonusDetailName
        openMDialogBo() {
            let self = this, currentItemList = self.model().currentItemList();
            if (!!currentItemList) {
                nts.uk.ui.windows.setShared('M_BASEYM', currentItemList.startYm);

                nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                    .onClosed(function() {
                        let currentItemGrid = self.model().currentItemGrid();
                        if (currentItemGrid) {
                            let stmtCode = nts.uk.ui.windows.getShared('M_RETURN');
                            currentItemGrid.bonusDetailCode = stmtCode;
                            if (stmtCode != undefined) {
                                service.getAllotLayoutName(stmtCode).done(function(stmtName: string) {
                                    currentItemGrid.bonusDetailName = stmtName;
                                    //update date to igGrid
                                    self.model().updateData();
                                }).fail(function(res) {
                                    alert(res);
                                });
                            }
                        }
                    });
            }
        }

        saveData() {
        }
    }

    interface IModel {
        ListItems: Array<IListModel>;
        GridItems: Array<IGridModel>;
    }

    // main model
    class Model {
        ListItems: KnockoutObservableArray<ListModel> = ko.observableArray([]);
        ListItemSelected: KnockoutObservable<string> = ko.observable(undefined);
        GridItems: KnockoutObservableArray<GridModel> = ko.observableArray([]);
        GridItemSelected: KnockoutObservable<string> = ko.observable(undefined);

        constructor(param: IModel) {
            let self = this;
            self.ListItems(param.ListItems.map((m) => { return new ListModel(m); }));
            self.GridItems(param.GridItems.map((m) => { return new GridModel(m); }));
            self.ListItemSelected.subscribe((v) => {
                if (v && v.indexOf('NEW') == -1) {
                    service.getEmployeeDetail(v).done(function(data: Array<any>) {
                        _.forEach(self.GridItems(), function(t) {
                            let item = _.find(data, function(m) {
                                return t.employmentCode == m.employeeCode;
                            });
                            if (item) {
                                t.bonusDetailCode = item.bonusDetailCode;
                                t.bonusDetailName = item.bonusDetailName;
                                t.paymentDetailCode = item.paymentDetailCode;
                                t.paymentDetailName = item.paymentDetailName;
                            }
                            else {
                                t.bonusDetailCode = '';
                                t.bonusDetailName = '';
                                t.paymentDetailCode = '';
                                t.paymentDetailName = '';
                            }
                        });
                        //update date to igGr    
                        self.updateData();
                    });
                }
            });
        }

        updateData() {
            let self = this;
            //update date to igGrid
            $("#C_LST_001").igGrid("option", "dataSource", ko.toJS(self.GridItems()));
        };

        currentItemList(): IListModel {
            let self = this;
            return _.find(self.ListItems(), function(t: IListModel) { return t.historyId == self.ListItemSelected(); });
        }

        currentItemGrid(): IGridModel {
            let self = this;
            return _.find(self.GridItems(), function(t: IGridModel) { return t.employmentCode == self.GridItemSelected(); });
        }
    }

    interface IListModel {
        historyId: string;
        endYm: number;
        startYm: number;
    }

    // list view model
    class ListModel {
        historyId: string;
        endYm: number;
        startYm: number;
        text: string;
        isMaxEnYm: boolean;

        constructor(param: IListModel) {
            this.historyId = param.historyId;
            this.endYm = param.endYm;
            this.startYm = param.startYm;
            this.update();
        }

        update() {
            this.text = nts.uk.time.formatYearMonth(this.startYm) + " ~ " + nts.uk.time.formatYearMonth(this.endYm);
        }
    }

    interface IGridModel {
        historyId: string;
        employmentCode: string;
        employmentName: string;
        bonusDetailCode?: string;
        bonusDetailName?: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
    }

    // grid view model
    class GridModel {
        historyId: string;
        employmentCode: string;
        employmentName: string;
        bonusDetailCode: string;
        bonusDetailName: string;
        paymentDetailCode: string;
        paymentDetailName: string;
        constructor(param: IGridModel) {
            this.historyId = param.historyId;
            this.employmentCode = param.employmentCode;
            this.employmentName = param.employmentName;
            this.bonusDetailCode = param.bonusDetailCode;
            this.bonusDetailName = param.bonusDetailName;
            this.paymentDetailCode = param.paymentDetailCode;
            this.paymentDetailName = param.paymentDetailName;
        }
    }
}

