var qmmm019;
(function (qmmm019) {
    var j;
    (function (j) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel1 = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel1() {
                    var self = this;
                    this.boxes = [];
                    this.boxes.push({ id: 1, text: "明細書に印字する行" });
                    this.boxes.push({ id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）" });
                    this.boxes.push({ id: 3, text: "レイアウトから行を削除（登録処理を行うまでは元に戻せます）" });
                }
                return ScreenModel1;
            }());
            viewmodel.ScreenModel1 = ScreenModel1;
            var BoxModel1 = (function () {
                function BoxModel1() {
                }
                return BoxModel1;
            }());
            viewmodel.BoxModel1 = BoxModel1;
        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
    })(j = qmmm019.j || (qmmm019.j = {}));
})(qmmm019 || (qmmm019 = {}));
