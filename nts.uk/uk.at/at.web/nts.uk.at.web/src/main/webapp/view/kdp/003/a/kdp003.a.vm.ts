module nts.uk.at.view.kdp003.a {
    import service = nts.uk.at.view.kdp003.a.service;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        
        const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";
        
        export class ScreenModel {
           
            // CCG001
            ccg001ComponentOption: GroupOption;
            
            datepickerValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;            
            
            // KCP005 start
            listComponentOption: any;
            selectedCodeEmployee: KnockoutObservableArray<string>;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            showOptionalColumn: KnockoutObservable<boolean>;
            // KCP005 end
            
            
            itemListCbb1: KnockoutObservableArray<ItemModel>;
            selectedOutputItemCode: KnockoutObservable<string>;
            
            checkedCardNOUnregisteStamp: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.declareCCG001();
               
                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.datepickerValue = ko.observable({});
                
                self.selectedCodeEmployee = ko.observableArray(['1','2']);
                self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([
                    {code: '1', isAlreadySetting: true},
                    {code: '2', isAlreadySetting: true}
                ]);
                self.isDialog = ko.observable(true);
                self.isShowNoSelectRow = ko.observable(true);
                self.isMultiSelect = ko.observable(true);
                self.isShowWorkPlaceName = ko.observable(true);
                self.isShowSelectAllButton = ko.observable(false);
                self.showOptionalColumn = ko.observable(false);
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.NO_SELECT,
                    selectedCode: self.selectedCodeEmployee,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: self.isShowSelectAllButton(),
                    showOptionalColumn: self.showOptionalColumn(),
                    maxRows: 15
                };
                
                self.itemListCbb1 = ko.observableArray([
                    new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給ながい文字列なが')
                ]);
        
                self.selectedOutputItemCode = ko.observable('1');
                
                self.checkedCardNOUnregisteStamp = ko.observable(false);
                
                self.subscribeEvent();
                self.bindingCondition();
            }
            
            /**
            * start screen
            */
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                let self = this,
                    companyId: string = __viewContext.user.companyId,
                    userId: string = __viewContext.user.employeeId;
                
                $.when(service.getDataStartPage(), service.restoreCharacteristic(companyId, userId))
                                    .done((dataStartPage, dataCharacteristic) => {
                    // get data from server
                    self.startDateString(dataStartPage.startDate);
                    self.endDateString(dataStartPage.endDate);
                    self.ccg001ComponentOption.periodStartDate = moment.utc(dataStartPage.startDate, DATE_FORMAT_YYYY_MM_DD).toISOString();
                    self.ccg001ComponentOption.periodEndDate = moment.utc(dataStartPage.endDate, DATE_FORMAT_YYYY_MM_DD).toISOString();
                                        
                    // get data from characteris
                    self.checkedCardNOUnregisteStamp(dataCharacteristic.cardNumNotRegister);
                    self.selectedOutputItemCode(dataCharacteristic.outputSetCode);
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            /**
            * binding component CCG001 and KCP005
            */
            public executeComponent(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                blockUI.grayout();
                $.when($('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption), 
                        $('#employee-list').ntsListComponent(self.listComponentOption)).done(() => {    
                        blockUI.clear();
                   dfd.resolve();     
                });
                return dfd.promise();
            }
            
            private declareCCG001(): void {
                let self = this;
                // Set component option
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2,
                    showEmployeeSelection: false,
                    showQuickSearchTab: true,
                    showAdvancedSearchTab: true,
                    showBaseDate: false,
                    showClosure: false,
                    showAllClosure: false,
                    showPeriod: true,
                    periodFormatYM: false,
                    
                    /** Required parameter */
                    baseDate: moment().toISOString(),
                    periodStartDate: moment().toISOString(),
                    periodEndDate: moment().toISOString(),
                    inService: true,
                    leaveOfAbsence: true,
                    closed: true,
                    retirement: false,
                    
                    /** Quick search tab options */
                    showAllReferableEmployee: true,
                    showOnlyMe: true,
                    showSameWorkplace: true,
                    showSameWorkplaceAndChild: true,
                    
                    /** Advanced search properties */
                    showEmployment: true,
                    showWorkplace: true,
                    showClassification: true,
                    showJobTitle: true,
                    showWorktype: false,
                    isMutipleCheck: true,
                    
                    /**
                    * Self-defined function: Return data from CCG001
                    * @param: data: the data return from CCG001
                    */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        let arrEmployeelst: UnitModel[] = [];
                        _.forEach(data.listEmployee, function(value) {
                            arrEmployeelst.push({ code: value.employeeCode, name: value.employeeName, workplaceName: value.workplaceName });
                        });
                        self.employeeList(arrEmployeelst);
                    }
                }    
            }
            
            /**
            * Export excel
            */
            private exportExcel(): void {
                let self = this,
                    companyId: string = __viewContext.user.companyId,
                    userId: string = __viewContext.user.employeeId;
                
                OutputConditionEmbossing outputConditionEmbossing = new OutputConditionEmbossing(userId, self.selectedOutputItemCode(), self.checkedCardNOUnregisteStamp());
                service.saveCharacteristic(companyId, userId, outputConditionEmbossing);        
            }
            
            /**
            * Open screen C
            */
            private openPreviewScrC(): void {
                    
            }
            
            /**
            * Open screen B
            */
            private openScrB(): void {
                
            }
            
            /**
            * Control display and active
            */
            private bindingCondition(): void {
                let self = this;    
                
                self.checkedCardNOUnregisteStamp.subscribe((newValue) => {
                    if (newValue) {
                        
                    } else {
                        
                    }
                })
            }
            
            /**
            * Subscribe Event
            */
            private subscribeEvent(): void {
                let self = this;
                self.selectedCodeEmployee.subscribe(function(value) {
                })
                
                self.startDateString.subscribe(function(value){
                    self.datepickerValue().startDate = value;
                    self.datepickerValue.valueHasMutated();        
                });
                
                self.endDateString.subscribe(function(value){
                    self.datepickerValue().endDate = value;   
                    self.datepickerValue.valueHasMutated();      
                });
            }
        }
        
        
        export interface GroupOption {
            /** Common properties */
            showEmployeeSelection?: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab?: boolean; // クイック検索
            showAdvancedSearchTab?: boolean; // 詳細検索
            showBaseDate?: boolean; // 基準日利用
            showClosure?: boolean; // 就業締め日利用
            showAllClosure?: boolean; // 全締め表示
            showPeriod?: boolean; // 対象期間利用
            periodFormatYM?: boolean; // 対象期間精度
            isInDialog?: boolean;
        
            /** Required parameter */
            baseDate?: string; // 基準日
            periodStartDate?: string; // 対象期間開始日
            periodEndDate?: string; // 対象期間終了日
            inService: boolean; // 在職区分
            leaveOfAbsence: boolean; // 休職区分
            closed: boolean; // 休業区分
            retirement: boolean; // 退職区分
        
            /** Quick search tab options */
            showAllReferableEmployee?: boolean; // 参照可能な社員すべて
            showOnlyMe?: boolean; // 自分だけ
            showSameWorkplace?: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員
        
            /** Advanced search properties */
            showEmployment?: boolean; // 雇用条件
            showWorkplace?: boolean; // 職場条件
            showClassification?: boolean; // 分類条件
            showJobTitle?: boolean; // 職位条件
            showWorktype?: boolean; // 勤種条件
            isMutipleCheck?: boolean; // 選択モード
            isTab2Lazy?: boolean;
        
            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }
        
        export interface EmployeeSearchDto {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceId: string;
            workplaceName: string;
        }
        
        export interface Ccg001ReturnedData {
            baseDate: string; // 基準日
            closureId?: number; // 締めID
            periodStart: string; // 対象期間（開始)
            periodEnd: string; // 対象期間（終了）
            listEmployee: Array<EmployeeSearchDto>; // 検索結果
        }
        
        
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
        
        export class ItemModel {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class OutputConditionEmbossing {
            userID: string;
            outputSetCode: string;
            cardNumNotRegister: boolean;
            
            constructor(userID: string, outputSetCode: string, cardNumNotRegister: boolean) {
                this.userID = userID;
                this.outputSetCode = outputSetCode;
                this.cardNumNotRegister = cardNumNotRegister;
            }
        }
        
        class OutputConditionOfEmbossingDto {
            startDate: string;
            endDate: string;
            lstStampingOutputItemSetDto: StampingOutputItemSetDto[];
            
            constructor(startDate: string, endDate: string, lstStampingOutputItemSetDto: StampingOutputItemSetDto[]) {
                this.startDate = startDate;
                this.endDate = endDate;
                this.lstStampingOutputItemSetDto = lstStampingOutputItemSetDto;
            }
        }
        
        class StampingOutputItemSetDto {
            stampOutputSetName: string;
            stampOutputSetCode: string;
            
            constructor(stampOutputSetName: string, stampOutputSetCode: string) {
                this.stampOutputSetName = stampOutputSetName;
                this.stampOutputSetCode = stampOutputSetCode;
            }
        }
    }
}