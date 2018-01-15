module nts.uk.at.view.kdw001.j {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.at.view.kdw001.share.model;
    export module viewmodel {
        export class ScreenModel {

            params: shareModel.executionProcessingCommand = new shareModel.executionProcessingCommand();
          //  closureID: any = __viewContext.transferred.value.closureID;
            //Declare import cScreenmodel, dScreenmodel
            cScreenmodel: any;
            dScreenmodel: any;

            //model bind at right screen data-bind:with
            currentItem: KnockoutObservable<CaseSpecExeContent>;

            //Declare for grid list
            items: KnockoutObservableArray<model.CaseSpecExeContentJS>;
            //Declare for binding to right screen side
            items2: KnockoutObservableArray<model.CaseSpecExeContent>;

            //columns: KnockoutObservableArray<NtsGridListColumn>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            //Declare wizard properties
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;
            warnVisible: KnockoutObservable<boolean>;



            constructor() {
                var self = this;
                self.params.setParamsScreenA({ closure: 1 });

                self.currentItem = ko.observable();
                self.warnVisible = ko.observable(false);

                //Init for grid list
                self.items = ko.observableArray([]);
                self.items2 = ko.observableArray([]);

                self.columns2 = ko.observableArray([
                    { headerText: getText('KDW001_32'), key: 'caseSpecExeContentID', width: 1, hidden: true },
                    { headerText: getText('KDW001_85'), key: 'useCaseName', width: 200 }
                ]);

                self.currentCode = ko.observable();

                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);

                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kdw001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kdw001.d.viewmodel.ScreenModel();

                self.currentCode.subscribe(newValue => {

                    let itemSelection = _.find(self.items2(), function(item: model.CaseSpecExeContent) {
                        return item.caseSpecExeContentID() == newValue;
                    });

                    //Set data cho view model data-binding: with
                    self.currentItem(itemSelection);
                        //*2j
                    if ((!nts.uk.util.isNullOrUndefined(itemSelection.dailyCreationSetInfo()) && itemSelection.dailyCreationSetInfo().executionType == 1)||(!nts.uk.util.isNullOrUndefined(itemSelection.dailyCalSetInfo()) && itemSelection.dailyCalSetInfo().executionType == 1)||(!nts.uk.util.isNullOrUndefined(itemSelection.monlyAggregationSetInfo()) && itemSelection.monlyAggregationSetInfo().executionType == 1)) {
                        self.warnVisible(true);
                    }else{
                        self.warnVisible(false);
                    }

                });
            }

            start() {
                let self = this;
                service.getAllCaseSpecExeContent().done(function(data) {

                    //List ObjectJS
                    let items = _.map(data, item => {
                        return new model.CaseSpecExeContentJS(item);
                    });
                    self.items(items);


                    //List Object Knockout
                    let items2 = _.map(data, item => {
                        return new model.CaseSpecExeContent(item);
                    });
                    self.items2(items2);

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
                                    self.dScreenmodel.dailyCal(dailyCalText += '(' + getText('KDW001_65') + ')');
                                    $("#wizard").ntsWizard("next");
                                });

                            }else{
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

                }); 
                
            }


            opendScreenC() {
                $("#wizard").ntsWizard("prev");
            }

            navigateView() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
        }

        //module model
        export module model {

            //Define CaseSpecExeContent JS
            export class CaseSpecExeContentJS {
                caseSpecExeContentID: string;
                orderNumber: number;
                useCaseName: string;

                reflectApprovalSetInfo: SetInforReflAprResult;
                dailyCreationSetInfo: SettingInforForDailyCreation;
                dailyCalSetInfo: CalExeSettingInfor;
                monlyAggregationSetInfo: CalExeSettingInfor;

                constructor(x: ICaseSpecExeContent) {
                    let self = this;
                    self.caseSpecExeContentID = x.caseSpecExeContentID;
                    self.orderNumber = x.orderNumber;
                    self.useCaseName = x.useCaseName;

                    self.reflectApprovalSetInfo = x.reflectApprovalSetInfo;
                    self.dailyCreationSetInfo = x.dailyCreationSetInfo;
                    self.dailyCalSetInfo = x.dailyCalSetInfo;
                    self.monlyAggregationSetInfo = x.monlyAggregationSetInfo;
                }
            }

            //Define CaseSpecExeContent knockout
            export class CaseSpecExeContent {
                caseSpecExeContentID: KnockoutObservable<string>;
                orderNumber: KnockoutObservable<number>;
                useCaseName: KnockoutObservable<string>;

                reflectApprovalSetInfo: KnockoutObservable<SetInforReflAprResult>;
                dailyCreationSetInfo: KnockoutObservable<SettingInforForDailyCreation>;
                dailyCalSetInfo: KnockoutObservable<CalExeSettingInfor>;
                monlyAggregationSetInfo: KnockoutObservable<CalExeSettingInfor>;

                constructor(x: ICaseSpecExeContent) {
                    let self = this;
                    if (x) {
                        self.caseSpecExeContentID = ko.observable(x.caseSpecExeContentID);
                        self.orderNumber = ko.observable(x.orderNumber);
                        self.useCaseName = ko.observable(x.useCaseName);

                        self.reflectApprovalSetInfo = ko.observable(x.reflectApprovalSetInfo);
                        self.dailyCreationSetInfo = ko.observable(x.dailyCreationSetInfo);
                        self.dailyCalSetInfo = ko.observable(x.dailyCalSetInfo);
                        self.monlyAggregationSetInfo = ko.observable(x.monlyAggregationSetInfo);
                    } else {
                        self.caseSpecExeContentID = ko.observable("");
                        self.orderNumber = ko.observable(0);
                        self.useCaseName = ko.observable("");

                        self.reflectApprovalSetInfo = ko.observable(null);
                        self.dailyCreationSetInfo = ko.observable(null);
                        self.dailyCalSetInfo = ko.observable(null);
                        self.monlyAggregationSetInfo = ko.observable(null);
                    }
                }
            }

            export interface ICaseSpecExeContent {
                caseSpecExeContentID: string;
                orderNumber: number;
                useCaseName: string;

                reflectApprovalSetInfo: SetInforReflAprResult;
                dailyCreationSetInfo: SettingInforForDailyCreation;
                dailyCalSetInfo: CalExeSettingInfor;
                monlyAggregationSetInfo: CalExeSettingInfor;
            }

            /**
             * class SetInforReflAprResult 
             */
            export class SetInforReflAprResult {
                executionType: number;
                executionTypeName: string;
                forciblyReflect: boolean;

                constructor(x: any) {
                    let self = this;
                    if (x) {
                        self.executionType = x.executionType;
                        self.executionTypeName = x.executionTypeName;
                        self.forciblyReflect = x.forciblyReflect;
                    } else {
                        self.executionType = 0;
                        self.executionTypeName = "";
                        self.forciblyReflect = false;
                    }
                }
            }//end classSetInforReflAprResult

            /**
             * class SettingInforForDailyCreation
             */
            export class SettingInforForDailyCreation {
                executionType: number;
                executionTypeName: string;
                creationType: number;
                partResetClassification: PartResetClassification;

                constructor(x: any) {
                    let self = this;
                    if (x) {
                        self.executionType = x.executionType;
                        self.executionTypeName = x.executionTypeName;
                        self.creationType = x.creationType;
                        self.partResetClassification = x.partResetClassification;
                    } else {
                        self.executionType = 0;
                        self.executionTypeName = "";
                        self.creationType = 0;
                        self.partResetClassification = null;
                    }
                }
            }//end class SettingInforForDailyCreation

            /**
             * class CalExeSettingInfor
             */
            export class CalExeSettingInfor {
                executionContent: number;
                executionType: number;
                executionTypeName: string;
                calExecutionSetInfoID: string;
                caseSpecExeContentID: string;

                constructor(x: any) {
                    let self = this;
                    if (x) {
                        self.executionContent = x.executionContent;
                        self.executionType = x.executionType;
                        self.executionTypeName = x.executionTypeName;
                        self.calExecutionSetInfoID = x.calExecutionSetInfoID;
                        self.caseSpecExeContentID = x.caseSpecExeContentID;
                    } else {
                        self.executionContent = 0;
                        self.executionType = 0;
                        self.executionTypeName = "";
                        self.calExecutionSetInfoID = "";
                        self.caseSpecExeContentID = "";
                    }
                }//end class ExecutionTime
            }//end class CalExeSettingInfor

            /**
             * class PartResetClassification
             */
            export class PartResetClassification {
                //マスタ再設定
                masterReconfiguration: boolean;
                //休業再設定
                closedHolidays: boolean;
                // 就業時間帯再設定
                resettingWorkingHours: boolean;
                // 打刻のみ再度反映
                reflectsTheNumberOfFingerprintChecks: boolean;
                // 特定日区分再設定
                specificDateClassificationResetting: boolean;
                // 申し送り時間再設定
                resetTimeAssignment: boolean;
                // 育児・介護短時間再設定
                resetTimeChildOrNurseCare: boolean;
                // 計算区分再設定
                calculationClassificationResetting: boolean;
                constructor(
                    masterReconfiguration: boolean,
                    closedHolidays: boolean,
                    resettingWorkingHours: boolean,
                    reflectsTheNumberOfFingerprintChecks: boolean,
                    specificDateClassificationResetting: boolean,
                    resetTimeAssignment: boolean,
                    resetTimeChildOrNurseCare: boolean,
                    calculationClassificationResetting: boolean) {

                    this.masterReconfiguration = masterReconfiguration;
                    this.closedHolidays = closedHolidays;
                    this.resettingWorkingHours = resettingWorkingHours;
                    this.reflectsTheNumberOfFingerprintChecks = reflectsTheNumberOfFingerprintChecks;
                    this.specificDateClassificationResetting = specificDateClassificationResetting;
                    this.resetTimeAssignment = resetTimeAssignment;
                    this.resetTimeChildOrNurseCare = resetTimeChildOrNurseCare;
                    this.calculationClassificationResetting = calculationClassificationResetting;

                }
            }//end class PartResetClassification

        }//end module model

    }

}
