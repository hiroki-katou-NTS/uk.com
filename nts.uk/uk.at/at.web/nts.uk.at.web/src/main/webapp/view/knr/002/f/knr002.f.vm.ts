module knr002.f {
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
            modelEmpInfoTer: KnockoutObservable<number>;
            modelEmpInfoTerName: KnockoutObservable<string>;
            lastSuccessDate: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            recoveryTargetList: KnockoutObservableArray<any>;
            selectedList: KnockoutObservableArray<string>;
           
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable("");
                self.empInfoTerName = ko.observable("");
                self.modelEmpInfoTer = ko.observable(0);
                self.modelEmpInfoTerName = ko.observable("");
                self.lastSuccessDate = ko.observable("");
                self.workLocationName = ko.observable("");
                self.recoveryTargetList = ko.observableArray<EmpInfoTerminal>([]);
                self.selectedList = ko.observableArray<string>([]);       
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                // get Shared from E
                self.empInfoTerCode(getShared('KNR002F_empInfoTerCode'));
                self.empInfoTerName(getShared('KNR002F_empInfoTerName'));
                self.modelEmpInfoTer(getShared('KNR002F_modelEmpInfoTer'));
                self.lastSuccessDate(getShared('KNR002F_lastSuccessDate'));
                //get Share from A
                let empInfoTerList = getShared('KNR002F_empInfoTerList');
                
                if(!self.modelEmpInfoTer()){
                    self.modelEmpInfoTer(7);
                    self.empInfoTerCode("0001");
                    self.empInfoTerName("isn't the shared name");
                    self.modelEmpInfoTerName("wrong");
                    self.lastSuccessDate("9999/99/99 99:99:99");
                }
                         
                service.getRecoveryTargeTertList(self.modelEmpInfoTer()).done((data)=>{
                    if(!data || data.leng <= 0 ){
                        self.recoveryTargetList([]);
                        self.bindDestinationCopyList();
                    }else{
                        let recoveryTargetTempList = [];                                         
                        if(empInfoTerList){
                            let keyMap: any = {};
                            _.forEach(empInfoTerList, e => {
                                keyMap[e.empInfoTerCode] = e;
                            }); 
                            for(let item of data){
                                let recoveryTargetTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, self.getModelName(self.modelEmpInfoTer()));                         
                                let currentItem = keyMap[item.empInfoTerCode];                           
                                if (currentItem) {
                                    recoveryTargetTemp.workLocationName = currentItem.workLocationName;
                                }                               
                                recoveryTargetTempList.push(recoveryTargetTemp);
                            }
                        }else{
                            for(let item of data){
                                let recoveryTargetTemp = new EmpInfoTerminal(item.empInfoTerCode, item.empInfoTerName, self.getModelName(self.modelEmpInfoTer()));                         
                                recoveryTargetTempList.push(recoveryTargetTemp);
                            }
                        }
                        self.recoveryTargetList(recoveryTargetTempList);
                        self.bindDestinationCopyList();
                }
            });
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            
            /**
             * bind table D3_2
             */
            private bindDestinationCopyList(): void{
                let self = this;
                $("#F4").ntsGrid({
                    height: 169,
                    dataSource: self.recoveryTargetList(),
                    primaryKey: 'empInfoTerCode',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    hidePrimaryKey: false,
                    columns: [
                        { headerText: '', key: 'availability', dataType: 'boolean', width: ' 35px', ntsControl: 'Checkbox' },
                        { headerText: getText('KNR002_238'), key: 'empInfoTerCode', dataType: 'string', width: 60},
                        { headerText: getText('KNR002_239'), key: 'empInfoTerName', dataType: 'string', width: 200},
                        { headerText: getText('KNR002_240'), key: 'modelEmpInfoTer', dataType: 'string', width: 70},
                        { headerText: getText('KNR002_241'), key: 'workLocationName', dataType: 'string', width: 200},
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
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
            /**
             * get Model Name
             */
            private getModelName(modelEmpInfoTer: Number): string{
                switch(modelEmpInfoTer){
                    case 7: return getText('KNR002_251');
                    case 8: return getText('KNR002_252');
                    case 9: return getText('KNR002_253');
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
                        let displayLog = new EmpInfoTerminal(" ", " ", " ");
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
            constructor(empInfoTerCode: string, empInfoTerName: string, modelEmpInfoTerName: string){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.modelEmpInfoTerName = modelEmpInfoTerName;
                this.availability = false;
            }
        }
    }
}