module knr002.d {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel{
        export class ScreenModel{
			empInfoTerCode: KnockoutObservable<string>;
            empInfoTerName: KnockoutObservable<string>;
            destinationCopyList: KnockoutObservableArray<EmpInfoTerminal>;
            selectableCodeList: Array<string>;
            command: any;
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable('');
                self.empInfoTerName = ko.observable('');
                self.destinationCopyList = ko.observableArray<EmpInfoTerminal>([]);
                self.selectableCodeList = [];
                self.command = {};
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                // get shared from C
                self.command = getShared('KNR002D_command');
                self.empInfoTerCode(self.command.empInfoTerCode);
                self.empInfoTerName(self.command.displayName);
                if(self.empInfoTerCode() === undefined || self.empInfoTerCode() === '' || self.empInfoTerCode().length <= 0){
                    self.destinationCopyList([]);
                    self.selectableCodeList = [];
                } else {
                        service.getDestinationCopyList(self.empInfoTerCode()).done((data: Array<any>) => {
                        if(!data){
                            self.destinationCopyList([]);
                            self.selectableCodeList = [];
                            self.bindDestinationCopyList();
                        } else {
                            // get Shared from A
                            let empInfoTerList = getShared('KNR002_empInfoTerList');
                            let desCopyTempList = [];
                            if(empInfoTerList){
                                let keyMap: any = {};
                                _.forEach(empInfoTerList, e => {
                                    keyMap[e.empInfoTerCode] = e;
                                }); 
                                for(let item of data){
                                    let desCopyTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, item.modelEmpInfoTer, item.workLocationName);
                                    let currentItem = keyMap[item.empInfoTerCode];                           
                                    if (currentItem) {
                                        desCopyTemp.modelEmpInfoTerName = currentItem.displayModelEmpInfoTer;
                                        desCopyTemp.workLocationName = currentItem.workLocationName;
                                    }                               
                                    desCopyTempList.push(desCopyTemp);
                                }
                            } else{
                                // do something
                                // for(let item of data){
                                //     let desCopyTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, item.modelEmpInfoTer, item.workLocationName);
                                //     desCopyTempList.push(desCopyTemp);
                                // }   
                            }
                            self.destinationCopyList(desCopyTempList);  
                        }                       
                        self.bindDestinationCopyList();
                    });
                }      
                blockUI.clear();   
                $('#D5_1').focus();																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * bind table D3_2
             */
            private bindDestinationCopyList(): void{
                let self = this;
                $("#D3_2").ntsGrid({
                    height: 169,
                    dataSource: self.destinationCopyList(),
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    hidePrimaryKey: false,
                    columns: [
                        { headerText: '', key: 'availability', dataType: 'boolean', width: ' 35px', ntsControl: 'Checkbox' },
                        { headerText: getText('KNR002_105'), key: 'empInfoTerCode', dataType: 'string', width: 60},
                        { headerText: getText('KNR002_106'), key: 'empInfoTerName', dataType: 'string', width: 200},
                        { headerText: getText('KNR002_107'), key: 'modelEmpInfoTerName', dataType: 'string', width: 120},
                        { headerText: getText('KNR002_108'), key: 'workLocationName', dataType: 'string', width: 200},
                    ],
                    features: [{
                        //  name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }],
                    ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }]
                });
            }
            /**
             * D5_1
             * 複写ボタン
             */
            private copy(): any {
                let self = this;
                // Process
                
                self.selectableCodeList = [];
                _.forEach(self.destinationCopyList(), e => {
                    if(e.availability)
                    self.selectableCodeList.push(e.empInfoTerCode);
                });  
                if(self.selectableCodeList.length <= 0){
                    dialog.error({messageId: "Msg_2093"}).then(()=>{
                        return;
                    }); 
                } else {    
                    setShared('KNR002D_selectableCodeList', self.selectableCodeList);
                    self.command.empInfoTerCode = self.selectableCodeList;      
                    blockUI.invisible();
                    service.checkRemoteSettingsToCopy(self.selectableCodeList).done(()=>{
                        self.call_C_Api(self.command);
                    }).fail(err => { 
                        dialog.confirm({messageId: err.messageId, messageParams: err.parameterIds})
                        .ifYes(() => {                   
                            self.call_C_Api(self.command);
                        }).ifNo(() => {
                            blockUI.clear();
                        });
                    });
                }          
            }
            /**
             * D5_2
             * キャンセルボタン
             */
            private cancel_Dialog(): any {
                nts.uk.ui.windows.close();
            }
            /**
             * fill blank record on Grid
             */
            private fillBlankRecord(arr: Array<any>, maxLen: number): any{
                let recordTotal = arr.length;
                if(recordTotal < maxLen){
                    let blankRecords = maxLen - recordTotal;
                    for(let i = 0; i < blankRecords; i++){
                        let displayLog = new EmpInfoTerminal(" ", " ", " ", " ");
                        arr.push(displayLog);
                    }
                }
                return arr;
            }
            /**
             * register and submit changes(call C screen Api)
             */
            private call_C_Api(command: any): any{
                delete command.displayName;
                blockUI.invisible();
                service.registerAndSubmitChanges(command).done(() => { 
                    let updateRemoteInput = {
                        listEmpTerminalCode: command.empInfoTerCode
                    }
                    service.updateRemoteSettings(updateRemoteInput).done(() => {})

                    nts.uk.ui.windows.close();
                }).fail(err => {
                    dialog.error({messageId: err.messageId});
                }).always(() => blockUI.clear());
            }
        }
        class EmpInfoTerminal{
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTerName: string;
            workLocationName: string;
            availability: boolean;
            constructor(empInfoTerCode: string, empInfoTerName: string, modelEmpInfoTerName: string, workLocationName: string){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.modelEmpInfoTerName = modelEmpInfoTerName;
                this.workLocationName = workLocationName;
                this.availability = false;
            }
        }
    }
}