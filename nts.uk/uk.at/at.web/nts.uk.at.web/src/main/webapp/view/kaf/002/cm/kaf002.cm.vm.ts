module nts.uk.at.view.kaf002.cm {
    export module viewmodel {
        import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
        import kaf002 = nts.uk.at.view.kaf002;
        let __viewContext: any = window["__viewContext"] || {};
        export class ScreenModel {
            m1: kaf002.m1.viewmodel.ScreenModel;
            m2: kaf002.m2.viewmodel.ScreenModel;
            m3: kaf002.m3.viewmodel.ScreenModel;
            m4: kaf002.m4.viewmodel.ScreenModel;
            m5: kaf002.m5.viewmodel.ScreenModel;
            stampRequestMode: KnockoutObservable<number> = ko.observable(0);
            screenMode: KnockoutObservable<number> = ko.observable(0);
            resultDisplay: KnockoutObservable<number> = ko.observable(0);
            application: KnockoutObservable<vmbase.Application> = ko.observable(new vmbase.Application('',moment(new Date()).format("YYYY/MM/20"),'',moment(new Date()).format("YYYY/MM/20"),'','',''));
            inputReasons: KnockoutObservableArray<vmbase.InputReason> = ko.observableArray([new vmbase.InputReason('','')]);
            currentReason: KnockoutObservable<vmbase.InputReason> = ko.observable('');
            topComment: KnockoutObservable<vmbase.CommentUI> = ko.observable(new vmbase.CommentUI('','',0)); 
            botComment: KnockoutObservable<vmbase.CommentUI> = ko.observable(new vmbase.CommentUI('','',0));
            approvalList: Array<vmbase.AppApprovalPhase> = [];
            constructor(stampRequestMode: number, screenMode: number){
                var self = this;
                self.stampRequestMode(stampRequestMode);
                self.screenMode(screenMode);
                switch(self.stampRequestMode()){
                    case 0: self.m1 = new kaf002.m1.viewmodel.ScreenModel();break;    
                    case 1: self.m2 = new kaf002.m2.viewmodel.ScreenModel();break;  
                    case 2: self.m3 = new kaf002.m3.viewmodel.ScreenModel();break; 
                    case 3: self.m4 = new kaf002.m4.viewmodel.ScreenModel();break; 
                    case 4: self.m5 = new kaf002.m5.viewmodel.ScreenModel();break; 
                    default: break;
                } 
                
            }
            start(data: vmbase.AppStampNewSetDto, approvalList: Array<vmbase.AppApprovalPhase>){
                var self = this;
                self.resultDisplay(data.appStampSetDto.stampRequestSettingDto.resultDisp);
                self.application().appDate(data.appCommonSettingDto.generalDate);
                self.inputReasons.removeAll();
                let inputReasonParams = [];
                _.forEach(data.appStampSetDto.applicationReasonDtos, o => {
                    inputReasonParams.push(new vmbase.InputReason(o.reasonID, o.reasonTemp));           
                });
                self.inputReasons(inputReasonParams);
                self.currentReason(_.first(self.inputReasons()).id);
                self.topComment().text(data.appStampSetDto.stampRequestSettingDto.topComment);
                self.topComment().color(data.appStampSetDto.stampRequestSettingDto.topCommentFontColor);
                self.topComment().fontWeight(data.appStampSetDto.stampRequestSettingDto.topCommentFontWeight);
                self.botComment().text(data.appStampSetDto.stampRequestSettingDto.bottomComment);
                self.botComment().color(data.appStampSetDto.stampRequestSettingDto.bottomCommentFontColor);
                self.botComment().fontWeight(data.appStampSetDto.stampRequestSettingDto.bottomCommentFontWeight);
                switch(self.stampRequestMode()){
                    case 0: self.m1.start(data.appStampSetDto.stampRequestSettingDto);break;    
                    case 1: self.m2.start(data.appStampSetDto.stampRequestSettingDto);break;  
                    case 2: self.m3.start(data.appStampSetDto.stampRequestSettingDto);break; 
                    case 3: self.m4.start(data.appStampSetDto.stampRequestSettingDto);break; 
                    case 4: self.m5.start(data.appStampSetDto.stampRequestSettingDto);break; 
                    default: break;
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
                self.application().titleReason(_.find(self.inputReasons(), o => o.id = self.currentReason()).id);
                switch(self.stampRequestMode()){
                    case 0: self.m1.register(self.application(), self.approvalList);break;    
                    case 1: self.m2.register(self.application(), self.approvalList);break;  
                    case 2: self.m3.register(self.application(), self.approvalList);break; 
                    case 3: self.m4.register(self.application(), self.approvalList);break; 
                    case 4: self.m5.register(self.application(), self.approvalList);break; 
                    default: break;
                }    
            }
            
            update(){
                var self = this;
                self.application().titleReason(_.find(self.inputReasons(), o => o.id = self.currentReason()).id);
                switch(self.stampRequestMode()){
                    case 0: self.m1.update(self.application(), self.approvalList);break;    
                    case 1: self.m2.update(self.application());break;  
                    case 2: self.m3.update(self.application());break; 
                    case 3: self.m4.update(self.application());break; 
                    case 4: self.m5.update(self.application());break;  
                    default: break;
                }    
            }
            
        }
    }
}