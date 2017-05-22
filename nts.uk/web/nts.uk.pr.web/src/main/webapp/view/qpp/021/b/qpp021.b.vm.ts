module qpp021.b.viewmodel {
    export class ScreenModel {
        stepList: Array<nts.uk.ui.NtsWizardStep>;
        stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        processDateComboBox: KnockoutObservableArray<ComboBoxModel>;
        selectedCbCode: KnockoutObservable<string>;

        selectPrinterCategory: KnockoutObservable<number>;

        selectPrintTypes: KnockoutObservableArray<RadioBoxModel>;
        selectPrintTypeCode: KnockoutObservable<number>;

        selectPrintTypesList: KnockoutObservableArray<PrintTypeModel>;
        selectPrintTypeListCode: KnockoutObservable<number>;

        selectLineItemLayout: KnockoutObservableArray<LineItemLayoutModel>;
        selectLineItemCode: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self._init();

            self.selectPrintTypeCode.subscribe(function(newValue) {
                if (!newValue) {
                    return;
                }
                // waiting kiban...............
            });
        }

        _init() {
            let self = this;
            self.selectPrinterCategory = ko.observable(0);
            self.stepList = [
                { content: '.A_LBL_002-step' },
                { content: '.A_LBL_003-step' },
                { content: '.A_LBL_004-step' },
            ];
            self.stepSelected = ko.observable({ id: 'A_LBL_002-step', content: '.A_LBL_002-step' });

            self.processDateComboBox = ko.observableArray([
                new ComboBoxModel('01', '基本給'),
                new ComboBoxModel('02', '役職手当'),
                new ComboBoxModel('03', '基本給'),
                new ComboBoxModel('04', '役職手当'),
                new ComboBoxModel('05', '基本給')
            ]);
            self.selectedCbCode = ko.observable('02');

            self.selectPrintTypes = ko.observableArray([
                new RadioBoxModel(0, '印刷タイプから選択する'),
                new RadioBoxModel(1, '明細レイアウトから選択する'),
            ]);
            self.selectPrintTypeCode = ko.observable(0);

            self.selectPrintTypesList = ko.observableArray([
                new PrintTypeModel(0, 'A4縦1人印刷'),
                new PrintTypeModel(1, 'A4縦2人印刷'),
                new PrintTypeModel(2, 'A4縦3人印刷'),
                new PrintTypeModel(3, 'A4横2人印刷'),
                new PrintTypeModel(4, '圧着式（Z折り）'),
                new PrintTypeModel(5, '圧着式（はがき）')
            ]);
            self.selectPrintTypeListCode = ko.observable(0);

            self.selectLineItemLayout = ko.observableArray([
                new LineItemLayoutModel('01', 'Screen A', 0, "レーザー　A4　縦向き　1人"),
                new LineItemLayoutModel('02', 'Screen B', 1, "レーザー　A4　縦向き　2人"),
                new LineItemLayoutModel('03', 'Screen C', 2, "レーザー　A4　縦向き　3人"),
                new LineItemLayoutModel('04', 'Screen D', 3, "レーザー　A4　横向き　2人"),
                new LineItemLayoutModel('05', 'Screen E', 4, "レーザー(圧着式)　縦向き　1人"),
                new LineItemLayoutModel('06', 'Screen F', 5, "レーザー(圧着式)　横向き　1人"),
                new LineItemLayoutModel('07', 'Screen G', 6, "ドットプリンタ　連続用紙　1人"),
                new LineItemLayoutModel('08', 'Screen H', 7, "PAYS単票"),
                new LineItemLayoutModel('09', 'Screen D', 8, "PAYS連続"),
            ]);
            self.selectLineItemCode = ko.observable(0);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        next() {
            $('#wizard').ntsWizard("next");
        }

        previous() {
            $('#wizard').ntsWizard("prev");
        }

        openDialogScreenD() {
            let self = this;
            let printType: number = 0;
            let visibleEnable: number;//1 ->   visible = true and enable = true, 2 -> visible = true and enable = false  , 3 -> visible = false
            let isVisible: boolean;
            if (self.selectPrintTypeCode() == 0) {
                if (self.selectPrintTypeListCode() == 3 || self.selectPrintTypeListCode() == 5) {
                    printType = 0;
                    isVisible = true;
                    visibleEnable = 3;
                } else {
                    printType = 1;
                    isVisible = false;
                    visibleEnable = 2;
                    if (self.selectPrinterCategory() == 0) {
                        visibleEnable = 1;
                    }
                }
            } else {
                if (self.selectLineItemCode() == 3 || self.selectLineItemCode() == 5) {
                    printType = 0;
                    isVisible = true;
                    visibleEnable = 3;
                } else {
                    printType = 1;
                    isVisible = false;
                    visibleEnable = 2;
                    if (self.selectPrinterCategory() == 0) {
                        visibleEnable = 1;
                    }
                }
            }
            nts.uk.ui.windows.setShared('QPP021_print_type', printType, true);
            nts.uk.ui.windows.setShared('QPP021_visible_Enable', visibleEnable, true);
            nts.uk.ui.windows.setShared('QPP021_visible', isVisible, true);
            nts.uk.ui.windows.sub.modal('/view/qpp/021/d/index.xhtml', { title: '詳細設定', });
        }

        openDialogScreenH(): void {
            var self = this;
            var processingNo: number = +self.selectedCbCode();
            var processingYm: number = 201705;
            nts.uk.ui.windows.setShared("processingNo", processingNo);
            nts.uk.ui.windows.setShared("processingYm", processingYm);
            nts.uk.ui.windows.sub.modal('/view/qpp/021/h/index.xhtml', { title: '連絡事項の設定', dialogClass: 'no-close' });
        }

        openDialogScreenI(): void {
            let self = this;
            let processingNo: number = 1;
            let processingYm: number = 201705;
            nts.uk.ui.windows.setShared("processingNo", processingNo);
            nts.uk.ui.windows.setShared("processingYm", processingYm);
            nts.uk.ui.windows.sub.modal('/view/qpp/021/i/index.xhtml', { title: 'コメント登録', dialogClass: 'no-close' });
        }

        openDialogRefundPadding(): void {
            nts.uk.ui.windows.sub.modal('/view/qpp/021/e/index.xhtml', { title: '余白設定', dialogClass: 'no-close' });
            //            var printTypeRandom = Math.floor((Math.random() * 3) + 1);
            //            if (printTypeRandom == 1) {
            //                nts.uk.ui.windows.sub.modal('/view/qpp/021/e/index.xhtml', { title: '余白設定', dialogClass: 'no-close' });
            //            }
            //
            //            if (printTypeRandom == 2) {
            //                nts.uk.ui.windows.sub.modal('/view/qpp/021/f/index.xhtml', { title: '余白設定2', dialogClass: 'no-close' });
            //            }
            //
            //            if (printTypeRandom == 3) {
            //                nts.uk.ui.windows.sub.modal('/view/qpp/021/g/index.xhtml', { title: '余白設定３', dialogClass: 'no-close' });
            //            }
        }
    }

    class ComboBoxModel {
        cbCode: string;
        cbName: string;
        constructor(cbCode: string, cbName: string) {
            this.cbCode = cbCode;
            this.cbName = cbName;
        }
    }

    class RadioBoxModel {
        rbCode: number;
        rbName: string;
        constructor(rbCode: number, rbName: string) {
            this.rbCode = rbCode;
            this.rbName = rbName;
        }
    }

    class PrintTypeModel {
        printTypeCode: number;
        printTypeName: string;
        constructor(printTypeCode: number, printTypeName: string) {
            this.printTypeCode = printTypeCode;
            this.printTypeName = printTypeName;
        }
    }

    class LineItemLayoutModel {
        statementCode: string;
        statementName: string;
        layoutAttributeId: number;
        layoutAttributeName: string;
        constructor(statementCode: string, statementName: string, layoutAttributeId: number, layoutAttributeName: string) {
            this.statementCode = statementCode;
            this.statementName = statementCode + " " + statementName;
            this.layoutAttributeId = layoutAttributeId;
            this.layoutAttributeName = layoutAttributeName;
        }
    }

}