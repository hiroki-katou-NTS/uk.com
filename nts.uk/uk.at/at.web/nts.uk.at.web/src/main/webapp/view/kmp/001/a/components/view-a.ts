/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const template = `
		<div data-bind="component: { 
					name: 'ccg001', 
					params: { employees: employees, baseDate: baseDate }}">
		</div>
		<div id="functions-area">
			<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmp/001/h/index.xhtml' },text: $i18n('KMP001_100')"></a>
			<button id="add" class="proceed" data-bind="text: $i18n('KMP001_5'), click: addStampCard"></button>
			<button class="danger" data-bind="text: $i18n('KMP001_6'), click: deleteStampCard, enable: mode() == 'update'"></button>
		</div>
		<div class="view-kmp">
			<div class="list-component float-left viewa">
				<div id="list-employee"></div>
			</div>
			<div class="float-left model-component" 
				data-bind="component: { 
					name: 'editor-area', 
					params: { model: model, stampCardEdit: stampCardEdit, textInput: textInput}}">
			</div>
		<div>
`;

	const KMP001A_API = {
		GET_STATUS_SETTING: 'screen/pointCardNumber/getStatusEmployeeSettingStampCard',
		GET_INFOMAITON_EMPLOYEE: 'screen/pointCardNumber/getEmployeeInfoCardNumber',
		ADD: 'at/record/register-stamp-card/view-a/save',
		UPDATE: 'at/record/register-stamp-card/view-a/update',
		DELETE: 'at/record/register-stamp-card/view-a/delete'
	};

	const DATE_FORMAT = 'YYYY/MM/DD';

	@component({
		name: 'view-a',
		template
	})
	class ViewA extends ko.ViewModel {

		public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
		public model: share.Model = new share.Model();
		public settings: KnockoutObservableArray<ISetting> = ko.observableArray([]);
		public baseDate: KnockoutObservable<string> = ko.observable('');
		public currentCodes: KnockoutObservableArray<string> = ko.observableArray([]);
		public mode: KnockoutObservable<MODE> = ko.observable('new');
		public stampCardEdit: share.StampCardEdit = new share.StampCardEdit();
		public textInput: KnockoutObservable<string> = ko.observable('');

		created() {
			const vm = this;

			vm.model.code
				.subscribe((c: string) => {
					const employees: IModel[] = ko.toJS(vm.employees);
					const current = _.find(employees, e => e.code === c)

					if (current) {
						vm.$ajax(KMP001A_API.GET_INFOMAITON_EMPLOYEE + "/" + ko.toJS(current.employeeId) + "/" + ko.toJS(current.affiliationId) + "/" + ko.toJS(vm.baseDate))
							.then((data: IModel) => {

								if (moment(data.retiredDate).format(DATE_FORMAT) === "9999/12/31") {
									data.retiredDate = null;
								}

								vm.mode("new");

								/*if (data.stampCardDto.length > 0) {
									data.stampCardDto[0].checked = true;
									vm.mode("update");
								} else {
									vm.mode("new");
								}*/

								vm.$errors('clear').then(() => {
									vm.model.update(ko.toJS(data));
									vm.model.employeeId(current.employeeId);
								})
							});
					}
					$(document).ready(function () {
						$('.ip-stamp-card').focus();
					});
				});

			vm.model.stampCardDto
				.subscribe(() => {
					const stampCards: [] = ko.toJS(vm.model.stampCardDto);
					let checkModeDelete: boolean = false;

					for (var i = 0; i < stampCards.length; i++) {
						const c: StampCard = stampCards[i];
						if (c.checked) {
							checkModeDelete = true;
							break;
						}
					}

					if (checkModeDelete) {
						vm.mode('update');
					} else {
						vm.mode('new');
					}
				})

			vm.employees
				.subscribe(() => {
					vm.reloadData(0);
					vm.model.code.valueHasMutated();
					$(document).ready(function () {
						$('.ip-stamp-card').focus();
					});
				});
		}

		mounted() {
			const vm = this;
			const dataFormate = 'YYYY/MM/DD';

			vm.$errors('clear');

			$('#list-employee').ntsListComponent(
				{
					isShowAlreadySet: true, //設定済表示
					isMultiSelect: false,
					listType: 4,
					employeeInputList: vm.employees,
					selectType: 1,
					selectedCode: vm.model.code,
					isShowNoSelectRow: false, //未選択表示
					alreadySettingList: vm.settings,
					isShowWorkPlaceName: true,  //職場表示
					isShowSelectAllButton: false,  //全選択表示
					isSelectAllAfterReload: true,
					disableSelection: false,
					maxRows: 21,
					maxWidth: 450
				} as any
			);
		}

		/*addNew() {
			const vm = this;
			if (ko.unwrap(vm.model.code) != '') {
				vm.mode("new");
				vm.model.addNewStampCard();
			}
			$(document).ready(function() {
				$('.ip-stamp-card').focus();
			});
		}*/

		deleteStampCard() {
			const vm = this,
				model: IModel = ko.toJS(vm.model),
				checkeds = model.stampCardDto.filter((f) => f.checked),
				index = _.map(ko.unwrap(vm.employees), m => m.code).indexOf(model.code);

			if (checkeds != null) {
				const command = { employeeId: model.employeeId, cardNumbers: checkeds.map(m => m.stampNumber), cardId: checkeds.map(m => m.stampCardId) };
				nts.uk.ui.dialog
					.confirm({ messageId: "Msg_18" })
					.ifYes(() => {
						vm.$blockui("invisible")
							.then(() => vm.$ajax(KMP001A_API.DELETE, command))
							.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
							.then(() => vm.reloadData(index))
							.then(() => vm.model.code.valueHasMutated())
							.always(() => vm.$blockui("clear"));
					})

			}
			$(document).ready(function () {
				$('.ip-stamp-card').focus();
			});
		}

		addStampCard() {
			const vm = this,
				model: IModel = ko.toJS(vm.model),
				index = _.map(ko.unwrap(vm.employees), m => m.code).indexOf(model.code);;

			var stampInput = ko.toJS(vm.textInput);

			if (ko.unwrap(vm.model.code) != '') {

				/*if (ko.toJS(vm.model.stampCardDto).length > 0) {
					const stamp: share.IStampCard = ko.toJS(model.stampCardDto[0]);
					stampInput = stamp.stampNumber;
				} else {
					stampInput = ko.toJS(vm.textInput);
				}*/

				if (stampInput == '') {
					vm.$dialog.info({ messageId: "Msg_1679" });
				} else {
					vm.validate()
						.then((valid: boolean) => {
							if (valid) {
								var s = (ko.toJS(vm.stampCardEdit.stampCardDigitNumber) - stampInput.length);

								if (s > 0) {
									switch (ko.toJS(vm.stampCardEdit.stampCardEditMethod)) {
										case 1:
											for (var i = 0; i < s; i++) {
												stampInput = "0" + stampInput;
											}
											break;
										case 2:
											for (var i = 0; i < s; i++) {
												stampInput = stampInput + "0";
											}
											break;
										case 3:
											for (var i = 0; i < s; i++) {
												stampInput = " " + stampInput;
											}
											break;
										case 4:
											for (var i = 0; i < s; i++) {
												stampInput = stampInput + " ";
											}
											break;
									}
								}

								const commandNew = { employeeId: ko.toJS(model.employeeId), cardNumber: stampInput };

								vm.$ajax(KMP001A_API.ADD, commandNew)
									.then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
									.then(() => vm.$blockui("invisible"))
									.then(() => vm.textInput(''))
									.then(() => vm.reloadData(index))
									.then(() => vm.model.code.valueHasMutated())
									.fail((err: any) => {
										vm.$dialog.error({ messageId: err.messageId });
									})
									.always(() => vm.$blockui("clear"));
							}
						});
				}
			}
			$(document).ready(function () {
				$('.ip-stamp-card').focus();
			});
		}

		reloadData(selectedIndex: number = 0) {
			const vm = this;
			const empids = ko.unwrap(vm.employees).map(m => m.employeeId);

			vm.$blockui("invisible")
				.then(() => vm.$ajax(KMP001A_API.GET_STATUS_SETTING, empids))
				.then((data: IEmployeeId[]) => {
					vm.settings([]);
					const employees: IModel[] = ko.toJS(vm.employees);
					const modelSetting = new Setting();

					for (var i = 0; i < data.length; i++) {
						const setting = _.find(employees, e => e.employeeId === data[i].employee);

						if (setting) {
							modelSetting.code(setting.code);
							modelSetting.isAlreadySetting(true);
							vm.settings.push(ko.toJS(modelSetting));
						}
					}

					if (ko.toJS(vm.model.code) == '') {
						vm.model.code(employees[selectedIndex].code);
					}
				}).then(() => {
					vm.$blockui("clear");
				});
		}

		public validate(action: 'clear' | undefined = undefined) {
			if (action === 'clear') {
				return $.Deferred().resolve()
					.then(() => $('.nts-input').ntsError('clear'));
			} else {
				return $.Deferred().resolve()
					/** Gọi xử lý validate của kiban */
					.then(() => $('.nts-input').trigger("validate"))
					/** Nếu có lỗi thì trả về false, không thì true */
					.then(() => !$('.nts-input').ntsError('hasError'));
			}
		}
	}

	interface IEmployeeId {
		employee: string;
	}

	type MODE = 'new' | 'update';
}