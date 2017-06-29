module nts.uk.at.view.kmk005.e {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            time: KnockoutObservable<string>;
            constructor() {
                var self = this;
                $('.explanAutoCalculation').html(nts.uk.resource.getText('KMK005_48').replace(/\n/g,'<br/>'));
                $('.explanNotAutoCalculation').html(nts.uk.resource.getText('KMK005_50').replace(/\n/g,'<br/>'));
                $('.explanNotAutoCalculationSpec').html(nts.uk.resource.getText('KMK005_51').replace(/\n/g,'<br/>'));
                $('.explanAutoSetOvertime').html(nts.uk.resource.getText('KMK005_53').replace(/\n/g,'<br/>'));
                self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("KMK005_17"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("KMK005_18"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                self.selectedTab = ko.observable('tab-1');  
                self.time = ko.observable('');    
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                return dfd.promise();
            }
            
        }      
    }
}