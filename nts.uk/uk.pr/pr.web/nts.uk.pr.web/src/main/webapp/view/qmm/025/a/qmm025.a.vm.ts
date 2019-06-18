module nts.uk.pr.view.qmm025.a.viewmodel {
    import time = nts.uk.time;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import validation = nts.uk.ui.validation;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;
    import info = nts.uk.ui.dialog.info;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        year: KnockoutObservable<string> = ko.observable(null);
        japanYear: KnockoutObservable<string> = ko.observable(null);

        empDeptItems: Array<EmpInfoDeptDto> = [];
        empAmountItems: Array<RsdtTaxPayAmountDto> = [];

        ccg001ComponentOption: GroupOption = null;
        baseDate: string;
        empSearchItems: Array<EmployeeSearchDto>;

        residentTaxValidator = new validation.NumberValidator(getText("QMM025_28"), "ResidentTax", {required: true});

        employIdLogin: any;

        enableA2_8: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.employIdLogin = __viewContext.user.employeeId;

            self.year.subscribe(newYear => {
                let year = self.formatYear(newYear);
                let yearJp = time.yearInJapanEmpire(year);
                if (isNullOrUndefined(yearJp)) {
                    self.japanYear("");
                } else {
                    self.japanYear("(" + yearJp.toString() + ")");
                }

            });

            self.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 3,
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
                showDepartment: true,
                showWorkplace: false,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,
                tabindex: 6,
                showOnStart: true,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.empSearchItems = data.listEmployee;
                    self.baseDate = data.baseDate;
                    self.initData();
                }
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            // Start component
            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
            self.year(self.formatYear(new Date()));
            self.loadMGrid();
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }

        loadMGrid() {
            let self = this;
            let height = $(window).height() - 90 - 290; 
            let width = $(window).width() + 20 - 1170;
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '30px',
                dataSource: self.empAmountItems,
                primaryKey: 'sid',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                errorsOnPage: false,
                columns: [
                    {headerText: "ID", key: 'sid', dataType: 'string', hidden: true},
                    {
                        headerText: '', key: 'selectedEmp', width: "35px", dataType: 'boolean',
                        checkbox: true,
                        ntsControl: 'CheckBoxEmp'
                    },
                    // A3_2
                    {
                        headerText: getText("QMM025_9"), key: 'departmentName', dataType: 'string', width: "100px",
                        ntsControl: "Label"
                    },
                    // A3_3
                    {
                        headerText: getText("QMM025_10"), key: 'empCd', dataType: 'string', width: "100px",
                        ntsControl: "Label"
                    },
                    // A3_4
                    {
                        headerText: getText("QMM025_11"), key: 'empName', dataType: 'string', width: "100px",
                        ntsControl: "Label"
                    },
                    // A3_5
                    {
                        headerText: getText("QMM025_12"), key: 'rsdtTaxPayeeName', dataType: 'string', width: "100px",
                        ntsControl: "Label", headerCssClass: "left-align",
                    },
                    // A3_6
                    {
                        headerText: getText("QMM025_13"), key: 'yearTaxAmount', dataType: 'string', width: "100px",
                        ntsControl: "Label", columnCssClass: "halign-right"
                    },
                    // A3_7
                    {
                        headerText: getText("QMM025_14"), key: 'inputAtr', width: "35px", dataType: 'boolean',
                        ntsControl: 'CheckBoxInputAtr'
                    },

                    {
                        headerText: getText("QMM025_15"), key: 'amountJune', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_16"), key: 'amountJuly', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_17"), key: 'amountAugust', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_18"), key: 'amountSeptember', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_19"), key: 'amountOctober', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_20"), key: 'amountNovember', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_21"), key: 'amountDecember', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_22"), key: 'amountJanuary', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_23"), key: 'amountFebruary', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_24"), key: 'amountMarch', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_25"), key: 'amountApril', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    },
                    {
                        headerText: getText("QMM025_26"), key: 'amountMay', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: false
                        }
                    }
                ],
                ntsControls: [
                    {
                        name: 'CheckBoxInputAtr', options: {value: 1, text: ''}, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (id, columnKey, value, rowData) {
                            self.selectInputAtr(id, value);
                        }
                    },
                    {
                        name: 'CheckBoxEmp', options: {value: 1, text: ''}, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (id, columnKey, value, rowData) {
                            self.selectEmp(id, value, rowData);
                        }
                    }
                ],
                features: [
                    {
                        name: "Sorting",
                        columnSettings: [
                            {columnKey: "departmentName", allowSorting: true, type: "String"},
                            {columnKey: "empCd", allowSorting: true, type: "String"},
                            {columnKey: "empName", allowSorting: true, type: "String"},
                            {columnKey: "rsdtTaxPayeeName", allowSorting: true},
                            {columnKey: "yearTaxAmount", allowSorting: true, type: "String"},
                            {columnKey: "inputAtr", allowSorting: true, type: "String"},
                            {columnKey: "amountJune", allowSorting: true, type: "Number"},
                            {columnKey: "amountJuly", allowSorting: true, type: "Number"},
                            {columnKey: "amountAugust", allowSorting: true, type: "Number"},
                            {columnKey: "amountSeptember", allowSorting: true, type: "Number"},
                            {columnKey: "amountOctober", allowSorting: true, type: "Number"},
                            {columnKey: "amountNovember", allowSorting: true, type: "Number"},
                            {columnKey: "amountDecember", allowSorting: true, type: "Number"},
                            {columnKey: "amountJanuary", allowSorting: true, type: "Number"},
                            {columnKey: "amountFebruary", allowSorting: true, type: "Number"},
                            {columnKey: "amountMarch", allowSorting: true, type: "Number"},
                            {columnKey: "amountApril", allowSorting: true, type: "Number"},
                            {columnKey: "amountMay", allowSorting: true, type: "Number"}
                        ]
                    },
                    {
                        name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'sid', allowResizing: false, minimumWidth: 0
                        }]
                    },
                    {
                        name: 'HeaderStyles',
                        columns: [
                            {key: 'selectedEmp', colors: ['left-align']},
                            {key: 'departmentName', colors: ['left-align']},
                            {key: 'empCd', colors: ['left-align']},
                            {key: 'empName', colors: ['left-align']},
                            {key: 'rsdtTaxPayeeName', colors: ['left-align']},
                            {key: 'yearTaxAmount', colors: ['left-align']}
                        ]
                    },
                    {
                        name: 'Paging',
                        pageSize: 20,
                        currentPageIndex: 0,
                        loaded: function () {
                            self.setPageStatus();
                        }
                    },
                    {
                        name: "ColumnFixing",
                        showFixButtons: false,
                        fixingDirection: 'left',
                        columnSettings: [
                            {
                                columnKey: "sid",
                                isFixed: true
                            },
                            {
                                columnKey: "selectedEmp",
                                isFixed: true
                            },
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
                    }
                ]
            }).create();
            self.setPageStatus();
        }

        selectEmp(id, value, rowData: RsdtTaxPayAmountDto) {
            let self = this;
            self.setDelete(id, value, rowData.inputAtr);
            self.enableA2_8(!_.isEmpty(self.getSidSelected()));
        }

        selectInputAtr(id, value) {
            let self = this;
            self.setDisable(id, value)
        }

        setPageStatus() {
            let self = this;
            let data = $("#grid").mGrid("dataSource");
            _.each(data, (item: RsdtTaxPayAmountDto) => {
                self.setDelete(item.sid, item.selectedEmp, item.inputAtr);
            })
        }

        setDelete(sid: string, isDelete: boolean, isAllMonth: boolean) {
            let self = this;
            self.setStateControl(sid, "selectedEmp", isDelete);
            self.setStateControl(sid, "departmentName", isDelete);
            self.setStateControl(sid, "empCd", isDelete);
            self.setStateControl(sid, "empName", isDelete);
            self.setStateControl(sid, "rsdtTaxPayeeName", isDelete);
            self.setStateControl(sid, "yearTaxAmount", isDelete);
            self.disableControl(sid, "inputAtr", isDelete);
            self.disableControl(sid, "amountJune", isDelete);
            self.disableControl(sid, "amountJuly", isDelete);
            self.setDisable(sid, isDelete ? false : isAllMonth);
        }

        setDisable(sid: string, isAllMonth: boolean) {
            let self = this;
            self.disableControl(sid, "amountAugust", !isAllMonth);
            self.disableControl(sid, "amountSeptember", !isAllMonth);
            self.disableControl(sid, "amountOctober", !isAllMonth);
            self.disableControl(sid, "amountNovember", !isAllMonth);
            self.disableControl(sid, "amountDecember", !isAllMonth);
            self.disableControl(sid, "amountJanuary", !isAllMonth);
            self.disableControl(sid, "amountFebruary", !isAllMonth);
            self.disableControl(sid, "amountMarch", !isAllMonth);
            self.disableControl(sid, "amountApril", !isAllMonth);
            self.disableControl(sid, "amountMay", !isAllMonth);
        }

        setStateControl(rowId, columnKey, isDelete) {
            if (isDelete) {
                $("#grid").mGrid("setState", rowId, columnKey, ['delete']);
            } else {
                $("#grid").mGrid("clearState", rowId, columnKey, ['delete'])
            }
        }

        disableControl(rowId, columnKey, isDisable) {
            if (isDisable) {
                $("#grid").mGrid("disableNtsControlAt", rowId, columnKey)
            } else {
                $("#grid").mGrid("enableNtsControlAt", rowId, columnKey)
            }
        }

        formatYear(date) {
            return moment(date).format("YYYY");
        }

        createParamGet() {
            let self = this;
            let listSId = _.map(self.empSearchItems, (item: EmployeeSearchDto) => {
                return item.employeeId;
            });
            let param = {
                listSId: listSId,
                baseDate: self.baseDate,
                year: self.formatYear(self.year())
            };
            return param;
        }

        /**
         * 起動する
         */
        initData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            $("#A2_3").ntsError('check');
            if (nts.uk.ui.errors.hasError()) {
                $("#grid").mGrid("destroy");
                self.empAmountItems = [];
                self.loadMGrid();
                block.clear();
                return;
            }
            let param = self.createParamGet();
            let getEmpInfoDept = service.getEmpInfoDept(param);
            let getRsdtTaxPayAmount = service.getRsdtTaxPayAmount(param);

            $.when(getEmpInfoDept, getRsdtTaxPayAmount).done((depts, amounts) => {
                self.empDeptItems = EmpInfoDeptDto.fromApp(depts);
                let data: Array<RsdtTaxPayAmountDto> = RsdtTaxPayAmountDto.fromApp(amounts, self.empDeptItems,
                    self.empSearchItems);
                self.empAmountItems = data;
                $("#grid").mGrid("destroy");
                self.loadMGrid();
            }).always(() => {
                $("#A2_3").focus();
                block.clear();
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
            $.when(getRsdtTaxPayAmount).done((amounts: Array<IRsdtTaxPayAmountDto>) => {
                let data: Array<RsdtTaxPayAmountDto> = RsdtTaxPayAmountDto.fromApp(amounts, self.empDeptItems,
                    self.empSearchItems);
                self.empAmountItems = data;
                $("#grid").mGrid("destroy");
                self.loadMGrid();
            }).always(() => {
                block.clear();
                self.focusA3_1();
            })
        }

        /**
         * 住民税を一括登録する
         */
        registerAmount() {
            let self = this;
            block.invisible();
            if (nts.uk.ui.errors.hasError() || !self.isValidForm()) {
                block.clear();
                return false;
            }

            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").mGrid("dataSource", true);
            service.registerTaxPayAmount(new RegisterCommand(empAmountItems, self.formatYear(self.year()))).done(() => {
                info({messageId: "Msg_15"}).then(() => {
                    self.getEmpAmount();
                });
            }).always(() => {
                self.focusA3_1();
                block.clear();
            })
        }

        /**
         * 住民税を一括削除する
         */
        deleteAmount() {
            let self = this;
            block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteTaxPayAmount(new DeleteCommand(self.getSidSelected(), self.formatYear(self.year()))).done(() => {
                    info({messageId: "Msg_16"}).then(() => {
                        self.enableA2_8(false);
                        self.getEmpAmount();
                    });
                }).always(() => {
                    self.focusA3_1();
                    block.clear();
                })
            }).ifNo(function() {
                block.clear();
                return false;
            })
        }

        getSidSelected(): Array<string> {
            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").mGrid("dataSource", true);
            let listEmpSelected = _.filter(empAmountItems, (item: RsdtTaxPayAmountDto) => {
                return item.selectedEmp;
            });
            let listSId = _.map(listEmpSelected, (item: RsdtTaxPayAmountDto) => {
                return item.sid;
            });
            return listSId;
        }

        isValidForm() {
            let self = this,
                check: any;
            let errorList = $("#grid").mGrid("errors", true);
            if (_.isEmpty(errorList)) {
                return true;
            }
            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").mGrid("dataSource", true);
            let isValid = true;
            _.each(errorList, error => {
                let emp = _.find(empAmountItems, (item: RsdtTaxPayAmountDto) => {
                    return item.sid == error.rowId;
                });
                if (emp.selectedEmp) return;
                if (emp.inputAtr) {
                    isValid = false;
                }
                if (error.columnKey == "amountJune" || error.columnKey == "amountJuly") {
                    isValid = false;
                }
            });
            return isValid;
        }


        jumpToCps001() {
            nts.uk.request.jump("com", "/view/cps/001/a/index.xhtml");
        }

        focusA3_1() {
            $("#grid_container").focus();
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
        selectedEmp: boolean;
        departmentName: string;//部門表示名
        empCd: string;//社員コード
        empName: string;//社員名称
        year: number;//年度
        rsdtTaxPayeeName: string;//住民税納付先.名称
        yearTaxAmount: string;//年税額
        inputAtr: boolean;//社員住民税納付額情報.入力区分
        amountJanuary: string;//社員住民税納付額情報.月次納付額.1月納付額
        amountFebruary: string;//社員住民税納付額情報.月次納付額.2月納付額
        amountMarch: string;//社員住民税納付額情報.月次納付額.3月納付額
        amountApril: string;//社員住民税納付額情報.月次納付額.4月納付額
        amountMay: string;//社員住民税納付額情報.月次納付額.5月納付額
        amountJune: string;//社員住民税納付額情報.月次納付額.6月納付額
        amountJuly: string;//社員住民税納付額情報.月次納付額.7月納付額
        amountAugust: string;//社員住民税納付額情報.月次納付額.8月納付額
        amountSeptember: string;//社員住民税納付額情報.月次納付額.9月納付額
        amountOctober: string;//社員住民税納付額情報.月次納付額.10月納付額
        amountNovember: string;//社員住民税納付額情報.月次納付額.11月納付額
        amountDecember: string;//社員住民税納付額情報.月次納付額.12月納付額

        constructor() {
        }

        static fromApp(items: Array<IRsdtTaxPayAmountDto>,
                       empDeptItems: Array<EmpInfoDeptDto>,
                       empSearchItems: Array<EmployeeSearchDto>): Array<RsdtTaxPayAmountDto> {
            let results: Array<RsdtTaxPayAmountDto> = [];
            _.each(items, (item: IRsdtTaxPayAmountDto) => {
                let dto: RsdtTaxPayAmountDto = new RsdtTaxPayAmountDto();
                dto.sid = item.sid;
                dto.selectedEmp = false;
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
                dto.inputAtr = ResidentTaxInputAtr.ALL_MONTH == item.inputAtr;
                if (dto.inputAtr) {
                    dto.yearTaxAmount = _.sum([
                        item.amountJanuary, item.amountFebruary, item.amountMarch, item.amountApril, item.amountMay,
                        item.amountJune, item.amountJuly, item.amountAugust, item.amountSeptember, item.amountOctober,
                        item.amountNovember, item.amountDecember
                    ]).toString();
                } else {
                    dto.yearTaxAmount = _.sum([item.amountJune, item.amountJuly * 11]).toString();
                }
                dto.yearTaxAmount = nts.uk.ntsNumber.formatNumber(dto.yearTaxAmount, new nts.uk.ui.option.NumberEditorOption({grouplength: 3}));

                dto.amountJanuary = isNullOrUndefined(item.amountJanuary) ? null : item.amountJanuary.toString();
                dto.amountFebruary = isNullOrUndefined(item.amountFebruary) ? null : item.amountFebruary.toString();
                dto.amountMarch = isNullOrUndefined(item.amountMarch) ? null : item.amountMarch.toString();
                dto.amountApril = isNullOrUndefined(item.amountApril) ? null : item.amountApril.toString();
                dto.amountMay = isNullOrUndefined(item.amountMay) ? null : item.amountMay.toString();
                dto.amountJune = isNullOrUndefined(item.amountJune) ? null : item.amountJune.toString();
                dto.amountJuly = isNullOrUndefined(item.amountJuly) ? null : item.amountJuly.toString();
                dto.amountAugust = isNullOrUndefined(item.amountAugust) ? null : item.amountAugust.toString();
                dto.amountSeptember = isNullOrUndefined(item.amountSeptember) ? null : item.amountSeptember.toString();
                dto.amountOctober = isNullOrUndefined(item.amountOctober) ? null : item.amountOctober.toString();
                dto.amountNovember = isNullOrUndefined(item.amountNovember) ? null : item.amountNovember.toString();
                dto.amountDecember = isNullOrUndefined(item.amountDecember) ? null : item.amountDecember.toString();
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

    class RegisterCommand {
        listEmpPayAmount: Array<EmpPayAmountCommand>;
        year: string;

        constructor(empAmountItems: Array<RsdtTaxPayAmountDto>, year: string) {
            this.listEmpPayAmount = EmpPayAmountCommand.toCommand(empAmountItems);
            this.year = year;
        }
    }

    class EmpPayAmountCommand {
        sid: string;//社員ID
        rsdtTaxPayeeName: string;//住民税納付先.名称
        inputAtr: number;//社員住民税納付額情報.入力区分
        amountJanuary: string;//社員住民税納付額情報.月次納付額.1月納付額
        amountFebruary: string;//社員住民税納付額情報.月次納付額.2月納付額
        amountMarch: string;//社員住民税納付額情報.月次納付額.3月納付額
        amountApril: string;//社員住民税納付額情報.月次納付額.4月納付額
        amountMay: string;//社員住民税納付額情報.月次納付額.5月納付額
        amountJune: string;//社員住民税納付額情報.月次納付額.6月納付額
        amountJuly: string;//社員住民税納付額情報.月次納付額.7月納付額
        amountAugust: string;//社員住民税納付額情報.月次納付額.8月納付額
        amountSeptember: string;//社員住民税納付額情報.月次納付額.9月納付額
        amountOctober: string;//社員住民税納付額情報.月次納付額.10月納付額
        amountNovember: string;//社員住民税納付額情報.月次納付額.11月納付額
        amountDecember: string;//社員住民税納付額情報.月次納付額.12月納付額

        constructor(data: RsdtTaxPayAmountDto) {
            let dataDefault = "0";
            this.sid = data.sid;
            this.amountJune = isNullOrEmpty(data.amountJune) ? dataDefault : data.amountJune;
            this.amountJuly = isNullOrEmpty(data.amountJuly) ? dataDefault : data.amountJuly;
            this.rsdtTaxPayeeName = data.rsdtTaxPayeeName;
            if (data.inputAtr) {
                this.inputAtr = ResidentTaxInputAtr.ALL_MONTH;
                this.amountJanuary = isNullOrEmpty(data.amountJanuary) ? dataDefault : data.amountJanuary;
                this.amountFebruary = isNullOrEmpty(data.amountFebruary) ? dataDefault : data.amountFebruary;
                this.amountMarch = isNullOrEmpty(data.amountMarch) ? dataDefault : data.amountMarch;
                this.amountApril = isNullOrEmpty(data.amountApril) ? dataDefault : data.amountApril;
                this.amountMay = isNullOrEmpty(data.amountMay) ? dataDefault : data.amountMay;
                this.amountAugust = isNullOrEmpty(data.amountAugust) ? dataDefault : data.amountAugust;
                this.amountSeptember = isNullOrEmpty(data.amountSeptember) ? dataDefault : data.amountSeptember;
                this.amountOctober = isNullOrEmpty(data.amountOctober) ? dataDefault : data.amountOctober;
                this.amountNovember = isNullOrEmpty(data.amountNovember) ? dataDefault : data.amountNovember;
                this.amountDecember = isNullOrEmpty(data.amountDecember) ? dataDefault : data.amountDecember;
            } else {
                this.inputAtr = ResidentTaxInputAtr.NOT_ALL_MONTH;
                this.amountJanuary = this.amountJuly;
                this.amountFebruary = this.amountJuly;
                this.amountMarch = this.amountJuly;
                this.amountApril = this.amountJuly;
                this.amountMay = this.amountJuly;
                this.amountAugust = this.amountJuly;
                this.amountSeptember = this.amountJuly;
                this.amountOctober = this.amountJuly;
                this.amountNovember = this.amountJuly;
                this.amountDecember = this.amountJuly;
            }
        }

        static toCommand(datas: Array<RsdtTaxPayAmountDto>) {
            let result: Array<EmpPayAmountCommand> = [];
            _.each(datas, (item: RsdtTaxPayAmountDto) => {
                if (item.selectedEmp) return;
                result.push(new EmpPayAmountCommand(item));
            })
            return result;
        }
    }

    class DeleteCommand {
        listSId: Array<string>;
        year: string;

        constructor(listSId: Array<string>, year: string) {
            this.listSId = listSId;
            this.year = year;
        }
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

    class CellState {
        rowId: number;
        columnKey: string;
        state: Array<any>

        constructor(rowId: any, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

    /**
     * 住民税入力区分
     */
    enum ResidentTaxInputAtr {
        ALL_MONTH = 1,
        NOT_ALL_MONTH = 0
    }

}