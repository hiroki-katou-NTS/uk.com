/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.d {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
    import validation = nts.uk.ui.validation;
    import disableCell = nts.uk.ui.mgrid.color.Disable;

    const API = {
        INIT_DISPLAY: 'screen/at/kaf021/init-display-approve',
        SEARCH: 'screen/at/kaf021/search-approve',
        APPROVE_DENIAL: 'at/record/monthly/agreement/monthly-result/special-provision/approve-denial',
        BULK_APPROVE: 'at/record/monthly/agreement/monthly-result/special-provision/bulk-approve'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        unapproveChecked: KnockoutObservable<boolean> = ko.observable(true);
        approveChecked: KnockoutObservable<boolean> = ko.observable(false);
        denialChecked: KnockoutObservable<boolean> = ko.observable(false);

        unapproveCount: KnockoutObservable<number> = ko.observable(0);
        unapproveCountStr: KnockoutObservable<string> = ko.observable(null);
        approveCount: KnockoutObservable<number> = ko.observable(0);
        approveCountStr: KnockoutObservable<string> = ko.observable(null);
        denialCount: KnockoutObservable<number> = ko.observable(0);
        denialCountStr: KnockoutObservable<string> = ko.observable(null);

        datePeriod: KnockoutObservable<any> = ko.observable({});

        datas: Array<any> = [];

        commentValidation = new validation.StringValidator(this.$i18n("KAF021_49"), "AgreementApprovalComments", { required: true });
        constructor() {
            super();
            const vm = this;
        }

        created() {
            const vm = this;

            vm.loadMGrid();
            vm.initDisplay().done(() => {
                $("#grid").mGrid("destroy");
                vm.loadMGrid();
            });

            _.extend(window, { vm });
        }

        mounted() {
            const vm = this;


        }

        setStatus() {
            const vm = this;

            let unapproveCount = _.filter(vm.datas, (item: ApplicationListDto) => {
                return item.approvalStatus == common.ApprovalStatusEnum.UNAPPROVED
            });
            let approveCount = _.filter(vm.datas, (item: ApplicationListDto) => {
                return item.approvalStatus == common.ApprovalStatusEnum.APPROVED
            });
            let denialCount = _.filter(vm.datas, (item: ApplicationListDto) => {
                return item.approvalStatus == common.ApprovalStatusEnum.DENY
            });
            vm.unapproveCount(unapproveCount.length);
            vm.approveCount(approveCount.length);
            vm.denialCount(denialCount.length);
            vm.unapproveCountStr(vm.$i18n("KAF021_66", [vm.unapproveCount().toString()]));
            vm.approveCountStr(vm.$i18n("KAF021_66", [vm.approveCount().toString()]));
            vm.denialCountStr(vm.$i18n("KAF021_66", [vm.denialCount().toString()]));
        }

        initDisplay(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            vm.$blockui("invisible");
            vm.datas = [];
            let param: any = {
                status: []
            };
            param.status.push(common.ApprovalStatusEnum.UNAPPROVED);
            vm.$ajax(API.INIT_DISPLAY, param).done((data: common.SpecialProvisionOfAgreementAppListDto) => {
                if (!data.setting.useSpecical) {
                    vm.$dialog.error({ messageId: "Msg_1843" }).done(() => {
                        vm.$jump('com', '/view/ccg/008/a/index.xhtml');
                    });
                    vm.$blockui("clear");
                    return;
                }

                vm.datePeriod({
                    startDate: moment(data.startDate).format("YYYY/MM/DD"),
                    endDate: moment(data.endDate).format("YYYY/MM/DD")
                });
                vm.datas = vm.convertData(data.applications);
                vm.setStatus();
                dfd.resolve();
            }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        search(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            let start = moment(vm.datePeriod().startDate, "YYYY/MM/DD").toISOString();
            let end = moment(vm.datePeriod().endDate, "YYYY/MM/DD").toISOString();
            if (!start || !end) return;
            vm.$blockui("invisible");
            vm.datas = [];
            let param: any = {
                startDate: start,
                endDate: end,
                status: []
            };
            if (vm.unapproveChecked()) param.status.push(common.ApprovalStatusEnum.UNAPPROVED);
            if (vm.approveChecked()) param.status.push(common.ApprovalStatusEnum.APPROVED);
            if (vm.denialChecked()) param.status.push(common.ApprovalStatusEnum.DENY);
            vm.$ajax(API.SEARCH, param).done((data: common.SpecialProvisionOfAgreementAppListDto) => {
                vm.datas = vm.convertData(data.applications);
                vm.setStatus();
                $("#grid").mGrid("destroy");
                vm.loadMGrid();
                dfd.resolve();
            }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        convertData(data: Array<common.IApplicationListDto>): Array<ApplicationListDto> {
            const vm = this;
            let results: Array<ApplicationListDto> = [];
            _.each(data, (item: any) => {
                let result: ApplicationListDto = item;
                result.approvalChecked = false;
                result.denialChecked = false;
                // result.employee = result.employeeCode + "　" + result.employeeName;
                result.employee = result.employeeName;
                if (result.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_MONTH) {
                    let ym = result.applicationTime?.oneMonthTime?.yearMonth.toString();
                    result.appType = textFormat(vm.$i18n("KAF021_64"), ym.substring(4));
                    result.month = parseTime(result.screenDisplayInfo?.overtime?.overtimeHoursOfMonth, true).format();
                    if (result.screenDisplayInfo?.overtimeIncludingHoliday?.overtimeHoursTargetMonth != null) {
                        result.month += "<br>(" + parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.overtimeHoursTargetMonth, true).format() + ")";
                    }
                    result.monthAverage2Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage2Str, true).format();
                    result.monthAverage3Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage3Str, true).format();
                    result.monthAverage4Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage4Str, true).format();
                    result.monthAverage5Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage5Str, true).format();
                    result.monthAverage6Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage6Str, true).format();
                    result.currentMax = parseTime(result.screenDisplayInfo?.upperContents?.oneMonthLimit.error, true).format();
                    result.newMax = parseTime(result.applicationTime?.oneMonthTime?.errorTime?.error, true).format();
                } else if (result.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_YEAR) {
                    result.appType = textFormat(vm.$i18n("KAF021_67"), result.applicationTime?.oneYearTime?.year);
                    result.month = "-"
                    result.currentMax = parseTime(result.screenDisplayInfo?.upperContents?.oneYearLimit.error, true).format();
                    result.newMax = parseTime(result.applicationTime?.oneYearTime?.errorTime?.error, true).format();
                }

                result.year = parseTime(result.screenDisplayInfo?.overtime?.overtimeHoursOfYear, true).format();
                result.exceededNumber = result.screenDisplayInfo?.exceededMonth;
                result.inputDateStr = moment(result.inputDate).format("YY/MM/DD(dd)");
                switch (result.approvalStatus) {
                    case common.ApprovalStatusEnum.UNAPPROVED:
                        result.approverStatusStr = vm.$i18n("KAF021_68");
                        break;
                    case common.ApprovalStatusEnum.APPROVED:
                        result.approverStatusStr = vm.$i18n("KAF021_69");
                        break;
                    case common.ApprovalStatusEnum.DENY:
                        result.approverStatusStr = vm.$i18n("KAF021_71");
                        break;
                }
                switch (result.confirmStatus) {
                    case common.ConfirmationStatusEnum.UNCONFIRMED:
                        result.confirmStatusStr = vm.$i18n("KAF021_68");
                        break;
                    case common.ConfirmationStatusEnum.CONFIRMED:
                        result.confirmStatusStr = vm.$i18n("KAF021_70");
                        break;
                    case common.ConfirmationStatusEnum.DENY:
                        result.confirmStatusStr = vm.$i18n("KAF021_71");
                        break;
                }

                results.push(result);
            })
            return results
        }

        loadMGrid() {
            const vm = this;
            let height = $(window).height() - 90 - 289;
            let width = $(window).width() + 20 - 1250;

            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '60px',
                rowHeight: '40px',
                dataSource: vm.datas,
                primaryKey: 'applicantId',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                columns: vm.getColumns(),
                ntsControls: [
                    {
                        name: 'ApprovalCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: ApplicationListDto) {
                            // UI処理６
                            if (value) {
                                $("#grid").mGrid("updateCell", rowId, "denialChecked", false)
                            }

                        }
                    },
                    {
                        name: 'DenialCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: ApplicationListDto) {
                            // UI処理６
                            if (value) {
                                $("#grid").mGrid("updateCell", rowId, "approvalChecked", false)
                            }
                        }
                    }
                ],
                features: [
                    {
                        name: "ColumnFixing",
                        showFixButtons: false,
                        fixingDirection: 'left',
                        columnSettings: [
                            {
                                columnKey: "applicantId",
                                isFixed: true
                            },
                            {
                                columnKey: "approvalChecked",
                                isFixed: true
                            },
                            {
                                columnKey: "denialChecked",
                                isFixed: true
                            },
                            {
                                columnKey: "workplaceName",
                                isFixed: true
                            },
                            {
                                columnKey: "employee",
                                isFixed: true
                            }
                        ]
                    },
                    {
                        name: 'HeaderStyles',
                        columns: vm.getHeaderStyles()
                    },
                    {
                        name: 'CellStyles',
                        states: vm.getCellStyles()
                    }
                ]
            }).create();
        }

        getColumns(): Array<any> {
            const vm = this;

            var columns = [];
            columns.push({ headerText: "key", key: 'applicantId', dataType: 'string', hidden: true });
            // D2_1
            columns.push({ headerText: vm.$i18n("KAF021_47"), key: 'approvalChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "ApprovalCheckBox" });
            // D2_2
            columns.push({ headerText: vm.$i18n("KAF021_38"), key: 'denialChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "DenialCheckBox" });
            // D2_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'workplaceName', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employee', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_5
            columns.push({ headerText: vm.$i18n("KAF021_2"), key: 'appType', dataType: 'string', width: '70px', ntsControl: "Label" });
            // D2_6
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // D2_7
                    { headerText: vm.$i18n("KAF021_16"), key: "month", dataType: 'string', width: '75px', ntsControl: "Label" },
                    // D2_8
                    { headerText: vm.$i18n("KAF021_26"), key: 'year', dataType: 'string', width: '75px', ntsControl: "Label" }
                ]
            });
            // D2_9
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // D2_10
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // D2_14
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // D2_12
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // D2_13
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // D2_14
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '50px', ntsControl: "Label" });
            // D2_15
            columns.push({
                headerText: vm.$i18n("KAF021_17"),
                group: [
                    // D2_16
                    { headerText: vm.$i18n("KAF021_27"), key: "currentMax", dataType: 'string', width: '75px', ntsControl: "Label" },
                    // D2_17
                    { headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px', ntsControl: "Label" }
                ]
            });

            // D2_18
            columns.push({ headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '360px', ntsControl: "Label" });
            // D2_19
            columns.push({
                headerText: vm.$i18n("KAF021_41"), key: 'comment', dataType: 'string', width: '360px',
                constraint: {
                    primitiveValue: 'AgreementApprovalComments',
                    required: false
                }
            });
            // D2_20
            columns.push({ headerText: vm.$i18n("KAF021_42"), key: 'applicant', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_21
            columns.push({ headerText: vm.$i18n("KAF021_43"), key: 'inputDateStr', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_22
            columns.push({ headerText: vm.$i18n("KAF021_44"), key: 'approver', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_23
            columns.push({ headerText: vm.$i18n("KAF021_34"), key: 'approverStatusStr', dataType: 'string', width: '80px', ntsControl: "Label" });
            // D2_24
            columns.push({ headerText: vm.$i18n("KAF021_45"), key: 'confirmer', dataType: 'string', width: '105px', ntsControl: "Label" });
            // D2_25
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'confirmStatusStr', dataType: 'string', width: '80px', ntsControl: "Label" });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];

            _.forEach(vm.datas, (data: any) => {
                if (!vm.isEnableApproval(data.approvalStatus, data.confirmStatus, data.canApprove, data.canConfirm)) {
                    cellStates.push(new common.CellState(data.applicantId, 'approvalChecked', [disableCell]));
                }

                if (!vm.isEnableDeny(data.approvalStatus, data.confirmStatus, data.canApprove, data.canConfirm)) {
                    cellStates.push(new common.CellState(data.applicantId, 'denialChecked', [disableCell]));
                }

                if (data.canApprove) {
                    cellStates.push(new common.CellState(data.applicantId, 'comment', ["cell-edit"]));
                } else {
                    cellStates.push(new common.CellState(data.applicantId, 'comment', [disableCell]));
                }

                cellStates.push(new common.CellState(data.applicantId, 'appType', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'month', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'year', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'exceededNumber', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'currentMax', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'newMax', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'inputDateStr', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'approverStatusStr', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'confirmStatusStr', ["center-align"]));
            })
            return cellStates;
        }

        getHeaderStyles(): Array<any> {
            const vm = this;
            return [
                { key: "monthAverage2Str", colors: ['padding-12'] },
                { key: "monthAverage3Str", colors: ['padding-12'] },
                { key: "monthAverage4Str", colors: ['padding-12'] },
                { key: "monthAverage5Str", colors: ['padding-12'] },
                { key: "monthAverage6Str", colors: ['padding-12'] },
                { key: "exceededNumber", colors: ['padding-5'] },
                { key: "currentMax", colors: ['#F8EFD4'] },
                { key: "newMax", colors: ['#F8EFD4'] },
                { key: "comment", colors: ['#F8EFD4'] },
            ]
        }

        approval() {
            const vm = this;

            vm.$blockui("invisible");
            let apps: Array<ApplicationListDto> = $("#grid").mGrid("dataSource", true);
            let appSelecteds = _.filter(apps, (app: ApplicationListDto) => { return app.approvalChecked; });
            if (_.isEmpty(appSelecteds)) {
                vm.$dialog.error({ messageId: "Msg_1857" });
                vm.$blockui("clear");
                return;
            }

            if (!vm.isValid(appSelecteds)) {
                vm.$blockui("clear");
                return;
            }

            vm.$dialog.confirm({ messageId: 'Msg_1840' }).then(res => {
                if (res == "yes") {
                    let approvalSelected = _.filter(appSelecteds, (app: ApplicationListDto) => { return app.canApprove });
                    let approverCommands = _.map(approvalSelected, (app: ApplicationListDto) => {
                        return new ApproveDenialAppSpecialProvisionApproverCommand(app.applicantId, common.ApprovalStatusEnum.APPROVED, app.comment);
                    });

                    let confirmerSelected = _.filter(appSelecteds, (app: ApplicationListDto) => { return app.canConfirm });
                    let confirmerCommands = _.map(confirmerSelected, (app: ApplicationListDto) => {
                        return new ApproveDenialAppSpecialProvisionConfirmerCommand(app.applicantId, common.ConfirmationStatusEnum.CONFIRMED);
                    });

                    vm.$ajax(API.APPROVE_DENIAL, { approvers: approverCommands, confirmers: confirmerCommands }).done(() => {
                        vm.$dialog.info({ messageId: "Msg_220" }).then(function () {
                            vm.search();
                        });
                    }).fail((error: any) => {
                        vm.$errors(error);
                    }).always(() => vm.$blockui("clear"));
                } else {
                    vm.$blockui("clear");
                }
            });
        }

        bulkApproval() {
            const vm = this;

            vm.$blockui("invisible");
            let apps: Array<ApplicationListDto> = $("#grid").mGrid("dataSource", true);

            if (!vm.isValid(apps)) {
                vm.$blockui("clear");
                return;
            }

            vm.$dialog.confirm({ messageId: 'Msg_1841' }).then(res => {
                if (res == "yes") {
                    let approvalSelected = _.filter(apps, (app: ApplicationListDto) => { return app.canApprove });
                    let approverCommands = _.map(approvalSelected, (app: ApplicationListDto) => {
                        return new BulkApproveAppSpecialProvisionApproverCommand(app.applicantId, app.comment);
                    });

                    let confirmerSelected = _.filter(apps, (app: ApplicationListDto) => { return app.canConfirm });
                    let confirmerCommands = _.map(confirmerSelected, (app: ApplicationListDto) => {
                        return new BulkApproveAppSpecialProvisionConfirmerCommand(app.applicantId);
                    });


                    vm.$ajax(API.BULK_APPROVE, { approvers: approverCommands, confirmers: confirmerCommands }).done(() => {
                        vm.$dialog.info({ messageId: "Msg_220" }).then(function () {
                            vm.search();
                        });
                    }).fail((error: any) => {
                        vm.$errors(error);
                    }).always(() => vm.$blockui("clear"));
                } else {
                    vm.$blockui("clear");
                }
            });
        }

        denial() {
            const vm = this;

            vm.$blockui("invisible");
            let apps: Array<ApplicationListDto> = $("#grid").mGrid("dataSource", true);
            let appSelecteds = _.filter(apps, (app: ApplicationListDto) => { return app.denialChecked; });
            if (_.isEmpty(appSelecteds)) {
                vm.$dialog.error({ messageId: "Msg_1857" });
                vm.$blockui("clear");
                return;
            }

            vm.$dialog.confirm({ messageId: 'Msg_1842' }).then(res => {
                if (res == "yes") {
                    let approvalSelected = _.filter(appSelecteds, (app: ApplicationListDto) => { return app.canApprove });
                    let approverCommands = _.map(approvalSelected, (app: ApplicationListDto) => {
                        return new ApproveDenialAppSpecialProvisionApproverCommand(app.applicantId, common.ApprovalStatusEnum.DENY, app.comment);
                    });

                    let confirmerSelected = _.filter(appSelecteds, (app: ApplicationListDto) => { return app.canConfirm });
                    let confirmerCommands = _.map(confirmerSelected, (app: ApplicationListDto) => {
                        return new ApproveDenialAppSpecialProvisionConfirmerCommand(app.applicantId, common.ConfirmationStatusEnum.DENY);
                    });

                    vm.$ajax(API.APPROVE_DENIAL, { approvers: approverCommands, confirmers: confirmerCommands }).done(() => {
                        vm.$dialog.info({ messageId: "Msg_222" }).then(function () {
                            vm.search();
                        });
                    }).fail((error: any) => {
                        vm.$errors(error);
                    }).always(() => vm.$blockui("clear"));
                } else {
                    vm.$blockui("clear");
                }
            });
        }

        isValid(data: Array<ApplicationListDto>) {
            const vm = this;

            let errorItems: Array<any> = [];
            _.forEach(data, (item: ApplicationListDto) => {
                if (!item.canApprove) return;
                let checkReason: any = vm.commentValidation.validate(item.comment == null ? null : item.comment);
                if (!checkReason.isValid) {
                    errorItems.push({
                        message: checkReason.errorMessage,
                        messageId: checkReason.errorCode,
                        supplements: {}
                    })
                }
            })

            if (!_.isEmpty(errorItems)) {
                nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
                return false;
            }

            return true;
        }

        // ※13
        isEnableApproval(approvalStatus: common.ApprovalStatusEnum, confirmStatus: common.ConfirmationStatusEnum,
            canApprove: boolean, canConfirm: boolean) {
            if (canApprove && !canConfirm) {
                if (approvalStatus == common.ApprovalStatusEnum.APPROVED) {
                    return false;
                } else {
                    return true;
                }
            }
            if (!canApprove && canConfirm) {
                if (confirmStatus == common.ConfirmationStatusEnum.CONFIRMED) {
                    return false;
                } else {
                    return true;
                }
            }
            if (canApprove && canConfirm) {
                if (approvalStatus == common.ApprovalStatusEnum.APPROVED) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }

        // ※14
        isEnableDeny(approvalStatus: common.ApprovalStatusEnum, confirmStatus: common.ConfirmationStatusEnum,
            canApprove: boolean, canConfirm: boolean) {
            if (canApprove && !canConfirm) {
                if (approvalStatus == common.ApprovalStatusEnum.UNAPPROVED) {
                    return true;
                } else {
                    return false;
                }
            }
            if (!canApprove && canConfirm) {
                if (confirmStatus == common.ConfirmationStatusEnum.UNCONFIRMED) {
                    return true;
                } else {
                    return false;
                }
            }
            if (canApprove && canConfirm) {
                if (approvalStatus == common.ApprovalStatusEnum.UNAPPROVED) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    interface ApplicationListDto extends common.IApplicationListDto {
        approvalChecked: boolean;
        denialChecked: boolean;
        employee: string;
        appType: any;
        month: any;
        year: any;
        monthAverage2Str: any;
        monthAverage3Str: any;
        monthAverage4Str: any;
        monthAverage5Str: any;
        monthAverage6Str: any;
        exceededNumber: number;
        currentMax: any;
        newMax: any;
        inputDateStr: string;
        approverStatusStr: any;
        confirmStatusStr: any;
        canApprove: boolean;
        canConfirm: boolean;
    }

    class ApproveDenialAppSpecialProvisionApproverCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 承認状態：承認
         */
        approvalStatus: common.ApprovalStatusEnum;
        /**
         * 承認者のコメント：承認コメント
         */
        approvalComment: string;

        constructor(applicantId: string, approvalStatus: common.ApprovalStatusEnum, approvalComment: string) {
            this.applicantId = applicantId;
            this.approvalStatus = approvalStatus;
            this.approvalComment = approvalComment;
        }
    }

    class ApproveDenialAppSpecialProvisionConfirmerCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 確認状態
         */
        confirmStatus: number;

        constructor(applicantId: string, confirmStatus: number) {
            this.applicantId = applicantId;
            this.confirmStatus = confirmStatus;
        }
    }

    class BulkApproveAppSpecialProvisionApproverCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 承認者のコメント：承認コメント
         */
        approvalComment: string;

        constructor(applicantId: string, approvalComment: string) {
            this.applicantId = applicantId;
            this.approvalComment = approvalComment;
        }
    }

    class BulkApproveAppSpecialProvisionConfirmerCommand {
        /**
        * 申請ID
        */
        applicantId: string;

        constructor(applicantId: string) {
            this.applicantId = applicantId;
        }
    }
}
