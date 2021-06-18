/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;

	const KMK004N_API = {
		REGISTER_WORK_TIME: 'screen/at/kmk004/viewN/monthlyWorkTimeSet/update',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewN/monthlyWorkTimeSet/delete',
		EMP_GET_BASIC_SETTING: 'screen/at/kmk004/viewN/getBasicSetting',
		COPY_SETTING: 'screen/at/kmk004/viewN/after-copy'
	};

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Employment'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, click: register, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, enable: checkDelete, click: openViewR, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: checkDelete, click: remove, i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-n">
	<table>
		<tr>
			<td id="view-n-left-side">
				<div id="empt-list-setting"></div>
			</td>
			
			<td id="right-side">
				<div class="view-l-common">
					<div class="header-l">
						<div id="title", data-bind="i18n: 'KMK004_307'"></div>
						<hr></hr>
						<div class="selected-item">
							<label data-bind= "text: currentItemName"></label>
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
										selectedId: selectedId,
										years: years,
										startYM: startYM
									}
								}"></div>
								
								<div id= class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years,
										workTimes: workTimes,
										type: type,
										selectedId: selectedId,
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
	`;

	export class ListType {
		static EMPLOYMENT = 1;
		static Classification = 2;
		static JOB_TITLE = 3;
		static EMPLOYEE = 4;
	}

	export class SelectType {
		static SELECT_BY_SELECTED_CODE = 1;
		static SELECT_ALL = 2;
		static SELECT_FIRST_ITEM = 3;
		static NO_SELECT = 4;
	}

	interface UnitModel {
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
	}

	interface UnitAlreadySettingModel {
		code: string;
		isAlreadySetting: boolean;
	}

	interface IWorkTimeSetCom {
		employmentCode: string; //雇用コード
		laborAttr: number; //勤務区分
		yearMonth: number; //年月
		laborTime: ILaborTime; //月労働時間
	}

	interface ILaborTime {
		legalLaborTime: number, //法定労働時間
		withinLaborTime: number, //所定労働時間
		weekAvgTime: number //週平均時間
	}

	@component({
		name: 'view-n',
		template
	})

	export class ViewNComponent extends ko.ViewModel {
		listComponentOption: any;
		selectedCode: KnockoutObservable<string>;
		selectedId: KnockoutObservable<string> = ko.observable('');
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
		employeeList: KnockoutObservableArray<UnitModel>;
		currentItemName: KnockoutObservable<string>;

		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public type: SIDEBAR_TYPE = 'Com_Employment';
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		btn_text: KnockoutObservable<string> = ko.observable('KMK004_340');
		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public startYM: KnockoutObservable<number> = ko.observable(0);
		selectedClosureId: KnockoutObservable<number> = ko.observable(null);
		
		constructor(private params: IParam) {
			super();
		}

		created() {
			let vm = this;
			vm.selectedCode = ko.observable('1');
			vm.listComponentOption = {
				isShowAlreadySet: true,
				isMultiSelect: false,
				listType: ListType.EMPLOYMENT,
				selectType: SelectType.SELECT_FIRST_ITEM,
				selectedCode: vm.selectedCode,
				isDialog: false,
				isShowNoSelectRow: false,
				alreadySettingList: vm.alreadySettingList,
				maxRows: 12,
				isDisplayClosureSelection: true,
				isDisplayFullClosureOption: false,
				closureSelectionType: ko.observable(null),
				selectedClosureId: vm.selectedClosureId,
			};

			vm.reloadInitData();

			vm.employeeList = ko.observableArray<UnitModel>([]);
			vm.currentItemName = ko.observable('');
			vm.params = { isLoadData: vm.isLoadData, sidebarType: "Com_Employment", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }
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

			vm.selectedCode.subscribe((newValue) => {
				let selectedItem = _.find(vm.employeeList(), emp => {
					return emp.code == newValue;
				});
				vm.currentItemName(selectedItem ? selectedItem.name : '');
				vm.params.empCode(newValue);
				vm.params.titleName = vm.currentItemName();
				vm.selectedId(newValue);
				
				vm.getBtnContent();
				$('#list-box').focus();
			});

			$('#empt-list-setting').ntsListComponent(vm.listComponentOption).done(() => {
				vm.employeeList($('#empt-list-setting').getDataList());

				vm.selectedClosureId.subscribe(() => {
					$('#empt-list-setting').ntsListComponent(vm.listComponentOption);
				});
			});
		}

		getBtnContent() {
			const vm = this;
			if (vm.selectedId() == '') {
				return;
			}
			
			vm.$ajax(KMK004N_API.EMP_GET_BASIC_SETTING + "/" + vm.selectedId()).done((data: any) => {
				if (data.deforLaborTimeEmpDto != null && data.empDeforLaborMonthActCalSetDto != null) {
					vm.btn_text('KMK004_341');
				} else vm.btn_text('KMK004_340');
			})

		}

		mounted() {
		}

		register() {
			const vm = this;

			let param: IWorkTimeSetCom[] = [];

			_.forEach(ko.unwrap(vm.workTimes), ((value) => {
				const t: IWorkTimeSetCom = { employmentCode: vm.selectedId(), laborAttr: 1, yearMonth: value.yearMonth(), laborTime: { legalLaborTime: value.laborTime(), withinLaborTime: null, weekAvgTime: null } };
				param.push(t);
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}
				
				vm.$ajax(KMK004N_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetEmps: param })).done(() => {
					_.remove(ko.unwrap(vm.years), ((value) => {
						return value.year == ko.unwrap(vm.selectedYear) as number;
					}));
					vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					vm.reloadInitData();
				}).then(() => vm.$dialog.info({ messageId: "Msg_15" }))
					.then(() => {
						$(document).ready(() => {
							$('#list-box').focus();
						})
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
						.then(() => vm.$ajax(KMK004N_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear(), employmentCode: vm.selectedId() })))
						.done(() => {
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
							vm.reloadInitData();
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


		close() {
			const vm = this;
			vm.$window.close();
		}

		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', ko.toJS(vm.params)).then(() => {
				vm.isLoadData(true);
				vm.reloadInitData();
				vm.getBtnContent();
			});
		}

		reloadInitData() {
			let vm = this;
			vm.$ajax(KMK004_API.EMP_INIT_SCREEN)
				.done((data: any) => {
					let settings: UnitAlreadySettingModel[] = [];
					_.forEach(data.employmentCds, ((value) => {
						let s: UnitAlreadySettingModel = { code: value.employmentCode, isAlreadySetting: true };
						settings.push(s);
					}));
					vm.alreadySettingList(settings);
				})
		}

		openViewR() {
			const vm = this;

			vm.$window.modal('at', '/view/kmk/004/r/index.xhtml', {
				screenMode: vm.type,
				selected: vm.selectedId(),
				year: vm.selectedYear(),
				laborAttr: 1
			}).then(() => {
				vm.$ajax(KMK004N_API.COPY_SETTING).done((data) => {
					vm.alreadySettingList(_.map(data, (item: string) => { return { code: item, isAlreadySetting: true } }));
				}).then(() => {
					$(document).ready(() => {
						$('#list-box').focus();
					});
				}).always(() => { vm.$blockui("clear"); });
			});
		}
	}
}