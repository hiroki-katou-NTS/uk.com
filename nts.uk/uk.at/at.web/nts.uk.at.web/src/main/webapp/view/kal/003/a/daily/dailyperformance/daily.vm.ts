module nts.uk.at.view.kal003.a.daily.dailyperformance {
    import getText = nts.uk.resource.getText;


    export module viewmodel {
        export class ScreenModel {
            /** functiton start page */
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                    {id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                self.selectedTab = ko.observable('tab-1');
            
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
            

        }//end screenModel
    }//end viewmodel

    //module model
    export module model {

        export interface IRole {
            roleId: string;
            roleCode: string;
            roleType: number;
            employeeReferenceRange: number;
            name: string;
            contractCode: string;
            assignAtr: number;
            companyId: string;
        }
        
       


    }//end module model

}//end module