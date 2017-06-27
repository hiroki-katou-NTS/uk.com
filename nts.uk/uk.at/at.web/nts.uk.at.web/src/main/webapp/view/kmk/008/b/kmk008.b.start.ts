module nts.uk.at.view.kmk008.b {
    __viewContext.ready(function() {
//        var screenModels = {
//            viewmodelB: new kmk008.b.viewmodel.ScreenModel(),
//            viewmodelC: new kmk008.c.viewmodel.ScreenModel(),
//
//        };
        let screenModels = new kmk008.b.viewmodel.ScreenModel();
        __viewContext.bind(screenModels);
    });
}