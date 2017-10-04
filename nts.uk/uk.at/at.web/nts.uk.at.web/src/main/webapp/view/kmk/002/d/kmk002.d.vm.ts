module nts.uk.at.view.kmk002.d {
    export module viewmodel {

        import FormulaSetting = nts.uk.at.view.kmk002.a.viewmodel.FormulaSetting;
        import ParamToD = nts.uk.at.view.kmk002.a.viewmodel.ParamToD;

        export class ScreenModel {
            formulaSetting: FormulaSettingVm;

            constructor() {
                this.formulaSetting = new FormulaSettingVm();
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Get params
                let dto = nts.uk.ui.windows.getShared('paramToD');

                // Set params to view model
                self.formulaSetting.fromParam(dto);

                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Return data to caller screen & close dialog.
             */
            public apply(): void {
                let self = this;
                if (self.formulaSetting.isValid()) {
                    nts.uk.ui.windows.setShared('returnFromD', self.formulaSetting.toDto());
                    self.close();
                }
            }

            /**
             * Close dialog
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
        }
        class FormulaSettingVm extends FormulaSetting {
            formulaId: string; //TODO dung lam gi?
            formulaName: string;
            performanceAtr: number; //TODO dung lam gi ?
            formulaAtr: string;
            selectableFormulas: KnockoutObservableArray<any>;
            selectedItemLeft: KnockoutObservable<any>;
            selectedItemRight: KnockoutObservable<any>;

            constructor () {
                super();
                this.formulaName = '';
                this.performanceAtr = 0;
                this.selectedItemLeft = ko.observable();
                this.selectedItemRight = ko.observable();
                this.formulaAtr = nts.uk.resource.getText('KMK002_70'); // time
                //self.formulaAtr = nts.uk.resource.getText('KMK002_71'); // number
                //self.formulaAtr = nts.uk.resource.getText('KMK002_72'); // amount

                this.selectableFormulas = ko.observableArray([
                    { code: '0', name: 'aaaaaaaaaaa' },
                    { code: '1', name: 'bbbbbbbbb' },
                    { code: '2', name: 'cccccccc' },
                    { code: '3', name: 'ddddddddd' }
                ]);
            }

            /**
             * Data input validation
             */
            public isValid(): boolean {
                let self = this;
                if (self.operator() == 3 && self.rightItem.inputValue() == 0) {
                    // Divide by zero
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_638" });
                    return false;
                }
                if (self.rightItem.settingMethod() == 1 && self.leftItem.settingMethod() == 1) {
                    // both item setting method = number.
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_420" });
                    return false;
                }
                if (self.rightItem.settingMethod() == 1 && !self.rightItem.inputValue()) {
                    // required input
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_419" });
                    return false;
                }
                if (self.leftItem.settingMethod() == 1 && !self.leftItem.inputValue()) {
                    // required input
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_419" });
                    return false;
                }
                //TODO Msg_114
                if (self.operator() == 0 || self.operator() == 1) {
                    
                }
                if ($('.nts-editor').ntsError('hasError')) {
                    return false;
                }
                return true;

            }

            /**
             * Convert to viewmodel
             */
            public fromParam(dto: ParamToD): void {
                let self = this;
                self.formulaId = dto.formulaId;
                self.formulaName = dto.formulaName;
                self.formulaAtr = dto.formulaAtr;
                super.fromDto(dto.formulaSetting);
            }

            /**
             * Convert to dto.
             */
            public toDto(): any {
                //TODO
            }

        }
    }
}