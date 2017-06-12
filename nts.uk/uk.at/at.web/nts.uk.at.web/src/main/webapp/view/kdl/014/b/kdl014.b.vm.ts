module kdl014.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '社員CD', key: 'employeeCd', width: 80 },
                { headerText: '社員名', key: 'employeeName', width: 80 },
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
            let startDate: string = '20160808';
            let endDate: string = '20170808';
            let employeeCode: string = '00003';
            let lstCardNumber: Array<string> = [];
            //let lstPersonID: Array<string> =['3C3F6EA0-5F1A-4477-844F-9A5DB849D538','50013D7B-24B1-4877-A37B-F2A83A0126F8','909F2111-7506-48C7-9A5D-B399CDDDC7F3'];
            //get list Card Number
//            service.getPersonIdByEmployee(employeeCode).done(function(employeeInfo: any) {
//                //console.log(employeeInfo.personId);
//                if (employeeInfo !== undefined) {
//                    let personId: string = employeeInfo.personId;
//                    //get list Card Number
//                    service.getStampNumberByPersonId(personId).done(function(lstStampNumber: any) {
//                        _.forEach(lstStampNumber, function(value) {
//                            lstCardNumber.push(value.cardNumber.toString());
//                        };
//                        //get list Stamp 
//                        service.getStampByCode(lstCardNumber, startDate, endDate).done(function(lstStamp: any) {
//                            console.log(lstStamp);
//                            //TODO
//                            if (lstStamp.length > 0) {
//                                _.forEach(lstStamp, function(item) {
//                                    self.items.push(new StampModel('0001','ducpm',item.date, _.padStart(nts.uk.time.parseTime(item.attendanceTime, true).format(), 5, '0'), item.stampReasonName, item.stampAtrName, item.stampMethodName, item.workLocationName, item.stampCombinationName));
//                                });
//                            }
//
//                            dfd.resolve();
//                        }).fail(function(res) {
//                            dfd.reject();
//                        });
//                        dfd.resolve();
//                    }).fail(function(res) {
//                        nts.uk.ui.dialog.alertError(res.message);
//                        dfd.reject();
//                    });
//                }
//                dfd.resolve();
//            }).fail(function(res) {
//                dfd.reject();
//            });
            
            //Get list stamp number from list person Id
//            service.getStampNumberByListPersonId(lstPersonID).done(function(lstStampNumber: any) {
//                console.log(lstStampNumber);
//                debugger;
//            }).fail(function(res) {
//                dfd.reject();
//            });
            let lstPersonID: Array<string> = [];
            let lstStampNumber: Array<string> =[];
            service.getListPersonByListEmployee(lstEmployeeCode).done(function(lstPersonId: any) {
                if(lstPersonId.length>0){
                    _.forEach(lstPersonId, function(item){
                        lstPersonID.push(item.personId);    
                    });  
                    console.log(lstPersonID);
                    //Get list STAMP NUMBER from PersonID 
                    service.getStampNumberByListPersonId(lstPersonID).done(function(StampNumbers: any) {
                        if(StampNumbers.length>0){
                            _.forEach(StampNumbers, function(i){
                                 lstStampNumber.push(i.cardNumber);
                            });  
                            //Get List Stamp Reference
                              
                        }
                    }).fail(function(res) {
                        dfd.reject();
                    });  
                }
            }).fail(function(res) {
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
        employeeCd:string;
        employeeName:string;
        date: string;
        attendanceTime: string;
        stampReasonName: string;
        stampAtrName: string;
        stampMethodName: string;
        workLocationName: string;
        stampCombinationName: string;
        constructor(employeeCd:string,employeeName:string,date: string, attendanceTime: string, stampReasonName: string, stampAtrName: string, stampMethodName: string, workLocationName: string, stampCombinationName: string) {
            var self = this;
            self.employeeCd = employeeCd;
            self.employeeName = employeeName;
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