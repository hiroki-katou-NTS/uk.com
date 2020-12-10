module knr002.d {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel{
        export class ScreenModel{
			empInfoTerCode: KnockoutObservable<string>;
            empInfoTerName: KnockoutObservable<string>;
            destinationCopyList: KnockoutObservableArray<EmpInfoTerminal>;
            //currentCodeList: Array<ItemModel>;
            selectableCodeList: Array<ItemModel>;
            //isCancel: boolean;
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable('');
                self.empInfoTerName = ko.observable('');
                self.destinationCopyList = ko.observableArray<EmpInfoTerminal>([]);
                //self.currentCodeList = [];
                self.selectableCodeList = [];
                //self.isCancel = false;
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                // get Shared from C
                let empInfoTerCodeShr = getShared('KNR002D_empInfoTerCode');
                let empInfoTerNameShr = getShared('KNR002D_empInfoTerName');
                self.empInfoTerCode(empInfoTerCodeShr? empInfoTerCodeShr : '');
                self.empInfoTerName(empInfoTerNameShr? empInfoTerNameShr : '');
                // get Shared from A
                let empInfoTerList = getShared('KNR002D_empInfoTerList');
               
                if(empInfoTerList){                   
                   let desCopyTempList = [];
                    for(let e of empInfoTerList){
                        if(e.empInfoTerCode != self.empInfoTerCode())
                            desCopyTempList.push(new EmpInfoTerminal(e.empInfoTerCode, e.empInfoTerName, e.modelEmpInfoTerName, e.workLocationName));
                    };
                    //desCopyTempList = self.fillBlankRecord(desCopyTempList, 5);
                    self.destinationCopyList(desCopyTempList);
                    self.bindDestinationCopyList();   
                }else{
                    //self.currentCodeList = self.isCancel ? self.selectableCodeList : getShared('KNR002D_currentCodeList');
                    if(self.empInfoTerCode() == ''){
                        self.empInfoTerCode('0001');
                        self.empInfoTerName("isn't the shared");
                    }
                    service.getDestinationCopyList(self.empInfoTerCode()).done(data => {
                        if(!data){
                            //do something
                        } else {
                            let desCopyTempList = [];
                            // if(!self.currentCodeList){
                            //     for(let item of data){
                            //         let desCopyTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, self.getModelName(item.modelEmpInfoTer), item.workLocationName);
                            //         desCopyTempList.push(desCopyTemp);
                            //     } 
                            // } else {
                            //     for(let item of data){                             
                            //         let desCopyTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, self.getModelName(item.modelEmpInfoTer), item.workLocationName);
                            //         desCopyTemp.availability = false; 
                            //         _.forEach(self.currentCodeList, e => {
                            //             if(desCopyTemp.empInfoTerCode == e.code){
                            //                 desCopyTemp.availability = true; 
                            //                 return;
                            //             }
                            //         });
                            //         desCopyTempList.push(desCopyTemp);
                            //     }  
                            // }       
                            for(let item of data){
                                let desCopyTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, self.getModelName(item.modelEmpInfoTer), item.workLocationName);
                                desCopyTempList.push(desCopyTemp);
                            }   
                           // desCopyTempList = self.fillBlankRecord(desCopyTempList, 5);
                            self.destinationCopyList(desCopyTempList);
                            self.bindDestinationCopyList();
                        }                       
                    });
                }

                $('#D5_1').focus();
                blockUI.clear();   																			
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
                        { headerText: getText('KNR002_107'), key: 'modelEmpInfoTerName', dataType: 'string', width: 70},
                        { headerText: getText('KNR002_108'), key: 'workLocationName', dataType: 'string', width: 200},
                    ],
                    features: [{
                        name: 'Selection',
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
                //self.isCancel = false;
                // Process
                nts.uk.ui.windows.close();
            }
            /**
             * D5_2
             * キャンセルボタン
             */
            private cancel_Dialog(): any {
                let self = this;
                //self.isCancel = true;
                self.selectableCodeList = [];
                _.forEach(self.destinationCopyList(), e => {
                    if(e.availability)
                    self.selectableCodeList.push(new ItemModel(e.empInfoTerCode));
                });             
                setShared('KNR002D_selectableCodeList', self.selectableCodeList);
                nts.uk.ui.windows.close();
            }
            /**
             * get Model Name
             */
            private getModelName(modelEmpInfoTer: Number): string{
                switch(modelEmpInfoTer){
                    case 7: return 'NRL_1';
                    case 8: return 'NRL_2';
                    case 9: return 'NRL_M';
                    default : return '';
                }	
            }
            /**
             * fill blank record to Grid
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
        class ItemModel{
            code: string;
            constructor(code: string){
                this.code = code;
            }
        }
    }
}