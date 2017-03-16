module nts.uk.pr.view.qmm016.k {
    export module viewmodel {
        /**
         * New history screen options.
         */
        export interface Option {
            /**
             * On copy call back.
             */
            onSelectItem: (data: CallBackData) => void;
        }
        
        /**
         * Callback data.
         */
        export interface CallBackData {
            
            masterCode: string;
            startYearMonth: number;
        }

        /**
         * Add simple history screen model.
         */
        export class ScreenModel {
            dialogOptions: Option;
            demensionItemList: any;

            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.dialogOptions = nts.uk.ui.windows.getShared('options');
                self.demensionItemList = ko.observableArray([]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.instance.loadDemensionList().done(res => {
                    self.demensionItemList(res);
                    dfd.resolve();
                })
                return dfd.promise();
            }

            /**
             * Create history and then dialog.
             */
            private btnApplyClicked(): void {
                var self = this;
                nts.uk.ui.windows.close();
            }

            /**
             * Close dialog.
             */
            private btnCancelClicked(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}