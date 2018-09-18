module nts.uk.pr.view.qmm012.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import getCategory = qmm012.share.model.CategoryAtr;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm012.share.model;

    export class ScreenModel {

        categoryAtr: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedCategoryAtr: KnockoutObservable<number> = ko.observable(null);
        item: KnockoutObservable<String> = ko.observable('');

        constructor() {
            let self = this;
            self.categoryAtr(model.getCategoryAtr());
            self.selectedCategoryAtr.subscribe(category => {
                self.item(model.getItemAtrText(category));           
            });
            self.selectedCategoryAtr(0);
        }
        saveData() {
            let self = this;
            setShared('QMM012_A_Params', self.selectedCategoryAtr()); 
            nts.uk.ui.windows.close();
        }
        cancelSetting() {
            nts.uk.ui.windows.close();
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
}