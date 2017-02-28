// TreeGrid Node
module qpp011.d {

    export class ScreenModel {
        //gridlist
        D_LST_001_Data: any;
        columns_D_LST_001: any;
        //currencyeditor
        currencyTaxPayRollMoney: any;
        currencyTaxBonusMoney: any;
        currencyTaxOverDueMoney: any;
        currencyTaxDemandChargeMoyney: any;
        currencyHeadcount: any;
        currencyRetirementBonusAmout: any;
        currencyCityTaxMoney: any;
        currencyPrefectureTaxMoney: any;
        //value 
        taxPayRollMoney: any;
        taxBonusMoney: any;
        taxOverDueMoney: any;
        taxDemandChargeMoyney: any;
        headcount: any;
        retirementBonusAmout: any;
        cityTaxMoney: any;
        prefectureTaxMoney: any;
        yearMonth: any;
        yearMonthToString: any;
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean>;
        //search box 
        filteredData: any;
        D_LST_001_selectedValue: any;
        D_LST_001_selectedResiTaxCode: KnockoutObservable<any>;
        D_LST_001_selectedRegisteredName: KnockoutObservable<any>;
        currentObject: any;
        INP_007_Date: KnockoutObservable<Date>;
        yearInJapanEmpire_LBL_019: any;
        yearInJapanEmpire_LBL_012: any;
        //datepicker
        date_D_INP_007: KnockoutObservable<Date>;

        constructor() {
            var self = this;

            //gridlist data                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
            self.columns_D_LST_001 = [
                { headerText: "resiTaxCode", key: "resiTaxCode", width: "250px", dataType: "string", hidden: true },
                { headerText: "registeredName", key: "registeredName", width: "230px", dataType: "string", formatter: registeredNameText },
            ];
            function registeredNameText(val, row) {
                return row.resiTaxCode + " " + row.registeredName;
            }
            self.D_LST_001_Data = ko.observable([]);
            service.findAllResidential().done(function(data: Array<any>) {
                self.D_LST_001_Data(data);
                BindTreeGrid("#D_LST_001", self.D_LST_001_Data());
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            self.D_LST_001_selectedValue = ko.observableArray([]);
            let D_LST_001 = $("#D_LST_001");
            self.D_LST_001_selectedResiTaxCode = ko.observable();
            self.D_LST_001_selectedRegisteredName = ko.observable();
            self.D_LST_001_selectedValue.subscribe(function(newValue) {
                let selectedRows = _.map(D_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    if (row != undefined)
                        return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    D_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        D_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
                var item = _.find(self.D_LST_001_Data(), function(obj) {
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
            self.prefectureTaxMoney = ko.observable();
            self.yearMonth = ko.observable();
            self.date_D_INP_007 = ko.observable(new Date());
            self.yearMonthToString = ko.observable();
            self.yearInJapanEmpire_LBL_019 = ko.observable();
            self.yearInJapanEmpire_LBL_012 = ko.observable("(" + nts.uk.time.yearmonthInJapanEmpire(self.date_D_INP_007()).toString() + ")");
            self.currentObject.subscribe(function(newValue) {
                self.taxPayRollMoney(newValue ? newValue.taxPayRollMoney : null);
                self.taxBonusMoney(newValue ? newValue.taxBonusMoney : null)
                self.taxOverDueMoney(newValue ? newValue.taxOverDueMoney : null);
                self.taxDemandChargeMoyney(newValue ? newValue.taxDemandChargeMoyney : null);
                self.headcount(newValue ? newValue.headcount : null);
                self.retirementBonusAmout(newValue ? newValue.retirementBonusAmout : null);
                self.cityTaxMoney(newValue ? newValue.cityTaxMoney : null);
                self.prefectureTaxMoney(newValue ? newValue.prefectureTaxMoney : null);
                self.yearMonth(newValue ? newValue.yearMonth : null);
                self.date_D_INP_007(newValue ? new Date(newValue.dueDate) : new Date());
                self.yearMonthToString(newValue ? nts.uk.time.formatYearMonth(self.yearMonth()) : null);
                self.yearInJapanEmpire_LBL_019(newValue ? "(" + nts.uk.time.yearmonthInJapanEmpire(self.yearMonthToString()).toString() + ")" : null);
                self.yearInJapanEmpire_LBL_012("(" + nts.uk.time.yearmonthInJapanEmpire(self.date_D_INP_007()).toString() + ")");
            });
            //currencyeditor

            //Switch


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
                            activeRowChanged: function(evt: any, ui: any) {
                                var selectedRows: any = ui.row;
                                self.D_LST_001_selectedValue([selectedRows.id]);
                            }
                        }]
                });
                $(gridID).setupSearchScroll("igTreeGrid");
            }
            function GetDataFormSelectedRows(selectedResiTaxCode) {
                service.findresidentialTax(selectedResiTaxCode, "201702").done(function(data: any) {
                    self.currentObject(data)
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                })
            }

        }
        UpdateResidentialTax(selectedResiTaxCode) {
            let self = this;
              let residentialTax = new service.model.residentialTax(
                selectedResiTaxCode,
                self.taxPayRollMoney(),
                201702,
                self.taxBonusMoney(),
                self.taxOverDueMoney(),
                self.taxDemandChargeMoyney(),
                self.taxDemandChargeMoyney(),
                new Date(self.date_D_INP_007()),
                self.headcount(),
                self.retirementBonusAmout(),
                self.cityTaxMoney(),
                self.prefectureTaxMoney());
            service.update(residentialTax).done(function(res: any) {
            }).fail(function(res) {
                alert(res.message);
            })
        }
        CreateResidentialTax(selectedResiTaxCode) {
            let self = this;
            let residentialTax = new service.model.residentialTax(
                selectedResiTaxCode,
                self.taxPayRollMoney(),
                201702,
                self.taxBonusMoney(),
                self.taxOverDueMoney(),
                self.taxDemandChargeMoyney(),
                self.taxDemandChargeMoyney(),
                new Date(self.date_D_INP_007()),
                self.headcount(),
                self.retirementBonusAmout(),
                self.cityTaxMoney(),
                self.prefectureTaxMoney());
            service.add(residentialTax).done(function(res: any) {
            }).fail(function(res) {
                alert(res.message);
            })
        }
        submitDialog() {
            let self = this;
            service.findresidentialTax(self.D_LST_001_selectedResiTaxCode(), "201702").done(function(data: any) {
                if (data) {
                    self.UpdateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                } else {
                    self.CreateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                }
            }).fail(function(res) {
                alert(res.message);
            })
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
    export class D_LST_001_ItemModel {
        resiTaxCode: string;
        registeredName: string;

        constructor(resiTaxCode: string, registeredName: string) {
            this.resiTaxCode = resiTaxCode;
            this.registeredName = registeredName;
        }
    }

};
