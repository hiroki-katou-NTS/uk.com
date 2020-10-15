 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.a.viewmodel {

    @bean()
    class Kaf018AViewModel extends ko.ViewModel {
        closureLst: KnockoutObservableArray<string> = ko.observableArray([]);
        selectedClosureId: KnockoutObservable<string> = ko.observable("");
		dateValue: KnockoutObservable<any> = ko.observable({});
		selectedIds: KnockoutObservableArray<number> = ko.observableArray([]);
		
		treeGrid: any;
		multiSelectedWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
		baseDate: KnockoutObservable<Date> = ko.observable(new Date());
		alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);
		
        created() {
			const vm = this;
			vm.treeGrid = {
                isShowAlreadySet: false,
                isMultipleUse: false,
                isMultiSelect: true,
                treeType: 1,
                selectedId: vm.multiSelectedWorkplaceId,
                baseDate: vm.baseDate,
                selectType: 1,
                isShowSelectButton: true,
                isDialog: true,
                showIcon: true,
                alreadySettingList: vm.alreadySettingList,
                maxRows: 15,
                tabindex: 1,
                systemType: 2
            };
			$('#tree-grid').ntsTreeComponent(vm.treeGrid).done(() => {
            });
        }
		
		extraction() {
			
		}
        
		emailSetting() {
			const vm = this;
			vm.$window.modal('/view/kaf/018/i/index.xhtml');
		}
    }

    const API = {
	
    }
}