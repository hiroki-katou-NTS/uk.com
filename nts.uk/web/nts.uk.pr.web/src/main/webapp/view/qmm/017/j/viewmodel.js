var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var j;
                    (function (j) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.itemList = ko.observableArray([
                                        new BoxModel(1, 'box 1'),
                                        new BoxModel(2, 'box 2')
                                    ]);
                                    self.selectedId = ko.observable(1);
                                    self.enable = ko.observable(true);
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
                        var BoxModel = (function () {
                            function BoxModel(id, name) {
                                var self = this;
                                self.id = id;
                                self.name = name;
                            }
                            return BoxModel;
                        }());
                    })(j = qmm017.j || (qmm017.j = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
