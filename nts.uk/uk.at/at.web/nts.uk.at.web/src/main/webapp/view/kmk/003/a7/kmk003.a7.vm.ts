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
        fixTableOptionForFixedOrDiffTime: any;
        fixTableOptionForFlowOrFlexUse: any;
        fixTableOptionForFlowOrFlexNotUse1: any;
        fixTableOptionForFlowOrFlexNotUse2: any;

        dataSourceForFixedOrDiffTime: KnockoutObservableArray<TimeRangeModel>;
        dataSourceForFlowOrFlexUse: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse1: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse2: KnockoutObservableArray<any>;

        isFlowOrFlex: KnockoutObservable<boolean>;

        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<number>;

        isFixedRestTime: KnockoutObservable<boolean>;
        isFlexOrFlowNotUse: KnockoutObservable<boolean>;
        isCheckFollowTime: KnockoutObservable<boolean>;
        
        //init for backup
//        backUpOfFixedOrDiffTime: any;
        backUpOfFlowOrFlexUse: any;
        backUpOfFlowOrFlexNotUse: any;
        /**
        * Constructor.
        */
        constructor(tabMode: any, enumSetting: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel, isLoading: KnockoutObservable<any>) {
            let self = this;

            self.tabMode = tabMode;

            //main model
            self.mainSettingModel = mainSettingModel;
            
//            self.backUpOfFixedOrDiffTime=null;
            self.backUpOfFlowOrFlexUse=null;
            self.backUpOfFlowOrFlexNotUse=null;

            self.isCheckFollowTime = ko.observable(true);
            self.isCheckFollowTime.subscribe(function() {
                self.refreshColumnSet();
            });
            self.dataSourceForFixedOrDiffTime = ko.observableArray([]);
            /////////////
            self.fixTableOptionForFixedOrDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForFixedOrDiffTime,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 92
            };
            
            self.loadDataToScreen();
            /////////////
            self.dataSourceForFlowOrFlexUse = ko.observableArray([]);
            self.fixTableOptionForFlowOrFlexUse = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexUse,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 94
            };

            /////////////
            self.dataSourceForFlowOrFlexNotUse1 = ko.observableArray([]);
            self.fixTableOptionForFlowOrFlexNotUse1 = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexNotUse1,
                isMultipleSelect: true,
                columns: self.columnSetting2(),
                tabindex: 95
            };

            /////////////
            self.dataSourceForFlowOrFlexNotUse2 = ko.observableArray([]);
            self.fixTableOptionForFlowOrFlexNotUse2 = {
                maxRow: 1,
                minRow: 0,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.dataSourceForFlowOrFlexNotUse2,
                isMultipleSelect: false,
                columns: self.columnSetting2(),
                tabindex: 97
            };

            //subscrible worktime ssettingmethod
            self.isFlowOrFlex = ko.computed(function() {

                return mainSettingModel.workTimeSetting.isFlex() || mainSettingModel.workTimeSetting.isFlow();
            });

            self.useFixedRestTimeOptions = ko.observableArray([
                { code: UseDivision.USE, name: nts.uk.resource.getText("KMK003_142") },
                { code: UseDivision.NOTUSE, name: nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable(1);

            self.isFixedRestTime = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.USE && self.isFlowOrFlex();
            });
            self.isFlexOrFlowNotUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.NOTUSE && self.isFlowOrFlex();
            });
            
            self.useFixedRestTime.subscribe((v) => {
                self.clearTabError();
                self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime(v == UseDivision.USE);
                //TODO load
//                self.dataSourceForFixedOrDiffTime(self.backUpOfFixedOrDiffTime);
                if (v) {
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.updateData(self.backUpOfFlowOrFlexNotUse);
                }
                else {
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.updateData(self.backUpOfFlowOrFlexUse);
                }
                self.updateDataModel();
            });
            
            //load data to screen 
//            self.setDataFlexOrFlowToModel();
            isLoading.subscribe((isDone: boolean) => {
                if (isDone) {
                    self.updateDataModel();
                    self.setDataFlexOrFlowToModel();
//                    self.backUpOfFixedOrDiffTime = self.dataSourceForFixedOrDiffTime();
                    self.backUpOfFlowOrFlexUse = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.toDto();
                    self.backUpOfFlowOrFlexNotUse = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.toDto();
                }
            });
            
            
        }
        
        private clearTabError() {
            $('#nts-fix-table-a7-fixed-difftime').find('.time-range-editor').ntsError('clear');
            $('#nts-fix-table-a7-flow-flex-use').find('.time-range-editor').ntsError('clear');
            $('#nts-fix-table-a7-flow-flex-notuse-1').find('.nts-editor').ntsError('clear');
            $('#nts-fix-table-a7-flow-flex-notuse-2').find('.nts-editor').ntsError('clear');
        }

        private loadDataToScreen() {
            let self = this;
            self.dataSourceForFixedOrDiffTime.subscribe((newDataSource: any) => {
                if (self.mainSettingModel.workTimeSetting.isFixed()) {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.lstTimezone(listDeductionTimeModel);
                }

                if (self.mainSettingModel.workTimeSetting.isDiffTime()) {
                    let listDiffTimeDeductTimezoneModel: DiffTimeDeductTimezoneModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DiffTimeDeductTimezoneModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        //is update start time ??
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
                self.isCheckFollowTime = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.useHereAfterRestSet;

                self.dataSourceForFlowOrFlexUse.subscribe((newDataSource: any) => {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones(listDeductionTimeModel);
                });

                self.dataSourceForFlowOrFlexNotUse1.subscribe((newData: any) => {
                    let listFlowRestSettingModel: FlowRestSettingModel[] = [];
                    for (let item of newData) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(item.column2());
                        rest.flowPassageTime(item.column1());
                        listFlowRestSettingModel.push(rest);
                    }
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets(listFlowRestSettingModel);
                });

                self.dataSourceForFlowOrFlexNotUse2.subscribe((newData: any) => {
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
                self.isCheckFollowTime = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.useHereAfterRestSet;

                self.dataSourceForFlowOrFlexUse.subscribe((newDataSource: any) => {
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for (let item of newDataSource) {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.timezones(listDeductionTimeModel);
                });

                self.dataSourceForFlowOrFlexNotUse1.subscribe((newData: any) => {
                    let listFlowRestSettingModel: FlowRestSettingModel[] = [];
                    for (let item of newData) {
                        let rest = new FlowRestSettingModel();
                        rest.flowRestTime(item.column2());
                        rest.flowPassageTime(item.column1());
                        listFlowRestSettingModel.push(rest);
                    }
                    self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.flowRestSets(listFlowRestSettingModel);
                });

                self.dataSourceForFlowOrFlexNotUse2.subscribe((newData: any) => {
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
            if (self.isFlowOrFlex()) {
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
                    self.dataSourceForFlowOrFlexUse(data);

                    for (let item of self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets()) {
                        data1.push({
                            column1: ko.observable(item.flowPassageTime()),
                            column2: ko.observable(item.flowRestTime())
                        });
                    }
                    self.dataSourceForFlowOrFlexNotUse1(data1);

                    let item = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.hereAfterRestSet;
                    data2.push({
                        column1: ko.observable(item.flowPassageTime()),
                        column2: ko.observable(item.flowRestTime())
                    });
                    self.dataSourceForFlowOrFlexNotUse2(data2);
                }
                //if flow
                if (self.mainSettingModel.workTimeSetting.isFlow()) {
                    let data: any = [];
                    let data1: any = [];
                    let data2: any = [];

                    for (let item of self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixedRestTimezone.timezones()) {
                        data.push({
                            column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                        });
                    }
                    self.dataSourceForFlowOrFlexUse(data);

                    for (let item of self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.flowRestSets()) {
                        data1.push({
                            column1: ko.observable(item.flowPassageTime()),
                            column2: ko.observable(item.flowRestTime())
                        });
                    }
                    self.dataSourceForFlowOrFlexNotUse1(data1);

                    let item = self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.hereAfterRestSet;
                    data2.push({
                        column1: ko.observable(item.flowPassageTime()),
                        column2: ko.observable(item.flowRestTime())
                    });
                    self.dataSourceForFlowOrFlexNotUse2(data2);
                }
            }
            else//for fixed or difftime
            {
                let data: any = [];
                if (self.mainSettingModel.workTimeSetting.isFixed()) {
                    for (let item of self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.lstTimezone()) {
                        data.push({
                            column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                        });
                    }
                }
                if (self.mainSettingModel.workTimeSetting.isDiffTime()) {
                    for (let item of self.mainSettingModel.diffWorkSetting.dayoffWorkTimezone.restTimezone.restTimezones()) {
                        data.push({
                            column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                        });
                    }
                }
                self.dataSourceForFixedOrDiffTime(data);
            }
        }

        /**
        * Update column setting
        */
        private refreshColumnSet() {
            let self = this;
            self.fixTableOptionForFixedOrDiffTime.columns = self.columnSetting();
            self.fixTableOptionForFlowOrFlexUse.columns = self.columnSetting();
            self.fixTableOptionForFlowOrFlexNotUse1.columns = self.columnSetting2();
            self.fixTableOptionForFlowOrFlexNotUse2.columns = self.columnSetting2();
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

        private columnSetting2(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"), key: "column1", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_174]',constraint: 'AttendanceTime', inputFormat: 'time',mode: 'time',enable: true}" />`,
                    cssClassName: 'column-time-editor'
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_176"), key: "column2", defaultValue: ko.observable(0), width: 107,
                    template: `<input data-bind="ntsTimeEditor: {name:'#[KMK003_176]',constraint: 'AttendanceTime',inputFormat: 'time',mode: 'time',enable: true}" />`,
                    cssClassName: 'column-time-editor',
                    enable: self.isCheckFollowTime()
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
            //            service.findWorkTimeSetByCode()    
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
                
                document.getElementById('nts-fix-table-a7-fixed-difftime').addEventListener('timerangedatachange', e => {
                    screenModel.dataSourceForFixedOrDiffTime.valueHasMutated();
                });
                
//                document.getElementById('nts-fix-table-a7-flow-flex-use').addEventListener('timerangedatachange', e => {
//                    screenModel.dataSourceForFlowOrFlexUse.valueHasMutated();
//                });
                
                isClickSave.subscribe((v) => {
                    if (screenModel.mainSettingModel.workTimeSetting.isFlex()) {
                        screenModel.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime(screenModel.useFixedRestTime() == UseDivision.USE);
                    }
                    if (screenModel.mainSettingModel.workTimeSetting.isFlow()) {
                        screenModel.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.fixRestTime(screenModel.useFixedRestTime() == UseDivision.USE);
                    }
                    if (v) {
                        screenModel.dataSourceForFlowOrFlexNotUse1.valueHasMutated();
                        screenModel.dataSourceForFlowOrFlexNotUse2.valueHasMutated();
                    }
                    
                });

                isClickNew.subscribe((v) => {
                    if (v) {
                        screenModel.dataSourceForFixedOrDiffTime([]);
                        screenModel.dataSourceForFlowOrFlexUse([]);
                        screenModel.dataSourceForFlowOrFlexNotUse1([]);
                        screenModel.dataSourceForFlowOrFlexNotUse2([{
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