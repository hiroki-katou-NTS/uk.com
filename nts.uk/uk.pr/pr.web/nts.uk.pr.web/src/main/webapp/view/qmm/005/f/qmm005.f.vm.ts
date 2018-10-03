module nts.uk.pr.view.qmm005.f.viewmodel {
    import model = nts.uk.pr.view.qmm005.share.model;
    import resource = nts.uk.resource;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    export class ScreenModel {
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        itemShared: any;

        constructor() {
            var self = this;
            self.itemsSwap = ko.observableArray([]);
            self.itemShared = getShared("QMM005_output_F");
            this.itemsSwap(self.itemShared.employeeList);
            this.columns = ko.observableArray([
                {headerText: resource.getText('QMM005_93'), key: 'code', width: 90},
                {headerText: resource.getText('QMM005_94'), key: 'name', width: 200}
            ]);
            this.currentCodeListSwap = ko.observableArray(self.itemShared.employeeSelectedList);
            if ($('#F1_2-grid1_container')) {
                setTimeout(function () {
                    $('#F1_2-grid1_container').focus();
                }, 550);
            }
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        remove() {
            this.itemsSwap.shift();
        }

        submit() {
            var self = this;
            if(self.currentCodeListSwap().length == 0){
                nts.uk.ui.dialog.alertError({messageId: "Msg_105"});
                return;
            }
            setShared("QMM005F_outParams", {
                processCateNo: self.itemShared.processCateNo,
                returnList: self.currentCodeListSwap()
            });
            close();
        }

        cancel() {
            close();
        }
    }
    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}