module nts.uk.at.view.kaf018.h.viewmodel {
    import text = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import model = kaf018.share.model;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        appApprovalUnapproved: model.MailTemp;
        dailyUnconfirmByPrincipal: model.MailTemp;
        dailyUnconfirmByConfirmer: model.MailTemp;
        monthlyUnconfirmByConfirmer: model.MailTemp;

        identityProcessUseSet: model.IdentityProcessUseSet = new model.IdentityProcessUseSet(0);
        approvalProcessingUseSet: model.ApprovalProcessingUseSetting = new model.ApprovalProcessingUseSetting(0, 0);

        screenEditMode: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: text("KAF018_77"), content: '.tab-content-1', enable: true, visible: true },
                { id: 'tab-2', title: text("KAF018_78"), content: '.tab-content-2', enable: self.identityProcessUseSet.useIdentityOfMonth, visible: ko.observable(true) },
                { id: 'tab-3', title: text("KAF018_79"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: text("KAF018_80"), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.selectedTab.subscribe((newValue) => {
                let mailType = 0;
                switch (newValue) {
                    case 'tab-1':
                        self.screenEditMode(self.appApprovalUnapproved.screenEditMode());
                        break;
                    case 'tab-2':
                        self.screenEditMode(self.dailyUnconfirmByPrincipal.screenEditMode());
                        break;
                    case 'tab-3':
                        self.screenEditMode(self.dailyUnconfirmByConfirmer.screenEditMode());
                        break;
                    case 'tab-4':
                        self.screenEditMode(self.monthlyUnconfirmByConfirmer.screenEditMode());
                        break;
                }
            });
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred();
            block.invisible();
            let sv0 = self.getApprovalStatusMail(0);
            let sv1 = self.getApprovalStatusMail(1);
            let sv2 = self.getApprovalStatusMail(2);
            let sv3 = self.getApprovalStatusMail(3);

            let cond1 = service.getIdentityProcessUseSet();
            let cond2 = service.getApprovalProcessingUseSetting();

            $.when(sv0, sv1, sv2, sv3, cond1, cond2).done(function(data0, data1, data2, data3, dataCond1, dataCond2) {
                self.appApprovalUnapproved = data0;
                self.dailyUnconfirmByPrincipal = data1;
                self.dailyUnconfirmByConfirmer = data2;
                self.monthlyUnconfirmByConfirmer = data3;

                self.identityProcessUseSet = dataCond1;
                self.approvalProcessingUseSet = dataCond2;

                block.clear();
                dfd.resolve();
            })
            return dfd.promise();
        }

        /**
         * アルゴリズム「承認状況本文起動」を実行する
         */
        private getApprovalStatusMail(mailType: number): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred();
            service.getApprovalStatusMail(mailType).done(function(data: any) {
                //ドメインが取得できた場合(lấy được)
                if (data && data.length) {
                    dfd.resolve(new model.MailTemp(data[0].mailType, data[0].mailSubject, data[0].mailContent, data[0].urlApprovalEmbed, data[0].urlDayEmbed, data[0].urlMonthEmbed, true));
                }
                //ドメインが取得できなかった場合
                else {
                    dfd.resolve(new model.MailTemp(mailType, "", "", 1, 1, 1, false));
                }

            })
            return dfd.promise();
        }

        /**
         * メール本文を登録する
         */
        private registerApprovalStatusMail(): void {
            var self = this;
            block.invisible();
            let obj = [
                self.appApprovalUnapproved,
                self.dailyUnconfirmByPrincipal,
                self.dailyUnconfirmByConfirmer,
                self.monthlyUnconfirmByConfirmer
            ];
            service.registerApprovalStatusMail(obj).done(function() {
                //画面モード　＝　更新
                self.screenEditMode(true);
                self.appApprovalUnapproved.screenEditMode(true);
                self.dailyUnconfirmByPrincipal.screenEditMode(true);
                self.dailyUnconfirmByConfirmer.screenEditMode(true);
                self.monthlyUnconfirmByConfirmer.screenEditMode(true);
            }).fail(function() {

            }).always(function() {
                block.clear();
            });
        }

        /**
         * テストメールを送信する
         */
        sendTestMail() {
            var self = this;
            block.invisible();
            self.confirmSenderMail().done(function(data: any) {
                //メッセージ（Msg_800）を表示する
                confirm({ messageId: "Msg_800", messageParams: [data.mailAddress] }).ifYes(() => {
                    alert("YES!");
                })
            }).fail(function() {

            }).always(function() {
                block.clear();
            })
        }

        /**
         * アルゴリズム「承認状況送信者メール確認」を実行する
         */
        private confirmSenderMail(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve({ empId: "001", empName: "name 1", mailAddress: "ABC@gmail.com" });
            return dfd.promise();
        }

        /**
         * アルゴリズム「承認状況メールテスト送信実行」を実行する
         */
        private exeTestMail(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * 終了する
         */
        close() {
            //画面を閉じる
            nts.uk.ui.windows.close()
        }
    }
}