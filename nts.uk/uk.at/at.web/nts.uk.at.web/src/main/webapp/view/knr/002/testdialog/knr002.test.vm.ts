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
            empInfoTerList_D: KnockoutObservableArray<any>;
            //currentCodeList_D: KnockoutObservableArray<any>;
            //F_Dialog
            empInfoTerCode_F: KnockoutObservable<string>;
            empInfoTerName_F: KnockoutObservable<string>;
            modelEmpInfoTer_F: KnockoutObservable<number>;


            
            constructor(){
                var self = this;
                self.sharedContent = ko.observable("The Shared Content is: \nScreen: ");
                self.isMulti = true;
                //B_Dialog
                self.empInfoTerCode_B = ko.observable("0002");
                self.empInfoTerName_B = ko.observable("Name 2B_Shared");
                self.modelEmpInfoTer_B = ko.observable("NRL-2B");
                self.workLocationName_B = ko.observable("Work Location 2B");
                self.lastSuccessDate_B = ko.observable("2020/12/12 12:12:12");
                self.status = ko.observable("Normal");
                //D_Dialog
                self.empInfoTerCode_D = ko.observable("0002");
                self.empInfoTerName_D = ko.observable("Name 2D_Shared");
                self.empInfoTerList_D = ko.observableArray([new EmpInfoTerminal("0001", "Name1", "NRL-1", "WLN 1"), 
                                                            new EmpInfoTerminal("0002", "Name2", "NRL-2", "WLN 2"), 
                                                            new EmpInfoTerminal("0003", "Name3", "NRL-3", "WLN 3"), 
                                                            new EmpInfoTerminal("0004", "Name4", "NRL-4", "WLN 4"),
                                                            new EmpInfoTerminal("0005", "Name5", "NRL-5", "WLN 5")]);
                // self.currentCodeList_D = ko.observableArray([new ItemModel("0001"), 
                //                                              new ItemModel("0002"),
                //                                              new ItemModel("0005"),
                //                                              new ItemModel("0004")]);

                //F_Dialog
                self.empInfoTerCode_F = ko.observable("0002");
                self.empInfoTerName_F = ko.observable("Name 2F_Shared");
                self.modelEmpInfoTer_F = ko.observable(9);
            }

            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
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
                //setShared From C
                setShared('KNR002D_empInfoTerCode', self.empInfoTerCode_D());
                setShared('KNR002D_empInfoTerName', self.empInfoTerName_D());
                //setShare From A
                setShared('KNR002D_empInfoTerList', self.empInfoTerList_D());
                //setShared('KNR002D_currentCodeList', self.currentCodeList_D());

                modal('/view/knr/002/d/index.xhtml', { title: 'D_Screen', }).onClosed(() => {
                    // let getSharedLst = getShared('KNR002D_selectableCodeList');                  
                    // if(getSharedLst)
                    //     self.currentCodeList_D(getSharedLst);
                    blockUI.clear();
                });
            }

            /**
             * 
             */
            private test_F_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002F_empInfoTerCode', self.empInfoTerCode_F());
                setShared('KNR002F_empInfoTerName', self.empInfoTerName_F());
                setShared('KNR002F_modelEmpInfoTer', self.modelEmpInfoTer_F());
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