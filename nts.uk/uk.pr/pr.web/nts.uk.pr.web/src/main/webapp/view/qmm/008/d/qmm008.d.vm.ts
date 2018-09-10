module nts.uk.com.view.qmm008.d {
    import text = nts.uk.resource.getText;
    export module viewmodel {
       export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
          
        
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;   
           
        simpleValue: KnockoutObservable<string>;
        
        // combobox
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        
        values : KnockoutObservable<string>;
           
        constructor() {
            
            this.items = ko.observableArray([]);
            
            for(let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給'));
            }
            
            
            this.columns2 = ko.observableArray([
                { headerText: text('QMM008_110'), key: 'code', width: 100 },
                { headerText: text('QMM008_111'), key: 'name', width: 200 }
            ]);
            
            this.switchOptions = ko.observableArray([
                { code: "1", name: '四捨五入' },
                { code: "2", name: '切り上げ' },
                { code: "3", name: '切り捨て' }
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            
            
            self.tabs = ko.observableArray([
            {id: 'tab-1', title: text('QMM008_112'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
            {id: 'tab-2', title: text('QMM008_113'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            
            self.simpleValue = ko.observable("123");
            
            self.itemList = ko.observableArray([
                new ItemModelComBoBox('1', '基本給'),
                new ItemModelComBoBox('2', '役職手当'),
                new ItemModelComBoBox('3', '基本給ながい')
            ]);

            self.selectedCode = ko.observable('1');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            
            self.values = ko.observable('');
            
        }
        

    }
    
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
        
    class ItemModelComBoBox {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }   
     
    }
}