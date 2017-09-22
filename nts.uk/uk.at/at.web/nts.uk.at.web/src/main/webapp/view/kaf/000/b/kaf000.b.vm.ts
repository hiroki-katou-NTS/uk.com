module nts.uk.at.view.kaf000.b.viewmodel {
    
    export abstract class ScreenModel {

        // Metadata
        appID: KnockoutObservable<string>;
        appType: KnockoutObservable<number>;
        
        /**
         * List
         */
        //listPhase
        listPhase: KnockoutObservableArray<model.AppApprovalPhase>;
        //listPhaseID
        listPhaseID: Array<String>;
        //list appID 
        listReasonByAppID: KnockoutObservableArray<String>;


        /**
         * value obj 
         */
        listReasonToApprover: KnockoutObservable<String>;
        reasonApp: KnockoutObservable<String>;

        dataApplication: KnockoutObservable<model.OutputGetAllDataApp>;

        //application
        objApp: KnockoutObservable<model.ApplicationDto>;
        inputDetail: KnockoutObservable<model.InputGetDetailCheck>;
        outputDetail: KnockoutObservable<model.DetailedScreenPreBootModeOutput>;

        //obj input
        inputMessageDeadline: KnockoutObservable<model.InputMessageDeadline>;
        //obj output message deadline
        outputMessageDeadline: KnockoutObservable<model.OutputMessageDeadline>;

        constructor(appType: number) {
            var self = this;
            // Metadata
            self.appID = __viewContext.transferred.value.appID;
            self.appType = ko.observable(appType);
            
            /**
             * List
             */

            self.listPhase = ko.observableArray([]);
            self.listReasonByAppID = ko.observableArray([]);
            /**
             * value obj
             */
            self.listReasonToApprover = ko.observable('');
            self.reasonApp = ko.observable('');
            self.dataApplication = ko.observable(null);
            //application
            self.objApp = ko.observable(null);
            self.inputDetail = ko.observable(null);
            self.outputDetail = ko.observable(null);

            //obj input get message deadline 
            self.inputMessageDeadline = ko.observable(new model.InputMessageDeadline("000000000000-0005", null, 1, null));
            //obj input get message deadline 
            self.outputMessageDeadline = ko.observable(null);
        }
                
        abstract update(): any;

        start(): JQueryPromise<any> {

            let self = this;
            var dfd = $.Deferred();
            var dfdMessageDeadline = self.getMessageDeadline(self.inputMessageDeadline());
            var dfdAllReasonByAppID = self.getAllReasonByAppID("000");
            var dfdAllDataByAppID = self.getAllDataByAppID("000");


            $.when(dfdAllReasonByAppID, dfdAllDataByAppID).done((dfdAllReasonByAppIDData, dfdAllDataByAppIDData) => {
                //self.listReasonByAppID(data);
                //self.getDetailCheck(self.inputDetail());
                dfd.resolve();
            });
            return dfd.promise();
        }

        // getMessageDeadline
        getMessageDeadline(inputMessageDeadline) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getMessageDeadline(inputMessageDeadline).done(function(data) {
                self.outputMessageDeadline(data);
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        //getAll data by App ID
        getAllDataByAppID(appID) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllDataByAppID(appID).done(function(data) {
                let temp = data.listOutputPhaseAndFrame;
                _.forEach(temp, function(phase) {
                    let listApproveAcceptedForView = [];
                    //con cai clone la de clone ra 1 array moi, tranh bi anh huong array goc(tạo ra 1 bản sao)
                    // _.sortBy sắp xếp theo dispOrder,
                    let frameTemp = _.sortBy(_.clone(phase.listApprovalFrameDto), ['dispOrder']);
                    for (var i = 0; i < frameTemp.length; i++) {
                        let frame = frameTemp[i];
                        let sameOrder = _.filter(phase.listApproveAcceptedDto, function(f) {
                            return frame["dispOrder"] === f["dispOrder"];
                        });
                        let approverSID = "";
                        _.forEach(sameOrder, function(so) {
                            approverSID += (nts.uk.util.isNullOrEmpty(approverSID) ? "" : ", ") + so["approverSID"];
                        });
                        frame["approverSID2"] = approverSID;
                        listApproveAcceptedForView.push(frame);
                    }
                    phase["listApproveAcceptedForView"] = listApproveAcceptedForView;
                });
                data.listOutputPhaseAndFrame = temp;
                self.dataApplication(data);
                //get obj application
                self.objApp(self.dataApplication().applicationDto);
                self.inputDetail(new model.InputGetDetailCheck(self.objApp(), new Date('2022-01-01 00:00:00')));
                dfd.resolve(data);
            });
            return dfd.promise();
        }
        //get all reason by app ID
        getAllReasonByAppID(appID) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllReasonByAppID(appID).done(function(data) {
                self.listReasonByAppID(data);
                self.reasonApp(self.listReasonByAppID()[0].toString());
                for (var i = 1; i < self.listReasonByAppID().length; i++) {
                    self.listReasonToApprover(
                        self.listReasonToApprover().toString() + self.listReasonByAppID()[i].toString() + "\n"
                    );
                }

                dfd.resolve(data);
            });
            return dfd.promise();
        }
        //get detail check 
        getDetailCheck(inputGetDetail) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getDetailCheck(inputGetDetail).done(function(data) {
                //
                dfd.resolve(data);
            });
            return dfd.promise();
        }
    }

    export module model {
        //class OutputGetAllDataApp
        export class OutputGetAllDataApp {
            applicationDto: ApplicationDto;
            listOutputPhaseAndFrame: Array<OutputPhaseAndFrame>;
            constructor(applicationDto: ApplicationDto,
                listOutputPhaseAndFrame: Array<OutputPhaseAndFrame>) {
                this.applicationDto = applicationDto;
                this.listOutputPhaseAndFrame = listOutputPhaseAndFrame;
            }
        }//end class OutputGetAllDataApp

        //class Application 
        export class ApplicationDto {
            applicationID: String;
            prePostAtr: number;
            inputDate: Date;
            enteredPersonSID: String;
            reversionReason: String;
            applicationDate: Date;
            applicationReason: String;
            applicationType: number;
            applicantSID: String;
            reflectPlanScheReason: number;
            reflectPlanTime: Date;
            reflectPlanState: number;
            reflectPlanEnforce: number;
            reflectPerScheReason: number;
            reflectPerTime: Date;
            reflectPerState: number;
            reflectPerEnforce: number;
            startDate: Date;
            endDate: Date;
            constructor(
                applicationID: String,
                prePostAtr: number,
                inputDate: Date,
                enteredPersonSID: String,
                reversionReason: String,
                applicationDate: Date,
                applicationReason: String,
                applicationType: number,
                applicantSID: String,
                reflectPlanScheReason: number,
                reflectPlanTime: Date,
                reflectPlanState: number,
                reflectPlanEnforce: number,
                reflectPerScheReason: number,
                reflectPerTime: Date,
                reflectPerState: number,
                reflectPerEnforce: number,
                startDate: Date,
                endDate: Date) {
                this.applicationID = applicationID;
                this.prePostAtr = prePostAtr;
                this.inputDate = inputDate;
                this.enteredPersonSID = enteredPersonSID;
                this.reversionReason = reversionReason;
                this.applicationDate = applicationDate;
                this.applicationReason = applicationReason;
                this.applicationType = applicationType;
                this.applicantSID = applicantSID;
                this.reflectPlanScheReason = reflectPlanScheReason;
                this.reflectPlanTime = reflectPlanTime;
                this.reflectPlanState = reflectPlanState;
                this.reflectPlanEnforce = reflectPlanEnforce;
                this.reflectPerScheReason = reflectPerScheReason;
                this.reflectPerTime = reflectPerTime;
                this.reflectPerState = reflectPerState;
                this.reflectPerEnforce = reflectPerEnforce;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }//end class Application


        //class OutputPhaseAndFrame 
        export class OutputPhaseAndFrame {
            appApprovalPhase: AppApprovalPhase;
            listApprovalFrame: Array<ApprovalFrame>;
            listApproveAccepted: Array<ApproveAccepted>;
            constructor(
                appApprovalPhase: AppApprovalPhase,
                listApprovalFrame: Array<ApprovalFrame>,
                listApproveAccepted: Array<ApproveAccepted>) {
                this.appApprovalPhase = appApprovalPhase;
                this.listApprovalFrame = listApprovalFrame;
                this.listApproveAccepted = listApproveAccepted;
            }
        }//end class OutputPhaseAndFrame


        // class AppApprovalPhase
        export class AppApprovalPhase {
            appID: KnockoutObservable<String>;
            phaseID: KnockoutObservable<String>;
            approvalForm: KnockoutObservable<number>;
            dispOrder: KnockoutObservable<number>;
            approvalATR: KnockoutObservable<number>;
            constructor(appID: String, phaseID: String, approvalForm: number, dispOrder: number, approvalATR: number) {
                this.appID = ko.observable(appID);
                this.phaseID = ko.observable(phaseID);
                this.approvalForm = ko.observable(approvalForm);
                this.dispOrder = ko.observable(dispOrder);
                this.approvalATR = ko.observable(approvalATR);
            }
        }

        // class ApprovalFrame
        export class ApprovalFrame {
            phaseID: KnockoutObservable<String>;
            dispOrder: KnockoutObservable<number>;
            approverSID: KnockoutObservable<String>;
            approvalATR: KnockoutObservable<number>;
            confirmATR: KnockoutObservable<number>;
            approvalDate: KnockoutObservable<String>;
            reason: KnockoutObservable<String>;
            representerSID: KnockoutObservable<String>;
            constructor(phaseID: String, dispOrder: number, approverSID: String, approvalATR: number,
                confirmATR: number, approvalDate: String, reason: String, representerSID: String) {
                this.phaseID = ko.observable(phaseID);
                this.dispOrder = ko.observable(dispOrder);
                this.approverSID = ko.observable(approverSID);
                this.approvalATR = ko.observable(approvalATR);
                this.confirmATR = ko.observable(confirmATR);
                this.approvalDate = ko.observable(approvalDate);
                this.reason = ko.observable(reason);
                this.representerSID = ko.observable(representerSID);
            }
        }//end class frame  

        //class ApproveAccepted
        export class ApproveAccepted {

        }//end class ApproveAccepted

        //class InputGetDetailCheck 
        export class InputGetDetailCheck {
            applicationDto: ApplicationDto;
            baseDate: Date;
            constructor(applicationDto: ApplicationDto,
                baseDate: Date) {
                this.applicationDto = applicationDto;
                this.baseDate = baseDate;

            }
        }//end class InputGetDetailCheck


        //class DetailedScreenPreBootModeOutput
        export class DetailedScreenPreBootModeOutput {
            user: number;
            reflectPlanState: number;
            authorizableFlags: boolean;
            approvalATR: number;
            alternateExpiration: boolean;
            constructor(user: number,
                reflectPlanState: number,
                authorizableFlags: boolean,
                approvalATR: number,
                alternateExpiration: boolean) {
                this.user = user;
                this.reflectPlanState = reflectPlanState;
                this.authorizableFlags = authorizableFlags;
                this.approvalATR = approvalATR;
                this.alternateExpiration = alternateExpiration;
            }
        }//end class DetailedScreenPreBootModeOutput


        //class InputMessageDeadline
        export class InputMessageDeadline {
            companyID: String;
            workplaceID: String;
            appType: number;
            appDate: Date;
            constructor(companyID: String, workplaceID: String, appType: number, appDate: Date) {
                this.companyID = companyID;
                this.workplaceID = workplaceID;
                this.appType = appType;
                this.appDate = appDate;
            }

        }//end class InputMessageDeadline

        //class outputMessageDeadline
        export class OutputMessageDeadline {
            message: String;
            deadline: String;
            constructor(message: String, deadline: String) {
                this.message = message;
                this.deadline = deadline;
            }
        }// end class outputMessageDeadline
    }
}