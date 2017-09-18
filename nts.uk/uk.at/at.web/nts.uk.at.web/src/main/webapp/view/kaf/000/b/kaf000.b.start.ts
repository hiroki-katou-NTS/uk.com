import kaf002 = nts.uk.at.view.kaf002;
let __viewContext: any = window["__viewContext"] || {};
__viewContext.ready(function() {
    var totalModel = {
        screenModel: new kaf000.b.viewmodel.ScreenModel(),
        screenModel9: new nts.uk.at.view.kaf009.b.viewmodel.ScreenModel()
    };

    //totalModel.screenModel.start().done(function() {
        __viewContext.bind(totalModel);
    //});
});

