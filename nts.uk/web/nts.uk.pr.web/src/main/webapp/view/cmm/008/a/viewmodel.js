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
                    self.selectedCloseCode = ko.observable(0);
                    self.managementHolidays = ko.observableArray([]);
                    self.holidayCode = ko.observable(1);
                    self.processingDateList = ko.observableArray([]);
                    self.selectedProcessCode = ko.observable(0);
                    self.employmentOutCode = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dataSource = ko.observableArray([]);
                    self.currentCode = ko.observable("");
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
                        var newEmployment = _.find(self.dataSource(), function (employ) {
                            if (employ.employmentCode === newValue && !self.isEnable()) {
                                self.isEnable(false);
                                self.currentCode(employ.employmentCode);
                                self.employmentName(employ.employmentName);
                                self.selectedCloseCode(employ.closeDateNo);
                                self.selectedProcessCode(employ.processingNo);
                                self.multilineeditor.memoValue(employ.memo);
                                self.employmentOutCode(employ.employementOutCd);
                                if (employ.displayFlg == 1) {
                                    self.isCheckbox(true);
                                }
                                else {
                                    self.isCheckbox(false);
                                }
                            }
                        });
                    });
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDateListItem = function () {
                    var self = this;
                    self.closeDateList.removeAll();
                    self.closeDateList.push(new ItemCloseDate(0, '0'));
                    self.closeDateList.push(new ItemCloseDate(1, '1'));
                    self.closeDateList.push(new ItemCloseDate(2, '2'));
                };
                ScreenModel.prototype.managementHolidaylist = function () {
                    var self = this;
                    self.managementHolidays = ko.observableArray([
                        { code: 1, name: 'する' },
                        { code: 2, name: 'しない' }
                    ]);
                    self.holidayCode = ko.observable(1);
                };
                ScreenModel.prototype.processingDateItem = function () {
                    var self = this;
                    self.processingDateList.push(new ItemProcessingDate(0, '0'));
                    self.processingDateList.push(new ItemProcessingDate(1, '1'));
                    self.processingDateList.push(new ItemProcessingDate(2, '2'));
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
                        { headerText: '締め日', prop: 'closeDateNo', width: 150 },
                        { headerText: '処理日区分', prop: 'processingNo', width: 150 },
                        { headerText: '初期表示', prop: 'displayStr', width: 100 }
                    ]);
                    self.singleSelectedCode = ko.observable(null);
                    return dfd.promise();
                };
                //登録ボタンを押す
                ScreenModel.prototype.createEmployment = function () {
                    var self = this;
                    //必須項目の未入力チェック
                    if (self.currentCode() === "") {
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
                    employment.employmentCode = self.currentCode();
                    employment.employmentName = self.employmentName();
                    employment.closeDateNo = self.selectedCloseCode();
                    employment.processingNo = self.selectedProcessCode();
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
                    self.currentCode("");
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
                    if (self.currentCode() === "")
                        return;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifNo(function () {
                        return;
                    });
                    var employment = new a.service.model.employmentDto();
                    employment.employmentCode = self.currentCode();
                    if (self.isCheckbox())
                        employment.displayFlg = 1;
                    else
                        employment.displayFlg = 0;
                    var indexItemDelete = _.findIndex(self.dataSource(), function (item) { return item.employmentCode == self.currentCode(); });
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
                function ItemProcessingDate(processingDateCode, processingDatename) {
                    this.processingDateCode = processingDateCode;
                    this.processingDatename = processingDatename;
                }
                return ItemProcessingDate;
            }());
            viewmodel.ItemProcessingDate = ItemProcessingDate;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
