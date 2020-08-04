module nts.uk.at.view.kaf002_ref.a.viewmodel {
    
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002AViewModel extends Kaf000AViewModel {
        
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        created() {
            var self = this;
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-2');
//            出勤／退勤
            $('#gridTable').ntsGrid({
                width: 500,
                height: 500,
                dataSource: [{k1:'abc', k2:'def', k3: 'cccccc', k4: 'k444444'}],
                rowVirtualization: true,
                virtualization: true,
                hidePrimaryKey: true,
                virtualizationMode: 'continuous',
                autoFitWindow: false,
                columns: [
                   {headerText: '', key: 'k1', dataType: 'string', width: '100px'},
                   {headerText: self.$i18n('KAF002_22'), key: 'k2', dataType: 'string', width: '100px'},
                   {headerText: self.$i18n('KAF002_23'), key: 'k3', dataType: 'string', width: '100px'},
                   {headerText: self.$i18n('KAF002_72'), key: 'k4', dataType: 'string', width: '100px'}
                ],
            });
            
        }
        
        mounted() {
            
        }
        
    }
    const API = {
            startStampApp: 'at/request/application/stamp/startStampApp',
            checkBeforeRegister: 'at/request/application/stamp/checkBeforeRegister',
            register: 'at/request/application/stamp/register',
            changeDate: 'at/request/application/stamp/changeDate'
    }
}