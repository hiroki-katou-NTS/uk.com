module nts.uk.at.view.ksu005.c {
    import getText = nts.uk.resource.getText;

    @bean()
    class Ksu005cViewModel extends ko.ViewModel {
        code: KnockoutObservable<String>;
        name: KnockoutObservable<String>;
        valueCode:KnockoutObservable<String>;
        valueName:KnockoutObservable<String>;
      
        constructor() {
            super();
            const self = this;
            self.code = ko.observable("03");
            self.name = ko.observable("切り捨て");
            self.valueCode = ko.observable("04");
            self.valueName = ko.observable("切り捨て04");
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        // openDialog(): void {
        //     let self = this;				
        //     self.currentScreen = nts.uk.ui.windows.sub.modal('/view/ksu/005/c/index.xhtml');
        // }
    }
}