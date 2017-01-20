module nts.uk.pr.view.qmm007.a {
    export module viewmodel {
        import UnitPriceHistoryModel = service.model.UnitPriceHistoryModel;

        export class ScreenModel {
            // UnitPriceHistory Model
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;

            // Selected ID of UnitPriceHistory
            selectedId: KnockoutObservable<string>;

            // Setting type
            isContractSettingEnabled: KnockoutObservable<boolean>;

            //Search box
            filteredData: any;
            historyList: any;

            // New mode flag
            isNewMode: KnockoutObservable<boolean>;

            // Nts editor options
            textEditorOption: any;
            currencyEditorOption: any;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel());
                self.historyList = ko.observableArray();
                self.switchButtonDataSource = ko.observableArray([
                    { code: 'Apply', name: '対象' },
                    { code: 'NotApply', name: '対象外' }
                ]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.selectedId = ko.observable('');
                self.selectedId.subscribe(id => {
                    if (id != null || id != undefined) {
                        self.isNewMode(false);
                        $('.save-error').ntsError('clear');
                        self.loadUnitPriceDetail(id);
                    }
                });

                self.isContractSettingEnabled = ko.observable(false);
                self.isNewMode = ko.observable(true);
                self.unitPriceHistoryModel().fixPaySettingType.subscribe(val => {
                    val == 'Contract' ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
                });
                // nts editor options
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }));
                self.currencyEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    placeholder: "0.00",
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }));

            }

            /**
             * Start page.
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadUnitPriceHistoryList().done(() => dfd.resolve(null));
                return dfd.promise();
            }

            /**
             * Go To Add UnitPriceHistory dialog.
             */
            goToB() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 360, width: 580 });
            }

            /**
             * Go To Edit UnitPriceHistory dialog.
             */
            goToC() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 420, width: 580 });
            }

            /**
             * Create or Update UnitPriceHistory.
             */
            save() {
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
            enableNewMode() {
                var self = this;
                $('.save-error').ntsError('clear');
                self.clearUnitPriceDetail();
                self.isNewMode(true);
            }

            /**
             * Clear all input.
             */
            clearUnitPriceDetail() {
                var model = this.unitPriceHistoryModel()
                model.id = '';
                model.unitPriceCode('');
                model.unitPriceName('');
                model.startMonth('2017/01');
                //model.endMonth('');
                model.budget(null);
                model.fixPaySettingType('Company');
                model.fixPayAtr('NotApply');
                model.fixPayAtrMonthly('NotApply');
                model.fixPayAtrDayMonth('NotApply');
                model.fixPayAtrDaily('NotApply');
                model.fixPayAtrHourly('NotApply');
            }

            /**
             * Load UnitPriceHistory detail.
             */
            loadUnitPriceDetail(id: string) {
                var self = this;
                service.find(id).done(data => {
                    var model = self.unitPriceHistoryModel()
                    model.id = data.id;
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

            /**
             * Load UnitPriceHistory tree list.
             */
            loadUnitPriceHistoryList(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getUnitPriceHistoryList().done(data => {
                    self.historyList(data);
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
        }
    }
}