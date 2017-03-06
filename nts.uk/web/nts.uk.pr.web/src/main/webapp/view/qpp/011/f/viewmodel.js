// TreeGrid Node
var qpp011;
(function (qpp011) {
    var f;
    (function (f) {
        var ScreenModel = (function () {
            function ScreenModel() {
                this.ResidentialData = ko.observable([]);
                this.currentIndex = 0;
                var self = this;
                //gridlist data
                self.F_LST_001_Data = ko.observable([]);
                self.F_LST_001_selectedValue = ko.observableArray([]);
                self.F_LST_001_columns = [
                    { headerText: "position", key: "position", width: "30px", dataType: "string", hidden: true },
                    { headerText: "code", key: "code", width: "30px", dataType: "string", hidden: true },
                    { headerText: "コード/名称", key: "displayText", width: "230px", dataType: "string" },
                ];
                function registeredNameAndCode(val, row) {
                    return row.resiTaxCode + " " + row.registeredName;
                }
                f.service.findAllResidential().done(function (data) {
                    f.service.getlistLocation().done(function (listLocation) {
                        self.ResidentialData(data);
                        self.ListLocation = listLocation;
                        var ArrayData = CreateDataArray(listLocation, data);
                        self.F_LST_001_Data(ArrayData);
                        BindTreeGrid("#F_LST_001", self.F_LST_001_Data(), self.F_LST_001_selectedValue);
                    }).fail(function (res) {
                        // Alert message
                        alert(res.message);
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res.message);
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
                        primaryKey: "position",
                        foreignKey: "child",
                        columns: self.F_LST_001_columns,
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
                //Create Tree Data
                function CreateDataArray(TreeData, Data) {
                    self.TreeArray = [];
                    var parentPositionIndex = 0;
                    for (var _i = 0, Data_1 = Data; _i < Data_1.length; _i++) {
                        var Object_1 = Data_1[_i];
                        var positionIndex = CreateTreeBranchs(Object_1.prefectureCode);
                        if (positionIndex) {
                            self.TreeArray.push(new TreeItem(Object_1.resiTaxCode, Object_1.resiTaxName, positionIndex, Object_1.resiTaxCode, Object_1.resiTaxCode + " " + Object_1.registeredName, 'Item'));
                        }
                    }
                    return self.TreeArray;
                }
                function CreateTreeBranchs(prefectureCode) {
                    return CreateBranch(CreateTreeRoot(prefectureCode), prefectureCode);
                }
                function CreateTreeRoot(prefectureCode) {
                    var prefectureCodeInt = parseInt(prefectureCode);
                    var PositionIndex;
                    switch (true) {
                        case (prefectureCodeInt == 1):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "1");
                            break;
                        case (2 <= prefectureCodeInt && prefectureCodeInt <= 7):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "2");
                            break;
                        case (8 <= prefectureCodeInt && prefectureCodeInt <= 14):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "3");
                            break;
                        case (15 <= prefectureCodeInt && prefectureCodeInt <= 23):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "4");
                            break;
                        case (24 <= prefectureCodeInt && prefectureCodeInt <= 30):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "5");
                            break;
                        case (31 <= prefectureCodeInt && prefectureCodeInt <= 35):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "6");
                            break;
                        case (26 <= prefectureCodeInt && prefectureCodeInt <= 39):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "7");
                            break;
                        case (40 <= prefectureCodeInt && prefectureCodeInt <= 47):
                            PositionIndex = AddTreeRootToArray(prefectureCode, "8");
                            break;
                    }
                    return PositionIndex;
                }
                function AddTreeRootToArray(prefectureCode, regionCode) {
                    var positionIndex = self.currentIndex;
                    var returnValue;
                    var root = _.find(self.TreeArray, { 'code': regionCode, 'typeBranch': 'Root' });
                    if (!root) {
                        root = _.find(self.ListLocation, { 'regionCode': regionCode });
                        if (root) {
                            var NewRoot = new TreeItem(root.regionCode, root.regionName, -1, positionIndex + 1, root.regionName, 'Root');
                            self.TreeArray.push(NewRoot);
                            self.currentIndex++;
                            returnValue = self.currentIndex;
                        }
                    }
                    else {
                        returnValue = root.position;
                    }
                    return returnValue;
                }
                function CreateBranch(parrentIndex, prefectureCode) {
                    if (parrentIndex) {
                        var firstBranch = _.find(self.TreeArray, { 'code': prefectureCode, 'typeBranch': 'firstBranch' });
                        var positionIndex = self.currentIndex;
                        var returnValue = void 0;
                        if (!firstBranch) {
                            for (var _i = 0, _a = self.ListLocation; _i < _a.length; _i++) {
                                var object = _a[_i];
                                firstBranch = _.find(object.prefectures, { 'prefectureCode': prefectureCode });
                                if (firstBranch)
                                    break;
                            }
                            if (firstBranch) {
                                var NewBranch = new TreeItem(firstBranch.prefectureCode, firstBranch.prefectureName, parrentIndex, positionIndex + 1, firstBranch.prefectureName, 'firstBranch');
                                self.TreeArray.push(NewBranch);
                                self.currentIndex++;
                                returnValue = self.currentIndex;
                            }
                        }
                        else {
                            returnValue = firstBranch.position;
                        }
                        return returnValue;
                    }
                }
                //End Create Tree Data
                //end set tree data
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
        //Tree Data Class
        var TreeItem = (function () {
            function TreeItem(code, name, child, position, displayText, typeBranch) {
                this.code = code;
                this.name = name;
                this.child = child;
                this.position = position;
                this.displayText = displayText;
                this.typeBranch = typeBranch;
            }
            return TreeItem;
        }());
        f.TreeItem = TreeItem;
    })(f = qpp011.f || (qpp011.f = {}));
})(qpp011 || (qpp011 = {}));
;
