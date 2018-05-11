module cps008.b {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        __viewContext['viewModel'].start();
        
        // Re-calculate size
        var currentDialog = nts.uk.ui.windows.getSelf();
        if (currentDialog.parent.globalContext.innerHeight < currentDialog.$dialog.height()) {
            currentDialog.setHeight(currentDialog.parent.globalContext.innerHeight - 50);
        }
        
        if (currentDialog.parent.globalContext.innerWidth < currentDialog.$dialog.width()) {
            currentDialog.setWidth(currentDialog.parent.globalContext.innerWidth - 50);
        }
        
    });
}