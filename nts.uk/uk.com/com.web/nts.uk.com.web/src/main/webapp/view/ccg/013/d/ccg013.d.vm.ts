 module nts.uk.com.view.ccg013.d.viewmodel {  
    export class ScreenModel {    
        singleSelectedCode: any;
        singleSelectedNewCode: any;
        selectedCodes: any;
        selectedNewCodes: any;
        headers: any;
        allItems: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        newItems: KnockoutObservableArray<ItemModel>;
        tempItems: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        newColumns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        newCurrentCodeList: KnockoutObservableArray<any>;
        titleBar: KnockoutObservable<any>;
        dataItems: KnockoutObservableArray<ItemModel>;
        
        //Dropdownlist contain System data
        systemList: KnockoutObservableArray<SystemModel>;
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<number>;
        
        disableSwap: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;         
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedNewCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedNewCodes = ko.observableArray([]);   
                     
            self.titleBar = ko.observable(null);
            
            self.allItems = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.tempItems = ko.observableArray([]);
            self.dataItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_49"), prop: 'code', key:'code', width: 55, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_50"), prop: 'name', key:'name', width: 167, formatter: _.escape },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            self.newColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_51"), prop: 'code', width: 55, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_52"), prop: 'targetItem', width: 160, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_53"), prop: 'name', width: 160, formatter: _.escape },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.newCurrentCodeList = ko.observableArray([]);
            
            //Set System data to dropdownlist
            self.systemList = ko.observableArray([
                new SystemModel(0, nts.uk.resource.getText("Enum_System_COMMON")),
                new SystemModel(1, nts.uk.resource.getText("Enum_System_TIME_SHEET")),
              
            ]);
            
            self.systemName = ko.observable('');
            self.currentSystemCode = ko.observable(0);
            self.selectedSystemCode = ko.observable(0);
            
            //Get data by system
            self.selectedSystemCode.subscribe(function(newValue) {
                self.findBySystem(Number(newValue));
            });        
            
            self.disableSwap = ko.observable(false);
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            var titleBar = nts.uk.ui.windows.getShared("titleBar");
            self.titleBar(titleBar);

            $.when(self.findAllDisplay()).done(function(){
                
                if (titleBar.treeMenus) {
                    _.forEach(titleBar.treeMenus, function(item: any) {
                        var standardMenu = _.find(self.allItems(), function(standardMenuItem) {
                            return standardMenuItem.code == item.code && standardMenuItem.system == item.system && standardMenuItem.menu_cls == item.classification;
                        }); 
                        if (standardMenu) {
                            var order = self.newItems().length + 1;
                            var primaryKey = nts.uk.util.randomId();
                            var data = new ItemModel(primaryKey, standardMenu.code, standardMenu.targetItem, standardMenu.name, order, standardMenu.menu_cls, standardMenu.system);
                            self.newItems.push(data);
                            self.tempItems.push(data);
                        }       
                    });
                }
                
                self.disableSwapButton();
                
                dfd.resolve();   
                $("#combo-box").focus();
            }).fail(function() {
                dfd.reject();    
            });
            
            return dfd.promise();
        }
        
        /**
         * Disable swap button when right contents does not have any items
         */
        disableSwapButton(): void{
            var self = this;
            
            if(self.newItems().length === 0){
                self.disableSwap(false);
            } else {
                self.disableSwap(true);
            }
        }
        
        /**
         * Get data by system id from db
         */
        findAllDisplay():JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.findBySystem().done(function(data) {
                    var list001: Array<ItemModel> = [];
                    var index = 0;
                    _.forEach(data, function(item) {
                        var id = nts.uk.util.randomId();
                        self.allItems.push(new ItemModel(id, item.code, item.targetItems, item.displayName, index, item.classification, item.system));
                        if (item.system == self.selectedSystemCode()) {
                            list001.push(new ItemModel(id, item.code, item.targetItems, item.displayName, index, item.classification, item.system));
                        }
                    });
                    
                    self.items(list001);
                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);    
                });
            
            return dfd.promise();
        }
        
        findBySystem(system: number): void {
            var self = this;
            var list001: Array<ItemModel> = [];
            var index = 0;
            _.forEach(self.allItems(), function(item: ItemModel) {
                if ((item.system == self.selectedSystemCode() && item.menu_cls != Menu_Cls.TopPage) || (item.system == 0 && item.menu_cls == Menu_Cls.TopPage)) {
                    var id = nts.uk.util.randomId();
                    list001.push(new ItemModel(id, item.code, item.targetItem, item.name, index, item.menu_cls, item.system));
                    index++;
                }
            });
            
            self.items(list001);
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
                    item.primaryKey = nts.uk.util.randomId();
                    self.newItems.push(new ItemModel(item.primaryKey, item.code, item.targetItem, item.name, item.order, item.menu_cls, item.system));
                } 
            })

            self.currentCodeList([]);
            self.newCurrentCodeList([]);
            self.disableSwapButton();
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
            
            self.disableSwapButton();
        }
                
        /**
         * Pass data to main screen
         * Close the popup
         */
        submit() {
            var self = this;
            let index = 1;
            _.each(self.newItems(), (item) =>{
                item.order = index;
                index ++;
            });
            nts.uk.ui.windows.setShared("CCG013D_MENUS", self.newItems());
            
            nts.uk.ui.windows.close();
        }
        
        /**
         * Click on button d1_26
         * Close the popup
         */
        closeDialog() {
            var self = this;
            
            nts.uk.ui.windows.setShared("CCG013D_MENUS", self.tempItems());
            
            nts.uk.ui.windows.close();
        }
    }
 
    export class ItemModel {
        primaryKey: string;
        code: string;
        targetItem: string;
        name: string;
        order: number;
        menu_cls: number;
        system: number;
        
        constructor(id: string, code: string, targetItem: string, name: string, order: number, menu_cls: number, system: number) {
            this.primaryKey = id;
            this.code = code;
            this.targetItem = targetItem;
            this.name = name;
            this.order = order;       
            this.menu_cls = menu_cls;
            this.system = system;
        }
    } 
          
    class SystemModel {
        systemCode: number;
        systemName: string;
        
        constructor(systemCode: number, systemName: string) {
            this.systemCode = systemCode;
            this.systemName = systemName;
        }
    }
     
    enum Menu_Cls {
        Standard = 0,
        OptionalItemApplication,
        MobilePhone,
        Tablet,
        CodeName,
        GroupCompanyMenu,
        Customize,
        OfficeHelper,
        TopPage
    }
 }