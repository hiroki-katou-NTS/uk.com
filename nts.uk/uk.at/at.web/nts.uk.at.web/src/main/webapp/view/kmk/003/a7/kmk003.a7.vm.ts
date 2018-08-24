module a7 {
    import EnumWorkForm = nts.uk.at.view.kmk003.a.viewmodel.EnumWorkForm;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import DiffTimeDeductTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeDeductTimezoneModel;

    import FlowRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowRestSettingModel;
    class ScreenModel {

        tabMode: KnockoutObservable<TabMode>;

        mainSettingModel: MainSettingModel;
        fixTableOptionForFixed: any;
        fixTableOptionForDiffTime: any;
        
        fixTableOptionForFlowUse: any;
        fixTableOptionForFlowNotUse1: any;
        fixTableOptionForFlowNotUse2: any;
        
        fixTableOptionForFlexUse: any;
        fixTableOptionForFlexNotUse1: any;
        fixTableOptionForFlexNotUse2: any;

        dataSourceForFixed: KnockoutObservableArray<any>;
        dataSourceForDiffTime: KnockoutObservableArray<any>;
        
        dataSourceForFlowUse: KnockoutObservableArray<any>;
        dataSourceForFlowNotUse1: KnockoutObservableArray<any>;
        dataSourceForFlowNotUse2: KnockoutObservableArray<any>;

        dataSourceForFlexUse: KnockoutObservableArray<any>;
        dataSourceForFlexNotUse1: KnockoutObservableArray<any>;
        dataSourceForFlexNotUse2: KnockoutObservableArray<any>;

        isFlow: KnockoutObservable<boolean>;
        isFlex: KnockoutObservable<boolean>;
        isFixed: KnockoutObservable<boolean>;
        isDiffTime: KnockoutObservable<boolean>;

        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<number>;

        isFlowUse: KnockoutObservable<boolean>;
        isFlowNotUse: KnockoutObservable<boolean>;
        isFlexUse: KnockoutObservable<boolean>;
        isFlexNotUse: KnockoutObservable<boolean>;
        
        isCheckFollowTimeForFlex: KnockoutObservable<boolean>;
        isCheckFollowTimeForFlow: KnockoutObservable<boolean>;
        
        //init for backup
        backUpOfFlowUse: any;
        backUpOfFlowNotUse: any;
        backUpOfFlexUse: any;
        backUpOfFlexNotUse: any;
        
        /**
        * Constructor.
        */
        constructor(tabMode: any, enumSetting: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel, isLoading: KnockoutObservable<any>) {
            let self = this;

            self.tabMode = tabMode;

            //main model
            self.mainSettingModel = mainSettingModel;
            
            self.backUpOfFlowUse=null;
            self.backUpOfFlowNotUse=null;
            self.backUpOfFlexUse=null;
            self.backUpOfFlexNotUse=null;

            self.isCheckFollowTimeForFlex = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.useHereAfterRestSet;
            self.isCheckFollowTimeForFlex.subscribe(function(v) {
                if (!v) {
                    $('#nts-fix-table-a7-flex-notuse-2').find('.nts-input').ntsError('clear');
                }
                else {
                    $('#nts-fix-table-a7-flex-notuse-2').find('.nts-editor').each((index, element) => {
                        if (!element.id) {
                            element.id = nts.uk.util.randomId();
                        }

                        $('#' + element.id).ntsEditor('validate');
                    })
                }
            });
            self.isCheckFollowTimeForFlow = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.useHereAfterRestSet;;
            self.isCheckFollowTimeForFlow.subscribe(function(v) {
                if (!v) {
                    $('#nts-fix-table-a7-flow-notuse-2').find('.nts-input').ntsError('clear');
                }
                else {
                    $('#nts-fix-table-a7-flow-notuse-2').find('.nts-editor').each((index, element) => {
                        if (!element.id) {
                            element.id = nts.uk.util.randomId();
                        }

                        $('#' + element.id).ntsEditor('validate');
                    })
                }
            });
            self.dataSourceForFixed = ko.observableArray([]);
            /////////////
            self.fixTableOptionForFixed = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForFixed,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 92
            };
            
            self.dataSourceForDiffTime = ko.observableArray([]);
            /////////////
            self.fixTableOptionForDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForDiffTime,
                isMultipleSelect: true,
                columns: self.difftimeColumnSetting(),
                tabindex: 92
            };
            self.loadDataToScreen();
            
            //subscrible worktime ssettingmethod
            self.isFlow = ko.computed(function() {
                return mainSettingModel.workTimeSetting.isFlow();
            });
            
            self.isFlex = ko.computed(function() {
                return mainSettingModel.workTimeSetting.isFlex();
            });
            
            self.isFixed = ko.computed(() => {
                return mainSettingModel.workTimeSetting.isFixed();
            });

            self.isDiffTime = ko.computed(() => {
                return mainSettingModel.workTimeSetting.isDiffTime();
            });
            
            /////////////
            self.dataSourceForFlowUse = ko.observableArray([]);
            self.fixTableOptionForFlowUse = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForFlowUse,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 94
            };

            /////////////
            self.dataSourceForFlowNotUse1 = ko.observableArray([]);
            self.fixTableOptionForFlowNotUse1 = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowNotUse1,
                isMultipleSelect: true,
                columns: self.columnSettingForFlow(),
                tabindex: 95
            };

            /////////////
            self.dataSourceForFlowNotUse2 = ko.observableArray([]);
            self.fixTableOptionForFlowNotUse2 = {
                maxRow: 1,
                minRow: 0,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.dataSourceForFlowNotUse2,
                isMultipleSelect: false,
                columns: self.columnSettingForFlow(),
                tabindex: 97
            };

            /////////////
            self.dataSourceForFlexUse = ko.observableArray([]);
            self.fixTableOptionForFlexUse = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForFlexUse,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 94
            };

            /////////////
            self.dataSourceForFlexNotUse1 = ko.observableArray([]);
            self.fixTableOptionForFlexNotUse1 = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlexNotUse1,
                isMultipleSelect: true,
                columns: self.columnSettingForFlex(),
                tabindex: 95
            };

            /////////////
            self.dataSourceForFlexNotUse2 = ko.observableArray([]);
            self.fixTableOptionForFlexNotUse2 = {
                maxRow: 1,
                minRow: 0,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.dataSourceForFlexNotUse2,
                isMultipleSelect: false,
                columns: self.columnSettingForFlex(),
                tabindex: 97
            };
            
            
            
            self.useFixedRestTimeOptions = ko.observableArray([
                { code: UseDivision.USE, name: nts.uk.resource.getText("KMK003_142") },
                { code: UseDivision.NOTUSE, name: nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable(1);

            self.isFlowUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.USE && self.isFlow();
            }).extend({ rateLimit: 200 });
            self.isFlowNotUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.NOTUSE && self.isFlow();
            }).extend({ rateLimit: 200 });
            self.isFlexUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.USE && self.isFlex();
            }).extend({ rateLimit: 200 });
            self.isFlexNotUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.NOTUSE && self.isFlex();
            }).extend({ rateLimit: 200 });
            
            self.useFixedRestTime.subscribe((v) => {
                self.clearTabError();
                self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime(v == UseDivision.USE);
                //TODO load
                if (self.mainSettingModel.workTimeSetting.isFlex()) {
                    if (v == UseDivision.NOTUSE && self.backUpOfFlexNotUse) {
                        self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.updateData(self.backUpOfFlexNotUse);
                    }
                    else {
                        if (self.backUpOfFlexUse) {
                            self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.updateData(self.backUpOfFlexUse);
                        }
                    }
                }
                if (self.mainSettingModel.workTimeSetting.isFlow()) {
                    if (v == UseDivision.NOTUSE && self.backUpOfFlowNotUse) {
                        $('#nts-fix-table-a7-flow-use').find('.nts-input').ntsError('clear');
                        self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.updateData(self.backUpOfFlowNotUse);
                    }
                    else {
                        if (self.backUpOfFlowUse) {
                            self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.updateData(self.backUpOfFlowUse);
                        }
                    }
                }
                self.updateDataModel();
            });
            
            //load data to screen 
            isLoading.subscribe((isDone: boolean) => {
                if (isDone) {
                    self.updateDataModel();
                    self.setDataFlexOrFlowToModel();
                    if (self.mainSettingModel.workTimeSetting.isFlex()) {
                        self.backUpOfFlexUse = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.toDto();
                        self.backUpOfFlexNotUse = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.toDto();
                    }
                    if (self.mainSettingModel.workTimeSetting.isFlow()) {
                        self.backUpOfFlowUse = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.toDto();
                        self.backUpOfFlowNotUse = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.toDto();
                    }
                }
            });
            
        }
        
        private clearTabError() {
            $('#nts-fix-table-a7-fixed-difftime').find('.time-range-editor').ntsError('clear');
            
            $('#nts-fix-table-a7-flex-use').find('.time-range-editor').ntsError('clear');
            $('#nts-fix-table-a7-flex-notuse-1').find('.nts-editor').ntsError('clear');
            $('#nts-fix-table-a7-flex-notuse-2').find('.nts-editor').ntsError('clear');
            
            $('#nts-fix-table-a7-flow-use').find('.time-range-editor').ntsError('clear');
            $('#nts-fix-table-a7-flow-notuse-1').find('.nts-editor').ntsError('clear');
            $('#nts-fix-table-a7-flow-notuse-2').find('.nts-editor').ntsError('clear');
        }

        private loadDataToScreen() {
            let self = this;
            self.dataSourceForFixed.subscribe((newDataSource: any) => {
                if (self.mainSettingModel.workTimeSetting.isFixed()) {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.timezones(listDeductionTimeModel);
                }

            });
            self.dataSourceForDiffTime.subscribe((newDataSource: any) => {
                if (self.mainSettingModel.workTimeSetting.isDiffTime()) {
                    let listDiffTimeDeductTimezoneModel: DiffTimeDeductTimezoneModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DiffTimeDeductTimezoneModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        deduct.isUpdateStartTime(item.column2());
                        listDiffTimeDeductTimezoneModel.push(deduct);
                    }
                    self.mainSettingModel.diffWorkSetting.dayoffWorkTimezone.restTimezone.restTimezones(listDiffTimeDeductTimezoneModel);
                }
            });
        }

        private setDataFlexOrFlowToModel() {
            let self = this;
            
            //if mode flex
            if (self.mainSettingModel.workTimeSetting.isFlex()) {
                self.useFixedRestTime(self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime()?UseDivision.USE:UseDivision.NOTUSE);
                self.dataSourceForFlexUse.subscribe((newDataSource: any) => {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones(listDeductionTimeModel);
                });

                self.dataSourceForFlexNotUse1.subscribe((newData: any) => {
                    let listFlowRestSettingModel: FlowRestSettingModel[] = [];
                    for (let item of newData) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(item.column2());
                        rest.flowPassageTime(item.column1());
                        listFlowRestSettingModel.push(rest);
                    }
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets(listFlowRestSettingModel);
                });

                self.dataSourceForFlexNotUse2.subscribe((newData: any) => {
                    if (newData && newData.length > 0) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(newData[0].column2());
                        rest.flowPassageTime(newData[0].column1());
                        self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.hereAfterRestSet = rest;
                    }
                });
            }

            //if mode flow
            if (self.mainSettingModel.workTimeSetting.isFlow()) {
                self.useFixedRestTime(self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixRestTime()?UseDivision.USE:UseDivision.NOTUSE);
                self.dataSourceForFlowUse.subscribe((newDataSource: any) => {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.timezones(listDeductionTimeModel);
                });

                self.dataSourceForFlowNotUse1.subscribe((newData: any) => {
                    let listFlowRestSettingModel: FlowRestSettingModel[] = [];
                    for (let item of newData) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(item.column2());
                        rest.flowPassageTime(item.column1());
                        listFlowRestSettingModel.push(rest);
                    }
                    self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.flowRestSets(listFlowRestSettingModel);
                });

                self.dataSourceForFlowNotUse2.subscribe((newData: any) => {
                    if (newData && newData.length > 0) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(newData[0].column2());
                        rest.flowPassageTime(newData[0].column1());
                        self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.hereAfterRestSet = rest;
                    }
                });
            }
        }

        private updateDataModel() {
            let self = this;
            //if flex
            if (self.mainSettingModel.workTimeSetting.isFlex()) {
                let data: any = [];
                let data1: any = [];
                let data2: any = [];

                for (let item of self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones()) {
                    data.push({
                        column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                    });
                }
                self.dataSourceForFlexUse(data);

                for (let item of self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets()) {
                    data1.push({
                        column1: ko.observable(item.flowPassageTime()),
                        column2: ko.observable(item.flowRestTime())
                    });
                }
                self.dataSourceForFlexNotUse1(data1);

                let item = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.hereAfterRestSet;
                data2.push({
                    column1: ko.observable(item.flowPassageTime()),
                    column2: ko.observable(item.flowRestTime())
                });
                self.dataSourceForFlexNotUse2(data2);
                //reset other mode
                self.resetWhenCopyMode();
            }
            //if flow
            if (self.mainSettingModel.workTimeSetting.isFlow()) {
                let data: any = [];
                let data1: any = [];
                let data2: any = [];

//                self.useFixedRestTime(self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixRestTime() ? UseDivision.USE : UseDivision.NOTUSE);
                for (let item of self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.timezones()) {
                    data.push({
                        column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                    });
                }
                self.dataSourceForFlowUse(data);

                for (let item of self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.flowRestSets()) {
                    data1.push({
                        column1: ko.observable(item.flowPassageTime()),
                        column2: ko.observable(item.flowRestTime())
                    });
                }
                self.dataSourceForFlowNotUse1(data1);

                let item = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.hereAfterRestSet;
                data2.push({
                    column1: ko.observable(item.flowPassageTime()),
                    column2: ko.observable(item.flowRestTime())
                });
                self.dataSourceForFlowNotUse2(data2);
                //reset other mode
                self.resetWhenCopyMode();
            }
            //for fixed or difftime

            if (self.mainSettingModel.workTimeSetting.isFixed()) {
                let data: any = [];
                for (let item of self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.timezones()) {
                    data.push({
                        column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                    });
                }
                self.dataSourceForFixed(data);
                //reset other mode
                self.resetWhenCopyMode();
            }
            if (self.mainSettingModel.workTimeSetting.isDiffTime()) {
                let data: any = [];
                for (let item of self.mainSettingModel.diffWorkSetting.dayoffWorkTimezone.restTimezone.restTimezones()) {
                    data.push({
                        column1: ko.observable({ startTime: item.start(), endTime: item.end() }),
                        column2: ko.observable(item.isUpdateStartTime())
                    });
                }
                self.dataSourceForDiffTime(data);
                //reset other mode
                self.resetWhenCopyMode();
            }
        }

        private resetWhenCopyMode() {
            let self = this;
            if (!self.mainSettingModel.workTimeSetting.isFixed()) {
                self.dataSourceForFixed([]);
            }
            if (!self.mainSettingModel.workTimeSetting.isDiffTime()) {
                self.dataSourceForDiffTime([]);
            }
            if (!self.mainSettingModel.workTimeSetting.isFlex()) {
                self.dataSourceForFlexUse([]);
                self.dataSourceForFlexNotUse1([]);
                self.dataSourceForFlexNotUse2([]);
            }
            if (!self.mainSettingModel.workTimeSetting.isFlow()) {
                self.dataSourceForFlowUse([]);
                self.dataSourceForFlowNotUse1([]);
                self.dataSourceForFlowNotUse2([]);
            }
        }
        
        /**
        * Update column setting
        */
        private refreshColumnSet() {
            let self = this;
            self.fixTableOptionForFixed.columns = self.columnSetting();
            self.fixTableOptionForDiffTime.columns = self.difftimeColumnSetting();
            
            self.fixTableOptionForFlexUse.columns = self.columnSetting();
            self.fixTableOptionForFlexNotUse1.columns = self.columnSettingForFlex();
            self.fixTableOptionForFlexNotUse2.columns = self.columnSettingForFlex();
            
            self.fixTableOptionForFlowUse.columns = self.columnSetting();
            self.fixTableOptionForFlowNotUse1.columns = self.columnSettingForFlow();
            self.fixTableOptionForFlowNotUse2.columns = self.columnSettingForFlow();
        }

        private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: 0, endTime: 0 }),
                    width: 243, template: 
                    `<div data-bind="ntsTimeRangeEditor: {required: true,
                            enable: true,
                            inputFormat: 'time',
                            startTimeNameId: '#[KMK003_163]',
                            endTimeNameId: '#[KMK003_164]',
                            startConstraint: 'TimeWithDayAttr',
                            endConstraint: 'TimeWithDayAttr',
                            paramId: 'KMK003_21'
                                }
                            "/>`
                }
            ];
            }
        
        private difftimeColumnSetting(): Array<any> {
            let self = this;
            return [{
                headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: 0, endTime: 0 }),
                width: 243, template:
                `<div data-bind="ntsTimeRangeEditor: {required: true,
                            enable: true,
                            inputFormat: 'time',
                            startTimeNameId: '#[KMK003_163]',
                            endTimeNameId: '#[KMK003_164]',
                            startConstraint: 'TimeWithDayAttr',
                            endConstraint: 'TimeWithDayAttr',
                            paramId: 'KMK003_21'
                                }
                            "/>`
            }, {
                    headerText: nts.uk.resource.getText("KMK003_129"),
                    key: "column2",
                    defaultValue: ko.observable(false),
                    width: 50,
                    template: `<div data-bind="ntsCheckBox: { enable: true }">`
                }];
        }
        
        //column for flex
        private columnSettingForFlex(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"), key: "column1", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_174]',constraint: 'AttendanceTime', inputFormat: 'time',mode: 'time',enable: true ,required: true}" />`,
                    cssClassName: 'column-time-editor'
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_176"), key: "column2", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_176]',constraint: 'AttendanceTime',inputFormat: 'time',mode: 'time',enable: true ,required: true}" />`,
                    cssClassName: 'column-time-editor',
                    enable: self.isCheckFollowTimeForFlex()
                }
            ];
        }
        
        //column for flow
        private columnSettingForFlow(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"), key: "column1", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_174]',constraint: 'AttendanceTime', inputFormat: 'time',mode: 'time',enable: true ,required: true}" />`,
                    cssClassName: 'column-time-editor'
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_176"), key: "column2", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_176]',constraint: 'AttendanceTime',inputFormat: 'time',mode: 'time',enable: true ,required: true}" />`,
                    cssClassName: 'column-time-editor',
                    enable: self.isCheckFollowTimeForFlow()
                }
            ];
        }
    }

    export enum UseDivision {
        NOTUSE,
        USE
    }
    class KMK003A7BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        private getData() {
            let self = this;
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a7/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let tabMode = input.tabMode;
            let enumSetting = input.enum;
            let mainSettingModel = input.mainSettingModel;
            let isLoading = input.isLoading;
            let isClickSave:KnockoutObservable<boolean> = input.isClickSave;
            let isClickNew:KnockoutObservable<boolean> = input.isClickNew;
            
            var screenModel = new ScreenModel(tabMode, enumSetting, mainSettingModel, isLoading);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                
                isClickSave.subscribe((v) => {
                    if (screenModel.mainSettingModel.workTimeSetting.isFlex()) {
                        screenModel.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime(screenModel.useFixedRestTime() == UseDivision.USE);
                        if (v) {
                            if (!screenModel.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.useHereAfterRestSet()) {
                                screenModel.dataSourceForFlexNotUse1.valueHasMutated();
                                screenModel.dataSourceForFlexNotUse2([{
                                    column1: ko.observable(0),
                                    column2: ko.observable(0)
                                }]);
                            }
                            else {
                                screenModel.dataSourceForFlexNotUse1.valueHasMutated();
                                screenModel.dataSourceForFlexNotUse2.valueHasMutated();
                            }
                        }
                    }
                    if (screenModel.mainSettingModel.workTimeSetting.isFlow()) {
                        screenModel.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixRestTime(screenModel.useFixedRestTime() == UseDivision.USE);
                        if (v) {
                            if (!screenModel.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.useHereAfterRestSet()) {
                                screenModel.dataSourceForFlowNotUse1.valueHasMutated();
                                screenModel.dataSourceForFlowNotUse2([{
                                    column1: ko.observable(0),
                                    column2: ko.observable(0)
                                }]);
                            }
                            else {
                                screenModel.dataSourceForFlowNotUse1.valueHasMutated();
                                screenModel.dataSourceForFlowNotUse2.valueHasMutated();
                            }
                        }
                    }
                });

                isClickNew.subscribe((v) => {
                    if (v) {
                        screenModel.dataSourceForFixed([]);
                        screenModel.dataSourceForDiffTime([]);
                        screenModel.dataSourceForFlexUse([]);
                        screenModel.dataSourceForFlexNotUse1([]);
                        screenModel.dataSourceForFlexNotUse2([{
                            column1: ko.observable(0),
                            column2: ko.observable(0)
                        }]);
                        screenModel.dataSourceForFlowUse([]);
                        screenModel.dataSourceForFlowNotUse1([]);
                        screenModel.dataSourceForFlowNotUse2([{
                            column1: ko.observable(0),
                            column2: ko.observable(0)
                        }]);
                        screenModel.useFixedRestTime(screenModel.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime() ? UseDivision.USE : UseDivision.NOTUSE);
                    }
                });

            });
        }

    }
    ko.bindingHandlers['ntsKMK003A7'] = new KMK003A7BindingHandler();
}