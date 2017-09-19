module nts.uk.at.view.kmk002.d {
    export module viewmodel {

        import FormulaSetting = nts.uk.at.view.kmk002.a.viewmodel.FormulaSetting;

        export class ScreenModel {
            formulaSetting: FormulaSetting;

            constructor() {
                this.formulaSetting = new FormulaSetting();
                console.log(this.formulaSetting);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                let dto = nts.uk.ui.windows.getShared("shared")
                console.log(dto);
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}