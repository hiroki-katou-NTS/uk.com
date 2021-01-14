module nts.uk.at.view.knr002.e {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.e.service;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            displayText: String = "hoi cham";

            mode: KnockoutObservableArray<any>;
            selectedMode: KnockoutObservable<number> = ko.observable(MODE.BACKUP);

            initData: KnockoutObservable<InitialDisplayBackupScreenDto> = ko.observable();
            selectedRow: KnockoutObservable<number> = ko.observable(0);
            selectedCode: KnockoutObservable<string> = ko.observable();
            selectedName: KnockoutObservable<string> = ko.observable();
            settingContentGrid: KnockoutObservableArray<LocationSettingDto> = ko.observableArray([]);
            instructions: KnockoutObservable<string> = ko.observable();
            loadTime: number = 0;
    
            constructor() {
                const vm = this;   

                vm.mode = ko.observableArray([
                    { code: MODE.BACKUP, name: getText("KNR002_116") },
                    { code: MODE.RESTORE, name: getText("KNR002_118") },
                ]);
                
                vm.instructions(getText("KNR002_254"));

                vm.selectedMode.subscribe((value: number) => {
                    $("#bak-grid1").ntsGrid("destroy");
                    vm.loadInstalledTerminals(value);
                    $('#bak-grid2').igGridSelection('selectRow', 0);
                    $('#bak-grid1').igGridSelection('selectRow', 0);
                    vm.setLabelCorlor(value);
                    if (value == MODE.BACKUP) {
                        vm.loadLocalSettings(vm.initData().listEmpInfoTerminal[0].empInfoTerCode);
                        vm.instructions(getText("KNR002_254"));
                    } else {
                        vm.loadBackupContent(vm.initData().listTimeRecordSetFormatBakEDto[0].empInfoTerCode);
                        vm.instructions(getText("KNR002_255"));
                    }
                }); 
            }

            private setLabelCorlor(value: number) {
                if (value == 1) {
                    $('.panel.ntsPanel').css('background-color', '#EDFAC2');
                    return;
                }
                $('.panel.ntsPanel').css('background-color', '#FFEBFF');
            }


            private loadBakGrid() {
                const vm = this;

                $('#bak-grid2').ntsGrid({
                    width: '455px', 
                    height: '140px',
                    dataSource: vm.initData().listTimeRecordSetFormatBakEDto,
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                        { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                        { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '95px'},
                        { headerText: 'Date', key: 'backupDate', dataType: 'string', width: '190px'},
                    ],
                    features: [
                        { 
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: false,
                            rowSelectionChanged: function(event: any, ui: any) {
                                vm.selectedRow(ui.row.index);
                                $('#bak-grid1').igGridSelection('selectRow', vm.selectedRow());
                                vm.selectedCode(ui.row.id);
                                vm.loadBackupContent(vm.selectedCode());
                                vm.selectedName(vm.initData().listTimeRecordSetFormatBakEDto[vm.selectedRow()].empInfoTerName);
                            }
                        }
                    ]
                });
                $('#bak-grid2').igGridSelection('selectRow', vm.selectedRow());
            }

            private loadSettingGrid() {
                const vm = this;

                $('#detail-grid').ntsGrid({
                    width: '560px', 
                    height: '485px',
                    dataSource: vm.settingContentGrid(),
                    primaryKey: 'smallClassification',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '大項目', key: 'majorClassification', dataType: 'string', width: '110px'},
                        { headerText: '小項目', key: 'smallClassification', dataType: 'string', width: '140px'},
                        { headerText: '設定値', key: 'settingValue', dataType: 'string', width: '90px'},
                        { headerText: '入力範囲', key: 'inputRange', dataType: 'string', width: '220px'},
                    ],
                    features: [
                        { 
                            
                        }
                    ]
                })
            }

            private loadInstalledTerminals(mode: number) {
                const vm = this;
                
                let gridData: any;
                let columns: any;
                let width: any;

                if (mode === 1) {
                    gridData = vm.initData().listEmpInfoTerminal;

                    columns = [{ headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                                { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                                { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '80px'}];

                    width = '280px';
                } else  {
                    gridData = vm.initData().listEmpInfoTerminal.map((item: EmpInfoTerminalEDto) => { 
                        
                        let index = _.findIndex(vm.initData().listCodeFlag, (codeFlag: FlagByCode) => codeFlag.empInfoTerminalCode == item.empInfoTerCode);
                        let status = '';

                        if (index != -1) {
                            status = vm.initData().listCodeFlag[index].flag ? getText("KNR002_256") : '';                 
                        }

                        let newItem: BakDataGridTop = {
                            empInfoTerCode: item.empInfoTerCode,
                            empInfoTerName: item.empInfoTerName,
                            displayModelEmpInfoTer: item.displayModelEmpInfoTer,
                            status
                        };

                        return newItem;
                    });

                    columns = [{ headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                                { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                                { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '80px'},
                                { headerText: '状況', key: 'status', dataType: 'string', width: '80px'}];
                    
                    width = '330px';  
                }

                $('#bak-grid1').ntsGrid({
                    width, 
                    height: '140px',
                    dataSource: gridData,
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns,
                    features: [
                        { 
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: false,
                            rowSelectionChanged: function(event: any, ui: any) {
                                vm.selectedRow(ui.row.index);
                                vm.selectedCode(ui.row.id);
                                $('#bak-grid2').igGridSelection('selectRow', vm.selectedRow());
                                vm.loadLocalSettings(vm.selectedCode());
                                vm.selectedName(vm.initData().listEmpInfoTerminal[vm.selectedRow()].empInfoTerName);
                            }
                        }
                    ]
                });

                $('#bak-grid1').igGridSelection('selectRow', vm.selectedRow());
            }

            private loadLocalSettings(code: string) {
                const vm = this;

                blockUI.invisible();
                service.getLocationSetting(code)
                .done((res: Array<LocationSettingDto>) => {
                    if (res) {
                        vm.settingContentGrid(res);
                        $("#detail-grid").igGrid("dataSourceObject", vm.settingContentGrid()).igGrid("dataBind");
                    }
                })
                .fail((res: any) => console.log(res))
                .always(() => blockUI.clear());
            }

            private loadBackupContent(code: string) {
                const vm = this;

                blockUI.invisible();
                service.getBackupContent(code)
                .done((res: Array<LocationSettingDto>) => {
                    if (res) {
                        vm.settingContentGrid(res);
                        $("#detail-grid").igGrid("dataSourceObject", vm.settingContentGrid()).igGrid("dataBind");
                    }
                })
                .fail((res: any) => console.log(res))
                .always(() => blockUI.clear());
            }

            public backupHandle() {
                const vm = this;
                
                blockUI.invisible();
                service.getBackup(vm.selectedCode())
                .done(() => {
                    let selectedCode : string = vm.selectedCode();
                    vm.loadInitData();
                    vm.selectedCode(selectedCode);
                })
                .fail((res: any) => console.log(res))
                .always(() => blockUI.clear());
            }

            public restoreHandle() {
                const vm = this;

                let shareData = vm.initData().listTimeRecordSetFormatBakEDto[vm.selectedRow()];
                setShared('KNR002E_share', shareData);
                modal('/view/knr/002/f/index.xhtml', { title: 'F_Screen', }).onClosed(() => {
                    let isCancel = getShared('KNR002E_cancel');
                    
                    if (!isCancel) {
                        vm.loadInitData();
                    }
                });
            }

            public startPage(): JQueryPromise<void> {
                const vm = this;
                var dfd = $.Deferred<void>();
                vm.loadInitData(vm.loadTime);
                dfd.resolve();
                return dfd.promise();
            }

            private loadInitData(loadTime?: number) {
                const vm = this;
                blockUI.invisible();

                service.getInitData()
                .done((res: InitialDisplayBackupScreenDto) => {
                    console.log('load Init');
                    if (res) {
                        
                        vm.setDisplayModelEmpInfoTerminal(res.listEmpInfoTerminal);
                        vm.setDisplayModelEmpInfoTerminal(res.listTimeRecordSetFormatBakEDto);
                
                        vm.initData(res);
                        if (loadTime == 0) {
                            vm.selectedCode(vm.initData().listEmpInfoTerminal[0].empInfoTerCode);
                            loadTime++;
                        }     
                        if (vm.selectedMode() == MODE.BACKUP) {
                            vm.loadLocalSettings(vm.selectedCode());
                        } else {
                            vm.loadBackupContent(vm.selectedCode());
                        }
                        vm.loadSettingGrid();
                        vm.loadBakGrid();
                        vm.loadInstalledTerminals(vm.selectedMode());
                    }
                })
                .fail((res: any) => console.log(res))
                .always(() => blockUI.clear());
            }

            private setDisplayModelEmpInfoTerminal(list: any) {
                const vm = this;
                for (let empInfoterminal of list) {
                    if (empInfoterminal.modelEmpInfoTer === 7) {
                        empInfoterminal.displayModelEmpInfoTer = getText("KNR002_251");
                    }
                    if (empInfoterminal.modelEmpInfoTer === 8) {
                        empInfoterminal.displayModelEmpInfoTer = getText("KNR002_252");
                    }
                    if (empInfoterminal.modelEmpInfoTer === 9) {
                        empInfoterminal.displayModelEmpInfoTer = getText("KNR002_253");
                    }
                }
            }

            public closeDialog() {
                nts.uk.ui.windows.close();
            }
        }


        export interface LocationSettingDto {
            majorNo: number;
            majorClassification: string;
            smallNo: number;
            smallClassification: string;
            settingValue: string;
            inputRange: string;
        }
        
        export interface BakDataGridTop {
            empInfoTerCode: string; 
            empInfoTerName: string;
            displayModelEmpInfoTer: string;
            status?: string;
        }
        export interface BakDataGridBottom {
            empInfoTerCode: string; 
            empInfoTerName: string;
            displayModelEmpInfoTer: string;
            backupDate: string;
        }
        export interface DetailGrid {
            majorClassification: string;
            smallClassification: string;
            setValue: string;
            inputRange: string;
        }

        export interface InitialDisplayBackupScreenDto {
            listEmpInfoTerminal: Array<EmpInfoTerminalEDto>;
            listTimeRecordSetFormatBakEDto: Array<TimeRecordSetFormatBakEDto>;
            listCodeFlag: Array<FlagByCode>;
        }

        export interface EmpInfoTerminalEDto {
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTer: number;
            displayModelEmpInfoTer: string;
        }

        export interface TimeRecordSetFormatBakEDto {
            backupDate: string;
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTer: number;
            displayModelEmpInfoTer: string;
        }

        export interface FlagByCode {
            empInfoTerminalCode: string;
            flag: boolean;
        }

        enum MODE {
            BACKUP = 1,
            RESTORE = 2,
        }
    }
}