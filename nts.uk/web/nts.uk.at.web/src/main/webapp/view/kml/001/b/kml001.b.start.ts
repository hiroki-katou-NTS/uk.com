module nts.uk.at.view.kml001.b {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.allUse()!=0){
                for(let i=0;i<10;i++){
                    if(screenModel.premiumItemList()[i].useAtr()!=0) {
                        $('.premiumName:eq('+i+')').focus();
                        break;
                    }
                }    
            }
            $("#premium-set-tbl-b > tbody > tr > td:NTH-CHILD(2) button").each(function (i) { $(this).attr('tabindex', i + 1); });
            $("#premium-set-tbl-b > tbody > tr > td:NTH-CHILD(3) input").each(function (i) { $(this).attr('tabindex', 3*i); });
        });
    });
}
