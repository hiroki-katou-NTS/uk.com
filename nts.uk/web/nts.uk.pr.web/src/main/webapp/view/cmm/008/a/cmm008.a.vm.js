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
                    self.isCheckbox = ko.observable(true);
                    self.closeDateList = ko.observableArray([]);
                    self.selectedCloseCode = ko.observable('システム未導入');
                    self.managementHolidays = ko.observableArray([]);
                    self.holidayCode = ko.observable(1);
                    self.processingDateList = ko.observableArray([]);
                    self.selectedProcessNo = ko.observable(0);
                    self.employmentOutCode = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dataSource = ko.observableArray([]);
                    self.currentCode = ko.observable("");
                    self.employmentCode = ko.observable("");
                    self.isEnable = ko.observable(false);
                    self.isDelete = ko.observable(true);
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
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var heightScreen = $(window).height();
                    var widthScreen = $(window).width();
                    var heightHeader = $('#header').height() + $('#functions-area').height();
                    var height = heightScreen - heightHeader - 75;
                    $('#contents-left').css({ height: height, width: widthScreen * 30 / 100 });
                    $('#contents-right').css({ height: height, width: widthScreen * 70 / 100 });
                    self.closeDateListItem();
                    self.managementHolidaylist();
                    self.processingDateItem();
                    self.dataSourceItem();
                    //list data click
                    self.currentCode.subscribe(function (newValue) {
                        if (!self.checkChange(self.employmentCode())) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifCancel(function () {
                                self.reloadScreenWhenListClick(newValue);
                                return;
                            }).ifYes(function () {
                                self.createEmployment();
                            });
                        }
                        else {
                            self.reloadScreenWhenListClick(newValue);
                        }
                    });
                    a.service.getProcessingNo();
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
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
                        { code: 1, name: 'する' },
                        { code: 2, name: 'しない' }
                    ]);
                    self.holidayCode = ko.observable(1);
                };
                //処理日区分 を取得する
                ScreenModel.prototype.processingDateItem = function () {
                    var self = this;
                    a.service.getProcessingNo().done(function (lstProcessingNo) {
                        if (lstProcessingNo.length !== 0) {
                            _.forEach(lstProcessingNo, function (processingNo) {
                                self.processingDateList.push(new ItemProcessingDate(processingNo.processingNo, processingNo.processingName));
                            });
                        }
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
                                //get processing name
                                _.find(self.processingDateList(), function (processNo) {
                                    employ.processingStr = processNo.processingName;
                                    return employ.processingNo == processNo.processingNo;
                                });
                                self.dataSource.push(employ);
                            });
                            if (self.currentCode() === "") {
                                var obEmployment = _.first(self.dataSource());
                                self.currentCode(obEmployment.employmentCode);
                            }
                        }
                        dfd.resolve(listResult);
                    });
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'employmentCode', width: 100 },
                        { headerText: '名称', prop: 'employmentName', width: 160 },
                        { headerText: '締め日', prop: 'closeDateNoStr', width: 150 },
                        { headerText: '処理日区分', prop: 'processingStr', width: 150 },
                        { headerText: '初期表示', prop: 'displayStr', width: 80 }
                    ]);
                    self.singleSelectedCode = ko.observable(null);
                    return dfd.promise();
                };
                //登録ボタンを押す
                ScreenModel.prototype.createEmployment = function () {
                    var self = this;
                    //必須項目の未入力チェック
                    if (self.employmentCode() === "") {
                        nts.uk.ui.dialog.alert("コードが入力されていません。");
                        $("#INP_002").focus();
                        return;
                    }
                    if (self.employmentName() === "") {
                        nts.uk.ui.dialog.alert("名称が入力されていません。");
                        $("#INP_003").focus();
                        return;
                    }
                    var employment = new a.service.model.employmentDto();
                    employment.employmentCode = self.employmentCode();
                    employment.employmentName = self.employmentName();
                    //今回は就業システム未導入の場合としてください。
                    //（上記にあるように　締め日区分 = 0 ）
                    employment.closeDateNo = 0;
                    employment.processingNo = self.selectedProcessNo();
                    employment.statutoryHolidayAtr = self.holidayCode();
                    employment.employementOutCd = self.employmentOutCode();
                    employment.memo = self.multilineeditor.memoValue();
                    if (self.isCheckbox())
                        employment.displayFlg = 1;
                    else
                        employment.displayFlg = 0;
                    //新規の時
                    if (self.isEnable()) {
                        a.service.createEmployment(employment).done(function () {
                            $.when(self.dataSource()).done(function () {
                                $.when(self.dataSourceItem()).done(function () {
                                    self.currentCode(employment.employmentCode);
                                });
                            });
                        }).fail(function (error) {
                            nts.uk.ui.dialog.alert(error.message);
                            self.isEnable(true);
                            $("#INP_002").focus();
                        });
                    }
                    else {
                        $.when(a.service.updateEmployment(employment)).done(function () {
                            $.when(self.dataSourceItem()).done(function () {
                                self.currentCode(employment.employmentCode);
                            });
                        });
                    }
                };
                //新規ボタンを押す
                ScreenModel.prototype.newCreateEmployment = function () {
                    var self = this;
                    //変更確認
                    if (!self.checkChange(self.employmentCode())) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifCancel(function () {
                            self.clearItem();
                            return;
                        }).ifYes(function () {
                            self.createEmployment();
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
                    else if (self.employmentCode() !== "") {
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
                    $("#INP_002").focus();
                };
                //削除
                ScreenModel.prototype.deleteEmployment = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifCancel(function () {
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
                            nts.uk.ui.dialog.alert(res.message);
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
