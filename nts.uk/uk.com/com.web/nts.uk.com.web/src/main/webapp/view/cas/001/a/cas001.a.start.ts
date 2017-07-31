module nts.uk.com.view.cas001.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
             let helpButton = "<button data-bind=\"ntsHelpButton: {image: \'test-image.png\', position: \'right top\', enable: true }\">?</button>";
               nts.uk.ui.ig.grid.header.getCell('A2_008', 'IsConfig').append($(helpButton));
        });

    });
}