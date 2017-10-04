module nts.uk.at.view.kaf002.m2 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampWork> = ko.observableArray([
                new vmbase.AppStampWork(0,1,0,'spCode1','spLocation1',true,true,0,'start1',0,'end1',true,true,true,true),
                new vmbase.AppStampWork(0,2,0,'spCode2','spLocation2',true,true,0,'start2',0,'end2',true,true,true,true),
                new vmbase.AppStampWork(0,3,0,'spCode3','spLocation3',true,true,0,'start3',0,'end3',true,true,true,true)
            ]);    
            supFrameNo: number = 10;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                var self = this;
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        for(let i=4;i<=self.supFrameNo;i++) {
                            self.appStampList.push(new vmbase.AppStampWork(0,i,0,'','',true,true,0,'',0,'',true,true,true,true));    
                        } 
                    } else {
                        self.appStampList.remove((o) => { return o.stampFrameNo() > 3 });   
                    } 
                });        
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
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
                    appID: application.applicationID,
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
        }
    }
}