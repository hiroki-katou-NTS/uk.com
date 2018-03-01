module nts.uk.at.view.kal001.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {

            columns: KnockoutObservableArray<any>;
            currentSelectedRow: KnockoutObservable<any>;

            dataSource : KnockoutObservableArray<model.ExtractAlarmData>;
            constructor() {
                let self = this;
                self.currentSelectedRow = ko.observable(null);
                self.dataSource = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: '', key: 'GUID', width: 1 ,hidden :true },
                    { headerText: getText('KAL001_20'), key: 'workplaceName', width: 100 },
                    { headerText: getText('KAL001_13'), key: 'employeeCode', width: 150 },
                    { headerText: getText('KAL001_14'), key: 'employeeName', width: 150 },
                    { headerText: getText('KAL001_15'), key: 'alarmValueDate', width: 100, isDateColumn: true, format: 'YYYY/MM/DD' },
                    { headerText: getText('KAL001_16'), key: 'category', width: 120},
                    { headerText: getText('KAL001_17'), key: 'alarmItem', width: 150 },
                    { headerText: getText('KAL001_18'), key: 'alarmValueMessage', width: 150 },
                    { headerText: getText('KAL001_19'), key: 'comment', width: 200 }
                ]);

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                
                for (let i = 1; i < 100; i++) {
                    let temp = new model.ExtractAlarmData({
                                    workplaceName: "workplaceName" +i,
                                    employeeID: "employeeId" +i,
                                    employeeCode: "employeeCode" +i,
                                    employeeName: "employeeName" +i,
                                    alarmValueDate: '2018/01/'+(i%30+1),
                                    category: "category" +i,
                                    alarmItem: "alarm pattern" +i,            
                                    alarmValueMessage: "message" +i,
                                    comment: "comment" +i,      
                                });
                    self.dataSource.push(temp);
                }
                
                $("#grid").igGrid({ 
                        height: '500px',
                        dataSource: self.dataSource(),
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
                service.saveAsExcel(self.dataSource());
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
        export class ExtractAlarmData {
            GUID : string;
            employeeID: string;
            workplaceName: string;
            comment: string;
            alarmValueMessage: string;
            alarmValueDate: string;
            alarmItem: string;
            employeeCode: string;
            employeeName: string;
            category: string;
            constructor(dto: service.ExtractAlarmDto ) {
                this.GUID = nts.uk.util.randomId();
                this.employeeID = dto.employeeID;
                this.workplaceName = dto.workplaceName;
                this.comment = dto.comment;
                this.alarmValueMessage = dto.alarmValueMessage;
                this.alarmValueDate = dto.alarmValueDate;
                this.alarmItem = dto.alarmItem;
                this.employeeCode = dto.employeeCode;
                this.employeeName = dto.employeeName;
                this.category = dto.category;
            }
        }
        
        
    }

}