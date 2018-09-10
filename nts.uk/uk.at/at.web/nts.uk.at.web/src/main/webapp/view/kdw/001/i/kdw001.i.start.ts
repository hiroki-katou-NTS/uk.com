module nts.uk.at.view.kdw001.i {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => { $("#close-dialog").focus(); });
            //Re-calculate size
            var currentDialog = nts.uk.ui.windows.getSelf();
            if(currentDialog.parent.globalContext.innerHeight < currentDialog.$dialog.height()){
                currentDialog.setHeight(currentDialog.parent.globalContext.innerHeight -20);    
            }
            
            if(currentDialog.parent.globalContext.innerWidth < currentDialog.$dialog.width()){
                currentDialog.setWidth(currentDialog.parent.globalContext.innerWidth -20);    
            }
            
        });
    });
}