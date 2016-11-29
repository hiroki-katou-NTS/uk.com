var qmmm019;
(function (qmmm019) {
    var i;
    (function (i) {
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
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel() {
                }
                return BoxModel;
            }());
            viewmodel.BoxModel = BoxModel;
        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
    })(i = qmmm019.i || (qmmm019.i = {}));
})(qmmm019 || (qmmm019 = {}));
