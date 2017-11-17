module nts.uk.at.view.kdw001.j {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.at.view.kdw001.share.model;
    export module viewmodel {
        export class ScreenModel {
            params: shareModel.executionProcessingCommand = new shareModel.executionProcessingCommand();
            closureID: any = __viewContext.transferred.value.closureID;
            //Declare import cScreenmodel, dScreenmodel
            cScreenmodel: any;
            dScreenmodel: any;

            //Declare for grid list
            items: KnockoutObservableArray<ItemModel>;
            //columns: KnockoutObservableArray<NtsGridListColumn>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            //Declare wizard properties
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;



            constructor() {
                var self = this;
                self.params.setParamsScreenA({ closure: self.closureID });

                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kdw001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kdw001.d.viewmodel.ScreenModel();

                //Init for grid list
                this.items = ko.observableArray([]);

                this.columns2 = ko.observableArray([
                    { headerText: getText('KDW001_32'), key: 'caseSpecExeContentID', width: 70, hidden: false },
                    { headerText: getText('KDW001_85'), key: 'useCaseName', width: 200 }
                ]);

                this.currentCode = ko.observable();

                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);

                //Get activeStep value from a screen or c screen
                __viewContext.transferred.ifPresent(data => {
                    self.activeStep(data.activeStep);
                });
                //self.stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });
            }

            start() {
                let self = this;
                service.getAllCaseSpecExeContent().done(function(data) {
                    let items = _.map(data, item => {
                        return new CaseSpecExeContent(item);
                    });
                    self.items(items);

                    //Set first select
                    self.currentCode(self.items()[0].caseSpecExeContentID);
                });
            }

            opendScreenD() {
                //nts.uk.request.jump("/view/kdw/001/j/index.xhtml", { "activeStep": 2, "screenName": "J" });
                let self = this;
                //                self.activeStep(2);
                self.params.setParamsScreenJ({
                    caseSpecExeContentID: self.currentCode()
                });
                self.dScreenmodel.periodDate(getText('KDW001_34', [self.params.periodStartDate, self.params.periodEndDate]));
                self.dScreenmodel.numberEmployee(getText('KDW001_21', [self.params.lstEmployeeID.length]));
                service.getCaseSpecExeContentById(self.currentCode()).done((data) => {
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        if (!nts.uk.util.isNullOrUndefined(data.dailyCreationSetInfo)) {
                            var dailyCreatedText = getText('KDW001_9');
                            self.dScreenmodel.dailyCreated(dailyCreatedText);
                            self.dScreenmodel.dailyCreatedVisible(true);
                            var partResetClassification = data.dailyCreationSetInfo.partResetClassification;
                            if (!nts.uk.util.isNullOrUndefined(partResetClassification)) {
                                if (partResetClassification.calculationClassificationResetting) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_58'));
                                }
                                if (partResetClassification.specificDateClassificationResetting) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_60'));
                                }
                                if (partResetClassification.refNumberFingerCheck) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_62'));
                                }
                                if (partResetClassification.masterReconfiguration) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_59'));
                                }
                                if (partResetClassification.resetTimeChildOrNurseCare) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_61'));
                                }
                                if (partResetClassification.closedHolidays) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_63'));
                                }
                                if (partResetClassification.resettingWorkingHours) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_71'));
                                }
                                if (partResetClassification.resetTimeAssignment) {
                                    self.dScreenmodel.dailyCreated(dailyCreatedText += '<br>' + '→' + getText('KDW001_72'));
                                }
                            }


                        } else {
                            self.dScreenmodel.dailyCreated('');
                            self.dScreenmodel.dailyCreatedVisible(false);

                        }


                        if (!nts.uk.util.isNullOrUndefined(data.dailyCalSetInfo)) {
                            var dailyCalText = getText('KDW001_10');
                            self.dScreenmodel.dailyCal(dailyCalText);
                            self.dScreenmodel.dailyCalVisible(true);
                            if (data.dailyCalSetInfo.executionType == 1) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_575" }).ifYes(() => {
                                    if (!nts.uk.util.isNullOrUndefined(data.reflectApprovalSetInfo)) {
                                        var approvalResultText = getText('KDW001_11');
                                        self.dScreenmodel.approvalResult(approvalResultText);
                                        self.dScreenmodel.approvalVisible(true);
                                        if (data.reflectApprovalSetInfo.executionType == 1) {
                                            self.dScreenmodel.approvalResult(approvalResultText += '(' + getText('KDW001_67') + ')');
                                        }
                                    } else {
                                        self.dScreenmodel.approvalResult('');
                                        self.dScreenmodel.approvalVisible(false);
                                    }

                                    if (!nts.uk.util.isNullOrUndefined(data.monlyAggregationSetInfo)) {
                                        var monthCountText = getText('KDW001_12');
                                        self.dScreenmodel.monthCount(monthCountText);
                                        self.dScreenmodel.monthCountVisible(true);
                                        if (data.monlyAggregationSetInfo.executionType == 1) {
                                            self.dScreenmodel.monthCount(monthCountText += '(' + getText('KDW001_70') + ')');
                                        }

                                    } else {
                                        self.dScreenmodel.monthCount('');
                                        self.dScreenmodel.monthCountVisible(false);
                                    }

                                    $("#wizard").ntsWizard("next");

                                    self.dScreenmodel.dailyCal(dailyCalText += '(' + getText('KDW001_65') + ')');
                                });

                            }
                        } else {
                            self.dScreenmodel.dailyCal('');
                            self.dScreenmodel.dailyCalVisible(false);

                            if (!nts.uk.util.isNullOrUndefined(data.reflectApprovalSetInfo)) {
                                var approvalResultText = getText('KDW001_11');
                                self.dScreenmodel.approvalResult(approvalResultText);
                                self.dScreenmodel.approvalVisible(true);
                                if (data.reflectApprovalSetInfo.executionType == 1) {
                                    self.dScreenmodel.approvalResult(approvalResultText += '(' + getText('KDW001_67') + ')');
                                }
                            } else {
                                self.dScreenmodel.approvalResult('');
                                self.dScreenmodel.approvalVisible(false);
                            }

                            if (!nts.uk.util.isNullOrUndefined(data.monlyAggregationSetInfo)) {
                                var monthCountText = getText('KDW001_12');
                                self.dScreenmodel.monthCount(monthCountText);
                                self.dScreenmodel.monthCountVisible(true);
                                if (data.monlyAggregationSetInfo.executionType == 1) {
                                    self.dScreenmodel.monthCount(monthCountText += '(' + getText('KDW001_70') + ')');
                                }

                            } else {
                                self.dScreenmodel.monthCount('');
                                self.dScreenmodel.monthCountVisible(false);
                            }

                            $("#wizard").ntsWizard("next");


                        }


                    }

                }));

            }

            opendScreenC() {
                //nts.uk.request.jump("/view/kdw/001/j/index.xhtml", { "activeStep": 0 });
                //                let self = this;
                //                self.activeStep(0);
                $("#wizard").ntsWizard("prev");
            }

            navigateView() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
        }

        //Define CaseSpecExeContent 
        export class CaseSpecExeContent {
            caseSpecExeContentID: string;
            orderNumber: number;
            useCaseName: string;

            constructor(x: ICaseSpecExeContent) {
                let self = this;
                self.caseSpecExeContentID = x.caseSpecExeContentID;
                self.orderNumber = x.orderNumber;
                self.useCaseName = x.useCaseName;
            }
        }

        export interface ICaseSpecExeContent {
            caseSpecExeContentID: string;
            orderNumber: number;
            useCaseName: string;
        }
    }

}
