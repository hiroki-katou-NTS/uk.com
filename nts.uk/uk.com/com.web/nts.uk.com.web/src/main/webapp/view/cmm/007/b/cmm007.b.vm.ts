module nts.uk.com.view.cmm007.b {
    import MailServerFindDto = model.SampleDto;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;

            constructor(){
                let _self = this;
                
                _self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("CMM007_7"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("CMM007_8"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-3', title: nts.uk.resource.getText("CMM007_9"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                _self.selectedTab = ko.observable('tab-1');
            
            } 
       }
           
    }
}