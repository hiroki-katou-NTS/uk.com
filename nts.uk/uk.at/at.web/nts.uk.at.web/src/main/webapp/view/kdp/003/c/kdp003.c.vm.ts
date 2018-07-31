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
            widthGrid: KnockoutObservable<string>;
            numberHiddent: number;
            

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
                self.widthGrid = ko.observable('770px');
                self.numberHiddent = 0;
                self.dataSource = [];
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
                 var count : number = 0; 
                service.findStampOutput(self.stampCode).done((data: StampOutputItemSetDto) => {
                    console.log(data);
                    self.hiddentOutputEmbossMethod(data.outputEmbossMethod);
                    
                    self.hiddentOutputWorkHours(data.outputWorkHours);
                    self.hiddentOutputSetLocation(data.outputSetLocation);
                    self.hiddentOutputPosInfor(data.outputPosInfor);
                    self.hiddentOutputOT(data.outputOT);
                    self.hiddentOutputNightTime(data.outputNightTime);
                    self.hiddentOutputSupportCard(data.outputSupportCard);
                    if(data.outputEmbossMethod){
                        count ++;
                    }
                    if(data.outputWorkHours){
                        count ++;
                    }
                    if( data.outputSetLocation){
                        count ++;
                    }
                    if( data.outputPosInfor){
                        count ++;
                    }
                     if( data.outputOT){
                        count ++;
                    }
                     if( data.outputNightTime){
                        count ++;
                    }
                     if( data.outputSupportCard){
                        count ++;
                    }
                    switch(count) { 
                       case 1: { 
                          self.widthGrid("880px"); 
                          break; 
                       } 
                       case 2: { 
                         self.widthGrid("990"); 
                          break; 
                       } 
                       case 3: { 
                         self.widthGrid("1100"); 
                          break; 
                       } 
                       case 4: { 
                         self.widthGrid("1210"); 
                          break; 
                       } 
                       case 5: { 
                         self.widthGrid("1320"); 
                          break; 
                       } 
                       case 6: { 
                         self.widthGrid("1430"); 
                          break; 
                       } 
                       default: { 
                          self.widthGrid("1540"); 
                          break; 
                       } 
}
                    service.exportExcel(self.dataPram).done((data) => {
                     data.forEach(item => {
                      item.date = moment.utc(item.date).format('YYYY/M/DD');
                   });
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
                    
                  data: any = self.dataPram;
                data.lstEmployeeId = [];
                  service.outPutFileExcel(data).done((data1) => {
                    console.log(data1);
                })
                
               
            }

        }
    }


}