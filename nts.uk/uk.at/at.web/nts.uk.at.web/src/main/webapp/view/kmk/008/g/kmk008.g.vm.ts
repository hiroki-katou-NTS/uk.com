module nts.uk.at.view.kmk008.g {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            
            items2: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            
            printType: number;

            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '年度', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '年月', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                
                this.items = ko.observableArray([]);
                for(let i = 1; i < 3; i++) {
                    this.items.push(new ItemModel("2010" , "1", "2"));
                }
                
                this.items2 = ko.observableArray([]);
                for(let i = 1; i < 3; i++) {
                    this.items2.push(new ItemModel("2010/6" , "a", "b"));
                }
                
                this.columns = ko.observableArray([
                    { headerText: '年度', key: 'year', width: 100  },
                    { headerText: 'エラー', key: 'error', width: 150 }, 
                    { headerText: 'アラーム', key: 'alarm', width: 150 } 
                ]); 
                this.currentCode = ko.observable();
                
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }
        
        class ItemModel {
            year: string;
            error: string;
            alarm: string;
            constructor(year: string, error: string, alarm: string) {
                this.year = year;
                this.error = error;
                this.alarm = alarm;
            }
        }
        
    }
}
