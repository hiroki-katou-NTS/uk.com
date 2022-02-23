/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.j {
	import getText = nts.uk.resource.getText;
	import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

	const QRCODE_SIZE = 'QRCodeSize';
	const EXPORT_QRCODE = 'screen/at/kmp001/j/exportQR';

	@bean()
	export class ViewModel extends ko.ViewModel {

		// CCG001
		ccg001ComponentOption: GroupOption;

		// KCP005
		listComponentOption: any;
		multiSelectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
		selectedIds: KnockoutObservableArray<string> = ko.observableArray([]);

		employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

		qrSizeOption: KnockoutObservableArray<any> = ko.observableArray([
			{ id: 0, name: getText("KMP001_164") },
			{ id: 1, name: getText("KMP001_165") },
			{ id: 2, name: getText("KMP001_166") }
		]);

		qrSize: KnockoutObservable<number> = ko.observable(0);
		textEditorJ2_12: KnockoutObservable<string> = ko.observable('');
		textEditorJ2_16: KnockoutObservable<string> = ko.observable('');

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
				showClosure: false,
				showAllClosure: false,
				showPeriod: false,
				periodFormatYM: true,

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
				showWorktype: false,
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
					if (employees.length) {
						vm.multiSelectedCode(_.map(employees, 'code'));

					}
				}

			};

			$('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {
				$("#ccg001-btn-search-drawer").focus();
			});


			vm.$window.storage(QRCODE_SIZE)
				.then((data: any) => {
					if (data) {
						vm.qrSize(data.qrCodeSize);
					}
				});

		}

		mounted() {
			let vm = this;
			vm.initEmployeeList();


			vm.qrSize.subscribe(() => {
				vm.textEditorJ2_12('');
				vm.textEditorJ2_16('');
			});

			// 大
			vm.textEditorJ2_12.subscribe((s) => {
				if (vm.qrSize() == 0 && s > '4') {
					vm.textEditorJ2_12('4');
				}
			});

			vm.textEditorJ2_16.subscribe((s) => {
				if (vm.qrSize() == 0 && s > '3') {
					vm.textEditorJ2_16('3');
				}
			});

			// 中
			vm.textEditorJ2_12.subscribe((s) => {
				if (vm.qrSize() == 1 && s > '5') {
					vm.textEditorJ2_12('5');
				}
			});

			vm.textEditorJ2_16.subscribe((s) => {
				if (vm.qrSize() == 1 && s > '4') {
					vm.textEditorJ2_16('4');
				}
			});

			// 小
			vm.textEditorJ2_12.subscribe((s) => {
				if (vm.qrSize() == 2 && s > '6') {
					vm.textEditorJ2_12('6');
				}
			});

			vm.textEditorJ2_16.subscribe((s) => {
				if (vm.qrSize() == 2 && s > '5') {
					vm.textEditorJ2_16('5');
				}
			});

		}

		exportQR() {
			let vm = this;

			if (vm.multiSelectedCode().length == 0) {
				vm.$dialog.error({ messageId: 'MsgB_2', messageParams: [nts.uk.resource.getText('KMT014_21')] });
			} else {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}

					vm.$window.storage("contractInfo")
						.then((data: any) => {
							if (data) {
								const input = {
									contractCode: data.contractCode,
									sIds: vm.getListSelectedEmployee(),
									qrSize: ko.unwrap(vm.qrSize),
									setRow: ko.unwrap(vm.textEditorJ2_12),
									setCol: ko.unwrap(vm.textEditorJ2_16),
								};

								nts.uk.request.exportFile(EXPORT_QRCODE, input)
									.then(() => {
										nts.uk.characteristics.save(QRCODE_SIZE, { qrCodeSize: ko.unwrap(vm.qrSize) });
									})
							}
						});

				}).fail((error) => {
					vm.$dialog.error(error);
				}).always(() => {
					vm.$blockui("clear");
				});

		}
	}
		
		private getListSelectedEmployee() {
		let self = this;
		self.selectedIds.removeAll();
		_.forEach(self.multiSelectedCode(), code => {
			var employee = self.employeeList().filter(function(emp) {
				return code == emp.code;
			});
			if (employee.length > 0) {
				self.selectedIds().push(employee[0].id);
			}
		});
		return self.selectedIds();
	}

	initEmployeeList() {
		let vm = this;
		vm.listComponentOption = {
			isShowAlreadySet: false,
			isMultiSelect: true,
			listType: ListType.EMPLOYEE,
			employeeInputList: vm.employeeList,
			selectType: SelectType.SELECT_BY_SELECTED_CODE,
			selectedCode: vm.multiSelectedCode,
			isDialog: false,
			isShowNoSelectRow: false,
			alreadySettingList: vm.alreadySettingList,
			isShowWorkPlaceName: true,
			isShowSelectAllButton: false,
			disableSelection: false
		};

		$('#com-kcp005').ntsListComponent(vm.listComponentOption);

	}


}

class ListType {
	static EMPLOYMENT = 1;
	static Classification = 2;
	static JOB_TITLE = 3;
	static EMPLOYEE = 4;
}

class SelectType {
	static SELECT_BY_SELECTED_CODE = 1;
	static SELECT_ALL = 2;
	static SELECT_FIRST_ITEM = 3;
	static NO_SELECT = 4;
}
}