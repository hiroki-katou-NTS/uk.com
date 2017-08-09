module nts.uk.at.view.kmf004.b {
    import kmf = nts.uk.at.view.kmf004;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kmf.viewmodel.TabScreenModel(),
            viewmodelG: new kmf.g.viewmodel.ScreenModel(),
            viewmodelH: new kmf.h.viewmodel.ScreenModel(),
            viewmodelI: new kmf.i.viewmodel.ScreenModel(),
            viewmodelK: new kmf.k.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);

        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
