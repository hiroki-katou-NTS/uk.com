module nts.uk.at.view.kaf018.a.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        let screenModel = new kaf018.a.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#combo-box').focus();
    });
}