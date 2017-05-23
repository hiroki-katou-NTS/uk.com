module nts.uk.pr.view.kmf001.d {
    export module viewmodel {
        export class ScreenModel {
            
            retentionYearsAmount: KnockoutObservable<number>;
            maxDaysCumulation: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<any>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.retentionYearsAmount = ko.observable(1);
                self.maxDaysCumulation = ko.observable(40);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    placeholder: "Type st",
                    textalign: "left"
                }));
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.findRetentionYearly().done(function(data: service.model.RetentionYearlyModel) {
                    self.retentionYearsAmount(data.retentionYearsAmount);
                    self.maxDaysCumulation(data.maxDaysCumulation);
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            public backToHistorySelection() {
                
            }
            
            public register() {
                var self = this;
                
                // Validate.
                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {
                    dfd.reject();
                    return dfd.promise();
                }
                
                var retentionYearly = new service.model.RetentionYearlyModel();
                retentionYearly.retentionYearsAmount = self.retentionYearsAmount();
                retentionYearly.maxDaysCumulation = self.maxDaysCumulation();
                service.saveRetentionYearly(retentionYearly).done(function() {
                    
                })
                .fail((res) => {
                        nts.uk.ui.dialog.alert(res.message);
                    });
            }
        }
    }
}