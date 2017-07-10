module cmm044.g.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    

    export class ScreenModel {
        
        empItems: KnockoutObservableArray<PersonModel>;
        empSelectedItem: KnockoutObservable<any>;
        infoList: KnockoutObservable<any>;
        singleSelectedCode: any;
        singleSelectedNewCode: any;
        selectedCodes: any;
        selectedNewCodes: any;
        headers: any;
        allItems: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        newItems: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        newColumns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        newCurrentCodeList: KnockoutObservableArray<any>;
        dataItems: KnockoutObservableArray<ItemModel>;

        selectedSystemCode: KnockoutObservable<number>;
        
        //CGG001
        ccgcomponent: GroupOption;
        selectedCode: KnockoutObservableArray<string>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        // Options
        baseDate: KnockoutObservable<Date>;
        isQuickSearchTab: KnockoutObservable<boolean>;
        isAdvancedSearchTab: KnockoutObservable<boolean>;
        isAllReferableEmployee: KnockoutObservable<boolean>;
        isOnlyMe: KnockoutObservable<boolean>;
        isEmployeeOfWorkplace: KnockoutObservable<boolean>;
        isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
        isMutipleCheck: KnockoutObservable<boolean>;
        isSelectAllEmployee: KnockoutObservable<boolean>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        
        

        constructor() {
            let self = this;
            
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedNewCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedNewCodes = ko.observableArray([]);
            
            
                     
            self.empItems= ko.observableArray([]);
            self.allItems = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.dataItems = ko.observableArray([]);
            
            self.selectedSystemCode = ko.observable(0);
            self.empSelectedItem = ko.observable();
            
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'webMenuCode', key:'webMenuCode', width: 55 },
                { headerText: '名称', prop: 'webMenuName', key:'webMenuName', width: 167 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            self.newColumns = ko.observableArray([
                { headerText: 'コード', prop: 'webMenuCode', width: 55 },
                { headerText: '表示名称', prop: 'webMenuName', width: 160 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.newCurrentCodeList = ko.observableArray([]);
            self.selectedEmployee = ko.observableArray([]);
            
            self.showinfoSelectedEmployee = ko.observable(false);
            self.baseDate = ko.observable(new Date());
            self.infoList = nts.uk.ui.windows.getShared("CCG013G_WEB_MENU");
            
             _.forEach(self.infoList, function(item: ItemModel) {
                    item.order = self.newItems().length + 1;
                    item.primaryKey = item.webMenuCode + item.order;
                    self.items.push(new ItemModel(item.primaryKey, item.webMenuCode, item.webMenuName, item.order));
            })
            
            
            
            
        }
        start() {
            let self = this;
            
            self.initCCG001();
        }

         add(): void{
             
             
            var self = this;
            
            var newItems = [];
            _.forEach(self.infoList, function(item: ItemModel) {
                if (_.indexOf(self.currentCodeList(), item.primaryKey) !== -1) {
                    item.order = self.newItems().length + 1;
                    item.primaryKey = item.webMenuCode + item.order;
                    self.newItems.push(new ItemModel(item.primaryKey, item.webMenuCode, item.webMenuName, item.order));
                } 
            })

            self.currentCodeList([]);
            self.newCurrentCodeList([]);
        }
        
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
        
        initCCG001() {
            let self = this;
            self.ccgcomponent = {
               baseDate: ko.observable(new Date()),
               // Show/hide options 
               isQuickSearchTab: true,
               isAdvancedSearchTab:true,
               isAllReferableEmployee: true,
               isOnlyMe: true,
               isEmployeeOfWorkplace: true,
               isEmployeeWorkplaceFollow: true,
               isMutipleCheck: true,
               isSelectAllEmployee:true,
               
               onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                   self.searchEmployee(dataList);
               },
               onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                   self.showinfoSelectedEmployee(true);
                   var dataEmployee: EmployeeSearchDto[] = [];
                   dataEmployee.push(data);
                   self.searchEmployee(dataEmployee);
               },
               onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                   self.searchEmployee(dataList);
               },
               onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                   self.searchEmployee(dataList);
               },
               onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                   self.searchEmployee(dataEmployee);
               }
           }

           $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        
        }
        searchEmployee(dataEmployee: EmployeeSearchDto[]) {
            var self = this;
            self.empItems.removeAll();
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                self.empItems.push(new PersonModel({
                        personId: item.employeeId,
                        code: item.employeeCode,
                        name: item.employeeName,
                    }));    
            });    
        }

        
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    
 

    }

    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }

    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
        }
    }

      export class ItemModel {
        primaryKey: string;
        webMenuCode: string;
        webMenuName: string;
        order: number;

        
        constructor(id: string, webMenuCode: string, webMenuName: string, order: number) {
            this.primaryKey = webMenuCode + order;
            this.webMenuCode = webMenuCode;
            this.webMenuName = webMenuName;
            this.order= order;

        }
    } 

}