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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    this.currentCode = ko.observable();
                                    this.items = ko.observableArray([]);
                                    for (var i = 1; i < 100; i++) {
                                        this.items.push(new ItemModel('00' + i, 'name' + i));
                                    }
                                    this.columns2 = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 100 },
                                        { headerText: '名称', key: 'name', width: 150 }
                                    ]);
                                    self.listOptions = ko.observableArray([new optionsModel(1, "基本情報"), new optionsModel(2, "保険料マスタの情報")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.modalValue = ko.observable("Goodbye world!");
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.index = 0;
                                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                    self.officeCode = ko.observable('');
                                    self.officeName = ko.observable('');
                                    self.shortName = ko.observable('');
                                    self.PicName = ko.observable('');
                                    self.PicPosition = ko.observable('');
                                    self.portCode = ko.observable(1);
                                    self.prefecture = ko.observable('');
                                    self.address1st = ko.observable('');
                                    self.kanaAddress1st = ko.observable('');
                                    self.address2nd = ko.observable('');
                                    self.kanaAddress2nd = ko.observable('');
                                    self.phoneNumber = ko.observable('');
                                    self.healthInsuOfficeRefCode1st = ko.observable('');
                                    self.healthInsuOfficeRefCode2nd = ko.observable('');
                                    self.pensionOfficeRefCode1st = ko.observable('');
                                    self.pensionOfficeRefCode2nd = ko.observable('');
                                    self.welfarePensionFundCode = ko.observable('');
                                    self.officePensionFundCode = ko.observable('');
                                    self.healthInsuCityCode = ko.observable('');
                                    self.healthInsuOfficeSign = ko.observable('');
                                    self.pensionCityCode = ko.observable('');
                                    self.pensionOfficeSign = ko.observable('');
                                    self.healthInsuOfficeCode = ko.observable('');
                                    self.healthInsuAssoCode = ko.observable('');
                                    self.memo = ko.observable('');
                                    self.textArea = ko.observable("");
                                    this.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        width: "100",
                                        textalign: "center"
                                    }));
                                }
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
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
                            viewmodel.ItemModel = ItemModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                            viewmodel.optionsModel = optionsModel;
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.childs = childs;
                                    self.custom = 'Random' + new Date().getTime();
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qmm008.c || (qmm008.c = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
