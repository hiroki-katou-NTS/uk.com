module nts.uk.at.view.kaf002.m3 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            appStampList: KnockoutObservableArray<vmbase.AppStampCancel> = ko.observableArray([
                new vmbase.AppStampCancel(4,1,0),
                new vmbase.AppStampCancel(4,2,0),
                new vmbase.AppStampCancel(4,3,0),
                new vmbase.AppStampCancel(4,4,0),
                new vmbase.AppStampCancel(4,5,0),
                new vmbase.AppStampCancel(4,6,0),
                new vmbase.AppStampCancel(4,7,0),
                new vmbase.AppStampCancel(4,8,0),
                new vmbase.AppStampCancel(4,9,0),
                new vmbase.AppStampCancel(4,10,0)
            ]); 
            supFrameNo: number = 10;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO;
                self.stampPlaceDisplay(data.stampPlaceDisp);
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
                    stampRequestMode: 2,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: null, 
                    appStampCancelCmds: ko.mapping.toJS(self.appStampList()),
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList   
                }
                service.insert(command);
            }
            
            update(application : vmbase.Application){
                var self = this;
                let command = {
                    appID: application.applicationID,
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 2,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: null, 
                    appStampCancelCmds: ko.mapping.toJS(self.appStampList()),
                    appStampOnlineRecordCmd: null  
                }
                service.update(command);   
            }
        }
    }
}