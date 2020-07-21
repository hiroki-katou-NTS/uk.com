module nts.uk.at.view.kaf000_ref.shr.viewmodel {
    export class Application {
        appID: string;
        employeeIDLst: KnockoutObservableArray<string>;
        prePostAtr: KnockoutObservable<number>;
        appType: number
        appDate: KnockoutObservable<any>;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opAppReason: KnockoutObservable<string>;
        constructor(appID: string, employeeIDLst: Array<string>, prePostAtr: number, appType: number, appDate: any, 
            opAppStandardReasonCD: number, opAppReason: string) {
            this.appID = appID;
            this.employeeIDLst = ko.observableArray(employeeIDLst);
            this.prePostAtr = ko.observable(prePostAtr);
            this.appType = appType;
            this.appDate = ko.observable(appDate);
            this.opAppStandardReasonCD = ko.observable(opAppStandardReasonCD);
            this.opAppReason = ko.observable(opAppReason);
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