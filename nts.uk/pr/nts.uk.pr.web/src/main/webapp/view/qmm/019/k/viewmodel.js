var qmmm019;
(function (qmmm019) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel2 = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel2() {
                    var self = this;
                    this.boxes2 = [];
                    this.boxes2.push({ id: 1, text: "明細書に印字する行" });
                    this.boxes2.push({ id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）" });
                    this.boxes2.push({ id: 3, text: "レイアウトから行を削除（登録処理を行うまでは元に戻せます）" });
                }
                ScreenModel2.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel2;
            }());
            viewmodel.ScreenModel2 = ScreenModel2;
            var BoxModel2 = (function () {
                function BoxModel2() {
                }
                return BoxModel2;
            }());
            viewmodel.BoxModel2 = BoxModel2;
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmmm019.k || (qmmm019.k = {}));
})(qmmm019 || (qmmm019 = {}));
