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
        
        export interface IWorkLocation {
            workLocationCD: string;
            workLocationName: string;  
        } 
        
        export class Application {
            applicationID: KnockoutObservable<string>; // 申請ID
            inputDate: KnockoutObservable<string>; // 入力日
            enteredPerson: KnockoutObservable<string>; // 入力者
            appDate: KnockoutObservable<string>; // 申請日
            titleReason: KnockoutObservable<string>; 
            contentReason: KnockoutObservable<string>;
            employeeID: KnockoutObservable<string>; // 申請者
            version: number;
            constructor( 
                applicationID: string,
                inputDate: string,
                enteredPerson: string,
                appDate: string,
                titleReason: string,
                contentReason: string,
                employeeID: string,
                version: number){
                    this.applicationID = ko.observable(applicationID);  
                    this.inputDate = ko.observable(inputDate);
                    this.enteredPerson = ko.observable(enteredPerson);
                    this.appDate = ko.observable(appDate);
                    this.titleReason = ko.observable(titleReason);
                    this.contentReason = ko.observable(contentReason);
                    this.employeeID = ko.observable(employeeID);
                    this.version = version;
            }
        }
        
        export class AppStampGoOutPermit {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutAtr: KnockoutObservable<number>;
            startTime: KnockoutObservable<CheckBoxTime>;
            startLocation: KnockoutObservable<CheckBoxLocation>;
            endTime: KnockoutObservable<CheckBoxTime>;
            endLocation: KnockoutObservable<CheckBoxLocation>;  
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutAtr: number,
                startTime: CheckBoxTime,
                startLocation: CheckBoxLocation,
                endTime: CheckBoxTime,
                endLocation: CheckBoxLocation){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutAtr = ko.observable(stampGoOutAtr);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
            }
        }
        
        export class AppStampWork {
            stampAtr: KnockoutObservable<number>;
            stampFrameNo: KnockoutObservable<number>;
            stampGoOutAtr: KnockoutObservable<number>;
            supportCard: KnockoutObservable<CheckBoxLocation>;
            supportLocation: KnockoutObservable<CheckBoxLocation>;
            startTime: KnockoutObservable<CheckBoxTime>;
            startLocation: KnockoutObservable<CheckBoxLocation>;
            endTime: KnockoutObservable<CheckBoxTime>;
            endLocation: KnockoutObservable<CheckBoxLocation>;   
            constructor(
                stampAtr: number,
                stampFrameNo: number,
                stampGoOutAtr: number,
                supportCard: CheckBoxLocation,
                supportLocation: CheckBoxLocation,
                startTime: CheckBoxTime,
                startLocation: CheckBoxLocation,
                endTime: CheckBoxTime,
                endLocation: CheckBoxLocation){
                    this.stampAtr = ko.observable(stampAtr);
                    this.stampFrameNo = ko.observable(stampFrameNo);
                    this.stampGoOutAtr = ko.observable(stampGoOutAtr);
                    this.supportCard = ko.observable(supportCard);
                    this.supportLocation = ko.observable(supportLocation);
                    this.startTime = ko.observable(startTime);
                    this.startLocation = ko.observable(startLocation);
                    this.endTime = ko.observable(endTime);
                    this.endLocation = ko.observable(endLocation);
            }
        }
        
        export class AppStampCancel {
            stampFrameNo: number;
            stampAtr: number;
            label: KnockoutObservable<string>;
            startTime: KnockoutObservable<string>;
            endTime: KnockoutObservable<string>;
            cancelAtr: KnockoutObservable<number>;
            constructor(stampFrameNo: number, stampAtr: number, label: string, startTime: string, endTime: string, cancelAtr: number){
                this.stampFrameNo = stampFrameNo;
                this.stampAtr = stampAtr;
                this.label = ko.observable(label);
                this.startTime = ko.observable(startTime);
                this.endTime = ko.observable(endTime);
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
            listFrame: Array<ApprovalFrame>;   
            constructor(phaseID: string, approvalForm: number, dispOrder: number, approvalATR: number, listFrame: Array<ApprovalFrame>){
                this.phaseID = phaseID;
                this.approvalForm = approvalForm;
                this.dispOrder = dispOrder;
                this.approvalATR = approvalATR;
                this.listFrame = listFrame;     
            }
        }
        
        export class ApprovalFrame {
            frameID: string;
            dispOrder: number;
            listApproveAccepted: Array<ApproveAccepted>;
            constructor(frameID: string, dispOrder: number, listApproveAccepted: Array<ApproveAccepted>){
                this.frameID = frameID;
                this.dispOrder = dispOrder; 
                this.listApproveAccepted = listApproveAccepted;   
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
            fontWeight: KnockoutObservable<boolean>;     
            constructor(text: string, color: string, fontWeight: boolean){
                this.text = ko.observable(text);
                this.color = ko.observable(color);
                this.fontWeight = ko.observable(fontWeight);      
            }
        }
        
        export class CheckBoxTime {
            value: KnockoutObservable<number>; 
            checked: KnockoutObservable<boolean>; 
            enable: KnockoutObservable<boolean>;     
            constructor(value: number, checked: boolean, enable: boolean){
                this.value = ko.observable(value);
                this.checked = ko.observable(checked);
                this.enable = ko.observable(enable);       
            }   
        }  
        
        export class CheckBoxLocation {
            code: KnockoutObservable<string>; 
            name: KnockoutObservable<string>;
            checked: KnockoutObservable<boolean>; 
            enable: KnockoutObservable<boolean>;     
            constructor(code: string, name: string, checked: boolean, enable: boolean){
                this.code = ko.observable(code);
                this.name = ko.observable(name);
                this.checked = ko.observable(checked);
                this.enable = ko.observable(enable);       
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
        
        export interface SimpleObject {
            code: number;
            name: string;        
        }
        
        export enum Enum {
            WORK = 0,
            GOOUT = 1   
        }
    }
}