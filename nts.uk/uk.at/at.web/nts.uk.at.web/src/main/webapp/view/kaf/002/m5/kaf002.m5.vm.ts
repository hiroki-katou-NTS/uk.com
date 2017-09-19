module nts.uk.at.view.kaf002.m5 {
    import service = nts.uk.at.view.kaf002.shr.service;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            constructor(){
                
            }
            
            register(application : vmbase.Application){
                alert("m5");    
            }
            
            update(application : vmbase.Application){
                alert("m5");    
            }
        }
    }
}