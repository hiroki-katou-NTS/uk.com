__viewContext.ready(function() {
    var viewModelA = new nts.uk.at.view.kmf004.a.viewmodel.ScreenModel();
    
    viewModelA.start().done(function(){
        __viewContext.bind(viewModelA);
        
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
    
});