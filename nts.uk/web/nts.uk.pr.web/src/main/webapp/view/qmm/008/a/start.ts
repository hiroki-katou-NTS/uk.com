__viewContext.ready(function() {
    var screenModel = new nts.uk.pr.view.qmm008.a.viewmodel.ScreenModel();
    $(".draggable").draggable({});
    this.bind(screenModel);
});