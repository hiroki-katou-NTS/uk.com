module nts.uk.at.view.kmf022.a {
    import kmf = nts.uk.at.view.kmf022;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        
        __viewContext.viewModel = {
            tabView: new kmf022.viewmodel.TabScreenModel(),
            viewmodelA: new kmf022.a.viewmodel.ScreenModel(),
            viewmodelL:new kmf022.l.viewmodel.ScreenModel(),
            viewmodelM:new kmf022.m.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);
        let height = $(window).height();
        $('#tabpanel-m').css("height", height - 163);
        $('#tabpanel-l').css("height", height - 144);
        $('#tabpanel-a').css("height", height - 144);
        $('#tabpanel-m').css({"overflow":"auto","-ms-overflow-style":"auto"});
        $('#tabpanel-l').css({"overflow":"auto","-ms-overflow-style":"auto"});
        $('#tabpanel-a').css({"overflow":"auto","-ms-overflow-style":"auto"});
        $('html').css({"overflow-y":"hidden","-ms-overflow-style":"none"});
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
