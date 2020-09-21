/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {

	const API = {
		UNAME: '/sys/portal/webmenu/username'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		schedules: KnockoutObservableArray<any> = ko.observableArray([]);

		baseDate = ko.observable({ begin: new Date(2020, 10, 1), finish: new Date(2020, 11, 5) });

		currentUser!: KnockoutObservable<string>;

		created() {
			const vm = this;
			const bussinesName: KnockoutObservable<string> = ko.observable('');

			vm.currentUser = ko.computed({
				read: () => {
					const bName = ko.unwrap(bussinesName);

					return `${vm.$i18n('KSU002_1')}&nbsp;&nbsp;&nbsp;&nbsp;${vm.$user.employeeCode}&nbsp;&nbsp;${bName}`;
				},
				owner: vm
			});

			vm.$ajax('com', API.UNAME)
				.then((name: string) => bussinesName(name))

			vm.schedules.subscribe((c) => console.log(c));
		}

		mounted() {

		}
		
		clickDayCell(type: string, date: any) {
			const vm = this;
			
			console.log(vm, type, date);
		}
	}
}