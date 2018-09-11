module nts.uk.pr.view.qmm012.b {

    import model = qmm012.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export module viewModel {
        export class ScreenModel {
            
            // category comboBox
            categoryList: KnockoutObservableArray<model.ItemModel>;
            selectedCategory: KnockoutObservable<string>;
            
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
                self.selectedCategory = ko.observable(model.CategoryAtr.PAYMENT_ITEM.toString());
                
                for(let i = 0; i < 15; i++) {
                    self.statementItemDataList.push({cid: i.toString(), 
                                                    salaryItemId: i.toString(),
                                                    statementItem: {categoryAtr: i,
                                                                    itemNameCd: i,
                                                                    defaultAtr: i,
                                                                    valueAtr: i,
                                                                    deprecatedAtr: i,
                                                                    socialInsuaEditableAtr: i,
                                                                    intergrateCd: i
                                                                }, 
                                                    statementItemName: {
                                                                    name: i.toString(),
                                                                    shortName: i.toString(),
                                                                    otherLanguageName: i.toString(),
                                                                    englishName: i.toString()
                                                                },
                                                    paymentItemSet: null,
                                                    statementItemDisplaySet: null,
                                                    itemRangeSet: null,
                                                    validityPeriodAndCycleSet: null,
                                                    breakdownItemSet: null,
                                                    }); 
                }
                self.statementItemDataSelected(new StatementItemData(self.statementItemDataList()[0]));
                
                self.gridColumns = [
                                        { headerText: '', key: 'salaryItemId', width: 0, formatter: _.escape, hidden: true },
                                        { headerText: getText('QMM012_27'), key: 'categoryAtr', width: 80 , formatter: _.escape },
                                        { headerText: getText('QMM012_32'), key: 'itemNameCd', width: 60, formatter: _.escape },
                                        { headerText: getText('QMM012_33'), key: 'name', width: 200, formatter: _.escape },
                                        { headerText: getText('QMM012_34'), key: 'deprecatedAtr', width: 50, formatter: _.escape }
                                   ];

            }//end constructor
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                deferred.resolve();
                return deferred.promise();
            }
            
            public create(): void {
                
            }
            
            public register(): void {
                
            }
            
            public copy(): void {
                
            }
            
            public deleteItem(): void {
                
            }
            
            public outputExcel(): void {
                
            }
            
            public modifyLog(): void {
                
            }
            
            public registerPrintingName(): void {
                
            }
            
        }
        
        class StatementItemData {
            cid: string;
            salaryItemId: KnockoutObservable<string>;
            statementItem: StatementItem;
            statementItemName: StatementItemName;
            
            // only show in gridlist
            categoryAtr: number;
            itemNameCd: string;
            name: string;
            deprecatedAtr: number;
            
            constructor(data: IStatementItemData) {
                let self = this;
                
                if (data) {
                    self.cid = data.cid;
                    self.salaryItemId = ko.observable(data.salaryItemId);
                    self.statementItem = new StatementItem(data.statementItem);
                    self.statementItemName = new StatementItemName(data.statementItemName);
                }
            }
        }
        
        class StatementItem {
            categoryAtr: number;
            categoryName: KnockoutObservable<string>;
            itemNameCd: KnockoutObservable<number>;
            defaultAtr: KnockoutObservable<number>;
            valueAtr: KnockoutObservable<number>;
            deprecatedAtr: KnockoutObservable<number>;
            socialInsuaEditableAtr: KnockoutObservable<number>;
            intergrateCd: KnockoutObservable<number>;
            
            constructor(data: IStatementItem) {
                let self = this;
                
                if (data) {
                    self.categoryAtr = data.categoryAtr;
                    
                    if(data.categoryAtr == model.CategoryAtr.PAYMENT_ITEM) {
                        self.categoryName = ko.observable(getText('QMM012_3'));
                    } else if(data.categoryAtr == model.CategoryAtr.DEDUCTION_ITEM) {
                        self.categoryName = ko.observable(getText('QMM012_4'));
                    } else if(data.categoryAtr == model.CategoryAtr.ATTEND_ITEM) {
                        self.categoryName = ko.observable(getText('QMM012_5'));
                    }
                    
                    self.itemNameCd = ko.observable(data.itemNameCd);
                    self.defaultAtr = ko.observable(data.defaultAtr);
                    self.valueAtr = ko.observable(data.valueAtr);
                    self.deprecatedAtr = ko.observable(data.deprecatedAtr);
                    self.socialInsuaEditableAtr = ko.observable(data.socialInsuaEditableAtr);
                    self.intergrateCd = ko.observable(data.intergrateCd);
                }
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
                }
            }
        }
        
        interface IStatementItemData {
            cid: string;
            salaryItemId: string;
            statementItem: IStatementItem;
            statementItemName: IStatementItemName;
            paymentItemSet: IPaymentItemSet;
            statementItemDisplaySet: IStatementItemDisplaySet;
            itemRangeSet: IItemRangeSet;
            validityPeriodAndCycleSet: IValidityPeriodAndCycleSet;
            breakdownItemSet: IBreakdownItemSet;
        }
        
        interface IStatementItem {
            categoryAtr: number;
            itemNameCd: number;
            defaultAtr: number;
            valueAtr: number;
            deprecatedAtr: number;
            socialInsuaEditableAtr: number;
            intergrateCd: number;
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
        
        function timeStore(value, row) {
            console.log(value);
        }
    }  
}