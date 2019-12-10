module nts.uk.at.view.kdw002.c {  
    __viewContext.ready(function() {
        let dataShare:any;
         this.transferred.ifPresent(data => {
            console.log(data);
             dataShare = data;
        });
        let screenModel = new viewmodel.ScreenModel(dataShare);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            
        });
    });
}
$(() =>{
    $(window).resize(() => {
       $('.fixed-flex-layout-right').height(window.innerHeight - 240);
       $('#grid_container').height(window.innerHeight - 280); 
       $('#grid_scroll').height(window.innerHeight - 280); 
       $('#grid_footer_container').height(window.innerHeight - 280); 
       $('#contents-area').height(window.innerHeight - 150);
    }).trigger('resize');     
});