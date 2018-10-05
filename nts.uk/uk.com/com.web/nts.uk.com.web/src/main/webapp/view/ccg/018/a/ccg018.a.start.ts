__viewContext.ready(function() {
    var viewModelA = new ccg018.a.viewmodel.ScreenModel();
    
    viewModelA.start().done(function(){
        __viewContext.bind(viewModelA);
        let self = nts.uk.ui._viewModel.content;
        self.changeTab(self.tabs()[0]);
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
    
});