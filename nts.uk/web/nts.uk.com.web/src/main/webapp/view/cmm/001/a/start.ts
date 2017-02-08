//module nts.uk.pr.view.cmm001.a {
//    let screenModel = new nts.uk.pr.view.cmm001.a.ScreenModel() ;
//        screenModel.start().done(function() {
//        __viewContext.bind(screenModel);
//        //screenModel.bindSortable();
//    });
    __viewContext.ready(function() {
        let screenModel = new nts.uk.pr.view.cmm001.a.ScreenModel() ;
        __viewContext.bind(screenModel);
    });
//}