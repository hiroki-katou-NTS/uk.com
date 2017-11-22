module nts.uk.at.view.kmk003.sample {

    export module viewModel {

        export class ScreenModel {
            
            selectionOption: KnockoutObservableArray<any>;
            selectedType: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            fixTableOption: any;
            itemList: KnockoutObservableArray<any>;
            dataSource: KnockoutObservableArray<any>;
            
            constructor() {
                let self = this;
                
                self.selectionOption = ko.observableArray([
                    { code: 0, name: 'Single Selection' },
                    { code: 1, name: 'Multiple Selection' },
                ]);
                self.selectedType = ko.observable(0);
                
                self.enable = ko.observable(false);
                
                self.itemList = ko.observableArray([
                    {code: 1, name: '基本給1'},
                    {code: 2, name: '役職手当2'},
                    {code: 3, name: '基本給3'}
                ]);
                
                self.dataSource = ko.observableArray(self.fakeDataSource());
                
                self.fixTableOption = {
                    maxRows: 5,
                    dataSource: self.dataSource,
                    isMultipleSelect: self.enable(),
                    columns: self.columnSetting(),
                    tabindex: 10
                }
                
                self.selectedType.subscribe(function(newValue) {
                    self.enable(newValue == 1);
                    self.fixTableOption.isMultipleSelect = self.enable();
                    $('#nts-fix-table').ntsFixTableCustom(self.fixTableOption);
                });
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
                    {headerText: "カラム1", key: "column1", width: 107, template: `<input data-bind="ntsTimeEditor: {
                        inputFormat: 'date'}" />`, cssClassName: 'column-time-editor'},
                    {headerText: "カラム2", key: "column2", width: 60, template: `<div data-bind="ntsCheckBox: {
                        enable: true}"></div>`},
                    {headerText: "カラム3", key: "column3", width: 243, template: `<div data-bind="ntsDateRangePicker: {
                        required: true, enable: true,showNextPrevious: false, maxRange: 'oneMonth'}"/>`},
                    {headerText: "カラム4", key: "column4", width: 300, dataSource: self.itemList(), template: `<div data-bind="ntsComboBox: {
                                            optionsValue: 'code',
                                            visibleItemsCount: 5,
                                            optionsText: 'name',
                                            editable: false,
                                            enable: true,
                                            columns: [
                                                { prop: 'code', length: 4 },
                                                { prop: 'name', length: 10 },
                                            ]}"></div>`, cssClassName: 'column-combo-box'}
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
    }
}