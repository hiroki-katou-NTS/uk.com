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
            
            dataGrid(){
                 var str = ['a0', 'b0', 'c0', 'd0', 'eo', 'f0', 'g0' ];
                for(var i = 1; i < 41; i++){
                        var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                        this.items.push({"Product ID": code, "Product Name": code, "Units in Stock": code, "Unit Price  ": code, "Promotion Exp ": code, "Is Promotion": code, "Total Price": code});
                }    
            }
            
            $("#grid10").igGrid({
                primaryKey: "ProductID",
                dataSource: [],//Datasources
                width: "100%",
                autoGenerateColumns: false,
                dataSourceType: "json",
                responseDataKey: "results",
                columns: [
                    { headerText: "Product ID", key: "ProductID", dataType: "number" },
                    { headerText: "Product Name", key: "ProductName", dataType: "string" },
                    { headerText: "Units in Stock", key: "UnitsInStock", dataType: "number" },
                    { headerText: "Unit Price", key: "UnitPrice", dataType: "number", format: "currency",    columnCssClass: "colStyle" },
                    {
                        headerText: "Promotion Exp Date", key: "PromotionExpDate", dataType: "date", unbound: true, format: "date",
                        unboundValues: [new Date("4/24/2012"), new Date("8/24/2012"), new Date("6/24/2012"), new Date("7/24/2012"), new Date("9/24/2012"), new Date("10/24/2012"), new Date("11/24/2012")]
                    },
                    { headerText: "Is Promotion", key: "IsPromotion", dataType: "bool", unbound: true, format: "checkbox" },
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
                        pageSize: 5
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
                                editorType: "checkbox"
                                
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