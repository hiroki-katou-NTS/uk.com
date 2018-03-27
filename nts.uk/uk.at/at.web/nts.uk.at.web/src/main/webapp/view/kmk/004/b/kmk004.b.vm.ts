module nts.uk.at.view.kmk004.b {
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
        
        export class ScreenModel {
            
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            baseDate: KnockoutObservable<Date>;
            
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
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
                
                function startPage():void {
                    
                }
            }
        }
    }
}