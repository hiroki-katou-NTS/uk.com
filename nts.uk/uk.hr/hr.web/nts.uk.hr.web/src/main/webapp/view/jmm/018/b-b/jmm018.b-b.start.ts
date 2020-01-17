module nts.uk.com.view.jmm018.tabb {
    import jmm018b = nts.uk.com.view.jmm018.tabb;
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new jmm018b.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            screenModel.hidden('href1', 'B422_12')
        });
    });
}