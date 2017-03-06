// TreeGrid Node
var qpp011;
(function (qpp011) {
    var b;
    (function (b) {
        var ScreenModel = (function () {
            function ScreenModel() {
                this.currentIndex = 0;
                var self = this;
                //start radiogroup data
                //B_01
                self.B_SEL_001_RadioItemList = ko.observableArray([
                    new BoxModel(1, '本社'),
                    new BoxModel(2, '法定調書出力用会社')
                ]);
                self.B_SEL_001_selectedId = ko.observable(1);
                self.B_SEL_002_Enable = ko.observable(false);
                self.B_SEL_001_selectedId.subscribe(function (newValue) {
                    if (newValue == 1) {
                        self.B_SEL_002_Enable(false);
                    }
                    else {
                        self.B_SEL_002_Enable(true);
                    }
                });
                //02
                self.C_SEL_002_Enable = ko.observable(false);
                self.C_SEL_001_selectedId = ko.observable(1);
                self.C_SEL_001_selectedId.subscribe(function (newValue) {
                    if (newValue == 1) {
                        self.C_SEL_002_Enable(false);
                    }
                    else {
                        self.C_SEL_002_Enable(true);
                    }
                });
                self.RadioItemList = ko.observableArray([
                    new BoxModel(1, '本社'),
                    new BoxModel(2, '法定調書出力用会社')
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(false);
                self.selectedId.subscribe(function (newValue) {
                    if (newValue == 1) {
                        self.enable(false);
                    }
                    else {
                        self.enable(true);
                    }
                });
                //end radiogroup data
                //start cobobox data
                self.ComboBoxItemList = ko.observableArray([
                    new ComboboxItemModel('0001', 'Item1'),
                    new ComboboxItemModel('0002', 'Item2'),
                    new ComboboxItemModel('0003', 'Item3')
                ]);
                self.ComboBoxCurrentCode = ko.observable(1);
                self.selectedCode = ko.observable('0001');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //end combobox data
                // start gridlist
                //B_LST_001
                //end gridlist
                //start number editer
                self.numbereditor = {
                    value: ko.observable(),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                        grouplength: 0,
                        decimallength: 0,
                        placeholder: "",
                        width: "",
                        textalign: "right"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                self.B_LST_001_Data = ko.observable([]);
                self.ResidentialData = ko.observable([]);
                //end number editer
                //Set Tree Data 
                b.service.findAllResidential().done(function (data) {
                    b.service.getlistLocation().done(function (listLocation) {
                        self.ResidentialData(data);
                        self.ListLocation = listLocation;
                        var ArrayData = CreateDataArray(listLocation, data);
                        self.B_LST_001_Data(ArrayData);
                        BindTreeGrid("#B_LST_001", self.B_LST_001_Data(), self.selectedValue_B_LST_001);
                        BindTreeGrid("#C_LST_001", self.B_LST_001_Data(), self.selectedValue_C_LST_001);
                    }).fail(function (res) {
                        // Alert message
                        alert(res.message);
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res.message);
                });
                self.C_SEL_003_ComboBoxItemList = ko.observableArray([]);
                self.C_SEL_004_ComboBoxItemList = ko.observableArray([]);
                self.C_SEL_003_selectedCode = ko.observable("");
                self.C_SEL_004_selectedCode = ko.observable("");
                b.service.findAllLinebank().done(function (data) {
                    for (var _i = 0, data_1 = data; _i < data_1.length; _i++) {
                        var object = data_1[_i];
                        self.C_SEL_003_ComboBoxItemList.push(new C_SEL_003_ComboboxItemModel(object.bankCode, object.branchCode, object.lineBankCode, object.lineBankName));
                    }
                    if (self.C_SEL_003_ComboBoxItemList.length > 0)
                        self.C_SEL_003_selectedCode(self.C_SEL_003_ComboBoxItemList[0].lineBankCode);
                }).fail(function (res) {
                    alert(res.message);
                });
                b.service.getCurrentProcessingNo().done(function (data) {
                    var yearMonth = data.currentProcessingYm;
                    self.B_INP_001_yearMonth(nts.uk.time.formatYearMonth(yearMonth));
                    self.B_INP_002_yearMonth(nts.uk.time.formatYearMonth(yearMonth));
                    self.C_INP_001_yearMonth(nts.uk.time.formatYearMonth(yearMonth));
                    self.C_INP_002_yearMonth(nts.uk.time.formatYearMonth(yearMonth));
                }).fail(function (res) {
                    alert(res.message);
                });
                self.B_SEL_002_ComboBoxItemList = ko.observableArray([]);
                self.B_SEL_002_selectedCode = ko.observable("");
                self.C_SEL_002_selectedCode = ko.observable("");
                b.service.findallRegalDoc().done(function (data) {
                    for (var _i = 0, data_2 = data; _i < data_2.length; _i++) {
                        var object = data_2[_i];
                        self.B_SEL_002_ComboBoxItemList.push(new B_SEL_002_ComboboxItemModel(object.reganDocCompanyCode, object.reganDocCompanyName));
                    }
                    if (data.length > 0) {
                        self.B_SEL_002_selectedCode(data[0].reganDocCompanyCode);
                        self.C_SEL_002_selectedCode(data[0].reganDocCompanyCode);
                    }
                }).fail(function (res) {
                    alert(res.message);
                });
                self.C_SEL_003_selectedCode.subscribe(function (newValue) {
                    self.C_SEL_004_ComboBoxItemList([]);
                    b.service.findLinebank(newValue).done(function (data) {
                        for (var _i = 0, _a = data.consignors; _i < _a.length; _i++) {
                            var object = _a[_i];
                            self.C_SEL_004_ComboBoxItemList.push(new C_SEL_004_ComboboxItemModel(object.code, object.memo));
                        }
                        if (self.C_SEL_004_ComboBoxItemList.length > 0)
                            self.C_SEL_004_selectedCode(self.C_SEL_004_ComboBoxItemList[0].code);
                    }).fail(function (res) {
                        alert(res);
                    });
                });
                self.columns = [
                    //   { headerText: "p", key: "position", width: "30px", dataType: "string", hidden: true },
                    { headerText: "position", key: "position", width: "30px", dataType: "string", hidden: true },
                    { headerText: "code", key: "code", width: "30px", dataType: "string", hidden: true },
                    { headerText: "コード/名称", key: "displayText", width: "230px", dataType: "string" },
                ];
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
                function BindTreeGrid(gridID, Data, selectedValue) {
                    $(gridID).igTreeGrid({
                        width: "480px",
                        height: "500px",
                        dataSource: Data,
                        autoGenerateColumns: false,
                        primaryKey: "position",
                        foreignKey: "child",
                        columns: self.columns,
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
                self.selectedValue_B_LST_001 = ko.observableArray([]);
                var $B_LST_001 = $("#B_LST_001");
                self.selectedValue_B_LST_001.subscribe(function (newValue) {
                    var selectedRows = _.map($B_LST_001.igTreeGridSelection("selectedRows"), function (row) {
                        if (row)
                            return row.position;
                    });
                    if (!_.isEqual(selectedRows, newValue)) {
                        $B_LST_001.igTreeGridSelection("clearSelection");
                        newValue.forEach(function (id) {
                            $B_LST_001.igTreeGridSelection("selectRowById", id);
                        });
                    }
                });
                self.selectedValue_C_LST_001 = ko.observableArray([]);
                var $C_LST_001 = $("#C_LST_001");
                self.selectedValue_C_LST_001.subscribe(function (newValue) {
                    var selectedRows = _.map($C_LST_001.igTreeGridSelection("selectedRows"), function (row) {
                        if (row != undefined)
                            return row.position;
                    });
                    if (!_.isEqual(selectedRows, newValue)) {
                        $C_LST_001.igTreeGridSelection("clearSelection");
                        newValue.forEach(function (id) {
                            $C_LST_001.igTreeGridSelection("selectRowById", id);
                        });
                    }
                });
                //B
                //001
                self.B_INP_001_yearMonth = ko.observable('2016/12');
                self.B_INP_001_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_B_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_001_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_B_LBL_005 = ko.observable();
                self.yearInJapanEmpire_B_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_001_yearMonth()).toString() + ")");
                //002
                self.B_INP_002_yearMonth = ko.observable('2016/12');
                self.B_INP_002_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_B_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_002_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_B_LBL_008 = ko.observable();
                self.yearInJapanEmpire_B_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_002_yearMonth()).toString() + ")");
                //003
                self.B_INP_003_yearMonth = ko.observable(new Date('2016/12/01'));
                self.B_INP_003_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_B_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_003_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_B_LBL_010 = ko.observable();
                self.yearInJapanEmpire_B_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_003_yearMonth()).toString() + ")");
                //C
                self.C_INP_001_yearMonth = ko.observable('2016/12');
                self.C_INP_001_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_C_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_001_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_C_LBL_005 = ko.observable();
                self.yearInJapanEmpire_C_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_001_yearMonth()).toString() + ")");
                //002
                self.C_INP_002_yearMonth = ko.observable('2016/12');
                self.C_INP_002_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_C_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_002_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_C_LBL_008 = ko.observable();
                self.yearInJapanEmpire_C_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_002_yearMonth()).toString() + ")");
                //003
                self.C_INP_003_yearMonth = ko.observable(new Date('2016/12/01'));
                self.C_INP_003_yearMonth.subscribe(function (newValue) {
                    self.yearInJapanEmpire_C_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_003_yearMonth()).toString() + ")");
                });
                self.yearInJapanEmpire_C_LBL_010 = ko.observable();
                self.yearInJapanEmpire_C_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.C_INP_003_yearMonth()).toString() + ")");
            }
            ScreenModel.prototype.openFDialog = function () {
                var self = this;
                nts.uk.sessionStorage.setItem("TargetDate", self.B_INP_001_yearMonth());
                nts.uk.ui.windows.sub.modal('/view/qpp/011/f/index.xhtml', { height: 560, width: 900, dialogClass: 'no-close' }).onClosed(function () {
                });
            };
            ScreenModel.prototype.openEDialog = function () {
                nts.uk.ui.windows.sub.modal('/view/qpp/011/e/index.xhtml', { height: 180, width: 300, dialogClass: 'no-close' }).onClosed(function () {
                });
            };
            ScreenModel.prototype.openDDialog = function () {
                var self = this;
                nts.uk.sessionStorage.setItem("TargetDate", self.B_INP_001_yearMonth());
                nts.uk.ui.windows.sub.modal('/view/qpp/011/d/index.xhtml', { height: 550, width: 1020, dialogClass: 'no-close' }).onClosed(function () {
                });
            };
            return ScreenModel;
        }());
        b.ScreenModel = ScreenModel;
        var BoxModel = (function () {
            function BoxModel(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
            return BoxModel;
        }());
        b.BoxModel = BoxModel;
        var C_SEL_003_ComboboxItemModel = (function () {
            function C_SEL_003_ComboboxItemModel(bankCode, branchCode, lineBankCode, lineBankName) {
                this.bankCode = bankCode;
                this.branchCode = branchCode;
                this.lineBankCode = lineBankCode;
                this.lineBankName = lineBankName;
            }
            return C_SEL_003_ComboboxItemModel;
        }());
        b.C_SEL_003_ComboboxItemModel = C_SEL_003_ComboboxItemModel;
        var B_SEL_002_ComboboxItemModel = (function () {
            function B_SEL_002_ComboboxItemModel(reganDocCompanyCode, reganDocCompanyName) {
                this.reganDocCompanyCode = reganDocCompanyCode;
                this.reganDocCompanyName = reganDocCompanyName;
            }
            return B_SEL_002_ComboboxItemModel;
        }());
        b.B_SEL_002_ComboboxItemModel = B_SEL_002_ComboboxItemModel;
        var C_SEL_004_ComboboxItemModel = (function () {
            function C_SEL_004_ComboboxItemModel(code, memo) {
                this.code = code;
                this.memo = memo;
            }
            return C_SEL_004_ComboboxItemModel;
        }());
        b.C_SEL_004_ComboboxItemModel = C_SEL_004_ComboboxItemModel;
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
        b.TreeItem = TreeItem;
        // End Tree Data Class
        var ComboboxItemModel = (function () {
            function ComboboxItemModel(code, name) {
                this.code = code;
                this.name = name;
            }
            return ComboboxItemModel;
        }());
        b.ComboboxItemModel = ComboboxItemModel;
    })(b = qpp011.b || (qpp011.b = {}));
})(qpp011 || (qpp011 = {}));
;
