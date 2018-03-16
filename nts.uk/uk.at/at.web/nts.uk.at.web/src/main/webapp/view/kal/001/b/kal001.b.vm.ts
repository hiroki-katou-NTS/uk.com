module nts.uk.at.view.kal001.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {

            columns: KnockoutObservableArray<any>;
            currentSelectedRow: KnockoutObservable<any>;

            dataSource : Array<model.ValueExtractAlarmDto>=[];
            constructor() {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                
                self.columns = ko.observableArray([
                    { headerText: '', key: 'gUID', width: 1 ,hidden :true },
                    { headerText: getText('KAL001_20'), key: 'workplaceName', width: 100 },
                    { headerText: getText('KAL001_13'), key: 'employeeCode', width: 150 },
                    { headerText: getText('KAL001_14'), key: 'employeeName', width: 150 },
                    { headerText: getText('KAL001_15'), key: 'alarmValueDate', width: 100},
                    { headerText: getText('KAL001_16'), key: 'categoryName', width: 120},
                    { headerText: getText('KAL001_17'), key: 'alarmItem', width: 150 },
                    { headerText: getText('KAL001_18'), key: 'alarmValueMessage', width: 150 },
                    { headerText: getText('KAL001_19'), key: 'comment', width: 200 }
                ]);

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                
                for (let i = 1; i < 100; i++) {
                    let temp = {
                                gUID: '' +i,
                                workplaceID : "workplaceID" +i,
                                hierarchyCd : "hierarchyCd"+i,
                                workplaceName : "workplaceName"+i,
                                employeeID : "employeeID"+i,
                                employeeCode : "employeeCode"+i,
                                employeeName : "employeeName" +i,
                                alarmValueDate : "alarmValueDate" +i,
                                category : i,
                                categoryName: "category" +i,
                                alarmItem : "alarmItem" +i,
                                alarmValueMessage : "alarmValueMessage" +i,
                                comment : "comment" +i,                                
                              };
                    self.dataSource.push(temp);
                }
                
                $("#grid").igGrid({ 
                        height: '500px',
                        dataSource: self.dataSource,
                        primaryKey: 'GUID',
                        columns: self.columns(), 
                        features: [
                            { name: 'Paging', type: 'local', pageSize: 15 },
                            {
                              name: "Tooltips",
                              columnSettings: [ 
                                                { columnKey: "workplaceName", allowTooltips: true }
                                              ]
                            }
                        ],
                        enableTooltip : true
                        });
                                

                dfd.resolve();

                return dfd.promise();
            }

            exportExcel(): void {
                let self = this;
                service.saveAsExcel(self.dataSource);
            }
            
            sendEmail(): void {
                let self = this;
                
            }
            
            closeDialog(){
                 nts.uk.ui.windows.close();
            }


        }
    }
    
            



    export module model {

        export interface ValueExtractAlarmDto{
            gUID: string;
            workplaceID : string;
            hierarchyCd : string;
            workplaceName : string;
            employeeID : string;
            employeeCode : string;
            employeeName : string;
            alarmValueDate : string;
            category : number;
            categoryName: string;
            alarmItem : string;
            alarmValueMessage : string;
            comment : string;            
        }                
    }
    

}