var cmm013;
(function (cmm013) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.employmentCode = ko.observable("");
                    self.employmentName = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.textSearch = {
                        valueSearch: ko.observable(""),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "繧ｳ繝ｼ繝峨・蜷咲ｧｰ縺ｧ讀懃ｴ｢繝ｻ繝ｻ繝ｻ",
                            width: "75%",
                            textalign: "left"
                        }))
                    };
                    self.items = ko.observableArray([
                        new ItemModel('001', '蝓ｺ譛ｬ邨ｦ'),
                        new ItemModel('150', '蠖ｹ閨ｷ謇句ｽ'),
                        new ItemModel('ABC', '蝓ｺ12譛ｬ邨ｦ')
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: '繧ｳ繝ｼ繝・', prop: 'code', width: 100 },
                        { headerText: '蜷咲ｧｰ', prop: 'name', width: 150 }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
                }
                ScreenModel.prototype.selectSomeItems = function () {
                    this.currentCode('150');
                    this.currentCodeList.removeAll();
                    this.currentCodeList.push('001');
                    this.currentCodeList.push('ABC');
                };
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                    this.currentCodeList.removeAll();
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
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = cmm013.f || (cmm013.f = {}));
})(cmm013 || (cmm013 = {}));
