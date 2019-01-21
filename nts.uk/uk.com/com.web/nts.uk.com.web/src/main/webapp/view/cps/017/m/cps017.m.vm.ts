module nts.uk.com.view.cps017.m {
    export module viewmodel {
        export class ScreenModel {
            langId: KnockoutObservable<string> = ko.observable('ja');
            date: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
            constructor() {
                var self = this;
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
                service.saveAsExcel(langId, date).done(function() {
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
