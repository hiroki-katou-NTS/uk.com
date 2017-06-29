 module nts.uk.com.view.ccg013.d.viewmodel {  
    export class ScreenModel {    
        singleSelectedCode: any;
        singleSelectedNewCode: any;
        selectedCodes: any;
        selectedNewCodes: any;
        headers: any;
        items: KnockoutObservableArray<ItemModel>;
        newItems: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        newColumns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        newCurrentCodeList: KnockoutObservableArray<any>;
        titleBar: KnockoutObservable<any>;
        dataItems: KnockoutObservableArray<DataModel>;
        
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
                     
            self.titleBar = ko.observable(null);
            
            this.items = ko.observableArray([]);
            this.newItems = ko.observableArray([]);
            this.dataItems = ko.observableArray([]);
            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key:'code', width: 60 },
                { headerText: '名称', prop: 'name', key:'name', width: 200 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            this.newColumns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '対象メニュー', prop: 'targetItem', width: 160 },
                { headerText: '表示名称', prop: 'name', width: 160 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
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
                self.findBySystem(Number(newValue));
            });
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            self.titleBar(nts.uk.ui.windows.getShared("titleBar"));

            $.when(self.findBySystem(0)).done(function(){
                dfd.resolve();   
            }).fail(function() {
                dfd.reject();    
            });
            
            return dfd.promise();
        }
        
        /**
         * Get data by system id from db
         */
        findBySystem(systemValue):JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.findBySystem(systemValue).done(function(data) {
                    var list001: Array<ItemModel> = [];
                    var index = 0;
                    _.forEach(data, function(item) {
                        var id = nts.uk.util.randomId();
                        list001.push(new ItemModel(id, item.code, item.targetItems, item.displayName, index));
                        index++;
                    });
                    
                    self.items(list001);
                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);    
                });
            
            return dfd.promise();
        }
        
        /**
         * Add items selected from left grid list to right grid list
         */
        add(): void{
            var self = this;
            
            var newItems = [];
            _.forEach(self.items(), function(item: ItemModel) {
                if (_.indexOf(self.currentCodeList(), item.primaryKey) !== -1) {
                    item.order = self.newItems().length + 1;
                    item.primaryKey = item.code + item.order;
                    self.newItems.push(new ItemModel(item.primaryKey, item.code, item.targetItem, item.name, item.order));
                } 
            })

            self.currentCodeList([]);
            self.newCurrentCodeList([]);
        }
        
        /**
         * Remove items selected in right grid list
         */
        remove(): void{
            var self = this;
            var newItems = self.newItems();
            _.remove(newItems, function(currentObject: ItemModel) {
                return _.indexOf(self.newCurrentCodeList(), currentObject.primaryKey) !== -1;
            });
            
            self.newItems([]);
            _.forEach(newItems, function(item) {
                item.order = self.newItems().length + 1;
                self.newItems.push(item);
            })            
        }
                
        /**
         * Pass data to main screen
         * Close the popup
         */
        submit() {
            var self = this;
            var index = 0;
            var currentData = self.newItems();
            var currentSystem = self.selectedSystemCode();
            
            _.forEach(currentData, function(item) {
                var pk = item.code + index;
                self.dataItems.push(new DataModel(pk, Number(currentSystem), item.code, item.targetItem, item.name, index));
                index++;
            })
            
            nts.uk.ui.windows.setShared("CCG013D_MENUS", self.dataItems());
            
            self.closeDialog();
        }
        
        /**
         * Click on button d1_26
         * Close the popup
         */
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
 
    export class ItemModel {
        primaryKey: string;
        code: string;
        targetItem: string;
        name: string;
        order: number;
        
        constructor(id: string, code: string, targetItem: string, name: string, order: number) {
            this.primaryKey = code + order;
            this.code = code;
            this.targetItem = targetItem;
            this.name = name;
            this.order = order;       
        }
    } 
     
    export class DataModel {
        primaryKey: string;
        system: number;
        code: string;
        targetItem: string;
        name: string;
        order: number;
        
        constructor(primaryKey: string, system: number, code: string, targetItem: string, name: string, order: number) {
            this.primaryKey = primaryKey;
            this.system = system;
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