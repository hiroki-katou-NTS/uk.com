// TreeGrid Node
module qpp014.a.viewmodel {
    export class ScreenModel {
        a_SEL_001_items: KnockoutObservableArray<shr.viewmodelbase.PayDayProcessing>;
        a_SEL_001_itemSelected: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.a_SEL_001_items = ko.observableArray([]);
            self.a_SEL_001_itemSelected = ko.observable();
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findAll().done(function() {
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }

        /**
         * get data from table PAYDAY_PROCESSING
         */
        findAll(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            //get data with BONUS_ATR = 0 
            qpp014.a.service.findAll(0)
                .done(function(data) {
                    if (data.length > 0) {
                        self.a_SEL_001_items(data);
                        self.a_SEL_001_itemSelected(self.a_SEL_001_items()[0]);
                    } else {
                        nts.uk.ui.dialog.alert("対象データがありません。");//ER010
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        /**
         * transfer data to screen D, go to next screen
         */
        nextScreen(): void {
            var self = this;
            var data = _.find(self.a_SEL_001_items(), function(x) {
                return x.processingNo === self.a_SEL_001_itemSelected();
            });
            nts.uk.request.jump("/view/qpp/014/b/index.xhtml", data);
        }
    }
};
