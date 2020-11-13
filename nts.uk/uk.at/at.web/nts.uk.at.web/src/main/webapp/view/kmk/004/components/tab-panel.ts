/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

interface Params {
	model: KnockoutObservable<string>;
}

const KMK004A_API = {
	GET_USAGE_UNIT_SETTING: 'screen/at/kmk004/getUsageUnitSetting'
};

const template =`<div class="sidebar-navigator">
	<ul class="navigator" data-bind="foreach: tabs">
		<li data-bind="click: function() {$component.changeTab.apply($component, [$data])}">
			<a href="javascript: void(0)" data-bind="
					text: $component.$i18n($data),
					css: { 'active': $component.params.model() === $data
				}"></a>
		</li>
	</ul>
</div>`;

@component({
	name: 'ko-panel',
	template
})
class TabPanel extends ko.ViewModel {
	public params!: Params;	

	public tabs: KnockoutObservableArray<string> = ko.observableArray(['Com_Company']);

	created(params: Params) {
		const vm = this;

		vm.params = params;

		vm.$blockui('invisible')
                .then(() => vm.$ajax(KMK004A_API.GET_USAGE_UNIT_SETTING))
                .then((data: IUnitSetting) => {
					if (data.workPlace) {
						vm.tabs.push('Com_Workplace');
					}
					if (data.employee) {
						vm.tabs.push('Com_Person');
					}
					if (data.employment) {
						vm.tabs.push('Com_Employment');
					}
                })
                .then(() => vm.$blockui('clear'));
	}
	
	changeTab(tab: string){
		const vm = this;
		
		vm.$errors('clear')
		.then(() => vm.params.model(tab));
	}
}

interface IUnitSetting {
	workPlace: boolean;
	employment: boolean;
	employee: boolean;
}