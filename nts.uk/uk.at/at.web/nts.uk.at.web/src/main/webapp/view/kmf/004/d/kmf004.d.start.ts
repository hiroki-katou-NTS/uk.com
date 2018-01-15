module nts.uk.at.view.kmf004.d {
    import kmf = nts.uk.at.view.kmf004;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        
        __viewContext.viewModel = {
            tabView: new kmf.viewmodel1.TabScreenModel(),
            viewmodelD: new kmf004.d.viewmodel.ScreenModel(),
            viewmodelE: new kmf004.e.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
