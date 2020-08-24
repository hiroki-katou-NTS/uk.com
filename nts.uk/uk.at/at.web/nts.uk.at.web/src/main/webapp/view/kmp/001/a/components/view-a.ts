/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const template = `
		<div id="com-ccg001"></div>
		<div class="sidebar-content-header">
			<span class="title" data-bind="text: $i18n('KMP001_1')"></span>
			<!--
			<button data-bind="text: $i18n('KMP001_4'), click: addNew, enable: mode() == 'update'"></button>
			-->
			<button id="add" class="proceed" data-bind="text: $i18n('KMP001_5'), click: addStampCard"></button>
			<button class="danger" data-bind="text: $i18n('KMP001_6'), click: deleteStampCard, enable: mode() == 'update'"></button>
			<!-- ko if: attendance -->
			<button data-bind="text: $i18n('KMP001_7'), click: showDiaLog"></button>
			<!-- /ko -->
		</div>
		<div class="view-kmp">
			<div class="list-component float-left viewa">
				<div id="list-employee"></div>
			</div>
			<div class="float-left model-component" 
				data-bind="component: { 
					name: 'editor-area', 
					params: { model: model, stampCardEdit: stampCardEdit, textInput: textInput}}"></div>
		<div>
`;

	const KMP001A_API = {
		GET_STAMPCARDDIGIT: 'screen/pointCardNumber/getStampCardDigit',
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
		attendance: KnockoutObservable<boolean> = ko.observable(true);

		public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
		public model: share.Model = new share.Model();
		public settings: KnockoutObservableArray<ISetting> = ko.observableArray([]);
		public employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
		public baseDate: KnockoutObservable<string> = ko.observable('');
		public currentCodes: KnockoutObservableArray<string> = ko.observableArray([]);
		public mode: KnockoutObservable<MODE> = ko.observable('new');
		public stampCardEdit: share.StampCardEdit = new share.StampCardEdit();
		public textInput: KnockoutObservable<string> = ko.observable('');

		created() {
			const vm = this;

			$(document).ready(function() {
				$('#com-ccg001').focus();
			});

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

								if (data.stampCardDto.length > 0) {
									data.stampCardDto[0].checked = true;
									vm.mode("update");
								} else {
									vm.mode("new");
								}

								vm.$errors('clear').then(() => {
									vm.model.update(ko.toJS(data));
									vm.model.employeeId(current.employeeId);
								})
							});
					}
					$(document).ready(function() {
						$('.ip-stamp-card').focus();
					});
				});

			vm.employees
				.subscribe(() => {
					vm.reloadData(0);
					vm.model.code.valueHasMutated();
					$(document).ready(function() {
						$('.ip-stamp-card').focus();
					});
				})

		}

		mounted() {
			const vm = this;
			const dataFormate = 'YYYY/MM/DD';

			vm.$errors('clear');

			if (vm.$user.role.isInCharge.attendance) {
				vm.attendance(true);
			} else {
				vm.attendance(false);
			}

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
					maxRows: 20,
					maxWidth: 450
				} as any
			);

			$('#com-ccg001')
				.ntsGroupComponent({
					/** Common properties */
					systemType: 2, //システム区分	
					showEmployeeSelection: true,
					showQuickSearchTab: true, //クイック検索
					showAdvancedSearchTab: true,
					showBaseDate: true, //基準日利用
					showClosure: false, //就業締め日利用
					showAllClosure: false, //全締め表示
					showPeriod: false, //対象期間利用
					periodFormatYM: true, //対象期間精度
					maxPeriodRange: 'oneMonth', //最長期間

					/** Required parameter */
					baseDate: ko.observable(moment().format(dataFormate)),
					periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
					periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
					inService: true,
					leaveOfAbsence: true,
					closed: true,
					retirement: true,

					/** Quick search tab options */
					showAllReferableEmployee: true, //参照可能な社員すべて
					showOnlyMe: true, //自分だけ
					showSameDepartment: false,
					showSameDepartmentAndChild: false,
					showSameWorkplace: true, //同じ職場の社員
					showSameWorkplaceAndChild: true, //同じ職場とその配下の社員

					/** Advanced search properties */
					showEmployment: true, //雇用条件
					showDepartment: false,
					showWorkplace: true, //職場条件
					showClassification: true, //分類条件
					showJobTitle: true, //職位条件
					showWorktype: false, //勤種条件
					isMutipleCheck: true, //選択モード

					/**
					* Self-defined function: Return data from CCG001
					* @param: data: the data return from CCG001
					*/
					returnDataFromCcg001: function(data: any) {
						vm.baseDate(moment.utc(data.baseDate, DATE_FORMAT).format("YYYY-MM-DD"));

						vm.employees([]);

						for (var i = 0; i < data.listEmployee.length; i++) {
							vm.employeeIds.push(data.listEmployee[i].employeeId);
						}

						const employees = data.listEmployee
							.map(m => ({
								affiliationId: m.affiliationId,
								affiliationName: m.affiliationName,
								code: m.employeeCode,
								name: m.employeeName,
								employeeId: m.employeeId
							}));

						vm.employees(employees);
						vm.model.clear();
						vm.reloadData();
					}
				});
		}

		showDiaLog() {
			const vm = this;

			vm.$window
				.modal('/view/kmp/001/d/index.xhtml')
				.then((data: IStampCardEdit) => {
					vm.stampCardEdit.update(data);
				});
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
			$(document).ready(function() {
				$('.ip-stamp-card').focus();
			});
		}

		addStampCard() {
			const vm = this,
				model: IModel = ko.toJS(vm.model),
				index = _.map(ko.unwrap(vm.employees), m => m.code).indexOf(model.code);;

			var stampInput = "";

			if (ko.unwrap(vm.model.code) != '') {

				if (ko.toJS(vm.model.stampCardDto).length > 0) {
					const stamp: share.IStampCard = ko.toJS(model.stampCardDto[0]);
					stampInput = stamp.stampNumber;
				} else {
					stampInput = ko.toJS(vm.textInput);
				}

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
										nts.uk.ui.dialog.error({ messageId: err.messageId });
									})
									.always(() => vm.$blockui("clear"));
							}
						});
				}
			}
			$(document).ready(function() {
				$('.ip-stamp-card').focus();
			});
		}

		reloadData(selectedIndex: number = 0) {
			const vm = this;
			vm.$blockui("invisible")
			vm.$ajax(KMP001A_API.GET_STATUS_SETTING, ko.toJS(vm.employeeIds))
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