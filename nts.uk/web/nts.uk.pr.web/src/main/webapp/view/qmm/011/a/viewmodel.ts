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
    import InsuBizRateItem = service.model.InsuBizRateItem;
    import BusinessTypeEnum = service.model.BusinessTypeEnum;
    export module viewmodel {
        export class ScreenModel {
            //雇用保険 detail B
            lstHistoryUnemployeeInsuranceRate: KnockoutObservableArray<HistoryUnemployeeInsuranceRateModel>;
            unemployeeInsuranceRateItemMode: KnockoutObservable<UnemployeeInsuranceRateItemMode>;
            lstUnemployeeInsuranceRateItem: UnemployeeInsuranceRateItem[];
            rateInputOptions: any;
            historyUnemployeeInsuranceRateStart: KnockoutObservable<string>;
            historyUnemployeeInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryUnemployeeInsuranceRate: KnockoutObservable<string>;
            //労災保険 detail C
            lstHistoryAccidentInsuranceRate: KnockoutObservableArray<HistoryAccidentInsuranceRateModel>;
            historyAccidentInsuranceRateStart: KnockoutObservable<string>;
            historyAccidentInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryAccidentInsuranceRate: KnockoutObservable<string>;
            lstInsuBizRateItem: InsuBizRateItem[];
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
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
                self.lstHistoryUnemployeeInsuranceRate = ko.observableArray([
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)))),
                    new HistoryUnemployeeInsuranceRateModel(new HistoryUnemployeeInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3))))
                ]);
                self.historyUnemployeeInsuranceRateStart = ko.observable('2016/04');
                self.historyUnemployeeInsuranceRateEnd = ko.observable('9999/12 終了年月');
                self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');

                var insuBizRateItemBiz1St = new InsuBizRateItem(BusinessTypeEnum.Biz1St, 60, 2);
                var insuBizRateItemBiz2Nd = new InsuBizRateItem(BusinessTypeEnum.Biz2Nd, 3, 3);
                var insuBizRateItemBiz3Rd = new InsuBizRateItem(BusinessTypeEnum.Biz3Rd, 15, 0);
                var insuBizRateItemBiz4Th = new InsuBizRateItem(BusinessTypeEnum.Biz4Th, 6.5, 2);
                var insuBizRateItemBiz5Th = new InsuBizRateItem(BusinessTypeEnum.Biz5Th, 13, 3);
                var insuBizRateItemBiz6Th = new InsuBizRateItem(BusinessTypeEnum.Biz6Th, 49, 0);
                var insuBizRateItemBiz7Th = new InsuBizRateItem(BusinessTypeEnum.Biz7Th, 3, 2);
                var insuBizRateItemBiz8Th = new InsuBizRateItem(BusinessTypeEnum.Biz8Th, 7, 3);
                var insuBizRateItemBiz9Th = new InsuBizRateItem(BusinessTypeEnum.Biz9Th, 2.5, 0);
                var insuBizRateItemBiz10Th = new InsuBizRateItem(BusinessTypeEnum.Biz10Th, 3, 0);
                self.lstInsuBizRateItem = [insuBizRateItemBiz1St, insuBizRateItemBiz2Nd, insuBizRateItemBiz3Rd, insuBizRateItemBiz4Th, insuBizRateItemBiz5Th,
                    insuBizRateItemBiz6Th, insuBizRateItemBiz7Th, insuBizRateItemBiz8Th, insuBizRateItemBiz9Th, insuBizRateItemBiz10Th];
                self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.lstInsuBizRateItem, self.rateInputOptions, selectionRoundingMethod));
                self.lstHistoryAccidentInsuranceRate = ko.observableArray([
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId006', 'companyCode001', new MonthRange(new YearMonth(2016, 4), new YearMonth(9999, 12)))),
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId005', 'companyCode001', new MonthRange(new YearMonth(2015, 10), new YearMonth(2016, 3)))),
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId004', 'companyCode001', new MonthRange(new YearMonth(2015, 4), new YearMonth(2015, 9)))),
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId003', 'companyCode001', new MonthRange(new YearMonth(2014, 9), new YearMonth(2015, 3)))),
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId002', 'companyCode001', new MonthRange(new YearMonth(2014, 4), new YearMonth(2014, 8)))),
                    new HistoryAccidentInsuranceRateModel(new HistoryAccidentInsuranceRate('historyId001', 'companyCode001', new MonthRange(new YearMonth(2013, 4), new YearMonth(2014, 3))))
                ]);
                self.historyAccidentInsuranceRateStart = ko.observable('2016/04');
                self.historyAccidentInsuranceRateEnd = ko.observable('9999/12 終了年月');
                self.selectionHistoryAccidentInsuranceRate = ko.observable('');
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
            constructor(historyAccidentInsuranceRate: HistoryAccidentInsuranceRate) {
                this.code = historyAccidentInsuranceRate.historyId;
                this.name = convertdata(historyAccidentInsuranceRate.monthRage.startMonth) + " ~ " + convertdata(historyAccidentInsuranceRate.monthRage.endMonth);
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
        //BusinessTypeEnum=1
        export class AccidentInsuranceRateBiz1StModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=2
        export class AccidentInsuranceRateBiz2NdModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=3
        export class AccidentInsuranceRateBiz3RdModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=4
        export class AccidentInsuranceRateBiz4ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=5
        export class AccidentInsuranceRateBiz5ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=6
        export class AccidentInsuranceRateBiz6ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=7
        export class AccidentInsuranceRateBiz7ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=8
        export class AccidentInsuranceRateBiz8ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=9
        export class AccidentInsuranceRateBiz9ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        //BusinessTypeEnum=10
        export class AccidentInsuranceRateBiz10ThModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
            }
        }
        export class AccidentInsuranceRateModel {
            accidentInsuranceRateBiz1StModel: AccidentInsuranceRateBiz1StModel;
            accidentInsuranceRateBiz2NdModel: AccidentInsuranceRateBiz2NdModel;
            accidentInsuranceRateBiz3RdModel: AccidentInsuranceRateBiz3RdModel;
            accidentInsuranceRateBiz4ThModel: AccidentInsuranceRateBiz4ThModel;
            accidentInsuranceRateBiz5ThModel: AccidentInsuranceRateBiz5ThModel;
            accidentInsuranceRateBiz6ThModel: AccidentInsuranceRateBiz6ThModel;
            accidentInsuranceRateBiz7ThModel: AccidentInsuranceRateBiz7ThModel;
            accidentInsuranceRateBiz8ThModel: AccidentInsuranceRateBiz8ThModel;
            accidentInsuranceRateBiz9ThModel: AccidentInsuranceRateBiz9ThModel;
            accidentInsuranceRateBiz10ThModel: AccidentInsuranceRateBiz10ThModel;
            constructor(lstInsuBizRateItem: InsuBizRateItem[], rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                for (var index = 0; index < lstInsuBizRateItem.length; index++) {
                    //Biz1St
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel = new AccidentInsuranceRateBiz1StModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz2Nd
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel = new AccidentInsuranceRateBiz2NdModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz3Rd
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel = new AccidentInsuranceRateBiz3RdModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz4Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel = new AccidentInsuranceRateBiz4ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz5Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel = new AccidentInsuranceRateBiz5ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz6Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel = new AccidentInsuranceRateBiz6ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz7Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel = new AccidentInsuranceRateBiz7ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz8Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel = new AccidentInsuranceRateBiz8ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz9Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel = new AccidentInsuranceRateBiz9ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz10Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel = new AccidentInsuranceRateBiz10ThModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                }
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