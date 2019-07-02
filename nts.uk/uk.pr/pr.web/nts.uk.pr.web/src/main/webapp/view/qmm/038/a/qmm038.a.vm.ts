module nts.uk.pr.view.qmm038.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import validation = nts.uk.ui.validation;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;

    export module viewmodel {

        export class ScreenModel {
            statementItems: Array<DataScreen> = [];
            ccg001ComponentOption: GroupOption = null;
            baseDate: KnockoutObservable<any> = ko.observable(moment().format("YYYY/MM/DD"));
            giveCurrTreatYear: KnockoutObservable<string> = ko.observable(moment().format("YYYY/MM"));
            employeeIds: Array<any>;
            numberValidator = new validation.NumberValidator(getText("QMM038_11"), "AverageWage", {required: true});
            dataUpdate: Array<UpdateEmployee> = [];

            constructor() {
                let self = this;

                // CCG001
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 3,
                    showEmployeeSelection: true,
                    showQuickSearchTab: true,
                    showAdvancedSearchTab: true,
                    showBaseDate: true,
                    showClosure: false,
                    showAllClosure: false,
                    showPeriod: false,
                    periodFormatYM: false,

                    /** Required parameter */
                    baseDate: moment().toISOString(),
                    periodStartDate: null,
                    periodEndDate: null,
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
                    showDepartment: true,
                    showWorkplace: false,
                    showClassification: false,
                    showJobTitle: false,
                    showWorktype: false,
                    isMutipleCheck: true,
                    tabindex: 5,
                    showOnStart: true,

                    /**
                     * Self-defined function: Return data from CCG001
                     * @param: data: the data return from CCG001
                     */
                    returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                        self.employeeIds = data.listEmployee.map(item => item.employeeId);
                        self.findByEmployee();
                    }
                };

                service.defaultData().done(function (response) {
                    if (response[0] != null) {
                        self.giveCurrTreatYear(response[0].substr(0, 4) + "/" + response[0].substr(4));
                        if(response[1] != null) {
                            self.baseDate(response[1]);
                        }
                        self.ccg001ComponentOption.baseDate = self.baseDate();
                    }
                    $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
                    self.loadMGrid();
                    $("#A2_3").focus();
                });
            }

            loadMGrid() {
                let self = this;
                let height = $(window).height() - 90 - 285;
                let width = $(window).width() + 20 - 1170;
                new nts.uk.ui.mgrid.MGrid($("#gridStatement")[0], {
                    width: '1000px',
                    height: '350px',
                    subWidth: width + 'px',
                    subHeight: height + 'px',
                    headerHeight: '23px',
                    dataSource: self.statementItems,
                    primaryKey: 'employeeCode',
                    primaryKeyDataType: 'string',
                    rowVirtualization: true,
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    enter: 'right',
                    autoFitWindow: false,
                    hidePrimaryKey: false,
                    errorsOnPage: false,
                    columns: [
                        {
                            headerText: getText("QMM038_7"), key: 'employeeCode', dataType: 'string', width: '200px',
                            ntsControl: "Label"
                        },
                        {
                            headerText: getText("QMM038_8"), key: 'businessName', dataType: 'string', width: "200px",
                            ntsControl: "Label"
                        },
                        {
                            headerText: getText("QMM038_9"), key: 'departmentName', dataType: 'string', width: "200px",
                            ntsControl: "Label"
                        },
                        {
                            headerText: getText("QMM038_10"), key: 'employmentName', dataType: 'string', width: "200px",
                            ntsControl: "Label"
                        },
                        {
                            headerText: getText("QMM038_11"), key: 'averageWage', dataType: 'string', width: "200px",
                            columnCssClass: 'currency-symbol',
                            constraint: {
                                cDisplayType: "Currency",
                                min: self.numberValidator.constraint.min,
                                max: self.numberValidator.constraint.max,
                                required: true
                            }
                        }
                    ],
                    features: [
                        {
                            name: "Sorting",
                            columnSettings: [
                                {columnKey: "employeeCode", allowSorting: true, type: "String"},
                                {columnKey: "businessName", allowSorting: true, type: "String"},
                                {columnKey: "departmentName", allowSorting: true, type: "String"},
                                {columnKey: "employmentName", allowSorting: true, type: "String"},
                                {columnKey: "averageWage", allowSorting: true, type: "Number"}
                            ]
                        },
                        {
                            name: 'HeaderStyles',
                            columns: [
                                {key: 'employeeCode', colors: ['left-align']},
                                {key: 'businessName', colors: ['left-align']},
                                {key: 'departmentName', colors: ['left-align']},
                                {key: 'employmentName', colors: ['left-align']},
                                {key: 'averageWage', colors: ['left-align']}
                            ]
                        },
                        {
                            name: 'Paging',
                            pageSize: 100,
                            currentPageIndex: 0
                        },
                    ]
                }).create();
            }


            findByEmployee() {
                let self = this;
                if ($('#A2_3').ntsError('hasError')) {
                    dialog.alertError({messageId:'MsgQ_257'});
                    $('#A2_3').focus();
                    return;
                }
                let command = {
                    employeeIds: self.employeeIds,
                    baseDate: self.baseDate(),
                    giveCurrTreatYear: moment(self.giveCurrTreatYear(),"YYYY/MM").format("YYYY/MM")
                };
                block.invisible();
                service.findByEmployee(command).done(function (response) {
                    self.statementItems = [];
                    if(response && response.length) {
                        self.statementItems = response.map(x => new DataScreen(x));
                        self.statementItems = _.sortBy(self.statementItems, ["employeeCode"]);
                    }
                    $("#gridStatement").mGrid("destroy");
                    self.loadMGrid();
                }).always(() => {
                    block.clear();
                });

            }

            isValidForm(): boolean {
                return _.isEmpty($("#gridStatement").mGrid("errors", true));
            }

            updateStatementItemName() {
                let self = this;
                $('.nts-input').trigger('validate');
                if (errors.hasError() || !self.isValidForm()) {
                    return;
                }
                block.invisible();
                // update
                self.dataUpdate = [];
                _.forEach($("#gridStatement").mGrid("dataSource", true), (item: DataScreen) => {
                    self.dataUpdate.push(new UpdateEmployee(item.employeeId, item.averageWage));
                });
                let command = {
                    employeeDtoList: self.dataUpdate,
                    giveCurrTreatYear: moment(self.giveCurrTreatYear(),"YYYY/MM").format("YYYY/MM")
                };
                service.update(command).done(function (response) {
                    if (response[0] == "Msg_15") {
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function () {
                            $("#A2_3").focus();
                        });
                    }
                }).fail(function (error) {

                }).always(function () {
                    block.clear();
                });
            }
        }
    }

    export interface IDataScreen {
        employeeId: string; // 基準日
        employeeCode: string; // 社員コード
        businessName: string; // ビジネスネーム
        departmentName: string; // 所属部門
        employmentName: string; // 所属雇用
        averageWage: string; // 所属雇用
    }

    export class DataScreen {
        employeeId: string; // 基準日
        employeeCode: string; // 社員コード
        businessName: string; // ビジネスネーム
        departmentName: string; // 所属部門
        employmentName: string; // 所属雇用
        averageWage: string; // 所属雇用

        constructor(param: IDataScreen) {
            this.employeeId = param.employeeId;
            this.employeeCode = param.employeeCode;
            this.businessName = param.businessName;
            this.departmentName = param.departmentName;
            this.employmentName = param.employmentName;
            this.averageWage = param.averageWage.toString();
        }
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
        showDepartment?: boolean; // 部門条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
        isTab2Lazy?: boolean;
        showOnStart?: boolean;
        tabindex?: number;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

    export class UpdateEmployee {
        employeeId: string;
        averageWage: string;

        constructor(employeeId: string, averageWage: string) {
            this.employeeId = employeeId;
            this.averageWage = averageWage;
        }
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        affiliationId: string;
        affiliationName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

}