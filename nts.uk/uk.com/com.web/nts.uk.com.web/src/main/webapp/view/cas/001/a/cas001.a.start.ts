module nts.uk.com.view.cas001.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext['screenModel']);
         
        
        let helpButton = "<button id=\"A2_012\" data-bind=\"ntsHelpButton: {image: \'A2_012.png\', position: \'right top\', enable: true }\">?</button>";
        nts.uk.ui.ig.grid.header.getCell('A2_008', 'setting').append($(helpButton));
        ko.applyBindings(__viewContext['screenModel'], nts.uk.ui.ig.grid.header.getCell('A2_008', 'setting')[0]);
         $(document).ready(function() {
        $("span.nts-editor-wrapped.ntsControl").attr("tabindex", 5);
        $("div.nts-checkbox-container.ntsControl.nts-grid-header-control-isChecked").attr("tabindex", 19);
        $("button.manual-button").attr("tabindex", -1);
           });
    });
}

