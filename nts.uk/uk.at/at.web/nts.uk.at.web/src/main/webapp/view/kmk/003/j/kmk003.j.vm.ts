module nts.uk.at.view.kmk003.j {

    export module viewmodel {  
        
        import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
        
        export class ScreenModel {        
            
            // Screen data 
            dataObject: any;
            
            // Is flow mode
            isFlow: KnockoutObservable<boolean>;
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
                
                _self.dataObject = null;
                _self.isFlow = ko.observable(null);
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
                _self.stampLeavingWork1Start = ko.observable(0);;
                _self.stampLeavingWork1End = ko.observable(0);
                _self.stampLeavingWork2Start = ko.observable(0);
                _self.stampLeavingWork2End = ko.observable(0);
                
                _self.stampGoWorkFlowStart = ko.observable(0);
                _self.stampGoWorkFlowEnd = ko.observable(0);
                _self.stampTwoTimeReflect = ko.observable(0);
                _self.stampLeavingWorkFlowStart = ko.observable(0);
                _self.stampLeavingWorkFlowEnd = ko.observable(0);
                
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
                console.log(dataObject);
                _self.dataObject = dataObject;
                _self.isFlow(dataObject.isFlow);
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
                //TODO
            }   
            
            /**
             * Save
             */
            public save(): void {
                let _self = this;
                
                //TODO
//                let dataObject: any = {
//                    delFromEmTime: _self.delFromEmTime(),       
//                    lateStampExactlyTimeIsLateEarly: _self.lateStampExactlyTimeIsLateEarly(),
//                    lateGraceTime: _self.lateGraceTime(),
//                    lateIncludeWorkingHour: _self.lateIncludeWorkingHour(),
//                    leaveEarlyStampExactlyTimeIsLateEarly: _self.leaveEarlyStampExactlyTimeIsLateEarly(),
//                    leaveEarlyGraceTime: _self.leaveEarlyGraceTime(),
//                    leaveEarlyIncludeWorkingHour: _self.leaveEarlyIncludeWorkingHour()
//                };
//                nts.uk.ui.windows.setShared("KMK003_DIALOG_J_OUTPUT_DATA", dataObject);
//                _self.close();
            }
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
                       
        }
    }    
}