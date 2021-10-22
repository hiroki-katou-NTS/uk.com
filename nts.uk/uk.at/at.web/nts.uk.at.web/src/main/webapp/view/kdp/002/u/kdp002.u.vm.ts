/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp002.u {

	const API = {
		NOTIFICATION_STAMP: 'at/record/stamp/notification_by_stamp',
		UPDATE: 'at/record/stamp/notice/viewMessageNotice'
	};

	interface IParams {
		sid: string;
		data: IModel;
		setting: INoticeSet;
	}

	@bean()
	export class ViewModel extends ko.ViewModel {

		modelShowView: KnockoutObservableArray<IEmployeeIdSeen> = ko.observableArray([]);
		model: KnockoutObservableArray<IMsgNotices> = ko.observableArray([]);
		sid: string;
		noticeSetting: NoticeSet = new NoticeSet();


		created(param: IParams) {
			const vm = this;

			_.forEach(param.data.msgNotices, ((value) => {
				value.message.creator = value.creator;
				value.message.startDate = moment(value.message.startDate).format('MM/DD');
			}));

			_.forEach(param.data.msgNotices, (value) => {
				
				if (value.message.targetInformation.destination == 2) {

					if (value.flag) {
						value.message.modeNew = true;
					} else {
						value.message.modeNew = false;
					}
					vm.modelShowView.push(value.message);
				}
			})

			vm.model(param.data.msgNotices);

			vm.sid = param.sid;

			if (param) {
	            vm.noticeSetting = param.setting;
            }

		}

		closeDialog() {
			const vm = this;

			vm.$blockui('invisible')
				.then(() => {
					let msgInfors: Array<ICreatorAndDate> = [];

					_.forEach(ko.unwrap(vm.model), (value) => {
						
						if (value.flag && value.message.targetInformation.destination == 2) {
							var item: ICreatorAndDate = {
								creatorId: value.message.creatorID,
								inputDate: value.message.inputDate
							}

							msgInfors.push(item);
						}
					})

					const params = {
						msgInfors: msgInfors,
						sid: vm.sid
					}

					vm.$ajax(API.UPDATE, params)
						.done(() => {
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
		employeeIdSeen: any;
		creator: string;
		modeNew: Boolean;
	}

	interface ICreatorAndDate {
		creatorId: string;
		inputDate: Date;
	}

	class NoticeSet {
		personMsgColor: ColorSettingDto ; //個人メッセージ色
		
		constructor() {
			this.personMsgColor = new ColorSettingDto();
		}
	}
	
	class ColorSettingDto {
		textColor: string; //文字色
		backGroundColor: string //背景色
		constructor() {
			this.textColor = '';
			this.backGroundColor = '';
		}
	}
}
