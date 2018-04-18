module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;


        __viewContext.bind(__viewContext["viewModel"]);
        
        $(document).ready(function() {
            let features: Array<any> = [
                {name: "Selection", multipleSelection: false, inherit: true},
                {name: "Tooltips", 
                 columnSettings: 
                 [{columnKey: "settingCode", allowTooltips: true},
                  {columnKey: "settingName", allowTooltips: true}],
                 visibility: "overflow", showDelay: 200, hideDelay: 200
               },
               {name: "Resizing"}];

            $("#category_grid").igGrid("option","features", features);
        });

        if (window.top != window.self) {
            $("#header").css("display", "none");
            $(".goout").css("display", "none");
            $("#closeBtn").css("visibility", "visible");
        }

        $(".ntsControl .nts-input").focusout(() => {
            $(".ntsControl .nts-input").css("padding-top", "5px !important");
            $(".ntsControl .nts-input").css("padding-bottom", "5px !important");
        });
    });
}



