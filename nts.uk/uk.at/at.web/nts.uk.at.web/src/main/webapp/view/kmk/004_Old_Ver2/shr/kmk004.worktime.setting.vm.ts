module nts.uk.at.view.kmk004.shr.worktime.setting {
    export module viewmodel {
        import DeformationLaborSetting = nts.uk.at.view.kmk004.shared.model.DeformationLaborSetting;   
        import FlexSetting = nts.uk.at.view.kmk004.shared.model.FlexSetting;   
        import FlexDaily = nts.uk.at.view.kmk004.shared.model.FlexDaily;   
        import FlexMonth = nts.uk.at.view.kmk004.shared.model.FlexMonth;   
        import NormalSetting = nts.uk.at.view.kmk004.shared.model.NormalSetting;   
        import WorkingTimeSetting = nts.uk.at.view.kmk004.shared.model.WorkingTimeSetting; 
        import Monthly = nts.uk.at.view.kmk004.shared.model.Monthly;   
        import WorktimeSettingDto = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDto; 
        import WorktimeNormalDeformSetting = nts.uk.at.view.kmk004.shared.model.WorktimeNormalDeformSetting;   
        import WorktimeFlexSetting1 = nts.uk.at.view.kmk004.shared.model.WorktimeFlexSetting1; 
        import NormalWorktime = nts.uk.at.view.kmk004.shared.model.NormalWorktime; 
        import FlexWorktimeAggrSetting = nts.uk.at.view.kmk004.shared.model.FlexWorktimeAggrSetting;
        import WorktimeSettingDtoSaveCommand = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDtoSaveCommand;
        import NormalWorktimeAggrSetting = nts.uk.at.view.kmk004.shared.model.NormalWorktimeAggrSetting;
        import DeformWorktimeAggrSetting = nts.uk.at.view.kmk004.shared.model.DeformWorktimeAggrSetting;
        import MonthlyCalSettingDto = nts.uk.at.view.kmk004.shared.model.MonthlyCalSettingDto;
        import StatutoryWorktimeSettingDto = nts.uk.at.view.kmk004.shared.model.StatutoryWorktimeSettingDto;
        import NormalSetParams = nts.uk.at.view.kmk004.shared.model.NormalSetParams ;
        import FlexSetParams = nts.uk.at.view.kmk004.shared.model.FlexSetParams;
        import DeformSetParams = nts.uk.at.view.kmk004.shared.model.DeformSetParams ;
        import SelectedSettingType = nts.uk.at.view.kmk004.shared.model.SelectedSettingType;
        import ItemModelNumber = nts.uk.at.view.kmk004.shared.model.ItemModelNumber;
        import ReferencePredTimeOfFlex = nts.uk.at.view.kmk004.shared.model.ReferencePredTimeOfFlex;
        import FlexMonthlyTime = nts.uk.at.view.kmk004.shared.model.FlexMonthlyTime;
        import PureFlexMonthlyTime = nts.uk.at.view.kmk004.shared.model.PureFlexMonthlyTime;
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            baseDate: KnockoutObservable<Date>;
            groupYear: KnockoutObservable<number>;

            // Start month.
            startMonth: KnockoutObservable<number>;
            
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
            aggrSelectionItemList: KnockoutObservableArray<any>;// FlexAggregateMethod フレックス集計方法
            selectedAggrSelection: KnockoutObservable<number>;
            
            worktimeSetting: WorktimeSetting;
            
            constructor() {
                let self = this;
                self.isNewMode = ko.observable(true);
                self.isLoading = ko.observable(true);
                
                // Datasource.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.baseDate = ko.observable(new Date());
                
                self.worktimeSetting = new WorktimeSetting();
                
                let userId = __viewContext.user.employeeId;
                let year = nts.uk.sessionStorage.nativeStorage.getItem("nts-uk-" + userId + "-kmk004-worktime-year-selection");
                if (!nts.uk.util.isNullOrEmpty(year) && "null" != year) {
                    year = parseInt(year);
                    self.worktimeSetting.normalSetting().year(year);
                } else {
                	year = new Date().getFullYear();
                }
                self.groupYear = ko.observable(year);
                
                self.groupYear.subscribe((v) => {
                    self.worktimeSetting.updateYear(v);
                    if (nts.uk.util.isNullOrEmpty(v) || $('#worktimeYearPicker').ntsError('hasError')) {
                        return;
                    } else {
                        nts.uk.sessionStorage.nativeStorage.setItem("nts-uk-" + userId + "-kmk004-worktime-year-selection", v);
                    }
                });

                
                // Update
                self.aggrSelectionItemList = ko.observableArray([
                    { id: 0, name: nts.uk.resource.getText("KMK004_51")},
                    { id: 1, name: nts.uk.resource.getText("KMK004_52")}
                ]);
                self.selectedAggrSelection = ko.observable(1);
                
                //==================UPDATE==================
                
            }
            
            /**
             * Initialize
             */
            public initialize(): JQueryPromise<void> {
                return this.setStartMonth();
            }
            
            public postBindingHandler(): void {
                let self = this;
                self.processTabToContent($('#worktime-tab-1').closest('div#tab-panel'), 'tab-1');
                
                self.processNextTabHandler($('#worktime-tab-1'), 'tab-2');
                self.processNextTabHandler($('#worktime-tab-2'), 'tab-3');
                
                self.processPrevTabHandler($('#worktime-tab-2'), 'tab-1');
                self.processPrevTabHandler($('#worktime-tab-3'), 'tab-2');

                $('#worktimeYearPicker').focus();
            }
            
            private processTabToContent(obj:any, tabId:any) {
                let self = this;
                let targTab = $('#worktime-' + tabId);
                obj.keydown((evt) => {
                    if (evt.which == 9 && !evt.shiftKey && evt.originalEvent.target == obj[0]) {
                        self.worktimeSetting.selectedTab(tabId);
                        targTab.find('input').first().focus();
                        
                        evt.stopPropagation();
                        return false;
                    }
                });
            }
            
            private processNextTabHandler(tabObj:any, nextTabId: string): void {
                let self = this;
                let targTab = $('#worktime-' + nextTabId);
                tabObj.keydown((evt) => {
                    if (evt.which == 9 && !evt.shiftKey) {
                        let inp = $(evt.target);
                        if (inp.closest('td').is(':last-child') && inp.closest('tr').is(':last-child')) {
                            self.worktimeSetting.selectedTab(nextTabId);
                            targTab.find('input[tabindex="' + (parseInt(inp.attr('tabindex')) + 1) + '"]').focus();
                            
                            evt.stopPropagation();
                            return false;
                        }
                    }
                });
            }
            
            private processPrevTabHandler(tabObj:any, prevTabId: string): void {
                let self = this;
                let targTab = $('#worktime-' + prevTabId);
                tabObj.keydown((evt) => {
                    if (evt.which == 9 && evt.shiftKey) {
                        let inp = $(evt.target);
                        if (inp.closest('td').is(':nth-child(2)') && inp.closest('tr').is(':nth-child(2)')) {
                            self.worktimeSetting.selectedTab(prevTabId);
                            targTab.find('input[tabindex="' + (parseInt(inp.attr('tabindex')) - 1) + '"]').focus();
                            
                            evt.stopPropagation();
                            return false;
                        }
                    }
                });
            }
            
            /**
             * Set start month.
             */
            private setStartMonth(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.getStartMonth().done(res => {
                    if (res.startMonth) {
                        self.startMonth = ko.observable(res.startMonth);
                    } else {
                        // Default startMonth..
                        self.startMonth = ko.observable(1);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * Check validate all input.
             */
            public hasError(): boolean {
                return $('.nts-editor').ntsError('hasError');
            }
                
            
        } // --- end ScreenModel
        
        export function getStartMonth(): JQueryPromise<number> {
            let self = this;
            let dfd = $.Deferred<number>();
            service.getStartMonth().done(res => {
                let month = 1;
                if (res.startMonth) {
                    month = res.startMonth;
                }
                dfd.resolve(month);
            });
            return dfd.promise();
        }
        
        
        /**
             * Company Worktime Setting (Tab 1)
             */
        export class WorktimeSetting {
            // Selected Tab
            selectedTab: KnockoutObservable<string>;
            // 会社別通常勤務労働時間
            normalWorktime: KnockoutObservable<NormalWorktime>;
            // 会社別変形労働労働時間
            deformLaborWorktime: KnockoutObservable<NormalWorktime>;
    
            // 会社別通常勤務月間労働時間
            normalSetting: KnockoutObservable<WorktimeNormalDeformSetting>;
            // 会社別フレックス勤務月間労働時間
            //            flexSetting: KnockoutObservable<WorktimeFlexSetting>;
            flexSetting: KnockoutObservable<WorktimeFlexSetting1>;
            // 会社別変形労働月間労働時間
            deformLaborSetting: KnockoutObservable<WorktimeNormalDeformSetting>;
    
            // Details
            // 通常勤務労働会社別月別実績集計設定
            normalAggrSetting: KnockoutObservable<NormalWorktimeAggrSetting>;
            // 変形労働会社別月別実績集計設定
            deformAggrSetting: KnockoutObservable<DeformWorktimeAggrSetting>;
            // フレックス会社別月別実績集計設定
            flexAggrSetting: KnockoutObservable<FlexWorktimeAggrSetting>;
                        
            // Com Flex TAB Get Pred
            optReferenceFlexPred: KnockoutObservableArray<any>;
            referenceFlexPred: KnockoutObservable<number>;
            originFlexMonthlyTime: PureFlexMonthlyTime[];
    
            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.normalWorktime = ko.observable(new NormalWorktime());
                self.deformLaborWorktime = ko.observable(new NormalWorktime());
    
                self.normalSetting = ko.observable(new WorktimeNormalDeformSetting());
                self.flexSetting = ko.observable(new WorktimeFlexSetting1());
                self.deformLaborSetting = ko.observable(new WorktimeNormalDeformSetting());
    
                self.normalAggrSetting = ko.observable(new NormalWorktimeAggrSetting());
                self.deformAggrSetting = ko.observable(new DeformWorktimeAggrSetting());
                self.flexAggrSetting = ko.observable(new FlexWorktimeAggrSetting());
                
                // Com Flex TAB Get Pred
                self.optReferenceFlexPred = ko.observableArray([
                    new ItemModelNumber(ReferencePredTimeOfFlex.FROM_MASTER, nts.uk.resource.getText("KMK004_147")),
                    new ItemModelNumber(ReferencePredTimeOfFlex.FROM_RECORD, nts.uk.resource.getText("KMK004_148"))
                ]);
                self.referenceFlexPred = ko.observable(ReferencePredTimeOfFlex.FROM_MASTER);
            }
            
            public setReferenceFlexPred(value? : number) : void {
                if(!nts.uk.util.isNullOrEmpty(value)) {
                    this.referenceFlexPred(value);
                }    
            }
                        
            public resetFlexSpecifiedTime(): void {
                let specifiedTime : FlexMonthlyTime[] = this.flexSetting().flexSettingDetail();
                specifiedTime.forEach((monthlyTime: FlexMonthlyTime) => {
                    let origin: PureFlexMonthlyTime = _.find(this.originFlexMonthlyTime, (originMonthlyTime) => originMonthlyTime.month == monthlyTime.month());
                    monthlyTime.specifiedTime(origin.specifiedTime);
                });
                // clear input monthly error
                $('#tab-2 .tbl-monthly tr td:nth-child(2) input').ntsError('clear');
                
            }
    
            public sortMonth(startMonth: number): void {
                let self = this;
                self.normalSetting().sortMonth(startMonth);
                self.flexSetting().sortMonth(startMonth);
                self.deformLaborSetting().sortMonth(startMonth);
            }
            //WorktimeSettingDto
            public updateDataDependOnYear(dto: StatutoryWorktimeSettingDto): void {
                let self = this;
                self.normalWorktime().updateData(dto.regularLaborTime);
                self.deformLaborWorktime().updateData(dto.transLaborTime);
    
                self.normalSetting().updateData(dto, SelectedSettingType.NORMAL_SETTING);
                self.flexSetting().updateData(dto);
                self.deformLaborSetting().updateData(dto, SelectedSettingType.DEFORM_LABOR_SETTING);
                self.originFlexMonthlyTime = ko.toJS(self.flexSetting().flexSettingDetail());
            }
    
            public updateDetailData(dto: MonthlyCalSettingDto): void {
                let self = this;
                // Update Detail Data
                self.normalAggrSetting().updateData(dto.regAggrSetting);
                self.deformAggrSetting().updateData(dto.deforAggrSetting);
                self.flexAggrSetting().updateData(dto.flexAggrSetting);
            }
            // Update Full Data: 8 Models
            public updateFullData(dto: WorktimeSettingDto): void {
                let self = this;
                self.updateDataDependOnYear(dto.statWorkTimeSetDto);
                self.updateDetailData(dto.monthCalSetDto);
            }
    
            public updateYear(year: number): void {
                let self = this;
                self.normalSetting().year(year);
                self.flexSetting().year(year);
                self.deformLaborSetting().year(year);
            }
    
            public gotoF(): void {
                let self = this;
                let params: NormalSetParams = new NormalSetParams();
                // F1_2
                params.startWeek = self.normalWorktime().startWeek();
                // F2_3
                params.isIncludeExtraAggr = self.normalAggrSetting().aggregateOutsideTimeSet.includeExtra();
                // F2_8
                params.isIncludeLegalAggr = self.normalAggrSetting().aggregateOutsideTimeSet.includeLegal();
                // F2_12
                params.isIncludeHolidayAggr = self.normalAggrSetting().aggregateOutsideTimeSet.includeHoliday();
    
                // F2_16
                params.isIncludeExtraExcessOutside = self.normalAggrSetting().excessOutsideTimeSet.includeExtra();
                // F2_21
                params.isIncludeLegalExcessOutside = self.normalAggrSetting().excessOutsideTimeSet.includeLegal();
                // F2_25
                params.isIncludeHolidayExcessOutside = self.normalAggrSetting().excessOutsideTimeSet.includeHoliday();
    
                nts.uk.ui.windows.setShared('NORMAL_SET_PARAM', params, true);
    
                nts.uk.ui.windows.sub.modal("/view/kmk/004/f/index.xhtml").onClosed(() => {
                    $('#worktimeYearPicker').focus();
    
                    // Get params
                    var normalSetOutput: NormalSetParams = nts.uk.ui.windows.getShared('NORMAL_SET_OUTPUT');
                    // If normalSetOutput is undefined
                    if (!normalSetOutput) {
                        return;
                    } else {
                        self.normalWorktime().startWeek(normalSetOutput.startWeek);
                        self.normalAggrSetting().aggregateOutsideTimeSet.includeExtra(normalSetOutput.isIncludeExtraAggr);
                        self.normalAggrSetting().aggregateOutsideTimeSet.includeLegal(normalSetOutput.isIncludeLegalAggr);
                        self.normalAggrSetting().aggregateOutsideTimeSet.includeHoliday(normalSetOutput.isIncludeHolidayAggr);
    
                        // F2_16
                        self.normalAggrSetting().excessOutsideTimeSet.includeExtra(normalSetOutput.isIncludeExtraExcessOutside);
                        // F2_21
                        self.normalAggrSetting().excessOutsideTimeSet.includeLegal(normalSetOutput.isIncludeLegalExcessOutside);
                        // F2_25
                        self.normalAggrSetting().excessOutsideTimeSet.includeHoliday(normalSetOutput.isIncludeHolidayExcessOutside);
                    }
                });
            }
    
            public gotoG(): void {
                let self = this;
                let params: FlexSetParams = new FlexSetParams();
                params.isEnableOverTime = self.flexAggrSetting().aggregateMethod() == 0 ? true : false;
                params.isIncludeOverTime = self.flexAggrSetting().includeOT(); // G1_2
                params.shortageSetting = self.flexAggrSetting().shortageSetting();// G2_2
    
                nts.uk.ui.windows.setShared('FLEX_SET_PARAM', params, true);
    
                nts.uk.ui.windows.sub.modal("/view/kmk/004/g/index.xhtml").onClosed(() => {
                    $('#worktimeYearPicker').focus();
    
                    // get params from dialog 
                    var flexSetOutput: FlexSetParams = nts.uk.ui.windows.getShared('FLEX_SET_OUTPUT');
                    // If FLEXSetOutput is undefined
                    if (!flexSetOutput) {
                        return;
                    } else {
                        self.flexAggrSetting().includeOT(flexSetOutput.isIncludeOverTime);
                        self.flexAggrSetting().shortageSetting(flexSetOutput.shortageSetting);
                    }
                });
            }
    
    
            public gotoH(): void {
    
                let self = this;
                let params: DeformSetParams = new DeformSetParams();
    
                params.strMonth = self.deformAggrSetting().startMonth();
                params.period = self.deformAggrSetting().period();
                params.repeatCls = self.deformAggrSetting().repeatCls();
                // H1_1
                params.startWeek = self.deformLaborWorktime().startWeek();
                // // H3_3
                params.isIncludeExtraAggr = self.deformAggrSetting().aggregateOutsideTimeSet.includeExtra();
                // H3_8
                params.isIncludeLegalAggr = self.deformAggrSetting().aggregateOutsideTimeSet.includeLegal();
                // H3_12
                params.isIncludeHolidayAggr = self.deformAggrSetting().aggregateOutsideTimeSet.includeHoliday();
    
                // H3_16
                params.isIncludeExtraExcessOutside = self.deformAggrSetting().excessOutsideTimeSet.includeExtra();
                // H3_21
                params.isIncludeLegalExcessOutside = self.deformAggrSetting().excessOutsideTimeSet.includeLegal();
                // H3_25
                params.isIncludeHolidayExcessOutside = self.deformAggrSetting().excessOutsideTimeSet.includeHoliday();
    
                nts.uk.ui.windows.setShared('DEFORM_SET_PARAM', params, true);
    
                nts.uk.ui.windows.sub.modal("/view/kmk/004/h/index.xhtml").onClosed(() => {
                    $('#worktimeYearPicker').focus();
    
                    // Get params
                    var deformSetOutput: DeformSetParams = nts.uk.ui.windows.getShared('DEFORM_SET_OUTPUT');
                    // If deformSetOutput is undefined
                    if (!deformSetOutput) {
                        return;
                    } else {
                        self.deformAggrSetting().startMonth(deformSetOutput.strMonth);
                        self.deformAggrSetting().period(deformSetOutput.period);
                        self.deformAggrSetting().repeatCls(deformSetOutput.repeatCls);
                        //
                        self.deformLaborWorktime().startWeek(deformSetOutput.startWeek);
                        self.deformAggrSetting().aggregateOutsideTimeSet.includeExtra(deformSetOutput.isIncludeExtraAggr);
                        self.deformAggrSetting().aggregateOutsideTimeSet.includeLegal(deformSetOutput.isIncludeLegalAggr);
                        self.deformAggrSetting().aggregateOutsideTimeSet.includeHoliday(deformSetOutput.isIncludeHolidayAggr);
    
                        self.deformAggrSetting().excessOutsideTimeSet.includeExtra(deformSetOutput.isIncludeExtraExcessOutside);
                        self.deformAggrSetting().excessOutsideTimeSet.includeLegal(deformSetOutput.isIncludeLegalExcessOutside);
                        self.deformAggrSetting().excessOutsideTimeSet.includeHoliday(deformSetOutput.isIncludeHolidayExcessOutside);
                    }
                });
            }
        }
    
    }
}