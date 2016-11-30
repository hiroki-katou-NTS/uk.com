__viewContext.ready(function() {
    //var data = this.transferred.get();
    var param = nts.uk.ui.windows.getShared('param');
    var data = {
        categoryId: param.categoryId,
        itemCode: param.itemCode,//'0030',
        isUpdate: param.isUpdate//true
    };
    var screenModel = new qmm019.f.viewmodel.ScreenModel(data);
    screenModel.start().done(function() {
        __viewContext.bind(screenModel);
    });
});