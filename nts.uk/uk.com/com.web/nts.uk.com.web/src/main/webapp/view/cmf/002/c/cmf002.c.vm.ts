module nts.uk.com.view.cmf002.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        currentStandardOutputItem: KnockoutObservable<model.StandardOutputItem> = ko.observable(new model.StandardOutputItem(null, null, null, null, 0));
        selectedStandardOutputItemCode: KnockoutObservable<string> = ko.observable("");
        listStandardOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        itemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        conditionName: KnockoutObservable<string>;
        categoryId: KnockoutObservable<string> = ko.observable("00001");
        categoryName: KnockoutObservable<string>;

        // itemCode: KnockoutObservable<string>;
        // itemName: KnockoutObservable<string>;
        // formula: KnockoutObservable<string>;
        // itemType: KnockoutObservable<number>;

        selectedExternalOutputCategoryItemData: KnockoutObservable<string>;
        listExternalOutputCategoryItemData: KnockoutObservableArray<model.ExternalOutputCategoryItemData> = ko.observableArray([]);

        selectedCategoryItem: KnockoutObservable<string> = ko.observable("");
        listCategoryItem: KnockoutObservableArray<model.CategoryItem> = ko.observableArray([]);

        constructor() {
            let self = this;
            let params = getShared("CMF002bParams");
            let _rsList: Array<model.ItemModel> = model.getItemTypes();
            self.itemTypes(_rsList);

            self.conditionName = ko.observable("Condition Name"); //params.conditionName + "　" + params.conditionCode
            self.categoryName = ko.observable("Category Name"); // params.categoryName
            // self.itemCode = ko.observable("Item Code");
            // self.itemName = ko.observable("Item Name");
            // self.itemType = ko.observable(0);
            //self.formula = ko.observable("A1+B2+C3");
            //self.selectedStandardOutputItemCode = ko.observable("123");
            self.selectedExternalOutputCategoryItemData = ko.observable("123");

            self.selectedStandardOutputItemCode.subscribe(code => {
                if (code) {
                    block.invisible();
                    service.findByCode(params.conditionCode, self.selectedStandardOutputItemCode()).done(data => {
                        if (data) {
                            self.isNewMode(false);
                            let item = new model.StandardOutputItem(data.outputItemCode, data.outputItemName, data.conditionSettingCode, "", data.itemType);
                            self.currentStandardOutputItem(item);
                            // self.itemCode(data.outputItemCode);
                            //  self.itemName(data.outputItemName);
                            //  self.itemType(data.itemType);
                            self.listCategoryItem(data.categoryItems);
                        }
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    self.settingNewMode();
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAllCategoryItem(self.categoryId()).done((result: Array<any>) => {
                let _rsList: Array<model.ExternalOutputCategoryItemData> = _.map(result, x => {
                    return new model.ExternalOutputCategoryItemData(x.itemNo, x.itemName);
                });
                self.listExternalOutputCategoryItemData(_rsList);
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }

        // 新規登録を実行する
        settingNewMode() {
            let self = this;
            self.selectedStandardOutputItemCode("");
        }
        
        // 出力項目を登録する
        registerOutputItem() {
            let self = this;
            let currentStandardOutputItem: model.StandardOutputItem = self.currentStandardOutputItem();
            
            
        }
        
        // 外部出力項目登録確認
        registerValidate() {
            
        }
        

        openCMF002g() {
            modal("/view/cmf/002/g/index.xhtml").onClosed(function() {

            });
        }

        openCMF002h() {
            modal("/view/cmf/002/h/index.xhtml").onClosed(function() {

            });
        }

        openCMF002f() {
            modal("/view/cmf/002/f/index.xhtml").onClosed(function() {

            });
        }

        openItemTypeSetting() {
            modal("/view/cmf/002/i/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/j/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/k/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/l/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/m/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/n/index.xhtml").onClosed(function() {

            });
        }
    }
}