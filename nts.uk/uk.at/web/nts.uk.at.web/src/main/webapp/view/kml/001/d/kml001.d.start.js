var kml001;
(function (kml001) {
    var d;
    (function (d) {
        __viewContext.ready(function () {
            var screenModel = new d.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            if (screenModel.isUpdate())
                $('#startDateInput').focus();
        });
    })(d = kml001.d || (kml001.d = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.d.start.js.map