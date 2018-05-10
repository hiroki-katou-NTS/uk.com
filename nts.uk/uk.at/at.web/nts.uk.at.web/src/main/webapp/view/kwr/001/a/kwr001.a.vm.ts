module nts.uk.at.view.kwr001.a {
    import ComponentOption = kcp.share.list.ComponentOption;
    
    import blockUI = nts.uk.ui.block;
    
    import service = nts.uk.at.view.kwr001.a.service;
    
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            dataOutputType: KnockoutObservableArray<any>;
            
            // datepicker A1_6
            requiredDatePicker: KnockoutObservable<boolean>; 
            enableDatePicker: KnockoutObservable<boolean>; 
            datepickerValue: KnockoutObservable<any>;
            startDatepicker: KnockoutObservable<string>;
            endDatepicker: KnockoutObservable<string>; 
            // datepicker A1_6
            
            // switch button A6_2
            selectedDataOutputType: KnockoutObservable<number>;
            
            // dropdownlist A7_3
            itemListCodeTemplate: KnockoutObservableArray<ItemModel>;
            selectedCodeA7_3: KnockoutObservable<string>;
            
            // dropdownlist A9_2
            itemListTypePageBrake: KnockoutObservableArray<ItemModel>;
            selectedCodeA9_2: KnockoutObservable<string>;
            
            // radio button group A13_1
            itemListConditionSet: KnockoutObservableArray<any>;
            selectedCodeA13_1: KnockoutObservable<number>;
            
            // start variable of CCG001
            ccg001ComponentOption: GroupOption;
            // end variable of CCG001
            
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
            
            requiredTemp: KnockoutObservable<boolean>;
            enableTemp: KnockoutObservable<boolean>;
            
            checkedA10_2: KnockoutObservable<boolean>;
            checkedA10_3: KnockoutObservable<boolean>;
            checkedA10_4: KnockoutObservable<boolean>;
            checkedA10_5: KnockoutObservable<boolean>;
            checkedA10_6: KnockoutObservable<boolean>;
            checkedA10_7: KnockoutObservable<boolean>;
            
            checkedA10_10: KnockoutObservable<boolean>;
            checkedA10_11: KnockoutObservable<boolean>;
            checkedA10_12: KnockoutObservable<boolean>;
            checkedA10_13: KnockoutObservable<boolean>;
            checkedA10_14: KnockoutObservable<boolean>;
            checkedA10_15: KnockoutObservable<boolean>;
            checkedA10_16: KnockoutObservable<boolean>;
            checkedA10_17: KnockoutObservable<boolean>;
            checkedA10_18: KnockoutObservable<boolean>;
            
            enableByCumulativeWkp: KnockoutObservable<boolean>;
            enableByOutputFormat: KnockoutObservable<boolean>;
            enableBtnConfigure: KnockoutObservable<boolean>;
            enableConfigErrCode: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;

                self.data = ko.observable(1);
                
                self.enableConfigErrCode = ko.observable(true);
                self.enableByOutputFormat = ko.observable(true);
                self.enableBtnConfigure = ko.observable(true);
                self.requiredTemp = ko.observable(true);
                self.enableTemp = ko.observable(true);;
                
                self.checkedA10_2 = ko.observable(false);
                self.checkedA10_3 = ko.observable(false);
                self.checkedA10_4 = ko.observable(false);
                self.checkedA10_5 = ko.observable(false);
                self.checkedA10_6 = ko.observable(false);
                self.checkedA10_7 = ko.observable(false);
                
                self.checkedA10_10 = ko.observable(false);
                self.checkedA10_11 = ko.observable(false);
                self.checkedA10_12 = ko.observable(false);
                self.checkedA10_13 = ko.observable(false);
                self.checkedA10_14 = ko.observable(false);
                self.checkedA10_15 = ko.observable(false);
                self.checkedA10_16 = ko.observable(false);
                self.checkedA10_17 = ko.observable(false);
                self.checkedA10_18 = ko.observable(false);
                
                self.enableByCumulativeWkp = ko.observable(true);
                
                self.checkedA10_7.subscribe(function(value) {
                    if (value == true) {
                        self.enableByCumulativeWkp(true);        
                    } else {
                        self.enableByCumulativeWkp(false);    
                    }
                })
                self.checkedA10_7.valueHasMutated();
                
                // start set variable for datepicker A1_6
                self.enableDatePicker = ko.observable(true);
                self.requiredDatePicker = ko.observable(true);
                
                self.startDatepicker = ko.observable("");
                self.endDatepicker = ko.observable("");
                self.datepickerValue = ko.observable({});
                
                self.startDatepicker.subscribe(function(value){
                    self.datepickerValue().startDate = value;
                    self.datepickerValue.valueHasMutated();        
                });
                
                self.endDatepicker.subscribe(function(value){
                    self.datepickerValue().endDate = value;   
                    self.datepickerValue.valueHasMutated();      
                });
                // end set variable for datepicker A1_6
                
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
//                    baseDate: moment(self.endDatepicker(), DATE_FORMAT_YYYY_MM_DD).toISOString(),
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
                                code: value.employeeCode,
                                name: value.employeeName,
                            };
                            employeeSearchs.push(employee);
                        });
                        self.employeeList(employeeSearchs);
                    }
                }
                // end set variable for CCG001
                
                // TODO: hoangdd - goi service lay enum thay cho viec set cung resource
                self.dataOutputType = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KWR001_10") },
                    { code: '1', name: nts.uk.resource.getText("KWR001_11") }
                ]);
                self.selectedDataOutputType = ko.observable(0);
                self.selectedDataOutputType.subscribe(function(value) {
                    if (value == 0) {
                        self.enableByOutputFormat(true);                        
                    } else {
                        self.enableByOutputFormat(false);    
                    }
                })
                self.selectedDataOutputType.valueHasMutated();

                // TODO: hoangdd - lay du lieu tu service
                self.itemListCodeTemplate = ko.observableArray([
                    new ItemModel('0', '123'),
                    new ItemModel('1', '456')
                ]);
                
                // TODO: hoangdd - lay du lieu tu service
                self.itemListTypePageBrake = ko.observableArray([
                    new ItemModel('0', '123'),
                    new ItemModel('1', '456')
                ]);
                
                self.selectedCodeA9_2 = ko.observable('1');
                
                self.selectedCodeA7_3 = ko.observable('1'); 
                
                // TODO: hoangdd - lay du lieu tu service
                self.itemListConditionSet = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KWR001_38")),
                    new BoxModel(1, nts.uk.resource.getText("KWR001_39"))
                ]);
                
                self.selectedCodeA13_1 = ko.observable(0);
                self.selectedCodeA13_1.subscribe(function(value) {
                    if (value==1) {
                        self.enableConfigErrCode(true);
                    } else {
                        self.enableConfigErrCode(false);
                    }
                })
                self.selectedCodeA13_1.valueHasMutated();
                
                // start define KCP005
                self.multiSelectedCode = ko.observableArray([]);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([]);
                self.isDialog = ko.observable(true);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(true);
                self.isShowWorkPlaceName = ko.observable(false);
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
                    tabindex: 5,
                    maxRows: 17
                };
                // end define KCP005
                
                // start component KCP005
//                $('#component-items-list').ntsListComponent(self.listComponentOption);
                // end component KCP005
            }
            
            public startPage(): JQueryPromise<any>  {
                blockUI.grayout();
                var dfd = $.Deferred<void>();
                var self = this;
                
                // TODO - hoangdd: goi service lay domain cho A7_6. gio dang fix cung
                self.enableBtnConfigure(true);
                
                $.when(self.getDataStartPageService(), self.getDataCharateristic(),
                        $('#component-items-list').ntsListComponent(self.listComponentOption)).done(function() {
                        blockUI.clear();
                    });
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private getDataStartPageService(): JQueryPromise<any> {
                var dfd = $.Deferred<void>();
                let self = this;
                
                // TODO - hoangdd: fake data
                let isExist = true;
                let keyRestore = "kwr001";
                service.getDataStartPage(isExist, keyRestore).done(function(data: any) {
                    self.startDatepicker(data.startDate);
                    self.endDatepicker(data.endDate);
                    self.ccg001ComponentOption.baseDate = moment.utc(self.endDatepicker(), DATE_FORMAT_YYYY_MM_DD).toISOString();
                    // start component CCG001
                    $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption).done(function(){
                        dfd.resolve();
                    });
                    // end component CCG001
                    console.log(data);
                    
                })
                
                return dfd.promise();
            }
            
            private getDataCharateristic(): JQueryPromise<any> {
                var dfd = $.Deferred<void>();
                console.log(__viewContext);
                let self = this;
                nts.uk.characteristics.save("WorkScheduleOutputCondition_", "sdf");
                $.when(nts.uk.characteristics.restore("WorkScheduleOutputCondition_")).done(function(data: any) {
                    console.log(data);
                });
                dfd.resolve();
                
                return dfd.promise();
            }
            
            openScreenB () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_B', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/b/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_B');
                });
            }
            openScreenC () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_C', self.data(), true);
//                nts.uk.request.jump("/view/kwr/001/c/index.xhtml", {} );
                nts.uk.ui.windows.sub.modal('/view/kwr/001/c/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_C');
                });
            }
        }
        
        const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";
        
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
        
        class ItemModel {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name){
                var self = this;
                self.id = id;
                self.name = name;
            }
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
        // end CCG001
        
        class WorkScheduleOutputCondition {
            companyId: string;
            userId: string;
            outputType: number;
            code: Array<string>;
            pageBreakIndicator: number;
            settingDetailTotalOuput: WorkScheduleSettingTotalOutput;
            conditionSetting: number;
            errorAlarmCode: Array<string>;
            
            constructor(companyId: string, userId: string, outputType: number, code: Array<string>, pageBreakIndicator: number,
                            settingDetailTotalOuput: WorkScheduleSettingTotalOutput, conditionSetting: number, errorAlarmCode: Array<string>) {
                this.companyId = companyId;
                this.userId = userId;
                this.outputType = outputType;
                this.code = code;
                this.pageBreakIndicator = pageBreakIndicator;
                this.settingDetailTotalOuput = settingDetailTotalOuput;
                this.conditionSetting =  conditionSetting;
                if (errorAlarmCode) {
                    this.errorAlarmCode = errorAlarmCode;        
                }
            }
        }
        
        class WorkScheduleSettingTotalOutput {
            details: boolean;
            personalTotal: boolean;
            workplaceTotal: boolean;
            totalNumberDay: boolean;
            grossTotal: boolean;
            cumulativeWorkplace: boolean;
            
            workplaceHierarchyTotal: TotalWorkplaceHierachy;
            
            constructor( details: boolean, personalTotal: boolean, workplaceTotal: boolean, totalNumberDay: boolean,
                            grossTotal: boolean, cumulativeWorkplace: boolean, workplaceHierarchyTotal?: TotalWorkplaceHierachy) {
                this.details = details;
                this.personalTotal = personalTotal;
                this.workplaceTotal = workplaceTotal;
                this.totalNumberDay = totalNumberDay;
                this.grossTotal = grossTotal;
                this.cumulativeWorkplace = cumulativeWorkplace;
                if (workplaceHierarchyTotal) {
                    this.workplaceHierarchyTotal = workplaceHierarchyTotal;
                }
            }
        }
        
        class TotalWorkplaceHierachy {
            firstLevel: boolean;
            secondLevel: boolean;
            thirdLevel: boolean;
            fourthLevel: boolean;
            fifthLevel: boolean;
            sixthLevel: boolean;
            seventhLevel: boolean;
            eighthLevel: boolean;
            ninthLevel: boolean; 
            
            constructor(firstLevel?: boolean, secondLevel?: boolean, thirdLevel?: boolean, fourthLevel?: boolean,
                            fifthLevel?: boolean, sixthLevel?: boolean, seventhLevel?: boolean, 
                            eighthLevel?: boolean, ninthLevel?: boolean ) {
                if (firstLevel) {
                    this.firstLevel = firstLevel;
                }
                
                if (secondLevel) {
                    this.secondLevel = secondLevel;
                }
                
                if (thirdLevel) {
                    this.thirdLevel = thirdLevel;
                }
                
                if (fourthLevel) {
                    this.fourthLevel = fourthLevel;
                }
                
                if (fifthLevel) {
                    this.fifthLevel = fifthLevel;
                }
                
                if (sixthLevel) {
                    this.sixthLevel = sixthLevel;
                }
                
                if (seventhLevel) {
                    this.seventhLevel = seventhLevel;
                }
                
                if (eighthLevel) {
                    this.eighthLevel = eighthLevel;
                }
                
                if (ninthLevel) {
                    this.ninthLevel = ninthLevel;
                }
            }
        }
    }
}