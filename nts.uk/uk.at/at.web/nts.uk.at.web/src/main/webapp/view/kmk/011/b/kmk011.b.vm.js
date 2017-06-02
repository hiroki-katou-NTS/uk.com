var kmk011;
(function (kmk011) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.currentCode = ko.observable('');
                    self.columns = ko.observableArray([
                        { headerText: nts.uk.resource.getText('KMK011_37'), key: 'divReasonCode', width: 100 },
                        { headerText: nts.uk.resource.getText('KMK011_38'), key: 'divReasonContent', width: 200 }
                    ]);
                    self.dataSource = ko.observableArray([]);
                    self.switchUSe3 = ko.observableArray([
                        { code: '1', name: nts.uk.resource.getText("Enum_DivergenceReasonInputRequiredAtr_Required") },
                        { code: '0', name: nts.uk.resource.getText("Enum_DivergenceReasonInputRequiredAtr_Optional") },
                    ]);
                    self.requiredAtr = ko.observable(0);
                    self.divReasonCode = ko.observable('');
                    self.divReasonContent = ko.observable('');
                    self.enableCode = ko.observable(false);
                    self.itemDivReason = ko.observable(null);
                    self.divTimeId = ko.observable(null);
                    self.enableDel = ko.observable(true);
                    //subscribe currentCode
                    self.currentCode.subscribe(function (codeChanged) {
                        self.clearError();
                        self.itemDivReason(self.findItemDivTime(codeChanged));
                        if (self.itemDivReason() === undefined || self.itemDivReason() == null) {
                            return;
                        }
                        self.objectOld = self.itemDivReason().divReasonCode + self.itemDivReason().divReasonContent + self.itemDivReason().requiredAtr;
                        self.enableCode(false);
                        self.divReasonCode(self.itemDivReason().divReasonCode);
                        self.divReasonContent(self.itemDivReason().divReasonContent);
                        self.requiredAtr(self.itemDivReason().requiredAtr);
                        self.enableDel(true);
                    });
                }
                /**
                 * start page
                 * get all divergence reason
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    self.currentCode('');
                    var dfd = $.Deferred();
                    self.divTimeId(nts.uk.ui.windows.getShared("KMK011_divTimeId"));
                    b.service.getAllDivReason(self.divTimeId()).done(function (lstDivReason) {
                        if (lstDivReason === undefined || lstDivReason.length == 0) {
                            self.dataSource([]);
                            self.enableCode(true);
                        }
                        else {
                            self.dataSource(lstDivReason);
                            var reasonFirst = _.first(lstDivReason);
                            self.currentCode(reasonFirst.divReasonCode);
                        }
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                /**
                 * find item Divergence Time is selected
                 */
                ScreenModel.prototype.findItemDivTime = function (value) {
                    var self = this;
                    var itemModel = null;
                    return _.find(self.dataSource(), function (obj) {
                        return obj.divReasonCode == value;
                    });
                };
                ScreenModel.prototype.refreshData = function () {
                    var self = this;
                    self.divReasonCode(null);
                    self.divReasonContent("");
                    self.requiredAtr(0);
                    self.enableCode(true);
                    self.clearError();
                    self.enableDel(false);
                    self.currentCode(null);
                    $("#inpCode").focus();
                };
                ScreenModel.prototype.clearError = function () {
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.nts-input').ntsError('clear');
                    }
                };
                ScreenModel.prototype.RegistrationDivReason = function () {
                    var self = this;
                    $('.nts-input').trigger("validate");
                    _.defer(function () {
                        if (!$('.nts-editor').ntsError("hasError")) {
                            if (self.enableCode() == false) {
                                var objectNew = self.convertCode(self.divReasonCode()) + self.divReasonContent() + self.requiredAtr();
                                if (self.objectOld == objectNew) {
                                    return;
                                }
                                self.updateDivReason();
                            }
                            else if (self.enableCode() == true) {
                                self.addDivReason();
                            }
                        }
                    });
                };
                ScreenModel.prototype.addDivReason = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.convertCode(self.divReasonCode());
                    var divReason = new model.Item(self.divTimeId(), self.divReasonCode(), self.divReasonContent(), self.requiredAtr());
                    b.service.addDivReason(divReason).done(function () {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                        self.getAllDivReasonNew();
                    }).fail(function (error) {
                        $('#inpCode').ntsError('set', error);
                    });
                };
                ScreenModel.prototype.convertCode = function (value) {
                    var self = this;
                    if (value.length == 1) {
                        var code = '0' + value;
                        self.divReasonCode(code);
                    }
                    else
                        return;
                };
                ScreenModel.prototype.updateDivReason = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var divReason = new model.Item(self.divTimeId(), self.divReasonCode(), self.divReasonContent(), self.requiredAtr());
                    b.service.updateDivReason(divReason).done(function () {
                        self.getAllDivReasonNew();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject(res);
                    });
                };
                //get all divergence reason new
                ScreenModel.prototype.getAllDivReasonNew = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.dataSource();
                    b.service.getAllDivReason(self.divTimeId()).done(function (lstDivReason) {
                        self.currentCode('');
                        self.dataSource(lstDivReason);
                        self.enableCode(false);
                        self.currentCode(self.divReasonCode());
                        dfd.resolve();
                    }).fail(function (error) {
                        nts.uk.ui.dialog.alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                //delete divergence reason
                ScreenModel.prototype.deleteDivReason = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_18')).ifYes(function () {
                        var divReason = self.itemDivReason();
                        self.index_of_itemDelete = self.dataSource().indexOf(self.itemDivReason());
                        b.service.deleteDivReason(divReason).done(function () {
                            //                    self.getDivReasonList_afterDelete();
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_16')).then(function () {
                                //                        $("#inpCode").focus();
                                self.getDivReasonList_afterDelete();
                                $("#inpCode").focus();
                                //                        self.refreshData();
                            });
                        });
                    }).ifNo(function () {
                        return;
                    });
                };
                //get list divergence reason after Delete 1 divergence reason
                ScreenModel.prototype.getDivReasonList_afterDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.dataSource();
                    b.service.getAllDivReason(self.divTimeId()).done(function (lstDivReason) {
                        self.dataSource(lstDivReason);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].divReasonCode);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete].divReasonCode);
                            }
                        }
                        else {
                            self.refreshData();
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        nts.uk.ui.dialog.alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var Item = (function () {
                    function Item(divTimeId, divReasonCode, divReasonContent, requiredAtr) {
                        this.divTimeId = divTimeId;
                        this.divReasonCode = divReasonCode;
                        this.divReasonContent = divReasonContent;
                        this.requiredAtr = requiredAtr;
                    }
                    return Item;
                }());
                model.Item = Item;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = kmk011.b || (kmk011.b = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.b.vm.js.map