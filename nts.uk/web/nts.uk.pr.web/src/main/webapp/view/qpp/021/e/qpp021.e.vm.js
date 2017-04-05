var qpp021;
(function (qpp021) {
    var e;
    (function (e) {
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
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qpp021.e || (qpp021.e = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.e.vm.js.map