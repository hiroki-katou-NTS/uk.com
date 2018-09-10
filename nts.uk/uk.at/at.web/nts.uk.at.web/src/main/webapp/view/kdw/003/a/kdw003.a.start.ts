module nts.uk.at.view.kdw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let dataShare: any;
        this.transferred.ifPresent(data => {
            console.log(data);
            dataShare = data;
        });
        var screenModel = __viewContext.vm = new nts.uk.at.view.kdw003.a.viewmodel.ScreenModel(dataShare);
        screenModel.startPage().done((data) => {
            //this.bind(screenModel, dialogOptions);
            //cursor move direction 
            screenModel.selectedDirection.subscribe((value) => {
                if (value == 0) {
                    $("#dpGrid").mGrid("directEnter", "below");
                } else {
                    $("#dpGrid").mGrid("directEnter", "right");
                }
            });
            if (data.bindDataMap) {
                screenModel.processMapData(data.data);
                //screenModel.loadKcp009();
            }
            __viewContext.bind(screenModel);
            if (data.bindDataMap) {
                screenModel.processFlex(data.data, false);
            }
            
        });
    });
}