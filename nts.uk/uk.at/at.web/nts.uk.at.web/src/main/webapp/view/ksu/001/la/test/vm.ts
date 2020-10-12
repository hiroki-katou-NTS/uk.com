module test {
    export module viewmodelTest{
        export class ScreenModel {
            currentScreen: any = null;  
            date: KnockoutObservable<string>;
            constructor() {
                var self = this;   
                self.date = ko.observable('2020/01/01');
            }     
            public startPage():JQueryPromise<void>{
                let self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            public openDialogLA(): void {
                let self = this;
                nts.uk.ui.windows.setShared("baseDate", moment(self.date()).format('YYYY/MM/DD'));
                self.currentScreen = nts.uk.ui.windows.sub.modal("/view/ksu/001/la/index.xhtml");
            }   
        }
    }    
}