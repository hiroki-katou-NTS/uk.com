/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.d {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;

    const API = {
        INIT_DISPLAY: 'screen/at/kaf021/init-display',
        SEARCH: 'screen/at/kaf021/search',
        APPROVE_DENIAL_APPROVER: 'at/record/monthly/agreement/monthly-result/special-provision/approve-denial-approver',
        APPROVE_DENIAL_CONFIRMER: 'at/record/monthly/agreement/monthly-result/special-provision/approve-denial-confirmer',
        BULK_APPROVE_APPROVER: 'at/record/monthly/agreement/monthly-result/special-provision/bulk-approve-approver',
        BULK_APPROVE_CONFIRMER: 'at/record/monthly/agreement/monthly-result/special-provision/bulk-approve-confirmer'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        mode: 1;

        unapproveChecked: KnockoutObservable<boolean> = ko.observable(true);
        approveChecked: KnockoutObservable<boolean> = ko.observable(true);
        denialChecked: KnockoutObservable<boolean> = ko.observable(true);

        unapproveCount: KnockoutObservable<number> = ko.observable(0);
        unapproveCountStr: KnockoutObservable<string> = ko.observable(null);
        approveCount: KnockoutObservable<number> = ko.observable(1);
        approveCountStr: KnockoutObservable<string> = ko.observable(null);
        denialCount: KnockoutObservable<number> = ko.observable(2);
        denialCountStr: KnockoutObservable<string> = ko.observable(null);

        datePeriod: KnockoutObservable<any> = ko.observable({});

        datas: Array<any> = [];
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

        setStatusCount() {
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
            param.status.push(common.ApprovalStatusEnum.APPROVED);
            param.status.push(common.ApprovalStatusEnum.DENY);
            vm.$ajax(API.INIT_DISPLAY, param).done((data: common.SpecialProvisionOfAgreementAppListDto) => {
                vm.datePeriod({
                    startDate: moment(data.startDate).format("YYYY/MM/DD"),
                    endDate: moment(data.endDate).format("YYYY/MM/DD")
                });
                vm.datas = vm.convertData(data.applications);
                vm.setStatusCount();
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
                vm.setStatusCount();
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
                result.employee = result.employeeCode + "　" + result.employeeName;
                if (result.applicationTime.typeAgreement == common.TypeAgreementApplicationEnum.ONE_MONTH) {
                    result.appType = textFormat(vm.$i18n("KAF021_64"), result.applicationTime?.oneMonthTime?.yearMonth);
                    result.month = parseTime(result.screenDisplayInfo?.overtime?.overtimeHoursOfMonth, true).format();
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
                        name: 'ApprovalCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: any) {
                            //vm.checkDelete(rowId, value);
                        }
                    },
                    {
                        name: 'DenialCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
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
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'approvalChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "ApprovalCheckBox" });
            // D2_2
            columns.push({ headerText: vm.$i18n("KAF021_38"), key: 'denialChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "DenialCheckBox" });
            // D2_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'workplaceName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // D2_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employee', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_5
            columns.push({ headerText: vm.$i18n("KAF021_2"), key: 'appType', dataType: 'string', width: '90px', ntsControl: "Label" });
            // D2_6
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // D2_7
                    { headerText: "month", key: "month", dataType: 'string', width: '75px', ntsControl: "Label" },
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
            columns.push({ headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '300px', ntsControl: "Label" });
            // D2_19
            columns.push({
                headerText: vm.$i18n("KAF021_41"), key: 'comment', dataType: 'string', width: '180px',
                constraint: {
                    primitiveValue: 'AgreementApprovalComments',
                    required: false
                }
            });
            // D2_20
            columns.push({ headerText: vm.$i18n("KAF021_42"), key: 'applicant', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_21
            columns.push({ headerText: vm.$i18n("KAF021_43"), key: 'inputDate', dataType: 'string', width: '100px', ntsControl: "Label" });
            // D2_22
            columns.push({ headerText: vm.$i18n("KAF021_44"), key: 'approver', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_23
            columns.push({ headerText: vm.$i18n("KAF021_34"), key: 'approverStatusStr', dataType: 'string', width: '80px', ntsControl: "Label" });
            // D2_24
            columns.push({ headerText: vm.$i18n("KAF021_45"), key: 'confirmer', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_25
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
                cellStates.push(new common.CellState(data.applicantId, 'newMax', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'comment', ["cell-edit"]));
                cellStates.push(new common.CellState(data.applicantId, 'inputDate', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'approverStatusStr', ["center-align"]));
                cellStates.push(new common.CellState(data.applicantId, 'confirmStatusStr', ["center-align"]));
            })
            return cellStates;
        }

        approval() {

        }

        bulkApproval() {

        }

        denial() {

        }

    }

    enum ScreenMode{
        APPROVER = 1,
        CONFIRMER = 2
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
        approverStatusStr: any;
        confirmStatusStr: any;
    }

    class ApproveDenialAppSpecialProvisionApproverCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 承認者：社員ID
         */
        approverId: string;
        /**
         * 承認状態：承認
         */
        approvalStatus: number;
        /**
         * 承認者のコメント：承認コメント
         */
        approvalComment: string;
    }

    class ApproveDenialAppSpecialProvisionConfirmerCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 確認者 (社員ID)
         */
        confirmerId: string;
        /**
         * 確認状態
         */
        confirmStatus: number;
    }

    class BulkApproveAppSpecialProvisionApproverCommand {
        /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 承認者：社員ID
         */
        approverId: string;
        /**
         * 承認者のコメント：承認コメント
         */
        approvalComment: string;
    }

    class BulkApproveAppSpecialProvisionConfirmerCommand {
         /**
         * 申請ID
         */
        applicantId: string;
        /**
         * 確認者 (社員ID)
         */
        confirmerId: string;
    }
}
