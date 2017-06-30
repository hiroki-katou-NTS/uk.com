module ccg013.a {
    __viewContext.ready(function() {
        var viewModel = new viewmodel.ScreenModel();
        viewModel.startPage().done(function() {
            __viewContext.bind(viewModel);
            
            $("#tabs").tabs()
            .find(".ui-tabs-nav").sortable({
                axis: "x"
            }).disableSelection();
    
            $(".tree-menu").sortable({
                axis: "y"
            }).disableSelection();
        });

        
    });
}


function initTitleBar() {
    $(".title-menu").sortable({
                items: ".title-menu-column",
                axis: "x"
            }).disableSelection();    
}