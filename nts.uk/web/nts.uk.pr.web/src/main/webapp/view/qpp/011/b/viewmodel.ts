// TreeGrid Node
module qpp011.b {

    export class ScreenModel {
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
        //
        gridListItems_B_LST_001: KnockoutObservableArray<GridItemModel_B_LST_001>;
        columns_B_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeList_B_LST_001: KnockoutObservableArray<any>;
        //
        gridListItems_C_LST_001: KnockoutObservableArray<GridItemModel_C_LST_001>;
        columns_C_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeList_C_LST_001: KnockoutObservableArray<any>;
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
            //B_LST_001
            this.gridListItems_B_LST_001 = ko.observableArray([
                new GridItemModel_B_LST_001('001', '陜難ｽｺ隴幢ｽｬ驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('150', '陟厄ｽｹ髢ｨ�ｽｷ隰�蜿･�ｽｽ�ｿｽ'),
                new GridItemModel_B_LST_001('ABC', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC1', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC2', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC3', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC4', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC5', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC6', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC7', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC8', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC9', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC10', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC11', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_B_LST_001('ABC12', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ')
            ]);
            this.columns_B_LST_001 = ko.observableArray([
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 100 },
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'name', width: 150 }
            ]);
            this.currentCodeList_B_LST_001 = ko.observableArray([]);
            //C_LST_001
            this.gridListItems_C_LST_001 = ko.observableArray([
                new GridItemModel_C_LST_001('001', '陜難ｽｺ隴幢ｽｬ驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('150', '陟厄ｽｹ髢ｨ�ｽｷ隰�蜿･�ｽｽ�ｿｽ'),
                new GridItemModel_C_LST_001('ABC', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC1', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC2', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC3', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC4', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC5', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC6', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC7', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC8', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC9', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC10', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC11', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ'),
                new GridItemModel_C_LST_001('ABC12', '陜難ｽｺ12隴幢ｽｬghj驍ｨ�ｽｦ')
            ]);
            this.columns_C_LST_001 = ko.observableArray([
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 100 },
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'name', width: 150 }
            ]);
            this.currentCodeList_C_LST_001 = ko.observableArray([]);
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
        openFDialog() {
            nts.uk.ui.windows.sub.modal('/view/qpp/011/f/index.xhtml', { height: 550, width: 740, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
        openDDialog() {
            nts.uk.ui.windows.sub.modal('/view/qpp/011/d/index.xhtml', { height: 550, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
    }
    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    export class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class GridItemModel_C_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class GridItemModel_B_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }

    }


};
