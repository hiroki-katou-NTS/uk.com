module nts.uk.com.view.ccg018.c.viewmodel {
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("CCG018_26") },
                { code: '2', name: nts.uk.resource.getText("CCG018_27") },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Decision
         */
        decision(): any {
            var self = this;
            nts.uk.ui.windows.setShared('divideOrNot', self.selectedRuleCode());
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