module nts.uk.at.view.kaf002.m1 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            stampAtr: KnockoutObservable<number> = ko.observable(0);
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampGoOutPermit> = ko.observableArray([
                new vmbase.AppStampGoOutPermit(this.stampAtr(),1,0,0,'',0,''),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),2,0,0,'',0,''),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),3,0,0,'',0,'')
            ]);
            
            constructor(){
                var self = this;
                self.stampAtr.subscribe(()=>{ self.extendsMode(false); });
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        for(let i=4;i<=10;i++) {
                            self.appStampList.push(new vmbase.AppStampGoOutPermit(self.stampAtr(),i,0,0,'',0,''));    
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
                    stampRequestMode: 0,
                    appStampGoOutPermitCmds: ko.mapping.toJS(self.appStampList()),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null   
                }
                service.insert(command);   
            }
            
            update(application : vmbase.Application){
                var self = this;
                let command = {
                    appID: "f49b73a6-a3ff-4db5-938a-51435a34cb85",
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.applicationDate(),
                    applicationReason: application.applicationReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 0,
                    appStampGoOutPermitCmds: ko.mapping.toJS(self.appStampList()),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null   
                }
                service.update(command);  
            }
        }
    }
}