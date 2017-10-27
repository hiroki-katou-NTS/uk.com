    __viewContext.ready(function() {
        var screenModel = new ksm002.c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            nts.uk.util.value.reset($(".specificName"));
            if(screenModel.allUse()!=0){
                for(let i=0;i<10;i++){
                    if(screenModel.specificDateItem()[i].useAtr()!=0) {
                        $('.specificName:eq('+i+')').focus();
                        break;
                    }
                }    
            }
            $("* input").attr('tabindex', -1);
            $("#specific-set-tbl-b > tbody > tr > td:NTH-CHILD(2) > div").each(function (i) { $(this).attr('tabindex', i*2+1); });
            $("#specific-set-tbl-b > tbody > tr > td:NTH-CHILD(3) > span > input").each(function (i) { $(this).attr('tabindex', i*2+2); });
            $("#functions-area-bottom > button:NTH-CHILD(1)").attr('tabindex', 21);
            $("#functions-area-bottom > button:NTH-CHILD(2)").attr('tabindex', 22);
        });
    });
