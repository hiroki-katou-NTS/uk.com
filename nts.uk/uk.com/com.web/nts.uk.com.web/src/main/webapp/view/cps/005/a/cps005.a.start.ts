module nts.uk.com.view.cps005.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            ko.computed(function() {
                __viewContext['screenModel'].isEnableButtonProceedA(nts.uk.ui._viewModel.errors.isEmpty() && __viewContext['screenModel'].currentData().isEnableButtonProceed());
            });
        });
    });
}

$(function() {
    $(document).on('click', '.search-btn', function(evt) {
        processFilter();
    });

    $(document).on('click', '.clear-btn', function(evt) {
        processFilter();
    })

})


function processFilter(){
    let dataSourceFilter: Array<any> = $("#category-list-items").igGrid("option", "dataSource");

    if (dataSourceFilter.length > 0) {
        if (nts.uk.text.isNullOrEmpty(__viewContext['screenModel'].currentData().perInfoCtgSelectCode())) {
            __viewContext['screenModel'].currentData().perInfoCtgSelectCode(dataSourceFilter[0].id);
        }
    } else {
        __viewContext['screenModel'].currentData().perInfoCtgSelectCode("");
        __viewContext['screenModel'].currentData().currentCtgSelected(new nts.uk.com.view.cps005.a.PerInfoCtgModel(null));
        __viewContext['screenModel'].isUpdate = false;
        $("#category-name-control").focus();
        __viewContext['screenModel'].currentData().isEnableButtonProceed(true);
        __viewContext['screenModel'].currentData().isEnableButtonOpenDialog(false);
        __viewContext['screenModel'].currentData().isHisTypeUpdateModel(false);
    }


}