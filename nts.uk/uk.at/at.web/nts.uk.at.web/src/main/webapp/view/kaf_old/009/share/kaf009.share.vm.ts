module nts.uk.at.view.kaf009.share {
    export class ScreenModel {
        //MultilineEditor
        requiredReason : KnockoutObservable<boolean> = ko.observable(false);
        //menu-bar 
        enableSendMail :KnockoutObservable<boolean> = ko.observable(false); 
        prePostDisp: KnockoutObservable<boolean> = ko.observable(false);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(false);
        isWorkChange:   KnockoutObservable<boolean> = ko.observable(true);
        //勤務を変更する 
        workChangeAtr: KnockoutObservable<boolean> = ko.observable(false);
        workState : KnockoutObservable<boolean> = ko.observable(true);
        startPage(settingData: any): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            let notInitialSelection = 0; //0:申請時に決める（初期選択：勤務を変更しない）
            let initialSelection = 1; //1:申請時に決める（初期選択：勤務を変更する）
            let notChange = 2; //2:変更しない
            let change = 3; //3:変更する
            //申請制限設定.申請理由が必須
            self.requiredReason(settingData.appCommonSettingDto.applicationSettingDto.requireAppReasonFlg == 1 ? true: false);
            if(settingData.appCommonSettingDto.appTypeDiscreteSettingDtos.length>0){
                //登録時にメールを送信する Visible
                self.enableSendMail(settingData.appCommonSettingDto.appTypeDiscreteSettingDtos[0].sendMailWhenRegisterFlg == 1 ? true: false); 
                
            }
            //事前事後区分 ※A１
            //申請表示設定.事前事後区分　＝　表示する　〇
            //申請表示設定.事前事後区分　＝　表示しない ×
            self.prePostDisp(settingData.appCommonSettingDto.applicationSettingDto.displayPrePostFlg == 1 ? true: false);
            if(settingData.goBackSettingDto　!= undefined){
                //事前事後区分 Enable ※A２
                //直行直帰申請共通設定.勤務の変更　＝　申請種類別設定.事前事後区分を変更できる 〇
                //直行直帰申請共通設定.勤務の変更　＝　申請種類別設定.事前事後区分を変更できない  ×
                self.prePostEnable(settingData.goBackSettingDto.workChangeFlg == change ? true: false);
                //条件：直行直帰申請共通設定.勤務の変更　＝　申請時に決める（初期選択：勤務を変更する）
                //条件：直行直帰申請共通設定.勤務の変更　＝　申請時に決める（初期選択：勤務を変更しない）
                if(settingData.goBackSettingDto.workChangeFlg == notInitialSelection 
                  || settingData.goBackSettingDto.workChangeFlg == initialSelection){
                    self.isWorkChange(true);
                    if(settingData.goBackSettingDto.workChangeFlg == notInitialSelection ){
                        self.workChangeAtr(false);
                    }else{
                        self.workChangeAtr(true);
                    }
                    
                }else if(settingData.goBackSettingDto.workChangeFlg == notChange){//条件：直行直帰申請共通設定.勤務の変更　＝　変更しない
                    self.isWorkChange(false);
                    self.workChangeAtr(false);
                }else{//条件：直行直帰申請共通設定.勤務の変更　＝　変更する
                    self.workChangeAtr(true);
                    self.isWorkChange(true);
                    self.workState(false);
                }
                
            }
            return dfd.promise();
        }    
    }
    
    
    export module common {
        /**
         * GoBackDirect item
         */
        export class GoBackDiretly {
            selectedBack: any;
            selectedGo: any;
            timeStart1: KnockoutObservable<number>;
            timeEnd1: KnockoutObservable<number>;
            workLocationCD: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            //TIME LINE 2
            selectedGo2: any;
            selectedBack2: any;
            timeStart2: KnockoutObservable<number>;
            timeEnd2: KnockoutObservable<number>;
            workLocationCD2: KnockoutObservable<string>;
            workLocationName2: KnockoutObservable<string>;
            //勤務種類
            workTypeCd: KnockoutObservable<string>;
            workTypeName: KnockoutObservable<string>;
            //勤務種類
            siftCD: KnockoutObservable<string>;
            siftName: KnockoutObservable<string>;
            constructor(
                selectedBack: number,
                selectedGo: number,
                timeStart1: number,
                timeEnd1: number,
                workLocationCD: string,
                workLocationName: string,
                //LINE 2
                selectedBack2: number,
                selectedGo2: number,
                timeStart2: number,
                timeEnd2: number,
                workLocationCD2: string,
                workLocationName2: string,
                workTypeCd:string,
                workTypeName:string,
                siftCD:string,
                siftName:string) {
                    let self = this;
                    //LINE 1
                    self.selectedGo = ko.observable(1);
                    self.selectedBack = ko.observable(1);
                    self.timeStart1 = ko.observable(0);
                    self.timeEnd1 = ko.observable(0);
                    self.workLocationCD = ko.observable('');
                    self.workLocationName = ko.observable('');
                    //LINE 2                   
                    self.selectedGo2 = ko.observable(1);
                    self.selectedBack2 = ko.observable(1);
                    self.timeStart2 = ko.observable(0);
                    self.timeEnd2 = ko.observable(0);
                    self.workLocationCD2 = ko.observable('');
                    self.workLocationName2 = ko.observable('');
                    //
                    self.workTypeCd = ko.observable('');
                    self.workTypeName = ko.observable('');
                    self.siftCD = ko.observable('');
                    self.siftName = ko.observable('');
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
        export class GoBackDiretlySetting {
            //color, font Weight
            //comment
            commentGo1: KnockoutObservable<string>;
            commentBack1: KnockoutObservable<string>;
            //comment
            commentGo2: KnockoutObservable<string>;
            commentBack2: KnockoutObservable<string>;
            colorGo: KnockoutObservable<string>;
            colorBack: KnockoutObservable<string>;
            fontWeightGo: KnockoutObservable<number>;
            fontWeightBack: KnockoutObservable<number>;
            constructor(
                commentGo1: string,
                commentBack1: string,
                commentGo2: string,
                commentBack2: string,
                colorGo: string,
                colorBack: string,
                fontWeightGo: string,
                fontWeightBack: string
            ) {
                let self = this;
                self.commentGo1 = ko.observable('');
                self.commentBack1 = ko.observable('');
                self.commentGo2 = ko.observable('');
                self.commentBack2 = ko.observable('');
                self.colorGo = ko.observable('#000000');
                self.colorBack = ko.observable('#000000');
                self.fontWeightGo = ko.observable(0);
                self.fontWeightBack = ko.observable(0);
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
        /**
         * Setting Data from Server 
         */
        export class CommonSetting {
            sid: string;
            employeeName: string;
            goBackSettingDto: GoBackDirectSetting;
            listReasonDto: Array<ReasonDto>;
            //appCommonSettingDto : appCommonSettingDto; 
            //.applicationSettingDto.requireAppReasonFlg
            constructor(sid:string,employeeName: string, goBackSettingDto: GoBackDirectSetting, listReasonDto: Array<ReasonDto>) {
                let self = this;
                self.sid = sid;
                self.employeeName = employeeName;
                self.goBackSettingDto = goBackSettingDto;
                self.listReasonDto = listReasonDto;
            }
        }
        /**
         * 
         */
        export interface AppCommonSettingDto{
            
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
            version: number;
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
                version: number,
                applicationID: string,
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
                this.version = version;
                this.applicationID = applicationID;
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
            version : number;
            appDate: string;
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