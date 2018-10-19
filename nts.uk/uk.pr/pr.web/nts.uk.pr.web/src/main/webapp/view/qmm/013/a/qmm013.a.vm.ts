module nts.uk.pr.view.qmm013.a {

    import model = qmm013.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;

    export module viewModel {
        export class ScreenModel {
            
            // Also display abolition
            isdisplayAbolition: KnockoutObservable<boolean> = ko.observable(true);

            unitPriceNameList: KnockoutObservableArray<ISalaryPerUnitPriceName> = ko.observableArray([]);
            unitPriceDataSelected: KnockoutObservable<SalaryPerUnitPriceData>;
            currentCode: KnockoutObservable<string> = ko.observable(null);
            checkCreate: KnockoutObservable<boolean> = ko.observable(false);
            
            // define gridColumns
            gridColumns: any;
            
            constructor() {
                let self = this;
                
                self.gridColumns = [
                                        { headerText: getText('QMM013_8'), key: 'code', width: 70 , formatter: _.escape },
                                        { headerText: getText('QMM013_9'), key: 'name', width: 220, formatter: _.escape },
                                        { headerText: getText('QMM013_10'), key: 'abolition', width: 50, formatter: v => {
                                            if (v == model.Abolition.ABOLISH) {
                                                return '<div style="text-align: center; max-height: 18px;"><i class="ui-icon ui-icon-check"></i></div>';
                                            }
                                            return '';
                                        } }
                                   ];

                self.unitPriceDataSelected = ko.observable(new SalaryPerUnitPriceData(null));

                self.isdisplayAbolition.subscribe(() => {
                    let oldCode = self.currentCode();
                    
                    self.loadListData().done(function() {
                        let matchData = _.filter(self.unitPriceNameList(), function(o: ISalaryPerUnitPriceName) {
                            return o.code == oldCode;
                        });
                        
                        if(matchData.length > 0) {
                            self.currentCode(oldCode);
                        } else if(self.unitPriceNameList().length > 0) {
                            self.currentCode(self.unitPriceNameList()[0].code);
                        }
                    });
                });

                self.currentCode.subscribe(x => {

                    if(x && (x != "")) {
                        let data: ISalaryPerUnitPriceName = _.filter(self.unitPriceNameList(), function(o: ISalaryPerUnitPriceName) {
                            return o.code == x;
                        })[0];

                        if(data) {
                            self.loadItemData(data.cid, data.code);
                        } else {
                            self.unitPriceDataSelected(new SalaryPerUnitPriceData(null));
                            self.checkCreate(true);
                            $("#A3_2").focus();
                        }
                    } else {
                        self.unitPriceDataSelected(new SalaryPerUnitPriceData(null));
                        self.checkCreate(true);
                        $("#A3_2").focus();
                    }

                    nts.uk.ui.errors.clearAll();
                });
            }//end constructor

            loadItemData(cid: string, code: string) {
                let self = this;
                block.invisible();

                service.getUnitPriceData(cid, code).done(function(data: ISalaryPerUnitPriceData) {
                    if(data) {
                        self.unitPriceDataSelected(new SalaryPerUnitPriceData(data));
                        self.checkCreate(false);

                        setTimeout(function(){
                            $("tr[data-id='" + code + "'] ").focus();
                            $("#A3_3").focus();
                        }, 200);
                    } else {
                        self.unitPriceDataSelected(new SalaryPerUnitPriceData(null));
                        self.checkCreate(true);

                        $("#A3_2").focus();
                    }

                    block.clear();
                }).fail(error => {
                    self.unitPriceDataSelected(new SalaryPerUnitPriceData(null));
                    self.checkCreate(true);

                    $("#A3_2").focus();

                    block.clear();
                });
            }
            
            loadListData(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                block.invisible();
                
                service.getAllUnitPriceName(self.isdisplayAbolition()).done(function(data: Array<ISalaryPerUnitPriceName>) {
                    self.unitPriceNameList(data);
                    
                    if(self.unitPriceNameList().length <= 0) {
                        self.currentCode(null);
                        self.currentCode.valueHasMutated();
                    }
                    
                    block.clear();
                    deferred.resolve();
                }).fail(error => {
                    self.unitPriceNameList([]);
                    self.currentCode(null);
                    self.currentCode.valueHasMutated();
                    
                    block.clear();
                    deferred.resolve();
                });
                
                return deferred.promise();
            }
            
            public create(): void {
                let self = this;
                
                nts.uk.ui.errors.clearAll();
                self.currentCode(null);
            }
            
            public register(): void {
                let self = this;
                let listMessage: Array<string> = [];

                $(".check-validate").trigger("validate");
                
                if(!nts.uk.ui.errors.hasError()) {
                    let command = ko.toJS(self.unitPriceDataSelected);
                    let oldCode = command.salaryPerUnitPriceName.code;

                    command.checkCreate = self.checkCreate();
                    command.salaryPerUnitPriceSetting.code = command.salaryPerUnitPriceName.code;

                    if (_.isEmpty(command.salaryPerUnitPriceName.note)) {
                        command.salaryPerUnitPriceName.note = null;
                    }

                    block.invisible();
                    service.registerUnitPriceData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            self.loadListData().done(function() {
                                let matchData = _.filter(self.unitPriceNameList(), function(o: ISalaryPerUnitPriceName) {
                                    return o.code == oldCode;
                                });

                                if(matchData.length > 0) {
                                    self.currentCode(oldCode);
                                    self.currentCode.valueHasMutated();
                                } else if(self.unitPriceNameList().length > 0) {
                                    self.currentCode(self.unitPriceNameList()[0].code);
                                }
                            });
                        });
                    }).fail(err => {
                        block.clear();
                        
                        if(err.messageId == "Msg_3") {
                            $('#A3_2').ntsError('set', { messageId: "Msg_3" });
                            $("#A3_2").focus();
                        }
                        
                        if(err.messageId == "Msg_358") {
                            $('#A3_3').ntsError('set', { messageId: "Msg_358" });
                            $("#A3_3").focus();
                        }
                    });
                }
            }
            
            public deleteItem(): void {
                let self = this;
                let nextCode = self.getNextCode();
                
                dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let command = ko.toJS(self.unitPriceDataSelected);
                    
                    block.invisible();
                    service.removeUnitPriceData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_16" }).then(() => {
                            self.loadListData().done(function() {
                                if(self.unitPriceNameList().length == 0) {
                                    self.create();
                                } else if(nextCode != null) {
                                    self.currentCode(nextCode);
                                }
                            });
                        });
                    }).fail(err => {
                        block.clear();
                        $("#A3_3").focus();
                    });
                })
            }
            
            public getNextCode(): string {
                let self = this;
                let nextItem: string = null;
                let array: Array<string> = self.unitPriceNameList().map(x => x.code);
                let value = self.currentCode();
                
                if(array.length > 0) {
                    let index = array.indexOf(value);
                    if(index >= 0) {
                        if(index < (array.length - 1)) {
                            nextItem = array[index + 1];
                        } else if(index > 0){
                            nextItem = array[index - 1];
                        }
                    }
                }
                
                return nextItem;
            }
        }

        export class SalaryPerUnitPriceData {
            salaryPerUnitPriceName: KnockoutObservable<SalaryPerUnitPriceName> = ko.observable(null);
            salaryPerUnitPriceSetting: KnockoutObservable<SalaryPerUnitPriceSetting> = ko.observable(null);

            constructor(params: ISalaryPerUnitPriceData) {
                this.salaryPerUnitPriceName(new SalaryPerUnitPriceName(params ? params.salaryPerUnitPriceName : null));
                this.salaryPerUnitPriceSetting(new SalaryPerUnitPriceSetting(params ? params.salaryPerUnitPriceSetting : null));

                nts.uk.ui.errors.clearAll();
            }
        }

        export class SalaryPerUnitPriceName {
            cid: KnockoutObservable<string> = ko.observable(null);
            code: KnockoutObservable<string> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            abolition: KnockoutObservable<number> = ko.observable(null);
            shortName: KnockoutObservable<string> = ko.observable(null);
            integrationCode: KnockoutObservable<string> = ko.observable(null);
            note: KnockoutObservable<string> = ko.observable(null);

            // Custom abolition from number to boolean
            abolitionCustom: KnockoutObservable<boolean>;

            constructor(params: ISalaryPerUnitPriceName) {
                let self = this;

                this.cid(params ? params.cid : null);
                this.code(params ? params.code : null);
                this.name(params ? params.name : null);
                this.abolition(params ? params.abolition : model.Abolition.NOT_ABOLISH);
                this.shortName(params ? params.shortName : null);
                this.integrationCode(params ? params.integrationCode : null);
                this.note(params ? params.note : null);

                self.abolitionCustom = ko.observable(self.abolition() == 1);
                self.abolitionCustom.subscribe(x => {
                    if (x) {
                        self.abolition(1);
                    } else {
                        self.abolition(0);
                    }
                });
            }
        }

        export class SalaryPerUnitPriceSetting {
            cid: KnockoutObservable<string> = ko.observable(null);
            code: KnockoutObservable<string> = ko.observable(null);
            unitPriceType: KnockoutObservable<number> = ko.observable(null);
            settingAtr: KnockoutObservable<number> = ko.observable(null);
            targetClassification: KnockoutObservable<number> = ko.observable(null);
            monthlySalary: KnockoutObservable<number> = ko.observable(null);
            monthlySalaryPerday: KnockoutObservable<number> = ko.observable(null);
            dayPayee: KnockoutObservable<number> = ko.observable(null);
            hourlyPay: KnockoutObservable<number> = ko.observable(null);

            // Covered switch button
            coveredList: KnockoutObservableArray<model.ItemModel>;

            // settingAtr radio button
            settingAtrList: KnockoutObservableArray<model.BoxModel>;

            // unitPriceType switch button
            unitPriceTypeList: KnockoutObservableArray<model.ItemModel>;

            constructor(params: ISalaryPerUnitPriceSetting) {
                let self = this;

                if(params) {
                    this.cid(params.cid ? params.cid : null);
                    this.code(params.code ? params.code : null);
                    this.unitPriceType(params.unitPriceType != null ? params.unitPriceType : model.PerUnitPriceType.HOUR);
                    this.settingAtr(params.settingAtr != null ? params.settingAtr : model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS);
                    this.targetClassification(params.targetClassification != null ? params.targetClassification : model.CoveredAtr.COVERED);
                    this.monthlySalary(params.monthlySalary != null ? params.monthlySalary : model.CoveredAtr.COVERED);
                    this.monthlySalaryPerday(params.monthlySalaryPerday != null ? params.monthlySalaryPerday : model.CoveredAtr.COVERED);
                    this.dayPayee(params.dayPayee != null ? params.dayPayee : model.CoveredAtr.COVERED);
                    this.hourlyPay(params.hourlyPay != null ? params.hourlyPay : model.CoveredAtr.COVERED);
                } else {
                    this.cid(null);
                    this.code(null);
                    this.unitPriceType(model.PerUnitPriceType.HOUR);
                    this.settingAtr(model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS);
                    this.targetClassification(model.CoveredAtr.COVERED);
                    this.monthlySalary(model.CoveredAtr.COVERED);
                    this.monthlySalaryPerday(model.CoveredAtr.COVERED);
                    this.dayPayee(model.CoveredAtr.COVERED);
                    this.hourlyPay(model.CoveredAtr.COVERED);
                }


                self.coveredList = ko.observableArray([
                    new model.ItemModel(model.CoveredAtr.COVERED.toString(), getText('QMM013_24')),
                    new model.ItemModel(model.CoveredAtr.NOT_COVERED.toString(), getText('QMM013_25'))
                ]);

                self.settingAtrList = ko.observableArray([
                    new model.BoxModel(model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS, getText('QMM013_21')),
                    new model.BoxModel(model.SettingClassification.DESIGNATE_FOR_EACH_SALARY_CONTRACT_TYPE, getText('QMM013_22'))
                ]);

                self.unitPriceTypeList = ko.observableArray([
                    new model.ItemModel(model.PerUnitPriceType.HOUR.toString(), getText('QMM013_35')),
                    new model.ItemModel(model.PerUnitPriceType.DAILY_AMOUNT.toString(), getText('QMM013_36')),
                    new model.ItemModel(model.PerUnitPriceType.OTHER.toString(), getText('QMM013_37'))
                ]);
            }
        }

        export interface ISalaryPerUnitPriceData {
            salaryPerUnitPriceName: ISalaryPerUnitPriceName;
            salaryPerUnitPriceSetting: ISalaryPerUnitPriceSetting;
        }

        export interface ISalaryPerUnitPriceName {
            cid: string;
            code: string;
            name: string;
            abolition: number;
            shortName: string;
            integrationCode: string;
            note: string;
        }

        export interface ISalaryPerUnitPriceSetting {
            cid: string;
            code: string;
            unitPriceType: number;
            settingAtr: number;
            targetClassification: number;
            monthlySalary: number;
            monthlySalaryPerday: number;
            dayPayee: number;
            hourlyPay: number;
        }
    }  
}