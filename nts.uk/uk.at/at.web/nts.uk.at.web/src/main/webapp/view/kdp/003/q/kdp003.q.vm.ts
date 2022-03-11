/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.q {
	import Role = nts.uk.at.kdp003.p.Role;

	const API = {

		//社員が作成するお知らせ登録の画面を表示する
		START_SCREEN: 'sys/portal/notice/notificationCreatedByEmp',

		//お知らせを登録する
		REGISTER_NOTICE: 'sys/portal/notice/registerMessageNotice',

		//お知らせを更新する
		UPDATE_NOTICE: 'sys/portal/notice/updateMessageNotice',

		//お知らせを削除する
		DELETE_NOTICE: 'sys/portal/notice/deleteMessageNotice',

		//打刻入力のお知らせの職場を取得する
		GET_WKP_BY_STAMP_NOTICE: 'at/record/stamp/notice/getWkpByStampNotice',
		
		//お知らせ宛先の職場の名称を取得する
		GET_NAME_OF_DESTINATION_WKP: 'sys/portal/notice/getNameOfDestinationWkp'
		
	};

	const COMMA = '、';

	@bean()
	export class ViewModel extends ko.ViewModel {
		messageText: KnockoutObservable<string> = ko.observable('');
		destination: KnockoutObservable<number> = ko.observable(1);
		dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
			startDate: moment.utc().format('YYYY/MM/DD'),
			endDate: moment.utc().format('YYYY/MM/DD')
		}));
		updateDate: KnockoutObservable<string> = ko.observable('');
		isNewMode: KnockoutObservable<boolean> = ko.observable(false);
		isActiveDelete: KnockoutComputed<boolean> = ko.computed(() => !this.isNewMode());
		assignAtr: KnockoutObservable<number> = ko.observable(0);
		employeeReferenceRange: KnockoutObservable<number> = ko.observable(0);

		// ※Q1 ロール.参照範囲＝全社員　OR　部門・職場(配下含む）
		isActiveWorkplaceBtn: KnockoutComputed<boolean> = ko.computed(() => this.employeeReferenceRange() === EmployeeReferenceRange.ALL_EMPLOYEE || this.employeeReferenceRange() === EmployeeReferenceRange.DEPARTMENT_AND_CHILD);
		parentParam = new ParentParam();
		isStartUpdateMode = false;
		workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
		workPlaceName: KnockoutObservableArray<string> = ko.observableArray([]);
		notificationCreated: KnockoutObservable<NotificationCreated> = ko.observable(null);
		
		workPlaceTxtRefer: KnockoutObservable<string> = ko.observable('');
		startDateOfMsgUpdate = '';
		regionalTime = 0;

		created(parentParam: ParentParam) {
			const vm = this;
			vm.regionalTime = parentParam.regionalTime;
            
            vm.$window.storage("serverTime").done((time) => {
                vm.dateValue(new DatePeriod({
                    startDate: moment(time).utc().add(vm.regionalTime, 'm').format('YYYY/MM/DD'),
                    endDate: moment(time).utc().add(vm.regionalTime, 'm').format('YYYY/MM/DD')
                }));
            });
			
			vm.parentParam = parentParam;
			vm.isNewMode(vm.parentParam.isNewMode);
			vm.isStartUpdateMode = !vm.parentParam.isNewMode;
			vm.employeeReferenceRange(parentParam.role.employeeReferenceRange ? parentParam.role.employeeReferenceRange : 0);
			vm.assignAtr(parentParam.role.assignAtr ? parentParam.role.assignAtr : 0);
			vm.onStartScreen();
		}

		mounted() {
			const vm = this;
			$(document).ready(() => {
				$('#area-notice').focus();
			});

			vm.destination.subscribe(() => {
				if (!_.isNull(vm.notificationCreated().workplaceInfo)) {
					const workplaceInfo = this.notificationCreated().workplaceInfo;
					vm.workPlaceIdList([workplaceInfo.workplaceId]);
					vm.workPlaceTxtRefer(`${workplaceInfo.workplaceCode} ${workplaceInfo.workplaceName}`);
				}
			});
		}

        /**
         * 起動する
         */
		onStartScreen() {
			const vm = this;
			const param = vm.parentParam.messageNotice;
			const msg = vm.isNewMode()
				? null
				: new MessageNotice({
					creatorID: param.creatorID,
					employeeIdSeen: param.employeeIdSeen,
					endDate: moment.utc(param.endDate).toISOString(),
					startDate: moment.utc(param.startDate).toISOString(),
					inputDate: param.inputDate,
					modifiedDate: param.modifiedDate,
					notificationMessage: param.notificationMessage,
					targetInformation: param.targetInformation
				});
			if (!vm.isNewMode() && msg !== null) {
				vm.destination(msg.targetInformation.destination);
				// Q3
				vm.messageText(msg.notificationMessage);
				// Q2_2
				const period = new DatePeriod({
					startDate: msg.startDate,
					endDate: msg.endDate
				});
				vm.dateValue(period);
				// Q2_3
				const modifiedDate = moment.utc(msg.modifiedDate).format('YYYY/M/D HH:mm');
				vm.updateDate(vm.$i18n('KDP003_68', [modifiedDate]));
				vm.startDateOfMsgUpdate = msg.startDate;
			}

			if (vm.isNewMode()) {
				vm.updateDate(vm.$i18n('KDP003_68', ['']));
			}

			const params: NotificationParams = new NotificationParams({
				refeRange: vm.employeeReferenceRange(),
				msg: msg
			});
			vm.$blockui('show');
			this.$ajax('com', API.START_SCREEN, params)
				.then((response: NotificationCreated) => {
					if (!response) {
						return;
					}
					vm.createNotice(response);
				})
				.fail(error => vm.$dialog.error(error))
				.always(() => vm.$blockui('hide'));

		}

		createNotice(data: NotificationCreated) {
			const vm = this;
			vm.notificationCreated(data);

			if (vm.isStartUpdateMode) {
				vm.isStartUpdateMode = false;
				const wkpList = vm.notificationCreated().targetWkps;
				vm.workPlaceTxtRefer(_.map(wkpList, wkp => wkp.workplaceName).join(COMMA));
				vm.workPlaceIdList(_.map(wkpList, wkp => wkp.workplaceId));
			}

			if (_.isEmpty(vm.workPlaceTxtRefer()) && !_.isNil(this.notificationCreated().workplaceInfo)) {
				const workplaceInfo = this.notificationCreated().workplaceInfo;
				vm.workPlaceIdList([workplaceInfo.workplaceId]);
				vm.workPlaceTxtRefer(workplaceInfo.workplaceName);
			}
			
			// ※　ロール.参照範囲＝全社員　OR　部門・職場(配下含む）の場合
			if (vm.employeeReferenceRange() === EmployeeReferenceRange.ALL_EMPLOYEE || vm.employeeReferenceRange() === EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
				const targetWkps = data.targetWkps;
				const workPlaceIdList = _.map(targetWkps, wkp => wkp.workplaceId);
				const workPlaceName = _.map(targetWkps, wkp => wkp.workplaceName);
				vm.workPlaceIdList(workPlaceIdList);
				vm.workPlaceName(workPlaceName);
			}
		}

        /**
         * Q20_1：登録をクリックする
         */
		onClickRegister() {
			const vm = this;
			const error: string = vm.checkBeforeRegister();
			if (!_.isEmpty(error)) {
				vm.$dialog.error({ messageId: error });
				return;
			}
			if (vm.isNewMode()) {
				vm.registerOnNewMode();
			} else {
				vm.registerOnUpdateMode();
			}
		}

        /**
         * Q1 登録前のチェックについて
         */
		checkBeforeRegister(): string {
			const vm = this;
			if (vm.destination() === DestinationClassification.WORKPLACE && _.isEmpty(vm.workPlaceIdList())) {
				return 'Msg_1813';
			}
			if (moment.utc(vm.dateValue().startDate).isBefore(moment.utc().format('YYYY/MM/DD'))) {
				if (vm.isNewMode()) {
					return 'Msg_1834';
				} else if (!moment.utc(vm.startDateOfMsgUpdate, 'YYYY/MM/DD').isSame(moment.utc(vm.dateValue().startDate, 'YYYY/MM/DD'))) {
					return 'Msg_1834';
				}
			}
		}

        /**
         * ※新規モードの場合
         */
		registerOnNewMode() {
			const vm = this;
			const message: MessageNotice = new MessageNotice({
				creatorID: __viewContext.user.employeeId,
				inputDate: moment.utc().toISOString(),
				modifiedDate: moment.utc().toISOString(),
				notificationMessage: vm.messageText(),
				targetInformation: new TargetInformation({
					destination: vm.destination(),
					targetWpids: vm.workPlaceIdList(),
					targetSIDs: []
				}),
				startDate: moment.utc(vm.dateValue().startDate).toISOString(),
				endDate: moment.utc(vm.dateValue().endDate).toISOString(),
				employeeIdSeen: null
			});

			const command = {
				creatorID: __viewContext.user.employeeId,
				messageNotice: message
			}
			vm.$blockui('show');
			vm.$validate().then((valid: boolean) => {
				if (valid) {
					vm.$ajax('com', API.REGISTER_NOTICE, command)
						.done(() => {
							vm.$dialog.info({ messageId: 'Msg_15' })
								.then(() => this.$window.close({ isClose: false }))
						})
						.fail((error: any) => {
							vm.$dialog.error(error);
						}).always(() => {
							vm.$blockui("hide");
						});
				} else {
					vm.$blockui("clear");
				}
			});

		}

        /**
         * ※更新モードの場合
         */
		registerOnUpdateMode() {
			const vm = this;
			const oldMsg = vm.parentParam.messageNotice;

			const message: MessageNotice = new MessageNotice({
				creatorID: oldMsg.creatorID,
				inputDate: oldMsg.inputDate,
				modifiedDate: moment.utc().toISOString(),
				notificationMessage: vm.messageText(),
				targetInformation: new TargetInformation({
					destination: vm.destination(),
					targetWpids: vm.isActiveWorkplaceBtn() ? vm.workPlaceIdList() : [],
					targetSIDs: []
				}),
				startDate: moment.utc(vm.dateValue().startDate).toISOString(),
				endDate: moment.utc(vm.dateValue().endDate).toISOString(),
			});

			const command = {
				sid: oldMsg.creatorID,
				inputDate: oldMsg.inputDate,
				messageNotice: message
			}
			vm.$blockui('show');
			vm.$validate().then((valid: boolean) => {
				if (valid) {
					vm.$ajax('com', API.UPDATE_NOTICE, command)
						.then(() => {
							vm.$dialog.info({ messageId: 'Msg_15' })
								.then(() => this.$window.close({ isClose: true }))
						})
						.fail(error => vm.$dialog.error(error))
						.always(() => vm.$blockui('hide'));
				} else {
					vm.$blockui('clear');
				}

			});

		}

        /**
         * Q20_2: 削除をクリックする
         */
		onClickDelete() {
			const vm = this;
			vm.$dialog.confirm({ messageId: 'Msg_18' }).then((result: 'no' | 'yes' | 'cancel') => {
				if (result !== 'yes') {
					return;
				}
				const messageNotice = vm.parentParam.messageNotice;
				const command = {
					creatorID: messageNotice.creatorID,
					inputDate: messageNotice.inputDate
				};
				vm.$blockui('show');
				vm.$ajax('com', API.DELETE_NOTICE, command)
					.then(() => {
						vm.$dialog.info({ messageId: 'Msg_16' })
							.then(() => this.$window.close({ isClose: false }))
					})
					.fail(error => {
							vm.$dialog.error(error)
							.then(() => this.$window.close())
					 })
					.always(() => vm.$blockui('hide'));
			});
		}

        /**
         * Q20_3：職場選択をクリックする
         */
		openKDP003KDialog() {
			const vm = this;
			vm.$window.modal('at', '/view/kdp/003/k/index.xhtml', { multiSelect: true })
				.then((selectedWP) => {
					if (selectedWP.selectedId) {
						vm.workPlaceIdList(selectedWP.selectedId);
					}

					if (vm.workPlaceIdList().length > 0) {
						vm.$ajax('com', API.GET_NAME_OF_DESTINATION_WKP, vm.workPlaceIdList()).then((response: WorkplaceInfo[]) => {
							if (response) {
								const workPlaceIdList = _.map(response, x => x.workplaceId);
								const workPlaceName = _.map(response, x => x.workplaceName);
								vm.workPlaceIdList(workPlaceIdList);
								vm.workPlaceName(workPlaceName);
								vm.workPlaceTxtRefer(vm.workPlaceName().join(COMMA));
							}
							});
					}
				});

				
	}

        /**
         * Q20_4: 戻る
         */
		returnP() {
			const vm = this;
			vm.$window.close();
		}

	}
	enum DestinationClassification {
		ALL = 0,
		WORKPLACE = 1,
		DEPARTMENT = 2
	}

	enum StartMode {
		WORKPLACE = 0,
		DEPARTMENT = 1
	}

	enum EmployeeReferenceRange {
		ALL_EMPLOYEE = 0,
		DEPARTMENT_AND_CHILD = 1,
		DEPARTMENT_ONLY = 2,
		ONLY_MYSELF = 3
	}

	export interface WorkplaceInfo {
		workplaceId: string; //職場ID
		workplaceCode: string; //職場コード
		workplaceName: string; //職場表示名
	}

	export class ParentParam {
		isNewMode: boolean;
		role: Role;
		messageNotice: MessageNotice;
		regionalTime: number;
	}

	export class DatePeriod {
		startDate: string;
		endDate: string;

		constructor(init?: Partial<DatePeriod>) {
			$.extend(this, init);
		}
	}

	export class MessageNotice {
		creatorID: string; //作成者ID
		inputDate: string; //入力日
		modifiedDate: string; //更新日
		targetInformation: TargetInformation; //対象情報
		startDate: any; //開始日
		endDate: any; //終了日
		employeeIdSeen: string[]; //対象情報
		notificationMessage: string; //メッセージの内容

		constructor(init?: Partial<MessageNotice>) {
			$.extend(this, init);
		}
	}

	export class NotificationParams {
		refeRange: number;
		msg: MessageNotice;
		constructor(init?: Partial<NotificationParams>) {
			$.extend(this, init);
		}
	}

	export class WorkplaceInfo {
		workplaceId: string;
		workplaceCode: string;
		workplaceName: string;
	}

	export class TargetInformation {
		targetSIDs: string[];
		targetWpids: string[];
		destination: number;
		constructor(init?: Partial<TargetInformation>) {
			$.extend(this, init);
		}
	}

	export interface NotificationCreated {
		workplaceInfo: WorkplaceInfo;
		targetWkps: WorkplaceInfo[];
	}

}