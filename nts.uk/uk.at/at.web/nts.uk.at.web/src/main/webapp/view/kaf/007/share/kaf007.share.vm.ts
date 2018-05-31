module nts.uk.at.view.kaf007.share {
    
    export module common {       
        export class AppWorkChangeCommand{
            //勤務変更申請
            workChange: KnockoutObservable<AppWorkChange>;                
            //申請
            application: KnockoutObservable<ApplicationCommand>;            
            //Data working
            dataWork:KnockoutObservable<DataWork>;
            constructor()
            {
                let self = this;
                self.workChange = ko.observable(new AppWorkChange());
                self.application = ko.observable(new ApplicationCommand());
                self.dataWork= ko.observable(new DataWork());
            }
        }
       /**
        * 勤務変更申請
        */
       export class AppWorkChange {
           version: KnockoutObservable<number>;
           cid: KnockoutObservable<string>;
           appId: KnockoutObservable<string>;
           //勤務種類コード
           workTypeCd: KnockoutObservable<string>;
           workTypeName: KnockoutObservable<string>;
           //就業時間帯コード
           workTimeCd: KnockoutObservable<string>;
           workTimeName: KnockoutObservable<string>;
           //休日を除外する
           excludeHolidayAtr: KnockoutObservable<number>;
           workChangeAtr : KnockoutObservable<number>;
           goWorkAtr1 : KnockoutObservable<number>;
           backHomeAtr1 : KnockoutObservable<number>;
           breakTimeStart1 : KnockoutObservable<number>;
           breakTimeEnd1 : KnockoutObservable<number>;
           workTimeStart1 : KnockoutObservable<number>;
           workTimeEnd1 : KnockoutObservable<number>;
           workTimeStart2 : KnockoutObservable<number>;
           workTimeEnd2 : KnockoutObservable<number>;
           goWorkAtr2 : KnockoutObservable<number>;
           backHomeAtr2 : KnockoutObservable<number>;            
           constructor() {
                   let self = this;
                   self.cid = ko.observable('');
                   self.appId = ko.observable('');
                   self.workTypeCd = ko.observable('');
                   self.workTypeName = ko.observable('');
                   self.workTimeCd = ko.observable('');
                   self.workTimeName = ko.observable('');
                   self.excludeHolidayAtr = ko.observable(0);
                   self.workChangeAtr  = ko.observable(0);
                   self.goWorkAtr1  = ko.observable(1);
                   self.backHomeAtr1  = ko.observable(1);
                   self.goWorkAtr2  = ko.observable(1);
                   self.backHomeAtr2  = ko.observable(1);
                   self.breakTimeStart1  = ko.observable(null);
                   self.breakTimeEnd1  = ko.observable(null);
                   self.workTimeStart1  = ko.observable(null);
                   self.workTimeEnd1  = ko.observable(null);
                   self.workTimeStart2  = ko.observable(null);
                   self.workTimeEnd2  = ko.observable(null);                    
           }
       }
       
       
       /**
        * 勤務変更申請設定
        */
       export class AppWorkChangeSetting {
           cid : KnockoutObservable<string>;
           excludeHoliday : KnockoutObservable<number>;
           workChangeTimeAtr : KnockoutObservable<number>;
           displayResultAtr : KnockoutObservable<number>;
           initDisplayWorktime : KnockoutObservable<number>;
           commentContent1 : KnockoutObservable<string>;
           commentFontWeight1 : KnockoutObservable<number>;
           commentFontColor1 : KnockoutObservable<string>;
           commentContent2 : KnockoutObservable<string>;
           commentFontWeight2 : KnockoutObservable<number>;
           commentFontColor2 : KnockoutObservable<string>;
           constructor() {
               let self = this;
               self.cid = ko.observable('');
               self.excludeHoliday = ko.observable(0);
               self.workChangeTimeAtr = ko.observable(0);
               self.displayResultAtr = ko.observable(0);
               self.initDisplayWorktime = ko.observable(0);
               self.commentContent1 = ko.observable('');
               self.commentFontWeight1 = ko.observable(0);
               self.commentFontColor1 = ko.observable('#000000');
               self.commentContent2 = ko.observable('');
               self.commentFontWeight2 = ko.observable(0);
               self.commentFontColor2 = ko.observable('#000000');
           }
       }
       /**
        * 申請
        */
       export class ApplicationCommand {  
           version: number;         
           applicationID: KnockoutObservable<string>;
           appReasonID: string;
           prePostAtr: KnockoutObservable<number>;
           inputDate: KnockoutObservable<string>;
           enteredPersonSID: KnockoutObservable<string>;
           reversionReason: string;
           applicationDate: KnockoutObservable<string>;
           applicationReason: KnockoutObservable<string>;
           applicationType: KnockoutObservable<number>;
           applicantSID: KnockoutObservable<string>;
           reflectPlanScheReason: number;
           reflectPlanTime: string;
           reflectPlanState: number;
           reflectPlanEnforce: number;
           reflectPerScheReason: number;
           reflectPerTime: string;
           reflectPerState: number;
           reflectPerEnforce: number;
           startDate: KnockoutObservable<string>;
           endDate: KnockoutObservable<string>;
           constructor() {
               let self = this;
               self.prePostAtr = ko.observable(0);
               self.enteredPersonSID = ko.observable('');
               self.applicationDate =  ko.observable('');
               self.applicationReason = ko.observable('');
               self.applicationType = ko.observable(2);
               self.applicantSID = ko.observable('');
               self.startDate =  ko.observable('');
               self.endDate =  ko.observable('');
               self.version = 0;
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
            defaultFlg: number
            constructor(companyId: string, appType: number, reasonID: string, displayOrder: number, reasonTemp: string, defaultFlg: number) {
                var self = this;
                self.companyId = companyId;
                self.appType = appType;
                self.reasonID = reasonID;
                self.displayOrder = displayOrder;
                self.reasonTemp = reasonTemp;
                self.defaultFlg = defaultFlg;
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
        
        export class RecordWorkInfo{
            appDate : KnockoutObservable<string>;
            workTypeCode : KnockoutObservable<string>;
            workTypeName : KnockoutObservable<string>;
            workTimeCode : KnockoutObservable<string>;
            workTimeName : KnockoutObservable<string>;
            startTime1 : KnockoutObservable<number>;
            endTime1 : KnockoutObservable<number>;
            startTime2 : KnockoutObservable<number>;
            endTime2 : KnockoutObservable<number>;
            breakTimeStart : KnockoutObservable<number>;
            breakTimeEnd : KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.appDate = ko.observable('');
                self.workTypeCode = ko.observable('');
                self.workTypeName = ko.observable('');
                self.workTimeCode = ko.observable('');
                self.workTimeName = ko.observable('');
                self.startTime1 = ko.observable(0);
                self.endTime1 = ko.observable(0);
                self.startTime2 = ko.observable(0);
                self.endTime2 = ko.observable(0);
                self.breakTimeStart = ko.observable(0);
                self.breakTimeEnd = ko.observable(0);
            }            
        }
        /*export enum WorkChangeType{
            //0:申請時に決める（初期選択：勤務を変更しない）
            NotInitSelection = 0,
            //1:申請時に決める（初期選択：勤務を変更する）
            InitSelection,
            //2:変更しない
            NotChange,
            //3:変更する
            Change,
        }*/
        export enum AppDateType{
            //申請開始日                                 
            StartDate = 0,
            //1:申請終了日
            EndDate
        }
        export class DataWork{
            workTypeCodes : KnockoutObservableArray<string>;
            workTimeCodes : KnockoutObservableArray<string>;
            selectedWorkTypeCd : KnockoutObservable<string>;
            selectedWorkTypeName : KnockoutObservable<string>;
            selectedWorkTimeCd : KnockoutObservable<string>;
            selectedWorkTimeName : KnockoutObservable<string>;
            constructor(){
                let self = this;
                self.workTypeCodes = ko.observableArray([]);
                self.workTimeCodes = ko.observableArray([]);
                self.selectedWorkTypeCd = ko.observable('');
                self.selectedWorkTypeName = ko.observable('');
                self.selectedWorkTimeCd = ko.observable('');
                self.selectedWorkTimeName = ko.observable('');
            }
        }
    }
}