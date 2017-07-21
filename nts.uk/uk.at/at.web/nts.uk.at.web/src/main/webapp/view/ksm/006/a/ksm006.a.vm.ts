module nts.uk.pr.view.ksm006.a {

    export module viewmodel {
        export class ScreenModel {


            isShowEmployment : KnockoutObservable<boolean>;
            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.isShowEmployment = ko.observable(true);

            }

            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                dfd.resolve();

                return dfd.promise();
            }
            registerWholeCompany(){}
            switchToCompanyTab(){}
            switchToWorkplaceTab(){}
            switchToClassTab(){}
        }
    }
}