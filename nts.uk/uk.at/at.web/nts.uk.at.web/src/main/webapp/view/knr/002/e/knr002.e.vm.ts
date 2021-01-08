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
            selectedMode: KnockoutObservable<number>;
    
            constructor() {
                const vm = this;   

                vm.mode = ko.observableArray([
                    { code: 1, name: getText("KNR002_116") },
                    { code: 2, name: getText("KNR002_117") },
                ]);
                vm.selectedMode = ko.observable(1);

                vm.selectedMode.subscribe((value: number) => {
                    $("#bak-grid1").ntsGrid("destroy");
                    vm.loadGridTop(value);
                    vm.setLabelCorlor(value);
                });
                
                vm.loadBakGrid();
                vm.loadGridTop(1);
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

                let dataGrid2 = [{empInfoTerCode: '0001',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'},
                                {empInfoTerCode: '0002',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'},
                                {empInfoTerCode: '0003',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'},
                                {empInfoTerCode: '0004',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'},
                                {empInfoTerCode: '0005',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'},
                                {empInfoTerCode: '0006',empInfoTerName: 'string',displayModelEmpInfoTer: 'string',backupDate: '2018/9/14 10:51'}];

                let detailGridData = [{majorClassification: 'string',smallClassification: 'small1',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small2',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small3',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small4',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small5',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small6',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small7',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small8',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small9',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small10',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small11',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small12',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small13',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small14',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small15',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small16',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small17',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small18',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small19',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small20',setValue: '5:00',inputRange: 'string'},
                                    {majorClassification: 'string',smallClassification: 'small21',setValue: '5:00',inputRange: 'string'}];

                $('#bak-grid2').ntsGrid({
                    width: '430px', 
                    height: '140px',
                    dataSource: dataGrid2,
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                        { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                        { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '80px'},
                        { headerText: 'Date', key: 'backupDate', dataType: 'string', width: '150px'},
                    ],
                    features: [
                        { 
                            
                        }
                    ]
                });

                $('#detail-grid').ntsGrid({
                    width: '560px', 
                    height: '485px',
                    dataSource: detailGridData,
                    primaryKey: 'smallClassification',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '大項目', key: 'majorClassification', dataType: 'string', width: '110px'},
                        { headerText: '小項目', key: 'smallClassification', dataType: 'string', width: '140px'},
                        { headerText: '設定値', key: 'setValue', dataType: 'string', width: '90px'},
                        { headerText: '入力範囲', key: 'inputRange', dataType: 'string', width: '220px'},
                    ],
                    features: [
                        { 
                            
                        }
                    ]
                })
            }

            private loadGridTop(mode: number) {
                const vm = this;
                
                let gridData: any;
                let columns: any;
                let width: any;

                if (mode === 1) {
                    gridData = [{empInfoTerCode: '0001',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0002',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0003',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0004',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0005',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0006',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'}];

                    columns = [{ headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                                { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                                { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '80px'}];

                    width = '280px';

                } else  {
                    gridData = [{empInfoTerCode: '0001',empInfoTerName: 'string',displayModelEmpInfoTer: 'string', status: "送信待ち", rawCode: "0001"},
                                {empInfoTerCode: '0002',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0003',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0004',empInfoTerName: 'string',displayModelEmpInfoTer: 'string', status: "送信待ち", rawCode: "0001"},
                                {empInfoTerCode: '0005',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'},
                                {empInfoTerCode: '0006',empInfoTerName: 'string',displayModelEmpInfoTer: 'string'}];

                    columns = [{ headerText: '端末No', key: 'empInfoTerCode', dataType: 'string', width: '70px'},
                                { headerText: 'Name', key: 'empInfoTerName', dataType: 'string', width: '100px'},
                                { headerText: 'Type', key: 'displayModelEmpInfoTer', dataType: 'string', width: '80px'},
                                { headerText: '状況', key: 'status', dataType: 'string', width: '80px'},
                                { headerText: '元データ端末No', key: 'rawCode', dataType: 'string', width: '130px'}];
                    
                    width = '460px';
                        
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
                            
                        }
                    ]
                });
            }

            public startPage(): JQueryPromise<void> {
                const vm = this;
                var dfd = $.Deferred<void>();
                vm.loadInitData();
                dfd.resolve();
                return dfd.promise();
            }

            private loadInitData() {
                const vm = this;
                blockUI.invisible();

                service.getInitData()
                .done((res: InitialDisplayBackupScreenDto) => {
                    console.log('done');
                    if (res) {
                        console.log(res);
                    }
                })
                .fail(res => console.log(res))
                .always(() => blockUI.clear());
            }

            public closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
        export interface BakDataGridTop {
            empInfoTerCode: string; 
            empInfoTerName: string;
            displayModelEmpInfoTer: string;
            status?: string;
            rawCode?: string;
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
        }

        export interface TimeRecordSetFormatBakEDto {
            backupDate: string;
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTer: number;
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