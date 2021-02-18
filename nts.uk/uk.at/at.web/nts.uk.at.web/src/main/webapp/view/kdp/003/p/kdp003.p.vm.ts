/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.p {

	const API = {
		GET_NOTICE: '/at/record/stamp/notice/getNoticeByStamping'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		displayNotices: KnockoutObservableArray<DisplayNotice> = ko.observableArray([]);
		dateValue: KnockoutObservable<any> = ko.observable({});
		startDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));;
		endDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));;

		created() {
			const vm = this;
			vm.startDate.subscribe((value) => {
				vm.dateValue().startDate = value;
				vm.dateValue.valueHasMutated();
			});

			vm.endDate.subscribe((value) => {
				vm.dateValue().endDate = value;
				vm.dateValue.valueHasMutated();
			});
		}

		mounted() {
			$(document).ready(() => {
				$('#date-range').focus();
			});
		}

		searchNotice() {
			const vm = this;
			const { randomId } = nts.uk.util;
			const param = { startDate: ko.unwrap(vm.startDate), endDate: ko.unwrap(vm.endDate) };
            const displayNotices: DisplayNotice[] = [];

			vm.$ajax(API.GET_NOTICE, param)
				.then((notices: MessageNotice[]) => {
					notices.forEach((data) => {
                        let notice = {
                            id: randomId(),
                            period: nts.uk.time.applyFormat("Short_MD", data.startDate) + vm.$i18n('KDP003_67') 
                                    + nts.uk.time.applyFormat("Short_MD", data.endDate),
                            message: data.notificationMessage
                        };
                        displayNotices.push(notice);
                    })
                    
                    vm.displayNotices(displayNotices);
				});
		}

		closeDialog() {
			const vm = this;
			vm.$window.close();
		}

	}

	export interface DisplayNotice {
		id: string;
		period: string;
		message: string;
	}

	export interface MessageNotice {
		creatorID: string;
		inputDate: string;
		modifiedDate: string;
		startDate: string;
		endDate: string;
		notificationMessage: string;
	}

}