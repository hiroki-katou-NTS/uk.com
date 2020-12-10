module knr002.g {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;

    export module viewmodel{
        export class ScreenModel{
            empInfoTerCode: KnockoutObservable<string>;
            empInfoTerName: KnockoutObservable<string>;
            modelEmpInfoTerName: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            
            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable("");
                self.empInfoTerName = ko.observable("");
                self.modelEmpInfoTerName = ko.observable("");
                self.workLocationName = ko.observable("");
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                //load process
                
                $('#G11_1').focus();
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
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
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
        }
        class DestinationCopy{
            availability: boolean;
            empInfoTerCode: string;
            empInfoTerName: string;
            modelEmpInfoTer: string;
            workLocationName: string;
            constructor(empInfoTerCode: string, empInfoTerName: string, modelEmpInfoTer: string, workLocationName: string, availability: boolean){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
                this.availability = availability;
                this.modelEmpInfoTer = modelEmpInfoTer;
                this.workLocationName = workLocationName;
            }
        }
    }
}