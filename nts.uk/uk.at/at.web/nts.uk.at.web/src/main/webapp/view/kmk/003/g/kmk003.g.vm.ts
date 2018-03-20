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
            constructor() {
                let self = this;

                self.switchOptions = ko.observableArray([
                    new Item(1, nts.uk.resource.getText("KMK003_113")),
                    new Item(0, nts.uk.resource.getText("KMK003_114"))
                ]);
                self.unitComboBoxOptions = ko.observableArray([
                    new Item(1, nts.uk.resource.getText("KMK003_113")),
                    new Item(0, nts.uk.resource.getText("KMK003_114"))
                ]);
                self.roundingComboBoxOptions = ko.observableArray([
                    new Item(1, nts.uk.resource.getText("KMK003_113")),
                    new Item(0, nts.uk.resource.getText("KMK003_114"))
                ]);

                self.switchValue = ko.observable(1);
                self.unitValue = ko.observable(1);
                self.roundingValue = ko.observable(1);
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
    }
}