module nts.uk.at.view.kaf002.b {
    import service = nts.uk.at.view.kaf002.shr.service;
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase; 
    export module viewmodel {
        const employmentRootAtr: number = 1; // EmploymentRootAtr: Application
        const applicationType: number = 7; // Application Type: Stamp Application
        export class ScreenModel {
            cm: kaf002.cm.viewmodel.ScreenModel;
            kaf000_a2: kaf000.a.viewmodel.ScreenModel;
            stampRequestMode: number = 0;
            screenMode: number = 0;
            employeeID: string = '';
            date: any = moment(new Date()).format("YYYY/MM/DD");
            enableSendMail: KnockoutObservable<boolean> = ko.observable(false);
            checkBoxValue: KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                var self = this;
                __viewContext.transferred.ifPresent(data => {
                    if(!nts.uk.util.isNullOrUndefined(data.appDate)){
                        self.date = moment(data.appDate).format("YYYY/MM/DD");
                    }
                    if(!nts.uk.util.isNullOrEmpty(data.employeeIDs)){
                        self.employeeID = data.employeeIDs[0];
                    }
                    self.stampRequestMode = data.stampRequestMode;
                    self.screenMode = data.screenMode;
                    return null;
                });
                self.cm = new kaf002.cm.viewmodel.ScreenModel(self.stampRequestMode, self.screenMode);
                self.kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
                self.startPage()
                .done((commonSet: vmbase.AppStampNewSetDto)=>{
                    self.enableSendMail(commonSet.appCommonSettingDto.appTypeDiscreteSettingDtos[0].sendMailWhenRegisterFlg == 1 ? false : true);
                    self.checkBoxValue(commonSet.appCommonSettingDto.applicationSettingDto.manualSendMailAtr == 1 ? true : false);
                    if(nts.uk.util.isNullOrEmpty(self.employeeID)){
                        self.employeeID = commonSet.employeeID;    
                    }
                    self.kaf000_a2.getAppDataDate(
                        applicationType, 
                        self.date,
                        true,self.employeeID)
                    .done(()=>{
                        if(nts.uk.util.isNullOrEmpty(self.kaf000_a2.approvalRootState())){
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_324" }).then(function(){
                                nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                            });
                        } else {
                            self.cm.start(commonSet, {'stampRequestMode': self.stampRequestMode }, true, self.employeeID, self.date);  
                        }  
                    }).fail((res1) => { 
                        nts.uk.ui.dialog.alertError({ messageId: res1.messageId }).then(function(){
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml"); 
                            nts.uk.ui.block.clear();
                        });
                    });   
                });
                self.cm.application().appDate.subscribe(value => {
                    nts.uk.ui.block.invisible();
                    self.kaf000_a2.getAppDataDate(7, value, false,self.employeeID)
                    .done(()=>{
                        self.cm.getAttendanceItem(
                            value,
                            [self.employeeID]    
                        );
                        nts.uk.ui.block.clear();         
                    }).fail(()=>{
                        nts.uk.ui.block.clear();    
                    });
                });
            }
            
            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                service.newScreenFind(self.employeeID, self.appDate)
                    .done(function(commonSet: vmbase.AppStampNewSetDto) {
                        dfd.resolve(commonSet); 
                    })
                    .fail(function(res) { 
                       if(res.messageId == 'Msg_426'){
                                nts.uk.ui.dialog.alertError({messageId : res.messageId}).then(function(){
                                    nts.uk.ui.block.clear();
                                });
                        }else{ 
                                nts.uk.ui.dialog.alertError({messageId: res.messageId}).then(function(){ 
                                    nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");
                                    nts.uk.ui.block.clear();  
                                });
                        }
                        dfd.reject(res); 
                    });
                return dfd.promise();
            }

            register() {
                var self = this;
                self.cm.register(self.kaf000_a2.errorFlag, self.kaf000_a2.errorMsg, self.checkBoxValue());
            }
            
            performanceReference(){
                alert('KDL004');   
            }
            
            changeAppDate(){
                var self = this;
                nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: self.employeeID}); 
            }
            
        }
    }
}