import kaf002 = nts.uk.at.view.kaf002;
let __viewContext: any = window["__viewContext"] || {};
__viewContext.ready(function() {
    var totalModel = {
        screenModel: new kaf000.b.viewmodel.ScreenModel(),
        screenModel9: new nts.uk.at.view.kaf009.b.viewmodel.ScreenModel(),
        m1: new kaf002.m1.viewmodel.ScreenModel(),
        m2: new kaf002.m2.viewmodel.ScreenModel(),
        m3: new kaf002.m3.viewmodel.ScreenModel(),
        m4: new kaf002.m4.viewmodel.ScreenModel(),
        m5: new kaf002.m5.viewmodel.ScreenModel(),
        cm: new kaf002.cm.viewmodel.ScreenModel()
    };
    
    $.extend(totalModel.cm, { 
        m1: totalModel.m1,
        m2: totalModel.m2,
        m3: totalModel.m3,
        m4: totalModel.m4,
        m5: totalModel.m5
    });


    //totalModel.screenModel.start().done(function() {
        __viewContext.bind(totalModel);
    //});
});

