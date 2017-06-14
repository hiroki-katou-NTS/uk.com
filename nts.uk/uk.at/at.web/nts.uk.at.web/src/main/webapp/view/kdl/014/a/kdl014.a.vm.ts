module kdl014.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '日付', key: 'date', width: 120 },
                { headerText: '打刻時間', key: 'attendanceTime', width: 80 },
                { headerText: '打刻理由', key: 'stampReasonName', width: 80 },
                { headerText: '打刻区分', key: 'stampAtrName', width: 80 },
                { headerText: '打刻方法', key: 'stampMethodName', width: 100 },
                { headerText: '打刻場所', key: 'workLocationName', width: 80 },
                { headerText: '組み合わせ区分', key: 'stampCombinationName', width: 100 }
            ]);
            self.currentCode = ko.observable();
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            // Get list stamp
            let cardNumber: string = '00000000000000000001';
            let startDate: string = '20160808';
            let endDate: string = '20170808';
            service.getStampByCode(cardNumber, startDate, endDate).done(function(lstStamp: any) {

                console.log(lstStamp);
                //console.log(_.padStart(nts.uk.time.parseTime(480, true).format(),5,'0'));
                //TODO
                if (lstStamp.length > 0) {
                    _.forEach(lstStamp, function(item) {
                        self.items.push(new StampModel(item.date, _.padStart(nts.uk.time.parseTime(item.attendanceTime,true).format(),5,'0'), item.stampReasonName, item.stampAtrName, item.stampMethodName, item.workLocationName, item.stampCombinationName));
                    }); 
                }
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            //get PersonId
            let employeeCode:string ='00003       ';
            service.getPersonByEmpCode(employeeCode).done(function(employeeInfo: any) {

                console.log(employeeInfo);
                
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            
            return dfd.promise();
        }

        /**Close function*/
        close() {
            nts.uk.ui.windows.close();
        }
    }


    class StampModel {
        date: string;
        attendanceTime: string;
        stampReasonName: string;
        stampAtrName: string;
        stampMethodName: string;
        workLocationName: string;
        stampCombinationName: string;
        constructor(date: string, attendanceTime: string, stampReasonName: string, stampAtrName: string, stampMethodName: string, workLocationName: string, stampCombinationName: string) {
            var self = this;
            self.date = date;
            self.attendanceTime = attendanceTime;
            self.stampReasonName = stampReasonName;
            self.stampAtrName = stampAtrName;
            self.stampMethodName = stampMethodName;
            self.workLocationName = workLocationName;
            self.stampCombinationName = stampCombinationName;
        }
    }
}