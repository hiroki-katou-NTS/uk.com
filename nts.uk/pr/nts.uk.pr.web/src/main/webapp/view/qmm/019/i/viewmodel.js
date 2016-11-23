var qmmm019;
(function (qmmm019) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    this.boxes = [];
                    this.boxes.push({ id: 1, text: "明細書に印字する行" });
                    this.boxes.push({ id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）" });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel() {
                }
                return BoxModel;
            }());
            viewmodel.BoxModel = BoxModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmmm019.a || (qmmm019.a = {}));
})(qmmm019 || (qmmm019 = {}));
