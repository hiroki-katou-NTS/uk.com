module nts.uk.com.view.kwr002.c.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        attendanceCode: KnockoutObservable<String>;
        attendanceName: KnockoutObservable<String>;
        useSeal: KnockoutObservableArray<any>;
        sealStamp: KnockoutObservableArray<String>;
        useSealValue: KnockoutObservable<any>;
        attendanceItemList: KnockoutObservableArray<viewmodel.model.AttendanceItem>;
        attendanceRecExp: KnockoutObservableArray<viewmodel.model.AttendanceRecExp>;

        columns: KnockoutObservableArray<any>;

        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<any>;

        constructor() {
            var self = this;

            self.attendanceCode = ko.observable('');
            self.attendanceName = ko.observable('');
            self.sealStamp = ko.observableArray([]);
            self.attendanceItemList = ko.observableArray([]);
            self.attendanceRecExp = ko.observableArray([]);

            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KWR002_86'), key: 'attendCode', width: 100 },
                { headerText: nts.uk.resource.getText('KWR002_87'), key: 'attendName', formatter: _.escape, width: 200 }
            ]);
            self.useSealValue = ko.observable(0);
            self.useSeal = ko.observableArray([
                { code: 1, name: "True"/*nts.uk.resource.getText("KWR002_63")*/ },
                { code: 0, name: "False"/*nts.uk.resource.getText("KWR002_64")*/ }
            ]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("KWR002_88"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("KWR002_89"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

        }
        start_page(): JQueryPromise<any> {
            blockUI.invisible();
            var dfd = $.Deferred();
            blockUI.clear();
            dfd.resolve();
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
            code: number;
            name: String;
            screenType: number;

            constructor(code: number, name: String, screenType: number) {
                this.code = code;
                this.name = name;
                this.screenType = screenType;
            }
        }
    }

}