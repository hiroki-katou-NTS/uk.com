module nts.uk.at.view.kmk003.g {

    export module viewmodel {

        export class ScreenModel {

            // Screen data 
            switchOptions: KnockoutObservableArray<any>;
            unitComboBoxOptions: KnockoutObservableArray<any>;
            roundingComboBoxOptions: KnockoutObservableArray<any>;
            switchValue: KnockoutObservable<number>;
            unitValue: KnockoutObservable<number>;
            roundingValue: KnockoutObservable<number>;
            
            lstRest: KnockoutObservableArray<any>;
            actualList: KnockoutObservableArray<any>;
            
            selectedRest: KnockoutObservable<number>;
            selectedActual: KnockoutObservable<number>;
            
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
                self.roundingValue = ko.observable(1);
                
                self.lstRest = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_235')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_236')),
                    new RadioBoxModel(2, nts.uk.resource.getText('KMK003_237'))
                ]);
                self.actualList = ko.observableArray([
                    new RadioBoxModel(0, nts.uk.resource.getText('KMK003_239')),
                    new RadioBoxModel(1, nts.uk.resource.getText('KMK003_240'))
                ]);
            
                self.selectedRest = ko.observable(0);
                self.selectedActual = ko.observable(0);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_G_INPUT_DATA");
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

                //TODO
                //get list enum
                let arrayUnit:any =[]; 
                dataObject.lstEnum.roundingTimeUnit.forEach(function(item:any,index:number){
                    arrayUnit.push(new Item(index, item.localizedName));
                });
                _self.unitComboBoxOptions(arrayUnit);
                let arrayRounding:any = [];
                dataObject.lstEnum.rounding.forEach(function(item:any, index:number) {
                    arrayRounding.push(new Item(index, item.localizedName));
                });
                _self.roundingComboBoxOptions(arrayRounding);
            }

            /**
             * Save
             */
            public save(): void {
                let _self = this;
            }

            /**
             * Close
             */
            public close(): void {
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