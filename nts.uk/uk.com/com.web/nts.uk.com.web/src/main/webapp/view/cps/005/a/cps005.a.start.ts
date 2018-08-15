module nts.uk.com.view.cps005.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        let vm: any = __viewContext['screenModel'];
        vm.startPage().done(function() {
            __viewContext.bind(vm);
            ko.computed(function() {
                vm.isEnableButtonProceedA(nts.uk.ui._viewModel.errors.isEmpty() && vm.currentData().isEnableButtonProceed());
            });
        });
    });
}

//$(function() {
//    $(document).on('click', '.search-btn', function(evt) {
//        processFilter();
//    });
//
//    $(document).on('click', '.clear-btn', function(evt) {
//        processFilter();
//    })
//
//    $(document).on("keydown", 'input.ntsSearchBox.nts-editor.ntsSearchBox_Component', function(e) {
//        if (e.keyCode == 13) {
//            processFilter();
//        }
//    })
//
//})
//
//
//function processFilter() {
//    let vm: any = __viewContext['screenModel'],
//        dataSourceFilter: Array<any> = $("#category-list-items").igGrid("option", "dataSource");
//
//    if (dataSourceFilter.length > 0) {
//        if (nts.uk.text.isNullOrEmpty(vm.currentData().perInfoCtgSelectCode())) {
//            vm.currentData().perInfoCtgSelectCode(dataSourceFilter[0].id);
//        }
//    } else {
//        vm.currentData().perInfoCtgSelectCode("");
//        vm.currentData().currentCtgSelected(new nts.uk.com.view.cps005.a.PerInfoCtgModel(null));
//        vm.isUpdate = false;
//        $("#category-name-control").focus();
//        vm.currentData().isEnableButtonProceed(true);
//        vm.currentData().isEnableButtonOpenDialog(false);
//        vm.currentData().isHisTypeUpdateModel(false);
//    }
//
//
//}