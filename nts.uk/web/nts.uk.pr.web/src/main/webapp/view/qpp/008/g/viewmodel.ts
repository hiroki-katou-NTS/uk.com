module qpp008.g.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        /*checkBox*/
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        /*switchButton*/
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);

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
                    _isDataBound = true;
                } else {
                    return;
                }

                var i, grid = ui.owner,
                    ds = grid.dataSource,
                    data = ds.data(),
                    dataLength = data.length;

                for (i = 0; i < dataLength; i++) {
                    if (_checkAll) {
                        if (data[i]["UnitPrice"] * data[i]["UnitsInStock"] < 15000) {
                            data[i]["IsPromotion"] = _checkAllValue;
                        }
                    } else if (data[i]["UnitPrice"] * data[i]["UnitsInStock"] < 15000) {
                        data[i]["IsPromotion"] = true;
                    }
                    else {
                        data[i]["IsPromotion"] = false;
                    }
                }
                _checkAll = false;
            });

            $("#grid10").on("iggridupdatingeditrowended", function(event, ui) {
                var unitPrice = ui.values["UnitPrice"];
                var unitsInStock = ui.values["UnitsInStock"];
                var totalValue = (unitPrice * unitsInStock) || ui.values["Total"];
                var x = _.find($(event.target).find(".ui-iggrid-modifiedrecord").children(), function(cell) { return $(cell).attr("aria-describedby").indexOf("UnitPrice") >= 0 })
                $(x).css("backgroundColor": "red");
                //                $("#grid10").igGridUpdating("setCellValue", ui.rowID, "Total", totalValue);

                if (totalValue < 1000) {
                    $("#grid10").igGridUpdating("setCellValue", ui.rowID, "IsPromotion", true);
                }
                else {
                    $("#grid10").igGridUpdating("setCellValue", ui.rowID, "IsPromotion", false);
                }
            });

            //instantiation
            var iggridData = [];
            var str = ['a0', 'b0', 'c0', 'd0', 'eo', 'f0', 'g0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 41; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    var codeUnisInStock = Math.floor((Math.random() * 20) + 3);
                    var UnitPrice = Math.floor((Math.random() * 5000) + 150);
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
                    {
                        headerText: "Total Price", key: "Total", dataType: "number", unbound: true, format: "currency",
                        formula: function CalculateTotal(data, grid) { return data["UnitPrice"] * data["UnitsInStock"]; }
                    }
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
                            {
                                columnKey: "Total",
                                editorType: "numeric",
                                readOnly: true
                            },
                            {
                                columnKey: "IsPromotion",
                                editorType: "checkbox",
                                readOnly: false

                            },
                            {
                                columnKey: "UnitsInStock",
                                required: true
                            },
                            {
                                columnKey: "UnitPrice",
                                required: true
                            }
                        ]
                    }
                ]
            });
            $("#cb1").on("click", function(event, ui) {
                var value = $(this).is(":checked");
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
            var self = this;

            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
    }
}