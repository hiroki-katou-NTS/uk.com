module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        __viewContext.bind(__viewContext["viewModel"]);
        
        $(document).ready(function() {
            __viewContext["viewModel"].checkBrowse();
        });

        if (window.top != window.self) {
            $("#header").css("display", "none");
            $(".goout").css("display", "none");
            $("#closeBtn").css("visibility", "visible");
        }

        $(".ntsControl .nts-input").focusout(() => {
            $(".ntsControl .nts-input").css("padding-top", "5px !important");
            $(".ntsControl .nts-input").css("padding-bottom", "5px !important");
        });
    });
}


$(function() {
    $(document).on('click', '.search-btn', function(evt) {
        let dataSourceFilter: Array<any> = $("#item_grid").igGrid("option", "dataSource");
        __viewContext["viewModel"].isFilter(true);
        __viewContext["viewModel"].dataSourceFilter = [];

        if (dataSourceFilter.length > 0) {
            __viewContext["viewModel"].currentItemId(dataSourceFilter[0].perInfoCtgId);
        } else {
            __viewContext["viewModel"].currentCategory().itemList([]);

        }
        __viewContext["viewModel"].dataSourceFilter = dataSourceFilter;
    });

    $(document).on('click', '.clear-btn', function(evt) {
        let dataSourceFilter: Array<any> = $("#item_grid").igGrid("option", "dataSource");

        if (dataSourceFilter.length > 0) {
            if (nts.uk.text.isNullOrEmpty(__viewContext["viewModel"].currentItemId())) {
                __viewContext["viewModel"].currentItemId(dataSourceFilter[0].perInfoCtgId);
            }
        } else {
            __viewContext["viewModel"].currentCategory().itemList([]);

        }
        __viewContext["viewModel"].isFilter(false);
        __viewContext["viewModel"].dataSourceFilter = [];
    })
})





