/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import tree = kcp.share.tree;
	import IParam = nts.uk.at.view.kmk004.p.IParam;

	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-m">
	<table>
		<tr>
			<td id="view-m-left-side">
				<div id="workplace-list"></div>
			</td>
			
			<td id="right-side">
				<div class="view-l">
					<div class="header-l">
						<div id="title", data-bind="i18n: 'KMK004_307'"></div>
						<hr></hr>
						<div>
							<label data-bind= "text: selectedItemText"></label>
						</div>
						
						<div class="header_title">
							<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
							<button data-bind="i18n: 'KMK004_338', click: openViewP"></button>
						</div>
						<div class="header_content">
							<div data-bind="visible: false, component: {
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
			</td>
		</tr>
	</table>
	`;

	interface UnitModel {
		id: string;
		code: string;
		name: string;
		nodeText?: string;
		level: number;
		heirarchyCode: string;
		isAlreadySetting?: boolean;
		children: Array<UnitModel>;
	}

	@component({
		name: 'view-m',
		template
	})

	export class ViewMComponent extends ko.ViewModel {
		selectedId: KnockoutObservable<any> = ko.observable();
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<tree.UnitAlreadySettingModel>;
		treeGrid: tree.TreeComponentOption;
		selectedItemText: KnockoutObservable<string> = ko.observable('');
		
		constructor(private params: IParam){
			super();
		}
		
		created() {
			const vm = this;

			vm.baseDate = ko.observable(new Date());
			vm.alreadySettingList = ko.observableArray([]);
			vm.treeGrid = {
				isShowAlreadySet: true,
				isMultipleUse: false,
				isMultiSelect: false,
				startMode: tree.StartMode.WORKPLACE,
				selectedId: vm.selectedId,
				baseDate: vm.baseDate,
				selectType: tree.SelectionType.SELECT_FIRST_ITEM,
				isShowSelectButton: true,
				isDialog: false,
				alreadySettingList: vm.alreadySettingList,
				maxRows: 12,
				tabindex: 1,
				systemType: 2
			};
			
			vm.params = {sidebarType : "Com_Workplace", wkpId: '', empCode :'', empId: '', titleName:''}
		}

		mounted() {
			const vm = this;
			$('#workplace-list').ntsTreeComponent(vm.treeGrid).done(() => {
				vm.selectedId.subscribe((data) => {
					let workplaces = $('#workplace-list').getDataList();
					let selectedItem: UnitModel = _.find(workplaces, ['id', data]);
					vm.selectedItemText(selectedItem ? selectedItem.name : '');
					vm.params.wkpId = data;
					vm.params.titleName = vm.selectedItemText();
				});
				vm.selectedId.valueHasMutated();
			});
		}
		
		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params)
		}
	}
}