module nts.uk.at.view.kaf007.a.viewmodel {
    import common = nts.uk.at.view.kaf007.share.common;
    import service = nts.uk.at.view.kaf007.share.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        appWorkChange: KnockoutObservable<common.AppWorkChangeCommand> = ko.observable(new common.AppWorkChangeCommand());
        isDisplayOpenCmm018:  KnockoutObservable<boolean> = ko.observable(true);
        //A3 事前事後区分:表示/活性
        prePostDisp: KnockoutObservable<boolean> = ko.observable(false);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(false);
        //A5 勤務を変更する:表示/活性
        isWorkChange:   KnockoutObservable<boolean> = ko.observable(true);
        workChangeAtr: KnockoutObservable<boolean> = ko.observable(false);
        workState : KnockoutObservable<boolean> = ko.observable(true);
        typeSiftVisible : KnockoutObservable<boolean> = ko.observable(true);
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        
        //勤務種類
        workTypeCd: KnockoutObservable<string> = ko.observable('');
        workTypeName: KnockoutObservable<string> = ko.observable('');
        //勤務種類
        siftCD: KnockoutObservable<string> = ko.observable('');
        siftName: KnockoutObservable<string> = ko.observable('');
    
        //A8 勤務時間２
        isMultipleTime: KnockoutObservable<boolean> = ko.observable(false);
    
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason : KnockoutObservable<boolean> = ko.observable(false);
        multilContent: KnockoutObservable<string> = ko.observable('');
        //excludeHolidayAtr
        excludeHolidayAtr: KnockoutObservable<boolean> = ko.observable(false);
    
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        employeeID : string ="";
        //menu-bar 
        enableSendMail :KnockoutObservable<boolean> = ko.observable(false); 
    
        dateFormat: string = 'YYYY/MM/DD';
        constructor() {
            let self = this;
            //KAF000_A
            self.kaf000_a = new kaf000.a.viewmodel.ScreenModel();            
            //startPage 009a AFTER start 000_A
            self.startPage().done(function(){
                self.kaf000_a.start(self.employeeID,1,2, moment(new Date()).format(self.dateFormat)).done(function(){
                    self.appWorkChange().appApprovalPhases = self.kaf000_a.approvalList;
                    nts.uk.ui.block.clear();
                })    
            });
            
        }
        /**
         * 
         */
        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            var self = this;
            var dfd = $.Deferred();
            
            //get Common Setting
            service.getWorkChangeCommonSetting().done(function(settingData: any) {
                if(!nts.uk.util.isNullOrEmpty(settingData)){
                    //申請共通設定
                    let appCommonSettingDto = settingData.appCommonSettingDto;
                    //勤務変更申請設定
                    let appWorkChangeCommonSetting = settingData.workChangeSetDto;                
                    
                    //A2_申請者 ID
                    self.employeeID = settingData.sid;
                    //A2_1 申請者
                    self.employeeName(settingData.employeeName);
                    
                    //A3 事前事後区分
                    //事前事後区分 ※A１
                    self.prePostDisp(appCommonSettingDto.applicationSettingDto.displayPrePostFlg == 1 ? true: false);
                    if( !nts.uk.util.isNullOrEmpty(appCommonSettingDto.appTypeDiscreteSettingDtos) && 
                            appCommonSettingDto.appTypeDiscreteSettingDtos.length>0){                         
                        //事前事後区分 Enable ※A２
                        self.prePostEnable(appCommonSettingDto.appTypeDiscreteSettingDtos[0].prePostCanChangeFlg == 1 ? true: false);
                    }
                    //A5 勤務を変更する ※A4                    
                    if(appWorkChangeCommonSetting　!= undefined){
                        /*let wcType: common.WorkChangeType = appWorkChangeCommonSetting.workChangeFlg;
                        switch (wcType) {
                            case WorkChangeType.NotInitSelection:
                            case Weekday.Sunday:
                                return false;
                            default:
                                return true;
                        }*/
                        if(appWorkChangeCommonSetting.workChangeTimeAtr == 0 
                          || appWorkChangeCommonSetting.workChangeTimeAtr == 1){
                            self.isWorkChange(true);
                            if(appWorkChangeCommonSetting.workChangeTimeAtr == 0 ){
                                self.workChangeAtr(false);
                            }else{
                                self.workChangeAtr(true);
                            }                            
                        }else if(appWorkChangeCommonSetting.workChangeTimeAtr == 2){
                            self.isWorkChange(false);
                            self.workChangeAtr(false);
                        }else{
                            self.workChangeAtr(true);
                            self.isWorkChange(true);
                            self.workState(false);
                        }                        
                    }
                    //定型理由
                    self.setReasonControl(settingData.listReasonDto);
                    //申請制限設定.申請理由が必須
                    self.requiredReason(settingData.appCommonSettingDto.applicationSettingDto.requireAppReasonFlg == 1 ? true: false);
                    //A8 勤務時間２ ※A7
                    //共通設定.複数回勤務
                    self.isMultipleTime(settingData.multipleTime);
                    
                }
                dfd.resolve();
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({messageId: res.messageId}).then(function(){ 
                    nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");  
                });
                dfd.reject();
            });
            return dfd.promise();
        }
        
        //勤務変更申請の登録を実行する
        registerClick(){
            let self =this;
            nts.uk.ui.block.invisible();
            self.appWorkChange().application().applicationDate(self.appWorkChange().application().startDate());
            let workChange = ko.toJS(self.appWorkChange());
            service.addWorkChange(workChange).done(() => {
                
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    location.reload();
                });
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { nts.uk.ui.block.clear(); });
            });
        }
        /**
         * set reason 
         */
        setReasonControl(data: Array<common.ReasonDto>) {
            var self = this;
            let comboSource: Array<common.ComboReason> = [];
            _.forEach(data, function(value: common.ReasonDto) {
                self.reasonCombo.push(new common.ComboReason(value.displayOrder, value.reasonTemp, value.reasonID));
                if(value.defaultFlg === 1){
                    self.selectedReason(value.reasonID);
                }
            });
        }

        
        /**
         * KDL003
         */
        openDialogKdl003() {
            let self = this,
            workTypeCodes = [],
            workChange = self.workChange(),
            workTimeCodes = [];
            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: workChange.workTypeCd(),
                workTimeCodes: workTimeCodes,
                selectedWorkTimeCode: workChange.workTimeCd(),
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    
                    workChange.workTypeCd(childData.selectedWorkTypeCode);
                    workChange.workTypeName(childData.selectedWorkTypeName);
                    workChange.workTimeCd(childData.selectedWorkTimeCode);
                    workChange.workTimeName(childData.selectedWorkTimeName);
                }
            })
        }
        
        /**
         * Jump to CMM018 Screen
         */
        openCMM018(){
            let self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: self.employeeID});  
        }
    }
    
}

