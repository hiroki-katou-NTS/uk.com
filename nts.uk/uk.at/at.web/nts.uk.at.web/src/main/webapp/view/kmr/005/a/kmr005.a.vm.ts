module nts.uk.at.view.kmr005.a.viewmodel {
    import service = nts.uk.at.view.kmr005.a.service;
    export class ScreenModel {
        constructor() {
            
        }
        
        exportFile() {
            service.exportFile();    
        }
    }

}

