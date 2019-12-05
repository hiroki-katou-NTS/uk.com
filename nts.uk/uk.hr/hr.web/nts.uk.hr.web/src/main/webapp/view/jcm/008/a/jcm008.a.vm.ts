module jcm008.a {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    var block = nts.uk.ui.block;
    
    export class ViewModel {
        searchFilter: SearchFilterModel;
        retirementSettings: KnockoutObservable<Array<RetirementSetting>>;
        selectedRetirementSetting: KnockoutObservable<string>;
        
        constructor() {
            let self = this;
            self.bindRetirementAgeGrid();
            self.searchFilter = new SearchFilterModel();
            self.retirementSettings = ko.observableArray([
                new RetirementSetting('1', '基本給'),
                new RetirementSetting('2', '役職手当'),
                new RetirementSetting('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            self.selectedRetirementSetting = self.retirementSettings()[0].code;
        }
        
        /** start page */
        start(historyId : any) {
            let self = this;
            let dfd = $.Deferred<any>();
            return dfd.promise();
        }
    
        /** event when click register */
        register() {
            
        }
                
        /** new mode */
        newMode() {
            let self = this;
            nts.uk.ui.errors.clearAll();
        }

        public bindRetirementAgeGrid(): void {
            $('#retirementAgeInfo').igGrid({
                autoGenerateColumns: false,
                columns:[
                    { headerText: getText('JCM008_A222_13') , key: 'classification', dataType: 'string', width: '80px'},
                    { headerText: getText('JCM008_A222_14') , key: 'age', dataType: 'string', width: '70px'},
                    { headerText: getText('JCM008_A222_15') , key: 'standard', dataType: 'string', width: '220px' },

                ],
                dataSource: [
                    {classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準'},
                    {classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準'},
                    {classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準'},
                    {classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準'}
                ],
                dataSourceType: 'json',
                responseDataKey: 'results'
            });
        }
    }

    class EmployeeModel {
      
    }

    class SearchFilterModel {
        includingReflected: KnockoutObservable<boolean> = ko.observable(false);
        retirementAgeDesignation: KnockoutObservable<boolean> = ko.observable(false);
        selectAll: KnockoutObservable<boolean> = ko.observable(false);
        retirementPeriod: KnockoutObservable<any> = ko.observable({});
        department: KnockoutObservable<string> = ko.observable("");
        constructor() {
            
        }
    }

    class RetirementSetting {
        code: string;
        name: string;
    
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}