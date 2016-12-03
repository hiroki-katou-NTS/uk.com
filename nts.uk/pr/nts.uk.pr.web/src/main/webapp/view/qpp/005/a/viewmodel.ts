module nts.uk.pr.view.qpp005.a {
    import option = nts.uk.ui.option;

    export module viewmodel {
        export class ScreenModel {
            isHandInput: KnockoutObservable<boolean>;
            paymentDataResult: KnockoutObservable<PaymentDataResultViewModel>;
            backupData: KnockoutObservable<PaymentDataResultViewModel>;
            categories: KnockoutObservable<CategoriesList>;
            option: KnockoutObservable<any>;
            employee: KnockoutObservable<Employee>;
            employeeList: KnockoutObservableArray<any>;
            switchButton: KnockoutObservable<SwitchButton>;
            visible: KnockoutObservable<any>;


            constructor() {
                var self = this;
                self.isHandInput = ko.observable(true);
                self.paymentDataResult = ko.observable(new PaymentDataResultViewModel());
                self.backupData = ko.observable(new PaymentDataResultViewModel());
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

                // グリッド設定
                self.switchButton = ko.observable(new SwitchButton());
                self.visible = ko.observable(self.switchButton().selectedRuleCode() == 'vnext');
                self.switchButton().selectedRuleCode.subscribe(function(newValue) {
                    self.visible(newValue == 'vnext');
                    qpp005.a.utils.gridSetup(self.switchButton().selectedRuleCode());
                });
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                qpp005.a.service.getPaymentData(self.employee().personId, self.employee().code).done(function(res: any) {

                    ko.mapping.fromJS(res, {}, self.paymentDataResult());
                    self.paymentDataResult().__ko_mapping__ = undefined;
                    self.backupData(self.paymentDataResult());

                    var categoryPayment: LayoutMasterCategoryViewModel = (<any>self).paymentDataResult().categories()[0];
                    var categoryDeduct: LayoutMasterCategoryViewModel = (<any>self).paymentDataResult().categories()[1];
                    var categoryArticle: LayoutMasterCategoryViewModel = (<any>self).paymentDataResult().categories()[3];
                    self.calcTotal(categoryPayment, categoryDeduct, categoryArticle, true);
                    self.calcTotal(categoryDeduct, categoryPayment, categoryArticle, false);

                    dfd.resolve();
                }).fail(function(res) {
                    $('.tb-category').css('display', 'none');
                    alert(res.message);
                });
                // Return.
                return dfd.promise();
            }

            /** Event click: 計算/登録*/
            register() {
                var self = this;
                // TODO: Check error input

                qpp005.a.service.register(self.paymentDataResult()).done(function(res) {
                    self.startPage();
                    utils.gridSetup(self.switchButton().selectedRuleCode());
                }).fail(function(res) {
                    alert(res.message);
                });
            }

            /** Event click: 対象者*/
            openEmployeeList() {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qpp/005/c/index.xhtml', { title: '社員選択' }).onClosed(() => {
                    var employee = nts.uk.ui.windows.getShared('employee');
                    self.employee(employee);

                    self.startPage();
                    return this;
                });
            }

            /** Event: Previous employee */
            prevEmployee() {
                var self = this;

                var eIdx = _.findIndex(self.employeeList(), function(o) {
                    return o.personId === self.employee().personId && o.code === self.employee().code;
                });
                if (eIdx === 0) {
                    return;
                }

                self.employee(self.employeeList()[eIdx - 1]);
                self.startPage();
            }

            /** Event: Next employee */
            nextEmployee() {
                var self = this;

                var eIdx = _.findIndex(self.employeeList(), function(o) {
                    return o.personId === self.employee().personId && o.code === self.employee().code;
                });
                var eSize = self.employeeList().length;
                if (eIdx === eSize - 1) {
                    return;
                }

                self.employee(self.employeeList()[eIdx + 1]);
                self.startPage();
            }

            openColorSettingGuide() {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qpp/005/d/index.xhtml', { title: '入力欄の背景色について' }).onClosed(() => {
                    var employee = nts.uk.ui.windows.getShared('employee');
                    self.employee(employee);

                    self.startPage();
                    return this;
                });
            }

            openGridSetting() {
                var self = this;
                $('#pơpup-orientation').ntsPopup('show');
            }

            openSetupTaxItem(screenModel, value) {
                var self = this;
                nts.uk.ui.windows.setShared("value", ko.toJS(value));
                nts.uk.ui.windows.setShared("employee", ko.toJS(self.employee()));
                nts.uk.ui.windows.setShared("processingYM", self.paymentDataResult().paymentHeader.processingYM());
                nts.uk.ui.windows.sub.modal('/view/qpp/005/f/index.xhtml', { title: '通勤費の設定' }).onClosed(() => {
                    var totalCommuteEditor = nts.uk.ui.windows.getShared('totalCommuteEditor');
                    var taxCommuteEditor = nts.uk.ui.windows.getShared('taxCommuteEditor');
                    var oneMonthCommuteEditor = nts.uk.ui.windows.getShared('oneMonthCommuteEditor');
                    var oneMonthRemainderEditor = nts.uk.ui.windows.getShared('oneMonthRemainderEditor');

                    var bakData = self.backupData();

                    var nId = 'ct' + value.categoryAtr() + '_' + (value.linePosition() - 1) + '_' + (value.columnPosition() - 1);
                    qpp005.a.utils.setBackgroundColorForItem(window.bakData, nId, totalCommuteEditor);

                    value.value(totalCommuteEditor == '' ? 0 : totalCommuteEditor);
                    value.commuteAllowTaxImpose = taxCommuteEditor;
                    value.commuteAllowMonth = oneMonthCommuteEditor;
                    value.commuteAllowFraction = oneMonthRemainderEditor;
                    //                    self.startPage();
                    return self;
                });
            }

            toggleHeader() {
                $('#content-header').toggle('slow');
                $('img', '#btToggle').toggle();
            }
            /**
             * auto Calculate item total
             */
            calcTotal(source, tranfer, destinate, isPayment) {
                var detailsPayment = _.flatMap(source.lines(), l => l.details());
                var totalPayment = _.last(detailsPayment);
                var inputtingsDetailsPayment = _.reject(detailsPayment, totalPayment);
                var updateTotalPayment = () => {
                    var total = _(inputtingsDetailsPayment).map(d => Number(util.orDefault(d.value(), 0))).sum();
                    totalPayment.value(total);

                    var detailsTranfer = _.flatMap(tranfer.lines(), l => l.details());
                    var totalValueTranfer = _.last(detailsTranfer).value();
                    var detailsDestinate = _.flatMap(destinate.lines(), l => l.details());
                    if (isPayment) {
                        _.last(detailsDestinate).value(total - totalValueTranfer);
                    } else {
                        _.last(detailsDestinate).value(totalValueTranfer - total);
                    }
                };
                inputtingsDetailsPayment.forEach(detail => {
                    detail.value.subscribe(() => updateTotalPayment());
                });

            }
        };

        export class SwitchButton {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.roundingRules = ko.observableArray([
                    { code: 'vnext', name: '縦方向' },
                    { code: 'hnext', name: '横方向' }
                ]);
                self.selectedRuleCode = ko.observable('hnext');
            }
        }
        export class Employee {
            personId: string;
            code: string;
            name: string;

            constructor(personId: string, code: string, name: string) {
                var self = this;

                self.personId = personId;
                self.code = code;
                self.name = name;
            }
        }

        /**
          * Model namespace.
       */
        export class PaymentDataResultViewModel {
            paymentHeader: PaymentDataHeaderViewModel;
            categories: Array<LayoutMasterCategoryViewModel>;
            remarks: string;
        }

        // header
        export class PaymentDataHeaderViewModel {
            personId: string;
            processingYM: number;
            dependentNumber: number;
            specificationCode: string;
            specificationName: string;
            makeMethodFlag: number;
            employeeCode: string;
            comment: KnockoutObservable<string>;
            printPositionCategories: Array<PrintPositionCategoryViewModel>;
            isCreated: boolean;

            constructor(personId: string, processingYM: number, dependentNumber: number, specificationCode: string, specificationName: string, makeMethodFlag: number, employeeCode: string, comment: string,
                printPositionCategories: Array<PrintPositionCategoryViewModel>, isCreated: boolean) {
                var self = this;

                self.personId = personId;
                self.processingYM = processingYM;
                self.dependentNumber = dependentNumber;
                self.specificationCode = specificationCode;
                self.makeMethodFlag = makeMethodFlag;
                self.employeeCode = employeeCode;
                self.comment = ko.observable(comment);
                self.printPositionCategories = printPositionCategories;
                self.isCreated = isCreated;
            }
        }

        export class PrintPositionCategoryViewModel {
            categoryAtr: number;
            lines: number;
        }
        // categories
        export class LayoutMasterCategoryViewModel {
            categoryAttribute: number;
            categoryPosition: number;
            lineCounts: number;
            lines: KnockoutObservableArray<LayoutMasterLine>;
        }

        export interface LayoutMasterLine {
            details: KnockoutObservableArray<DetailItemViewModel>;
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
            displayAtr: number;
            taxAtr: number;
            limitAmount: number;
            commuteAllowTaxImpose: number;
            commuteAllowMonth: number;
            commuteAllowFraction: number;
            isCreated: boolean;
            option: KnockoutComputed<any>;

            constructor(categoryAtr: number, itemAtr: number, itemCode: string, itemName: string, value: number,
                columnPosition: number, linePosition: number, deductAtr: number, displayAtr: number, taxAtr: number,
                limitAmount: number, commuteAllowTaxImpose: number, commuteAllowMonth: number, commuteAllowFraction: number,
                isCreated: boolean) {
                var self = this;
                self.categoryAtr = categoryAtr;
                self.itemAtr = itemAtr;
                self.itemCode = itemCode;
                self.itemName = itemName;
                self.value = ko.observable(value);
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
                self.option = ko.computed(function() {
                    if (self.itemAtr === 0) {
                        return ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: 'time' }));
                    } else if (self.itemAtr === 1) {
                        return ko.mapping.fromJS(new option.NumberEditorOption({ grouplength: 3, decimallength: 1 }));
                    } else if (self.itemAtr == 2 || self.itemAtr === 3) {
                        return ko.mapping.fromJS(new option.CurrencyEditorOption({ grouplength: 3, decimallength: 2, currencyformat: 'JPY', currencyposition: 'left' }));
                    }
                });
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


        //                    var cates = _.map(res.categories, function(category: viewModel.LayoutMasterCategoryViewModel): Category {
        //                        switch (category.categoryAttribute) {
        //                            case 0:
        //                                return new Category(0, '支給');
        //                            case 1:
        //                                return new Category(1, '控除');
        //                            case 2:
        //                                return new Category(2, '勤怠');
        //                            case 3:
        //                                return new Category(3, '記事');
        //                            default:
        //                                break;
        //                        };
        //                    });
        //                    self.categories().items(cates);
        //                    self.categories().selectedCode(res.categories[0].categoryAttribute);
    }
}