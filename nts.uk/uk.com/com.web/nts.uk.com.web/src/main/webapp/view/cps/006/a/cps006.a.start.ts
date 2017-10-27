module nts.uk.com.view.cps006.a {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
        $(".ntsSearchBox").attr("placeholder", "名称で検索…");
        $(".search-btn").html("絞り込み");
    });
}