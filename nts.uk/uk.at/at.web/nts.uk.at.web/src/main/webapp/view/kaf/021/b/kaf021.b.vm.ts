/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.b {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
    import common = nts.uk.at.kaf021.common;

    @bean()
    class ViewModel extends ko.ViewModel {
        datas: Array<EmployeeAgreementTimeNew> = [];
        appType: common.AppTypeEnum = null;
        constructor() {
            super();
            const vm = this;
        }

        created(params: any) {
            const vm = this;
            if (params != null) {
                vm.datas = params.datas;
                vm.appType = params.appType;

                let cacheJson = localStorage.getItem('kaf021b_cache');
                let cache: Array<Kaf021B_Cache> = JSON.parse(cacheJson);

                // UI処理3参照
                // 登録画面から選択画面に戻り再度同じ従業員を選択した場合、同じ入力値を自動入力する
                if (cache) {
                    _.each(vm.datas, (item: EmployeeAgreementTimeNew) => {
                        let empCache = _.find(cache, (c: Kaf021B_Cache) => {
                            return c.employeeId == item.employeeId;
                        });

                        if (empCache){
                            item.newMax = empCache.newMax;
                            item.reason = empCache.reason;
                        }
                    })
                }
            }
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
                        name: 'CellStyles',
                        states: vm.getCellStyles()
                    }
                ]
            }).create();
        }

        getColumns(): Array<any> {
            const vm = this;
            let month = vm.getMonth()
            let currentMonth = vm.getMonthKey(month);

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // B3_1
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // B3_2
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '140px', ntsControl: "Label" });
            // B3_3
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // B3_4
                    { headerText: vm.$i18n("KAF021_64", [month.toString()]), key: currentMonth, dataType: 'string', width: '75px', ntsControl: "Label" },
                    // B3_5
                    { headerText: vm.$i18n("KAF021_26"), key: 'yearStr', dataType: 'string', width: '75px', ntsControl: "Label" }
                ]
            });
            // B3_6
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_7
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_8
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_9
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_10
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_11
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '50px', ntsControl: "Label" });

            // B3_12
            columns.push({
                headerText: vm.getCurrentMaxHeader(),
                group: [
                    // B3_13
                    { headerText: vm.$i18n("KAF021_27"), key: vm.getCurrentMaxKey(), dataType: 'string', width: '75px', ntsControl: "Label" },
                    // B3_14
                    { headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px' }
                ]
            });

            // B3_15
            columns.push({ headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '320px' });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];
            let currentMonth = vm.getMonthKey(vm.getMonth());
            _.forEach(vm.datas, (data: EmployeeAgreementTimeNew) => {
                cellStates.push(new common.CellState(data.employeeId, 'currentTime', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'yearStr', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, currentMonth, ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'exceededNumber', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, vm.getCurrentMaxKey(), ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'newMax', ["center-align", "cell-edit"]));
                cellStates.push(new common.CellState(data.employeeId, 'reason', ["cell-edit"]));
            })
            return cellStates;
        }

        register() {
            const vm = this;
            localStorage.setItem('kaf021b_cache', null);
            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
        }

        preScreen() {
            const vm = this;
            let data: Array<EmployeeAgreementTimeNew> = $("#grid").mGrid("dataSource", true);
            let cache: Array<Kaf021B_Cache> = _.map(data, (item: EmployeeAgreementTimeNew) => {
                return new Kaf021B_Cache(item.employeeId, item.newMax, item.reason);
            })
            localStorage.setItem('kaf021b_cache', JSON.stringify(cache));
            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
        }

        getMonth() {
            const vm = this;
            let date = vm.$date.today();
            let month = 0;

            if (vm.appType == common.AppTypeEnum.NEXT_MONTH) {
                month = date.getMonth() + 2;
            } else {
                month = date.getMonth() + 1;
            }
            return month;
        }

        getMonthKey(month: number) {
            return 'month' + month + 'Str';
        }

        getCurrentMaxKey() {
            const vm = this;
            if (vm.appType == common.AppTypeEnum.YEARLY) {
                return "yearMaxTimeStr"
            } else {
                return vm.getMonthMaxKey(vm.getMonth());
            }
        }

        getMonthMaxKey(month: number) {
            return 'month' + month + 'MaxTimeStr';
        }

        getCurrentMaxHeader(){
            const vm = this;
            // TODO
            if (vm.appType == common.AppTypeEnum.YEARLY) {
                return "年度";
            } else {
                return "月度";
            }
        }
    }

    class EmployeeAgreementTimeNew extends common.EmployeeAgreementTime {
        newMax: number;
        reason: string;
    }

    class Kaf021B_Cache{
        employeeId: string;
        newMax: number;
        reason: string;

        constructor(employeeId: string, newMax: number, reason: string){
            this.employeeId = employeeId;
            this.newMax = newMax;
            this.reason = reason;
        }
    }
}
