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
                                    self.previousStartMonth = ko.observable('');
                                    self.getDate();
                                }
                                ScreenModel.prototype.getDate = function () {
                                    var self = this;
                                    self.OfficeItemModel().childs.forEach(function (item, index) {
                                        if (item.code == self.selectedHistoryCode()) {
                                            if (index > 0) {
                                                var previousViewRangeString = self.OfficeItemModel().childs[index + 1].codeName;
                                                var previousRangeCharIndex = previousViewRangeString.indexOf("~");
                                                self.previousStartMonth(previousViewRangeString.substr(0, previousRangeCharIndex));
                                            }
                                            var viewRangeString = self.OfficeItemModel().childs[index].codeName;
                                            var rangeCharIndex = viewRangeString.indexOf("~");
                                            self.endMonth(viewRangeString.substr(rangeCharIndex + 1, viewRangeString.length));
                                            self.startMonth(viewRangeString.substr(0, rangeCharIndex));
                                        }
                                    });
                                };
                                ScreenModel.prototype.compareStringDate = function (date1, date2) {
                                    var index1 = date1.indexOf("/");
                                    var year1 = Number(date1.substring(0, index1));
                                    var month1 = Number(date1.substring(index1 + 1, date1.length));
                                    var index2 = date2.indexOf("/");
                                    var year2 = Number(date2.substring(0, index2));
                                    var month2 = Number(date2.substring(index2 + 1, date2.length));
                                    if (year1 < year2) {
                                        return true;
                                    }
                                    else {
                                        if (month1 < month2) {
                                            return true;
                                        }
                                        else {
                                            return false;
                                        }
                                    }
                                };
                                ScreenModel.prototype.minusOneMonth = function (stringDate) {
                                    var index = stringDate.indexOf("/");
                                    var year = stringDate.substr(0, index);
                                    var month = (Number(stringDate.substring(index + 1, stringDate.length)) - 1).toString();
                                    if (month == "0") {
                                        year = (Number(year) - 1).toString();
                                    }
                                    return month.length == 1 ? year + "/0" + month : year + "/" + month;
                                };
                                ScreenModel.prototype.clickSettingButton = function () {
                                    var self = this;
                                    self.OfficeItemModel().childs.forEach(function (item, index) {
                                        if (item.code == self.selectedHistoryCode()) {
                                            if (self.compareStringDate(self.startMonth(), self.endMonth()) && self.compareStringDate(self.previousStartMonth(), self.minusOneMonth(self.startMonth()))) {
                                                self.OfficeItemModel().childs[index].codeName = self.startMonth() + "~" + self.endMonth();
                                                self.OfficeItemModel().childs[index + 1].codeName = self.previousStartMonth() + "~" + self.minusOneMonth(self.startMonth());
                                            }
                                            else {
                                                alert("ER011");
                                            }
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
