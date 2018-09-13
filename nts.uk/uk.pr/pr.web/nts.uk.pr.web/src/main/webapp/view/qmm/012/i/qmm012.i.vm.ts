module nts.uk.pr.view.qmm012.i.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import alertError = nts.uk.ui.dialog.alertError;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        lstBreakdownItemSet: KnockoutObservableArray<IBreakdownItemSet> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');
        currentBreakdownItemSet: KnockoutObservable<IBreakdownItemSet> = ko.observable(new BreakdownItemSet(null));

        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        salaryItemId: KnockoutObservable<string> = ko.observable('salary1');
        requiredCode: KnockoutObservable<boolean> = ko.observable(true);
        bindAtr: KnockoutObservable<string> = ko.observable('bindAtr');

        breakdownItemCode: KnockoutObservable<string> = ko.observable('');
        breakdownItemName: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this,
                dfd = $.Deferred();

            self.currentCode.subscribe((item) => {
                let itemModel = _.find(self.lstBreakdownItemSet(), function(x) { return x.breakdownItemCode == item });
                self.breakdownItemCode(itemModel.breakdownItemCode);
                self.breakdownItemName(itemModel.breakdownItemName);
                self.isNewMode(true);
            });
            block.invisible();
            service.getAllBreakdownItemSetById(self.salaryItemId()).done(function(data: Array<IBreakdownItemSet>) {
                if (data) {
                    let dataSort = _.sortBy(data, ["breakdownItemCode"]);
                    self.lstBreakdownItemSet(dataSort);
                    self.currentCode(self.lstBreakdownItemSet()[0].breakdownItemCode);
                }
                else {
                    //new mode
                    self.createItemSet();
                }
                dfd.resolve(self);
            }).fail(function(error) {
                alertError({ messageId: res.messageId });
                dfd.reject();
            }).always(() => {
                block.clear();
            });

        }

        createItemSet() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.breakdownItemCode('');
            self.breakdownItemName('');
            self.isNewMode(false);
            $("#breakdownItemCode").focus();
        }

        saveItemSet() {
            let self = this;
            let data: BreakdownItemSet = {
                salaryItemId: self.salaryItemId(), 
                breakdownItemCode: self.breakdownItemCode(), 
                breakdownItemName: self.breakdownItemName()};
            $("#breakdownItemCode").trigger("validate");
            $("#breakdownItemName").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create new holiday
                    service.addBreakdownItemSet(ko.toJS(data)).done(() => {
                        self.getAllData(data.breakdownItemCode).done(() => {
                            dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.setFocus();
                            });
                        });
                    }).fail(function(error) {
                            error.messageId == 'Msg_3') 
                            $('#breakdownItemCode').ntsError('set', error);
                            $('#breakdownItemName').focus();
                            dialog.alertError({ messageId: error.messageId });
                        
                    }).always(function() {
                        self.setFocus();
                        block.clear();
                    });
                } else {
                    // update
                    service.updateBreakdownItemSet(ko.toJS(data)).done(() => {
                        self.getAllData(data.breakdownItemCode).done(() => {
                            dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.setFocus();
                            });
                        });
                    }).fail(function(error) {
                        if (error.messageId == 'Msg_880') {
                            dialog.alertError({ messageId: error.messageId });
                        } else {
                            dialog.alertError({ messageId: error.messageId });
                        }
                    }).always(function() {
                        self.setFocus();
                        block.clear();
                    });
                }
            }
        }

        deleteItemSet() {
            let self = this,
                lstBreakdownItemSet = self.lstBreakdownItemSet,
                currentBreakdownItemSet: BreakdownItemSet = self.currentBreakdownItemSet();
            block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                if (currentBreakdownItemSet.breakdownItemCode()) {
                    let index: number = _.findIndex(lstBreakdownItemSet(), function(x)
                    { return x.cd() == currentBreakdownItemSet.breakdownItemCode() });
                    service.removeBreakdownItemSet(ko.toJS(currentBreakdownItemSet)).done(function() {
                        self.getAllData(self.currentCode()).done(() => {
                            dialog.info({ messageId: "Msg_16" }).then(() => {
                                if (self.lstBreakdownItemSet().length == 0) {
                                    self.currentCode('');
                                    self.isNewMode(true);
                                    self.setFocus();
                                    nts.uk.ui.errors.clearAll();
                                } else {
                                    if (index == self.lstBreakdownItemSet().length) {
                                        self.currentCode(self.lstBreakdownItemSet()[index - 1].breakdownItemCode());
                                    } else {
                                        self.currentCode(self.lstBreakdownItemSet()[index].breakdownItemCode());
                                    }
                                }
                            });
                        });

                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                        block.clear();
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    block.clear();
                }
            }).then(() => {
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
                block.clear();
            });;
        }
        close() {
            nts.uk.ui.windows.close();
        }

        settingCreateMode() {
            let self = this;
        }

        setFocus() {
            let self = this;
            if (self.isNewMode()) {
                $('#breakdownItemCode').focus();
            } else {
                $('#breakdownItemName').focus();
            }
        }

        getAllData(breakdownItemCode: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.lstBreakdownItemSet.removeAll();
            service.getAllBreakdownItemSetById(self.salaryItemId()).done(function(data: Array<BreakdownItemSet>) {
                if (data) {
                    let dataSort = _.sortBy(data, ["breakdownItemCode"]);
                    self.lstBreakdownItemSet(dataSort);
                    self.currentCode(self.lstBreakdownItemSet()[0].breakdownItemCode);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.currentCode('');
                    self.currentBreakdownItemSet(new BreakdownItemSet(null));
                    self.isNewMode(true);
                }
                block.clear();
                dfd.resolve(self);
            }).fail(function(res) {
                alertError({ messageId: res.messageId });
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }

    /**
     * 内訳項目設定
     */
    export interface IBreakdownItemSet {
        salaryItemId: string;
        breakdownItemCode: string;
        breakdownItemName: string;
    }
    export class BreakdownItemSet {
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        breakdownItemCode: KnockoutObservable<string> = ko.observable('');
        breakdownItemName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IBreakdownItemSet) {
            let self = this;
            self.salaryItemId(param ? param.salaryItemId : '');
            self.breakdownItemCode(param ? param.breakdownItemCode : '');
            self.breakdownItemName(param ? param.breakdownItemName : '');
        }
    }

}