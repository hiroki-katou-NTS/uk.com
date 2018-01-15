module nts.uk.at.view.kcp006.b {
    __viewContext.ready(function() {
        let data = nts.uk.ui.windows.getShared('eventData');
        var screenModel = new viewmodel.ScreenModelB(data);
        screenModel.start().done(() => {
            __viewContext.bind(screenModel);
        });
    });
}