module nts.uk.com.view.cps006.a {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);

    });
}


$(function() {
    $(document).on('click', '.search-btn', function(evt) {
        __viewContext["viewModel"].isFiltered = true;
        __viewContext["viewModel"].ctgLstFilter = $("#category_grid").igGrid("option", "dataSource");
    });

    $(document).on('click', '.clear-btn', function(evt) {
        __viewContext["viewModel"].isFiltered = false;
    });
})