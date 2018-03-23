module nts.uk.at.view.kaf018.e.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        let screenModel = new kaf018.e.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}