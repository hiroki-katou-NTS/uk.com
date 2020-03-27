module nts.uk.at.view.kdp010 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {

            title: KnockoutObservable<string> = ko.observable('');
            removeAble: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'B', name: getText('KDP010_3'), active: true }),
                new TabModel({ id: 'E', name: getText('KDP010_6') })
            ]);
            currentTab: KnockoutObservable<string> = ko.observable('B');
            oldtab: KnockoutObservable<any> = ko.observable(new TabModel({ id: 0, name: "" }));
            checkChange: KnockoutObservable<boolean> = ko.observable(true);

            constructor() {

                let self = this;
                //get use setting
                self.tabs().map((t) => {
                    // set title for tab
                    if (t.active() == true) {
                        self.title(t.name);
                    }
                });
            }

            changeTab(tab: TabModel) {

                let self = this,
                    view: any = __viewContext.viewModel;
                if (self.checkChange == false) {
                    return;
                }
                // cancel action if tab self click
                if (self.oldtab().id == tab.id) {
                    return;
                }
                //set not display remove button first when change tab
                tab.active(true);
                self.title(tab.name);
                self.oldtab(tab);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
                    case 'B':
                        self.currentTab('B');
                        if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                            view.viewmodelB.start();
                        }
                        break;
                    case 'E':
                        self.currentTab('E');
                        if (!!view.viewmodelE && typeof view.viewmodelE.start == 'function') {
                            view.viewmodelE.start();
                        }
                        break;
                }
            }
        }

        interface ITabModel {
            id: any;
            name: string;
            active?: boolean;
            display?: boolean;
        }

        class TabModel {
            id: any;
            name: string;
            active: KnockoutObservable<boolean> = ko.observable(false);
            display: KnockoutObservable<boolean> = ko.observable(true);

            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
                this.display(param.display || true);
            }

            changeTab() {
                // call parent view action
                __viewContext.viewModel.tabView.changeTab(this);
            }
        }
    }

    export module b.viewmodel {
        export class ScreenModel {
            // B4_2 - 打刻画面のサーバー時刻補正間隔 
            correcValue: KnockoutObservable<number> = ko.observable(10);
            // B5_2 - 打刻履歴表示方法
            optionStamping: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KDP010_19") },
                { id: 1, name: nts.uk.resource.getText("KDP010_20") },
                { id: 2, name: nts.uk.resource.getText("KDP010_21") }
            ]);
            selectedStamping: KnockoutObservable<number> = ko.observable(0);
            // B6_3
            letterColors: KnockoutObservable<string> = ko.observable("#ffffff");
            // B6_5
            backgroundColors: KnockoutObservable<string> = ko.observable("#0033cc");
            // B10_2
            optionHighlight: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KDP010_39") },
                { id: 1, name: nts.uk.resource.getText("KDP010_40") }
            ]);
            selectedHighlight: KnockoutObservable<number> = ko.observable(0);
            // B7_2
            stampValue: KnockoutObservable<number> = ko.observable(3);
            // List StampPageLayout (ページレイアウト設定)
            lstStampPage: KnockoutObservable<any> = ko.observable({});
            checkInUp: KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                let self = this;
            }

            /**
             * Start page.
             */
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                self.getStamp();
                self.getData();

                return dfd.promise();
            }

            /**
             * Get data from db.
             */
            getData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getStampSetting().done(function(totalTimeArr) {
                    if (totalTimeArr) {
                        self.selectedHighlight(totalTimeArr.buttonEmphasisArt);
                        self.selectedStamping(totalTimeArr.historyDisplayMethod);
                        self.correcValue(totalTimeArr.correctionInterval);
                        self.letterColors(totalTimeArr.textColor);
                        self.backgroundColors(totalTimeArr.backGroundColor);
                        self.stampValue(totalTimeArr.resultDisplayTime);
                    }
                    dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }

            getStamp(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getStampPage().done(function(stampPage) {
                    if (stampPage && stampPage.length > 0)
                        self.checkInUp(true);
                    else
                        self.checkInUp(false);
                    
                        dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }

            /**
             * Registration function. 
             */
            registration() {
                let self = this;
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                nts.uk.ui.block.invisible();
                // Data from Screen 
                let data = new model.StampSettingPersonDto({
                    buttonEmphasisArt: self.selectedHighlight(),
                    historyDisplayMethod: self.selectedStamping(),
                    correctionInterval: self.correcValue(),
                    textColor: self.letterColors(),
                    backGroundColor: self.backgroundColors(),
                    resultDisplayTime: self.stampValue()
                });
                service.saveStampSetting(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * Open E dialog to set condition list.
             */
            openEDialog() {
                nts.uk.ui.windows.sub.modal("/view/kdp/010/g/index.xhtml").onClosed(() => {
                let self = this;
                    self.getStamp();   
                });
            }
        }
    }

    export module model {
        export class StampSettingPersonDto {

            /** 個人利用の打刻設定.打刻ボタンを抑制する */
            buttonEmphasisArt: boolean;
            /** 個人利用の打刻設定.打刻画面の表示設定.打刻履歴表示方法 */
            historyDisplayMethod: number;
            /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面のサーバー時刻補正間隔 */
            correctionInterval: number;
            /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面の日時の色設定.文字色 */
            textColor: string;
            /** 個人利用の打刻設定.打刻画面の表示設定.打刻画面の日時の色設定.背景色  */
            backGroundColor: string;
            /** 個人利用の打刻設定.打刻画面の表示設定.打刻結果自動閉じる時間 */
            resultDisplayTime: number;

            constructor(param: IStampSettingPersonDto) {
                this.buttonEmphasisArt = param.buttonEmphasisArt;
                this.historyDisplayMethod = param.historyDisplayMethod;
                this.correctionInterval = param.correctionInterval;
                this.textColor = param.textColor;
                this.backGroundColor = param.backGroundColor;
                this.resultDisplayTime = param.resultDisplayTime;
            }
        }

        interface IStampSettingPersonDto {
            buttonEmphasisArt: number;
            historyDisplayMethod: number;
            correctionInterval: number;
            textColor: number;
            backGroundColor: number;
            resultDisplayTime: number;
        }
    }

}