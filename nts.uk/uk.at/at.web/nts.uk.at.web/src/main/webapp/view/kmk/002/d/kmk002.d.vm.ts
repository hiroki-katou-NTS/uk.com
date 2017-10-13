module nts.uk.at.view.kmk002.d {
    export module viewmodel {

        import FormulaSettingDto = nts.uk.at.view.kmk002.a.service.model.FormulaSettingDto;
        import SettingItemDto = nts.uk.at.view.kmk002.a.service.model.SettingItemDto;
        import ParamToD = nts.uk.at.view.kmk002.a.viewmodel.ParamToD;

        export class ScreenModel {
            formulaSetting: FormulaSetting;

            constructor() {
                this.formulaSetting = new FormulaSetting();
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
                self.formulaSetting.fromDto(dto);

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
        class FormulaSetting {
            formulaId: string; //TODO dung lam gi?
            formulaName: string;
            performanceAtr: number; //TODO dung lam gi ?
            formulaAtr: string;
            selectableFormulas: KnockoutObservableArray<any>;
            selectedItemLeft: KnockoutObservable<any>;
            selectedItemRight: KnockoutObservable<any>;

            minusSegment: KnockoutObservable<number>;
            operator: KnockoutObservable<number>;
            leftItem: FormulaSettingItem;
            rightItem: FormulaSettingItem;

            operatorDatasource: KnockoutObservableArray<any>;

            constructor() {
                this.minusSegment = ko.observable(0);
                this.operator = ko.observable(0);
                this.leftItem = new FormulaSettingItem();
                this.rightItem = new FormulaSettingItem();

                // fixed 
                this.leftItem.dispOrder = 1;
                this.leftItem.settingMethod(0);
                this.rightItem.dispOrder = 2;
                this.rightItem.settingMethod(1);

                this.operatorDatasource = ko.observableArray([
                    { code: 0, name: '+' },
                    { code: 1, name: '-' },
                    { code: 2, name: '*' },
                    { code: 3, name: '/' }
                ]);
                
                //abc
                this.formulaName = '';
                this.performanceAtr = 0;
                this.selectedItemLeft = ko.observable();
                this.selectedItemRight = ko.observable();
                this.formulaAtr = nts.uk.resource.getText('KMK002_70'); // time
                //self.formulaAtr = nts.uk.resource.getText('KMK002_71'); // number
                //self.formulaAtr = nts.uk.resource.getText('KMK002_72'); // amount

                this.selectableFormulas = ko.observableArray([
                    { code: '0', name: 'aaaaaaaaaaa', atr: 1 },
                    { code: '1', name: 'bbbbbbbbb', atr: 1 },
                    { code: '2', name: 'cccccccc', atr: 2 },
                    { code: '3', name: 'ddddddddd', atr: 2 }
                ]);
            }

            /**
             * Data input validation
             */
            public isValid(): boolean {
                let self = this;

                // Check divide by zero
                if (self.operator() == 3 && self.rightItem.inputValue() == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_638" });
                    return false;
                }

                // both item setting method = number.
                if (self.isBothNumberInput()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_420" });
                    return false;
                }

                // Check required input
                if (self.rightItem.settingMethod() == 1 && !self.rightItem.inputValue()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_419" });
                    return false;
                }

                // Check required input
                if (self.leftItem.settingMethod() == 1 && !self.leftItem.inputValue()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_419" });
                    return false;
                }

                // Validate calculation
                // If operator is '+' or '-' , 
                // both item must have the same attribute if setting method is both item selection 
                if (self.operator() == 0 || self.operator() == 1) {
                    if (self.isBothItemSelect() && self.isDifferentAtr()) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_114" });
                        return false;
                    }
                }

                // Validate number input.
                if ($('.nts-editor').ntsError('hasError')) {
                    return false;
                }

                return true;

            }

            /**
             * Check if attribute of left item and right item is different.
             */
            private isDifferentAtr(): boolean {
                let self = this;
                let leftItem = self.findFormulaById(self.selectedItemLeft());
                let rightItem = self.findFormulaById(self.selectedItemRight());

                if (leftItem.atr != rightItem.atr) {
                    return true;
                }

                return false;
            }

            /**
             * Check if both item's method setting is item selection
             */
            private isBothItemSelect(): boolean {
                let self = this;
                if (self.leftItem.settingMethod() == 0 && self.rightItem.settingMethod() == 0) {
                    return true;
                }
                return false;
            }

            /**
             * Check if both item's method setting is numerical input.
             */
            private isBothNumberInput(): boolean {
                let self = this;
                if (self.leftItem.settingMethod() == 1 && self.rightItem.settingMethod() == 1) {
                    return true;
                }
                return false;
            }

            /**
             * find formula by id.
             */
            private findFormulaById(id: string): any {
                let self = this;
                let f = _.find(self.selectableFormulas(), item => item.code == id);
                return f;
            }

            /**
             * Convert to viewmodel
             */
            public fromDto(dto: ParamToD): void {
                let self = this;
                self.formulaId = dto.formulaId;
                self.formulaName = dto.formulaName;
                self.formulaAtr = dto.formulaAtr;
                self.minusSegment(dto.formulaSetting.minusSegment);
                self.operator(dto.formulaSetting.operator);
                self.leftItem.fromDto(dto.formulaSetting.leftItem);
                self.rightItem.fromDto(dto.formulaSetting.rightItem);
            }

            /**
             * convert viewmodel to dto
             */
            public toDto(): FormulaSettingDto {
                let self = this;
                let dto: FormulaSettingDto = <FormulaSettingDto>{};

                dto.minusSegment = self.minusSegment();
                dto.operator = self.operator();
                dto.leftItem = this.leftItem.toDto();
                dto.rightItem = this.rightItem.toDto();

                return dto;
            }

        }
        /**
         * Formula setting item
         */
        class FormulaSettingItem {
            settingMethod: KnockoutObservable<number>;
            dispOrder: number;
            inputValue: KnockoutObservable<number>;
            formulaItemId: KnockoutObservable<string>;

            constructor() {
                this.settingMethod = ko.observable(1);
                this.dispOrder = 1;
                this.inputValue = ko.observable(1);
                this.formulaItemId = ko.observable(nts.uk.util.randomId());
            }

            /**
             * is input value check
             */
            public isInputValue(): boolean {
                if (this.settingMethod() == 0) {
                    return false;
                }
                return true;
            }

            /**
             * convert dto to viewmodel
             */
            public fromDto(dto: SettingItemDto): void {
                this.settingMethod(dto.settingMethod);
                this.dispOrder = dto.dispOrder;
                this.inputValue(dto.inputValue);
                this.formulaItemId(dto.formulaItemId);
            }

            /**
             * convert viewmodel to dto
             */
            public toDto(): SettingItemDto {
                let self = this;
                let dto: SettingItemDto = <SettingItemDto>{};

                dto.settingMethod = this.settingMethod();
                dto.dispOrder = this.dispOrder;
                dto.inputValue = this.inputValue();
                dto.formulaItemId = this.formulaItemId();

                return dto;
            }
        }
    }
}