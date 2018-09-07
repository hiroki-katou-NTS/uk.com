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
            categoryList: KnockoutObservableArray<ItemModel>;
            selectedCategory: KnockoutObservable<string>;
            
            // statement gridlist
            statementList: KnockoutObservableArray<IStatementItem> = ko.observableArray([]);
            statementSelected: KnockoutObservable<StatementItem> = ko.observable(null);
            
            constructor() {
                let self = this;
                
                // category comboBox
                self.categoryList = ko.observableArray([
                    new model.ItemModel(model.CategoryAtr.PAYMENT_ITEM, getText('QMM012_3')),
                    new model.ItemModel(model.CategoryAtr.DEDUCTION_ITEM, getText('QMM012_4')),
                    new model.ItemModel(model.CategoryAtr.ATTEND_ITEM, getText('QMM012_5')),
                    new model.ItemModel(model.CategoryAtr.REPORT_ITEM, getText('QMM012_6')),
                    new model.ItemModel(model.CategoryAtr.OTHER_ITEM, getText('QMM012_7'))
                ]);
                self.selectedCategory = ko.observable(model.CategoryAtr.PAYMENT_ITEM);

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
        
        class StatementItem {
            cid: string;
            categoryAtr: number;
            itemNameCd: string;
            salaryItemId: string;
            defaultAtr: KnockoutObservable<number>;
            valueAtr: KnockoutObservable<number>;
            deprecatedAtr: KnockoutObservable<number>;
            socialInsuaEditableAtr: KnockoutObservable<number>;
            intergrateCd: KnockoutObservable<number>;
            
            constructor(item: IStatementItem) {
                let self = this;
                
                if (item) {
                    self.cid = item.cid;
                    self.categoryAtr = item.categoryAtr;
                    self.itemNameCd = item.itemNameCd;
                    self.salaryItemId = item.salaryItemId;
                    self.defaultAtr(item.defaultAtr);
                    self.valueAtr(item.valueAtr);
                    self.deprecatedAtr(item.deprecatedAtr);
                    self.socialInsuaEditableAtr(item.socialInsuaEditableAtr);
                    self.intergrateCd(item.intergrateCd);
                    
                }
            }
        }
        
        interface IStatementItem {
            cid: string;
            categoryAtr: number;
            itemNameCd: string;
            salaryItemId: string;
            defaultAtr: number;
            valueAtr: number;
            deprecatedAtr: number;
            socialInsuaEditableAtr: number;
            intergrateCd: number;
        }
    }  
}