module nts.uk.at.view.kdp003.c {
    import getText = nts.uk.resource.getText;
    import StampOutputItemSetDto = nts.uk.at.view.kdp003.c.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {

            dataSource: any;
            stampCode : string;
            dataPram: any;
            selectedList: KnockoutObservableArray<any>;
            hiddentOutputEmbossMethod: KnockoutObservable<boolean>;
            hiddentOutputWorkHours: KnockoutObservable<boolean>;
            hiddentOutputSetLocation: KnockoutObservable<boolean>;
            hiddentOutputPosInfor: KnockoutObservable<boolean>;
            hiddentOutputOT: KnockoutObservable<boolean>;
            hiddentOutputNightTime: KnockoutObservable<boolean>;
            hiddentOutputSupportCard: KnockoutObservable<boolean>;

            constructor(dataShare:any) {
                var self = this;
                self.stampCode = dataShare.outputSetCode
                self.dataPram = dataShare
                self.hiddentOutputEmbossMethod = ko.observable(false);
                self.hiddentOutputWorkHours = ko.observable(false);
                self.hiddentOutputSetLocation = ko.observable(false);
                self.hiddentOutputPosInfor = ko.observable(false);
                self.hiddentOutputOT = ko.observable(false);
                self.hiddentOutputNightTime = ko.observable(false);
                self.hiddentOutputSupportCard = ko.observable(false);
                self.dataSource = [
//                    { "wkpCode": "1", "wkpName": "1", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "2", "wkpName": "2", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "3", "wkpName": "3", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "4", "wkpName": "4", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "5", "wkpName": "5", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "6", "wkpName": "6", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "7", "wkpName": "7", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "8", "wkpName": "8", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "9", "wkpName": "9", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "10", "wkpName": "10", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "11", "wkpName": "11", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "12", "wkpName": "12", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "13", "wkpName": "13", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "14", "wkpName": "14", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "15", "wkpName": "15", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "16", "wkpName": "16", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "17", "wkpName": "17", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "18", "wkpName": "18", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "19", "wkpName": "19", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "20", "wkpName": "20", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "21", "wkpName": "21", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "22", "wkpName": "22", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "23", "wkpName": "23", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" },
//                    { "wkpCode": "24", "wkpName": "24", "empCode": "fakeData", "empName": "2018/07/11", "cardNo": "fakeData", "date": "fakeData", "time": "fakeData", "atdType": "fakeData", "workTimeZone": "fakeData", "installPlace": "fakeData", "localInfor": "fakeData", "otTime": "fakeData", "midnightTime": "fakeData", "supportCard": "fakeData" }
                ];
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    allowFiltering: true,
                    mode: 'row',
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors', enableRowNumbering: true });

            }
            selectionChanged(evt, ui) {
                let self = this;
                var selectedRows = ui.selectedRows;
                var arr = [];
                for (var i = 0; i < selectedRows.length; i++) {
                    arr.push("" + selectedRows[i].id);
                }
                this.selectedList(arr);
            };



            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                service.findStampOutput(self.stampCode).done((data: StampOutputItemSetDto) => {
                    console.log(data);
                    self.hiddentOutputEmbossMethod(data.outputEmbossMethod);
                    self.hiddentOutputWorkHours(data.outputWorkHours);
                    self.hiddentOutputSetLocation(data.outputSetLocation);
                    self.hiddentOutputPosInfor(data.outputPosInfor);
                    self.hiddentOutputOT(data.outputOT);
                    self.hiddentOutputNightTime(data.outputNightTime);
                    self.hiddentOutputSupportCard(data.outputSupportCard);
                    service.exportExcel(self.dataPram).done((data) => {
                      data.forEach(item => {
                        item.date = moment.utc(item.date).format('YYYY/MM/DD');
                    });
                    console.log(data);
                          self.dataSource = data;
                     $("#kdp003-grid").igGrid("dataSourceObject", self.dataSource);
                     $("#kdp003-grid").igGrid("dataBind"); 
                    });

                    dfd.resolve();
                })
                    .fail((res: any) => {

                    dfd.reject();
                });
               
                return dfd.promise();
            }
            
            
            /**
            * Export excel
            */
            public exportExcel(): void {
                let self = this,
                    
                    data: any = {};
                
               
            }

        }
    }


}