__viewContext.ready(function() {
    var screenModel = new jcm007.z.ViewModel();
    screenModel.start().done(function(){
        __viewContext.bind(screenModel);
        $('#gridListEmployeesContentIcm007').focus();
    });
   
});
   