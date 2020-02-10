__viewContext.ready(function() {
    var screenModel = new jcm008.a.ViewModel();
    screenModel.start().done(function(){
        __viewContext.bind(screenModel);
        $('.ntsStartDatePicker').focus();
        screenModel.setScroll();
    });
    screenModel.bindHidingEvent();
});