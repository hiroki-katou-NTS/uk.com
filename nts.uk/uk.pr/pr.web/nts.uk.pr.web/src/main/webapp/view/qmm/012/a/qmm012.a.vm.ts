module nts.uk.pr.view.qmm012.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import getCategoryAtr = qmm012.share.model.getCategoryAtr;
    import getCategory = qmm012.share.model.CategoryAtr;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        categoryAtr: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCategoryAtr: KnockoutObservable<number> = ko.observable(null);
        item: KnockoutObservable<String> = ko.observable('');

        constructor() {
            let self = this;
            self.categoryAtr = getCategoryAtr();
            self.selectedCategoryAtr.subscribe(category => {
                let getcategory = _.find(self.categoryAtr, function(x) { return x.code == category; });
                if (category == 0) {
                    self.item(getText("QMM012_5"));
                } else
                    if (category == 1) {
                        self.item(getText('QMM012_6'));
                    } else
                        if (category == 2) {
                            self.item(getText('QMM012_7'));
                        }

            });
            self.selectedCategoryAtr(0);
        }
        saveData() {
            let params = setShared('QMM012_A_Params', self.selectedCategoryAtr(); 
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