module nts.uk.pr.view.qmm007.a {
    export module viewmodel {
        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;

        export class ScreenModel {
            filteredData: any;
            historyList: any;
            selectedId: KnockoutObservable<string>;
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;
            isContractSettingEnabled: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;
            textEditorOption: any;
            currencyEditorOption: any;
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
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }));

            }

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadUnitPriceHistoryList().done(() => dfd.resolve(null));
                return dfd.promise();
            }

            goToB() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 360, width: 580 });
            }

            goToC() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 420, width: 580 });
            }

            // collect data from model
            collectData(): UnitPriceHistoryDto {
                var self = this;
                var dto = new UnitPriceHistoryDto();
                var model = self.unitPriceHistoryModel();
                dto.id = model.id;
                dto.unitPriceCode = model.unitPriceCode();
                dto.unitPriceName = model.unitPriceName();
                dto.startMonth = model.startMonth();
                dto.endMonth = model.endMonth();
                dto.budget = model.budget();
                dto.fixPaySettingType = model.fixPaySettingType();
                dto.fixPayAtr = model.fixPayAtr();
                dto.fixPayAtrMonthly = model.fixPayAtrMonthly();
                dto.fixPayAtrDayMonth = model.fixPayAtrDayMonth();
                dto.fixPayAtrDaily = model.fixPayAtrDaily();
                dto.fixPayAtrHourly = model.fixPayAtrHourly();
                dto.memo = model.memo();
                return dto;
            }

            save() {
                var self = this;
                if (self.isNewMode()) {
                    service.create(self.collectData());
                } else {
                    service.update(self.collectData());
                }
            }

            remove() {
                var self = this;
                service.remove(self.collectData().id);
            }

            enableNewMode() {
                var self = this;
                self.clearUnitPriceDetail();
                self.isNewMode(true);
            }

            clearUnitPriceDetail() {
                var model = this.unitPriceHistoryModel()
                model.id = '';
                model.unitPriceCode('');
                model.unitPriceName('');
                model.startMonth('2017/01');
                //model.endMonth('');
                model.budget(0);
                model.fixPaySettingType('Company');
                model.fixPayAtr('NotApply');
                model.fixPayAtrMonthly('NotApply');
                model.fixPayAtrDayMonth('NotApply');
                model.fixPayAtrDaily('NotApply');
                model.fixPayAtrHourly('NotApply');
            }

            loadUnitPriceDetail(id: string) {
                var self = this;
                service.getUnitPriceHistoryDetail(id).done(data => {
                    console.log(data);
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
                });
            }

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

        export class UnitPriceHistoryModel {
            id: string;
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
                this.unitPriceCode = ko.observable('');
                this.unitPriceName = ko.observable('');
                this.startMonth = ko.observable('2017/01');
                this.endMonth = ko.observable('（平成29年01月） ~');
                this.budget = ko.observable(0);
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