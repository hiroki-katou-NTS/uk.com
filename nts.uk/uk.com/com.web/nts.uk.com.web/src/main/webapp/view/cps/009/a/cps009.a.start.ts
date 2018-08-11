module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        __viewContext.bind(__viewContext["viewModel"]);

        $(document).ready(function() {
           // __viewContext["viewModel"].checkBrowse();
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






