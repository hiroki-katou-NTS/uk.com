module nts.uk.pr.view.qmm039.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import model = nts.uk.pr.view.qmm039.share.model;

    export class ScreenModel {
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        timeCloseDateSelectedCode: KnockoutObservable<number>;
        takeoverMethod: KnockoutObservable<number> = ko.observable(1);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        constructor() {
            let self = this;
            let params = getShared("QMM039_A_PARAMS");
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


            let displayLastestHistory = "";
            self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.NO_HISTORY, getText('QMM039_28', [displayLastestHistory])));
            self.takeoverMethod(0);

            //self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.NO_HISTORY, getText('QMM039_29'));
        }

        addNewHistory() {

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
