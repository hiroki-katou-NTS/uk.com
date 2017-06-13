__viewContext.ready(function(){
    __viewContext.bind({
        openKDL014(){
          nts.uk.ui.windows.sub.modal('/view/kdl/014/a/index.xhtml', { title: '打刻参照',});  
        };
        openKDL014B(){
          nts.uk.ui.windows.sub.modal('/view/kdl/014/b/index.xhtml', { title: '打刻参照B',});  
        }
    });
}