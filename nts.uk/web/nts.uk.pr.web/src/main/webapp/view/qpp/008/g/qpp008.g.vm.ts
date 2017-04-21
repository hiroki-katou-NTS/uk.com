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
                        let cell1 = $("#grid10").igGrid("cellById", item["employeeCode"], "difference");
                        $(cell1).addClass('red').attr('data-class', 'red');
                        $(window).on('resize', () => { $(cell1).addClass('red'); });
                        item["confirmedStatus"] = true;
                    } else if (item["difference"] > 0) {
                        let cell1 = $("#grid10").igGrid("cellById", item["employeeCode"], "difference");
                        $(cell1).addClass('yellow').attr('data-class', 'yellow');
                        $(window).on('resize', () => { $(cell1).addClass('yellow'); });
                        item["confirmedStatus"] = true;
                    }
                });
            });
            //instantiation
            let iggridData = new Array();
            let str = ['A00000', 'b00000', 'c00000', 'd00000', 'e00000', 'f00000', 'g00000'];
            for (let j = 0; j < 4; j++) {
                for (let i = 1; i < 41; i++) {
                    let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    let itemCode = i < 10 ? "000" + i : "00" + i;
                    let comparisonDate1 = Math.floor(Math.random() * 20);
                    let comparisonDate2 = Math.floor(Math.random() * 20);
                    let difference = comparisonDate2 - comparisonDate1;
                    let reasonDifference = "";
                    let confirmedStatus = false;
                    if (difference < 0) {
                        reasonDifference = "reason of difference.";
                        confirmedStatus = true;
                    }
                    let registrationStatus1 = "registrationStatus1";
                    let registrationStatus2 = "registrationStatus2";

                    iggridData.push(
                        {
                            "itemCode": itemCode,
                            "employeeCode": code, "employeeName": code, "categoryAtr": "支", "itemName": "Mr Person Name",
                            "comparisonDate1": comparisonDate1, "comparisonDate2": comparisonDate2,
                            "difference": difference, "reasonDifference": reasonDifference, "registrationStatus1": registrationStatus1,
                            "registrationStatus2": registrationStatus2, "confirmedStatus": confirmedStatus
                        });
                }
            }

            $("#grid10").igGrid({
                primaryKey: "employeeCode",
                dataSource: iggridData,
                width: "100%",
                autoGenerateColumns: false,
                dataSourceType: "json",
                responseDataKey: "results",
                columns: [
                    { headerText: "itemCode", key: "itemCode", dataType: "string", allowHiding: true, hidden: true },
                    { headerText: "社員コード", key: "employeeCode", dataType: "string" },
                    { headerText: "氏名", key: "employeeName", dataType: "string" },
                    { headerText: "区分", key: "categoryAtr", dataType: "string" },
                    { headerText: "項目名", key: "itemName", dataType: "string" },
                    { headerText: "比較年月1", key: "comparisonDate1", dataType: "number", format: "currency" },
                    { headerText: "比較年月2", key: "comparisonDate2", dataType: "number", format: "currency" },
                    { headerText: "差額", key: "difference", dataType: "number", format: "currency", columnCssClass: "colStyle" },
                    { headerText: "差異理由", key: "reasonDifference", dataType: "string" },
                    { headerText: "登録状況（比較年月１）", key: "registrationStatus1", dataType: "string" },
                    { headerText: "登録状況（比較年月2）", key: "registrationStatus2", dataType: "string" },
                    { headerText: "確認済", key: "confirmedStatus", dataType: "bool", unbound: true, format: "checkbox" },
                ],
                features:
                [
                    {
                        name: "Responsive",
                        enableVerticalRendering: false,
                        columnSettings: [
                            {
                                columnKey: "employeeCode",
                                classes: "ui-hidden-tablet"
                            }
                        ]
                    },
                    {
                        name: "Paging",
                        type: "local",
                        pageIndexChanged: function(evt: any, ui: any) {
                            //                            _.forEach(ui.owner.grid.dataSource.data(), function(item, cellId) {
                            //                                if (item["UnitPrice"] > 270) {
                            //                                    var cell = $("#grid10").igGrid("cellById", 3, cellId);
                            //                                    $(cell).addClass('red');
                            //                                } else if (item["UnitPrice"] < 100) {
                            //                                    var cell1 = $("#grid10").igGrid("cellById", 3, cellId);
                            //                                    $(cell1).css("background-color", "yellow");
                            //                                }
                            //                            });
                        },
                        pageSize: 20
                    },
                    {
                        name: "Updating",
                        editMode: "row",
                        enableAddRow: false,
                        enableDeleteRow: true,
                        columnSettings: [
                            {
                                columnKey: "itemCode",
                                readOnly: true
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
                    }
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
                dfd.resolve();
            }).fail(function(error: any) {

            });
            return dfd.promise();
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
        id: KnockoutObservable<number>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        categoryAtr: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        itemCode: KnockoutObservable<string>;
        comparisonDate1: KnockoutObservable<number>;
        comparisonDate2: KnockoutObservable<number>;
        difference: KnockoutObservable<number>;
        reasonDifference: KnockoutObservable<string>;
        registrationStatus1: KnockoutObservable<string>;
        registrationStatus2: KnockoutObservable<string>;
        confirmedStatus: KnockoutObservable<boolean>;
        constructor(data: any, id: number) {
            let self = this;
            if (data) {
                self.id = ko.observable(id);
                self.employeeCode = ko.observable(data.employeeCode);
                self.employeeName = ko.observable(data.employeeName);
                self.categoryAtr = ko.observable(data.categoryAtr);
                self.itemName = ko.observable(data.itemName);
                self.itemCode = ko.observable(data.itemCode);
                self.comparisonDate1 = ko.observable(data.comparisonDate1);
                self.comparisonDate2 = ko.observable(data.comparisonDate2);
                self.difference = ko.observable(data.valueDifference);
                self.reasonDifference = ko.observable(data.reasonDifference);
                self.registrationStatus1 = ko.observable(data.registrationStatus1);
                self.registrationStatus2 = ko.observable(data.registrationStatus2);
                self.confirmedStatus = ko.observable(data.confirmedStatus);
            }
        }
    }
}