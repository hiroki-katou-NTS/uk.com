__viewContext.ready(function() {
    //var data = this.transferred.get();
    var data = {
        categoryId: 0,
        itemCode: '0003',
        isUpdate: true
    };
    var screenModel = new qmm019.f.viewmodel.ScreenModel(data);
    screenModel.start().done(function() {
        __viewContext.bind(screenModel);
    });
});