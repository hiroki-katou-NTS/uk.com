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
            constructor() {
                var self = this;
                __viewContext.transferred.ifPresent(data => {
                    self.stampRequestMode = data.stampRequestMode;
                    self.screenMode = data.screenMode;
                });
                self.cm = new kaf002.cm.viewmodel.ScreenModel(self.stampRequestMode, self.screenMode);
                self.kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
                self.startPage().done((commonSet: vmbase.AppStampNewSetDto)=>{
                    self.employeeID = commonSet.employeeID;
                    self.kaf000_a2.start(
                        self.employeeID, 
                        employmentRootAtr, 
                        applicationType, 
                        moment(new Date()).format("YYYY/MM/DD")).done(()=>{
                        let listPhase = self.kaf000_a2.approvalRoot().beforeApprovers; 
                        let approvalList = [];
                        for(let x = 1; x <= listPhase.length; x++){
                            let phaseLoop = listPhase[x-1];
                            let appPhase = new vmbase.AppApprovalPhase(
                                "",
                                phaseLoop.approvalForm,
                                x,
                                0,
                                []); 
                            for(let y = 1; y <= phaseLoop.approvers.length; y++){
                                let frameLoop = phaseLoop.approvers[y-1];
                                let appFrame = new vmbase.ApprovalFrame(
                                    "",
                                    y,
                                    []);
                                let appAccepted = new vmbase.ApproveAccepted(
                                    "",
                                    frameLoop.sid,
                                    0,
                                    frameLoop.confirmPerson ? 1 : 0,
                                    "",
                                    "",
                                    frameLoop.sid);
                                appFrame.approveAcceptedCmds.push(appAccepted);
                                appPhase.approvalFrameCmds.push(appFrame);   
                            };
                            approvalList.push(b);    
                        };
                        
                        self.cm.start(commonSet, {'stampRequestMode': self.stampRequestMode }, approvalList);    
                    }).fail(function(res) { 
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    });   
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                }); 
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.newScreenFind()
                    .done(function(commonSet: vmbase.AppStampNewSetDto) {
                        
                        dfd.resolve(commonSet); 
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                        dfd.reject(res); 
                    });
                return dfd.promise();
            }

            register() {
                var self = this;
                self.cm.register();
            }
            
            update() {
                var self = this;
                self.cm.update();
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