__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.items = ko.observableArray([
                new GridItemModel('001', '陜難ｽｺ隴幢ｽｬ驍ｨ�ｽｦ'),
                new GridItemModel('150', '陟厄ｽｹ髢ｨ�ｽｷ隰�蜿･�ｽｽ�ｿｽ'),
                new GridItemModel('ABC', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC1', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC2', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC3', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC4', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC5', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC6', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC7', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC8', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC9', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC10', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC11', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel('ABC12', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ')
            ]);
            self.columns = ko.observableArray([
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 100 },
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'name', width: 150 }
            ]);
            self.gridListCurrentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.currencyeditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "USD",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '納付先別' },
                { code: '2', name: '個人明細別' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
        return ScreenModel;
    }());
    var GridItemModel = (function () {
        function GridItemModel(code, name, description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
        return GridItemModel;
    }());
    this.bind(new ScreenModel());
});
function closeDialog() {
    nts.uk.ui.windows.close();
}
function submitInfo() {
    nts.uk.ui.windows.close();
}
//# sourceMappingURL=start.js.map