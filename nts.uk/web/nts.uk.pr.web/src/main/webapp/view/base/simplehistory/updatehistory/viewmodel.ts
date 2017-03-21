module nts.uk.pr.view.base.simplehistory.updatehistory {
    export module viewmodel {
        /**
         * New history screen options.
         */
        export interface UpdateHistoryScreenOption {
            /**
             * Screen mode.
             * Default: MODE_MASTER_HISTORY
             * @see UpdateHistoryScreenMode
             */
            screenMode?: number;

            /**
             * Function name.
             */
            name: string;

            /**
             * Master model.
             * Optional in MODE_HISTORY_ONLY
             */
            master?: model.MasterModel<any>;

            /**
             * Latest model.
             */
            history: model.HistoryModel;

            /**
             * Remove master on lasthiotory remove.
             */
            removeMasterOnLastHistoryRemove?: boolean;

            /**
             * On copy call back.
             */
            onDeleteCallBack: (data: UpdateHistoryCallBackData) => void;

            /**
             * On create call back.
             */
            onUpdateCallBack: (data: UpdateHistoryCallBackData) => void;
        }
        
        /**
         * Callback data.
         */
        export interface UpdateHistoryCallBackData {
            masterCode: string;
            historyId: string;
            startYearMonth: number;
        }

        /**
         * Add simple history screen model.
         */
        export class ScreenModel {
            private static ACTION_TYPE_DELETE: string = 'DELETE';
            private static ACTION_TYPE_UPDATE: string = 'UPDATE';

            /**
             * Dialog options.
             */
            dialogOptions: UpdateHistoryScreenOption;

            /**
             * Create type.
             */
            actionType: KnockoutObservable<string>;

            /**
             * Start year month.
             */
            startYearMonth: KnockoutObservable<number>;

            /**
             * Last year month.
             */
            endYearMonth: string;
            
            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.dialogOptions = nts.uk.ui.windows.getShared('options');
                self.dialogOptions.screenMode = self.dialogOptions.screenMode || simplehistory.dialogbase.ScreenMode.MODE_MASTER_HISTORY;
                self.actionType = ko.observable(ScreenModel.ACTION_TYPE_UPDATE);
                self.startYearMonth = ko.observable(self.dialogOptions.history.start);
                self.endYearMonth = nts.uk.time.formatYearMonth(self.dialogOptions.history.end);
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

            /**
             * Create history and then dialog.
             */
            private btnApplyClicked(): void {
                var self = this;
                var callBackData: UpdateHistoryCallBackData = {
                    masterCode: self.dialogOptions.master ? self.dialogOptions.master.code : undefined,
                    historyId: self.dialogOptions.history.uuid,
                    startYearMonth: self.startYearMonth()
                };
                if (self.actionType() == ScreenModel.ACTION_TYPE_DELETE) {
                      self.dialogOptions.onDeleteCallBack(callBackData);
                } else {
                    self.dialogOptions.onUpdateCallBack(callBackData);
                }
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