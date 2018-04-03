module nts.uk.at.view.kal001.c {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //table
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            //data
            listEmployee: KnockoutObservableArray<model.EmployeeSendEmail>;
            isSendToMe: KnockoutObservable<boolean>;
            isSendToManager: KnockoutObservable<boolean>;
            shareEmployee : KnockoutObservableArray<model.ShareEmployee>;
            
            constructor() {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                self.listEmployee = ko.observableArray([]);
                self.isSendToMe = ko.observable(true);
                self.isSendToManager = ko.observable(true);
                for (let i = 1; i < 100; i++) {
                    let temp = new model.EmployeeSendEmail(
                        false,
                        false,
                        'workplace ' + i,
                        'workplaceName' +'i',
                        'employeeId' +i,
                        'employeeCode' + i,
                        'employeeName' + i
                    );
                    self.listEmployee.push(temp);
                }

                self.columns = ko.observableArray([
                    { headerText: '', key: 'GUID', width: 1, hidden: true },
                    { headerText: getText('KAL001_23'),  dataType: 'boolean', key: 'isSendToMe', showHeaderCheckbox: true, width: 100, ntsControl: 'isSendToMe' },
                    { headerText: getText('KAL001_24'),  dataType: 'boolean',  key: 'isSendToManager', showHeaderCheckbox: true, width: 100, ntsControl: 'isSendToManager' },
                    { headerText: getText('KAL001_27'), key: 'workplaceId', width: 150 },
                    { headerText: getText('KAL001_25'), key: 'employeeCode', width: 300 },
                    { headerText: getText('KAL001_26'), key: 'employeeName', width: 150 }
                ]);
                $("#grid").ntsGrid({
                    width: '650px',
                    height: '350px',
                    dataSource: self.listEmployee(),
                    primaryKey: 'GUID',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: self.columns(),
                    features: [{ name: 'Resizing' },
                        {
                            name: 'Selection', 
                            mode: 'row',
                            multipleSelection: false
                        }
                    ],
                    avgRowHeight: "26px",
                    ntsControls: [
                        { name: 'isSendToMe', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        { name: 'isSendToManager', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    ]
                });

            }

            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any> {
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
                 console.log(" tu dep trai");                 
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
            constructor(isSendToMe: boolean,
                isSendToManager: boolean,
                workplaceId: string,
                workplaceName: string,
                employeeId: string, 
                employeeCode: string,
                employeeName: string) {
                this.GUID = nts.uk.util.randomId();
                this.isSendToMe = ko.observable(isSendToMe);
                this.isSendToManager = ko.observable( isSendToManager);
                this.workplaceId = workplaceId;
                this.workplaceName = workplaceName;
                this.employeeId = employeeId;
                this.employeeCode = employeeCode;
                this.employeeName = employeeName;
            }

        }
        
        export class ShareEmployee{
            employeeId: string;
            workplaceId: string;
            workplaceName: string;
            constructor(employeeId: string, workplaceId, workplaceName: string){
                this.employeeId = employeeId;
                this.workplaceId = workplaceId;
                this.workplaceName= workplaceName;
            }    
        }

    }//end module model

}//end module