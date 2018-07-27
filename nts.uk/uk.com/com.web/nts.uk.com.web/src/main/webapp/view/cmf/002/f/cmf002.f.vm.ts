module nts.uk.com.view.cmf002.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        conditionSetCode: string = '001';
        conditionSetName: string = 'A社向け会計システム　テーブルA';
        categoryName: string = '個人情報';
        externalOutputCategoryItemData: KnockoutObservable<ExternalOutputCategoryItemData> = ko.observable(new ExternalOutputCategoryItemData({
            itemNo: '',
            itemName: '',
            categoryId: '',
            itemType: ''
        });
        outputItemList: KnockoutObservableArray<IOutputItem> = ko.observableArray([]);
        categoryItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectionItemList: KnockoutObservableArray<IExternalOutputCategoryItemData> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<string> = ko.observable('');
        selectedCategoryItemCodeList: KnockoutObservableArray<number> = ko.observableArray([]);
        selectedSelectionItemList: KnockoutObservableArray<number> = ko.observableArray([]);
        itemTypeItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(getItemType());
        selectedItemType: KnockoutObservable<string> = ko.observable('');
        selectedAddOutputItem: KnockoutObservableArray<any> = ko.observableArray([]);
        // getShared from screen B
        condSetCd: KnockoutObservable<string> = ko.observable('000');
        categoryId: KnockoutObservable<number> = ko.observable('102');
        itemNo: KnockoutObservable<number> = ko.observable('1');

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen() {
            let self = this;
            service.getOutputItem(self.condSetCd()).done(function(data: Array<any>) {
                if (data && data.length) {
                    self.outputItemList(data);
                }
            })
            service.getCtgData(self.categoryId(), self.itemNo()).done(function(data: Array<any>) {
                if (data && data.length) {
                    self.categoryItemList(data);
                }
            })
        }

        //終了する
        closeDialog() {
            close();
        }

        btnRightClick() {
            let self = this;
            if (self.selectedCategoryItemCodeList()) {
                for (let item of self.selectedCategoryItemCodeList()) {
                    let _selectedItem = _.find(self.categoryItemList(), function(x) { return x.itemNo == item });
                    self.selectionItemList.push(_selectedItem);
                    self.categoryItemList.remove(_selectedItem);
                }
            }
        }

        btnLeftClick() {
            let self = this;
            if (self.selectedSelectionItemList()) {
                for (let item of self.selectedSelectionItemList()) {
                    let _selectedItem = _.find(self.selectionItemList(), function(x) { return x.itemNo == item });
                    self.selectionItemList.remove(_selectedItem);
                    self.categoryItemList.push(_selectedItem);
                }
            }
        }

        register() {
            let self = this;
            if (self.selectedSelectionItemList().length) {
                let _listOutputItemCode = _.map(self.outputItemList(), function(o) { return parseInt(o.outputItemCode) });
                for (let item of self.selectedSelectionItemList()) {
                    var _selectedItem = _.find(self.selectionItemList(), function(x) { return x.itemNo == item });
                    self.selectedAddOutputItem.removeAll();
                    self.selectedAddOutputItem.push(ko.toJS(new AddOutputItem(parseInt(_.max(_listOutputItemCode)), '000', _selectedItem.itemName, _selectedItem.itemType, _selectedItem.itemNo, _selectedItem.categoryId)));
                }
                service.addOutputItem(self.selectedAddOutputItem()).done(function() {
                    service.getOutputItem(self.condSetCd()).done(function(data: Array<any>) {
                        if (data && data.length) {
                            self.outputItemList(data);
                        }
                    })
                });
            } else {
                alertError({ messageId: 'Msg_656' });
            }
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
        itemNo: number;
        itemName: string;
        categoryId: number;
        itemType: number;
    }

    export class ExternalOutputCategoryItemData {
        itemNo: KnockoutObservable<number> = ko.observable('');
        itemName: KnockoutObservable<string> = ko.observable('');
        categoryId: KnockoutObservable<number> = ko.observable('');
        itemType: KnockoutObservable<number> = ko.observable('');
        constructor(param: IExternalOutputCategoryItemData) {
            let self = this;
            self.itemNo(param.itemNo || '');
            self.itemName(param.itemName || '');
            self.categoryId(param.categoryId || '');
            self.itemType(param.itemType || '');
        }
    }

    export interface IAddOutputItem {
        outItemCd: number;
        condSetCd: string;
        outItemName: string;
        itemType: number;
        itemNo: number;
        categoryId: number;
    }

    export class AddOutputItem {
        outItemCd: KnockoutObservable<number> = ko.observable('');
        condSetCd: KnockoutObservable<string> = ko.observable('');
        outItemName: KnockoutObservable<string> = ko.observable('');
        itemType: KnockoutObservable<number> = ko.observable('');
        itemNo: KnockoutObservable<number> = ko.observable('');
        categoryId: KnockoutObservable<number> = ko.observable('');
        constructor(outItemCd: number, condSetCd: string, outItemName: string, itemType: number, itemNo: number, categoryId: number) {
            let self = this;
            self.outItemCd(outItemCd);
            self.condSetCd(condSetCd);
            self.outItemName(outItemName);
            self.itemType(itemType);
            self.itemNo(itemNo);
            self.categoryId(categoryId);
        }
    }
}
