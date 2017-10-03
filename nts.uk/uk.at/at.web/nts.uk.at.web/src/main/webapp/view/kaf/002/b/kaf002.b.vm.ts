module nts.uk.at.view.kaf002.b {
    import service = nts.uk.at.view.kaf002.shr.service;
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase; 
    export module viewmodel {
        let __viewContext: any = window["__viewContext"] || {};
        export class ScreenModel {
            cm: kaf002.cm.viewmodel.ScreenModel;
            kaf000_a2: kaf000.a.viewmodel.ScreenModel;
            stampRequestMode: number = 0;
            screenMode: number = 0;
            constructor() {
                var self = this;
                __viewContext.transferred.ifPresent(data => {
                    self.stampRequestMode = data.stampRequestMode;
                    self.screenMode = data.screenMode;
                });
                self.cm = new kaf002.cm.viewmodel.ScreenModel(self.stampRequestMode, self.screenMode);
                self.kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
                self.kaf000_a2.start().done(()=>{
                    self.startPage();    
                });
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.newScreenFind()
                    .done(function(data: vmbase.AppStampNewSetDto) {
                        let a = self.kaf000_a2.approvalRoot().beforeApprovers;
                        let approvalList = [];
                        _.forEach(a, appPhase => {
                            let b = new vmbase.AppApprovalPhase(
                                "",
                                appPhase.approvalForm,
                                appPhase.orderNumber,
                                1,
                                []); 
                            _.forEach(appPhase.approvers, appFrame => {
                                let c = new vmbase.ApprovalFrame(
                                    "",
                                    appFrame.orderNumber,
                                    []);
                                let d = new vmbase.ApproveAccepted(
                                    "",
                                    appFrame.sid,
                                    0,
                                    appFrame.confirmPerson ? 1 : 0,
                                    "",
                                    "",
                                    appFrame.sid);
                                c.approveAcceptedCmds.push(d);
                                b.approvalFrameCmds.push(c);   
                            });
                            approvalList.push(b);    
                        });
                        self.cm.start(data, approvalList);
                        dfd.resolve(); 
                    })
                    .fail(function(res) { 
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
                nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: "000100003"}); 
            }
            
        }
    }
}