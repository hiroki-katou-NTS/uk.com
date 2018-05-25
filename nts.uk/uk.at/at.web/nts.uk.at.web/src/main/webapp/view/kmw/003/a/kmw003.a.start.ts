module nts.uk.at.view.kmw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = __viewContext.vm = new nts.uk.at.view.kmw003.a.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            //this.bind(screenModel, dialogOptions);
            //cursor move direction 
            screenModel.selectedDirection.subscribe((value) => {
                if (value == 0) {
                    $("#dpGrid").ntsGrid("directEnter", "below", "");
                } else {
                    $("#dpGrid").ntsGrid("directEnter", "right", "");
                }
            });
            __viewContext.bind(screenModel);
        });
    });
}