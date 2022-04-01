 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.i.viewmodel {
	import shareModel = kaf018.share.model;

	@bean()
	class Kaf018IViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
		selectedTab: KnockoutObservable<string>;

		useSetting: shareModel.UseSetting;
		
		checkI4: KnockoutObservable<boolean> = ko.observable(false);
		checkI3: KnockoutObservable<boolean> = ko.observable(false);
		checkI1: KnockoutObservable<boolean> = ko.observable(false);
		checkI2: KnockoutObservable<boolean> = ko.observable(false);
		checkI5: KnockoutObservable<boolean> = ko.observable(false);

		appApprovalUnapproved: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		dailyUnconfirmByPrincipal: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		dailyUnconfirmByConfirmer: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		monthlyUnconfirmByPrincipal: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		monthlyUnconfirmByConfirmer: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		workConfirmation: KnockoutObservable<shareModel.MailTemp> = ko.observable(null);
		
		screenEditMode: KnockoutObservable<boolean> = ko.observable(false);

		created() {
			const vm = this;
			vm.tabs = ko.observableArray([
				{ id: 'tab-1', title: vm.$i18n("KAF018_453"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
				{ id: 'tab-2', title: vm.$i18n("KAF018_454"), content: '.tab-content-2', enable: vm.checkI4, visible: vm.checkI4 },
				{ id: 'tab-3', title: vm.$i18n("KAF018_455"), content: '.tab-content-3', enable: vm.checkI3, visible: vm.checkI3 },
				{ id: 'tab-6', title: vm.$i18n("KAF018_456"), content: '.tab-content-6', enable: vm.checkI1, visible: vm.checkI1 },
				{ id: 'tab-4', title: vm.$i18n("KAF018_457"), content: '.tab-content-4', enable: vm.checkI2, visible: vm.checkI2 },
				{ id: 'tab-5', title: vm.$i18n("KAF018_458"), content: '.tab-content-5', enable: vm.checkI5, visible: vm.checkI5 }
			]);
			vm.selectedTab = ko.observable('tab-1');
			
			// vm.selectedTab.subscribe((newValue) => {
			// 	nts.uk.ui.errors.clearAll();
			// 	vm.hasError();
			// 	switch (newValue) {
			// 		case 'tab-1':
			// 			vm.screenEditMode(vm.appApprovalUnapproved().editMode());
			// 			vm.$nextTick(() => $("#I3_1_1").focus());
			// 			break;
			// 		case 'tab-2':
			// 			vm.screenEditMode(vm.dailyUnconfirmByPrincipal().editMode());
			// 			vm.$nextTick(() => $("#I4_1_1").focus());
			// 			break;
			// 		case 'tab-3':
			// 			vm.screenEditMode(vm.dailyUnconfirmByConfirmer().editMode());
			// 			vm.$nextTick(() => $("#I5_1_1").focus());
			// 			break;
			// 		case 'tab-6':
			// 			vm.screenEditMode(vm.monthlyUnconfirmByPrincipal().editMode());
			// 			vm.$nextTick(() => $("#I8_1_1").focus());
			// 			break;
			// 		case 'tab-4':
			// 			vm.screenEditMode(vm.monthlyUnconfirmByConfirmer().editMode());
			// 			vm.$nextTick(() => $("#I6_1_1").focus());
			// 			break;
			// 		case 'tab-5':
			// 			vm.screenEditMode(vm.workConfirmation().editMode());
			// 			vm.$nextTick(() => $("#I7_1_1").focus());
			// 			break;
			// 	}
			// });
			vm.$blockui("show");
			vm.$ajax('at', API.getUseSetting).done(function(useSetting) {
				vm.useSetting = useSetting;
				vm.$ajax('at', API.getMailTemp).done(function(data: any) {
					_.each(data, function(mail) {
						let temp = new shareModel.MailTemp(
							mail.mailType,
							mail.mailSubject,
							mail.mailContent,
							mail.urlApprovalEmbed,
							mail.urlDayEmbed,
							mail.urlMonthEmbed,
							mail.editMode);
						switch (mail.mailType) {
							case 0:
        						temp.urlDayEmbed(false);
        						temp.urlMonthEmbed(false);
								vm.appApprovalUnapproved(temp);
								break;
							case 1:
								temp.urlApprovalEmbed(false);
        						temp.urlMonthEmbed(false);
								vm.dailyUnconfirmByPrincipal(temp);
								break;
							case 2:
								temp.urlApprovalEmbed(false);
        						temp.urlMonthEmbed(false);
								vm.dailyUnconfirmByConfirmer(temp);
								break;
							case 5:
								temp.urlApprovalEmbed(false);
        						temp.urlDayEmbed(false);
								vm.monthlyUnconfirmByPrincipal(temp);
								break;
							case 3:
								temp.urlApprovalEmbed(false);
        						temp.urlDayEmbed(false);
								vm.monthlyUnconfirmByConfirmer(temp);
								break;
							case 4:
								temp.urlApprovalEmbed(false);
								vm.workConfirmation(temp);
								break;
							default:
								break;
						}
					});
				
					vm.screenEditMode(vm.appApprovalUnapproved().editMode());
					vm.checkI4(vm.useSetting.usePersonConfirm);
					vm.checkI3(vm.useSetting.useBossConfirm);
					vm.checkI1(vm.useSetting.monthlyIdentityConfirm);
					vm.checkI2(vm.useSetting.monthlyConfirm);
					vm.checkI5(vm.useSetting.employmentConfirm);
					
					vm.selectedTab.valueHasMutated();
				});
			}).always(() => {
				vm.$blockui("hide");
				
			});
		}

		mounted() {
			let vm = this;
			vm.selectedTab.subscribe((newValue) => {
				nts.uk.ui.errors.clearAll();
				// vm.hasError();
				switch (newValue) {
					case 'tab-1':
						vm.screenEditMode(vm.appApprovalUnapproved().editMode());
						vm.$nextTick(() => $("#I3_1_1").focus());
						break;
					case 'tab-2':
						vm.screenEditMode(vm.dailyUnconfirmByPrincipal().editMode());
						vm.$nextTick(() => $("#I4_1_1").focus());
						break;
					case 'tab-3':
						vm.screenEditMode(vm.dailyUnconfirmByConfirmer().editMode());
						vm.$nextTick(() => $("#I5_1_1").focus());
						break;
					case 'tab-6':
						vm.screenEditMode(vm.monthlyUnconfirmByPrincipal().editMode());
						vm.$nextTick(() => $("#I8_1_1").focus());
						break;
					case 'tab-4':
						vm.screenEditMode(vm.monthlyUnconfirmByConfirmer().editMode());
						vm.$nextTick(() => $("#I6_1_1").focus());
						break;
					case 'tab-5':
						vm.screenEditMode(vm.workConfirmation().editMode());
						vm.$nextTick(() => $("#I7_1_1").focus());
						break;
				}
			});
		}

		/**
		 * メール本文を登録する
		 */
		registerApprovalStatusMail(): void {
			const vm = this;

			//validate
			if (vm.hasError()) {
				return;
			}

			vm.$blockui("show");
			let listMail = [vm.getMailTempJS(vm.appApprovalUnapproved())];
			if (vm.checkI4()) {
				listMail.push(vm.getMailTempJS(vm.dailyUnconfirmByPrincipal()));
			}
			if (vm.checkI3()) {
				listMail.push(vm.getMailTempJS(vm.dailyUnconfirmByConfirmer()));
			}
			if (vm.checkI1()) {
				listMail.push(vm.getMailTempJS(vm.monthlyUnconfirmByPrincipal()));
			}
			if (vm.checkI2()) {
				listMail.push(vm.getMailTempJS(vm.monthlyUnconfirmByConfirmer()));
			}
			if (vm.checkI5()) {
				listMail.push(vm.getMailTempJS(vm.workConfirmation()));
			}
			
			//アルゴリズム「承認状況メール本文登録」を実行する
			vm.$ajax('at', API.registerMail, listMail).then(function() {
				//画面モード　＝　更新
				vm.screenEditMode(true);
				vm.appApprovalUnapproved().editMode(true);
				if(vm.checkI4()) {
					vm.dailyUnconfirmByPrincipal().editMode(true);
				}
				if(vm.checkI3()) {
					vm.dailyUnconfirmByConfirmer().editMode(true);
				}
				if(vm.checkI1()) {
					vm.monthlyUnconfirmByPrincipal().editMode(true);
				}
				if(vm.checkI2()) {
					vm.monthlyUnconfirmByConfirmer().editMode(true);
				}
				if(vm.checkI5()) {
					vm.workConfirmation().editMode(true);
				}
				return vm.$dialog.info({ messageId: "Msg_15" });
			}).then(() => {
				vm.$blockui("hide");		
			});
		}

		private hasError(): boolean {
			const vm = this;
			$('#I3_1_1').ntsError('check');
			$('#I3_2_1').ntsError('check');
			$('#I4_1_1').ntsError('check');
			$('#I4_2_1').ntsError('check');
			$('#I5_1_1').ntsError('check');
			$('#I5_2_1').ntsError('check');
			$('#I6_1_1').ntsError('check');
			$('#I6_2_1').ntsError('check');
			$('#I7_1_1').ntsError('check');
			$('#I7_2_1').ntsError('check');
			$('#I8_1_1').ntsError('check');
			$('#I8_2_1').ntsError('check');

			return nts.uk.ui.errors.hasError();
		}

		private getMailTempJS(mail: shareModel.MailTemp) {
			let obj = ko.toJS(mail)
			obj.urlApprovalEmbed = obj.urlApprovalEmbed ? 1 : 0;
			obj.urlDayEmbed = obj.urlDayEmbed ? 1 : 0;
			obj.urlMonthEmbed = obj.urlMonthEmbed ? 1 : 0;
			obj.editMode = obj.editMode ? 1 : 0;
			return obj;
		}

		/**
		 * テストメールを送信する
		 */
		sendTestMail() {
			const vm = this;
			if (nts.uk.ui.errors.hasError()) { return; }
			vm.$blockui("show");
			// アルゴリズム「承認状況メールテスト送信」を実行する
			vm.$ajax('at', API.confirmSenderMail).done(function(data: any) {
				//メッセージ（Msg_800）を表示する
				vm.$dialog.confirm({ messageId: "Msg_800", messageParams: [data] }).then((result) => {
					if (result === 'yes') {
						//アルゴリズム「承認状況メールテスト送信実行」を実行する
						let mailType = 0,
							mailTempJS: any = {},
							screenUrlApprovalEmbed = false,
							screenUrlDayEmbed = false,
							screenUrlMonthEmbed = false;
						switch (vm.selectedTab()) {
							case 'tab-1':
								mailType = 0;
								mailTempJS = vm.getMailTempJS(vm.appApprovalUnapproved());
								screenUrlApprovalEmbed = mailTempJS.urlApprovalEmbed == 1 ? true : false;
								break;
							case 'tab-2':
								mailType = 1;
								mailTempJS = vm.getMailTempJS(vm.dailyUnconfirmByPrincipal());
								screenUrlDayEmbed = mailTempJS.urlDayEmbed == 1 ? true : false;
								break;
							case 'tab-3':
								mailType = 2;
								mailTempJS = vm.getMailTempJS(vm.dailyUnconfirmByConfirmer());
								screenUrlDayEmbed = mailTempJS.urlDayEmbed == 1 ? true : false;
								break;
							case 'tab-6':
								mailType = 5;
								mailTempJS = vm.getMailTempJS(vm.monthlyUnconfirmByPrincipal());
								screenUrlMonthEmbed = mailTempJS.urlMonthEmbed == 1 ? true : false;
								break;
							case 'tab-4':
								mailType = 3;
								mailTempJS = vm.getMailTempJS(vm.monthlyUnconfirmByConfirmer());
								screenUrlMonthEmbed = mailTempJS.urlMonthEmbed == 1 ? true : false;
								break;
							case 'tab-5':
								mailType = 4;
								mailTempJS = vm.getMailTempJS(vm.workConfirmation());
								screenUrlDayEmbed = mailTempJS.urlDayEmbed == 1 ? true : false;
								screenUrlMonthEmbed = mailTempJS.urlMonthEmbed == 1 ? true : false;
								break;
						}
						vm.$blockui("show");
						vm.$ajax('at', API.sendTestMail, { mailType, screenUrlApprovalEmbed, screenUrlDayEmbed, screenUrlMonthEmbed })
						.done(function(result: any) {
							shareModel.showMsgSendEmail(result);
						}).fail(function(err) {
							vm.$dialog.error({ messageId: err.messageId });
						}).always(function() {
							vm.$blockui("hide");
						});
					}
				});
			}).fail(function(err) {
				vm.$dialog.error({ messageId: err.messageId });
			}).always(function() {
				vm.$blockui("hide");
			});
		}

		/**
		 * 終了する
		*/
		close() {
			const vm = this;
			//画面を閉じる
			vm.$window.close();
		}
	}

	const API = {
		getMailTemp: "at/request/application/approvalstatus/getMailTemp",
		registerMail: "at/request/application/approvalstatus/registerMail",
		confirmSenderMail: "at/request/application/approvalstatus/confirmSenderMail",
		sendTestMail: "at/request/application/approvalstatus/sendTestMail",
		getUseSetting: "at/record/application/realitystatus/getUseSetting"
	}
}