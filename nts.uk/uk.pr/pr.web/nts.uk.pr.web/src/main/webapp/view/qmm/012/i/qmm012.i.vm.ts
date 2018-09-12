module nts.uk.pr.view.qmm012.i.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ScreenModel {
        
        lstBreakdownItemSet: KnockoutObservableArray<BreakdownItemSet> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');
        currentBreakdownItemSet: KnockoutObservable<BreakdownItemSet> = ko.observable(new BreakdownItemSet(null));
        
        requiredCode: KnockoutObservable<boolean> = ko.observable(true);
        breakdownItemCode: KnockoutObservable<string> = ko.observable(''); 
        breakdownItemName: KnockoutObservable<string> = ko.observable(''); 
        bindAtr: KnockoutObservable<string> = ko.observable('bindAtr'); 
        constructor() {
            let self = this;
            dfd = $.Deferred();
            block.invisible();
            
            service.getAllBreakdownItemSetById().done(function(data: Array<BreakdownItemSet>) {
                if (data) {
                    let dataSort = _.sortBy(data, ["breakdownItemCode"]);
                    self.listBreakdownItemSet(dataSort);
                    self.currentCode(self.listBreakdownItemSet()[0].breakdownItemCode);
                }
                else{
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
            
        }
        
        saveItemSet() {

        }
        
        deleteItemSet() {

        }
            close() {
            nts.uk.ui.windows.close();
        }
            
        settingCreateMode() {
            let self = this;
        }
            
        setFocus() {
            let self = this;
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
    export class BreakdownItemSet {
        salaryItemId: string;
        breakdownItemCode: number;
        breakdownItemName: string;
        
    
        constructor(salaryItemId: string, breakdownItemCode: number,  breakdownItemName: string) {
            this.salaryItemId = salaryItemId;
            this.breakdownItemCode = breakdownItemCode;
            this.breakdownItemName = breakdownItemName;
        }
    }
    
}