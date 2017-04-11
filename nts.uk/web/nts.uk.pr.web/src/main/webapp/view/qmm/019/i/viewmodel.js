var qmmm019;
(function (qmmm019) {
    var i;
    (function (i) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel("1", '明細書に印字する行'),
                        new BoxModel("2", '明細書に印字しない行 （この行は印刷はされませんが、値の参照・修正が可能です）')
                    ]);
                    self.selectedCode = ko.observable("1");
                    self.enable = ko.observable(true);
                }
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    var totalNormalLineNumber = Number(nts.uk.ui.windows.getShared('totalNormalLineNumber'));
                    var totalGrayLineNumber = Number(nts.uk.ui.windows.getShared('totalGrayLineNumber'));
                    if ((self.selectedCode() === "1" && totalNormalLineNumber === 10)
                        || (self.selectedCode() === "2" && totalGrayLineNumber === 5)) {
                        var msg = self.selectedCode() === "1" ? "明細書に印字する行に行を追加できません。" : "明細書に印字しない行に行を追加できません。";
                        nts.uk.ui.dialog.alert(msg).then(function () {
                            nts.uk.ui.windows.setShared('selectedCode', undefined);
                            nts.uk.ui.windows.close();
                        });
                    }
                    else {
                        nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());
                        nts.uk.ui.windows.close();
                    }
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
        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
    })(i = qmmm019.i || (qmmm019.i = {}));
})(qmmm019 || (qmmm019 = {}));
