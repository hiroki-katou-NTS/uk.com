module knr002.f {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;

    export module viewmodel{
        export class ScreenModel{
			empInfoTerCode: KnockoutObservable<string>;
            empInfoTerName: KnockoutObservable<string>;
            modelEmpInfoTer: KnockoutObservable<string>;
            lastSuccessDate: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            recoveryTargetList: KnockoutObservableArray<RecoveryTarget>;
            selectedList: KnockoutObservableArray<string>;
           
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable("0001");
                self.empInfoTerName = ko.observable("Name 1");
                self.modelEmpInfoTer = ko.observable("NRL-1");
                self.lastSuccessDate = ko.observable("2020/12/12 12:12:12");
                self.workLocationName = ko.observable("WLN 1");
                self.recoveryTargetList = ko.observableArray<RecoveryTarget>([]);
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
                service.getDestinationCopyList(self.empInfoTerCode()).done((data)=>{
                    if(data.length < 0){
                        //do something
                    }else{
                        let recoveryTargetTempList = [];
                        for(let item of data){
                            let recoveryTargetTemp = new RecoveryTarget(item.empInfoTerCode, item.empInfoTerName, self.getModelName(8), item.workLocationName, false);
                            recoveryTargetTempList.push(recoveryTargetTemp);
                        }
                        self.recoveryTargetList(recoveryTargetTempList);
                        self.bindDestinationCopyList();
                        //$("#F4").igGrid("option", "dataSource", self.destinationCopyList());
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
                    height: 168,
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
                    case 7: return 'NRL_1';
                    case 8: return 'NRL_2';
                    case 9: return 'NRL_M';
                    default : return '';
                }	
            }
        }
        class RecoveryTarget{
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTer: string;
            workLocationName: string;
            availability: boolean;
            constructor(empInfoTerCode: string, empInfoTerName: string, modelEmpInfoTer: string, workLocationName: string, availability: boolean){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.modelEmpInfoTer = modelEmpInfoTer;
                this.workLocationName = workLocationName;
                this.availability = availability;
            }
        }
    }
}