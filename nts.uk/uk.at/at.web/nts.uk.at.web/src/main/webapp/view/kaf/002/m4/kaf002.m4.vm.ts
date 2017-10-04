module nts.uk.at.view.kaf002.m4 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            appStamp: KnockoutObservable<vmbase.AppStampOnlineRecord> = ko.observable(new vmbase.AppStampOnlineRecord(0,0)); 
            supFrameNo: number = 10;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            stampCombinationList: KnockoutObservableArray<vmbase.StampCombination> = ko.observableArray([]);  
            constructor(){
                
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO;
                self.stampPlaceDisplay(data.stampPlaceDisp);
                service.getStampCombinationAtr().done(data => {
                    let a = [];
                    _.forEach(data, (item, index) => {
                        a.push(new vmbase.StampCombination(index, item.name));        
                    });   
                    self.stampCombinationList(a);
                }).fail(res => {
                    console.log(res);
                });
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
                    stampRequestMode: 3,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: null, 
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: ko.mapping.toJS(self.appStamp()),
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
                    stampRequestMode: 3,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: null, 
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: ko.mapping.toJS(self.appStamp())
                }
                service.update(command);    
            }
        }
    }
}