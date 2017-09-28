module nts.uk.at.view.kml002.h {
    import kml = nts.uk.at.view.kml002;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kml.viewmodel.TabScreenModel(),
            viewmodelH: new kml.h.viewmodel.ScreenModel(),
            viewmodelA: new kml.a.viewmodel.ScreenModel()
        };
        
        __viewContext.bind(__viewContext.viewModel);
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
