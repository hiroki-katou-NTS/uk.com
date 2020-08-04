module nts.uk.at.view.kaf000_ref.shr.viewmodel {
    export class Application {
        appID: string;
        prePostAtr: KnockoutObservable<number>;
        employeeIDLst: KnockoutObservableArray<string>;
        appType: number;
        appDate: KnockoutObservable<string>;
        opAppReason: KnockoutObservable<string>;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opReversionReason: KnockoutObservable<string>;
        opAppStartDate: KnockoutObservable<string>;
        opAppEndDate: KnockoutObservable<string>;
        constructor(appType: number) {
            this.appID = null;
            this.prePostAtr = ko.observable(null);
            this.employeeIDLst = ko.observableArray([]);
            this.appType = appType;
            this.appDate = ko.observable("");
            this.opAppReason = ko.observable(null);
            this.opAppStandardReasonCD = ko.observable(null);
            this.opReversionReason = ko.observable(null);
            this.opAppStartDate = ko.observable("");
            this.opAppEndDate = ko.observable("");
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
        
        export enum AppType {
            OVER_TIME_APPLICATION = 0, // 残業申請
            ABSENCE_APPLICATION = 1, // 休暇申請
            WORK_CHANGE_APPLICATION = 2, // 勤務変更申請
            BUSINESS_TRIP_APPLICATION = 3, // 出張申請
            GO_RETURN_DIRECTLY_APPLICATION = 4, // 直行直帰申請
            LEAVE_TIME_APPLICATION = 6, // 休出時間申請
            STAMP_APPLICATION = 7, // 打刻申請
            ANNUAL_HOLIDAY_APPLICATION = 8, // 時間休暇申請
            EARLY_LEAVE_CANCEL_APPLICATION = 9, // 遅刻早退取消申請
            COMPLEMENT_LEAVE_APPLICATION = 10, // 振休振出申請
            OPTIONAL_ITEM_APPLICATION = 15, // 任意項目申請    
        }
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
        
        public static checkUsage(
            mode: boolean, // true: new, false: detail 
            element: string, // element select to set error
            vm: any
        ) {
            vm.$errors("clear", [element]);
            let appDispInfoStartupOutput = vm.appDispInfoStartupOutput(),
                useDivision = appDispInfoStartupOutput.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].useDivision,
                recordDate = appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.recordDate,
                empHistImport = appDispInfoStartupOutput.appDispInfoWithDateOutput.empHistImport,
                opErrorFlag = appDispInfoStartupOutput.appDispInfoWithDateOutput.opErrorFlag,
                msgID = "";
            if(mode && useDivision == 0) {
                vm.$errors(element, "Msg_323");
                vm.$dialog.error({ messageId: "Msg_323" }).then(() => {
                    if(recordDate == 0) {
                        vm.$jump("com", "/view/ccg/008/a/index.xhtml");    
                    }
                });   
                return false;
            }
            
            if(_.isNull(opErrorFlag)) {
                return true;    
            }
            switch(opErrorFlag){
                case 1:
                    msgID = "Msg_324";
                    break;
                case 2: 
                    msgID = "Msg_238";
                    break;
                case 3:
                    msgID = "Msg_237";
                    break;
                default: 
                    break;
            }  
            if(_.isEmpty(msgID)) { 
                return true;
            }
            vm.$errors(element, msgID);
            vm.$dialog.error({ messageId: msgID }).then(() => {
                if(recordDate == 0) {
                    vm.$jump("com", "/view/ccg/008/a/index.xhtml");    
                }    
            });
        }
    }
}