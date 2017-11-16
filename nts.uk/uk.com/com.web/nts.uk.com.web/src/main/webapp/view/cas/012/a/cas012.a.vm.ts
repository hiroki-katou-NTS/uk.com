module nts.uk.com.view.cas012.a.viewmodel {
    export class ScreenModel {
        //ComboBOx RollType
        listRollType: KnockoutObservableArray<RollType>;
        selectedRollType: KnockoutObservable<string>;
        //ComboBox Company
        listCompany: KnockoutObservableArray<Company>;
        selectedCompany: KnockoutObservable<string>;
        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<RoleIndividual>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.listRollType = ko.observableArray([
                new RollType('1','基本給'),
                new RollType('2','役職手当'),
                new RollType('3','基本給')
            ]);
            self.listCompany = ko.observableArray([
                new Company('1','基本給1'),
                new Company('2','基本給2'),
                new Company('3','基本給3')
            ]);
            self.selectedRollType = ko.observable('1');
            self.selectedCompany = ko.observable('1');
                this.listRoleIndividual = ko.observableArray([]);
            
            for(let i = 1; i < 100; i++) {
                this.listRoleIndividual.push(new RoleIndividual('00' + i, '基本給', "description " + i, i%3 === 0));
            }
             self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100},
                { headerText: '名称', key: 'name', width: 150}, 
                { headerText: '説明', key: 'description', width: 150 }
            ]);
            self.currentCode = ko.observable(); 

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
    }
    class RollType {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class Company {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
     class RoleIndividual {
        code: string;
        name: string;
        description: string;
      
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
           
        }
         }
    
}    
    
    