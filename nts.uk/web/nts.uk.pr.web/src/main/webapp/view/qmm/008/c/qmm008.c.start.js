__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
    $.when(screenModel.startPage()).done(function () {
        $(".draggable").draggable({});
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=qmm008.c.start.js.map