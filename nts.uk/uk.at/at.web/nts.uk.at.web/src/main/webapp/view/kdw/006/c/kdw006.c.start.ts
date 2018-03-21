module nts.uk.at.view.kdw006.c {
    import kdw = nts.uk.at.view.kdw006.c;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = new kdw.viewmodel.ScreenModelC();
        __viewContext.bind(__viewContext.viewModel);
    });
}
