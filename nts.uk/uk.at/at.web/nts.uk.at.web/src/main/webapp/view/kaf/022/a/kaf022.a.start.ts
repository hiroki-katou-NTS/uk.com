module nts.uk.at.view.kmf022.a {
    import kmf = nts.uk.at.view.kmf022;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        
        __viewContext.viewModel = {
            tabView: new kmf.viewmodel1.TabScreenModel(),
            viewmodelA: new kmf022.a.viewmodel.ScreenModel()
//            viewmodelM: new kmf004.e.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
