// TreeGrid Node
var qpp011;
(function (qpp011) {
    var f;
    (function (f) {
        var ScreenModel = (function () {
            function ScreenModel() {
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
                f.service.findAllResidential().done(function (data) {
                    self.F_LST_001_Data(data);
                    BindTreeGrid("#F_LST_001", self.F_LST_001_Data(), self.F_LST_001_selectedValue);
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                var $F_LST_001 = $("#F_LST_001");
                self.F_LST_001_selectedValue.subscribe(function (newValue) {
                    var selectedRows = _.map($F_LST_001.igTreeGridSelection("selectedRows"), function (row) {
                        if (row != undefined)
                            return row.id;
                    });
                    if (!_.isEqual(selectedRows, newValue)) {
                        $F_LST_001.igTreeGridSelection("clearSelection");
                        newValue.forEach(function (id) {
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
                                rowSelectionChanged: function (evt, ui) {
                                    var selectedRows = ui.selectedRows;
                                    selectedValue(_.map(selectedRows, function (row) {
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
            ScreenModel.prototype.submitDialog = function () {
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        f.ScreenModel = ScreenModel;
        var GridItemModel_F_LST_001 = (function () {
            function GridItemModel_F_LST_001(code, name) {
                this.code = code;
                this.name = name;
            }
            return GridItemModel_F_LST_001;
        }());
        f.GridItemModel_F_LST_001 = GridItemModel_F_LST_001;
    })(f = qpp011.f || (qpp011.f = {}));
})(qpp011 || (qpp011 = {}));
;
