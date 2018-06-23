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
        dataRecoveryProcessId: KnockoutObservable<string> = ko.observable(null);
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
        kcp005ComponentOptionScreenG: any;
        selectedEmployeeCodeScreenG: KnockoutObservableArray<string> = ko.observableArray([]);
        employeeListScreenG: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
        //Screen H
        recoveryMethodDescription1: KnockoutObservable<string> = ko.observable("");
        recoveryMethodDescription2: KnockoutObservable<string> = ko.observable("");
        dataRecoverySummary: KnockoutObservable<DataRecoverySummary> = ko.observable(new DataRecoverySummary([], 0, [], []));
        kcp005ComponentOptionScreenH: any;
        selectedEmployeeCodeScreenH: KnockoutObservableArray<string> = ko.observableArray([]);
        employeeListScreenH: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

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
            $("#H4_1").ntsFixedTable({ height: 164 });

            //_____KCP005G________
            self.kcp005ComponentOptionScreenG = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeListScreenG,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCodeScreenG,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: [],
                isShowWorkPlaceName: false,
                isShowSelectAllButton: true,
                maxRows: 15
            };

            //_____KCP005H________
            self.kcp005ComponentOptionScreenH = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeListScreenH,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCodeScreenH,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: [],
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                maxRows: 20
            };

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
            console.log(fileInfo);
            if (fileInfo.id != null && fileInfo.originalName != null) {
                setShared("CMF004lParams", {
                    fileId: fileInfo.id,
                    fileName: fileInfo.originalName
                });
                nts.uk.ui.windows.sub.modal('../c/index.xhtml').onClosed(()=>{
                    setShared("CMF004_D_PARAMS", getShared("CMF004_D_PARAMS"));
                    nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(()=>{
                        if (getShared("CMF004_E_PARAMS")){
                            setShared("CMF004_E_PARAMS", getShared("CMF004_E_PARAMS"));
                            $('#data-recovery-wizard').ntsWizard("next");
                        }
                    });
                });
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

            self.startDateString(moment.utc().subtract(1, "M").format("YYYY/MM/DD"));
            self.endDateString(moment.utc().format("YYYY/MM/DD"));
            let paramSearch = {
                startDate: moment.utc(self.dataRecoverySelection().executePeriodInput().startDate, "YYYY/MM/DD hh:mm:ss").toISOString(),
                endDate: moment.utc(self.dataRecoverySelection().executePeriodInput().endDate, "YYYY/MM/DD hh:mm:ss").add(1, "d").add(-1, "s").toISOString()
            };
            service.findDataRecoverySelection(paramSearch).done(function(data: Array<any>) {
                if (data && data.length) {
                    for (let i = 0; i < data.length; i++) {
                        let itemTarget =
                            {
                                saveSetCode: data[i].code,
                                saveSetName: data[i].name,
                                supplementaryExplanation: data[i].suppleExplanation,
                                storageStartDate: moment.utc(data[i].saveStartDatetime).format('YYYY/MM/DD hh:mm:ss'),
                                executeCategory: (data[i].saveForm) % 2 == 0 ? getText('CMF004_460') : getText('CMF004_461'),
                                targetNumber: data[i].targetNumberPeople + "人",
                                saveFileName: data[i].saveFileName,
                                fileId: data[i].fileId
                            };
                        self.dataRecoverySelection().recoveryFileList.push(itemTarget);
                    }
                }
            });
        }



        /**
         * E:データ内容確認
         */
        initScreenE(): void {
            let self = this;

            //Get Data TableList for Screen E
            service.findTableList('11111111-5a91-4e42-9a29-fefa858942d5').done(function(data: Array<any>) {
                let listCategory: Array<CategoryInfo> = [];
                if (data && data.length) {
                    _.each(data, (x, i) => {
                        let rowNumber = i + 1;
                        let iscanNotBeOld: boolean = (x.canNotBeOld == 1);
                        let isRecover: boolean = iscanNotBeOld;
                        let categoryName = x.categoryName;
                        let categoryId = x.categoryId;
                        let recoveryPeriod = x.retentionPeriodCls;
                        let startOfPeriod = x.saveDateFrom;
                        let endOfPeriod = x.saveDateTo;
                        let recoveryMethod = x.storageRangeSaved == 0 ? getText('CMF004_305') : getText('CMF004_306');
                        listCategory.push(new CategoryInfo(rowNumber, isRecover, categoryId, categoryName, recoveryPeriod, startOfPeriod, endOfPeriod, recoveryMethod, iscanNotBeOld));
                    });
                    self.dataContentConfirm().dataContentcategoryList(listCategory);
                    self.recoverySourceFile(data[0].compressedFileName + '.zip');
                    self.recoverySourceCode(data[0].saveSetCode);
                    self.recoverySourceName(data[0].saveSetName);
                    self.supplementaryExplanation(data[0].supplementaryExplanation);
                }

            });
        }

        getTextRecovery(recoveryPeriod): string {
            if (recoveryPeriod() === 0) return getText("Enum_TimeStore_FULL_TIME");
            if (recoveryPeriod() === 1) return getText("Enum_TimeStore_DAILY");
            if (recoveryPeriod() === 2) return getText("Enum_TimeStore_MONTHLY");
            if (recoveryPeriod() === 3) return getText("Enum_TimeStore_ANNUAL");
        }

        /**
         * F:データ復旧期間変更
         */
        initScreenF(): void {
            let self = this;
            let _listCategory = _.filter(self.dataContentConfirm().dataContentcategoryList(), x => { return x.isRecover() == true; });
            let _itemList: Array<CategoryInfo> = [];
            _.forEach(_listCategory, (x, i) => {
                _itemList.push(new CategoryInfo(i + 1, x.isRecover(), x.categoryId(), x.categoryName(), x.recoveryPeriod(), x.startOfPeriod(), x.endOfPeriod(), x.recoveryMethod(), x.iscanNotBeOld()));
            });
            self.changeDataRecoveryPeriod().changeDataCategoryList(_itemList);
        }

        initScreenG(): void {
            let self = this;
            //Get Data PerformDataRecover for Screen KCP 005
            service.findPerformDataRecover('11111111-5a91-4e42-9a29-fefa858942d5').done(function(data: any) {
                if (data.targets) {
                    self.employeeListScreenG.removeAll();
                    _.forEach(data.targets, x => {
                        self.employeeListScreenG.push({ code: x.scd, name: x.bussinessName, id: x.sid });
                    });
                }
            })
        }

        /**
         * H:データ復旧サマリ
         */
        initScreenH(): void {
            let self = this;
            let _categoryList = self.getRecoveryCategory(self.changeDataRecoveryPeriod().changeDataCategoryList());
            let _employeeList = self.getRecoveryEmployee(self.employeeListScreenG(), self.selectedEmployeeCodeScreenG());
            self.employeeListScreenH(_employeeList);
            let _recoveryMethod = self.dataContentConfirm().selectedRecoveryMethod();
            let _recoveryMethodDescription1 = self.getRecoveryMethodDescription1(_recoveryMethod);
            let _recoveryMethodDescription2 = self.getRecoveryMethodDescription2(_recoveryMethod);
            self.dataRecoverySummary().recoveryCategoryList(_categoryList);
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
                _itemList.push(new CategoryInfo(i + 1, x.isRecover(), x.categoryId(), x.categoryName(), x.recoveryPeriod(), startDate, endDate, x.recoveryMethod(), x.iscanNotBeOld()));
            });
            return _itemList;
        }

        formatDate(recoveryPeriod, dateFormat): string {
            if (recoveryPeriod() == PeriodEnum.DAY) {
                return moment.utc(dateFormat).format("YYYY/MM/DD");
            }
            if (recoveryPeriod() == PeriodEnum.MONTH) {
                return nts.uk.time.formatYearMonth(parseInt(dateFormat));
            }
            return dateFormat;
        }

        /**
         * Get recovery employee
         */
        getRecoveryEmployee(dataEmployeeList: Array<UnitModel>, selectedEmployeeList: Array<string>): Array<UnitModel> {
            let employeeList: Array<any> = [];
            _.each(selectedEmployeeList, x => {
                let employee = _.find(dataEmployeeList, x1 => { return x1.code === x });
                if (employee) {
                    employeeList.push(employee);
                }
            });
            return employeeList;
        }

        getRecoveryMethodDescription1(recoveryMethod: number): string {
            if (recoveryMethod == RecoveryMethod.RESTORE_ALL) return getText('CMF004_92');
            return getText('CMF004_93');
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
            nts.uk.ui.errors.clearAll();
            let checkItemE = _.filter(self.dataContentConfirm().dataContentcategoryList(), x => { return x.isRecover() == true; }).length;
            (checkItemE == 0) ? $('#E5_2').ntsError('set', { messageId: "Msg_1256" }) : $('#data-recovery-wizard').ntsWizard("next");
        }

        nextToScreenG(): void {
            let self = this;
            self.initScreenG();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        nextToScreenH(): void {
            let self = this;
            self.initScreenH();
            $('#data-recovery-wizard').ntsWizard("next");
        }

        restoreData_click(): void {
            nts.uk.ui.windows.sub.modal("/view/cmf/004/i/index.xhtml");
        }

        backToPreviousScreen(): void {
            $('#data-recovery-wizard').ntsWizard("prev");
            nts.uk.ui.errors.clearAll();
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
        FULLTIME = 0, //全期間一律
        DAY = 1, //日次
        MONTH = 2, //月次
        YEAR = 3  //年次
    }

    export enum RecoveryMethod {
        RESTORE_ALL = 0, //全件復旧
        SELECTED_RANGE = 1 //選択した範囲で復旧
    }

    export class CategoryInfo {
        rowNumber: KnockoutObservable<number>;
        isRecover: KnockoutObservable<boolean>;
        categoryId: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        recoveryPeriod: KnockoutObservable<string>;
        recoveryMethod: KnockoutObservable<string>;
        startOfPeriod: KnockoutObservable<string>;
        endOfPeriod: KnockoutObservable<string>;
        iscanNotBeOld: KnockoutObservable<boolean>;
        constructor(rowNumber: number, isRecover: boolean, categoryId: string, categoryName: string, recoveryPeriod: string, startOfPeriod: string, endOfPeriod: string, recoveryMethod: string, iscanNotBeOld: boolean) {
            let self = this;
            self.rowNumber = ko.observable(rowNumber);
            self.isRecover = ko.observable(isRecover);
            self.categoryId = ko.observable(categoryId);
            self.categoryName = ko.observable(categoryName);
            self.recoveryPeriod = ko.observable(recoveryPeriod);
            self.startOfPeriod = ko.observable(startOfPeriod);
            self.endOfPeriod = ko.observable(endOfPeriod);
            self.recoveryMethod = ko.observable(recoveryMethod);
            self.iscanNotBeOld = ko.observable(iscanNotBeOld);
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
        static EMPLOYMENT = 1;
        static CLASSIFICATION = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        id: string;
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