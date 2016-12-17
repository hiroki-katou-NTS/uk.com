var qmmm019;
(function (qmmm019) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel("1", '明細書に印字する行'),
                        new BoxModel("2", '明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）'),
                        new BoxModel("3", 'レイアウトから行を削除（登録処理を行うまでは元に戻せます）')
                    ]);
                    self.selectedCode = ko.observable("1");
                    self.enable = ko.observable(true);
                }
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmmm019.k || (qmmm019.k = {}));
})(qmmm019 || (qmmm019 = {}));
