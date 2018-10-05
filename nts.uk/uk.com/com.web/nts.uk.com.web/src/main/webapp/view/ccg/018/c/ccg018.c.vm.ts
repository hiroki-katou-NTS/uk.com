module nts.uk.com.view.ccg018.c.viewmodel {
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedCategorySet: any;
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("CCG018_26") },
                { code: 0, name: nts.uk.resource.getText("CCG018_27") },
            ]);
            //if categorySet != 3, set the value of selectedCategorySet
            if (nts.uk.ui.windows.getShared('categorySet') === undefined) {
                self.selectedCategorySet = ko.observable(0);
            } else {
                self.selectedCategorySet = ko.observable(nts.uk.ui.windows.getShared('categorySet'));
            }
            $('#C1-3').focus();
        }

        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.grayout;
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            nts.uk.ui.block.clear();
            return dfd.promise();
        }

        /**
         * Decision
         */
        decision(): any {
            var self = this;
            nts.uk.ui.windows.setShared('categorySetC', self.selectedCategorySet());
            nts.uk.ui.windows.close();
        }

        /**
         * Close dialog
         */
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
    }
}