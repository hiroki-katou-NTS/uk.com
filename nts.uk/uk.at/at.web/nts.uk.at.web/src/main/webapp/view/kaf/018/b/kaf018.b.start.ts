module nts.uk.at.view.kaf018.b.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        let screenModel = new kaf018.b.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}