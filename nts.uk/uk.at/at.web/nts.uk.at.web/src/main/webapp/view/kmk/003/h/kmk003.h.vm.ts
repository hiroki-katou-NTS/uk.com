module nts.uk.at.view.kmk003.h {

    export module viewmodel {

        export class ScreenModel {

            useStamp: KnockoutObservable<boolean>;
            useStampCalcMethod: KnockoutObservable<number>;
            timeManagerSetAtr: KnockoutObservable<number>;
            //update 3.4
            calcMethodLst: KnockoutObservableArray<any>;
            selectedCalcMethod: KnockoutObservable<any>;

            unitComboBoxOptions: KnockoutObservableArray<any>;
            roundingComboBoxOptions: KnockoutObservableArray<any>;
            unitValue: KnockoutObservable<number>;
            roundingValue: KnockoutObservable<number>;
            useRest: KnockoutObservable<boolean>;

            dataObject: KnockoutObservable<any>;

            isFlow: KnockoutObservable<boolean>;

            constructor() {
                let self = this;

                self.useStamp = ko.observable(false);
                self.useStampCalcMethod = ko.observable(0);
                self.timeManagerSetAtr = ko.observable(0);

                //update 3.4
                self.calcMethodLst = ko.observableArray([
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_235')),
                    new RadioBoxModel(2, nts.uk.resource.getText('KMK003_236')),
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_237'))
                ]);
                self.selectedCalcMethod = ko.observable(false);

                self.isFlow = ko.observable(false);

                self.unitComboBoxOptions = ko.observableArray([]);
                self.roundingComboBoxOptions = ko.observableArray([]);
                self.unitValue = ko.observable(1);
                self.roundingValue = ko.observable(0);
                self.useRest = ko.observable(false);
                self.dataObject = ko.observable(null);
                //change rounding type when change unit
                self.unitValue.subscribe((v) => {
                    if (v == 4 || v == 6)//case 15 or 30 minute
                    {
                        self.roundingComboBoxOptions(self.getRounding());
                    } else {
                        self.roundingComboBoxOptions(self.getSpecialRounding());
                    }
                });

                _.defer(() => $('#calculate-method').focus());
            }
            

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();

                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_H_INPUT");
                self.dataObject(dataObject);
                self.bindingData(self.dataObject());
                dfd.resolve();
                return dfd.promise();
            }

            public setInitialFocus(): void {
                const focusIndex = $('#initial-focus-h');
                const focusIndex2 = $('#initial-focus-h2');
                if (focusIndex.length > 0) {
                    focusIndex.focus();
                } else {
                    focusIndex2.focus();
                }
            }

            /**
             * Binding data
             */
            private bindingData(dto : any) {
                let self = this;
                if (nts.uk.util.isNullOrUndefined(dto)) {
                    return;
                }

                self.selectedCalcMethod(dto.calcMethod);
                self.useStamp(dto.useStamp ? dto.useStamp : false);
                self.useStampCalcMethod(dto.useStampCalcMethod ? dto.useStampCalcMethod : 0);
                self.timeManagerSetAtr(dto.timeManagerSetAtr ? dto.timeManagerSetAtr : 0);

                //update 3.4
                self.isFlow(dto.isFlow);

                let arrayUnits: any = [];
                dto.lstEnum.roundingTimeUnit.forEach(function (item: any, index: number) {
                    arrayUnits.push(new Item(index, item.localizedName));
                });
                self.unitComboBoxOptions(arrayUnits);
                self.roundingComboBoxOptions(self.getSpecialRounding());
                self.unitValue(dto.roundUnit);
                self.roundingValue(dto.roundType);
                self.useRest(dto.useRest);
            }

            private getRounding(): any {
                let arrayRounding: any = [];
                this.dataObject().lstEnum.rounding.forEach(function (item: any, index: number) {
                    arrayRounding.push(new Item(index, item.localizedName));
                });
                return arrayRounding;
            }

            private getSpecialRounding(): any {
                let arrayRounding: any = [];
                this.dataObject().lstEnum.rounding.forEach(function (item: any, index: number) {
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
                let self = this;

                let dto = {
                    calcMethod: self.selectedCalcMethod(),
                    useStamp: self.useStamp(),
                    useStampCalcMethod: self.useStampCalcMethod(),
                    timeManagerSetAtr: self.timeManagerSetAtr(),
                    roundUnit: 0,
                    roundType: 0,
                    useRest: self.useRest()
                };

                if (self.isFlow()){
                    dto.roundType = self.roundingValue();
                    dto.roundUnit = self.unitValue();
                }


                nts.uk.ui.windows.setShared("KMK003_DIALOG_H_OUTPUT", dto);
                self.close();
            }

            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }

        }

        class RadioBoxModel {
            id: number;
            name: string;

            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }

         class Item {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export interface DialogHParam {
            calcMethod: number;
            useStamp: boolean;
            useStampCalcMethod: number;
            timeManagerSetAtr: number;
            lstEnum : any;
            roundUnit: number;
            roundType: number;
            useRest: boolean;
            isFlow: boolean;
        }
    }
}