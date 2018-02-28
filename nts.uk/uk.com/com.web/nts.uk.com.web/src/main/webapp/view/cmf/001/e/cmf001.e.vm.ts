module nts.uk.com.view.cmf001.e.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listCsvItem: KnockoutObservableArray<model.MappingListData>;
        listCsvItemShared: model.MappingListData[];
        selectedCsvItemNumber: KnockoutObservable<number>;
        selectedCsvItem: model.MappingListData
        constructor() {
            var self = this;
            self.listCsvItemShared = getShared("listCsvItem");
            self.selectedCsvItem = getShared("selectedCsvItemShared");
            if (self.listCsvItemShared.length > 0) {
                self.listCsvItem = ko.observableArray(self.listCsvItemShared);
                if (_.isNull(self.selectedCsvItem)) {
                    self.selectedCsvItemNumber = ko.observable(1);
                } else {
                    if(_.some(self.listCsvItemShared, self.selectedCsvItem)){
                        self.selectedCsvItemNumber = ko.observable(self.selectedCsvItem.dispCsvItemNumber);
                    }else{
                        self.listCsvItem = ko.observableArray([new model.MappingListData(1, "None")]);
                        alertError({ messageId: "Msg_905", messageParams: ["X", "Y"] });
                    }
                }
            }
            else {
                self.listCsvItem = ko.observableArray([new model.MappingListData(1, "None")]);
                alertError({ messageId: "Msg_905", messageParams: ["X", "Y"] });
            }
            self.selectedCsvItemNumber.subscribe(function(data: any) {
                if (data) {
                    self.selectedCsvItem = _.find(ko.toJS(self.listCsvItemShared), (x: model.MappingListData) => x.dispCsvItemNumber == data);
                }
            });
        }
        cancel() {
            nts.uk.ui.windows.close();
        }
        save() {
            var self = this;
            setShared("selectedCsvItemShared", self.selectedCsvItem);
            nts.uk.ui.windows.close();
        }
    }
}