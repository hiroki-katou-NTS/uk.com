module nts.uk.pr.view.qmm012.b {

    import model = qmm012.share.model;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;

    export module viewModel {
        export class ScreenModel {
            
            // category comboBox
            categoryList: KnockoutObservableArray<model.ItemModel>;
            selectedCategory: KnockoutObservable<string> = ko.observable(null);

            // Also display abolition
            isdisplayAbolition: KnockoutObservable<boolean> = ko.observable(false);
            
            // statement gridList
            statementItemDataList: KnockoutObservableArray<IStatementItemCustom> = ko.observableArray([]);
            statementItemDataSelected: KnockoutObservable<StatementItemData>;
            currentKey: KnockoutObservable<string> = ko.observable(null);
            
            // define gridColumns
            gridColumns: any;
            
            constructor() {
                let self = this;
                
                // category comboBox
                self.categoryList = ko.observableArray([
                    new model.ItemModel('5', getText('Enum_CategoryAtr_ALL')),
                    new model.ItemModel(model.CategoryAtr.PAYMENT_ITEM.toString(), getText('QMM012_3')),
                    new model.ItemModel(model.CategoryAtr.DEDUCTION_ITEM.toString(), getText('QMM012_4')),
                    new model.ItemModel(model.CategoryAtr.ATTEND_ITEM.toString(), getText('QMM012_5')),
                    new model.ItemModel(model.CategoryAtr.REPORT_ITEM.toString(), getText('QMM012_6')),
                    new model.ItemModel(model.CategoryAtr.OTHER_ITEM.toString(), getText('QMM012_7'))
                ]);
                
                self.gridColumns = [
                                        { headerText: '', key: 'key', hidden: true },
                                        { headerText: getText('QMM012_27'), key: 'categoryAtr', width: 80 , formatter: getCategoryAtrText },
                                        { headerText: getText('QMM012_32'), key: 'itemNameCd', width: 60, formatter: _.escape },
                                        { headerText: getText('QMM012_33'), key: 'name', width: 200, formatter: _.escape },
                                        { headerText: getText('QMM012_34'), key: 'deprecatedAtr', width: 50, formatter: v => {
                                            if (v == model.Abolition.ABOLISH) {
                                                return '<div style="text-align: center; max-height: 18px;"><i class="ui-icon ui-icon-check"></i></div>';
                                            }
                                            return '';
                                        } }
                                   ];

                self.statementItemDataSelected = ko.observable(new StatementItemData(null, self));
                
                self.selectedCategory.subscribe(() => {
                    let oldKey= self.statementItemDataSelected().key;
                    
                    self.loadListData().done(function() {
                        let matchKey = _.filter(self.statementItemDataList(), function(o) {
                            return oldKey == o.key;
                        });
                        
                        if(matchKey.length > 0) {
                            self.currentKey(oldKey);
                        } else if(self.statementItemDataList().length > 0) {
                            self.currentKey(self.statementItemDataList()[0].key);
                        }
                    });
                });

                self.isdisplayAbolition.subscribe(() => {
                    let oldKey = self.statementItemDataSelected().key;
                    
                    self.loadListData().done(function() {
                        let matchKey = _.filter(self.statementItemDataList(), function(o) {
                            return oldKey == o.key;
                        });
                        
                        if(matchKey.length > 0) {
                            self.currentKey(oldKey);
                        } else if(self.statementItemDataList().length > 0) {
                            self.currentKey(self.statementItemDataList()[0].key);
                        }
                    });
                });

                self.currentKey.subscribe(x => {

                    if(x && (x != "")) {
                        let data: IStatementItemCustom = _.filter(self.statementItemDataList(), function(o) {
                            return x == o.key;
                        })[0];

                        if(data) {
                            self.loadItemData(data.categoryAtr, data.itemNameCd);

                            setTimeout(function(){
                                $("tr[data-id='" + x + "'] ").focus();
                                $("#B3_3").focus();
                            }, 500);
                        } else {
                            self.statementItemDataSelected(new StatementItemData(null, self));
                        }
                    } else {
                        self.statementItemDataSelected(new StatementItemData(null, self));
                    }

                    nts.uk.ui.errors.clearAll();
                });
            }//end constructor

            loadItemData(categoryAtr: number, itemNameCd: string) {
                let self = this;
                block.invisible();

                service.getStatementItemData(categoryAtr, itemNameCd).done(function(data: IStatementItemData) {
                    if(data) {
                        self.statementItemDataSelected(new StatementItemData(data, self));
                    } else {
                        self.statementItemDataSelected(new StatementItemData(null, self));
                    }

                    block.clear();
                }).fail(error => {
                    self.statementItemDataSelected(new StatementItemData(null, self));

                    block.clear();
                });
            }
            
            loadListData(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                block.invisible();
                
                let category: number = -1;
                if(!_.isEmpty(self.selectedCategory())) {
                    category = parseInt(self.selectedCategory(), 10);
                }
                
                service.getAllStatementItemData(category, self.isdisplayAbolition()).done(function(data: Array<IStatementItemCustom>) {
                    data.sort(self.compare);
                    self.statementItemDataList(data);
                    
                    if(self.statementItemDataList().length <= 0) {
                        self.currentKey(null);
                        self.statementItemDataSelected(new StatementItemData(null, self));
                    }
                    
                    block.clear();
                    deferred.resolve();
                }).fail(error => {
                    self.statementItemDataSelected(new StatementItemData(null, self));
                    
                    block.clear();
                    deferred.resolve();
                });
                
                return deferred.promise();
            }
            
            compare(a: IStatementItemCustom, b: IStatementItemCustom): number {
            
                let comparison = 0;
                if (a.categoryAtr > b.categoryAtr) {
                    comparison = 1;
                } else if (a.categoryAtr < b.categoryAtr) {
                    comparison = -1;
                } else if (a.categoryAtr == b.categoryAtr) {
                    if(a.itemNameCd > b.itemNameCd) {
                        comparison = 1;
                    } else {
                        comparison = -1;
                    }
                }
                
                return comparison;
            }
            
            public create(): void {
                let self = this;
                
                nts.uk.ui.errors.clearAll();

                nts.uk.ui.windows.sub.modal('../a/index.xhtml').onClosed(() => {
                    let data = getShared("QMM012_A_Params");
                    
                    if(data != null) {
                        let categoryAtr = parseInt(data, 10);
                        self.currentKey(null);
                        self.statementItemDataSelected().statementItem().categoryAtr(categoryAtr);
                    }
                    
                    if(self.statementItemDataSelected().checkCreate()) {
                        $("#B3_2").focus();
                    } else {
                        $("#B3_3").focus();
                    }
                });
            }
            
            public register(): void {
                let self = this;
                let categoryAtr = self.statementItemDataSelected().statementItem().categoryAtr();
                let itemRangeSet;
                
                nts.uk.ui.errors.clearAll();
                
                if((categoryAtr == model.CategoryAtr.PAYMENT_ITEM) || (categoryAtr == model.CategoryAtr.DEDUCTION_ITEM)) {
                    itemRangeSet = (categoryAtr == model.CategoryAtr.PAYMENT_ITEM) ? self.statementItemDataSelected().paymentItemSet() : self.statementItemDataSelected().deductionItemSet();

                    if((itemRangeSet.errorUpperLimitSetAtr() == 1) && ((itemRangeSet.errorUpRangeVal() == null) || (itemRangeSet.errorUpRangeVal().toString() == ""))) {
                        $('#C2_12').ntsError('set', { messageId: "MsgQ_14" });
                    }
                    
                    if((itemRangeSet.errorLowerLimitSetAtr() == 1) && ((itemRangeSet.errorLoRangeVal() == null) || (itemRangeSet.errorLoRangeVal().toString() == ""))) {
                        $('#C2_15').ntsError('set', { messageId: "MsgQ_15" });
                    }
                    
                    if((itemRangeSet.errorUpperLimitSetAtr() == 1) && (itemRangeSet.errorLowerLimitSetAtr() == 1)
                            && (itemRangeSet.errorUpRangeVal() != null) && (itemRangeSet.errorLoRangeVal() != null)
                            && (parseInt(itemRangeSet.errorUpRangeVal().toString(), 10) <= parseInt(itemRangeSet.errorLoRangeVal().toString(), 10))) {
                        $('#C2_15').ntsError('set', { messageId: "MsgQ_1" });
                    }
                    
                    if((itemRangeSet.alarmUpperLimitSetAtr() == 1) && ((itemRangeSet.alarmUpRangeVal() == null) || (itemRangeSet.alarmUpRangeVal().toString() == ""))) {
                        $('#C2_19').ntsError('set', { messageId: "MsgQ_16" });
                    }
                    
                    if((itemRangeSet.alarmLowerLimitSetAtr() == 1) && ((itemRangeSet.alarmLoRangeVal() == null) || (itemRangeSet.alarmLoRangeVal().toString() == ""))) {
                        $('#C2_22').ntsError('set', { messageId: "MsgQ_17" });
                    }
                    
                    if((itemRangeSet.alarmUpperLimitSetAtr() == 1) && (itemRangeSet.alarmLowerLimitSetAtr() == 1)
                            && (itemRangeSet.alarmUpRangeVal() != null) && (itemRangeSet.alarmLoRangeVal() != null)
                            && (parseInt(itemRangeSet.alarmUpRangeVal().toString(), 10) <= parseInt(itemRangeSet.alarmLoRangeVal().toString(), 10))) {
                        $('#C2_22').ntsError('set', { messageId: "MsgQ_2" });
                    }

                    $(".check-validate").trigger("validate");

                    // clear rangeSet validate if disable
                    if(!itemRangeSet.errorUpperLimitSettingAtrCus()) {
                        $('#C2_12').ntsError('clear');
                    }
                    if(!itemRangeSet.errorLowerLimitSettingAtrCus()) {
                        $('#C2_15').ntsError('clear');
                    }
                    if(!itemRangeSet.alarmUpperLimitSettingAtrCus()) {
                        $('#C2_19').ntsError('clear');
                    }
                    if(!itemRangeSet.alarmLowerLimitSettingAtrCus()) {
                        $('#C2_22').ntsError('clear');
                    }
                } else if(categoryAtr == model.CategoryAtr.ATTEND_ITEM) {
                    itemRangeSet = self.statementItemDataSelected().timeItemSet();

                    if(itemRangeSet.timeCountAtr() == 0) {
                        if ((itemRangeSet.errorUpperLimitSetAtr() == 1) && ((itemRangeSet.errorUpRangeValTime() == null) || (itemRangeSet.errorUpRangeValTime().toString() == ""))) {
                            $('#E1_20').ntsError('set', {messageId: "MsgQ_14"});
                        }

                        if ((itemRangeSet.errorLowerLimitSetAtr() == 1) && ((itemRangeSet.errorLoRangeValTime() == null) || (itemRangeSet.errorLoRangeValTime().toString() == ""))) {
                            $('#E1_23').ntsError('set', {messageId: "MsgQ_15"});
                        }

                        if ((itemRangeSet.errorUpperLimitSetAtr() == 1) && (itemRangeSet.errorLowerLimitSetAtr() == 1)
                            && (itemRangeSet.errorUpRangeValTime() != null) && (itemRangeSet.errorLoRangeValTime() != null)
                            && (parseInt(itemRangeSet.errorUpRangeValTime().toString(), 10) <= parseInt(itemRangeSet.errorLoRangeValTime().toString(), 10))) {
                            $('#E1_23').ntsError('set', {messageId: "MsgQ_1"});
                        }

                        if ((itemRangeSet.alarmUpperLimitSetAtr() == 1) && ((itemRangeSet.alarmUpRangeValTime() == null) || (itemRangeSet.alarmUpRangeValTime().toString() == ""))) {
                            $('#E1_27').ntsError('set', {messageId: "MsgQ_16"});
                        }

                        if ((itemRangeSet.alarmLowerLimitSetAtr() == 1) && ((itemRangeSet.alarmLoRangeValTime() == null) || (itemRangeSet.alarmLoRangeValTime().toString() == ""))) {
                            $('#E1_30').ntsError('set', {messageId: "MsgQ_17"});
                        }

                        if ((itemRangeSet.alarmUpperLimitSetAtr() == 1) && (itemRangeSet.alarmLowerLimitSetAtr() == 1)
                            && (itemRangeSet.alarmUpRangeValTime() != null) && (itemRangeSet.alarmLoRangeValTime() != null)
                            && (parseInt(itemRangeSet.alarmUpRangeValTime().toString(), 10) <= parseInt(itemRangeSet.alarmLoRangeValTime().toString(), 10))) {
                            $('#E1_30').ntsError('set', {messageId: "MsgQ_2"});
                        }
                    } else {
                        if ((itemRangeSet.errorUpperLimitSetAtr() == 1) && ((itemRangeSet.errorUpRangeValNum() == null) || (itemRangeSet.errorUpRangeValNum().toString() == ""))) {
                            $('#E1_20').ntsError('set', {messageId: "MsgQ_14"});
                        }

                        if ((itemRangeSet.errorLowerLimitSetAtr() == 1) && ((itemRangeSet.errorLoRangeValNum() == null) || (itemRangeSet.errorLoRangeValNum().toString() == ""))) {
                            $('#E1_23').ntsError('set', {messageId: "MsgQ_15"});
                        }

                        if ((itemRangeSet.errorUpperLimitSetAtr() == 1) && (itemRangeSet.errorLowerLimitSetAtr() == 1)
                            && (itemRangeSet.errorUpRangeValNum() != null) && (itemRangeSet.errorLoRangeValNum() != null)
                            && (parseInt(itemRangeSet.errorUpRangeValNum().toString(), 10) <= parseInt(itemRangeSet.errorLoRangeValNum().toString(), 10))) {
                            $('#E1_23').ntsError('set', {messageId: "MsgQ_1"});
                        }

                        if ((itemRangeSet.alarmUpperLimitSetAtr() == 1) && ((itemRangeSet.alarmUpRangeValNum() == null) || (itemRangeSet.alarmUpRangeValNum().toString() == ""))) {
                            $('#E1_27').ntsError('set', {messageId: "MsgQ_16"});
                        }

                        if ((itemRangeSet.alarmLowerLimitSetAtr() == 1) && ((itemRangeSet.alarmLoRangeValNum() == null) || (itemRangeSet.alarmLoRangeValNum().toString() == ""))) {
                            $('#E1_30').ntsError('set', {messageId: "MsgQ_17"});
                        }

                        if ((itemRangeSet.alarmUpperLimitSetAtr() == 1) && (itemRangeSet.alarmLowerLimitSetAtr() == 1)
                            && (itemRangeSet.alarmUpRangeValNum() != null) && (itemRangeSet.alarmLoRangeValNum() != null)
                            && (parseInt(itemRangeSet.alarmUpRangeValNum().toString(), 10) <= parseInt(itemRangeSet.alarmLoRangeValNum().toString(), 10))) {
                            $('#E1_30').ntsError('set', {messageId: "MsgQ_2"});
                        }
                    }

                    $(".check-validate").trigger("validate");

                    // clear rangeSet validate if disable
                    if(!itemRangeSet.errorUpperLimitSettingAtrCus()) {
                        $('#E1_20').ntsError('clear');
                    }
                    if(!itemRangeSet.errorLowerLimitSettingAtrCus()) {
                        $('#E1_23').ntsError('clear');
                    }
                    if(!itemRangeSet.alarmUpperLimitSettingAtrCus()) {
                        $('#E1_27').ntsError('clear');
                    }
                    if(!itemRangeSet.alarmLowerLimitSettingAtrCus()) {
                        $('#E1_30').ntsError('clear');
                    }
                }
                
                if(!nts.uk.ui.errors.hasError()) {
                    let oldKey = self.statementItemDataSelected().key;
                    let command = ko.toJS(self.statementItemDataSelected);
                    delete command.setBreakdownItem;
                    delete command.setValidity;
                    delete command.paymentItemSet.screenModel;
                    delete command.paymentItemSet.setTaxExemptionLimit;
                    delete command.screenModel;
                    
                    if(self.statementItemDataSelected().checkCreate()) {
                        oldKey = self.statementItemDataSelected().statementItem().categoryAtr().toString() + self.statementItemDataSelected().statementItem().itemNameCd();
                    }

                    // clear all tax value if not visible
                    if((command.paymentItemSet.taxAtr == model.TaxAtr.LIMIT_TAX_EXEMPTION) ||
                        (command.paymentItemSet.taxAtr == model.TaxAtr.LIMIT_TAX_EXEMPTION.toString()) ||
                        (command.paymentItemSet.taxAtr == model.TaxAtr.COMMUTING_EXPENSES_MANUAL) ||
                        (command.paymentItemSet.taxAtr == model.TaxAtr.COMMUTING_EXPENSES_MANUAL.toString())) {

                        if((command.paymentItemSet.limitAmountAtr != model.LimitAmountClassification.FIXED_AMOUNT) &&
                            (command.paymentItemSet.limitAmountAtr != model.LimitAmountClassification.FIXED_AMOUNT.toString())) {
                            command.paymentItemSet.limitAmount = null;
                        }

                        if((command.paymentItemSet.limitAmountAtr != model.LimitAmountClassification.TAX_EXEMPTION_LIMIT_MASTER) &&
                            (command.paymentItemSet.limitAmountAtr != model.LimitAmountClassification.TAX_EXEMPTION_LIMIT_MASTER.toString())) {
                            command.paymentItemSet.taxLimitAmountCode = null;
                        }
                    } else {
                        command.paymentItemSet.limitAmountAtr = null;
                        command.paymentItemSet.limitAmount = null;
                        command.paymentItemSet.taxLimitAmountCode = null;
                    }

                    // clear phần thập phân
                    if((command.paymentItemSet.limitAmountAtr == model.LimitAmountClassification.FIXED_AMOUNT) && (command.paymentItemSet.limitAmount != null)) {
                        command.paymentItemSet.limitAmount = clearDecimal(command.paymentItemSet.limitAmount.toString());
                    }

                    // clear phần thập phân
                    if(categoryAtr == model.CategoryAtr.PAYMENT_ITEM) {
                        if ((command.paymentItemSet.errorUpperLimitSetAtr == 1) && (command.paymentItemSet.errorUpRangeVal != null)) {
                            command.paymentItemSet.errorUpRangeVal = clearDecimal(command.paymentItemSet.errorUpRangeVal.toString());
                        }

                        if ((command.paymentItemSet.errorLowerLimitSetAtr == 1) && (command.paymentItemSet.errorLoRangeVal != null)) {
                            command.paymentItemSet.errorLoRangeVal = clearDecimal(command.paymentItemSet.errorLoRangeVal.toString());
                        }

                        if ((command.paymentItemSet.alarmUpperLimitSetAtr == 1) && (command.paymentItemSet.alarmUpRangeVal != null)) {
                            command.paymentItemSet.alarmUpRangeVal = clearDecimal(command.paymentItemSet.alarmUpRangeVal.toString());
                        }

                        if ((command.paymentItemSet.alarmLowerLimitSetAtr == 1) && (command.paymentItemSet.alarmLoRangeVal != null)) {
                            command.paymentItemSet.alarmLoRangeVal = clearDecimal(command.paymentItemSet.alarmLoRangeVal.toString());
                        }
                    }

                    // clear phần thập phân
                    if(categoryAtr == model.CategoryAtr.DEDUCTION_ITEM) {
                        if ((command.deductionItemSet.errorUpperLimitSetAtr == 1) && (command.deductionItemSet.errorUpRangeVal != null)) {
                            command.deductionItemSet.errorUpRangeVal = clearDecimal(command.deductionItemSet.errorUpRangeVal.toString());
                        }

                        if ((command.deductionItemSet.errorLowerLimitSetAtr == 1) && (command.deductionItemSet.errorLoRangeVal != null)) {
                            command.deductionItemSet.errorLoRangeVal = clearDecimal(command.deductionItemSet.errorLoRangeVal.toString());
                        }

                        if ((command.deductionItemSet.alarmUpperLimitSetAtr == 1) && (command.deductionItemSet.alarmUpRangeVal != null)) {
                            command.deductionItemSet.alarmUpRangeVal = clearDecimal(command.deductionItemSet.alarmUpRangeVal.toString());
                        }

                        if ((command.deductionItemSet.alarmLowerLimitSetAtr == 1) && (command.deductionItemSet.alarmLoRangeVal != null)) {
                            command.deductionItemSet.alarmLoRangeVal = clearDecimal(command.deductionItemSet.alarmLoRangeVal.toString());
                        }
                    }
                    
                    // clear rangeSet value if disable
                    if(!itemRangeSet.errorUpperLimitSettingAtrCus()) {
                        command.paymentItemSet.errorUpRangeVal = null;
                        command.deductionItemSet.errorUpRangeVal = null;
                        command.timeItemSet.errorUpRangeValNum = null;
                        command.timeItemSet.errorUpRangeTime = null;
                    }
                    if(!itemRangeSet.errorLowerLimitSettingAtrCus()) {
                        command.paymentItemSet.errorLoRangeVal = null;
                        command.deductionItemSet.errorLoRangeVal = null;
                        command.timeItemSet.errorLoRangeValNum = null;
                        command.timeItemSet.errorLoRangeValTime = null;
                    }
                    if(!itemRangeSet.alarmUpperLimitSettingAtrCus()) {
                        command.paymentItemSet.alarmUpRangeVal = null;
                        command.deductionItemSet.alarmUpRangeVal = null;
                        command.timeItemSet.alarmUpRangeValNum = null;
                        command.timeItemSet.alarmUpRangeValTime = null;
                    }
                    if(!itemRangeSet.alarmLowerLimitSettingAtrCus()) {
                        command.paymentItemSet.alarmLoRangeVal = null;
                        command.deductionItemSet.alarmLoRangeVal = null;
                        command.timeItemSet.alarmLoRangeValNum = null;
                        command.timeItemSet.alarmLoRangeValTime = null;
                    }

                    // clear rangeSet of attend_item value if other mode
                    if(categoryAtr == model.CategoryAtr.ATTEND_ITEM) {
                        if((command.timeItemSet.timeCountAtr == 0) || (command.timeItemSet.timeCountAtr == '0')) {
                            command.timeItemSet.errorUpRangeValNum = null;
                            command.timeItemSet.errorLoRangeValNum = null;
                            command.timeItemSet.alarmUpRangeValNum = null;
                            command.timeItemSet.alarmLoRangeValNum = null;
                        } else {
                            command.timeItemSet.errorUpRangeValTime = null;
                            command.timeItemSet.errorLoRangeValTime = null;
                            command.timeItemSet.alarmUpRangeValTime = null;
                            command.timeItemSet.alarmLoRangeValTime = null;
                        }
                    }

                    // Optional fields: set null value if isEmpty
                    if (_.isEmpty(command.paymentItemSet.note)) {
                        command.paymentItemSet.note = null;
                    }
                    if (_.isEmpty(command.deductionItemSet.note)) {
                        command.deductionItemSet.note = null;
                    }
                    if (_.isEmpty(command.timeItemSet.note)) {
                        command.timeItemSet.note = null;
                    }

                    // set value cho ValueAtr
                    if((command.statementItem.categoryAtr == model.CategoryAtr.ATTEND_ITEM) || (command.statementItem.categoryAtr == model.CategoryAtr.ATTEND_ITEM.toString())) {
                        if((command.timeItemSet.timeCountAtr == model.TimeCountAtr.TIMES) || (command.timeItemSet.timeCountAtr == model.TimeCountAtr.TIMES.toString())) {
                            command.statementItem.valueAtr = 3;
                        } else {
                            command.statementItem.valueAtr = 2;
                        }
                    }
                    
                    block.invisible();
                    service.registerStatementItemData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            self.loadListData().done(function() {
                                let matchKey = _.filter(self.statementItemDataList(), function(o) {
                                    return oldKey == o.key;
                                });
                                
                                if(matchKey.length > 0) {
                                    self.currentKey(oldKey);
                                    self.currentKey.valueHasMutated();
                                } else if(self.statementItemDataList().length > 0) {
                                    self.currentKey(self.statementItemDataList()[0].key);
                                }
                            });
                        });
                    }).fail(err => {
                        block.clear();
                        
                        if(err.messageId == "Msg_3") {
                            $('#B3_2').ntsError('set', { messageId: "Msg_3" });
                            $("#B3_2").focus();
                        }
                        
                        if(err.messageId == "Msg_358") {
                            $('#B3_3').ntsError('set', { messageId: "Msg_358" });
                            $("#B3_3").focus();
                        }
                    });
                }
            }
            
            public copy(): void {
                
            }
            
            public deleteItem(): void {
                let self = this;
                let nextKey = self.getNextKey();
                
                dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let command = ko.toJS(self.statementItemDataSelected);
                    delete command.setBreakdownItem;
                    delete command.setValidity;
                    delete command.paymentItemSet.screenModel;
                    delete command.paymentItemSet.setTaxExemptionLimit;
                    delete command.screenModel;
                    
                    block.invisible();
                    service.removeStatementItemData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_16" }).then(() => {
                            self.loadListData().done(function() {
                                if(self.statementItemDataList().length == 0) {
                                    self.create();
                                } else if(nextKey != null) {
                                    self.currentKey(nextKey);
                                }
                            });
                        });
                    }).fail(err => {
                        block.clear();
                        $("#B3_3").focus();
                    });
                })
            }
            
            public getNextKey(): string {
                let self = this;
                let nextItem: string = null;
                let array: Array<string> = self.statementItemDataList().map(x => x.key);
                let value = self.statementItemDataSelected().key;
                
                if(array.length > 0) {
                    let index = array.indexOf(value);
                    if(index >= 0) {
                        if(index < (array.length - 1)) {
                            nextItem = array[index + 1];
                        } else if(index > 0){
                            nextItem = array[index - 1];
                        }
                    }
                }
                
                return nextItem;
            }
            
            public outputExcel(): void {
                
            }
            
            public modifyLog(): void {
                
            }
            
            public registerPrintingName(): void {
                let self = this;
                
                nts.uk.ui.windows.sub.modal('../j/index.xhtml').onClosed(() => {
                    if(self.statementItemDataSelected().checkCreate()) {
                        $("#B3_2").focus();
                    } else {
                        $("#B3_3").focus();
                    }
                });
            }
            
        }
        
        class StatementItemData {
            cid: string;
            key: string;
            statementItem: KnockoutObservable<StatementItem>;
            statementItemName: KnockoutObservable<StatementItemName>;
            paymentItemSet: KnockoutObservable<PaymentItemSet>;
            statementItemDisplaySet: KnockoutObservable<StatementItemDisplaySet>;
            deductionItemSet: KnockoutObservable<DeductionItemSet>;
            timeItemSet: KnockoutObservable<TimeItemSet>;
            
            // check SetValidityPeriodCycle
            isSetValidity: KnockoutObservable<boolean> = ko.observable(false);
            
            // check BreakdownItemSet
            isSetBreakdownItem: KnockoutObservable<boolean> = ko.observable(false);
            
            // mode of screen
            checkCreate: KnockoutObservable<boolean> = ko.observable(true);
            
            screenModel: ScreenModel;
            
            constructor(data: IStatementItemData, screenModel: ScreenModel) {
                let self = this;
                self.screenModel = screenModel;
                
                if (data) {
                    self.cid = data.cid;
                    self.key = data.key;
                    self.statementItem = ko.observable(new StatementItem(data.statementItem));
                    self.statementItemName = ko.observable(new StatementItemName(data.statementItemName));
                    self.paymentItemSet = ko.observable(new PaymentItemSet(data.paymentItemSet, screenModel));
                    self.statementItemDisplaySet = ko.observable(new StatementItemDisplaySet(data.statementItemDisplaySet));
                    self.deductionItemSet = ko.observable(new DeductionItemSet(data.deductionItemSet, self));
                    self.timeItemSet = ko.observable(new TimeItemSet(data.timeItemSet, self));
                    
                    if(data.validityPeriodAndCycleSet) {
                        self.isSetValidity(true);
                    }
                    
                    if(data.breakdownItemSet && (data.breakdownItemSet.length > 0)) {
                        self.isSetBreakdownItem(true);
                    }
                    
                    self.checkCreate = ko.observable(false);
                } else {
                    self.cid = "";
                    self.key = null;
                    self.statementItem = ko.observable(new StatementItem(null));
                    self.statementItemName = ko.observable(new StatementItemName(null));
                    self.paymentItemSet = ko.observable(new PaymentItemSet(null, screenModel));
                    self.statementItemDisplaySet = ko.observable(new StatementItemDisplaySet(null));
                    self.deductionItemSet = ko.observable(new DeductionItemSet(null, self));
                    self.timeItemSet = ko.observable(new TimeItemSet(null, self));
                    
                    self.checkCreate = ko.observable(true);
                }
                
                nts.uk.ui.errors.clearAll();
            }
            
            public setBreakdownItem(): void {
                let self = this;

                setShared("QMM012_B_TO_I_PARAMS", {categoryAtr: self.statementItem().categoryAtr(), itemNameCd: self.statementItem().itemNameCd(), categoryName: self.statementItem().categoryName()});

                nts.uk.ui.windows.sub.modal('../i/index.xhtml').onClosed(() => {
                    let isSetting = getShared("QMM012_I_IS_SETTING");
                    self.isSetBreakdownItem(isSetting);
                    
                    $("#C3_8").focus();
                });
            }
            
            public setValidity(): void {
                let self = this;

                setShared("QMM012_B_TO_H_SALARY_ITEM_ID", {
                    categoryAtr: self.statementItem().categoryAtr(),
                    itemNameCd: self.statementItem().itemNameCd(),
                    name: self.statementItemName().name(),
                });

                nts.uk.ui.windows.sub.modal('../h/index.xhtml').onClosed(() => {
                    let isSetting = getShared("QMM012_H_IS_SETTING");
                    
                    if(isSetting && (isSetting.exitStatus == 1)) {
                        self.isSetValidity(isSetting);
                    }
                    
                    $("#C3_2").focus();
                });
            }
        }
        
        class StatementItem {
            categoryAtr: KnockoutObservable<number>;
            categoryName: KnockoutObservable<string>;
            itemNameCd: KnockoutObservable<string>;
            defaultAtr: number;
            valueAtr: KnockoutObservable<number>;
            deprecatedAtr: KnockoutObservable<number>;
            socialInsuaEditableAtr: KnockoutObservable<number>;
            intergrateCd: KnockoutObservable<string>;
            
            // Custom deprecatedAtr from number to string
            deprecatedAtrCustom: KnockoutObservable<boolean>;
            
            constructor(data: IStatementItem) {
                let self = this;
                
                if (data) {
                    self.categoryAtr = ko.observable(data.categoryAtr);              
                    self.categoryName =  ko.observable(model.getCategoryAtrText(data.categoryAtr));
                    self.itemNameCd = ko.observable(data.itemNameCd);
                    self.defaultAtr = data.defaultAtr;
                    self.valueAtr = ko.observable(data.valueAtr);
                    self.deprecatedAtr = ko.observable(data.deprecatedAtr);
                    self.socialInsuaEditableAtr = ko.observable(data.socialInsuaEditableAtr);
                    self.intergrateCd = ko.observable(data.intergrateCd);
                } else {
                    self.categoryAtr = ko.observable(null);
                    self.categoryName =  ko.observable(null);
                    self.itemNameCd = ko.observable(null);
                    self.defaultAtr = 0;
                    self.valueAtr = ko.observable(null);
                    self.deprecatedAtr = ko.observable(0);
                    self.socialInsuaEditableAtr = ko.observable(null);
                    self.intergrateCd = ko.observable(null);
                }
                
                self.deprecatedAtrCustom = ko.observable(self.deprecatedAtr() == 1);
                
                self.deprecatedAtrCustom.subscribe(x => {
                    if (x) {
                        self.deprecatedAtr(1);
                    } else {
                        self.deprecatedAtr(0);
                    }
                });
                
                self.categoryAtr.subscribe(x => {
                    if (x != null) {
                        self.categoryName(model.getCategoryAtrText(x));
                    }
                });
            }
        }
        
        class StatementItemName {
            name: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            otherLanguageName: KnockoutObservable<string>;
            englishName: KnockoutObservable<string>;
            
            constructor(data: IStatementItemName) {
                let self = this;
                
                if (data) {
                    self.name = ko.observable(data.name);
                    self.shortName = ko.observable(data.shortName);
                    self.otherLanguageName = ko.observable(data.otherLanguageName);
                    self.englishName = ko.observable(data.englishName);
                } else {
                    self.name = ko.observable(null);
                    self.shortName = ko.observable(null);
                    self.otherLanguageName = ko.observable(null);
                    self.englishName = ko.observable(null);
                }
            }
        }
        
        class PaymentItemSet {
            breakdownItemUseAtr: KnockoutObservable<number>;
            laborInsuranceCategory: KnockoutObservable<number>;
            settingAtr: KnockoutObservable<number>;
            everyoneEqualSet: KnockoutObservable<number>;
            monthlySalary: KnockoutObservable<number>;
            hourlyPay: KnockoutObservable<number>;
            dayPayee: KnockoutObservable<number>;
            monthlySalaryPerday: KnockoutObservable<number>;
            averageWageAtr: KnockoutObservable<number>;
            socialInsuranceCategory: KnockoutObservable<number>;
            taxAtr: KnockoutObservable<number>;
            taxableAmountAtr: KnockoutObservable<number>;
            limitAmount: KnockoutObservable<number>;
            limitAmountAtr: KnockoutObservable<number>;
            taxLimitAmountCode: KnockoutObservable<string>;
            taxExemptionName: KnockoutObservable<string>;
            errorUpperLimitSetAtr: KnockoutObservable<number>;
            errorUpRangeVal: KnockoutObservable<number>;
            errorLowerLimitSetAtr: KnockoutObservable<number>;
            errorLoRangeVal: KnockoutObservable<number>;
            alarmUpperLimitSetAtr: KnockoutObservable<number>;
            alarmUpRangeVal: KnockoutObservable<number>;
            alarmLowerLimitSetAtr: KnockoutObservable<number>;
            alarmLoRangeVal: KnockoutObservable<number>;
            note: KnockoutObservable<string>;
            
            // category comboBox
            taxList: KnockoutObservableArray<model.ItemModel>;
            
            // Covered switch button
            coveredList: KnockoutObservableArray<model.ItemModel>;
            
            // settingAtr radio button
            settingAtrList: KnockoutObservableArray<model.BoxModel>;
            
            // breakdownItemUse switch button
            breakdownItemUseList: KnockoutObservableArray<model.ItemModel>;
            
            // limitAmountClassification  switch button
            limitAmountList: KnockoutObservableArray<model.ItemModel>;
            
            // taxableAmountClassification  switch button
            taxableAmountList: KnockoutObservableArray<model.BoxModel>;

            // option for rangeValue
            numberEditorOption: any;

            // number -> boolean for checkbox
            errorUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            errorLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmLowerLimitSettingAtrCus: KnockoutObservable<boolean>;

            screenModel: ScreenModel;
            
            constructor(data: IPaymentItemSet, screenModel: ScreenModel) {
                let self = this;
                self.screenModel = screenModel;

                self.numberEditorOption = {
                    grouplength: 3,
                    width: "150px",
                    textalign: "right",
                    currencyformat: "JPY"
                };
                
                self.taxList = ko.observableArray([
                    new model.ItemModel(model.TaxAtr.TAXATION.toString(), getText('Enum_TaxAtr_TAXATION')),
                    new model.ItemModel(model.TaxAtr.LIMIT_TAX_EXEMPTION.toString(), getText('Enum_TaxAtr_LIMIT_TAX_EXEMPTION')),
                    new model.ItemModel(model.TaxAtr.NO_LIMIT_TAX_EXEMPTION.toString(), getText('Enum_TaxAtr_NO_LIMIT_TAX_EXEMPTION')),
                    new model.ItemModel(model.TaxAtr.COMMUTING_EXPENSES_MANUAL.toString(), getText('Enum_TaxAtr_COMMUTING_EXPENSES_MANUAL')),
                    new model.ItemModel(model.TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER.toString(), getText('Enum_TaxAtr_COMMUTING_EXPENSES_USING_COMMUTER'))
                ]);
                
                self.coveredList = ko.observableArray([
                    new model.ItemModel(model.CoveredAtr.COVERED.toString(), getText('QMM012_41')),
                    new model.ItemModel(model.CoveredAtr.NOT_COVERED.toString(), getText('QMM012_42'))
                ]);
                
                self.settingAtrList = ko.observableArray([
                    new model.BoxModel(model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS, getText('QMM012_45')),
                    new model.BoxModel(model.SettingClassification.DESIGNATE_FOR_EACH_SALARY_CONTRACT_TYPE, getText('QMM012_46'))
                ]);
                
                self.breakdownItemUseList = ko.observableArray([
                    new model.ItemModel(model.BreakdownItemUseAtr.USE.toString(), getText('QMM012_72')),
                    new model.ItemModel(model.BreakdownItemUseAtr.NOT_USE.toString(), getText('QMM012_73'))
                ]);
                
                self.limitAmountList = ko.observableArray([
                    new model.ItemModel(model.LimitAmountClassification.FIXED_AMOUNT.toString(), getText('QMM012_77')),
                    new model.ItemModel(model.LimitAmountClassification.TAX_EXEMPTION_LIMIT_MASTER.toString(), getText('QMM012_78')),
                    new model.ItemModel(model.LimitAmountClassification.REFER_TO_PERSONAL_TRANSPORTATION_LIMIT.toString(), getText('QMM012_79')),
                    new model.ItemModel(model.LimitAmountClassification.REFER_TO_PERSONAL_TRANSPORTATION_TOOL_LIMIT.toString(), getText('QMM012_80'))
                ]);
                
                self.taxableAmountList = ko.observableArray([
                    new model.BoxModel(model.TaxableAmountClassification.OVERDRAFT_TAXATION, getText('QMM012_83')),
                    new model.BoxModel(model.TaxableAmountClassification.FULL_TAXATION, getText('QMM012_84'))
                ]);
                
                if (data) {
                    self.breakdownItemUseAtr = ko.observable(data.breakdownItemUseAtr);
                    self.laborInsuranceCategory = ko.observable(data.laborInsuranceCategory);
                    self.settingAtr = ko.observable(data.settingAtr);
                    self.everyoneEqualSet = ko.observable(data.everyoneEqualSet);
                    self.monthlySalary = ko.observable(data.monthlySalary);
                    self.hourlyPay = ko.observable(data.hourlyPay);
                    self.dayPayee = ko.observable(data.dayPayee);
                    self.monthlySalaryPerday = ko.observable(data.monthlySalaryPerday);
                    self.averageWageAtr = ko.observable(data.averageWageAtr);
                    self.socialInsuranceCategory = ko.observable(data.socialInsuranceCategory);
                    self.taxAtr = ko.observable(data.taxAtr);
                    self.taxableAmountAtr = ko.observable(data.taxableAmountAtr);
                    self.limitAmount = ko.observable(data.limitAmount);
                    self.limitAmountAtr = ko.observable((data.limitAmountAtr != null) ? data.limitAmountAtr : model.LimitAmountClassification.FIXED_AMOUNT);
                    self.taxLimitAmountCode = ko.observable(data.taxLimitAmountCode);
                    self.taxExemptionName = ko.observable(data.taxExemptionName);
                    self.errorUpperLimitSetAtr= ko.observable(data.errorUpperLimitSetAtr);
                    self.errorUpRangeVal= ko.observable(data.errorUpRangeVal);
                    self.errorLowerLimitSetAtr= ko.observable(data.errorLowerLimitSetAtr);
                    self.errorLoRangeVal= ko.observable(data.errorLoRangeVal);
                    self.alarmUpperLimitSetAtr= ko.observable(data.alarmUpperLimitSetAtr);
                    self.alarmUpRangeVal= ko.observable(data.alarmUpRangeVal);
                    self.alarmLowerLimitSetAtr= ko.observable(data.alarmLowerLimitSetAtr);
                    self.alarmLoRangeVal= ko.observable(data.alarmLoRangeVal);
                    self.note = ko.observable(data.note);
                } else {
                    self.breakdownItemUseAtr = ko.observable(model.BreakdownItemUseAtr.NOT_USE);
                    self.laborInsuranceCategory = ko.observable(model.CoveredAtr.COVERED);
                    self.settingAtr = ko.observable(model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS);
                    self.everyoneEqualSet = ko.observable(model.CoveredAtr.COVERED);
                    self.monthlySalary = ko.observable(model.CoveredAtr.COVERED);
                    self.hourlyPay = ko.observable(model.CoveredAtr.COVERED);
                    self.dayPayee = ko.observable(model.CoveredAtr.COVERED);
                    self.monthlySalaryPerday = ko.observable(model.CoveredAtr.COVERED);
                    self.averageWageAtr = ko.observable(model.CoveredAtr.COVERED);
                    self.socialInsuranceCategory = ko.observable(model.CoveredAtr.COVERED);
                    self.taxAtr = ko.observable(model.TaxAtr.TAXATION);
                    self.taxableAmountAtr = ko.observable(model.TaxableAmountClassification.OVERDRAFT_TAXATION);
                    self.limitAmount = ko.observable(null);
                    self.limitAmountAtr = ko.observable(model.LimitAmountClassification.FIXED_AMOUNT);
                    self.taxLimitAmountCode = ko.observable(null);
                    self.taxExemptionName = ko.observable(null);
                    self.errorUpperLimitSetAtr= ko.observable(null);
                    self.errorUpRangeVal= ko.observable(null);
                    self.errorLowerLimitSetAtr= ko.observable(null);
                    self.errorLoRangeVal= ko.observable(null);
                    self.alarmUpperLimitSetAtr= ko.observable(null);
                    self.alarmUpRangeVal= ko.observable(null);
                    self.alarmLowerLimitSetAtr= ko.observable(null);
                    self.alarmLoRangeVal= ko.observable(null);
                    self.note = ko.observable(null);
                }

                self.errorUpperLimitSettingAtrCus = ko.observable(self.errorUpperLimitSetAtr() == 1);
                self.errorLowerLimitSettingAtrCus = ko.observable(self.errorLowerLimitSetAtr() == 1);
                self.alarmUpperLimitSettingAtrCus = ko.observable(self.alarmUpperLimitSetAtr() == 1);
                self.alarmLowerLimitSettingAtrCus = ko.observable(self.alarmLowerLimitSetAtr() == 1);
                
                // mapping 2 thuộc tính của 2 domain từ cùng 1 item
                self.breakdownItemUseAtr.subscribe(x => {
                    if(x != null) {
                        screenModel.statementItemDataSelected().deductionItemSet().breakdownItemUseAtr(x);
                    }
                });
                
                // mapping 3 thuộc tính của 3 domain từ cùng 1 item
                self.note.subscribe(x => {
                    if(x != null) {
                        screenModel.statementItemDataSelected().deductionItemSet().note(x);
                        screenModel.statementItemDataSelected().timeItemSet().note(x);
                    }
                });
                
                self.limitAmountAtr.subscribe(x => {
                    if((x != null) && (x != model.LimitAmountClassification.FIXED_AMOUNT)) {
                        $('#C4_8').ntsError('clear');
                    }
                });
                
                self.taxAtr.subscribe(x => {
                    if((x != null) && (x != model.TaxAtr.LIMIT_TAX_EXEMPTION) && (x != model.TaxAtr.COMMUTING_EXPENSES_MANUAL)) {
                        $('#C4_8').ntsError('clear');
                        self.limitAmount(null);
                    }
                });

                self.errorUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorUpperLimitSetAtr(1);
                    } else {
                        self.errorUpperLimitSetAtr(0);
                        $('#C2_12').ntsError('clear');
                    }
                });

                self.errorLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorLowerLimitSetAtr(1);
                    } else {
                        self.errorLowerLimitSetAtr(0);
                        $('#C2_15').ntsError('clear');
                    }
                });

                self.alarmUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmUpperLimitSetAtr(1);
                    } else {
                        self.alarmUpperLimitSetAtr(0);
                        $('#C2_19').ntsError('clear');
                    }
                });

                self.alarmLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmLowerLimitSetAtr(1);
                    } else {
                        self.alarmLowerLimitSetAtr(0);
                        $('#C2_22').ntsError('clear');
                    }
                });
            }
            
            public setTaxExemptionLimit(): void {
                let self = this;

                setShared("QMM012_B_TO_K_PARAMS", self.taxLimitAmountCode());

                nts.uk.ui.windows.sub.modal('../k/index.xhtml').onClosed(() => {
                    let data = getShared("QMM012_K_DATA");
                    
                    if(data && data.code) {
                        self.taxLimitAmountCode(data.code);
                        self.taxExemptionName(data.name);
                    }
                    
                    $("#C5_2").focus();
                });
            }
        }
        
        class StatementItemDisplaySet {
            zeroDisplayAtr: KnockoutObservable<number>;
            itemNameDisplay: KnockoutObservable<number>;
            
            // zeroDisplayAtrList switch button
            zeroDisplayAtrList: KnockoutObservableArray<model.ItemModel>;
            
            // number -> boolean for checkbox
            itemNameDisplayCustom: KnockoutObservable<boolean>;
            
            constructor(data: IStatementItemDisplaySet) {
                let self = this;
                
                self.zeroDisplayAtrList = ko.observableArray([
                    new model.ItemModel(model.Display.SHOW.toString(), getText('QMM012_53')),
                    new model.ItemModel(model.Display.NOT_SHOW.toString(), getText('QMM012_54'))
                ]);
                
                if (data) {
                    self.zeroDisplayAtr = ko.observable(data.zeroDisplayAtr);
                    self.itemNameDisplay = ko.observable(data.itemNameDisplay);
                } else {
                    self.zeroDisplayAtr = ko.observable(model.Display.SHOW);
                    self.itemNameDisplay = ko.observable(model.Display.NOT_SHOW);
                }
                
                self.itemNameDisplayCustom = ko.observable(self.itemNameDisplay() == 1);
                
                self.itemNameDisplayCustom.subscribe(x => {
                    if (x) {
                        self.itemNameDisplay(1);
                    } else {
                        self.itemNameDisplay(0);
                    }
                });
            }
        }
        
        class DeductionItemSet {
            deductionItemAtr: KnockoutObservable<number>;
            breakdownItemUseAtr: KnockoutObservable<number>;
            errorUpperLimitSetAtr: KnockoutObservable<number>;
            errorUpRangeVal: KnockoutObservable<number>;
            errorLowerLimitSetAtr: KnockoutObservable<number>;
            errorLoRangeVal: KnockoutObservable<number>;
            alarmUpperLimitSetAtr: KnockoutObservable<number>;
            alarmUpRangeVal: KnockoutObservable<number>;
            alarmLowerLimitSetAtr: KnockoutObservable<number>;
            alarmLoRangeVal: KnockoutObservable<number>;
            note: KnockoutObservable<string>;

            // option for rangeValue
            numberEditorOption: any;

            // deductionItemList switch button
            deductionItemList: KnockoutObservableArray<model.ItemModel>;

            // number -> boolean for checkbox
            errorUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            errorLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            
            constructor(data: IDeductionItemSet, parent: StatementItemData) {
                let self = this;

                self.numberEditorOption = {
                    grouplength: 3,
                    width: "150px",
                    textalign: "right",
                    currencyformat: "JPY"
                };
                
                self.deductionItemList = ko.observableArray([
                    new model.ItemModel(model.DeductionItemAtr.OPTIONAL_DEDUCTION_ITEM.toString(), getText('Enum_DeductionItemAtr_OPTIONAL_DEDUCTION_ITEM')),
                    new model.ItemModel(model.DeductionItemAtr.SOCIAL_INSURANCE_ITEM.toString(), getText('Enum_DeductionItemAtr_SOCIAL_INSURANCE_ITEM')),
                    new model.ItemModel(model.DeductionItemAtr.INCOME_TAX_ITEM.toString(), getText('Enum_DeductionItemAtr_INCOME_TAX_ITEM')),
                    new model.ItemModel(model.DeductionItemAtr.INHABITANT_TAX_ITEM.toString(), getText('Enum_DeductionItemAtr_INHABITANT_TAX_ITEM'))
                ]);
                
                if (data) {
                    self.deductionItemAtr = ko.observable(data.deductionItemAtr);
                    self.breakdownItemUseAtr = ko.observable(data.breakdownItemUseAtr);
                    self.errorUpperLimitSetAtr= ko.observable(data.errorUpperLimitSetAtr);
                    self.errorUpRangeVal= ko.observable(data.errorUpRangeVal);
                    self.errorLowerLimitSetAtr= ko.observable(data.errorLowerLimitSetAtr);
                    self.errorLoRangeVal= ko.observable(data.errorLoRangeVal);
                    self.alarmUpperLimitSetAtr= ko.observable(data.alarmUpperLimitSetAtr);
                    self.alarmUpRangeVal= ko.observable(data.alarmUpRangeVal);
                    self.alarmLowerLimitSetAtr= ko.observable(data.alarmLowerLimitSetAtr);
                    self.alarmLoRangeVal= ko.observable(data.alarmLoRangeVal);
                    self.note = ko.observable(data.note);

                    // show in screen because use same component
                    parent.paymentItemSet().breakdownItemUseAtr(data.breakdownItemUseAtr);
                    parent.paymentItemSet().note(data.note);
                } else {
                    self.deductionItemAtr = ko.observable(model.DeductionItemAtr.OPTIONAL_DEDUCTION_ITEM);
                    self.breakdownItemUseAtr = ko.observable(model.BreakdownItemUseAtr.NOT_USE);
                    self.errorUpperLimitSetAtr= ko.observable(null);
                    self.errorUpRangeVal= ko.observable(null);
                    self.errorLowerLimitSetAtr= ko.observable(null);
                    self.errorLoRangeVal= ko.observable(null);
                    self.alarmUpperLimitSetAtr= ko.observable(null);
                    self.alarmUpRangeVal= ko.observable(null);
                    self.alarmLowerLimitSetAtr= ko.observable(null);
                    self.alarmLoRangeVal= ko.observable(null);
                    self.note = ko.observable(null);
                }

                self.errorUpperLimitSettingAtrCus = ko.observable(self.errorUpperLimitSetAtr() == 1);
                self.errorLowerLimitSettingAtrCus = ko.observable(self.errorLowerLimitSetAtr() == 1);
                self.alarmUpperLimitSettingAtrCus = ko.observable(self.alarmUpperLimitSetAtr() == 1);
                self.alarmLowerLimitSettingAtrCus = ko.observable(self.alarmLowerLimitSetAtr() == 1);

                self.errorUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorUpperLimitSetAtr(1);
                    } else {
                        self.errorUpperLimitSetAtr(0);
                        $('#C2_12').ntsError('clear');
                    }
                });

                self.errorLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorLowerLimitSetAtr(1);
                    } else {
                        self.errorLowerLimitSetAtr(0);
                        $('#C2_15').ntsError('clear');
                    }
                });

                self.alarmUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmUpperLimitSetAtr(1);
                    } else {
                        self.alarmUpperLimitSetAtr(0);
                        $('#C2_19').ntsError('clear');
                    }
                });

                self.alarmLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmLowerLimitSetAtr(1);
                    } else {
                        self.alarmLowerLimitSetAtr(0);
                        $('#C2_22').ntsError('clear');
                    }
                });
            }
        }
        
        class TimeItemSet {
            averageWageAtr: KnockoutObservable<number>;
            workingDaysPerYear: KnockoutObservable<number>;
            timeCountAtr: KnockoutObservable<number>;
            errorUpperLimitSetAtr: KnockoutObservable<number>;
            errorUpRangeValTime: KnockoutObservable<number>;
            errorUpRangeValNum: KnockoutObservable<number>;
            errorLowerLimitSetAtr: KnockoutObservable<number>;
            errorLoRangeValTime: KnockoutObservable<number>;
            errorLoRangeValNum: KnockoutObservable<number>;
            alarmUpperLimitSetAtr: KnockoutObservable<number>;
            alarmUpRangeValTime: KnockoutObservable<number>;
            alarmUpRangeValNum: KnockoutObservable<number>;
            alarmLowerLimitSetAtr: KnockoutObservable<number>;
            alarmLoRangeValTime: KnockoutObservable<number>;
            alarmLoRangeValNum: KnockoutObservable<number>;
            note: KnockoutObservable<string>;
            parameterValue: number;

            // option for range time Value
            timeEditorOption: any;

            // option for range number Value
            numberEditorOption: any;
            
            // timeCountList switch button
            timeCountList: KnockoutObservableArray<model.ItemModel>;
            
            // coveredList switch button
            coveredList: KnockoutObservableArray<model.ItemModel>;

            // number -> boolean for checkbox
            errorUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            errorLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            
            constructor(data: ITimeItemSet, parent: StatementItemData) {
                let self = this;

                self.numberEditorOption = {
                    grouplength: 3,
                    decimallength: 2,
                    width: "150px",
                    textalign: "right"
                };

                self.timeEditorOption = {
                    width: "150px",
                    textalign: "right"
                };

                self.timeCountList = ko.observableArray([
                    new model.ItemModel(model.TimeCountAtr.TIME.toString(), getText('QMM012_88')),
                    new model.ItemModel(model.TimeCountAtr.TIMES.toString(), getText('QMM012_89'))
                ]);
                
                self.coveredList = ko.observableArray([
                    new model.ItemModel(model.CoveredAtr.COVERED.toString(), getText('QMM012_41')),
                    new model.ItemModel(model.CoveredAtr.NOT_COVERED.toString(), getText('QMM012_42'))
                ]);
                
                if (data) {
                    self.averageWageAtr = ko.observable(data.averageWageAtr);
                    self.workingDaysPerYear = ko.observable(data.workingDaysPerYear);
                    self.timeCountAtr = ko.observable(data.timeCountAtr);
                    self.errorUpperLimitSetAtr = ko.observable(data.errorUpperLimitSetAtr);
                    self.errorUpRangeValTime = ko.observable(data.errorUpRangeValTime);
                    self.errorUpRangeValNum = ko.observable(data.errorUpRangeValNum);
                    self.errorLowerLimitSetAtr = ko.observable(data.errorLowerLimitSetAtr);
                    self.errorLoRangeValTime = ko.observable(data.errorLoRangeValTime);
                    self.errorLoRangeValNum = ko.observable(data.errorLoRangeValNum);
                    self.alarmUpperLimitSetAtr = ko.observable(data.alarmUpperLimitSetAtr);
                    self.alarmUpRangeValTime = ko.observable(data.alarmUpRangeValTime);
                    self.alarmUpRangeValNum = ko.observable(data.alarmUpRangeValNum);
                    self.alarmLowerLimitSetAtr = ko.observable(data.alarmLowerLimitSetAtr);
                    self.alarmLoRangeValTime = ko.observable(data.alarmLoRangeValTime);
                    self.alarmLoRangeValNum = ko.observable(data.alarmLoRangeValNum);
                    self.note = ko.observable(data.note);
                    self.parameterValue = data.parameterValue;

                    // show in screen because use same component
                    parent.paymentItemSet().note(data.note);
                } else {
                    self.averageWageAtr = ko.observable(model.CoveredAtr.COVERED);
                    self.workingDaysPerYear = ko.observable(model.CoveredAtr.COVERED);
                    self.timeCountAtr = ko.observable(model.TimeCountAtr.TIME);
                    self.errorUpperLimitSetAtr = ko.observable(null);
                    self.errorUpRangeValTime = ko.observable(null);
                    self.errorUpRangeValNum = ko.observable(null);
                    self.errorLowerLimitSetAtr = ko.observable(null);
                    self.errorLoRangeValTime = ko.observable(null);
                    self.errorLoRangeValNum = ko.observable(null);
                    self.alarmUpperLimitSetAtr = ko.observable(null);
                    self.alarmUpRangeValTime = ko.observable(null);
                    self.alarmUpRangeValNum = ko.observable(null);
                    self.alarmLowerLimitSetAtr = ko.observable(null);
                    self.alarmLoRangeValTime = ko.observable(null);
                    self.alarmLoRangeValNum = ko.observable(null);
                    self.note = ko.observable(null);
                    self.parameterValue = 2;
                }

                self.errorUpperLimitSettingAtrCus = ko.observable(self.errorUpperLimitSetAtr() == 1);
                self.errorLowerLimitSettingAtrCus = ko.observable(self.errorLowerLimitSetAtr() == 1);
                self.alarmUpperLimitSettingAtrCus = ko.observable(self.alarmUpperLimitSetAtr() == 1);
                self.alarmLowerLimitSettingAtrCus = ko.observable(self.alarmLowerLimitSetAtr() == 1);

                self.errorUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorUpperLimitSetAtr(1);
                    } else {
                        self.errorUpperLimitSetAtr(0);
                        $('#E1_20').ntsError('clear');
                    }
                });

                self.errorLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorLowerLimitSetAtr(1);
                    } else {
                        self.errorLowerLimitSetAtr(0);
                        $('#E1_23').ntsError('clear');
                    }
                });

                self.alarmUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmUpperLimitSetAtr(1);
                    } else {
                        self.alarmUpperLimitSetAtr(0);
                        $('#E1_27').ntsError('clear');
                    }
                });

                self.alarmLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmLowerLimitSetAtr(1);
                    } else {
                        self.alarmLowerLimitSetAtr(0);
                        $('#E1_30').ntsError('clear');
                    }
                });

                self.timeCountAtr.subscribe(x => {
                    if (x != null) {
                        $('#E1_20').ntsError('clear');
                        $('#E1_23').ntsError('clear');
                        $('#E1_27').ntsError('clear');
                        $('#E1_30').ntsError('clear');
                    }
                });
            }
        }

        interface IStatementItemCustom {
            key: string;
            categoryAtr: number;
            itemNameCd: string;
            name: string;
            deprecatedAtr: number;
        }

        interface IStatementItemData {
            cid: string;
            statementItem: IStatementItem;
            statementItemName: IStatementItemName;
            paymentItemSet: IPaymentItemSet;
            deductionItemSet: IDeductionItemSet;
            timeItemSet: ITimeItemSet;
            statementItemDisplaySet: IStatementItemDisplaySet;
            validityPeriodAndCycleSet: IValidityPeriodAndCycleSet;
            breakdownItemSet: Array<IBreakdownItemSet>;
            
            // only show in gridlist
            key: string;
            categoryAtr: number;
            itemNameCd: string;
            name: string;
            deprecatedAtr: number;
        }
        
        interface IStatementItem {
            categoryAtr: number;
            itemNameCd: string;
            defaultAtr: number;
            valueAtr: number;
            deprecatedAtr: number;
            socialInsuaEditableAtr: number;
            intergrateCd: string;
        }
        
        interface IStatementItemName {
            name: string;
            shortName: string;
            otherLanguageName: string;
            englishName: string;
        }
        
        interface IPaymentItemSet {
            breakdownItemUseAtr: number;
            laborInsuranceCategory: number;
            settingAtr: number;
            everyoneEqualSet: number;
            monthlySalary: number;
            hourlyPay: number;
            dayPayee: number;
            monthlySalaryPerday: number;
            averageWageAtr: number;
            socialInsuranceCategory: number;
            taxAtr: number;
            taxableAmountAtr: number;
            limitAmount: number;
            limitAmountAtr: number;
            taxLimitAmountCode: string;
            taxExemptionName: string;
            errorUpperLimitSetAtr: number;
            errorUpRangeVal: number;
            errorLowerLimitSetAtr: number;
            errorLoRangeVal: number;
            alarmUpperLimitSetAtr: number;
            alarmUpRangeVal: number;
            alarmLowerLimitSetAtr: number;
            alarmLoRangeVal: number;
            note: string;
        }
        
        interface IDeductionItemSet {
            deductionItemAtr: number;
            breakdownItemUseAtr: number;
            errorUpperLimitSetAtr: number;
            errorUpRangeVal: number;
            errorLowerLimitSetAtr: number;
            errorLoRangeVal: number;
            alarmUpperLimitSetAtr: number;
            alarmUpRangeVal: number;
            alarmLowerLimitSetAtr: number;
            alarmLoRangeVal: number;
            note: string;
        }
        
        interface ITimeItemSet {
            averageWageAtr: number;
            workingDaysPerYear: number;
            timeCountAtr: number;
            errorUpperLimitSetAtr: number;
            errorUpRangeValTime: number;
            errorUpRangeValNum: number;
            errorLowerLimitSetAtr: number;
            errorLoRangeValTime: number;
            errorLoRangeValNum: number;
            alarmUpperLimitSetAtr: number;
            alarmUpRangeValTime: number;
            alarmUpRangeValNum: number;
            alarmLowerLimitSetAtr: number;
            alarmLoRangeValTime: number;
            alarmLoRangeValNum: number;
            note: string;
            parameterValue: number;
        }
        
        interface IStatementItemDisplaySet {
            zeroDisplayAtr: number;
            itemNameDisplay: number;
        }
        
        interface IValidityPeriodAndCycleSet {
            cycleSettingAtr: number;
            january: number;
            february: number;
            march: number;
            april: number;
            may: number;
            june: number;
            july: number;
            august: number;
            september: number;
            october: number;
            november: number;
            december: number;
            periodAtr: number;
            yearPeriodStart: number;
            yearPeriodEnd: number;
        }
        
        interface IBreakdownItemSet {
            breakdownItemCode: number;
            breakdownItemName: string;
        }
        
        function getCategoryAtrText(itemAtr, row) {
            switch (itemAtr) {
                case "0":
                    return getText('Enum_CategoryAtr_PAYMENT_ITEM');
                case "1":
                    return getText('Enum_CategoryAtr_DEDUCTION_ITEM');
                case "2":
                    return getText('Enum_CategoryAtr_ATTEND_ITEM');
                case "3":
                    return getText('Enum_CategoryAtr_REPORT_ITEM');
                case "4":
                    return getText('Enum_CategoryAtr_OTHER_ITEM');
                default:
                    return "";
            }
        }

        function clearDecimal(value: string) {
            let index = value.indexOf(".");

            if(index >= 0) {
                value = value.substring(0, index);
            }

            return value;
        }
        
    }  
}