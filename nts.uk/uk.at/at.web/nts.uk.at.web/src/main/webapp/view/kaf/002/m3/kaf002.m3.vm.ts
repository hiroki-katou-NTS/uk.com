module nts.uk.at.view.kaf002.m3 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            appStampList: KnockoutObservableArray<vmbase.AppStampCancel> = ko.observableArray([]); 
            supFrameNo: number = 1;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO;
                for(let i=1;i<=self.supFrameNo;i++) {
                    self.appStampList.push(new vmbase.AppStampCancel(4,i,0));    
                } 
                self.stampPlaceDisplay(data.stampPlaceDisp);
                if(!nts.uk.util.isNullOrUndefined(appStampData)){
                    self.appStampList.removeAll();
                    _.forEach(appStampData, item => {
                        self.appStampList.push(
                            new vmbase.AppStampWork(
                                item.stampAtr,
                                item.stampFrameNo,
                                item.stampGoOutReason,
                                item.supportCard,
                                item.supportLocation,
                                false,
                                false,
                                item.startTime,
                                item.startLocation,
                                item.endTime,
                                item.endLocation, 
                                false, 
                                false, 
                                false, 
                                false
                        ));        
                    });
                }
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