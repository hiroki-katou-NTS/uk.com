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

