__viewContext.ready(function () {
    //var data = this.transferred.get();
    var data = nts.uk.ui.windows.getShared('param');
    var screenModel = new qmm019.f.viewmodel.ScreenModel(data);
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
});
