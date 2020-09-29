/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cmm024.c {
	import service = nts.uk.com.view.cmm024.a.service;
	import ScheduleHistoryDto = nts.uk.com.view.cmm024.a.service.ScheduleHistoryDto;
	import HistoryRes = nts.uk.com.view.cmm024.a.service.HistoryRes;

	@bean()
	class ViewModel extends ko.ViewModel {

		registrationHistory: KnockoutObservableArray<any> = ko.observableArray([]);
		registrationHistoryType: KnockoutObservable<number> = ko.observable(0);
		newStartDate: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());

		//set & get Share
		scheduleHistorySelected: KnockoutObservable<ScheduleHistoryDto> = ko.observable(null);

		constructor(params: any) {
			// start point of object
			super();

			let vm = this;

			vm.registrationHistory.push({ id: HistoryRes.HISTORY_TRANSFER, name: vm.$i18n('CMM024_26') });
			vm.registrationHistory.push({ id: HistoryRes.HISTORY_NEW, name: vm.$i18n('CMM024_27') });

		}

		created(params: any) {
			// start point of object
			let vm = this;
		}

		mounted() {
			// raise event when view initial success full
			let vm = this;

			vm.$window.storage("scheduleHistorySelected").then((data) => {
				vm.scheduleHistorySelected(data);
				vm.initScheduleHistory();
			});

		}
		/**
		 * Process
		 * */
		proceed() {
			let vm = this,
				isAfter: boolean = true,
				currentDateHistory = vm.scheduleHistorySelected();

			let newStartDate: Date = vm.newStartDate(); //開始年月日テキストボックス -> A2_6, B2_6
			let newEndDate: Date = moment(service.END_DATE, 'YYYY/MM/DD').toDate();

			if (!nts.uk.util.isNullOrEmpty(currentDateHistory)) {

				isAfter = moment(newStartDate).isAfter(currentDateHistory.startDate);
			}

			if (!isAfter) {
				vm.$dialog.error('Msg_12').then(() => { });
				return;
			} else {
				let startDate = moment(newStartDate, 'YYYY/MM/DD').format('YYYY/MM/DD');
				let newScheduleHistoryDto: ScheduleHistoryDto;

				newScheduleHistoryDto = new ScheduleHistoryDto(startDate, service.END_DATE);

				//履歴から引き継ぐ履歴から引き継ぐ
				if (vm.registrationHistoryType() === HistoryRes.HISTORY_TRANSFER) {
					newScheduleHistoryDto = new ScheduleHistoryDto(
						startDate, '9999/12/31',
						currentDateHistory.personalInfoApprove,
						currentDateHistory.personalInfoConfirm);
				}

				vm.$window.storage("newScheduleHistory", {
					scheduleHistoryItem: newScheduleHistoryDto,
					registrationHistoryType: vm.registrationHistoryType()
				}).then(() => {
					vm.$window.close();
					return false;
				});
			}
		}

		/**
		 * Cancel and close window
		 * */
		cancel() {
			let vm = this;

			vm.$window.storage("newScheduleHistory", {}).then(() => {
				vm.$window.close();
				return false;
			});
		}

		initScheduleHistory() {
			let vm = this,
				scheduleHistory: ScheduleHistoryDto = vm.scheduleHistorySelected();

			if (!nts.uk.util.isNullOrUndefined(scheduleHistory)) {
				if (!nts.uk.util.isNullOrEmpty(scheduleHistory.startDate)) {
					vm.newStartDate(moment(scheduleHistory.startDate, 'YYYY/MM/DD').toDate());
				}
			}
		}
	}
}