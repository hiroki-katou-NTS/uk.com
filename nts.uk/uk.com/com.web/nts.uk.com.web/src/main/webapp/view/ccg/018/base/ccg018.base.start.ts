__viewContext.ready(function() {
    __viewContext.viewModel = {
        viewmodelBase: new ccg018.base.viewmodel.ScreenModel(),
        viewmodelA: new ccg018.a.viewmodel.ScreenModel(),
        viewmodelB: new ccg018.b.viewmodel.ScreenModel(),
    };
    __viewContext.bind(__viewContext.viewModel);

    // show active tab panel 
    $('.navigator li a.active').trigger('click');
});