module nts.uk.at.view.kmk011.common {
    export module history {
        export class ScreenModel {
            
            enable_button_creat: KnockoutObservable<boolean>;
            enable_button_edit: KnockoutObservable<boolean>;
            enable_button_delete: KnockoutObservable<boolean>;
            
            // list hist
            histList: KnockoutObservableArray<any>;
            histName: KnockoutObservable<string>;
            currentHist: KnockoutObservable<number>
            selectedHist: KnockoutObservable<string>;
            isEnableListHist: KnockoutObservable<boolean>;
            
            constructor(){
                let _self = this;
                
                _self.enable_button_creat = ko.observable(true);
                _self.enable_button_edit = ko.observable(true);
                _self.enable_button_delete = ko.observable(true);
                
                // list hist
                _self.histList = ko.observableArray([]);
                _self.histName = ko.observable('');
                _self.currentHist = ko.observable(null);
                _self.selectedHist = ko.observable(null)
                _self.isEnableListHist = ko.observable(false);
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public createMode() : void {
                
            }
            
            public editMode() : void {
                
            }
            
            public deleteMode() : void {
                
            }
        }
    }
}