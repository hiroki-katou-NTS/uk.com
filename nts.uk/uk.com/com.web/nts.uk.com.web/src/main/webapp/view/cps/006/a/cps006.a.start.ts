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
        __viewContext["viewModel"].categoryList.removeAll();
        nts.uk.com.view.cps006.a.service.getAllCategory().done(function(data: Array<any>) {
            if (data.length > 0) {
                if (__viewContext["viewModel"].isAbolished()) {

                    __viewContext["viewModel"].categoryList(_.map(data, x => new nts.uk.com.view.cps006.a.viewmodel.CategoryInfo({
                        id: x.id,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        categoryType: x.categoryType,
                        isAbolition: x.isAbolition
                    })));
                } else {
                    __viewContext["viewModel"].categoryList(_.map(_.filter(data, x => { return x.isAbolition == 0 }), x => new nts.uk.com.view.cps006.a.viewmodel.CategoryInfo({
                        id: x.id,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        categoryType: x.categoryType,
                        isAbolition: x.isAbolition
                    })));

                }
            }
        });

        $("#category_grid").igGrid("option", "dataSource", __viewContext["viewModel"].categoryList());

    });
})