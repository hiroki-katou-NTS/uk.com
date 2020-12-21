/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	import KMK004_API = nts.uk.at.view.kmk004.KMK004_API;

	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: existYear, i18n: 'KMK004_227'"></button>
	</div>
	
	<div>
	<div class="view-m">
		<table>
			<tr>
				<td id="view-m-left-side">
					<div id="workplace-list"></div>
				</td>
				
				<td id="right-side">
					<div class="view-l-common">
						<div class="header-l">
							<div id="title", data-bind="i18n: 'KMK004_307'"></div>
							<hr></hr>
							<div class ="selected-item">
								<label data-bind= "text: selectedItemText"></label>
							</div>
							
							<div class="header_title">
								<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
								<button data-bind="i18n: btn_text, click: openViewP"></button>
							</div>
							<div class="header_content">
								<div data-bind="component: {
									name: 'view-l-basic-setting',
									params: paramL
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
											selectedId: selectedIdParam
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
				</td>
			</tr>
		</table>
		</div>
	</div>
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
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
		treeGrid: any;
		workplaces: [];
		selectedItemText: KnockoutObservable<string> = ko.observable('');
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public type: SIDEBAR_TYPE = 'Com_Workplace';
		public selectedIdParam: KnockoutObservable<string> = ko.observable('');
		paramL: IParam;
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		btn_text: KnockoutObservable<string> = ko.observable('');

		constructor(public params: IParam) {
			super();
		}

		created() {
			const vm = this;

			vm.$ajax(KMK004_API.WKP_INIT_SCREEN)
				.done((data: any) => {
					let settings: UnitAlreadySettingModel[] = [];
					_.forEach(data.wkpIds, ((value) => {
						let s: UnitAlreadySettingModel = { id: value.workplaceId, isAlreadySetting: true };
						settings.push(s);
					}));
					vm.alreadySettingList(settings);
				})

			vm.baseDate = ko.observable(new Date());
			vm.alreadySettingList = ko.observableArray([]);
			vm.treeGrid = {
				isShowAlreadySet: true,
				isMultipleUse: false,
				isMultiSelect: false,
				startMode: 0,
				selectedId: vm.selectedId,
				baseDate: vm.baseDate,
				selectType: 3,
				isShowSelectButton: true,
				isDialog: false,
				alreadySettingList: vm.alreadySettingList,
				maxRows: 12,
				tabindex: 1,
				systemType: 2
			};
			vm.paramL = { isLoadData: vm.isLoadData, sidebarType: "Com_Workplace", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }
			vm.selectedYear
				.subscribe(() => {
					vm.$errors('clear');
					if (vm.selectedYear != null) {
						vm.existYear(true);
					}
				});
			vm.selectedId.subscribe((data) => {
				let selectedItem: UnitModel = _.find(vm.workplaces, ['id', data]);
				vm.selectedItemText(selectedItem ? selectedItem.name : '');
				vm.paramL.wkpId(data);
				vm.paramL.titleName = vm.selectedItemText();
				vm.selectedIdParam(data);
				vm.btn_text(
					vm.alreadySettingList().filter(i => data == i.id).length == 0 ? 'KMK004_338' : 'KMK004_339');
			});

			$('#workplace-list').ntsTreeComponent(vm.treeGrid).done(() => {
				vm.workplaces = $('#workplace-list').getDataList();
				vm.selectedId.valueHasMutated();
			});
		}

		mounted() {
			$(document).ready(function() {
				$('.listbox').focus();
			});
		}

		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', ko.toJS(vm.paramL)).then(() => {
				vm.isLoadData(true);
			});
		}
	}

	interface UnitAlreadySettingModel {
		id: string;
		isAlreadySetting: boolean;
	}
}