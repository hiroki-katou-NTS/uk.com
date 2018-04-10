module nts.uk.at.view.kal001.c {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //table
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            //data
            listEmployee: KnockoutObservableArray<modeldto.EmployeeSendEmail> = ko.observableArray([]);;
            shareEmployees : KnockoutObservableArray<modeldto.ShareEmployee> 
            isSendToMe: KnockoutObservable<boolean>;
            isSendToManager: KnockoutObservable<boolean>;
            constructor( shareEmployees: Array<modeldto.ShareEmployee>) {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                self.shareEmployees  = ko.observableArray(shareEmployees);
                self.isSendToMe = ko.observable(true);
                self.isSendToManager = ko.observable(true);                                              
            }

            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any>  {
                let self = this;
                let dfd = $.Deferred(); 
                service.getEmployeeSendEmail(self.shareEmployees()).done((listEmployeeDto: Array<modeldto.EmployeeDto>)=>{
                    self.listEmployee(_.map(listEmployeeDto, (e) =>{
                        return new modeldto.EmployeeSendEmail(e);                        
                    }));
                    dfd.resolve();                     
                }).fail((error) =>{
                    nts.uk.ui.dialog.alertError(error);
                });                 

                               
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
    export module modeldto {

        export class EmployeeSendEmail {
            GUID: string;
            isSendToMe:  KnockoutObservable<boolean>;
            isSendToManager: KnockoutObservable<boolean>;
            workplaceId: string;
            workplaceName: string;
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            constructor(e: EmployeeDto ) {
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
        
        export interface EmployeeDto{
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceId: string;
            workplaceName: string;   
        }

        export class ShareEmployee{
            employeeID: string;
            workplaceID: string;
        }
                
    }//end module model

}//end module