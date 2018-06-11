module nts.uk.at.view.kaf002.m5 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    import setShared = nts.uk.ui.windows.setShared;
    export module viewmodel {
        export class ScreenModel {
            supFrameNo: number = 1;
            stampPlaceDisplay: KnockoutObservable<number> = ko.observable(0);
            stampAtr: KnockoutObservable<number> = ko.observable(0); 
            appStampList: KnockoutObservableArray<vmbase.AppStampWork> = ko.observableArray([]);
            stampAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            stampGoOutAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            workLocationList: Array<vmbase.IWorkLocation> = [];
            displayItemNo: number = 5;
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto, listWorkLocation: Array<vmbase.IWorkLocation>){
                var self = this;    
                self.workLocationList = listWorkLocation;
                self.supFrameNo = 1;
                self.stampPlaceDisplay(data.stampPlaceDisp);
                if(data.stampAtr_Work_Disp==1) self.stampAtrList.push({ code: 0, name: nts.uk.resource.getText('KAF002_29') });
                if(data.stampAtr_GoOut_Disp==1) self.stampAtrList.push({ code: 1, name: nts.uk.resource.getText('KAF002_31') });
                if(data.stampAtr_Care_Disp==1) self.stampAtrList.push({ code: 2, name: nts.uk.resource.getText('KAF002_32') });
                if(data.stampAtr_Sup_Disp==1) self.stampAtrList.push({ code: 3, name: nts.uk.resource.getText('KAF002_33') });
                self.stampAtrList.push({ code: 4, name: nts.uk.resource.getText('KAF002_34') });
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
                            new vmbase.AppStampWork(
                                item.stampAtr,
                                item.stampFrameNo,
                                item.stampGoOutReason,
                                new vmbase.CheckBoxLocation(item.supportCard,'',true,false),
                                new vmbase.CheckBoxLocation(item.supportLocation,self.findWorkLocationName(item.supportLocation),true,false),
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
                    switch(value){
                        case 0: {
                            self.displayItemNo = 5; 
                            self.refreshData(); 
                            break;
                        }
                        case 1: {
                            self.displayItemNo = 10; 
                            self.refreshData(); 
                            break;
                        }
                        case 2: {
                            self.displayItemNo = 2; 
                            self.refreshData(); 
                            break;
                        }
                        case 3: {
                            self.displayItemNo = 2; 
                            self.refreshData(); 
                            break;
                        }
                        case 4: {
                            self.displayItemNo = 10; 
                            self.refreshData(); 
                            break;
                        }
                        default: break;      
                    }
                });
                nts.uk.ui.block.clear();
            }
            
            refreshData(){
                var self = this;
                let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                self.appStampList.removeAll();
                let a = [];
                for(let i=1;i<=self.displayItemNo;i++){
                    a.push(
                        new vmbase.AppStampWork(
                            self.stampAtr(),
                            i,
                            stampGoOutAtr,
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(null,true,false),
                            new vmbase.CheckBoxLocation('','',true,false),
                            new vmbase.CheckBoxTime(null,true,false),
                            new vmbase.CheckBoxLocation('','',true,false))
                    );    
                };
                self.appStampList(a);    
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
                    stampRequestMode: 4,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: _.filter(
                                                _.map(self.appStampList(), (item) => self.convertToJS(item)), 
                                                o => { return !nts.uk.util.isNullOrEmpty(o.startTime)||
                                                              !nts.uk.util.isNullOrEmpty(o.startLocation)||
                                                              !nts.uk.util.isNullOrEmpty(o.endTime)}),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null
                }
                if(nts.uk.util.isNullOrEmpty(command.appStampWorkCmds)){
                    $('.m5-time-editor').first().ntsError('set', {messageId:"Msg_308"});
                } else {
                    nts.uk.ui.block.invisible();
                    service.insert(command)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            if(data.autoSendMail){
                                nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: data.autoSuccessMail }).then(() => {
                                    location.reload();
                                });    
                            } else {
                                if(self.checkBoxValue()){
                                    let command = {appID: data.appID};
                                    setShared("KDL030_PARAM", command);
                                    nts.uk.ui.windows.sub.modal("/view/kdl/030/a/index.xhtml").onClosed(() => {
                                        location.reload();
                                    });    
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
                    stampRequestMode: 4,
                    appStampGoOutPermitCmds: null,
                    appStampWorkCmds: _.map(self.appStampList(), (item) => self.convertToJS(item)),
                    appStampCancelCmds: null,
                    appStampOnlineRecordCmd: null
                }
                if(!nts.uk.util.isNullOrEmpty(command.appStampWorkCmds)){
                    nts.uk.ui.block.invisible();
                    service.update(command)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            if(data.autoSendMail){
                                nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: data.autoSuccessMail }).then(() => {
                                    location.reload();
                                });    
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
            
            convertToJS(appStamp: KnockoutObservable<vmbase.AppStampWork>){
                return {
                    stampAtr: appStamp.stampAtr(),
                    stampFrameNo: appStamp.stampFrameNo(),
                    stampGoOutAtr: appStamp.stampGoOutAtr(),
                    supportCard: appStamp.supportCard().code(),
                    supportLocation: appStamp.supportLocation().code(),
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
            
            openSelectCardDialog(frameNo: number){
                // alert('KDL018');
            }
        }
    }
}