module nts.uk.at.view.kaf002.m1 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    import setShared = nts.uk.ui.windows.setShared;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    export module viewmodel {
        export class ScreenModel {
            stampAtr: KnockoutObservable<number> = ko.observable(1);
            extendsMode: KnockoutObservable<boolean> = ko.observable(false);
            extendsModeDisplay: KnockoutObservable<boolean> = ko.observable(true);
            appStampList: KnockoutObservableArray<vmbase.AppStampGoOutPermit> = ko.observableArray([]);
            supFrameNo: number = 3;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            stampAtrList: KnockoutObservableArray<vmbase.SimpleObject> = ko.observableArray([]);
            stampGoOutAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            workLocationList: Array<vmbase.IWorkLocation> = [];
            displayAllLabel: string = nts.uk.resource.getText("KAF002_13", nts.uk.resource.getText('KAF002_31'));
            displayItemNo: number = this.supFrameNo;
            constructor(){
                var self = this;
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        self.refreshData();
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
                self.refreshData();
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
                    nts.uk.ui.errors.clearAll();
                    if(value == 1){
                        self.displayItemNo = self.extendsMode() ? 10 : self.supFrameNo;   
                        self.extendsModeDisplay(!self.extendsMode() && (self.stampAtr() == 1));      
                    } else {
                        self.displayItemNo = 2;
                        self.extendsModeDisplay(!self.extendsMode() && (self.stampAtr() == 1)); 
                    }
                    self.refreshData();
                });
                nts.uk.ui.block.clear();
            }
            
            extendsModeEvent(){
                var self = this;
                self.displayItemNo = 10;
                self.extendsMode(!self.extendsMode());    
                self.extendsModeDisplay(!self.extendsMode() && (self.stampAtr() == 1)); 
            }
            
            refreshData(){
                var self = this;
                let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                self.appStampList.removeAll();
                for(let i=1;i<=self.displayItemNo;i++) {
                    self.appStampList.push(
                        new vmbase.AppStampGoOutPermit(
                            self.stampAtr(),
                            i,
                            stampGoOutAtr,
                            new vmbase.CheckBoxTime(null,true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(null,true,false),
                            new vmbase.CheckBoxLocation('','',true,false)));    
                }     
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
            
            register(application : vmbase.Application, checkBoxValue: boolean){
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
                    appStampGoOutPermitCmds: _.filter(
                                                    _.map(self.appStampList(), (item) => self.convertToJS(item)), 
                                                    o => { return !nts.uk.util.isNullOrEmpty(o.startTime)||
                                                                  !nts.uk.util.isNullOrEmpty(o.startLocation)||
                                                                  !nts.uk.util.isNullOrEmpty(o.endTime)}),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null  
                }
                if(nts.uk.util.isNullOrEmpty(command.appStampGoOutPermitCmds)){
                    $('.m1-time-editor').first().ntsError('set', {messageId:"Msg_308"});        
                } else {
                    nts.uk.ui.block.invisible();
                    service.insert(command)
                    .done((data) => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            if(data.autoSendMail){
                                appcommon.CommonProcess.displayMailResult(data);    
                            } else {
                                if(checkBoxValue){
                                    appcommon.CommonProcess.openDialogKDL030(data.appID);   
                                } else {
                                    location.reload();
                                }   
                            }
                        });    
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();});    
                    });  
                }
            }
            
            update(application : vmbase.Application){
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
                    appStampGoOutPermitCmds: _.filter(
                                                    _.map(self.appStampList(), (item) => self.convertToJS(item)), 
                                                    o => { return !nts.uk.util.isNullOrEmpty(o.startTime)||
                                                                  !nts.uk.util.isNullOrEmpty(o.startLocation)||
                                                                  !nts.uk.util.isNullOrEmpty(o.endTime)}),
                    appStampWorkCmds: null,
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null 
                }
                if(!nts.uk.util.isNullOrEmpty(command.appStampGoOutPermitCmds)){
                    nts.uk.ui.block.invisible();
                    service.update(command)
                    .done((data) => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            if(data.autoSendMail){
                                appcommon.CommonProcess.displayMailResult(data);    
                            } else {
                                location.reload();
                            }
                        });     
                    })
                    .fail(function(res) { 
                        if(res.optimisticLock == true){
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function(){
                                location.reload();
                            });    
                        } else {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();}); 
                        }
                    });  
                }
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