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
                    var p;
                    (function (p) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.items = ko.observableArray([]);
                                    for (var i = 1; i < 100; i++) {
                                        this.items.push(new ItemModel('00' + i, '基本給', "description " + i, "other" + i));
                                    }
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 100 }
                                    ]);
                                    this.currentCodeList = ko.observableArray([]);
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = p.viewmodel || (p.viewmodel = {}));
                        var ItemModel = (function () {
                            function ItemModel(code, name, description, other1, other2) {
                                this.code = code;
                                this.name = name;
                                this.description = description;
                                this.other1 = other1;
                                this.other2 = other2 || other1;
                            }
                            return ItemModel;
                        }());
                    })(p = qmm017.p || (qmm017.p = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
