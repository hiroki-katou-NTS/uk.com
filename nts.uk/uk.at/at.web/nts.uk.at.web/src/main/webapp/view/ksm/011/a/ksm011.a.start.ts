module nts.uk.at.view.ksm011.a {
    import ksm = nts.uk.at.view.ksm011;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new ksm.viewmodel.TabScreenModel(),
            viewmodelA: new ksm.a.viewmodel.ScreenModel(),
            viewmodelB: new ksm.b.viewmodel.ScreenModel(),
            viewmodelC: new ksm.c.viewmodel.ScreenModel(),
            viewmodelD: new ksm.d.viewmodel.ScreenModel()
        };

            __viewContext.bind(__viewContext.viewModel);
        
        // show active tab panel 
        _.delay(() => {
            $(".link-control a").on("click", function() {
                event.preventDefault();
                $(".link-control a").removeClass('hyperlink-disable');
                $(this).addClass("hyperlink-disable");
                $('#item-panel').animate({ scrollTop: $(".item-panel").scrollTop() + $($(this).attr("href")).position().top - 5 }, 'fast');
            });
            $('.navigator li a.active').trigger('click');
        }, 200);
        
        // show active tab panel 
        _.delay(() => {
            $(".link-control1 a").on("click", function() {
                event.preventDefault();
                $(".link-control1 a").removeClass('hyperlink-disable');
                $(this).addClass("hyperlink-disable");
                $('#item-panel-d').animate({ scrollTop: $("#item-panel-d").scrollTop() + $($(this).attr("href")).position().top }, 'fast');
            });
        }, 2);
    });
}
