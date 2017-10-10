module nts.uk.at.view.kaf002.shr {
    export module vmbase {
        export interface AppStampNewSetDto {
            appCommonSettingDto: AppCommonSettingDto;
            appStampSetDto: AppStampSetDto;     
            companyID: string;
            employeeID: string;
            employeeName: string;
        }
        
        export interface AppCommonSettingDto {
            generalDate: string;
            applicationSettingDto: string;  
            requestOfEachCommonDtos: string;
            appTypeDiscreteSettingDtos: string;
            applicationDeadlineDtos: string;
        }   
        
        export interface AppStampSetDto {
            stampRequestSettingDto: StampRequestSettingDto;
            applicationReasonDtos: ApplicationReasonDto[];
        }
        
        export interface ApplicationReasonDto {
            companyId: string;
            appType: number;
            reasonID: string;
            displayOrder: number;
            reasonTemp: string;
            defaultFlg: number;
        }
        
        export interface StampRequestSettingDto {
            companyID: string;
            topComment: string;
            topCommentFontColor: string;
            topCommentFontWeight: number;
            bottomComment: string;
            bottomCommentFontColor: string;
            bottomCommentFontWeight: number;
            resultDisp: number;
            supFrameDispNO: number;
            stampPlaceDisp: number;
            stampAtr_Work_Disp: number;
            stampAtr_GoOut_Disp: number;
            stampAtr_Care_Disp: number;
            stampAtr_Sup_Disp: number;
            stampGoOutAtr_Private_Disp: number;
            stampGoOutAtr_Public_Disp: number;
            stampGoOutAtr_Compensation_Disp: number;
            stampGoOutAtr_Union_Disp: number;
        }
        
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
                employeeID: string){
                    this.applicationID = ko.observable(applicationID);  
                    this.inputDate = ko.observable(inputDate);
                    this.enteredPerson = ko.observable(enteredPerson);
                    this.appDate = ko.observable(appDate);
                    this.titleReason = ko.observable(titleReason);
                    this.contentReason = ko.observable(contentReason);
                    this.employeeID = ko.observable(employeeID);
            }
        }
        
        export class AppStampGoOutPermit {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutAtr: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            startLocation: KnockoutObservable<string>;
            endTime: KnockoutObservable<number>;
            endLocation: KnockoutObservable<string>;  
            startTimeDisp: KnockoutObservable<boolean>; 
            startLocationDisp: KnockoutObservable<boolean>; 
            endTimeDisp: KnockoutObservable<boolean>; 
            endLocationDisp: KnockoutObservable<boolean>;    
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutAtr: number,
                startTime: number,
                startLocation: string,
                endTime: number,
                endLocation: string,
                startTimeDisp: boolean, 
                startLocationDisp: boolean,
                endTimeDisp: boolean, 
                endLocationDisp: boolean){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutAtr = ko.observable(stampGoOutAtr);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
                    this.startTimeDisp = ko.observable(startTimeDisp);
                    this.startLocationDisp = ko.observable(startLocationDisp);
                    this.endTimeDisp = ko.observable(endTimeDisp);
                    this.endLocationDisp = ko.observable(endLocationDisp);
            }
        }
        
        export class AppStampWork {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutAtr: KnockoutObservable<number>;
            supportCard: KnockoutObservable<string>;
            supportLocation: KnockoutObservable<string>;
            supportCardDisp: KnockoutObservable<boolean>;  
            supportLocationDisp: KnockoutObservable<boolean>; 
            startTime: KnockoutObservable<number>;
            startLocation: KnockoutObservable<string>;
            endTime: KnockoutObservable<number>;
            endLocation: KnockoutObservable<string>;   
            startTimeDisp: KnockoutObservable<boolean>; 
            startLocationDisp: KnockoutObservable<boolean>; 
            endTimeDisp: KnockoutObservable<boolean>; 
            endLocationDisp: KnockoutObservable<boolean>; 
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutAtr: number,
                supportCard: string,
                supportLocation: string,
                supportCardDisp: boolean,
                supportLocationDisp: boolean,
                startTime: number,
                startLocation: string,
                endTime: number,
                endLocation: string,
                startTimeDisp: boolean, 
                startLocationDisp: boolean,
                endTimeDisp: boolean,
                endLocationDisp: boolean){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutAtr = ko.observable(stampGoOutAtr);
                    this.supportCard = ko.observable(supportCard);
                    this.supportLocation = ko.observable(supportLocation);
                    this.supportCardDisp = ko.observable(supportCardDisp);
                    this.supportLocationDisp = ko.observable(supportLocationDisp);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
                    this.startTimeDisp = ko.observable(startTimeDisp);
                    this.startLocationDisp = ko.observable(startLocationDisp);
                    this.endTimeDisp = ko.observable(endTimeDisp);
                    this.endLocationDisp = ko.observable(endLocationDisp);
            }
        }
        
        export class AppStampCancel {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            cancelAtr: KnockoutObservable<number>;
            constructor(stampAtr: number, stampFrameNo: number, cancelAtr: number){
                this.stampAtr = ko.observable(stampAtr);
                this.stampFrameNo = ko.observable(stampFrameNo);
                this.cancelAtr = ko.observable(cancelAtr);         
            }
        }
        
        export class AppStampOnlineRecord {
            stampCombinationAtr: KnockoutObservable<number>;
            appTime: KnockoutObservable<number>;    
            constructor(stampCombinationAtr: number, appTime: number){
                this.stampCombinationAtr = ko.observable(stampCombinationAtr);
                this.appTime = ko.observable(appTime);        
            }
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
        
        export class InputReason {
            id: string;
            content: string; 
            constructor(id: string, content: string){
                this.id = id;
                this.content = content;        
            }
        }
        
        export class CommentUI {
            text: KnockoutObservable<string>; 
            color: KnockoutObservable<string>; 
            fontWeight: KnockoutObservable<number>;     
            constructor(text: string, color: string, fontWeight: number){
                this.text = ko.observable(text);
                this.color = ko.observable(color);
                this.fontWeight = ko.observable(fontWeight);      
            }
        }
        
        export class StampCombination {
            value: number;
            name: string;
            constructor(value: number, name: string) {
                this.value = value;
                this.name = name;    
            }    
        }
        
        export enum Enum {
            WORK = 0,
            GOOUT = 1   
        }
    }
}