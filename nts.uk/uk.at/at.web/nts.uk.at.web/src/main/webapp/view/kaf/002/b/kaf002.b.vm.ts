module nts.uk.at.view.kaf002.b {
    import service = nts.uk.at.view.kaf002.shr.service;
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase; 
    export module viewmodel {
        let __viewContext: any = window["__viewContext"] || {};
        export class ScreenModel {
            cm = new kaf002.cm.viewmodel.ScreenModel();
            kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
            constructor() {
                var self = this;
                // self.kaf000_a2.start();
                self.startPage();
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
        }
    }
}