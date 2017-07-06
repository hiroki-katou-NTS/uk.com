__viewContext.ready(function() {
    __viewContext.viewModel = {
        viewmodelA: new ccg018.a.viewmodel.ScreenModel(),
        viewmodelA1: new ccg018.a1.viewmodel.ScreenModel(),
        viewmodelB: new ccg018.b.viewmodel.ScreenModel(),
    };
    __viewContext.bind(__viewContext.viewModel);

    // show active tab panel 
    $('.navigator li a.active').trigger('click');
});