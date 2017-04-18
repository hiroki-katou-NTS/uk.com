module qpp008.g.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        firstLoad: KnockoutObservable<boolean>;
        /*checkBox*/
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        /*switchButton*/
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<any>;

        itemCategoryChecked: KnockoutObservableArray<ItemModel>;
        selectedItemCategoryCode: KnockoutObservableArray<number>;

        confirmedSqueezingSwitch: KnockoutObservableArray<ItemModel>;
        selectedconfirmedCode: KnockoutObservable<number>;

        detailsEmployerList: KnockoutObservableArray<ItemModel>;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
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
            var self = this;
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            /*switchButton*/
            self.roundingRules = ko.observableArray([
                { code: '1', name: 'すべて' },
                { code: '2', name: '未確認' },
                { code: '3', name: '確認済み' }
            ]);
            /*iggrid*/
            self.firstLoad = ko.observable(true);
            var _isDataBound = false;
            var _checkAll = false;
            var _checkAllValue = false;
            // event

            $("#grid10").on("iggridupdatingdatadirty", function(event, ui) {
                $("#grid10").igGrid("saveChanges");
                return false;
            });

            $("#grid10").on("iggriddatabound", function(event, ui) {
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
                        if (data[i]["UnitPrice"] < 100 || data[i]["UnitPrice"] > 270) {
                            data[i]["IsPromotion"] = _checkAllValue;
                        }
                    }
                    else if (self.firstLoad()) {
                        if (data[i]["UnitPrice"] < 100 || data[i]["UnitPrice"] > 270) {
                            data[i]["IsPromotion"] = true;
                        }
                        //                    else {
                        //                        data[i]["IsPromotion"] = false;
                        //                    }
                    }

                    //                _checkAll = false;
                }
                self.firstLoad = ko.observable(false);
            });
            //Bind after initialization  
            //            $(document).delegate("#grid10", "iggridupdatingeditcellstarted", function(evt, ui) {
            //                alert(ui.columnIndex);
            //            };
            $(document).delegate("#grid10", "iggridupdatingeditrowstarting", function(evt: any, ui: any) {
                let $cell = $($("#grid10").igGrid("cellById", ui.rowID, "UnitPrice"));
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
                //var x = _.find($(event.target).find(".ui-iggrid-modifiedrecord").children(), function(cell) { return $(cell).attr("aria-describedby").indexOf("UnitPrice") >= 0 })
                _.forEach(ui.owner.dataSource.dataView(), function(item, index) {
                    if (item["UnitPrice"] > 270) {
                        let cell1 = $("#grid10").igGrid("cellById", item["ProductID"], "UnitPrice");
                        $(cell1).addClass('red').attr('data-class', 'red');
                        $(window).on('resize', () => { $(cell1).addClass('red'); });
                        //item["IsPromotion"] = true;
                    } else if (item["UnitPrice"] < 100) {
                        let cell1 = $("#grid10").igGrid("cellById", item["ProductID"], "UnitPrice");
                        $(cell1).addClass('yellow').attr('data-class', 'yellow');
                        $(window).on('resize', () => { $(cell1).addClass('yellow'); });

                        //item["IsPromotion"] = true;
                    } else {
                        //item["IsPromotion"] = false;
                    }
                });
            });

            //            $("#grid10").on("iggridupdatingeditrowended", function(event, ui) {
            //                if (ui.update) {
            //
            //                    var unitPrice = ui.values["UnitPrice"];
            //                    var unitsInStock = ui.values["UnitsInStock"];
            //                    var totalValue = (unitPrice * unitsInStock) || ui.values["Total"];
            //
            //                    if (totalValue < 1000) {
            //                        $("#grid10").igGridUpdating("setCellValue", ui.rowID, "IsPromotion", true);
            //                    }
            //                    else {
            //                        $("#grid10").igGridUpdating("setCellValue", ui.rowID, "IsPromotion", false);
            //                    }
            //                }
            //            });

            //instantiation
            let iggridData = new Array();
            let str = ['a0', 'b0', 'c0', 'd0', 'eo', 'f0', 'g0'];
            for (let j = 0; j < 4; j++) {
                for (let i = 1; i < 41; i++) {
                    let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    let codeUnisInStock = Math.floor((Math.random() * 20) + 3);
                    let UnitPrice = Math.floor((Math.random() * 5000) + 150);
                    iggridData.push({ "ProductID": i, "ProductName": code, "UnitsInStock": 100, "UnitPrice": i * 15, "PromotionExpDate": new Date("2017/05/05"), "IsPromotion": true, "Total": 1000 });
                }
            }

            $("#grid10").igGrid({
                primaryKey: "ProductID",
                dataSource: iggridData,//Datasources
                width: "100%",
                autoGenerateColumns: false,
                dataSourceType: "json",
                responseDataKey: "results",
                columns: [
                    { headerText: "Product ID", key: "ProductID", dataType: "number" },
                    { headerText: "Product Name", key: "ProductName", dataType: "string" },
                    { headerText: "Units in Stock", key: "UnitsInStock", dataType: "number" },
                    { headerText: "Unit Price", key: "UnitPrice", dataType: "number", format: "currency", columnCssClass: "colStyle" },
                    {
                        headerText: "Promotion Exp Date", key: "PromotionExpDate", dataType: "date", unbound: true, format: "date",
                        unboundValues: [new Date("4/24/2012"), new Date("8/24/2012"), new Date("6/24/2012"), new Date("7/24/2012"), new Date("9/24/2012"), new Date("10/24/2012"), new Date("11/24/2012")]
                    },
                    { headerText: "Is Promotion<br/><input type='checkbox' id='cb1'/>", key: "IsPromotion", dataType: "bool", unbound: true, format: "checkbox" },
//                    {
//                        headerText: "Total Price", key: "Total", dataType: "number", unbound: true, format: "currency",
//                        formula: function CalculateTotal(data: any, grid: any) { return data["UnitPrice"] * data["UnitsInStock"]; }
//                    }
                ],
                features:
                [
                    {
                        name: "Responsive",
                        enableVerticalRendering: false,
                        columnSettings: [
                            {
                                columnKey: "ProductID",
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
                                columnKey: "ProductID",
                                readOnly: true
                            },
//                            {
//                                columnKey: "Total",
//                                editorType: "numeric",
//                                readOnly: true
//                            },
                            {
                                columnKey: "ProductName",
                                readOnly: true
                            },
                            {
                                columnKey: "UnitsInStock",
                                readOnly: true
                            },
                            {
                                columnKey: "UnitPrice",
                                readOnly: true
                            },
                            {
                                columnKey: "IsPromotion",
                                editorType: "checkbox",
                                readOnly: false

                            },
                            {
                                columnKey: "UnitPrice",
                                readOnly: true
                            }
                        ]
                    }
                ]
            });
            //checkBox
            $("#cb1").on("click", function(event, ui) {
                let value = $(this).is(":checked");
                _checkAll = true;
                _checkAllValue = value;
                //                var dataSource = $("#grid10").igGrid("option", "dataSource");
                //                _.forEach(dataSource, function(item){
                //                    item["IsPromotion"] = value;
                //                });
                //                $("#grid10").igGrid("option", "dataSource", dataSource);
                $("#grid10").igGrid("dataBind");
            });
            self.selectedRuleCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            let self = this;

            let dfd = $.Deferred();
            dfd.resolve();

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
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        classification: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        comparisonDate1: KnockoutObservable<number>;
        comparisonDate2: KnockoutObservable<number>;
        difference: KnockoutObservable<number>;
        reasonDifference: KnockoutObservable<string>;
        registrationStatus1: KnockoutObservable<string>;
        registrationStatus2: KnockoutObservable<string>;
        confirmedStatus: KnockoutObservable<boolean>;
        constructor(data: any) {
            let self = this;
            if (data) {
                self.employeeCode = ko.observable(data.employeeCode);
                self.employeeName = ko.observable(data.employeeName);
                self.classification = ko.observable(data.classification);
                self.itemName = ko.observable(data.itemName);
                self.comparisonDate1 = ko.observable(data.comparisonDate1);
                self.comparisonDate2 = ko.observable(data.comparisonDate2);
                self.difference = ko.observable(data.difference);
                self.reasonDifference = ko.observable(data.reasonDifference);
                self.registrationStatus1 = ko.observable(data.registrationStatus1);
                self.registrationStatus2 = ko.observable(data.registrationStatus2);
                self.confirmedStatus = ko.observable(data.confirmedStatus);
            } else {
                self.employeeCode = ko.observable(data.employeeCode);
                self.employeeName = ko.observable(data.employeeName);
                self.classification = ko.observable(data.classification);
                self.itemName = ko.observable(data.itemName);
                self.comparisonDate1 = ko.observable(data.comparisonDate1);
                self.comparisonDate2 = ko.observable(data.comparisonDate2);
                self.difference = ko.observable(data.difference);
                self.reasonDifference = ko.observable(data.reasonDifference);
                self.registrationStatus1 = ko.observable(data.registrationStatus1);
                self.registrationStatus2 = ko.observable(data.registrationStatus2);
                self.confirmedStatus = ko.observable(data.confirmedStatus);
            }
        }
    }
}