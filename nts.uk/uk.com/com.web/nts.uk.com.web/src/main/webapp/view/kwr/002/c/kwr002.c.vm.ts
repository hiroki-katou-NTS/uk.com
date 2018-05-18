module nts.uk.com.view.kwr002.c.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import TabPanel = nts.uk.ui.tabpanel;
    export class ScreenModel {

        attendanceCode: KnockoutObservable<String>;
        attendanceName: KnockoutObservable<String>;
        useSeal: KnockoutObservableArray<any>;
        sealStamp: KnockoutObservableArray<String>;
        useSealValue: KnockoutObservable<any>;
        attendanceItemList: KnockoutObservableArray<model.AttendanceItem>;
        attendanceRecExpDaily: KnockoutObservableArray<viewmodel.model.AttendanceRecExp>;
        attendanceRecExpMonthly: KnockoutObservableArray<viewmodel.model.AttendanceRecExp>;

        columns: KnockoutObservableArray<any>;

        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;

        sealName1: KnockoutObservable<String>;
        sealName2: KnockoutObservable<String>;
        sealName3: KnockoutObservable<String>;
        sealName4: KnockoutObservable<String>;
        sealName5: KnockoutObservable<String>;
        sealName6: KnockoutObservable<String>;
        currentCode: KnockoutObservable<number>;
      
        
        


        constructor() {
            var self = this;

            self.attendanceCode = ko.observable('');
            self.attendanceName = ko.observable('');
            self.sealStamp = ko.observableArray([]);
            self.attendanceItemList = ko.observableArray([]);
            self.attendanceRecExpDaily = ko.observableArray([]);
            self.attendanceRecExpMonthly =  ko.observableArray([]);
            self.sealName1 = ko.observable('');
            self.sealName2 = ko.observable('');
            self.sealName3 = ko.observable('');
            self.sealName4 = ko.observable('');
            self.sealName5 = ko.observable('');
            self.sealName6 = ko.observable('');
            
            
            self.currentCode = ko.observable(0);
            //            self.columns = ko.observableArray([
            //                { headerText: nts.uk.resource.getText('KWR002_86'), key: 'attendanceCode', width: 100 },
            //                { headerText: nts.uk.resource.getText('KWR002_87'), key: 'name', formatter: _.escape, width: 200 }
            //            ]);
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KWR002_86'), prop: 'attendanceItemId', width: 100 },
                { headerText: nts.uk.resource.getText('KWR002_87'), prop: 'attendanceItemName', width: 200 }
            ]);
            self.useSealValue = ko.observable(true);
            self.useSeal = ko.observableArray([
                { code: true, name: nts.uk.resource.getText("KWR002_63") },
                { code: false, name: nts.uk.resource.getText("KWR002_64") }
            ]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("KWR002_88"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("KWR002_89"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-2');

        }
        start_page(): JQueryPromise<any> {
            blockUI.invisible();
            var self = this;
            var dfd = $.Deferred();

            service.findAllAttendanceRecExportDaily(1).done(function(listattendanceRecExpDailyList: Array<model.AttendanceRecExp>) {
                if (listattendanceRecExpDailyList.length > 0) {
                    listattendanceRecExpDailyList.forEach(item => {
                        var columnIndex :number = item.columnIndex;
                            self.attendanceRecExpDaily()[columnIndex] = item;
                    })
                }
                
            });
            
            service.findAllAttendanceRecExportMonthly(1).done(function(listattendanceRecExpMonthlyList: Array<model.AttendanceRecExp>) {
                if (listattendanceRecExpMonthlyList.length > 0) {
                    listattendanceRecExpMonthlyList.forEach(item => {
                        var columnIndex :number = item.columnIndex;
                            self.attendanceRecExpMonthly()[columnIndex] = item;
                    })
                }
                
            });
            
            service.getSealStamp(1).done(function(sealStampList : Array<String>){
                    if(sealStampList.length >0){
                        self.sealName1(sealStampList[0]);
                        self.sealName2(sealStampList[1]);
                        self.sealName3(sealStampList[2]);
                        self.sealName4(sealStampList[3]);
                        self.sealName5(sealStampList[4]);
                        self.sealName6(sealStampList[5]);
                        
                    }    
            });
            service.getAttendanceSingleList().done(function(listAttendanceItem: Array<model.AttendanceItem>) {
                if (listAttendanceItem.length != 0) {
                    self.attendanceItemList(listAttendanceItem);
                    self.currentCode(1);
                }
                dfd.resolve();
            });
           
            blockUI.clear();
            return dfd.promise();
        }
    }

    export module model {

        export class AttendanceRecExp {

            exportAtr: number;
            columnIndex: number;
            userAtr: Boolean;
            upperPosition: String;
            lowwerPosition: String;

            constructor(exportAtr: number, columnIndex: number, userAtr: Boolean, upperPosition: String, lowwerPosition: String) {

                this.exportAtr = exportAtr;
                this.columnIndex = columnIndex;
                this.userAtr = userAtr;
                this.upperPosition = upperPosition;
                this.lowwerPosition = lowwerPosition;
            }
        }

        export class AttendanceItem {
            attendanceItemId: number;
            attendanceItemName: String;
            screenUseItem: number;

            constructor(code: number, name: String, screenType: number) {
                this.attendanceItemId = code;
                this.attendanceItemName = name;
                this.screenUseItem = screenType;
            }
        }
    }

}