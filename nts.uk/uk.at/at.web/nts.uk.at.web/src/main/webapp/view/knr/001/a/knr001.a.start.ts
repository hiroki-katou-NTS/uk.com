module nts.uk.at.view.knr001.a {
    
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.knr001.a.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
           // setTimeout(()=>{$("#pg-name").text("就業情報端末の登録");   }, 300);
           // setTimeout(()=>{$(".ntsSearchBox_Component").attr("placeholder", "端末No名称で検索・・・");   }, 300);
        });        
    });
}