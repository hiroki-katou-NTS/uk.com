/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	import KMK004_API = nts.uk.at.view.kmk004.KMK004_API;

	const KMK004M_API = {
		REGISTER_WORK_TIME: 'screen/at/kmk004/viewM/monthlyWorkTimeSet/update',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewM/monthlyWorkTimeSet/delete',
		WKP_GET_BASIC_SETTING: 'screen/at/kmk004/viewM/getBasicSetting'
	};

	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, click: register, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: existYear, click: remove, i18n: 'KMK004_227'"></button>
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
									<div class= "box-year" id= "box-year" data-bind="component: {
										name: 'box-year',
										params:{ 
											selectedYear: selectedYear,
											type: type,
											selectedId: selectedIdParam,
											isLoadInitData: isLoadInitData
										}
									}"></div>
									
									<div class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years,
										workTimes: workTimes,
										type: type,
										selectId: paramL.wkpId()
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

	interface IWorkTimeSetCom {
		workplaceId: string;
		laborAttr: number;
		yearMonth: number;
		laborTime: ILaborTime;
	}

	interface ILaborTime {
		legalLaborTime: number,
		withinLaborTime: number,
		weekAvgTime: number
	}

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
		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		isLoadInitData: KnockoutObservable<boolean> = ko.observable(false);

		constructor(public params: IParam) {
			super();
		}

		created() {
			const vm = this;

			vm.$ajax(KMK004_API.WKP_INIT_SCREEN)
				.done((data: any) => {
					let settings: UnitAlreadySettingModel[] = [];
					_.forEach(data.wkpIds, ((value) => {
						let s: UnitAlreadySettingModel = { workplaceId: value.workplaceId, isAlreadySetting: true };
						settings.push(s);
					}));
					vm.alreadySettingList(settings);
				})

			vm.baseDate = ko.observable(new Date());
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
			vm.paramL = { isLoadInitData: vm.isLoadInitData, isLoadData: vm.isLoadData, sidebarType: "Com_Workplace", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }
			vm.selectedYear
				.subscribe(() => {
					vm.$errors('clear');
					if (vm.selectedYear != null) {
						vm.existYear(true);
					}
				});

			$('#workplace-list').ntsTreeComponent(vm.treeGrid).done(() => {
				vm.workplaces = $('#workplace-list').getDataList();
				let flat: any = function(wk: UnitModel) {
					return [wk, _.flatMap(wk.children, flat)];
				},
					flatMapItems = _.flatMapDeep(vm.workplaces, flat);

				vm.selectedId.subscribe((data) => {
					let selectedItem: UnitModel = _.find(flatMapItems, ['id', data]);
					vm.selectedItemText(selectedItem ? selectedItem.name : '');
					vm.paramL.wkpId(data);
					vm.paramL.titleName = vm.selectedItemText();
					vm.selectedIdParam(data);
					
				vm.$ajax(KMK004M_API.WKP_GET_BASIC_SETTING + "/" + vm.paramL.wkpId()).done((data: any) => {
					if (data.deforLaborTimeWkpDto != null && data.wkpDeforLaborMonthActCalSetDto != null) {
						vm.btn_text('KMK004_339');
					} else vm.btn_text('KMK004_338');
				})	
					
				});

				vm.selectedId.valueHasMutated();
			});



		}

		mounted() {
			$(document).ready(function() {
				$('.listbox').focus();
			});
		}

		register() {
			const vm = this;

			let param: IWorkTimeSetCom[] = [];

			_.forEach(ko.unwrap(vm.workTimes), ((value) => {
				const t: IWorkTimeSetCom = { workplaceId: vm.paramL.wkpId(), laborAttr: 1, yearMonth: value.yearMonth(), laborTime: { legalLaborTime: value.laborTime(), withinLaborTime: null, weekAvgTime: null } };
				param.push(t);
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}

				vm.$ajax(KMK004M_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetWkps: param })).done(() => {
					vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
						vm.close();
						vm.isLoadInitData(true);
						$('#box-year').focus();
					})

				}).fail((error) => {
					vm.$dialog.error(error);
				}).always(() => {
					vm.$blockui("clear");
				});
			});
		}


		remove() {
			const vm = this;
			vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
				if (result === 'yes') {
					vm.$blockui("invisible");
					vm.$ajax(KMK004M_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear(), workplaceId: vm.paramL.wkpId() })).done(() => {
						vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
							vm.close();
							vm.isLoadInitData(true);
							$('#box-year').focus();
						})

					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");

					});

				} else {
					$('#box-year').focus();
				}
			});

		}

		close() {
			const vm = this;
			vm.$window.close();
		}

		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', ko.toJS(vm.paramL)).then(() => {
				vm.isLoadData(true);
			});
		}
	}

	interface UnitAlreadySettingModel {
		workplaceId: string;
		isAlreadySetting: boolean;
	}
}