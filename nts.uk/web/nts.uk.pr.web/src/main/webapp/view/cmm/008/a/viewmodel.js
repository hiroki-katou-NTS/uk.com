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
                    //self.employmentCode = ko.observable("");
                    self.employmentName = ko.observable("");
                    self.isCheckbox = ko.observable(true);
                    self.closeDateList = ko.observableArray([]);
                    self.selectedCloseCode = ko.observable(0);
                    self.managementHolidays = ko.observableArray([]);
                    self.holidayCode = ko.observable(1);
                    self.processingDateList = ko.observableArray([]);
                    self.selectedProcessCode = ko.observable(0);
                    self.employmentOutCode = ko.observable("");
                    self.memoValue = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dataSource = ko.observableArray([]);
                    self.listResult = ko.observableArray([]);
                    self.isEnable = ko.observable(false);
                    self.multilineeditor = {
                        memoValue: ko.observable(''),
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
                        var newEmployment = _.find(self.listResult(), function (employ) {
                            if (employ.employmentCode === newValue) {
                                self.currentCode(employ.employmentCode);
                                //self.employmentCode(employ.employmentCode);
                                self.employmentName(employ.employmentName);
                                self.selectedCloseCode(employ.closeDateNo);
                                self.selectedProcessCode(employ.processingNo);
                                self.memoValue(employ.memo);
                                self.employmentOutCode(employ.employementOutCd);
                                return;
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
                    self.dataSource = ko.observableArray([]);
                    a.service.getAllEmployments().done(function (listResult) {
                        self.listResult(listResult);
                        if (listResult.length === 0 || listResult === undefined) {
                            self.isEnable(true);
                        }
                        else {
                            self.isEnable(false);
                            for (var _i = 0, listResult_1 = listResult; _i < listResult_1.length; _i++) {
                                var employ = listResult_1[_i];
                                var closeDate = employ.closeDateNo.toString();
                                var processingNo = employ.processingNo.toString();
                                var displayText = employ.displayFlg.toString();
                                self.dataSource.push(new ItemModel(employ.employmentCode, employ.employmentName, closeDate, processingNo, displayText));
                            }
                            if (self.currentCode() === null
                                || self.currentCode() === undefined) {
                                var obEmployment = _.first(self.listResult());
                                self.currentCode(obEmployment.employmentCode);
                            }
                        }
                    });
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 },
                        { headerText: '締め日', prop: 'closeDate', width: 150 },
                        { headerText: '処理日区分', prop: 'processingNo', width: 150 },
                        { headerText: '初期表示', prop: 'displayFlg', width: 150 }
                    ]);
                    this.currentCode = ko.observable("");
                    self.singleSelectedCode = ko.observable(null);
                };
                //登録ボタンを押す
                ScreenModel.prototype.createEmployment = function () {
                    var self = this;
                    //self.employmentCode(nts.uk.text.padLeft(self.employmentCode(),'0',10));
                    self.currentCode(nts.uk.text.padLeft(self.currentCode(), '0', 10));
                    //必須項目の未入力チェック
                    if (self.currentCode() === ""
                        || self.employmentName() === "") {
                        alert("コード/名称が入力されていません。");
                        return;
                    }
                    var employment = new a.service.model.employmentDto();
                    employment.employmentCode = self.currentCode();
                    employment.employmentName = self.employmentName();
                    employment.closeDateNo = self.selectedCloseCode();
                    employment.processingNo = self.selectedProcessCode();
                    employment.statutoryHolidayAtr = self.holidayCode();
                    employment.employementOutCd = self.employmentOutCode();
                    employment.memo = self.memoValue();
                    if (self.isCheckbox)
                        employment.displayFlg = 1;
                    else
                        employment.displayFlg = 0;
                    if (self.isEnable()) {
                        var isCheck = false;
                        //コード重複チェック
                        a.service.getEmploymentByCode(self.currentCode()).done(function (employmentChk) {
                            if (employmentChk !== null || employmentChk !== undefined) {
                                alert("入力したコードは既に存在しています。\r\nコードを確認してください。");
                                isCheck = true;
                                return;
                            }
                        });
                        if (isCheck) {
                            $("#INP_002").focus();
                            return;
                        }
                        a.service.createEmployment(employment).done(function () {
                        });
                    }
                    else {
                        a.service.updateEmployment(employment).done(function () {
                        });
                    }
                    self.start();
                };
                //新規ボタンを押す
                ScreenModel.prototype.newCreateEmployment = function () {
                    var self = this;
                    self.currentCode("");
                    self.employmentName("");
                    self.isEnable(true);
                    self.memoValue("");
                    self.employmentOutCode("");
                    self.currentCode("");
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
            var ItemModel = (function () {
                //childs: any;
                function ItemModel(code, name, description, other1, other2) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                    this.other1 = other1;
                    this.other2 = other2 || other1;
                    //this.childs = childs;     
                }
                return ItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
