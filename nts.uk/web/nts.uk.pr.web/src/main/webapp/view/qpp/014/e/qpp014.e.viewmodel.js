var qpp014;
(function (qpp014) {
    var e;
    (function (e) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                $('#successful').css('display', 'none');
                $('#error').css('display', 'none');
                self.items_E_LST_003 = ko.observableArray([]);
                self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i_1, '基本給', "description " + i_1));
                }
                self.currentCode_E_LST_003 = ko.observable();
                self.transferDate = ko.observable(nts.uk.time.parseYearMonthDate(nts.uk.ui.windows.getShared("transferDate")).format());
                self.processingDate = ko.observable(nts.uk.ui.windows.getShared("processingDate"));
            }
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.setShared("closeDialog", true, true);
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.goToScreenGOrH = function () {
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.stopProcessing = function () {
                var self = this;
                self.timer.end();
                nts.uk.ui.windows.setShared("closeDialog", false, true);
                $('#successful').css('display', 'none');
                $('#stop').css('display', 'none');
                $('#error').css('display', '');
                nts.uk.ui.windows.getSelf().setHeight(595);
            };
            return ScreenModel;
        }());
        e.ScreenModel = ScreenModel;
        var ItemModel_E_LST_003 = (function () {
            function ItemModel_E_LST_003(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_E_LST_003;
        }());
        e.ItemModel_E_LST_003 = ItemModel_E_LST_003;
    })(e = qpp014.e || (qpp014.e = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.e.viewmodel.js.map