module ccg013.a {
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new viewmodel.ScreenModel();
        __viewContext['viewModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['viewModel']);
        });
    });
}


function bindSortable() {
    // remove all jQueryUI Instance
    $("#tabs.ui-tabs").tabs("destroy");
    $("#tabs .ui-sortable").sortable("destroy");

    // Rebind tabs and sortable
    $("#tabs").tabs()
        .find(".ui-tabs-nav").sortable({
            axis: "x"
        }).disableSelection();

    $(".title-menu").sortable({
        items: ".title-menu-column",
        axis: "x"
    }).disableSelection();

    $(".tree-menu").sortable({
        axis: "y"
    }).disableSelection();
}