module nts.uk.com.view.cmf002.v1.viewmodel {
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
        listCategoryItem: KnockoutObservableArray<Category>;
        selectedCategoryItem: KnockoutObservable<number>;
        selectedCategoryCode: KnockoutObservable<string> = ko.observable('');
        
        
        constructor() {
            var self = this;
            self.listCategoryItem = ko.observableArray([
                new Category(1, "Item 1"),
                new Category(2, "Item 2"),
                new Category(3, "Item 3"),
                new Category(4, "Item 4"),
                new Category(5, "Item 5"),
                new Category(6, "Item 6"),
                new Category(7, "Item 7"),
                new Category(8, "Item 8"),
                new Category(9, "Item 9"),
                new Category(10, "Item 10"),
                new Category(11, "Item 11"),
            ]);
            self.selectedCategoryItem = ko.observable(1);
        }
        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        selectCategoryItem() {

        }
        cancelSelectCategoryItem() {
            nts.uk.ui.windows.close();
        }
        
    }
    export class Category {
        dispCategoryCode: number;
        dispCategoryName: string;
        constructor(dispCategoryCode: number, dispCategoryName: string) {
            this.dispCategoryCode = dispCategoryCode;
            this.dispCategoryName = dispCategoryName;
        }
    }
}