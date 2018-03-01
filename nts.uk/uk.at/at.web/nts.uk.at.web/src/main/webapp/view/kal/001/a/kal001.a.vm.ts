module nts.uk.at.view.kal001.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import service = nts.uk.at.view.kal001.a.service;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        
        // search component
        ccgcomponent: GroupOption;
        baseDateSearch: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

        // employee list component
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;     

        
        // right component
        alarmCombobox: KnockoutObservableArray<any> = ko.observableArray([]);
        currentAlarmCode : KnockoutObservable<string> = ko.observable('');
        periodByCategory : KnockoutObservableArray<PeriodByCategory> = ko.observableArray([]);
        checkAll  : KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            
            //search component
            self.selectedEmployee = ko.observableArray([]);
            self.baseDateSearch = ko.observable(new Date());            
            self.ccgcomponent = {
               baseDate: self.baseDateSearch,
               //Show/hide options
               isQuickSearchTab: true,
               isAdvancedSearchTab: true,
               isAllReferableEmployee: true,
               isOnlyMe: true,
               isEmployeeOfWorkplace: true,
               isEmployeeWorkplaceFollow: true,
               isMutipleCheck: true,
               isSelectAllEmployee: true,

               onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                   self.employeeList(_.map(dataList, (data) =>{  return new UnitModelDto(data);}));
                   console.log(self.employeeList());
               },
               onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                   var dataEmployee: EmployeeSearchDto[] = [];
                   dataEmployee.push(data);
                   self.employeeList(_.map(dataEmployee, (data) =>{  return new UnitModelDto(data);}));
                   console.log(self.employeeList());
               },
               onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                   self.employeeList(_.map(dataList, (data) =>{  return new UnitModelDto(data);}));
                   console.log(self.selectedEmployee());
               },
               onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                   self.employeeList(_.map(dataList, (data) =>{  return new UnitModelDto(data);}));
                   console.log(self.employeeList());
               },
               onApplyEmployee: function(dataList: EmployeeSearchDto[]) {
                   self.employeeList(_.map(dataList, (data) =>{  return new UnitModelDto(data);}));
                   console.log(self.employeeList());
               }

            }
                  
            
            
            // employee list component
           // self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                {code: '1', isAlreadySetting: true},
                {code: '2', isAlreadySetting: true}
            ]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(false);
            this.employeeList = ko.observableArray<UnitModel>([]);
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.multiSelectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                maxRows  : 22
            };
        
            
                   
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });
            service.getAlarmByUser().done((alarmData)=>{
                
                self.alarmCombobox(alarmData);                
                self.currentAlarmCode(self.alarmCombobox()[0].alarmCode);
                
                service.getCheckConditionTime(self.currentAlarmCode()).done((checkTimeData)=>{
                    self.periodByCategory(_.map((checkTimeData), (item) =>{
                        return new PeriodByCategory(item);
                    }));
                    self.alarmCodeChange();
                    dfd.resolve();
                }).fail((errorCheckTime) =>{
                    alertError(errorCheckTime);
                });
                
            }).fail((errorAlarm)=>{
                 alertError(errorAlarm);
            });
            

            return dfd.promise();
        }
        
        public alarmCodeChange(): void{
            let self = this;
            self.currentAlarmCode.subscribe((newCode)=>{
                    service.getCheckConditionTime(newCode).done((checkTimeData)=>{
                        self.periodByCategory(_.map((checkTimeData), (item) =>{
                            return new PeriodByCategory(item);
                        }));
                    });    
            });
        }
        

        public clickCheckAll(): void{
            let self = this;
            self.checkAll(!self.checkAll());
            if(self.checkAll()){
                let periodArr = self.periodByCategory();
                periodArr.forEach((p: PeriodByCategory) =>{
                    p.checkBox(true);
                });
                self.periodByCategory(periodArr);    
            }else{
                let periodArr = self.periodByCategory();
                periodArr.forEach((p: PeriodByCategory) =>{
                    p.checkBox(false);
                });
                self.periodByCategory(periodArr);        
            }
            return;  
        }
        
        public checkBoxAllOrNot(checkBox: boolean): void{
            var self = this;
            if(checkBox && self.periodByCategory().filter(e => e.checkBox()==checkBox).length == self.periodByCategory().length)
                self.checkAll(true);
            else
                self.checkAll(false);

            
                            
        }
        
        public open_Dialog(): any {
            let self = this;
            nts.uk.ui.windows.setShared("alarmCode", self.currentAlarmCode());
            modal("/view/kal/001/b/index.xhtml").onClosed(() => {
                
            });
        }

    }
    
    
    export class PeriodByCategory{
        category : number;
        categoryName: string;
        startDate : KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        checkBox: KnockoutObservable<boolean>;
        startMonth: KnockoutObservable<string>;
        endMonth: KnockoutObservable<string>;
        constructor(dto:  service.CheckConditionTimeDto){
            this.category = dto.category;
            this.categoryName = dto.categoryName;
            this.startDate = ko.observable(dto.startDate);
            this.endDate = ko.observable(dto.endDate);
            this.startMonth = ko.observable(dto.startMonth);
            this.endMonth = ko.observable(dto.endMonth);
            this.checkBox = ko.observable(false);
        }
        
        public setClick() : void{
            this.checkBox(!this.checkBox());    
            __viewContext["viewmodel"].checkBoxAllOrNot(this.checkBox());
        }
        
        public  getFormattedDate(date : number) : string {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();
        
            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;
        
            return [year, month, day].join('/');
        }
    }
    
    
    
    // employee list component
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
        export class UnitModelDto implements UnitModel{
            code: string;
            name: string;
            workplaceName: string;
            isAlreadySetting: boolean;
            constructor(employee : EmployeeSearchDto){
                this.code = employee.employeeCode;
                this.name = employee.employeeName;
                this.workplaceName = employee.workplaceName;    
            }            
        }    
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }
    
    
    // search component
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

