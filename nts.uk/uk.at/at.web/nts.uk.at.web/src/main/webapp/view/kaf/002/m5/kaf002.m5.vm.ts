module nts.uk.at.view.kaf002.m5 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            supFrameNo: number = 7;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            stampAtr: KnockoutObservable<number> = ko.observable(0); 
            appStampList: KnockoutObservableArray<vmbase.AppStampWork> = ko.observableArray([]);
            constructor(){
                var self = this; 
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = 10;
                self.stampPlaceDisplay(data.stampPlaceDisp);
                self.refreshData();
            }
            
            refreshData(){
                var self = this;
                self.appStampList.removeAll();
                let a = [];
                for(let i=1;i<=self.supFrameNo;i++){
                    a.push(new vmbase.AppStampWork(this.stampAtr(),i,0,'spCode','spLocation',true,true,0,'start',0,'end',true,true,true,true));    
                };
                self.appStampList(a);    
            }
            
            register(application : vmbase.Application, approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                let command = {
                    appID: "",
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 1,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: ko.mapping.toJS(self.appStampList()),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList   
                }
                service.insert(command);    
            }
            
            update(application : vmbase.Application){
                var self = this;
                let command = {
                    appID: "",
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 1,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: ko.mapping.toJS(self.appStampList()),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null  
                }
                service.update(command);     
            }
            
            openSelectLocationDialog(timeType: string, frameNo: number){
                var self = this;
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', false);
                nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("KDL010workLocation")!=null){
                        let workLocation = nts.uk.ui.windows.getShared("KDL010workLocation");
                        self.appStampList()[frameNo][timeType+'Location'](workLocation);     
                    }
                });      
            }
            
            openSelectCardDialog(frameNo: number){
                alert('KDL018');
            }
        }
    }
}