module nts.uk.pr.view.qmm007.c {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;
        import UnitPriceHistoryModel = nts.uk.pr.view.qmm007.a.viewmodel.UnitPriceHistoryModel;

        export class ScreenModel {
            unitPriceHistoryModel: UnitPriceHistoryModel;

            edittingMethod: KnockoutObservable<string>;
            isEditMode: KnockoutObservable<boolean>;
            isLatestHistory: boolean;

            constructor() {
                var self = this;
                self.unitPriceHistoryModel = ko.mapping.fromJS(nts.uk.ui.windows.getShared('unitPriceHistoryModel'));
                self.isLatestHistory = nts.uk.ui.windows.getShared('isLatestHistory');
                self.edittingMethod = ko.observable('Edit');
                self.isEditMode = ko.observable(true);

                self.edittingMethod.subscribe(val => {
                    val == 'Edit' ? self.isEditMode(true) : self.isEditMode(false);
                });
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
             * Update or remove history then close dialog.
             */
            btnApplyClicked() {
                var self = this;
                if (self.isEditMode()) {
                    service.update(ko.toJS(self.unitPriceHistoryModel)).done(() => {
                        nts.uk.ui.windows.setShared('isUpdated', true);
                        nts.uk.ui.windows.close();
                    });
                } else {
                    service.remove(self.unitPriceHistoryModel.id(), self.unitPriceHistoryModel.unitPriceCode()).done(() => {
                        nts.uk.ui.windows.setShared('isRemoved', true);
                        nts.uk.ui.windows.close();
                    });
                }
            }

            /**
             * Close dialog.
             */
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }
        }
    }
}