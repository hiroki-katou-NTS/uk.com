module knr002.test {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal =  nts.uk.ui.windows.sub.modal;



    export module viewmodel{
        export class ScreenModel{
       
            isMulti: boolean;
            sharedContent: KnockoutObservable<string>;

            //B_Dialog
            empInfoTerCode_B: KnockoutObservable<string>;
            empInfoTerName_B: KnockoutObservable<string>;
            modelEmpInfoTer_B: KnockoutObservable<string>;
            workLocationName_B: KnockoutObservable<string>;
            lastSuccessDate_B: KnockoutObservable<string>;
            status: KnockoutObservable<string>;
            //D_Dialog
            empInfoTerCode_D: KnockoutObservable<string>;
            empInfoTerName_D: KnockoutObservable<string>;
            currentCodeList_D: KnockoutObservableArray<any>;

            
            constructor(){
                var self = this;
                self.sharedContent = ko.observable("The Shared Content is: \nScreen: ");
                self.isMulti = true;
                //B_Dialog
                self.empInfoTerCode_B = ko.observable("0002");
                self.empInfoTerName_B = ko.observable("Name 2_Shared");
                self.modelEmpInfoTer_B = ko.observable("NRL-2");
                self.workLocationName_B = ko.observable("Work Location 2");
                self.lastSuccessDate_B = ko.observable("2020/12/12 12:12:12");
                self.status = ko.observable("Normal");
                //D_Dialog
                self.empInfoTerCode_D = ko.observable("0002");
                self.empInfoTerName_D = ko.observable("Name 2_Shared");
                self.currentCodeList_D = ko.observableArray([new ItemModel("0001"), 
                                                             new ItemModel("0002"),
                                                             new ItemModel("0005"),
                                                             new ItemModel("0004")]);
            }

            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                

                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }    
            /**
             * 
             */
            private test_B_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002B_empInfoTerCode', self.empInfoTerCode_B());
                setShared('KNR002B_empInfoTerName', self.empInfoTerName_B());
                setShared('KNR002B_modelEmpInfoTer', self.modelEmpInfoTer_B());
                setShared('KNR002B_workLocationName', self.workLocationName_B());
                setShared('KNR002B_lastSuccessDate', self.lastSuccessDate_B());
                setShared('KNR002B_status', self.status());
                modal('/view/knr/002/b/index.xhtml', { title: 'B_Screen', }).onClosed(() => {
                    // Do nothing
                    blockUI.clear();
                });
            }
            
            /**
             * 
             */
            private test_D_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002D_empInfoTerCode', self.empInfoTerCode_D());
                setShared('KNR002D_empInfoTerName', self.empInfoTerName_D());
                setShared('KNR002D_currentCodeList', self.currentCodeList_D());

                modal('/view/knr/002/d/index.xhtml', { title: 'D_Screen', }).onClosed(() => {
                    let getSharedLst = getShared('KNR002D_selectableCodeList');                  
                    if(getSharedLst)
                        self.currentCodeList_D(getSharedLst);
                    blockUI.clear();
                });
            }

            /**
             * 
             */
            private test_F_Dialog(): void{
                modal('/view/knr/002/f/index.xhtml', { title: 'F_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

            /**
             * 
             */
            private test_G_Dialog(): void{
                modal('/view/knr/002/g/index.xhtml', { title: 'G_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
            /**
             * 
             */
            private test_H_Dialog(): void{
                modal('/view/knr/002/h/index.xhtml', { title: 'H_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }

              /**
             * 
             */
            private test_K_Dialog(): void{
                modal('/view/knr/002/k/index.xhtml', { title: 'K_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
        }
        class ItemModel {
            code: string;
            constructor(code: string) {
                this.code = code;
            }
        }       
    }
}