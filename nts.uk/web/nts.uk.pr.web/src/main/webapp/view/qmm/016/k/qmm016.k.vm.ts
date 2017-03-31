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
            demension: model.DemensionItemDto;
        }

        /**
         * Add simple history screen model.
         */
        export class ScreenModel {
            dialogOptions: Option;
            demensionItemList: KnockoutObservableArray<model.DemensionItemDto>;
            selectedDemension: KnockoutObservable<model.DemensionItemDto>;

            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.dialogOptions = nts.uk.ui.windows.getShared('options');
                self.demensionItemList = ko.observableArray<model.DemensionItemDto>([]);
                self.selectedDemension = ko.observable(undefined);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.loadDemensionSelectionList().done(res => { 
                    self.demensionItemList(res);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Create history and then dialog.
             */
            private btnApplyClicked(): void {
                var self = this;
                if (self.selectedDemension()) {
                    var callBackData: CallBackData = {
                        demension: self.selectedDemension()
                    };
                    self.dialogOptions.onSelectItem(callBackData);
                    nts.uk.ui.windows.close();
                }
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