/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.c {
    import parseTime = nts.uk.time.parseTime;

    @bean()
    class ViewModel extends ko.ViewModel {
        unapproveChecked: KnockoutObservable<boolean> = ko.observable(false);
        approveChecked: KnockoutObservable<boolean> = ko.observable(false);
        denialChecked: KnockoutObservable<boolean> = ko.observable(false);
        datePeriod: KnockoutObservable<any> = ko.observable({});

        datas: Array<any> = [];
        constructor() {
            super();
            const vm = this;
            vm.getMockDataApprove();
        }

        created(params: any) {
            const vm = this;

            vm.loadMGrid();
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
                        name: 'CheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId, columnKey, value, rowData) {
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
                                columnKey: "checked",
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
                    // {
                    //     name: 'CellStyles',
                    //     states: vm.getCellStyles()
                    // }
                ]
            }).create();
        }

        getColumns(): Array<any> {
            const vm = this;

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // C2_1
            columns.push({ headerText: "", key: 'checked', dataType: 'boolean', width: '35px', checkbox: true, ntsControl: "CheckBox" });
            // C2_2
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // C2_3
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '140px', ntsControl: "Label" });
            // C2_4
            columns.push({ headerText: vm.$i18n("KAF021_2"), key: 'appType', dataType: 'string', width: '140px', ntsControl: "Label" });
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
                    { headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px', ntsControl: "Label" }
                ]
            });

            // C2_17
            columns.push({ headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '300px' });
            // C2_18
            columns.push({ headerText: vm.$i18n("KAF021_41"), key: 'comment', dataType: 'string', width: '150px' });
            // C2_19
            columns.push({ headerText: vm.$i18n("KAF021_42"), key: 'app', dataType: 'string', width: '100px' });
            // C2_20
            columns.push({ headerText: vm.$i18n("KAF021_43"), key: 'appDate', dataType: 'string', width: '100px' });
            // C2_21
            columns.push({ headerText: vm.$i18n("KAF021_44"), key: 'approver', dataType: 'string', width: '100px' });
            // C2_22
            columns.push({ headerText: vm.$i18n("KAF021_34"), key: 'approverStatus', dataType: 'string', width: '100px' });
            // C2_23
            columns.push({ headerText: vm.$i18n("KAF021_45"), key: 'representative', dataType: 'string', width: '100px' });
            // C2_24
            columns.push({ headerText: vm.$i18n("KAF021_46"), key: 'confirmStatus', dataType: 'string', width: '100px' });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];

            _.forEach(vm.datas, (data: any) => {
                cellStates.push(new common.CellState(data.employeeId, 'currentTime', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'yearStr', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'exceededNumber', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'newMax', ["center-align", "cell-edit"]));
                cellStates.push(new common.CellState(data.employeeId, 'reason', ["cell-edit"]));
            })
            return cellStates;
        }

        register() {

        }

        del() {

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
                data.appType = "appType " + i;
                data.month = "month " + i;
                data.year = "year " + i;
                data.monthAverage2Str = parseTime(i, true).format();
                data.monthAverage3Str = parseTime(i + 2, true).format();
                data.monthAverage4Str = parseTime(i + 4, true).format();
                data.monthAverage5Str = parseTime(i + 5, true).format();
                data.monthAverage6Str = parseTime(i + 10, true).format();
                data.exceededNumber = i;
                datas.push(data);
            }

            vm.datas = datas;
        }
    }

}
