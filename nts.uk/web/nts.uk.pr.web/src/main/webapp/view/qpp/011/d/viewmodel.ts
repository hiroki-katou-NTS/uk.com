// TreeGrid Node
module qpp011.d {

    export class ScreenModel {
        //gridlist
        D_LST_001_Data: any;
        columns_D_LST_001: any;
        //value 
        taxPayRollMoney: any;
        taxBonusMoney: any;
        taxOverDueMoney: any;
        taxDemandChargeMoyney: any;
        address: any;
        headcount: any;
        retirementBonusAmout: any;
        cityTaxMoney: any;
        prefectureTaxMoney: any;
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
        yearInJapanEmpire_LBL_012: any;

        D_LBL_018_Year_Month: any;
        D_LBL_019_yearInJapanEmpire: any;
        //datepicker
        date_D_INP_007: KnockoutObservable<Date>;

        constructor() {
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
            service.findAllResidential().done(function(data: Array<any>) {
                self.D_LST_001_Data(data);
                BindTreeGrid("#D_LST_001", self.D_LST_001_Data());
                if (self.D_LST_001_Data().length > 0)
                    self.D_LST_001_selectedValue([self.D_LST_001_Data()[0].resiTaxCode]);
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
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
            self.address = ko.observable();
            self.prefectureTaxMoney = ko.observable();
            self.date_D_INP_007 = ko.observable(new Date());
            self.yearInJapanEmpire_LBL_012 = ko.observable("(" + nts.uk.time.yearmonthInJapanEmpire(self.date_D_INP_007().toString()).toString() + ")");
            self.currentObject.subscribe(function(newValue) {
                self.taxPayRollMoney(newValue ? newValue.taxPayRollMoney : null);
                self.taxBonusMoney(newValue ? newValue.taxBonusMoney : null)
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
                            activeRowChanged: function(evt: any, ui: any) {
                                var selectedRows: any = ui.row;
                                self.D_LST_001_selectedValue([selectedRows.id]);
                            }
                        }]
                });
                $(gridID).setupSearchScroll("igTreeGrid");
            }
            function GetDataFormSelectedRows(selectedResiTaxCode) {
                let TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
                service.findresidentialTax(selectedResiTaxCode, TargetDate).done(function(data: any) {
                    self.currentObject(data)
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                })
            }

        }
        UpdateResidentialTax(selectedResiTaxCode) {
            let self = this;
            let TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
            let residentialTax = new service.model.residentialTax(
                selectedResiTaxCode,
                self.taxPayRollMoney(),
                TargetDate,
                self.taxBonusMoney(),
                self.taxOverDueMoney(),
                self.taxDemandChargeMoyney(),
                self.address(),
                new Date(self.date_D_INP_007().toDateString()),
                self.headcount(),
                self.retirementBonusAmout(),
                self.cityTaxMoney(),
                self.prefectureTaxMoney());
            service.update(residentialTax).done(function(data: any) {
                self.currentObject(residentialTax);
            }).fail(function(res) {
                alert(res.message);
            })
        }
        CreateResidentialTax(selectedResiTaxCode) {
            let self = this;
            let TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
            let residentialTax = new service.model.residentialTax(
                selectedResiTaxCode,
                self.taxPayRollMoney(),
                TargetDate,
                self.taxBonusMoney(),
                self.taxOverDueMoney(),
                self.taxDemandChargeMoyney(),
                self.address(),
                new Date(self.date_D_INP_007().toDateString()),
                self.headcount(),
                self.retirementBonusAmout(),
                self.cityTaxMoney(),
                self.prefectureTaxMoney());
            if (residentialTax.cityTaxMoney && residentialTax.dueDate && residentialTax.prefectureTaxMoney && residentialTax.resimentTaxCode && residentialTax.retirementBonusAmout && residentialTax.taxBonusMoney && residentialTax.taxDemandChargeMoyney && residentialTax.taxOverDueMoney && residentialTax.taxPayRollMoney && residentialTax.yearMonth) {
                service.add(residentialTax).done(function(data: any) {
                    self.currentObject(residentialTax);
                }).fail(function(res) {
                    alert(res.message);
                })
            }
        }
        submitDialog() {
            let self = this;
            let TargetDate = self.D_LBL_018_Year_Month = self.D_LBL_018_Year_Month.replace("/", "");
            if (self.D_LST_001_selectedResiTaxCode()) {
                service.findresidentialTax(self.D_LST_001_selectedResiTaxCode(), TargetDate).done(function(data: any) {
                    if (data) {
                        self.UpdateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                    } else {
                        self.CreateResidentialTax(self.D_LST_001_selectedResiTaxCode());
                    }
                }).fail(function(res) {
                    alert(res.message);
                })
            }
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
