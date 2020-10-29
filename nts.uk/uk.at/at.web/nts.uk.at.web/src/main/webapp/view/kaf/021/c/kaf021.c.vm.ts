/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.c {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
    import validation = nts.uk.ui.validation;
    import disableCell = nts.uk.ui.mgrid.color.Disable;

    const API = {
        INIT_DISPLAY: 'screen/at/kaf021/init-display',
        SEARCH: 'screen/at/kaf021/search',
        APPLY: 'at/record/monthly/agreement/monthly-result/special-provision/apply',
        DELETE: 'at/record/monthly/agreement/monthly-result/special-provision/delete'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        unapproveChecked: KnockoutObservable<boolean> = ko.observable(true);
        approveChecked: KnockoutObservable<boolean> = ko.observable(true);
        denialChecked: KnockoutObservable<boolean> = ko.observable(true);

        unapproveCount: KnockoutObservable<number> = ko.observable(0);
        unapproveCountStr: KnockoutObservable<string> = ko.observable(null);
        approveCount: KnockoutObservable<number> = ko.observable(0);
        approveCountStr: KnockoutObservable<string> = ko.observable(null);
        denialCount: KnockoutObservable<number> = ko.observable(0);
        denialCountStr: KnockoutObservable<string> = ko.observable(null);

        datePeriod: KnockoutObservable<any> = ko.observable({});
        datas: Array<any> = [];
        hasData: KnockoutObservable<boolean> = ko.observable(false);

        yearTimeValidation = new validation.TimeValidator(this.$i18n("KAF021_18"), "AgreementOneYearTime", { required: true, valueType: "Clock", inputFormat: "hh:mm", outputFormat: "time", mode: "time" });
        reasonsValidation = new validation.StringValidator(this.$i18n("KAF021_19"), "ReasonsForAgreement", { required: true });
        constructor() {
            super();
            const vm = this;
        }

        created(params: any) {
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

            vm.hasData(!_.isEmpty(vm.datas));
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
            param.status.push(common.ApprovalStatusEnum.APPROVED);
            param.status.push(common.ApprovalStatusEnum.DENY);
            vm.$ajax(API.INIT_DISPLAY, param).done((data: common.SpecialProvisionOfAgreementAppListDto) => {
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
            vm.$blockui("invisible");
            vm.datas = [];
            let param: any = {
                startDate: moment(vm.datePeriod().startDate, "YYYY/MM/DD").toISOString(),
                endDate: moment(vm.datePeriod().endDate, "YYYY/MM/DD").toISOString(),
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
                result.checked = false;
                result.employee = result.employeeCode + "　" + result.employeeName;
                if (result.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_MONTH) {
                    result.appType = textFormat(vm.$i18n("KAF021_64"), result.applicationTime?.oneMonthTime?.yearMonth);
                    result.month = parseTime(result.screenDisplayInfo?.overtime?.overtimeHoursOfMonth, true).format();
                    if (result.screenDisplayInfo?.overtimeIncludingHoliday?.overtimeHoursTargetMonth != null) {
                        result.month += "<br>(" + parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.overtimeHoursTargetMonth, true).format() + ")";
                    }
                    result.currentMax = parseTime(result.screenDisplayInfo?.upperContents?.oneMonthLimit.error, true).format();
                    result.newMax = parseTime(result.applicationTime?.oneMonthTime?.errorTime?.error, true).format();
                } else if (result.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_YEAR) {
                    result.appType = textFormat(vm.$i18n("KAF021_67"), result.applicationTime?.oneYearTime?.year);
                    result.month = "-"
                    result.currentMax = parseTime(result.screenDisplayInfo?.upperContents?.oneYearLimit.error, true).format();
                    result.newMax = parseTime(result.applicationTime?.oneYearTime?.errorTime?.error, true).format();
                }

                result.year = parseTime(result.screenDisplayInfo?.overtime?.overtimeHoursOfYear, true).format();
                result.monthAverage2Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage2Str, true).format();
                result.monthAverage3Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage3Str, true).format();
                result.monthAverage4Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage4Str, true).format();
                result.monthAverage5Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage5Str, true).format();
                result.monthAverage6Str = parseTime(result.screenDisplayInfo?.overtimeIncludingHoliday?.monthAverage6Str, true).format();
                result.exceededNumber = result.screenDisplayInfo?.exceededMonth;
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
                        name: 'CheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: any) {
                            //vm.checkDelete(rowId, value);
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
                                columnKey: "checked",
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
            // C2_1
            columns.push({ headerText: "", key: 'checked', dataType: 'boolean', width: '35px', checkbox: true, ntsControl: "CheckBox" });
            // C2_2
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'workplaceName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // C2_3
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employee', dataType: 'string', width: '140px', ntsControl: "Label" });
            // C2_4
            columns.push({ headerText: vm.$i18n("KAF021_2"), key: 'appType', dataType: 'string', width: '90px', ntsControl: "Label" });
            // C2_5
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // C2_6
                    { headerText: vm.$i18n("KAF021_16"), key: "month", dataType: 'string', width: '75px', ntsControl: "Label" },
                    // C2_7
                    { headerText: vm.$i18n("KAF021_26"), key: 'year', dataType: 'string', width: '75px', ntsControl: "Label" }
                ]
            });
            // C2_8
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // C2_9
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // C2_10
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // C2_11
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // C2_12
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // C2_13
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '50px', ntsControl: "Label" });
            // C2_14
            columns.push({
                headerText: vm.$i18n("KAF021_17"),
                group: [
                    // C2_15
                    { headerText: vm.$i18n("KAF021_27"), key: "currentMax", dataType: 'string', width: '75px', ntsControl: "Label" },
                    // C2_16
                    {
                        headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px',
                        constraint: {
                            primitiveValue: 'AgreementOneYearTime',
                            required: true
                        }
                    }
                ]
            });

            // C2_17
            columns.push({
                headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '300px',
                constraint: {
                    primitiveValue: 'ReasonsForAgreement',
                    required: true
                }
            });
            // C2_18
            columns.push({ headerText: vm.$i18n("KAF021_41"), key: 'comment', dataType: 'string', width: '180px', ntsControl: "Label" });
            // C2_19
            columns.push({ headerText: vm.$i18n("KAF021_42"), key: 'applicant', dataType: 'string', width: '140px', ntsControl: "Label" });
            // C2_20
            columns.push({ headerText: vm.$i18n("KAF021_43"), key: 'inputDate', dataType: 'string', width: '100px', ntsControl: "Label" });
            // C2_21
            columns.push({ headerText: vm.$i18n("KAF021_44"), key: 'approver', dataType: 'string', width: '140px', ntsControl: "Label" });
            // C2_22
            columns.push({ headerText: vm.$i18n("KAF021_34"), key: 'approverStatusStr', dataType: 'string', width: '80px', ntsControl: "Label" });
            // C2_23
            columns.push({ headerText: vm.$i18n("KAF021_45"), key: 'confirmer', dataType: 'string', width: '140px', ntsControl: "Label" });
            // C2_24
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'confirmStatusStr', dataType: 'string', width: '80px', ntsControl: "Label" });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];

            _.forEach(vm.datas, (data: any) => {
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
                cellStates.push(new common.CellState(data.applicantId, 'newMax', ["center-align", "cell-edit"]));
                if (data.approvalStatus == common.ApprovalStatusEnum.APPROVED){
                    cellStates.push(new common.CellState(data.applicantId, 'checked', [disableCell]));
                    cellStates.push(new common.CellState(data.applicantId, 'reason', ["cell-edit", disableCell]));
                } else {
                    cellStates.push(new common.CellState(data.applicantId, 'reason', ["cell-edit"]));
                }
                cellStates.push(new common.CellState(data.applicantId, 'inputDate', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'approverStatusStr', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'confirmStatusStr', ["center-align"]));

            })
            return cellStates;
        }

        register() {
            const vm = this;
            vm.$blockui("invisible");
            let appApplys = vm.getAppSelecteds();
            if (!vm.isValid(appApplys)) {
                vm.$blockui("clear");
                return;
            };
            if (_.isEmpty(appApplys)) {
                vm.$dialog.error({ messageId: "Msg_1857" });
                vm.$blockui("clear");
                return;
            }

            vm.$dialog.confirm({ messageId: 'Msg_1840' }).then(res => {
                if (res == "yes") {
                    let commands: Array<ApplyAppSpecialProvisionCommand> = [];
                    _.each(appApplys, (app: ApplicationListDto) => {
                        if (app.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_MONTH) {
                            // month
                            commands.push(new ApplyAppSpecialProvisionCommand(common.TypeAgreementApplicationEnum.ONE_MONTH, 
                                app.applicantId, moment.duration(app.newMax).asMinutes(), app.reason))
                        } else if (app.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_YEAR) {
                            // year
                            commands.push(new ApplyAppSpecialProvisionCommand(common.TypeAgreementApplicationEnum.ONE_YEAR, 
                                app.applicantId, moment.duration(app.newMax).asMinutes(), app.reason))
                        }
                    })                   
    
                    // call api
                    vm.$ajax(API.APPLY, commands).done((res: any) => {
                        let errorItems: Array<any> = common.generateErrors(res);
                        if (!_.isEmpty(errorItems)) {
                            nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
                        } else {
                            vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                                vm.search();
                            });
                        }
                    }).always(() => vm.$blockui("clear"));
                } else {
                    vm.$blockui("clear");
                }
            });
        }

        del() {
            const vm = this;
            vm.$dialog.confirm({ messageId: 'Msg_1839' }).then(res => {
                if (res == "yes") {
                    vm.$blockui("invisible");
                    let appDeletes = vm.getAppSelecteds();
                    if (_.isEmpty(appDeletes)) {
                        vm.$dialog.error({ messageId: "Msg_1857" });
                        vm.$blockui("clear");
                        return;
                    }
                    let appDeleteIds: Array<any> = _.map(appDeletes, (app: common.ApplicationListDto) => { return { applicantId: app.applicantId }; });
                    vm.$ajax(API.DELETE, appDeleteIds).done(() => {
                        vm.$dialog.info({ messageId: "Msg_16" }).then(function () {
                            vm.$blockui("clear");
                            vm.search();
                        });
                    }).always(() => vm.$blockui("clear"));
                }
            });
        }

        getAppSelecteds(): Array<ApplicationListDto> {
            const vm = this;
            let apps: Array<ApplicationListDto> = $("#grid").mGrid("dataSource", true);
            let appSelecteds = _.filter(apps, (app: ApplicationListDto) => { return app.checked; });
            return appSelecteds;
        }

        isValid(data: Array<ApplicationListDto>) {
            const vm = this;

            let errorItems: Array<any> = [];
            _.forEach(data, (item: ApplicationListDto) => {
                let checkTime: any = vm.yearTimeValidation.validate(item.newMax == null ? null : item.newMax);
                if (!checkTime.isValid) {
                    errorItems.push({
                        message: checkTime.errorMessage,
                        messageId: checkTime.errorCode,
                        supplements: {}
                    })
                }


                let checkReason: any = vm.reasonsValidation.validate(item.reason == null ? null : item.reason);
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
    }

    interface ApplicationListDto extends common.IApplicationListDto {
        checked: boolean;
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
        approverStatusStr: any;
        confirmStatusStr: any;
    }

    class ApplyAppSpecialProvisionCommand {
        /**
         * ３６協定申請種類
         */
        typeAgreement: number;
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * １ヵ月時間OR年間時間
         */
        time: number;
        /**
         * 申請理由
         */
        reason: string;

        constructor(typeAgreement: number, applicantId: string, time: number, reason: string) {
            this.typeAgreement = typeAgreement;
            this.applicantId = applicantId;
            this.time = time;
            this.reason = reason;
        }
    }
}
