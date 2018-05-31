module nts.uk.at.view.kmk003.f {

    export module viewmodel {

        export class ScreenModel {

            // Screen data 
            oneDay: KnockoutObservable<number>;
            morning: KnockoutObservable<number>;
            afternoon: KnockoutObservable<number>;
            beforeUpdateWorkTimeOption: KnockoutObservable<any>;
            dataObject: KnockoutObservable<any>;
            constructor() {
                let self = this;
                self.oneDay = ko.observable(1);
                self.morning = ko.observable(1);
                self.afternoon = ko.observable(1);
                self.beforeUpdateWorkTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                    width: "50"
                }));
                self.dataObject = ko.observable(null);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_F_INPUT_DATA");
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

                //bind data to screen
                _self.oneDay(dataObject.oneDayDialog);
                _self.morning(dataObject.morningDialog);
                _self.afternoon(dataObject.afternoonDialog);
            }

            /**
             * Save
             */
            public save(): void {
                let _self = this;               
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }
                
                let outputObject = {
                    oneDayDialog: _self.oneDay()==null?_self.dataObject().oneDayDialog:_self.oneDay(),
                    morningDialog: _self.morning()==null?_self.dataObject().morningDialog:_self.morning(),
                    afternoonDialog: _self.afternoon()==null?_self.dataObject().afternoonDialog:_self.afternoon()
                };
                nts.uk.ui.windows.setShared("KMK003_DIALOG_F_OUTPUT_DATA", outputObject);
                nts.uk.ui.windows.close();
            }

            /**
             * Close
             */
            public close(): void {
                let self = this;
                nts.uk.ui.windows.setShared("KMK003_DIALOG_F_OUTPUT_DATA", self.dataObject());
                nts.uk.ui.windows.close();
            }

        }
    }
}