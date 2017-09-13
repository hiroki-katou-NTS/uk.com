module nts.uk.at.view.kaf002.m2 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampWork> = ko.observableArray([
                new vmbase.AppStampWork(4,1,0,'','',0,'',0,''),
                new vmbase.AppStampWork(4,2,0,'','',0,'',0,''),
                new vmbase.AppStampWork(4,3,0,'','',0,'',0,'')
            ]);    
            constructor(){
                var self = this;
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        for(let i=4;i<=10;i++) {
                            self.appStampList.push(new vmbase.AppStampWork(4,i,0,'','',0,'',0,''));    
                        } 
                    } else {
                        self.appStampList.remove((o) => { return o.stampFrameNo() > 2 });   
                    } 
                });        
            }
            
            extendsModeEvent(){
                var self = this;
                self.extendsMode(!self.extendsMode());   
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
                    stampRequestMode: 1,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: ko.mapping.toJS(self.appStampList()),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null  
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
                    stampRequestMode: 1,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: ko.mapping.toJS(self.appStampList()),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null  
                }
                service.update(command);     
            }
        }
    }
}