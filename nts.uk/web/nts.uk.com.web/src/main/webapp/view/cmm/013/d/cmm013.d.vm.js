var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.inp_003 = ko.observable(null);
                    self.inp_003_enable = ko.observable(true);
                    self.endDateUpdate = ko.observable('');
                    self.enable = ko.observable(true);
                    self.oldStartDate = ko.observable('');
                    self.lstMessage = ko.observableArray([]);
                    self.histIdUpdate = ko.observable('');
                    self.oldEndDate = ko.observable('');
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.endDateUpdate(nts.uk.ui.windows.getShared('cmm013EndDate'));
                    self.oldEndDate(nts.uk.ui.windows.getShared('cmm013OldEndDate'));
                    self.oldStartDate(nts.uk.ui.windows.getShared('cmm013StartDate'));
                    self.histIdUpdate(nts.uk.ui.windows.getShared('cmm013HistoryId'));
                    self.listMessage();
                    self.setValueForRadio();
                    self.selectedId.subscribe(function (newValue) {
                        if (newValue == 1) {
                            self.inp_003_enable(false);
                        }
                        else {
                            self.inp_003_enable(true);
                        }
                    });
                    self.inp_003(self.oldStartDate());
                    if (self.oldEndDate() === "9999/12/31") {
                        self.enable(true);
                    }
                    else {
                        self.enable(false);
                    }
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.setValueForRadio = function () {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '履歴を削除する '),
                        new BoxModel(2, '履歴を修正する')
                    ]);
                    self.selectedId = ko.observable(2);
                };
                ScreenModel.prototype.listMessage = function () {
                    var self = this;
                    self.lstMessage.push(new ItemMessage("ER001", "*が入力されていません。"));
                    self.lstMessage.push(new ItemMessage("ER005", "入力した*は既に存在しています。\r\n*を確認してください。"));
                    self.lstMessage.push(new ItemMessage("ER010", "対象データがありません。"));
                    self.lstMessage.push(new ItemMessage("AL001", "変更された内容が登録されていません。\r\nよろしいですか。"));
                    self.lstMessage.push(new ItemMessage("AL002", "データを削除します。\r\nよろしいですか？"));
                    self.lstMessage.push(new ItemMessage("ER026", "更新対象のデータが存在しません。"));
                    self.lstMessage.push(new ItemMessage("ER023", "履歴の期間が重複しています。"));
                };
                ScreenModel.prototype.clearShared = function () {
                    nts.uk.ui.windows.setShared('cmm013StartDate', '', true);
                    nts.uk.ui.windows.setShared('cmm013EndDate', '', true);
                    nts.uk.ui.windows.setShared('cmm013HistoryId', '', true);
                };
                ScreenModel.prototype.closeDialog = function () {
                    var self = this;
                    self.clearShared();
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.positionHis = function () {
                    var self = this;
                    var historyInfo = new model.ListHistoryDto(self.histIdUpdate(), self.oldStartDate(), self.inp_003());
                    if (self.selectedId() === 1) {
                        var AL002 = _.find(self.lstMessage(), function (mess) {
                            return mess.messCode === "AL002";
                        });
                        nts.uk.ui.dialog.confirm(AL002.messName).ifCancel(function () {
                            return;
                        }).ifYes(function () {
                            d.service.deleteHistory(historyInfo).fail(function (res) {
                                var delMess = _.find(self.lstMessage(), function (mess) {
                                    return mess.messCode === "ER005";
                                });
                                nts.uk.ui.dialog.alert(delMess.messName);
                                nts.uk.ui.windows.close();
                            }).done(function () {
                                self.clearShared();
                                nts.uk.ui.windows.close();
                            });
                        });
                    }
                    else {
                        var originallyStartDate = new Date(self.oldStartDate());
                        var newStartDate = new Date(self.inp_003());
                        if (originallyStartDate >= newStartDate) {
                            var AL023 = _.find(self.lstMessage(), function (mess) {
                                return mess.messCode === "ER023";
                            });
                            nts.uk.ui.dialog.alert(AL023.messName);
                            nts.uk.ui.windows.close();
                        }
                        else {
                            d.service.updateHistory(historyInfo).fail(function (res) {
                                var upMess = _.find(self.lstMessage(), function (mess) {
                                    return mess.messCode === res.message;
                                });
                                nts.uk.ui.dialog.alert(upMess.messName);
                            }).done(function () {
                                self.clearShared();
                                nts.uk.ui.windows.close();
                            });
                        }
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    this.id = id;
                    this.name = name;
                }
                return BoxModel;
            }());
            var model;
            (function (model) {
                var ListHistoryDto = (function () {
                    function ListHistoryDto(historyId, oldStartDate, newStartDate) {
                        this.historyId = historyId;
                        this.oldStartDate = oldStartDate;
                        this.newStartDate = newStartDate;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
            })(model = viewmodel.model || (viewmodel.model = {}));
            var ItemMessage = (function () {
                function ItemMessage(messCode, messName) {
                    this.messCode = messCode;
                    this.messName = messName;
                }
                return ItemMessage;
            }());
            viewmodel.ItemMessage = ItemMessage;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.vm.js.map