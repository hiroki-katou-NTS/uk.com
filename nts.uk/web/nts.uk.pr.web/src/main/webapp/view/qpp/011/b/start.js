__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '譛ｬ遉ｾ'),
                new BoxModel(2, '豕募ｮ夊ｪｿ譖ｸ蜃ｺ蜉帷畑莨夂､ｾ')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            this.items = ko.observableArray([
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
            this.columns = ko.observableArray([
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 100 },
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'name', width: 150 }
            ]);
            this.gridListCurrentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            self.numbereditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 0,
                    decimallength: 0,
                    placeholder: "",
                    width: "",
                    textalign: "right"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        return ScreenModel;
    }());
    var BoxModel = (function () {
        function BoxModel(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
        return BoxModel;
    }());
    var ComboboxItemModel = (function () {
        function ComboboxItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return ComboboxItemModel;
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
function OpenModeSubWindow(url, option) {
    nts.uk.ui.windows.sub.modal(url, option);
}
function closeDialog() {
    nts.uk.ui.windows.close();
}
