var qpp021;
(function (qpp021) {
    var g;
    (function (g) {
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
        })(viewmodel = g.viewmodel || (g.viewmodel = {}));
    })(g = qpp021.g || (qpp021.g = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.g.vm.js.map