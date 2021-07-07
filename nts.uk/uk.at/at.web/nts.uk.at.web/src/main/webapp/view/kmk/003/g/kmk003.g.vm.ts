module nts.uk.at.view.kmk003.g {
    export module viewmodel {
        import SettingMethod = a5.SettingMethod;
        import EnumWorkForm = a5.EnumWorkForm;

        export class ScreenModel {

            // Screen data
            unitComboBoxOptions: KnockoutObservableArray<any>;
            roundingComboBoxOptions: KnockoutObservableArray<any>;
            unitValue: KnockoutObservable<number>;
            roundingValue: KnockoutObservable<number>;

            calcMethodLst: KnockoutObservableArray<any>;
            fixedCalcMethodLst: KnockoutObservableArray<any>;
            useRest: KnockoutObservable<boolean>;

            usePrivateGoOutRest: KnockoutObservable<boolean>;
            useAssoGoOutRest: KnockoutObservable<boolean>;

            selectedCalcMethod: KnockoutObservable<number>;
            selectedFixedCalc: KnockoutObservable<number>;
            dataObject: KnockoutObservable<any>;
            updateRounding: KnockoutObservable<boolean>;
            isFlow: KnockoutObservable<boolean>;
            isFlex: KnockoutObservable<boolean>;

            constructor() {
                let self = this;

                self.unitComboBoxOptions = ko.observableArray([]);
                self.roundingComboBoxOptions = ko.observableArray([]);

                self.unitValue = ko.observable(1);
                self.roundingValue = ko.observable(0);

                self.calcMethodLst = ko.observableArray([
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_235')),
                    new RadioBoxModel(2, nts.uk.resource.getText('KMK003_236')),
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_237'))
                ]);

                self.fixedCalcMethodLst = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_241')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_243'))
                ]);

                self.useRest = ko.observable(false);

                self.usePrivateGoOutRest = ko.observable(false);
                self.useAssoGoOutRest = ko.observable(false);

                self.selectedCalcMethod = ko.observable(0);
                self.selectedFixedCalc = ko.observable(0);
                self.dataObject = ko.observable(null);

                self.updateRounding = ko.observable(true);
                //change rounding type when change unit
                self.unitValue.subscribe((v) => {
                    if (v == 4 || v == 6)//case 15 or 30 minute
                    {
                        self.roundingComboBoxOptions(self.getRounding(self.dataObject()));
                    } else {
                        self.roundingComboBoxOptions(self.getSpecialRounding(self.dataObject()));
                    }
                    self.updateRounding.notifySubscribers(self.updateRounding());
                });
                self.isFlow = ko.observable(false);
                self.isFlex = ko.observable(false);

                _.defer(() => $('#calculate-method').focus());
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

                //get list enum
                let arrayUnits: any = [];
                let _self = this;
                if (nts.uk.util.isNullOrUndefined(dataObject)) {
                    return;
                }
                _self.selectedCalcMethod(dataObject.calcMethod);
                //check mode
                if (dataObject.workForm == EnumWorkForm.FLEX || dataObject.isFlow)// flex or flow
                {
                    //check if Flow
                    if (dataObject.isFlow) {
                        _self.isFlow(true);
                    }
                    //check if flex
                    else {
                        _self.isFlex(true);
                    }
                    dataObject.lstEnum.roundingTimeUnit.forEach(function (item: any, index: number) {
                        arrayUnits.push(new Item(index, item.localizedName));
                    });

                    _self.unitComboBoxOptions(arrayUnits);
                    _self.roundingComboBoxOptions(_self.getSpecialRounding(_self.dataObject()));
                    _self.unitValue(dataObject.roundUnit);
                    _self.roundingValue(dataObject.roundType);
                    _self.selectedCalcMethod(dataObject.calcMethod);
                    _self.selectedFixedCalc(dataObject.fixedCalcMethod);
                    _self.useRest(dataObject.useRest);
                    _self.usePrivateGoOutRest(dataObject.usePrivateGoOutRest);
                    _self.useAssoGoOutRest(dataObject.useAssoGoOutRest);
                }
            }

            private getRounding(dataObject: any): any {
                let arrayRounding: any = [];
                dataObject.lstEnum.rounding.forEach(function (item: any, index: number) {
                    arrayRounding.push(new Item(index, item.localizedName));
                });
                return arrayRounding;
            }

            private getSpecialRounding(dataObject: any): any {
                let arrayRounding: any = [];
                dataObject.lstEnum.rounding.forEach(function (item: any, index: number) {
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
                let returnData: any;

                if (_self.isFlex()) { // flex
                    returnData = {
                        //default values
                        useRest: _self.useRest(),
                        roundUnit: 0,
                        roundType: 0,
                        calcMethod: _self.selectedCalcMethod(),
                        fixedCalcMethod: _self.selectedFixedCalc(),
                        usePrivateGoOutRest: _self.usePrivateGoOutRest(),
                        useAssoGoOutRest: _self.useAssoGoOutRest()
                    };
                } else if (_self.isFlow()) { // flow
                    returnData = {
                        useRest: _self.useRest(),
                        roundUnit: _self.unitValue(),
                        roundType: _self.roundingValue(),
                        calcMethod: _self.selectedCalcMethod(),
                        fixedCalcMethod: _self.selectedFixedCalc(),
                        usePrivateGoOutRest: _self.usePrivateGoOutRest(),
                        useAssoGoOutRest: _self.useAssoGoOutRest()
                    };
                } else { //other
                    returnData = {
                        calcMethod: _self.selectedCalcMethod()
                    };
                }
                nts.uk.ui.windows.setShared("KMK003_DIALOG_G_OUTPUT_DATA", returnData);
                nts.uk.ui.windows.close();
            }

            /**
             * Close
             */
            public close() : void {
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