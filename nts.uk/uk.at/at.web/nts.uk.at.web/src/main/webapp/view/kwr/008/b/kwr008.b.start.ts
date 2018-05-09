module nts.uk.at.view.kwr008.b {
    __viewContext.ready(function() { 
        let screenModel = new nts.uk.at.view.kwr008.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
            $('#table-output-items').ntsFixedTable({ height: 388, width: 1200 });    
        });
    }); 
}
        