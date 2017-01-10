var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    this.items = ko.observableArray([
                        new ItemModel('001', 'name1'),
                        new ItemModel('002', 'name2'),
                        new ItemModel('003', 'name3')
                    ]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 40 },
                        { headerText: '名称', prop: 'name', width: 130 },
                    ]);
                    this.currentCode = ko.observable();
                    this.currentCodeList = ko.observableArray([]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.b.service.getPaymentDateProcessingList().done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    nts.uk.ui.windows.setShared('selectedCodeList', this.currentCodeList);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeWindow = function () {
                    nts.uk.ui.windows.setShared('selectedCodeList', ko.observableArray([]));
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
