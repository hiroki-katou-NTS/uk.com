module nts.uk.at.view.kmk002.d {
    export module viewmodel {

        import FormulaSetting = nts.uk.at.view.kmk002.a.viewmodel.FormulaSetting;

        export class ScreenModel {
            formulaSetting: FormulaSetting;
            zxcv: KnockoutObservableArray<any>;

            constructor() {
                this.formulaSetting = new FormulaSetting();
                this.zxcv = ko.observableArray([
                    { code: '0', name: 'aaaaaaaaaaa' },
                    { code: '1', name: 'bbbbbbbbb' },
                    { code: '2', name: 'cccccccc' },
                    { code: '3', name: 'ddddddddd' }
                ]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                let dto = nts.uk.ui.windows.getShared("shared");
                self.formulaSetting.fromDto(dto);
                console.log(dto);
                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Return data to caller screen & close dialog.
             */
            public apply(): void {
                let self = this;
                nts.uk.ui.windows.setShared('returned', self.formulaSetting.toDto());
                self.close();
            }

            /**
             * Close dialog
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}