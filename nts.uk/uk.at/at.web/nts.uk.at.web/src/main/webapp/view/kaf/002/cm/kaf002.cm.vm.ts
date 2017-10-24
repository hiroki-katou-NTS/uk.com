module nts.uk.at.view.kaf002.cm {
    export module viewmodel {
        import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
        import service = nts.uk.at.view.kaf002.shr.service;
        import kaf002 = nts.uk.at.view.kaf002;
        export class ScreenModel {
            m1: kaf002.m1.viewmodel.ScreenModel;
            m2: kaf002.m2.viewmodel.ScreenModel;
            m3: kaf002.m3.viewmodel.ScreenModel;
            m4: kaf002.m4.viewmodel.ScreenModel;
            m5: kaf002.m5.viewmodel.ScreenModel;
            stampRequestMode: KnockoutObservable<number> = ko.observable(0);
            screenMode: KnockoutObservable<number> = ko.observable(0);
            resultDisplay: KnockoutObservable<number> = ko.observable(0);
            application: KnockoutObservable<vmbase.Application> = ko.observable(new vmbase.Application('',moment(new Date()).format("YYYY/MM/DD"),'',moment(new Date()).format("YYYY/MM/DD"),'','','',0));
            inputReasons: KnockoutObservableArray<vmbase.InputReason> = ko.observableArray([new vmbase.InputReason('','')]);
            currentReason: KnockoutObservable<string> = ko.observable('');
            inputReasonsDisp: KnockoutObservable<number> = ko.observable(0);
            detailReasonDisp: KnockoutObservable<number> = ko.observable(0);
            topComment: KnockoutObservable<vmbase.CommentUI> = ko.observable(new vmbase.CommentUI('','',0)); 
            botComment: KnockoutObservable<vmbase.CommentUI> = ko.observable(new vmbase.CommentUI('','',0));
            approvalList: Array<vmbase.AppApprovalPhase> = [];
            employeeName: KnockoutObservable<string> = ko.observable("");
            constructor(stampRequestMode: number, screenMode: number){
                var self = this;
                self.stampRequestMode(stampRequestMode);
                self.screenMode(screenMode);
                switch(self.stampRequestMode()){
                    case 0: self.m1 = new kaf002.m1.viewmodel.ScreenModel(); break;
                    case 1: self.m2 = new kaf002.m2.viewmodel.ScreenModel(); break;
                    case 2: self.m3 = new kaf002.m3.viewmodel.ScreenModel(); break;
                    case 3: self.m4 = new kaf002.m4.viewmodel.ScreenModel(); break;
                    case 4: self.m5 = new kaf002.m5.viewmodel.ScreenModel(); break;
                    default: break;
                }    
            }
            start(commonSet: vmbase.AppStampNewSetDto, appStampData: any, approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                self.inputReasonsDisp(commonSet.appCommonSettingDto.appTypeDiscreteSettingDtos[0].typicalReasonDisplayFlg);
                self.detailReasonDisp(commonSet.appCommonSettingDto.appTypeDiscreteSettingDtos[0].displayReasonFlg);
                self.resultDisplay(commonSet.appStampSetDto.stampRequestSettingDto.resultDisp);
                self.inputReasons.removeAll();
                let inputReasonParams = [];
                _.forEach(commonSet.appStampSetDto.applicationReasonDtos, o => {
                    inputReasonParams.push(new vmbase.InputReason(o.reasonID, o.reasonTemp));           
                });
                self.inputReasons(inputReasonParams);
                self.topComment().text(commonSet.appStampSetDto.stampRequestSettingDto.topComment);
                self.topComment().color(commonSet.appStampSetDto.stampRequestSettingDto.topCommentFontColor);
                self.topComment().fontWeight(commonSet.appStampSetDto.stampRequestSettingDto.topCommentFontWeight);
                self.botComment().text(commonSet.appStampSetDto.stampRequestSettingDto.bottomComment);
                self.botComment().color(commonSet.appStampSetDto.stampRequestSettingDto.bottomCommentFontColor);
                self.botComment().fontWeight(commonSet.appStampSetDto.stampRequestSettingDto.bottomCommentFontWeight);
                service.findAllWorkLocation().done((listWorkLocation: Array<vmbase.IWorkLocation>)=>{
                    $('.cm-memo').focus();
                    switch(self.stampRequestMode()){
                        case 0: self.m1.start(appStampData.appStampGoOutPermitCmds, commonSet.appStampSetDto.stampRequestSettingDto, listWorkLocation);break;    
                        case 1: self.m2.start(appStampData.appStampWorkCmds, commonSet.appStampSetDto.stampRequestSettingDto, listWorkLocation);break;  
                        case 2: self.m3.start(appStampData.appStampCancelCmds, commonSet.appStampSetDto.stampRequestSettingDto, listWorkLocation);break; 
                        case 3: self.m4.start(appStampData.appStampOnlineRecordCmd, commonSet.appStampSetDto.stampRequestSettingDto, listWorkLocation);break; 
                        case 4: self.m5.start(appStampData.appStampWorkCmds, commonSet.appStampSetDto.stampRequestSettingDto, listWorkLocation);break; 
                        default: break;
                    }     
                });
                if(self.screenMode()==0){
                    if(!nts.uk.util.isNullOrUndefined(appStampData)) {
                        self.application(new vmbase.Application(
                            appStampData.appID,
                            appStampData.inputDate,
                            appStampData.enteredPerson,
                            appStampData.applicationDate,
                            appStampData.titleReason,
                            appStampData.detailReason,
                            appStampData.employeeID,
                            appStampData.version
                        ));
                    }
                    self.stampRequestMode(appStampData.stampRequestMode);
                    self.employeeName(appStampData.employeeName);
                    self.currentReason(self.application().titleReason());
                } else {
                    self.application().appDate(commonSet.appCommonSettingDto.generalDate);    
                    self.employeeName(commonSet.employeeName);
                    if(self.inputReasonsDisp() == 1 && self.inputReasons().length != 0){
                        self.currentReason(_.first(self.inputReasons()).id);
                    }
                }
                _.forEach(approvalList, appPhase => {
                    _.forEach(appPhase.approverDtos, appFrame => {
                        _.forEach(appFrame.approveAcceptedCmds, appAccepted => {
                            appAccepted.approvalDate = self.application().appDate();
                        });    
                    }); 
                });
                self.approvalList = approvalList;
            }
            
            register(){
                var self = this;
                self.application().titleReason(self.currentReason());
                switch(self.stampRequestMode()){
                    case 0: self.m1.register(self.application(), self.approvalList);break;    
                    case 1: self.m2.register(self.application(), self.approvalList);break;  
                    case 2: self.m3.register(self.application(), self.approvalList);break; 
                    case 3: self.m4.register(self.application(), self.approvalList);break; 
                    case 4: self.m5.register(self.application(), self.approvalList);break; 
                    default: break;
                }    
            }
            
            update(approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                self.application().titleReason(self.currentReason());
                switch(self.stampRequestMode()){
                    case 0: self.m1.update(self.application(), approvalList);break;    
                    case 1: self.m2.update(self.application(), approvalList);break;  
                    case 2: self.m3.update(self.application(), approvalList);break; 
                    case 3: self.m4.update(self.application(), approvalList);break; 
                    case 4: self.m5.update(self.application(), approvalList);break;  
                    default: break;
                }    
            }
            
        }
    }
}