var qpp014;
(function (qpp014) {
    var i;
    (function (i_1) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                $('#successful').css('display', 'none');
                $('#stop').css('display', 'none');
                nts.uk.ui.windows.getSelf().setHeight(570);
                self.items_I_LST_003 = ko.observableArray([]);
                for (var i_2 = 1; i_2 < 14; i_2++) {
                    self.items_I_LST_003.push(new ItemModel_I_LST_003('00' + i_2, '基本給', "description " + i_2));
                }
                self.currentCode_I_LST_003 = ko.observable();
            }
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        i_1.ScreenModel = ScreenModel;
        var ItemModel_I_LST_003 = (function () {
            function ItemModel_I_LST_003(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_I_LST_003;
        }());
        i_1.ItemModel_I_LST_003 = ItemModel_I_LST_003;
    })(i = qpp014.i || (qpp014.i = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.i.viewmodel.js.map