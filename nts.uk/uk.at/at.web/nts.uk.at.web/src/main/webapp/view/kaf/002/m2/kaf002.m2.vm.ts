module nts.uk.at.view.kaf002.m2 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampWork> = ko.observableArray([]);    
            supFrameNo: number = 1;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                var self = this;
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        for(let i=self.supFrameNo+1;i<=10;i++) {
                            self.appStampList.push(
                                new vmbase.AppStampWork(
                                    0,
                                    i,
                                    0,
                                    new vmbase.CheckBoxLocation('','',true,false),
                                    new vmbase.CheckBoxLocation('','',true,false),
                                    new vmbase.CheckBoxTime(0,true,false),
                                    new vmbase.CheckBoxLocation('','',true,false),
                                    new vmbase.CheckBoxTime(0,true,false),
                                    new vmbase.CheckBoxLocation('','',true,false)));    
                        } 
                    } else {
                        self.appStampList.remove((o) => { return o.stampFrameNo() > self.supFrameNo });   
                    } 
                });        
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO;
                for(let i=1;i<=self.supFrameNo;i++) {
                    self.appStampList.push(
                        new vmbase.AppStampWork(
                            0,
                            i,
                            0,
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(0,true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(0,true,false),
                            new vmbase.CheckBoxLocation('','',true,false)));    
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
                                new vmbase.CheckBoxLocation(item.supportCard,'',true,false),
                                new vmbase.CheckBoxLocation(item.supportLocation,'',true,false),
                                new vmbase.CheckBoxTime(item.startTime,true,false),
                                new vmbase.CheckBoxLocation(item.startLocation,'',true,false),
                                new vmbase.CheckBoxTime(item.endTime,true,false),
                                new vmbase.CheckBoxLocation(item.endLocation,'',true,false) 
                        ));        
                    });
                }
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
                service.insert(command)
                .done(() => {})
                .fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                }); 
            }
            
            update(application : vmbase.Application){
                var self = this;
                let command = {
                    version: application.version,
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
                service.update(command)
                .done(() => {})
                .fail(function(res) { 
                    if(res.optimisticLock == true){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function(){nts.uk.ui.block.clear();});    
                    } else {
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});    
                    }
                });  
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