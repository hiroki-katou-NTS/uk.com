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
        bentoReservationWithEmp: any[] = [];
        listEmpInfo: any[] = [];
        condition3: KnockoutObservable<boolean> = ko.observable(true);
        condition4: KnockoutObservable<boolean> = ko.observable(false);
        condition5: KnockoutObservable<boolean> = ko.observable(false);
        hasErrorsGrid: KnockoutObservable<boolean> = ko.observable(false);
        disableAllRow: KnockoutObservable<boolean> = ko.observable(false);
        isDateError: KnockoutObservable<boolean> = ko.observable(false);
        dateParam: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
    
        created(param: any) {
            const vm = this;
            
            if (param) {
                vm.receptionNames(param.receptionNames);
                vm.receptionHours1(param.receptionHours1);
                vm.receptionHours2(param.receptionHours2);
                vm.orderMngAtr(param.orderMngAtr);
                vm.date(param.correctionDate);
                vm.dateParam(param.correctionDate);
                vm.empIds = param.empIds;
            }

            if (vm.orderMngAtr()) {
                let conditions: any[] = [
                    { 'id': ReservationCorrect.ALL_RESERVE, 'name': vm.$i18n('KMR003_54') },
                    { 'id': ReservationCorrect.MORE_THAN_2_ITEMS, 'name': vm.$i18n('KMR003_55') },
                    { 'id': ReservationCorrect.ORDER, 'name': vm.$i18n('KMR003_56') },
                    { 'id': ReservationCorrect.NOT_ORDERING, 'name': vm.$i18n('KMR003_57') }
                ];
                vm.extractionConditions(conditions);
            } else {
                let conditions: any[] = [
                    { 'id': ReservationCorrectNotOrder.ALL_RESERVE, 'name': vm.$i18n('KMR003_54') },
                    { 'id': ReservationCorrectNotOrder.MORE_THAN_2_ITEMS, 'name': vm.$i18n('KMR003_55') }
                ];
                vm.extractionConditions(conditions);
            }
            vm.selectedCondition(0);

            vm.selectedReception.subscribe((value) => {
                if (value == 1 && vm.receptionHours1()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours1().endTime) );
                }
                if (value == 2 && vm.receptionHours2()) {
                    vm.closingTime(nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", vm.receptionHours2().endTime) );
                }
            });

            if (param) {
                if (param.selectedReception) {
                    vm.selectedReception(param.selectedReception);
                } else {
                    vm.selectedReception('1');
                }
            }

            
        }
        
        mounted() {
            const vm = this;
            vm.startReservation();
    
            vm.date.subscribe((value) => {
                if (value) {
                    vm.startReservation();
                    vm.isDateError(false);
                } else {
                    vm.isDateError(true);
                }
            });
    
            vm.selectedReception.subscribe(() => {
                vm.startReservation();
            });
    
            vm.selectedCondition.subscribe(() => {
                vm.startReservation();
            });

            setInterval(() => {
                let dataSource = $('#grid').mGrid("dataSource");

                if (_.filter(dataSource, ['deleteFlg', true]).length == 0) {
                    vm.condition4(false);
                } else {
                    vm.condition4(true);
                }

                let states = _.filter(vm.gridOptions.features, ['name', 'CellStyles'])[0].states;
                let deleteState = _.filter(states, {'columnKey': "deleteFlg", 'state': ['mgrid-disable']});
                if (deleteState.length === dataSource.length) {
                    vm.disableAllRow(true);
                } else {
                    vm.disableAllRow(false);
                }

                let errors = $('#grid').mGrid('errors');
                if (errors.length > 0) {
                    vm.hasErrorsGrid(false);
                } else {
                    vm.hasErrorsGrid(true);
                }
            }, 200);
        }
        
        startReservation() {
            const vm = this;
            
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
                    if (res.exceptions.length > 0) {
                        if (res.exceptions[0].messageId === "Msg_2256") {
                            vm.$dialog.info({messageId: res.exceptions[0].messageId, messageParams: res.exceptions[0].params});
                        } else {
                            vm.$dialog.error({messageId: res.exceptions[0].messageId, messageParams: res.exceptions[0].params});
                        }
                        vm.listEmpInfo = res.listEmpInfo;
                        // return;
                    }
                    vm.convertToGridData(res);
                    vm.bindGrid();
                }
            }).fail((err) => {
                if (err) {
                    vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                }
            }).always(() => vm.$blockui('hide'));
        }

        convertToGridData(param: any) {
            const vm = this;

            vm.gridOptions = { dataSource: [], columns: [], features: [], ntsControls: [] };

            // bind columns
            vm.gridOptions.columns = [
                { headerText: '' , itemId: 'deleteFlg', key: 'deleteFlg', dataType: 'boolean', width: '50px', checkbox: true, ntsControl: 'deleteFlg' }, 
                { headerText: '' , itemId: 'employeeId', key: 'employeeId', dataType: 'string', width: '150px', hidden: true }, 
                { headerText: vm.$i18n('KMR003_46') , itemId: 'employeeCode', key: 'employeeCode', ntsControl: 'Label', dataType: 'string', width: '120px' }, 
                { headerText: vm.$i18n('KMR003_47') , itemId: 'employeeName', key: 'employeeName', ntsControl: 'Label', dataType: 'string', width: '200px' }, 
                { headerText: vm.$i18n('KMR003_48') , itemId: 'time', key: 'time', ntsControl: 'Label', dataType: 'string', width: '160px' }, 
            ];
            if (vm.orderMngAtr()) {
                vm.gridOptions.columns.push(
                    { headerText: vm.$i18n('KMR003_49'), key: 'orderedHeader', 
                        group: [
                            { headerText: '' , itemId: 'ordered', key: 'ordered', dataType: 'boolean', width: '70px', checkbox: true, ntsControl: 'ordered' }
                        ] }, 
                );
            }
        
            let headerStyle = { name: 'HeaderStyles', columns: [
                { key: 'deleteFlg', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'employeeCode', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'employeeName', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'time', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'orderedHeader', colors: ['remove-border-bottom'] }, 
                { key: 'ordered', colors: ['remove-border-top', 'align-center', 'header_backgroundcolor'] }, 
            ] };
            let cellStates: any[] = [];

            if (param.bento.length == 0) {
                vm.condition3(false);
            } else {
                vm.condition3(true);
            }
            for (let i = 0; i < param.bento.length; i++) {
                let columnHeader = "<div>" + param.bento[i].name + "</div><div>" + '(' + param.bento[i].unit + ')' + "</div>";
                vm.gridOptions.columns.push({ headerText: columnHeader , itemId: 'bento' + param.bento[i].frameNo, key: 'bento' + param.bento[i].frameNo, dataType: 'string', width: '70px', constraint: {primitiveValue: 'BentoReservationCount'} })
                headerStyle.columns.push({ key: 'bento' + param.bento[i].frameNo, colors: ['align-center', 'header_backgroundcolor'] });
            }
            
            // bind features
            let columnFixing = { name: 'ColumnFixing', columnSettings: [
                { columnKey: 'deleteFlg', isFixed: true }, 
                { columnKey: 'employeeCode', isFixed: true }, 
                { columnKey: 'employeeName', isFixed: true }, 
                { columnKey: 'time', isFixed: true }, 
                { columnKey: 'ordered', isFixed: true }
            ]};
            
            // bind ntsControls
            let ntsControls = [
                { name: 'deleteFlg', options: {value: false}, optionsValue: 'value', controlType: 'CheckBox' },
                { name: 'ordered', options: {value: false }, optionsValue: 'value', optionsText: vm.$i18n('KMR003_49'), controlType: 'CheckBox' },
                { name: 'notEditable', editable: false }
            ]
            vm.gridOptions.ntsControls = ntsControls;
            
            // bind dataSource
            vm.bentoReservationWithEmp = param.bentoReservation;
            vm.listEmpInfo = param.listEmpInfo;

            if (vm.bentoReservationWithEmp.length == 0) {
                vm.condition5(false);
            } else {
                vm.condition5(true);
            }
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
                
                let item: any = { 
                    employeeId: empId, 
                    deleteFlg: false, 
                    employeeCode: empCode, 
                    employeeName: empBusinessName, 
                    time: dateMax, 
                    ordered: ordered
                }
                details.forEach((detail: any) => {
                    item['bento' + detail.frameNo] = detail.bentoCount
                })
                vm.gridOptions.dataSource.push(item);
                
                for (let i = 0; i < param.bento.length; i++) {
                    if (!param.bentoReservation[empId].canChangeReservation) {
                        cellStates.push({ rowId: empId, columnKey: 'bento' + param.bento[i].frameNo, state: ['mgrid-disable'] });
                    }
                }
                if (!param.bentoReservation[empId].canChangeReservation) {
                    cellStates.push({ rowId: empId, columnKey: 'deleteFlg', state: ['mgrid-disable'] });
                    cellStates.push({ rowId: empId, columnKey: 'employeeCode', state: ['disable-cell'] });
                    cellStates.push({ rowId: empId, columnKey: 'employeeName', state: ['disable-cell', 'limited-label', 'padding-3'] });
                    cellStates.push({ rowId: empId, columnKey: 'time', state: ['disable-cell'] });
                }
                
                if (!param.roleFlag) {
                    cellStates.push({ rowId: empId, columnKey: 'ordered', state: ['mgrid-disable'] });
                }
                
                cellStates.push({ rowId: empId, columnKey: 'employeeName', state: ['limited-label', 'padding-3'] });
                cellStates.push({ rowId: empId, columnKey: 'deleteFlg', state: ['align-center'] });
                cellStates.push({ rowId: empId, columnKey: 'ordered', state: ['align-center'] });
            }
            vm.gridOptions.dataSource = _.sortBy(vm.gridOptions.dataSource, ['employeeCode']);
            vm.gridOptions.features.push(columnFixing);
            vm.gridOptions.features.push(headerStyle);
            vm.gridOptions.features.push({ name: 'CellStyles', states: cellStates });
            vm.gridOptions.features.push({ name: 'Tooltip', error: true });
        }

        bindGrid() {
            const vm = this;

            if ($("#grid").data("mGrid")) $("#grid").mGrid("destroy");
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: '1200px',
                height: '600px',
                headerHeight: "70px",
                subHeight: "250px",
                subWidth: "100px",
                dataSource: vm.gridOptions.dataSource,
                columns: vm.gridOptions.columns,
                primaryKey: 'employeeId',
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: false,
                features: vm.gridOptions.features,
                ntsControls: vm.gridOptions.ntsControls
            }).create();
        }

        backToA() {
            const vm = this;

            let param = {
                employeeList: _.map(vm.listEmpInfo, x => {return { id: x.employeeId, code: x.employeeCode, name: x.businessName, workplaceName: null  }}), 
                correctionDate: vm.dateParam() ? vm.dateParam() : vm.date(), 
                selectedReception: vm.selectedReception()
            }
            vm.$jump("../a/index.xhtml", param);
        }

        createNew() {
            const vm = this;

            let param = {
                empIds: vm.empIds, 
                date: vm.date(), 
                frameNo: vm.selectedReception(), 
                receptionHour: vm.selectedReception() == 1 ? vm.receptionHours1() : vm.receptionHours2()
            }

            vm.$window.modal('at', '/view/kmr/003/c/index.xhtml', param)
            .then(() => {
                let status = nts.uk.ui.windows.getShared("STATUSC");
                if (status === "DONE") {
                    vm.startReservation();
                }
            })
        }

        register() {
            const vm = this;

            let output: any = vm.convertRowToCommand(false);
            let command = output.command;
            let errors2297: any[] = [];
            if (output.errors.length > 0) {
                _.forEach(output.errors, (x: any) => {
                    let param = x.employeeCode + ' ' + x.employeeName;
                    let message = vm.$i18n.message("Msg_2297", [param]);
                    errors2297.push({ message: message, messageId: "Msg_2297", supplements: {} });
                })
                errors2297 = _.sortBy(errors2297, ['message']);
            }

            vm.$blockui('grayout');
            vm.$ajax(API.register, command).done((res) => {
                if (res) {
                    let errors: any[] = [];
                    _.forEach(res, (x: any) => {
                        let message = vm.$i18n.message(x.messageId, x.params);
                        errors.push({ message: message, messageId: x.messageId, supplements: {} });
                    });
                    errors = errors.concat(errors2297);
                    errors = _.sortBy(errors, ['message']);
                    nts.uk.ui.dialog.bundledErrors({
                        errors: errors
                    }).then(() => vm.startReservation());
                } else {
                    if (errors2297.length > 0) {
                        nts.uk.ui.dialog.bundledErrors({
                            errors: errors2297
                        }).then(() => vm.startReservation());
                    } else {
                        vm.$dialog.info({ messageId: "Msg_15"}).then(() => vm.startReservation());
                    }
                }
            }).fail((err) => {
                if (err) {
                    vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                }
                vm.$blockui('hide')
            })
            // .always(() => vm.$blockui('hide'));
        }

        clear() {
            const vm = this;

            let command: any[] = vm.convertRowToCommand(true).command;

            vm.$blockui("grayout");
            nts.uk.ui.dialog.confirm({messageId:'Msg_18'})
            .ifYes(function() {
                vm.$ajax(API.removeReservation, command).done((res) => {
                    if (res) {
                        let errors: any[] = [];
                        _.forEach(res, (x: any) => {
                            let message = vm.$i18n.message(x.messageId, x.params);
                            errors.push({ message: message, messageId: x.messageId, supplements: {} });
                        })
                        errors = _.sortBy(errors, ['message']);
                        nts.uk.ui.dialog.bundledErrors({
                            errors: errors
                        }).then(() => vm.startReservation());
                    } else {
                        vm.$dialog.info({ messageId: "Msg_16"}).then(() => vm.startReservation());
                    }
                }).fail((err) => {
                    if (err) {
                        vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                        vm.$blockui('hide');
                    }
                })
                // .always(() => vm.$blockui('hide'));
            }).ifNo(function(){
                vm.$blockui('hide');
            });
        }

        convertRowToCommand(isDelete: boolean) {
            const vm = this;

            let dataSource = $('#grid').mGrid("dataSource");
            let command: any = [];
            let errors: any = [];

            for (let empId in vm.bentoReservationWithEmp) {
                let emp = _.filter(vm.listEmpInfo, (e: any) => e.employeeId === empId)[0];

                let canChangeReservation = _.clone(vm.bentoReservationWithEmp[empId].canChangeReservation);
                if (canChangeReservation) {
                    let bentoReservation = _.clone(vm.bentoReservationWithEmp[empId].bentoReservation);
                    let listBentoReservationDetail = bentoReservation.listBentoReservationDetail;
                    let details = [];
    
                    let rowFilter = _.filter(dataSource, (x: any) => x.employeeId === empId);
                    if (rowFilter.length > 0) {
                        let row = rowFilter[0];
    
                        if (isDelete && !row.deleteFlg) {
                            continue;
                        }
                        // loop dataSource grid
                        for (let bento in row) {
                            if (_.startsWith(bento, 'bento')) {
                                let frame = bento.substring(5);
                                // filter bento + frame in list reservation
                                if (_.filter(listBentoReservationDetail, (x: any) => x.frameNo == frame).length > 0) {
                                    // record not edit
                                    if (row[bento] && _.filter(listBentoReservationDetail, (x: any) => x.frameNo == frame)[0].bentoCount == row[bento]) {
                                        details.push(_.filter(listBentoReservationDetail, (x: any) => x.frameNo == frame)[0]);
                                    } 
                                    // record is edited
                                    else if ((row[bento] && _.filter(listBentoReservationDetail, (x: any) => x.frameNo == frame)[0].bentoCount != row[bento] && !_.isEmpty(row[bento].toString().trim()))) {
                                        let detail = _.filter(listBentoReservationDetail, (x: any) => x.frameNo == frame)[0];
                                        detail.bentoCount = row[bento].toString().trim();
                                        detail.dateTime = moment().format('YYYY/MM/DD HH:mm:ss');
    
                                        details.push(detail);
                                    }
                                    // record is deleted in grid
                                    else {
                                        // not add to list details
                                    }
                                } else {
                                    if (row[bento] && !_.isEmpty(row[bento].toString().trim())) {
                                        details.push({ 
                                            frameNo: parseInt(frame),
                                            bentoCount: row[bento].toString().trim(), 
                                            dateTime: moment().format('YYYY/MM/DD HH:mm:ss'), 
                                            autoReservation: false,
                                         });
                                    }
                                }
                            }
    
                        }
    
                        if (details.length == 0) {
                            errors.push({
                                employeeCode: emp.employeeCode, 
                                employeeName: emp.businessName
                            })

                            if (!isDelete) {
                                continue;
                            }
                        }
    
                        // add 1 reservation record to list command
                        bentoReservation.listBentoReservationDetail = details;
                        bentoReservation.ordered = row['ordered'];
                        command.push({
                            bentorReservation: bentoReservation, 
                            employeeCode: emp.employeeCode, 
                            employeeName: emp.businessName
                        });
                }
                }
            }

            return {
                command: command, 
                errors: errors
            };
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
        startCorrect: "at/record/reservation/bento-menu/startCorrect",
        register: "at/record/reservation/bento-menu/registerCorrect", 
        removeReservation: "at/record/reservation/bento-menu/removeReservation"
    }
}