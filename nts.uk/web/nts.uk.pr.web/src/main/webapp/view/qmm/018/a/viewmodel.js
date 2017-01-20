var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //self.paymentDateProcessingList = ko.observableArray([]);
                    //self.selectedPaymentDate = ko.observable(null);
                    self.checked = ko.observable(true);
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '就業からの連携' },
                        { code: '2', name: '明細書項目から選択' }
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                    self.itemList = ko.observableArray([
                        { code: '1', name: '足した後' },
                        { code: '2', name: '足す前' }
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(3);
                    self.selectedItemList = ko.observableArray([]);
                    self.texteditor1 = {
                        value: ko.computed(function () {
                            var s;
                            ko.utils.arrayForEach(self.selectedItemList(), function (item) { if (!s) {
                                s = item.name;
                            }
                            else {
                                s += " + " + item.name;
                            } });
                            return s;
                        }),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "500px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    self.percentage = ko.observable('60');
                    self.texteditor2 = {
                        value: self.percentage,
                        constraint: 'ExceptionPayRate',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "30px",
                            textalign: "center"
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                /*
                startPage(): JQueryPromise<any> {
                    var self = this;
        
                    var dfd = $.Deferred();
                    qmm018.a.service.getPaymentDateProcessingList().done(function(data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function(res) {
        
                    });
                    return dfd.promise();
                }
                */
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = {
                        attendDayGettingSet: self.selectedRuleCode(),
                        exceptionPayRate: parseInt(self.texteditor2.value()),
                        roundDigitSet: self.currentCode(),
                        roundTimingSet: self.checked() ? 1 : 0
                    };
                    qmm018.a.service.saveData(command).done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openSubWindow = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", { title: "労働日数項目一覧", dialogClass: "no-close" }).onClosed(function () {
                        var selectedList = nts.uk.ui.windows.getShared('selectedItemList');
                        self.selectedItemList.removeAll();
                        if (selectedList().length) {
                            ko.utils.arrayForEach(selectedList(), function (item) { self.selectedItemList.push(item); });
                        }
                    });
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
