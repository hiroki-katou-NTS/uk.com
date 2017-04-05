var qpp021;
(function (qpp021) {
    var c;
    (function (c) {
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
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qpp021.c || (qpp021.c = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.c.vm.js.map