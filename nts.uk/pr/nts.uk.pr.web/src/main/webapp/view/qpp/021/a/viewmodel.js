var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ItemModel = (function () {
                                function ItemModel(id, code, name) {
                                    var self = this;
                                    this.id = id;
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                            var RadioBox = (function () {
                                function RadioBox() {
                                    var self = this;
                                    self.enable = ko.observable(true);
                                    self.items = ko.observableArray([
                                        { value: 1, text: '単票出力' },
                                        { value: 2, text: '連続帳票印刷' },
                                        { value: 3, text: '圧着式印刷（Z折り）' },
                                        { value: 4, text: '圧着式印刷（はがき）' }
                                    ]);
                                    self.selectedValue = ko.observable({ value: 1, text: 'One' });
                                }
                                return RadioBox;
                            }());
                            viewmodel.RadioBox = RadioBox;
                            var Listbox = (function () {
                                function Listbox() {
                                    var self = this;
                                    self.itemList = ko.observableArray([
                                        new ItemModel('A00000000000000000000000000000000001', '00000000001', '日通　社員１'),
                                        new ItemModel('A00000000000000000000000000000000002', '00000000002', '日通　社員2'),
                                        new ItemModel('A00000000000000000000000000000000003', '00000000003', '日通　社員3'),
                                        new ItemModel('A00000000000000000000000000000000004', '00000000004', '日通　社員4'),
                                        new ItemModel('A00000000000000000000000000000000005', '00000000005', '日通　社員5'),
                                        new ItemModel('A00000000000000000000000000000000006', '00000000006', '日通　社員6'),
                                        new ItemModel('A00000000000000000000000000000000007', '00000000007', '日通　社員7'),
                                        new ItemModel('A00000000000000000000000000000000008', '00000000008', '日通　社員8'),
                                        new ItemModel('A00000000000000000000000000000000009', '00000000009', '日通　社員9'),
                                        new ItemModel('A00000000000000000000000000000000010', '00000000010', '日通　社員１0'),
                                    ]);
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(3);
                                    self.selectedCode = ko.observable(null);
                                    self.isEnable = ko.observable(true);
                                    self.selectedCodes = ko.observableArray([]);
                                }
                                return Listbox;
                            }());
                            viewmodel.Listbox = Listbox;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.isEnable = ko.observable(true);
                                    var self = this;
                                    self.listBox = new Listbox();
                                    self.radioBox = new RadioBox();
                                }
                                ScreenModel.prototype.print = function () {
                                    var self = this;
                                    nts.uk.pr.view.qpp021.a.service.print(self.listBox.selectedCode(), '0000000004');
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                                  * Model namespace.
                               */
                            var PaymentDataResultViewModel = (function () {
                                function PaymentDataResultViewModel(paymentHeader, categories, remarks) {
                                    var self = this;
                                    self.paymentHeader = paymentHeader;
                                    self.categories = categories;
                                    self.remarks = ko.observable(remarks);
                                    self.remarkCount = ko.computed(function () {
                                        return nts.uk.text.format('入力文字数：{0}文字', self.remarks() == undefined ? 0 : self.remarks().length);
                                    });
                                }
                                return PaymentDataResultViewModel;
                            }());
                            viewmodel.PaymentDataResultViewModel = PaymentDataResultViewModel;
                            // header
                            var PaymentDataHeaderViewModel = (function () {
                                function PaymentDataHeaderViewModel(personId, companyName, departmentCode, departmentName, personName, processingYM, dependentNumber, specificationCode, specificationName, makeMethodFlag, employeeCode, comment, printPositionCategories, isCreated) {
                                    var self = this;
                                    self.personId = personId;
                                    self.personName = personName;
                                    self.companyName = companyName;
                                    self.departmentCode = departmentCode;
                                    self.departmentName = departmentName;
                                    self.processingYM = processingYM;
                                    self.dependentNumber = dependentNumber;
                                    self.specificationCode = specificationCode;
                                    self.makeMethodFlag = makeMethodFlag;
                                    self.employeeCode = employeeCode;
                                    self.comment = ko.observable(comment);
                                    self.printPositionCategories = printPositionCategories;
                                    self.isCreated = isCreated;
                                }
                                return PaymentDataHeaderViewModel;
                            }());
                            viewmodel.PaymentDataHeaderViewModel = PaymentDataHeaderViewModel;
                            var PrintPositionCategoryViewModel = (function () {
                                function PrintPositionCategoryViewModel() {
                                }
                                return PrintPositionCategoryViewModel;
                            }());
                            viewmodel.PrintPositionCategoryViewModel = PrintPositionCategoryViewModel;
                            // categories
                            var LayoutMasterCategoryViewModel = (function () {
                                function LayoutMasterCategoryViewModel() {
                                }
                                return LayoutMasterCategoryViewModel;
                            }());
                            viewmodel.LayoutMasterCategoryViewModel = LayoutMasterCategoryViewModel;
                            // item
                            var DetailItemViewModel = (function () {
                                function DetailItemViewModel(categoryAtr, itemAtr, itemCode, itemName, value, calculationMethod, correctFlag, columnPosition, linePosition, deductAtr, displayAtr, taxAtr, limitAmount, commuteAllowTaxImpose, commuteAllowMonth, commuteAllowFraction, isCreated, itemType) {
                                    var self = this;
                                    self.categoryAtr = categoryAtr;
                                    self.itemAtr = itemAtr;
                                    self.itemCode = itemCode;
                                    self.itemName = itemName;
                                    self.value = ko.observable(value);
                                    self.calculationMethod = calculationMethod;
                                    self.columnPosition = columnPosition;
                                    self.linePosition = linePosition;
                                    self.deductAtr = deductAtr;
                                    self.displayAtr = displayAtr;
                                    self.taxAtr = taxAtr;
                                    self.limitAmount = limitAmount;
                                    self.commuteAllowTaxImpose = commuteAllowTaxImpose;
                                    self.commuteAllowMonth = commuteAllowMonth;
                                    self.commuteAllowFraction = commuteAllowFraction;
                                    self.isCreated = isCreated;
                                    self.itemType = itemType;
                                }
                                return DetailItemViewModel;
                            }());
                            viewmodel.DetailItemViewModel = DetailItemViewModel;
                            var CategoriesList = (function () {
                                function CategoriesList() {
                                    this.selectionChangedEvent = $.Callbacks();
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.selectedCode = ko.observable();
                                    self.selectedCode.subscribe(function (selectedCode) {
                                        self.selectionChangedEvent.fire(selectedCode);
                                    });
                                }
                                return CategoriesList;
                            }());
                            viewmodel.CategoriesList = CategoriesList;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qpp021.a || (qpp021.a = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
