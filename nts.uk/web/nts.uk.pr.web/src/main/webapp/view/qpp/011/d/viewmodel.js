// TreeGrid Node
var qpp011;
(function (qpp011) {
    var d;
    (function (d) {
        var ScreenModel = (function () {
            function ScreenModel() {
                //gridlist
                this.D_LST_001_Data = ko.observable([]);
                this.D_LST_001_selectedValue = ko.observableArray([]);
                this.D_LST_001_selectedResiTaxCode = ko.observable();
                this.D_LST_001_selectedRegisteredName = ko.observable();
                this.currentObject = ko.observable();
                this.ResidentialData = ko.observable([]);
                this.currentIndex = 0;
                //End Data Variable
                //value 
                this.taxPayRollMoney = ko.observable();
                this.taxBonusMoney = ko.observable();
                this.taxOverDueMoney = ko.observable();
                this.taxDemandChargeMoyney = ko.observable();
                this.address = ko.observable();
                this.headcount = ko.observable();
                this.retirementBonusAmout = ko.observable();
                this.cityTaxMoney = ko.observable();
                this.prefectureTaxMoney = ko.observable();
                //datepicker
                this.date_D_INP_007 = ko.observable(new Date());
                var self = this;
                //gridlist data                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
                self.columns_D_LST_001 = [
                    { headerText: "position", key: "position", width: "30px", dataType: "string", hidden: true },
                    { headerText: "code", key: "code", width: "30px", dataType: "string", hidden: true },
                    { headerText: "コード/名称", key: "displayText", width: "230px", dataType: "string" },
                ];
                function registeredNameAndCode(val, row) {
                    return row.resiTaxCode + " " + row.registeredName;
                }
                //Set Tree Data 
                d.service.findAllResidential().done(function (data) {
                    d.service.getlistLocation().done(function (listLocation) {
                        self.ResidentialData(data);
                        self.ListLocation = listLocation;
                        var ArrayData = CreateDataArray(listLocation, data);
                        self.D_LST_001_Data(ArrayData);
                        BindTreeGrid("#D_LST_001", self.D_LST_001_Data(), self.D_LST_001_selectedValue);
                        if (data.length > 0)
                            self.D_LST_001_selectedValue([data[0].resiTaxCode]);
                    }).fail(function (res) {
                        // Alert message
                        alert(res.message);
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res.message);
                });
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
                var D_LST_001 = $("#D_LST_001");
                self.D_LST_001_selectedValue.subscribe(function (newValue) {
                    var selectedRows = _.map(D_LST_001.igTreeGridSelection("selectedRows"), function (row) {
                        if (row != undefined)
                            return row.id;
                    });
                    if (!_.isEqual(selectedRows, newValue)) {
                        D_LST_001.igTreeGridSelection("clearSelection");
                        newValue.forEach(function (id) {
                            D_LST_001.igTreeGridSelection("selectRowById", id);
                        });
                    }
                    var item = _.find(self.D_LST_001_Data(), function (obj) {
                        return (obj.position == self.D_LST_001_selectedValue() && obj.typeBranch == "Item");
                    });
                    if (item != undefined) {
                        self.D_LST_001_selectedResiTaxCode(item.position);
                        self.D_LST_001_selectedRegisteredName(item.displayText);
                        GetDataFormSelectedRows(item.position);
                    }
                    else {
                        self.D_LST_001_selectedResiTaxCode("");
                        self.D_LST_001_selectedRegisteredName("");
                        GetDataFormSelectedRows(null);
                    }
                });
                self.yearInJapanEmpire_LBL_012 = ko.observable("(" + nts.uk.time.yearmonthInJapanEmpire(self.date_D_INP_007().toString()).toString() + ")");
                self.currentObject.subscribe(function (newValue) {
                    self.taxPayRollMoney(newValue ? newValue.taxPayRollMoney : null);
                    self.taxBonusMoney(newValue ? newValue.taxBonusMoney : null);
                    self.taxOverDueMoney(newValue ? newValue.taxOverDueMoney : null);
                    self.taxDemandChargeMoyney(newValue ? newValue.taxDemandChargeMoyney : null);
                    self.address(newValue ? newValue.address : null);
                    self.headcount(newValue ? newValue.headcount : null);
                    self.retirementBonusAmout(newValue ? newValue.retirementBonusAmout : null);
                    self.cityTaxMoney(newValue ? newValue.cityTaxMoney : null);
                    self.prefectureTaxMoney(newValue ? newValue.prefectureTaxMoney : null);
                    self.date_D_INP_007(newValue ? new Date(newValue.dueDate) : new Date());
                    self.yearInJapanEmpire_LBL_012("(" + nts.uk.time.yearmonthInJapanEmpire(self.date_D_INP_007().toString()).toString() + ")");
                });
                //currencyeditor
                //Switch
                self.D_LBL_018_Year_Month = nts.uk.sessionStorage.getItemAndRemove("TargetDate").value;
                self.D_LBL_019_yearInJapanEmpire = ko.observable();
                self.D_LBL_019_yearInJapanEmpire("(" + nts.uk.time.yearmonthInJapanEmpire(self.D_LBL_018_Year_Month).toString() + ")");
                self.enable = ko.observable(true);
                self.roundingRules = ko.observableArray([
                    { code: '1', name: 'Item1' },
                    { code: '2', name: 'Item2' }
                ]);
                self.selectedRuleCode = ko.observable(1);
                function BindTreeGrid(gridID, Data, selectedValue) {
                    $(gridID).igTreeGrid({
                        width: "280px",
                        height: "350px",
                        dataSource: Data,
                        autoGenerateColumns: false,
                        primaryKey: "position",
                        foreignKey: "child",
                        columns: self.columns_D_LST_001,
                        initialExpandDepth: 2,
                        features: [
                            {
                                name: "Selection",
                                mode: "row",
                                activeRowChanged: function (evt, ui) {
                                    var selectedRows = ui.row;
                                    selectedValue([selectedRows.id]);
                                }
                            }]
                    });
                    $(gridID).setupSearchScroll("igTreeGrid");
                }
                function GetDataFormSelectedRows(selectedResiTaxCode) {
                    var TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
                    d.service.findresidentialTax(selectedResiTaxCode, TargetDate).done(function (data) {
                        self.currentObject(data);
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                }
            }
            ;
            ;
            ScreenModel.prototype.UpdateResidentialTax = function (selectedResiTaxCode) {
                var self = this;
                var TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
                var residentialTax = new d.service.model.residentialTax(selectedResiTaxCode, self.taxPayRollMoney(), TargetDate, self.taxBonusMoney(), self.taxOverDueMoney(), self.taxDemandChargeMoyney(), self.address(), new Date(self.date_D_INP_007().toDateString()), self.headcount(), self.retirementBonusAmout(), self.cityTaxMoney(), self.prefectureTaxMoney());
                d.service.update(residentialTax).done(function (data) {
                    self.currentObject(residentialTax);
                }).fail(function (res) {
                    alert(res.message);
                });
            };
            ScreenModel.prototype.CreateResidentialTax = function (selectedResiTaxCode) {
                var self = this;
                var TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
                var residentialTax = new d.service.model.residentialTax(selectedResiTaxCode, self.taxPayRollMoney(), TargetDate, self.taxBonusMoney(), self.taxOverDueMoney(), self.taxDemandChargeMoyney(), self.address(), new Date(self.date_D_INP_007().toDateString()), self.headcount(), self.retirementBonusAmout(), self.cityTaxMoney(), self.prefectureTaxMoney());
                if (residentialTax.cityTaxMoney && residentialTax.dueDate && residentialTax.prefectureTaxMoney && residentialTax.resimentTaxCode && residentialTax.retirementBonusAmout && residentialTax.taxBonusMoney && residentialTax.taxDemandChargeMoyney && residentialTax.taxOverDueMoney && residentialTax.taxPayRollMoney && residentialTax.yearMonth) {
                    d.service.add(residentialTax).done(function (data) {
                        self.currentObject(residentialTax);
                    }).fail(function (res) {
                        alert(res.message);
                    });
                }
            };
            ScreenModel.prototype.submitDialog = function () {
                var self = this;
                var TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
                if (self.D_LST_001_selectedResiTaxCode()) {
                    d.service.findresidentialTax(self.D_LST_001_selectedResiTaxCode(), TargetDate).done(function (data) {
                        if (data) {
                            self.UpdateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                        }
                        else {
                            self.CreateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                        }
                    }).fail(function (res) {
                        alert(res.message);
                    });
                }
            };
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        d.ScreenModel = ScreenModel;
        var D_LST_001_ItemModel = (function () {
            function D_LST_001_ItemModel(resiTaxCode, registeredName) {
                this.resiTaxCode = resiTaxCode;
                this.registeredName = registeredName;
            }
            return D_LST_001_ItemModel;
        }());
        d.D_LST_001_ItemModel = D_LST_001_ItemModel;
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
        d.TreeItem = TreeItem;
    })(d = qpp011.d || (qpp011.d = {}));
})(qpp011 || (qpp011 = {}));
;
