module nts.uk.at.view.kaf000_ref.shr.viewmodel {
    export class Application {
        appID: string;
        prePostAtr: KnockoutObservable<number>;
        employeeIDLst: KnockoutObservableArray<string>;
        appType: number
        appDate: KnockoutObservable<any>;
        opAppReason: KnockoutObservable<string>;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opReversionReason: KnockoutObservable<string>;
        constructor(appID: string, prePostAtr: number, employeeIDLst: Array<string>, appType: number, appDate: any, 
            opAppReason: string, opAppStandardReasonCD: number, opReversionReason?: string) {
            this.appID = appID;
            this.prePostAtr = ko.observable(prePostAtr);
            this.employeeIDLst = ko.observableArray(employeeIDLst);
            this.appType = appType;
            this.appDate = ko.observable(appDate);
            this.opAppReason = ko.observable(opAppReason);
            this.opAppStandardReasonCD = ko.observable(opAppStandardReasonCD);
            this.opReversionReason = ko.observable(opReversionReason);
        }        
    }       
    
    export class ApplicationSub {
        appID: string;
        appType: number;
        constructor(appID: string, appType: number) {
            this.appID = appID;
            this.appType = appType;
        }      
    }
    
    export module model {
        // loại người đăng nhập
        // người đại diện tương đương người approver, người confirm có ưu tiên cao hơn
        export enum UserType { 
            APPLICANT_APPROVER = 0, // 申請本人&承認者
            APPROVER = 1, // 承認者
            APPLICANT = 2, // 申請本人
            OTHER = 3, // その他        
        }; 
        
        // trạng thái của phase chứa user
        export enum ApprovalAtr { 
            UNAPPROVED = 0, // 未承認   
            APPROVED = 1, // 承認済
            DENIAL = 2, // 否認
            REMAND = 3, // 差し戻し
        };
        
        export enum Status {
            NOTREFLECTED = 0, // 未反映
            WAITREFLECTION = 1, //反映待ち
            REFLECTED = 2, //反映済
            CANCELED = 3, //取消済
            REMAND = 4,//差し戻し
            DENIAL = 5, //否認
            PASTAPP = 99, //過去申請 
        };
    }
    
    export class CommonProcess {
        public static initCommonSetting() {
            return {
                appDetailScreenInfo: {
                    application: {},
                    approvalLst: [],
                    authorComment: "",
                    user: 0, 
                    reflectPlanState: 0,
                    outputMode: 0,
                    authorizableFlags: false,
                    approvalATR: 0,
                    alternateExpiration: false    
                },
                appDispInfoNoDateOutput: {
                    employeeInfoLst: [],
                    requestSetting: {},
                    appReasonLst: []    
                },
                appDispInfoWithDateOutput: {
                    approvalFunctionSet: {},
                    employmentSet: {},
                    workTimeLst: [],
                    listApprovalPhaseState: [],
                    errorFlag: 0,
                    prePostAtr: 1,
                    baseDate: "",
                    achievementOutputLst: [],
                    appDetailContentLst: [],
                    empHistImport: {}
                }  
            }    
        }    
        
        public static initDeadlineMsg(value: any, vm: any) {
            vm.message(value.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].memo);
            if(_.isEmpty(vm.message())) {
                vm.displayMsg(false);         
            } else {
                vm.displayMsg(true);
            }
            
            let advanceAppAcceptanceLimit = value.appDispInfoNoDateOutput.advanceAppAcceptanceLimit == 1;
            let allowFutureDay = value.appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting.afterhandRestriction.allowFutureDay;
            let appDeadlineUseCategory = value.appDispInfoWithDateOutput.appDeadlineUseCategory == 1;
            // 注意：申請表示情報(基準日関係なし)．事前申請の受付制限が利用しない && 申請表示情報．申請設定（基準日関係なし）．申請承認設定．申請設定．受付制限設定．事後の受付制限．未来日許可しないがfalse && 申請表示情報(基準日関係あり)．申請締め切り日利用区分が利用しない
            if(!advanceAppAcceptanceLimit && !allowFutureDay && !appDeadlineUseCategory) {
                // 締め切りエリア全体に非表示
                vm.displayDeadline(false);        
            } else {
                vm.displayDeadline(true);     
            }
            // {1}事前受付日
            let prePart = "";
            if(advanceAppAcceptanceLimit) {
                // ・申請表示情報(基準日関係なし)．事前受付時分がNull
                if(_.isNull(value.appDispInfoNoDateOutput.opAdvanceReceptionHours)) {
                    prePart = vm.$i18n('KAF000_38', [value.appDispInfoNoDateOutput.opAdvanceReceptionDate]);        
                } 
                // ・申請表示情報(基準日関係なし)．事前受付時分がNullじゃない
                else {
                    prePart = vm.$i18n('KAF000_41', [value.appDispInfoNoDateOutput.opAdvanceReceptionHours]);  
                }             
            }
            // {2}事後受付日
            let postPart = vm.$i18n('KAF000_39', [moment(vm.$date.today()).format("YYYY/MM/DD")]);
            // {3}締め切り期限日
            let deadlinePart = vm.$i18n('KAF000_40', [value.appDispInfoWithDateOutput.opAppDeadline]);
            vm.deadline(prePart + postPart + deadlinePart);    
        }
    }
}