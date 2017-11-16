module nts.uk.com.view.cps017.c {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        
//        __viewContext["viewModel"].start().done(function(){
//            __viewContext.bind(__viewContext["viewModel"]);
//        });
        __viewContext.bind(__viewContext["viewModel"]);
    });
}


/*
module nts.uk.com.view.cps017.c {

    __viewContext.ready(function() {
        __viewContext.screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext.screenModel);
    });
}
*/
