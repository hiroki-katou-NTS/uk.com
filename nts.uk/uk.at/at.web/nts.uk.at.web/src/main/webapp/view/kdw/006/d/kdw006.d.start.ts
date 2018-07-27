module nts.uk.at.view.kdw006.d {
    import kdw = nts.uk.at.view.kdw006.d;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = new kdw.viewmodel.ScreenModelD();
        __viewContext.viewModel.start().done(function() {
            __viewContext.bind(__viewContext.viewModel);
        });
    });
}
