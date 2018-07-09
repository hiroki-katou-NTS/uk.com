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
        processFilter();
    });

    $(document).on('click', '.clear-btn', function(evt) {
        let vm: any = __viewContext["viewModel"],
            dataSourceFilter: Array<any> = $("#item_grid").igGrid("option", "dataSource");

        if (dataSourceFilter.length > 0) {
            if (nts.uk.text.isNullOrEmpty(vm.currentItemId())) {
                vm.currentItemId(dataSourceFilter[0].perInfoCtgId);
            }
        } else {
            vm.currentCategory().itemList([]);

        }
        vm.isFilter(false);
        vm.dataSourceFilter = [];
    })

    $(document).on('keydown', 'input.ntsSearchBox.nts-editor.ntsSearchBox_Component', function(e) {
        processFilter();

    })
})


function processFilter() {

    let vm: any = __viewContext["viewModel"],
        dataSourceFilter: Array<any> = $("#item_grid").igGrid("option", "dataSource");
    vm.isFilter(true);
    vm.dataSourceFilter = [];

    if (dataSourceFilter.length > 0) {
        vm.currentItemId(dataSourceFilter[0].perInfoCtgId);
    } else {
        vm.currentCategory().itemList([]);
    }
    vm.dataSourceFilter = dataSourceFilter;
}





