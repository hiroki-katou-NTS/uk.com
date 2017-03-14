module qmm012.b.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean> = ko.observable(true);
        //combobox
        //001
        ComboBoxItemList_B_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_B_001: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean> = ko.observable(true);
        isEditable: KnockoutObservable<boolean> = ko.observable(true);
        //gridlist
        GridlistItems_B_001: KnockoutObservableArray<service.model.ItemMasterModel> = ko.observableArray([]);
        GridColumns_B_001: KnockoutObservableArray<any>;
        GridlistCurrentCode_B_001: KnockoutObservable<any> = ko.observable();
        GridlistCurrentItem_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentItemAbName_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentItemName_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentDisplaySet_B_001: KnockoutObservable<any> = ko.observable(false);
        GridCurrentUniteCode_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentCategoryAtr_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentCategoryAtrName_B_001: KnockoutObservable<any> = ko.observable();
        GridCurrentCodeAndName_B_001: KnockoutObservable<any> = ko.observable();
        //Checkbox
        //B_002
        checked_B_002: KnockoutObservable<boolean> = ko.observable(true);
        //combobox
        //text editer
        texteditor_B_INP_002: any;
        texteditor_B_INP_003: any;
        texteditor_B_INP_004: any;
        enable_B_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        //textarea 
        textArea: KnockoutObservable<any> = ko.observable("");
        screenModel: qmm012.b.ScreenModel;
        constructor(screenModel: qmm012.b.ScreenModel) {
            let self = this;
            self.screenModel = screenModel;
            //start combobox data
            //001
            self.ComboBoxItemList_B_001 = ko.observableArray([
                new ComboboxItemModel('1', '蜈ｨ莉ｶ'),
                new ComboboxItemModel('2', '謾ｯ邨ｦ鬆�逶ｮ'),
                new ComboboxItemModel('3', '謗ｧ髯､鬆�逶ｮ'),
                new ComboboxItemModel('4', '蜍､諤�鬆�逶ｮ'),
                new ComboboxItemModel('5', '險倅ｺ矩��逶ｮ'),
                new ComboboxItemModel('6', '縺昴�ｮ莉夜��逶ｮ')
            ]);
            self.selectedCode_B_001 = ko.observable('1');
            // start gridlist
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
                self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtrValue : '');
                self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : '');
                self.GridCurrentItemAbName_B_001(itemModel ? itemModel.itemAbName : '');
                self.GridCurrentCategoryAtrName_B_001(itemModel ? itemModel.categoryAtrName : '');
                if (self.GridlistCurrentCode_B_001()) {
                    self.enable_B_INP_002(false);
//                    service.findItemperiod(self.GridCurrentCategoryAtr_B_001(), self.GridlistCurrentCode_B_001()).done(function(PeriodItem: service.model.ItemPeriodModel) {
//                       // self.screenModel.screenModelC.CurrentItemPeriod(PeriodItem);
//                      
//                    }).fail(function(res) {
//                        // Alert message
//                        alert(res);
//                    });
                }
            });
          
            self.GridCurrentCategoryAtr_B_001.subscribe(function(newValue) {
              //  self.screenModel.screenModelC.checked_C_012(false);
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
            });
            service.findAllItemMaster().done(function(MasterItems: Array<service.model.ItemMasterModel>) {
                for (let item of MasterItems) {
                    self.GridlistItems_B_001.push(item);
                }
                if(self.GridlistItems_B_001().length>0)
                    self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[1].itemCode);
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            //end gridlist
            //text editer
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
                })),
                enable: ko.observable(true)
            };
            //INP_004
            self.texteditor_B_INP_004 = {
                value: self.GridCurrentItemAbName_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                })),
                enable: ko.observable(true)
            };
        }
        DeleteDialog() {
            let self = this;
            service.deleteItemMaster(self.GridlistCurrentItem_B_001()).done(function(any) {
                let index = self.GridlistItems_B_001().indexOf(self.GridlistCurrentItem_B_001());
                if (index != undefined) {
                    self.GridlistItems_B_001().splice(index, 1);
                    if (self.GridlistItems_B_001().length - 1 > 1) {
                        if (index < self.GridlistItems_B_001().length - 1)
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index].itemCode);
                        else
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index - 1].itemCode);
                    } else
                        self.GridlistCurrentCode_B_001(undefined);
                }
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
        }
        openADialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function(): any {
                let groupCode = nts.uk.ui.windows.getShared('groupCode');
                if (groupCode != undefined) {
                    self.GridlistCurrentCode_B_001(undefined);
                    self.GridCurrentCategoryAtr_B_001(groupCode);
                    self.enable_B_INP_002(true);
                }
            });
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
}