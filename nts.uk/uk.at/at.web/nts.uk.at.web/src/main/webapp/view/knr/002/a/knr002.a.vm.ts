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
            selectedFilter: KnockoutObservable<string>;
            dataSource: KnockoutObservable<ResponseData> = ko.observable();
            gridData: KnockoutObservableArray<ListEmpInfoTerminalDto> = ko.observableArray([]);

            // num of state
            numAbnormalState: KnockoutObservable<number> = ko.observable(0);
            numNormalState: KnockoutObservable<number> = ko.observable(0);
            numOfRegTerminals: KnockoutObservable<number> = ko.observable(0);
            numUntransmitted: KnockoutObservable<number> = ko.observable(0);
            

            
            constructor() {
                const vm = this;
                vm.filterButton = ko.observableArray([
                    { code: '3', name: getText("KNR002_21") },
                    { code: '0', name: getText("KNR002_22") },
                    { code: '1', name: getText("KNR002_23") },
                    { code: '2', name: getText("KNR002_24") }
                ]);
                vm.selectedFilter = ko.observable('3');
                vm.selectedFilter.subscribe(function(value) {
                    vm.filterHandle(value); 
                });
            }

            public startPage(): JQueryPromise<void>{
                var vm = this;		

                var dfd = $.Deferred<void>();
                vm.loadData();
                setInterval(vm.loadData.bind(vm), 300000);																	
                dfd.resolve();											
                return dfd.promise();											
            }

            private removeBorder () {
                let vm = this;
                $('#grid_headers th:nth-child(8)').addClass('br-0');
                $('#grid_headers th:nth-child(9)').addClass('br-0 bl-0');
                $('#grid_headers th:nth-child(10)').addClass('bl-0');
            }

            public loadData() {
                let vm = this;
                blockUI.invisible();
                
                service.getAll()
                .done((res: ResponseData) => {
                    if (res) {
                        
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
                            }
                            if (dto.terminalCurrentState === 1) {
                                dto.displayCurrentState = getText("KNR002_23");
                            }
                            if (dto.terminalCurrentState === 2) {
                                dto.displayCurrentState = getText("KNR002_24");
                            }
    
                            dto.displayFlag = dto.requestFlag ? getText("KNR002_250") : '';
                        }
    
                        vm.gridData(res.listEmpInfoTerminalDto);
                        setShared('KNR002D_empInfoTerList', res.listEmpInfoTerminalDto);
                        vm.loadGrid();
                        vm.setGridSize();
                        $(window).on('resize', () => {
                            vm.setGridSize();
                            vm.removeBorder();
                        });
                    }
                })
                .fail(res => console.log('fail roi'))
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
            

            private filterHandle(value: string) {
                $("#grid").igGridFiltering('filter', ([{ fieldName: "terminalCurrentState", expr: parseInt(value), cond: "equals"}]));
                    $('.ui-iggrid-footer').css('display', 'none');
                    if (value == '3') {
                        $("#grid").igGridFiltering("filter", []);
                    } 
            }

            private setGridSize() {
                let width = window.innerWidth;
                let height = window.innerHeight;
                $('#grid').igGrid('option', 'width', width - 17);
                $('#grid').igGrid('option', 'height', height - 198);
            }

            private loadGrid() {
                let vm = this;

                var stateTable: any = [];
                
                vm.dataSource().listEmpInfoTerminalDto.forEach((e, index) => {
                    if (e.terminalCurrentState !== 1)  return;
                    [ "empInfoTerCode", "terminalCurrentState", "empInfoTerName", "displayModelEmpInfoTer",
                        "workLocationName",  "signalLastTime", "displayCurrentState", "open", "open1", "open2", "displayFlag" ].forEach(column => {
                        stateTable.push(new CellState(e.empInfoTerCode, column, ["bg-red"]));
                    });
                    
                    
                });
                    
                $("#grid").ntsGrid({
                    width: '800px',
                    height: '400px',
                    dataSource: vm.dataSource().listEmpInfoTerminalDto,
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    autoFitWindow: true,
                    columns: [
                            { headerText: getText("KNR002_40"), key: 'empInfoTerCode', dataType: 'string', width: '90px'},
                            { headerText: 'CurrentState', key: 'terminalCurrentState', dataType: 'number', width: '0px',  hidden: true },
                            { headerText: getText("KNR002_41"), key: 'empInfoTerName', dataType: 'string', width: '220px'},
                            { headerText: getText("KNR002_42"), key: 'displayModelEmpInfoTer', dataType: 'string', width: '127px' },
                            { headerText: getText("KNR002_43"), key: 'workLocationName', dataType: 'string', width: '160px' },
                            { headerText: getText("KNR002_44"), key: 'signalLastTime', dataType: 'string', width: '180px' },
                            { headerText: getText("KNR002_45"), key: 'displayCurrentState', dataType: 'string', width: '100px' },
                            { headerText: getText("KNR002_46"), key: 'open', dataType: 'string', width: '76px', unbound: true, ntsControl: 'Button7' },
                            { headerText: getText("KNR002_47"), key: 'open1', dataType: 'string', width: '84px', unbound: true, ntsControl: 'Button8' },
                            { headerText: '', key: 'open2', dataType: 'string', width: '89px', unbound: true, ntsControl: 'Button9' },
                            { headerText: '', key: 'displayFlag', dataType: 'string', width: '133px' },
                            
                    ],
                    features: [
                            { 
                                name: 'Filtering', 
                                renderFilterButton : false
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
                        { name: 'Button8', text: getText("KNR002_51"), click: function(e: any) { vm.openKNR002CDialog(e) }, controlType: 'Button' },
                        { name: 'Button9', text: getText("KNR002_52"), click: function(e: any) { vm.openKNR002GDialog(e) }, controlType: 'Button' }
                    ],
                });

                vm.filterHandle(vm.selectedFilter());
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