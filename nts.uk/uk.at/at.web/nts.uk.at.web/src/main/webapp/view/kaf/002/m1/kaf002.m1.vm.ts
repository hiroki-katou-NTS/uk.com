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
            stampAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            stampGoOutAtrList: KnockoutObservableArray<any> = ko.observableArray([]);
            constructor(){
                var self = this;
                self.stampAtr.subscribe(()=>{ self.extendsMode(false); });
                self.extendsMode.subscribe((v)=>{ 
                    if(v){
                        let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                        for(let i=self.supFrameNo+1;i<=10;i++) {
                            self.appStampList.push(new vmbase.AppStampGoOutPermit(self.stampAtr(),i,stampGoOutAtr,0,'',0,'',true, true, true, true));    
                        } 
                    } else {
                        self.appStampList.remove((o) => { return o.stampFrameNo() > self.supFrameNo });   
                    } 
                });        
            }
            
            start(appStampData: any, data: vmbase.StampRequestSettingDto){
                var self = this;    
                self.supFrameNo = data.supFrameDispNO;
                self.stampPlaceDisplay(data.stampPlaceDisp);
                if(!nts.uk.util.isNullOrUndefined(appStampData)){
                    self.appStampList.removeAll();
                    _.forEach(appStampData, item => {
                        self.appStampList.push(
                            new vmbase.AppStampGoOutPermit(
                                item.stampAtr,
                                item.stampFrameNo,
                                item.stampGoOutReason,
                                item.startTime,
                                item.startLocation,
                                item.endTime,
                                item.endLocation, 
                                false, 
                                false, 
                                false, 
                                false
                        ));        
                    });
                }
                if(data.stampAtr_GoOut_Disp==1) self.stampAtrList.push({ code: 1, name: nts.uk.resource.getText('KAF002_31') });
                if(data.stampAtr_Care_Disp==1) self.stampAtrList.push({ code: 2, name: nts.uk.resource.getText('KAF002_32') });
                if(data.stampAtr_Sup_Disp==1) self.stampAtrList.push({ code: 3, name: nts.uk.resource.getText('KAF002_33') });
                self.stampAtr(_.first(self.stampAtrList()).code);
                if(data.stampGoOutAtr_Private_Disp==1) self.stampGoOutAtrList.push({ code: 0, name: nts.uk.resource.getText('KAF002_40') });
                if(data.stampGoOutAtr_Public_Disp==1) self.stampGoOutAtrList.push({ code: 1, name: nts.uk.resource.getText('KAF002_41') });
                if(data.stampGoOutAtr_Compensation_Disp==1) self.stampGoOutAtrList.push({ code: 2, name: nts.uk.resource.getText('KAF002_42') });
                if(data.stampGoOutAtr_Union_Disp==1) self.stampGoOutAtrList.push({ code: 3, name: nts.uk.resource.getText('KAF002_43') });
                let stampGoOutAtr = _.first(self.stampGoOutAtrList()).code;
                for(let i=1;i<=self.supFrameNo;i++) {
                    self.appStampList.push(new vmbase.AppStampGoOutPermit(self.stampAtr(),i,stampGoOutAtr,0,'star',0,'end',true, true, true, true));    
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