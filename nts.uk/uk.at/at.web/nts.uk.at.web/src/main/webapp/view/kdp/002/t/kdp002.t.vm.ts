module nts.uk.at.view.kdp002.t {
    export module viewmodel {
        export class ScreenModel {
            dataShare: KnockoutObservableArray<any> = ko.observableArray([]);
            labelNames: KnockoutObservable<string> = ko.observable('');
            labelColor: KnockoutObservable<string> = ko.observable('');
            buttonInfo: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
            share: KnockoutObservableArray<any> = ko.observableArray([]);
            messageContent: KnockoutObservable<string> = ko.observable('');
            messageColor: KnockoutObservable<string> = ko.observable('');
            errorDate: KnockoutObservable<string> = ko.observable('');
            errorDateStr: KnockoutObservable<string> = ko.observable('');
            constructor() {
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                self.share = nts.uk.ui.windows.getShared('KDP010_2T');
                let lstButton: ItemModel[] = [];
                for (let i = 1; i < 6; i++) {
                    lstButton.push(new ItemModel('', '', ''));
                }
                self.buttonInfo(lstButton);
                if (!self.share) {
                    self.dataShare = {
                        messageContent: '個人の打刻入力で利用する設定です',
                        messageColor: '#0033cc',
                        listRequired: [{
                            buttonName: ko.observable('個人の打')
                        }, {
                            buttonName: ko.observable('個人の打')
                        }, {
                            buttonName: ko.observable('個人の打')
                        }, {
                            buttonName: ko.observable('個人の打')
                        }, {
                            buttonName: ko.observable('個人の打')
                        }, {
                            buttonName: ko.observable('個人の打')
                        }]
                    }
                } else {
                    self.share.dailyAttdErrorInfos = _.orderBy(self.share.dailyAttdErrorInfos, ['lastDateError'], ['desc']);
                    let error = self.share.dailyAttdErrorInfos[0];
                    self.messageContent(error.messageContent);
                    self.messageColor(error.messageColor);
                    self.errorDate(error.lastDateError);
                    self.errorDateStr( nts.uk.resource.getText('KDP002_102')  + error.lastDateError);
                    let listRequired = [];
                    let length = error.listRequired.length > 6 ? 6 : error.listRequired.length;
                    for (let idx = 0; idx < length; idx ++) {
                        listRequired.push(self.getBtn(error.listRequired[idx]));
                    }
                    self.dataShare = {
                        listRequired: listRequired
                    }
                }

                dfd.resolve();
                return dfd.promise();
            }

            public getBtn(errorType: number) {
                let self = this;
                let btn = {};
                let transfer = { appDate: self.errorDate() };
                switch (errorType) {

                    case 0:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 0 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;
                    case 1:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 0 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;

                    case 2:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 0 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;
                    case 3:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 1 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;
                    case 4:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 2 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;

                    case 6:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 4 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;

                    case 7:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 6 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;
                    case 8:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 7 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        //KAF002-打刻申請（外出許可）
                        transfer.stampRequestMode = 0;
                        transfer.screenMode = 1;
                        break;
                    case 9:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 7 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        //KAF002-打刻申請（出退勤打刻漏れ）
                        transfer.stampRequestMode = 1;
                        transfer.screenMode = 1;
                        break;
                    case 10:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 7 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        //KAF002-打刻申請（打刻取消）
                        transfer.stampRequestMode = 2;
                        transfer.screenMode = 1;
                        break;
                    case 11:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 7 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        //KAF002-打刻申請（レコーダイメージ）
                        transfer.stampRequestMode = 3;
                        transfer.screenMode = 1;
                        break;
                    case 12:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 7 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        //KAF002-打刻申請（その他）
                        transfer.stampRequestMode = 4;
                        transfer.screenMode = 1;
                        break;

                    case 14:
                        let app = _.find(self.share.appDispNames, (app) => { return app.appType === 10 });
                        btn.buttonName = app ? ko.observable(app.dispName) : ko.observable('');
                        btn.appType = app.appType;
                        btn.screen = app.url;
                        break;

                    default:
                        break;
                }

                btn.transfer = transfer;
                return btn;
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                let self = this;
                let shareG = {
                    messageContent: self.labelNames(),
                    messageColor: self.labelColor(),
                    errorDate: self.errorDate(),
                    listRequired: self.dataShare.listRequired,
                    isClose: true
                };
                nts.uk.ui.windows.setShared('KDP010_T', shareG);
                nts.uk.ui.windows.close();
            }

            /**
             * Close dialog
             */
            public jumpScreen(data, vm): void {
                let shareG = {
                    messageContent: vm.labelNames(),
                    messageColor: vm.labelColor(),
                    errorDate: vm.errorDate(),
                    btn: data
                };
                nts.uk.ui.windows.setShared('KDP010_T', shareG);
                nts.uk.ui.windows.close();
            }

        }

    }
    export class ItemModel {
        buttonName: string;
        buttonColor: string;
        textColor: string;

        constructor(buttonName: string, buttonColor: string, textColor: string) {
            this.buttonName = ko.observable('') || '';
            this.buttonColor = ko.observable('') || '';
            this.textColor = ko.observable('') || '';
        }
    }
}