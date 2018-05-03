module nts.uk.com.view.cmm048.a {

    export module viewmodel {

        export class ScreenModel {
            
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("CMM048_4"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("CMM048_5"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                _self.selectedTab = ko.observable('tab-1');
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public save() {
                
            }
            
        }
    }
}