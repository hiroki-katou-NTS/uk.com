module nts.uk.pr.view.qpp021.b {
    export module viewmodel {
        export class ScreenModel {
            stepList: Array<nts.uk.ui.NtsWizardStep>;
            stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

            processDateComboBox: KnockoutObservableArray<ComboBoxModel>;
            selectedCbCode: KnockoutObservable<number>;

            selectPrinterCategory: KnockoutObservable<number>;

            selectPrintTypes: KnockoutObservableArray<RadioBoxModel>;
            selectPrintTypeCode: KnockoutObservable<number>;

            selectPrintTypesList: KnockoutObservableArray<PrintTypeModel>;
            selectPrintTypeListCode: KnockoutObservable<number>;

            selectLineItemLayout: KnockoutObservableArray<LineItemLayoutModel>;
            selectLineItemCode: KnockoutObservable<string>;

            currentProcessingYm: KnockoutObservable<string>;
            isEnablePrintTypes: KnockoutObservable<boolean>;
            isEnableLineItemLayout: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self._init();

                self.selectPrintTypeCode.subscribe(function(newValue) {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    if (newValue == 1) {
                        self.isEnablePrintTypes(false);
                        self.isEnableLineItemLayout(true);
                    } else {
                        self.isEnablePrintTypes(true);
                        self.isEnableLineItemLayout(false);
                    }
                });

                self.selectedCbCode.subscribe(function(newValue) {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    let currentProcessDate = _.find(self.processDateComboBox(), function(item) { return item.cbCode == newValue }).currentProcessingYm;
                    self.currentProcessingYm(nts.uk.time.formatYearMonth(currentProcessDate));
                    self.getLineItemLayout(currentProcessDate);
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

                self.processDateComboBox = ko.observableArray([]);
                self.selectedCbCode = ko.observable(1);

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

                self.selectLineItemLayout = ko.observableArray([]);
                self.selectLineItemCode = ko.observable("");

                self.currentProcessingYm = ko.observable("");
                self.isEnablePrintTypes = ko.observable(true);
                self.isEnableLineItemLayout = ko.observable(false);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let baseYM = 0;
                self.processDateComboBox([]);
                service.getPaydayProcessing().done(data => {
                    let lstprocessDate = _.map(data, function(item: any) {
                        return new ComboBoxModel(item.processingNo, item.processingName, item.currentProcessingYm);
                    });
                    if (lstprocessDate && lstprocessDate.length > 0) {
                        self.processDateComboBox(lstprocessDate);
                        self.currentProcessingYm(nts.uk.time.formatYearMonth(lstprocessDate[0].currentProcessingYm));
                        baseYM = lstprocessDate[0].currentProcessingYm;
                    }
                    self.getLineItemLayout(baseYM);
                }).fail(function(error) {
                    console.log(error.message);
                });
                dfd.resolve();
                return dfd.promise();
            }

            getLineItemLayout(baseYM: number) {
                let self = this;
                let dfd = $.Deferred();
                if (baseYM == 0) return;
                self.selectLineItemLayout([]);
                service.getLineItemLayout(baseYM).done(data => {
                    let lineItemLayouts = _.map(data, function(item: any) {
                        return new LineItemLayoutModel(item.stmtCode, item.stmtName, item.layoutAtr.value, item.layoutAtr.localizedName);
                    });
                    if (lineItemLayouts && lineItemLayouts.length > 0) {
                        self.selectLineItemLayout(lineItemLayouts);
                         self.selectLineItemCode(lineItemLayouts[0].statementCode);
                    }
                });
            }

            next() {
                let self = this;
                if (self.selectPrintTypeCode() == 1 && nts.uk.text.isNullOrEmpty(self.selectLineItemCode())) {
                    nts.uk.ui.dialog.alert("明細レイアウトが選択されていません。")
                    return;
                }
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
                    if (nts.uk.text.isNullOrEmpty(self.selectLineItemCode())) {
                        nts.uk.ui.dialog.alert("明細レイアウトが選択されていません。")
                        return;
                    }
                    let itemLayoutSelect = _.find(self.selectLineItemLayout(), function(item) {
                        return item.statementCode == self.selectLineItemCode();
                    });
                    if (itemLayoutSelect.layoutAttributeId == 3 || itemLayoutSelect.layoutAttributeId == 5) {
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
            cbCode: number;
            cbName: string;
            currentProcessingYm: number;
            constructor(cbCode: number, cbName: string, currentProcessingYm: number) {
                this.cbCode = cbCode;
                this.cbName = nts.uk.text.padLeft(cbCode.toString(), '0', 2) + ": " + cbName;
                this.currentProcessingYm = currentProcessingYm;
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
}