module nts.uk.pr.view.qmm011.a {

    //import data class .... BEGIN
    import option = nts.uk.ui.option;
    import RoundingMethodDto = service.model.RoundingMethodDto;
    import HistoryInsuranceInDto = service.model.HistoryInsuranceInDto;
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
    import AccidentInsuranceHistoryDto = service.model.AccidentInsuranceHistoryDto;
    import AccidentInsuranceRateHistoryFindInDto = service.model.AccidentInsuranceRateHistoryFindInDto;
    import InsuBizRateItemDto = service.model.InsuBizRateItemDto;
    import TypeActionInsuranceRate = service.model.TypeActionInsuranceRate;
    import InsuranceBusinessTypeDto = service.model.InsuranceBusinessTypeDto;
    import UnemployeeInsuranceRateCopyDto = service.model.UnemployeeInsuranceRateCopyDto;
    import InsuranceBusinessTypeUpdateModel = nts.uk.pr.view.qmm011.e.viewmodel.InsuranceBusinessTypeUpdateModel;
    import AddHistoryInfoModel = nts.uk.pr.view.qmm011.d.viewmodel.AddHistoryInfoModel;
    import UpdateHistoryInfoModel = nts.uk.pr.view.qmm011.f.viewmodel.UpdateHistoryInfoModel;
    //import data class ... END

    export module viewmodel {

        export class ScreenModel {

            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            //Update or Add To service 1 add 2 update
            typeActionUnemployeeInsurance: KnockoutObservable<number>;
            //雇用保険 detail B
            lstUnemployeeInsuranceRateHistory: KnockoutObservableArray<HistoryInsuranceInDto>;
            unemployeeInsuranceRateModel: KnockoutObservable<UnemployeeInsuranceRateModel>;
            rateInputOptions: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            selectionUnemployeeInsuranceRateHistory: KnockoutObservable<string>;
            beginHistoryStartUnemployeeInsuranceRate: KnockoutObservable<string>;
            isEmptyUnemployee: KnockoutObservable<boolean>;
            //労災保険 detail C
            lstAccidentInsuranceRateHistory: KnockoutObservableArray<AccidentInsuranceHistoryDto>;
            selectionAccidentInsuranceRateHistory: KnockoutObservable<string>;
            beginHistoryStartAccidentInsuranceRate: KnockoutObservable<string>;
            //detail D
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
            //Update or Add  typeAccidentInsurance: KnockoutObservable<number>;
            typeActionAccidentInsurance: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;
            isEnable: KnockoutObservable<boolean>;
            isEnableSaveUnemployeeInsurance: KnockoutObservable<boolean>;
            isEnableEditUnemployeeInsurance: KnockoutObservable<boolean>;
            isEnableSaveActionAccidentInsurance: KnockoutObservable<boolean>;
            isEnableEditActionAccidentInsurance: KnockoutObservable<boolean>;
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
                self.isEnable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.typeActionUnemployeeInsurance = ko.observable(TypeActionInsuranceRate.add);
                self.typeActionAccidentInsurance = ko.observable(TypeActionInsuranceRate.add);
                self.isEmptyUnemployee = ko.observable(true);
                self.isEmptyAccident = ko.observable(true);
                self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.unemployeeInsuranceRateModel = ko.observable(new UnemployeeInsuranceRateModel(self.rateInputOptions, self.selectionRoundingMethod));
                self.lstUnemployeeInsuranceRateHistory = ko.observableArray<HistoryInsuranceInDto>([]);
                self.lstAccidentInsuranceRateHistory = ko.observableArray<AccidentInsuranceHistoryDto>([]);
                self.selectionUnemployeeInsuranceRateHistory = ko.observable('');
                self.selectionAccidentInsuranceRateHistory = ko.observable('');
                self.isEnableSaveUnemployeeInsurance = ko.observable(true);
                self.isEnableEditUnemployeeInsurance = ko.observable(true);
                self.isEnableSaveActionAccidentInsurance = ko.observable(true);
                self.isEnableEditActionAccidentInsurance = ko.observable(true);
                self.beginHistoryStartUnemployeeInsuranceRate = ko.observable('');
                self.beginHistoryStartAccidentInsuranceRate = ko.observable('');
            }

            //open dialog edit UnemployeeInsuranceRateHistory => show view model xhtml (action event add)
            private openEditUnemployeeInsuranceRateHistory() {
                var self = this;
                //get historyId by selection of view
                var historyId = self.selectionUnemployeeInsuranceRateHistory();
                //call service find data by historyId
                service.findUnemployeeInsuranceRateHistory(historyId).done(data => {
                    //set data fw to f
                    nts.uk.ui.windows.setShared("historyEnd", data.endMonth);
                    nts.uk.ui.windows.setShared("historyStart", data.startMonth);
                    nts.uk.ui.windows.setShared("historyId", data.historyId);
                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryUnemployee);

                    //open dialog f/index.xhtml
                    nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(() => {
                        //OnClose => call
                        //get fw f=>respone
                        var updateHistoryInfoModel: UpdateHistoryInfoModel = nts.uk.ui.windows.getShared("updateHistoryInfoModel");
                        if (updateHistoryInfoModel != null && updateHistoryInfoModel != undefined) {
                            if (updateHistoryInfoModel.typeUpdate == 1) {
                                //delete action => reload view
                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                self.reloadDataUnemployeeInsuranceRateByAction();
                            } else {
                                //set up type is update
                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                                //update action => set data by action
                                var UnemployeeInsuranceHistoryDto: HistoryInsuranceInDto;
                                //get data update
                                UnemployeeInsuranceHistoryDto = new HistoryInsuranceInDto();
                                UnemployeeInsuranceHistoryDto.historyId = updateHistoryInfoModel.historyId;
                                UnemployeeInsuranceHistoryDto.startMonth = updateHistoryInfoModel.historyStart;
                                UnemployeeInsuranceHistoryDto.endMonth = updateHistoryInfoModel.historyEnd;
                                //update to viewmodel
                                self.unemployeeInsuranceRateModel().setHistoryData(UnemployeeInsuranceHistoryDto);
                            }
                        }
                    });
                });
            }

            //open dialog add UnemployeeInsuranceRateHistory => show view model xhtml (action event add)
            private openAddUnemployeeInsuranceRateHistory() {
                var self = this;
                //set data fw to /d/
                nts.uk.ui.windows.setShared("isEmpty", self.isEmptyUnemployee());

                if (!self.isEmptyUnemployee()) {
                    //set historyStart => begin set data using type !=2 (1)
                    nts.uk.ui.windows.setShared("historyStart", self.beginHistoryStartUnemployeeInsuranceRate());
                }

                //open d/index.xhtml 
                nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    //OnClose => call
                    //get fw d => respone
                    var addHistoryInfoModel: AddHistoryInfoModel = nts.uk.ui.windows.getShared("addHistoryInfoModel");
                    if (addHistoryInfoModel != null && addHistoryInfoModel != undefined) {
                        var UnemployeeInsuranceHistoryDto: HistoryInsuranceInDto;
                        UnemployeeInsuranceHistoryDto = new HistoryInsuranceInDto();
                        var unemployeeInsuranceRateCopyDto: UnemployeeInsuranceRateCopyDto;
                        unemployeeInsuranceRateCopyDto = new UnemployeeInsuranceRateCopyDto();
                        unemployeeInsuranceRateCopyDto.historyIdCopy = self.selectionUnemployeeInsuranceRateHistory();
                        unemployeeInsuranceRateCopyDto.startMonth = addHistoryInfoModel.starMonth;
                        if (addHistoryInfoModel.typeModel == 2) {
                            //(1) => reset data
                            self.resetValueUnemployeeInsuranceRate();

                            unemployeeInsuranceRateCopyDto.addNew = true;
                            service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(data => {
                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                self.reloadDataUnemployeeInsuranceRateByAction();
                            }).fail(function(error) {
                                self.showMessageSaveUnemployeeInsurance(error.message)
                            });
                        } else {
                            unemployeeInsuranceRateCopyDto.addNew = false;
                            service.copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto).done(data => {
                                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                                self.reloadDataUnemployeeInsuranceRateByAction();
                            }).fail(function(error) {
                                self.showMessageSaveUnemployeeInsurance(error.message)
                            });
                        }
                    }
                });
            }

            //open dialog edit InsuranceBusinessType => show view model xhtml (action event edit)
            private openEditInsuranceBusinessType() {
                var self = this;
                //call service get all insurance business type
                service.findAllInsuranceBusinessType().done(data => {
                    //set data fw to /e
                    nts.uk.ui.windows.setShared("InsuranceBusinessTypeDto", data);
                    //open dialog /e/index.xhtml
                    nts.uk.ui.windows.sub.modal("/view/qmm/011/e/index.xhtml", { height: 600, width: 425, title: "事業種類の登録" }).onClosed(() => {
                        //OnClose => call
                        //get fw e => respone 
                        var insuranceBusinessTypeUpdateModel = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateModel");

                        if (insuranceBusinessTypeUpdateModel != null && insuranceBusinessTypeUpdateModel != undefined) {
                            //reload insurance business type by call service
                            service.findAllInsuranceBusinessType().done(data => {
                                //update model view 
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                            });
                        }
                    });
                });
            }

            //open dialog edit AccidentInsuranceHistory => show view model xhtml (action event edit)
            private openEditAccidentInsuranceRateHistory() {
                var self = this;
                //get historyId by selection view
                var historyId = self.selectionAccidentInsuranceRateHistory();
                //call service get info history by historyId
                service.findAccidentInsuranceRateHistory(historyId).done(data => {
                    //set data fw to /f
                    nts.uk.ui.windows.setShared("historyEnd", data.endMonth);
                    nts.uk.ui.windows.setShared("historyStart", data.startMonth);
                    nts.uk.ui.windows.setShared("historyId", data.historyId);
                    nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);

                    //open dialog /f/index.xhtml
                    nts.uk.ui.windows.sub.modal('/view/qmm/011/f/index.xhtml', { title: '労働保険料率の登録>マスタ修正ログ', dialogClass: 'no-close' }).onClosed(() => {
                        //get data f => respone
                        var updateHistoryInfoModel: UpdateHistoryInfoModel = nts.uk.ui.windows.getShared("updateHistoryInfoModel");

                        if (updateHistoryInfoModel != null && updateHistoryInfoModel != undefined) {
                            //get action by respone
                            if (updateHistoryInfoModel.typeUpdate == 1) {
                                //set type action accident insurance add
                                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                                //delete action => reload data viewmodel
                                self.reloadDataAccidentInsuranceRateByAction();
                            } else {
                                //set type action accident insurance update
                                self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                                //update action => setup info update to viewmodel
                                var accidentInsuranceHistoryDto: AccidentInsuranceHistoryDto;
                                accidentInsuranceHistoryDto = new AccidentInsuranceHistoryDto();
                                accidentInsuranceHistoryDto.historyId = updateHistoryInfoModel.historyId;
                                accidentInsuranceHistoryDto.startMonth = updateHistoryInfoModel.historyStart;
                                accidentInsuranceHistoryDto.endMonth = updateHistoryInfoModel.historyEnd;
                                //setup data history by update
                                self.accidentInsuranceRateModel().setHistoryData(accidentInsuranceHistoryDto);
                            }
                        }
                    });
                });
            }

            //open dialog add AccidentInsuranceRateHistory => show view model xhtml (action event add)
            private openAddAccidentInsuranceRateHistory() {
                var self = this;
                //set data fw to /d/
                nts.uk.ui.windows.setShared("isEmpty", self.isEmptyAccident());

                if (!self.isEmptyAccident()) {
                    nts.uk.ui.windows.setShared("historyStart", self.beginHistoryStartAccidentInsuranceRate());
                }

                //open dialog /d/index.xhtml
                nts.uk.ui.windows.sub.modal('/view/qmm/011/d/index.xhtml', { title: '労働保険料率の登録>履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    //OnClose => call
                    //get fw d => respone
                    var addHistoryInfoModel: AddHistoryInfoModel = nts.uk.ui.windows.getShared("addHistoryInfoModel");
                    if (addHistoryInfoModel != null && addHistoryInfoModel != undefined) {
                        var accidentInsuranceHistoryDto: AccidentInsuranceHistoryDto;
                        accidentInsuranceHistoryDto = new AccidentInsuranceHistoryDto();
                        //get type action add by respone
                        if (addHistoryInfoModel.typeModel == 2) {
                            //type reset data
                            self.resetValueAccidentInsuranceRate();
                        }
                        //update history info
                        accidentInsuranceHistoryDto.historyId = '';
                        accidentInsuranceHistoryDto.startMonthRage = nts.uk.time.formatYearMonth(addHistoryInfoModel.starMonth);
                        accidentInsuranceHistoryDto.startMonth = addHistoryInfoModel.starMonth;
                        accidentInsuranceHistoryDto.endMonth = 999912;
                        accidentInsuranceHistoryDto.endMonthRage = '9999/12';
                        self.accidentInsuranceRateModel().setHistoryData(accidentInsuranceHistoryDto);
                        //set to viewmodel update history info
                        self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
                        self.isEnableSaveActionAccidentInsurance(true);
                    }
                });
            }

            //show UnemployeeInsuranceHistory (change event)
            private showchangeUnemployeeInsuranceHistory(selectionUnemployeeInsuranceRateHistory: string) {
                if (selectionUnemployeeInsuranceRateHistory != null
                    && selectionUnemployeeInsuranceRateHistory != undefined
                    && selectionUnemployeeInsuranceRateHistory != '') {
                    var self = this;
                    self.detailUnemployeeInsuranceRateHistory(selectionUnemployeeInsuranceRateHistory);
                }
            }

            //Clear show message error connection by server
            private clearErrorSaveUnemployeeInsurance() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_saveUnemployeeInsuranceHistory').ntsError('clear');
            }

            //show message save UnemployeeInsurance 
            private showMessageSaveUnemployeeInsurance(message: string) {
                $('#btn_saveUnemployeeInsuranceHistory').ntsError('set', message);
            }

            //Clear show message error connection by server
            private clearErrorSaveAccidentInsurance() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_saveAccidentInsuranceHistory').ntsError('clear');
            }

            //show message save AccidentInsurance 
            private showMessageSaveAccidentInsurance(message: string) {
                $('#btn_saveAccidentInsuranceHistory').ntsError('set', message);
            }

            //action save UnemployeeInsuranceHistory Onlick connection service
            private saveUnemployeeInsuranceHistory() {
                var self = this;

                // get type action (ismode)
                if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                    //type action is add
                    //call service add UnemployeeInsuranceHistory
                    service.addUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(data => {
                        //reload viewmodel
                        self.reloadDataUnemployeeInsuranceRateByAction();
                        //clear error viewmodel
                        self.clearErrorSaveUnemployeeInsurance();
                    }).fail(function(res) {
                        //show message by exception message
                        self.showMessageSaveUnemployeeInsurance(res.message);
                        self.reloadDataUnemployeeInsuranceRateByAction();
                    })
                } else {
                    //type action is update
                    //call service update UnemployeeInsuranceHistory
                    service.updateUnemployeeInsuranceRate(self.unemployeeInsuranceRateModel()).done(data => {
                        //reload viewmodel
                        self.reloadDataUnemployeeInsuranceRateByAction();
                        //clear error viewmodel
                        self.clearErrorSaveUnemployeeInsurance();
                    }).fail(function(res) {
                        //show message by exception message
                        self.showMessageSaveUnemployeeInsurance(res.message);
                        self.reloadDataUnemployeeInsuranceRateByAction();
                    })
                }

                return true;
            }

            //action save AccidentInsuranceHistory Onlick connection service
            private saveAccidentInsuranceHistory() {
                var self = this;

                //get type action (ismode)
                if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                    //type action is add
                    //call service add AccidentInsuranceRate
                    service.addAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(data => {
                        //reload viewmodel
                        self.reloadDataAccidentInsuranceRateByAction();
                        //clear error viewmodel
                        self.clearErrorSaveAccidentInsurance();
                    }).fail(function(res) {
                        //show message by exception message
                        self.showMessageSaveAccidentInsurance(res.message);
                    })
                } else {
                    //type action is update
                    //call service update AccidentInsuranceRate
                    service.updateAccidentInsuranceRate(self.accidentInsuranceRateModel()).done(data => {
                        //reload viewmodel
                        self.reloadDataAccidentInsuranceRateByAction();
                        //clear error viewmodel
                        self.clearErrorSaveAccidentInsurance();
                    }).fail(function(res) {
                        //show message by exception message
                        self.showMessageSaveAccidentInsurance(res.message);
                    })
                }

                return true;
            }

            //show AccidentInsuranceHistory (change event)
            private showchangeAccidentInsuranceHistory(selectionAccidentInsuranceRateHistory: string) {
                if (selectionAccidentInsuranceRateHistory != null
                    && selectionAccidentInsuranceRateHistory != undefined
                    && selectionAccidentInsuranceRateHistory != '') {
                    var self = this;
                    self.detailAccidentInsuranceRateHistory(selectionAccidentInsuranceRateHistory);
                }
            }

            // startPage => show view model xhtml (constructor)
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //findAll history Unemployee Insurance Rate
                self.findAllUnemployeeInsuranceRateHistory().done(data => {
                    //find All History Accident Insurance Rate
                    self.findAllAccidentInsuranceRateHistory().done(data => {
                        dfd.resolve(self);
                    });
                });

                return dfd.promise();
            }

            //find add UnemployeeInsuranceRateHistory => show view model xhtml (constructor)
            private findAllUnemployeeInsuranceRateHistory(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //call service get all history unemployee insuranceRate
                service.findAllUnemployeeInsuranceRateHistory().done(data => {
                    //get data respone
                    if (data != null && data.length > 0) {
                        //data[] is length > 0
                        //set List history unemployee insurance rate by data
                        self.lstUnemployeeInsuranceRateHistory(data);
                        //set selection by fisrt data select
                        self.selectionUnemployeeInsuranceRateHistory(data[0].historyId);
                        //subscribe history selection
                        if (self.isEmptyUnemployee()) {
                            self.selectionUnemployeeInsuranceRateHistory.subscribe(function(selectionUnemployeeInsuranceRateHistory: string) {
                                self.showchangeUnemployeeInsuranceHistory(selectionUnemployeeInsuranceRateHistory);
                            });
                        }
                        //set isEmptyUnemployee false (not isEmptyUnemployee)
                        self.isEmptyUnemployee(false);
                        //call detail history unemployee insurance rate
                        self.detailUnemployeeInsuranceRateHistory(data[0].historyId).done(data => {
                            //fw to start page
                            dfd.resolve(self);
                        });
                    } else {
                        //set history unemployee insurance rate is empty
                        self.newmodelEmptyDataUnemployeeInsuranceRate();
                        //fw to start page
                        dfd.resolve(self);
                    }
                });

                return dfd.promise();
            }

            //reload action
            private reloadDataUnemployeeInsuranceRateByAction() {
                var self = this;

                //call service get all history unemployee insurance rate
                service.findAllUnemployeeInsuranceRateHistory().done(data => {
                    if (data != null && data.length > 0) {
                        //get data respone is data not null length > 0
                        //reset selection history unemployee insurance rate
                        self.selectionUnemployeeInsuranceRateHistory('');
                        //reset List history unemployee insurance rate
                        self.lstUnemployeeInsuranceRateHistory([]);
                        //get historyId by selection viewmodel
                        var historyId = self.unemployeeInsuranceRateModel().unemployeeInsuranceHistoryModel.historyId();

                        if (self.typeActionUnemployeeInsurance() == TypeActionInsuranceRate.add) {
                            //type action is add => set historyId (selection first data)
                            historyId = data[0].historyId;
                        }

                        if (self.isEmptyUnemployee()) {
                            //empty history unemployee insurance rate => set subscribe
                            self.selectionUnemployeeInsuranceRateHistory.subscribe(function(selectionUnemployeeInsuranceRateHistory: string) {
                                self.showchangeUnemployeeInsuranceHistory(self.selectionUnemployeeInsuranceRateHistory());
                            });
                            //set is empty 
                            self.isEmptyUnemployee(false);
                        }

                        //set data List history unemployee insurance rate
                        self.selectionUnemployeeInsuranceRateHistory(historyId);
                        self.lstUnemployeeInsuranceRateHistory(data);
                        //call detail history unemployee insurance rate
                        self.detailUnemployeeInsuranceRateHistory(historyId).done(data => {
                            //set enable input viewmodel
                            self.unemployeeInsuranceRateModel().setEnable(true);
                        });
                        self.isEnableSaveUnemployeeInsurance(true);
                        self.isEnableEditUnemployeeInsurance(true);
                    } else {
                        //set history unemployee insurance rate is empty
                        self.newmodelEmptyDataUnemployeeInsuranceRate();
                    }
                });
            }

            //new model data = []
            private newmodelEmptyDataUnemployeeInsuranceRate() {
                var self = this;
                //reset selection history unemployee insurance rate
                self.selectionUnemployeeInsuranceRateHistory('');
                //reset List history unemployee insurance rate
                self.lstUnemployeeInsuranceRateHistory([]);
                //reset value history unemployee insurance rate
                self.resetValueUnemployeeInsuranceRate();
                //set is empty history unemployee insurance rate
                self.isEmptyUnemployee(true);
                self.isEnableSaveUnemployeeInsurance(false);
                self.isEnableEditUnemployeeInsurance(false);
            }

            //reset value UnemployeeInsuranceRate
            private resetValueUnemployeeInsuranceRate() {
                var self = this;
                //reset view model history unemployee insurance rate
                self.unemployeeInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                //reset enable input viewmodel
                self.unemployeeInsuranceRateModel().setEnable(false);
                //set ismode type action add
                self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.add);
                //reset selection history unemployee insurance rate
                self.selectionUnemployeeInsuranceRateHistory('');
            }

            //detail UnemployeeInsuranceRateHistory => show view model xhtml (action event)
            private detailUnemployeeInsuranceRateHistory(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                if (historyId != null && historyId != undefined && historyId != '') {
                    //call service detail history unemployee insurance rate by historyId
                    service.detailUnemployeeInsuranceRateHistory(historyId).done(data => {
                        //set viewmode by data
                        self.unemployeeInsuranceRateModel().setListItem(data.rateItems);
                        self.unemployeeInsuranceRateModel().setHistoryData(data.historyInsurance);
                        //set ismode type action is update
                        self.typeActionUnemployeeInsurance(TypeActionInsuranceRate.update);
                        self.beginHistoryStartUnemployeeInsuranceRate(nts.uk.time.formatYearMonth(data.historyInsurance.startMonth));
                        dfd.resolve(null);
                    });
                }

                return dfd.promise();
            }

            //find All AccidentInsuranceRateHistory => Show View model xhtml (constructor)
            private findAllAccidentInsuranceRateHistory(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //call service find all history accident insurance rate
                service.findAllAccidentInsuranceRateHistory().done(data => {
                    //get data respone
                    if (data != null && data.length > 0) {
                        //data not null length > 0 => update List history accident insurance rate
                        self.lstAccidentInsuranceRateHistory = ko.observableArray<AccidentInsuranceHistoryDto>(data);
                        self.selectionAccidentInsuranceRateHistory(data[0].historyId);
                        //subscribe history accident insurance rate
                        self.selectionAccidentInsuranceRateHistory.subscribe(function(selectionAccidentInsuranceRateHistory: string) {
                            self.showchangeAccidentInsuranceHistory(selectionAccidentInsuranceRateHistory);
                        });
                        //set is emmpty history accident insurance rate
                        self.isEmptyAccident(false);
                        //call detail history accident insurance rate by historyId
                        self.detailAccidentInsuranceRateHistory(data[0].historyId).done(data => {
                            //call service get all insurance business type
                            service.findAllInsuranceBusinessType().done(data => {
                                //update insurance business type
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                                dfd.resolve(self);
                            });
                        });
                        self.isEnableSaveActionAccidentInsurance(true);
                    } else {
                        //reset viewmode is empty
                        self.newmodelEmptyDataAccidentInsuranceRate();
                        dfd.resolve(self);
                    }
                });

                return dfd.promise();
            }

            //reload action
            private reloadDataAccidentInsuranceRateByAction() {
                var self = this;
                //call service find all  history accident insurance rate
                service.findAllAccidentInsuranceRateHistory().done(data => {
                    //get data by respone
                    if (data != null && data.length > 0) {
                        //data not null length > 0
                        //reset List history accident insurance rate
                        self.selectionAccidentInsuranceRateHistory('');
                        self.lstAccidentInsuranceRateHistory([]);
                        //set historyId
                        var historyId = self.accidentInsuranceRateModel().accidentInsuranceRateHistoryModel.historyId();
                        if (self.typeActionAccidentInsurance() == TypeActionInsuranceRate.add) {
                            historyId = data[0].historyId;
                        }
                        self.selectionAccidentInsuranceRateHistory(historyId);
                        //update List history accident insurance rate
                        self.lstAccidentInsuranceRateHistory(data);
                        //call detail history accident insurance rate
                        self.detailAccidentInsuranceRateHistory(historyId).done(data => {
                            //call service get all insurance business type
                            service.findAllInsuranceBusinessType().done(data => {
                                //update insurance business type
                                self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                            });
                        });
                        if (self.isEmptyAccident()) {
                            self.selectionAccidentInsuranceRateHistory.subscribe(function(selectionAccidentInsuranceRateHistory: string) {
                                self.showchangeAccidentInsuranceHistory(selectionAccidentInsuranceRateHistory);
                            });
                            self.isEmptyAccident(false);
                        }
                        self.isEnableSaveActionAccidentInsurance(true);
                        self.isEnableEditActionAccidentInsurance(true);
                    } else {
                        //reset viewmode is empty
                        self.newmodelEmptyDataAccidentInsuranceRate();
                    }
                });
            }

            //new model data = []
            private newmodelEmptyDataAccidentInsuranceRate() {
                var self = this;
                self.selectionAccidentInsuranceRateHistory('');
                self.lstAccidentInsuranceRateHistory([]);
                self.resetValueAccidentInsuranceRate();
                service.findAllInsuranceBusinessType().done(data => {
                    self.updateInsuranceBusinessTypeAccidentInsuranceDto(data);
                });
                self.isEmptyAccident(true);
                self.isEnableSaveActionAccidentInsurance(false);
                self.isEnableEditActionAccidentInsurance(false);
                self.accidentInsuranceRateModel().setEnable(false);
            }

            //reset value AccidentInsuranceRate
            private resetValueAccidentInsuranceRate() {
                var self = this;
                self.selectionAccidentInsuranceRateHistory('');
                self.accidentInsuranceRateModel().resetValue(self.rateInputOptions, self.selectionRoundingMethod);
                self.typeActionAccidentInsurance(TypeActionInsuranceRate.add);
            }

            //detail AccidentInsuranceRateHistory => show view model xhtml (action event)
            private detailAccidentInsuranceRateHistory(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                if (historyId != null && historyId != undefined && historyId != '') {
                    //call service find  accident insurance rate by historyId
                    service.findAccidentInsuranceRate(historyId).done(data => {
                        self.accidentInsuranceRateModel().setListItem(data.rateItems);
                        self.accidentInsuranceRateModel().setVersion(data.version);
                        self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                        self.accidentInsuranceRateModel().setHistoryData(data.historyInsurance);
                        self.accidentInsuranceRateModel().setEnable(true);
                        self.beginHistoryStartAccidentInsuranceRate(nts.uk.time.formatYearMonth(data.historyInsurance.startMonth));
                        dfd.resolve(null);
                    });
                }

                return dfd.promise();
            }

            //update InsuranceBusinessType by Model viewmodel
            private updateInsuranceBusinessTypeAccidentInsurance
                (insuranceBusinessTypeUpdateModel: InsuranceBusinessTypeUpdateModel) {
                var self = this;
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz1StModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz1St());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz2NdModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz2Nd());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz3RdModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz3Rd());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz4ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz4Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz5ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz5Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz6ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz6Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz7ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz7Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz8ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz8Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz9ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz9Th());
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz10ThModel
                    .updateInsuranceBusinessType(insuranceBusinessTypeUpdateModel.bizNameBiz10Th());
            }

            //update insurance business type accident insurance
            private updateInsuranceBusinessTypeAccidentInsuranceDto(InsuranceBusinessTypeDto: InsuranceBusinessTypeDto) {
                var self = this;
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz1StModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz1St);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz2NdModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz2Nd);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz3RdModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz3Rd);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz4ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz4Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz5ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz5Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz6ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz6Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz7ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz7Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz8ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz8Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz9ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz9Th);
                self.accidentInsuranceRateModel()
                    .accidentInsuranceRateBiz10ThModel
                    .updateInsuranceBusinessType(InsuranceBusinessTypeDto.bizNameBiz10Th);
            }
        }

        export class UnemployeeInsuranceRateItemSettingModel {

            roundAtr: KnockoutObservable<number>;
            rate: KnockoutObservable<number>;
            isEnable: KnockoutObservable<boolean>;

            constructor() {
                this.roundAtr = ko.observable(0);
                this.rate = ko.observable(0);
                this.isEnable = ko.observable(true);
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
                if (this.isEnable == null || this.isEnable == undefined) {
                    this.isEnable = ko.observable(false);
                } else {
                    this.isEnable(false);
                }
            }

            setItem(unemployeeInsuranceRateItemSetting: UnemployeeInsuranceRateItemSettingDto) {
                this.roundAtr(unemployeeInsuranceRateItemSetting.roundAtr);
                this.rate(unemployeeInsuranceRateItemSetting.rate);
            }

            setEnable(isEnable: boolean) {
                this.isEnable(isEnable);
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

            setEnable(isEnable: boolean) {
                this.companySetting.setEnable(isEnable);
                this.personalSetting.setEnable(isEnable);
            }
        }

        export class UnemployeeInsuranceHistoryModel {

            historyId: KnockoutObservable<string>;
            startJapStartMonth: KnockoutObservable<string>;
            startMonth: KnockoutObservable<number>;
            endMonth: KnockoutObservable<number>;
            endMonthRage: KnockoutObservable<string>;

            constructor() {
                this.historyId = ko.observable('');
                this.startJapStartMonth = ko.observable('');
                this.endMonthRage = ko.observable('');
                this.endMonth = ko.observable(0);
                this.startMonth = ko.observable(0);
            }

            resetValue() {
                if (this.historyId != null && this.historyId != undefined) {
                    this.historyId('');
                } else {
                    this.historyId = ko.observable('');
                }
                if (this.startJapStartMonth != null && this.startJapStartMonth != undefined) {
                    this.startJapStartMonth('');
                } else {
                    this.startJapStartMonth = ko.observable('');
                }
                if (this.endMonthRage != null && this.endMonthRage != undefined) {
                    this.endMonthRage('9999/12');
                } else {
                    this.endMonthRage = ko.observable('9999/12');
                }
                if (this.endMonth != null && this.endMonth != undefined) {
                    this.endMonth(999912);
                } else {
                    this.endMonth = ko.observable(999912);
                }
                if (this.startMonth != null && this.startMonth != undefined) {
                    this.endMonth(0);
                } else {
                    this.startMonth = ko.observable(0);
                }
            }

            updateData(unemployeeInsuranceHistory: HistoryInsuranceInDto) {
                this.historyId(unemployeeInsuranceHistory.historyId);
                this.startJapStartMonth(nts.uk.time.formatYearMonth(unemployeeInsuranceHistory.startMonth)
                    + ' (' + nts.uk.time.yearmonthInJapanEmpire(unemployeeInsuranceHistory.startMonth).toString() + ') ');
                this.endMonthRage(nts.uk.time.formatYearMonth(unemployeeInsuranceHistory.endMonth));
                this.startMonth(unemployeeInsuranceHistory.startMonth);
                this.endMonth(unemployeeInsuranceHistory.endMonth);
            }

            setMonthRage(startDate: number, endDate: number) {
                this.startMonth(startDate);
                this.endMonth(endDate);
            }
        }

        export class UnemployeeInsuranceRateModel {

            unemployeeInsuranceRateItemAgroforestryModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemContructionModel: UnemployeeInsuranceRateItemModel;
            unemployeeInsuranceRateItemOtherModel: UnemployeeInsuranceRateItemModel;
            version: KnockoutObservable<number>;
            unemployeeInsuranceHistoryModel: UnemployeeInsuranceHistoryModel;
            isShowTable: KnockoutObservable<boolean>;

            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.unemployeeInsuranceRateItemAgroforestryModel
                    = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.unemployeeInsuranceRateItemContructionModel
                    = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.unemployeeInsuranceRateItemOtherModel
                    = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                this.version = ko.observable(0);
                this.isShowTable = ko.observable(false);
                this.unemployeeInsuranceHistoryModel = new UnemployeeInsuranceHistoryModel();
            }

            resetValue(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                if (this.unemployeeInsuranceRateItemAgroforestryModel == null
                    || this.unemployeeInsuranceRateItemAgroforestryModel == undefined) {
                    this.unemployeeInsuranceRateItemAgroforestryModel
                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemAgroforestryModel.resetValue();
                }
                if (this.unemployeeInsuranceRateItemContructionModel == null
                    || this.unemployeeInsuranceRateItemContructionModel == undefined) {
                    this.unemployeeInsuranceRateItemContructionModel
                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemContructionModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemContructionModel.resetValue();
                }
                if (this.unemployeeInsuranceRateItemOtherModel == null
                    || this.unemployeeInsuranceRateItemOtherModel == undefined) {
                    this.unemployeeInsuranceRateItemOtherModel
                        = new UnemployeeInsuranceRateItemModel(rateInputOptions, selectionRoundingMethod);
                    this.unemployeeInsuranceRateItemOtherModel.resetValue();
                } else {
                    this.unemployeeInsuranceRateItemOtherModel.resetValue();
                }
                if (this.unemployeeInsuranceHistoryModel == null
                    || this.unemployeeInsuranceHistoryModel == undefined) {
                    this.unemployeeInsuranceHistoryModel = new UnemployeeInsuranceHistoryModel();
                } else {
                    this.unemployeeInsuranceHistoryModel.resetValue();
                }
            }

            setHistoryData(UnemployeeInsuranceHistoryDto: HistoryInsuranceInDto) {
                this.unemployeeInsuranceHistoryModel.updateData(UnemployeeInsuranceHistoryDto);
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
            setEnable(isEnable: boolean) {
                this.unemployeeInsuranceRateItemAgroforestryModel.setEnable(isEnable);
                this.unemployeeInsuranceRateItemContructionModel.setEnable(isEnable);
                this.unemployeeInsuranceRateItemOtherModel.setEnable(isEnable);
                this.isShowTable(!isEnable);
            }
        }

        export class AccidentInsuranceRateDetailModel {

            insuRate: KnockoutObservable<number>;
            insuRound: KnockoutObservable<number>;
            insuranceBusinessType: KnockoutObservable<string>;
            rateInputOptions: any;
            selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>;
            isEnable: KnockoutObservable<boolean>;

            constructor(rateInputOptions: any, selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                this.rateInputOptions = rateInputOptions;
                this.selectionRoundingMethod = selectionRoundingMethod;
                this.insuranceBusinessType = ko.observable('');
                this.insuRate = ko.observable(0);
                this.insuRound = ko.observable(0);
                this.isEnable = ko.observable(true);
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
                if (this.isEnable == null || this.isEnable == undefined) {
                    this.isEnable = ko.observable(true);
                } else {
                    this.isEnable(true);
                }
            }

            setEnable(isEnable: boolean) {
                this.isEnable(isEnable);
            }
        }

        export class AccidentInsuranceRateHistoryModel {

            historyId: KnockoutObservable<string>;
            startMonthRage: KnockoutObservable<string>;
            startMonth: KnockoutObservable<number>;
            endMonthRage: KnockoutObservable<string>;
            endMonth: KnockoutObservable<number>;

            constructor() {
                this.historyId = ko.observable('');
                this.startMonthRage = ko.observable('');
                this.endMonthRage = ko.observable('');
                this.endMonth = ko.observable(0);
                this.startMonth = ko.observable(0);
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

                if (this.startMonth != null && this.startMonth != undefined) {
                    this.startMonth = ko.observable(0);
                } else {
                    this.startMonth(0);
                }

                if (this.endMonth != null && this.endMonth != undefined) {
                    this.endMonth = ko.observable(0);
                } else {
                    this.endMonth(0);
                }
            }

            updateData(historyDto: HistoryInsuranceInDto) {
                this.resetValue();
                this.historyId(historyDto.historyId);
                this.startMonth(historyDto.startMonth);
                this.endMonth(historyDto.endMonth);
                this.startMonthRage(nts.uk.time.formatYearMonth(historyDto.startMonth)
                    + ' (' + nts.uk.time.yearmonthInJapanEmpire(historyDto.startMonth).toString() + ') ');
                this.endMonthRage(nts.uk.time.formatYearMonth(historyDto.endMonth));
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
            accidentInsuranceRateHistoryModel: AccidentInsuranceRateHistoryModel;
            version: KnockoutObservable<number>;
            isShowTable: KnockoutObservable<boolean>;

            constructor(rateInputOptions: any,
                selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
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
                this.accidentInsuranceRateHistoryModel = new AccidentInsuranceRateHistoryModel();
                this.isShowTable = ko.observable(true);
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

            setHistoryData(historyDto: HistoryInsuranceInDto) {
                this.accidentInsuranceRateHistoryModel.updateData(historyDto);
            }

            resetValue(rateInputOptions: any,
                selectionRoundingMethod: KnockoutObservableArray<RoundingMethodDto>) {
                if (this.accidentInsuranceRateBiz1StModel == null
                    || this.accidentInsuranceRateBiz1StModel == undefined) {
                    this.accidentInsuranceRateBiz1StModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz1StModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz1StModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz2NdModel == null
                    || this.accidentInsuranceRateBiz2NdModel == undefined) {
                    this.accidentInsuranceRateBiz2NdModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz2NdModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz2NdModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz3RdModel == null
                    || this.accidentInsuranceRateBiz3RdModel == undefined) {
                    this.accidentInsuranceRateBiz3RdModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz3RdModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz3RdModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz4ThModel == null
                    || this.accidentInsuranceRateBiz4ThModel == undefined) {
                    this.accidentInsuranceRateBiz4ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz4ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz4ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz5ThModel == null
                    || this.accidentInsuranceRateBiz5ThModel == undefined) {
                    this.accidentInsuranceRateBiz5ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz5ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz5ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz6ThModel == null
                    || this.accidentInsuranceRateBiz6ThModel == undefined) {
                    this.accidentInsuranceRateBiz6ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz6ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz6ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz7ThModel == null
                    || this.accidentInsuranceRateBiz7ThModel == undefined) {
                    this.accidentInsuranceRateBiz7ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz7ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz7ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz8ThModel == null
                    || this.accidentInsuranceRateBiz8ThModel == undefined) {
                    this.accidentInsuranceRateBiz8ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz8ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz8ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz9ThModel == null
                    || this.accidentInsuranceRateBiz9ThModel == undefined) {
                    this.accidentInsuranceRateBiz9ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz9ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz9ThModel.resetValue();
                }
                if (this.accidentInsuranceRateBiz10ThModel == null
                    || this.accidentInsuranceRateBiz10ThModel == undefined) {
                    this.accidentInsuranceRateBiz10ThModel
                        = new AccidentInsuranceRateDetailModel(rateInputOptions, selectionRoundingMethod);
                    this.accidentInsuranceRateBiz10ThModel.resetValue();
                } else {
                    this.accidentInsuranceRateBiz10ThModel.resetValue();
                }
                if (this.version == null || this.version == undefined) {
                    this.version = ko.observable(0);
                } else {
                    this.version(0);
                }
                if (this.accidentInsuranceRateHistoryModel == null
                    || this.accidentInsuranceRateHistoryModel == undefined) {
                    this.accidentInsuranceRateHistoryModel
                        = new AccidentInsuranceRateHistoryModel();
                } else {
                    this.accidentInsuranceRateHistoryModel.resetValue();
                }
            }
            setEnable(isEnable: boolean) {
                this.accidentInsuranceRateBiz1StModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz2NdModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz3RdModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz4ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz5ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz6ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz7ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz8ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz9ThModel.setEnable(isEnable);
                this.accidentInsuranceRateBiz10ThModel.setEnable(isEnable);
                this.isShowTable(!isEnable);
            }
        }
    }
}