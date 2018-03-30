module nts.uk.com.view.cmf002.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        conditionSetCode: string = '001';
        conditionSetName: string = 'A社向け会計システム　テーブルA';
        categoryName: string = '個人情報';
        outputItemList: KnockoutObservableArray<IOutputItem> = ko.observableArray([]);
        categoryItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectionItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<string> = ko.observable('');
        selectedCategoryItemCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        selectedSelectionItemList: KnockoutObservableArray<string> = ko.observableArray([]);
        itemTypeItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(getItemType());
        selectedItemType: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;


            self.initScreen();
        }

        initScreen() {
            let self = this;
            let outputItemList: Array<IOutputItem> = [];
            let categoryItemList: Array<IExternalOutputCategoryItemData> = [];

            for (let i = 1; i <= 30; i++) {
                outputItemList.push(ko.toJS({ outputItemCode: i >= 10 ? '0' + i : '00' + i, outputItemName: '出力項目' }));
                categoryItemList.push(ko.toJS({ itemNo: i >= 10 ? '00' + i : '000' + i, itemName: 'カテゴリ項目一覧' }));
            }

            self.outputItemList(outputItemList);
            self.categoryItemList(categoryItemList);
        }

        //終了する
        closeDialog() {
            close();
        }
        
        btnRightClick() {
            let self = this;
            if (self.selectedCategoryItemCodeList()) {
                for (let item of self.selectedCategoryItemCodeList()) {
                    let _selectedItem = _.find(self.categoryItemList(), x => { return x.itemNo === item });
                    let _indexSelectedItem = _.findIndex(self.categoryItemList(), x => { return x.itemNo === item });
                    self.selectionItemList.push(_selectedItem);
                    self.categoryItemList.remove(_selectedItem);
                }
            }
        }
        
        btnLeftClick() {
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
    
    //項目型
    export function getItemType(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText("Enum_ItemType_NUMERIC")),
            new model.ItemModel(1, getText("Enum_ItemType_CHARACTER")),
            new model.ItemModel(2, getText("Enum_ItemType_DATE")),
            new model.ItemModel(3, getText("Enum_ItemType_TIME")),
            new model.ItemModel(4, getText("Enum_ItemType_INS_TIME")),
            new model.ItemModel(5, getText("Enum_ItemType_IN_SERVICE"))
        ];
    }
    
    //出力項目(定型/ユーザ)
    export interface IOutputItem {
        outputItemCode: string;
        outputItemName: string;
    }

    export class OutputItem {
        outputItemCode: KnockoutObservable<string> = ko.observable('');
        outputItemname: KnockoutObservable<string> = ko.observable('');
        constructor(param: IOutputItem) {
            let self = this;
            self.outputItemCode(param.outputItemCode || '');
            self.outputItemname(param.outputItemName || '');
        }
    }
    
    //外部出力カテゴリ項目データ
    export interface IExternalOutputCategoryItemData {
        itemNo: string;
        itemName: string;
    }

    export class ExternalOutputCategoryItemData {
        itemNo: KnockoutObservable<string> = ko.observable('');
        itemName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IExternalOutputCategoryItemData) {
            let self = this;
            self.itemNo(param.itemNo || '');
            self.itemName(param.itemName || '');
        }
    }
}
