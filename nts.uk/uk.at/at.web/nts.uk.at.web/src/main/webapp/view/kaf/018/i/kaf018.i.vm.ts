 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.i.viewmodel {
	import shareModel = kaf018.share.model;

	@bean()
	class Kaf018IViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
		selectedTab: KnockoutObservable<string>;

		useSetting: shareModel.UseSetting;

		checkH3: KnockoutObservable<boolean> = ko.observable(false);
		checkH2: KnockoutObservable<boolean> = ko.observable(false);
		checkH1: KnockoutObservable<boolean> = ko.observable(false);

		appApprovalUnapproved: shareModel.MailTemp = null;
		dailyUnconfirmByPrincipal: shareModel.MailTemp = null;
		dailyUnconfirmByConfirmer: shareModel.MailTemp = null;
		monthlyUnconfirmByPrincipal: shareModel.MailTemp = null;
		monthlyUnconfirmByConfirmer: shareModel.MailTemp = null;
		workConfirmation: shareModel.MailTemp = null;
		
		screenEditMode: KnockoutObservable<boolean> = ko.observable(false);

		created() {
			const vm = this;
			vm.tabs = ko.observableArray([
				{ id: 'tab-1', title: vm.$i18n("KAF018_77"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
				{ id: 'tab-2', title: vm.$i18n("KAF018_78"), content: '.tab-content-2', enable: vm.checkH3, visible: vm.checkH3 },
				{ id: 'tab-3', title: vm.$i18n("KAF018_79"), content: '.tab-content-3', enable: vm.checkH2, visible: vm.checkH2 },
				{ id: 'tab-6', title: vm.$i18n("KAF018_456"), content: '.tab-content-6', enable: vm.checkH3, visible: vm.checkH3 },
				{ id: 'tab-4', title: vm.$i18n("KAF018_80"), content: '.tab-content-4', enable: vm.checkH1, visible: vm.checkH1 },
				{ id: 'tab-5', title: vm.$i18n("KAF018_81"), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) }
			]);
			vm.selectedTab = ko.observable('tab-1');
			
			vm.selectedTab.subscribe((newValue) => {
				let mailType = 0;
				nts.uk.ui.errors.clearAll();
				switch (newValue) {
					case 'tab-1':
						vm.screenEditMode(vm.appApprovalUnapproved.editMode());
						break;
					case 'tab-2':
						vm.screenEditMode(vm.dailyUnconfirmByPrincipal.editMode());
						break;
					case 'tab-3':
						vm.screenEditMode(vm.dailyUnconfirmByConfirmer.editMode());
						break;
					case 'tab-6':
						vm.screenEditMode(vm.monthlyUnconfirmByPrincipal.editMode());
						break;
					case 'tab-4':
						vm.screenEditMode(vm.monthlyUnconfirmByConfirmer.editMode());
						break;
					case 'tab-5':
						vm.screenEditMode(vm.workConfirmation.editMode());
						break;
				}
			});
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
								vm.appApprovalUnapproved = temp;
								break;
							case 1:
								vm.dailyUnconfirmByPrincipal = temp;
								break;
							case 2:
								vm.dailyUnconfirmByConfirmer = temp;
								break;
							case 3:
								vm.monthlyUnconfirmByConfirmer = temp;
								break;
							case 4:
								vm.workConfirmation = temp;
								break;
						}
					});
				
					vm.screenEditMode(vm.appApprovalUnapproved.editMode());
					if (vm.dailyUnconfirmByPrincipal.editMode()) {
						vm.checkH3(true);
					}
					else {
						vm.checkH3(vm.useSetting.usePersonConfirm);
					}
					if (vm.dailyUnconfirmByConfirmer.editMode()) {
						vm.checkH2(true);
					}
					else {
						vm.checkH2(vm.useSetting.useBossConfirm);
					}
					if (vm.monthlyUnconfirmByConfirmer.editMode()) {
						vm.checkH1(true);
					}
					else {
						vm.checkH1(vm.useSetting.monthlyConfirm);
					}
				});
			}).always(() => {
				vm.$blockui("hide");
				$("#H3_1_1").focus();
			});
		}

		/**
		 * メール本文を登録する
		 */
		private registerApprovalStatusMail(): void {
			const vm = this;

			//validate
			if (vm.hasError()) {
				return;
			}

			vm.$blockui("show");
			let listMail = [
				vm.getMailTempJS(vm.appApprovalUnapproved),
				vm.getMailTempJS(vm.workConfirmation)
			];
			if (vm.checkH3()) {
				listMail.push(vm.getMailTempJS(vm.dailyUnconfirmByPrincipal));
			}
			if (vm.checkH2()) {
				listMail.push(vm.getMailTempJS(vm.dailyUnconfirmByConfirmer));
			}
			if (vm.checkH1()) {
				listMail.push(vm.getMailTempJS(vm.monthlyUnconfirmByConfirmer));
			}
			
			//アルゴリズム「承認状況メール本文登録」を実行する
			vm.$ajax('at', API.registerMail, listMail).done(function() {
				//画面モード　＝　更新
				vm.screenEditMode(true);
				vm.appApprovalUnapproved.editMode(true);
				vm.workConfirmation.editMode(true);
				if (vm.checkH3()) {
					vm.dailyUnconfirmByPrincipal.editMode(true);
				}
				if (vm.checkH2()) {
					vm.dailyUnconfirmByConfirmer.editMode(true);
				}
				if (vm.checkH1()) {
					vm.monthlyUnconfirmByConfirmer.editMode(true);
				}
				vm.$dialog.info({ messageId: "Msg_15" });
				vm.$blockui("hide");
			});
		}

		private hasError(): boolean {
			const vm = this;
			$('#H3_1_1').ntsError('check');
			$('#H3_2_1').ntsError('check');
			$('#H4_1_1').ntsError('check');
			$('#H4_2_1').ntsError('check');
			$('#H5_1_1').ntsError('check');
			$('#H5_2_1').ntsError('check');
			$('#H6_1_1').ntsError('check');
			$('#H6_2_1').ntsError('check');
			$('#H7_1_1').ntsError('check');
			$('#H7_2_1').ntsError('check');

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
						let mailType = 0;
						switch (vm.selectedTab()) {
							case 'tab-1':
								mailType = 0;
								break;
							case 'tab-2':
								mailType = 1;
								break;
							case 'tab-3':
								mailType = 2;
								break;
							case 'tab-4':
								mailType = 3;
								break;
							case 'tab-5':
								mailType = 4;
								break;
						}
						vm.$blockui("show");
						vm.$ajax('at', nts.uk.text.format(API.sendTestMail, mailType)).done(function(result: any) {
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
		sendTestMail: "at/request/application/approvalstatus/sendTestMail/{0}",
		getUseSetting: "at/record/application/realitystatus/getUseSetting"
	}
}