module nts.uk.pr.view.qmm017.l {
    export module viewmodel {
        export class ScreenModel {
            easyFormulaName: KnockoutObservable<string>;
            comboBoxFormulaType: KnockoutObservable<ComboBox>;
            comboBoxBaseAmount: KnockoutObservable<ComboBox>;

            constructor() {
                var self = this;
                self.easyFormulaName = ko.observable('');
                self.comboBoxFormulaType = ko.observable(new ComboBox([
                    new ItemModel(0, '計算式１'),
                    new ItemModel(1, '計算式２'),
                    new ItemModel(2, '計算式3')
                ]));
                self.comboBoxBaseAmount = ko.observable(new ComboBox([
                    new ItemModel(0, '固定値'),
                    new ItemModel(2, '個人単価'),
                    new ItemModel(3, '支給項目'),
                    new ItemModel(4, '控除項目')
                ]));
                self.comboBoxFormulaType().selectedCode.subscribe(function(codeChange) {
                    if (codeChange === 2) {
                        self.comboBoxBaseAmount().itemList([
                            new ItemModel(0, '固定値'),
                            new ItemModel(1, '会社単価'),
                            new ItemModel(3, '支給項目'),
                            new ItemModel(4, '控除項目')
                        ]);
                    } else {
                        self.comboBoxBaseAmount().itemList([
                            new ItemModel(0, '固定値'),
                            new ItemModel(2, '個人単価'),
                            new ItemModel(3, '支給項目'),
                            new ItemModel(4, '控除項目')
                        ]);
                    }
                });
            }
        }

        export class ComboBox {
            itemList: KnockoutObservableArray<ItemModel>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            selectedCode: KnockoutObservable<number>;

            constructor(itemList: Array<ItemModel>) {
                var self = this;
                self.itemList = ko.observableArray(itemList);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(0);
                self.selectedCode = ko.observable(null);
            }
        }

        class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}