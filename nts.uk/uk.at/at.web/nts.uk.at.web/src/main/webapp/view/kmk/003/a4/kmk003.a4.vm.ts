module a4 {
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import EnumWorkForm = nts.uk.at.view.kmk003.a.viewmodel.EnumWorkForm;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import PrioritySettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.PrioritySettingModel;
    import StampPiorityAtr = nts.uk.at.view.kmk003.a.service.model.common.StampPiorityAtr;
    
    class ScreenModel {
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        
        priorityOptions: KnockoutObservableArray<Item>;
        priorityGoWork: KnockoutObservable<number>;
        priorityLeaveWork: KnockoutObservable<number>;

        stampComboBoxOptions: KnockoutObservableArray<Item>;
        stampGoWork: KnockoutObservable<number>;
        stampLeaveWork: KnockoutObservable<number>;

        stampRoundingOptions: KnockoutObservableArray<Item>;
        stampRoundingGoWork: KnockoutObservable<number>;
        stampRoundingLeaveWork: KnockoutObservable<number>;


        leastWorkTime: KnockoutObservable<number>;
        morningEndTime: KnockoutObservable<number>;
        afternoonStartTime: KnockoutObservable<number>;

        oneDay: KnockoutObservable<number>;
        morning: KnockoutObservable<number>;
        afternoon: KnockoutObservable<number>;
        
        mainSettingModel: MainSettingModel;
        enumSetting: WorkTimeSettingEnumDto;
        /**
        * Constructor.
        */
        constructor(tabMode: any,enumSetting: WorkTimeSettingEnumDto,mainSettingModel: MainSettingModel) {
            let self = this;
            
            // Subscribe Detail/Simple mode 
            self.isDetailMode = ko.observable(null);
            tabMode.subscribe((value: any) => {
                value == TabMode.DETAIL ? self.isDetailMode(true) : self.isDetailMode(false);
            });
            tabMode() == TabMode.DETAIL ? self.isDetailMode(true) : self.isDetailMode(false);
            
            //main model
            self.mainSettingModel = mainSettingModel;
            self.enumSetting = enumSetting;
            
            self.priorityOptions = ko.observableArray([
                new Item(0, nts.uk.resource.getText("KMK003_69")),
                new Item(1, nts.uk.resource.getText("KMK003_70"))
            ]);

            self.priorityGoWork = ko.observable(0);
            self.priorityLeaveWork = ko.observable(0);

            let roundTimes:Item[] = [];
            enumSetting.roundingTime.forEach(function(item, index) {
                roundTimes.push(new Item(index, item.localizedName));
            });
            
            self.stampComboBoxOptions = ko.observableArray(roundTimes);

            self.stampGoWork = ko.observable(0);
            self.stampLeaveWork = ko.observable(0);

            self.stampRoundingOptions = ko.observableArray([
                new Item(0, nts.uk.resource.getText("KMK003_72")),
                new Item(1, nts.uk.resource.getText("KMK003_73"))
            ]);

            self.stampRoundingGoWork = ko.observable(0);
            self.stampRoundingLeaveWork = ko.observable(0);

            self.leastWorkTime = ko.observable(0);
            self.morningEndTime = ko.observable(0);
            self.afternoonStartTime = ko.observable(0);

            self.oneDay = ko.observable(0);
            self.morning = ko.observable(0);
            self.afternoon = ko.observable(0);
            self.bindToScreen();
        }
        
        public bindToScreen() {
            let self = this;
            let workForm = self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr();

            let stamp = self.mainSettingModel.commonSetting.stampSet;
            self.priorityGoWork = stamp.getPrioritySetsGoWork().priorityAtr;
            self.priorityLeaveWork = stamp.getPrioritySetsLeaveWork().priorityAtr;
            //                
            self.stampRoundingGoWork = stamp.getRoundingSetsAttendance().roundingSet.roundingTimeUnit;
            self.stampRoundingLeaveWork = stamp.getRoundingSetsOfficeWork().roundingSet.roundingTimeUnit;
            
            self.stampGoWork = stamp.getRoundingSetsAttendance().roundingSet.fontRearSection;
            self.stampLeaveWork = stamp.getRoundingSetsOfficeWork().roundingSet.fontRearSection;
            //check mode screen
        }
        
        /**
         * Open Dialog Advance Setting
         */
        public openAdvanceSettingDialog(): void {
            let _self = this;

            nts.uk.ui.block.grayout();
            let isLinked: boolean = false;
            
            let isFlow: boolean = _self.mainSettingModel.workTimeSetting.isFlow(); 
            let isUseTime2: boolean = _self.mainSettingModel.workTimeSetting.isFlow() && _self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr(); 
            let isFixedAndUseTime2: boolean = _self.mainSettingModel.workTimeSetting.isFixed() && _self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr();          
            // Add common param
            let dataObject: any = {
                isManageEntryExit: _self.mainSettingModel.manageEntryExit.useClassification(),
                isFlow: isFlow,
                isUseTime2: isUseTime2,
                isFixedAndUseTime2: isFixedAndUseTime2,
                listRoundingTimeUnit: _self.enumSetting.roundingTimeUnit,
                
                prioritySettingEntering: _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsEnter().priorityAtr(),
                prioritySettingExit: _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsExit().priorityAtr(),
                prioritySettingPcLogin: _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsPcLogin().priorityAtr(),
                prioritySettingPcLogout: _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsPcLogout().priorityAtr(),
                goOutRoundingUnit: _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsGoOut().roundingSet.roundingTimeUnit(),
                goOutFontRearSection: _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsGoOut().roundingSet.fontRearSection(),
                turnBackRoundingUnit: _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsTurnBack().roundingSet.roundingTimeUnit(),
                turnBackFontRearSection: _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsTurnBack().roundingSet.fontRearSection()
            };
            // Add worktime mode param 
            if (_self.mainSettingModel.workTimeSetting.isFlow()) {
                dataObject.stampTwoTimeReflect = _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.twoTimesWorkReflectBasicTime();
                if (_self.mainSettingModel.isInterlockDialogJ()) {
                    let oneDayEndTime: number = _self.mainSettingModel.predetemineTimeSetting.startDateClock() + (_self.mainSettingModel.predetemineTimeSetting.rangeTimeDay());
                    dataObject.stampGoWorkFlowStart = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampGoWorkFlowEnd = oneDayEndTime;                    
                    dataObject.stampLeavingWorkFlowStart = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampLeavingWorkFlowEnd = oneDayEndTime;
                } else {                   
                    dataObject.stampGoWorkFlowStart = _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().startTime();
                    dataObject.stampGoWorkFlowEnd = _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().endTime();                    
                    dataObject.stampLeavingWorkFlowStart = _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().startTime();
                    dataObject.stampLeavingWorkFlowEnd = _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().endTime();
                }
            } 
            if (_self.mainSettingModel.workTimeSetting.isFixed()) {
                if (_self.mainSettingModel.isInterlockDialogJ()) {
                    let workStart: number = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    let workEnd: number = _self.mainSettingModel.predetemineTimeSetting.startDateClock() + _self.mainSettingModel.predetemineTimeSetting.rangeTimeDay();
                    let endWork1: number = _self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne.end();
                    let startWork2: number = _self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.start();
                    if (_self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr()) {                       
                        dataObject.stampGoWork1Start = workStart;
                        dataObject.stampGoWork1End = endWork1;
                        dataObject.stampLeavingWork1Start = workStart;
                        dataObject.stampLeavingWork1End = startWork2;
                        dataObject.stampGoWork2Start = endWork1 + 1;
                        dataObject.stampGoWork2End = workEnd;
                        dataObject.stampLeavingWork2Start = startWork2 + 1;
                        dataObject.stampLeavingWork2End = workEnd;
                    } else {
                        dataObject.stampGoWork1Start = workStart;
                        dataObject.stampGoWork1End = workEnd;
                        dataObject.stampLeavingWork1Start = workStart;
                        dataObject.stampLeavingWork1End = workEnd;
                        dataObject.stampGoWork2Start = 0;
                        dataObject.stampGoWork2End = 1440;
                        dataObject.stampLeavingWork2Start = 0;
                        dataObject.stampLeavingWork2End = 1440;
                    }                    
                } else {
                    dataObject.stampGoWork1Start = _self.mainSettingModel.fixedWorkSetting.getGoWork1Stamp().startTime();
                    dataObject.stampGoWork1End = _self.mainSettingModel.fixedWorkSetting.getGoWork1Stamp().endTime();
                    dataObject.stampLeavingWork1Start = _self.mainSettingModel.fixedWorkSetting.getLeaveWork1Stamp().startTime();
                    dataObject.stampLeavingWork1End = _self.mainSettingModel.fixedWorkSetting.getLeaveWork1Stamp().endTime();
                    dataObject.stampGoWork2Start = _self.mainSettingModel.fixedWorkSetting.getGoWork2Stamp().startTime();
                    dataObject.stampGoWork2End = _self.mainSettingModel.fixedWorkSetting.getGoWork2Stamp().endTime();
                    dataObject.stampLeavingWork2Start = _self.mainSettingModel.fixedWorkSetting.getLeaveWork2Stamp().startTime();
                    dataObject.stampLeavingWork2End = _self.mainSettingModel.fixedWorkSetting.getLeaveWork2Stamp().endTime();
                }                              
            }
            if (_self.mainSettingModel.workTimeSetting.isDiffTime()) {
                if (_self.mainSettingModel.isInterlockDialogJ()) {
                    let oneDayEndTime: number = _self.mainSettingModel.predetemineTimeSetting.startDateClock() + (_self.mainSettingModel.predetemineTimeSetting.rangeTimeDay());
                    dataObject.stampGoWork1Start = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampGoWork1End = oneDayEndTime;
                    dataObject.stampLeavingWork1Start = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampLeavingWork1End = oneDayEndTime;
                    dataObject.stampGoWork2Start = 0;
                    dataObject.stampGoWork2End = 1440;
                    dataObject.stampLeavingWork2Start = 0;
                    dataObject.stampLeavingWork2End = 1440;
                } else {
                    dataObject.stampGoWork1Start = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().startTime();
                    dataObject.stampGoWork1End = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().endTime();
                    dataObject.stampLeavingWork1Start = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().startTime();
                    dataObject.stampLeavingWork1End = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().endTime();
                    dataObject.stampGoWork2Start = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork2Stamp().startTime();
                    dataObject.stampGoWork2End = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork2Stamp().endTime();
                    dataObject.stampLeavingWork2Start = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork2Stamp().startTime();
                    dataObject.stampLeavingWork2End = _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork2Stamp().endTime();
                }                
            } 
            if (_self.mainSettingModel.workTimeSetting.isFlex()) {
                if (_self.mainSettingModel.isInterlockDialogJ()) {
                    let oneDayEndTime: number = _self.mainSettingModel.predetemineTimeSetting.startDateClock() + (_self.mainSettingModel.predetemineTimeSetting.rangeTimeDay());
                    dataObject.stampGoWork1Start = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampGoWork1End = oneDayEndTime;
                    dataObject.stampLeavingWork1Start = _self.mainSettingModel.predetemineTimeSetting.startDateClock();
                    dataObject.stampLeavingWork1End = oneDayEndTime;
                    dataObject.stampGoWork2Start = 0;
                    dataObject.stampGoWork2End = 1440;
                    dataObject.stampLeavingWork2Start = 0;
                    dataObject.stampLeavingWork2End = 1440;
                } else {
                    dataObject.stampGoWork1Start = _self.mainSettingModel.flexWorkSetting.getGoWork1Stamp().startTime();
                    dataObject.stampGoWork1End = _self.mainSettingModel.flexWorkSetting.getGoWork1Stamp().endTime();
                    dataObject.stampLeavingWork1Start = _self.mainSettingModel.flexWorkSetting.getLeaveWork1Stamp().startTime();
                    dataObject.stampLeavingWork1End = _self.mainSettingModel.flexWorkSetting.getLeaveWork1Stamp().endTime();
                    dataObject.stampGoWork2Start = _self.mainSettingModel.flexWorkSetting.getGoWork2Stamp().startTime();
                    dataObject.stampGoWork2End = _self.mainSettingModel.flexWorkSetting.getGoWork2Stamp().endTime();
                    dataObject.stampLeavingWork2Start = _self.mainSettingModel.flexWorkSetting.getLeaveWork2Stamp().startTime();
                    dataObject.stampLeavingWork2End = _self.mainSettingModel.flexWorkSetting.getLeaveWork2Stamp().endTime();
                }
            } 
            // Set object
            nts.uk.ui.windows.setShared("KMK003_DIALOG_J_INPUT_DATA", dataObject);
            nts.uk.ui.windows.sub.modal("/view/kmk/003/j/index.xhtml").onClosed(() => {
                let outputDataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_J_OUTPUT_DATA");               
                if (nts.uk.util.isNullOrUndefined(outputDataObject)) {
                    return;    
                }
                // Update common data
                _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsEnter().priorityAtr(outputDataObject.prioritySettingEntering);
                _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsExit().priorityAtr(outputDataObject.prioritySettingExit);
                _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsPcLogin().priorityAtr(outputDataObject.prioritySettingPcLogin);
                _self.mainSettingModel.commonSetting.stampSet.getPrioritySetsPcLogout().priorityAtr(outputDataObject.prioritySettingPcLogout);
                _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsGoOut().roundingSet.roundingTimeUnit(outputDataObject.goOutRoundingUnit);
                _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsGoOut().roundingSet.fontRearSection(outputDataObject.goOutFontRearSection);
                _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsTurnBack().roundingSet.roundingTimeUnit(outputDataObject.turnBackRoundingUnit);
                _self.mainSettingModel.commonSetting.stampSet.getRoundingSetsTurnBack().roundingSet.fontRearSection(outputDataObject.turnBackFontRearSection);
                // Update worktime mode data 
                if (_self.mainSettingModel.workTimeSetting.isFlow()) {
                    _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().startTime(outputDataObject.stampGoWorkFlowStart);
                    _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().endTime(outputDataObject.stampGoWorkFlowEnd);
                    _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.twoTimesWorkReflectBasicTime(outputDataObject.stampTwoTimeReflect);
                    _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().startTime(outputDataObject.stampLeavingWorkFlowStart);
                    _self.mainSettingModel.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().endTime(outputDataObject.stampLeavingWorkFlowEnd);
                } 
                if (_self.mainSettingModel.workTimeSetting.isFixed()) {
                    _self.mainSettingModel.fixedWorkSetting.getGoWork1Stamp().startTime(outputDataObject.stampGoWork1Start);
                    _self.mainSettingModel.fixedWorkSetting.getGoWork1Stamp().endTime(outputDataObject.stampGoWork1End);
                    _self.mainSettingModel.fixedWorkSetting.getLeaveWork1Stamp().startTime(outputDataObject.stampLeavingWork1Start);
                    _self.mainSettingModel.fixedWorkSetting.getLeaveWork1Stamp().endTime(outputDataObject.stampLeavingWork1End);
                    _self.mainSettingModel.fixedWorkSetting.getGoWork2Stamp().startTime(outputDataObject.stampGoWork2Start);
                    _self.mainSettingModel.fixedWorkSetting.getGoWork2Stamp().endTime(outputDataObject.stampGoWork2End);
                    _self.mainSettingModel.fixedWorkSetting.getLeaveWork2Stamp().startTime(outputDataObject.stampLeavingWork2Start);
                    _self.mainSettingModel.fixedWorkSetting.getLeaveWork2Stamp().endTime(outputDataObject.stampLeavingWork2End);
                }
                if (_self.mainSettingModel.workTimeSetting.isDiffTime()) {
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().startTime(outputDataObject.stampGoWork1Start);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().endTime(outputDataObject.stampGoWork1End);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().startTime(outputDataObject.stampLeavingWork1Start);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().endTime(outputDataObject.stampLeavingWork1End);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork2Stamp().startTime(outputDataObject.stampGoWork2Start);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getGoWork2Stamp().endTime(outputDataObject.stampGoWork2End);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork2Stamp().startTime(outputDataObject.stampLeavingWork2Start);
                    _self.mainSettingModel.diffWorkSetting.stampReflectTimezone.getLeaveWork2Stamp().endTime(outputDataObject.stampLeavingWork2End);
                } 
                if (_self.mainSettingModel.workTimeSetting.isFlex()) {
                    _self.mainSettingModel.flexWorkSetting.getGoWork1Stamp().startTime(outputDataObject.stampGoWork1Start);
                    _self.mainSettingModel.flexWorkSetting.getGoWork1Stamp().endTime(outputDataObject.stampGoWork1End);
                    _self.mainSettingModel.flexWorkSetting.getLeaveWork1Stamp().startTime(outputDataObject.stampLeavingWork1Start);
                    _self.mainSettingModel.flexWorkSetting.getLeaveWork1Stamp().endTime(outputDataObject.stampLeavingWork1End);
                    _self.mainSettingModel.flexWorkSetting.getGoWork2Stamp().startTime(outputDataObject.stampGoWork2Start);
                    _self.mainSettingModel.flexWorkSetting.getGoWork2Stamp().endTime(outputDataObject.stampGoWork2End);
                    _self.mainSettingModel.flexWorkSetting.getLeaveWork2Stamp().startTime(outputDataObject.stampLeavingWork2Start);
                    _self.mainSettingModel.flexWorkSetting.getLeaveWork2Stamp().endTime(outputDataObject.stampLeavingWork2End);
                } 
                
                // Update interlock 
                _self.mainSettingModel.updateInterlockDialogJ();
                nts.uk.ui.block.clear();
            });
        }
    }
    export class Item {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    class KMK003A4BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a4/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let tabMode = input.tabMode;
            let enumSetting = input.enum;
            let mainSettingModel = input.mainSettingModel;

            var screenModel = new ScreenModel(tabMode, enumSetting, mainSettingModel);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
            
//             $.fn.getData = function() {
//                return {
//                    name: screenModel.leastWorkTime()
//                };
//            }
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

    }
    ko.bindingHandlers['ntsKMK003A4'] = new KMK003A4BindingHandler();

}
