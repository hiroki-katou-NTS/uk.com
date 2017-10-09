module nts.uk.at.view.kaf009.share {
    export module common {
        /**
         * Setting Data from Server 
         */
        export class CommonSetting {
            employeeName: string;
            goBackSettingDto: GoBackDirectSetting;
            listReasonDto: Array<ReasonDto>;
            constructor(employeeName: string, goBackSettingDto: GoBackDirectSetting, listReasonDto: Array<ReasonDto>) {
                var self = this;
                self.employeeName = employeeName;
                self.goBackSettingDto = goBackSettingDto;
                self.listReasonDto = listReasonDto;
            }
        }
        /**
         * 直行直帰申請共通設定
         */
        export class GoBackDirectSetting {
            workChangeFlg: number;
            workChangeTimeAtr: number;
            perfomanceDisplayAtr: number;
            contraditionCheckAtr: number;
            workType: number;
            lateLeaveEarlySettingAtr: number;
            commentContent1: string;
            commentFontWeight1: number;
            commentFontColor1: string;
            commentContent2: string;
            commentFontWeight2: number;
            commentFontColor2: string;
            constructor(workChangeFlg: number, workChangeTimeAtr: number, perfomanceDisplayAtr: number,
                contraditionCheckAtr: number, workType: number, lateLeaveEarlySettingAtr: number, commentContent1: string,
                commentFontWeight1: number, commentFontColor1: string, commentContent2: string, commentFontWeight2: number,
                commentFontColor2: string) {
                var self = this;
                self.workChangeFlg = workChangeFlg;
                self.workChangeTimeAtr = workChangeTimeAtr;
                self.perfomanceDisplayAtr = perfomanceDisplayAtr;
                self.contraditionCheckAtr = contraditionCheckAtr;
                self.workType = workType;
                self.lateLeaveEarlySettingAtr = lateLeaveEarlySettingAtr;
                self.commentContent1 = commentContent1;
                self.commentFontWeight1 = commentFontWeight1;
                self.commentFontColor1 = commentFontColor1;
                self.commentContent2 = commentContent2;
                self.commentFontWeight2 = commentFontWeight2;
                self.commentFontColor2 = commentFontColor2;
            }
        }
        /**
         * 理由
         */
        export class ReasonDto {
            companyId: string;
            appType: number;
            reasonID: string;
            displayOrder: number;
            reasonTemp: string;
            constructor(companyId: string, appType: number, reasonID: string, displayOrder: number, reasonTemp: string) {
                var self = this;
                self.companyId = companyId;
                self.appType = appType;
                self.reasonID = reasonID;
                self.displayOrder = displayOrder;
                self.reasonTemp = reasonTemp;
            }

        };
        /**
         * 
         */
        export class ComboReason {
            reasonCode: number;
            reasonName: string;
            reasonId: string;

            constructor(reasonCode: number, reasonName: string, reasonId: string) {
                this.reasonCode = reasonCode;
                this.reasonName = reasonName;
                this.reasonId = reasonId;
            }
        }

        /**
         * 
         * 直行直帰申請
         */
        export class GoBackDirectData {
            appID: string;
            workTypeCD: string;
            siftCD: string;
            workChangeAtr: number;
            goWorkAtr1: number;
            backHomeAtr1: number;
            workTimeStart1: number;
            workTimeEnd1: number;
            workLocationCD1: string;
            goWorkAtr2: number;
            backHomeAtr2: number;
            workTimeStart2: number;
            workTimeEnd2: number;
            workLocationCD2: string;
            constructor(appID: string,
                workTypeCD: string,
                siftCD: string,
                workChangeAtr: number,
                goWorkAtr1: number,
                backHomeAtr1: number,
                workTimeStart1: number,
                workTimeEnd1: number,
                workLocationCD1: string,
                goWorkAtr2: number,
                backHomeAtr2: number,
                workTimeStart2: number,
                workTimeEnd2: number,
                workLocationCD2: string) {
                this.appID = appID;
                this.siftCD = siftCD;
                this.workChangeAtr = workChangeAtr;
                this.goWorkAtr1 = goWorkAtr1;
                this.backHomeAtr1 = backHomeAtr1;
                this.workTimeStart1 = workTimeStart1;
                this.workTimeEnd1 = workTimeEnd1;
                this.workLocationCD1 = workLocationCD1;
                this.goWorkAtr2 = goWorkAtr2;
                this.backHomeAtr2 = backHomeAtr2;
                this.workTimeStart2 = workTimeStart2;
                this.workTimeEnd2 = workTimeEnd2;
                this.workLocationCD2 = workLocationCD2;
            }
        }
        /**
         * 
         */
        export interface IWorkLocation {
            workLocationCode: string;
            workLocationName: string;
        }
        /**
         * Application detail
         */
        export class ApplicationCommand {
            applicationID: string;
            appReasonID: string;
            prePostAtr: number;
            inputDate: string;
            enteredPersonSID: string;
            reversionReason: string;
            applicationDate: string;
            applicationReason: string;
            applicationType: number;
            applicantSID: string;
            reflectPlanScheReason: number;
            reflectPlanTime: string;
            reflectPlanState: number;
            reflectPlanEnforce: number;
            reflectPerScheReason: number;
            reflectPerTime: string;
            reflectPerState: number;
            reflectPerEnforce: number;
            startDate: string;
            endDate: string;
            listPhase: any;
            constructor(
                appReasonID: string,
                prePostAtr: number,
                inputDate: string,
                enteredPersonSID: string,
                reversionReason: string,
                applicationDate: string,
                applicationReason: string,
                applicantSID: string,
                reflectPlanTime: string,
                reflectPerTime: string,
                startDate: string,
                endDate: string) {
                this.applicationID = "";
                this.appReasonID = appReasonID;
                this.prePostAtr = prePostAtr;
                this.inputDate = moment.utc(inputDate, "YYYY/MM/DD").toISOString();
                this.enteredPersonSID = enteredPersonSID;
                this.reversionReason = reversionReason;
                this.applicationDate = moment.utc(applicationDate, "YYYY/MM/DD").toISOString();
                this.applicationReason = applicationReason;
                this.applicationType = 4;
                this.applicantSID = applicantSID;
                this.reflectPlanScheReason = 1;
                this.reflectPlanTime = moment.utc(reflectPlanTime, "YYYY/MM/DD").toISOString();
                this.reflectPlanState = 1;
                this.reflectPlanEnforce = 1;
                this.reflectPerScheReason = 1;
                this.reflectPerTime = moment.utc(reflectPerTime, "YYYY/MM/DD").toISOString();
                this.reflectPerState = 1;
                this.reflectPerEnforce = 1;
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
                this.listPhase = null;
            }
        }
        /**
         * 
         */
        export class GoBackCommand {
            //appDate: string;
            //appID: string;
            workTypeCD: string;
            siftCD: string;
            workChangeAtr: number;
            goWorkAtr1: number;
            backHomeAtr1: number;
            workTimeStart1: number;
            workTimeEnd1: number;
            workLocationCD1: string;
            goWorkAtr2: number;
            backHomeAtr2: number;
            workTimeStart2: number;
            workTimeEnd2: number;
            workLocationCD2: string;
            //appCommand: ApplicationCommand;
            //appApprovalPhaseCmds: Array<common.AppApprovalPhase>;
        }
        
        
        export class AppApprovalPhase {
            phaseID: string;
            approvalForm: number;
            dispOrder: number;
            approvalATR: number;
            approvalFrameCmds: Array<ApprovalFrame>;   
            constructor(phaseID: string, approvalForm: number, dispOrder: number, approvalATR: number, approvalFrameCmds: Array<ApprovalFrame>){
                this.phaseID = phaseID;
                this.approvalForm = approvalForm;
                this.dispOrder = dispOrder;
                this.approvalATR = approvalATR;
                this.approvalFrameCmds = approvalFrameCmds;     
            }
        }
        
        export class ApprovalFrame {
            frameID: string;
            dispOrder: number;
            approveAcceptedCmds: Array<ApproveAccepted>;
            constructor(frameID: string, dispOrder: number, approveAcceptedCmds: Array<ApproveAccepted>){
                this.frameID = frameID;
                this.dispOrder = dispOrder; 
                this.approveAcceptedCmds = approveAcceptedCmds;   
            }     
        }
        
        export class ApproveAccepted {
            appAcceptedID: string;
            approverSID: string;
            approvalATR: number;
            confirmATR: number;
            approvalDate: string;
            reason: string;
            representerSID: string; 
            constructor(appAcceptedID: string, approverSID: string, approvalATR: number, 
                confirmATR: number, approvalDate: string, reason: string, representerSID: string){
                this.appAcceptedID = appAcceptedID;
                this.approverSID = approverSID;
                this.approvalATR = approvalATR;
                this.confirmATR = confirmATR;
                this.approvalDate = approvalDate;
                this.reason = reason;
                this.representerSID = representerSID;    
            }
        }
    }
}