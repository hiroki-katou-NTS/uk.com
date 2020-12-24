/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
		<div class="title" data-bind="i18n:screenMode"></div>
		<a class="goback"  data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button data-bind=" enable:enableSave() ,click: register,i18n: 'KMK004_225'" class="proceed"></button>
		<button data-bind="enable:enableCopy() ,click: copy,visible:screenMode != 'Com_Company' ,i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable:enableDelete()  ,click: remove,i18n: 'KMK004_227'"></button>
	`;

@component({
	name: 'sidebar-button',
	template
})

class SidebarButton extends ko.ViewModel {
	screenMode: string;
	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	created(params?: ISidebarButtonParam) {
		let vm = this;
		vm.screenData = params.screenData;
		vm.screenMode = params.screenMode;
	}

	register() {
		const vm = this;
		if (vm.screenData().updateMode()) {
			vm.updateData();
		} else {
			vm.registerData();
		}
	}

	updateData() {
		const vm = this;
		let cmd;

		if (vm.screenMode == 'Com_Company') {

			cmd = { workTimeSetComs: ko.toJS(vm.screenData().monthlyWorkTimeSetComs()) };
			vm.$blockui('invisible');
			vm.$ajax(API_G_URL.UPDATE, cmd).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().saveToUnSaveList();
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}


		if (vm.screenMode == 'Com_Workplace') {

			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				wkpId = vm.screenData().selected();

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.workplaceId = wkpId;
			});

			vm.$blockui('invisible');
			vm.$ajax(API_H_URL.UPDATE, { workTimeSetWkps: workTimeSetComs }).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().saveToUnSaveList();
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}
		if (vm.screenMode == 'Com_Employment') {
			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				empCd = vm.screenData().selected();

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.employmentCode = empCd;
			});

			vm.$blockui('invisible');
			vm.$ajax(API_I_URL.UPDATE, { workTimeSetEmps: workTimeSetComs }).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}
		if (vm.screenMode == 'Com_Person') {
			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				scd = vm.screenData().selected(),
				selectedEmp: any = _.find(vm.getScreenDatas(), ['code', scd]);

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.empId = selectedEmp.id;
			});

			vm.$blockui('invisible');
			vm.$ajax(API_J_URL.UPDATE, { workTimeSetShas: workTimeSetComs }).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}
	}

	enableDelete() {

		const vm = this;
		return vm.screenData().yearList().length > 0 && vm.screenData().updateMode();
	}

	enableSave() {
		const vm = this;

		return _.filter(ko.toJS(vm.screenData().monthlyWorkTimeSetComs()), ['laborTime.checkbox', true]).length > 0 && vm.screenData().yearList().length > 0;

	}

	enableCopy() {
		const vm = this;
		return vm.screenData().yearList().length > 0 && vm.screenData().updateMode();
	}

	registerData() {
		const vm = this;

		let cmd;

		if (vm.screenMode == 'Com_Company') {
			cmd = { workTimeSetComs: ko.toJS(vm.screenData().monthlyWorkTimeSetComs()) };
			vm.$blockui('invisible');
			vm.$ajax(API_G_URL.REGISTER, cmd).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}


		if (vm.screenMode == 'Com_Workplace') {

			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				wkpId = vm.screenData().selected();

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.workplaceId = wkpId;
			});

			vm.$blockui('invisible');
			vm.$ajax(API_H_URL.REGISTER, { workTimeSetWkps: workTimeSetComs }).done((data) => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { workplaceId: item, isAlreadySetting: true } }));
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}
		if (vm.screenMode == 'Com_Employment') {
			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				empCd = vm.screenData().selected();

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.employmentCode = empCd;
			});

			vm.$blockui('invisible');
			vm.$ajax(API_I_URL.REGISTER, { workTimeSetEmps: workTimeSetComs }).done((data) => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});
		}
		if (vm.screenMode == 'Com_Person') {

			let workTimeSetComs = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
				scd = vm.screenData().selected(),
				selectedEmp: any = _.find(vm.getScreenDatas(), ['code', scd]);

			_.forEach(workTimeSetComs, (timeSet) => {
				timeSet.empId = selectedEmp.id;
			});

			workTimeSetComs = _.filter(workTimeSetComs, ['laborTime.checkbox', true]);

			vm.$blockui('invisible');
			vm.$ajax(API_J_URL.REGISTER, { workTimeSetShas: workTimeSetComs }).done((data) => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.screenData().serverYears.push(Number(vm.screenData().selectedYear()));
					vm.screenData().clearUpdateYear(vm.screenData().selectedYear());
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				});
			}).fail((error) => {
				vm.$dialog.error(error);
			}).always(() => {
				vm.$blockui("clear");
			});

		}
	}

	getScreenDatas() {

		const vm = this;

		let gridId;
		if (vm.screenMode == 'Com_Workplace') {
			gridId = '#work-place-list';
		}
		if (vm.screenMode == 'Com_Employment') {
			gridId = '#empt-list-setting';
		}
		if (vm.screenMode == 'Com_Person') {
			gridId = '#employee-list'
		}

		return $(gridId).getDataList();

	}

	copy() {
		const vm = this;

		vm.$window.modal('/view/kmk/004/r/index.xhtml', {
			screenMode: vm.screenMode,
			data: vm.getScreenDatas(),
			selected: vm.screenData().selected(),
			year: vm.screenData().selectedYear(),
			laborAttr: 2,
		}).then(() => {

			vm.$blockui('invisible');
			if (vm.screenMode == 'Com_Workplace') {

				vm.$ajax(API_H_URL.AFTER_COPY).done((data) => {
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { workplaceId: item, isAlreadySetting: true } }));
				}).always(() => { vm.$blockui("clear"); });
			}
			if (vm.screenMode == 'Com_Employment') {

				vm.$ajax(API_I_URL.AFTER_COPY).done((data) => {
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				}).always(() => { vm.$blockui("clear"); });
			}
			if (vm.screenMode == 'Com_Person') {

				vm.$ajax(API_J_URL.AFTER_COPY).done((data) => {
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				}).always(() => { vm.$blockui("clear"); });

			}


		});
	}

	remove() {
		const vm = this;

		vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
			if (result === 'yes') {
				vm.deleteData();
			}
		});

	}

	deleteData() {
		const vm = this;

		let selectedYear = vm.screenData().selectedYear(),
			selectedId = vm.screenData().selected();
		vm.$blockui('invisible');
		if (vm.screenMode === 'Com_Company') {

			vm.$ajax(API_G_URL.DELETE + selectedYear).done(() => {
				vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
					vm.screenData().setSelectedAfterRemove(selectedYear);
					vm.screenData().deleteYear(selectedYear);
					vm.screenData().clearUnSaveList(selectedYear);
				});
			}).always(() => { vm.$blockui("clear"); });
		}

		if (vm.screenMode == 'Com_Workplace') {
			let cmd = { workplaceId: selectedId, year: selectedYear };

			vm.$ajax(API_H_URL.DELETE, cmd).done((data) => {
				vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
					vm.screenData().setSelectedAfterRemove(selectedYear);
					vm.screenData().deleteYear(selectedYear);
					vm.screenData().clearUnSaveList(selectedYear);
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { workplaceId: item, isAlreadySetting: true } }));
				});
			}).always(() => { vm.$blockui("clear"); });
		}

		if (vm.screenMode == 'Com_Employment') {
			let cmd = { employmentCode: selectedId, year: selectedYear };

			vm.$ajax(API_I_URL.DELETE, cmd).done((data) => {
				vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
					vm.screenData().setSelectedAfterRemove(selectedYear);
					vm.screenData().deleteYear(selectedYear);
					vm.screenData().clearUnSaveList(selectedYear);
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				});
			}).always(() => { vm.$blockui("clear"); });
		}

		if (vm.screenMode == 'Com_Person') {

			let selectedEmp: any = _.find(vm.getScreenDatas(), ['code', selectedId]);

			let cmd = { sId: selectedEmp.id, year: selectedYear };

			vm.$ajax(API_J_URL.DELETE, cmd).done((data) => {
				vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
					vm.screenData().setSelectedAfterRemove(selectedYear);
					vm.screenData().deleteYear(selectedYear);
					vm.screenData().clearUnSaveList(selectedYear);
					vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));
				});
			}).always(() => { vm.$blockui("clear"); });
		}


	}
}
interface ISidebarButtonParam {
	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string;
}
