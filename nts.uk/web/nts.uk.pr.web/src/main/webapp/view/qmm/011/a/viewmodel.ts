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
    import HistoryAccidentInsuranceRateFindInDto = service.model.HistoryAccidentInsuranceRateFindInDto;
    import InsuBizRateItemDto = service.model.InsuBizRateItemDto;
    import TypeActionInsuranceRate = service.model.TypeActionInsuranceRate;
    import InsuranceBusinessTypeDto = service.model.InsuranceBusinessTypeDto;
    import InsuranceBusinessTypeUpdateModel = nts.uk.pr.view.qmm011.e.viewmodel.InsuranceBusinessTypeUpdateModel;
    export module viewmodel {
        export class ScreenModel {
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            //Update or Add To service 1 add 2 update
            typeActionUnemployeeInsurance: KnockoutObservable<number>;
            //雇用保険 detail B
            lstHistoryUnemployeeInsuranceRate: KnockoutObservableArray<HistoryUnemployeeInsuranceDto>;
            unemployeeInsuranceRateModel: KnockoutObservable<UnemployeeInsuranceRateModel>;
            rateInputOptions: any;
            selectionHistoryUnemployeeInsuranceRate: KnockoutObservable<string>;
            isEmptyUnemployee: KnockoutObservable<boolean>;
            //労災保険 detail C
            lstHistoryAccidentInsuranceRate: KnockoutObservableArray<HistoryAccidentInsuranceDto>;
            selectionHistoryAccidentInsuranceRate: KnockoutObservable<string>;
            //detail D
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
            //Update or Add  typeAccidentInsurance: KnockoutObservable<number>;
            typeActionAccidentInsurance: KnockoutObservable<number>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            textEditorOption: KnockoutObservable<any>;
            isEnable: KnockoutObservable<boolean>;
            isEmptyAccident: KnockoutObservable<boolean>;
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
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(2);
                self.isEnable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.add);
                self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.add);
                self.isEmptyUnemployee = ko.observable(true);
                self.isEmptyAccident = ko.observable(true);
                self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.selectionHistoryUnemployeeInsuranceRate = ko.observable('');
                self.selectionHistoryAccidentInsuranceRate = ko.observable('');
            }
            //open dialog edit HistoryUnemployeeInsuranceRate => show view model xhtml (action event add)
            private openEditHistoryUnemployeeInsuranceRate() {
                var self = this;
                var historyId = self.selectionHistoryUnemployeeInsuranceRate();
                service.findHisotryUnemployeeInsuranceRate(historyId).done(data => {
                    nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                    nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                    nts.uk.ui.windows.setShared("historyId", data.historyId);
                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                    self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                    nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(() => {
                        //OnClose => call
                        var updateHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryUnemployeeInsuranceDto");
                        if (updateHistoryUnemployeeInsuranceDto != null && updateHistoryUnemployeeInsuranceDto != undefined) {
                            self.unemployeeInsuranceRateModel().setHistoryData(updateHistoryUnemployeeInsuranceDto);
                        }
                    });
                });
            }
            //open dialog add HistoryUnemployeeInsuranceRate => show view model xhtml (action event add)
            private openAddHistoryUnemployeeInsuranceRate() {
                var self = this;
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);
                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    //OnClose => call
                    var addHistoryUnemployeeInsuranceDto = nts.uk.ui.windows.getShared("addHistoryUnemployeeInsuranceDto");
                    if (addHistoryUnemployeeInsuranceDto != null && addHistoryUnemployeeInsuranceDto != undefined) {
                        self.resetValueUnemployeeInsuranceRate();
                        self.unemployeeInsuranceRateModel().setHistoryData(addHistoryUnemployeeInsuranceDto);
                    }
                });
            }
            //open dialog edit InsuranceBusinessType => show view model xhtml (action event edit)
            private openEditInsuranceBusinessType() {
                var self = this;
                service.findAllInsuranceBusinessType().done(data => {
                    nts.uk.ui.windows.setShared("InsuranceBusinessTypeDto", data);
                    nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 700, width: 425, title: "事業種類の登録" }).onClosed(() => {
                        //OnClose => call
                        var insuranceBusinessTypeUpdateModel = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateModel");
                        if (insuranceBusinessTypeUpdateModel != null && insuranceBusinessTypeUpdateModel != undefined) {
                            service.findAllInsuranceBusinessType().done(data => {
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                            });
                        }
                    });
                });

            }
            //open dialog edit HistoryAccidentInsurance => show view model xhtml (action event edit)
            private openEditHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var self = this;
                var historyId = self.selectionHistoryAccidentInsuranceRate();
                service.findHistoryAccidentInsuranceRate(historyId).done(data => {
                    nts.uk.ui.windows.setShared("historyEnd", data.endMonthRage);
                    nts.uk.ui.windows.setShared("historyStart", data.startMonthRage);
                    nts.uk.ui.windows.setShared("historyId", data.historyId);
                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                    self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                    nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(() => {
                        var updateHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryAccidentInsuranceDto");
                        if (updateHistoryAccidentInsuranceDto != null && updateHistoryAccidentInsuranceDto != undefined) {
                            self.accidentInsuranceRateModel().setHistoryData(updateHistoryAccidentInsuranceDto);
                        }
                    });
                });
            }
            //open dialog add HistoryAccidentInsuranceRate => show view model xhtml (action event add)
            private openAddHistoryAccidentInsuranceRate() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var self = this;
                nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    //OnClose => call
                    var addHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("addHistoryAccidentInsuranceDto");
                    if (addHistoryAccidentInsuranceDto != null && addHistoryAccidentInsuranceDto != undefined) {
                        self.accidentInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                        self.accidentInsuranceRateModel().setHistoryData(addHistoryAccidentInsuranceDto);
                        self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                        //self.historyAccidentInsuranceRateStart(addHistoryAccidentInsuranceDto.startMonthRage);
                        //self.historyAccidentInsuranceRateEnd(addHistoryAccidentInsuranceDto.endMonthRage);
                    }
                });
            }
            //show HistoryUnemployeeInsurance (change event)
            private showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate: string) {
                if (selectionHistoryUnemployeeInsuranceRate != null && selectionHistoryUnemployeeInsuranceRate != undefined && selectionHistoryUnemployeeInsuranceRate != '') {
                    var self = this;
                    self.detailHistoryUnemployeeInsuranceRate(selectionHistoryUnemployeeInsuranceRate);
                }
            }
            //Clear show message error connection by server
            private clearErrorSaveUnemployeeInsurance() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_saveHistoryUnemployeeInsurance').ntsError('clear');
            }
            //show message save UnemployeeInsurance 
            private showMessageSaveUnemployeeInsurance(message: string) {
                $('#btn_saveHistoryUnemployeeInsurance').ntsError('set', message);
            }
            //Clear show message error connection by server
            private clearErrorSaveAccidentInsurance() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_saveHistoryAccidentInsurance').ntsError('clear');
            }
            //show message save AccidentInsurance 
            private showMessageSaveAccidentInsurance(message: string) {
                $('#btn_saveHistoryAccidentInsurance').ntsError('set', message);
            }
            //action save HistoryUnemployeeInsurance Onlick connection service
            private saveHistoryUnemployeeInsurance() {
                var self = this;
                //add type action mode
                if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                    service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(data => {
                        self.reloadDataUnemployeeInsuranceRateByAction();
                        self.clearErrorSaveUnemployeeInsurance();
                    }).fail(function(res) {
                        self.showMessageSaveUnemployeeInsurance(res.message);
                    })
                }
                //update
                else {
                    service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(data => {
                        self.reloadDataUnemployeeInsuranceRateByAction();
                        self.clearErrorSaveUnemployeeInsurance();
                    }).fail(function(res) {
                        self.showMessageSaveUnemployeeInsurance(res.message);
                    })
                }
                return true;
            }
            //action save HistoryAccidentInsurance Onlick connection service
            private saveHistoryAccidentInsurance() {
                var self = this;
                //add type action mode
                if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                    service.addAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(data => {
                        self.reloadDataAccidentInsuranceRateByAction();
                        self.clearErrorSaveAccidentInsurance();
                    }).fail(function(res) {
                        self.showMessageSaveAccidentInsurance(res.message);
                    })
                }
                //update
                else {
                    service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(data => {
                        self.reloadDataAccidentInsuranceRateByAction();
                        self.clearErrorSaveAccidentInsurance();
                    }).fail(function(res) {
                        self.showMessageSaveAccidentInsurance(res.message);
                    })
                }
                return true;
            }
            //show HistoryAccidentInsurance (change event)
            private showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate: string) {
                if (selectionHistoryAccidentInsuranceRate != null && selectionHistoryAccidentInsuranceRate != undefined && selectionHistoryAccidentInsuranceRate != '') {
                    var self = this;
                    self.detailHistoryAccidentInsuranceRate(selectionHistoryAccidentInsuranceRate);
                }
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
                    if (data != null && data.length > 0) {
                        self.lstHistoryUnemployeeInsuranceRate = ko.observableArray<HistoryUnemployeeInsuranceDto>(data);
                        self.selectionHistoryUnemployeeInsuranceRate(data[0].historyId);
                        // self.historyUnemployeeInsuranceRateStart = ko.observable(data[0].startMonthRage);
                        // self.historyUnemployeeInsuranceRateEnd = ko.observable(data[0].endMonthRage);
                        self.selectionHistoryUnemployeeInsuranceRate.subscribe(function(selectionHistoryUnemployeeInsuranceRate: string) {
                            self.showchangeHistoryUnemployeeInsurance(selectionHistoryUnemployeeInsuranceRate);
                        });
                        self.detailHistoryUnemployeeInsuranceRate(data[0].historyId).done(data => {
                            dfd.resolve(self);
                        });
                    } else {
                        self.newmodelEmptyDataUnemployeeInsuranceRate();
                        dfd.resolve(self);
                    }

                });
                return dfd.promise();
            }

            //reload action
            private reloadDataUnemployeeInsuranceRateByAction() {
                var self = this;
                service.findAllHisotryUnemployeeInsuranceRate().done(data => {
                    if (data != null && data.length > 0) {
                        self.selectionHistoryUnemployeeInsuranceRate('');
                        self.lstHistoryUnemployeeInsuranceRate([]);
                        var historyId = self.unemployeeInsuranceRateModel().historyUnemployeeInsuranceModel.historyId();
                        if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                            historyId = data[0].historyId;
                        }
                        if (self.isEmptyUnemployee()) {
                            self.selectionHistoryUnemployeeInsuranceRate.subscribe(function(selectionHistoryUnemployeeInsuranceRate: string) {
                                self.showchangeHistoryUnemployeeInsurance(self.selectionHistoryUnemployeeInsuranceRate());
                            });
                            self.isEmptyUnemployee(false);
                        }
                        self.selectionHistoryUnemployeeInsuranceRate(historyId);
                        self.lstHistoryUnemployeeInsuranceRate(data);
                        self.detailHistoryUnemployeeInsuranceRate(historyId).done(data => {

                        });
                    } else {
                        self.newmodelEmptyDataUnemployeeInsuranceRate();
                    }
                });
            }
            //new model data = []
            private newmodelEmptyDataUnemployeeInsuranceRate() {
                var self = this;
                if (self.lstHistoryUnemployeeInsuranceRate == null || self.lstHistoryUnemployeeInsuranceRate == undefined) {
                    self.lstHistoryUnemployeeInsuranceRate = ko.observableArray<HistoryUnemployeeInsuranceDto>([]);
                } else {
                    self.lstHistoryUnemployeeInsuranceRate([]);
                }
                self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.selectionHistoryUnemployeeInsuranceRate('');
                self.resetValueUnemployeeInsuranceRate();
                self.isEmptyUnemployee(true);
            }
            //reset value UnemployeeInsuranceRate
            private resetValueUnemployeeInsuranceRate() {
                var self = this;
                self.unemployeeInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                self.selectionHistoryUnemployeeInsuranceRate('');
            }
            //detail HistoryUnemployeeInsuranceRate => show view model xhtml (action event)
            private detailHistoryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                if (historyId != null && historyId != undefined && historyId != '') {
                    service.detailHistoryUnemployeeInsuranceRate(historyId).done(data => {
                        self.unemployeeInsuranceRateModel().setListItem(data.rateItems);
                        self.unemployeeInsuranceRateModel().setHistoryData(data.historyInsurance);
                        self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                        dfd.resolve(null);
                    });
                }
                return dfd.promise();
            }
            //find All HistoryAccidentInsuranceRate => Show View model xhtml (constructor)
            private findAllHistoryAccidentInsuranceRate(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllHistoryAccidentInsuranceRate().done(data => {
                    if (data != null && data.length > 0) {
                        self.lstHistoryAccidentInsuranceRate = ko.observableArray<HistoryAccidentInsuranceDto>(data);
                        self.selectionHistoryAccidentInsuranceRate = ko.observable(data[0].historyId);
                        self.selectionHistoryAccidentInsuranceRate.subscribe(function(selectionHistoryAccidentInsuranceRate: string) {
                            self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                        });
                        self.detailHistoryAccidentInsuranceRate(data[0].historyId).done(data => {
                            service.findAllInsuranceBusinessType().done(data => {
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                dfd.resolve(self);
                            });
                        });
                    } else {
                        self.newmodelEmptyDataAccidentInsuranceRate();
                        dfd.resolve(self);
                    }
                });
                return dfd.promise();
            }

            //reload action
            private reloadDataAccidentInsuranceRateByAction() {
                var self = this;
                service.findAllHistoryAccidentInsuranceRate().done(data => {
                    if (data != null && data.length > 0) {
                        self.selectionHistoryAccidentInsuranceRate('');
                        self.lstHistoryAccidentInsuranceRate([]);
                        var historyId = self.accidentInsuranceRateModel().historyAccidentInsuranceRateModel.historyId();
                        if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                            historyId = data[0].historyId;
                        }
                        self.selectionHistoryAccidentInsuranceRate(historyId);
                        self.lstHistoryAccidentInsuranceRate(data);
                        self.detailHistoryAccidentInsuranceRate(historyId).done(data => {
                            service.findAllInsuranceBusinessType().done(data => {
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                            });
                        });
                        if (self.isEmptyAccident()) {
                            self.selectionHistoryAccidentInsuranceRate.subscribe(function(selectionHistoryAccidentInsuranceRate: string) {
                                self.showchangeHistoryAccidentInsurance(selectionHistoryAccidentInsuranceRate);
                            });
                            self.isEmptyAccident(false);
                        }
                    } else {
                        self.newmodelEmptyDataAccidentInsuranceRate();
                    }
                });
            }
            //new model data = []
            private newmodelEmptyDataAccidentInsuranceRate() {
                var self = this;
                if (self.lstHistoryAccidentInsuranceRate == null || self.lstHistoryAccidentInsuranceRate == undefined) {
                    self.lstHistoryAccidentInsuranceRate = ko.observableArray<HistoryAccidentInsuranceDto>([]);
                } else {
                    self.lstHistoryAccidentInsuranceRate([]);
                }
                self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.selectionHistoryAccidentInsuranceRate = ko.observable('');
                self.resetValueAccidentInsuranceRate();
                service.findAllInsuranceBusinessType().done(data => {
                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                });
                self.isEmptyAccident(true);
            }
            //reset value AccidentInsuranceRate
            private resetValueAccidentInsuranceRate() {
                var self = this;
                self.accidentInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                self.selectionHistoryAccidentInsuranceRate('');
            }
            //detail HistoryAccidentInsuranceRate => show view model xhtml (action event)
            private detailHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                if (historyId != null && historyId != undefined && historyId != '') {
                    service.findAccidentInsuranceRate(historyId).done(data => {
                        self.accidentInsuranceRateModel().setListItem(data.rateItems);
                        self.accidentInsuranceRateModel().setVersion(data.version);
                        self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                        self.accidentInsuranceRateModel().setHistoryData(data.historyInsurance);
                        dfd.resolve(null);
                    });
                }
                return dfd.promise();
            }
            //update InsuranceBusinessType by Model viewmodel
            private updateInsuranceBusinessTypeAccidentInsurance(insuranceBusinessTypeUpdateModel: InsuranceBusinessTypeUpdateModel) {
                var self = this;
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz1St());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz2Nd());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz3Rd());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz4Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz5Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz6Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz7Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz8Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz9Th());
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz10Th());
            }
            private updateInsuranceBusinessTypeAccidentInsuranceDto(InsuranceBusinessTypeDto: InsuranceBusinessTypeDto) {
                var self = this;
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz1St);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz2Nd);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz3Rd);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz4Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz5Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz6Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz7Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz8Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz9Th);
                self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz10Th);
            }
        }

        export class UnemployeeInsuranceRateItemSettingModel {
            roundAtr: KnockoutObservable<number>;
            rate: KnockoutObservable<number>;
            constructor() {
                this.roundAtr = ko.observable(0);
                this.rate = ko.observable(0);
            }
            resetValue() {
                if (this.roundAtr == null || this.roundAtr == undefined) {
                    this.roundAtr = ko.observable(0);
                } else {
                    this.roundAtr(0);
                }
                if (this.rate == null || this.rate == undefined) {
                    this.rate = ko.observable(0);
                } else {
                    this.rate(0);
                }
            }
            setItem(unemployeeInsuranceRateItemSetting: UnemployeeInsuranceRateItemSettingDto) {
                this.roundAtr(unemployeeInsuranceRateItemSetting.roundAtr);
                this.rate(unemployeeInsuranceRateItemSetting.rate);
            }
        }

        export class UnemployeeInsuranceRateItemModel {
            companySetting: UnemployeeInsuranceRateItemSettingModel;
            personalSetting: UnemployeeInsuranceRateItemSettingModel;
            rateInputOptions: any; selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
                this.companySetting = new UnemployeeInsuranceRateItemSettingModel();
                this.personalSetting = new UnemployeeInsuranceRateItemSettingModel();
            }
            resetValue() {
                if (this.companySetting == null || this.companySetting == undefined) {
                    this.companySetting = new UnemployeeInsuranceRateItemSettingModel();
                    this.companySetting.resetValue();
                } else {
                    this.companySetting.resetValue();
                }
                if (this.personalSetting == null || this.personalSetting == undefined) {
                    this.personalSetting = new UnemployeeInsuranceRateItemSettingModel();
                    this.personalSetting.resetValue();
                } else {
                    this.personalSetting.resetValue();
                }

            }
            setItem(unemployeeInsuranceRateItemDto: UnemployeeInsuranceRateItemDto) {
                this.companySetting.setItem(unemployeeInsuranceRateItemDto.companySetting);
                this.personalSetting.setItem(unemployeeInsuranceRateItemDto.personalSetting);
            }
        }
        export class HistoryUnemployeeInsuranceModel {
            historyId: KnockoutObservable<string>;
            startMonthRage: KnockoutObservable<string>;
            endMonthRage: KnockoutObservable<string>;
            constructor() {
                this.historyId = ko.observable('');
                this.startMonthRage = ko.observable('');
                this.endMonthRage = ko.observable('');
            }
            resetValue() {
                if (this.historyId != null && this.historyId != undefined) {
                    this.historyId('');
                } else {
                    this.historyId = ko.observable('');
                }
                if (this.startMonthRage != null && this.startMonthRage != undefined) {
                    this.startMonthRage('');
                } else {
                    this.startMonthRage = ko.observable('');
                }
                if (this.endMonthRage != null && this.endMonthRage != undefined) {
                    this.endMonthRage('9999/12');
                } else {
                    this.endMonthRage = ko.observable('9999/12');
                }
            }
            updateData(historyUnemployeeInsurance: HistoryUnemployeeInsuranceDto) {
                this.resetValue();
                this.historyId(historyUnemployeeInsurance.historyId);
                this.startMonthRage(historyUnemployeeInsurance.startMonthRage);
                this.endMonthRage(historyUnemployeeInsurance.endMonthRage);
            }
        }
        export class UnemployeeInsuranceRateModel {
            unemployeeInsuranceRateItemAgroforestryModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemContructionModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemOtherModel: UnemployeeInsuranceRateItemModel;
            version: KnockoutObservable<number>;
            historyUnemployeeInsuranceModel: HistoryUnemployeeInsuranceModel;
            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.version = ko.observable(0);
                this.historyUnemployeeInsuranceModel = new HistoryUnemployeeInsuranceModel();
            }
            resetValue(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                if (this.unemployeeInsuranceRateItemAgroforestryModel == null || this.unemployeeInsuranceRateItemAgroforestryModel == undefined) {
                    this.unemployeeInsuranceRateItemAgroforestryModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                }
                if (this.unemployeeInsuranceRateItemContructionModel == null || this.unemployeeInsuranceRateItemContructionModel == undefined) {
                    this.unemployeeInsuranceRateItemContructionModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemContructionModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemContructionModel.resetValue();
                }
                if (this.unemployeeInsuranceRateItemOtherModel == null || this.unemployeeInsuranceRateItemOtherModel == undefined) {
                    this.unemployeeInsuranceRateItemOtherModel = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemOtherModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemOtherModel.resetValue();
                }
                if (this.historyUnemployeeInsuranceModel == null || this.historyUnemployeeInsuranceModel == undefined) {
                    this.historyUnemployeeInsuranceModel = new HistoryUnemployeeInsuranceModel();
                } else {
                    this.historyUnemployeeInsuranceModel.resetValue();
                }
            }
            setHistoryData(historyUnemployeeInsuranceDto: HistoryUnemployeeInsuranceDto) {
                this.historyUnemployeeInsuranceModel.updateData(historyUnemployeeInsuranceDto);
            }
            setListItem(rateItems: UnemployeeInsuranceRateItemDto[]) {
                if (rateItems != null && rateItems.length > 0) {
                    for (var rateItem of rateItems) {
                        //Agroforestry
                        if (rateItem.careerGroup == CareerGroupDto.Agroforestry) {
                            this.unemployeeInsuranceRateItemAgroforestryModel.setItem(rateItem);
                        }
                        //Contruction
                        else if (rateItem.careerGroup == CareerGroupDto.Contruction) {
                            this.unemployeeInsuranceRateItemContructionModel.setItem(rateItem);
                        }
                        //Other
                        else if (rateItem.careerGroup == CareerGroupDto.Other) {
                            this.unemployeeInsuranceRateItemOtherModel.setItem(rateItem);
                        }
                    }
                }
            }
            setVersion(version: number) {
                this.version(version);
            }
        }
        export class AccidentInsuranceRateDetailModel {
            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            insuranceBusinessType: KnockoutObservable<string>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
                this.insuranceBusinessType = ko.observable('');
                this.insuRate = ko.observable(0);
                this.insuRound = ko.observable(0);
            }
            //Fuction update value insuranceBusinessType
            updateInsuranceBusinessType(insuranceBusinessType: string) {
                if (this.insuranceBusinessType() != null && this.insuranceBusinessType() != undefined) {
                    this.insuranceBusinessType(insuranceBusinessType);
                }
            }
            setItem(insuBizRateItem: InsuBizRateItemDto) {
                this.insuRate(insuBizRateItem.insuRate);
                this.insuRound(insuBizRateItem.insuRound);
            }
            resetValue() {
                if (this.insuRate == null || this.insuRate == undefined) {
                    this.insuRate = ko.observable(0);
                } else {
                    this.insuRate(0);
                }
                if (this.insuRound == null || this.insuRound == undefined) {
                    this.insuRound = ko.observable(0);
                } else {
                    this.insuRate(0);
                }
            }
        }
        export class HistoryAccidentInsuranceRateModel {
            historyId: KnockoutObservable<string>;
            startMonthRage: KnockoutObservable<string>;
            endMonthRage: KnockoutObservable<string>;
            constructor() {
                this.historyId = ko.observable('');
                this.startMonthRage = ko.observable('');
                this.endMonthRage = ko.observable('');
            }
            resetValue() {
                if (this.historyId != null && this.historyId != undefined) {
                    this.historyId('');
                } else {
                    this.historyId = ko.observable('');
                }
                if (this.startMonthRage != null && this.startMonthRage != undefined) {
                    this.startMonthRage('');
                } else {
                    this.startMonthRage = ko.observable('');
                }
                if (this.endMonthRage != null && this.endMonthRage != undefined) {
                    this.endMonthRage('9999/12');
                } else {
                    this.endMonthRage = ko.observable('9999/12');
                }
            }
            updateData(historyAccidentInsuranceRateDto: HistoryAccidentInsuranceDto) {
                this.resetValue();
                this.historyId(historyAccidentInsuranceRateDto.historyId);
                this.startMonthRage(historyAccidentInsuranceRateDto.startMonthRage);
                this.endMonthRage(historyAccidentInsuranceRateDto.endMonthRage);
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
            historyAccidentInsuranceRateModel: HistoryAccidentInsuranceRateModel;
            version: KnockoutObservable<number>;
            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.accidentInsuranceRateBiz1StModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz2NdModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz3RdModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz4ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz5ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz6ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz7ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz8ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz9ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.accidentInsuranceRateBiz10ThModel =
                    new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                this.historyAccidentInsuranceRateModel = new HistoryAccidentInsuranceRateModel();

            }
            setListItem(lstInsuBizRateItem: InsuBizRateItemDto[]) {
                for (var rateItem of lstInsuBizRateItem) {
                    //Biz1St
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz1St) {
                        this.accidentInsuranceRateBiz1StModel.setItem(rateItem);
                    }
                    //Biz2Nd
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz2Nd) {
                        this.accidentInsuranceRateBiz2NdModel.setItem(rateItem);
                    }
                    //Biz3Rd
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz3Rd) {
                        this.accidentInsuranceRateBiz3RdModel.setItem(rateItem);
                    }
                    //Biz4Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz4Th) {
                        this.accidentInsuranceRateBiz4ThModel.setItem(rateItem);
                    }
                    //Biz5Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz5Th) {
                        this.accidentInsuranceRateBiz5ThModel.setItem(rateItem);
                    }
                    //Biz6Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz6Th) {
                        this.accidentInsuranceRateBiz6ThModel.setItem(rateItem);
                    }
                    //Biz7Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz7Th) {
                        this.accidentInsuranceRateBiz7ThModel.setItem(rateItem);
                    }
                    //Biz8Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz8Th) {
                        this.accidentInsuranceRateBiz8ThModel.setItem(rateItem);
                    }
                    //Biz9Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz9Th) {
                        this.accidentInsuranceRateBiz9ThModel.setItem(rateItem);
                    }
                    //Biz10Th
                    if (rateItem.insuBizType == BusinessTypeEnumDto.Biz10Th) {
                        this.accidentInsuranceRateBiz10ThModel.setItem(rateItem);
                    }
                }
            }
            setVersion(version: number) {
                if (this.version == null || this.version == undefined) {
                    this.version = ko.observable(version);
                } else {
                    this.version(version);
                }
            }
            setHistoryData(historyAccidentInsuranceRateDto: HistoryAccidentInsuranceDto) {
                this.historyAccidentInsuranceRateModel.updateData(historyAccidentInsuranceRateDto);
            }
            resetValue(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                if (this.accidentInsuranceRateBiz1StModel == null || this.accidentInsuranceRateBiz1StModel == undefined) {
                    this.accidentInsuranceRateBiz1StModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz1StModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz1StModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz2NdModel == null || this.accidentInsuranceRateBiz2NdModel == undefined) {
                    this.accidentInsuranceRateBiz2NdModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz2NdModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz2NdModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz3RdModel == null || this.accidentInsuranceRateBiz3RdModel == undefined) {
                    this.accidentInsuranceRateBiz3RdModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz3RdModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz3RdModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz4ThModel == null || this.accidentInsuranceRateBiz4ThModel == undefined) {
                    this.accidentInsuranceRateBiz4ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz4ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz4ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz5ThModel == null || this.accidentInsuranceRateBiz5ThModel == undefined) {
                    this.accidentInsuranceRateBiz5ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz5ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz5ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz6ThModel == null || this.accidentInsuranceRateBiz6ThModel == undefined) {
                    this.accidentInsuranceRateBiz6ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz6ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz6ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz7ThModel == null || this.accidentInsuranceRateBiz7ThModel == undefined) {
                    this.accidentInsuranceRateBiz7ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz7ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz7ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz8ThModel == null || this.accidentInsuranceRateBiz8ThModel == undefined) {
                    this.accidentInsuranceRateBiz8ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz8ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz8ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz9ThModel == null || this.accidentInsuranceRateBiz9ThModel == undefined) {
                    this.accidentInsuranceRateBiz9ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz9ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz9ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz10ThModel == null || this.accidentInsuranceRateBiz10ThModel == undefined) {
                    this.accidentInsuranceRateBiz10ThModel = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz10ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz10ThModel.resetValue();
                }
                if (this.version == null || this.version == undefined) {
                    this.version = ko.observable(0);
                } else {
                    this.version(0);
                }
                if (this.historyAccidentInsuranceRateModel == null || this.historyAccidentInsuranceRateModel == undefined) {
                    this.historyAccidentInsuranceRateModel = new HistoryAccidentInsuranceRateModel();
                } else {
                    this.historyAccidentInsuranceRateModel.resetValue();
                }
            }

        }

    }
}