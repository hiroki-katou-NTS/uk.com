module nts.uk.at.view.kmk002.c {
    export module viewmodel {
        export class ScreenModel {

            list: any;
            check: any;
            columns: any;
            columnsRight: any;
            selectedCodes: any;

            constructor() {
                this.check = ko.observable();
                this.list = ko.observableArray([
                    { code: '0', name: '回数' }, // TIMES
                    { code: '1', name: '金額' }, // AMOUNT
                    { code: '2', name: '時間' } // TIME
                ]);
                this.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'name', width: 100 }
                ]);
                this.columnsRight = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_58'), key: 'operator', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'name', width: 100 }
                ]);
                this.selectedCodes = ko.observableArray([]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}