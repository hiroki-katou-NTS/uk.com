/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-l">
				<div class="header-l">
					<div id="title", data-bind="i18n: 'KMK004_307'"></div>
					<hr></hr>
					<div class="header_title">
						<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
						<button data-bind="i18n: 'KMK004_231', click: openViewP"></button>
					</div>
					<div class="header_content">
						<div data-bind="component: {
							name: 'view-l-basic-setting',
							params:{ 
								
							}
						}"></div>
					</div>
					<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_232'"></div>
				</div>
				<div class="content">
					<button id = "btn_year" data-bind="i18n: 'KMK004_233'"></button>
					<table id = "btm_area">
						<tr>
							<td>
								<div data-bind="component: {
									name: 'view-l-listbox'
								}"></div>
							</td>
							<td>
								<div data-bind="component: {
									name: 'view-l-times-table'
								}"></div>
							</td>
						</tr>
					</table>
			</div>
	</div>
	`;

	

	@component({
		name: 'view-l',
		template
    })
    
	export class ViewLComponent extends ko.ViewModel {
		constructor(private params: IParam){
			super();
		}
		
		created() {
			let vm = this;
			vm.params = {sidebarType : "Com_Company", wkpId: '', empCode :'', empId: '', titleName:''}
		}

		mounted() {
			
		}
		
		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params)
		}
    }
}
