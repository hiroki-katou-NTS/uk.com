var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var ccg015;
                (function (ccg015) {
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.code = ko.observable("code");
                                    self.name = ko.observable("name");
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = ccg015.c || (ccg015.c = {}));
                })(ccg015 = view.ccg015 || (view.ccg015 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ccg015.c.vm.js.map