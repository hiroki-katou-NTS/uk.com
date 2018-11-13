module nts.uk.pr.view.qmm038.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    export module viewmodel {

        export class ScreenModel {
            statementItems: Array<IDataScreen> = [];
            ccg001ComponentOption: GroupOption = null;
            baseDate: KnockoutObservable<string>;


            constructor() {
                let self = this;


                nts.uk.pr.view.qmm038.a.service.defaultData().done(function(response) {
                    self.baseDate = ko.observable(response);
                });



                self.loadGrid();
                // CCG001
                self.ccg001ComponentOption = <GroupOption>{
                    /** Common properties */
                    systemType: 1,
                    showEmployeeSelection: true,
                    showQuickSearchTab: true,
                    showAdvancedSearchTab: true,
                    showBaseDate: true,
                    showClosure: false,
                    showAllClosure: false,
                    showPeriod: false,
                    periodFormatYM: false,

                    /** Required parameter */
                    baseDate: self.baseDate(),
                    periodStartDate: moment().toISOString(),
                    periodEndDate: moment().toISOString(),
                    inService: true,
                    leaveOfAbsence: true,
                    closed: true,
                    retirement: true,

                    /** Quick search tab options */
                    showAllReferableEmployee: true,
                    showOnlyMe: true,
                    showSameWorkplace: false,
                    showSameWorkplaceAndChild: false,

                    /** Advanced search properties */
                    showEmployment: true,
                    showWorkplace: false,
                    showClassification: false,
                    showJobTitle: false,
                    showWorktype: false,
                    isMutipleCheck: false,
                    tabindex: 4,

                    /**
                     * Self-defined function: Return data from CCG001
                     * @param: data: the data return from CCG001
                     */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    }
                }

                $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
            }

            loadGrid(){
                let self = this;
                $("#gridStatement").ntsGrid({
                    width: '807px',
                    height: '459px',
                    dataSource: self.statementItems,
                    primaryKey: 'itemNameCd',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        {headerText: getText("QMM038_7"), key: 'itemNameCd', dataType: 'string', width: '150px'},
                        {headerText: getText("QMM038_8"), key: 'name', dataType: 'string', width: '150px'},
                        {headerText: getText("QMM038_9"), key: 'shortName', dataType: 'string', width: '150px'},
                        {
                            headerText: getText("QMM038_10"), key: 'englishName', dataType: 'string', width: '150px',
                            ntsControl: 'TextEditor'
                        },
                        {
                            headerText: getText("QMM038_11"), key: 'otherLanguageName', dataType: 'string', width: '200px',
                            ntsControl: 'TextEditor'
                        },
                    ],
                    features: [
                        {
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: false
                        },
                        {
                            name: 'Paging',
                            pageSize: 20,
                            currentPageIndex: 0
                        }

                    ],
                    ntsControls: [
                        {name: 'TextEditor', controlType: 'TextEditor', constraint: {valueType: 'String', required: false}}
                    ],
                });
            }

            updateStatelmentItemName() {
                let self = this;
                let statementItems: Array<IDataScreen> = $("#gridStatement").igGrid("option", "dataSource")
                // update
                _.forEach(statementItems, (item: IDataScreen) => {

                })
                self.validateForm(statementItems);
                if(nts.uk.ui.errors.hasError()) {
                    return;
                }
                block.invisible();

                service.update(statementItems).done(() => {

                }).fail(function (error) {

                }).always(function () {
                    block.clear();
                });
            }

            validateForm(statementItems: Array<IDataScreen>) {
                let self = this,
                    check: any;
                nts.uk.ui.errors.clearAll();
                _.each(statementItems, (item: IDataScreen) => {
                    /*check = self.englishNameValidator.validate(item.englishName);
                    if(!check.isValid) {
                        self.setErrorEnglishName(item.itemNameCd, check.errorCode, check.errorMessage)
                    }
                    check = self.otherLanguageNameValidator.validate(item.otherLanguageName);
                    if(!check.isValid) {
                        self.setErrorOtherLanguageName(item.itemNameCd, check.errorCode, check.errorMessage)
                    }*/
                })
            }

        }


    }
        export interface IDataScreen {
            employeeCode: string;
            businessName: string;
            department: string;
            employment: string;
        }

        // Note: Defining these interfaces are optional
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

}