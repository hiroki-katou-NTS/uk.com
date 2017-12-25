module nts.uk.at.view.kmk003.sample {

    import SimpleWorkTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.SimpleWorkTimeSettingDto;
    
    export module viewModel {

        export class ScreenModel {
            
            enable: KnockoutObservable<boolean>;
            fixTableOption1: any;
            dataSource1: KnockoutObservableArray<any>;
            
            itemList: KnockoutObservableArray<any>;
            
            fixTableOption2: any;
            dataSource2: KnockoutObservableArray<any>;
            
            constructor() {
                let self = this;
                
                self.enable = ko.observable(true);
                
                self.itemList = ko.observableArray([
                    {code: 1, name: '基本給1'},
                    {code: 2, name: '役職手当2'},
                    {code: 3, name: '基本給3'}
                ]);
                
                self.dataSource1 = ko.observableArray([]);
                self.fixTableOption1 = {
                    maxRow: 7,
                    minRow: 0,
                    maxRowDisplay: 5,
                    isShowButton: true,
                    dataSource: self.dataSource1,
                    isMultipleSelect: self.enable(),
                    columns: self.columnSetting(),
                    tabindex: 10
                }
                
                self.dataSource2 = ko.observableArray([]);
                self.fixTableOption2 = {
                    maxRow: 10,
                    minRow: 2,
                    maxRowDisplay: 5,
                    isShowButton: true,
                    dataSource: self.dataSource2,
                    isMultipleSelect: self.enable(),
                    columns: self.columnSetting(),
                    tabindex: 10
                }
                
                self.dataSource1.subscribe((newList) => {
                    console.log(newList);
                });
            }
                      
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();

                self.dataSource1.push({
                    column1: ko.observable(2300), column2: ko.observable(false),
                    column3: ko.observable({startTime: 1300, endTime: 1400}), column4: ko.observable(3)
                });
                self.dataSource1.push({
                    column1: ko.observable(1000), column2: ko.observable(false),
                    column3: ko.observable({startTime: 1000, endTime: 1200}), column4: ko.observable(3)
                });
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * testDataSource
             */
            public testDataSource() {
                console.log(this.dataSource1());
            }
            
            /**
             * columnSetting
             */
            private columnSetting(): Array<any> {
                let self = this;
                return [
                    {headerText: "カラム1", key: "column1", defaultValue: ko.observable(1200), width: 107, template: `<input data-bind="ntsTimeEditor: {
                        inputFormat: 'time', mode: 'time'}" />`, cssClassName: 'column-time-editor'},
                    {headerText: "カラム2", key: "column2", defaultValue: ko.observable(true), width: 60, template: `<div data-bind="ntsCheckBox: {
                        enable: true}"></div>`},
                    {headerText: "カラム3", key: "column3", defaultValue: ko.observable({startTime: 1000, endTime: 1200}), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        name: 'Duration', required: true, enable: true, inputFormat: 'time'}"/>`},
                    {headerText: "カラム4", key: "column4", defaultValue: ko.observable(1), width: 300, dataSource: self.itemList(), template: `<div data-bind="ntsComboBox: {
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
        }
    }
}