var qpp021;
(function (qpp021) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
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
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.next = function () {
                    $('#wizard').ntsWizard("next");
                };
                ScreenModel.prototype.previous = function () {
                    $('#wizard').ntsWizard("prev");
                };
                ScreenModel.prototype.openDialogScreenD = function () {
                    nts.uk.ui.windows.sub.modal('/view/qpp/021/d/index.xhtml', { title: '詳細設定', });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ComboBoxModel = (function () {
                function ComboBoxModel(cbCode, cbName) {
                    this.cbCode = cbCode;
                    this.cbName = cbName;
                }
                return ComboBoxModel;
            }());
            var RadioBoxModel = (function () {
                function RadioBoxModel(rbCode, rbName) {
                    this.rbCode = rbCode;
                    this.rbName = rbName;
                }
                return RadioBoxModel;
            }());
            var PrintTypeModel = (function () {
                function PrintTypeModel(printTypeCode, printTypeName) {
                    this.printTypeCode = printTypeCode;
                    this.printTypeName = printTypeName;
                }
                return PrintTypeModel;
            }());
            var LineItemLayoutModel = (function () {
                function LineItemLayoutModel(statementCode, statementName, layoutAttributeId, layoutAttributeName) {
                    this.statementCode = statementCode;
                    this.statementName = statementCode + " " + statementName;
                    this.layoutAttributeId = layoutAttributeId;
                    this.layoutAttributeName = layoutAttributeName;
                }
                return LineItemLayoutModel;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp021.b || (qpp021.b = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.b.vm.js.map