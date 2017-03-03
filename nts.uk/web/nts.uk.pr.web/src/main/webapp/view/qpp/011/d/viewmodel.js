// TreeGrid Node
var qpp011;
(function (qpp011) {
    var d;
    (function (d) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //gridlist data                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
                self.columns_D_LST_001 = [
                    { headerText: "resiTaxCode", key: "resiTaxCode", width: "250px", dataType: "string", hidden: true },
                    { headerText: "コード/名称", key: "registeredName", width: "230px", dataType: "string", formatter: registeredNameAndCode },
                ];
                function registeredNameAndCode(val, row) {
                    return row.resiTaxCode + " " + row.registeredName;
                }
                self.D_LST_001_selectedValue = ko.observableArray([]);
                self.D_LST_001_Data = ko.observable([]);
                d.service.findAllResidential().done(function (data) {
                    self.D_LST_001_Data(data);
                    BindTreeGrid("#D_LST_001", self.D_LST_001_Data());
                    if (self.D_LST_001_Data().length > 0)
                        self.D_LST_001_selectedValue([self.D_LST_001_Data()[0].resiTaxCode]);
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                var D_LST_001 = $("#D_LST_001");
                self.D_LST_001_selectedResiTaxCode = ko.observable();
                self.D_LST_001_selectedRegisteredName = ko.observable();
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
                        return obj.resiTaxCode == self.D_LST_001_selectedValue();
                    });
                    if (item != undefined) {
                        self.D_LST_001_selectedResiTaxCode(item.resiTaxCode);
                        self.D_LST_001_selectedRegisteredName(item.registeredName);
                        GetDataFormSelectedRows(item.resiTaxCode);
                    }
                });
                //crr
                self.currentObject = ko.observable();
                self.taxPayRollMoney = ko.observable();
                self.taxBonusMoney = ko.observable();
                self.taxOverDueMoney = ko.observable();
                self.taxDemandChargeMoyney = ko.observable();
                self.headcount = ko.observable();
                self.retirementBonusAmout = ko.observable();
                self.cityTaxMoney = ko.observable();
                self.address = ko.observable();
                self.prefectureTaxMoney = ko.observable();
                self.date_D_INP_007 = ko.observable(new Date());
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
                function BindTreeGrid(gridID, Data) {
                    $(gridID).igTreeGrid({
                        width: "280px",
                        height: "350px",
                        dataSource: Data,
                        autoGenerateColumns: false,
                        primaryKey: "resiTaxCode",
                        columns: self.columns_D_LST_001,
                        childDataKey: "files",
                        initialExpandDepth: 2,
                        features: [
                            {
                                name: "Selection",
                                mode: "row",
                                activeRowChanged: function (evt, ui) {
                                    var selectedRows = ui.row;
                                    self.D_LST_001_selectedValue([selectedRows.id]);
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
    })(d = qpp011.d || (qpp011.d = {}));
})(qpp011 || (qpp011 = {}));
;
