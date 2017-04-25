module qpp008.g.viewmodel {
    export class ScreenModel {
        detailDifferentList: KnockoutObservableArray<DetailsEmployeer>;
        selectedDetailDiff: KnockoutObservable<number>;
        firstLoad: KnockoutObservable<boolean>;
        /*checkBox*/
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        itemCategoryChecked: KnockoutObservableArray<ItemModel>;
        selectedItemCategoryCode: KnockoutObservableArray<number>;

        confirmedSqueezingSwitch: KnockoutObservableArray<ItemModel>;
        selectedconfirmedCode: KnockoutObservable<number>;

        detailsEmployerList: KnockoutObservableArray<ItemModel>;

        constructor() {
            let self = this;
            self.detailDifferentList = ko.observableArray([]);
            self.selectedDetailDiff = ko.observable(0);
            self.initIggrid(self.detailDifferentList);
            self.itemCategoryChecked = ko.observableArray([
                new ItemModel(0, '支給'),
                new ItemModel(1, '控除'),
                new ItemModel(2, '記事'),
            ]);
            self.selectedItemCategoryCode = ko.observableArray([0, 1, 2])

            self.confirmedSqueezingSwitch = ko.observableArray([
                new ItemModel(0, 'すべて'),
                new ItemModel(1, '未確認'),
                new ItemModel(2, '確認済み'),
            ]);
            self.selectedconfirmedCode = ko.observable(0);

            /*checkBox*/
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);



        }

        initIggrid(data: KnockoutObservableArray<DetailsEmployeer>) {
            let self = this;
            /*iggrid*/
            self.firstLoad = ko.observable(true);
            let _isDataBound = false;
            let _checkAll = false;
            let _checkAllValue = false;
            // event

            $("#grid10").on("iggridupdatingdatadirty", function(event: any, ui: any) {
                $("#grid10").igGrid("saveChanges");
                return false;
            });

            $("#grid10").on("iggriddatabound", function(event: any, ui: any) {
                if (_checkAll) {
                    //                    return true;
                } else if (_isDataBound === false) {
                    _isDataBound = false;
                } else {
                    return;
                }
                let i = ui.owner;
                let grid = ui.owner;
                let ds = grid.dataSource;
                let data = ds.data();
                let dataLength = data.length;

                for (i = 0; i < dataLength; i++) {
                    if (_checkAll) {
                        if (data[i]["difference"] != 0) {
                            data[i]["confirmedStatus"] = _checkAllValue;
                        }
                    }
                    else if (self.firstLoad()) {
                        if (data[i]["difference"] != 0) {
                            data[i]["confirmedStatus"] = true;
                        }
                    }

                }
                self.firstLoad = ko.observable(false);
            });

            $(document).delegate("#grid10", "iggridupdatingeditrowstarting", function(evt: any, ui: any) {
                let $cell = $($("#grid10").igGrid("cellById", ui.rowID, "difference"));
                //var $cell1 = $($("#grid10").igGrid("cellAt", 4, ui.rowID));
                if (!($cell.hasClass("red") || $cell.hasClass("yellow"))) {
                    return false;

                }
                //return the triggered event
                evt;
                // get reference to igGridUpdating widget
                ui.owner;
                // to get key or index of row
                ui.rowID;
                // check if that event is raised while new-row-adding
                ui.rowAdding;
            });
            //change color by delegate
            $(document).delegate("#grid10", "iggriddatarendered", function(evt: any, ui: any) {
                _.forEach(ui.owner.dataSource.dataView(), function(item, index) {
                    item["confirmedStatus"] = false;
                    if (item["difference"] < 0) {
                        let cell1 = $("#grid10").igGrid("cellById", item["id"], "difference");
                        $(cell1).addClass('red').attr('data-class', 'red');
                        $(window).on('resize', () => { $(cell1).addClass('red'); });
                        item["confirmedStatus"] = true;
                    } else if (item["difference"] > 0) {
                        let cell1 = $("#grid10").igGrid("cellById", item["id"], "difference");
                        $(cell1).addClass('yellow').attr('data-class', 'yellow');
                        $(window).on('resize', () => { $(cell1).addClass('yellow'); });
                        item["confirmedStatus"] = true;
                    }
                });
            });

            $("#grid10").igGrid({
                primaryKey: "id",
                dataSource: data(),
                width: "100%",
                autoGenerateColumns: false,
                responseDataKey: "id",
                columns: [
                    { headerText: "", key: "id", dataType: "number", hidden: true},
                    { headerText: "社員コード", key: "employeeCode", dataType: "string" },
                    { headerText: "氏名", key: "employeeName", dataType: "string" },
                    { headerText: "区分", key: "categoryAtr", dataType: "string" },
                    { headerText: "項目名", key: "itemName", dataType: "string" },
                    { headerText: "比較年月1", key: "comparisonDate1", dataType: "number", format: "currency" },
                    { headerText: "比較年月2", key: "comparisonDate2", dataType: "number", format: "currency" },
                    { headerText: "差額", key: "difference", dataType: "number", format: "currency", columnCssClass: "colStyle" },
                    {
                        headerText: "差異理由",
                        key: "reasonDifference",
                        dataType: "string",
                        width: "200px",
                        unbound: true,
                        template: "<input type='text' value='${reasonDifference}' class='reasonDifference-text'/>"
                    },
                    { headerText: "登録状況（比較年月１）", key: "registrationStatus1", dataType: "string" },
                    { headerText: "登録状況（比較年月2）", key: "registrationStatus2", dataType: "string" },
                    {
                        headerText: "確認済",
                        key: "confirmedStatus",
                        dataType: "bool",
                        width: "70px",
                        unbound: true,
                        template: "<input type='checkbox' value='${confirmedStatus}' class='confirmedStatus-checkBox'/>"
                    }
                ],
                features:
                [
                    {
                        name: "Paging",
                        type: "local",
                        pageIndexChanged: function(evt: any, ui: any) {

                        },
                        pageSize: 20
                    },
                    {
                        name: "Updating",
                        editMode: "none",
                        enableAddRow: false,
                        enableDeleteRow: false,
                        columnSettings: [
                            {
                                columnKey: "id",
                                readOnly: true,
                            },
                            {
                                columnKey: "employeeCode",
                                readOnly: true
                            },
                            {
                                columnKey: "employeeName",
                                readOnly: true
                            },
                            {
                                columnKey: "categoryAtr",
                                readOnly: true
                            },
                            {
                                columnKey: "itemName",
                                readOnly: true
                            },
                            {
                                columnKey: "comparisonDate1",
                                readOnly: true
                            },
                            {
                                columnKey: "comparisonDate2",
                                readOnly: true
                            },
                            {
                                columnKey: "difference",
                                readOnly: true
                            },
                            {
                                columnKey: "reasonDifference",
                                editorType: "text",
                                readOnly: false
                            },
                            {
                                columnKey: "registrationStatus1",
                                readOnly: true
                            },
                            {
                                columnKey: "registrationStatus2",
                                readOnly: true
                            },
                            {
                                columnKey: "confirmedStatus",
                                editorType: "checkbox",
                                readOnly: false
                            },
                        ]
                    }, 
                ]
            });
            //checkBox
            $("#cb1").on("click", function(event, ui) {
                let value = $(this).is(":checked");
                _checkAll = true;
                _checkAllValue = value;
                $("#grid10").igGrid("dataBind");
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let processingYMEarlier = 201702;
            let processingYMLater = 201703;
            let dfd = $.Deferred();
            self.detailDifferentList([]);
            service.getDetailDifferentials(processingYMEarlier, processingYMLater).done(function(data: any) {
                let mapDetailDiff = _.map(data, function(item, i) {
                    return new DetailsEmployeer(item, i);
                });
                self.detailDifferentList(mapDetailDiff);
                self.initIggrid(self.detailDifferentList);
                dfd.resolve();
            }).fail(function(error: any) {

            });
            return dfd.promise();
        }

        loadDataGrid() {
            let self = this;
            let processingYMEarlier = 201702;
            let processingYMLater = 201703;
            self.detailDifferentList([]);
            service.getDetailDifferentials(processingYMEarlier, processingYMLater).done(function(data: any) {
                let mapDetailDiff = _.map(data, function(item, i) {
                    return new DetailsEmployeer(item, i);
                });
                self.detailDifferentList(mapDetailDiff);
            }).fail(function(error: any) {

            });
        }
    }

    class ItemModel {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            let self = this;
            self.code = code;
            self.name = name;
        }
    }

    class DetailsEmployeer {
        id: number;
        employeeCode: string;
        employeeName: string;
        categoryAtr: string;
        itemName: string;
        itemCode: string;
        comparisonDate1: number;
        comparisonDate2: number;
        difference: number;
        reasonDifference: string;
        registrationStatus1: string;
        registrationStatus2: string;
        confirmedStatus: boolean;
        constructor(data: any, id: number) {
            let self = this;
            if (data) {
                self.id = id;
                self.employeeCode = data.employeeCode;
                self.employeeName = data.employeeName;
                self.categoryAtr = data.categoryAtr;
                self.itemName = data.itemName;
                self.itemCode = data.itemCode;
                self.comparisonDate1 = data.comparisonValue1;
                self.comparisonDate2 = data.comparisonValue2;
                self.difference = data.valueDifference;
                self.reasonDifference = data.reasonDifference;
                self.registrationStatus1 = data.registrationStatus1;
                self.registrationStatus2 = data.registrationStatus2;
                self.confirmedStatus = data.confirmedStatus;
            }
        }
    }
}