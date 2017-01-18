module nts.uk.pr.view.qmm007.a {
    export module viewmodel {
        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;
        import SettingType = service.model.SettingType;
        import ApplySetting = service.model.ApplySetting;

        export class ScreenModel {
            filteredData: any;
            historyList: any;
            selectedId: KnockoutObservable<string>;
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;
            isContractSettingEnabled: KnockoutObservable<boolean>;
            textEditorOption: any;
            currencyEditorOption: any;
            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel());
                self.historyList = ko.observableArray();
                self.switchButtonDataSource = ko.observableArray([
                    { code: '0', name: '対象' },
                    { code: '1', name: '対象外' }
                ]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.selectedId = ko.observable('');
                self.selectedId.subscribe(id => {
                    if (id != null || id != undefined) {
                        self.loadUnitPriceDetail();
                    }
                });

                self.isContractSettingEnabled = ko.observable(true);
                self.unitPriceHistoryModel().fixPaySettingType.subscribe( val => {
                    val == 1 ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
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
                nts.uk.ui.windows.setShared('code', this.unitPriceHistoryModel().unitPriceCode());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 350, width: 580 });
            }

            goToC() {
                nts.uk.ui.windows.setShared('code', this.unitPriceHistoryModel().unitPriceCode());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 410, width: 580 });
            }

            test() {
                var self = this;
                self.unitPriceHistoryModel().unitPriceCode('1');
                self.unitPriceHistoryModel().unitPriceName('ガソリン単価');
                self.unitPriceHistoryModel().startMonth('2015/04');
                self.unitPriceHistoryModel().budget(120);
            }

            // collect data from model
            collectData(): UnitPriceHistoryDto {
                var self = this
                var data = new UnitPriceHistoryDto();
                data.unitPriceCode = self.unitPriceHistoryModel().unitPriceCode();
                data.unitPriceName = self.unitPriceHistoryModel().unitPriceName();
                data.startMonth = self.unitPriceHistoryModel().startMonth();
                data.endMonth = self.unitPriceHistoryModel().endMonth();
                data.budget = self.unitPriceHistoryModel().budget();
                data.fixPaySettingType = self.unitPriceHistoryModel().fixPaySettingType();
                data.fixPayAtr = self.unitPriceHistoryModel().fixPayAtr();
                data.fixPayAtrMonthly = self.unitPriceHistoryModel().fixPayAtrMonthly();
                data.fixPayAtrDayMonth = self.unitPriceHistoryModel().fixPayAtrDayMonth();
                data.fixPayAtrDaily = self.unitPriceHistoryModel().fixPayAtrDaily();
                data.fixPayAtrHourly = self.unitPriceHistoryModel().fixPayAtrHourly();
                data.memo = self.unitPriceHistoryModel().memo();
                return data;
            }

            save() {
                var self = this;
                service.save(self.collectData());
            }

            remove() {
                var self = this;
                service.remove(self.collectData().id);
            }

            clearUnitPriceDetail() {
                var self = this;
                self.unitPriceHistoryModel().id = ''
                self.unitPriceHistoryModel().unitPriceCode('');
                self.unitPriceHistoryModel().unitPriceName('');
                self.unitPriceHistoryModel().startMonth('');
                self.unitPriceHistoryModel().budget(0);
                self.unitPriceHistoryModel().fixPaySettingType(0);
                self.unitPriceHistoryModel().fixPayAtr(0);
                self.unitPriceHistoryModel().fixPayAtrMonthly(0);
                self.unitPriceHistoryModel().fixPayAtrDayMonth(0);
                self.unitPriceHistoryModel().fixPayAtrDaily(0);
                self.unitPriceHistoryModel().fixPayAtrHourly(0);
            }

            loadUnitPriceDetail() {
                var self = this;
                service.getUnitPriceHistoryDetail(self.selectedId()).done(data => {
                    console.log(data);
                    self.unitPriceHistoryModel().id = data.id;
                    self.unitPriceHistoryModel().unitPriceCode(data.unitPriceCode);
                    self.unitPriceHistoryModel().unitPriceName(data.unitPriceName);
                    self.unitPriceHistoryModel().startMonth(data.startMonth);
                    //self.unitPriceHistoryModel().endMonth(data.endMonth);
                    self.unitPriceHistoryModel().budget(data.budget);
                    self.unitPriceHistoryModel().fixPaySettingType(data.fixPaySettingType);
                    self.unitPriceHistoryModel().fixPayAtr(data.fixPayAtr);
                    self.unitPriceHistoryModel().fixPayAtrMonthly(data.fixPayAtrMonthly);
                    self.unitPriceHistoryModel().fixPayAtrDayMonth(data.fixPayAtrDayMonth);
                    self.unitPriceHistoryModel().fixPayAtrDaily(data.fixPayAtrDaily);
                    self.unitPriceHistoryModel().fixPayAtrHourly(data.fixPayAtrHourly);
                });
            }

            loadUnitPriceHistoryList(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getUnitPriceHistoryList().done( data => {
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
            fixPaySettingType: KnockoutObservable<SettingType>;
            fixPayAtr: KnockoutObservable<ApplySetting>;
            fixPayAtrMonthly: KnockoutObservable<ApplySetting>;
            fixPayAtrDayMonth: KnockoutObservable<ApplySetting>;
            fixPayAtrDaily: KnockoutObservable<ApplySetting>;
            fixPayAtrHourly: KnockoutObservable<ApplySetting>;
            memo: KnockoutObservable<string>;

            constructor() {
                this.unitPriceCode = ko.observable('');
                this.unitPriceName = ko.observable('');
                this.startMonth = ko.observable('');
                this.endMonth = ko.observable('（平成29年01月） ~');
                this.budget = ko.observable(0);
                this.fixPaySettingType = ko.observable(1);
                this.fixPayAtr = ko.observable(1);
                this.fixPayAtrMonthly = ko.observable(1);
                this.fixPayAtrDayMonth = ko.observable(1);
                this.fixPayAtrDaily = ko.observable(1);
                this.fixPayAtrHourly = ko.observable(1);
                this.memo = ko.observable('');
            }
        }

    }
}