module nts.uk.at.view.kaf002.b {
    import service = nts.uk.at.view.kaf002.shr.service;
    import kaf002 = nts.uk.at.view.kaf002;
    let __viewContext: any = window["__viewContext"] || {};
    
    __viewContext.ready(function() {
        var bigModel = new kaf002.b.viewmodel.ScreenModel();
        __viewContext.bind(bigModel); 
    });
}