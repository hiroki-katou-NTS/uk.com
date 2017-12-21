module cps001.a {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext['viewModel'] = new vm.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        $(".ntsSearchBox").attr("placeholder", "名称で検索…");
        $(".search-btn").html("絞り込み");
    });
}