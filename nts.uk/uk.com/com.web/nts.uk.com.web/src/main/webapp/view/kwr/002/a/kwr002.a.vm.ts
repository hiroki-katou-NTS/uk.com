module nts.uk.com.view.kwr002.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import TabPanel = nts.uk.ui.tabpanel;

    export module viewModel {
        export class ScreenModel {

            items: KnockoutObservableArray<Employee>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;

            enable: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;

            constructor() {
                let self = this;

                self.enable = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.itemList = ko.observableArray([
                    new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
                ]);

                self.selectedCode = ko.observable('1');

                self.items = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR002_13"), key: 'employeeCD', width: 140 },
                    { headerText: nts.uk.resource.getText("KWR002_14"), key: 'name', width: 200 },
                    { headerText: nts.uk.resource.getText("KWR002_15"), key: 'affiliation', width: 150 }
                ]);
                console.log(self.columns);

                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
            }

            start_page(): JQueryPromise<any> {

                blockUI.invisible();
                let self = this;
                let dfd = $.Deferred();
                let listDemo: Array<Employee> = [];

                for (let i = 0; i < 5; i++) {
                    listDemo.push(new Employee('00' + i, '基本給', "description " + i));
                }
                self.items(listDemo);
                dfd.resolve();
                blockUI.clear();
                return dfd.promise();
            }
        }
        export class Employee {
            employeeCD: string;
            name: string;
            affiliation: string;
            constructor(employeeCD: string, name: string, affiliation: string) {
                this.employeeCD = employeeCD;
                this.name = name;
                this.affiliation = affiliation;
            }
        }

        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }

}