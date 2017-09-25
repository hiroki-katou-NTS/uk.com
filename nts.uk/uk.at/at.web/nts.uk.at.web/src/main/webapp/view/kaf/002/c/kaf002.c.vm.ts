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
                self.cm = new kaf002.cm.viewmodel.ScreenModel(0,1);
                self.startPage();
            }
            
            testAbstract() {
                alert('aaaaaa');    
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.newScreenFind()
                    .done(function(data: vmbase.AppStampNewSetDto) {
                        self.cm.start(data);
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
                
            }
        }
    }
}