module cmm018.n.viewmodel {
    export class ScreenModel {
        //Right table's properties.
        items2: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList2: KnockoutObservableArray<any>;
        count: number = 100;

        //Left filter area
        ccgcomponent: GroupOption;

        // Options
        baseDate: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

        constructor() {
            var self = this;

            //Init right table.
            self.items2 = ko.observableArray([]);

            self.columns2 = ko.observableArray([
                { headerText: '社員CD', key: 'code', width: 30, hidden: true},
                { headerText: '申請一覧', key: 'name', width: 200 }
            ]);

            self.currentCodeList2 = ko.observableArray([]);

            //Init selectedEmployee
            self.selectedEmployee = ko.observableArray([]);
            self.baseDate = ko.observable(new Date());
        }

        loadGrid() {
            var self = this;
            self.ccgcomponent = {
                baseDate: self.baseDate,
                //Show/hide options
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: false,
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
                onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                    self.selectedEmployee(dataList);
                },
                onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                    var dataEmployee: EmployeeSearchDto[] = [];
                    dataEmployee.push(data);
                    self.selectedEmployee(dataEmployee);
                },
                onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                    self.selectedEmployee(dataList);
                },
                onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                    self.selectedEmployee(dataList);
                },
                onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                    self.selectedEmployee(dataEmployee);
                }

            }

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        }

        getRightList() {
            let self = this;
            var dfd = $.Deferred();
            service.getRightList().done(function(data) {
                let items = _.map(data, item => {
                    return new ItemModel(item);
                });
                self.items2(items);

                dfd.resolve();
            });
            return dfd.promise();
        }

        start() {
            let self = this;
            let dfd = $.Deferred();
            $.when(self.getRightList()).done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }
    }

    export class ItemModel {
        code: string;
        name: string;
        constructor(x: IItemModel) {
            this.code = x.value;
            this.name = x.localizedName;
        }
    }
    
    export interface IItemModel {
        value: string;
        localizedName: string;
    }
    
    export interface EmployeeSearchDto {
        employeeId: string;

        employeeCode: string;

        employeeName: string;

        workplaceCode: string;

        workplaceId: string;

        workplaceName: string;
    }

    export interface GroupOption {
        baseDate?: KnockoutObservable<Date>;
        // クイック検索タブ
        isQuickSearchTab: boolean;
        // 参照可能な社員すべて
        isAllReferableEmployee: boolean;
        //自分だけ
        isOnlyMe: boolean;
        //おなじ部門の社員
        isEmployeeOfWorkplace: boolean;
        //おなじ＋配下部門の社員
        isEmployeeWorkplaceFollow: boolean;


        // 詳細検索タブ
        isAdvancedSearchTab: boolean;
        //複数選択 
        isMutipleCheck: boolean;

        //社員指定タイプ or 全社員タイプ
        isSelectAllEmployee: boolean;

        onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

        onSearchOnlyClicked: (data: EmployeeSearchDto) => void;

        onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;

        onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;

        onApplyEmployee: (data: EmployeeSearchDto[]) => void;
    }
}
