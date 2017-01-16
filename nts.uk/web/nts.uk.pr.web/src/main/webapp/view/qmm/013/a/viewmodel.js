var qmm013;
(function (qmm013) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    this.currentName = ko.observable();
                    this.currentCodeList = ko.observableArray([]);
                    this.items = ko.observableArray([
                        new ItemModel('001', '基本給', '<i class="icon icon-close"></i>'),
                        new ItemModel('150', '役職手当', '<i class="icon icon-close"></i>'),
                        new ItemModel('ABC', '基本給', '<i class="icon icon-close"></i>')
                    ]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 },
                        { headerText: '廃止', prop: 'abolition', width: 50 }
                    ]);
                    this.currentCode = ko.observable();
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '全員一律で指定する'),
                        new BoxModel(2, '給与約束形態ごとに指定する')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '2', name: '対象外' }
                    ]);
                    self.SEL_004 = ko.observableArray([
                        { code: '1', name: '時間単価' },
                        { code: '2', name: '金額' },
                        { code: '3', name: 'その他' },
                    ]);
                    self.SEL_005_check = ko.observable(1);
                    self.SEL_006_check = ko.observable(1);
                    self.SEL_007_check = ko.observable(1);
                    self.SEL_008_check = ko.observable(1);
                    self.SEL_009_check = ko.observable(1);
                    self.SEL_004_check = ko.observable(1);
                    self.texteditor1 = {
                        value: self.currentCode,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "45px",
                            textalign: "center"
                        })),
                    };
                    self.texteditor2 = {
                        value: self.currentName,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "180px",
                            textalign: "left"
                        })),
                    };
                    self.multilineeditor = {
                        value: ko.observable(''),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "450px",
                            textalign: "left"
                        })),
                    };
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm013.a.service.getPaymentDateProcessingList().done(function () {
                        dfd.resolve();
                    }).fail(function () {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, abolition) {
                    this.code = code;
                    this.name = name;
                    this.abolition = abolition;
                }
                return ItemModel;
            }());
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm013.a || (qmm013.a = {}));
})(qmm013 || (qmm013 = {}));
