module nts.uk.pr.view.qmm018.b {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

            if (/Edge/.test(navigator.userAgent)) {
                $("#swap-list-grid1_displayContainer").addClass('edgeList');
                $("#swap-list-grid2_displayContainer").addClass('edgeList');
                $("#swap-list-grid1_scrollContainer").addClass('edgeList');
                $("#swap-list-grid2_scrollContainer").addClass('edgeList');
            } else if (/Chrome/.test(navigator.userAgent)) {
                $("#swap-list-grid1_displayContainer").addClass('chromeList');
                $("#swap-list-grid2_displayContainer").addClass('chromeList');
                $("#swap-list-grid1_scrollContainer").addClass('chromeList');
                $("#swap-list-grid2_scrollContainer").addClass('chromeList');
            } else {
                $("#swap-list-grid1_displayContainer").addClass('ieList');
                $("#swap-list-grid2_displayContainer").addClass('ieList');
                $("#swap-list-grid1_scrollContainer").addClass('ieList');
                $("#swap-list-grid2_scrollContainer").addClass('ieList');
            }

            $("#swap-list-grid1_container").focus();
        });
    });
}
