module nts.uk.pr.view.qmm007.a {
    export module viewmodel {

        import UnitPriceDto = service.model.UnitPriceDto;
        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;
        import DateTimeDto = service.model.DateTimeDto;

        import SettingType = service.model.SettingType;
        import ApplySetting = service.model.ApplySetting;

        export class ScreenModel {
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;

            historyList: any;
            unitPriceDetailModel: KnockoutObservable<UnitPriceDetailModel>;

            lbl_005: KnockoutObservable<string>;

            inp_002_code: any;
            inp_003_name: any;
            inp_004_date: any;
            inp_005_money: any;
            inp_006_memo: KnockoutObservable<string>;

            sel_001_settingType: KnockoutObservable<SettingType>;
            sel_002_payAtr: KnockoutObservable<ApplySetting>;
            sel_003_payAtrMonthly: KnockoutObservable<ApplySetting>;
            sel_004_payAtrDayMonth: KnockoutObservable<ApplySetting>;
            sel_005_payAtrDaily: KnockoutObservable<ApplySetting>;
            sel_006_payAtrHourly: KnockoutObservable<ApplySetting>;

            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.unitPriceDetailModel = ko.observable(new UnitPriceDetailModel());
                self.historyList = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                    new Node('0003', 'Bangkok Thailand', []),
                    new Node('0004', 'Tokyo Japan', []),
                    new Node('0005', 'Jakarta Indonesia', []),
                    new Node('0002', 'Seoul Korea', []),
                    new Node('0006', 'Paris France', []),
                    new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                    new Node('0010', 'Beijing China', []),
                    new Node('0011', 'London United Kingdom', []),
                    new Node('0012', 'USA', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])])]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([]);

                self.lbl_005 = ko.observable('（平成29年01月） ~');

                self.inp_002_code = {
                    value: ko.observable(self.unitPriceDetailModel().unitPriceCode),
                    constraint: 'UnitPriceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "50px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.inp_003_name = {
                    value: ko.observable(self.unitPriceDetailModel().unitPriceName),
                    constraint: 'UnitPriceName',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "250px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.inp_004_date = {
                    value: ko.observable(self.unitPriceDetailModel().startDate),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "70px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.inp_005_money = {
                    value: ko.observable(self.unitPriceDetailModel().budget),
                    constraint: 'Money',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                        grouplength: 3,
                        decimallength: 2,
                        currencyformat: "JPY",
                        currencyposition: 'right',
                        width: "100px",
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.inp_006_memo = ko.observable('');

                self.switchButtonDataSource = ko.observableArray([
                    { code: '1', name: '対象' },
                    { code: '2', name: '対象外' }
                ]);

                self.sel_001_settingType = ko.observable(0);
                self.sel_002_payAtr = ko.observable(0);
                self.sel_003_payAtrMonthly = ko.observable(0);
                self.sel_004_payAtrDayMonth = ko.observable(0);
                self.sel_005_payAtrDaily = ko.observable(0);
                self.sel_006_payAtrHourly = ko.observable(0);

            }

            startPage(): JQueryPromise<any> {
                var self = this;

                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            goToB() {
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 350, width: 450 });
            }

            goToC() {
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 410, width: 560 });
            }

            test() {
                //this.unitPriceDetailModel().unitPriceCode = this.inp_002_code.value();
                //alert(this.unitPriceDetailModel().unitPriceCode);
                var self = this;
                self.inp_002_code.value(1);
                self.inp_003_name.value('ガソリン単価')
                self.inp_004_date.value('2015-04');
                self.inp_005_money.value(120);
                self.sel_001_settingType(2);
                self.sel_002_payAtr(2);
                self.sel_003_payAtrMonthly(2);
                self.sel_004_payAtrDayMonth(2);
                self.sel_005_payAtrDaily(2);
                self.sel_006_payAtrHourly(2);
            }

            collectData(): UnitPriceHistoryDto {
                var self = this
                var data = new UnitPriceHistoryDto();
                data.unitPriceCode = self.inp_002_code.value();
                console.log(data);
                return null;
            }

            loadUnitPriceDetail(unitPricecode: number, startDate: DateTimeDto) {
            }

        }

        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
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