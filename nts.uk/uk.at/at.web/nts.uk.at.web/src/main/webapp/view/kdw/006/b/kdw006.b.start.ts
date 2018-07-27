//module nts.uk.at.view.kdw006.b {
//    import kdw = nts.uk.at.view.kdw006;
//
//    let __viewContext: any = window["__viewContext"] || {};
//    __viewContext.ready(function() {
//        __viewContext.viewModel = new kdw.viewmodel.TabScreenModel();
//        screenModel.start().done(function() {
//            __viewContext.bind(__viewContext.viewModel);
//        });
//    });
//}



module nts.uk.at.view.kdw006.b {  
    import kdw = nts.uk.at.view.kdw006;
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new kdw.b.viewmodel.TabScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}