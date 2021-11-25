module nts.uk.at.kmr003.b {
    @bean()
    export class KMR003BViewModel extends ko.ViewModel {
        date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
        receptionNames: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedReception: KnockoutObservable<any> = ko.observable();
        receptionHours1: KnockoutObservable<ReservationRecTime> = ko.observable(null);
        receptionHours2: KnockoutObservable<ReservationRecTime> = ko.observable(null);
        closingTime: KnockoutObservable<string> = ko.observable();
        orderMngAtr: KnockoutObservable<boolean> = ko.observable(false);
        extractionConditions: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCondition: KnockoutObservable<any> = ko.observable();
        empIds: any[] = [];
        gridOptions: any = { dataSource: [], columns: [], features: [], ntsControls: [] };
        deleteAll: KnockoutObservable<boolean> = ko.observable(false);
    
        created(param: any) {
            const vm = this;
            
            if (param) {
                vm.receptionNames(param.receptionNames);
                vm.receptionHours1(param.receptionHours1);
                vm.receptionHours2(param.receptionHours2);
                vm.orderMngAtr(param.orderMngAtr);
                vm.empIds = param.empIds;
            }

            if (vm.orderMngAtr()) {
                let conditions: any[] = [
                    { 'id': ReservationCorrect.ALL_RESERVE, 'name': '予約した全部' },
                    { 'id': ReservationCorrect.MORE_THAN_2_ITEMS, 'name': '１商品２件以上' },
                    { 'id': ReservationCorrect.ORDER, 'name': '発注済み' },
                    { 'id': ReservationCorrect.NOT_ORDERING, 'name': '未発注' }
                ];
                vm.extractionConditions(conditions);
            } else {
                let conditions: any[] = [
                    { 'id': ReservationCorrectNotOrder.ALL_RESERVE, 'name': '予約した全部' },
                    { 'id': ReservationCorrectNotOrder.MORE_THAN_2_ITEMS, 'name': '１商品２件以上' }
                ];
                vm.extractionConditions(conditions);
            }
            vm.selectedCondition(1);

            vm.selectedReception.subscribe((value) => {
                if (value == 1 && vm.receptionHours1()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().endTime) );
                }
                if (value == 2 && vm.receptionHours2()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().endTime) );
                }
            });

            if (param) {
                vm.selectedReception(param.selectedReception);
            }

            let paramStart = {
                reservationDate: moment().format('YYYY/MM/DD'), 
                correctionDate: moment(vm.date()).format('YYYY/MM/DD'), 
                frameNo: vm.selectedReception(), 
                extractCondition: vm.selectedCondition(), 
                employeeIds: vm.empIds
            }

            vm.$blockui('show');
            vm.$ajax(API.startCorrect, paramStart).done((res) => {
                if (res) {
                    console.log(res);
                    vm.convertToGridData(res);
                    vm.bindGrid();
                }
            }).fail((err) => {
                if (err) {
                    vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                }
            }).always(() => vm.$blockui('hide'));
        }
        
        mounted() {
            const vm = this;
            
        }

        convertToGridData(param: any) {
            const vm = this;

            let orderheader = "<div>" + vm.$i18n('KMR003_49') +"</div><div data-bind='ntsCheckBox: { checked: deleteAll }'>" + "</div>";

            // bind columns
            vm.gridOptions.columns = [
                { headerText: '' , itemId: 'deleteFlg', key: 'deleteFlg', dataType: 'boolean', width: '50px', checkbox: true, ntsControl: 'deleteFlg' }, 
                { headerText: '' , itemId: 'employeeId', key: 'employeeId', dataType: 'string', width: '150px', hidden: true }, 
                { headerText: vm.$i18n('KMR003_46') , itemId: 'employeeCode', key: 'employeeCode', dataType: 'string', width: '150px' }, 
                { headerText: vm.$i18n('KMR003_47') , itemId: 'employeeName', key: 'employeeName', dataType: 'string', width: '300px' }, 
                { headerText: vm.$i18n('KMR003_48') , itemId: 'time', key: 'time', dataType: 'string', width: '300px' }, 
                { headerText: orderheader , itemId: 'ordered', key: 'ordered', dataType: 'boolean', width: '70px', ntsControl: 'ordered' }
            ];
        
            let headerStyle = { name: 'HeaderStyles', columns: [
                { key: 'deleteFlg', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'employeeCode', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'employeeName', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'time', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'ordered', colors: ['align-center', 'header_backgroundcolor'] }, 
            ] };
            let cellStates: any[] = [];

            for (let i = 0; i < param.bento.length; i++) {
                let columnHeader = "<div>" + param.bento[i].name + "</div><div>" + '(' + param.bento[i].unit + ')' + "</div>";
                vm.gridOptions.columns.push({ headerText: columnHeader , itemId: 'bento' + i, key: 'bento' + i, dataType: 'string', width: '70px' })
                headerStyle.columns.push({ key: 'bento' + i, colors: ['align-center', 'header_backgroundcolor'] });
                cellStates.push({ rowId: 'bento' + i, columnKey: 'employeeCode', state: [nts.uk.ui.mgrid.color.Lock] });
                cellStates.push({ rowId: 'bento' + i, columnKey: 'employeeName', state: [nts.uk.ui.mgrid.color.Lock] });
                cellStates.push({ rowId: 'bento' + i, columnKey: 'time', state: [nts.uk.ui.mgrid.color.Lock] });
            }

            // bind features
            let columnFixing = { name: 'ColumnFixing', columnSettings: [
                { columnKey: 'deleteFlg', isFixed: true }, 
                { columnKey: 'employeeCode', isFixed: true }, 
                { columnKey: 'employeeName', isFixed: true }, 
                { columnKey: 'time', isFixed: true }, 
                { columnKey: 'ordered', isFixed: true }
            ]};
            vm.gridOptions.features.push(columnFixing);
            vm.gridOptions.features.push(headerStyle);
            vm.gridOptions.features.push({ name: 'CellStyles', states: cellStates });

            // bind ntsControls
            let ntsControls = [
                { name: 'deleteFlg', options: {value: false}, optionsValue: 'value', controlType: 'CheckBox' },
                { name: 'ordered', options: {value: false }, optionsValue: 'value', optionsText: vm.$i18n('KMR003_49'), controlType: 'CheckBox' },
                { name: 'notEditable', editable: false }
            ]
            vm.gridOptions.ntsControls = ntsControls;

            // bind dataSource
            for (let empId in param.bentoReservation) {
                let emp = _.filter(param.listEmpInfo, (e: any) => e.employeeId === empId)[0];

                // item B42_2
                let empCode = emp.employeeCode;
                // item B42_3
                let empBusinessName = emp.businessName;

                let bentoReservation = param.bentoReservation[empId].bentoReservation;
                let details = bentoReservation.listBentoReservationDetail;
                let dateMax = moment.max(_.map(details, (detail: any) => moment(detail.dateTime))).format("YYYY/MM/DD HH:mm");
                let ordered = bentoReservation.ordered;

                let item = { 
                    employeeId: empId, 
                    deleteFlg: false, 
                    employeeCode: empCode, 
                    employeeName: empBusinessName, 
                    time: dateMax, 
                    ordered: ordered
                 }

                 vm.gridOptions.dataSource.push(item);
            }
        }

        bindGrid() {
            const vm = this;

            if ($("#grid").data("mGrid")) $("#grid").mGrid("destroy");
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: '1200px',
                height: '400px',
                headerHeight: "70px",
                subHeight: "140px",
                subWidth: "100px",
                dataSource: vm.gridOptions.dataSource,
                columns: vm.gridOptions.columns,
                primaryKey: 'employeeId',
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: true,
                features: vm.gridOptions.features,
                ntsControls: vm.gridOptions.ntsControls
            }).create();
        }

        backToA() {
            const vm = this;

            vm.$jump("../a/index.xhtml");
        }

        createNew() {
            const vm = this;

        }

        register() {
            const vm = this;

        }

        delete() {
            const vm = this;

        }
    }

    interface ReservationRecTime {
        receptionName: string;
        startTime: number;
        endTime: number;
    }

    // 予約修正抽出条件発注無し
    enum ReservationCorrectNotOrder {
        ALL_RESERVE = 0,
        MORE_THAN_2_ITEMS = 1
    }

    // 予約修正抽出条件
    enum ReservationCorrect {
        ALL_RESERVE = 0, 
        MORE_THAN_2_ITEMS = 1, 
        ORDER = 2,
        NOT_ORDERING = 3 
    }

    const API = {
        startCorrect: "at/record/reservation/bento-menu/startCorrect"
    }
}