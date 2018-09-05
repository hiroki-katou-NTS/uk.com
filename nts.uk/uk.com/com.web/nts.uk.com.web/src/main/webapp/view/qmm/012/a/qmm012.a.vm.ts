module nts.uk.com.view.qmm012.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import getCategoryAtr = qmm012.share.model.getCategoryAtr;
    import getCategory = qmm012.share.model.CategoryAtr;

    export class ScreenModel {

        categoryAtr: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCategoryAtr: KnockoutObservable<number> = ko.observable(0);
        item: KnockoutObservable<String> = ko.observable('');

        constructor() {
            let self = this;
            self.categoryAtr = getCategoryAtr();
            selectedCategoryAtr.subscribe(category =>{
                let getcategory = _.find(self.categoryAtr(), function(x) { return x.code == category; });
                
            });
        }
        saveData(){

        }
        cancelSetting(){
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