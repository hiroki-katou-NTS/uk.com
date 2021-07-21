module nts.uk.at.view.knr002.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.a.service;
    import modal =  nts.uk.ui.windows.sub.modal;


    export module viewmodel{
        export class ScreenModel{

            // switch button
            filterButton: KnockoutObservableArray<any>;
            selectedFilter: KnockoutObservable<number> = ko.observable();
            dataSource: KnockoutObservable<ResponseData> = ko.observable();
            normalList: Array<ListEmpInfoTerminalDto> = [];
            abNormalList: Array<ListEmpInfoTerminalDto> = [];
            notCommunicatedList: Array<ListEmpInfoTerminalDto> = [];

            // num of state
            numAbnormalState: KnockoutObservable<number> = ko.observable(0);
            numNormalState: KnockoutObservable<number> = ko.observable(0);
            numOfRegTerminals: KnockoutObservable<number> = ko.observable(0);
            numUntransmitted: KnockoutObservable<number> = ko.observable(0);
            

            
            constructor() {
                const vm = this;
                vm.filterButton = ko.observableArray([
                    { code: 3, name: getText("KNR002_21") },
                    { code: 0, name: getText("KNR002_22") },
                    { code: 1, name: getText("KNR002_23") },
                    { code: 2, name: getText("KNR002_24") }
                ]);
                vm.selectedFilter(3);
                
                vm.selectedFilter.subscribe(function(value) {
                    let filterValue;
                    switch(value) {
                        case 0:
                            $("#grid").ntsGrid("destroy");
                            vm.loadGrid(vm.normalList);
                            filterValue =  getText("KNR002_22");
                            break;
                        case 1:
                            $("#grid").ntsGrid("destroy");
                            vm.loadGrid(vm.abNormalList);
                            filterValue =  getText("KNR002_23");
                            break;
                        case 2:
                            $("#grid").ntsGrid("destroy");
                            vm.loadGrid(vm.notCommunicatedList);
                            filterValue =  getText("KNR002_24");
                            break;
                        case 3:
                            $("#grid").ntsGrid("destroy");
                            vm.loadGrid(vm.dataSource().listEmpInfoTerminalDto);
                            filterValue =  getText("KNR002_21");
                            break;
                    }
                    setTimeout(() => {
                        $('#grid tr:nth-child(1)').focus();
                    }, 0);
                });
            }

            public startPage(): JQueryPromise<void>{
                var vm = this;		

                var dfd = $.Deferred<void>();
                vm.loadData(vm.selectedFilter());
                setInterval(vm.refresh.bind(vm), 300000);
                																	
                dfd.resolve();											
                return dfd.promise();											
            }

            private removeBorder () {
                let vm = this;
                $('#grid_headers th:nth-child(8)').addClass('br-0');
                $('#grid_headers th:nth-child(9)').addClass('br-0 bl-0');
                $('#grid_headers th:nth-child(10)').addClass('bl-0');
            }

            private refresh() {
                const vm = this;

                $("#grid").ntsGrid("destroy");
                vm.normalList = [];
                vm.abNormalList = [];
                vm.notCommunicatedList = [];
  
                vm.loadData(vm.selectedFilter());
            }

            public loadData(selectedFilter: number) {
                let vm = this;
                blockUI.invisible();
                
                service.getAll()
                .done((res: ResponseData) => {
                    if (res) {
                        // let start = performance.now();
                        vm.dataSource(res);
                        vm.numOfRegTerminals(res.numOfRegTerminals);
                        vm.numAbnormalState(res.numAbnormalState);
                        vm.numNormalState(res.numNormalState);
                        vm.numUntransmitted(res.numUntransmitted)
    
                        for (let dto of res.listEmpInfoTerminalDto) {
    
                            if (dto.modelEmpInfoTer === 7) {
                                dto.displayModelEmpInfoTer = getText("KNR002_251");
                            }
                            if (dto.modelEmpInfoTer === 8) {
                                dto.displayModelEmpInfoTer = getText("KNR002_252");
                            }
                            if (dto.modelEmpInfoTer === 9) {
                                dto.displayModelEmpInfoTer = getText("KNR002_253");
                            }
                            if (dto.terminalCurrentState === 0) {
                                dto.displayCurrentState = getText("KNR002_22");
                                vm.normalList.push(dto);
                            }
                            if (dto.terminalCurrentState === 1) {
                                dto.displayCurrentState = getText("KNR002_23");
                                vm.abNormalList.push(dto);
                            }
                            if (dto.terminalCurrentState === 2) {
                                dto.displayCurrentState = getText("KNR002_24");
                                vm.notCommunicatedList.push(dto);
                            }
                            dto.displayFlag = dto.requestFlag ? getText("KNR002_250") : '';   
                        }
    
                        
                        setShared('KNR002_empInfoTerList', res.listEmpInfoTerminalDto);

                         switch(selectedFilter) {
                            case 0:
                                vm.loadGrid(vm.normalList);
                                break;
                            case 1:
                                vm.loadGrid(vm.abNormalList);
                                break;
                            case 2:
                                vm.loadGrid(vm.notCommunicatedList);
                                break;
                            case 3:
                                vm.loadGrid(vm.dataSource().listEmpInfoTerminalDto);
                                break;
                        }

                        $('#grid tr:nth-child(1)').focus();
                        
                        // vm.loadGrid(vm.dataSource().listEmpInfoTerminalDto);
                    
                        $(window).on('resize', () => {
                            vm.setGridSize();
                            vm.removeBorder();
                        });
                    }
                })
                .fail(res => {})
                .always(() => blockUI.clear() );
            }

            private openKNR002GDialog(data: any) {
                const vm = this;
                blockUI.invisible();
                setShared('KNR002G_empInfoTerCode', data.empInfoTerCode);
                setShared('KNR002G_empInfoTerName', data.empInfoTerName);
                setShared('KNR002G_modelEmpInfoTer', data.displayModelEmpInfoTer);
                setShared('KNR002G_workLocationName', data.workLocationName);
       
                modal('/view/knr/002/g/index.xhtml', { title: 'G_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private openKNR002BDialog(data: any) {
                const vm = this;
                blockUI.invisible();
                setShared('knr002-b', data);
                modal('/view/knr/002/b/index.xhtml', { title: 'B_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private openKNR002CDialog(data: any) {
                const vm = this;
                blockUI.invisible();
                setShared('knr002-c', data);
                modal('/view/knr/002/c/index.xhtml', { title: 'C_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private openKNR002EDialog(data: any) {
                const vm = this;
                blockUI.invisible();
                setShared('knr002-e', data);
                modal('/view/knr/002/e/index.xhtml', { title: 'E_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private openKNR002LDialog() {
                const vm = this;
                blockUI.invisible();
                let data = {
                    mode: 'bulk',
                    empInfoTerminalCode: ''
                };
                setShared('dataShareL', data);
                modal('/view/knr/002/l/index.xhtml', { title: 'L_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            private setGridSize() {
                let width = window.innerWidth;
                let height = window.innerHeight;
                $('#grid').igGrid('option', 'width', width - 17);
                $('#grid').igGrid('option', 'height', height - 198);
            }

            private loadGrid(data: any) {
                let vm = this;

                var stateTable: any = [];
                
                data.forEach((e: any, index: any) => {
                    if (e.terminalCurrentState !== 1)  return;
                    [ "empInfoTerCode", "terminalCurrentState", "empInfoTerName", "displayModelEmpInfoTer",
                        "workLocationName",  "signalLastTime", "displayCurrentState", "open", "open1", "open2", "displayFlag" ].forEach(column => {
                        stateTable.push(new CellState(e.empInfoTerCode, column, ["bg-red"]));
                    });
                    
                    
                });
                   
                // let start = performance.now();
                let gridWidth = window.innerWidth - 17;
                let gridHeight = window.innerHeight - 198; 
                $("#grid").ntsGrid({
                    width: gridWidth,
                    height: gridHeight,
                    dataSource: data,
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    //autoFitWindow: true,
                    columns: [
                            { headerText: getText("KNR002_40"), key: 'empInfoTerCode', dataType: 'string', width: '90px'},
                            { headerText: 'CurrentState', key: 'terminalCurrentState', dataType: 'number', width: '0px',  hidden: true },
                            { headerText: getText("KNR002_41"), key: 'empInfoTerName', dataType: 'string', width: '220px'},
                            { headerText: getText("KNR002_42"), key: 'displayModelEmpInfoTer', dataType: 'string', width: '127px' },
                            { headerText: getText("KNR002_43"), key: 'workLocationName', dataType: 'string', width: '160px' },
                            { headerText: getText("KNR002_44"), key: 'signalLastTime', dataType: 'string', width: '180px' },
                            { headerText: getText("KNR002_45"), key: 'displayCurrentState', dataType: 'string', width: '100px' },
                            { headerText: getText("KNR002_46"), key: 'open', dataType: 'string', width: '76px', unbound: true, ntsControl: 'Button7' },
                            { headerText: getText("KNR002_47"), key: 'open1', dataType: 'string', width: '78px', unbound: true, ntsControl: 'Button8' },
                            { headerText: '', key: 'open2', dataType: 'string', width: '89px', unbound: true, ntsControl: 'Button9' },
                            { headerText: '', key: 'displayFlag', dataType: 'string', width: '133px' },
                            
                    ],
                    features: [
                            { 
                            }
                        ],
                    ntsFeatures: [
                        { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: stateTable },
                    ],
                    ntsControls: [
                        { name: 'Button7', text: getText("KNR002_50"), click: function(e: any) { vm.openKNR002BDialog(e) }, controlType: 'Button' },
                        { name: 'Button8', text: getText("KNR002_51"), click: function(e: any) { 
                            e.totalRegisteredTer = data.length;
                            vm.openKNR002CDialog(e) 
                        }, controlType: 'Button' },
                        { name: 'Button9', text: getText("KNR002_52"), click: function(e: any) { vm.openKNR002GDialog(e) }, controlType: 'Button' }
                    ],
                });

                data.forEach((item: any) => {
                    if (item.terminalCurrentState == 2) {
                        $("#grid").ntsGrid("disableNtsControlAt", item.empInfoTerCode, "open1", 'Button')
                    }
                });
                
                vm.removeBorder();
            }
        }

        export interface ResponseData {
            listEmpInfoTerminalDto: Array<ListEmpInfoTerminalDto>;
            numAbnormalState: number;
            numNormalState: number;
            numOfRegTerminals: number;
            numUntransmitted: number;
        }
        export interface ListEmpInfoTerminalDto {
            empInfoTerCode: string; 
            empInfoTerName: string; 
            modelEmpInfoTer: number; 
            requestFlag: boolean; 
            signalLastTime: string; 
            terminalCurrentState: number;
            workLocationCd: string; 
            workLocationName: string;
            displayModelEmpInfoTer: string;
            displayCurrentState: string;
            displayFlag: string;
        }

        class CellState {
            rowId: string;
            columnKey: string;
            state: Array<any>;

            constructor(rowId: string, columnKey: string, state: Array<any>) {
                this.rowId = rowId;
                this.columnKey = columnKey;
                this.state = state;
            }
        }
    }
}