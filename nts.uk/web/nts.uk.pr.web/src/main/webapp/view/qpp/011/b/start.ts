__viewContext.ready(function() {
    class ScreenModel {
        //radiogroup
        RadioItemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        //combobox
        ComboBoxItemList: KnockoutObservableArray<ComboboxItemModel>;
        itemName: KnockoutObservable<string>;
        ComboBoxCurrentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //gridlist
        gridListItems: KnockoutObservableArray<GridItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        gridListCurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        //number editter
        numbereditor: any;
        constructor() {
            var self = this;
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '譛ｬ遉ｾ'),
                new BoxModel(2, '豕募ｮ夊ｪｿ譖ｸ蜃ｺ蜉帷畑莨夂､ｾ')
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
            self.selectedCode = ko.observable('0001')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //end combobox data
            // start gridlist
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
            }
            //end number editer
            
        }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class GridItemModel {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }

    }

    this.bind(new ScreenModel());
});
function OpenModeSubWindow(url: string, option?: any) {
    nts.uk.ui.windows.sub.modal(url, option);
}
function closeDialog(): any {
    nts.uk.ui.windows.close();
}