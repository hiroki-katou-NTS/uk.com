module kdl014.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        startEndDate: string;
        
        //Param get from test main-screen.
        startDate: string;
        endDate: string;

        //Value diplay in screen.
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            var self = this;
            
            self.startEndDate = '';
            
            //Init param get from test main-screen.
            self.startDate = '';
            self.endDate = '';

            //Init value display in this screen.
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

            //Get data from main screen
            let data: any = getShared('KDL014B_PARAM');
            let startTemp = data.startDate;
            let endTemp = data.endDate;
            let lstEmployeeCode: Array<string> = data.lstEmployee;

            //Convert attribute to display in this screen.
            self.startDate = moment(startTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)') + '　～　';
            self.endDate = moment(endTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)');
            self.startEndDate = '' + self.startDate + '' + self.endDate;

            //Create param to get list stamp from server.
            let stampParam = new StampParam(startTemp, endTemp, lstEmployeeCode);
            //Get List Stamp from server.
            service.getListStampDetail(stampParam).done(function(lstStamp: any) {
                //Define value to save list stamp get from server.
                let lstEmloyee: Array<PersonModel> = [];
                let lstSource: Array<StampModel> = [];
                if (lstStamp.length > 0) {
                    _.forEach(lstStamp, function(item) {
                        //When cardNumber != '';
                        if(item.cardNumber != ''){
                            lstSource.push(new StampModel(item.employeeCode, item.pname, moment(item.date, 'YYYY/MM/DD').format('YYYY/MM/DD (ddd)'), _.padStart(nts.uk.time.parseTime(item.attendanceTime, true).format(), 5, '0'), item.stampReasonName, item.stampAtrName, item.stampMethodName, item.workLocationName, item.stampCombinationName));    
                        }
                    });
                }
                self.items(_.orderBy(lstSource, ['date', 'attendanceTime', 'employeeCd'], ['asc', 'asc', 'asc']));
                $("#igGridStamp").igGrid({ dataSource: self.items() });
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


    export class StampModel {
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

    export class PersonModel {
        employeeCd: string;
        personId: string;
        constructor(employeeCd: string, personId: string) {
            var self = this;
            self.employeeCd = employeeCd;
            self.personId = personId;
        }
    }

    export class StampParam {
        startDate: string;
        endDate: string;
        sIDs: Array<string>;
        constructor(startDate: string, endDate: string, sids: Array<string>) {
            var self = this;
            self.startDate = startDate;
            self.endDate = endDate;
            self.sIDs = sids;
        }
    }
}