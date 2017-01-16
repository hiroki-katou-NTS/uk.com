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
    import InsuranceBusinessType = service.model.InsuranceBusinessType;
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
            //detail D
            lstInsuranceBusinessType: InsuranceBusinessType[];
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

                var insuranceBusinessTypeBiz1St = new InsuranceBusinessType(BusinessTypeEnum.Biz1St, "事業種類名1");
                var insuranceBusinessTypeBiz2Nd = new InsuranceBusinessType(BusinessTypeEnum.Biz2Nd, "事業種類名2");
                var insuranceBusinessTypeBiz3Rd = new InsuranceBusinessType(BusinessTypeEnum.Biz3Rd, "事業種類名3");
                var insuranceBusinessTypeBiz4Th = new InsuranceBusinessType(BusinessTypeEnum.Biz4Th, "事業種類名4");
                var insuranceBusinessTypeBiz5Th = new InsuranceBusinessType(BusinessTypeEnum.Biz5Th, "事業種類名5");
                var insuranceBusinessTypeBiz6Th = new InsuranceBusinessType(BusinessTypeEnum.Biz6Th, "事業種類名6");
                var insuranceBusinessTypeBiz7Th = new InsuranceBusinessType(BusinessTypeEnum.Biz7Th, "事業種類名7");
                var insuranceBusinessTypeBiz8Th = new InsuranceBusinessType(BusinessTypeEnum.Biz8Th, "事業種類名8");
                var insuranceBusinessTypeBiz9Th = new InsuranceBusinessType(BusinessTypeEnum.Biz9Th, "事業種類名9");
                var insuranceBusinessTypeBiz10Th = new InsuranceBusinessType(BusinessTypeEnum.Biz10Th, "事業種類名11");

                self.lstInsuranceBusinessType = [insuranceBusinessTypeBiz1St, insuranceBusinessTypeBiz2Nd, insuranceBusinessTypeBiz3Rd, insuranceBusinessTypeBiz4Th,
                    insuranceBusinessTypeBiz5Th, insuranceBusinessTypeBiz6Th, insuranceBusinessTypeBiz7Th, insuranceBusinessTypeBiz8Th, insuranceBusinessTypeBiz9Th, insuranceBusinessTypeBiz10Th];
                self.lstInsuBizRateItem = [insuBizRateItemBiz1St, insuBizRateItemBiz2Nd, insuBizRateItemBiz3Rd, insuBizRateItemBiz4Th, insuBizRateItemBiz5Th,
                    insuBizRateItemBiz6Th, insuBizRateItemBiz7Th, insuBizRateItemBiz8Th, insuBizRateItemBiz9Th, insuBizRateItemBiz10Th];
                self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.lstInsuBizRateItem, self.lstInsuranceBusinessType, self.rateInputOptions, selectionRoundingMethod));
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

        export class UnemployeeInsuranceRateItemModel {
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
            unemployeeInsuranceRateItemAgroforestryModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemContructionModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemOtherModel: UnemployeeInsuranceRateItemModel;
            constructor(lstUnemployeeInsuranceRateItem: UnemployeeInsuranceRateItem[], rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                for (var index = 0; index < lstUnemployeeInsuranceRateItem.length; index++) {
                    //Agroforestry
                    if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Agroforestry) {
                        this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Contruction
                    else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Contruction) {
                        this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Other
                    else if (lstUnemployeeInsuranceRateItem[index].careerGroup == CareerGroup.Other) {
                        this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemModel(lstUnemployeeInsuranceRateItem[index].companySetting, lstUnemployeeInsuranceRateItem[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                }
            }
        }
        export class AccidentInsuranceRateDetailModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            insuranceBusinessType: KnockoutObservable<string>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = ko.observableArray(selectionRoundingMethod);
                this.insuranceBusinessType = ko.observable('');
            }
            setInsuranceBusinessType(insuranceBusinessType: string) {
                this.insuranceBusinessType = ko.observable(insuranceBusinessType);
            }
        }
        export class AccidentInsuranceRateModel {
            accidentInsuranceRateBiz1StModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz2NdModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz3RdModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz4ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz5ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz6ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz7ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz8ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz9ThModel: AccidentInsuranceRateDetailModel;
            accidentInsuranceRateBiz10ThModel: AccidentInsuranceRateDetailModel;
            constructor(lstInsuBizRateItem: InsuBizRateItem[], lstInsuranceBusinessType: InsuranceBusinessType[], rateInputOptions: any, selectionRoundingMethod: RoundingMethod[]) {
                for (var index = 0; index < lstInsuBizRateItem.length; index++) {
                    //Biz1St
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz2Nd
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz3Rd
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz4Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz5Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz6Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz7Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz8Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz9Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz10Th
                    if (lstInsuBizRateItem[index].insuBizType == BusinessTypeEnum.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel = new AccidentInsuranceRateDetailModel(lstInsuBizRateItem[index], rateInputOptions, selectionRoundingMethod);
                    }
                }
                for (var index = 0; index < lstInsuranceBusinessType.length; index++) {
                    //Biz1St
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz2Nd
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz3Rd
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz4Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz5Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz6Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz7Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz8Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz9Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz9Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
                    }
                    //Biz10Th
                    if (lstInsuranceBusinessType[index].bizOrder == BusinessTypeEnum.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel.setInsuranceBusinessType(lstInsuranceBusinessType[index].bizName);
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