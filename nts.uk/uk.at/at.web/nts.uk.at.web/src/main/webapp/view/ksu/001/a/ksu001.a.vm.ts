module ksu001.a.viewmodel {
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
        ccgcomponent: GroupOption;
        selectedCode: KnockoutObservableArray<any>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        selectedEmployee: KnockoutObservableArray<any>;
        isShow: KnockoutObservable<boolean>;
        
        //Grid list A2_4
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        
        //Date time
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;
        
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        
        roundingRules1: KnockoutObservableArray<any>;
        selectedRuleCode1: any;
        
        roundingRules2: KnockoutObservableArray<any>;
        selectedRuleCode2: any;

        constructor() {
            let self = this;
            self.ccgcomponent = ko.observable();
            self.selectedCode = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(true);
            self.selectedEmployee = ko.observableArray([]);
            self.isShow = ko.observable(false);
            //Employee 
            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            self.items = ko.observableArray([]);
            
            //Date time
            self.dateTimePrev = ko.observable('2017/04/01');
            self.dateTimeAfter = ko.observable('2017/04/01');
            
            //Grid list
            for(let i = 1; i <= 12; i++) {
                self.items.push(new ItemModel('00' + i, '基本給' + i, '00' + i));
            }
            self.columns = ko.observableArray([ 
                { headerText: nts.uk.resource.getText("KSU001_19"), key: 'code', width: 50 },
                { headerText: nts.uk.resource.getText("KSU001_20"), key: 'name', width: 150 },
                { headerText: 'コード', key: 'id', width: 50, hidden: true }, 
            ]);
            self.currentCodeList = ko.observableArray([]);
            // Fire event.
            $("#multi-list").on('itemDeleted', (function(e: Event) {
                alert("Item is deleted in multi grid is " + e["detail"]["target"]);
            }));
            //Switch button
            self.roundingRules = ko.observableArray([
            { code: '1', name: '抽出' },
            { code: '2', name: '２８日' },
            { code: '3', name: '末日' }]);
            self.selectedRuleCode = ko.observable(1);
            
            self.roundingRules1 = ko.observableArray([
            { code: '1', name: '略名' },
            { code: '2', name: '時刻' },
            { code: '3', name: '記号' }]);
            self.selectedRuleCode1 = ko.observable(1);
            
            self.roundingRules2 = ko.observableArray([
            { code: '1', name: '予定' },
            { code: '2', name: '実績' }]);
            self.selectedRuleCode2 = ko.observable(1);
            
            //popup 1
            $('#popup-area2').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.create')
                }
            });

            $('.create').click(function() {
                $('#popup-area2').toggle();
            });
            
            //popup 2
            $('#popup-area3').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.check')
                }
            });

            $('.check').click(function() {
                $('#popup-area3').toggle();
            });
            
            //popup 3
            $('#popup-area4').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.vacation')
                }
            });

            $('.vacation').click(function() {
                $('#popup-area4').toggle();
            });
            
            //popup 4
            $('#popup-area5').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.setting')
                }
            });

            $('.setting').click(function() {
                $('#popup-area5').toggle();
            });
            
            //popup 5
//            $('#popup-area6').ntsPopup({
//                position: {
//                    my: 'left top',
//                    at: 'left bottom',
//                    of: $('.setting-button')
//                }
//            });
//
//            $('.setting-button').click(function() {
//                $('#popup-area6').toggle();
//            });
        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            self.initCCG001();
            dfd.resolve();
            return dfd.promise();
        }

        initCCG001() {
            let self = this;
            self.ccgcomponent = {
                baseDate: ko.observable(new Date()),
                // Show/hide options 
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: true,

                //Event options
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
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
    
      class ItemModel {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
    export module model {
    }
}