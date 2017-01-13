module nts.uk.pr.view.qmm011.a {
    import option = nts.uk.ui.option;
    import RoundingMethod = service.model.RoundingMethod;
    import HistoryUnemployeeInsuranceRate = service.model.HistoryUnemployeeInsuranceRate;
    import MonthRange = service.model.MonthRange;
    import YearMonth = service.model.YearMonth;
    import UnemployeeInsuranceRateItemSetting = service.model.UnemployeeInsuranceRateItemSetting;
    import UnemployeeInsuranceRateItem = service.model.UnemployeeInsuranceRateItem;
    import CareerGroup = service.model.CareerGroup;
    import HistoryAccidentInsuranceRate = service.model.HistoryAccidentInsuranceRate;
    export module viewmodel {
        export class ScreenModel {
            blst001: KnockoutObservableArray<BItemModelLST001>;
            lstHistoryUnemployeeInsuranceRate: KnockoutObservableArray<HistoryUnemployeeInsuranceRateModel>;
            unemployeeInsuranceRateItemMode: KnockoutObservable<UnemployeeInsuranceRateItemMode>;
            lstUnemployeeInsuranceRateItem: UnemployeeInsuranceRateItem[];
            rateInputOptions: any;
            bsel001: KnockoutObservableArray<RoundingMethod>;
            bsel002: KnockoutObservableArray<RoundingMethod>;
            bsel003: KnockoutObservableArray<RoundingMethod>;
            bsel004: KnockoutObservableArray<RoundingMethod>;
            bsel005: KnockoutObservableArray<RoundingMethod>;
            bsel006: KnockoutObservableArray<RoundingMethod>;
            bsel001Code: KnockoutObservable<string>;
            bsel002Code: KnockoutObservable<string>;
            bsel003Code: KnockoutObservable<string>;
            bsel004Code: KnockoutObservable<string>;
            bsel005Code: KnockoutObservable<string>;
            bsel006Code: KnockoutObservable<string>;
            historystart: KnockoutObservable<string>;
            historyend: KnockoutObservable<string>;
            rateGeneralBusinessPerson: KnockoutObservable<string>;
            binp003: KnockoutObservable<string>;
            binp004: KnockoutObservable<string>;
            binp005: KnockoutObservable<string>;
            binp006: KnockoutObservable<string>;
            clst001: KnockoutObservableArray<CItemModelLST001>;
            clstsel001: KnockoutObservableArray<string>;
            csel001: KnockoutObservableArray<CItemModelSEL001>;
            csel0011: KnockoutObservableArray<CItemModelSEL001>;
            csel0012: KnockoutObservableArray<CItemModelSEL001>;
            csel0013: KnockoutObservableArray<CItemModelSEL001>;
            csel0014: KnockoutObservableArray<CItemModelSEL001>;
            csel0015: KnockoutObservableArray<CItemModelSEL001>;
            csel0016: KnockoutObservableArray<CItemModelSEL001>;
            csel0017: KnockoutObservableArray<CItemModelSEL001>;
            csel0018: KnockoutObservableArray<CItemModelSEL001>;
            csel0019: KnockoutObservableArray<CItemModelSEL001>;
            csel001Code: KnockoutObservable<string>;
            csel0011Code: KnockoutObservable<string>;
            csel0012Code: KnockoutObservable<string>;
            csel0013Code: KnockoutObservable<string>;
            csel0014Code: KnockoutObservable<string>;
            csel0015Code: KnockoutObservable<string>;
            csel0016Code: KnockoutObservable<string>;
            csel0017Code: KnockoutObservable<string>;
            csel0018Code: KnockoutObservable<string>;
            csel0019Code: KnockoutObservable<string>;
            cinp001: KnockoutObservable<string>;
            cinp002: KnockoutObservable<string>;
            cinp003: KnockoutObservable<string>;
            cinp004: KnockoutObservable<string>;
            cinp005: KnockoutObservable<string>;
            cinp006: KnockoutObservable<string>;
            cinp007: KnockoutObservable<string>;
            cinp008: KnockoutObservable<string>;
            cinp009: KnockoutObservable<string>;
            cinp010: KnockoutObservable<string>;
            cinp011: KnockoutObservable<string>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            isEnable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;

            constructor() {
                var self = this;
                var selectionRoundingMethod = [new RoundingMethod(0, "切り捨て"),
                    new RoundingMethod(1, "切り上げ"),
                    new RoundingMethod(2, "四捨五入"),
                    new RoundingMethod(3, "五捨六入"),
                    new RoundingMethod(4, "五捨五超入")];
                var unemployeeInsuranceRateItemAgroforestry = new UnemployeeInsuranceRateItem(CareerGroup.Agroforestry, new UnemployeeInsuranceRateItemSetting(3, 55.59), new UnemployeeInsuranceRateItemSetting(1, 55.5));
                var unemployeeInsuranceRateItemContruction = new UnemployeeInsuranceRateItem(CareerGroup.Contruction, new UnemployeeInsuranceRateItemSetting(3, 8.59), new UnemployeeInsuranceRateItemSetting(1, 55.6));
                var unemployeeInsuranceRateItemOther = new UnemployeeInsuranceRateItem(CareerGroup.Other, new UnemployeeInsuranceRateItemSetting(3, 8.59), new UnemployeeInsuranceRateItemSetting(1, 65.5));
                self.lstUnemployeeInsuranceRateItem = [unemployeeInsuranceRateItemAgroforestry, unemployeeInsuranceRateItemContruction, unemployeeInsuranceRateItemOther];
                self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                self.unemployeeInsuranceRateItemMode = ko.observable(new UnemployeeInsuranceRateItemMode(self.lstUnemployeeInsuranceRateItem, self.rateInputOptions, selectionRoundingMethod));
                self.blstsel001 = ko.observableArray([]);
                self.bsel001 = selectionRoundingMethod;
                self.bsel002 = selectionRoundingMethod;
                self.bsel003 = selectionRoundingMethod;
                self.bsel004 = selectionRoundingMethod;
                self.bsel005 = selectionRoundingMethod;
                self.bsel006 = selectionRoundingMethod;
                self.lstHistoryUnemployeeInsuranceRate = ko.observableArray([
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3))))
                ]);
                self.historystart = ko.observable('2016/04');
                self.historyend = ko.observable('9999/12 終了年月');
                self.rateGeneralBusinessPerson = ko.observable('40.009');
                self.binp003 = ko.observable('40.009');
                self.binp004 = ko.observable('40.009');
                self.binp005 = ko.observable('40.009');
                self.binp006 = ko.observable('40.009');
                self.bsel001Code = ko.observable(null);
                self.bsel002Code = ko.observable(null);
                self.bsel003Code = ko.observable(null);
                self.bsel004Code = ko.observable(null);
                self.bsel005Code = ko.observable(null);
                self.bsel006Code = ko.observable(null);
                var valueclst001 = ko.observableArray([
                    new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                    new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                    new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                    new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
                ]);
                var valuecsel001 = ko.observableArray([
                    new CItemModelSEL001("0", "切り捨て"),
                    new CItemModelSEL001("1", "切り上げ"),
                    new CItemModelSEL001("2", "四捨五入"),
                    new CItemModelSEL001("3", "五捨六入"),
                    new CItemModelSEL001("4", "五捨五超入")
                ]);
                self.clst001 = valueclst001;
                self.csel001 = valuecsel001;
                self.csel0011 = valuecsel001;
                self.csel0012 = valuecsel001;
                self.csel0013 = valuecsel001;
                self.csel0014 = valuecsel001;
                self.csel0015 = valuecsel001;
                self.csel0016 = valuecsel001;
                self.csel0017 = valuecsel001;
                self.csel0018 = valuecsel001;
                self.csel0019 = valuecsel001;
                self.clstsel001 = ko.observableArray([]);
                self.csel001Code = ko.observable(null);
                self.csel0011Code = ko.observable(null);
                self.csel0012Code = ko.observable(null);
                self.csel0013Code = ko.observable(null);
                self.csel0014Code = ko.observable(null);
                self.csel0015Code = ko.observable(null);
                self.csel0016Code = ko.observable(null);
                self.csel0017Code = ko.observable(null);
                self.csel0018Code = ko.observable(null);
                self.csel0019Code = ko.observable(null);
                self.cinp001 = ko.observable(null);
                self.cinp002 = ko.observable('40.009');
                self.cinp003 = ko.observable('40.009');
                self.cinp004 = ko.observable('40.009');
                self.cinp005 = ko.observable('40.009');
                self.cinp006 = ko.observable('40.009');
                self.cinp007 = ko.observable('40.009');
                self.cinp008 = ko.observable('40.009');
                self.cinp009 = ko.observable('40.009');
                self.cinp010 = ko.observable('40.009');
                self.cinp011 = ko.observable('40.009');
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(2);
                self.isEnable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            }
        }

        export function convertdata(yearmonth: YearMonth): string {
            var viewmonth = '';
            if (yearmonth.month < 10) {
                viewmonth = '0' + yearmonth.month;
            } else {
                viewmonth = '' + yearmonth.month;
            }
            return '' + yearmonth.year + '/' + viewmonth;
        }

        export class UnemployeeInsuranceRateItemSettingModel {
            roundAtr: KnockoutObservable<number>;
            rate: KnockoutObservable<number>;
            constructor(unemployeeInsuranceRateItemSetting: UnemployeeInsuranceRateItemSetting) {
                this.roundAtr = ko.observable(unemployeeInsuranceRateItemSetting.roundAtr);
                this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
            }
        }
        export class HistoryUnemployeeInsuranceRateModel {
            code: string;//historyId
            name: string;//monthRange
            constructor(historyUnemployeeInsuranceRate: HistoryUnemployeeInsuranceRate) {
                this.code = historyUnemployeeInsuranceRate.historyId;
                this.name = convertdata(historyUnemployeeInsuranceRate.monthRage.startMonth) + " ~ " + convertdata(historyUnemployeeInsuranceRate.monthRage.endMonth);
            }
        }

        export class HistoryAccidentInsuranceRateModel {
            code: string;//historyId
            name: string;//monthRange
            constructor(historyUnemployeeInsuranceRate: HistoryUnemployeeInsuranceRate) {
                this.code = historyUnemployeeInsuranceRate.historyId;
                this.name = convertdata(historyUnemployeeInsuranceRate.monthRage.startMonth) + " ~ " + convertdata(historyUnemployeeInsuranceRate.monthRage.endMonth);
            }
        }
        //CareerGroup = 0
        export class UnemployeeInsuranceRateItemAgroforestryModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(companySetting: UnemployeeInsuranceRateItemSetting, personalSetting: UnemployeeInsuranceRateItemSetting, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //CareerGroup = 1
        export class UnemployeeInsuranceRateItemContructionModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(companySetting: UnemployeeInsuranceRateItemSetting, personalSetting: UnemployeeInsuranceRateItemSetting, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //CareerGroup = 2
        export class UnemployeeInsuranceRateItemOtherModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(companySetting: UnemployeeInsuranceRateItemSetting, personalSetting: UnemployeeInsuranceRateItemSetting, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        export class UnemployeeInsuranceRateItemMode {
            unemployeeInsuranceRateItemAgroforestryModel: UnemployeeInsuranceRateItemAgroforestryModel;
            unemployeeInsuranceRateItemContructionModel: UnemployeeInsuranceRateItemContructionModel;
            unemployeeInsuranceRateItemOtherModel: UnemployeeInsuranceRateItemOtherModel;
            constructor(lstUnemployeeInsuranceRateItem: UnemployeeInsuranceRateItem[], rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                for (var index = 0; index < lstUnemployeeInsuranceRateItem.length; index++) {
                    //Agroforestry
                    if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Agroforestry) {
                        this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemAgroforestryModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Contruction
                    else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Contruction) {
                        this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemContructionModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Other
                    else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Other) {
                        this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemOtherModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                }
            }
        }
        export class BItemModelLST001 {
            code: string;
            name: string
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class CItemModelLST001 {
            code: string;
            name: string
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class CItemModelSEL001 {
            code: string;
            name: string
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}