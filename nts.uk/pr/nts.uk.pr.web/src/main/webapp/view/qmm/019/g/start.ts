__viewContext.ready(function () {
    var screenModel = new qmm019.g.viewmodel.ScreenModel();
    var vm = screenModel;
    
//    screenModel.startPage().done(function() {
//        __viewContext.bind(vm);
//    });
    this.bind(vm);
});