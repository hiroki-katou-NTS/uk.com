module nts.uk.pr.view.qmm025.a.viewmodel {
    import time = nts.uk.time;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        year: KnockoutObservable<string>;
        japanYear: KnockoutObservable<string>

        empDeptItems: Array<EmpInfoDeptDto>;
        empAmountItems: KnockoutObservableArray<RsdtTaxPayAmountDto>;
        columns: Array<any>;
        ntsControls: Array<any>;
        currentSid: KnockoutObservable<any>;
        currentSidItems: KnockoutObservableArray<any>;

        ccg001ComponentOption: GroupOption = null;
        baseDate: KnockoutObservable<string>;
        empSearchItems: Array<EmployeeSearchDto>;

        constructor() {
            let self = this;

            self.year = ko.observable("2018");
            self.japanYear = ko.observable("");

            self.year.subscribe(newYear => {
                let year = time.formatDate(new Date(newYear), "yyyy")
                if (isNaN(year)) {
                    self.japanYear("");
                } else {
                    self.japanYear("(" + time.yearInJapanEmpire(year + "06").toLocaleString().split(' ').slice(0, 3).join('') + ")");
                }

            })

            self.empDeptItems = [];
            self.empAmountItems = ko.observableArray([]);
            self.columns = [
                {headerText: "", key: 'sid', width: 100, hidden: true},
                // A3_2
                {
                    headerText: getText("QMM025_9"), key: 'departmentName', width: 100, hidden: false,
                    formatter: _.escape
                },
                // A3_3
                {headerText: getText("QMM025_10"), key: 'empCd', width: 100, hidden: false, formatter: _.escape},
                // A3_4
                {headerText: getText("QMM025_11"), key: 'empName', width: 100, hidden: false, formatter: _.escape},
                // A3_5
                {
                    headerText: getText("QMM025_12"), key: 'rsdtTaxPayeeName', width: 100, hidden: false,
                    formatter: _.escape
                },
                // A3_6
                {
                    headerText: getText("QMM025_13"), key: 'yearTaxAmount', width: 100, hidden: false,
                    formatter: _.escape
                },
                // A3_7
                {
                    headerText: getText("QMM025_14"), key: 'inputAtr', width: 100, hidden: false,
                    dataType: 'boolean', ntsControl: 'CheckInputAtr'
                },
                // A3_8
                {headerText: getText("QMM025_15"), key: 'amountJune', width: 100, hidden: false},
                // A3_9
                {headerText: getText("QMM025_16"), key: 'amountJuly', width: 100, hidden: false},
                // A3_10
                {headerText: getText("QMM025_17"), key: 'amountAugust', width: 100, hidden: false},
                // A3_11
                {headerText: getText("QMM025_18"), key: 'amountSeptember', width: 100, hidden: false},
                // A3_12
                {headerText: getText("QMM025_19"), key: 'amountOctober', width: 100, hidden: false},
                // A3_13
                {headerText: getText("QMM025_20"), key: 'amountNovember', width: 100, hidden: false,},
                // A3_14
                {headerText: getText("QMM025_21"), key: 'amountDecember', width: 100, hidden: false},
                // A3_15
                {headerText: getText("QMM025_22"), key: 'amountJanuary', width: 100, hidden: false},
                // A3_16
                {headerText: getText("QMM025_23"), key: 'amountFebruary', width: 100, hidden: false},
                // A3_17
                {headerText: getText("QMM025_24"), key: 'amountMarch', width: 100, hidden: false},
                // A3_18
                {headerText: getText("QMM025_25"), key: 'amountApril', width: 100, hidden: false},
                // A3_19
                {headerText: getText("QMM025_26"), key: 'amountMay', width: 100, hidden: false}
            ];
            self.ntsControls = [
                {
                    name: 'CheckInputAtr', options: {value: 1, text: ''}, optionsValue: 'value',
                    optionsText: 'text', controlType: 'CheckBox', enable: true
                },
                {
                    name: 'TaxAmount',
                    controlType: 'TextEditor',
                    constraint: {valueType: 'Integer', required: true, format: "Number_Separated"}
                }

            ];
            self.currentSid = ko.observable(null);
            self.currentSidItems = ko.observableArray([]);

            self.baseDate = ko.observable(moment().toISOString());
            self.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: false,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: null,
                showAllClosure: null,
                showPeriod: null,
                periodFormatYM: null,

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: null,
                showOnlyMe: null,
                showSameWorkplace: null,
                showSameWorkplaceAndChild: null,

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
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    block.invisible();
                    self.empSearchItems = data.listEmployee;
                    self.initData().always(() => {
                        block.clear();
                    });
                }
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            // Start component
            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
            self.loadGrid();
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }

        createParamGet() {
            let self = this;
            let listSId = _.map(self.empSearchItems, (item: EmployeeSearchDto) => {
                return item.employeeId;
            })
            let param = {
                listSId: listSId,
                baseDate: self.baseDate(),
                year: moment(self.year()).format("YYYY")
            }
            return param;
        }

        loadGrid() {
            let self = this;
            $("#grid").ntsGrid({
                width: "1180px",
                height: '420px',
                dataSource: self.empAmountItems(),
                primaryKey: 'sid',
                multiple: true,
                value: self.currentSidItems,
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                columns: self.columns,
                ntsControls: self.ntsControls,
                features: [
                    {
                        name: 'Paging',
                        pageSize: 10,
                        currentPageIndex: 0
                    },
                    /*{
                        name: "ColumnFixing",
                        showFixButtons: false,
                        fixingDirection: 'left',
                        columnSettings: [
                            {
                                columnKey: "departmentName",
                                isFixed: true
                            },
                            {
                                columnKey: "empCd",
                                isFixed: true
                            },
                            {
                                columnKey: "empName",
                                isFixed: true
                            },
                            {
                                columnKey: "rsdtTaxPayeeName",
                                isFixed: true
                            },
                            {
                                columnKey: "yearTaxAmount",
                                isFixed: true
                            }
                        ]
                    }*/
                ],
                ntsFeatures: [],

            })
        }

        /**
         * 起動する
         */
        initData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let param = self.createParamGet();
            let getEmpInfoDept = service.getEmpInfoDept(param);
            let getRsdtTaxPayAmount = service.getRsdtTaxPayAmount(param);

            $.when(getEmpInfoDept, getRsdtTaxPayAmount).done((depts, amounts) => {
                self.empDeptItems = EmpInfoDeptDto.fromApp(depts);
                let data: Array<RsdtTaxPayAmountDto> = RsdtTaxPayAmountDto.fromApp(amounts, self.empDeptItems,
                    self.empSearchItems);
                self.empAmountItems(data);
                $("#grid").ntsGrid("destroy")
                self.loadGrid();
            }).fail(err => {
                alertError(err);
            }).always(() => {
                dfd.resolve();
            })
            return dfd.promise();
        }

        /**
         * 対象を表示する
         */
        getEmpAmount() {
            let self = this;
            block.invisible();
            let param = self.createParamGet();
            let getRsdtTaxPayAmount = service.getRsdtTaxPayAmount(param);
            $.when(getRsdtTaxPayAmount).done((amounts) => {
                let data: Array<RsdtTaxPayAmountDto> = RsdtTaxPayAmountDto.fromApp(amounts, self.empDeptItems,
                    self.empSearchItems);
                self.empAmountItems(data);
            }).fail(err => {
                alertError(err);
            }).always(() => {
                block.clear();
            })
        }

        /**
         * 住民税を一括登録する
         */
        registerAmount() {

        }

        /**
         * 住民税を一括削除する
         */
        deleteAmount() {

        }
    }

    class EmpInfoDeptDto {
        sid: string;//社員ID
        departmentName: string;//部門表示名

        constructor() {
        }

        static fromApp(items: Array<IEmpInfoDeptDto>): Array<EmpInfoDeptDto> {
            let results: Array<EmpInfoDeptDto> = [];
            _.each(items, (item: IEmpInfoDeptDto) => {
                let dto: EmpInfoDeptDto = new EmpInfoDeptDto();
                dto.sid = item.sid;
                dto.departmentName = item.departmentName;
                results.push(dto);
            })

            return results;
        }
    }

    interface IEmpInfoDeptDto {
        sid: string;//社員ID
        departmentName: string;//部門表示名
    }

    class RsdtTaxPayAmountDto {
        sid: string;//社員ID
        departmentName: string;//部門表示名
        empCd: string;//社員コード
        empName: string;//社員名称
        year: number;//年度
        rsdtTaxPayeeName: string;//住民税納付先.名称
        yearTaxAmount: string;//年税額
        inputAtr: boolean;//社員住民税納付額情報.入力区分
        amountJanuary: number;//社員住民税納付額情報.月次納付額.1月納付額
        amountFebruary: number;//社員住民税納付額情報.月次納付額.2月納付額
        amountMarch: number;//社員住民税納付額情報.月次納付額.3月納付額
        amountApril: number;//社員住民税納付額情報.月次納付額.4月納付額
        amountMay: number;//社員住民税納付額情報.月次納付額.5月納付額
        amountJune: number;//社員住民税納付額情報.月次納付額.6月納付額
        amountJuly: number;//社員住民税納付額情報.月次納付額.7月納付額
        amountAugust: number;//社員住民税納付額情報.月次納付額.8月納付額
        amountSeptember: number;//社員住民税納付額情報.月次納付額.9月納付額
        amountOctober: number;//社員住民税納付額情報.月次納付額.10月納付額
        amountNovember: number;//社員住民税納付額情報.月次納付額.11月納付額
        amountDecember: number;//社員住民税納付額情報.月次納付額.12月納付額

        constructor() {
        }

        static fromApp(items: Array<IRsdtTaxPayAmountDto>,
                       empDeptItems: Array<EmpInfoDeptDto>,
                       empSearchItems: Array<EmployeeSearchDto>): Array<RsdtTaxPayAmountDto> {
            let results: Array<RsdtTaxPayAmountDto> = [];
            _.each(items, (item: IRsdtTaxPayAmountDto) => {
                let dto: RsdtTaxPayAmountDto = new RsdtTaxPayAmountDto();
                dto.sid = item.sid;

                let empDept: EmpInfoDeptDto = _.find(empDeptItems, {'sid': item.sid});
                if (empDept == null) {
                    dto.departmentName = "";
                } else {
                    dto.departmentName = empDept.departmentName;
                }

                let empSearch: EmployeeSearchDto = _.find(empSearchItems, {'employeeId': item.sid});
                if (empSearch == null) {
                    dto.empCd = "";
                    dto.empName = "";
                } else {
                    dto.empCd = empSearch.employeeCode;
                    dto.empName = empSearch.employeeName;
                }

                dto.year = item.year;
                dto.rsdtTaxPayeeName = item.rsdtTaxPayeeName;
                dto.inputAtr = item.inputAtr == 1 ? true : false;
                dto.amountJanuary = item.amountJanuary;
                dto.amountFebruary = item.amountFebruary;
                dto.amountMarch = item.amountMarch;
                dto.amountApril = item.amountApril;
                dto.amountMay = item.amountMay;
                dto.amountJune = item.amountJune;
                dto.amountJuly = item.amountJuly;
                dto.amountAugust = item.amountAugust;
                dto.amountSeptember = item.amountSeptember;
                dto.amountOctober = item.amountOctober;
                dto.amountNovember = item.amountNovember;
                dto.amountDecember = item.amountDecember;
                results.push(dto);
            })

            return results;
        }
    }

    interface IRsdtTaxPayAmountDto {
        sid: string;//社員ID
        year: number;//年度
        rsdtTaxPayeeName: string;//住民税納付先.名称
        inputAtr: number;//社員住民税納付額情報.入力区分
        amountJanuary: number;//社員住民税納付額情報.月次納付額.1月納付額
        amountFebruary: number;//社員住民税納付額情報.月次納付額.2月納付額
        amountMarch: number;//社員住民税納付額情報.月次納付額.3月納付額
        amountApril: number;//社員住民税納付額情報.月次納付額.4月納付額
        amountMay: number;//社員住民税納付額情報.月次納付額.5月納付額
        amountJune: number;//社員住民税納付額情報.月次納付額.6月納付額
        amountJuly: number;//社員住民税納付額情報.月次納付額.7月納付額
        amountAugust: number;//社員住民税納付額情報.月次納付額.8月納付額
        amountSeptember: number;//社員住民税納付額情報.月次納付額.9月納付額
        amountOctober: number;//社員住民税納付額情報.月次納付額.10月納付額
        amountNovember: number;//社員住民税納付額情報.月次納付額.11月納付額
        amountDecember: number;//社員住民税納付額情報.月次納付額.12月納付額
    }

    // Note: Defining these interfaces are optional
    interface GroupOption {
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

    interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }
}