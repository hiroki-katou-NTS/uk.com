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
                        self.cm.start(data, self.kaf000_a2.approvalRoot().beforeApprovers);
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
            
            performanceReference(){
                alert('KDL004');   
            }
            
            changeAppDate(){
                // CMM018    
            }
            
        }
    }
}