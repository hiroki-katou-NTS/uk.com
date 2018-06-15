module nts.uk.at.view.kwr006.a {

    import service = nts.uk.at.view.kwr006.a.service;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {
        
        export class ScreenModel {
            // datepicker A1_6 A1_7 A1_8
            enableDatePicker: KnockoutObservable<boolean>;
            requiredDatePicker: KnockoutObservable<boolean>;
            datepickerValue: KnockoutObservable<any>;
            startDatepicker: KnockoutObservable<string>;
            endDatepicker: KnockoutObservable<string>;
            
            dataOutputType: KnockoutObservableArray<any>;

            // switch button A6_2
            selectedDataOutputType: KnockoutObservable<number>;
            enableByOutputFormat: KnockoutObservable<boolean>;

            // dropdownlist A7_3
            itemListCodeTemplate: KnockoutObservableArray<ItemModel>;
            selectedCodeA7_3: KnockoutObservable<string>;
            
            // dropdownlist A9_2
            itemListTypePageBrake: KnockoutObservableArray<ItemModel>;
            selectedCodeA9_2: KnockoutObservable<number>;
            
            // start declare KCP005
            listComponentOption: any;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            // end KCP005
            
            checkedA10_2: KnockoutObservable<boolean>;
            checkedA10_3: KnockoutObservable<boolean>;
            checkedA10_4: KnockoutObservable<boolean>;
            checkedA10_5: KnockoutObservable<boolean>;
            checkedA10_6: KnockoutObservable<boolean>;
            
            checkedA11_3: KnockoutObservable<boolean>;
            checkedA11_4: KnockoutObservable<boolean>;
            checkedA11_5: KnockoutObservable<boolean>;
            checkedA11_6: KnockoutObservable<boolean>;
            checkedA11_7: KnockoutObservable<boolean>;
            checkedA11_8: KnockoutObservable<boolean>;
            checkedA11_9: KnockoutObservable<boolean>;
            checkedA11_10: KnockoutObservable<boolean>;
            checkedA11_11: KnockoutObservable<boolean>;
            
            //A10_6
             enableByCumulativeWkp: KnockoutObservable<boolean>;
            
            
             enableBtnConfigure: KnockoutObservable<boolean>;
            // start variable of CCG001
            ccg001ComponentOption: GroupOption;
            // end variable of CCG001
            constructor() {
                let self = this;
                self.defineDatasource();
                
                // datepicker A1_6 A1_7 A1_8
                self.enableDatePicker = ko.observable(true);
                self.requiredDatePicker = ko.observable(true);
                self. enableBtnConfigure = ko.observable(true);
                self.datepickerValue = ko.observable({});
                self.startDatepicker = ko.observable("");
                self.endDatepicker = ko.observable("");
    
                // switch button A6_2
                self.enableByOutputFormat = ko.observable(true);
                self.selectedDataOutputType = ko.observable(0);
                
                // dropdownlist A7_3
                self.itemListCodeTemplate = ko.observableArray([]);
                self.selectedCodeA7_3 = ko.observable(''); 
                
                self.selectedCodeA9_2 = ko.observable(1);
                
                //A10_2
                self.checkedA10_2 = ko.observable(false);
                self.checkedA10_3 = ko.observable(true);
                self.checkedA10_4 = ko.observable(false);
                self.checkedA10_5 = ko.observable(false);
                self.checkedA10_6 = ko.observable(true);
                self.enableByCumulativeWkp =ko.observable(true); 
                
                self.checkedA11_3 = ko.observable(true);
                self.checkedA11_4 = ko.observable(false);
                self.checkedA11_5 = ko.observable(false);
                self.checkedA11_6 = ko.observable(true);
                self.checkedA11_7 = ko.observable(false);
                self.checkedA11_8 = ko.observable(true);
                self.checkedA11_9 = ko.observable(false);
                self.checkedA11_10 = ko.observable(false);
                self.checkedA11_11 = ko.observable(false);
                
                // start define KCP005
                self.multiSelectedCode = ko.observableArray([]);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([]);
                self.isDialog = ko.observable(true);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(true);
                self.isShowWorkPlaceName = ko.observable(false);
                self.isShowSelectAllButton = ko.observable(false);
                self.employeeList = ko.observableArray<UnitModel>([]);

                self.defineComponentOption();
                self.initSubscribers();
            }

            private initSubscribers(): void {
                let self = this;
                self.startDatepicker.subscribe(function(value) {
                    self.datepickerValue().startDate = value;
                    self.datepickerValue.valueHasMutated();
                });

                self.endDatepicker.subscribe(function(value) {
                    self.datepickerValue().endDate = value;
                    self.datepickerValue.valueHasMutated();
                });

                self.checkedA10_6.subscribe(function(value) {
                    if (value == true) {
                        self.enableByCumulativeWkp(true);        
                    } else {
                        self.enableByCumulativeWkp(false);        
                    }
                })
                self.checkedA10_6.valueHasMutated();    

                self.selectedDataOutputType.subscribe(function(value) {
                    if (value == 0) {
                        self.enableByOutputFormat(true);
                    } else {
                        self.enableByOutputFormat(false);
                    }
                })
                self.selectedDataOutputType.valueHasMutated();
            }

            private defineDatasource(): void {
                let self = this;
                // dropdownlist A9_2
                self.itemListTypePageBrake = ko.observableArray([
                    new ItemModel('0', 'なし'),
                    new ItemModel('1', '社員'),
                    new ItemModel('2', '職場')
                ]);
                self.dataOutputType = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KWR006_10") },
                    { code: '1', name: nts.uk.resource.getText("KWR006_11") }
                ]);    
            }

            private defineComponentOption(): void {
                let self = this;
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
                    tabindex: 5,
                    maxRows: 17
                };
                // start set variable for CCG001
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2,
                    showEmployeeSelection: false,
                    showQuickSearchTab: true,
                    showAdvancedSearchTab: true,
                    showBaseDate: true,
                    showClosure: false,
                    showAllClosure: false,
                    showPeriod: false,
                    periodFormatYM: false,
                    
                    /** Required parameter */
                    baseDate: moment().toISOString(),
                    periodStartDate: moment().toISOString(),
                    periodEndDate: moment().toISOString(),
                    inService: true,
                    leaveOfAbsence: true,
                    closed: true,
                    retirement: true,
                    
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
                    showWorktype: true,
                    isMutipleCheck: true,
                    
                    /**
                    * Self-defined function: Return data from CCG001
                    * @param: data: the data return from CCG001
                    */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.employeeList.removeAll();
                        var employeeSearchs: UnitModel[] = [];
                        _.forEach(data.listEmployee, function(value) {
                            var employee: UnitModel = {
                                id: value.employeeId,
                                code: value.employeeCode,
                                name: value.employeeName,
                            };
                            employeeSearchs.push(employee);
                        });
                        self.employeeList(employeeSearchs);
                    }
                }    
            }

            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                let self = this;
                self.loadWorkScheduleOutputCondition().done(() => dfd.resolve());
                return dfd.promise();
            }
            
            public executeBindingComponent(): void {
                let self = this;
                
                // start component CCG001
                // start component KCP005
                $.when($('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption), 
                            $('#component-items-list').ntsListComponent(self.listComponentOption)).done(function() {
                    $('.ntsStartDatePicker').focus();
                    blockUI.clear();
                });
            }
            
            public exportExcel(): void {
                let self = this;
                if (!self.hasSelectedEmployees()) {
                    return;
                }

                self.saveWorkScheduleOutputCondition();
                
                const lul = {
                    fileType: 0
                }
                service.exportSchedule(lul);
            }

            public exportPdf(): void {
                let self = this;
                if (!self.hasSelectedEmployees()) {
                    return;
                }

                self.saveWorkScheduleOutputCondition();

                const lul = {
                    fileType: 1
                    
                }
                service.exportSchedule(lul);
            }

            public openScreenC(): void {
                nts.uk.ui.windows.sub.modal('/view/kwr/006/c/index.xhtml');
            }

            /**
             * Load domain characteristic: WorkScheduleOutputCondition
             */
            private loadWorkScheduleOutputCondition(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                service.restoreCharacteristic().done(data => {
                    if (_.isNil(data)) {
                        // TODO: create new
                    } else {
                        // update
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Save domain characteristic: WorkScheduleOutputCondition
             */
            private saveWorkScheduleOutputCondition(): JQueryPromise<void> {
                // TODO: tao view model cho WorkScheduleOutputCondition
                let dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            private hasSelectedEmployees(): boolean {
                let self = this;
                if (_.isEmpty(self.multiSelectedCode())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_884' });
                    return false;
                }
                return true;
            }
        }
        
         // dropdownlist A9_2
        class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }
        
         export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
        // start CCG001
        export interface GroupOption {
            /** Common properties */
            showEmployeeSelection: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab: boolean; // クイック検索
            showAdvancedSearchTab: boolean; // 詳細検索
            showBaseDate: boolean; // 基準日利用
            showClosure: boolean; // 就業締め日利用
            showAllClosure: boolean; // 全締め表示
            showPeriod: boolean; // 対象期間利用
            periodFormatYM: boolean; // 対象期間精度
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
            showAllReferableEmployee: boolean; // 参照可能な社員すべて
            showOnlyMe: boolean; // 自分だけ
            showSameWorkplace: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員
        
            /** Advanced search properties */
            showEmployment: boolean; // 雇用条件
            showWorkplace: boolean; // 職場条件
            showClassification: boolean; // 分類条件
            showJobTitle: boolean; // 職位条件
            showWorktype: boolean; // 勤種条件
            isMutipleCheck: boolean; // 選択モード
        
            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }

        export interface Ccg001ReturnedData {
            baseDate: string; // 基準日
            closureId?: number; // 締めID
            periodStart: string; // 対象期間（開始)
            periodEnd: string; // 対象期間（終了）
            listEmployee: Array<EmployeeSearchDto>; // 検索結果
        }
        // end CCG001
        
        export interface EmployeeSearchDto {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceId: string;
            workplaceName: string;
        }
    }
}