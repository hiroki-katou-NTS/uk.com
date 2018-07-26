module nts.uk.at.view.kdp003.c {
    import getText = nts.uk.resource.getText;
    import StampOutputItemSetDto = nts.uk.at.view.kdp003.c.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {

            dataSource: any;
            selectedList: KnockoutObservableArray<any>;
            hiddentOutputEmbossMethod: KnockoutObservable<boolean>;
            hiddentOutputWorkHours: KnockoutObservable<boolean>;
            hiddentOutputSetLocation: KnockoutObservable<boolean>;
            hiddentOutputPosInfor: KnockoutObservable<boolean>;
            hiddentOutputOT: KnockoutObservable<boolean>;
            hiddentOutputNightTime: KnockoutObservable<boolean>;
            hiddentOutputSupportCard: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.hiddentOutputEmbossMethod = ko.observable(false);
                self.hiddentOutputWorkHours = ko.observable(false);
                self.hiddentOutputSetLocation = ko.observable(false);
                self.hiddentOutputPosInfor = ko.observable(false);
                self.hiddentOutputOT = ko.observable(false);
                self.hiddentOutputNightTime = ko.observable(false);
                self.hiddentOutputSupportCard = ko.observable(false);
                self.dataSource = [
                    { "userId": "1", "loginId": "1", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "2", "loginId": "2", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "3", "loginId": "3", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "4", "loginId": "4", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "5", "loginId": "5", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "6", "loginId": "6", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "7", "loginId": "7", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "8", "loginId": "8", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "9", "loginId": "9", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "10", "loginId": "10", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "11", "loginId": "11", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "12", "loginId": "12", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "13", "loginId": "13", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "14", "loginId": "14", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "15", "loginId": "15", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "16", "loginId": "16", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "17", "loginId": "17", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "18", "loginId": "18", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "19", "loginId": "19", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "20", "loginId": "20", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "21", "loginId": "21", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "22", "loginId": "22", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" },
                    { "userId": "23", "loginId": "23", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }, 
                    { "userId": "24", "loginId": "24", "userName": "fakeData", "lockOutDateTime": "2018/07/11", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData", "logType": "fakeData" }
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
                //console.log(evt.type);
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
                service.findStampOutput("01").done((data: StampOutputItemSetDto) => {
                    console.log(data);
                    self.hiddentOutputEmbossMethod(data.outputEmbossMethod);
                    self.hiddentOutputWorkHours(data.outputWorkHours);
                    self.hiddentOutputSetLocation(data.outputSetLocation);
                    self.hiddentOutputPosInfor(data.outputPosInfor);
                    self.hiddentOutputOT(data.outputOT);
                    self.hiddentOutputNightTime(data.outputNightTime);
                    self.hiddentOutputSupportCard(data.outputSupportCard);
                      
                    
                    dfd.resolve();
                }).fail((res: any) => {

                    dfd.reject();
                });
                
                return dfd.promise();
            }

        }
    }


}