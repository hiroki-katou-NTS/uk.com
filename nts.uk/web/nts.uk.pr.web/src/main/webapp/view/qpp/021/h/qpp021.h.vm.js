var qpp021;
(function (qpp021) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qpp021.h || (qpp021.h = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.h.vm.js.map