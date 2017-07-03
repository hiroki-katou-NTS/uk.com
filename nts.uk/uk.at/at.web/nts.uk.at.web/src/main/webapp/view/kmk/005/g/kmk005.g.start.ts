module nts.uk.at.view.kmk005.g {
    import kmk = nts.uk.at.view.kmk005;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kmk.viewmodel.TabScreenModel(),
            viewmodelG: new kmk.g.viewmodel.ScreenModel(),
            viewmodelH: new kmk.h.viewmodel.ScreenModel(),
            viewmodelI: new kmk.i.viewmodel.ScreenModel(),
            viewmodelK: new kmk.k.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);

        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
