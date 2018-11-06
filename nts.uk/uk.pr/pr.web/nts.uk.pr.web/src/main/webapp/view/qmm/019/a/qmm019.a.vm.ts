module nts.uk.pr.view.qmm019.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import SpecificationLayoutHist = nts.uk.pr.view.qmm019.share.model.SpecificationLayoutHist;
    import modal = nts.uk.ui.windows.sub.modal

    export class ScreenModel {

        dataSource: KnockoutObservableArray<SpecificationLayoutHist>;
        currentHistory: KnockoutObservable<string>;
        headers: any;
        columns: KnockoutObservableArray<any>;

        constructor() {
            let self = this;

            self.dataSource = ko.observableArray([
                new SpecificationLayoutHist({
                    specCode: "1",
                    name: "1",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "a"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "b"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "c"
                        },
                    ]
                }),
                new SpecificationLayoutHist({
                    specCode: "2",
                    name: "2",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "d"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "e"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "f"
                        },
                    ]
                }),
                new SpecificationLayoutHist({
                    specCode: "3",
                    name: "3",
                    history: [
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "g"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "h"
                        },
                        {
                            startMonth: "startMonth",
                            endMonth: "endMonth",
                            historyId: "j"
                        },
                    ]
                })
            ]);

            self.currentHistory = ko.observable(null);
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]);

            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'nodeText', width: 100 }
            ]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        public create(): void {

        }

        public registered(): void {

        }

        public outputExcel(): void {
            modal("/view/qmm/019/d/index.xhtml");
        }

        public modifyLog(): void {

        }
    }
}