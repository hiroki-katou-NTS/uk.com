module nts.uk.at.kmr003.c {
    @bean()
    export class KMR003CViewModel extends ko.ViewModel {
        date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
        receptionHour: KnockoutObservable<string> = ko.observable("");
        frameNo: any;
        empIds: any[];
        listEmpInfo: any[] = [];
        gridOptions: any = { dataSource: [], columns: [], features: [], ntsControls: [] };
        stampMap: any;
        hasErrorsGrid: KnockoutObservable<boolean> = ko.observable(false);
        errorList: any[] = [];

        created(param: any) {
            const vm = this;

            if (param) {
                vm.date(moment(param.date).format('YYYY/MM/DD'));
                vm.receptionHour(vm.date() + " " + param.receptionHour.receptionName + " " + 
                    nts.uk.time.format.byId("Time_Short_HM", param.receptionHour.startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", param.receptionHour.endTime));
                vm.empIds = param.empIds;
                vm.frameNo = param.frameNo

                let params = {
                    correctionDate: vm.date(), 
                    frameNo: vm.frameNo, 
                    frameName: param.receptionHour.receptionName, 
                    employeeIds: vm.empIds
                }

                vm.$blockui('show');
                vm.$ajax(API.startNewReservation, params).done((res: any) => {
                    if (res) {
                        vm.errorList = res.exceptions;
                        vm.stampMap = res.stampMap;
                        vm.convertToGridData(res);
                        vm.bindGrid();
                        let errors: any[] = [];
                        _.forEach(vm.errorList, (x: any) => {
                            let message = vm.$i18n.message(x.messageId, x.params);
                            errors.push({ message: message, messageId: x.messageId, supplements: {} });
                        })
                        
                        if (errors.length > 0) {
                            nts.uk.ui.dialog.bundledErrors({
                                errors: errors
                            })
                        }
                    }
                }).fail((error: any) => {
                    if (error) {
                        vm.$dialog.error({messageId: error.messageId, messageParams: error.parameterIds});
                    }
                }).always(() => vm.$blockui('hide'))
            }
        }

        convertToGridData(param: any) {
            const vm = this;

            vm.gridOptions = { dataSource: [], columns: [], features: [], ntsControls: [] };


            //bind columns
            vm.gridOptions.columns = [
                { headerText: '' , itemId: 'employeeId', key: 'employeeId', dataType: 'string', width: '150px', hidden: true },
                { headerText: vm.$i18n('KMR003_46') , itemId: 'employeeCode', key: 'employeeCode', ntsControl: 'Label', dataType: 'string', width: '120px' },
                { headerText: vm.$i18n('KMR003_47') , itemId: 'employeeName', key: 'employeeName', ntsControl: 'Label', dataType: 'string', width: '200px' }
            ];

            let headerStyle = { name: 'HeaderStyles', columns: [
                { key: 'employeeCode', colors: ['align-center', 'header_backgroundcolor'] }, 
                { key: 'employeeName', colors: ['align-center', 'header_backgroundcolor'] }, 
            ] };
            let cellStates: any[] = [];

            for (let i = 0; i < param.bento.length; i++) {
                let columnHeader = "<div>" + param.bento[i].name + "</div><div>" + '(' + param.bento[i].unit + ')' + "</div>";
                vm.gridOptions.columns.push({ headerText: columnHeader , itemId: 'bento' + param.bento[i].frameNo, key: 'bento' + param.bento[i].frameNo, dataType: 'string', width: '70px', constraint: {primitiveValue: 'BentoReservationCount'} })
                headerStyle.columns.push({ key: 'bento' + param.bento[i].frameNo, colors: ['align-center', 'header_backgroundcolor'] });
            }
            
            // bind features
            let columnFixing = { name: 'ColumnFixing', columnSettings: [
                { columnKey: 'employeeCode', isFixed: true }, 
                { columnKey: 'employeeName', isFixed: true }, 
            ]};

            // bind dataSource
            vm.listEmpInfo = param.listEmpInfo;
            
            for (let i = 0; i < vm.listEmpInfo.length; i++) {
                let emp = vm.listEmpInfo[i];
                
                let item = { 
                    employeeId: emp.employeeId, 
                    employeeCode: emp.employeeCode, 
                    employeeName: emp.businessName
                }
                cellStates.push({ rowId: emp.employeeId, columnKey: 'employeeName', state: ['limited-label', 'padding-3'] });
                
                vm.gridOptions.dataSource.push(item);
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
                width: '1170px',
                height: '600px',
                headerHeight: "70px",
                subHeight: "117px",
                subWidth: "105px",
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

        mounted() {
            const vm = this;

            setInterval(() => {
                let dataSource = $('#grid').mGrid("dataSource");
                let errors = $('#grid').mGrid('errors');
                if (errors.length > 0) {
                    vm.hasErrorsGrid(false);
                } else {
                    vm.hasErrorsGrid(true);
                }
            }, 200);
        }

        register() {
            const vm = this;

            let dataSource = $('#grid').mGrid("dataSource");
            let command = {
                frameNo: vm.frameNo, 
                correctionDate: moment(vm.date()).format("YYYY/MM/DD"), 
                bentoReservations: []
            }
            for (let i = 0; i < dataSource.length; i++) {
                let row = dataSource[i];
                let empId = row.employeeId;
                let cardNo = vm.stampMap[empId];
                let record = {
                    reservationCardNo: cardNo, 
                    reservationDate: moment(vm.date()).format("YYYY/MM/DD"), 
                    closingTimeFrame: vm.frameNo, 
                    ordered: false, 
                    workLocationCode: null, 
                    listBentoReservationDetail: []
                }

                for (let item in row) {
                    if (_.startsWith(item, 'bento')) {
                        let frame = item.substring(5);

                        if (item && row[item] && !_.isEmpty(row[item].trim())) {
                            let detail = {
                                frameNo: frame, 
                                bentoCount: row[item].toString().trim(), 
                                dateTime: moment().format("YYYY/MM/DD HH:mm:ss"), 
                                autoReservation: false
                            }

                            record.listBentoReservationDetail.push(detail);
                        }
                    }
                }

                if (record.listBentoReservationDetail.length > 0) {
                    command.bentoReservations.push(record);
                }
            }

            vm.$blockui('grayout');
            vm.$ajax(API.register, command).done((res: any) => {
                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                    nts.uk.ui.windows.setShared("STATUSC", "DONE");
                    vm.$window.close();
                });
            }).fail((err: any) => {
                if (err) {
                    vm.$dialog.error({messageId: err.messageId, messageParams: err.parameterIds});
                }
            }).always(() => { vm.$blockui('hide') });
        }

        close() {
            const vm = this;

            nts.uk.ui.windows.setShared("STATUSC", "CLOSE");
            vm.$window.close();
        }
    }

    const API = {
        startNewReservation: "at/record/reservation/bento-menu/startNewReservation", 
        register: "at/record/reservation/bento-menu/registerNewReservation"
    }
}