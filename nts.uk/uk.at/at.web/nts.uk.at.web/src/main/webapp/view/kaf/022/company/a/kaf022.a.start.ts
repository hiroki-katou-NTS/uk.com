module nts.uk.at.view.kmf022.a {
    import kmf = nts.uk.at.view.kmf022;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        
        __viewContext.viewModel = {
            tabView: new kmf022.viewmodel.TabScreenModel(),
            viewmodelA: new kmf022.a.viewmodel.ScreenModel(),
            viewmodelL:new kmf022.l.viewmodel.ScreenModel(),
            viewmodelM:new kmf022.m.viewmodel.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);
        // show active tab panel 
        $('.navigator li a.active').trigger('click');
    });
}
$(() => {
    $(window).on('resize', () => {
        var tabContent1 = $(".tab-content-1");
        var tabContent2 = $(".tab-content-2");
        var tabContent3 = $(".tab-content-3");
        var tabContent4 = $(".tab-content-4");
        var tabContent6 = $(".tab-content-6");
        var tabContent7 = $(".tab-content-7");
        var tabContent11 = $(".tab-content-11");
        var tabContent13 = $(".tab-content-13");
        var tabContent14 = $(".tab-content-14");
        var height = window.innerHeight - 200;
        var height4 = window.innerHeight - 240;
        if(tabContent1.length){
            tabContent1.css({
                'height': `${height}px`
            });
        }
        
        if(tabContent2.length){
            tabContent2.css({
                'max-height': `564px`,
                'height': height < 564 ? `${height}px` : ``
            });
        }
        
        if(tabContent3.length){
            tabContent3.css({
                'height': `${height}px`
            });
        }
        
        if(tabContent4.length){
            tabContent4.css({
                'max-height': `308px`,
                'height': height < 308 ? `${height}px` : ``
            });
        }
        
        if(tabContent6.length){
            tabContent6.css({
                'max-height': `470px`,
                'height': height < 470 ? `${height}px` : ``
            });
        }
        
        if(tabContent7.length){
            tabContent7.css({
                'max-height': `737px`,
                'height': height < 737 ? `${height}px` : ``
            });
        }
        
        if(tabContent11.length){
            tabContent11.css({
                'max-height': `445px`,
                'height': height < 455 ? `${height}px` : ``
            });
        }
        
        if(tabContent13.length){
            tabContent13.css({
                'max-height': `420px`,
                'height': height < 420 ? `${height}px` : ``
            });
        }
        
        if(tabContent14.length){
            tabContent14.css({
                'max-height': `801px`,
                'height': height < 801 ? `${height}px` : ``
            });
        }
        
    }).trigger('resize');
});