module nts.uk.com.view.cmf005.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import gridColumn = nts.uk.ui.NtsGridListColumn;

    export class ScreenModel {
        //Dropdownlist contain System data
        systemList: KnockoutObservableArray<SystemModel> = ko.observableArray([]);
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<number>;

        //C5
        listCategorysDelete: KnockoutObservableArray<CategoryModel> = ko.observableArray([]);
        swapColumns: KnockoutObservableArray<gridColumn>;
        listCategorysChose: KnockoutObservableArray<CategoryModel> = ko.observableArray([]);

        /* screen */
        constructor() {
            this.systemName = ko.observable('');
            this.currentSystemCode = ko.observable(0);
            this.selectedSystemCode = ko.observable(0);

            //C5
            // set tam sau lay tu tang apdapter
            var array = [];
            for (var i = 1; i < 10; i++) {
                array.push(new CategoryModel('0000' + i, 'cccccc' + i));
            }
            this.listCategorysDelete(array);

            this.swapColumns = ko.observableArray([
                { headerText: getText('CMF005_19'), key: 'categoryId', width: 100 },
                { headerText: getText('CMF005_20'), key: 'categoryName', width: 150 }
            ]);

        }

        // init screen
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(), systemList = self.systemList;

            service.getListSystemType().done((itemList: Array<SystemModel>) => {

                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {
                    systemList(itemList);
                }

            }).always(() => {
                dfd.resolve();
            });
            return dfd.promise();
        }

      
        //Load category
        loadCategoryList(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(), listCategorysDelete = self.listCategorysDelete,
                listCategorysChose =self.listCategorysChose,
                searchText='';
            // get list category delete
            let listCategoryId = [];
            for (let categoryModel in listCategorysChose) {
                console.log(categoryModel.categoryId); // "species"
                listCategoryId.push(categoryModel.categoryId);
            }
            
//            service.getCategorysDeletion(self.selectedSystemCode,searchText,listCategoryId).done((itemList: Array<CategoryModel>) => {
//                console.log("aaaaaa:" + itemList.length);
//                // in case number of RoleSet is greater then 0
//                if (itemList && itemList.length > 0) {
//                    listCategorysDelete(itemList);
//                }
//
//            }).always(() => {
//                dfd.resolve();
//            });
            return dfd.resolve();
        }

        // Return code / name of selected line
        selectedListCategory() {
            var self = this;
            let listCategoryChose = self.listCategorysChose;
            let listCategorysDelete = self.listCategorysDelete;
            
            for (let categoryModel in listCategorysDelete) {
                console.log("ố ố:"+categoryModel.categoryId); // "ố ố"
                
            }
            console.log("systemList:" + self.systemList.length);
            console.log("listCategorysDelete" + listCategorysDelete.length);
            console.log("listCategoryChose:" + listCategoryChose.length);

            //            if (!_.isEqual(self.selectedConvertCode(), "")){
            //                codeConvert = _.find(ko.toJS(self.listConvertCode), (x: model.AcceptanceCodeConvert) => x.dispConvertCode == self.selectedConvertCode());
            //            }
            //            // 選択された行のコード/名称を返す
            //            setShared("cmf005kOutput", { selectedConvertCodeShared: codeConvert});
            //            nts.uk.ui.windows.close();
        }
        //Cancel and exit
        cancelSelectCategory() {
            nts.uk.ui.windows.close();
        }
    }

    class SystemModel {
        systemTypeValue: number;
        systemTypeName: string;

        constructor(systemTypeValue: number, systemTypeName: string) {
            this.systemTypeValue = systemTypeValue;
            this.systemTypeName = systemTypeName;
        }
    }

    class CategoryModel {
        categoryId: string;
        categoryName: string;
        deletable: boolean;
        constructor(categoryId: number, categoryName: string) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.deletable = categoryId % 2 === 0;
        }
    }
}