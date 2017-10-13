module nts.uk.at.view.kdw007.a.viewmodel {
    export class ScreenModel {

        listUseAtr: KnockoutObservableArray<any>;
        listTypeAtr: KnockoutObservableArray<any>;
        gridListColumns: KnockoutObservableArray<any>;
        lstErrorAlarm: KnockoutObservableArray<any>;
        selectedErrorAlarm: KnockoutObservable<any>;
        selectedErrorAlarmCode: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.listUseAtr = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") }
            ]);
            self.listTypeAtr = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Error") },
                { code: '1', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Alarm") },
                { code: '2', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Other") }
            ]);
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW007_6"), key: 'code', width: 45 },
                { headerText: nts.uk.resource.getText("KDW007_7"), key: 'name', width: 280 }
            ]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("KDW007_9"), content: '.settingTab', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.lstErrorAlarm = ko.observableArray([]);
            self.selectedErrorAlarm = ko.observable(ko.mapping.fromJS(new ErrorAlarmWorkRecord()));
            self.selectedErrorAlarmCode = ko.observable(null);
            self.selectedErrorAlarmCode.subscribe((code) => {
                if (code) {
                    let foundItem = _.find(self.lstErrorAlarm(), (item: ErrorAlarmWorkRecord) => {
                        return item.code == code;
                    });
                    if (foundItem) {
                        self.changeSelectedErrorAlarm(foundItem);
                    }
                }
            });
        }

        changeSelectedErrorAlarm(foundItem) {
            let self = this;
            $(".nts-input").ntsError("clear");
            self.selectedErrorAlarm().companyId(foundItem.companyId);
            self.selectedErrorAlarm().code(foundItem.code);
            self.selectedErrorAlarm().name(foundItem.name);
            self.selectedErrorAlarm().fixedAtr(foundItem.fixedAtr);
            self.selectedErrorAlarm().useAtr(foundItem.useAtr);
            self.selectedErrorAlarm().typeAtr(foundItem.typeAtr);
            self.selectedErrorAlarm().displayMessage(foundItem.displayMessage);
            self.selectedErrorAlarm().boldAtr(foundItem.boldAtr);
            self.selectedErrorAlarm().messageColor(foundItem.messageColor);
            self.selectedErrorAlarm().cancelableAtr(foundItem.cancelableAtr);
            self.selectedErrorAlarm().errorDisplayItem(foundItem.errorDisplayItem);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAll().done((lstData) => {
                let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                self.lstErrorAlarm(sortedData);
                self.selectedErrorAlarmCode(sortedData[0].code);
                dfd.resolve();
            });
            return dfd.promise();
        }

        update() {
            let self = this;
            self.selectedErrorAlarm().boldAtr() ? self.selectedErrorAlarm().boldAtr(1) : self.selectedErrorAlarm().boldAtr(0);
            if(self.selectedErrorAlarm().name().trim() == ''){
                self.selectedErrorAlarm().name('');
                $("#errorAlarmWorkRecordName").focus();
                return;
            }
            if(self.selectedErrorAlarm().displayMessage().trim() == ''){
                self.selectedErrorAlarm().displayMessage('');
                $("#messageDisplay").focus();
                return;
            }
            service.update(ko.mapping.toJS(self.selectedErrorAlarm)).done(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                let currentItem = self.selectedErrorAlarmCode();
                self.startPage().done(() => {
                    self.selectedErrorAlarmCode(currentItem);
                });
            });
        }
    }

    export class ErrorAlarmWorkRecord {
        /* 会社ID */
        companyId: string;
        /* コード */
        code: string;
        /* 名称 */
        name: string;
        /* システム固定とする */
        fixedAtr: number;
        /* 使用する */
        useAtr: number;
        /* 区分 */
        typeAtr: number;
        /* 表示メッセージ */
        displayMessage: string;
        /* メッセージを太字にする */
        boldAtr: number;
        /* メッセージの色 */
        messageColor: string;
        /* エラーアラームを解除できる */
        cancelableAtr: number;
        /* エラー表示項目 */
        errorDisplayItem: number;

        constructor() {
            this.companyId = '';
            this.code = '';
            this.name = '';
            this.fixedAtr = 0;
            this.useAtr = 0;
            this.typeAtr = 0;
            this.displayMessage = '';
            this.boldAtr = 0;
            this.messageColor = '';
            this.cancelableAtr = 0;
            this.errorDisplayItem = null;
        }
    }
}