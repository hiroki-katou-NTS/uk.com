module nts.uk.pr.view.qmm007.a {
    export module viewmodel {

        import UnitPriceDto = service.model.UnitPriceDto;
        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;
        import DateTimeDto = service.model.DateTimeDto;

        import SettingType = service.model.SettingType;
        import ApplySetting = service.model.ApplySetting;

        export class ScreenModel {
            filteredData: any;
            selectedCode: any;

            historyList: any;
            unitPriceDetailModel: KnockoutObservable<UnitPriceDetailModel>;

            code: any;
            name: any;
            startDate: any;
            endDate: KnockoutObservable<string>;
            money: any;
            memo: KnockoutObservable<string>;

            settingType: KnockoutObservable<SettingType>;
            payAtr: KnockoutObservable<ApplySetting>;
            payAtrMonthly: KnockoutObservable<ApplySetting>;
            payAtrDayMonth: KnockoutObservable<ApplySetting>;
            payAtrDaily: KnockoutObservable<ApplySetting>;
            payAtrHourly: KnockoutObservable<ApplySetting>;
            
            textEditorOption: any;
            currencyEditorOption: any;

            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.unitPriceDetailModel = ko.observable(new UnitPriceDetailModel());
                self.historyList = ko.observableArray();
                self.switchButtonDataSource = ko.observableArray([
                    { code: '1', name: '対象' },
                    { code: '2', name: '対象外' }
                ]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.selectedCode = ko.observable();
                self.selectedCode.subscribe(val => {
                    if (val != null || val != undefined) {
                        self.code(val);
                        console.log(val);
                    }
                });

                //options
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

                //input
                self.code = ko.observable(self.unitPriceDetailModel().unitPriceCode);
                self.name = ko.observable(self.unitPriceDetailModel().unitPriceName);
                self.startDate = ko.observable(self.unitPriceDetailModel().startDate);
                self.endDate = ko.observable('（平成29年01月） ~');
                self.money = ko.observable(self.unitPriceDetailModel().budget);
                self.memo = ko.observable('');

                //setting
                self.settingType = ko.observable(0);
                self.payAtr = ko.observable(0);
                self.payAtrMonthly = ko.observable(0);
                self.payAtrDayMonth = ko.observable(0);
                self.payAtrDaily = ko.observable(0);
                self.payAtrHourly = ko.observable(0);

            }

            startPage(): JQueryPromise<any> {
                var self = this;

                var dfd = $.Deferred();
                self.loadUnitPriceHistoryList().done(() => dfd.resolve(null));
                return dfd.promise();
            }

            goToB() {
                nts.uk.ui.windows.setShared('code', this.code());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 350, width: 450 });
            }

            goToC() {
                nts.uk.ui.windows.setShared('code', this.code());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 410, width: 560 });
            }

            test() {
                var self = this;
                self.code(1);
                self.name('ガソリン単価')
                self.startDate('2015-04');
                self.money(120);
                self.settingType(2);
                self.payAtr(2);
                self.payAtrMonthly(2);
                self.payAtrDayMonth(2);
                self.payAtrDaily(2);
                self.payAtrHourly(2);
            }

            collectData(): UnitPriceHistoryDto {
                var self = this
                var data = new UnitPriceHistoryDto();
                data.unitPriceCode = self.code.value();
                console.log(data);
                return null;
            }

            clearUnitPriceDetail() {
                var self = this;
                self.code(null);
                self.name('')
                self.startDate('');
                self.money(null);
                self.settingType(0);
                self.payAtr(0);
                self.payAtrMonthly(0);
                self.payAtrDayMonth(0);
                self.payAtrDaily(0);
                self.payAtrHourly(0);
            }
            loadUnitPriceDetail(model: UnitPriceDetailModel) {
                var self = this;
                self.code.value(model.unitPriceCode);
                self.name.value(model.unitPriceName)
                self.startDate.value(model.startDate);
                self.money.value(model.budget);
                self.settingType(model.fixPaySettingType);
                self.payAtr(model.fixPayAtr);
                self.payAtrMonthly(model.fixPayAtrMonthly);
                self.payAtrDayMonth(model.fixPayAtrDayMonth);
                self.payAtrDaily(model.fixPayAtrDaily);
                self.payAtrHourly(model.fixPayAtrHourly);
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

        /*
                export class UnitPriceDetailModel {
                    unitPriceCode: KnockoutObservable<string>;
                    startDate: KnockoutObservable<string>;
                    budget: KnockoutObservable<number>;
                    fixPaySettingType: KnockoutObservable<SettingType>;
                    fixPayAtr: KnockoutObservable<ApplySetting>;
                    fixPayAtrMonthly: KnockoutObservable<ApplySetting>;
                    fixPayAtrDayMonth: KnockoutObservable<ApplySetting>;
                    fixPayAtrDaily: KnockoutObservable<ApplySetting>;
                    fixPayAtrHourly: KnockoutObservable<ApplySetting>;
                    memo: KnockoutObservable<string>;
        
                    constructor(unitPriceCode: string, startDate: number, budget: number, fixPaySettingType: number, fixPayAtr: number,
                        fixPayAtrMonthly: number, fixPayAtrDayMonth: number, fixPayAtrDaily: number, fixPayAtrHourly: number, memo: string) {
                        this.unitPriceCode = ko.observable(unitPriceCode);
                        this.budget = ko.observable(budget);
                        this.fixPaySettingType = ko.observable(fixPaySettingType);
                        this.fixPayAtr = ko.observable(fixPayAtr);
                        this.fixPayAtrMonthly = ko.observable(fixPayAtrMonthly);
                        this.fixPayAtrDayMonth = ko.observable(fixPayAtrDayMonth);
                        this.fixPayAtrDaily = ko.observable(fixPayAtrDaily);
                        this.fixPayAtrHourly = ko.observable(fixPayAtrHourly);
                        this.memo = ko.observable(memo);
                    }
                }
        */

        export class UnitPriceDetailModel extends UnitPriceHistoryDto {
        }

        export class UnitPriceHistoryModel {
        }

    }
}