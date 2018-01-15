module nts.uk.at.view.kdw006.b {
    import kdw = nts.uk.at.view.kdw006;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kdw.viewmodel.TabScreenModel(),
            viewmodelB: new kdw.b.viewmodel.ScreenModelB(),
            viewmodelC: new kdw.c.viewmodel.ScreenModelC(),
            viewmodelD: new kdw.d.viewmodel.ScreenModelD(),
            viewmodelE: new kdw.e.viewmodel.ScreenModelE(),
            viewmodelG: new kdw.g.viewmodel.ScreenModelG()
        };
         __viewContext.viewModel.viewmodelB.start();
        __viewContext.bind(__viewContext.viewModel);

        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
