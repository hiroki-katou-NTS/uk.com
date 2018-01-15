module nts.uk.at.view.kmk013.a {
    export module viewmodel {
        export class ScreenModel {
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            openDialogB(): void  {
                nts.uk.request.jump("/view/kmk/013/b/index.xhtml");
            }
            openDialogC(): void  {
                nts.uk.request.jump("/view/kmk/013/c/index.xhtml");
            }
            openDialogD(): void  {
                nts.uk.request.jump("/view/kmk/013/d/index.xhtml");
            }
            openDialogE(): void  {
                nts.uk.request.jump("/view/kmk/013/e/index.xhtml");
            }
            openDialogH(): void  {
                nts.uk.request.jump("/view/kmk/013/h/index.xhtml");
            }

        }
    }
}