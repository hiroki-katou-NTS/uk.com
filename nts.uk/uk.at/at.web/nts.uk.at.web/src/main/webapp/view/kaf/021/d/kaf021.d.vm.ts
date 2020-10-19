/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.d {
    import parseTime = nts.uk.time.parseTime;

    const API = {
        INIT: 'screen/at/kaf021/init-display',
        APPROVE_DENIAL_APPROVER: 'at/record/monthly/agreement/monthly-result/special-provision/approve-denial-approver',
        APPROVE_DENIAL_CONFIRMER: 'at/record/monthly/agreement/monthly-result/special-provision/approve-denial-confirmer',
        BULK_APPROVE_APPROVER: 'at/record/monthly/agreement/monthly-result/special-provision/bulk-approve-approver',
        BULK_APPROVE_CONFIRMER: 'at/record/monthly/agreement/monthly-result/special-provision/bulk-approve-confirmer'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        unapproveChecked: KnockoutObservable<boolean> = ko.observable(false);
        approveChecked: KnockoutObservable<boolean> = ko.observable(false);
        denialChecked: KnockoutObservable<boolean> = ko.observable(false);

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
            vm.getMockDataApprove();
        }

        created(params: any) {
            const vm = this;

            vm.unapproveCountStr(vm.$i18n("KAF021_66", [vm.unapproveCount().toString()]));
            vm.approveCountStr(vm.$i18n("KAF021_66", [vm.approveCount().toString()]));
            vm.denialCountStr(vm.$i18n("KAF021_66", [vm.denialCount().toString()]));
            vm.loadMGrid();

            _.extend(window, { vm });
        }

        mounted() {
            const vm = this;


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
                primaryKey: 'employeeId',
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
                                columnKey: "employeeId",
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
                                columnKey: "wkpName",
                                isFixed: true
                            },
                            {
                                columnKey: "employeeName",
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
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // D2_1
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'approvalChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "ApprovalCheckBox" });
            // D2_2
            columns.push({ headerText: vm.$i18n("KAF021_38"), key: 'denialChecked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "DenialCheckBox" });
            // D2_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // D2_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '140px', ntsControl: "Label" });
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
            columns.push({ headerText: vm.$i18n("KAF021_41"), key: 'comment', dataType: 'string', width: '180px' });
            // D2_20
            columns.push({ headerText: vm.$i18n("KAF021_42"), key: 'applicant', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_21
            columns.push({ headerText: vm.$i18n("KAF021_43"), key: 'appDate', dataType: 'string', width: '100px', ntsControl: "Label" });
            // D2_22
            columns.push({ headerText: vm.$i18n("KAF021_44"), key: 'approver', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_23
            columns.push({ headerText: vm.$i18n("KAF021_34"), key: 'approverStatus', dataType: 'string', width: '80px', ntsControl: "Label" });
            // D2_24
            columns.push({ headerText: vm.$i18n("KAF021_45"), key: 'representative', dataType: 'string', width: '140px', ntsControl: "Label" });
            // D2_25
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'confirmStatus', dataType: 'string', width: '80px', ntsControl: "Label" });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];

            _.forEach(vm.datas, (data: any) => {
                cellStates.push(new common.CellState(data.employeeId, 'appType', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'month', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'year', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'exceededNumber', ["center-align"]));                
                cellStates.push(new common.CellState(data.employeeId, 'currentMax', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'newMax', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'comment', ["cell-edit"]));
                cellStates.push(new common.CellState(data.employeeId, 'appDate', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'approverStatus', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'confirmStatus', ["center-align"]));
            })
            return cellStates;
        }

        approval() {

        }

        bulkApproval() {

        }

        denial() {

        }

        getMockDataApprove() {
            const vm = this;
            let datas: Array<any> = []
            for (let i = 0; i < 100; i++) {
                let data: any = {};
                data.employeeId = i.toString();
                data.checked = i % 2 == 0 ? true : false;
                data.wkpName = "wkpName " + i;
                data.employeeName = "employeeName " + i;
                if (i % 2 == 0){
                    data.appType = i + "月度";
                }
                else{
                    data.appType = 2020 + "年度";
                }
                data.month = parseTime(i + 20000, true).format();
                data.year = parseTime(i + 10000, true).format();
                data.monthAverage2Str = parseTime(i, true).format();
                data.monthAverage3Str = parseTime(i + 20000, true).format();
                data.monthAverage4Str = parseTime(i + 1000, true).format();
                data.monthAverage5Str = parseTime(i + 5000, true).format();
                data.monthAverage6Str = parseTime(i + 1000, true).format();
                data.exceededNumber = i;
                data.currentMax = parseTime(i + 10, true).format();
                data.newMax = parseTime(i + 12, true).format();
                data.reason = "reason " + i;
                data.comment = "comment " + i;
                data.applicant = "申請者 " + i;
                data.appDate = "2020/10/20 ";
                data.approver = "承認者 " + i;
                data.approverStatus = "承認済み";
                data.representative = "従業員代表 " + i;
                data.confirmStatus = "確認済み";
                
                datas.push(data);
            }

            vm.datas = datas;
        }
    }

}
