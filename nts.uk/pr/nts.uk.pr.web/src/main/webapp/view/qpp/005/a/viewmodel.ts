module nts.uk.pr.view.qpp005 {
    import option = nts.uk.ui.option;

    export module viewmodel {
        export class ScreenModel {
            isHandInput: KnockoutObservable<boolean>;
            paymentDataResult: KnockoutObservable<model.PaymentDataResult>;
            categories: KnockoutObservable<CategoriesList>;
            option: KnockoutObservable<any>;
            employeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.isHandInput = ko.observable(true);
                self.paymentDataResult = ko.observable<model.PaymentDataResult>();
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
                var employees = _.map(employeeMocks, function(employee) {
                    return new Employee(employee.code, employee.name);
                });
                self.employeeList = ko.observableArray(employees);
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                qpp005.service.getPaymentData("A00000000000000000000000000000000001", self.employeeCode()).done(function(paymentResult: any) {
                    self.paymentDataResult(paymentResult);
                    var cates = _.map(paymentResult.categories, function(category: model.LayoutMasterCategoryModel): Category {
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
                    self.categories().selectedCode(paymentResult.categories[0].categoryAttribute);


                    dfd.resolve();
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            }

            openEmployeeList() {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qpp/005/dlgemployeelist/index.xhtml', { title: '社員選択' }).onClosed(() => {
                    var employeeCode = nts.uk.ui.windows.getShared('employee');
                    self.employeeCode(employeeCode);
                    self.employeeName(employee);
                });

            }

            public definedItems() {

            }
        };

        function Employee(code, name) {
            var self = this;

            self.code = code;
            self.name = name;
        }

        /**
          * Model namespace.
       */
        export module model {
            export class PaymentDataResult {
                paymentHeader: PaymentDataHeaderModel;
                categories: Array<LayoutMasterCategoryModel>;
            }

            // header
            export class PaymentDataHeaderModel {
                dependentNumber: number;
                specificationCode: string;
                specificationName: string;
                makeMethodFlag: number;
                employeeCode: string;
                comment: string;
            }

            // categories
            export class LayoutMasterCategoryModel {
                categoryAttribute: number;
                categoryPosition: number;
                lineCounts: number;
                details: Array<DetailItemModel>;
            }

            // item
            export class DetailItemModel {
                categoryAtr: number;
                itemCode: string;
                itemNme: string;
                value: any;
                linePosition: number;
                columnPosition: number;
                isCreated: boolean;

                constructor(categoryAtr: number, itemCode: string, itemNme: string, value: number, linePosition: number, columnPosition: number, isCreated: boolean) {
                    var self = this;

                    self.categoryAtr = categoryAtr;
                    self.itemCode = itemCode;
                    self.itemNme = itemNme;
                    self.value = ko.observable(value);
                    self.linePosition = linePosition;
                    self.columnPosition = columnPosition;
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

        export class self {
            categoryAtr: number;
            itemCode: string;
            itemNme: string;
            value: number;
            colPosition: number;
            rowPosition: number;
            isCreated: boolean;

            constructor(categoryAtr: number, itemCode: string, itemNme: string, value: number, colPosition: number, rowPosition: number, isCreated: boolean) {
                var self = this;
                self.categoryAtr = categoryAtr;
                self.itemCode = itemCode;
                self.itemNme = itemNme;
                self.value = value;
                self.colPosition = colPosition;
                self.rowPosition = rowPosition;
                self.isCreated = isCreated;
            }
        }
    }
}