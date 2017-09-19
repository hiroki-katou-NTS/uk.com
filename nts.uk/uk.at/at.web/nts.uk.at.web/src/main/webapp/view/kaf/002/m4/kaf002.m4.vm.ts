module nts.uk.at.view.kaf002.m4 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            appStamp: KnockoutObservable<vmbase.AppStampOnlineRecord> = ko.observable(new vmbase.AppStampOnlineRecord(0,0)); 
            constructor(){
                
            }
            
            register(application : vmbase.Application){
                var self = this;
                let command = {
                    appID: "",
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.applicationDate(),
                    applicationReason: application.applicationReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 3,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: null, 
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: ko.mapping.toJS(self.appStamp())
                }
                service.insert(command);     
            }
            
            update(application : vmbase.Application){
                var self = this;
                let command = {
                    appID: application.applicationID,
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.applicationDate(),
                    applicationReason: application.applicationReason(),
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