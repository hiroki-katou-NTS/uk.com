module nts.uk.pr.view.qmm017.l {
    export module viewmodel {
        export class ScreenModel {
            easyFormulaName: KnockoutObservable<string>;
            comboBoxFormulaType: KnockoutObservable<ComboBox>;
            comboBoxBaseAmount: KnockoutObservable<ComboBox>;
            baseAmountFixedValue: KnockoutObservable<number>;
            baseAmountListItem: KnockoutObservableArray<ItemModel>;
            baseAmountFixedValueOption: KnockoutObservable<any>;
            baseAmountSelectionItems: KnockoutObservable<string>;
            optionBaseAmountSelectionItems: KnockoutObservable<any>;
            comboBoxBaseValue: KnockoutObservable<ComboBox>;
            baseValueFixedValue: KnockoutObservable<number>;
            premiumRate: KnockoutObservable<number>;
            premiumRateInputOption: KnockoutObservable<any>;
            switchButtonRoundingFiguresD: KnockoutObservable<SwitchButton>;
            switchButtonRoundingFiguresF: KnockoutObservable<SwitchButton>;
            comboBoxCoefficient: KnockoutObservable<ComboBox>;
            coefficientFixedValue: KnockoutObservable<number>;
            comboBoxAdjustmentAtr: KnockoutObservable<ComboBox>;

            constructor(param) {
                var self = this;
                let paramIsUpdate = param.isUpdate;
                let paramDirtyData = param.dirtyData;
                self.initOriginal();
                if (paramIsUpdate === true) {
                    self.easyFormulaName(paramDirtyData.easyFormulaName());
                    self.comboBoxFormulaType().selectedCode(paramDirtyData.easyFormulaType());
                    self.comboBoxBaseAmount().selectedCode(paramDirtyData.baseAmountAttr());
                    self.baseAmountFixedValue(paramDirtyData.baseAmountFixedValue());
                    self.baseAmountListItem(paramDirtyData.baseAmountListItem);
                    self.comboBoxBaseValue().selectedCode(paramDirtyData.baseValueAttr());
                    self.baseValueFixedValue(paramDirtyData.baseValueFixedValue());
                    self.premiumRate(paramDirtyData.premiumRate());
                    self.switchButtonRoundingFiguresD().selectedRuleCode(paramDirtyData.roundingFiguresD());
                    self.switchButtonRoundingFiguresF().selectedRuleCode(paramDirtyData.roundingFiguresF());
                    self.comboBoxCoefficient().selectedCode(paramDirtyData.coefficientAttr());
                    self.coefficientFixedValue(paramDirtyData.coefficientFixedValue());
                    self.comboBoxAdjustmentAtr().selectedCode(paramDirtyData.adjustmentAttr());
                }

            }

            initOriginal() {
                var self = this;
                self.easyFormulaName = ko.observable(null);
                self.comboBoxFormulaType = ko.observable(new ComboBox([
                    new ItemModel('0', '計算式１'),
                    new ItemModel('1', '計算式２'),
                    new ItemModel('2', '計算式3')
                ]));
                //              0 Fixed value
                //              1 Company Unit price 
                //              2 Person unit price
                //              3 Payment items
                //              4 Deduction item
                self.comboBoxBaseAmount = ko.observable(new ComboBox([
                    new ItemModel('0', '固定値'),
                    new ItemModel('2', '個人単価'),
                    new ItemModel('3', '支給項目'),
                    new ItemModel('4', '控除項目')
                ]));
                // if formula type 3, base amount attribute: 0, 1, 3, 4
                // if formula type 1 or 2, base amount attribute: 0, 2, 3, 4
                self.comboBoxFormulaType().selectedCode.subscribe(function(codeChange) {
                    if (codeChange === '2') {
                        self.comboBoxBaseAmount().itemList([
                            new ItemModel('0', '固定値'),
                            new ItemModel('1', '会社単価'),
                            new ItemModel('3', '支給項目'),
                            new ItemModel('4', '控除項目')
                        ]);
                    } else {
                        self.comboBoxBaseAmount().itemList([
                            new ItemModel('0', '固定値'),
                            new ItemModel('2', '個人単価'),
                            new ItemModel('3', '支給項目'),
                            new ItemModel('4', '控除項目')
                        ]);
                    }
                });
                self.baseAmountFixedValueOption = ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY"
                }));
                self.baseAmountFixedValue = ko.observable(0);
                self.baseAmountListItem = ko.observableArray([]);
                self.baseAmountSelectionItems = ko.observable('');
                self.optionBaseAmountSelectionItems = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "290px",
                    textalign: "left"
                }));
                //Fixed value  0
                //Standard number of days 1
                //Number of working days 2
                //Work day 3
                //Work days + paid usage 4
                //Reference days * Reference time 5
                //Number of working days * Reference time 6
                //Working days * Reference time 7
                //(Attendance days + number of paid use) * reference time 8
                //Working time 9
                self.comboBoxBaseValue = ko.observable(new ComboBox([
                    new ItemModel('0', '固定値'),
                    new ItemModel('1', '基準日数'),
                    new ItemModel('2', '要勤務日数'),
                    new ItemModel('3', '出勤日数'),
                    new ItemModel('4', '出勤日数+有給使用数'),
                    new ItemModel('5', '基準日数*基準時間'),
                    new ItemModel('6', '要勤務日数*基準時間'),
                    new ItemModel('7', '出勤日数*基準時間'),
                    new ItemModel('8', '(出勤日数+有給使用数)*基準時間'),
                    new ItemModel('9', '出勤時間')
                ]));
                self.baseValueFixedValue = ko.observable(0);
                self.premiumRate = ko.observable(0);
                self.premiumRateInputOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 0,
                    symbolChar: '%'
                }));
                //Round figure: 0
                //Round up: 1
                //Truncate: 2
                //No round figure: 3
                self.switchButtonRoundingFiguresD = ko.observable(new SwitchButton([
                    new ItemModel('0', '四捨五入'),
                    new ItemModel('1', '切り上げ'),
                    new ItemModel('2', '切り捨て'),
                    new ItemModel('3', '端数処理なし')
                ]));
                //Round figure: 0
                //Round up: 1
                //Truncate: 2
                self.switchButtonRoundingFiguresF = ko.observable(new SwitchButton([
                    new ItemModel('0', '四捨五入'),
                    new ItemModel('1', '切り上げ'),
                    new ItemModel('2', '切り捨て')
                ]));
                //TO DO: get items which have CTG_ATR = 2 from table QCAMT_ITEM to add to the item model list from the fourth item 
                self.comboBoxCoefficient = ko.observable(new ComboBox([
                    new ItemModel('0000', '固定値'),
                    new ItemModel('F200', '基準日数'),
                    new ItemModel('F000', '出勤日数+有給使用数')
                ]));
                self.coefficientFixedValue = ko.observable(0);
                //Do not adjust: 0
                //Plus adjustment: 1
                //Minus adjustment: 2
                //Plus and minus inversion: 3
                self.comboBoxAdjustmentAtr = ko.observable(new ComboBox([
                    new ItemModel('0', '調整しない'),
                    new ItemModel('1', 'プラス調整'),
                    new ItemModel('2', 'マイナス調整'),
                    new ItemModel('3', 'プラスマイナス反転')
                ]));
            }

            closeAndReturnData() {
                var self = this;
                let easyFormulaDetail = {
                    easyFormulaCode: '000',
                    easyFormulaName: self.easyFormulaName(),
                    easyFormulaType: self.comboBoxFormulaType().selectedCode(),
                    baseAmountAttr: self.comboBoxBaseAmount().selectedCode(),
                    baseAmountFixedValue: self.baseAmountFixedValue(),
                    baseAmountListItem: self.baseAmountListItem(),
                    baseValueAttr: self.comboBoxBaseValue().selectedCode(),
                    baseValueFixedValue: self.baseValueFixedValue(),
                    premiumRate: self.premiumRate(),
                    roundingFiguresD: self.switchButtonRoundingFiguresD().selectedRuleCode(),
                    roundingFiguresF: self.switchButtonRoundingFiguresF().selectedRuleCode(),
                    coefficientAttr: self.comboBoxCoefficient().selectedCode(),
                    coefficientFixedValue: self.coefficientFixedValue(),
                    adjustmentAttr: self.comboBoxAdjustmentAtr().selectedCode()
                };
                nts.uk.ui.windows.setShared('easyFormulaDetail', easyFormulaDetail);
                nts.uk.ui.windows.close();
            }

            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }

        export class ComboBox {
            itemList: KnockoutObservableArray<ItemModel>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<string>
            selectedCode: KnockoutObservable<string>;

            constructor(itemList: Array<ItemModel>) {
                var self = this;
                self.itemList = ko.observableArray(itemList);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable('0');
                self.selectedCode = ko.observable('0');
            }
        }

        export class SwitchButton {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;

            constructor(itemList: Array<ItemModel>) {
                var self = this;
                self.roundingRules = ko.observableArray(itemList);
                self.selectedRuleCode = ko.observable('0');
            }
        }

        class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}