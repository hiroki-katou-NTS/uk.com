module kal004.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = kal004.share.model;
    import service = kal004.a.service;
    export class ScreenModel {
        alarmSource: KnockoutObservableArray<share.AlarmPatternSettingDto>;
        alarmHeader: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<string>;
        createMode: KnockoutObservable<boolean>;
        alarmCode: KnockoutObservable<string>;
        alarmName: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        checkConditionList: KnockoutObservableArray<share.ModelCheckConditonCode>;
        checkHeader: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        checkSource: Array<share.ModelCheckConditonCode>;

        // SetPermission
        setPermissionModel: any = new nts.uk.at.view.kal004.tab3.viewmodel.ScreenModel();

        // Period setting
        periodSetting: any = new nts.uk.at.view.kal004.tab2.viewModel.ScreenModel();

        constructor() {
            let self = this;
            self.alarmSource = ko.observableArray([]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL004_12'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL004_13'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: getText('KAL004_14'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.alarmHeader = ko.observableArray([
                { headerText: getText('KAL004_7'), key: 'alarmPatternCD', width: 40 },
                { headerText: getText('KAL004_8'), key: 'alarmPatternName', width: 150 },
            ]);
            self.alarmCode = ko.observable('');
            self.alarmName = ko.observable('');
            self.createMode = ko.observable(false);

            self.checkConditionList = ko.observableArray([]);
            self.checkSource = [];

            self.checkHeader = ko.observableArray([
                { headerText: getText('KAL004_21'), key: 'GUID', width: 10, hidden: true },
                { headerText: getText('KAL004_21'), key: 'categoryName', width: 130 },
                { headerText: getText('KAL004_17'), key: 'checkConditonCode', width: 50 },
                { headerText: getText('KAL004_18'), key: 'checkConditionName', width: 150 }
            ]);

            self.currentCodeListSwap = ko.observableArray([]);
            self.currentCode = ko.observable('');
        }

        public startPage(): any {
            let self = this;


            service.getCheckConditionCode().done((res1) => {
                self.checkConditionList(_.cloneDeep(res1));
                self.checkSource = _.cloneDeep(res1);

                service.getAlarmPattern().done((res) => {
                    self.alarmSource(res);

                    self.currentCode.subscribe((newV) => {
                        self.alarmCodeChange(newV);
                    });

                    if (res.length > 0) {
                        self.currentCode(res[0].alarmPatternCD);
                    }
                }).fail((error) => {
                    nts.uk.ui.dialog.alert({ messageId: error.messageId });
                });
            }).fail((error1) => {
                nts.uk.ui.dialog.alert({ messageId: error1.messageId });
            });

        }

        public alarmCodeChange(newV): any {
            let self = this;
            if (newV == '') self.createMode(true);
            else {
                self.createMode(false);
                let currentAlarm = _.find(self.alarmSource(), function(a) { return a.alarmPatternCD == newV });
                self.alarmCode(currentAlarm.alarmPatternCD);
                self.alarmName(currentAlarm.alarmPatternName);

                let currentCodeListSwap = [];
                let checkSource = _.cloneDeep(self.checkSource);

                currentAlarm.checkConList.forEach((x) => {
                    x.checkConditionCodes.forEach((y) => {
                        let CD = _.find(_.cloneDeep(self.checkSource), { 'category': x.alarmCategory, 'checkConditonCode': y });
                        currentCodeListSwap.push(_.cloneDeep(CD));
                    });
                });

                _.remove(checkSource, (leftItem) => {
                    let optItem = _.find(currentCodeListSwap, (rightItem) => {
                        return leftItem.GUID == rightItem.GUID;
                    });
                    if (optItem)
                        return optItem.GUID == leftItem.GUID;
                });
                self.currentCodeListSwap([]);
                self.checkConditionList(checkSource);
                self.currentCodeListSwap(currentCodeListSwap);

            }

        }
    }

}

