/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.k {


	const API = {

		REGISTER: '/at/record/stamp/employment/system/register-stamp-input'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		screenData: KnockoutObservable<ScreenData> = ko.observable(new ScreenData());

		screenMode: 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person' = 'Com_Workplace';

		startMonthLst = ko.observableArray([]);

		carryforwardSetInShortageFlex = ko.observableArray(__viewContext.enums.CarryforwardSetInShortageFlex);

		created(param?: any) {
			const vm = this;
			vm.initMonthLst();
			if (param) {
				vm.screenMode = param.screenMode;
			}
			if (vm.screenMode !== 'Com_Company') {
				let windowSize = nts.uk.ui.windows.getSelf();
				windowSize.$dialog.dialog("option", "height", 695);
			}
		}

		initMonthLst() {
			const vm = this;
			let startMonthLst = [];
			for (let i = 1; i <= 12; i++) {
				startMonthLst.push(new MonthItem({ code: i, name: i.toString() + '月' }));
			}
			vm.startMonthLst(startMonthLst);
			vm.screenData().selectedMonth(startMonthLst[0].code);
		}


		register() {
			const vm = this;
			vm.$window.close();

		}

		getMessage() {

			const vm = this
				, msgs = [
					{ mode: 'Com_Company', msg: '' },
					{ mode: 'Com_Workplace', msg: 'KMK004_344' },
					{ mode: 'Com_Employment', msg: 'KMK004_345' },
					{ mode: 'Com_Person', msg: 'KMK004_346' }];

			return vm.$i18n.text(_.find(msgs, ['mode', vm.screenMode]).msg);
		}

		close() {
			const vm = this;
			vm.$window.close();

		}

		remove() {
			const vm = this;
		}


		mounted() {

		}
	}

	class ScreenData {
		clearingPeriod: KnockoutObservable<number> = ko.observable(1);
		selectedMonth: KnockoutObservable<number> = ko.observable(1);
		selectedStage: KnockoutObservable<number> = ko.observable(2);
		selectedInShortageFlex: KnockoutObservable<number> = ko.observable(0);
		monthCountingMethod: KnockoutObservable<number> = ko.observable(1);
		includeOvertimeHours: KnockoutObservable<boolean> = ko.observable(false);
		includeNonStatutoryLeave: KnockoutObservable<boolean> = ko.observable(false);
		useRegularWorkingHours: KnockoutObservable<number> = ko.observable(1);
		legalFlextime: KnockoutObservable<boolean> = ko.observable(false);
		constructor(param?: any) {
			if (param) {
				this.clearingPeriod(param.clearingPeriod);
			}
		}
	}

	class MonthItem {
		code: number = 1;
		name: string = '';

		constructor(param: any) {
			this.code = param.code;
			this.name = param.name;
		}
	}
}
