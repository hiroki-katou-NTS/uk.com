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
            $("* input").attr('tabindex', -1);
            $("#premium-set-tbl-b > tbody > tr > td:NTH-CHILD(2) > div").each(function (i) { $(this).attr('tabindex', i*2+1); });
            $("#premium-set-tbl-b > tbody > tr > td:NTH-CHILD(3) > span").each(function (i) { $(this).attr('tabindex', i*2+2); });
            $("#functions-area-bottom > button:NTH-CHILD(1)").attr('tabindex', 23);
            $("#functions-area-bottom > button:NTH-CHILD(2)").attr('tabindex', 24);
        });
    });
}
