var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var f;
                    (function (f) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(receiveOfficeItem, selectedHistoryCode) {
                                    var self = this;
                                    self.OfficeItemModel = ko.observable(receiveOfficeItem);
                                    self.selectedHistoryCode = ko.observable(selectedHistoryCode);
                                    self.listOptions = ko.observableArray([new optionsModel(1, "履歴を削除する"), new optionsModel(2, "履歴を修正する")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.modalValue = ko.observable("Goodbye world!");
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    if (receiveOfficeItem != null) {
                                        this.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                                    }
                                    self.startMonth = ko.observable('');
                                    self.endMonth = ko.observable('');
                                    self.getDate();
                                }
                                ScreenModel.prototype.getDate = function () {
                                    var self = this;
                                    self.OfficeItemModel().childs.forEach(function (item, index) {
                                        if (item.code == self.selectedHistoryCode()) {
                                            var viewRangeString = self.OfficeItemModel().childs[index].codeName;
                                            var rangeCharIndex = viewRangeString.indexOf("~");
                                            self.endMonth(viewRangeString.substr(rangeCharIndex + 1, viewRangeString.length));
                                            self.startMonth(viewRangeString.substr(0, rangeCharIndex));
                                        }
                                    });
                                };
                                ScreenModel.prototype.clickSettingButton = function () {
                                    var self = this;
                                    self.OfficeItemModel().childs.forEach(function (item, index) {
                                        if (item.code == self.selectedHistoryCode()) {
                                            self.OfficeItemModel().childs[index].codeName = self.startMonth() + "~" + self.endMonth();
                                        }
                                    });
                                    nts.uk.ui.windows.setShared("updateHistoryChildValue", self.OfficeItemModel(), true);
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("updateHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qmm008.f || (qmm008.f = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
