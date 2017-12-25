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
            useHalfDay: KnockoutObservable<number>;

            //tabs
            tabs: KnockoutObservableArray<TabItem>;
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
                self.tabMode.subscribe(newValue => {
                    if (newValue === TabMode.DETAIL) {
                        self.changeTabMode(true);
                    } else {                       
                        self.changeTabMode(false);
                    }   
                });
                
                //use half day

                self.useHalfDayOptions = ko.observableArray([
                    { code: HalfDayEnum.USE, name: nts.uk.resource.getText("KMK003_49") },
                    { code: HalfDayEnum.NOT_USE, name: nts.uk.resource.getText("KMK003_50") }
                ]);

                self.useHalfDay = ko.observable(HalfDayEnum.NOT_USE);
                
                self.useHalfDay.subscribe(function(userHalfDay) {
                    self.mainSettingModel.fixedWorkSetting.useHalfDayShift(userHalfDay === HalfDayEnum.USE);
                    self.mainSettingModel.flexWorkSetting.useHalfDayShift(userHalfDay === HalfDayEnum.USE);
                    self.mainSettingModel.diffWorkSetting.isUseHalfDayShift(userHalfDay === HalfDayEnum.USE);
                });

                //
                self.tabs = ko.observableArray([]);
                self.tabs.push(new TabItem('tab-1', nts.uk.resource.getText("KMK003_17"), '.tab-a1', true, true));
                self.tabs.push(new TabItem('tab-2', nts.uk.resource.getText("KMK003_18"), '.tab-a2', true, true));
                self.tabs.push(new TabItem('tab-3', nts.uk.resource.getText("KMK003_89"), '.tab-a3', true, true));
                self.tabs.push(new TabItem('tab-4', nts.uk.resource.getText("KMK003_19"), '.tab-a4', true, true));
                self.tabs.push(new TabItem('tab-5', nts.uk.resource.getText("KMK003_20"), '.tab-a5', true, true));
                self.tabs.push(new TabItem('tab-6', nts.uk.resource.getText("KMK003_90"), '.tab-a6', true, true));
                self.tabs.push(new TabItem('tab-7', nts.uk.resource.getText("KMK003_21"), '.tab-a7', true, true));
                self.tabs.push(new TabItem('tab-8', nts.uk.resource.getText("KMK003_200"), '.tab-a8', true, true));
                self.tabs.push(new TabItem('tab-9', nts.uk.resource.getText("KMK003_23"), '.tab-a9', true, true));
                self.tabs.push(new TabItem('tab-10', nts.uk.resource.getText("KMK003_24"), '.tab-a10', true, true));
                self.tabs.push(new TabItem('tab-11', nts.uk.resource.getText("KMK003_25"), '.tab-a11', true, true));
                self.tabs.push(new TabItem('tab-12', nts.uk.resource.getText("KMK003_26"), '.tab-a12', true, true));
                self.tabs.push(new TabItem('tab-13', nts.uk.resource.getText("KMK003_27"), '.tab-a13', true, true));
                self.tabs.push(new TabItem('tab-14', nts.uk.resource.getText("KMK003_28"), '.tab-a14', true, true));
                self.tabs.push(new TabItem('tab-15', nts.uk.resource.getText("KMK003_29"), '.tab-a15', true, true));
                self.tabs.push(new TabItem('tab-16', nts.uk.resource.getText("KMK003_30"), '.tab-a16', true, true));
                
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
//                    service.findAllWorkTimeSet().done(function(worktime) {
//                        self.workTimeSettings(worktime);
//                        if (worktime && worktime.length > 0) {
//                            self.selectedWorkTimeCode(worktime[0].worktimeCode);
                            dfd.resolve();
//                        }
//                    });
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
            
            /**
             * Change tab mode
             */
            private changeTabMode(isDetail: boolean): void {
                let _self = this;
                if (isDetail) {
                    _.forEach(_self.tabs(), tab => tab.setVisible(true));
                } else {
                    let simpleTabsId: string[] = ['tab-1','tab-2','tab-3','tab-4','tab-5','tab-6','tab-7','tab-9','tab-10','tab-11','tab-12'];
                    _.forEach(_self.tabs(), tab => {
                        if (_.findIndex(simpleTabsId, id => tab.id === id) === -1) {
                            tab.setVisible(false);
                        } else {
                            tab.setVisible(true);
                        }                        
                    });
                }
            }
            
            private save() {
                let self = this;
                /*let data = self.data();
                self.tabMode('2');
                self.isClickSave(true);
                service.savePred(data).done(function() {
                    self.isClickSave(false);
                });*/
                //TODO need check mode save new or update here 
                switch(self.workTimeSettingModel.workTimeDivision.workTimeDailyAtr()){
                    case EnumWorkForm.REGULAR: 
                        switch (self.workTimeSettingModel.workTimeDivision.workTimeMethodSet()) {
                            case SettingMethod.FIXED: 
                                service.saveFixedWorkSetting(self.collectDataFixed()).done(function() {

                                }).fail(function(error) {
                                    nts.uk.ui.dialog.alertError(error);
                                });
                                break;
                            //for flow and difftime
                            case SettingMethod.FIXED: break;
                            case SettingMethod.FIXED: break;
                            default: break;
                        }
                        break;
                    case EnumWorkForm.FLEX:
                        service.saveFlexWorkSetting(self.collectDataFlex()).done(function() {

                        }).fail(function(error) {
                            nts.uk.ui.dialog.alertError(error);
                        });
                        break;
                    default: break;
                }
                
            }
            
            /**
             * function get flow mode by selection ui
             */
            private getFlowModeBySelected(selectedSettingMethod: string): boolean {
                return (selectedSettingMethod === '3');
            }
            
            /**
             * function collection data fixed mode 
             */
            private collectDataFixed():any{
                var self = this;
                var command: any;
                command = {
                    flexWorkSetting: self.mainSettingModel.fixedWorkSetting.toDto(),
                    predseting: self.predetemineTimeSettingModel.toDto(),
                    worktimeSetting: self.workTimeSettingModel.toDto()
                };
                return command;  
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
                    self.useHalfDay(HalfDayEnum.USE);
                } else {
                    self.useHalfDay(HalfDayEnum.NOT_USE);
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
         * Tab Item
         */
        export class TabItem { 
            id: string;
            title: string; 
            content: string; 
            enable: KnockoutObservable<boolean>; 
            visible: KnockoutObservable<boolean>; 
            
            constructor(id: string, title: string, content: string, enable: boolean, visible: boolean) {
                this.id = id;
                this.title = title;
                this.content = content;
                this.enable = ko.observable(enable);
                this.visible = ko.observable(visible);
            }
            
            public setVisible(visible: boolean): void {
                this.visible(visible);
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
        
        /**
         * HalfDayEnum
         */
        export enum HalfDayEnum {
            NOT_USE,
            USE
        }
    }
}