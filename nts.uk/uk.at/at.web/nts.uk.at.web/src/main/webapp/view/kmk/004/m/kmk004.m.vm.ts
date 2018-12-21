//TODO
module nts.uk.com.view.kmk004.m {
    export module viewmodel {
        export class ScreenModel {
            langId: KnockoutObservable<string> = ko.observable('ja');
            dateValue: KnockoutObservable<any>;
            constructor() {
                var self = this;
                self.dateValue = ko.observable({});
            }
            //閉じるボタン
            closeDialog() {
                nts.uk.ui.windows.close();
            }

            printExcel() {
                var self = this;
                nts.uk.ui.block.grayout();
                let langId = self.langId();
                let startDate = moment.utc(self.dateValue().startDate ,"YYYY/MM/DD");
                let endDate = moment.utc(self.dateValue().endDate ,"YYYY/MM/DD") ;
                service.saveAsExcel(langId, startDate, endDate).done(function() {
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
