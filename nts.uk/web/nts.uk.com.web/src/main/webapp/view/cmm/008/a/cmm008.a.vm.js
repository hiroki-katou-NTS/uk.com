var cmm008;
(function (cmm008) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.employmentName = ko.observable("");
                    self.isCheckbox = ko.observable(false);
                    self.closeDateList = ko.observableArray([]);
                    self.selectedCloseCode = ko.observable('システム未導入');
                    self.managementHolidays = ko.observableArray([]);
                    self.holidayCode = ko.observable(0);
                    self.processingDateList = ko.observableArray([]);
                    self.selectedProcessNo = ko.observable(0);
                    self.employmentOutCode = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dataSource = ko.observableArray([]);
                    self.currentCode = ko.observable("");
                    self.employmentCode = ko.observable("");
                    self.isEnable = ko.observable(false);
                    self.isDelete = ko.observable(true);
                    self.lstMessage = ko.observableArray([]);
                    self.isMess = ko.observable(false);
                    self.isUseKtSet = ko.observable(0);
                    self.multilineeditor = {
                        memoValue: ko.observable(""),
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                    };
                }
                ;
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var heightScreen = $(window).height();
                    var widthScreen = $(window).width();
                    var heightHeader = $('#header').height() + $('#functions-area').height();
                    var height = heightScreen - heightHeader - 80;
                    $('#contents-left').css({ height: height, width: widthScreen * 40 / 100 });
                    $('#contents-right').css({ height: height, width: widthScreen * 60 / 100 });
                    self.listMessage();
                    $.when(self.userKtSet()).done(function () {
                        self.closeDateListItem();
                        self.processingDateItem();
                        self.managementHolidaylist();
                        self.dataSourceItem();
                        dfd.resolve(self.holidayCode());
                    });
                    self.currentCode.subscribe(function (newValue) {
                        if (!self.checkChange(self.employmentCode())) {
                            var AL001 = _.find(self.lstMessage(), function (mess) {
                                return mess.messCode === "AL001";
                            });
                            if (!self.isMess()) {
                                nts.uk.ui.dialog.confirm(AL001.messName).ifCancel(function () {
                                    self.isMess(true);
                                    self.currentCode(self.employmentCode());
                                    return;
                                }).ifYes(function () {
                                    self.isMess(false);
                                    self.reloadScreenWhenListClick(newValue);
                                });
                            }
                        }
                        else {
                            self.reloadScreenWhenListClick(newValue);
                        }
                        if (self.isMess() && self.employmentCode() === newValue) {
                            self.isMess(false);
                        }
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.userKtSet = function () {
                    var def = $.Deferred();
                    var self = this;
                    a.service.getCompanyInfor().done(function (companyInfor) {
                        if (companyInfor !== undefined) {
                            self.isUseKtSet(companyInfor.use_Kt_Set);
                            if (self.isUseKtSet() === 0) {
                                $('.UseKtSet').css('display', 'none');
                            }
                        }
                        def.resolve(self.isUseKtSet());
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        def.reject();
                    });
                    return def.promise();
                };
                ScreenModel.prototype.reloadScreenWhenListClick = function (newValue) {
                    var self = this;
                    var newEmployment = _.find(self.dataSource(), function (employ) {
                        if (employ.employmentCode === newValue) {
                            self.employmentCode(employ.employmentCode);
                            self.employmentName(employ.employmentName);
                            if (employ.closeDateNo === 0) {
                                self.selectedCloseCode('システム未導入');
                            }
                            self.selectedProcessNo(employ.processingNo);
                            self.multilineeditor.memoValue(employ.memo);
                            self.employmentOutCode(employ.employementOutCd);
                            self.holidayCode(employ.statutoryHolidayAtr);
                            if (employ.displayFlg == 1) {
                                self.isCheckbox(true);
                            }
                            else {
                                self.isCheckbox(false);
                            }
                            self.isDelete(true);
                            self.isEnable(false);
                            return;
                        }
                    });
                };
                ScreenModel.prototype.closeDateListItem = function () {
                    var self = this;
                    self.closeDateList.removeAll();
                    self.closeDateList.push(new ItemCloseDate(0, 'システム未導入'));
                };
                ScreenModel.prototype.managementHolidaylist = function () {
                    var self = this;
                    self.managementHolidays = ko.observableArray([
                        { code: 0, name: 'する' },
                        { code: 1, name: 'しない' }
                    ]);
                    self.holidayCode = ko.observable(0);
                };
                ScreenModel.prototype.listMessage = function () {
                    var self = this;
                    self.lstMessage.push(new ItemMessage("ER001", "*が入力されていません。"));
                    self.lstMessage.push(new ItemMessage("ER005", "入力した*は既に存在しています。\r\n*を確認してください。"));
                    self.lstMessage.push(new ItemMessage("ER010", "対象データがありません。"));
                    self.lstMessage.push(new ItemMessage("AL001", "変更された内容が登録されていません。\r\nよろしいですか。"));
                    self.lstMessage.push(new ItemMessage("AL002", "データを削除します。\r\nよろしいですか？"));
                    self.lstMessage.push(new ItemMessage("ER026", "更新対象のデータが存在しません。"));
                };
                ScreenModel.prototype.processingDateItem = function () {
                    var self = this;
                    a.service.getProcessingNo().done(function (lstProcessingNo) {
                        if (lstProcessingNo.length !== 0) {
                            _.forEach(lstProcessingNo, function (processingNo) {
                                self.processingDateList.push(new ItemProcessingDate(processingNo.processingNo, processingNo.processingName));
                            });
                        }
                    }).fail(function (res) {
                        var ER010 = _.find(self.lstMessage(), function (mess) {
                            return mess.messCode === "ER010";
                        });
                        nts.uk.ui.dialog.alert(ER010.messName);
                    });
                };
                ScreenModel.prototype.dataSourceItem = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.dataSource([]);
                    $.when(a.service.getAllEmployments()).done(function (listResult) {
                        if (listResult.length === 0 || listResult === undefined) {
                            self.isEnable(true);
                            self.isDelete(false);
                        }
                        else {
                            self.isEnable(false);
                            self.isDelete(true);
                            _.forEach(listResult, function (employ) {
                                if (employ.displayFlg == 1) {
                                    employ.displayStr = "<span style='color: #00B050; font-size: 18px'>●</span>";
                                }
                                else {
                                    employ.displayStr = "";
                                }
                                if (employ.closeDateNo === 0) {
                                    employ.closeDateNoStr = "システム未導入";
                                }
                                var process = _.find(self.processingDateList(), function (processNo) {
                                    return employ.processingNo == processNo.processingNo;
                                });
                                if (process !== undefined)
                                    employ.processingStr = process.processingName;
                                else
                                    employ.processingStr = "";
                                self.dataSource.push(employ);
                            });
                            if (self.currentCode() === "") {
                                var obEmployment = _.first(self.dataSource());
                                self.currentCode(obEmployment.employmentCode);
                            }
                        }
                        dfd.resolve(listResult);
                    });
                    if (self.isUseKtSet() === 0) {
                        this.columns = ko.observableArray([
                            { headerText: 'コード', prop: 'employmentCode', width: '30%' },
                            { headerText: '名称', prop: 'employmentName', width: '50%' },
                            { headerText: '初期表示', prop: 'displayStr', width: '20%' }
                        ]);
                    }
                    else {
                        this.columns = ko.observableArray([
                            { headerText: 'コード', prop: 'employmentCode', width: '18%' },
                            { headerText: '名称', prop: 'employmentName', width: '28%' },
                            { headerText: '締め日', prop: 'closeDateNoStr', width: '23%' },
                            { headerText: '処理日区分', prop: 'processingStr', width: '17%' },
                            { headerText: '初期表示', prop: 'displayStr', width: '14%' }
                        ]);
                    }
                    self.singleSelectedCode = ko.observable(null);
                    return dfd.promise();
                };
                ScreenModel.prototype.createEmployment = function () {
                    var self = this;
                    var ER001 = _.find(self.lstMessage(), function (mess) {
                        return mess.messCode === "ER001";
                    });
                    if (self.employmentCode() === "") {
                        nts.uk.ui.dialog.alert(ER001.messName.replace('*', 'コード'));
                        $("#inpCode").focus();
                        return;
                    }
                    if (self.employmentName() === "") {
                        nts.uk.ui.dialog.alert(ER001.messName.replace('*', '名称'));
                        $("#inpName").focus();
                        return;
                    }
                    var employment = new a.service.model.employmentDto();
                    employment.employmentCode = self.employmentCode();
                    employment.employmentName = self.employmentName();
                    employment.closeDateNo = 0;
                    employment.processingNo = self.selectedProcessNo();
                    employment.statutoryHolidayAtr = self.holidayCode();
                    employment.employementOutCd = self.employmentOutCode();
                    employment.memo = self.multilineeditor.memoValue();
                    if (self.dataSource().length === 0) {
                        self.isCheckbox(true);
                    }
                    if (self.isCheckbox())
                        employment.displayFlg = 1;
                    else
                        employment.displayFlg = 0;
                    if (self.isEnable()) {
                        a.service.createEmployment(employment).done(function () {
                            $.when(self.dataSource()).done(function () {
                                $.when(self.dataSourceItem()).done(function () {
                                    self.currentCode(employment.employmentCode);
                                });
                            });
                        }).fail(function (error) {
                            var newMess = _.find(self.lstMessage(), function (mess) {
                                return mess.messCode === error.message;
                            });
                            nts.uk.ui.dialog.alert(newMess.messName.split('*').join('コード'));
                            self.isEnable(true);
                            $("#inpCode").focus();
                        });
                    }
                    else {
                        $.when(a.service.updateEmployment(employment)).done(function () {
                            $.when(self.dataSourceItem()).done(function () {
                                self.currentCode(employment.employmentCode);
                            });
                        }).fail(function (res) {
                            var newMess = _.find(self.lstMessage(), function (mess) {
                                return mess.messCode === res.message;
                            });
                            nts.uk.ui.dialog.alert(newMess.messName);
                        });
                    }
                };
                ScreenModel.prototype.newCreateEmployment = function () {
                    var self = this;
                    if (self.dataSource().length !== 0 && !self.checkChange(self.employmentCode())) {
                        var AL001 = _.find(self.lstMessage(), function (mess) {
                            return mess.messCode === "AL001";
                        });
                        nts.uk.ui.dialog.confirm(AL001.messName).ifCancel(function () {
                            return;
                        }).ifYes(function () {
                            self.clearItem();
                        });
                    }
                    else {
                        self.clearItem();
                    }
                };
                ScreenModel.prototype.checkChange = function (employmentCodeChk) {
                    var self = this;
                    var chkEmployment = _.find(self.dataSource(), function (employ) {
                        return employ.employmentCode == employmentCodeChk;
                    });
                    if (chkEmployment !== undefined && chkEmployment !== null) {
                        if (chkEmployment.employmentName !== self.employmentName()
                            || chkEmployment.memo !== self.multilineeditor.memoValue()
                            || chkEmployment.processingNo !== self.selectedProcessNo()
                            || chkEmployment.statutoryHolidayAtr !== self.holidayCode()
                            || chkEmployment.employementOutCd !== self.employmentOutCode()
                            || chkEmployment.displayFlg !== (self.isCheckbox() ? 1 : 0)) {
                            return false;
                        }
                        else {
                            return true;
                        }
                    }
                    else if (self.employmentCode() !== "" && self.isEnable()) {
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.clearItem = function () {
                    var self = this;
                    self.employmentCode("");
                    self.employmentName("");
                    self.isEnable(true);
                    self.multilineeditor.memoValue("");
                    self.employmentOutCode("");
                    self.currentCode("");
                    self.isCheckbox(false);
                    self.isDelete(false);
                    self.holidayCode(0);
                    self.selectedProcessNo(0);
                    $("#inpCode").focus();
                };
                ScreenModel.prototype.deleteEmployment = function () {
                    var self = this;
                    var AL002 = _.find(self.lstMessage(), function (mess) {
                        return mess.messCode === "AL002";
                    });
                    nts.uk.ui.dialog.confirm(AL002.messName).ifCancel(function () {
                        return;
                    }).ifYes(function () {
                        var employment = new a.service.model.employmentDto();
                        employment.employmentCode = self.employmentCode();
                        if (self.isCheckbox())
                            employment.displayFlg = 1;
                        else
                            employment.displayFlg = 0;
                        var indexItemDelete = _.findIndex(self.dataSource(), function (item) { return item.employmentCode == self.employmentCode(); });
                        a.service.deleteEmployment(employment).done(function () {
                            $.when(self.dataSourceItem()).done(function () {
                                if (self.dataSource().length === 0) {
                                    self.isEnable(true);
                                    self.isDelete(false);
                                    self.newCreateEmployment();
                                }
                                else if (self.dataSource().length === indexItemDelete) {
                                    self.isEnable(false);
                                    self.isDelete(true);
                                    self.currentCode(self.dataSource()[indexItemDelete - 1].employmentCode);
                                }
                                else {
                                    self.isEnable(false);
                                    self.isDelete(true);
                                    if (indexItemDelete > self.dataSource().length) {
                                        self.currentCode(self.dataSource()[0].employmentCode);
                                    }
                                    else {
                                        self.currentCode(self.dataSource()[indexItemDelete].employmentCode);
                                    }
                                }
                            });
                        }).fail(function (res) {
                            var delMess = _.find(self.lstMessage(), function (mess) {
                                return mess.messCode === res.message;
                            });
                            nts.uk.ui.dialog.alert(delMess.messName);
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemCloseDate = (function () {
                function ItemCloseDate(closeDateCode, closeDatename) {
                    this.closeDateCode = closeDateCode;
                    this.closeDatename = closeDatename;
                }
                return ItemCloseDate;
            }());
            viewmodel.ItemCloseDate = ItemCloseDate;
            var ItemProcessingDate = (function () {
                function ItemProcessingDate(processingNo, processingName) {
                    this.processingNo = processingNo;
                    this.processingName = processingName;
                }
                return ItemProcessingDate;
            }());
            viewmodel.ItemProcessingDate = ItemProcessingDate;
            var ItemMessage = (function () {
                function ItemMessage(messCode, messName) {
                    this.messCode = messCode;
                    this.messName = messName;
                }
                return ItemMessage;
            }());
            viewmodel.ItemMessage = ItemMessage;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
//# sourceMappingURL=cmm008.a.vm.js.map