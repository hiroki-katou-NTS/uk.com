module nts.uk.pr.view.qmm011.a {
    import option = nts.uk.ui.option;
    import RoundingMethod = service.model.RoundingMethod;
    import HistoryUnemployeeInsuranceRateDto = service.model.HistoryUnemployeeInsuranceRateDto;
    import MonthRange = service.model.MonthRange;
    import YearMonth = service.model.YearMonth;
    import UnemployeeInsuranceRateItemSetting = service.model.UnemployeeInsuranceRateItemSetting;
    import UnemployeeInsuranceRateItem = service.model.UnemployeeInsuranceRateItem;
    import CareerGroup = service.model.CareerGroup;
    import AccidentInsuranceRateDto = service.model.AccidentInsuranceRateDto;
    import InsuBizRateItem = service.model.InsuBizRateItem;
    import BusinessTypeEnum = service.model.BusinessTypeEnum;
    import InsuranceBusinessType = service.model.InsuranceBusinessType;
    import TypeHistory = service.model.TypeHistory;
    import UnemployeeInsuranceRateDto = service.model.UnemployeeInsuranceRateDto;
    import HistoryAccidentInsuranceRateDto = service.model.HistoryAccidentInsuranceRateDto;
    export module viewmodel {
        export class ScreenModel {
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            //雇用保険 detail B
            lstHistoryUnemployeeInsuranceRate: KnockoutObservableArray<HistoryUnemployeeInsuranceRateDto>;
            unemployeeInsuranceRateModel: KnockoutObservable<UnemployeeInsuranceRateModel>;
            rateInputOptions: any;
            historyUnemployeeInsuranceRateStart: KnockoutObservable<string>;
            historyUnemployeeInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryUnemployeeInsuranceRate: KnockoutObservable<string>;
            //労災保険 detail C
            lstHistoryAccidentInsuranceRate: KnockoutObservableArray<HistoryAccidentInsuranceRateDto>;
            historyAccidentInsuranceRateStart: KnockoutObservable<string>;
            historyAccidentInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryAccidentInsuranceRate: KnockoutObservable<string>;
            lstInsuBizRateItem: InsuBizRateItem[];
            //detail D
            lstInsuranceBusinessType: InsuranceBusinessType[];
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            isEnable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;

            constructor() {
                var self = this;
                self.selectionRoundingMethod = ko.observableArray<RoundingMethod>([new RoundingMethod("RoundUp", "切り捨て"),
                    new RoundingMethod("Truncation", "切り上げ"),
                    new RoundingMethod("RoundDown", "四捨五入"),
                    new RoundingMethod("Down5_Up6", "五捨六入"),
                    new RoundingMethod("Down4_Up5", "五捨五超入")]);
                self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                self.historyUnemployeeInsuranceRateStart = ko.observable('');
                self.historyUnemployeeInsuranceRateEnd = ko.observable('');

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

                self.lstInsuranceBusinessType = [insuranceBusinessTypeBiz1St, insuranceBusinessTypeBiz2Nd,
                    insuranceBusinessTypeBiz3Rd, insuranceBusinessTypeBiz4Th, insuranceBusinessTypeBiz5Th, insuranceBusinessTypeBiz6Th,
                    insuranceBusinessTypeBiz7Th, insuranceBusinessTypeBiz8Th, insuranceBusinessTypeBiz9Th, insuranceBusinessTypeBiz10Th];
                self.historyAccidentInsuranceRateStart = ko.observable('');
                self.historyAccidentInsuranceRateEnd = ko.observable('');
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(2);
                self.isEnable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            }

            openEditHistoryUnemployeeInsuranceRate() {
                var self = this;
                var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                nts.uk.ui.windows.setShared("historyId", historyId);
                nts.uk.ui.windows.setShared("historyStart", self.historyUnemployeeInsuranceRateStart());
                nts.uk.ui.windows.setShared("historyEnd", self.historyUnemployeeInsuranceRateEnd());
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);

                nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(() => {
                    //OnClose => call
                });
            }
            openAddHistoryUnemployeeInsuranceRate() {
                nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 400, width: 560, title: "労働保険料率の登録>履歴の追加" }).onClosed(() => {
                    //OnClose => call
                });
            }
            openEditInsuranceBusinessType() {
                nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 590, width: 425, title: "事業種類の登録" }).onClosed(() => {
                    //OnClose => call
                });
            }
            openEditHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var historyId = this.selectionHistoryAccidentInsuranceRate();
                nts.uk.ui.windows.setShared("historyId", historyId);
                // nts.uk.ui.windows.setShared("lsthistoryValue", this.lstHistoryAccidentInsurance);
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(() => {
                    //OnClose => call
                });
            }
            openAddHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 500, width: 600, title: "労働保険料率の登録>履歴の追加" }).onClosed(() => {
                    //OnClose => call
                });
            }
            showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate: string) {
                var self = this;
                self.findHisotryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
            }
            showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate: string) {
                var self = this;
                self.findHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                self.detailHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllHisotryUnemployeeInsuranceRate().done(data => {
                    self.findAllHistoryAccidentInsuranceRate().done(data => {
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            findAllHisotryUnemployeeInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllHisotryUnemployeeInsuranceRate().done(data => {
                    self.lstHistoryUnemployeeInsuranceRate = ko.observableArray<HistoryUnemployeeInsuranceRateDto>(data);
                    self.selectionHistoryUnemployeeInsuranceRate = ko.observable(data[0].historyId);
                    self.historyUnemployeeInsuranceRateStart = ko.observable(data[0].startMonthRage);
                    self.historyUnemployeeInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                    self.selectionHistoryUnemployeeInsuranceRate.subscribe(function(selectionHistoryUnemployeeInsuranceRate: string) {
                        self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                    });
                    self.detailHistoryUnemployeeInsuranceRate(data[0].historyId).done(data => {
                        dfd.resolve(self);
                    });

                });
                return dfd.promise();
            }
            findHisotryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHisotryUnemployeeInsuranceRate(historyId).done(data => {
                    self.historyUnemployeeInsuranceRateStart(data.startMonthRage);
                    self.historyUnemployeeInsuranceRateEnd(data.endMonthRage);
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            findHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHistoryAccidentInsuranceRate(historyId).done(data => {
                    self.historyAccidentInsuranceRateStart(data.startMonthRage);
                    self.historyAccidentInsuranceRateEnd(data.endMonthRage);
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            detailHistoryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.detailHistoryUnemployeeInsuranceRate(historyId).done(data => {
                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
                    dfd.resolve(null);
                });
                return dfd.promise();
            }

            findAllHistoryAccidentInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllHistoryAccidentInsuranceRate().done(data => {
                    self.lstHistoryAccidentInsuranceRate = ko.observableArray<HistoryAccidentInsuranceRateDto>(data);
                    self.selectionHistoryAccidentInsuranceRate = ko.observable(data[0].historyId);
                    self.historyAccidentInsuranceRateStart = ko.observable(data[0].startMonthRage);
                    self.historyAccidentInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                    self.selectionHistoryAccidentInsuranceRate.subscribe(function(selectionHistoryAccidentInsuranceRate: string) {
                        self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                    });
                    self.detailHistoryAccidentInsuranceRate(data[0].historyId).done(data => {
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }
            detailHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.detailHistoryAccidentInsuranceRate(historyId).done(data => {
                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(data, self.lstInsuranceBusinessType, self.rateInputOptions, self.selectionRoundingMethod));
                    dfd.resolve(null);
                });
                return dfd.promise();
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
            roundAtr: KnockoutObservable<string>;
            rate: KnockoutObservable<number>;
            constructor(unemployeeInsuranceRateItemSetting: UnemployeeInsuranceRateItemSetting) {
                this.roundAtr = ko.observable("RoundUp");
                this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
            }
        }

        export class UnemployeeInsuranceRateItemModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any; selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(companySetting: UnemployeeInsuranceRateItemSetting, personalSetting: UnemployeeInsuranceRateItemSetting,
                rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>) {
                this.companySetting = new UnemployeeInsuranceRateItemSettingModel(companySetting);
                this.personalSetting = new UnemployeeInsuranceRateItemSettingModel(personalSetting);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
            }
        }
        export class UnemployeeInsuranceRateModel {
            unemployeeInsuranceRateItemAgroforestryModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemContructionModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemOtherModel: UnemployeeInsuranceRateItemModel;

            constructor(unemployeeInsuranceRate: UnemployeeInsuranceRateDto, rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>) {
                for (var index = 0; index < unemployeeInsuranceRate.rateItems.length; index++) {
                    //Agroforestry
                    if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Agroforestry) {
                        this.unemployeeInsuranceRateItemAgroforestryModel =
                            new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting,
                                unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Contruction
                    else if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Contruction) {
                        this.unemployeeInsuranceRateItemContructionModel =
                            new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting,
                                unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Other
                    else if (unemployeeInsuranceRate.rateItems[index].careerGroup === CareerGroup.Other) {
                        this.unemployeeInsuranceRateItemOtherModel =
                            new UnemployeeInsuranceRateItemModel(unemployeeInsuranceRate.rateItems[index].companySetting,
                                unemployeeInsuranceRate.rateItems[index].personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                }
            }
        }
        export class AccidentInsuranceRateDetailModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<string>;
            insuranceBusinessType: KnockoutObservable<string>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>;
            constructor(insuBizRateItem: InsuBizRateItem, rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
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
            constructor(accidentInsuranceRate: AccidentInsuranceRateDto, lstInsuranceBusinessType: InsuranceBusinessType[],
                rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethod>) {
                for (var index = 0; index < accidentInsuranceRate.rateItems.length; index++) {
                    //Biz1St
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz2Nd
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz3Rd
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz4Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz5Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz6Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz7Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz8Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz9Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz10Th
                    if (accidentInsuranceRate.rateItems[index].insuBizType === BusinessTypeEnum.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel =
                            new AccidentInsuranceRateDetailModel(accidentInsuranceRate.rateItems[index], rateInputOptions, selectionRoundingMethod);
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

    }
}