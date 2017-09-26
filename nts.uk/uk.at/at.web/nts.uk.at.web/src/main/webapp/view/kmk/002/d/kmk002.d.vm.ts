module nts.uk.at.view.kmk002.d {
    export module viewmodel {

        import FormulaSetting = nts.uk.at.view.kmk002.a.viewmodel.FormulaSetting;

        export class ScreenModel {
            formulaSetting: FormulaSetting;
            formulaAtr: string;
            formulaName: string;
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
                let dto = nts.uk.ui.windows.getShared("formulaSetting");

                // mock.
                self.formulaAtr = nts.uk.resource.getText('KMK002_70'); // time
                //self.formulaAtr = nts.uk.resource.getText('KMK002_71'); // number
                //self.formulaAtr = nts.uk.resource.getText('KMK002_72'); // amount
                self.formulaName = 'aaaaa';

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
                if (self.isValid()) {
                    nts.uk.ui.windows.setShared('formulaSetting', self.formulaSetting.toDto());
                    self.close();
                }
            }

            private isValid(): boolean {
                let self = this;
                if (self.formulaSetting.operator() == 3 && self.formulaSetting.rightItem.inputValue() == 0) {
                    // Divide by zero
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_638" });
                    return false;
                }
                if (self.formulaSetting.rightItem.settingMethod() == 1 && self.formulaSetting.leftItem.settingMethod() == 1) {
                    // both item setting method = number.
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_420" });
                    return false;
                }
                //TODO Msg_114
                //TODO Msg_419 de required?
                return true;

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