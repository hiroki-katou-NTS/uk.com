module kdl014.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        //Param get from test main-screen
        employeeID: string;
        startDate: string;
        endDate: string;

        //Param diplay on screen.
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        employeeCode: KnockoutObservable<String>;
        employeeName: string;
        startEndDate: string;

        constructor() {
            var self = this;
            //Init param get from test main-screen.
            self.startDate = '';
            self.endDate = '';
            self.employeeID = '';
            self.employeeCode = "";

            //Init param display in screen.
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL014_4"), key: 'date', width: 130 },
                { headerText: nts.uk.resource.getText("KDL014_5"), key: 'attendanceTime', width: 100 },
                { headerText: nts.uk.resource.getText("KDL014_6"), key: 'stampAtrName', width: 80 },
                { headerText: nts.uk.resource.getText("KDL014_11"), key: 'stampMethodName', width: 120 },
                { headerText: nts.uk.resource.getText("KDL014_13"), key: 'stampReasonName', width: 80 },
                { headerText: nts.uk.resource.getText("KDL014_7"), key: 'workLocationName', width: 170 },
                { headerText: nts.uk.resource.getText("KDL014_12"), key: 'stampCombinationName', width: 100 }
            ]);
            self.employeeName = '';
            self.startEndDate = '';
            $("#igGridStamp").igGrid({
                width: '810px',
                height: '435px',
                dataSource: self.items(),
                columns: self.columns()
            });

        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            //Get data from main screen.
            let data: any = getShared('KDL014A_PARAM');
            let startTemp = data.startDate;
            let endTemp = data.endDate;
            self.employeeID = data.employeeID;

            //Set value for param display in screen.
            self.startDate = moment(startTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)') + '　～　';
            self.endDate = moment(endTemp, 'YYYYMMDD').format('YYYY/MM/DD (ddd)');
            self.startEndDate = '' + self.startDate + '' + self.endDate;
            
            //self.employeeName = "name" + self.employeeID;

            //Create Param to get list Stamp from server.
            let arrEmpCode: Array<string> = [];
            arrEmpCode.push(self.employeeID);
            let stampParam = new StampParam(startTemp, endTemp, arrEmpCode);

            //Get list stamp by param created.
            service.getListStampDetail(stampParam).done(function(lstStamp: any) {
                //List save Stamp list
                let lstSource: Array<StampModel> = [];
                
                if (lstStamp.length > 0) {
                    _.forEach(lstStamp, function(item) {
                        //When cardNumber != '';
                        if(item.cardNumber != ''){
                            lstSource.push(new StampModel(moment(item.date, 'YYYY/MM/DD').format('YYYY/MM/DD (ddd)'), _.padStart(nts.uk.time.parseTime(item.attendanceTime, true).format(), 5, '0'), item.stampReasonName, item.stampAtrName, item.stampMethodName, item.workLocationName, item.stampCombinationName));
                        }
                        
                        //Set employeeName by Dto of emloyeeName.
                        self.employeeName = item.pname;
                        self.employeeCode = item.employeeCode;
                    });
                };
                //set list data source
                self.items(_.orderBy(lstSource, ['date', 'attendanceTime'], ['asc', 'asc']));
                $("#igGridStamp").igGrid({ dataSource: self.items() });
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject();
            });

            return dfd.promise();
        }

        close() {
            nts.uk.ui.windows.close();
        }
    }


    export class StampModel {
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