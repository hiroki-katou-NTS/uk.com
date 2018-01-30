module nts.uk.at.view.kal004.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = nts.uk.at.view.kal004.share.model;
    import service = nts.uk.at.view.kal004.a.service;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        alarmSource: KnockoutObservableArray<share.AlarmPatternSettingDto>;
        currentAlarm: share.AlarmPatternSettingDto;
        alarmHeader: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<string>;
        createMode: KnockoutObservable<boolean>;
        alarmCode: KnockoutObservable<string>;
        alarmName: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        alarmCategoryArr: Array<share.EnumConstantDto> = [];

        // Tab 1
        checkHeader: KnockoutObservableArray<any>;
        checkSource: Array<share.ModelCheckConditonCode>;
        checkConditionList: KnockoutObservableArray<share.ModelCheckConditonCode>;
        currentCodeListSwap: KnockoutObservableArray<share.ModelCheckConditonCode>;

        // Tab 2: Period setting
        periodSetting: any = new nts.uk.at.view.kal004.tab2.viewModel.ScreenModel();

        // Tab 3: SetPermission
        setPermissionModel: any = new nts.uk.at.view.kal004.tab3.viewmodel.ScreenModel();

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
                { headerText: getText('KAL004_8'), key: 'alarmPatternName', width: 175 },
            ]);
            self.alarmCode = ko.observable('');
            self.alarmName = ko.observable('');
            self.createMode = ko.observable(true);

            self.checkConditionList = ko.observableArray([]);
            self.checkSource = [];

            self.checkHeader = ko.observableArray([
                { headerText: getText('KAL004_21'), key: 'GUID', width: 10, hidden: true },
                { headerText: 'cssClass', key: 'cssClass', width: 10, hidden: true },
                { headerText: getText('KAL004_21'), key: 'categoryName', width: 120, template: "<span class='${cssClass}'>${categoryName}</span>" },
                { headerText: getText('KAL004_17'), key: 'checkConditonCode', width: 40, template: "<span class='${cssClass}'>${checkConditonCode}</span>" },
                { headerText: getText('KAL004_18'), key: 'checkConditionName', width: 150, template: "<span class='${cssClass}'>${checkConditionName}</span>" }
            ]);

            self.currentCodeListSwap = ko.observableArray([]);
            self.currentCode = ko.observable('');
            self.currentAlarm = null;
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getEnumAlarm().done((enumRes) => {
                self.alarmCategoryArr = enumRes;
            }).fail((enumErr) => {
                nts.uk.ui.dialog.alert({ messageId: enumErr.messageId });
            });
            service.getCheckConditionCode().done((res) => {
                let resolve = _.map(res, (x) => { return new share.ModelCheckConditonCode(x) });
                self.checkSource = _.cloneDeep(resolve);

                self.getAlarmPattern().done(() => {

                    if (self.alarmSource().length > 0) {
                        self.currentCode(self.alarmSource()[0].alarmPatternCD);
                    } else {
                        self.checkConditionList(self.checkSource);
                    }
                    dfd.resolve();
                });
            }).fail((error) => {
                nts.uk.ui.dialog.alert({ messageId: error.messageId });
                dfd.resolve();
            });
            return dfd.promise();
        }


        public getAlarmPattern(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();

            service.getAlarmPattern().done((res) => {
                let alarmResolve = _.sortBy(res, [function(o) { return o.alarmPatternCD; }]);
                self.alarmSource(alarmResolve);

                self.initSubscribe();

            }).fail((error) => {
                nts.uk.ui.dialog.alert({ messageId: error.messageId });
            }).always(() => {
                dfd.resolve();
            });

            return dfd.promise();
        }

        private initSubscribe(): void {
            var self = this;
            // AlarmPatternSetting
            self.currentCode.subscribe((newV) => {
                $('#alarmName').focus();
                self.periodSetting.listStorageCheckCondition.removeAll();
                self.alarmCodeChange(newV);

            });

            self.checkConditionList.subscribe((newCheck) => {
                _.remove(newCheck, function(leftItem) {
                    let optItem = _.find(self.checkSource, function(item) {
                        return leftItem.GUID == item.GUID;
                    });
                    return (optItem) ? false : true;
                });
                if (!_.differenceWith(newCheck, self.checkConditionList(), _.isEqual))
                    self.checkConditionList(newCheck);
            });
            
            // Tab 2: Period Setting
            self.currentCodeListSwap.subscribe((listCode) => {

                let categories = _.uniq(_.map(listCode, (code) => { return code.category }));
                let shareTab2: Array<share.CheckConditionCommand> = [];
                categories.forEach((category) => {

                    let checkConditionCodes = [];
                    listCode.forEach((code) => { if (code.category == category) { checkConditionCodes.push(code.checkConditonCode); } });

                    let categoryInputed = self.currentAlarm == null ? null : _.find(self.currentAlarm.checkConList, (checkCon) => { return checkCon.alarmCategory == category });
                    if (categoryInputed) {
                        shareTab2.push(new share.CheckConditionCommand(category, checkConditionCodes, new share.ExtractionPeriodDailyCommand(categoryInputed.extractionDaily)));
                    } else {
                        shareTab2.push(new share.CheckConditionCommand(category, checkConditionCodes, null));
                    }

                });
                self.periodSetting.listCheckConditionCode(shareTab2);

            });

        }

        public alarmCodeChange(newV): any {
            let self = this;
            errors.clearAll();
            if (newV == '') {
                self.createMode(true);
                self.alarmCode('');
                self.alarmName('');
                self.currentCode('');
                self.currentCodeListSwap([]);
                self.checkConditionList(_.sortBy(self.checkSource, ['category', 'checkConditonCode']));
                self.currentAlarm = null;
                
                // tab3
                self.setPermissionModel.listRoleID([]);
                self.setPermissionModel.selectedRuleCode(1);
            }
            else {
                self.createMode(false);
                self.currentAlarm = _.find(self.alarmSource(), function(a) { return a.alarmPatternCD == newV });
                self.alarmCode(self.currentAlarm.alarmPatternCD);
                self.alarmName(self.currentAlarm.alarmPatternName);

                // Tab 1
                let currentCodeListSwap = [];
                let checkSource = _.cloneDeep(self.checkSource);

                self.currentAlarm.checkConList.forEach((x) => {
                    x.checkConditionCodes.forEach((y) => {
                        let CD = _.find(_.cloneDeep(self.checkSource), { 'category': x.alarmCategory, 'checkConditonCode': y });
                        if (CD !== undefined)
                            currentCodeListSwap.push(_.cloneDeep(CD));
                        else {
                            let category = _.find(self.alarmCategoryArr, ['value', x.alarmCategory]);
                            currentCodeListSwap.push(share.ModelCheckConditonCode.createNotFoundCheckConditonCode(category, y));
                        }

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
                self.checkConditionList(_.sortBy(checkSource, ['category', 'checkConditonCode']));
                self.currentCodeListSwap(_.sortBy(currentCodeListSwap, ['category', 'checkConditonCode']));

                // Tab 3: Permission Setting
                self.setPermissionModel.listRoleID(self.currentAlarm.alarmPerSet.roleIds);
                self.setPermissionModel.selectedRuleCode(self.currentAlarm.alarmPerSet.authSetting == true ? 0 : 1);
            }
        }

        private buildNotFoundCheckCondition(): void {

        }

        public saveAlarm(): void {
            let self = this;

            // Validate input
            $(".nts-input").trigger("validate");
            if ($(".nts-input").ntsError("hasError")) return;
            
            // Validate logic
            if(_.find(self.currentCodeListSwap(), {'cssClass': 'red-color'})){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_817" });
                return;
            }
            
            // Create command
            let alarmPerSet: share.AlarmPermissionSettingCommand = new share.AlarmPermissionSettingCommand(self.setPermissionModel.selectedRuleCode() == 1 ? false : true, self.setPermissionModel.listRoleID());
            let checkConditonList: Array<share.CheckConditionCommand> = self.periodSetting.listCheckCondition();
            let command = new share.AddAlarmPatternSettingCommand(self.alarmCode(), self.alarmName(), alarmPerSet, checkConditonList);
            block.invisible();
            // Call service
            if (self.createMode()) {
                service.addAlarmPattern(command).done(() => {

                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });

                    self.getAlarmPattern().done(function() {
                        self.currentCode(self.alarmCode());
                    }).always(() => {
                        block.clear();
                    });
                }).fail((error) => {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    block.clear();
                });
            } else {
                service.updateAlarmPattern(command).done(() => {

                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });

                    self.getAlarmPattern().done(function() {
                        self.currentCode(self.alarmCode());
                    }).always(() => {
                        block.clear();
                    });
                }).fail((error) => {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    block.clear();
                });

            }
            $('#alarmName').focus();
        }
        public createAlarm(): void {
            let self = this;
            self.currentCode('');
            $('#alarmCode').focus();
            errors.clearAll();

        }
        public removeAlarm(): void {
            let self = this;
            if (self.currentCode() !== '') {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    // Call service  alarmPatternCD
                    block.invisible();
                    service.removeAlarmPattern({ alarmPatternCD: self.alarmCode() }).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });

                        let index = _.findIndex(self.alarmSource(), ['alarmPatternCD', self.alarmCode()]);
                        index = _.min([self.alarmSource().length - 2, index]);
                        self.getAlarmPattern().done(function() {
                            self.selectAlarmByIndex(index);
                        }).always(() => {
                            block.clear();
                        });

                    }).fail((error) => {
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                        block.clear();
                    });

                });

            }
        }
        private selectAlarmByIndex(index: number) {
            let self = this;
            let selectAlarmByIndex = _.nth(self.alarmSource(), index);
            if (selectAlarmByIndex !== undefined)
                self.currentCode(selectAlarmByIndex.alarmPatternCD);
            else {
                self.currentCode('');
                nts.uk.ui.errors.clearAll();
            }
        }


    }

}

