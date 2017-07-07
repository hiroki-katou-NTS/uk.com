module nts.uk.at.view.kmk008.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {

            }
            startPage(): JQueryPromise<any> {
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            opendScreenWithLaborSystemAtr0() {
                nts.uk.request.jump("/view/kmk/008/b/index.xhtml", { "laborSystemAtr": 0 });
            }

            opendScreenWithLaborSystemAtr1() {
                nts.uk.request.jump("/view/kmk/008/b/index.xhtml", { "laborSystemAtr": 1 });
            }
        }

    }
}
