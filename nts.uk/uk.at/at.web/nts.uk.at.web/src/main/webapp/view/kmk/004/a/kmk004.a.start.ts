module nts.uk.at.view.kmk004.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#companyYearPicker').focus();
            
            // Auto next tab when press tab key.
            $("[tabindex='22']").on('keydown', function(e) {
                if (e.which == 9) {
                    screenModel.companyWTSetting.selectedTab('tab-2');
                }
            });
            
            $("[tabindex='48']").on('keydown', function(e) {
                if (e.which == 9) {
                    screenModel.companyWTSetting.selectedTab('tab-3');
                }
            });
            $("[tabindex='7']").on('keydown', function(e) {
                if (e.which == 9 &&  !$(e.target).parents("[tabindex='7']")[0]) {
                    screenModel.companyWTSetting.selectedTab('tab-1');
                }
            });
        });
    });
}