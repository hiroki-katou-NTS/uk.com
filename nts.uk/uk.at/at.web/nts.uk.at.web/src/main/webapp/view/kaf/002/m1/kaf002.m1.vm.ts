module nts.uk.at.view.kaf002.m1 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            stampAtr: KnockoutObservable<number> = ko.observable(1);
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            appStampList: KnockoutObservableArray<vmbase.AppStampGoOutPermit> = ko.observableArray([]);
            supFrameNo: number = 1;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            stampAtrList: KnockoutObservableArray<vmbase.SimpleObject> = ko.observableArray([]);
            stampGoOutAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            workLocationList: Array<vmbase.IWorkLocation> = [];
            displayAllLabel: KnockoutObservable<string> = ko.observable(''); 
            constructor(){
                var self = this;
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                        for(let i=self.supFrameNo+1;i<=10;i++) {
                            self.appStampList.push(
                                new vmbase.AppStampGoOutPermit(
                                    self.stampAtr(),
                                    i,
                                    stampGoOutAtr,
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
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto, listWorkLocation: Array<vmbase.IWorkLocation>){
                var self = this;    
                self.workLocationList = listWorkLocation;
                self.supFrameNo = data.supFrameDispNO;
                self.stampPlaceDisplay(data.stampPlaceDisp);
                if(data.stampAtr_GoOut_Disp==1) self.stampAtrList.push({ code: 1, name: nts.uk.resource.getText('KAF002_31') });
                if(data.stampAtr_Care_Disp==1) self.stampAtrList.push({ code: 2, name: nts.uk.resource.getText('KAF002_32') });
                if(data.stampAtr_Sup_Disp==1) self.stampAtrList.push({ code: 3, name: nts.uk.resource.getText('KAF002_33') });
                self.stampAtr(_.first(self.stampAtrList()).code);
                if(data.stampGoOutAtr_Private_Disp==1) self.stampGoOutAtrList.push({ code: 0, name: nts.uk.resource.getText('KAF002_40') });
                if(data.stampGoOutAtr_Public_Disp==1) self.stampGoOutAtrList.push({ code: 1, name: nts.uk.resource.getText('KAF002_41') });
                if(data.stampGoOutAtr_Compensation_Disp==1) self.stampGoOutAtrList.push({ code: 2, name: nts.uk.resource.getText('KAF002_42') });
                if(data.stampGoOutAtr_Union_Disp==1) self.stampGoOutAtrList.push({ code: 3, name: nts.uk.resource.getText('KAF002_43') });
                let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                self.displayAllLabel(nts.uk.resource.getText("KAF002_13", nts.uk.resource.getText('KAF002_31')));
                self.appStampList.removeAll();
                for(let i=1;i<=self.supFrameNo;i++) {
                    self.appStampList.push(
                        new vmbase.AppStampGoOutPermit(
                            self.stampAtr(),
                            i,
                            stampGoOutAtr,
                            new vmbase.CheckBoxTime(0,true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(0,true,false),
                            new vmbase.CheckBoxLocation('','',true,false)));    
                } 
                if(!nts.uk.util.isNullOrUndefined(appStampData)){
                    self.appStampList.removeAll();
                    _.forEach(appStampData, item => {
                        self.appStampList.push(
                            new vmbase.AppStampGoOutPermit(
                                item.stampAtr,
                                item.stampFrameNo,
                                item.stampGoOutReason,
                                new vmbase.CheckBoxTime(item.startTime,true,false),
                                new vmbase.CheckBoxLocation(item.startLocation,self.findWorkLocationName(item.startLocation),true,false),
                                new vmbase.CheckBoxTime(item.endTime,true,false),
                                new vmbase.CheckBoxLocation(item.endLocation,self.findWorkLocationName(item.endLocation),true,false) 
                        ));      
                        self.stampAtr(item.stampAtr);  
                    });
                }
                self.stampAtr.subscribe((value)=>{ 
                    self.appStampList.removeAll();
                    let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                    for(let i=1;i<=self.supFrameNo;i++) {
                        self.appStampList.push(
                            new vmbase.AppStampGoOutPermit(
                                value,
                                i,
                                stampGoOutAtr,
                                new vmbase.CheckBoxTime(0,true,false),
                                new vmbase.CheckBoxLocation('','',true,false),
                                new vmbase.CheckBoxTime(0,true,false),
                                new vmbase.CheckBoxLocation('','',true,false)));    
                    } 
                    switch(value){
                        case 1: self.displayAllLabel(nts.uk.resource.getText("KAF002_13", nts.uk.resource.getText('KAF002_31'))); break;
                        case 2: self.displayAllLabel(nts.uk.resource.getText("KAF002_13", nts.uk.resource.getText('KAF002_32'))); break;
                        case 3: self.displayAllLabel(nts.uk.resource.getText("KAF002_13", nts.uk.resource.getText('KAF002_33'))); break;
                        default: break;    
                    }
                });
            }
            
            extendsModeEvent(){
                var self = this;
                self.extendsMode(!self.extendsMode());   
            }
            
            findWorkLocationName(workLocationCD: string): string {
                var self = this;
                let workLocationObject: any = _.find(self.workLocationList, item => { return item.workLocationCD == workLocationCD });
                if(nts.uk.util.isNullOrUndefined(workLocationObject)){
                    return "";        
                } else {
                    return workLocationObject.workLocationName;
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
                    stampRequestMode: 0,
                    appStampGoOutPermitCmds: _.map(self.appStampList(), (item) => self.convertToJS(item)),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList   
                }
                service.insert(command)
                .done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                        $('.cm-memo').focus();
                        nts.uk.ui.block.clear();
                    });    
                })
                .fail(function(res) { 
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId}).then(function(){nts.uk.ui.block.clear();});
                });  
            }
            
            update(application : vmbase.Application, approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                let command = {
                    version: application.version,
                    appID: application.applicationID(),
                    inputDate: application.inputDate(),
                    enteredPerson: application.enteredPerson(),
                    applicationDate: application.appDate(),
                    titleReason: application.titleReason(), 
                    detailReason: application.contentReason(),
                    employeeID: application.employeeID(),
                    stampRequestMode: 0,
                    appStampGoOutPermitCmds: _.map(self.appStampList(), (item) => self.convertToJS(item)),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null,
                    appApprovalPhaseCmds: approvalList    
                }
                service.update(command)
                .done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                        $('.cm-memo').focus();
                        nts.uk.ui.block.clear();
                    });     
                })
                .fail(function(res) { 
                    if(res.optimisticLock == true){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function(){nts.uk.ui.block.clear();});    
                    } else {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId}).then(function(){nts.uk.ui.block.clear();});    
                    }
                });  
            }
            
            convertToJS(appStamp: KnockoutObservable<vmbase.AppStampGoOutPermit>){
                return {
                    stampAtr: appStamp.stampAtr(),
                    stampFrameNo: appStamp.stampFrameNo(),
                    stampGoOutAtr: appStamp.stampGoOutAtr(),
                    startTime: appStamp.startTime().value(),
                    startLocation: appStamp.startLocation().code(),
                    endTime: appStamp.endTime().value(),
                    endLocation: appStamp.endLocation().code()    
                }           
            }
            
            openSelectLocationDialog(timeType: string, frameNo: number){
                var self = this;
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.appStampList()[frameNo][timeType+'Location']().code());
                nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("KDL010workLocation")!=null){
                        let workLocation = nts.uk.ui.windows.getShared("KDL010workLocation");
                        self.appStampList()[frameNo][timeType+'Location']().code(workLocation);   
                        self.appStampList()[frameNo][timeType+'Location']().name(self.findWorkLocationName(workLocation));   
                    }
                });      
            }
        }
    }
}