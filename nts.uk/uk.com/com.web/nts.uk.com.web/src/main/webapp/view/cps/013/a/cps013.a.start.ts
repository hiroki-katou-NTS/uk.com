module nts.uk.com.view.cps013.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        nts.uk.ui.block.invisible();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            setTimeout(() => {
                $('span.box').attr("tabindex", "7");
            }, 100);
        }).always(() => {
            nts.uk.ui.block.clear();
        });

        $(document).ready(function() {
            $('#grid2_container').attr("tabindex", "6");
            $('#grid2_virtualContainer').attr("tabindex", "6");
            $('span.box').attr("tabindex", "7");
            let beforeIndex = 6;
            $(window).keyup((e) => {
                // $('span.box').attr("tabindex", "7");
                if (e.which === 9) {
                    let tabindex = e.target.attributes.tabindex ? e.target.attributes.getNamedItem("tabindex").value : e.target.attributes.getNamedItem("tab-index").value;
                    if (tabindex == 6) {
                        $("#grid2_flag > span > div > label > span.box").focus();
                    }
                    beforeIndex = parseInt(tabindex);
                }
            });
        });
    });
}