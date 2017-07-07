module nts.uk.sys.view.ccg013.j {
    __viewContext.ready(function() {
        console.time('初期データ取得');
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        console.timeEnd('初期データ取得');
    });
}