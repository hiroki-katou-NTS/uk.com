module qpp021.b.viewmodel {
    export class ScreenModel {
        selectPrintType: KnockoutObservable<number>;

        stepList: Array<nts.uk.ui.NtsWizardStep>;
        stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        processDateComboBox: KnockoutObservableArray<ComboBoxModel>;
        selectedCbCode: KnockoutObservable<string>;

        selectCategorys: KnockoutObservableArray<RadioBoxModel>;
        selectedRbCode: KnockoutObservable<number>;

        selectPrintTypes: KnockoutObservableArray<PrintTypeModel>;
        selectPrintTypeCode: KnockoutObservable<string>;

        selectLineItemLayout: KnockoutObservableArray<LineItemLayoutModel>;
        selectLineItemCodes: KnockoutObservableArray<number>;


        constructor() {
            let self = this;
            self.selectPrintType = ko.observable(1);
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

            self.selectCategorys = ko.observableArray([
                new RadioBoxModel(1, '印刷タイプから選択する'),
                new RadioBoxModel(2, '明細レイアウトから選択する'),
            ]);
            self.selectedRbCode = ko.observable(1);

            self.selectPrintTypes = ko.observableArray([
                new PrintTypeModel(0, 'レーザー　A4　縦向き　1人'),
                new PrintTypeModel(1, 'レーザー　A4　縦向き　2人'),
                new PrintTypeModel(2, 'レーザー　A4　縦向き　3人'),
                new PrintTypeModel(3, 'レーザー　A4　横向き　2人'),
                new PrintTypeModel(4, 'レーザー(圧着式)　縦向き　1人'),
                new PrintTypeModel(5, 'レーザー(圧着式)　横向き　1人'),
                new PrintTypeModel(6, 'ドットプリンタ　連続用紙　1人'),
                new PrintTypeModel(7, 'PAYS単票'),
                new PrintTypeModel(8, 'PAYS連続')
            ]);
            self.selectPrintTypeCode = ko.observable("01");

            self.selectLineItemLayout = ko.observableArray([
                new LineItemLayoutModel('01', 'Screen A', 0, "A4　縦向き　1人"),
                new LineItemLayoutModel('02', 'Screen B', 1, "A4　縦向き　2人"),
                new LineItemLayoutModel('03', 'Screen C', 2, "A4　縦向き　3人"),
                new LineItemLayoutModel('04', 'Screen D', 3, "A4　縦向き　4人"),
            ]);
            self.selectLineItemCodes = ko.observableArray([]);

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
        
        openDialogScreenD(){
                //nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected(), true);
                //nts.uk.ui.windows.setShared('CMM013_startDateLast', self.startDateLast(), true);
                nts.uk.ui.windows.sub.modal('/view/qpp/021/d/index.xhtml', { title: '詳細設定', })
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