__viewContext.ready(function () {
    class ScreenModel {
        
        constructor(){  
            let self = this;
            $("#test2").ntsButtonTable("init", {row: 3, column: 10, source: [
                [ {}, {}, {text: "test", tooltip: "test"}, {}],
                [],
                []
                ], contextMenu: [
                    {id: "cut", text: "切り取り", action: self.openDialog, style: "icon icon-dot"},
                    {id: "copy", text: "名前を変更", action: self.openDialog, style: "icon icon-dot"},
                    {id: "delete", text: "削除", action: self.remove, style: "icon icon-close"}
                ], disableMenuOnDataNotSet: [1,2], mode: "normal"});
        }
        
        openDialog() {
            let dfd = $.Deferred();
            // Set parent value
            nts.uk.ui.windows.setShared("parentValue", "test");
            nts.uk.ui.windows.setShared("isTransistReturnData", false);
            nts.uk.ui.windows.sub.modal("/view/sample/functionwrap/window/subwindow.xhtml").onClosed(() => {
                // Get child value
                var returnValue = nts.uk.ui.windows.getShared("childValue");
                dfd.resolve({text: returnValue, tooltip: returnValue});
            });
            
            return dfd.promise();
        }
        
        remove() {
            let dfd = $.Deferred();
            
            setTimeout(function(){
                dfd.resolve(undefined);
            }, 10);
            
            return dfd.promise();
        }
    }
    
    this.bind(new ScreenModel());
    
});