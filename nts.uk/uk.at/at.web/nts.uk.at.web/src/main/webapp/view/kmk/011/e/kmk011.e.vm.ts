module nts.uk.at.view.kmk011.e {
    import viewModelHist = nts.uk.at.view.kmk011.common.history;
    export module viewmodel {
        export class ScreenModel {
            //history screen
            screenHist: KnockoutObservable<any>;
            
            // work type list
            items: KnockoutObservableArray<ItemModel[]>;
            columns2: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;
            
            constructor() {
                var _self = this;
                
                //for history screen
                _self.screenHist = ko.observable(new viewModelHist.ScreenModel());
                
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
                _self.screenHist().start_page().done( () => {
                     dfd.resolve();
                });
                return dfd.promise();
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