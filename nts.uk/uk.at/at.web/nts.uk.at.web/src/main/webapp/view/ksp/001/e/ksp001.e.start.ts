module nts.uk.at.view.ksp001.e {

    import getText = nts.uk.resource.getText;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // set time out de chac chan viec hien thi header la dung
        setTimeout(() => {
            // set text header
            $("#multi-list_name").css('display', 'none');
            $($("#multi-list_name").prev()).css('display', 'none');
            $($($("#multi-list_name").closest("#multi-list_headers")).children()[0]).css('display', 'none')
            $("#multi-list_name").after("<th role=" + 'columnheader' + " tabindex=" + -1 + " id = " + "th-css" + " class= " + "ui-iggrid-header" + "> <span id = " + "span-css" + " class=" + "ui-iggrid-headertext" + ">" + getText('KSP001_30') + " </span> </th>");
            $("#th-css").addClass("ui-widget-header");
            // set focus
            $($('span.ui-state-default.ui-corner-all.ui-igcheckbox-normal')[1]).focus();
        }, 10);
    });
}