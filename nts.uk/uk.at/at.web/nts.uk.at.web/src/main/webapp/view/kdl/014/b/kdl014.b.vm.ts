module kdl014.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        startDate: string;
        endDate: string;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL014_8"), key: 'employeeCd', width: 110 },
                { headerText: nts.uk.resource.getText("KDL014_9"), key: 'employeeName', width: 120 },
                { headerText: nts.uk.resource.getText("KDL014_4"), key: 'date', width: 130 },
                { headerText: nts.uk.resource.getText("KDL014_5"), key: 'attendanceTime', width: 80 },
                { headerText: nts.uk.resource.getText("KDL014_6"), key: 'stampAtrName', width: 80 },
                { headerText: nts.uk.resource.getText("KDL014_11"), key: 'stampMethodName', width: 120 },
                { headerText: nts.uk.resource.getText("KDL014_13"), key: 'stampReasonName', width: 80 },
                { headerText: nts.uk.resource.getText("KDL014_7"), key: 'workLocationName', width: 170 },
                { headerText: nts.uk.resource.getText("KDL014_12"), key: 'stampCombinationName', width: 100 }
            ]);
            startDate = '';
            endDate = '';
            $("#igGridStamp").igGrid({
                width: '1010px',
                height: '395px',
                dataSource: self.items(),
                columns: self.columns(),
                features: [{
                    name: 'Paging',
                    type: "local",
                    pageSize: 6 
                    }]
            });
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            // Get list stamp
            let startTemp: string = nts.uk.ui.windows.getShared("kdl014startDateB");
            let endTemp: string = nts.uk.ui.windows.getShared("kdl014endDateB");
            self.startDate = moment(startTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)') + '  ～';
            self.endDate = moment(endTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)');
            let lstEmployeeCode: Array<string> = nts.uk.ui.windows.getShared("kdl014lstEmployeeB");
            let lstCardNumber: Array<string> = [];
            let lstPersonID: Array<string> = [];
            let lstEmloyee: Array<PersonModel> = [];
            let lstStampNumber: Array<string> = [];
            let lstSource: Array<StampModel> = [];
            service.getListPersonByListEmployee(lstEmployeeCode).done(function(persons: any) {
                if (persons.length > 0) {
                    _.forEach(persons, function(person) {
                        lstPersonID.push(person.personId);
                        lstEmloyee.push(new PersonModel(person.employeeCode, person.personId));
                    });
                    //Get list STAMP NUMBER from PersonID 
                    service.getStampNumberByListPersonId(lstPersonID).done(function(StampNumbers: any) {
                        if (StampNumbers.length > 0) {
                            _.forEach(StampNumbers, function(i) {
                                lstStampNumber.push(i.cardNumber);
                            });
                            //Get List Stamp Reference
                            service.getStampByCode(lstStampNumber, startTemp, endTemp).done(function(lstStamp: any) {
                                if (lstStamp.length > 0) {
                                    _.forEach(lstStamp, function(item) {
                                        _.forEach(lstEmloyee, function(employee) {
                                            if (employee.personId == item.personId) {
                                                lstSource.push(new StampModel(employee.employeeCd, 'name', moment(item.date, 'YYYY/MM/DD').format('YYYY/MM/DD (ddd)'), _.padStart(nts.uk.time.parseTime(item.attendanceTime, true).format(), 5, '0'), item.stampReasonName, item.stampAtrName, item.stampMethodName, item.workLocationName, item.stampCombinationName));
                                                return false;
                                            }
                                        });
                                    });
                                }
                                self.items(_.orderBy(lstSource, ['date', 'attendanceTime', 'employeeCd'], ['asc', 'asc', 'asc']));
                                $("#igGridStamp").igGrid({ dataSource: self.items() });
                                dfd.resolve();
                            }).fail(function(res) {
                                dfd.reject();
                            });
                            dfd.resolve();
                        }
                        dfd.resolve();
                    }).fail(function(res) {
                        dfd.reject();
                    });
                }
                dfd.resolve();
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
        employeeCd: string;
        employeeName: string;
        date: string;
        attendanceTime: string;
        stampReasonName: string;
        stampAtrName: string;
        stampMethodName: string;
        workLocationName: string;
        stampCombinationName: string;
        constructor(employeeCd: string, employeeName: string, date: string, attendanceTime: string, stampReasonName: string, stampAtrName: string, stampMethodName: string, workLocationName: string, stampCombinationName: string) {
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

    class PersonModel {
        employeeCd: string;
        personId: string;
        constructor(employeeCd: string, personId: string) {
            var self = this;
            self.employeeCd = employeeCd;
            self.personId = personId;
        }
    }
}