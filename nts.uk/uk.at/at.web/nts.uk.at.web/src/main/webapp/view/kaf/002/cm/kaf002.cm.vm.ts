module nts.uk.at.view.kaf002.cm {
    export module viewmodel {
        import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
        import kaf002 = nts.uk.at.view.kaf002;
        let __viewContext: any = window["__viewContext"] || {};
        export class ScreenModel {
            m1 = new kaf002.m1.viewmodel.ScreenModel();
            m2 = new kaf002.m2.viewmodel.ScreenModel();
            m3 = new kaf002.m3.viewmodel.ScreenModel();
            m4 = new kaf002.m4.viewmodel.ScreenModel();
            m5 = new kaf002.m5.viewmodel.ScreenModel();
            stampRequestMode: KnockoutObservable<number> = ko.observable(0);
            screenMode: KnockoutObservable<number> = ko.observable(0);
            application: KnockoutObservable<vmbase.Application> = ko.observable(new vmbase.Application('',0,'2017/09/07','','2017/09/07','',0,''));
            constructor(){
                var self = this;
                __viewContext.transferred.ifPresent(data => {
                    self.stampRequestMode(data.stampRequestMode);
                    self.screenMode(data.screenMode);
                });
                
            }
            
            register(){
                var self = this;
                switch(self.stampRequestMode()){
                    case 0: self.m1.register(self.application());break;    
                    case 1: self.m2.register(self.application());break;  
                    case 2: self.m3.register(self.application());break; 
                    case 3: self.m4.register(self.application());break; 
                    case 4: self.m5.register(self.application());break; 
                }    
            }
            
            update(){
                var self = this;
                switch(self.stampRequestMode()){
                    case 0: self.m1.update(self.application());break;    
                    case 1: self.m2.update(self.application());break;  
                    case 2: self.m3.update(self.application());break; 
                    case 3: self.m4.update(self.application());break; 
                    case 4: self.m5.update(self.application());break;  
                }    
            }
            
        }
    }
}