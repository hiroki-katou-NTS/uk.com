__viewContext.ready(function(){
    var viewModel = new kdl014.a.viewmodel.ScreenModel();
    viewModel.start().done(() => {
        debugger;
      __viewContext.bind(viewModel);       
    });
    
    //__viewContext.bind(viewModel);
})