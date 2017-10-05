module nts.uk.at.view.kaf002.m1 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            stampAtr: KnockoutObservable<number> = ko.observable(1);
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampGoOutPermit> = ko.observableArray([
                new vmbase.AppStampGoOutPermit(this.stampAtr(),1,0,0,'sta1',0,'end1', true, true, true, true),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),2,0,0,'sta2',0,'end2', true, true, true, true),
                new vmbase.AppStampGoOutPermit(this.stampAtr(),3,0,0,'sta3',0,'end3', true, true, true, true)
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
                    stampRequestMode: 0,
                    appStampGoOutPermitCmds: ko.mapping.toJS(self.appStampList()),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList   
                }
                service.insert(command);   
            }
            
            update(application : vmbase.Application, approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                let command = {
                    appID: "29d3a60f-3542-4843-9a8e-dd5285b2f743",
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
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList    
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
        }
    }
}