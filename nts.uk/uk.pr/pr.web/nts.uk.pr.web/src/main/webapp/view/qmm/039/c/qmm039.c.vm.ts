module nts.uk.pr.view.qmm039.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import model = nts.uk.pr.view.qmm039.share.model;
    export class ScreenModel {
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        startMonth: KnockoutObservable<string> = ko.observable('');
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.dateValue = ko.observable({});

            self.startDateString.subscribe(function(value){
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe(function(value){
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });

            let params = getShared("QMM039_C_PARAMS");
            if (params) {
                block.invisible();

                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.DELETE, getText('QMM039_36')));
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.UPDATE, getText('QMM039_37')));
            }
            block.clear();
        }

        editHistory() {
            let self = this;
        }

        updateHistory() {
            let self = this;
        }

        deleteHistory() {
            let self = this;
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;
        }

    }
}
