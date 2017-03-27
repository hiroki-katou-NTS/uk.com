var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var i;
                    (function (i) {
                        __viewContext.ready(function () {
                            var screenModel = new i.viewmodel.ScreenModel(nts.uk.ui.windows.getShared("officeName"), nts.uk.ui.windows.getShared("pensionModel"));
                            screenModel.startPage().done(function () {
                                __viewContext.bind(screenModel);
                            });
                        });
                    })(i = qmm008.i || (qmm008.i = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
