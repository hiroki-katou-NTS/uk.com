module nts.uk.at.view.kaf018.h.viewmodel {
    import text = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import model = kaf018.share.model;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        checkH3: KnockoutObservable<boolean> = ko.observable(false);
        checkH2: KnockoutObservable<boolean> = ko.observable(false);
        checkH1: KnockoutObservable<boolean> = ko.observable(false);

        appApprovalUnapproved: model.MailTemp = null;
        dailyUnconfirmByPrincipal: model.MailTemp = null;
        dailyUnconfirmByConfirmer: model.MailTemp = null;
        monthlyUnconfirmByConfirmer: model.MailTemp = null;
        workConfirmation: model.MailTemp = null;

        identityProcessUseSet: model.IdentityProcessUseSet = new model.IdentityProcessUseSet(false);
        approvalProcessingUseSet: model.ApprovalProcessingUseSetting = new model.ApprovalProcessingUseSetting(false, false);

        screenEditMode: KnockoutObservable<boolean> = ko.observable(false);
        listMailType: [];

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: text("KAF018_77"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: text("KAF018_78"), content: '.tab-content-2', enable: self.checkH3, visible: ko.observable(true) },
                { id: 'tab-3', title: text("KAF018_79"), content: '.tab-content-3', enable: self.checkH2, visible: ko.observable(true) },
                { id: 'tab-4', title: text("KAF018_80"), content: '.tab-content-4', enable: self.checkH1, visible: ko.observable(true) },
                { id: 'tab-5', title: text("KAF018_81"), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.selectedTab.subscribe((newValue) => {
                let mailType = 0;
                switch (newValue) {
                    case 'tab-1':
                        self.screenEditMode(self.appApprovalUnapproved.editMode());
                        break;
                    case 'tab-2':
                        self.screenEditMode(self.dailyUnconfirmByPrincipal.editMode());
                        break;
                    case 'tab-3':
                        self.screenEditMode(self.dailyUnconfirmByConfirmer.editMode());
                        break;
                    case 'tab-4':
                        self.screenEditMode(self.monthlyUnconfirmByConfirmer.editMode());
                        break;
                    case 'tab-5':
                        self.screenEditMode(self.workConfirmation.editMode());
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

            service.getMailBySetting().done(function(data: any) {
                _.each(data, function(mail) {
                    let temp = new model.MailTemp(
                        mail.mailType,
                        mail.mailSubject,
                        mail.mailContent,
                        mail.urlApprovalEmbed,
                        mail.urlDayEmbed,
                        mail.urlMonthEmbed,
                        mail.editMode);
                    switch (mail.mailType) {
                        case 0:
                            self.appApprovalUnapproved = temp;
                            break
                        case 1:
                            self.dailyUnconfirmByPrincipal = temp;
                            break
                        case 2:
                            self.dailyUnconfirmByConfirmer = temp;
                            break
                        case 3:
                            self.monthlyUnconfirmByConfirmer = temp;
                            break
                        case 4:
                            self.workConfirmation = temp;
                            break;
                    }
                });
                self.checkH3(self.dailyUnconfirmByPrincipal == null ? false : true);
                self.checkH2(self.dailyUnconfirmByConfirmer == null ? false : true);
                self.checkH1(self.monthlyUnconfirmByConfirmer == null ? false : true);

                block.clear();
                dfd.resolve();
            });
            return dfd.promise();
            /*let cond1 = service.getIdentityProcessUseSet();
            let cond2 = service.getApprovalProcessingUseSetting();
            
            $.when(cond1, cond2).done(function(dataCond1, dataCond2) {
                self.identityProcessUseSet.useIdentityOfMonth(dataCond1.useIdentityOfMonth == 0 ? false : true);
                self.approvalProcessingUseSet.useDayApproverConfirm(dataCond2.useDayApproverConfirm == 0 ? false : true);
                self.approvalProcessingUseSet.useMonthApproverComfirm(dataCond2.useMonthApproverComfirm == 0 ? false : true);

                let sv0 = self.getApprovalStatusMail(0);
                let sv1 = self.getApprovalStatusMail(1);
                let sv2 = self.getApprovalStatusMail(2);
                let sv3 = self.getApprovalStatusMail(3);
                let sv4 = self.getApprovalStatusMail(4);
                $.when(sv0, sv1, sv2, sv3, sv4).done(function(data0, data1, data2, data3, data4) {
                    self.appApprovalUnapproved = data0;
                    self.dailyUnconfirmByPrincipal = data1;
                    self.dailyUnconfirmByConfirmer = data2;
                    self.monthlyUnconfirmByConfirmer = data3;
                    self.workConfirmation = data4;
                    self.screenEditMode(data0.editMode());

                    block.clear();
                    dfd.resolve();
                })
            })
            return dfd.promise();*/
        }

        /**
         * アルゴリズム「承認状況本文起動」を実行する
         */
        private getApprovalStatusMail(mailType: number): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred();
            service.getApprovalStatusMail(mailType).done(function(data: any) {
                //ドメインが取得できた場合(lấy được)
                if (data) {
                    dfd.resolve(new model.MailTemp(
                        data.mailType,
                        data.mailSubject,
                        data.mailContent,
                        data.urlApprovalEmbed,
                        data.urlDayEmbed,
                        data.urlMonthEmbed,
                        1));
                }
                else {
                    //ドメインが取得できなかった場合
                    dfd.resolve(new model.MailTemp(mailType, "", "", 1, 1, 1, 0));
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
            let listMail = [
                self.getMailTempJS(self.appApprovalUnapproved),
                self.getMailTempJS(self.workConfirmation)
            ];
            if (self.checkH3()) {
                listMail.push(self.getMailTempJS(self.dailyUnconfirmByPrincipal));
            }
            if (self.checkH2()) {
                listMail.push(self.getMailTempJS(self.dailyUnconfirmByConfirmer));
            }
            if (self.checkH1()) {
                listMail.push(self.getMailTempJS(self.monthlyUnconfirmByConfirmer));
            }

            //アルゴリズム「承認状況メール本文登録」を実行する
            service.registerApprovalStatusMail(listMail).done(function() {
                //画面モード　＝　更新
                self.screenEditMode(true);
                self.appApprovalUnapproved.editMode(true);
                self.dailyUnconfirmByPrincipal.editMode(true);
                self.dailyUnconfirmByConfirmer.editMode(true);
                self.monthlyUnconfirmByConfirmer.editMode(true);
                block.clear();
            });
        }

        private getMailTempJS(mail: model.MailTemp) {
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