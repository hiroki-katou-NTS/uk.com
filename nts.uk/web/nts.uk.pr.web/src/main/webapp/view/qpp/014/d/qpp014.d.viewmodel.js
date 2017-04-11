var qpp014;
(function (qpp014) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.date = ko.observable('20161212');
                    self.d_SEL_001_selectedCode = ko.observable(1);
                    self.d_SEL_002_selectedCode = ko.observable(1);
                    self.d_LST_001_items = ko.observableArray([]);
                    for (var i_1 = 1; i_1 < 31; i_1++) {
                        self.d_LST_001_items.push(({ code: '00' + i_1, name: '基本給' + i_1, description: ('description' + i_1) }));
                    }
                    self.countItems = ko.observable(self.d_LST_001_items().length);
                    self.d_LST_001_itemSelected = ko.observable(0);
                    self.d_nextScreen = ko.computed(function () {
                        return self.d_SEL_002_selectedCode() == 2 ? 'screen_g' : 'screen_h';
                    });
                    self.d_lbl_002 = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
                    self.d_lbl_015 = ko.observable('( ' + data.processingNo + ' : ');
                    self.d_lbl_016 = ko.observable(data.processingName + ' )');
                }
                ScreenModel.prototype.openEDialog = function () {
                    nts.uk.ui.windows.sub.modal("/view/qpp/014/e/index.xhtml", { title: "振込データの作成結果一覧", dialogClass: "no-close" }).onClosed(function () {
                        $('#wizard').ntsWizard("next");
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qpp014.d || (qpp014.d = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.d.viewmodel.js.map