module nts.uk.at.view.kmk003.j {

    export module viewmodel {  
        
        import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
        
        export class ScreenModel {        
            
            timeEditorOption: KnockoutObservable<any>;
            
            // Screen data 
            dataObject: any;
            
            // Is flow mode
            isManageEntryExit: KnockoutObservable<boolean>;
            isFlow: KnockoutObservable<boolean>;
            isUseTime2: KnockoutObservable<boolean>;
            isFixedAndUseTime2: KnockoutObservable<boolean>;
            
            // Data            
            listPriorityClassification: KnockoutObservableArray<any>;
            listRoundingTimeUnit: KnockoutObservableArray<EnumConstantDto>;
            listFontRearSection: KnockoutObservableArray<any>;
            
            stampGoWork1Start: KnockoutObservable<number>;
            stampGoWork1End: KnockoutObservable<number>;
            stampGoWork2Start: KnockoutObservable<number>;
            stampGoWork2End: KnockoutObservable<number>;
            stampLeavingWork1Start: KnockoutObservable<number>;
            stampLeavingWork1End: KnockoutObservable<number>;
            stampLeavingWork2Start: KnockoutObservable<number>;
            stampLeavingWork2End: KnockoutObservable<number>;
            
            stampGoWorkFlowStart: KnockoutObservable<number>;
            stampGoWorkFlowEnd: KnockoutObservable<number>;
            stampTwoTimeReflect: KnockoutObservable<number>;
            stampLeavingWorkFlowStart: KnockoutObservable<number>;
            stampLeavingWorkFlowEnd: KnockoutObservable<number>;
            
            prioritySettingEntering: KnockoutObservable<number>;
            prioritySettingExit: KnockoutObservable<number>;
            prioritySettingPcLogin: KnockoutObservable<number>;
            prioritySettingPcLogout: KnockoutObservable<number>;                      
            goOutRoundingUnit: KnockoutObservable<number>;
            goOutFontRearSection: KnockoutObservable<number>;
            turnBackRoundingUnit: KnockoutObservable<number>;
            turnBackFontRearSection: KnockoutObservable<number>;
            
            constructor() {
                let _self = this;
                
                _self.timeEditorOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                    width: "50"
                }));
                
                _self.dataObject = null;
                _self.isManageEntryExit = ko.observable(null);
                _self.isFlow = ko.observable(null);
                _self.isUseTime2 = ko.observable(null);
                _self.isFixedAndUseTime2 = ko.observable(null);
                
                _self.listPriorityClassification = ko.observableArray([
                    {value: 0, localizedName: nts.uk.resource.getText("KMK003_69")},
                    {value: 1, localizedName: nts.uk.resource.getText("KMK003_70")}
                ]);
                _self.listRoundingTimeUnit = ko.observableArray([]);
                _self.listFontRearSection = ko.observableArray([
                    {value: 0, localizedName: nts.uk.resource.getText("KMK003_72")},
                    {value: 1, localizedName: nts.uk.resource.getText("KMK003_73")}
                ]);
                
                _self.stampGoWork1Start = ko.observable(0);
                _self.stampGoWork1End = ko.observable(0);
                _self.stampGoWork2Start = ko.observable(0);
                _self.stampGoWork2End = ko.observable(0);
                _self.stampLeavingWork1Start = ko.observable(0);
                _self.stampLeavingWork1End = ko.observable(0);
                _self.stampLeavingWork2Start = ko.observable(0);
                _self.stampLeavingWork2End = ko.observable(0);
                
                _self.stampGoWork1Start.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampGoWork1End.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampGoWork2Start.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampGoWork2End.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWork1Start.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWork1End.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWork2Start.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWork2End.subscribe(() => nts.uk.ui.errors.clearAll());
                
                _self.stampGoWorkFlowStart = ko.observable(0);
                _self.stampGoWorkFlowEnd = ko.observable(0);
                _self.stampTwoTimeReflect = ko.observable(0);
                _self.stampLeavingWorkFlowStart = ko.observable(0);
                _self.stampLeavingWorkFlowEnd = ko.observable(0);
                
                _self.stampGoWorkFlowStart.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampGoWorkFlowEnd.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWorkFlowStart.subscribe(() => nts.uk.ui.errors.clearAll());
                _self.stampLeavingWorkFlowEnd.subscribe(() => nts.uk.ui.errors.clearAll());
                
                _self.prioritySettingEntering = ko.observable(0);
                _self.prioritySettingExit = ko.observable(0);
                _self.prioritySettingPcLogin = ko.observable(0);
                _self.prioritySettingPcLogout = ko.observable(0);
                _self.goOutRoundingUnit = ko.observable(0);
                _self.goOutFontRearSection = ko.observable(0);
                _self.turnBackRoundingUnit = ko.observable(0);
                _self.turnBackFontRearSection = ko.observable(0);
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_J_INPUT_DATA");
                _self.bindingData(dataObject);
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Binding data
             */
            private bindingData(dataObject: any) {
                let _self = this;
                if (nts.uk.util.isNullOrUndefined(dataObject)) {                                   
                    return;
                }
                _self.dataObject = dataObject;
                
                if (dataObject.isManageEntryExit === 1) {
                    _self.isManageEntryExit(true);
                } else {
                    _self.isManageEntryExit(false);
                }                
                _self.isFlow(dataObject.isFlow);
                _self.isUseTime2(dataObject.isUseTime2);
                _self.isFixedAndUseTime2(dataObject.isFixedAndUseTime2);
                
                _self.listRoundingTimeUnit(dataObject.listRoundingTimeUnit);
                
                _self.prioritySettingEntering(dataObject.prioritySettingEntering);
                _self.prioritySettingExit(dataObject.prioritySettingExit);
                _self.prioritySettingPcLogin(dataObject.prioritySettingPcLogin);
                _self.prioritySettingPcLogout(dataObject.prioritySettingPcLogout);
                _self.goOutRoundingUnit(dataObject.goOutRoundingUnit);
                _self.goOutFontRearSection(dataObject.goOutFontRearSection);
                _self.turnBackRoundingUnit(dataObject.turnBackRoundingUnit);
                _self.turnBackFontRearSection(dataObject.turnBackFontRearSection);
                // Add worktime mode param 
                if (_self.isFlow()) {
                    _self.stampGoWorkFlowStart(!isNaN(dataObject.stampGoWorkFlowStart) ? dataObject.stampGoWorkFlowStart : 0);
                    _self.stampGoWorkFlowEnd(!isNaN(dataObject.stampGoWorkFlowEnd) ? dataObject.stampGoWorkFlowEnd : 0);
                    _self.stampTwoTimeReflect(!isNaN(dataObject.stampTwoTimeReflect) ? dataObject.stampTwoTimeReflect : 0);
                    _self.stampLeavingWorkFlowStart(!isNaN(dataObject.stampLeavingWorkFlowStart) ? dataObject.stampLeavingWorkFlowStart : 0);
                    _self.stampLeavingWorkFlowEnd(!isNaN(dataObject.stampLeavingWorkFlowEnd) ? dataObject.stampLeavingWorkFlowEnd : 0);
                } else {
                    _self.stampGoWork1Start(!isNaN(dataObject.stampGoWork1Start) ? dataObject.stampGoWork1Start : 0);
                    _self.stampGoWork1End(!isNaN(dataObject.stampGoWork1End) ? dataObject.stampGoWork1End : 0);
                    _self.stampGoWork2Start(!isNaN(dataObject.stampGoWork2Start) ? dataObject.stampGoWork2Start : 0);
                    _self.stampGoWork2End(!isNaN(dataObject.stampGoWork2End) ? dataObject.stampGoWork2End : 0);
                    _self.stampLeavingWork1Start(!isNaN(dataObject.stampLeavingWork1Start) ? dataObject.stampLeavingWork1Start : 0);
                    _self.stampLeavingWork1End(!isNaN(dataObject.stampLeavingWork1End) ? dataObject.stampLeavingWork1End : 0);
                    _self.stampLeavingWork2Start(!isNaN(dataObject.stampLeavingWork2Start) ? dataObject.stampLeavingWork2Start : 0);
                    _self.stampLeavingWork2End(!isNaN(dataObject.stampLeavingWork2End) ? dataObject.stampLeavingWork2End : 0);
                }
            }   
            
            /**
             * Save
             */
            public save(): void {
                let _self = this;
                
                // Validate Msg_770
                if (_self.isFlow()) {
                    _self.validTimeRange("stampGoWorkFlowStart", "stampGoWorkFlowEnd", _self.stampGoWorkFlowStart(), _self.stampGoWorkFlowEnd(), "KMK003_270");
                    _self.validTimeRange("stampLeavingWorkFlowStart", "stampLeavingWorkFlowEnd", _self.stampLeavingWorkFlowStart(), _self.stampLeavingWorkFlowEnd(), "KMK003_273");
                    $("#stampTwoTimeReflect").ntsEditor('validate');
                } else {
                    _self.validTimeRange("stampGoWork1Start", "stampGoWork1End", _self.stampGoWork1Start(), _self.stampGoWork1End(), "KMK003_271");
                    _self.validTimeRange("stampGoWork2Start", "stampGoWork2End", _self.stampGoWork2Start(), _self.stampGoWork2End(), "KMK003_272");
                    _self.validTimeRange("stampLeavingWork1Start", "stampLeavingWork1End", _self.stampLeavingWork1Start(), _self.stampLeavingWork1End(), "KMK003_274");
                    _self.validTimeRange("stampLeavingWork2Start", "stampLeavingWork2End", _self.stampLeavingWork2Start(), _self.stampLeavingWork2End(), "KMK003_275");
                }
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }
                
                let dataObject: any = {
                    prioritySettingEntering: _self.prioritySettingEntering(),
                    prioritySettingExit: _self.prioritySettingExit(),
                    prioritySettingPcLogin: _self.prioritySettingPcLogin(),
                    prioritySettingPcLogout: _self.prioritySettingPcLogout(),
                    goOutRoundingUnit: _self.goOutRoundingUnit(),
                    goOutFontRearSection: _self.goOutFontRearSection(),
                    turnBackRoundingUnit: _self.turnBackRoundingUnit(),
                    turnBackFontRearSection: _self.turnBackFontRearSection()
                };
                // Add worktime mode param 
                if (_self.isFlow()) {
                    dataObject.stampGoWorkFlowStart = _self.stampGoWorkFlowStart();
                    dataObject.stampGoWorkFlowEnd = _self.stampGoWorkFlowEnd();
                    dataObject.stampTwoTimeReflect = _self.stampTwoTimeReflect();
                    dataObject.stampLeavingWorkFlowStart = _self.stampLeavingWorkFlowStart();
                    dataObject.stampLeavingWorkFlowEnd = _self.stampLeavingWorkFlowEnd();
                } else {
                    dataObject.stampGoWork1Start = _self.stampGoWork1Start();
                    dataObject.stampGoWork1End = _self.stampGoWork1End();
                    dataObject.stampLeavingWork1Start = _self.stampLeavingWork1Start();
                    dataObject.stampLeavingWork1End = _self.stampLeavingWork1End();
                    dataObject.stampGoWork2Start = _self.stampGoWork2Start();
                    dataObject.stampGoWork2End = _self.stampGoWork2End();
                    dataObject.stampLeavingWork2Start = _self.stampLeavingWork2Start();
                    dataObject.stampLeavingWork2End = _self.stampLeavingWork2End();
                }
                nts.uk.ui.windows.setShared("KMK003_DIALOG_J_OUTPUT_DATA", dataObject);
                _self.close();
            }
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
                   
            /**
             * Validate time range
             */
            private validTimeRange(startInputId: string, endInputId: string, startTime: number, endTime: number, paramId: string) {
                let self = this;
                if($('#' + startInputId).prop('disabled') && $('#' + endInputId).prop('disabled')) {
                    return true;
                }
                
                // clear error
                $('#' + startInputId).ntsError('clear');
                $('#' + endInputId).ntsError('clear');
                
                // validate
                $('#' + startInputId).ntsEditor('validate');
                $('#' + endInputId).ntsEditor('validate');
                
                if ($('#' + startInputId).ntsError('hasError') || $('#' + endInputId).ntsError('hasError')) {
                    return false;
                }
                if (startTime >= endTime) {
                    $('#' + startInputId).ntsError('set', {messageId:'Msg_770',messageParams:[nts.uk.resource.getText(paramId)]});
                    if (!$('#' + startInputId).parent().hasClass('error')) {
                        _.defer(() =>$('#' + startInputId).parent().addClass('error'));
                    }
                    if (!$('#' + endInputId).parent().hasClass('error')) {
                        _.defer(() =>$('#' + endInputId).parent().addClass('error'));
                    }
                    return false;
                }
                return true;
            }
        }
    }    
}