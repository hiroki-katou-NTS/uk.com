module nts.uk.at.view.kal003.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        setTimeout(function() { $("#combo-box").focus(); }, 100);
    });
}