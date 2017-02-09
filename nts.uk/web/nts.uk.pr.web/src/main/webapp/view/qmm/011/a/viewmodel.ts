module nts.uk.pr.view.qmm011.a {
    import option = nts.uk.ui.option;
    import RoundingMethodDto = service.model.RoundingMethodDto;
    import HistoryUnemployeeInsuranceDto = service.model.HistoryUnemployeeInsuranceDto;
    import MonthRange = service.model.MonthRange;
    import YearMonth = service.model.YearMonth;
    import UnemployeeInsuranceRateItemSettingDto = service.model.UnemployeeInsuranceRateItemSettingDto;
    import UnemployeeInsuranceRateItemDto = service.model.UnemployeeInsuranceRateItemDto;
    import CareerGroupDto = service.model.CareerGroupDto;
    import AccidentInsuranceRateDto = service.model.AccidentInsuranceRateDto;
    import BusinessTypeEnumDto = service.model.BusinessTypeEnumDto;
    import InsuranceBusinessType = service.model.InsuranceBusinessType;
    import TypeHistory = service.model.TypeHistory;
    import UnemployeeInsuranceRateDto = service.model.UnemployeeInsuranceRateDto;
    import HistoryAccidentInsuranceDto = service.model.HistoryAccidentInsuranceDto;
    import InsuBizRateItemDto = service.model.InsuBizRateItemDto;
    import TypeActionInsuranceRate = service.model.TypeActionInsuranceRate;
    export module viewmodel {
        export class ScreenModel {
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            //Update or Add To service 1 add 2 update
            typeActionUnemployeeInsurance: KnockoutObservable<number>;
            //雇用保険 detail B
            lstHistoryUnemployeeInsuranceRate: KnockoutObservableArray<HistoryUnemployeeInsuranceDto>;
            unemployeeInsuranceRateModel: KnockoutObservable<UnemployeeInsuranceRateModel>;
            rateInputOptions: any;
            historyUnemployeeInsuranceRateStart: KnockoutObservable<string>;
            historyUnemployeeInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryUnemployeeInsuranceRate: KnockoutObservable<string>;
            //労災保険 detail C
            lstHistoryAccidentInsuranceRate: KnockoutObservableArray<HistoryAccidentInsuranceDto>;
            historyAccidentInsuranceRateStart: KnockoutObservable<string>;
            historyAccidentInsuranceRateEnd: KnockoutObservable<string>;
            selectionHistoryAccidentInsuranceRate: KnockoutObservable<string>;
            //detail D
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
            //Update or Add  typeAccidentInsurance: KnockoutObservable<number>;
            typeActionAccidentInsurance: KnockoutObservable<number>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            isEnable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            constructor() {
                var self = this;

                self.selectionRoundingMethod = ko.observableArray<RoundingMethodDto>([new RoundingMethodDto(0, "切り捨て"),//"RoundUp 
                    new RoundingMethodDto(1, "切り上げ"),//Truncation
                    new RoundingMethodDto(2, "四捨五入"),//"RoundDown"
                    new RoundingMethodDto(3, "五捨六入"),//Down5_Up6
                    new RoundingMethodDto(4, "五捨五超入")]);//Down4_Up5
                self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                self.historyUnemployeeInsuranceRateStart = ko.observable('');
                self.historyUnemployeeInsuranceRateEnd = ko.observable('');
                self.historyAccidentInsuranceRateStart = ko.observable('');
                self.historyAccidentInsuranceRateEnd = ko.observable('');
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(2);
                self.isEnable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.update);
                self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.update);
            }
            //open dialog edit HistoryUnemployeeInsuranceRate => show view model xhtml (action event add)
            private openEditHistoryUnemployeeInsuranceRate() {
                var self = this;
                var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                service.findHisotryUnemployeeInsuranceRate(historyId).done(data => {
                    nts.uk.ui.windows.setShared("historyId", data.historyId);
                    nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                    nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                    nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 550, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(() => {
                        //OnClose => call
                        var updateHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryUnemployeeInsuranceDto");
                        if (updateHistoryUnemployeeInsuranceDto != null && updateHistoryUnemployeeInsuranceDto != undefined) {
                            self.historyUnemployeeInsuranceRateStart(updateHistoryUnemployeeInsuranceDto.startMonthRage);
                            self.historyUnemployeeInsuranceRateEnd(updateHistoryUnemployeeInsuranceDto.endMonthRage);
                        }
                    });
                });
            }
            //open dialog add HistoryUnemployeeInsuranceRate => show view model xhtml (action event add)
            private openAddHistoryUnemployeeInsuranceRate() {
                var self = this;
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 400, width: 560, title: "労働保険料率の登録>履歴の追加" }).onClosed(() => {
                    //OnClose => call
                    var addHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("addHistoryUnemployeeInsuranceDto");
                    if (addHistoryUnemployeeInsuranceDto != null && addHistoryUnemployeeInsuranceDto != undefined) {
                        self.historyUnemployeeInsuranceRateStart(addHistoryUnemployeeInsuranceDto.startMonthRage);
                        self.historyUnemployeeInsuranceRateEnd(addHistoryUnemployeeInsuranceDto.endMonthRage);
                    }
                });
            }
            //open dialog edit InsuranceBusinessType => show view model xhtml (action event edit)
            private openEditInsuranceBusinessType() {
                var self = this;
                service.findAllInsuranceBusinessType("companyCode001").done(data => {
                    nts.uk.ui.windows.setShared("insuranceBusinessTypeUpdateDto", data);
                    nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 590, width: 425, title: "事業種類の登録" }).onClosed(() => {
                        //OnClose => call
                    });
                });

            }
            //open dialog edit HistoryAccidentInsurance => show view model xhtml (action event edit)
            private openEditHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var self = this;
                var historyId = self.selectionHistoryAccidentInsuranceRate();
                nts.uk.ui.windows.setShared("historyId", historyId);
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                nts.uk.ui.windows.setShared("historyStart", self.historyAccidentInsuranceRateStart());
                nts.uk.ui.windows.setShared("historyEnd", self.historyAccidentInsuranceRateEnd());
                self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                nts.uk.ui.windows.sub.modal("/view/qmm/011/f/index.xhtml", { height: 420, width: 500, title: "労働保険料率の登録>マスタ修正ログ" }).onClosed(() => {
                    //OnClose => call
                    var updateHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryAccidentInsuranceDto");
                    if (updateHistoryAccidentInsuranceDto != null && updateHistoryAccidentInsuranceDto != undefined) {
                        self.historyAccidentInsuranceRateStart(updateHistoryAccidentInsuranceDto.startMonthRage);
                        self.historyAccidentInsuranceRateEnd(updateHistoryAccidentInsuranceDto.endMonthRage);
                    }
                });
            }
            //open dialog add HistoryAccidentInsuranceRate => show view model xhtml (action event add)
            private openAddHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var self = this;
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                nts.uk.ui.windows.sub.modal("/view/qmm/011/d/index.xhtml", { height: 500, width: 600, title: "労働保険料率の登録>履歴の追加" }).onClosed(() => {
                    //OnClose => call
                    var addHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("addHistoryAccidentInsuranceDto");
                    if (addHistoryAccidentInsuranceDto != null && addHistoryAccidentInsuranceDto != undefined) {
                        self.historyAccidentInsuranceRateStart(addHistoryAccidentInsuranceDto.startMonthRage);
                        self.historyAccidentInsuranceRateEnd(addHistoryAccidentInsuranceDto.endMonthRage);
                    }
                });
            }
            //show HistoryUnemployeeInsurance (change event)
            private showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate: string) {
                var self = this;
                self.findHisotryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
            }
            //action save HistoryUnemployeeInsurance Onlick connection service
            private saveHistoryUnemployeeInsurance() {
                var self = this;
                //add type action mode
                if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                    var historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(),
                        self.historyUnemployeeInsuranceRateStart(), self.historyUnemployeeInsuranceRateEnd());
                    service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel(), historyUnemployeeInsuranceDto, "companyCode001");
                }
                //update
                else {
                    var historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(),
                        self.historyUnemployeeInsuranceRateStart(), self.historyUnemployeeInsuranceRateEnd());
                    service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel(), historyUnemployeeInsuranceDto, "companyCode001");
                }
                return true;
            }
            //action save HistoryAccidentInsurance Onlick connection service
            private saveHistoryAccidentInsurance() {
                var self = this;
                //add type action mode
                if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                    var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(),
                        self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                    service.addAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto, "companyCode001");
                }
                //update
                else {
                    var historyAccidentInsuranceDto = new HistoryAccidentInsuranceDto(self.selectionHistoryUnemployeeInsuranceRate(),
                        self.historyAccidentInsuranceRateStart(), self.historyAccidentInsuranceRateEnd());
                    service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel(), historyAccidentInsuranceDto, "companyCode001");
                }
                return true;
            }
            //show HistoryAccidentInsurance (change event)
            private showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate: string) {
                var self = this;
                self.findHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                self.detailHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
            }
            // startPage => show view model xhtml (constructor)
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllHisotryUnemployeeInsuranceRate().done(data => {
                    self.findAllHistoryAccidentInsuranceRate().done(data => {
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }
            //find add HisotryUnemployeeInsuranceRate => show view model xhtml (constructor)
            private findAllHisotryUnemployeeInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllHisotryUnemployeeInsuranceRate().done(data => {
                    self.lstHistoryUnemployeeInsuranceRate = ko.observableArray<HistoryUnemployeeInsuranceDto>(data);
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
            //find HisotryUnemployeeInsuranceRate => show view model xhtml (action event)
            private findHisotryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHisotryUnemployeeInsuranceRate(historyId).done(data => {
                    self.historyUnemployeeInsuranceRateStart(data.startMonthRage);
                    self.historyUnemployeeInsuranceRateEnd(data.endMonthRage);
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            //find HistoryAccidentInsuranceRate => show view model xhtml (action event)
            private findHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findHistoryAccidentInsuranceRate(historyId).done(data => {
                    self.historyAccidentInsuranceRateStart(data.startMonthRage);
                    self.historyAccidentInsuranceRateEnd(data.endMonthRage);
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            //detail HistoryUnemployeeInsuranceRate => show view model xhtml (action event)
            private detailHistoryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.detailHistoryUnemployeeInsuranceRate(historyId).done(data => {
                    self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            //find All HistoryAccidentInsuranceRate => Show View model xhtml (constructor)
            private findAllHistoryAccidentInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllHistoryAccidentInsuranceRate().done(data => {
                    self.lstHistoryAccidentInsuranceRate = ko.observableArray<HistoryAccidentInsuranceDto>(data);
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
            //detail HistoryAccidentInsuranceRate => show view model xhtml (action event)
            private detailHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.detailHistoryAccidentInsuranceRate(historyId).done(data => {
                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(data, self.rateInputOptions, self.selectionRoundingMethod));
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
        }

        export class UnemployeeInsuranceRateItemSettingModel {
            roundAtr: KnockoutObservable<number>;
            rate: KnockoutObservable<number>;
            constructor(unemployeeInsuranceRateItemSetting: UnemployeeInsuranceRateItemSettingDto) {
                this.roundAtr = ko.observable(unemployeeInsuranceRateItemSetting.roundAtr);
                this.rate = ko.observable(unemployeeInsuranceRateItemSetting.rate);
            }
        }

        export class UnemployeeInsuranceRateItemModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any; selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            constructor(companySetting: UnemployeeInsuranceRateItemSettingDto, personalSetting: UnemployeeInsuranceRateItemSettingDto,
                rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
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
            version: KnockoutObservable<number>;

            constructor(unemployeeInsuranceRate: UnemployeeInsuranceRateDto, rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                for (var rateItem of unemployeeInsuranceRate.rateItems) {
                    //Agroforestry
                    if (rateItem.careerGroup == CareerGroupDto.Agroforestry) {
                        this.unemployeeInsuranceRateItemAgroforestryModel =
                            new UnemployeeInsuranceRateItemModel(rateItem.companySetting,
                                rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Contruction
                    else if (rateItem.careerGroup == CareerGroupDto.Contruction) {
                        this.unemployeeInsuranceRateItemContructionModel =
                            new UnemployeeInsuranceRateItemModel(rateItem.companySetting,
                                rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    //Other
                    else if (rateItem.careerGroup == CareerGroupDto.Other) {
                        this.unemployeeInsuranceRateItemOtherModel =
                            new UnemployeeInsuranceRateItemModel(rateItem.companySetting,
                                rateItem.personalSetting, rateInputOptions, selectionRoundingMethod);
                    }
                    this.version = ko.observable(unemployeeInsuranceRate.version);
                }
            }
        }
        export class AccidentInsuranceRateDetailModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<string>;
            insuranceBusinessType: KnockoutObservable<string>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            constructor(insuBizRateItem: InsuBizRateItemDto, rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.insuRate = ko.observable(insuBizRateItem.insuRate);
                this.insuRound = ko.observable(insuBizRateItem.insuRound);
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
                this.insuranceBusinessType = ko.observable(insuBizRateItem.insuranceBusinessType);
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
            version: KnockoutObservable<number>;
            constructor(accidentInsuranceRate: AccidentInsuranceRateDto,
                rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                for (var rateItem of accidentInsuranceRate.rateItems) {
                    //Biz1St
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz2Nd
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz3Rd
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz4Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz5Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz6Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz7Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz8Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz9Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    //Biz10Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel =
                            new AccidentInsuranceRateDetailModel(rateItem, rateInputOptions, selectionRoundingMethod);
                    }
                    this.version = ko.observable(accidentInsuranceRate.version);
                }
            }
        }

    }
}