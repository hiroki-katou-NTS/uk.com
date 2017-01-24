module nts.uk.pr.view.qmm007.a {
    export module viewmodel {

        export class ScreenModel {
            // UnitPriceHistory Model
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;

            // Selected ID of UnitPriceHistory
            selectedId: KnockoutObservable<string>;

            // Tree grid
            filteredData: KnockoutObservableArray<any>;
            historyList: KnockoutObservableArray<any>;

            // Setting type
            isContractSettingEnabled: KnockoutObservable<boolean>;

            // New mode flag
            isNewMode: KnockoutObservable<boolean>;

            // Nts text editor options
            textEditorOption: any;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.isNewMode = ko.observable(true);
                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel());
                self.historyList = ko.observableArray();
                self.switchButtonDataSource = ko.observableArray([
                    { code: 'Apply', name: '対象' },
                    { code: 'NotApply', name: '対象外' }
                ]);

                // Tree grid
                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.selectedId = ko.observable('');
                self.selectedId.subscribe(id => {
                    if (id) {
                        self.isNewMode(false);
                        $('.save-error').ntsError('clear');
                        self.loadUnitPriceDetail(id);
                    }
                });

                // Setting type
                self.isContractSettingEnabled = ko.observable(false);
                self.unitPriceHistoryModel().fixPaySettingType.subscribe(val => {
                    val == 'Contract' ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
                });

                // Nts text editor options
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }));
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadUnitPriceHistoryList().done(() => dfd.resolve());
                return dfd.promise();
            }

            /**
             * Go to Add UnitPriceHistory dialog.
             */
            private goToB() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 360, width: 580 });
            }

            /**
             * Go to Edit UnitPriceHistory dialog.
             */
            private goToC() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 420, width: 580 });
            }

            /**
             * Create or Update UnitPriceHistory.
             */
            private save() {
                var self = this;
                if (self.isNewMode()) {
                    service.create(service.collectData(self.unitPriceHistoryModel()));
                } else {
                    service.update(service.collectData(self.unitPriceHistoryModel()));
                }
            }

            /**
             * Clear all input and switch to new mode.
             */
            private enableNewMode() {
                var self = this;
                $('.save-error').ntsError('clear');
                self.clearUnitPriceDetail();
                self.isNewMode(true);
            }

            /**
             * Clear all input.
             */
            private clearUnitPriceDetail() {
                var model = this.unitPriceHistoryModel()
                model.id = '';
                model.unitPriceCode('');
                model.unitPriceName('');
                model.startMonth('');
                //model.endMonth('');
                model.budget(null);
                model.fixPaySettingType('Company');
                model.fixPayAtr('NotApply');
                model.fixPayAtrMonthly('NotApply');
                model.fixPayAtrDayMonth('NotApply');
                model.fixPayAtrDaily('NotApply');
                model.fixPayAtrHourly('NotApply');
                model.memo('');
            }

            /**
             * Load UnitPriceHistory detail.
             */
            private loadUnitPriceDetail(id: string) {
                var self = this;
                if (id) {
                    service.find(id).done(data => {
                        var model = self.unitPriceHistoryModel()
                        model.id = data.id;
                        model.version = data.version;
                        model.unitPriceCode(data.unitPriceCode);
                        model.unitPriceName(data.unitPriceName);
                        model.startMonth(data.startMonth);
                        //model.endMonth(data.endMonth);
                        model.budget(data.budget);
                        model.fixPaySettingType(data.fixPaySettingType);
                        model.fixPayAtr(data.fixPayAtr);
                        model.fixPayAtrMonthly(data.fixPayAtrMonthly);
                        model.fixPayAtrDayMonth(data.fixPayAtrDayMonth);
                        model.fixPayAtrDaily(data.fixPayAtrDaily);
                        model.fixPayAtrHourly(data.fixPayAtrHourly);
                        model.memo(data.memo);
                    });
                }
            }

            /**
             * Load UnitPriceHistory tree list.
             */
            private loadUnitPriceHistoryList(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getUnitPriceHistoryList().done(data => {
                    self.historyList(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }
        }

        export class UnitPriceHistoryModel {
            id: string;
            version: number;
            unitPriceCode: KnockoutObservable<string>;
            unitPriceName: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            budget: KnockoutObservable<number>;
            fixPaySettingType: KnockoutObservable<string>;
            fixPayAtr: KnockoutObservable<string>;
            fixPayAtrMonthly: KnockoutObservable<string>;
            fixPayAtrDayMonth: KnockoutObservable<string>;
            fixPayAtrDaily: KnockoutObservable<string>;
            fixPayAtrHourly: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;

            constructor() {
                this.id = '';
                this.version = 0;
                this.unitPriceCode = ko.observable('');
                this.unitPriceName = ko.observable('');
                this.startMonth = ko.observable('');
                this.endMonth = ko.observable('（平成29年01月） ~');
                this.budget = ko.observable(null);
                this.fixPaySettingType = ko.observable('Company');
                this.fixPayAtr = ko.observable('NotApply');
                this.fixPayAtrMonthly = ko.observable('NotApply');
                this.fixPayAtrDayMonth = ko.observable('NotApply');
                this.fixPayAtrDaily = ko.observable('NotApply');
                this.fixPayAtrHourly = ko.observable('NotApply');
                this.memo = ko.observable('');
            }
        }

    }
}