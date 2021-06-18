/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	import KMK004_API = nts.uk.at.view.kmk004.KMK004_API;

	const KMK004M_API = {
		REGISTER_WORK_TIME: 'screen/at/kmk004/viewM/monthlyWorkTimeSet/update',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewM/monthlyWorkTimeSet/delete',
		WKP_GET_BASIC_SETTING: 'screen/at/kmk004/viewM/getBasicSetting',
		COPY_SETTING: 'screen/at/kmk004/viewM/after-copy'
	};

	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, click: register, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, enable: checkDelete, click: openViewR, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: checkDelete, click: remove, i18n: 'KMK004_227'"></button>
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
								<div data-bind="ntsFormLabel: {inline: true, text: $i18n('KMK004_229')}"></div>
								<button data-bind="i18n: btn_text, click: openViewP"></button>
							</div>
							<div class="header_content">
								<div data-bind="component: {
									name: 'view-l-basic-setting',
									params: params
								}"></div>
							</div>
							<div data-bind="ntsFormLabel: {inline: true, text: $i18n('KMK004_232')}"></div>
						</div>
						<div class="content">
								<div class="div_row"> 
									<div class= "box-year" data-bind="component: {
										name: 'box-year',
										params:{ 
											selectedYear: selectedYear,
											type: type,
											selectedId: selectedIdParam,
											years: years,
											startYM: startYM
										}
									}"></div>
									
									<div class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years,
										workTimes: workTimes,
										type: type,
										selectedId: selectedIdParam,
										yearDelete: yearDelete,
										startYM: startYM
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
		workplaceId: string; //職場 ID
		laborAttr: number; //勤務区分
		yearMonth: number; //年月
		laborTime: ILaborTime; //月労働時間
	}

	interface ILaborTime {
		legalLaborTime: number, //法定労働時間
		withinLaborTime: number, //所定労働時間
		weekAvgTime: number //週平均時間
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
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		btn_text: KnockoutObservable<string> = ko.observable('KMK004_338');
		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public startYM: KnockoutObservable<number> = ko.observable(0);
		
		constructor(private params: IParam) {
			super();
		}

		created() {
			const vm = this;

			vm.getwkpIdList();

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
			vm.params = { isLoadData: vm.isLoadData, sidebarType: "Com_Workplace", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }

			vm.years
				.subscribe((years: IYear[]) => {
					const [first] = years;

					if (first) {
						if (ko.unwrap(vm.selectedYear) == null) {
							vm.selectedYear(first.year);
						}
						vm.existYear(true);
					} else {
						vm.selectedYear(null);

						vm.existYear(false);
						vm.checkDelete(false);
					}
				});

			vm.selectedYear
				.subscribe((val: number | null) => {
					const _years = ko.unwrap(vm.years);

					const exist = _.find(_years, ({ year }) => year == val);

					if (exist) {
						if (ko.unwrap(vm.existYear)) {
							if (exist.isNew) {
								vm.checkDelete(false);
							} else {
								vm.checkDelete(true);
							}
						} else {
							vm.checkDelete(true);
						}
					} else {
						vm.checkDelete(false);
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
					vm.params.wkpId(data);
					vm.params.titleName = vm.selectedItemText();
					vm.selectedIdParam(data);
					vm.getBtnContent();
					$('#list-box').focus();
				});

				vm.selectedId.valueHasMutated();
			});
		}

		getBtnContent() {
			const vm = this;
			vm.$ajax(KMK004M_API.WKP_GET_BASIC_SETTING + "/" + vm.selectedIdParam()).done((data: any) => {
				if (data.deforLaborTimeWkpDto != null && data.wkpDeforLaborMonthActCalSetDto != null) {
					vm.btn_text('KMK004_339');
				} else vm.btn_text('KMK004_338');
			})
		}

		getwkpIdList() {
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
		}
		mounted() {
			$(document).ready(() => {
				$('#list-box').focus();
			});
		}

		register() {
			const vm = this;

			let param: IWorkTimeSetCom[] = [];

			_.forEach(ko.unwrap(vm.workTimes), ((value) => {
				const t: IWorkTimeSetCom = { workplaceId: vm.selectedIdParam(), laborAttr: 1, yearMonth: value.yearMonth(), laborTime: { legalLaborTime: value.laborTime(), withinLaborTime: null, weekAvgTime: null } };
				param.push(t);
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}

				vm.$ajax(KMK004M_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetWkps: param })).done(() => {
					_.remove(ko.unwrap(vm.years), ((value) => {
						return value.year == ko.unwrap(vm.selectedYear) as number;
					}));
					vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					vm.getwkpIdList();
				}).then(() => vm.$dialog.info({ messageId: "Msg_15" }))
					.then(() => {
						$(document).ready(() => {
							$('#list-box').focus();
						});
					}).always(() => {
						vm.$errors('clear');
					}).then(() => {
						vm.selectedYear.valueHasMutated();
					});
			});
		}

		remove() {
			const vm = this;
			const index = _.map(ko.unwrap(vm.years), m => m.year.toString()).indexOf(ko.unwrap(vm.selectedYear).toString());
			const old_index = index === ko.unwrap(vm.years).length - 1 ? index - 1 : index;

			nts.uk.ui.dialog
				.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					vm.$blockui("invisible")
						.then(() => vm.$ajax(KMK004M_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear(), workplaceId: vm.selectedIdParam() })))
						.then(() => {
							vm.yearDelete(ko.unwrap(vm.selectedYear));
						})
						.then(() => {
							_.remove(ko.unwrap(vm.years), ((value) => {
								return value.year == ko.unwrap(vm.selectedYear);
							}));
							vm.years(ko.unwrap(vm.years));
							if (ko.unwrap(vm.years).length > 0) {
								vm.selectedYear(ko.unwrap(vm.years)[old_index].year);
							} else {
								vm.selectedYear.valueHasMutated();
							}
							vm.getwkpIdList();
						})
						.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
						.then(() => {
							$(document).ready(() => {
								$('#list-box').focus();
							});
						}).then(() => {
							vm.$errors('clear');
						}).always(() => vm.$blockui("clear"));
				}).ifNo(() => {
                    $(document).ready(() => {
						$('#list-box').focus();
					});
                });
		}

		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', ko.toJS(vm.params)).then(() => {
				vm.isLoadData(true);
				vm.getwkpIdList();
				vm.getBtnContent();
			});
		}

		openViewR() {
			const vm = this;

			vm.$window.modal('at', '/view/kmk/004/r/index.xhtml', {
				screenMode: vm.type,
				selected: vm.selectedId(),
				year: vm.selectedYear(),
				laborAttr: 1
			}).then(() => {
				vm.$ajax(KMK004M_API.COPY_SETTING).done((data) => {
					vm.alreadySettingList(_.map(data, (item: string) => { return { workplaceId: item, isAlreadySetting: true } }));
				}).then(() => {
					$(document).ready(() => {
						$('#list-box').focus();
					});
				}).always(() => { vm.$blockui("clear"); });
			});
		}
	}

	interface UnitAlreadySettingModel {
		workplaceId: string;
		isAlreadySetting: boolean;
	}
}