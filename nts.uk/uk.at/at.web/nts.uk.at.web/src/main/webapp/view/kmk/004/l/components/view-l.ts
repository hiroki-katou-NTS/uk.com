/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: false, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: existYear, i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-l">
				<div class="header-l">
					<div id="title", data-bind="i18n: 'KMK004_307'"></div>
					<hr></hr>
					<div class="header_title">
						<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
						<button data-bind="i18n: 'KMK004_231', click: openViewP"></button>
					</div>
					<div class="header_content">
						<div data-bind="component: {
							name: 'view-l-basic-setting',
							params: params
						}"></div>
					</div>
					<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
				</div>
				<div class="content">
					
					<div class="div_row"> 
								
								<div class= "box-year" data-bind="component: {
									name: 'box-year',
									params:{ 
										selectedYear: selectedYear,
										type: type,
										selectedId: ko.observable('')
									}
								}"></div>
								
								<div class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years
									}
								}"></div>
					</div>
			</div>
	</div>
	`;

	@component({
		name: 'view-l',
		template
    })
    
	export class ViewLComponent extends ko.ViewModel {
		
		selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public existYear: KnockoutObservable<boolean> = ko.observable(true);
		public type: SIDEBAR_TYPE = 'Com_Company';
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		
		constructor(private params: IParam){
			super();
		}
		
		created() {
			let vm = this;
			vm.params = {isLoadData: vm.isLoadData, sidebarType : "Com_Company", wkpId: ko.observable(''), empCode :ko.observable(''), empId: ko.observable(''), titleName:'', deforLaborTimeComDto: null, settingDto: null}
			vm.selectedYear
			.subscribe(() => {
				vm.$errors('clear');
				if(vm.selectedYear != null) {
					vm.existYear(true);
				}
			});
		}

		mounted() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}
		
		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params).then(() => {
				vm.isLoadData(true);
			});
		}
		
    }
}
