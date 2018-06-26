module nts.uk.at.view.kfp001.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;

    export class ScreenModel {

        items: KnockoutObservableArray<ItemModel>;
        code: KnockoutObservable<string> = ko.observable("");
        name: KnockoutObservable<string> = ko.observable("");
        start: KnockoutObservable<string> = ko.observable("");
        end: KnockoutObservable<string> = ko.observable("");
        targetNum: KnockoutObservable<string> = ko.observable("");
        currentCode: KnockoutObservable<string> = ko.observable("");
        params: any;

        constructor() {
            var self = this;
            self.params = getShared("Kfp001fParams");
            self.code(self.params.code);
            self.name(self.params.name);
            self.start(self.params.start);
            self.end(self.params.end);
            self.targetNum(self.params.dispTargetPeopleNum);
            self.items = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getTargets(self.params.logId).done((result: Array<any>) => {
                if (result && result.length > 0) {
                    let list: Array<ItemModel> = _.map(result, t => {
                        return new ItemModel(t.employeeId, t.employeeCode, t.employeeName, t.status);
                    });
                    self.items(list);
                }
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
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        status: string;

        constructor(id: string, code: string, name: string, status: string) {
            this.employeeId = id;
            this.employeeCode = code;
            this.employeeName = name;
            this.status = status;
        }
    }

}