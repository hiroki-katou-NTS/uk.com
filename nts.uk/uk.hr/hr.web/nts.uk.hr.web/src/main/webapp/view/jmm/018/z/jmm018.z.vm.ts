module nts.uk.com.view.jmm018.z.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        menuApprovalSettingInforList: KnockoutObservableArray<any>;
        
        
        constructor() {
            let self = this;
            self.menuApprovalSettingInforList = ko.observableArray([]) 
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            new service.getMenuApprovalSettings().done(function(data: any) {
                console.log(data);
                let tg = [];
                _.forEach(data, (item) => {
                    tg.push(new MenuApprovalSettingInfor(item));
                });
                self.menuApprovalSettingInforList(tg);
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        update(): void {
            let self = this;
            let param = ko.toJS(self.menuApprovalSettingInforList());
            console.log(param);
            block.grayout();
            new service.update(param).done(function(data: any) {
                
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
            });
        }
        
    }
   
    export interface IMenuApprovalSettingInfor {
        menuApprovalSettings: any;
        rptLayoutCD: string;
        rptLayoutName: string;
        displayName: string;
        screenID: string;
        useApproval: boolean;
        noRankOrder: boolean;
    }
    
    class MenuApprovalSettingInfor {
        menuApprovalSettings: any;
        rptLayoutCD: string;
        rptLayoutName: string;
        displayName: string;
        screenID: string;
        useApproval: boolean;
        noRankOrder: boolean;
        name: string;
        approverName1: KnockoutObservable<string>;
        approverName2: KnockoutObservable<string>;
        constructor(param: IMenuApprovalSettingInfor) {
            let self = this;
            self.menuApprovalSettings = new MenuApprovalSettings(param.menuApprovalSettings);
            self.rptLayoutCD = param.rptLayoutCD;
            self.rptLayoutName = param.rptLayoutName;
            self.displayName = param.displayName;
            self.screenID = param.screenID;
            self.useApproval = param.useApproval;
            self.noRankOrder = param.noRankOrder;
            self.name = param.menuApprovalSettings.programId+' '+param.displayName + (param.menuApprovalSettings.programId == 'JHN001'? ('('+param.rptLayoutCD+param.rptLayoutName+')'):'');
             self.approverName1 = ko.observable(param.menuApprovalSettings.apr1BusinessName == null? getText('JMM018_Z422_1_16_1'): param.menuApprovalSettings.apr1BusinessName);
            self.approverName2 = ko.observable(param.menuApprovalSettings.apr2BusinessName == null? getText('JMM018_Z422_1_18_1'): param.menuApprovalSettings.apr2BusinessName);
        }
        
        selectApprover1(): void {
            let self = this;
            console.log(self);
            let param = {
                listInfor: self
            }
            setShared('shareToJMM018Y', param);
            modal('/view/jmm/018/y/index.xhtml').onClosed(function(): any {
                let param = getShared('shareToJMM018B');
                if(param != undefined){
                    item.setEnableRetirePlanCourse(param, self);
                }
            })
        }
        
        selectApprover2(): void {
            let self = this;
            console.log(self);
        }
    }
    
    class MenuApprovalSettings {
        cId: string;
        workId: string;
        rptLayoutId: string;
        programId: string;
        screenId: string;
        useApproval: KnockoutObservable<boolean>;
        availableAprRoot: KnockoutObservable<boolean>;
        availableAprWork1: KnockoutObservable<boolean>;
        availableAprWork2: KnockoutObservable<boolean>;
        apr1Sid: KnockoutObservable<string>;
        apr1Scd: KnockoutObservable<string>;
        apr1BusinessName: KnockoutObservable<string>;
        app1Devcd: KnockoutObservable<string>;
        app1DevName: KnockoutObservable<string>;
        app1Poscd: KnockoutObservable<string>;
        app1PosName: KnockoutObservable<string>;
        apr2Sid: KnockoutObservable<string>;
        apr2Scd: KnockoutObservable<string>;
        apr2BusinessName: KnockoutObservable<string>;
        app2Devcd: KnockoutObservable<string>;
        app2DevName: KnockoutObservable<string>;
        app2Poscd: KnockoutObservable<string>;
        app2PosName: KnockoutObservable<string>;
        constructor(param: any) {
            let self = this;
            self.cId = param.cId;
            self.workId = param.workId;
            self.rptLayoutId = param.rptLayoutId;
            self.programId = param.programId;
            self.screenId = param.screenId;
            self.useApproval = ko.observable(param.useApproval);
            self.availableAprRoot = ko.observable(param.availableAprRoot);
            self.availableAprWork1 = ko.observable(param.availableAprWork1);
            self.availableAprWork2 = ko.observable(param.availableAprWork2);
            self.apr1Sid = ko.observable(param.apr1Sid);
            self.apr1Scd = ko.observable(param.apr1Scd);
            self.apr1BusinessName = ko.observable(param.apr1BusinessName);
            self.app1Devcd = ko.observable(param.app1Devcd);
            self.app1DevName = ko.observable(param.app1DevName);
            self.app1Poscd = ko.observable(param.app1Poscd);
            self.app1PosName = ko.observable(param.app1PosName);
            self.apr2Sid = ko.observable(param.apr2Sid);
            self.apr2Scd = ko.observable(param.apr2Scd);
            self.apr2BusinessName = ko.observable(param.apr2BusinessName);
            self.app2Devcd = ko.observable(param.app2Devcd);
            self.app2DevName = ko.observable(param.app2DevName);
            self.app2Poscd = ko.observable(param.app2Poscd);
            self.app2PosName = ko.observable(param.app2PosName);
        }
        
    }
}
