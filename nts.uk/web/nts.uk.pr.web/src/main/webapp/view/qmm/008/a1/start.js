__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.a1.viewmodel.ScreenModel();
    $.when(screenModel.startPage()).done(function () {
        $(".draggable").draggable({});
        __viewContext.bind(screenModel);
    });
});
