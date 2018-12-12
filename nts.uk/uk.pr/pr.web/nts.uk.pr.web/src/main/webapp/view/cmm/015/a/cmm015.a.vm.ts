module nts.uk.pr.view.cmm015.a.viewmodel {

    import getText = nts.uk.resource.getText

    export class ScreenModel {
        dataSource: KnockoutObservableArray<model.ISalaryClassificationInformation>;
        selectedCode: KnockoutObservable<string>;
        selectedItem: KnockoutObservable<model.SalaryClassificationInformation>;
        columns: KnockoutObservableArray<any>;
        isDeleteEnable: KnockoutObservable<boolean>;
        isCodeEnable: KnockoutObservable<boolean>;
        isUpdateMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            self.isCodeEnable = ko.observable(false);
            self.dataSource = ko.observableArray([]);
            self.selectedCode = ko.observable();
            self.columns = ko.observableArray([
                {headerText: getText("CMM015_7"), key: 'salaryClassificationCode'},
                {headerText: getText("CMM015_8"), key: 'salaryClassificationName', formatter: _.escape}
            ]);
            self.isDeleteEnable = ko.observable(false);
            self.selectedItem = ko.observable(new model.SalaryClassificationInformation(null));
            self.isUpdateMode = ko.observable(true);
            self.selectedCode.subscribe((code) => {
                if (code) {
                    self.selectedItem(new model.SalaryClassificationInformation(self.find(code)));
                    self.isUpdateMode(true);
                    self.setUpdateMode();
                } else {
                    self.selectedItem(new model.SalaryClassificationInformation(null));
                    self.isUpdateMode(false);
                    self.setNewMode();
                }
            });

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.getAllSalaryClassificationInformation().done(function (data: any) {
                if (data.length > 0) {
                    self.isUpdateMode(true);
                    self.dataSource(data);
                    self.selectedCode(data[0].salaryClassificationCode);
                } else {
                    self.isUpdateMode(false);
                }
                dfd.resolve();
                nts.uk.ui.block.clear();
            }).fail(function (res) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.bundledErrors(res);
                dfd.reject();
            });
            return dfd.promise();
        }

        setNewMode() {
            let self = this;
            self.selectedCode(null);
            self.selectedItem(new model.SalaryClassificationInformation(null));
            self.isDeleteEnable(false);
            self.isCodeEnable(true);
            $('#A3_2').focus();
            nts.uk.ui.errors.clearAll();
        }

        setUpdateMode() {
            let self = this;
            self.isDeleteEnable(true);
            self.isCodeEnable(false);
            $('#A3_3').focus();
            nts.uk.ui.errors.clearAll();
        }

        registerSalaryClassificationInformation() {
            let self = this;
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let item = self.selectedItem();
            if (self.isUpdateMode()) {
                nts.uk.ui.block.invisible();
                service.updateSalaryClassificationInformation(ko.toJS(item)).done(function () {
                    service.getAllSalaryClassificationInformation().done(function (data: any) {
                        self.dataSource(data);
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({messageId: "Msg_15"});
                    }).fail(function (res) {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.bundledErrors(res);
                    });
                }).fail(function (res) {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.bundledErrors(res);
                });
            } else {
                nts.uk.ui.block.invisible();
                service.addSalaryClassificationInformation(ko.toJS(item)).done(function () {
                    service.getAllSalaryClassificationInformation().done(function (data: any) {
                        self.dataSource(data);
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                            self.selectedCode(item.salaryClassificationCode());
                        });
                    }).fail(function (res) {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.bundledErrors(res);
                    });
                }).fail(function (res) {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.bundledErrors(res);
                });
            }
        }

        removeSalaryClassificationInformation() {
            let self = this;
            nts.uk.ui.dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                nts.uk.ui.block.invisible();
                let code = self.selectedCode();
                let index = self.dataSource().indexOf(self.find(code));
                service.removeSalaryClassificationInformation(code).done(function () {
                    service.getAllSalaryClassificationInformation().done(function (data: any) {
                        self.dataSource(data);
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({messageId: "Msg_16"}).then(() => {
                            let dataLength = self.dataSource().length;
                            if (dataLength > 0) {
                                if (index == dataLength) {
                                    self.selectedCode(self.dataSource()[index - 1].salaryClassificationCode);
                                } else {
                                    self.selectedCode(self.dataSource()[index].salaryClassificationCode);
                                }
                            } else {
                                self.selectedCode(null);
                            }
                        });

                    }).fail(function (res) {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.bundledErrors(res);
                    });
                }).fail(function (res) {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.bundledErrors(res);
                });
            });
        }

        find(code: string): any {
            let self = this;
            return _.find(self.dataSource(), x => code === x.salaryClassificationCode);
        }
    }

    export module model {
        export interface ISalaryClassificationInformation {
            salaryClassificationCode: string;
            salaryClassificationName: string;
            memo: string;
        }

        export class SalaryClassificationInformation {
            salaryClassificationCode: KnockoutObservable<string> = ko.observable(null);
            salaryClassificationName: KnockoutObservable<string> = ko.observable(null);
            memo: KnockoutObservable<string> = ko.observable(null);

            constructor(params: ISalaryClassificationInformation) {
                if (!params) return;
                this.salaryClassificationCode(params.salaryClassificationCode);
                this.salaryClassificationName(params.salaryClassificationName);
                this.memo(params.memo);
            }
        }
    }
}