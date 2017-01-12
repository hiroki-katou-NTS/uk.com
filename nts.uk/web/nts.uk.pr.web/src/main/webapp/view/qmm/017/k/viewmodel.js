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
                    var k;
                    (function (k) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.enable = ko.observable(true);
                                    self.items = ko.observableArray([
                                        { value: 1, text: 'One' },
                                        { value: 2, text: 'Two' },
                                        { value: 3, text: 'Three' }
                                    ]);
                                    self.selectedValue = ko.observable(2);
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
                        var BoxModel = (function () {
                            function BoxModel(id, name) {
                                var self = this;
                                self.id = id;
                                self.name = name;
                            }
                            return BoxModel;
                        }());
                    })(k = qmm017.k || (qmm017.k = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
