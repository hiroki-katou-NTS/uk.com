module nts.uk.at.view.kaf002.m1 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            stampAtr: KnockoutObservable<number> = ko.observable(1);
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampGoOutPermit> = ko.observableArray([
                new vmbase.AppStampGoOutPermit(this.stampAtr(),1,0,0,'start1',0,'end1', true, true, true, true),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),2,0,0,'start2',0,'end2', true, true, true, true),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),3,0,0,'start3',0,'end3', true, true, true, true)
            ]);
            supFrameNo: number = 10;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                var self = this;
                self.stampAtr.subscribe(()=>{ self.extendsMode(false); });
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        for(let i=4;i<=self.supFrameNo;i++) {
                            self.appStampList.push(new vmbase.AppStampGoOutPermit(self.stampAtr(),i,0,0,'',0,'',true, true, true, true));    
                        } 
                    } else {
                        self.appStampList.remove((o) => { return o.stampFrameNo() > 3 });   
                    } 
                });        
            }
            
            start(data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO > 10 ? 10 : data.supFrameDispNO;
                self.stampPlaceDisplay(data.stampPlaceDisp);
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
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
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
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
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