module nts.uk.at.view.kmk003.sample {

    export module viewModel {

        export class ScreenModel {
            
            fixTableOption: any;
            itemList: KnockoutObservableArray<any>;
            selectedCode: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                
                self.itemList = ko.observableArray([
                    {code: 1, name: '基本給1'},
                    {code: 2, name: '役職手当2'},
                    {code: 3, name: '基本給3'}
                ]);
                self.selectedCode = ko.observable('1');
                
                self.fixTableOption = {
                    height: 500,
                    dataSource: self.fakeDataSource(),
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
                    {column1: ko.observable("12:00"), column2: true, column3: ko.observable({})},
                    {column1: ko.observable("23:00"), column2: false, column3: ko.observable({})},
                    {column1: ko.observable("10:00"), column2: false, column3: ko.observable({})},
                ];
            }
            
            /**
             * columnSetting
             */
            private columnSetting(): Array<any> {
                let self = this;
                return [
                    {headerText: "カラム1", key: "column1", width: 50, controlType: ControlType.TimeEditor, options: {
                        inputFormat: 'time', mode: 'time'}},
                    {headerText: "カラム2", key: "column2", width: 10, controlType: ControlType.CheckBox, options: {
                        enable: true}},
                    {headerText: "カラム3", key: "column3", width: 100, controlType: ControlType.DateRangeEditor,
                        options: {enable: true, showNextPrevious: false, maxRange: 'oneMonth'}},
//                    {headerText: "カラム4", key: "column4", width: 400, controlType: ControlType.ComboBox,
//                        options: {options: self.itemList(), optionsValue: 'code', optionsText: 'name',
//                        value: self.selectedCode(), columns: [{ prop: 'code', length: 4 },{ prop: 'name', length: 10 }], enable: true, editable: false}}
                ];
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