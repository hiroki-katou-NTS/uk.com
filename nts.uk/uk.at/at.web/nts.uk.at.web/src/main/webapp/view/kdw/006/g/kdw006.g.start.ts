module nts.uk.at.view.kdw006.g {
    import kdw = nts.uk.at.view.kdw006.g;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = new kdw.viewmodel.ScreenModelG();
        __viewContext.viewModel.start().done(function() {
            __viewContext.bind(__viewContext.viewModel);
        });
    });
} 