module nts.uk.at.view.kal001.c {  
    __viewContext.ready(function() {
        let shareEmployees : Array<modeldto.ShareEmployee> =  nts.uk.ui.windows.getShared("employeeList");
        let screenModel = new viewmodel.ScreenModel(shareEmployees); 

        screenModel.startPage().done(() =>{
            __viewContext.bind(screenModel); 
        });      
    });
}