module nts.uk.at.view.kaf022.wkp {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
        setTimeout(function (){
            $("#fixed-table-cmp").ntsFixedTable({height: jQuery(window).height() - 185});
            $("#fixed-table-wkp").ntsFixedTable({height: jQuery(window).height() - 245});
        }, 500)

    });
}