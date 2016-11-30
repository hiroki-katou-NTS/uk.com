module qmmm019.j.viewmodel{
    export class ScreenModel {
        selectedBox: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.selectedBox = ko.observable("3");           
        }
        
        chooseItem() {
            var self = this;
            nts.uk.ui.windows.setShared('selectedCode', self.selectedBox());
            nts.uk.ui.windows.close();
        }
        
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}