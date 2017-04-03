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
                    $('.func-btn').css('visibility', 'hidden');
                    $('#screenB').css('display', 'none');
                    var self = this;
                    self.a_SEL_001_items = ko.observableArray([
                        new qpp014.shr.viewmodelbase.PayDayProcessing('1', 1, '1', 1, 1, 1, 1),
                        new qpp014.shr.viewmodelbase.PayDayProcessing('2', 2, '2', 2, 2, 2, 2),
                        new qpp014.shr.viewmodelbase.PayDayProcessing('3', 3, '3', 3, 3, 3, 3)
                    ]);
                    self.a_SEL_001_itemSelected = ko.observable(1);
                }
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