module nts.uk.at.view.kaf018.f.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        let screenModel = new kaf018.f.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}