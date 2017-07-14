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

    $(window).trigger('resize');
}

$(window).on('resize', function() {
    let container = $('#menu_container'),
        parent = container.parent('div');
    container
        .css('max-width', parent.width() + 'px')
        .css('min-height', (window.innerHeight - 240) + 'px')
        .css('max-height', (window.innerHeight - 240) + 'px');
    $('.ui-tabs-panel')
        .css('min-width', (parent.width() - 20) + 'px')
        .css('min-height', (window.innerHeight - 300) + 'px');
});