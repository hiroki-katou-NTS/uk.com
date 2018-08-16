module nts.uk.com.view.cps005.b {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext['screenModelB'] = new viewmodel.ScreenModel();
        __viewContext['screenModelB'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModelB']);

            $('#contents-area').on('focus', '#timePointItemMin', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timePointItem().hintTimeMin('');
            });

            $('#contents-area').on('focusout', '#timePointItemMin', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timePointItem().hintTimeMin('0:00');
            });

            $('#contents-area').on('focus', '#timePointItemMax', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timePointItem().hintTimeMax('');
            });

            $('#contents-area').on('focusout', '#timePointItemMax', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timePointItem().hintTimeMax('0:00');
            });


            $('#contents-area').on('focus', '#timeItemMin', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timeItem().hintTimeItemMin('');
            });

            $('#contents-area').on('focusout', '#timeItemMin', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timeItem().hintTimeItemMin('0:00');
            });

            $('#contents-area').on('focus', '#timeItemMax', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timeItem().hintTimeItemMax('');
            });

            $('#contents-area').on('focusout', '#timeItemMax', function() {
                __viewContext['screenModelB'].currentItemData().currentItemSelected().timeItem().hintTimeItemMax('0:00');
            });

            ko.computed(function() {
                __viewContext['screenModelB'].isEnableButtonProceed(nts.uk.ui._viewModel.errors.isEmpty() && __viewContext['screenModelB'].currentItemData().isEnableButtonProceed());
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
//})
//function processFilter() {
//    let dataSourceFilter: Array<any> = $("#item-info-list").igGrid("option", "dataSource");
//
//    if (dataSourceFilter.length > 0) {
//        if (nts.uk.text.isNullOrEmpty(__viewContext['screenModelB'].currentItemData().perInfoItemSelectCode())) {
//            __viewContext['screenModelB'].currentItemData().perInfoItemSelectCode(dataSourceFilter[0].id);
//        }
//    } else {
//        __viewContext['screenModelB'].currentItemData().perInfoItemSelectCode("");
//        __viewContext['screenModelB'].currentItemData().currentItemSelected(new nts.uk.com.view.cps005.b.PersonInfoItem(null));
//        __viewContext['screenModelB'].isUpdate = false;
//        $("#item-name-control").focus();
//        __viewContext['screenModelB'].currentItemData().isEnableButtonProceed(true);
//        __viewContext['screenModelB'].currentItemData().isEnableButtonDelete(false);
//    }
//
//}

