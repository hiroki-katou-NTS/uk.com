module nts.uk.com.view.cmf004.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        stepList: Array<NtsWizardStep> = [
            { content: '.step-1' },
            { content: '.step-2' },
            { content: '.step-3' },
            { content: '.step-4' },
            { content: '.step-5' }
        ];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable({ id: 'step-1', content: '.step-1' });
        activeStep: KnockoutObservable<number> = ko.observable(0);
        selectedId: KnockoutObservable<number> = ko.observable(1);
        options: KnockoutObservableArray<any> = ko.observableArray([
            { value: 1, text: getText('CMF004_30') },
            { value: 2, text: getText('CMF004_32') }
        ]);
        //ScreenB
        startDateString: KnockoutObservable<string> = ko.observable("");
        endDateString: KnockoutObservable<string> = ko.observable("");
        dataRecoverySelection: KnockoutObservable<DataRecoverySelection> = ko.observable(new DataRecoverySelection(1, 0, {}, [], ""));
        //upload file
        fileId: KnockoutObservable<string> = ko.observable(null);
        fileName: KnockoutObservable<string> = ko.observable(null);
        //Screen E, F, G, H
        recoverySourceFile: KnockoutObservable<string> = ko.observable("");
        recoverySourceCode: KnockoutObservable<string> = ko.observable("");
        recoverySourceName: KnockoutObservable<string> = ko.observable("");
        supplementaryExplanation: KnockoutObservable<string> = ko.observable("");
        //Screen E
        recoveryMethodOptions: KnockoutObservableArray<any> = ko.observableArray([
            { value: RecoveryMethod.RESTORE_ALL, text: getText('CMF004_92') },
            { value: RecoveryMethod.SELECTED_RANGE, text: getText('CMF004_93') }
        ]);
        dataContentConfirm: KnockoutObservable<DataContentConfirm> = ko.observable(new DataContentConfirm([], 1));
        //Screen F
        changeDataRecoveryPeriod: KnockoutObservable<ChangeDataRecoveryPeriod> = ko.observable(new ChangeDataRecoveryPeriod([]));
        //CCG001
        ccg001ComponentOption: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
        //KCP005
        kcp005ComponentOption: any;
        selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray([]);
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
        //Screen H
        recoveryMethodDescription1: KnockoutObservable<string> = ko.observable("");
        recoveryMethodDescription2: KnockoutObservable<string> = ko.observable("");
        dataRecoverySummary: KnockoutObservable<DataRecoverySummary> = ko.observable(new DataRecoverySummary([], 0, [], []));
        optionsEmployee: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            let self = this;


            //Fixed table
            if (/Chrome/.test(navigator.userAgent)) {
                $("#E4_1").ntsFixedTable({ height: 164, width: 700 });
                $("#F4_1").ntsFixedTable({ height: 184, width: 700 });
            } else {
                $("#E4_1").ntsFixedTable({ height: 162, width: 700 });
                $("#F4_1").ntsFixedTable({ height: 182, width: 700 });
            }
            $("#H4_1").ntsFixedTable({ height: 164, width: 700 });

            //_____CCG001________
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
                showOnlyMe: false,
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
                    self.applyKCP005ContentSearch(data.listEmployee);
                    self.selectedEmployee(data.listEmployee);
                }
            };

            //_____KCP005________
            self.kcp005ComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCode,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: [],
                isShowWorkPlaceName: false,
                isShowSelectAllButton: true,
                maxWidth: 450,
                maxRows: 15
            };

            //Screen H
            self.optionsEmployee = ko.observableArray([
                { headerText: 'employeeId', key: 'employeeId', width: 100, hidden: true },
                { headerText: 'コード', key: 'employeeCode', width: 100, hidden: false },
                { headerText: '名称', key: 'employeeName', width: 150, hidden: false }
            ]);

            self.startDateString.subscribe(value => {
                self.dataRecoverySelection().executePeriodInput().startDate = value;
                self.dataRecoverySelection().executePeriodInput.valueHasMutated();
            });

            self.endDateString.subscribe(value => {
                self.dataRecoverySelection().executePeriodInput().endDate = value;
                self.dataRecoverySelection().executePeriodInput.valueHasMutated();
            });

            self.dataContentConfirm().dataContentcategoryList.subscribe(value => {
                self.setWidthScrollHeader('.contentE', value);
            });

            self.changeDataRecoveryPeriod().changeDataCategoryList.subscribe(value => {
                self.setWidthScrollHeader('.contentF', value);
            });

            self.dataRecoverySummary().recoveryCategoryList.subscribe(value => {
                self.setWidthScrollHeader('.contentH', value);
            });
        }

        finished(fileInfo: any) {
            var self = this;
            //self.fileId(fileInfo.id);
            console.log(fileInfo);
            if (fileInfo.id != null && fileInfo.originalName != null) {
                setShared("CMF004lParams", {
                    fileId: fileInfo.id,
                    fileName: fileInfo.originalName
                });
                nts.uk.ui.windows.sub.modal("/view/cmf/004/c/index.xhtml");
            }
        }

        private setWidthScrollHeader(frame, value): void {
            if (value.length > 5) {
                $(frame + ' .scroll-header.nts-fixed-header').css('width', '17px');
            } else {
                $(frame + ' .scroll-header.nts-fixed-header').css('width', '0px');
            }
        }

        private initWizardScreen(): void {
            let self = this;
            self.initScreenB();
        }

        /**
         * B: データ復旧選択
         */
        private initScreenB(): void {
            let self = this;
            for (let i = 1; i < 100; i++) {
                let item = {
                    saveSetCode: i < 10 ? '00' + i : '0' + i,
                    saveSetName: '保存セット' + i,
                    supplementaryExplanation: '補足説明' + i,
                    storageStartDate: '2018/08/08 08:08:08',
                    executeCategory: i % 2 == 0 ? '手動実行' : '自動実行',
                    targetNumber: i + '人',
                    saveFileName: 'File ' + i + '.zip'
                }
                self.dataRecoverySelection().recoveryFileList().push(item);
            }
        }

        /**
         * E:データ内容確認
         */
        private initScreenE(): void {
            let self = this;
            self.recoverySourceFile('パターンA20170321_001.zip');
            self.recoverySourceCode('001');
            self.recoverySourceName('手動セットA');
            self.supplementaryExplanation('2017年05月締め前');
            let listCategory: Array<CategoryInfo> = [];
            for (let i = 1; i < 10; i++) {
                let isRecover = i % 2 ? true : false;
                let categoryName = '個人情報マスタ';
                let recoveryPeriod = '日次';
                let startOfPeriod = '2018/01/0' + i;
                let endOfPeriod = '2018/01/31';
                if (i % 2 == 0) {
                    recoveryPeriod = '月次';
                    startOfPeriod = '2018/01';
                    endOfPeriod = '2018/01';
                }
                if (i % 3 == 0) {
                    recoveryPeriod = '年次';
                    startOfPeriod = '2018';
                    endOfPeriod = '2018';
                }
                let recoveryMethod = i % 2 ? getText('CMF004_305') : getText('CMF004_306');
                listCategory.push(new CategoryInfo(i, isRecover, categoryName, recoveryPeriod, startOfPeriod, endOfPeriod, recoveryMethod));
            }
            self.dataContentConfirm().dataContentcategoryList(listCategory);
        }

        /**
         * F:データ復旧期間変更
         */
        private initScreenF(): void {
            let self = this;
            let _listCategory = _.filter(self.dataContentConfirm().dataContentcategoryList(), x => { return x.isRecover() == true; });
            let _itemList: Array<CategoryInfo> = [];
            _.forEach(_listCategory, (x, i) => {
                _itemList.push(new CategoryInfo(i + 1, x.isRecover(), x.categoryName(), x.recoveryPeriod(), x.startOfPeriod(), x.endOfPeriod(), x.recoveryMethod()));
            });
            self.changeDataRecoveryPeriod().changeDataCategoryList(_itemList);
        }

        /**
         * H:データ復旧サマリ
         */
        private initScreenH(): void {
            let self = this;
            let _categoryList = self.getRecoveryCategory(self.changeDataRecoveryPeriod().changeDataCategoryList());
            let _employeeList = self.getRecoveryEmployee(self.selectedEmployeeCode());
            let _recoveryMethod = self.dataContentConfirm().selectedRecoveryMethod();
            let _recoveryMethodDescription1 = _.filter(self.recoveryMethodOptions(), x => { return x.value == _recoveryMethod }).map(x1 => { return x1.text }).toString();
            let _recoveryMethodDescription2 = self.getRecoveryMethodDescription2(_recoveryMethod);
            self.dataRecoverySummary().recoveryCategoryList(_categoryList);
            self.dataRecoverySummary().recoveryEmployee(_employeeList);
            self.dataRecoverySummary().recoveryMethod(_recoveryMethod);
            self.recoveryMethodDescription1(_recoveryMethodDescription1);
            self.recoveryMethodDescription2(_recoveryMethodDescription2);
        }

        /**
         * Get recovery category
         */
        private getRecoveryCategory(selectedCategory: Array<CategoryInfo>): Array<any> {
            let self = this;
            let _listCategory = _.filter(selectedCategory, x => { return x.isRecover() == true; });
            let _itemList: Array<CategoryInfo> = [];
            _.forEach(_listCategory, (x, i) => {
                let startDate = self.formatDate(x.recoveryPeriod, x.startOfPeriod());
                let endDate = self.formatDate(x.recoveryPeriod, x.endOfPeriod());
                _itemList.push(new CategoryInfo(i + 1, x.isRecover(), x.categoryName(), x.recoveryPeriod(), startDate, endDate, x.recoveryMethod()));
            });
            return _itemList;
        }

        private formatDate(recoveryPeriod, dateFormat): string {
            let self = this;
            if (self.periodInputType(recoveryPeriod) == PeriodEnum.DAY) {
                return moment.utc(dateFormat).format("YYYY/MM/DD");
            }
            if (self.periodInputType(recoveryPeriod) == PeriodEnum.MONTH) {
                return nts.uk.time.formatYearMonth(parseInt(dateFormat));
            }
            return dateFormat;
        }

        /**
         * Get recovery employee
         */
        private getRecoveryEmployee(selectedEmployeeIds: Array<string>): Array<any> {
            let self = this;
            let employeeList: Array<any> = [];
            _.each(selectedEmployeeIds, x => {
                let employee = _.find(self.selectedEmployee(), x1 => { return x1.employeeCode === x });
                if (employee) {
                    employeeList.push(employee);
                }
            });
            return employeeList;
        }

        private getRecoveryMethodDescription2(recoveryMethod: number): string {
            if (recoveryMethod == RecoveryMethod.RESTORE_ALL) return getText('CMF004_94');
            return getText('CMF004_95');
        }

        private nextToScreenE(): void {
            let self = this;
            self.initScreenE();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        private nextToScreenF(): void {
            let self = this;
            self.initScreenF();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        private nextToScreenG(): void {
            $('#data-recovery-wizard').ntsWizard("next");
        }

        private nextToScreenH(): void {
            let self = this;
            self.initScreenH();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        private restoreData_click(): void {
        }

        private backToPreviousScreen(): void {
            $('#data-recovery-wizard').ntsWizard("prev");
        }

        /**
         * Apply CCG001 search data to KCP005
         */
        private applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeList.removeAll();
            let employeeSearchs: Array<UnitModel> = [];
            for (let employeeSearch of dataList) {
                let employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                    workplaceName: employeeSearch.workplaceName
                };
                employeeSearchs.push(employee);
            }
            self.employeeList(employeeSearchs);
        }

        private periodInputType(inputType): number {
            if (inputType() === '日次') return PeriodEnum.DAY;
            if (inputType() === '月次') return PeriodEnum.MONTH;
            if (inputType() === '年次') return PeriodEnum.YEAR;
            return 0;
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.initWizardScreen();
            dfd.resolve(self);
            return dfd.promise();
        }
    }

    export enum PeriodEnum {
        DAY = 1, //日次
        MONTH = 2, //月次
        YEAR = 3  //年次
    }

    export enum RecoveryMethod {
        RESTORE_ALL = 1, //全件復旧
        SELECTED_RANGE = 2 //選択した範囲で復旧
    }

    export class CategoryInfo {
        rowNumber: KnockoutObservable<number>;
        isRecover: KnockoutObservable<boolean>;
        categoryName: KnockoutObservable<string>;
        recoveryPeriod: KnockoutObservable<string>;
        recoveryMethod: KnockoutObservable<string>;
        startOfPeriod: KnockoutObservable<string>;
        endOfPeriod: KnockoutObservable<string>;
        constructor(rowNumber: number, isRecover: boolean, categoryName: string, recoveryPeriod: string, startOfPeriod: string, endOfPeriod: string, recoveryMethod: string) {
            let self = this;
            self.rowNumber = ko.observable(rowNumber);
            self.isRecover = ko.observable(isRecover);
            self.categoryName = ko.observable(categoryName);
            self.recoveryPeriod = ko.observable(recoveryPeriod);
            self.startOfPeriod = ko.observable(startOfPeriod);
            self.endOfPeriod = ko.observable(endOfPeriod);
            self.recoveryMethod = ko.observable(recoveryMethod);
        }
    }


    export interface IRecoveryFileInfo {
        saveSetCode: string;
        saveSetName: string;
        supplementaryExplanation: string;
        storageStartDate: string;
        executeCategory: string;
        targetNumber: string;
        saveFileName: string;
    }

    export class RecoveryFileInfo {
        saveSetCode: string;
        saveSetName: string;
        supplementaryExplanation: string;
        storageStartDate: string;
        executeCategory: string;
        targetNumber: string;
        saveFileName: string;
        constructor(input: IRecoveryFileInfo) {
            let self = this;
            self.saveSetCode = input.saveSetCode;
            self.saveSetName = input.saveSetName;
            self.supplementaryExplanation = input.supplementaryExplanation;
            self.storageStartDate = input.storageStartDate;
            self.executeCategory = input.executeCategory;
            self.targetNumber = input.targetNumber;
            self.saveFileName = input.saveFileName;
        }
    }

    /**
     * B: データ復旧選択
     */
    export class DataRecoverySelection {
        selectedUploadCls: KnockoutObservable<number>;
        selectedSaveFileCls: KnockoutObservable<number>;
        executePeriodInput: KnockoutObservable<any>;
        recoveryFileList: KnockoutObservableArray<IRecoveryFileInfo>;
        selectedRecoveryFile: KnockoutObservable<string>;
        constructor(selectedUploadCls: number, selectedSaveFileCls: number, executePeriodInput: any, recoveryFileList: Array<any>, selectedRecoveryFile: string) {
            let self = this;
            self.selectedUploadCls = ko.observable(selectedUploadCls);
            self.selectedSaveFileCls = ko.observable(selectedSaveFileCls);
            self.executePeriodInput = ko.observable(executePeriodInput);
            self.recoveryFileList = ko.observableArray(recoveryFileList);
            self.selectedRecoveryFile = ko.observable(selectedRecoveryFile);
        }
    }

    /**
     * E:データ内容確認
     */
    export class DataContentConfirm {
        dataContentcategoryList: KnockoutObservableArray<CategoryInfo>;
        selectedRecoveryMethod: KnockoutObservable<number>;
        constructor(categoryList: Array<any>, selectedRecoveryMethod: number) {
            let self = this;
            self.dataContentcategoryList = ko.observableArray(categoryList);
            self.selectedRecoveryMethod = ko.observable(selectedRecoveryMethod);
        }
    }

    /**
     * F:データ復旧期間変更
     */
    export class ChangeDataRecoveryPeriod {
        changeDataCategoryList: KnockoutObservableArray<CategoryInfo>;
        constructor(categoryList: Array<any>) {
            let self = this;
            self.changeDataCategoryList = ko.observableArray(categoryList);
        }
    }

    /**
     * H:データ復旧サマリ
     */
    export class DataRecoverySummary {
        recoveryCategoryList: KnockoutObservableArray<CategoryInfo>;
        recoveryMethod: KnockoutObservable<number>;
        recoveryEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        selectedEmployee: KnockoutObservableArray<any>;
        constructor(recoveryCategoryList: Array<any>, recoveryMethod: number, recoveryEmployee: Array<any>, selectedEmployee: Array<any>) {
            let self = this;
            self.recoveryCategoryList = ko.observableArray(recoveryCategoryList);
            self.recoveryMethod = ko.observable(recoveryMethod);
            self.recoveryEmployee = ko.observableArray(recoveryEmployee);
            self.selectedEmployee = ko.observableArray(selectedEmployee);
        }
    }

    /**
     * CCG001
     */
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

    /**
     * KCP005
     */
    export class ListType {
        static EMPLOYMENT = 1;
        static CLASSIFICATION = 2;
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
}