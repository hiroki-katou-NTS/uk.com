module nts.uk.hr.view.jmm017.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            
            let contentArea = $(".sidebar-html")[0].getBoundingClientRect().height - ($("#header")[0].getBoundingClientRect().height + $(".sidebar-content-header")[0].getBoundingClientRect().height + $(".nts-guide-area")[0].getBoundingClientRect().height + 10);
            $(".contents-area").css({overflow: 'auto', height:contentArea +"px"});
        });
    });
}