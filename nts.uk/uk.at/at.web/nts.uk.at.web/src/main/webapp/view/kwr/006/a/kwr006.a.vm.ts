module nts.uk.at.view.kwr006.a {

    import service = nts.uk.at.view.kwr006.a.service;
    import blockUI = nts.uk.ui.block;
    import MonthlyWorkScheduleConditionDto = service.model.MonthlyWorkScheduleConditionDto;
    import WorkScheduleSettingTotalOutputDto = service.model.WorkScheduleSettingTotalOutputDto;
    import TotalWorkplaceHierachyDto = service.model.TotalWorkplaceHierachyDto;

    export module viewmodel {

        export class ScreenModel {
            monthlyWorkScheduleConditionModel: MonthlyWorkScheduleConditionModel;
            // datepicker A1_6 A1_7 A1_8
            enableDatePicker: KnockoutObservable<boolean>;
            requiredDatePicker: KnockoutObservable<boolean>;
            datepickerValue: KnockoutObservable<any>;
            startDatepicker: KnockoutObservable<string>;
            endDatepicker: KnockoutObservable<string>;

            dataOutputType: KnockoutObservableArray<any>;

            // dropdownlist A7_3
            itemListCodeTemplate: KnockoutObservableArray<ItemModel>;
            selectedCodeA7_3: KnockoutObservable<string>;

            // dropdownlist A9_2
            itemListTypePageBrake: KnockoutObservableArray<ItemModel>;

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

            enableBtnConfigure: KnockoutObservable<boolean>;
            // start variable of CCG001
            ccg001ComponentOption: GroupOption;
            // end variable of CCG001
            constructor() {
                let self = this;
                self.monthlyWorkScheduleConditionModel = new MonthlyWorkScheduleConditionModel();
                self.defineDatasource();

                // datepicker A1_6 A1_7 A1_8
                self.enableDatePicker = ko.observable(true);
                self.requiredDatePicker = ko.observable(true);
                self.enableBtnConfigure = ko.observable(true);
                self.datepickerValue = ko.observable({});
                self.startDatepicker = ko.observable("");
                self.endDatepicker = ko.observable("");

                // dropdownlist A7_3
                self.itemListCodeTemplate = ko.observableArray([]);
                self.selectedCodeA7_3 = ko.observable('');

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

            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                self.loadListOutputItemMonthlyWorkSchedule().done(res => {
                    if (_.isEmpty(self.itemListCodeTemplate())) {
                        self.loadAuthorityOfEmploymentForm().done(hasAuthority => {
                            if (hasAuthority) {
                                self.openScreenC();
                            } else {
                                nts.uk.ui.dialog.alertError({ messageId: 'Msg_1141' });
                            }
                        });
                        dfd.resolve();
                    } else {
                        self.loadWorkScheduleOutputCondition().done(() => dfd.resolve());
                    }
                });
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
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/kwr/006/c/index.xhtml').onClosed(() => {
                    self.loadListOutputItemMonthlyWorkSchedule();    
                });
            }

            /**
             * Load domain characteristic: WorkScheduleOutputCondition
             */
            private loadWorkScheduleOutputCondition(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.restoreCharacteristic().done(data => {
                    if (!_.isNil(data)) {
                        self.monthlyWorkScheduleConditionModel.updateData(data);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private loadListOutputItemMonthlyWorkSchedule(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                //TODO
                dfd.resolve();
                return dfd.promise();
            }

            private loadAuthorityOfEmploymentForm(): JQueryPromise<boolean> {
                let self = this;
                let dfd = $.Deferred<boolean>();
                //TODO: load co ra thi return true, load dialog C. Khong ra thi return false, ban msg_1141
                dfd.resolve(true);
                return dfd.promise();
            }

            /**
             * Save domain characteristic: WorkScheduleOutputCondition
             */
            private saveWorkScheduleOutputCondition(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.saveCharacteristic(self.monthlyWorkScheduleConditionModel.toDto()).done(() => dfd.resolve());
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

        export class MonthlyWorkScheduleConditionModel {
            companyId: string;
            userId: string;
            outputType: KnockoutObservable<number>;
            pageBreakIndicator: KnockoutObservable<number>;
            totalOutputSetting: WorkScheduleSettingTotalOutputModel;
            isIndividualTypeSelected: KnockoutComputed<boolean>;

            constructor() {
                let self = this;
                self.companyId = __viewContext.user.companyId;
                self.userId = __viewContext.user.employeeId;
                self.outputType = ko.observable(0);
                self.pageBreakIndicator = ko.observable(0);
                self.isIndividualTypeSelected = ko.computed(() => self.outputType() == 0);
                self.totalOutputSetting = new WorkScheduleSettingTotalOutputModel();
                self.totalOutputSetting.isIndividualTypeSelected = self.isIndividualTypeSelected;
            }
            public updateData(data: MonthlyWorkScheduleConditionDto): void {
                let self = this;
                self.companyId = data.companyId;
                self.userId = data.userId;
                self.outputType(data.outputType);
                self.pageBreakIndicator(data.pageBreakIndicator);
                self.totalOutputSetting.updateData(data.totalOutputSetting);
            }

            public toDto(): MonthlyWorkScheduleConditionDto {
                let self = this;
                let dto = <MonthlyWorkScheduleConditionDto>{};
                dto.companyId = self.companyId;
                dto.userId = self.userId;
                dto.outputType = self.outputType();
                dto.pageBreakIndicator = self.pageBreakIndicator();
                dto.totalOutputSetting = self.totalOutputSetting.toDto();
                return dto;
            }
        }

        export class WorkScheduleSettingTotalOutputModel {
            details: KnockoutObservable<boolean>;
            personalTotal: KnockoutObservable<boolean>;
            workplaceTotal: KnockoutObservable<boolean>;
            grossTotal: KnockoutObservable<boolean>;
            cumulativeWorkplace: KnockoutObservable<boolean>;
            workplaceHierarchyTotal: TotalWorkplaceHierachyModel;
            isIndividualTypeSelected: KnockoutComputed<boolean>;

            constructor() {
                let self = this;
                self.details = ko.observable(false);
                self.personalTotal = ko.observable(false);
                self.workplaceTotal = ko.observable(false);
                self.grossTotal = ko.observable(false);
                self.cumulativeWorkplace = ko.observable(false);
                self.workplaceHierarchyTotal = new TotalWorkplaceHierachyModel();
                self.workplaceHierarchyTotal.cumulativeWorkplace = self.cumulativeWorkplace;
            }
            public updateData(data: WorkScheduleSettingTotalOutputDto): void {
                let self = this;
                self.details(data.details);
                self.personalTotal(data.personalTotal);
                self.workplaceTotal(data.workplaceTotal);
                self.grossTotal(data.grossTotal);
                self.workplaceHierarchyTotal.updateData(data.workplaceHierarchyTotal);
            }

            public toDto(): WorkScheduleSettingTotalOutputDto {
                let self = this;
                let dto = <WorkScheduleSettingTotalOutputDto>{};
                dto.details = self.details();
                dto.personalTotal = self.personalTotal();
                dto.workplaceTotal = self.workplaceTotal();
                dto.grossTotal = self.grossTotal();
                dto.workplaceHierarchyTotal = self.workplaceHierarchyTotal.toDto();

                return dto;
            }
        }

        export class TotalWorkplaceHierachyModel {
            firstLevel: KnockoutObservable<boolean>;
            secondLevel: KnockoutObservable<boolean>;
            thirdLevel: KnockoutObservable<boolean>;
            fourthLevel: KnockoutObservable<boolean>;
            fifthLevel: KnockoutObservable<boolean>;
            sixthLevel: KnockoutObservable<boolean>;
            seventhLevel: KnockoutObservable<boolean>;
            eightLevel: KnockoutObservable<boolean>;
            ninthLevel: KnockoutObservable<boolean>;
            cumulativeWorkplace: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.firstLevel = ko.observable(false);
                self.secondLevel = ko.observable(false);
                self.thirdLevel = ko.observable(false);
                self.fourthLevel = ko.observable(false);
                self.fifthLevel = ko.observable(false);
                self.sixthLevel = ko.observable(false);
                self.seventhLevel = ko.observable(false);
                self.eightLevel = ko.observable(false);
                self.ninthLevel = ko.observable(false);
            }

            public updateData(data: TotalWorkplaceHierachyDto): void {
                let self = this;
                self.firstLevel(data.firstLevel);
                self.secondLevel(data.secondLevel);
                self.thirdLevel(data.thirdLevel);
                self.fourthLevel(data.fourthLevel);
                self.fifthLevel(data.fifthLevel);
                self.sixthLevel(data.sixthLevel);
                self.seventhLevel(data.seventhLevel);
                self.eightLevel(data.eightLevel);
                self.ninthLevel(data.ninthLevel);
                self.cumulativeWorkplace(data.cumulativeWorkplace);
            }

            public toDto(): TotalWorkplaceHierachyDto {
                let self = this;
                let dto = <TotalWorkplaceHierachyDto>{};
                dto.firstLevel = self.firstLevel();
                dto.secondLevel = self.secondLevel();
                dto.thirdLevel = self.thirdLevel();
                dto.fourthLevel = self.fourthLevel();
                dto.fifthLevel = self.fifthLevel();
                dto.sixthLevel = self.sixthLevel();
                dto.seventhLevel = self.seventhLevel();
                dto.eightLevel = self.eightLevel();
                dto.ninthLevel = self.ninthLevel();
                dto.cumulativeWorkplace = self.cumulativeWorkplace();
                return dto;
            }
        }
    }
}