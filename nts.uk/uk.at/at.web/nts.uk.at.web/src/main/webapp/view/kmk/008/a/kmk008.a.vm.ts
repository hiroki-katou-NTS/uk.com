module nts.uk.at.view.kmk008.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {

            }
            startPage(): JQueryPromise<any> {
                let dfd = $.Deferred();
                dfd.resolve();
                $("#button").focus();
                return dfd.promise();
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
