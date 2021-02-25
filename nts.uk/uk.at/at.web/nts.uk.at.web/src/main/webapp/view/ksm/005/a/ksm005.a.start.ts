module nts.uk.at.view.ksm005.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(res);
            // screenModel.isBuild = true;
        }).then(() => {
            if(screenModel.lstMonthlyPattern().length > 0) {
                $('#inp_monthlyPatternName').focus();
            } else {
                $('#inp_monthlyPatternCode').focus();
            }
        }).always(() => nts.uk.ui.errors.clearAll());
    });
}