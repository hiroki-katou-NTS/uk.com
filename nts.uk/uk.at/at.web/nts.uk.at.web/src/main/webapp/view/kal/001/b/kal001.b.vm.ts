module nts.uk.at.view.kal001.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //table
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            //data
            listAlarmExtraValueWorkRecord : KnockoutObservableArray<model.AlarmExtraValueWorkRecord>;
            constructor() {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                self.listAlarmExtraValueWorkRecord = ko.observableArray([]);
                
                for (let i = 1; i < 100; i++) {
                    var temp = new model.AlarmExtraValueWorkRecord(
                        'employeeID ' + i, 
                        'workplaceID' + i, 
                        false,
                        'comment '+i,
                        'alarmValueMessage'+ i,
                        '2018/01/'+(i%30+1),    
                        'alarmItem '+i,
                        'employeeCode'+i, 
                        'employeeName'+i,
                        i);
                    self.listAlarmExtraValueWorkRecord.push(temp);
                }
                
                self.columns = ko.observableArray([
                    { headerText: '', key: 'GUID', width: 1 ,hidden :true },
                    { headerText: getText('KAL001_20'), key: 'workplaceID', width: 100, enableTooltip : true },
                    { headerText: getText('KAL001_13'), key: 'employeeCode', width: 100 },
                    { headerText: getText('KAL001_14'), key: 'employeeName', width: 150 },
                    { headerText: getText('KAL001_15'), key: 'alarmValueDate', width: 100, isDateColumn: true, format: 'YYYY/MM/DD' },
                    { headerText: getText('KAL001_16'), key: 'category', width: 60},
                    { headerText: getText('KAL001_17'), key: 'alarmItem', width: 150 },
                    { headerText: getText('KAL001_18'), key: 'alarmValueMessage', width: 150 },
                    { headerText: getText('KAL001_19'), key: 'comment', width: 200 }
                ]);
            $("#grid").igGrid({ 
                    width: '1020px',
                    height: '400px',
                    dataSource: self.listAlarmExtraValueWorkRecord(),
                    primaryKey: 'GUID',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: self.columns(), 
                    features: [{ name: 'Paging', type: 'local',pageSize: 10,style: "popover" }],
                    avgRowHeight: "26px",
                    enableTooltip : true
                    });
            }

            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                // get all CaseSpecExeContent

                dfd.resolve();

                return dfd.promise();
            }//end start page

            printErrorByCsv(): void {
                let self = this;
                service.saveAsCsv(self.listAlarmExtraValueWorkRecord());
            }
            
            printErrorByExcel(): void {
                let self = this;
                service.saveAsExcel(self.listAlarmExtraValueWorkRecord());
            }
            
            closeDialog(){
                 nts.uk.ui.windows.close();
            }


        }//end screenModel
    }//end viewmodel

    //module model
    export module model {

        //class AlarmExtraValueWorkRecord
        export class AlarmExtraValueWorkRecord {
            GUID : string;
            employeeID: string;
            workplaceID: string;
            isClassification: boolean;
            comment: string;
            alarmValueMessage: string;
            alarmValueDate: string;
            alarmItem: string;
            employeeCode: string;
            employeeName: string;
            category: number;
            constructor(employeeID: string,
                workplaceID: string,
                isClassification: boolean,
                comment: string,
                alarmValueMessage: string,
                alarmValueDate: string,
                alarmItem: string,
                employeeCode: string,
                employeeName: string,
                category: number) {
                this.GUID = nts.uk.util.randomId();
                this.employeeID = employeeID;
                this.workplaceID = workplaceID;
                this.isClassification = isClassification;
                this.comment = comment;
                this.alarmValueMessage = alarmValueMessage;
                this.alarmValueDate = alarmValueDate;
                this.alarmItem = alarmItem;
                this.employeeCode = employeeCode;
                this.employeeName = employeeName;
                this.category = category;
            }

        }//end class AlarmExtraValueWorkRecord


    }//end module model

}//end module