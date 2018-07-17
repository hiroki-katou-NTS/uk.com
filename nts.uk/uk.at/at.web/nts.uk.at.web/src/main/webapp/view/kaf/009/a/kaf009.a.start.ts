module nts.uk.at.view.kaf009.a {
    __viewContext.ready(function() {
//        var screenModel = {
//            mainModel: new nts.uk.at.view.kaf009.a.viewmodel.ScreenModel(),
//            commonModel: new kaf000.a.viewmodel.ScreenModel()
//        };
//        let d1 = screenModel.mainModel.startPage();
//        let d2 = screenModel.commonModel.start();
//
//        $.when(d1,d2).done(function(){
//        //$.when(d1).done(function(){
//        __viewContext.bind(screenModel);   
//        });
        let transferData = __viewContext.transferred.value;
        var screenModel = new nts.uk.at.view.kaf009.a.viewmodel.ScreenModel(transferData);
        __viewContext.bind(screenModel);
    });
}