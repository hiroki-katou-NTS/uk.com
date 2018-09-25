module nts.uk.pr.view.qmm012.b {

    import model = qmm012.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import dialog = nts.uk.ui.dialog;

    export module viewModel {
        export class ScreenModel {
            
            // category comboBox
            categoryList: KnockoutObservableArray<model.ItemModel>;
            selectedCategory: KnockoutObservable<string> = ko.observable(null);
            
            // Also display abolition
            isdisplayAbolition: KnockoutObservable<boolean> = ko.observable(false);
            
            // statement gridList
            statementItemDataList: KnockoutObservableArray<IStatementItemData> = ko.observableArray([]);
            statementItemDataSelected: KnockoutObservable<StatementItemData> = ko.observable(null);
            
            // define gridColumns
            gridColumns: any;
            
            constructor() {
                let self = this;
                
                // category comboBox
                self.categoryList = ko.observableArray([
                    new model.ItemModel(model.CategoryAtr.PAYMENT_ITEM.toString(), getText('QMM012_3')),
                    new model.ItemModel(model.CategoryAtr.DEDUCTION_ITEM.toString(), getText('QMM012_4')),
                    new model.ItemModel(model.CategoryAtr.ATTEND_ITEM.toString(), getText('QMM012_5')),
                    new model.ItemModel(model.CategoryAtr.REPORT_ITEM.toString(), getText('QMM012_6')),
                    new model.ItemModel(model.CategoryAtr.OTHER_ITEM.toString(), getText('QMM012_7'))
                ]);
                
                self.gridColumns = [
                                        { headerText: '', key: 'salaryItemId', width: 0, formatter: _.escape, hidden: true },
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
                
                self.selectedCategory.subscribe(() => {
                    self.loadListData();
                });

                self.isdisplayAbolition.subscribe(() => {
                    self.loadListData();
                });
            }//end constructor
            
            loadListData(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                block.invisible();
                
                let category: number = -1;
                if(!_.isEmpty(self.selectedCategory())) {
                    category = parseInt(self.selectedCategory(), 10);
                }
                
                service.getAllStatementItemData(category, self.isdisplayAbolition()).done(function(data: Array<IStatementItemData>) {
                    data.sort(self.compare);
                    self.statementItemDataList(data);
                    
                    if(data.length > 0) {
                        self.statementItemDataSelected(new StatementItemData(data[0], self));
                        $("#B3_3").focus();
                    } else {
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
            
            compare(a: IStatementItemData, b: IStatementItemData): number {
            
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
                        self.statementItemDataSelected(new StatementItemData(null, self));
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
                let listMessage: Array<string> = [];
                let itemRangeSet = self.statementItemDataSelected().itemRangeSet();
                
                nts.uk.ui.errors.clearAll();
                
                if((categoryAtr == model.CategoryAtr.PAYMENT_ITEM) || (categoryAtr == model.CategoryAtr.DEDUCTION_ITEM)) {
                    if((itemRangeSet.errorUpperLimitSettingAtr() == 1) && (itemRangeSet.errorUpperRangeValueAmount() == null)) {
                        $('#C2_12').ntsError('set', { messageId: "MsgQ_14" });
                    }
                    
                    if((itemRangeSet.errorLowerLimitSettingAtr() == 1) && (itemRangeSet.errorLowerRangeValueAmount() == null)) {
                        $('#C2_15').ntsError('set', { messageId: "MsgQ_15" });
                    }
                    
                    if((itemRangeSet.errorUpperLimitSettingAtr() == 1) && (itemRangeSet.errorLowerLimitSettingAtr() == 1)
                            && (itemRangeSet.errorUpperRangeValueAmount() != null) && (itemRangeSet.errorLowerRangeValueAmount() != null)
                            && itemRangeSet.errorUpperRangeValueAmount() <= itemRangeSet.errorLowerRangeValueAmount()) {
                        $('#C2_15').ntsError('set', { messageId: "MsgQ_1" });
                    }
                    
                    if((itemRangeSet.alarmUpperLimitSettingAtr() == 1) && (itemRangeSet.alarmUpperRangeValueAmount() == null)) {
                        $('#C2_19').ntsError('set', { messageId: "MsgQ_16" });
                    }
                    
                    if((itemRangeSet.alarmLowerLimitSettingAtr() == 1) && (itemRangeSet.alarmLowerRangeValueAmount() == null)) {
                        $('#C2_22').ntsError('set', { messageId: "MsgQ_16" });
                    }
                    
                    if((itemRangeSet.alarmUpperLimitSettingAtr() == 1) && (itemRangeSet.alarmLowerLimitSettingAtr() == 1)
                            && (itemRangeSet.alarmUpperRangeValueAmount() != null) && (itemRangeSet.alarmLowerRangeValueAmount() != null)
                            && itemRangeSet.alarmUpperRangeValueAmount() <= itemRangeSet.alarmLowerRangeValueAmount()) {
                        $('#C2_22').ntsError('set', { messageId: "MsgQ_2" });
                    }
                }
                
                if(categoryAtr == model.CategoryAtr.ATTEND_ITEM) {
                    //TODO phải chơi 2 kiểu time
                }
                
                $(".check-validate").trigger("validate");
                
                if(!nts.uk.ui.errors.hasError()) {
                    let oldSalaryId = self.statementItemDataSelected().salaryItemId();
                    let command = ko.toJS(self.statementItemDataSelected);
                    delete command.setBreakdownItem;
                    delete command.setValidity;
                    delete command.paymentItemSet.screenModel;
                    delete command.paymentItemSet.setTaxExemptionLimit;
                    
                    if(self.statementItemDataSelected().checkCreate()) {
                         oldSalaryId = nts.uk.util.randomId();
                         command.salaryItemId = oldSalaryId;
                    }
                    
                    if((command.paymentItemSet.limitAmount != null) && isNaN(command.paymentItemSet.limitAmount)) {
                        command.paymentItemSet.limitAmount = null;
                    }
                    
                    block.invisible();
                    service.registerStatementItemData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            self.loadListData().done(function() {
                                let matchSalaryID = _.filter(self.statementItemDataList(), function(o) {
                                    return oldSalaryId == o.salaryItemId;
                                });
                                
                                if(matchSalaryID.length > 0) {
                                    self.statementItemDataSelected().salaryItemId(oldSalaryId);
                                }
                                
                                $("#B3_3").focus();
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
                let nextSalaryId = self.getNextSalaryId();
                
                dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let command = ko.toJS(self.statementItemDataSelected);
                    delete command.setBreakdownItem;
                    delete command.setValidity;
                    delete command.paymentItemSet.screenModel;
                    delete command.paymentItemSet.setTaxExemptionLimit;
                    
                    block.invisible();
                    service.removeStatementItemData(command).done(function() {
                        block.clear();
                        
                        dialog.info({ messageId: "Msg_16" }).then(() => {
                            self.loadListData().done(function() {
                                if(self.statementItemDataList().length == 0) {
                                    self.create();
                                } else if(nextSalaryId != null) {
                                    self.statementItemDataSelected().salaryItemId(nextSalaryId);
                                    $("#B3_3").focus();
                                }
                            });
                        });
                    }).fail(err => {
                        block.clear();
                        $("#B3_3").focus();
                    });
                })
            }
            
            public getNextSalaryId(): string {
                let self = this;
                let nextItem: string = null;
                let array: Array<string> = self.statementItemDataList().map(x => x.salaryItemId);
                let value = self.statementItemDataSelected().salaryItemId();
                
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
            salaryItemId: KnockoutObservable<string>;
            statementItem: KnockoutObservable<StatementItem>;
            statementItemName: KnockoutObservable<StatementItemName>;
            paymentItemSet: KnockoutObservable<PaymentItemSet>;
            statementItemDisplaySet: KnockoutObservable<StatementItemDisplaySet>;
            itemRangeSet: KnockoutObservable<ItemRangeSet>;
            deductionItemSet: KnockoutObservable<DeductionItemSet>;
            timeItemSet: KnockoutObservable<TimeItemSet>;
            
            // check SetValidityPeriodCycle
            isSetValidity: KnockoutObservable<boolean> = ko.observable(false);
            
            // check BreakdownItemSet
            isSetBreakdownItem: KnockoutObservable<boolean> = ko.observable(false);
            
            // mode of screen
            checkCreate: KnockoutObservable<boolean> = ko.observable(true);
            
            constructor(data: IStatementItemData, screenModel: ScreenModel) {
                let self = this;
                
                if (data) {
                    self.cid = data.cid;
                    self.salaryItemId = ko.observable(data.salaryItemId);
                    self.statementItem = ko.observable(new StatementItem(data.statementItem));
                    self.statementItemName = ko.observable(new StatementItemName(data.statementItemName));
                    self.paymentItemSet = ko.observable(new PaymentItemSet(data.paymentItemSet, screenModel));
                    self.statementItemDisplaySet = ko.observable(new StatementItemDisplaySet(data.statementItemDisplaySet));
                    self.itemRangeSet = ko.observable(new ItemRangeSet(data.itemRangeSet));
                    self.deductionItemSet = ko.observable(new DeductionItemSet(data.deductionItemSet));
                    self.timeItemSet = ko.observable(new TimeItemSet(data.timeItemSet));
                    
                    if(data.validityPeriodAndCycleSet) {
                        self.isSetValidity(true);
                    }
                    
                    if(data.breakdownItemSet && (data.breakdownItemSet.length > 0)) {
                        self.isSetBreakdownItem(true);
                    }
                    
                    self.checkCreate = ko.observable(false);
                } else {
                    self.cid = "";
                    self.salaryItemId = ko.observable("");
                    self.statementItem = ko.observable(new StatementItem(null));
                    self.statementItemName = ko.observable(new StatementItemName(null));
                    self.paymentItemSet = ko.observable(new PaymentItemSet(null, screenModel));
                    self.statementItemDisplaySet = ko.observable(new StatementItemDisplaySet(null));
                    self.itemRangeSet = ko.observable(new ItemRangeSet(null));
                    self.deductionItemSet = ko.observable(new DeductionItemSet(null));
                    self.timeItemSet = ko.observable(new TimeItemSet(null));
                    
                    self.checkCreate = ko.observable(true);
                }
                
                self.salaryItemId.subscribe(x => {
                    
                    if(x) {
                        data = _.filter(screenModel.statementItemDataList(), function(o) {
                            return x == o.salaryItemId;
                        })[0];
                        
                        self.statementItem(new StatementItem(data.statementItem));
                        self.statementItemName(new StatementItemName(data.statementItemName));
                        self.paymentItemSet(new PaymentItemSet(data.paymentItemSet, screenModel));
                        self.statementItemDisplaySet(new StatementItemDisplaySet(data.statementItemDisplaySet));
                        self.itemRangeSet(new ItemRangeSet(data.itemRangeSet));
                        self.deductionItemSet(new DeductionItemSet(data.deductionItemSet));
                        self.timeItemSet = ko.observable(new TimeItemSet(data.timeItemSet));
                        
                        if(data.validityPeriodAndCycleSet) {
                            self.isSetValidity(true);
                        } else {
                            self.isSetValidity(false);
                        }
                        
                        if(data.breakdownItemSet && (data.breakdownItemSet.length > 0)) {
                            self.isSetBreakdownItem(true);
                        } else {
                            self.isSetBreakdownItem(false);
                        }
                        
                        self.checkCreate(false);
                        screenModel.statementItemDataSelected(self);
                        $("#B3_3").focus();
                    }
                    
                    nts.uk.ui.errors.clearAll();
                });
                
                nts.uk.ui.errors.clearAll();
            }
            
            public setBreakdownItem(): void {
                let self = this;

                setShared("QMM012_B_TO_I_PARAMS", {salaryItemId: self.salaryItemId(), categoryName: self.statementItem().categoryName()});

                nts.uk.ui.windows.sub.modal('../i/index.xhtml').onClosed(() => {
                    self.isSetBreakdownItem(getShared("QMM012_I_IS_SETTING"));
                    $("#C3_8").focus();
                });
            }
            
            public setValidity(): void {
                let self = this;

                setShared("QMM012_B_TO_H_SALARY_ITEM_ID", {
                    salaryItemId: self.salaryItemId(),
                    categoryAtr: self.statementItem().categoryAtr(),
                    itemNameCd: self.statementItem().itemNameCd(),
                    name: self.statementItemName().name(),
                });

                nts.uk.ui.windows.sub.modal('../h/index.xhtml').onClosed(() => {
                    self.isSetValidity(getShared("QMM012_H_IS_SETTING"));
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
            
            screenModel: ScreenModel;
            
            constructor(data: IPaymentItemSet, screenModel: ScreenModel) {
                let self = this;
                self.screenModel = screenModel;
                
                self.taxList = ko.observableArray([
                    new model.ItemModel(model.TaxAtr.TAXATION.toString(), getText('QMM012_x')),
                    new model.ItemModel(model.TaxAtr.LIMIT_TAX_EXEMPTION.toString(), getText('QMM012_x')),
                    new model.ItemModel(model.TaxAtr.NO_LIMIT_TAX_EXEMPTION.toString(), getText('QMM012_x')),
                    new model.ItemModel(model.TaxAtr.COMMUTING_EXPENSES_MANUAL.toString(), getText('QMM012_x')),
                    new model.ItemModel(model.TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER.toString(), getText('QMM012_x'))
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
                    self.limitAmountAtr = ko.observable(data.limitAmountAtr);
                    self.taxLimitAmountCode = ko.observable(data.taxLimitAmountCode);
                    self.taxExemptionName = ko.observable(data.taxExemptionName);
                    self.note = ko.observable(data.note);
                } else {
                    self.breakdownItemUseAtr = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.laborInsuranceCategory = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.settingAtr = ko.observable(model.SettingClassification.DESIGNATE_BY_ALL_MEMBERS);
                    self.everyoneEqualSet = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.monthlySalary = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.hourlyPay = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.dayPayee = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.monthlySalaryPerday = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.averageWageAtr = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.socialInsuranceCategory = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.taxAtr = ko.observable(model.TaxAtr.TAXATION);
                    self.taxableAmountAtr = ko.observable(model.TaxableAmountClassification.OVERDRAFT_TAXATION);
                    self.limitAmount = ko.observable(null);
                    self.limitAmountAtr = ko.observable(model.LimitAmountClassification.FIXED_AMOUNT);
                    self.taxLimitAmountCode = ko.observable(null);
                    self.taxExemptionName = ko.observable(null);
                    self.note = ko.observable(null);
                }
                
                // mapping 2 thuộc tính của 2 domain từ cùng 1 item
                self.breakdownItemUseAtr.subscribe(x => {
                    if(x) {
                        screenModel.statementItemDataSelected().deductionItemSet().breakdownItemUseAtr(x);
                    }
                });
                
                // mapping 3 thuộc tính của 3 domain từ cùng 1 item
                self.note.subscribe(x => {
                    if(x) {
                        screenModel.statementItemDataSelected().deductionItemSet().note(x);
                        screenModel.statementItemDataSelected().timeItemSet().note(x);
                    }
                });
                
                self.limitAmountAtr.subscribe(x => {
                    if((x != null) && (x != model.LimitAmountClassification.FIXED_AMOUNT)) {
                        $('#C4_8').ntsError('clear');
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
                    self.zeroDisplayAtr = ko.observable(model.Display.NOT_SHOW);
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
        
        class ItemRangeSet {
            rangeValueAtr: KnockoutObservable<number>;
            errorUpperLimitSettingAtr: KnockoutObservable<number>;
            errorUpperRangeValueAmount: KnockoutObservable<number>;
            errorUpperRangeValueTime: KnockoutObservable<number>;
            errorUpperRangeValueNum: KnockoutObservable<number>;
            errorLowerLimitSettingAtr: KnockoutObservable<number>;
            errorLowerRangeValueAmount: KnockoutObservable<number>;
            errorLowerRangeValueTime: KnockoutObservable<number>;
            errorLowerRangeValueNum: KnockoutObservable<number>;
            alarmUpperLimitSettingAtr: KnockoutObservable<number>;
            alarmUpperRangeValueAmount: KnockoutObservable<number>;
            alarmUpperRangeValueTime: KnockoutObservable<number>;
            alarmUpperRangeValueNum: KnockoutObservable<number>;
            alarmLowerLimitSettingAtr: KnockoutObservable<number>;
            alarmLowerRangeValueAmount: KnockoutObservable<number>;
            alarmLowerRangeValueTime: KnockoutObservable<number>;
            alarmLowerRangeValueNum: KnockoutObservable<number>;
            
            // number -> boolean for checkbox
            errorUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            errorLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmUpperLimitSettingAtrCus: KnockoutObservable<boolean>;
            alarmLowerLimitSettingAtrCus: KnockoutObservable<boolean>;
            
            // option for numberEditor binding
            numberEditorOption: any;
            
            constructor(data: IItemRangeSet) {
                let self = this;
            
                self.numberEditorOption = {
                    grouplength: 3,
                    decimallength: 3,
                    width: "150px",
                    textalign: "right",
                    currencyformat: "JPY"
                };
                
                if (data) {
                    self.rangeValueAtr = ko.observable(data.rangeValueAtr);
                    self.errorUpperLimitSettingAtr = ko.observable(data.errorUpperLimitSettingAtr);
                    self.errorUpperRangeValueAmount = ko.observable(data.errorUpperRangeValueAmount);
                    self.errorUpperRangeValueTime = ko.observable(data.errorUpperRangeValueTime);
                    self.errorUpperRangeValueNum = ko.observable(data.errorUpperRangeValueNum);
                    self.errorLowerLimitSettingAtr = ko.observable(data.errorLowerLimitSettingAtr);
                    self.errorLowerRangeValueAmount = ko.observable(data.errorLowerRangeValueAmount);
                    self.errorLowerRangeValueTime = ko.observable(data.errorLowerRangeValueTime);
                    self.errorLowerRangeValueNum = ko.observable(data.errorLowerRangeValueNum);
                    self.alarmUpperLimitSettingAtr = ko.observable(data.alarmUpperLimitSettingAtr);
                    self.alarmUpperRangeValueAmount = ko.observable(data.alarmUpperRangeValueAmount);
                    self.alarmUpperRangeValueTime = ko.observable(data.alarmUpperRangeValueTime);
                    self.alarmUpperRangeValueNum = ko.observable(data.alarmUpperRangeValueNum);
                    self.alarmLowerLimitSettingAtr = ko.observable(data.alarmLowerLimitSettingAtr);
                    self.alarmLowerRangeValueAmount = ko.observable(data.alarmLowerRangeValueAmount);
                    self.alarmLowerRangeValueTime = ko.observable(data.alarmLowerRangeValueTime);
                    self.alarmLowerRangeValueNum = ko.observable(data.alarmLowerRangeValueNum);
                } else {
                    self.rangeValueAtr = ko.observable(null);
                    self.errorUpperLimitSettingAtr = ko.observable(null);
                    self.errorUpperRangeValueAmount = ko.observable(null);
                    self.errorUpperRangeValueTime = ko.observable(null);
                    self.errorUpperRangeValueNum = ko.observable(null);
                    self.errorLowerLimitSettingAtr = ko.observable(null);
                    self.errorLowerRangeValueAmount = ko.observable(null);
                    self.errorLowerRangeValueTime = ko.observable(null);
                    self.errorLowerRangeValueNum = ko.observable(null);
                    self.alarmUpperLimitSettingAtr = ko.observable(null);
                    self.alarmUpperRangeValueAmount = ko.observable(null);
                    self.alarmUpperRangeValueTime = ko.observable(null);
                    self.alarmUpperRangeValueNum = ko.observable(null);
                    self.alarmLowerLimitSettingAtr = ko.observable(null);
                    self.alarmLowerRangeValueAmount = ko.observable(null);
                    self.alarmLowerRangeValueTime = ko.observable(null);
                    self.alarmLowerRangeValueNum = ko.observable(null);
                }
                
                self.errorUpperLimitSettingAtrCus = ko.observable(self.errorUpperLimitSettingAtr() == 1);
                self.errorLowerLimitSettingAtrCus = ko.observable(self.errorLowerLimitSettingAtr() == 1);
                self.alarmUpperLimitSettingAtrCus = ko.observable(self.alarmUpperLimitSettingAtr() == 1);
                self.alarmLowerLimitSettingAtrCus = ko.observable(self.alarmLowerLimitSettingAtr() == 1);
                
                self.errorUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorUpperLimitSettingAtr(1);
                    } else {
                        self.errorUpperLimitSettingAtr(0);
                        $('#C2_12').ntsError('clear');
                    }
                });
                
                self.errorLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.errorLowerLimitSettingAtr(1);
                    } else {
                        self.errorLowerLimitSettingAtr(0);
                        $('#C2_15').ntsError('clear');
                    }
                });
                
                self.alarmUpperLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmUpperLimitSettingAtr(1);
                    } else {
                        self.alarmUpperLimitSettingAtr(0);
                        $('#C2_19').ntsError('clear');
                    }
                });
                
                self.alarmLowerLimitSettingAtrCus.subscribe(x => {
                    if (x) {
                        self.alarmLowerLimitSettingAtr(1);
                    } else {
                        self.alarmLowerLimitSettingAtr(0);
                        $('#C2_22').ntsError('clear');
                    }
                });
            }
        }
        
        class DeductionItemSet {
            deductionItemAtr: KnockoutObservable<number>;
            breakdownItemUseAtr: KnockoutObservable<number>;
            note: KnockoutObservable<string>;
            
            // deductionItemList switch button
            deductionItemList: KnockoutObservableArray<model.ItemModel>;
            
            constructor(data: IDeductionItemSet) {
                let self = this;
                
                self.deductionItemList = ko.observableArray([
                    new model.ItemModel(model.DeductionItemAtr.OPTIONAL_DEDUCTION_ITEM.toString(), getText('QMM012_y')),
                    new model.ItemModel(model.DeductionItemAtr.SOCIAL_INSURANCE_ITEM.toString(), getText('QMM012_y')),
                    new model.ItemModel(model.DeductionItemAtr.INCOME_TAX_ITEM.toString(), getText('QMM012_y')),
                    new model.ItemModel(model.DeductionItemAtr.INHABITANT_TAX_ITEM.toString(), getText('QMM012_y'))
                ]);
                
                if (data) {
                    self.deductionItemAtr = ko.observable(data.deductionItemAtr);
                    self.breakdownItemUseAtr = ko.observable(data.breakdownItemUseAtr);
                    self.note = ko.observable(data.note);
                } else {
                    self.deductionItemAtr = ko.observable(model.DeductionItemAtr.OPTIONAL_DEDUCTION_ITEM);
                    self.breakdownItemUseAtr = ko.observable(model.BreakdownItemUseAtr.NOT_USE);
                    self.note = ko.observable(null);
                }
            }
        }
        
        class TimeItemSet {
            averageWageAtr: KnockoutObservable<number>;
            workingDaysPerYear: KnockoutObservable<number>;
            timeCountAtr: KnockoutObservable<number>;
            note: KnockoutObservable<string>;
            
            // timeCountList switch button
            timeCountList: KnockoutObservableArray<model.ItemModel>;
            
            // coveredList switch button
            coveredList: KnockoutObservableArray<model.ItemModel>;
            
            constructor(data: ITimeItemSet) {
                let self = this;
                
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
                    self.note = ko.observable(data.note);
                } else {
                    self.averageWageAtr = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.workingDaysPerYear = ko.observable(model.CoveredAtr.NOT_COVERED);
                    self.timeCountAtr = ko.observable(model.TimeCountAtr.TIME);
                    self.note = ko.observable(null);
                }
            }
        }
        
        interface IStatementItemData {
            cid: string;
            salaryItemId: string;
            statementItem: IStatementItem;
            statementItemName: IStatementItemName;
            paymentItemSet: IPaymentItemSet;
            deductionItemSet: IDeductionItemSet;
            timeItemSet: ITimeItemSet;
            statementItemDisplaySet: IStatementItemDisplaySet;
            itemRangeSet: IItemRangeSet;
            validityPeriodAndCycleSet: IValidityPeriodAndCycleSet;
            breakdownItemSet: Array<IBreakdownItemSet>;
            
            // only show in gridlist
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
            note: string;
        }
        
        interface IDeductionItemSet {
            deductionItemAtr: number;
            breakdownItemUseAtr: number;
            note: string;
        }
        
        interface ITimeItemSet {
            averageWageAtr: number;
            workingDaysPerYear: number;
            timeCountAtr: number;
            note: string;
        }
        
        interface IStatementItemDisplaySet {
            zeroDisplayAtr: number;
            itemNameDisplay: number;
        }
        
        interface IItemRangeSet {
            rangeValueAtr: number;
            errorUpperLimitSettingAtr: number;
            errorUpperRangeValueAmount: number;
            errorUpperRangeValueTime: number;
            errorUpperRangeValueNum: number;
            errorLowerLimitSettingAtr: number;
            errorLowerRangeValueAmount: number;
            errorLowerRangeValueTime: number;
            errorLowerRangeValueNum: number;
            alarmUpperLimitSettingAtr: number;
            alarmUpperRangeValueAmount: number;
            alarmUpperRangeValueTime: number;
            alarmUpperRangeValueNum: number;
            alarmLowerLimitSettingAtr: number;
            alarmLowerRangeValueAmount: number;
            alarmLowerRangeValueTime: number;
            alarmLowerRangeValueNum: number;
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
                    return getText('QMM012_3');
                case "1":
                    return getText('QMM012_4');
                case "2":
                    return getText('QMM012_5');
                case "3":
                    return getText('QMM012_6');
                case "4":
                    return getText('QMM012_7');
                default:
                    return "";
            }
        }
        
    }  
}