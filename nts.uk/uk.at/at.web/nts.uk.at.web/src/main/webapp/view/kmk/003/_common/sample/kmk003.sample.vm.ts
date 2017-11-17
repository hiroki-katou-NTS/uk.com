module nts.uk.at.view.kmk003.sample {

    export module viewModel {

        export class ScreenModel {
            
            fixTableOption: any;
            itemList: KnockoutObservableArray<any>;
            dataSource: KnockoutObservableArray<any>;
            selectedCode: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                
                self.itemList = ko.observableArray([
                    {code: 1, name: '基本給1'},
                    {code: 2, name: '役職手当2'},
                    {code: 3, name: '基本給3'}
                ]);
                self.selectedCode = ko.observable('1');
                
                self.dataSource = ko.observableArray(self.fakeDataSource());
                
                self.fixTableOption = {
                    maxRows: 5,
                    dataSource: self.dataSource,
                    isMultipleSelect: true,
                    columns: self.columnSetting()
                }
            }
                      
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();

                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * fakeDataSource
             */
            private fakeDataSource(): Array<any> {
                return [
                    {column1: ko.observable("12:00"), column2: ko.observable(true), column3: ko.observable({}),
                        column4: ko.observable(1)},
                    {column1: ko.observable("23:00"), column2: ko.observable(false), column3: ko.observable({}),
                        column4: ko.observable(2)},
                    {column1: ko.observable("10:00"), column2: ko.observable(false), column3: ko.observable({}),
                        column4: ko.observable(3)}
                ];
            }
            
            /**
             * columnSetting
             */
            private columnSetting(): Array<any> {
                let self = this;
                return [
                    {headerText: "カラム1", key: "column1", width: 107, template: `<input data-bind=\"ntsTimeEditor: {
                        inputFormat: 'date', option: {width: '80'}}\" />`},
                    {headerText: "カラム2", key: "column2", width: 60, template: `<div data-bind=\"ntsCheckBox: {
                        enable: true}\"></div>`},
                    {headerText: "カラム3", key: "column3", width: 243, template: `<div data-bind="ntsDateRangePicker: {
                        required: true, enable: true,showNextPrevious: false, value: dateValue, maxRange: 'oneMonth'}"/>`},
                    {headerText: "カラム4", key: "column4", width: 255, dataSource: self.itemList(), template: `<div data-bind="ntsComboBox: {
                                            options: itemListCbb1,
                                            optionsValue: 'code',
                                            visibleItemsCount: 5,
                                            value: selectedCode,
                                            optionsText: 'name',
                                            editable: false,
                                            enable: true,
                                            columns: [
                                                { prop: 'code', length: 4 },
                                                { prop: 'name', length: 10 },
                                            ]}"></div>
                    `}
                ];
            }
            
            public addRowItem() {
                let self = this;
                self.dataSource.push({column1: ko.observable("10:00"), column2: ko.observable(false),
                    column3: ko.observable({}), column4: ko.observable(3)});
            }
            public removeItem() {
                let self = this;
                self.dataSource(self.dataSource().filter(item => item.isChecked() == false));
            }
        }
        
        class ControlType {
            static CheckBox: number = 1;
            static TimeEditor: number = 2;
            static DateRangeEditor: number = 3;
            static ComboBox: number = 4;
        }
    }
}