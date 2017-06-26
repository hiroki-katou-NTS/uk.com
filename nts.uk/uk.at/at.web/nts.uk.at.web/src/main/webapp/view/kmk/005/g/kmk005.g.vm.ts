module nts.uk.at.view.kmk005.g {
    export module viewmodel {
        export class ScreenModel {
            panelIndex: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.panelIndex = ko.observable(1);
                
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                return dfd.promise();
            }
            
            setPanelIndex(index: number){
                var self = this;
                self.panelIndex(index);
            }
            
        }      
    }
}