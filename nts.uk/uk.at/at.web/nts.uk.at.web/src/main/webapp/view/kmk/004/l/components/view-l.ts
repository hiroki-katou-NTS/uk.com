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
		<button class="danger" data-bind="enable: existYear, click: remove, i18n: 'KMK004_227'"></button>
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
								
								<div class= "box-year" id= "class= "box-year" data-bind="component: {
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
										years: years,
										workTimes: workTimes,
										type: type
									}
								}"></div>
					</div>
			</div>
	</div>
	`;

	interface IWorkTimeSetCom {
		laborAttr: number;
		yearMonth: number;
		laborTime: ILaborTime;
	}

	interface ILaborTime {
		legalLaborTime: number,
		withinLaborTime: number,
		weekAvgTime: number
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

		constructor(private params: IParam) {
			super();
		}

		created() {
			let vm = this;
			vm.params = { isLoadData: vm.isLoadData, sidebarType: "Com_Company", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null }
			vm.selectedYear
				.subscribe(() => {
					vm.$errors('clear');
					if (vm.selectedYear != null) {
						vm.existYear(true);
					}

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
				const t: IWorkTimeSetCom = { laborAttr: 1, yearMonth: value.yearMonth(), laborTime: { legalLaborTime: value.laborTime(), withinLaborTime: null, weekAvgTime: null } };
				param.push(t);
			}));

			vm.$validate('.nts-editor').then((valid: boolean) => {
				if (!valid) {
					return;
				}

				vm.$ajax(KMK004L_API.REGISTER_WORK_TIME, ko.toJS({ workTimeSetComs: param })).done(() => {
					vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
						vm.close();
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
					vm.$ajax(KMK004L_API.DELETE_WORK_TIME, ko.toJS({ year: vm.selectedYear()})).done(() => {
						vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
							vm.close();
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
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params).then(() => {
				vm.isLoadData(true);
			});
		}

	}
}
