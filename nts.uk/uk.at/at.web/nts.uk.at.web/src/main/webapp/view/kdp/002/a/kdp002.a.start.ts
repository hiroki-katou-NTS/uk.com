module nts.uk.at.view.kdp002.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
			if($('#stamp-info')[0]){
		 		setInterval(function () {
                    reCalGridWidthHeight();
                });			
			}
            setTimeout(() => setScroll(screenModel.stampGrid().currentCode()), 200);
			$(window).resize(function () {reCalGridWidthHeight()});
        });
    });
}
