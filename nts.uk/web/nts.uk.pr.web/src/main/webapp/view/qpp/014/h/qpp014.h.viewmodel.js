var qpp014;
(function (qpp014) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.h_INP_001 = ko.observable();
                    self.h_LST_001_items = ko.observableArray([]);
                    for (var i_1 = 1; i_1 < 100; i_1++) {
                        self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i_1, '基本給', "description " + i_1));
                    }
                    self.h_LST_001_itemsSelected = ko.observable();
                    self.yearMonthDateInJapanEmpire = ko.computed(function () {
                        if (self.h_INP_001() == undefined || self.h_INP_001() == null || self.h_INP_001() == "") {
                            return '';
                        }
                        return "(" + nts.uk.time.yearInJapanEmpire(moment(self.h_INP_001()).format('YYYY')).toString() +
                            moment(self.h_INP_001()).format('MM') + " 月 " + moment(self.h_INP_001()).format('DD') + " 日)";
                    });
                    self.processingDate = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
                    self.processingDateInJapanEmprire = ko.computed(function () {
                        return "(" + nts.uk.time.yearmonthInJapanEmpire(self.processingDate()).toString() + ")";
                    });
                    self.processingNo = ko.observable(data.processingNo + ' : ');
                    self.processingName = ko.observable(data.processingName + ' )');
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel_H_LST_001 = (function () {
                function ItemModel_H_LST_001(code, name, description) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                }
                return ItemModel_H_LST_001;
            }());
            viewmodel.ItemModel_H_LST_001 = ItemModel_H_LST_001;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qpp014.h || (qpp014.h = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.h.viewmodel.js.map