var qpp014;
(function (qpp014) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.b_stepList = [
                        { content: '.step-1' },
                        { content: '.step-2' }
                    ];
                    self.b_stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                    self.viewmodeld = new qpp014.d.viewmodel.ScreenModel(data);
                    self.viewmodelg = new qpp014.g.viewmodel.ScreenModel(data);
                    self.viewmodelh = new qpp014.h.viewmodel.ScreenModel(data);
                    self.data = data;
                    //sparePayAtr not transfer value in screen d, so tranfer it here
                    self.viewmodeld.sparePayAtr.subscribe(function (newValue) {
                        nts.uk.ui.windows.setShared("sparePayAtr", newValue, true);
                    });
                    //transfer data between screen d and screen g
                    self.viewmodelg.g_INP_001(ko.computed(function () {
                        return self.viewmodelg.g_INP_001(self.viewmodeld.dateOfPayment());
                    }));
                    //transfer data between screen d and screen h
                    self.viewmodelh.h_INP_001(ko.computed(function () {
                        return self.viewmodelh.h_INP_001(self.viewmodeld.dateOfPayment());
                    }));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                /**
                 * back to screen A
                 * AL004
                 */
                ScreenModel.prototype.backToScreenA = function () {
                    nts.uk.ui.dialog.confirm("処理を中断します。\r\n よろしいですか？").ifYes(function () {
                        nts.uk.request.jump("/view/qpp/014/a/index.xhtml");
                    }).ifNo(function () {
                        return;
                    });
                };
                /**
                 * go to screen J
                 */
                ScreenModel.prototype.goToScreenJ = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared("data", self.data, true);
                    nts.uk.ui.windows.setShared("dateOfPayment", self.viewmodeld.dateOfPayment(), true);
                    nts.uk.ui.windows.sub.modal("/view/qpp/014/j/index.xhtml", { title: "振込チェックリスト", dialogClass: "no-close" });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp014.b || (qpp014.b = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.b.viewmodel.js.map