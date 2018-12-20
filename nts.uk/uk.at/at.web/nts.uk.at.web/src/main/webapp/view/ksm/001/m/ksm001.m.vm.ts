module nts.uk.com.view.ksm001.m {
    export module viewmodel {
        export class ScreenModel {
            langId: KnockoutObservable<string> = ko.observable('ja');
            date: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            constructor() {
                var self = this;
                var self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });
            }
            //閉じるボタン
            closeDialog() {
                nts.uk.ui.windows.close();
            }

            printExcel() {
                var self = this;
                nts.uk.ui.block.grayout();
                let langId = self.langId();
                let date = self.date();
                let startDate = self.dateValue().startDate;
                let endDate = self.dateValue().endDate;
                let period = self.dateValue();
                service.saveAsExcel(langId, period).done(function() {
                    nts.uk.ui.windows.close();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

        }


    }
}
