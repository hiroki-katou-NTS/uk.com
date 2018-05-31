module nts.uk.at.view.kmk003.g {
    export module viewmodel {
        import SettingMethod = a5.SettingMethod;
        import EnumWorkForm = a5.EnumWorkForm;
        import WorkTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeSettingModel;
        export class ScreenModel {

            // Screen data 
            switchOptions: KnockoutObservableArray<any>;
            unitComboBoxOptions: KnockoutObservableArray<any>;
            roundingComboBoxOptions: KnockoutObservableArray<any>;
            switchValue: KnockoutObservable<number>;
            unitValue: KnockoutObservable<number>;
            roundingValue: KnockoutObservable<number>;

            lstRest: KnockoutObservableArray<any>;
            specialLstRest: KnockoutObservableArray<any>;
            actualList: KnockoutObservableArray<any>;

            selectedRest: KnockoutObservable<number>;
            selectedActual: KnockoutObservable<number>;
            dataObject: KnockoutObservable<any>;
            updateRounding : KnockoutObservable<boolean>;
            isFlow: KnockoutObservable<boolean>;
            constructor() {
                let self = this;

                self.switchOptions = ko.observableArray([
                    new Item(1, nts.uk.resource.getText("KMK003_113")),
                    new Item(0, nts.uk.resource.getText("KMK003_114"))
                ]);

                self.unitComboBoxOptions = ko.observableArray([]);
                self.roundingComboBoxOptions = ko.observableArray([]);

                self.switchValue = ko.observable(1);
                self.unitValue = ko.observable(1);
                self.roundingValue = ko.observable(0);

                self.lstRest = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_235')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_236')),
                    new RadioBoxModel(2, nts.uk.resource.getText('KMK003_237'))
                ]);
                self.specialLstRest = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_235')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_236'))
                ]);
                self.actualList = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_239')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_240'))
                ]);

                self.selectedRest = ko.observable(0);
                self.selectedActual = ko.observable(0);
                self.dataObject = ko.observable(null);
                
                self.updateRounding = ko.observable(true);
                //change rounding type when change unit
                self.unitValue.subscribe((v) => {
                    if (v == 4 || v == 6)//case 15 or 30 minute
                    {
                        self.roundingComboBoxOptions(self.getRounding(self.dataObject()));
                    }
                    else {
                        self.roundingComboBoxOptions(self.getSpecialRounding(self.dataObject()));
                    }
                    self.updateRounding.notifySubscribers(self.updateRounding());
                });
                self.isFlow = ko.observable(false);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_G_INPUT_DATA");
                _self.dataObject(dataObject);
                _self.bindingData(dataObject);

                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Binding data
             */
            private bindingData(dataObject: any) {
                let _self = this;

                if (nts.uk.util.isNullOrUndefined(dataObject)) {
                    return;
                }

                //check mode
                if ((dataObject.workForm == EnumWorkForm.FLEX) || ((dataObject.workForm == EnumWorkForm.REGULAR) && (dataObject.settingMethod == SettingMethod.FLOW)))//case flex
                {
                    //check is Flex
                    if (dataObject.workForm != EnumWorkForm.FLEX) {
                        _self.isFlow(true);
                    }
                    //get list enum
                    let arrayUnit: any = [];
                    dataObject.lstEnum.roundingTimeUnit.forEach(function(item: any, index: number) {
                        arrayUnit.push(new Item(index, item.localizedName));
                    });
                    _self.unitComboBoxOptions(arrayUnit);
                    _self.roundingComboBoxOptions(_self.getSpecialRounding(_self.dataObject()));

                    _self.switchValue(dataObject.useRest);
                    _self.unitValue(dataObject.roundUnit);
                    _self.roundingValue(dataObject.roundType);
                    _self.selectedRest(dataObject.calcMethod);
                }
                else {
                    _self.selectedActual(dataObject.actualRest);
                    _self.selectedRest(dataObject.restTimeCalcMethod);
                }
            }
            
            private getRounding(dataObject:any):any
            {
                let arrayRounding: any = [];
                dataObject.lstEnum.rounding.forEach(function(item: any, index: number) {
                        arrayRounding.push(new Item(index, item.localizedName));
                });
                return arrayRounding;
            }

            
            private getSpecialRounding(dataObject:any):any
            {
                let arrayRounding: any = [];
                dataObject.lstEnum.rounding.forEach(function(item: any, index: number) {
                    if (index != 2) {
                        arrayRounding.push(new Item(index, item.localizedName));
                    }
                });
                return arrayRounding;
            }

            /**
             * Save
             */
            public save(): void {
                let _self = this;
                let data: any = _self.dataObject();
                let returnData: any = null;
                if ((data.workForm == EnumWorkForm.FLEX) || ((data.workForm == EnumWorkForm.REGULAR) && (data.settingMethod == SettingMethod.FLOW)))//case flex
                {
                    if (data.workForm == EnumWorkForm.FLEX) {
                        returnData = {
                            //default values
                            useRest: 0,
                            roundUnit: 0,
                            roundType: 0,
                            calcMethod: _self.selectedRest()
                        };    
                    }
                    else {
                        returnData = {
                            useRest: _self.switchValue(),
                            roundUnit: _self.unitValue(),
                            roundType: _self.roundingValue(),
                            calcMethod: _self.selectedRest()
                        };
                    }
                }
                else {
                    returnData = {
                        actualRest: _self.selectedActual(),
                        restTimeCalcMethod: _self.selectedRest()
                    };
                }
                nts.uk.ui.windows.setShared("KMK003_DIALOG_G_OUTPUT_DATA", returnData);
                nts.uk.ui.windows.close();
            }

            /**
             * Close
             */
            public close(): void {
                let self = this;
                nts.uk.ui.windows.setShared("KMK003_DIALOG_G_OUTPUT_DATA", self.dataObject());
                nts.uk.ui.windows.close();
            }

        }
        export class Item {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class RadioBoxModel {
            id: number;
            name: string;

            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
    }
}