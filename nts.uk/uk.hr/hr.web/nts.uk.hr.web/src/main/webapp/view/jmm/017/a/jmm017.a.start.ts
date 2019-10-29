module nts.uk.hr.view.jmm017.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
              let contentArea = $(".sidebar-html")[0].getBoundingClientRect().height - ($("#header")[0].getBoundingClientRect().height + $(".sidebar-content-header")[0].getBoundingClientRect().height);
              $('#tabpanel-2 > div.contents-area,#tabpanel-2 > div.nts-guide-area').wrapAll('<div class="wrapScroll"></div>');  
              $(".wrapScroll").css({overflow: 'auto', height:contentArea +"px"}) ;
            
        });
    });
}