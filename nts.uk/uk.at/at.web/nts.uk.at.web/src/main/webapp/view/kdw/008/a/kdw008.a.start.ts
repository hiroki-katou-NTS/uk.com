module nts.uk.at.view.kdw008.a {  
    __viewContext.ready(function() {
        let dataShare:any;
         this.transferred.ifPresent(data => {
            console.log(data);
             dataShare = data;
        });
        
        let screenModel = new viewmodel.ScreenModel(dataShare);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}