module nts.uk.pr.view.qmm025.a.viewmodel {
    import time = nts.uk.time;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import validation = nts.uk.ui.validation;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import info = nts.uk.ui.dialog.info;

    export class ScreenModel {
        year: KnockoutObservable<string> = ko.observable(null);
        japanYear: KnockoutObservable<string> = ko.observable(null);

        empDeptItems: Array<EmpInfoDeptDto> = [];
        empAmountItems: Array<RsdtTaxPayAmountDto> = [];

        ccg001ComponentOption: GroupOption = null;
        baseDate: KnockoutObservable<string> = ko.observable(moment().toISOString());
        empSearchItems: Array<EmployeeSearchDto>;

        residentTaxValidator = new validation.NumberValidator(getText("QMM025_28"), "ResidentTax", {required: true});

        employIdLogin: any;

        constructor() {
            let self = this;
            self.employIdLogin = __viewContext.user.employeeId;

            self.year.subscribe(newYear => {
                let year = self.formatYear(newYear);
                if (isNaN(year)) {
                    self.japanYear("");
                } else {
                    self.japanYear("(" + time.yearInJapanEmpire(year).toString() + ")");
                }

            });

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
                    self.empSearchItems = data.listEmployee;
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
            // self.loadGrid();
            self.loadMGrid();
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }

        loadMGrid() {
            let self = this;
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1070px",
                height: "200px",
                subWidth: "130px",
                subHeight: "270px",
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
                        showHeaderCheckbox: true,
                        ntsControl: 'CheckInputAtr'
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
                        ntsControl: "Label"
                    },
                    // A3_6
                    {
                        headerText: getText("QMM025_13"), key: 'year', dataType: 'string', width: "100px",
                        ntsControl: "Label"
                    },
                    // A3_7
                    {
                        headerText: getText("QMM025_14"), key: 'inputAtr', width: "50px", dataType: 'boolean',
                        ntsControl: 'CheckInputAtr'
                    },

                    {
                        headerText: getText("QMM025_15"), key: 'amountJune', dataType: 'string', width: '130px',
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            // primitiveValue: "ResidentTax",
                            cDisplayType: "Currency",
                            min: self.residentTaxValidator.constraint.min,
                            max: self.residentTaxValidator.constraint.max,
                            required: true
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
                        name: 'CheckInputAtr', options: {value: 1, text: ''}, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        click: function () {
                            alert('Do something.');
                        }
                    }
                ],
                features: [
                    {
                        name: 'Paging',
                        pageSize: 20,
                        currentPageIndex: 0
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
                                columnKey: "year",
                                isFixed: true
                            }
                        ]
                    }
                ]
            }).create();
        }

        setDelete(sid: string, isDelete: boolean) {
            let self = this;
            self.setStateControl(sid, "sid", isDelete);
            self.setStateControl(sid, "selectedEmp", isDelete);
            self.setStateControl(sid, "departmentName", isDelete);
            self.setStateControl(sid, "empCd", isDelete);
            self.setStateControl(sid, "empName", isDelete);
            self.setStateControl(sid, "rsdtTaxPayeeName", isDelete);
            self.setStateControl(sid, "sid", isDelete);
            self.setStateControl(sid, "year", isDelete);

            self.disableControl(sid, "inputAtr", isDelete);
            self.disableControl(sid, "amountJune", isDelete);
            self.disableControl(sid, "amountJuly", isDelete);
            self.disableControl(sid, "amountAugust", isDelete);
            self.disableControl(sid, "amountSeptember", isDelete);
            self.disableControl(sid, "amountOctober", isDelete);
            self.disableControl(sid, "amountNovember", isDelete);
            self.disableControl(sid, "amountDecember", isDelete);
            self.disableControl(sid, "amountJanuary", isDelete);
            self.disableControl(sid, "amountFebruary", isDelete);
            self.disableControl(sid, "amountMarch", isDelete);
            self.disableControl(sid, "amountApril", isDelete);
            self.disableControl(sid, "amountMay", isDelete);
        }

        setDisable(sid: string, isDisable: boolean) {
            let self = this;
            self.disableControl(sid, "amountAugust", isDisable);
            self.disableControl(sid, "amountSeptember", isDisable);
            self.disableControl(sid, "amountOctober", isDisable);
            self.disableControl(sid, "amountNovember", isDisable);
            self.disableControl(sid, "amountDecember", isDisable);
            self.disableControl(sid, "amountJanuary", isDisable);
            self.disableControl(sid, "amountFebruary", isDisable);
            self.disableControl(sid, "amountMarch", isDisable);
            self.disableControl(sid, "amountApril", isDisable);
            self.disableControl(sid, "amountMay", isDisable);
        }

        setStateControl(rowId, columnKey, isDelete) {
            if (isDelete) {
                $("#grid").mGrid("setState", rowId, columnKey, ['delete']);
            } else {
                $("#grid").mGrid("setState", rowId, columnKey, ['de']);
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
            })
            let param = {
                listSId: listSId,
                baseDate: self.baseDate(),
                year: self.formatYear(self.year())
            }
            return param;
        }

        /**
         * 起動する
         */
        initData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let param = self.createParamGet();
            let getEmpInfoDept = service.getEmpInfoDept(param);
            let getRsdtTaxPayAmount = service.getRsdtTaxPayAmount(param);

            $.when(getEmpInfoDept, getRsdtTaxPayAmount).done((depts, amounts) => {
                self.empDeptItems = EmpInfoDeptDto.fromApp(depts);
                let data: Array<RsdtTaxPayAmountDto> = RsdtTaxPayAmountDto.fromApp(amounts, self.empDeptItems,
                    self.empSearchItems);
                self.empAmountItems = data;
                // $("#grid").ntsGrid("destroy")
                // self.loadGrid();
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
                // $("#grid").ntsGrid("destroy")
                // self.loadGrid();
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
            self.validateForm();
            if (nts.uk.ui.errors.hasError()) {
                block.clear();
                return false;
            }
            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").data("igGrid").dataSource.dataSource();
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
            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").data("igGrid").dataSource.dataSource();
            let listEmpSelected = _.filter(empAmountItems, (item: RsdtTaxPayAmountDto) => {
                return item.selectedEmp;
            });
            let listSId = _.map(listEmpSelected, (item: RsdtTaxPayAmountDto) => {
                return item.sid;
            });
            service.deleteTaxPayAmount(new DeleteCommand(listSId, self.formatYear(self.year()))).done(() => {
                info({messageId: "Msg_16"}).then(() => {
                    self.getEmpAmount();
                });
            }).always(() => {
                self.focusA3_1();
                block.clear();
            })
        }

        validateForm() {
            let self = this,
                check: any;
            nts.uk.ui.errors.clearAll();
            let empAmountItems: Array<RsdtTaxPayAmountDto> = $("#grid").data("igGrid").dataSource.dataView();
            _.each(empAmountItems, (item: RsdtTaxPayAmountDto) => {
                if ((item.selectedEmp)) return;
                let check,
                    sid = item.sid;
                check = self.residentTaxValidator.validate(item.amountJune);
                if (!check.isValid) {
                    self.setError(sid, "amountJune", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountJuly);
                if (!check.isValid) {
                    self.setError(sid, "amountJuly", check.errorCode, check.errorMessage);
                }
                if (!item.inputAtr) return;
                check = self.residentTaxValidator.validate(item.amountAugust);
                if (!check.isValid) {
                    self.setError(sid, "amountAugust", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountSeptember);
                if (!check.isValid) {
                    self.setError(sid, "amountSeptember", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountOctober);
                if (!check.isValid) {
                    self.setError(sid, "amountOctober", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountNovember);
                if (!check.isValid) {
                    self.setError(sid, "amountNovember", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountDecember);
                if (!check.isValid) {
                    self.setError(sid, "amountDecember", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountJanuary);
                if (!check.isValid) {
                    self.setError(sid, "amountJanuary", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountFebruary);
                if (!check.isValid) {
                    self.setError(sid, "amountFebruary", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountMarch);
                if (!check.isValid) {
                    self.setError(sid, "amountMarch", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountApril);
                if (!check.isValid) {
                    self.setError(sid, "amountApril", check.errorCode, check.errorMessage);
                }
                check = self.residentTaxValidator.validate(item.amountMay);
                if (!check.isValid) {
                    self.setError(sid, "amountMay", check.errorCode, check.errorMessage);
                }
            })
        }

        setError(id: string, key: string, messageId: any, message: any) {
            $("#grid").find("tr[data-id='" + id + "']").find("td[aria-describedby='grid_" + key + "']").ntsError('set', {
                messageId: messageId,
                message: message
            });
            /*$("#grid").find(".nts-grid-control-" + key + "-" + id + " input").ntsError('set', {
                messageId: messageId,
                message: message
            });*/
        }

        jumpToCps001() {
            nts.uk.request.jump("com", "/view/cps/001/a/index.xhtml");
        }

        focusA3_1() {
            $("#grid_container").focus();
        }

        test() {
            let self = this;
            self.setDelete("4f0b9f7d-0883-42df-9c2a-9a34406ab7dd", true);
            self.setDisable("d973be23-a360-44ad-9530-0205f820e46d", true);
            let sss = $("#grid").mGrid("errors");
        }

        test2() {
            let self = this;
            self.setDelete("4f0b9f7d-0883-42df-9c2a-9a34406ab7dd", false);
            self.setDisable("d973be23-a360-44ad-9530-0205f820e46d", false);
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
        // yearTaxAmount: string;//年税額
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

                //dto.year = item.year;
                //dto.yearTaxAmount = "";
                dto.year = item.year;
                dto.rsdtTaxPayeeName = item.rsdtTaxPayeeName;
                dto.inputAtr = ResidentTaxInputAtr.ALL_MONTH == item.inputAtr;
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
            this.sid = data.sid;
            this.amountJune = data.amountJune;
            this.amountJuly = data.amountJuly;
            this.rsdtTaxPayeeName = data.rsdtTaxPayeeName;
            if (data.inputAtr) {
                this.inputAtr = ResidentTaxInputAtr.ALL_MONTH;
                this.amountJanuary = data.amountJanuary;
                this.amountFebruary = data.amountFebruary;
                this.amountMarch = data.amountMarch;
                this.amountApril = data.amountApril;
                this.amountMay = data.amountMay;
                this.amountAugust = data.amountAugust;
                this.amountSeptember = data.amountSeptember;
                this.amountOctober = data.amountOctober;
                this.amountNovember = data.amountNovember;
                this.amountDecember = data.amountDecember;
            } else {
                this.inputAtr = ResidentTaxInputAtr.NOT_ALL_MONTH;
                this.amountJanuary = "0";
                this.amountFebruary = "0";
                this.amountMarch = "0";
                this.amountApril = "0";
                this.amountMay = "0";
                this.amountAugust = "0";
                this.amountSeptember = "0";
                this.amountOctober = "0";
                this.amountNovember = "0";
                this.amountDecember = "0";
            }
        }

        static toCommand(datas: Array<RsdtTaxPayAmountDto>) {
            let result: Array<EmpPayAmountCommand> = [];
            _.each(datas, (item: RsdtTaxPayAmountDto) => {
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