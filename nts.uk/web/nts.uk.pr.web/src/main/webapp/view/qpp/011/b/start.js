__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '本社'),
                new BoxModel(2, '法定調書出力用会社')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            //end radiogroup data
            //start combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //end combobox data
            // start gridlist
            this.items = ko.observableArray([
                new GridItemModel('001', '蝓ｺ譛ｬ邨ｦ'),
                new GridItemModel('150', '蠖ｹ閨ｷ謇句ｽ�'),
                new GridItemModel('ABC', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC1', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC2', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC3', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC4', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC5', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC6', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC7', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC8', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC9', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC10', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC11', '蝓ｺ12譛ｬghj邨ｦ'),
                new GridItemModel('ABC12', '蝓ｺ12譛ｬghj邨ｦ')
            ]);
            this.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝�', prop: 'code', width: 100 },
                { headerText: '蜷咲ｧｰ', prop: 'name', width: 150 }
            ]);
            this.gridListCurrentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            //end gridlist
            //start number editer
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
            //end number editer
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
