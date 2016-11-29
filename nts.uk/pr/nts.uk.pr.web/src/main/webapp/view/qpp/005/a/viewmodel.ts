module nts.uk.pr.view.qpp005 {
    import option = nts.uk.ui.option;

    export module viewmodel {
        export class ScreenModel {
            isHandInput: KnockoutObservable<boolean>;
            paymentDataResult: KnockoutObservable<viewModel.PaymentDataResultViewModel>;
            categories: KnockoutObservable<CategoriesList>;
            option: KnockoutObservable<any>;
            employee: KnockoutObservable<Employee>;
            employeeList: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.isHandInput = ko.observable(true);
                self.paymentDataResult = ko.observable(new viewModel.PaymentDataResultViewModel());
                self.categories = ko.observable(new CategoriesList());
                self.option = ko.mapping.fromJS(new option.TextEditorOption());
                self.employee = ko.observable<Employee>();
                var employeeMocks = [
                    { personId: 'A00000000000000000000000000000000001', code: 'A000000000001', name: '日通　社員1' },
                    { personId: 'A00000000000000000000000000000000002', code: 'A000000000002', name: '日通　社員2' },
                    { personId: 'A00000000000000000000000000000000003', code: 'A000000000003', name: '日通　社員3' },
                    { personId: 'A00000000000000000000000000000000004', code: 'A000000000004', name: '日通　社員4' },
                    { personId: 'A00000000000000000000000000000000005', code: 'A000000000005', name: '日通　社員5' },
                    { personId: 'A00000000000000000000000000000000006', code: 'A000000000006', name: '日通　社員6' },
                    { personId: 'A00000000000000000000000000000000007', code: 'A000000000007', name: '日通　社員7' },
                    { personId: 'A00000000000000000000000000000000008', code: 'A000000000008', name: '日通　社員8' },
                    { personId: 'A00000000000000000000000000000000009', code: 'A000000000009', name: '日通　社員9' },
                    { personId: 'A00000000000000000000000000000000010', code: 'A000000000010', name: '日通　社員10' }
                ];
                var employees = _.map(employeeMocks, function(employee) {
                    return new Employee(employee.personId, employee.code, employee.name);
                });
                self.employeeList = ko.observableArray(employees);
                self.employee(employees[0]);
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                qpp005.service.getPaymentData(self.employee().personId(), self.employee().code()).done(function(res: any) {

                    ko.mapping.fromJS(res, {}, self.paymentDataResult());

                    var cates = _.map(res.categories, function(category: viewModel.LayoutMasterCategoryViewModel): Category {
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
                        };
                    });
                    self.categories().items(cates);
                    self.categories().selectedCode(res.categories[0].categoryAttribute);


                    dfd.resolve();
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            }

            /** Event click: 計算/登録*/
            register() {
                var self = this;
                // TODO: Check error input

                qpp005.service.register(self.paymentDataResult()).done(function(res) {

                });
            }

            /** Event click: 対象者*/
            openEmployeeList() {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qpp/005/dlgemployeelist/index.xhtml', { title: '社員選択' }).onClosed(() => {
                    var employee = nts.uk.ui.windows.getShared('employee');
                    self.employee(employee);

                    self.startPage();
                    return this;
                });
            }

            /** Event: Previous employee */
            prevEmployee() {
                var self = this;

                var eIdx = _.indexOf(self.employeeList(), self.employee());
                var eSize = self.employeeList().length;
                if (eIdx === 0) {
                    return;
                }

                self.employee(self.employeeList()[eIdx - 1]);
                self.startPage();
            }

            /** Event: Next employee */
            nextEmployee() {
                var self = this;

                var eIdx = _.indexOf(self.employeeList(), self.employee());
                var eSize = self.employeeList().length;
                if (eIdx === eSize - 1) {
                    return;
                }

                self.employee(self.employeeList()[eIdx + 1]);
                self.startPage();
            }
        };

        //        private parseResultDtoToViewModel(resultDto) {
        //              //        }
        
        export class Employee {
            personId: KnockoutObservable<string>;
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;

            constructor(personId: string, code: string, name: string) {
                var self = this;

                self.personId = ko.observable(personId);
                self.code = ko.observable(code);
                self.name = ko.observable(name);
            }
        }

        /**
          * Model namespace.
       */
        export module viewModel {
            export class PaymentDataResultViewModel {
                paymentHeader: PaymentDataHeaderViewModel;
                categories: Array<LayoutMasterCategoryViewModel>;
            }

            // header
            export class PaymentDataHeaderViewModel {
                dependentNumber: number;
                specificationCode: string;
                specificationName: string;
                makeMethodFlag: number;
                employeeCode: string;
                comment: string;
                isCreated: boolean;
            }

            // categories
            export class LayoutMasterCategoryViewModel {
                categoryAttribute: number;
                categoryPosition: number;
                lineCounts: number;
                details: Array<DetailItemViewModel>;
            }

            // item
            export class DetailItemViewModel {
                categoryAtr: number;
                itemAtr: number;
                itemCode: string;
                itemName: string;
                value: KnockoutObservable<number>;
                columnPosition: number;
                linePosition: number;
                deductAtr: number;
                isCreated: boolean;

                constructor(categoryAtr: number, itemAtr: number, itemCode: string, itemName: string, value: number, 
                            columnPosition: number, linePosition: number, deductAtr: number, isCreated: boolean) {
                    var self = this;
                    self.categoryAtr = categoryAtr;
                    self.itemAtr = itemAtr;
                    self.itemCode = itemCode;
                    self.itemName = itemName;
                    self.value = ko.observable(value);
                    self.columnPosition = columnPosition;
                    self.linePosition = linePosition;
                    self.deductAtr = deductAtr;
                    self.isCreated = isCreated;
                }
            }
        }

        export class CategoriesList {
            items: KnockoutObservableArray<any>;
            selectedCode: KnockoutObservable<any>;
            selectionChangedEvent = $.Callbacks();
            unselecting: KnockoutObservable<boolean>;

            constructor() {
                var self = this;

                self.items = ko.observableArray([]);
                self.selectedCode = ko.observable();
                self.selectedCode.subscribe(function(selectedCode) {
                    self.selectionChangedEvent.fire(selectedCode);
                });
            }
        }

        /** 
        * View Model Category
        */
        export class Category {
            code: number;
            name: string;
            /**
             * Constructor
             * @param: code
             * @param: name
             */
            constructor(code: number, name: string) {
                var self = this;
                self.code = code;
                self.name = name;
            }
        }
    }
}