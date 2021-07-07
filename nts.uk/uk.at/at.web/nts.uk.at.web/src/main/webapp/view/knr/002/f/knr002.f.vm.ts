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
            selectedList: Array<String>;
           
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable("");
                self.empInfoTerName = ko.observable("");
                self.modelEmpInfoTer = ko.observable(0);
                self.modelEmpInfoTerName = ko.observable("");
                self.lastSuccessDate = ko.observable("");
                self.workLocationName = ko.observable("");
                self.recoveryTargetList = ko.observableArray<EmpInfoTerminal>([]);
                self.selectedList = [];    
                  
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
                const sharedData = getShared('KNR002E_share');               
                self.empInfoTerCode(sharedData.empInfoTerCode);
                self.empInfoTerName(sharedData.empInfoTerName);
                self.modelEmpInfoTer(sharedData.modelEmpInfoTer);
                self.modelEmpInfoTerName(self.getModelName(self.modelEmpInfoTer()));
                self.lastSuccessDate(sharedData.backupDate);

                //get Share from A
                let empInfoTerList = getShared('KNR002_empInfoTerList');
                
                if(self.modelEmpInfoTer() === undefined || self.modelEmpInfoTer() === 0){
                    self.empInfoTerCode('');
                    self.empInfoTerName('');
                    self.modelEmpInfoTer(0);
                    self.lastSuccessDate('');
                    self.workLocationName('');
                    self.recoveryTargetList([]);
                    self.selectedList = [];
                    self.bindDestinationCopyList(); 
                } else {             
                    service.getRecoveryTargetList(self.modelEmpInfoTer()).done((data)=>{
                        if(!data || data.leng <= 0 ){
                            self.recoveryTargetList([]); 
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
                        }
                        self.bindDestinationCopyList(); 
                    });											
                }
                blockUI.clear(); 
                $('#F6_1').focus();																			
                dfd.resolve();											
                return dfd.promise();
            }
            
            /**
             * bind table F4
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
                        { headerText: getText('KNR002_238'), key: 'empInfoTerCode', dataType: 'string', width: 65},
                        { headerText: getText('KNR002_239'), key: 'empInfoTerName', dataType: 'string', width: 200},
                        { headerText: getText('KNR002_240'), key: 'modelEmpInfoTerName', dataType: 'string', width: 120},
                        { headerText: getText('KNR002_241'), key: 'workLocationName', dataType: 'string', width: 200},
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
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                const vm = this;

                setShared('KNR002E_cancel', true);
                nts.uk.ui.windows.close();
            }
            /**
             * click btn F6_1 復旧ボタン
             */
            private recovery(): void {
                let self = this;

                setShared('KNR002E_cancel', false);
                self.selectedList = [];             
                _.forEach(self.recoveryTargetList(), e => {
                    if(e.availability)
                    self.selectedList.push(e.empInfoTerCode);
                }); 
                if(self.selectedList.length <= 0){
                    dialog.error({messageId: "Msg_2035"}).then(()=>{
                        return;
                    }); 
                } else {
                    let x = `${self.empInfoTerCode()}`;
                    let y = '';
                    _.forEach(self.selectedList, (e => {
                        y += `「${e}」`
                    }));
                    dialog.confirm({ messageId: "Msg_2020", messageParams: [x, y.substring(1, y.length - 1)] }).ifYes(() => {
                        blockUI.invisible();
                        service.recovery(self.empInfoTerCode(), self.selectedList).done(() => {

                            let updateRemoteInput = {
                                listEmpTerminalCode: self.selectedList
                            }

                            service.updateRemoteSettings(updateRemoteInput).done(() => {});

                            nts.uk.ui.windows.close();
                            }).fail((err) => {
                                dialog.error({ messageId: err.messageId });
                            }).always(() => {
                                blockUI.clear();
                            });
                    }).ifNo(function() {
                        blockUI.clear();
                    });
                }   
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