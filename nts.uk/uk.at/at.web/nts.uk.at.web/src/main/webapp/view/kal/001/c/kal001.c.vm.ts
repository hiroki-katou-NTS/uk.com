module nts.uk.at.view.kal001.c {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //table
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            //data
            listEmployee: KnockoutObservableArray<model.EmployeeSendEmail> = ko.observableArray([]);;
            shareEmployees : KnockoutObservableArray<model.ShareEmployee> 
            isSendToMe: KnockoutObservable<boolean>;
            isSendToManager: KnockoutObservable<boolean>;
            constructor( shareEmployees: Array<model.ShareEmployee>) {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                self.shareEmployees  = ko.observableArray(shareEmployees);
                self.isSendToMe = ko.observable(true);
                self.isSendToManager = ko.observable(true);
                                
                self.listEmployee(_.map(self.shareEmployees(), (e) =>{
                    return new model.EmployeeSendEmail(e);
                }));                
                self.columns = ko.observableArray([
                    { headerText: '', key: 'GUID', width: 1, hidden: true },
                    { headerText: getText('KAL001_23'),  dataType: 'boolean', key: 'isSendToMe', showHeaderCheckbox: true, width: 100, ntsControl: 'isSendToMe' },
                    { headerText: getText('KAL001_24'),  dataType: 'boolean',  key: 'isSendToManager', showHeaderCheckbox: true, width: 100, ntsControl: 'isSendToManager' },
                    { headerText: getText('KAL001_27'), key: 'workplaceName', width: 170 },
                    { headerText: getText('KAL001_25'), key: 'employeeCode', width: 170 },
                    { headerText: getText('KAL001_26'), key: 'employeeName', width: 170 }
                ]);
                
                $("#grid").ntsGrid({
                    height: '450px',
                    dataSource: self.listEmployee(),
                    primaryKey: 'GUID',
                    virtualization: true,
                    virtualizationMode: 'continuous',                    
                    columns: self.columns(),
                    features: 
                           [                       
                            {
                              name: "Tooltips",
                              columnSettings: [ 
                                                { columnKey: "workplaceName", allowTooltips: true }
                                              ]
                            }                    
                          ],
                    enableTooltip : true,
                    ntsControls: [
                        { name: 'isSendToMe', options: { value: 1, text: '' },  optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        { name: 'isSendToManager', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    ]
                });                


            }

            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any>  {
                let self = this;
                let dfd = $.Deferred();                  
                
                dfd.resolve();
                return dfd.promise();                
            }//end start page 

             closeDialog(){
                 nts.uk.ui.windows.close();
            }
             sendEmail(){
                 let self = this;              
                 return;
            }



        }//end screenModel
    }//end viewmodel  

    //module model
    export module model {

        export class EmployeeSendEmail {
            GUID: string;
            isSendToMe:  KnockoutObservable<boolean>;
            isSendToManager: KnockoutObservable<boolean>;
            workplaceId: string;
            workplaceName: string;
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            constructor(e: ShareEmployee ) {
                this.GUID = nts.uk.util.randomId();
                this.isSendToMe = ko.observable(false);
                this.isSendToManager = ko.observable(false);
                this.workplaceId = e.workplaceId;
                this.workplaceName = e.workplaceName;
                this.employeeId = e.employeeId;
                this.employeeCode = e.employeeCode;
                this.employeeName = e.employeeName;
            }

        }
        
        export class ShareEmployee{
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceId: string;
            workplaceName: string;
            constructor(employeeId: string, employeeCode: string, employeeName: string,  workplaceId, workplaceName: string){
                this.employeeId = employeeId;
                this.employeeCode = employeeCode;
                this.employeeName = employeeName;
                this.workplaceId = workplaceId;
                this.workplaceName= workplaceName;
            }    
        }

    }//end module model

}//end module