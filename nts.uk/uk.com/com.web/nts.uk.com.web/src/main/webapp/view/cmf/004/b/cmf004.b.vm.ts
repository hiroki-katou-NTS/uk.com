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

        setWidthScrollHeader(frame, value): void {
            if (value.length > 5) {
                $(frame + ' .scroll-header.nts-fixed-header').css('width', '17px');
            } else {
                $(frame + ' .scroll-header.nts-fixed-header').css('width', '0px');
            }
        }

        initWizardScreen(): void {
            let self = this;
            self.initScreenB();
        }

        /**
         * B: データ復旧選択
         */
        initScreenB(): void {
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
        initScreenE(): void {
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
        initScreenF(): void {
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
        initScreenH(): void {
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
        getRecoveryCategory(selectedCategory: Array<CategoryInfo>): Array<any> {
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

        formatDate(recoveryPeriod, dateFormat): string {
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
        getRecoveryEmployee(selectedEmployeeIds: Array<string>): Array<any> {
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

        getRecoveryMethodDescription2(recoveryMethod: number): string {
            if (recoveryMethod == RecoveryMethod.RESTORE_ALL) return getText('CMF004_94');
            return getText('CMF004_95');
        }

        nextToScreenE(): void {
            let self = this;
            self.initScreenE();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        nextToScreenF(): void {
            let self = this;
            self.initScreenF();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        nextToScreenG(): void {
            $('#data-recovery-wizard').ntsWizard("next");
        }

        nextToScreenH(): void {
            let self = this;
            self.initScreenH();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        restoreData_click(): void {
        }

        backToPreviousScreen(): void {
            $('#data-recovery-wizard').ntsWizard("prev");
        }

        periodInputType(inputType): number {
            if (inputType() === '日次') return PeriodEnum.DAY;
            if (inputType() === '月次') return PeriodEnum.MONTH;
            if (inputType() === '年次') return PeriodEnum.YEAR;
            return 3;
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
        DAY   = 0, //日次
        MONTH = 1, //月次
        YEAR  = 2  //年次
    }

    export enum RecoveryMethod {
        RESTORE_ALL    = 0, //全件復旧
        SELECTED_RANGE = 1 //選択した範囲で復旧
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
            self.rowNumber      = ko.observable(rowNumber);
            self.isRecover      = ko.observable(isRecover);
            self.categoryName   = ko.observable(categoryName);
            self.recoveryPeriod = ko.observable(recoveryPeriod);
            self.startOfPeriod  = ko.observable(startOfPeriod);
            self.endOfPeriod    = ko.observable(endOfPeriod);
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
            self.saveSetCode              = input.saveSetCode;
            self.saveSetName              = input.saveSetName;
            self.supplementaryExplanation = input.supplementaryExplanation;
            self.storageStartDate         = input.storageStartDate;
            self.executeCategory          = input.executeCategory;
            self.targetNumber             = input.targetNumber;
            self.saveFileName             = input.saveFileName;
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
            self.selectedUploadCls    = ko.observable(selectedUploadCls);
            self.selectedSaveFileCls  = ko.observable(selectedSaveFileCls);
            self.executePeriodInput   = ko.observable(executePeriodInput);
            self.recoveryFileList     = ko.observableArray(recoveryFileList);
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
            self.selectedRecoveryMethod  = ko.observable(selectedRecoveryMethod);
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
            self.recoveryMethod       = ko.observable(recoveryMethod);
            self.recoveryEmployee     = ko.observableArray(recoveryEmployee);
            self.selectedEmployee     = ko.observableArray(selectedEmployee);
        }
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    /**
     * KCP005
     */
    export class ListType {
        static EMPLOYMENT     = 1;
        static CLASSIFICATION = 2;
        static JOB_TITLE      = 3;
        static EMPLOYEE       = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL              = 2;
        static SELECT_FIRST_ITEM       = 3;
        static NO_SELECT               = 4;
    }
}