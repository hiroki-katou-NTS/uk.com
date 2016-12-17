var qmmm019;
(function (qmmm019) {
    var j;
    (function (j) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '明細書に印字する行'),
                        new BoxModel(2, '明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）'),
                        new BoxModel(3, 'レイアウトから行を削除（登録処理を行うまでは元に戻せます）')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.setShared('selectedCode', undefined);
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
        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
    })(j = qmmm019.j || (qmmm019.j = {}));
})(qmmm019 || (qmmm019 = {}));
