/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.b {
	const template = `
		<div class="sidebar-content-header">
			<span class="title" data-bind= "text: $i18n('KMP001_2')"></span>
			<button class="proceed" data-bind= "text: $i18n('KMP001_5'), click: addStampCard"></button>
			<button class="danger" data-bind= "text: $i18n('KMP001_6'), click: deleteStamCard"></button>
		</div>
		<div class="search_label" style="padding-bottom: 0px">
			<span class="sub_title" data-bind= "text: $i18n('KMP001_22')"></span>
			<input data-bind="ntsTextEditor: {value: inputStampCard}" style="width: 225px"/>
			<button id="top_bottom" data-bind= "text: $i18n('KMP001_23'),
												click: getStampCard">
			</button>
			<button id="top_bottom" data-bind= "text: $i18n('KMP001_24'),
												click: getAllStampCard"></button>
		</div>
		<div class="view-kmp">
			<div class="float-left list-component">
				<div class="caret-right caret-background bg-green" style="padding: 10px;">
					<table id="card-list" 
						data-bind="ntsGridList: {
							height: 300,
							optionsValue: 'stampNumber',
							columns: [
					            { headerText: $i18n('KMP001_22'), prop: 'stampNumber', width: 180 },
					            { headerText: $i18n('KMP001_8'), prop: 'employeeCode', width: 112 },
					            { headerText: $i18n('KMP001_9'), prop: 'businessName', width: 110 }
					        ],
							dataSource: items,
							value: model.stampNumber
						}">
					</table>
				</div>
			</div>
			<div class="float-left model-component">
				<table>
					<tbody>
						<tr>
							<td class="label-column-left">
								<div id="td-bottom">
									<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_22') }"></div>
								</div>
							</td>
							<td class="data">
								<div id="td-bottom" data-bind="text: model.stampNumber"></div>
							</td>
						</tr>
						<tr>
							<td class="label-column-left">
								<div id="td-bottom">
									<div id=" td-bottom" data-bind="ntsFormLabel: { text: $i18n('KMP001_9'), required: true }"></div>
									<button id="td-bottom" data-bind="i18n: 'KMP001_26', click: openDialogCDL009a"></button>
								</div>
							</td>
							<td class="data">
								<div id="td-bottom">
									<div id="td-bottom" data-bind="text: employee.employeeCode"></div>
									<div id="td-bottom" style="margin-left: 10px" data-bind="text: employee.businessName"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label-column-left">
								<div id="td-bottom">
									<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_20') }"></div>
								</div>
							</td>
							<td class="data">
								<div id="td-bottom" data-bind="text: employee.entryDate"></div>
							</td>
						</tr>
						<tr>
							<td class="label-column-left">
								<div id="td-bottom">
									<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_21') }"></div>
								</div>
							</td>
							<td class="data">
							<div id="td-bottom" data-bind="text: employee.retiredDate"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	`;

	interface Params {

	}

	const KMP001B_API = {
		GET_STAMPCARD: 'screen/pointCardNumber/getEmployeeFromCardNo/',
		GET_ALL_STAMPCARD: 'screen/pointCardNumber/getAllEmployeeFromCardNo/',
		GET_INFO_EMPLOYEE: 'screen/pointCardNumber/getEmployeeInformationViewB/',
		ADD_STAMP: 'at/record/register-stamp-card/view-b/save',
		DELETE_STAMP: 'at/record/register-stamp-card/view-b/delete'
	};

	@component({
		name: 'view-b',
		template
	})
	export class ViewBComponent extends ko.ViewModel {
		public params!: Params;
		public inputStampCard: KnockoutObservable<string> = ko.observable('');
		public items: KnockoutObservableArray<IStampCard> = ko.observableArray([]);
		public currentCode: KnockoutObservable<string> = ko.observable('');
		public model: StampCard = new StampCard();
		public employee: EmployeeVIewB = new EmployeeVIewB();
		public mode: KnockoutObservable<MODE> = ko.observable('all');
		public stampCardBackSelect: string = '';

		created(params: Params) {
			const vm = this;
			this.params = params;

			vm.model.stampNumber
				.subscribe((c: string) => {
					const stampCards: IStampCard[] = ko.toJS(vm.items);
					const current = _.find(stampCards, e => e.stampNumber === c);

					vm.model.clear();
					vm.employee.clear();
					if (current) {
						vm.employee.employeeId(current.employeeId);
						vm.model.employeeId(current.employeeId);
						vm.employee.employeeId.valueHasMutated();
					}
					else {
						vm.items([]);
						vm.model.clear();
						vm.employee.clear();
					}
				});

			vm.employee.employeeId
				.subscribe((c: string) => {
					if (c != '') {
						vm.$ajax(KMP001B_API.GET_INFO_EMPLOYEE + ko.toJS(c))
							.then((data: IEmployeeVIewB[]) => {
								vm.employee.update(ko.toJS(data));
							})
					}
				})
		}

		getStampCard() {
			const vm = this;
			vm.$blockui("invisible");
			const stampInput: string = ko.toJS(vm.inputStampCard);
			vm.mode("one");
			vm.stampCardBackSelect = stampInput;

			if (stampInput == '') {
				vm.$dialog.info({ messageId: "Msg_1679" });
			} else {
				vm.reloadAllStampCard(0);
			}
			vm.$blockui("clear")
		}

		getAllStampCard() {
			const vm = this;
			vm.mode("all");
			vm.inputStampCard('');
			vm.reloadAllStampCard(0);
		}

		openDialogCDL009a() {
			const vm = this;
			const params = { selectedIds: [], isMultiple: false, baseDate: new Date(), target: 1 };

			vm.$window
				.storage('CDL009Params', params)
				.then(() => vm.$window.modal('com', '/view/cdl/009/a/index.xhtml'))
				.then(() => vm.$window.storage('CDL009Output'))
				.then((data: string | string[]) => {
					if (data != '') {
						vm.employee.employeeId(ko.toJS(data));
					}
				});
		}

		reloadAllStampCard(selectedIndex: number = 0) {
			const vm = this;

			if (ko.unwrap(vm.mode) == "all") {
				vm.model.clear();
				vm.$blockui("invisible")
					.then(() => vm.$ajax(KMP001B_API.GET_ALL_STAMPCARD))
					.then((data: IDataResponse) => {
						const { stampCards, employeeInfo } = data;
						const stampCardList: IStampCard[] = [];
						_.each(stampCards, (st: IStampCardResponse) => {
							const { employeeId, stampNumber } = st;
							const exist = _.find(employeeInfo, (emp: IEmployeeInfoResponse) => emp.employeeId === employeeId);

							if (exist) {
								const { businessName, employeeCode } = exist;
								const data: IStampCard = {
									employeeId,
									businessName,
									employeeCode,
									stampNumber
								};

								stampCardList.push(data);
							}
						});

						vm.items(stampCardList);
						const record = stampCardList[selectedIndex];

						if (record) {
							vm.model.stampNumber(record.stampNumber);
							vm.model.update(record);
						} else {
							vm.model.clear();
						}
					})
					.always(() => {
						vm.$blockui("clear");
					});

			} else {
				vm.$blockui("invisible");
				vm.$ajax(KMP001B_API.GET_STAMPCARD + vm.stampCardBackSelect)
					.then((data: IStampCard[]) => {
						if (data) {
							vm.items(data);
							if (data[selectedIndex]) {
								vm.model.stampNumber(data[selectedIndex].stampNumber);
								vm.model.update(data[selectedIndex]);
							} else {
								vm.model.stampNumber('');
							}
						}

					}).always(() => {
						vm.$blockui("clear");
					});

			}
		}

		addStampCard() {
			const vm = this,
				command = { employeeId: ko.toJS(vm.model.employeeId), employeeIdSelect: ko.toJS(vm.employee.employeeId), cardNumber: ko.toJS(vm.model.stampNumber) },
				oldIndex = _.map(ko.unwrap(vm.items), m => m.stampNumber).indexOf(command.cardNumber),
				newIndex = oldIndex == ko.unwrap(vm.items).length - 1 ? oldIndex - 1 : oldIndex;

			vm.$blockui("invisible");
			if (ko.toJS(vm.model.employeeId) != '' && ko.toJS(vm.employee.employeeId) != '') {
				if (ko.toJS(vm.model.employeeId) === ko.toJS(vm.employee.employeeId)) {
					vm.$dialog.info({ messageId: "Msg_15" });
				} else {
					vm.$ajax(KMP001B_API.ADD_STAMP, command)
						.then(() => vm.$dialog.info({ messageId: "Msg_15" }))
						.then(() => vm.reloadAllStampCard(newIndex));
				}
			}
			vm.$blockui("clear");
		}

		deleteStamCard() {
			const vm = this,
				command = { employeeId: ko.toJS(vm.model.employeeId), cardNumber: ko.toJS(vm.model.stampNumber) },
				oldIndex = _.map(ko.unwrap(vm.items), m => m.stampNumber).indexOf(command.cardNumber),
				newIndex = oldIndex == ko.unwrap(vm.items).length - 1 ? oldIndex - 1 : oldIndex;

			vm.$blockui("invisible");
			if (ko.toJS(vm.model.employeeId) != '' && ko.toJS(vm.employee.employeeId) != '') {
				nts.uk.ui.dialog
					.confirm({ messageId: "Msg_18" })
					.ifYes(() => {
						vm.$ajax(KMP001B_API.DELETE_STAMP, command)
							.done(() => vm.$dialog.info({ messageId: "Msg_16" }))
							.then(() => vm.model.clear())
							.then(() => vm.reloadAllStampCard(newIndex));
					})
			}
			vm.$blockui("clear");
		}
	}

	interface IDataResponse {
		employeeInfo: IEmployeeInfoResponse[];
		stampCards: IStampCardResponse[];
	}

	interface IEmployeeInfoResponse {
		businessName: string;
		employeeCode: string;
		employeeId: string;
	}

	interface IStampCardResponse {
		employeeId: string;
		stampNumber: string;
	}

	interface IStampCard {
		stampNumber: string;
		businessName: string;
		employeeCode: string;
		employeeId: string;
	}

	interface IEmployeeVIewB {
		businessName: string;
		employeeCode: string;
		employeeId: string;
		entryDate: Date;
		retiredDate: Date;
	}

	class StampCard {
		stampNumber: KnockoutObservable<String> = ko.observable('');
		employeeCode: KnockoutObservable<String> = ko.observable('');
		businessName: KnockoutObservable<String> = ko.observable('');
		employeeId: KnockoutObservable<String> = ko.observable('');

		constructor(params?: IStampCard) {
			const seft = this;

			if (params) {
				seft.stampNumber(params.stampNumber);
				seft.update(params);
			}
		}

		update(params: IStampCard) {
			const seft = this;
			seft.employeeCode(params.stampNumber);
			seft.employeeCode(params.employeeCode);
			seft.businessName(params.businessName);
			seft.employeeId(params.employeeId);
		}

		clear() {
			const self = this;
			self.update({ stampNumber: '', employeeCode: '', businessName: '', employeeId: '' });
		}
	}

	class EmployeeVIewB {
		businessName: KnockoutObservable<string> = ko.observable('');
		employeeCode: KnockoutObservable<string> = ko.observable('');
		employeeId: KnockoutObservable<string> = ko.observable('');
		entryDate: KnockoutObservable<Date | null> = ko.observable(null);
		retiredDate: KnockoutObservable<Date | null> = ko.observable(null);

		constructor(params?: IEmployeeVIewB) {
			const seft = this;

			if (params) {
				seft.employeeId(params.employeeId);
				seft.update(params);
			}
		}

		update(params: IEmployeeVIewB) {
			const seft = this;

			seft.employeeCode(params.employeeCode);
			seft.businessName(params.businessName);
			seft.entryDate(params.entryDate);
			seft.retiredDate(params.retiredDate);
		}

		clear() {
			const self = this;
			self.update({ businessName: '', employeeCode: '', employeeId: '', entryDate: null, retiredDate: null })
		}
	}

	type MODE = 'one' | 'all';
}