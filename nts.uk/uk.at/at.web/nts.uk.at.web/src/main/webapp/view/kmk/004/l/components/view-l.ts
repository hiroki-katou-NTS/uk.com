/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;

	const KMK004L_API = {
		REGISTER_WORK_TIME: 'screen/at/kmk004/viewL/monthlyWorkTimeSet/update',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewL/monthlyWorkTimeSet/delete'
	};

	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, click: register, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: false, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: checkDelete, click: remove, i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-l">
				<div class="header-l">
					<div id="title", data-bind="i18n: 'KMK004_307'"></div>
					<hr></hr>
					<div class="header_title">
						<div data-bind="ntsFormLabel: {inline: true, text: $i18n('KMK004_229')}"></div>
						<button data-bind="i18n: 'KMK004_231', click: openViewP"></button>
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
										selectedId: ko.observable(''),
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
										selectedId: ko.observable(''),
										yearDelete: yearDelete,
										startYM: startYM
									}
								}"></div>
					</div>
			</div>
	</div>
	`;

	interface IWorkTimeSetCom {
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
		name: 'view-l',
		template
	})

	export class ViewLComponent extends ko.ViewModel {

		selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		public existYear: KnockoutObservable<boolean> = ko.observable(true);
		public type: SIDEBAR_TYPE = 'Com_Company';
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public startYM: KnockoutObservable<number> = ko.observable(0);

		constructor(private params: IParam) {
			super();
		}

		created() {
			let vm = this;
			vm.params = { isLoadData: vm.isLoadData, sidebarType: "Com_Company", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }
			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length > 0) {
						vm.existYear(true);
					} else {
						vm.existYear(false);
						vm.checkDelete(false);
					}
				});

			vm.selectedYear
				.subscribe(() => {
					const exist = _.find(ko.unwrap(vm.years), (m: IYear) => m.year as number == ko.unwrap(vm.selectedYear) as number);

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

		mounted() {
			$(document).ready(() => {
				$('#list-box').focus();
			});

		}

		register() {
			const vm = this;

			let param: IWorkTimeSetCom[] = [];

			_.forEach(ko.unwrap(vm.workTimes), ((value) => {
				const t: IWorkTimeSetCom = { laborAttr: 1, yearMonth: value.yearMonth(), laborTime: { legalLaborTime: value.laborTime(), withinLaborTime: null, weekAvgTime: null } };
				param.push(t);
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}

				vm.$ajax(KMK004L_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetComs: param })).done(() => {
					_.remove(ko.unwrap(vm.years), ((value) => {
						return value.year == ko.unwrap(vm.selectedYear) as number;
					}));
					vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
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
					vm.$blockui("invisible");
					vm.$ajax(KMK004L_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear() })).done(() => {
						vm.yearDelete(ko.unwrap(vm.selectedYear));
					})
						.then(() => {
							_.remove(ko.unwrap(vm.years), ((value) => {
								return value.year == ko.unwrap(vm.selectedYear);
							}));
							vm.years(ko.unwrap(vm.years));
							if (ko.unwrap(vm.years).length > 0) {
								vm.selectedYear(ko.unwrap(vm.years)[old_index].year);
							}
						})
						.then(() => {vm.selectedYear.valueHasMutated();})
						.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
						.then(() => {
							$(document).ready(() =>{
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
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params).then(() => {
				vm.isLoadData(true);
			});
		}

	}
}
