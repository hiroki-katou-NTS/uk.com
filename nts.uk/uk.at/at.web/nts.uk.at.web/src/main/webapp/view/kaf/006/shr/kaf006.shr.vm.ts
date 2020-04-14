module nts.uk.at.view.kaf006.share {
    export module common {
        export class HolidayType{
            code: number;
            name: string;
            constructor(code : number, name: string){
                this.code = code;
                this.name = name;
            }
        }
        export class TypeOfDuty{
            typeOfDutyID: number;
            typeOfDutyName: string;
            constructor(typeOfDutyID : number,typeOfDutyName :string){
                this.typeOfDutyID = typeOfDutyID;
                this.typeOfDutyName = typeOfDutyName;
            }
        }
        export class EmployeeOT{
            id: string;
            name: string;
            constructor(id: string, name: string){
                this.id = id;
                this.name = name;
            }    
        }
        /**
         * 
         */
        export class Application {
            applicationID: KnockoutObservable<string>; // 申請ID
            inputDate: KnockoutObservable<string>; // 入力日
            enteredPerson: KnockoutObservable<string>; // 入力者
            appDate: KnockoutObservable<string>; // 申請日
            titleReason: KnockoutObservable<string>;
            contentReason: KnockoutObservable<string>;
            employeeID: KnockoutObservable<string>; // 申請者
            constructor(
                applicationID: string,
                inputDate: string,
                enteredPerson: string,
                appDate: string,
                titleReason: string,
                contentReason: string,
                employeeID: string) {
                this.applicationID = ko.observable(applicationID);
                this.inputDate = ko.observable(inputDate);
                this.enteredPerson = ko.observable(enteredPerson);
                this.appDate = ko.observable(appDate);
                this.titleReason = ko.observable(titleReason);
                this.contentReason = ko.observable(contentReason);
                this.employeeID = ko.observable(employeeID);
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
            reasonId: string;
            reasonName: string;
            constructor(reasonId: string, reasonName: string) {
                this.reasonId = reasonId;
                this.reasonName = reasonName;
            }
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
        export class AppApprovalPhase {
            phaseID: string;
            approvalForm: number;
            dispOrder: number;
            approvalATR: number;
            approvalFrameCmds: Array<ApprovalFrame>;
            constructor(phaseID: string, approvalForm: number, dispOrder: number, approvalATR: number, approvalFrameCmds: Array<ApprovalFrame>) {
                this.phaseID = phaseID;
                this.approvalForm = approvalForm;
                this.dispOrder = dispOrder;
                this.approvalATR = approvalATR;
                this.approvalFrameCmds = approvalFrameCmds;
            }
        }
        /**
         * 
         */
        export class ApprovalFrame {
            frameID: string;
            dispOrder: number;
            approveAcceptedCmds: Array<ApproveAccepted>;
            constructor(frameID: string, dispOrder: number, approveAcceptedCmds: Array<ApproveAccepted>) {
                this.frameID = frameID;
                this.dispOrder = dispOrder;
                this.approveAcceptedCmds = approveAcceptedCmds;
            }
        }
        /**
         * 
         */
        export class ApproveAccepted {
            appAcceptedID: string;
            approverSID: string;
            approvalATR: number;
            confirmATR: number;
            approvalDate: string;
            reason: string;
            representerSID: string;
            constructor(appAcceptedID: string, approverSID: string, approvalATR: number,
                confirmATR: number, approvalDate: string, reason: string, representerSID: string) {
                this.appAcceptedID = appAcceptedID;
                this.approverSID = approverSID;
                this.approvalATR = approvalATR;
                this.confirmATR = confirmATR;
                this.approvalDate = approvalDate;
                this.reason = reason;
                this.representerSID = representerSID;
            }
        }
        
        export class DisplayReason {
            typeLeave: number;    
            displayFixedReason: boolean;
            displayAppReason: boolean;
            constructor(typeLeave: number, displayFixedReason: boolean, displayAppReason: boolean){
                this.typeLeave = typeLeave;
                this.displayFixedReason = displayFixedReason;
                this.displayAppReason = displayAppReason;              
            }
        }
        
        export class SettingNo65 {
            //休暇種類
            hdType: number;
            //画面モード
            screenMode: number;
            //休暇申請設定．年休より優先消化チェック区分 - HdAppSet
            pridigCheck: number;
            //振休管理設定．管理区分
            subVacaManage: boolean;
            //休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
            subVacaTypeUseFlg: boolean;
            //代休管理設定．管理区分
            subHdManage: boolean;
            //休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
            subHdTypeUseFlg: boolean;
            constructor(hdType: number, screenMode: number, pridigCheck: number, subVacaManage: boolean,
                    subVacaTypeUseFlg: boolean, subHdManage: boolean, subHdTypeUseFlg: boolean){
                this.hdType = hdType;
                this.screenMode = screenMode;
                this.pridigCheck = pridigCheck;
                this.subVacaManage = subVacaManage;
                this.subVacaTypeUseFlg = subVacaTypeUseFlg;
                this.subHdManage = subHdManage;
                this.subHdTypeUseFlg = subHdTypeUseFlg;
            }
        }
    }
}