 module nts.uk.com.view.ccg013.d.viewmodel {  
    export class ScreenModel {    
        singleSelectedCode: any;
        singleSelectedNewCode: any;
        selectedCodes: any;
        selectedNewCodes: any;
        headers: any;
        items: KnockoutObservableArray<ItemModel>;
        newItems: KnockoutObservableArray<NewItemModel>;
        columns: KnockoutObservableArray<any>;
        newColumns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        newCurrentCodeList: KnockoutObservableArray<any>;
        
        //Dropdownlist contain System data
        systemList: KnockoutObservableArray<SystemModel>;
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<string>;
        
        constructor() {
            var self = this;         
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedNewCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedNewCodes = ko.observableArray([]);            
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]);
            
            this.items = ko.observableArray([]);
            this.newItems = ko.observableArray([]);
            
            var str = ['a0', 'b0', 'c0', 'd0'];
            
            for(var j = 0; j < 4; j++) {
                for(var i = 1; i < 51; i++) {    
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;         
                    this.items.push(new ItemModel(code,code));
                } 
            }
            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 200 }
            ]);
            
            this.newColumns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '対象メニュー', prop: 'targetItem', width: 160 },
                { headerText: '表示名称', prop: 'name', width: 160 }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.newCurrentCodeList = ko.observableArray([]);
            
            //Set System data to dropdownlist
            self.systemList = ko.observableArray([
                new SystemModel('0', '共通'),
                new SystemModel('1', '勤次郎'),
                new SystemModel('2', 'オフィスヘルパー'),
                new SystemModel('3', 'Ｑ太郎'),
                new SystemModel('4', '人事郎'),
            ]);
            
            self.systemName = ko.observable('');
            self.currentSystemCode = ko.observable(0);
            self.selectedSystemCode = ko.observable('0')
            
            //Get data by system
            self.selectedSystemCode.subscribe(function(newValue) {
                service.findBySystem(Number(newValue)).done(function(data) {
                    var list001: Array<ItemModel> = [];
                    
                    _.forEach(data, function(item) {
                        list001.push(new ItemModel(item.code, item.displayName));
                    });
                    
                    self.items(list001);
                });
            });
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.findBySystem(0).done(function(data) {
                    var list001: Array<ItemModel> = [];
                    
                    _.forEach(data, function(item) {
                        list001.push(new ItemModel(item.code, item.displayName));
                    });
                    
                    self.items(list001);
                    dfd.resolve(data);
                }).fail(function(res) {
            });
            
            return dfd.promise();
        }
        
        newData(): void{
            var self = this;
            self.newItems();
        }
        
        add(): void{
            var self = this;
        }
        
        remove(): void{
            var self = this;
        }
        
        up(): void{
            var self = this;
        }
        
        down(): void{
            var self = this;
        }
        
        /**
         * Click on button d1_26
         * Close the popup
         */
        closeDialog() {
            nts.uk.ui.windows.close();
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
     
    class NewItemModel {
        code: string;
        targetItem: string;
        name: string;
        order: number;
        constructor(code: string, targetItem: string, name: string, order: number) {
            this.code = code;
            this.targetItem = targetItem;
            this.name = name;
            this.order = order;       
        }
    } 
     
    class SystemModel {
        systemCode: string;
        systemName: string;
        
        constructor(systemCode: string, systemName: string) {
            this.systemCode = systemCode;
            this.systemName = systemName;
        }
    }
 }