module nts.uk.pr.view.qpp007.b {
    export module viewmodel {

        export class ScreenModel {
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<any>;
            switchValue: KnockoutObservable<string>;
            salaryPrintSettingModel: KnockoutObservable<SalaryPrintSettingModel>;

            constructor() {
                var self = this;
                self.salaryPrintSettingModel = ko.observable<SalaryPrintSettingModel>();
                self.switchButtonDataSource = ko.observableArray([
                    { code: 'Hourly', name: '時間' },
                    { code: 'Minutely', name: '分' }
                ]);
                self.switchValue = ko.observable('Apply');
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.loadSalaryPrintSetting().done(() =>
                    dfd.resolve());
                return dfd.promise();
            }

            /**
             * Call service to load salary print setting.
             */
            private loadSalaryPrintSetting(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findSalaryPrintSetting().then(res => {
                    self.salaryPrintSettingModel(new SalaryPrintSettingModel('Minutely', false, true, true, false, true, true, false, true, false, true, false, true, false, true, false, false, false, true, false, true));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Call service to save print setting.
             */
            private saveSetting(): void {
                var self = this;
                service.saveSalaryPrintSetting(ko.toJS(self.salaryPrintSettingModel));
            }
            /**
             * Cancel and close dialog.
             */
            private cancel(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class SalaryPrintSettingModel {
            showDetail: KnockoutObservable<boolean>; //001

            showPersonalTotal: KnockoutObservable<boolean>; //004
            showPersonalMonthlyAmount: KnockoutObservable<boolean>; //005

            showDivisionTotal: KnockoutObservable<boolean>; //006
            showDivisionMonthlyTotal: KnockoutObservable<boolean>; //007

            showHierarchyAccumulation: KnockoutObservable<boolean>; //008
            showHierarchyMonthlyAccumulation: KnockoutObservable<boolean>;// 009

            showHierarchy1: KnockoutObservable<boolean>;
            showHierarchy2: KnockoutObservable<boolean>;
            showHierarchy3: KnockoutObservable<boolean>;
            showHierarchy4: KnockoutObservable<boolean>;
            showHierarchy5: KnockoutObservable<boolean>;
            showHierarchy6: KnockoutObservable<boolean>;
            showHierarchy7: KnockoutObservable<boolean>;
            showHierarchy8: KnockoutObservable<boolean>;
            showHierarchy9: KnockoutObservable<boolean>;

            showTotal: KnockoutObservable<boolean>;//002
            showMonthlyAmount: KnockoutObservable<boolean>; //003

            outputDistinction: KnockoutObservable<string>; //019

            showSectionalCalculation: KnockoutObservable<boolean>;
            showDepartmentMonthlyAmount: KnockoutObservable<boolean>;
            constructor(
                outputDistinction: string,
                showDepartmentMonthlyAmount: boolean,
                showDetail: boolean,
                showDivisionMonthlyTotal: boolean,
                showDivisionTotal: boolean,
                showHierarchy1: boolean,
                showHierarchy2: boolean,
                showHierarchy3: boolean,
                showHierarchy4: boolean,
                showHierarchy5: boolean,
                showHierarchy6: boolean,
                showHierarchy7: boolean,
                showHierarchy8: boolean,
                showHierarchy9: boolean,
                showHierarchyAccumulation: boolean,
                showHierarchyMonthlyAccumulation: boolean,
                showMonthlyAmount: boolean,
                showPersonalMonthlyAmount: boolean,
                showPersonalTotal: boolean,
                showSectionalCalculation: boolean,
                showTotal: boolean
            ) {
                this.outputDistinction = ko.observable(outputDistinction);
                this.showDepartmentMonthlyAmount = ko.observable(showDepartmentMonthlyAmount);
                this.showDetail = ko.observable(showDetail);
                this.showDivisionMonthlyTotal = ko.observable(showDivisionMonthlyTotal);
                this.showDivisionTotal = ko.observable(showDivisionTotal);
                this.showHierarchy1 = ko.observable(showHierarchy1);
                this.showHierarchy2 = ko.observable(showHierarchy2);
                this.showHierarchy3 = ko.observable(showHierarchy3);
                this.showHierarchy4 = ko.observable(showHierarchy4);
                this.showHierarchy5 = ko.observable(showHierarchy5);
                this.showHierarchy6 = ko.observable(showHierarchy6);
                this.showHierarchy7 = ko.observable(showHierarchy7);
                this.showHierarchy8 = ko.observable(showHierarchy8);
                this.showHierarchy9 = ko.observable(showHierarchy9);
                this.showHierarchyAccumulation = ko.observable(showHierarchyAccumulation);
                this.showHierarchyMonthlyAccumulation = ko.observable(showHierarchyMonthlyAccumulation);
                this.showMonthlyAmount = ko.observable(showMonthlyAmount);
                this.showPersonalMonthlyAmount = ko.observable(showPersonalMonthlyAmount);
                this.showPersonalTotal = ko.observable(showPersonalTotal);
                this.showSectionalCalculation = ko.observable(showSectionalCalculation);
                this.showTotal = ko.observable(showTotal);
            }
        }
    }
}