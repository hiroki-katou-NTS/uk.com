module nts.uk.at.view.ksu005.b {
    import getText = nts.uk.resource.getText;

    @bean()
    class Ksu005bViewModel extends ko.ViewModel {
        currentScreen: any = null;
        items: KnockoutObservableArray<any>;
        persons: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        columns3: KnockoutObservableArray<NtsGridListColumn>;
        selectedCode: KnockoutObservable<any> = ko.observable();
        selectedPerson: KnockoutObservable<any> = ko.observable();
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        addSelected: any;
        outputSettingModel: KnockoutObservable<OutputSettingModel> = ko.observable(new OutputSettingModel());
        lstAddColInfo: KnockoutObservableArray<any>;

        itemsSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        columns2: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any> = ko.observableArray([]);

        lstWorkInfo: KnockoutObservableArray<any>;
        workSelected: any;
        checked: KnockoutObservable<boolean>;

        itemList: KnockoutObservableArray<ScreenItem>;

        countNumberRow: number = 1;
        checkAll: KnockoutObservable<any> = ko.observable(false); 

        constructor() {            
            super();
            const self = this;
            self.items = ko.observableArray([
                { code: "1", name: '四捨五入' },
                { code: "2", name: '切り上げ' },
                { code: "3", name: '切り捨て' }
            ]);

            self.persons = ko.observableArray([
                { id: "1", name: '四捨五入' },
                { id: "2", name: '切り上げ' },
                { id: "3", name: '切り捨て' }
            ]);

            self.columns = ko.observableArray([
                { headerText: getText('KSU005_18'), key: 'code', width: 60 },
                { headerText: getText('KSU005_19'), key: 'name', width: 150}
            ]);

            self.columns2 = ko.observableArray([
                { headerText: getText('KSU005_43'), key: 'name', width: 130 },
                { headerText: 'No', key: 'id', width: 50, hidden: true }, 
            ]);
 
            self.columns3 = ko.observableArray([     
                { headerText: "", key: 'id', width: 30},          
                { headerText: getText('KSU005_48'), key: 'name', width: 150}
            ]);

            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('KSU005_23'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('KSU005_24'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('KSU005_25'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)}
            
            ]);

            let arr = [
                {id:1, name:getText('KSU005_73')},
                {id:2, name:getText('KSU005_73')},
                {id:3, name:getText('KSU005_73')},
                {id:4, name:getText('KSU005_73')},
                {id:5, name:getText('KSU005_73')}
            ]

            self.itemsSwap(arr);
            self.selectedTab = ko.observable('tab-1');

            self.lstAddColInfo = ko.observableArray([
                { code: '0', name: getText('KSU005_27') },
                { code: '1', name: getText('KSU005_28') }
            ]);
            self.addSelected = ko.observable(0);
            self.checked = ko.observable(false);

            self.lstWorkInfo = ko.observableArray([
                { code: '0', name: getText('KSU005_73') },
                { code: '1', name: getText('KSU005_74') }
            ]);
            self.workSelected = ko.observable(0);
            $("#fixed-table").ntsFixedTable({ height: 400, width: 500 });        
            
            self.itemList = ko.observableArray([
                new ScreenItem(true, self.countNumberRow)
                
                
            ]);

            self.checkAll.subscribe((value) =>{
                let temp = self.itemList();
                if (value) {
                    for (let i = 1; i < temp.length; i++) {
                        temp[i].checked(true);
                    }
                    self.itemList(temp);
                } else {
                    for (let i = 1; i < temp.length; i++) {
                        temp[i].checked(false);
                    }
                    self.itemList(temp);
                }

            });
        }

        addItem(){
            var self = this;
            self.countNumberRow = self.countNumberRow + 1;
            self.itemList.push(new ScreenItem(false, self.countNumberRow));    
        }
        
        removeItem(){
            var self = this;
            var evens = _.remove(self.itemList(), function(n) {
                return !n.checked();
              });
              for (let i = 0; i < evens.length; i++) {
                evens[i].text(i + 1);
              }
            self.countNumberRow = evens.length;
            self.itemList(evens);    
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        openDialog(): void {
            let self = this;				
            self.currentScreen = nts.uk.ui.windows.sub.modal('/view/ksu/005/c/index.xhtml');
        }

        mounted(){
            $("#swap-list-grid2_name").html(getText('KSU005_45'));
        }
    }

    class ScreenItem {
        text: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;
        
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isNumberOne: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor(isNumberOne: any, text: number) {
            var self = this;
            self.text = ko.observable(text);
            self.checked = ko.observable(false);
            self.isNumberOne = ko.observable(isNumberOne);
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給')
            ]);
            self.selectedCode = ko.observable('1');
        }
    }

    interface IOutputSettingModel {
        code: string;
        name: string;
    }
    class OutputSettingModel {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(params?: IOutputSettingModel){
            const self = this;
            if(params) {
                self.code(params.code);
                self.name(params.name);
            }
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
}