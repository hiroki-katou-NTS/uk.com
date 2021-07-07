/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	const DATE_FORMAT = 'YYYY/MM/DD';

	const KMK004O_API = {
		REGISTER_WORK_TIME: 'screen/at/kmk004/viewO/monthlyWorkTimeSet/update',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewO/monthlyWorkTimeSet/delete',
		GET_EMPLOYEE_ID: 'screen/at/kmk004/viewO/getEmployeeIds',
		SHA_GET_BASIC_SETTING: 'screen/at/kmk004/viewO/getBasicSetting',
		COPY_SETTING: 'screen/at/kmk004/viewO/after-copy',
		GET_WORK_TIME_BY_COM: 'screen/at/kmk004/viewL/getWorkingHoursByCompany',
	};

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Person'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: checkAdd, click: register, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, enable: checkDelete, click: openViewR, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: checkDelete, click: remove, i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-o">
	<div id="com-ccg001"></div>
	<table>
		<tr>
			<td id="view-o-left-side">
				<div id="com-kcp005"></div>
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
							<button data-bind="enable: initBtnEnable, i18n: btn_text, click: openViewP"></button>
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
										startYM: startYM,
										isNewYear: isNewYear
									}
								}"></div>
								
								<div id= class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years,
										checkEmployee: checkEmployee,
										workTimes: workTimes,
										type: type,
										selectedId: selectedId,
										yearDelete: yearDelete,
										startYM: startYM,
										isNewYear: isNewYear
									}
								}"></div>
							</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	`;

	interface UnitModel {
		id?: string;
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
		optionalColumn?: any;
	}

	interface UnitAlreadySettingModel {
		code: string;
		isAlreadySetting: boolean;
	}

	interface IWorkTimeSetCom {
		empId: string; //社員 ID
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
		name: 'view-o',
		template
	})

	export class ViewOComponent extends ko.ViewModel {
		ccg001ComponentOption: GroupOption;

		//KCP005
		listComponentOption: any;
		selectedCode: KnockoutObservable<any> = ko.observable();
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

		employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
		currentItemName: KnockoutObservable<string>;
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public type: SIDEBAR_TYPE = 'Com_Person';
		public selectedId: KnockoutObservable<string> = ko.observable('');
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		btn_text: KnockoutObservable<string> = ko.observable('KMK004_342');
		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(true);
		initBtnEnable: KnockoutObservable<boolean> = ko.observable(false);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public startYM: KnockoutObservable<number> = ko.observable(0);
		public isNewYear: KnockoutObservable<boolean> = ko.observable(false);
		public checkAdd: KnockoutObservable<boolean> = ko.observable(false);

		constructor(private params: IParam) {
			super();
		}

		created() {
			const vm = this;
			const date = moment(new Date()).toDate();

			vm.ccg001ComponentOption = {
				/** Common properties */
				systemType: 1,
				showEmployeeSelection: true,
				showQuickSearchTab: true,
				showAdvancedSearchTab: true,
				showBaseDate: true,
				showClosure: true,
				showAllClosure: true,
				showPeriod: true,
				periodFormatYM: false,

				/** Required parameter */
				baseDate: date,
				periodStartDate: date,
				periodEndDate: date,
				leaveOfAbsence: true,
				closed: true,
				retirement: true,

				/** Quick search tab options */
				showAllReferableEmployee: true,
				showOnlyMe: true,
				showSameDepartment: false,
				showSameDepartmentAndChild: false,
				showSameWorkplace: true,
				showSameWorkplaceAndChild: true,

				/** Advanced search properties */
				showEmployment: true,
				showDepartment: false,
				showWorkplace: true,
				showClassification: true,
				showJobTitle: true,
				showWorktype: true,
				isMutipleCheck: true,

				returnDataFromCcg001: function(data: any) {
					const employees = data.listEmployee
						.map((m: any) => ({
							workplaceName: m.affiliationName,
							code: m.employeeCode,
							name: m.employeeName,
							id: m.employeeId
						}));

					vm.employeeList(employees);
					vm.getEmployeeIds();
					if (employees.length) {
						vm.selectedCode(employees[0].code);
						$('#list-box').focus();
						if (employees.length > 300) {
							vm.initEmployeeList();
						}
					}
				}

			}

			//KCP005
			vm.currentItemName = ko.observable('');

			vm.params = { isLoadData: vm.isLoadData, sidebarType: "Com_Person", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null };
			vm.years
				.subscribe((years: IYear[]) => {
					const [first] = years;

					if (first) {
						if (ko.unwrap(vm.selectedYear) == null) {
							vm.selectedYear(first.year);
						}

						vm.existYear(true);
						vm.checkDelete(true);
					} else {
						vm.selectedYear(null);

						vm.existYear(false);
						vm.checkDelete(false);
					}

				});

			vm.workTimes
				.subscribe(() => {
					let check = false;
					_.forEach(ko.unwrap(vm.workTimes), ((value) => {
						if (ko.unwrap(value.check)) {
							check = true;
						}
					}));
					if (ko.unwrap(vm.existYear)) {
						vm.checkAdd(check);
					} else {
						vm.checkAdd(false);
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

		}

		getEmployeeIds() {
			const vm = this;
			vm.$ajax(KMK004O_API.GET_EMPLOYEE_ID)
				.done((data: any) => {
					let settings: UnitAlreadySettingModel[] = [];
					_.forEach(data, ((value) => {
						const setting : UnitModel = _.find(ko.unwrap(vm.employeeList), e => e.id === value.employeeId);
						
						if (setting){
							let s: UnitAlreadySettingModel = { code: setting.code, isAlreadySetting: true };
							settings.push(s);
						}

					}));
					vm.alreadySettingList(settings);
				})
		}

		mounted() {
			let vm = this;
			vm.initEmployeeList();

			$('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {
				$("#ccg001-btn-search-drawer").focus();
				vm.selectedCode.subscribe((newValue) => {
					let selectedItem: UnitModel = _.find(vm.employeeList(), ['code', newValue]);
					vm.currentItemName(selectedItem.code + " " + selectedItem.name);
					vm.params.empId(selectedItem.id);
					vm.params.titleName = vm.currentItemName();
					vm.selectedId(selectedItem.id);
					vm.initBtnEnable(true);

					vm.getBtnContent();
					$('#list-box').focus();
				});
			});
		}

		initEmployeeList() {
			let vm = this;

			vm.listComponentOption = {
				isShowAlreadySet: true,
				isMultiSelect: false,
				listType: ListType.EMPLOYEE,
				employeeInputList: vm.employeeList,
				selectType: SelectType.SELECT_FIRST_ITEM,
				selectedCode: vm.selectedCode,
				isDialog: false,
				isShowNoSelectRow: false,
				alreadySettingList: vm.alreadySettingList,
				isShowWorkPlaceName: true,
				isShowSelectAllButton: false,
				disableSelection: false
			};
			
			$('#com-kcp005').ntsListComponent(vm.listComponentOption);

		}

		getBtnContent() {
			const vm = this;
			vm.$ajax(KMK004O_API.SHA_GET_BASIC_SETTING + "/" + vm.selectedId()).done((data: any) => {
				if (data.deforLaborTimeShaDto != null && data.shaDeforLaborMonthActCalSetDto != null) {
					vm.btn_text('KMK004_343');
				} else vm.btn_text('KMK004_342');
			})
		}

		register() {
			const vm = this;
			var firstItem = ko.toJS(vm.workTimes()[0]).yearMonth.toString().substring(0, 4);

			let param: IWorkTimeSetCom[] = [];
			_.forEach(ko.unwrap(vm.workTimes), ((value) => {
				if (ko.toJS(value.yearMonth().toString().substring(0, 4)) == firstItem) {
					const t: IWorkTimeSetCom = {
						empId: vm.selectedId(),
						laborAttr: 1,
						yearMonth: parseInt(ko.unwrap(vm.selectedYear) + value.yearMonth().toString().substring(4, 6)),
						laborTime: {
							legalLaborTime: value.laborTime(),
							withinLaborTime: null,
							weekAvgTime: null
						}
					};
					if (t.laborTime.legalLaborTime != null) {
						param.push(t);
					}

				} else if (ko.toJS(value.yearMonth().toString().substring(0, 4)) != firstItem) {

					const t: IWorkTimeSetCom = {
						empId: vm.selectedId(),
						laborAttr: 1,
						yearMonth: parseInt(parseInt(ko.unwrap(vm.selectedYear).toString()) + 1 + value.yearMonth().toString().substring(4, 6)),
						laborTime: {
							legalLaborTime: value.laborTime(),
							withinLaborTime: null,
							weekAvgTime: null
						}
					};
					if (t.laborTime.legalLaborTime != null) {
						param.push(t);
					}

				}
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}

				vm.$ajax(KMK004O_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear(), employeeId: vm.selectedId() }))
						.done(() => {
							vm.$ajax(KMK004O_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetShas: param })).done(() => {
								_.remove(ko.unwrap(vm.years), ((value) => {
									return value.year == ko.unwrap(vm.selectedYear) as number;
								}));
								vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
								vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
								vm.getEmployeeIds();
							}).then(() => vm.$dialog.info({ messageId: "Msg_15" }))
								.then(() => {
									$(document).ready(() => {
										$('#list-box').focus();
									});
								}).always(() => {
									vm.$errors('clear');
								});
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
						.then(() => vm.$ajax(KMK004O_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear(), employeeId: vm.selectedId() })))
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
							vm.getEmployeeIds();
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
				vm.getEmployeeIds();
				vm.getBtnContent();
			});
		}

		openViewR() {
			const vm = this;

			vm.$window.modal('at', '/view/kmk/004/r/index.xhtml', {
				data: vm.employeeList(),
				screenMode: vm.type,
				selected: vm.selectedCode(),
				year: vm.selectedYear(),
				laborAttr: 1
			}).then(() => {
				vm.$ajax(KMK004O_API.COPY_SETTING).done((data) => {
					vm.alreadySettingList(_.map(data, (item: string) => {
						let emp: any = _.find(vm.employeeList(), ['id', item]);
						if (!emp) {
							return { code: null, isAlreadySetting: false };
							}
						return { code: emp.code, isAlreadySetting: true } 
					}));
				}).then(() => {
					$(document).ready(() => {
						$('#list-box').focus();
					});
				}).always(() => { vm.$blockui("clear"); });
			});
		}

	}
}