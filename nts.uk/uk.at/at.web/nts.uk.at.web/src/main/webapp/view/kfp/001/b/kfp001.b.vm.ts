module nts.uk.at.view.kfp001.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {

            cScreenmodel: any;
            dScreenmodel: any;
            aggrList: KnockoutObservableArray<any>;

            //Wizard
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;
            stepList: Array<NtsWizardStep>;

            // List
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            optionalList: KnockoutObservableArray<any>;
            exeList: KnockoutObservableArray<any>;
            items: KnockoutObservableArray<model.OptionalAggrPeriodDto>;
            itemsExe: KnockoutObservableArray<model.AggrPeriodExcutionDto>
            currentItem: KnockoutObservable<model.OptionalAggrPeriodDto>
            currentItemExe: KnockoutObservable<model.AggrPeriodExcutionDto>

            //
            enableNEW: KnockoutObservable<boolean>;
            enableDEL: KnockoutObservable<boolean>;
            reintegration: KnockoutObservable<boolean> = ko.observable(false);
            peopleNo: KnockoutObservable<number>;
            peopleFromC: KnockoutObservable<number>;

            aggrId: KnockoutObservable<string>;

            mode: KnockoutObservable<number>;

            enableText: KnockoutObservable<boolean> = ko.observable(false);

            dateValue: KnockoutObservable<any>;
            dateCurrentValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            peopleCount: KnockoutObservable<string> = ko.observable('');

            status: KnockoutObservable<number> = ko.observable(0);
            preOfError: KnockoutObservable<string> = ko.observable('');
            isFocus: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                var self = this;
                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kfp001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kfp001.d.viewmodel.ScreenModel();
                self.aggrList = ko.observableArray([]);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});
                self.dateCurrentValue = ko.observable({});

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);
                self.mode = ko.observable(0);
                self.activeStep.subscribe(newVal => {
                    self.enableText(false);
                    if (newVal == 0) {
                        $('#hor-scroll-button-hide').hide();
                        _.defer(() => {
                            $('#hor-scroll-button-hide').show();
                            // call api load table
                            self.backStartKFP001B();
                        });
                        $('#NEW_BTN_B1_2').show();
                        $('#DELETE_BTN_B1_3').show();
                    } else {
                        $('#NEW_BTN_B1_2').hide();
                        $('#DELETE_BTN_B1_3').hide();
                    }
                })

                //
                self.peopleNo = ko.observable(null);
                self.peopleFromC = ko.observable(null);
                self.optionalList = ko.observableArray([]);
                self.exeList = ko.observableArray([]);
                self.items = ko.observableArray([]);
                self.itemsExe = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'aggrFrameCode', width: 45 },
                    { headerText: '名称', key: 'optionalAggrName', width: 130 }
                ]);
                self.currentItem = ko.observable(new model.OptionalAggrPeriodDto({}));
                self.currentItemExe = ko.observable(new model.AggrPeriodExcutionDto({}));
                self.currentCode = ko.observable();
                self.aggrId = ko.observable('');
                self.currentCode.subscribe(function(codeChanged) {
                    if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                        self.enableNEW(true);
                        self.enableDEL(true);
                        if (!_.isNil(self.findOptional(codeChanged))) {
                          self.currentItem(self.findOptional(codeChanged));
                        } else {
                          self.currentItem().startDate(self.dateValue().startDate);
                          self.currentItem().endDate(self.dateValue().endDate);
                        }
                        self.currentItemExe(self.findExc(codeChanged))
                        self.getPeriod();
                        self.dateValue({
                            startDate: self.currentItem().startDate(),
                            endDate: self.currentItem().endDate()
                        });

                        self.dateCurrentValue({
                            startDate: self.currentItem().startDate(),
                            endDate: self.currentItem().endDate()
                        });

                        self.enableText(false);
                        nts.uk.ui.errors.clearAll();
                        $('#update-mode').show();
                        $('#button-reference-B7_3').focus();
                        self.mode(1);

                    }
                    $('.control-group').find('#code-text-d4-2').focus();
                });
                self.reintegration.subscribe((vl)=>{
                    if(vl == false){
                        self.dateValue({
                            startDate: self.dateCurrentValue().startDate,
                            endDate: self.dateCurrentValue().endDate
                        });
                    }
                });
                //
                self.enableNEW = ko.observable(true);
                self.enableDEL = ko.observable(true);
                //                self.aggrFrameCode = ko.observable("D01");
                //                self.optionalAggrName = ko.observable("THANH DEP ZAI");
                //                self.startDate = ko.observable('15062018');
                //                self.endDate = ko.observable('20062018');
            }

            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                $.when(self.getAllOptionalAggrPeriod()).done(function() {
                    if (self.items().length > 0) {
                        self.currentCode(self.items()[0].aggrFrameCode());
                        self.mode(1);
                        self.getPeriod();
                        self.dateValue({
                            startDate: self.currentItem().startDate(),
                            endDate: self.currentItem().endDate()
                        });
                        $('#update-mode').show();
                        $('#update-mode').focus();
                        let period = {
                            periodStartDate: self.currentItem().startDate(),
                            periodEndDate: self.currentItem().endDate()
                        }
                        nts.uk.ui.windows.setShared("KFP001_DATAB", period);
                    } else {
                        self.initDataB();
                    }

                    //                    service.findByAggrFrameCode(self.currentItem().aggrFrameCode()).done(function(aggrPeriod_arr: Array<model.IOptionalAggrPeriodDto>) {
                    //
                    //                    })

                    // Bind data to Screen D


                    ko.computed(() => {

                        self.dScreenmodel.listEmp(self.cScreenmodel.selectedEmployee());
                        self.dScreenmodel.peopleNo(null);
                        if (_.size(self.cScreenmodel.multiSelectedCode()) >= 418) {
                            self.dScreenmodel.peopleNo(_.size(self.cScreenmodel.selectedEmployee()));
                            self.dScreenmodel.peopleCount(nts.uk.resource.getText("KFP001_23", [_.size(self.cScreenmodel.selectedEmployee())]));
                        } else {
                            self.dScreenmodel.peopleNo(_.size(self.cScreenmodel.multiSelectedCode()));
                            self.dScreenmodel.peopleCount(nts.uk.resource.getText("KFP001_23", [_.size(self.cScreenmodel.multiSelectedCode())]));
                        }


                        self.dScreenmodel.listSelect((self.cScreenmodel.multiSelectedCode()));
                        if (!_.isNil(self.currentItem())) {
                          self.dScreenmodel.aggrFrameCode(self.currentItem().aggrFrameCode());
                          self.dScreenmodel.optionalAggrName(self.currentItem().optionalAggrName());
                        }
                        self.dScreenmodel.startDate(self.dateValue().startDate);
                        self.dScreenmodel.endDate(self.dateValue().endDate);
                        self.dScreenmodel.mode(self.mode());
                        self.dScreenmodel.executionId(self.aggrId);
                        self.dScreenmodel.listAggr(self.optionalList());
                        self.dScreenmodel.presenceOfError(self.status());
                        self.dScreenmodel.executionStatus(self.preOfError());
                        if (self.mode() == 1){
                            self.cScreenmodel.periodStartDate(self.currentItem().startDate());
                            self.cScreenmodel.periodEndDate(self.currentItem().endDate());
                        } else {
                            self.cScreenmodel.periodStartDate(self.dateValue().startDate);
                            self.cScreenmodel.periodEndDate(self.dateValue().endDate);
                        }

                    });

                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
                return dfd.promise();
            }

            //Display data in update module when back to screen
            backStartKFP001B(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                $.when(self.getAllOptionalAggrPeriod()).done(function() {
                    const isItemExist = _.find(self.items(), data => data.aggrFrameCode() === self.currentItem().aggrFrameCode());
                    if (self.items().length > 0) {
                        self.currentCode(self.currentItem().aggrFrameCode());
                        self.mode(!!isItemExist ? 1 : 0);
                        self.enableNEW(!!isItemExist);
                        self.enableDEL(!!isItemExist);
                        // self.enableText(!!isItemExist);
                        self.getPeriod();
                        $('#update-mode').show();
                        $('#update-mode').focus();
                        let period = {
                            periodStartDate: self.currentItem().startDate(),
                            periodEndDate: self.currentItem().endDate()
                        }
                        nts.uk.ui.windows.setShared("KFP001_DATAB", period);
                    } else {
                        self.initDataB();
                    }

                    ko.computed(() => {
                        self.dScreenmodel.listEmp(self.cScreenmodel.selectedEmployee());
                        self.dScreenmodel.peopleNo(null);
                        if (_.size(self.cScreenmodel.multiSelectedCode()) >= 418) {
                            self.dScreenmodel.peopleNo(_.size(self.cScreenmodel.selectedEmployee()));
                            self.dScreenmodel.peopleCount(nts.uk.resource.getText("KFP001_23", [_.size(self.cScreenmodel.selectedEmployee())]));
                        } else {
                            self.dScreenmodel.peopleNo(_.size(self.cScreenmodel.multiSelectedCode()));
                            self.dScreenmodel.peopleCount(nts.uk.resource.getText("KFP001_23", [_.size(self.cScreenmodel.multiSelectedCode())]));
                        }


                        self.dScreenmodel.listSelect((self.cScreenmodel.multiSelectedCode()));
                        self.dScreenmodel.aggrFrameCode(self.currentItem().aggrFrameCode());
                        self.dScreenmodel.optionalAggrName(self.currentItem().optionalAggrName());
                        self.dScreenmodel.startDate(self.dateValue().startDate);
                        self.dScreenmodel.endDate(self.dateValue().endDate);
                        self.dScreenmodel.mode(self.mode());
                        self.dScreenmodel.executionId(self.aggrId);
                        self.dScreenmodel.listAggr(self.optionalList());
                        self.dScreenmodel.presenceOfError(self.status());
                        self.dScreenmodel.executionStatus(self.preOfError());
                        if (self.mode() == 1){
                            self.cScreenmodel.periodStartDate(self.currentItem().startDate());
                            self.cScreenmodel.periodEndDate(self.currentItem().endDate());
                        } else {
                            self.cScreenmodel.periodStartDate(self.dateValue().startDate);
                            self.cScreenmodel.periodEndDate(self.dateValue().endDate);
                        }

                    });

                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
                return dfd.promise();
            }

            getPeriod() {

                var self = this;
                var dfd = $.Deferred();

                $.when(service.findAggrCode(self.currentCode())).done(function(data) {
                  if (!_.isEmpty(data)) {
                    service.findTargetPeriod(data[0].aggrId).done(function(dataTarget) {
                      self.aggrId = data[0].aggrId;
                      self.peopleNo(dataTarget.length);
                      self.status(data[0].executionStatus);
                      self.preOfError(data[0].presenceOfError);
                      self.peopleCount(nts.uk.resource.getText("KFP001_23", [dataTarget.length]));
                    })
                  }
                }).fail(function() {
                    dfd.reject();
                });;

                return dfd.promise();
            }
            // ドメインモデル 「任意集計期間」 を取得する get domain OptionalAggrPeriod by CompanyId            
            getAllOptionalAggrPeriod(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.items.removeAll();
                service.findAllOptionalAggrPeriod().done(function(optionalAggrPeriod_arr: Array<model.IOptionalAggrPeriodDto>) {
                    self.optionalList(optionalAggrPeriod_arr);
                    _.forEach(optionalAggrPeriod_arr, function(optionalAggrPeriodRes: model.IOptionalAggrPeriodDto) {
                        var optionalAggr: model.IOptionalAggrPeriodDto = {
                            aggrFrameCode: optionalAggrPeriodRes.aggrFrameCode,
                            optionalAggrName: optionalAggrPeriodRes.optionalAggrName,
                            startDate: optionalAggrPeriodRes.startDate,
                            endDate: optionalAggrPeriodRes.endDate,
                        };
                        self.items.push(new model.OptionalAggrPeriodDto(optionalAggr));

                        $('#code-text-d4-2').focus();
                    });
                    dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }


            findStatus() {
                let self = this;
                var dfd = $.Deferred();
                service.findExecAggr(self.currentItem().aggrFrameCode()).done(function(data) {
                    if (data.executionStatus == model.ExecutionStatus.Processing) {
                        nts.uk.ui.windows.sub.modal('/view/kfp/001/e/index.xhtml');
                    } else {
                        $("#wizard").ntsWizard("next").done(function() {
                        });
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }


            findOptional(value: any): any {
                let self = this;
                var result = _.find(self.items(), function(obj: model.OptionalAggrPeriodDto) {
                    return obj.aggrFrameCode() == value;
                });
                return result;
            }

            findExc(value: any): any {
                let self = this;
                var result = _.find(self.itemsExe(), function(obj: model.AggrPeriodExcutionDto) {
                    return obj.aggrFrameCode() == value;
                });
                return result;
            }
            initDataB() {
                let self = this;
                var emptyObject: model.IOptionalAggrPeriodDto = {};
                if (self.items().length >= 100) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1151" });
                } else {
                    self.enableText(true);
                    self.currentItem(new model.OptionalAggrPeriodDto(emptyObject))
                    self.currentCode("");
                    self.peopleNo(0);
                    nts.uk.ui.errors.clearAll();
                    self.enableNEW(false);
                    self.enableDEL(false);
                    $('#update-mode').hide();
                    self.dateValue({
                        startDate: new Date(),
                        endDate: new Date()
                    });
                    self.mode(0);
                }
                self.isFocus(true)
            }

            deleteDataB() {

                let self = this;
                let dataStatus = [];
                var index_of_itemDelete = _.findIndex(self.items(), function(item) {
                    return item.aggrFrameCode() == self.currentCode();
                });
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {

                    $.when(service.findStatus(self.currentCode(), 2)).done(function(dataEx) {

                        if (dataEx.length > 0) {
                            nts.uk.ui.dialog.info({ messageId: "Msg_1172" });
                            return;
                        } else {
                            $.when(service.deleteOptionalAggr(self.currentCode())).done(function() {
                                $.when(self.getAllOptionalAggrPeriod()).done(function() {
                                    var optionalAggrId: string = null;
                                    if (self.items().length == 0) {
                                        self.initDataB();
                                    } else if (self.items().length == 1) {
                                        optionalAggrId = self.items()[0].aggrFrameCode();
                                    } else if (index_of_itemDelete == self.items().length) {
                                        optionalAggrId = self.items()[index_of_itemDelete - 1].aggrFrameCode();
                                    } else {
                                        optionalAggrId = self.items()[index_of_itemDelete].aggrFrameCode();
                                    }
                                    self.currentCode(optionalAggrId);
                                });
                            })
                        }
                    })
                }).ifNo(function() {
                    return;
                });
            }
            navigateView() {

                nts.uk.request.jump("/view/kfp/001/a/index.xhtml");
            }
            opendScreenBorJ() {
                let self = this;
                var dfd = $.Deferred();
                nts.uk.ui.windows.setShared("B_CHECKED", self.reintegration());
                $("#code-text-d4-2").trigger("validate");
                $("#code-text-d4-21").trigger("validate");
                $("#start-date-B6-3").trigger("validate");
                $("#end-date-B6-4").trigger("validate");
                if (self.currentItem().aggrFrameCode() == null || self.currentItem().optionalAggrName() == null) {
                    return;
                }
                let checkCode = _.filter(self.optionalList(), function(obj) {
                    return obj.aggrFrameCode == self.currentItem().aggrFrameCode();
                });
                if (checkCode.length > 0 && self.mode() == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_3" });
                } else {
                    if (self.status() == model.ExecutionStatus.Processing) {
                        let executionDto = {
                            aggrFrameCode: self.currentItem().aggrFrameCode(),
                            executionAtr: 1,
                            executionStatus: 2,
                            presenceOfError: 1,
                            startDateTime: moment(self.currentItem().startDate()).utc(),
                            endDateTime: moment(self.currentItem().endDate()).utc(),
                        };
                        let aggrPeriodDto = {
                            aggrFrameCode: self.currentItem().aggrFrameCode(),
                            optionalAggrName: self.currentItem().optionalAggrName(),
                            startDate: moment(self.currentItem().startDate()).utc(),
                            endDate: moment(self.currentItem().endDate()).utc(),
                            peopleNo: self.peopleNo()

                        }
                        let targetDto = {
                            executionEmpId: ko.observable(''),
                            employeeId: [],
                            state: 0
                        }
                        let addAggrPeriodCommand = {
                            reintegration:self.reintegration(),//EA4209
                            mode: self.mode(),
                            aggrPeriodCommand: aggrPeriodDto,
                            targetCommand: targetDto,
                            executionCommand: executionDto
                        }

                        nts.uk.ui.windows.setShared("KFP001_DATAE", addAggrPeriodCommand);

                        let period = {
                            startDate: self.currentItem().startDate(),
                            endDate: self.currentItem().endDate()
                        }
                        nts.uk.ui.windows.setShared("KFP001_PERIOD", period);
                        let data  = {anyPeriodAggrLogId: self.aggrId , startDateTime: self.currentItem().startDate(), endDateTime:self.currentItem().endDate()}
                        nts.uk.ui.windows.setShared("KFP001_DATAD", data);
                        nts.uk.ui.windows.sub.modal('/view/kfp/001/e/index.xhtml').onClosed(() => {
                            self.start();
                        });
                    } else {
                        $("#wizard").ntsWizard("next").done(function() {
                            self.cScreenmodel.start();
                        });
                    }
                }

            }

            opendScreenF() {
                let self = this;
                let params = {
                    code: self.currentItem().aggrFrameCode(),
                    name: self.currentItem().optionalAggrName(),
                    start: self.currentItem().startDate(),
                    end: self.currentItem().endDate(),
                    logId: self.aggrId,
                    dispTargetPeopleNum: self.peopleCount()

                }
                nts.uk.ui.windows.setShared("Kfp001fParams", params);
                nts.uk.ui.windows.sub.modal('/view/kfp/001/f/index.xhtml');
            }

            targetEmployee() {
                let self = this;
                // Call to F
            }
        }

        export module model {

            export interface IOptionalAggrPeriodDto {
                aggrFrameCode?: string;
                optionalAggrName?: string;
                startDate?: number;
                endDate?: number;

            }
            export class OptionalAggrPeriodDto {
                aggrFrameCode: KnockoutObservable<string>;
                optionalAggrName: KnockoutObservable<string>;
                startDate: KnockoutObservable<number>;
                endDate: KnockoutObservable<number>;
                constructor(param: IOptionalAggrPeriodDto) {
                    this.aggrFrameCode = ko.observable(param.aggrFrameCode || null);
                    this.optionalAggrName = ko.observable(param.optionalAggrName || null);
                    this.startDate = ko.observable(param.startDate || null);
                    this.endDate = ko.observable(param.endDate || null);
                }
            }

            export interface IAggrPeriodExcutionDto {
                executionEmpId?: string;
                aggrFrameCode?: string;
                aggrId?: string;
                startDateTime?: number;
                endDateTime?: number;
                executionAtr?: number;
                executionStatus?: number;
                presenceOfError?: number;

            }
            export class AggrPeriodExcutionDto {
                executionEmpId: KnockoutObservable<string>;
                aggrFrameCode: KnockoutObservable<string>;
                aggrId: KnockoutObservable<string>;
                startDateTime: KnockoutObservable<number>;
                endDateTime: KnockoutObservable<number>;
                executionAtr: KnockoutObservable<number>;
                executionStatus: KnockoutObservable<number>;
                presenceOfError: KnockoutObservable<number>;
                constructor(param: IAggrPeriodExcutionDto) {
                    this.executionEmpId = ko.observable(param.aggrFrameCode || null);
                    this.aggrFrameCode = ko.observable(param.aggrFrameCode || null);
                    this.aggrId = ko.observable(param.aggrId || null);
                    this.startDateTime = ko.observable(param.startDateTime || null);
                    this.endDateTime = ko.observable(param.endDateTime || null);
                    this.executionAtr = ko.observable(param.executionAtr || null);
                    this.executionStatus = ko.observable(param.executionStatus || null);
                    this.presenceOfError = ko.observable(param.presenceOfError || null);
                }
            }


            export enum ExecutionStatus {
                // 0:完了
                Done = 0,
                // 1:完了（エラーあり）
                DoneWitdError = 1,
                // 2:処理中
                Processing = 2,
                // 3:実行中止
                StopExecution = 3,
                // 4:中断開始
                StartOfInterruption = 4,
                // 5:中断終了
                EndOfInterruption = 5
            }
            export enum PresenceOfError {

                // 0:エラーあり
                Error = 0,
                // 1:エラーなし
                NoError = 1

            }
        }
    }
}
