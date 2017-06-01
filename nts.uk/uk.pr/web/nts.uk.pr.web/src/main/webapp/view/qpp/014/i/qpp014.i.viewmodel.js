// TreeGrid Node
var qpp014;
(function (qpp014) {
    var i;
    (function (i_1) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                $('#successful').css('display', 'none');
                //$('#stop').css('display', 'none');
                $('#error').css('display', 'none');
                //nts.uk.ui.windows.getSelf().setHeight(570);
                self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
                self.items_I_LST_003 = ko.observableArray([]);
                for (var i_2 = 1; i_2 < 14; i_2++) {
                    self.items_I_LST_003.push(new ItemModel_I_LST_003('00' + i_2, '基本給', "description " + i_2));
                }
                self.currentCode_I_LST_003 = ko.observable();
                self.processingDateInJapanEmprire = ko.observable(nts.uk.ui.windows.getShared("processingDateInJapanEmprire"));
                self.transferBank = ko.observable(nts.uk.ui.windows.getShared("label"));
            }
            /**
             * close dialog
             */
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            /**
             * stop Processing
             */
            ScreenModel.prototype.stopProcessing = function () {
                var self = this;
                self.timer.end();
                nts.uk.ui.windows.setShared("closeDialog", false, true);
                $('#successful').css('display', 'none');
                $('#stop').css('display', 'none');
                $('#error').css('display', '');
                nts.uk.ui.windows.getSelf().setHeight(570);
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