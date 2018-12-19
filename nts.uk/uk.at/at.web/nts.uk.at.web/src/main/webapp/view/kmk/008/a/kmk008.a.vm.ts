module nts.uk.at.view.kmk008.a {
    export module viewmodel {
        export class ScreenModel {
            langId: KnockoutObservable<string> = ko.observable('ja');
            constructor() {

            }
            
            
            startPage(): JQueryPromise<any> {
                let dfd = $.Deferred();
                dfd.resolve();
                $("#button").focus();
                return dfd.promise();
            }
            
            private exportExcel(): void {
                var self = this;
                nts.uk.ui.block.grayout();
                let langId = self.langId();
                service.saveAsExcel(langId).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            
            opendScreenBWithLaborSystemAtr0() {
                nts.uk.request.jump("/view/kmk/008/b/index.xhtml", { "laborSystemAtr": 0 });
            }

            opendScreenBWithLaborSystemAtr1() {
                nts.uk.request.jump("/view/kmk/008/b/index.xhtml", { "laborSystemAtr": 1 });
            }

            openDialogScreenJ() {
                nts.uk.ui.windows.sub.modal("/view/kmk/008/j/index.xhtml").onClosed(() => {
                });
            }

            openDialogScreenI() {
                nts.uk.ui.windows.sub.modal("/view/kmk/008/i/index.xhtml").onClosed(() => {
                });
            }

            opendScreenG() {
                 nts.uk.request.jump("/view/kmk/008/g/index.xhtml");
            }
        }

    }
}
