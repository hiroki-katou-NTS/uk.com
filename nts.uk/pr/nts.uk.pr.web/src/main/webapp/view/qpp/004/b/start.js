__viewContext.ready(function () {
    var data = this.transferred.get();
    var screenModel = new qpp004.b.viewmodel.ScreenModel(data);
    this.bind(screenModel);
});
