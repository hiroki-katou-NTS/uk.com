var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.viewmodelb = new qpp014.b.viewmodel.ScreenModel();
                    this.viewmodeld = new qpp014.d.viewmodel.ScreenModel();
                    this.viewmodelg = new qpp014.g.viewmodel.ScreenModel();
                    this.viewmodelh = new qpp014.h.viewmodel.ScreenModel();
                    var self = this;
                    $('.func-btn').css('visibility', 'hidden');
                    $('#screenB').css('display', 'none');
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
                    qpp014.a.service.findAll(0)
                        .done(function (data) {
                        if (data.length > 0) {
                            self.a_SEL_001_items(data);
                            self.a_SEL_001_itemSelected(self.a_SEL_001_items()[0]);
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
                    $("#screenA").css("display", "none");
                    $("#screenB").css("display", "");
                    $("#screenB").ready(function () {
                        $(".func-btn").css("visibility", "visible");
                    });
                };
                ScreenModel.prototype.backScreen = function () {
                    $("#screenB").css("display", "none");
                    $("#screenA").css("display", "");
                    $(".func-btn").css("visibility", "hidden");
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.a.viewmodel.js.map