module nts.uk.at.view.kal004.tab3.viewmodel {
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        executionAuthor: KnockoutObservable<string>;
        listRoleID : KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KAL004_112') },
                { code: '1', name: nts.uk.resource.getText('KAL004_113') }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.executionAuthor = ko.observable("");
            self.listRoleID = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        
        
        openCDL025() {
            var currentScreen = nts.uk.ui.windows.sub.modal("com","/view/cdl/025/index.xhtml");
        }

    }
}
