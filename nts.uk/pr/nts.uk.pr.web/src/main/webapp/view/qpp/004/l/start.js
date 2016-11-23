__viewContext.ready(function () {
    var screenModel = new qpp004.l.viewmodel.ScreenModel();
    var data = {
        personIdList: ["1", "2", "3"],
        processingNo: 1,
        processingYearMonth: 201611
    };
    screenModel.startPage(data).done(function () {
        __viewContext.bind(screenModel);
    });
    $('#timer').countup();
});
