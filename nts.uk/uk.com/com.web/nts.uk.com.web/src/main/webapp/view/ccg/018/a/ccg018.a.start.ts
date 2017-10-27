__viewContext.ready(function() {
    var viewModelA = new ccg018.a.viewmodel.ScreenModel();
    
    viewModelA.start().done(function(){
        __viewContext.bind(viewModelA);
        
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
    
});