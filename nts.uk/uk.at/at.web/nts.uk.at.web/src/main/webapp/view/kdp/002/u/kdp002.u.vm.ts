/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp002.u {

	const API = {
		NOTIFICATION_STAMP: 'at/record/stamp/notification_by_stamp',
		UPDATE: 'at/record/stamp/notice/viewMessageNotice'
	};

	interface IParams {
		sid: string;
		data: IModel;
	}

	@bean()
	export class ViewModel extends ko.ViewModel {

		model: KnockoutObservableArray<IMsgNotices> = ko.observableArray([]);
		sid: string;

		created(param: IParams) {
			const vm = this

			vm.model(param.data.msgNotices);
			vm.sid = param.sid;
		}

		closeDialog() {
			const vm = this;

			vm.$blockui('invisible')
				.then(() => {
					let msgInfors: Array<ICreatorAndDate> = [];

					_.forEach(ko.unwrap(vm.model), (value) => {

						var item: ICreatorAndDate = {
							creatorId: value.message.creatorID,
							inputDate: vm.$date.now()
						}

						msgInfors.push(item);
					})

					const params = {
						msgInfors: msgInfors,
						sid: vm.sid
					}

					console.log(vm.$date.now());

					vm.$ajax(API.UPDATE, params)
						.always(() => {
							vm.$blockui('clear');
							vm.$window.close();
						});
				});
		}

		mounted() {
			$(document).ready(function () {
				$('.x-large').focus();
			});
		}
	}

	interface IModel {
		msgNotices: IMsgNotices[];
	}

	interface IMsgNotices {
		creator: string;
		flag: boolean;
		message: IEmployeeIdSeen;
	}

	interface IEmployeeIdSeen {
		endDate: string;
		inputDate: Date;
		modifiedDate: Date;
		notificationMessage: string;
		startDate: Date;
		targetInformation: ITargetInformation;
	}

	interface ICreatorAndDate {
		creatorId: string;
		inputDate: Date;
	}
}
