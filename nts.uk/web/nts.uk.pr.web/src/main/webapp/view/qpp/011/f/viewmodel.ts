// TreeGrid Node
module qpp011.f {
    export class ScreenModel {
        //gridlist
        F_LST_001_Data: any;
        columns_F_LST_001: any;
        F_LST_001_selectedValue: any;
        F_LST_001_columns: any;
        //currencyeditor
        F_LBL_002_Year_Month: any;
        currencyeditor: any;
        F_LBL_003_yearInJapanEmpire: any;
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            //gridlist data
            self.F_LST_001_Data = ko.observable([]);
            self.F_LST_001_selectedValue = ko.observableArray([]);
            self.F_LST_001_columns = [
                { headerText: "resiTaxCode", key: "resiTaxCode", width: "250px", dataType: "string", hidden: true },
                { headerText: "コード/名称", key: "registeredName", width: "230px", dataType: "string", formatter: registeredNameAndCode },
            ];
            function registeredNameAndCode(val, row) {
                return row.resiTaxCode + " " + row.registeredName;
            }
            service.findAllResidential().done(function(data: Array<any>) {
                self.F_LST_001_Data(data);
                BindTreeGrid("#F_LST_001", self.F_LST_001_Data(), self.F_LST_001_selectedValue);
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            let $F_LST_001 = $("#F_LST_001");
            self.F_LST_001_selectedValue.subscribe(function(newValue) {
                let selectedRows = _.map($F_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    if (row != undefined)
                        return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    $F_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        $F_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
            });
            function BindTreeGrid(gridID, Data, selectedValue) {
                $(gridID).igTreeGrid({
                    width: "450px",
                    height: "300px",
                    dataSource: Data,
                    autoGenerateColumns: false,
                    primaryKey: "resiTaxCode",
                    columns: self.F_LST_001_columns,
                    childDataKey: "files",
                    initialExpandDepth: 2,
                    features: [
                        {
                            name: "Selection",
                            multipleSelection: true,
                            rowSelectionChanged: function(evt: any, ui: any) {
                                var selectedRows: Array<any> = ui.selectedRows;
                                selectedValue(_.map(selectedRows, function(row) {
                                    return row.id;
                                }));
                            }
                        },
                        {
                            name: "RowSelectors",
                            enableCheckBoxes: true,
                            checkBoxMode: "biState",
                            enableSelectAllForPaging: true,
                            enableRowNumbering: false
                        }]
                });
                $(gridID).setupSearchScroll("igTreeGrid");
            }
            //currencyeditor
            self.currencyeditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "USD",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //Switch
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '納付先別' },
                { code: '2', name: '個人明細別' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.F_LBL_002_Year_Month = nts.uk.sessionStorage.getItemAndRemove("TargetDate").value;
            self.F_LBL_003_yearInJapanEmpire = ko.observable();
            self.F_LBL_003_yearInJapanEmpire("(" + nts.uk.time.yearmonthInJapanEmpire(self.F_LBL_002_Year_Month).toString() + ")");

        }
        submitDialog() {
            nts.uk.ui.windows.close();
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
    export class GridItemModel_F_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
};
