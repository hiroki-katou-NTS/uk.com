module nts.uk.at.view.kmk006.temp {
    import close = nts.uk.ui.windows.close;
    import dialog  = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            baseDate: KnockoutObservable<string> = ko.observable('');
            langId: KnockoutObservable<string> = ko.observable('ja');
            constructor() {
                block.invisible();
                var self = this;
                var params = getShared('KMK006');
                this.baseDate(params.baseDate);
                block.clear();
            }

            private exportExcel(domainId: string, domainType: string) {
                var self = this;
                let baseDate: any = moment.utc(self.baseDate(), 'YYYY/MM/DD').toISOString();
                service.exportExcel(self.langId(), domainId, domainType, baseDate)
                    .fail(function (res) {
                        nts.uk.ui.dialog.alertError(res);
                    })
            }

            private cancel() {
                close();
            }


        }
    }
}