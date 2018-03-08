module nts.uk.at.view.kmk011.e {
    
    export module viewmodel {
        export class ScreenModel {
            //history screen
            enable_button_creat: KnockoutObservable<boolean>;
            enable_button_edit: KnockoutObservable<boolean>;
            enable_button_delete: KnockoutObservable<boolean>;
            histList: KnockoutObservableArray<any>;
            histName: KnockoutObservable<string>;
            currentHist: KnockoutObservable<number>
            selectedHist: KnockoutObservable<string>;
            isEnableListHist: KnockoutObservable<boolean>;
            
            //divergence time setting
            emailAuth: KnockoutObservable<string>;
            myMessage: KnockoutObservable<string>;
            selectedRuleCode: KnockoutObservable<any>;
            roundingRules: KnockoutObservableArray<any>;
            required: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            
            // work type list
            items: KnockoutObservableArray<ItemModel[]>;
            columns2: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;
            
            constructor() {
                var _self = this;
                
                //history screen
                _self.enable_button_creat = ko.observable(true);
                _self.enable_button_edit = ko.observable(true);
                _self.enable_button_delete = ko.observable(true);
                _self.histList = ko.observableArray([]);
                _self.histName = ko.observable('');
                _self.currentHist = ko.observable(null);
                _self.selectedHist = ko.observable(null)
                _self.isEnableListHist = ko.observable(false);
                
                //divergence time setting
                _self.emailAuth =  ko.observable("test");
                _self.myMessage = ko.observable("test21");
                _self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                _self.selectedRuleCode = ko.observable(1);
                _self.required = ko.observable(true);
                _self.enable = ko.observable(true);
                
                // work type list
                _self.items = ko.observableArray([new ItemModel('00' + 0, '基本給', "description " + 0, 0%3 === 0)]);
                for(let i = 1; i < 100; i++) {
                    _self.items.push(new ItemModel('00' + i, '基本給', "description " + i, i%3 === 0));
                }
                _self.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);
                _self.currentCode = ko.observable();
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            // history mode
            public createMode() : void {
                
            }
            public editMode() : void {
                
            }
            public deleteMode() : void {
                
            }
        }
        
        // work type list
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            switchValue: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;    
                this.deletable = false;
            }
        }
        
    }
}