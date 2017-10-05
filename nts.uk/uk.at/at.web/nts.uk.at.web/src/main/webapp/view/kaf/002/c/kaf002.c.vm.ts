module nts.uk.at.view.kaf002.c {
    import service = nts.uk.at.view.kaf002.shr.service;
    import kaf000 = nts.uk.at.view.kaf000;
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase; 
    export module viewmodel {
        export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
            cm: kaf002.cm.viewmodel.ScreenModel;
            constructor(appType: number) {
                super(appType);
                var self = this;
                self.cm = new kaf002.cm.viewmodel.ScreenModel(0,0);
                self.startPage(self.appID());
                self.appID.subscribe(value=>{
                    if(self.appType()==7){
                        self.startPage(value);       
                    }
                });
            }
            
            testAbstract() {
                alert('aaaaaa');    
            }
            
            startPage(appID: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var dfdCommonSet = service.newScreenFind();
                var dfdAppStamp = service.findByAppID(appID);
                $.when(dfdCommonSet, dfdAppStamp).done((commonSetData, appStampData) => {
                    self.cm.start(commonSetData, appStampData, self.listPhase());
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
            
            update(){
                var self = this;
                self.cm.register();
            }
        }
    }
}