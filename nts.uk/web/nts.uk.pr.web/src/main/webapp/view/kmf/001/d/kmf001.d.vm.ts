module nts.uk.pr.view.kmf001.d {
    export module viewmodel {
        export class ScreenModel {
            
            numberOfYear: KnockoutObservable<number>;
            maxOfDays: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<any>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.numberOfYear = ko.observable(1);
                self.maxOfDays = ko.observable(40);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    placeholder: "Type st",
                    textalign: "left"
                }));
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            
            public backToHistorySelection() {
                
            }
            
            public register() {
                
            }
        }
    }
}