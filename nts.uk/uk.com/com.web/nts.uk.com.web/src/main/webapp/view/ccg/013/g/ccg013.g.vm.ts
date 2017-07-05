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
        titleBar: KnockoutObservable<any>;
        dataItems: KnockoutObservableArray<ItemModel>;

        empItems: KnockoutObservableArray<PersonModel>;
        empSelectedItem: KnockoutObservable<any>;

        

        constructor() {
            let self = this;
            
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedNewCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedNewCodes = ko.observableArray([]);   
                     
            self.titleBar = ko.observable(null);
            
            self.allItems = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.dataItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key:'code', width: 55 },
                { headerText: '名称', prop: 'name', key:'name', width: 167 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            self.newColumns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 55 },
                { headerText: '対象メニュー', prop: 'targetItem', width: 160 },
                { headerText: '表示名称', prop: 'name', width: 160 },
                { headerText: 'pk', prop: 'primaryKey', key:'primaryKey', width: 1, hidden: true }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.newCurrentCodeList = ko.observableArray([]);

            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            
            

        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            self.empItems.removeAll();

            /**
             * Demo EmployeeCode & EmployeeId
             */
            _.range(30).map(i => {
                i++;
                if (i < 10) {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A00000000' + i,
                        name: '日通　社員' + i,
                    }));
                } else {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A0000000' + i,
                        name: '日通　社員' + i,
                    }));
                }
            });

            dfd.resolve();
            return dfd.promise();
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

    export module model {

    }
}