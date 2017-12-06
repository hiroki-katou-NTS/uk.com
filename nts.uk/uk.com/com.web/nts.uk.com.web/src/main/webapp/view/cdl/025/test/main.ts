module nts.uk.at.view.cdl025.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        currentCode: KnockoutObservable<string>;
        currentCodes: KnockoutObservableArray<string>;
        constructor() {
            var self = this;
            self.currentCode = ko.observable("");
            self.currentCodes = ko.observableArray([]);
        }

        openCDL025() {
            var self = this;
            let param = {
                roleType: 1,
                multiple: true
            };
            nts.uk.ui.windows.setShared("paramCdl025", param);
            nts.uk.ui.windows.sub.modal("/view/cdl/025/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("dataCdl025");
                self.currentCodes(data);
            });
        }

        openCDL025Single() {
            var self = this;
            let param = {
                roleType: 1,
                multiple: false
            };
            nts.uk.ui.windows.setShared("paramCdl025", param);
            nts.uk.ui.windows.sub.modal("/view/cdl/025/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("dataCdl025");
                self.currentCode(data);
            });
        }
    }

}
