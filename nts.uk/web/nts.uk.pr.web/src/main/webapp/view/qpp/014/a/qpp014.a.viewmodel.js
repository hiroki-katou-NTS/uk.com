var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.a_SEL_001_items = ko.observableArray([]);
                    self.a_SEL_001_itemSelected = ko.observable();
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findAll().done(function () {
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.findAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qpp014.a.service.findAll(1)
                        .done(function (data) {
                        if (data.length > 0) {
                            self.a_SEL_001_items(data);
                            self.a_SEL_001_itemSelected(self.a_SEL_001_items()[0]);
                            self.a_SEL_001_itemSelected().process = ' ' + self.a_SEL_001_itemSelected().processingNo + ' : ' + self.a_SEL_001_itemSelected().processingName;
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。");
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.nextScreen = function () {
                    var self = this;
                    var data = _.find(self.a_SEL_001_items(), function (x) {
                        return x.processingNo === self.a_SEL_001_itemSelected();
                    });
                    nts.uk.request.jump("/view/qpp/014/b/index.xhtml", data);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.a.viewmodel.js.map