module ccg013.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });    
        
        var tabs = $( "#tabs" ).tabs();
        tabs.find( ".ui-tabs-nav" ).sortable({
          axis: "x"
        }).disableSelection();
        
        $( ".title-menu" ).sortable({
            axis: "x"    
        }).disableSelection();
        $( ".tree-menu" ).sortable({
            axis: "y"    
        }).disableSelection();;
    });
}
