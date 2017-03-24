module qmm012.b.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean> = ko.observable(true);
        //combobox
        //001
        ComboBoxItemList_B_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_B_001: KnockoutObservable<number> = ko.observable(1);
        //gridlist
        GridlistItems_B_001: KnockoutObservableArray<service.model.ItemMasterModel> = ko.observableArray([]);
        GridColumns_B_001: KnockoutObservableArray<any>;
        GridlistCurrentCode_B_001: KnockoutObservable<string> = ko.observable('');
        GridlistCurrentItem_B_001: KnockoutObservable<service.model.ItemMasterModel> = ko.observable(null);
        GridCurrentItemAbName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentItemName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentDisplaySet_B_001: KnockoutObservable<any> = ko.observable(false);
        GridCurrentUniteCode_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentCategoryAtr_B_001: KnockoutObservable<number> = ko.observable(0);
        GridCurrentCategoryAtrName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentCodeAndName_B_001: KnockoutObservable<string> = ko.observable('');
        //Checkbox
        //B_002
        checked_B_002: KnockoutObservable<boolean> = ko.observable(true);
        //combobox
        //text editer
        texteditor_B_INP_002: any;
        texteditor_B_INP_003: any;
        texteditor_B_INP_004: any;
        enable_B_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        screenModel: qmm012.b.ScreenModel;
        constructor(screenModel: qmm012.b.ScreenModel) {
            let self = this;
            self.screenModel = screenModel;
            //set combobox data
            //001
            self.ComboBoxItemList_B_001 = ko.observableArray([
                new ComboboxItemModel(1, '全件'),
                new ComboboxItemModel(2, '支給項目'),
                new ComboboxItemModel(3, '控除項目'),
                new ComboboxItemModel(4, '勤怠項目'),
                new ComboboxItemModel(5, '記事項目'),
                new ComboboxItemModel(6, 'その他項目')
            ]);
            // set gridlist data
            self.GridColumns_B_001 = ko.observableArray([
                { headerText: '項目区分', prop: 'categoryAtrName', width: 80 },
                { headerText: 'コード', prop: 'itemCode', width: 50 },
                { headerText: '名称', prop: 'itemName', width: 150 },
                { headerText: '廃止', prop: 'displaySet', width: 20, formatter: makeIcon }
            ]);

            function makeIcon(val) {
                if (val == 1)
                    return "<div class = 'NoIcon' > </div>";
                return "";
            }
            self.GridlistCurrentCode_B_001.subscribe(function(newValue) {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel: service.model.ItemMasterModel) {
                    return ItemModel.itemCode == newValue;
                });
                self.GridlistCurrentItem_B_001(item);
            });

            self.GridlistCurrentItem_B_001.subscribe(function(itemModel: service.model.ItemMasterModel) {
                self.GridCurrentItemName_B_001(itemModel ? itemModel.itemName : '');
                self.GridCurrentUniteCode_B_001(itemModel ? itemModel.uniteCode : '');
                self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtr : 0);
                //Because there are many items in the same group  After set value , need call ChangeGroup function for Set Value to layout
                ChangeGroup(self.GridCurrentCategoryAtr_B_001());
                self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : '');
                self.GridCurrentItemAbName_B_001(itemModel ? itemModel.itemAbName : '');
                self.GridCurrentCategoryAtrName_B_001(itemModel ? itemModel.categoryAtrName : '');
                //when CurrentCode != undefined , need disable INP_002
                if (self.GridlistCurrentCode_B_001() != undefined) {
                    self.enable_B_INP_002(false);
                }
            });

            self.GridCurrentCategoryAtr_B_001.subscribe(function(newValue) {
                //when change to different group, need  call ChangeGroup function for Set Value to layout
                ChangeGroup(newValue);
            });

            function ChangeGroup(newValue) {
                $('#screenC').hide();
                $('#screenD').hide();
                $('#screenE').hide();
                $('#screenF').hide();
                switch (newValue) {
                    case 0:
                        $('#screenC').show();
                        self.screenModel.screenModelC.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 1:
                        $('#screenD').show();
                        self.screenModel.screenModelD.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 2:
                        $('#screenE').show();
                        self.screenModel.screenModelE.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 3:
                        $('#screenF').show();
                        self.screenModel.screenModelF.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                }
            }

            service.findAllItemMaster().done(function(MasterItems: Array<service.model.ItemMasterModel>) {
                for (let item of MasterItems) {
                    self.GridlistItems_B_001.push(item);
                }
                //set selected first item in list
                if (self.GridlistItems_B_001().length > 0)
                    self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[0].itemCode);
            }).fail(function(res) {
                alert(res);
            });
            
            //set text editer data
            //INP_002
            self.texteditor_B_INP_002 = {
                value: self.GridlistCurrentCode_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                })),
                enable: self.enable_B_INP_002
            };
            //INP_003
            self.texteditor_B_INP_003 = {
                value: self.GridCurrentItemName_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }))
            };
            //INP_004
            self.texteditor_B_INP_004 = {
                value: self.GridCurrentItemAbName_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }))
            };

        }

        GetCurrentItemMaster() {
            let self = this;
            return new service.model.ItemMasterModel(
                self.GridlistCurrentCode_B_001(),
                self.GridCurrentItemName_B_001(),
                self.GridCurrentCategoryAtr_B_001(),
                self.GridCurrentCategoryAtrName_B_001(),
                self.GridCurrentItemAbName_B_001(),
                self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameO : self.GridCurrentItemAbName_B_001(),
                self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameE : self.GridCurrentItemAbName_B_001(),
                self.GridCurrentDisplaySet_B_001(),
                self.GridCurrentUniteCode_B_001(),
                self.GetCurrentZeroDisplaySet(),
                self.GetCurrentItemDisplayAtr(),
                1
            )
        }

        DeleteDialog() {
            let self = this;
            service.deleteItemMaster(self.GridlistCurrentItem_B_001()).done(function(any) {
                //i'm not reload Gridlist , just remove that item in GridlistItems Array
                let index = self.GridlistItems_B_001().indexOf(self.GridlistCurrentItem_B_001());
                if (index != undefined) {
                    self.GridlistItems_B_001().splice(index, 1);
                    //set selected code after remove
                    if (self.GridlistItems_B_001().length - 1 > 1) {
                        //if is not last item, set selected next item 
                        if (index < self.GridlistItems_B_001().length - 1)
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index].itemCode);
                        else
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index - 1].itemCode);
                    } else
                        self.GridlistCurrentCode_B_001('');
                }
            }).fail(function(res) {

                alert(res);
            });
        }
        GetCurrentZeroDisplaySet() {
            let Result;
            let self = this;
            let CurrentGroup = self.GridCurrentCategoryAtr_B_001();
            switch (CurrentGroup) {
                case 0:
                    Result = self.screenModel.screenModelC.CurrentZeroDisplaySet();
                    break;
                case 1:
                    Result = self.screenModel.screenModelD.CurrentZeroDisplaySet();
                    break;
                case 2:
                    Result = self.screenModel.screenModelE.CurrentZeroDisplaySet();
                    break;
            }
            return Result;
        }
        GetCurrentItemDisplayAtr() {
            let Result;
            let self = this;
            let CurrentGroup = self.GridCurrentCategoryAtr_B_001();
            switch (CurrentGroup) {
                case 0:
                    Result = self.screenModel.screenModelC.CurrentItemDisplayAtr();
                    break;
                case 1:
                    Result = self.screenModel.screenModelD.CurrentItemDisplayAtr();
                    break;
                case 2:
                    Result = self.screenModel.screenModelE.CurrentItemDisplayAtr();
                    break;
            }
            return Result;
        }


        openADialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function(): any {
                let groupCode = Number(nts.uk.sessionStorage.getItemAndRemove('groupCode').value);
                //set layout for new.
                if (groupCode != undefined) {
                    self.GridlistCurrentCode_B_001('');
                    self.GridCurrentCategoryAtr_B_001(groupCode);
                    self.enable_B_INP_002(true);
                }
            });
        }
        submitData() {
            let self = this;
            let ItemMaster = self.GetCurrentItemMaster();
            //if self.enable_B_INP_002 == true is mean New mode
            if (self.enable_B_INP_002()) {
                self.AddNewItemMaster(ItemMaster);
            } else {
                self.UpdateItemMaster(ItemMaster);
            }
        }
        AddNewItemMaster(ItemMaster: service.model.ItemMasterModel) {
            let self = this;
            self.GridlistItems_B_001().push(ItemMaster);
            self.GridlistCurrentCode_B_001(ItemMaster.itemCode);
        }
        UpdateItemMaster(ItemMaster: service.model.ItemMasterModel) {

        }

        openJDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('../j/index.xhtml', { height: 700, width: 970, dialogClass: "no-close" }).onClosed(function(): any {
            });
        }
    }

    class ComboboxItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}