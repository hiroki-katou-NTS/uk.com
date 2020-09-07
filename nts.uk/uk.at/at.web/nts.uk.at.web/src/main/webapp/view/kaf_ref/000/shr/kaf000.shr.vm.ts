module nts.uk.at.view.kaf000_ref.shr.viewmodel {
    export class Application {
        appID: string;
        prePostAtr: KnockoutObservable<number>;
        appType: number
        appDate: KnockoutObservable<any>;
        constructor(appID: string, prePostAtr: number, appType: number, appDate: any) {
            this.appID = appID;
            this.prePostAtr = ko.observable(prePostAtr);
            this.appType = appType;
            this.appDate = ko.observable(appDate);
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
    }
}