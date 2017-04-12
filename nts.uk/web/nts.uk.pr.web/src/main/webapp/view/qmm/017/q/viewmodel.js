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
                    var q;
                    (function (q) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.items = ko.observableArray([]);
                                    for (var i = 1; i < 4; i++) {
                                        this.items.push(new ItemModel('00' + i, '基本給', "description " + i));
                                    }
                                    this.columns = ko.observableArray([
                                        { headerText: '計算式の項目名', prop: 'code', width: 150 },
                                        { headerText: '値', prop: 'value', width: 100 }
                                    ]);
                                    this.currentCodeList = ko.observableArray([]);
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = q.viewmodel || (q.viewmodel = {}));
                        var ItemModel = (function () {
                            function ItemModel(name, code, description) {
                                this.name = name;
                                this.code = code;
                                this.description = description;
                            }
                            return ItemModel;
                        }());
                    })(q = qmm017.q || (qmm017.q = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
