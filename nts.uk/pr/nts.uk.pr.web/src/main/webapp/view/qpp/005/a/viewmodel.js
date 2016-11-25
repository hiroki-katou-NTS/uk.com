var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var option = nts.uk.ui.option;
                    var viewmodel;
                    (function (viewmodel) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                var self = this;
                                self.isHandInput = ko.observable(true);
                                self.paymentDataResult = ko.observable();
                                self.categories = ko.observable(new CategoriesList());
                                self.option = ko.mapping.fromJS(new option.TextEditorOption());
                                self.employeeCode = ko.observable('A000000000001');
                                self.employeeName = ko.observable('社員1');
                                var employeeMocks = [
                                    { code: 'A000000000001', name: '日通　社員1' },
                                    { code: 'A000000000002', name: '日通　社員2' },
                                    { code: 'A000000000003', name: '日通　社員3' },
                                    { code: 'A000000000004', name: '日通　社員4' },
                                    { code: 'A000000000005', name: '日通　社員5' },
                                    { code: 'A000000000006', name: '日通　社員6' },
                                    { code: 'A000000000007', name: '日通　社員7' },
                                    { code: 'A000000000008', name: '日通　社員8' },
                                    { code: 'A000000000009', name: '日通　社員9' },
                                    { code: 'A000000000010', name: '日通　社員10' }
                                ];
                                var employees = _.map(employeeMocks, function (employee) {
                                    return new Employee(employee.code, employee.name);
                                });
                                self.employeeList = ko.observableArray(employees);
                            }
                            ScreenModel.prototype.startPage = function () {
                                var self = this;
                                var dfd = $.Deferred();
                                qpp005.service.getPaymentData("A00000000000000000000000000000000001", self.employeeCode()).done(function (paymentResult) {
                                    self.paymentDataResult(paymentResult);
                                    var cates = _.map(paymentResult.categories, function (category) {
                                        switch (category.categoryAttribute) {
                                            case 0:
                                                return new Category(0, '支給');
                                            case 1:
                                                return new Category(1, '控除');
                                            case 2:
                                                return new Category(2, '勤怠');
                                            case 3:
                                                return new Category(3, '記事');
                                            default:
                                                break;
                                        }
                                        ;
                                    });
                                    self.categories().items(cates);
                                    self.categories().selectedCode(paymentResult.categories[0].categoryAttribute);
                                    dfd.resolve();
                                }).fail(function (res) {
                                    // Alert message
                                    alert(res);
                                });
                                // Return.
                                return dfd.promise();
                            };
                            ScreenModel.prototype.openEmployeeList = function () {
                                var self = this;
                                nts.uk.ui.windows.sub.modal('/view/qpp/005/dlgemployeelist/index.xhtml', { title: '社員選択' }).onClosed(function () {
                                    var employeeCode = nts.uk.ui.windows.getShared('employee');
                                    self.employeeCode(employeeCode);
                                    self.employeeName(employee);
                                });
                            };
                            ScreenModel.prototype.definedItems = function () {
                            };
                            return ScreenModel;
                        }());
                        viewmodel.ScreenModel = ScreenModel;
                        ;
                        function Employee(code, name) {
                            var self = this;
                            self.code = code;
                            self.name = name;
                        }
                        /**
                          * Model namespace.
                       */
                        var model;
                        (function (model) {
                            var PaymentDataResult = (function () {
                                function PaymentDataResult() {
                                }
                                return PaymentDataResult;
                            }());
                            model.PaymentDataResult = PaymentDataResult;
                            // header
                            var PaymentDataHeaderModel = (function () {
                                function PaymentDataHeaderModel() {
                                }
                                return PaymentDataHeaderModel;
                            }());
                            model.PaymentDataHeaderModel = PaymentDataHeaderModel;
                            // categories
                            var LayoutMasterCategoryModel = (function () {
                                function LayoutMasterCategoryModel() {
                                }
                                return LayoutMasterCategoryModel;
                            }());
                            model.LayoutMasterCategoryModel = LayoutMasterCategoryModel;
                            // item
                            var DetailItemModel = (function () {
                                function DetailItemModel(categoryAtr, itemCode, itemNme, value, linePosition, columnPosition, isCreated) {
                                    var self = this;
                                    self.categoryAtr = categoryAtr;
                                    self.itemCode = itemCode;
                                    self.itemNme = itemNme;
                                    self.value = ko.observable(value);
                                    self.linePosition = linePosition;
                                    self.columnPosition = columnPosition;
                                    self.isCreated = isCreated;
                                }
                                return DetailItemModel;
                            }());
                            model.DetailItemModel = DetailItemModel;
                        })(model = viewmodel.model || (viewmodel.model = {}));
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
                        /**
                        * View Model Category
                        */
                        var Category = (function () {
                            /**
                             * Constructor
                             * @param: code
                             * @param: name
                             */
                            function Category(code, name) {
                                var self = this;
                                self.code = code;
                                self.name = name;
                            }
                            return Category;
                        }());
                        viewmodel.Category = Category;
                        var self = (function () {
                            function self(categoryAtr, itemCode, itemNme, value, colPosition, rowPosition, isCreated) {
                                var self = this;
                                self.categoryAtr = categoryAtr;
                                self.itemCode = itemCode;
                                self.itemNme = itemNme;
                                self.value = value;
                                self.colPosition = colPosition;
                                self.rowPosition = rowPosition;
                                self.isCreated = isCreated;
                            }
                            return self;
                        }());
                        viewmodel.self = self;
                    })(viewmodel = qpp005.viewmodel || (qpp005.viewmodel = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
