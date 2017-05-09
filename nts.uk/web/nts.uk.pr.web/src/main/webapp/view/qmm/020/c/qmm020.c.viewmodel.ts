module qmm020.c.viewmodel {
    export class ScreenModel {
        gridColumns: Array<any> = [];
        model: KnockoutObservable<Model> = ko.observable(undefined);

        constructor() {
            let self = this;
            self.gridColumns = [
                { headerText: "コード", key: "employmentCode", dataType: "string", width: "100px" },
                { headerText: "名称", key: "employmentName", dataType: "string", width: "200px" },
                {
                    headerText: "給与明細書", key: "paymentDetailCode", dataType: "string", width: "250px",
                    template: '<button class="c-btn-001" onclick="__viewContext.viewModel.viewmodelC.openMDialog()">選択</button><span>${paymentDetailCode} ${paymentDetailName}</span>'
                },
                {
                    headerText: "賞与明細書", key: "bonusDetailCode", dataType: "string", width: "20%",
                    template: '<button class="c-btn-002" onclick="__viewContext.viewModel.viewmodelC.openMDialog()">選択</button><span>${bonusDetailCode} ${bonusDetailName}</span>'
                },
            ];

            self.model(new Model({ ListItems: [], GridItems: [] }));

            // call start function
            self.start();
        }

        start() {
            let self = this;
            // get list history data
            service.getEmployeeAllotHeaderList().done(function(data: Array<IListModel>) {
                if (data.length > 0) {
                    data.map((m) => { self.model().ListItems.push(new ListModel(m)); });
                }
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
        }

        openJDialog() {
            let self = this;
            nts.uk.ui.windows.setShared("J_DATA", { displayMode: 1, startYm: 201701, endYm: 201708 });
            nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { width: 485, height: 550, title: '履歴の追加', dialogClass: "no-close" })
                .onClosed(function() {
                    nts.uk.ui.windows.setShared("J_DATA", null);
                    let value: any = nts.uk.ui.windows.getShared('J_RETURN');
                    if (value) {
                        if (value.selectedMode == 2) {
                            self.model().ListItems.push(new ListModel({ historyId: '', startYm: value.startDate, endYm: 999912 }));
                        } else {
                            let getMaxDate = nts.uk.ui.windows.getShared('C_MAXDATE');
                            let hist = getMaxDate.historyId;
                            
                        }
                    }
                });
        }

        openKDialog() {
        }

        openMDialog() {
            let self = this, currentItemList = self.model().currentItemList();
            if (!!currentItemList) {
                nts.uk.ui.windows.setShared('M_BASEYM', currentItemList.startYm);

                nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                    .onClosed(function() {
                        let currentItemGrid = self.model().currentItemGrid();
                        if (currentItemGrid) {
                            let stmtCode = nts.uk.ui.windows.getShared('M_RETURN');
                            currentItemGrid.paymentDetailCode = stmtCode;
                            service.getAllotLayoutName(stmtCode).done(function(stmtName: string) {
                                currentItemGrid.paymentDetailName = stmtName;

                                //update date to igGrid
                                self.model().updateData();
                            }).fail(function(res) {
                                alert(res);
                            });
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
                if (v) {
                    service.getEmployeeDetail(v).done(function(data: Array<any>) {
                        _.forEach(self.GridItems(), function(t) {
                            let item = _.find(data, function(m) {
                                return t.employmentCode == m.employeeCode;
                            });
                            if (item) {
                                t.bonusDetailCode = item.bonusDetailCode;
                                t.paymentDetailCode = item.paymentDetailCode;
                            }
                            else {
                                t.bonusDetailCode = '';
                                t.paymentDetailCode = '';
                            }
                        });
                        //update date to igGrid
                        self.updateData();
                    });
                }
            });
            // get itemMax of ListItem
            service.getAllotEmployeeMaxDate().done(function(itemMax: number) {
                let maxDate: IListModel = _.find(self.ListItems(), function(obj) { return (obj.endYm) == itemMax; });
                nts.uk.ui.windows.setShared("C_MAXDATE", maxDate);
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
        constructor(param: IListModel) {
            this.historyId = param.historyId;
            this.endYm = param.endYm;
            this.startYm = param.startYm;
            this.text = param.startYm + "~" + param.endYm;
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


    /*
    export class ScreenModel {
        // listbox
        itemTotalList: KnockoutObservableArray<TotalModel>;
        itemHeaderList: KnockoutObservableArray<TotalModel>;
        currentItem: KnockoutObservable<TotalModel>;
        itemIggridSelected: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string>;
        maxItem: KnockoutObservable<TotalModel>;
        //selectedCode: KnockoutObservable<string>;
        dataSource: any;
        selectedList: any;

        constructor() {
            let self = this;
            self.selectedCode = ko.observable();
            self.selectedList = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            self.itemTotalList = ko.observableArray([]);
            self.itemHeaderList = ko.observableArray([]);
            self.maxItem = ko.observable(null);
            self.dataSource = ko.observableArray([]);
            self.itemIggridSelected = ko.observable(undefined);
            self.currentItem = ko.observable(new TotalModel({
                historyId: '',
                employmentCode: '',
                paymentDetailCode: '',
                bonusDetailCode: '',
                startYm: '',
                endYm: ''
            }));



            self.selectedCode.subscribe(function(codeChange) {
                self.getHist();
                service.getEmployeeDetail(ko.toJS(codeChange)).done(function(data: Array<IModel>) {
                    let employeeItems: Array<IModel> = [];
                    if (data && data.length > 0) {
                        employeeItems = $("#C_LST_001").igGrid("option", "dataSource");
                        if (employeeItems.length == 0) {
                            employeeItems = data;
                        }
                        for (let i in employeeItems) {
                            let item = employeeItems[i];
                            let itemAllotSetting = _.find(data, function(allot) { return item.employmentCode == allot.employeeCode; });
                            if (itemAllotSetting) {
                                item.paymentDetailCode = itemAllotSetting.paymentDetailCode;
                                item.paymentDetailName = itemAllotSetting.paymentDetailName;
                                item.bonusDetailCode = itemAllotSetting.bonusDetailCode;
                                item.bonusDetailName = itemAllotSetting.bonusDetailName;
                            } else {
                                item.paymentDetailCode = undefined;
                                item.paymentDetailName = undefined;
                                item.bonusDetailCode = undefined;
                                item.bonusDetailName = undefined;
                            }
                        }

                        // Set data to grid
                        $("#C_LST_001").igGrid("option", "dataSource", employeeItems);
                    }

                }).fail(function(res) {
                    //Alert message
                    alert(res);
                });
                self.currentItem(ko.mapping.fromJS(self.getHist(codeChange)));
            });

            // init columns and title for grid
            self.LoadData([]);

            // call first method
            self.start();
        }

        LoadData(itemList) {
            let self = this;
            $("#C_LST_001").igGrid({
                columns: [
                    { headerText: "繧ｳ繝ｼ繝�", key: "employmentCode", dataType: "string", width: "100px" },
                    { headerText: "蜷咲ｧｰ", key: "employmentName", dataType: "string", width: "200px" },
                    { headerText: "", key: "paymentDetailCode", dataType: "string", hidden: true },
                    { headerText: "", key: "paymentDetailName", dataType: "string", hidden: true },
                    { headerText: "", key: "bonusDetailCode", dataType: "string", hidden: true },
                    { headerText: "", key: "bonusDetailName", dataType: "string", hidden: true },
                    {
                        headerText: "邨ｦ荳取�守ｴｰ譖ｸ", key: "paymentDetailCode", dataType: "string", width: "250px", unbound: true,
                        template: '<button class="c-btn-001" onclick="__viewContext.viewModel.viewmodelC.openMDialog()">選択</button><span>${paymentDetailCode} ${paymentDetailName}</span>'
                    },
                    {
                        headerText: "雉樔ｸ取�守ｴｰ譖ｸ", key: "bonusDetailCode", dataType: "string", width: "20%", unbound: true,
                        template: '<button class="c-btn-002" onclick="__viewContext.viewModel.viewmodelC.openMDialog()">選択</button><span>${bonusDetailCode} ${bonusDetailName}</span>'
                    },
                ],
                width: "800px",
                height: "240px",
                primaryKey: "employmentCode",
                dataSource: itemList
            });
        }

        // subscribe iggrid
        currentIggrid(): TotalModel {
            let self = this;
            return _.find(self.itemTotalList(), function(m: IModel) { return m.employmentCode == self.itemIggridSelected(); });
        }

        //find histId to subscribe
        getHist(value: any) {
            let self = this;
            let rtHist = _.find(ko.toJS(self.itemHeaderList()), function(item: IModel) {
                return item.historyId == value;
            });
            return rtHist;
        }

        // start function
        start() {
            var self = this;
            //fill employ data to c_LST_001
            service.getEmployeeName().done(function(data: Array<IModel>) {
                let employeeItem: Array<TotalModel> = [];
                if (data && data.length > 0) {
                    _.forEach(data, function(item) {
                        employeeItem.push({ historyId: item.historyId, employmentCode: item.employmentCode, employmentName: item.employmentName });
                    });
                }
                self.itemTotalList(employeeItem);
                // self.currentItem(employeeItem.employmentName);
                // Set datafor grid
                $("#C_LST_001").igGrid("option", "dataSource", employeeItem);

            }).fail(function(res) {
                alert(res);
            });

            //Get list startDate, endDate of History  
            let totalItem: Array<TotalModel> = [];
            service.getEmployeeAllotHeaderList().done(function(data: Array<IModel>) {
                if (data.length > 0) {
                    _.forEach(data, function(item) {
                        totalItem.push(new TotalModel({ historyId: item.historyId, startEnd: item.startYm + ' ~ ' + item.endYm, endYm: item.endYm, startYm: item.startYm }));
                    });
                    ko.mapping.toJS(self.itemHeaderList(totalItem));
                }
            }).fail(function(res) {
                alert(res);
            });

            service.getAllotEmployeeMaxDate().done(function(itemMax: number) {
                let maxDate: TotalModel = _.find(self.itemHeaderList(), function(obj) { return parseInt(obj.endYm()) == itemMax; });
                self.maxDate = (maxDate.startYm || "").toString();
                self.maxItem(maxDate);
            }).fail(function(res) {
                alert(res);
            });
        }

        //Open dialog Add History
        openJDialog() {
            var self = this;
            var historyScreenType = "1";
            let valueShareJDialog = historyScreenType + "~" + "201701";

            nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);

            nts.uk.ui.windows.sub.modal('../j/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ邏舌★縺托ｼ槫ｱ･豁ｴ霑ｽ蜉�' })
                .onClosed(function() {
                    let returnJDialog: string = nts.uk.ui.windows.getShared('returnJDialog');
                    if (!nts.uk.util.isNullOrUndefined(returnJDialog)) {

                        var modeRadio = returnJDialog.split("~")[0];
                        var returnValue = returnJDialog.split("~")[1];
                        if (returnValue != '') {
                            //                        let employeeAllotSettings = new Array<EmployeeAllotSettingDto>();
                            var items = self.itemTotalList();
                            var addItem;
                            var copItem;
                            if (modeRadio === "2") {
                                addItem = new TotalModel({
                                    historyId: '',
                                    employmentCode: '',
                                    paymentDetailCode: '',
                                    bonusDetailCode: '',
                                    startYm: returnValue,
                                    endYm: '999912',
                                    startEnd: (returnValue + ' ~ ' + '999912')
                                });
                                items.push(addItem);
                            } else {
                                copItem = new TotalModel({
                                    historyId: '',
                                    employmentCode: '',
                                    employmentName: '',
                                    paymentDetailCode: '',
                                    paymentDetailName: '',
                                    bonusDetailCode: '',
                                    bonusDetailName: '',
                                    startYm: returnValue,
                                    endYm: '999912',
                                    startEnd: (returnValue + ' ~ ' + '999912')
                                });
                                self.currentItem().historyId(copItem.historyId());
                                self.currentItem().startYm(returnValue);
                                self.currentItem().endYm('999912');
                                self.currentItem().employmentCode(self.maxItem().historyId());
                                //get employmentName, paymentDetailName, paymentDetailCode
                                service.getAllEmployeeAllotSetting(ko.toJS(self.maxItem().historyId())).done(function(data) {
                                    self.itemListDetail([]);
                                    if (data && data.length > 0) {
                                        _.map(data, function(item: IModel) {
                                            self.itemListDetail.push(new copItem(item.historyId, item.employmentCode, item.employmentName, item.paymentDetailCode
                                                , item.paymentDetailName, item.bonusDetailCode, item.bonusDetailName, item.startYm, item.endYm, item.startEnd));
                                        });
                                        self.currentItem().employmentCode(ko.toJS(self.itemListDetail()[0].employmentCode));
                                        self.currentItem().employmentName(ko.toJS(self.itemListDetail()[0].employmentName));
                                        self.currentItem().paymentDetailCode(ko.toJS(self.itemListDetail()[0].paymentDetailCode));
                                        self.currentItem().paymentDetailName(ko.toJS(self.itemListDetail()[0].paymentDetailName));
                                        self.currentItem().bonusDetailCode(ko.toJS(self.itemListDetail()[0].bonusDetailCode));
                                        self.currentItem().bonusDetailName(ko.toJS(self.itemListDetail()[0].bonusDetailName));
                                    }

                                    $("#C_LST_001").igGrid("option", "dataSource", ko.mapping.toJS(self.itemListDetail));
                                }).fail(function(res) {
                                    alert(res);
                                });

                                items.push(copItem);

                            }

                            self.itemTotalList([]);
                            self.itemTotalList(items);
                        }
                    }
                });
        }

        //Open dialog Edit History
        openKDialog() {
            var self = this;
            //var singleSelectedCode = self.singleSelectedCode().split(';');
            //nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ邏舌★縺托ｼ槫ｱ･豁ｴ邱ｨ髮�' }).onClosed(function(): any {
                //self.start(self.singleSelectedCode());
            });
        }

        openMDialog() {
            var self = this;
            var valueShareMDialog = self.currentItem().startYm();
            //debugger;
            nts.uk.ui.windows.setShared('M_BASEYM', valueShareMDialog);

            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '隴丞ｮ茨ｽｴ�ｽｰ隴厄ｽｸ邵ｺ�ｽｮ鬩包ｽｸ隰夲ｿｽ' }).onClosed(function(): any {
                //get selected code from M dialog
                var stmtCodeSelected = nts.uk.ui.windows.getShared('M_RETURN');
                var stmtCode = ko.mapping.toJS(self.currentItem().paymentDetailCode(stmtCodeSelected));
                //get Name payment Name
                service.getAllotLayoutName(stmtCode).done(function(stmtName: string) {
                    self.currentItem().paymentDetailName(stmtName);

                    //self.LoadData(ko.mapping.toJS(self.itemTotalList(self.currentItem())));
                    debugger;
                }).fail(function(res) {
                    alert(res);
                });

            });
        }

    }


    interface IModel {
        companyCode?: string;
        historyId: string;

        employmentCode?: string;
        employmentName?: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
        bonusDetailCode?: string;
        bonusDetailName?: string;
        startYm?: string;
        endYm?: string;
        startEnd?: string;
    }

    class TotalModel {
        id: string;
        companyCode: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;

        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        paymentDetailCode: KnockoutObservable<string>;
        paymentDetailName: KnockoutObservable<string>;
        bonusDetailCode: KnockoutObservable<string>;
        bonusDetailName: KnockoutObservable<string>;
        startYm: KnockoutObservable<string>;
        endYm: KnockoutObservable<string>;
        startEnd: string;
        constructor(param: IModel) {
            this.companyCode = ko.observable(param.companyCode);
            this.id = param.historyId;
            this.historyId = ko.observable(param.historyId);
            this.startEnd = param.startEnd;
            this.employmentCode = ko.observable(param.employmentCode);
            this.employmentName = ko.observable(param.employmentName);
            this.paymentDetailCode = ko.observable(param.paymentDetailCode);
            this.paymentDetailName = ko.observable(param.paymentDetailName);
            this.bonusDetailCode = ko.observable(param.bonusDetailCode);
            this.bonusDetailName = ko.observable(param.bonusDetailName);
            this.startYm = ko.observable(param.startYm);
            this.endYm = ko.observable(param.endYm);
        }
    }
    */
}

