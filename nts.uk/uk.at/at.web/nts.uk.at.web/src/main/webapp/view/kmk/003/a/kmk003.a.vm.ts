module nts.uk.at.view.kmk003.a {

    import SimpleWorkTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.SimpleWorkTimeSettingDto;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;

    import FlexWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.flexset.FlexWorkSettingDto;
    
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    
    import WorkTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeSettingModel;
    import PredetemineTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.predset.PredetemineTimeSettingModel;
    import FixedWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixedWorkSettingModel;
    import FlowWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlWorkSettingModel;
    import DiffTimeWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeWorkSettingModel;
    import FlexWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexWorkSettingModel;
    
    import FlexWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlexWorkSettingSaveCommand;
    
    export module viewmodel {

        export class ScreenModel {

            workFormOptions: KnockoutObservableArray<ItemWorkForm>;
            selectedWorkForm: KnockoutObservable<string>;

            settingMethodOptions: KnockoutObservableArray<ItemSettingMethod>;
            selectedSettingMethod: KnockoutObservable<string>;
            isRegularMode: KnockoutObservable<boolean>;

            workTimeSettings: KnockoutObservableArray<SimpleWorkTimeSettingDto>;
            columnWorktimeSettings: KnockoutObservable<any>;
            selectedWorkTimeCode: KnockoutObservable<string>;

            siftCode: KnockoutObservable<string>;
            
            

            siftName: KnockoutObservable<string>;
            

            siftShortName: KnockoutObservable<string>;
            

            siftSymbolName: KnockoutObservable<string>;
           

            //color
            pickColor: KnockoutObservable<string>;

            siftRemark: KnockoutObservable<string>;

            memo: KnockoutObservable<string>;
            

            //tab mode
            tabModeOptions: KnockoutObservableArray<any>;
            tabMode: KnockoutObservable<number>;

            //use half day
            useHalfDayOptions: KnockoutObservableArray<any>;
            useHalfDay: KnockoutObservable<string>;

            //tabs
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;

            //data
            isClickSave: KnockoutObservable<boolean>;
            
            mainSettingModel: MainSettingModel;
            
            workTimeSettingModel: WorkTimeSettingModel;
            predetemineTimeSettingModel: PredetemineTimeSettingModel;
            
            settingEnum: WorkTimeSettingEnumDto;
            dataModelOneDay: EmTimeZoneSetModel[];
            constructor() {
                let self = this;
                self.workFormOptions = ko.observableArray([
                    new ItemWorkForm('1', '通常勤務・変形労働用'),
                    new ItemWorkForm('2', 'フレックス勤務用')
                ]);
                self.selectedWorkForm = ko.observable('1');
                self.settingMethodOptions = ko.observableArray([
                    new ItemSettingMethod('1', "固定勤務"),
                    new ItemSettingMethod('2', "時差勤務"),
                    new ItemSettingMethod('3', "流動勤務")
                ]);
                self.selectedSettingMethod = ko.observable('1');
                
                
                self.workTimeSettings = ko.observableArray([]);
                self.columnWorktimeSettings = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'worktimeCode', width: 100 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'workTimeName', width: 130 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'description', width: 50 }
                ]);
                self.selectedWorkTimeCode = ko.observable('');


                self.siftCode = ko.observable('');
                self.siftName = ko.observable('');
                self.siftShortName = ko.observable('');

                self.siftSymbolName = ko.observable('');

                //color
                self.pickColor = ko.observable('');

                self.siftRemark = ko.observable('');
               

                self.memo = ko.observable('');

                //tab mode
                self.tabModeOptions = ko.observableArray([
                    { code: TabMode.SIMPLE, name: nts.uk.resource.getText("KMK003_190") },
                    { code: TabMode.DETAIL, name: nts.uk.resource.getText("KMK003_191") }
                ]);

                self.tabMode = ko.observable(TabMode.DETAIL);

                //use half day

                self.useHalfDayOptions = ko.observableArray([
                    { code: "1", name: nts.uk.resource.getText("KMK003_49") },
                    { code: "0", name: nts.uk.resource.getText("KMK003_50") }
                ]);

                self.useHalfDay = ko.observable("0");
                
                self.useHalfDay.subscribe(function(userHalfDay) {
                    self.mainSettingModel.flexWorkSetting.useHalfDayShift(userHalfDay === "1");
                });

                //
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK003_17"), content: '.tab-a1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK003_18"), content: '.tab-a2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK003_89"), content: '.tab-a3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: nts.uk.resource.getText("KMK003_19"), content: '.tab-a4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-5', title: nts.uk.resource.getText("KMK003_20"), content: '.tab-a5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-6', title: nts.uk.resource.getText("KMK003_90"), content: '.tab-a6', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-7', title: nts.uk.resource.getText("KMK003_21"), content: '.tab-a7', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-8', title: nts.uk.resource.getText("KMK003_200"), content: '.tab-a8', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-9', title: nts.uk.resource.getText("KMK003_23"), content: '.tab-a9', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-10', title: nts.uk.resource.getText("KMK003_24"), content: '.tab-a10', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-11', title: nts.uk.resource.getText("KMK003_25"), content: '.tab-a11', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-12', title: nts.uk.resource.getText("KMK003_26"), content: '.tab-a12', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-13', title: nts.uk.resource.getText("KMK003_27"), content: '.tab-a13', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-14', title: nts.uk.resource.getText("KMK003_28"), content: '.tab-a14', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-15', title: nts.uk.resource.getText("KMK003_29"), content: '.tab-a15', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-16', title: nts.uk.resource.getText("KMK003_30"), content: '.tab-a16', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');

                //data get from service
                self.isClickSave = ko.observable(false);
                
                self.mainSettingModel = new MainSettingModel();
                //TODO: xoa model khong dung den
                self.workTimeSettingModel = self.mainSettingModel.workTimeSetting;
                self.predetemineTimeSettingModel = self.mainSettingModel.predetemineTimeSetting;
                self.selectedWorkTimeCode.subscribe(function(worktimeCode: string){
                   self.updateWorktimeCode(worktimeCode); 
                });
                self.dataModelOneDay = [];
                
                self.isRegularMode = ko.computed(function() {
                    return self.workTimeSettingModel.workTimeDivision.workTimeDailyAtr() == EnumWorkForm.REGULAR;
                });
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.getEnumWorktimeSeting().done(function(setting) {
                    self.settingEnum = setting;
                    service.findAllWorkTimeSet().done(function(worktime) {
                        self.workTimeSettings(worktime);
                        if (worktime && worktime.length > 0) {
                            self.selectedWorkTimeCode(worktime[0].worktimeCode);
                            dfd.resolve();
                        }
                    });
                });
                
                
                // set ntsFixedTable style
                return dfd.promise();
            }

            private updateWorktimeCode(worktimeCode: string): JQueryPromise<void> {
                var self = this;
                let dfd = $.Deferred<void>();
                service.findWorktimeSetingInfoByCode(worktimeCode).done(function(worktimeSettingInfo) {
                    self.workTimeSettingModel.updateData(worktimeSettingInfo.worktimeSetting);
                    self.predetemineTimeSettingModel.updateData(worktimeSettingInfo.predseting);
                    /*service.findByCodeFlexWorkSetting(worktimeCode).done(function(flexdata) {
                        if (flexdata) {
                            self.updateDataFlexMode(flexdata);
                        }
                    });*/
                   dfd.resolve();
                });
                return dfd.promise();
            }
            
            
            private save() {
                let self = this;
                /*let data = self.data();
                self.tabMode('2');
                self.isClickSave(true);
                service.savePred(data).done(function() {
                    self.isClickSave(false);
                });*/
                service.saveFlexWorkSetting(self.collectDataFlex()).done(function() {

                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            
            /**
             * function get flow mode by selection ui
             */
            private getFlowModeBySelected(selectedSettingMethod: string): boolean {
                return (selectedSettingMethod === '3');
            }
            
            /**
             * function collection data flex mode 
             */
            private collectDataFlex(): FlexWorkSettingSaveCommand{
                var self = this;
                var command: FlexWorkSettingSaveCommand;
                command = {
                    flexWorkSetting: self.mainSettingModel.flexWorkSetting.toDto(),
                    predseting: self.predetemineTimeSettingModel.toDto(),
                    worktimeSetting: self.workTimeSettingModel.toDto()
                };
                return command;     
            }
            
            /**
             * update data by flex mode
             */
            private updateDataFlexMode(data: FlexWorkSettingDto) {
                var self = this;
                //self.mainSettingModel.flexWorkSetting.updateData(data);
                if (data.useHalfDayShift) {
                    self.useHalfDay('1');
                } else {
                    self.useHalfDay('0');
                }
            }
          
        }
        
        export class ItemWorkForm {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemSettingMethod {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        /**
         * Store all Setting Model, use for tab data binding
         */
        export class MainSettingModel {
            workTimeSetting: WorkTimeSettingModel;
            predetemineTimeSetting: PredetemineTimeSettingModel;
            fixedWorkSetting: FixedWorkSettingModel;
            flowWorkSetting: FlowWorkSettingModel;
            diffWorkSetting: DiffTimeWorkSettingModel;
            flexWorkSetting: FlexWorkSettingModel;
            
            constructor() {
                this.workTimeSetting = new WorkTimeSettingModel();
                this.predetemineTimeSetting = new PredetemineTimeSettingModel();
                this.fixedWorkSetting = new FixedWorkSettingModel();
                this.flowWorkSetting = new FlowWorkSettingModel();
                this.diffWorkSetting = new DiffTimeWorkSettingModel();
                this.flexWorkSetting = new FlexWorkSettingModel();
            }
        }
        
        export enum EnumWorkForm {
            REGULAR,
            FLEX
        }
        
        export enum SettingMethod {
            FIXED,
            DIFFTIME,
            FLOW
        }
        
        export enum TabMode {
            SIMPLE,
            DETAIL
        }
    }
}