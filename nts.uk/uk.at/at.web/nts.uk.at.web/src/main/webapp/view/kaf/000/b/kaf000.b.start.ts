__viewContext.ready(function() {
    let totalModel = {
        screenModel: new kaf000.b.viewmodel.ScreenModel(),
        screenModel9: new nts.uk.at.view.kaf009.b.viewmodel.ScreenModel()
    };


    totalModel.screenModel.start().done(function() {
        __viewContext.bind(totalModel);
    });
});

