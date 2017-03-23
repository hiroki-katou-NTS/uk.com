__viewContext.ready(function () {
    var self = this;
    var screenModel = new nts.uk.pr.view.qmm008.a.viewmodel.ScreenModel();
    $.when(screenModel.start()).done(function () {
        $(".draggable").draggable({});
        self.bind(screenModel);
    });
});
//# sourceMappingURL=start.js.map