var qpp014;
(function (qpp014) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var data = this.transferred.get();
            if (typeof data === 'undefined' || data === null) {
                nts.uk.request.jump("/view/qpp/014/a/index.xhtml", {});
            }
            else {
                var screenModel = new qpp014.b.viewmodel.ScreenModel(data);
                screenModel.startPage().done(function () {
                    __viewContext.bind(screenModel);
                });
            }
        });
    })(b = qpp014.b || (qpp014.b = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.b.start.js.map