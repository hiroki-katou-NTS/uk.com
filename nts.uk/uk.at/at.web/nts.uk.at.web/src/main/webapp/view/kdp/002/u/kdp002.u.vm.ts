/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.kdp002.u {

	const API = {
		NOTIFICATION_STAMP: 'at/record/stamp/notification_by_stamp'
	};

	interface IParams {
		sid: string;
		data: IModel;
	}

	@bean()
	export class ViewModel extends ko.ViewModel {

		model: KnockoutObservableArray<IMsgNotices> = ko.observableArray([]);

		created(param: IParams) {
			const vm = this
			vm.model(param.data.msgNotices);
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
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
		endDate: string,
		inputDate: Date
		modifiedDate: Date
		notificationMessage: string
		startDate: Date
		targetInformation: ITargetInformation
	}
}
