module nts.uk.at.view.kdw006.b {
    import kdw = nts.uk.at.view.kdw006;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kdw.viewmodel.TabScreenModel(),
            viewmodelB: new kdw.b1.viewmodel.ScreenModel(),
            viewmodelC: new kdw.c.viewmodel.ScreenModel(),
            viewmodelD: new kdw.d.viewmodel.ScreenModel(),
            viewmodelE: new kdw.e.viewmodel.ScreenModel(),
            viewmodelG: new kdw.g.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);

        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
