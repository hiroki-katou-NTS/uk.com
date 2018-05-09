module nts.uk.at.view.kmw006.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;

    export class ScreenModel {

        items: KnockoutObservableArray<ItemModel>;
        closureIdName: KnockoutObservable<string> = ko.observable("");
        dispTargetYm: KnockoutObservable<string> = ko.observable("");
        executionDt: KnockoutObservable<string> = ko.observable("");
        currentCode: KnockoutObservable<string> = ko.observable("");
        params: any;

        constructor() {
            var self = this;
            self.params = getShared("Kmw006dParams");
            self.closureIdName(self.params.closure);
            self.dispTargetYm(self.params.targetYm);
            self.executionDt(self.params.executionDt);
            self.items = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getResults(self.params.listEmpId).done((result: Array<any>) => {
                let _a: Array<ItemModel> = [];
                for (var i = 0; i < result.length; i++) {
                    let r = result[i];
                    _a.push(new ItemModel(i + 1, r.employeeCode, r.pname));
                }
                self.items(_a);
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        private closeDialog() {
            nts.uk.ui.windows.close();
        }

    }

    class ItemModel {
        no: number;
        employeeCode: string;
        employeeName: string;

        constructor(no: number, code: string, name: string) {
            this.no = no;
            this.employeeCode = code;
            this.employeeName = name
        }
    }

}