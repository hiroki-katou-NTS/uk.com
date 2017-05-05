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
                                function ScreenModel(topPageCode, topPageName) {
                                    var self = this;
                                    self.parentTopPageCode = ko.observable(topPageCode);
                                    self.parentTopPageName = ko.observable(topPageName);
                                    self.newTopPageCode = ko.observable("");
                                    self.newTopPageName = ko.observable("");
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