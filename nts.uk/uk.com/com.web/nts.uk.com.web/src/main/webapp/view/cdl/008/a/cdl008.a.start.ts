module nts.uk.com.view.cdl008.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
         $('#workplaceList').ntsTreeComponent(screenModel.workplaces).done(function(){
              $('#workplaceList').focusTreeGridComponent();
         });
    });
}