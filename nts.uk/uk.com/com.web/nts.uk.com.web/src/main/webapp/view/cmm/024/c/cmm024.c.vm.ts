/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cmm024.c {
	import common = nts.uk.com.view.cmm024.a.common;
	import ScheduleHistoryDto = common.ScheduleHistoryDto;
	import HistoryRes = common.HistoryRes;
	import EmployeeDto = common.EmployeeDto;

	@bean()
	class ViewModel extends ko.ViewModel {

		registrationHistory: KnockoutObservableArray<any> = ko.observableArray([]);
		registrationHistoryType: KnockoutObservable<number> = ko.observable(0);
		newStartDate: KnockoutObservable<Date> = ko.observable(null);

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
			});

			$('.ntsDatepicker').focus();
		}
		/**
		 * Process
		 * */
		proceed() {
			let vm = this,
				isAfter: boolean = true,
				currentDateHistory = vm.scheduleHistorySelected();

			let newStartDate: Date = vm.newStartDate(), //開始年月日テキストボックス -> A2_6, B2_6
				newEndDate: Date = moment(common.END_DATE).toDate(),
				cStartDate: Date = moment(common.END_DATE).toDate();

			if (!nts.uk.util.isNullOrEmpty(currentDateHistory)) {
				cStartDate = moment(currentDateHistory.startDate).toDate()
				isAfter = moment(newStartDate).format('YYYYMMDD') > moment(cStartDate).format('YYYYMMDD');
			}

			if (!isAfter) {
				vm.$dialog.error({ messageId: "Msg_156", messageParams: [moment(cStartDate).format('YYYY/MM/DD')] }).then(() => {
					$('.ntsDatepicker').focus();					
				});				
				return;
			} else {
				let startDate = moment(newStartDate).format('YYYY/MM/DD');
				let newScheduleHistoryDto: ScheduleHistoryDto,
					personalInfoApprove: Array<EmployeeDto> = [],
					personalInfoConfirm : Array<EmployeeDto> = [];

				newScheduleHistoryDto = new ScheduleHistoryDto(startDate, common.END_DATE);

				//履歴から引き継ぐ履歴から引き継ぐ
				if (vm.registrationHistoryType() === HistoryRes.HISTORY_TRANSFER) {

					if (!nts.uk.util.isNullOrUndefined(currentDateHistory)) {
						personalInfoApprove = currentDateHistory.personalInfoApprove;
						personalInfoConfirm = currentDateHistory.personalInfoConfirm;
					} else 
						vm.registrationHistoryType(HistoryRes.HISTORY_NEW); //list is empty

					newScheduleHistoryDto = new ScheduleHistoryDto(
						startDate,
                        common.END_DATE, //endDate
						personalInfoApprove,
						personalInfoConfirm
					);
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
	}
}