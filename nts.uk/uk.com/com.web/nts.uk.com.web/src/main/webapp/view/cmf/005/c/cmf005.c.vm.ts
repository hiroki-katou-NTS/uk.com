module nts.uk.com.view.cmf005.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf005.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import gridColumn = nts.uk.ui.NtsGridListColumn;

    export class ScreenModel {
        //Dropdownlist contain System data
        systemList: KnockoutObservableArray<model.SystemModel> = ko.observableArray([]);
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<number>;

        //C5
        listCategorysDelete: KnockoutObservableArray<model.ItemCategory> = ko.observableArray([]);
        swapColumns: KnockoutObservableArray<gridColumn>;
        listCategorysChose: KnockoutObservableArray<model.ItemCategory> = ko.observableArray([]);

        /* screen */
        constructor() {
            this.systemName = ko.observable('');
            this.currentSystemCode = ko.observable(0);
            this.selectedSystemCode = ko.observable(0);

            //C5
            // set tam sau lay tu tang apdapter
            var array = [];
            for (var i = 1; i < 10; i++) {
                array.push(new model.ItemCategory(i, '0000' + i, 'cccccc' + i, '1', '2'));
            }
            this.listCategorysDelete(array);

            this.swapColumns = ko.observableArray([
                { headerText: '', key: 'cateItemNumber', width: 40, hidden: true, formatter: _.escape },
                { headerText: getText('CMF005_19'), key: 'categoryId', width: 80 },
                { headerText: getText('CMF005_20'), key: 'categoryName', width: 150 }
            ]);

        }

        // init screen
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(), systemList = self.systemList;
            service.getListSystemType().done((itemList: Array<model.SystemModel>) => {
                // in case number of System list is greater then 0
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
                listCategorysChose = self.listCategorysChose,
                searchText = '';
            // get list category delete
            let listCategoryId = [];
            let category = model.ItemCategory;
            for (category in listCategorysChose) {
                console.log(category.categoryId); // "species"
                listCategoryId.push(category.categoryId);
            }

            //            service.getCategorysDeletion(self.selectedSystemCode,searchText,listCategoryId).done((itemList: Array<model.ItemCategory>) => {
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
            // return screen B
            setShared("CMF005COutput", { listCategoryChose: self.listCategorysChose(),systemTypeId:self.selectedSystemCode()});
            nts.uk.ui.windows.close();
        }
        //Cancel and exit
        cancelSelectCategory() {
            nts.uk.ui.windows.close();
        }
    }




}