module nts.uk.at.view.kal004.tab2.viewModel {
    import share = nts.uk.at.view.kal004.share.model;
    
    export class ScreenModel {
        listCheckConditionCode: KnockoutObservableArray<share.ModelCheckConditonCode> = ko.observableArray([]);
        listCheckCondition: KnockoutObservableArray<share.CheckCondition> = ko.observableArray([]);
        
        constructor() {
            var self = this;
            self.listCheckConditionCode.subscribe((newList) => {
                self.changeCheckCondition(newList);
            });
        }
        
        private changeCheckCondition(listCheckCode: Array<share.ModelCheckConditonCode>): void {
            var self = this;
            
        }
        
        private openDialog(): void {
             nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                console.log("success!");
            }); 
        }
    }
    
    
}