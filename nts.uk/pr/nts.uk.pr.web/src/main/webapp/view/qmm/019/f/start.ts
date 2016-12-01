__viewContext.ready(function() {
    var param = nts.uk.ui.windows.getShared('param');
    
    var data = {
        categoryId: param.categoryId,
        itemCode: param.itemCode,
        isUpdate: param.isUpdate,
        startYm: 201604,
        stmtCode: '01'
    }
    
    var screenModel = new qmm019.f.viewmodel.ScreenModel(data);
    screenModel.start().done(function() {
        __viewContext.bind(screenModel);
    });
});