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
                    {headerText: "カラム1", key: "column1", width: 107,controlType: ControlType.TimeEditor, properties: {
                        inputFormat: 'time', mode: 'time'}},
                    {headerText: "カラム2", key: "column2", width: 60, controlType: ControlType.CheckBox, properties: {
                        enable: true}},
                    {headerText: "カラム3", key: "column3", width: 243, controlType: ControlType.DateRangeEditor,
                        properties: {enable: true, showNextPrevious: false, maxRange: 'oneMonth'}},
                    {headerText: "カラム4", key: "column4", width: 255, controlType: ControlType.ComboBox,
                        properties: {
                            options: self.itemList(), optionsValue: 'code', optionsText: 'name',
                            value: self.selectedCode(), columns: "[{ prop: 'code', length: 4 },{ prop: 'name', length: 10 }]",
                            enable: true, editable: false}}
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