var qpp014;
(function (qpp014) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.viewmodeld = new qpp014.d.viewmodel.ScreenModel();
                    this.viewmodelg = new qpp014.g.viewmodel.ScreenModel();
                    this.viewmodelh = new qpp014.h.viewmodel.ScreenModel();
                    var self = this;
                    self.b_stepList = [
                        { content: '.step-1' },
                        { content: '.step-2' }
                    ];
                    self.b_stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.goToScreenA = function () {
                    nts.uk.request.jump("/view/qpp/014/a/index.xhtml");
                };
                ScreenModel.prototype.goToScreenJ = function () {
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