/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.p {

	const API = {
		GET_NOTICE: '/at/record/stamp/notice/getNoticeByStamping',

		//社員のお知らせの画面を表示する
		GET_EMP_NOTICE: 'sys/portal/notice/getEmployeeNotification'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
			startDate: moment.utc().format('YYYY/MM/DD'),
			endDate: moment.utc().format('YYYY/MM/DD')
		}));

		itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		msgNotice: MessageNotice[] = [];
		role: KnockoutObservable<Role> = ko.observable(new Role());

		created() {
			const vm = this;
			vm.searchMessage(null);
			vm.$blockui('show');
			vm.$ajax('com', API.GET_EMP_NOTICE)
				.then((response: EmployeeNotification) => {
					if (response.role) {
						vm.role(response.role);
					}
				})
				.fail(error => vm.$dialog.error(error))
				.always(() => vm.$blockui('hide'));
		}

		mounted() {
			$(document).ready(() => {
				$('#P2_2 .start').focus();
			});
		}

		searchMessage(param: DatePeriod) {
			const vm = this;
			vm.$blockui('show');
			vm.$ajax(API.GET_NOTICE, param).then((response: MessageNotice[]) => {
				if (response) {
					vm.msgNotice = response;
					const itemList = _.map(response, msg => new ItemModel({
						creatorID: msg.creatorID,
						inputDate: msg.inputDate,
						ymDisplay: moment.utc(msg.startDate, 'YYYYMMDD').format('M/D').toString()
							+ ' ' + vm.$i18n('KDP003_67').toString() + ' '
							+ moment.utc(msg.endDate, 'YYYYMMDD').format('M/D').toString(),
						content: msg.notificationMessage
					}));
					vm.itemList(itemList);
				}
			})
				.fail(error => vm.$dialog.error(error))
				.always(() => vm.$blockui('hide'));
		}

		onClickSearch() {
			const vm = this;
			
			vm.$validate('#P2_2').then((valid: boolean) => {
				if (!valid) {
					nts.uk.ui.errors.show();
					return;
				}
				const param: DatePeriod = new DatePeriod({
					startDate: moment.utc(vm.dateValue().startDate).toISOString(),
					endDate: moment.utc(vm.dateValue().endDate).toISOString()
				});
				vm.searchMessage(param);
			});
		}

		/**
		 * P20_1:新規をクリックする
		 */
		openScreenQInNewMode() {
			const vm = this;
			vm.$window.modal('at', '/view/kdp/003/q/index.xhtml', {
				isNewMode: true,
				role: vm.role(),
				messageNotice: null
			})
				.then(result => {
					if (result && !result.isClose) {
						vm.onClickSearch();
					}
				});
		}

		/**
		 * P4_2:対象のリンクラベルをクリックする
		 */
		onClickTargetLink(data: ItemModel) {
			const vm = this;
			vm.$window.modal('at', '/view/kdp/003/q/index.xhtml', {
				isNewMode: false,
				role: vm.role(),
				messageNotice: vm.findMessage(data)
			})
				.then(() => {
					vm.onClickSearch();
				});
		}

		findMessage(data: ItemModel): MessageNotice {
			return _.find(this.msgNotice, m => m.creatorID === data.creatorID && m.inputDate === data.inputDate);
		}

		closeDialog() {
			const vm = this;
			vm.$window.close();
		}

	}

	class EmployeeNotification {
		msgNotices: MsgNotices[];
		role: Role;
		systemDate: string;
	}

	class MsgNotices {
		message: MessageNotice;
		creator: string;
		dateDisplay: string;
		flag: boolean;
		messageDisplay: string;
	}

	export class Role {
		companyId: string;
		roleId: string;
		roleCode: string;
		roleName: string;
		assignAtr: number;
		employeeReferenceRange: number;
	}

	export class DatePeriod {
		startDate: string;
		endDate: string;

		constructor(init?: Partial<DatePeriod>) {
			$.extend(this, init);
		}
	}

	export class ItemModel {
		creatorID: string;
		inputDate: string;
		ymDisplay: string;
		content: string;

		constructor(init?: Partial<ItemModel>) {
			$.extend(this, init);
		}
	}

	class MessageNotice {
		creatorID: string;
		inputDate: string;
		modifiedDate: string;
		targetInformation: any;
		startDate: any;
		endDate: any;
		employeeIdSeen: string[];
		notificationMessage: string;
	}

	export class TargetInformation {
		targetSIDs: string[];
		targetWpids: string[];
		destination: number;
		constructor(init?: Partial<TargetInformation>) {
			$.extend(this, init);
		}
	}

}