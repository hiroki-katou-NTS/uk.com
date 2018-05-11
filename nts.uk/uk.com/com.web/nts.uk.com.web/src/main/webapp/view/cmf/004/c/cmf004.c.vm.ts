module nts.uk.com.view.cmf004.c {
    export module viewmodel {
        export class ScreenModel {
            
            password: KnockoutObservable<string> = ko.observable('');
            
            constructor() {
            }
            
           private processing(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(() => {
                   
                });
            }
        }
    }
}