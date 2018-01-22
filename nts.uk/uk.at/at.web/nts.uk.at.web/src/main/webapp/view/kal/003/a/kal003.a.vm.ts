module nts.uk.at.view.kal003.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = kal003.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import tab = nts.uk.at.view.kal003.a.tab;

    export class ScreenModel {
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;

        cbbItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getListCategory());
        selectedCategory: KnockoutObservable<number> = ko.observable(model.CATEGORY.SCHEDULE_DAILY);

        radioItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getConditionToExtractDaily());
        selectedDataCondition: KnockoutObservable<number> = ko.observable(model.DATA_CONDITION_TO_EXTRACT.ALL);

        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);

        listAlarmCheckCondition: KnockoutObservableArray<model.AlarmCheckConditionByCategory> = ko.observableArray([]);
        selectedAlarmCheckConditionCode: KnockoutObservable<string> = ko.observable('');
        selectedAlarmCheckCondition: KnockoutObservable<model.AlarmCheckConditionByCategory>;

        //scope check tab
        tabScopeCheck: tab.ScopeCheckTab;
        //daily tab
        tabDailyErrorAlarm: tab.DailyPerformanceTab;
        //check condition tab
        tabCheckCondition: tab.CheckConditionTab;
        //tab fixed check condition
        tabFixedCondition: tab.FixedCheckConditionTab;

        constructor() {
            var self = this;

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL003_15'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY }, this), visible: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY }, this) },
                { id: 'tab-3', title: getText('KAL003_16'), content: '.tab-content-3', enable: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY || self.selectedCategory() == model.CATEGORY.SCHEDULE_4_WEEK }, this), visible: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY || self.selectedCategory() == model.CATEGORY.SCHEDULE_4_WEEK }, this) },
                { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY }, this), visible: ko.computed(() => { return self.selectedCategory() == model.CATEGORY.DAILY }, this) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.tabScopeCheck = new tab.ScopeCheckTab();
            self.tabDailyErrorAlarm = new tab.DailyPerformanceTab();
            self.tabCheckCondition = new tab.CheckConditionTab(self.selectedCategory());
            self.tabFixedCondition = new tab.FixedCheckConditionTab();

            self.selectedCategory.subscribe((data) => {
                self.switchCategory(data);
            });

            self.selectedAlarmCheckCondition = ko.observable(new model.AlarmCheckConditionByCategory('', '', new model.ItemModel(0, ""), [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));

            self.selectedAlarmCheckConditionCode.subscribe(function(data: any) {
                if (data) {
                    let item = _.find(self.listAlarmCheckCondition(), (x: model.AlarmCheckConditionByCategory) => x.code() == data);
                    if (item) {
                        self.selectedAlarmCheckCondition(item);
                        self.tabScopeCheck.targetCondition(item.targetCondition());
                        self.tabCheckCondition.listWorkRecordExtractingConditions(item.dailyAlarmCheckCondition().listExtractConditionWorkRecork());
                        self.tabCheckCondition.schedule4WeekCheckCondition(item.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition());
                        self.selectedDataCondition(item.dailyAlarmCheckCondition().conditionToExtractDaily());
                        self.tabFixedCondition.listFixedConditionWorkRecord(item.dailyAlarmCheckCondition().listFixedExtractConditionWorkRecord());
                        self.screenMode(model.SCREEN_MODE.UPDATE);
                    }
                }
            });

        }

        startPage(code?: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.listAlarmCheckCondition.removeAll();

            service.getAllData(self.selectedCategory()).done(function(data: Array<any>) {
                
                if (data && data.length) {
                    let _accList: Array<model.AlarmCheckConditionByCategory> = _.map(data, acc => {
                        let category = _.find(ko.toJS(self.cbbItemList), (x: model.ItemModel) => x.code == acc.category);
                        let item = new model.AlarmCheckConditionByCategory(acc.code, acc.name, category, acc.availableRoles, new model.AlarmCheckTargetCondition(acc.targetCondition.filterByEmployment, acc.targetCondition.filterByClassification, acc.targetCondition.filterByJobTitle, acc.targetCondition.filterByBusinessType, acc.targetCondition.targetEmployment, acc.targetCondition.targetClassification, acc.targetCondition.targetJobTitle, acc.targetCondition.targetBusinessType));
                        let _fixedList: Array<model.FixedConditionWorkRecord> = _.map(acc.dailyAlarmCheckCondition.listFixedExtractConditionWorkRecord, (fix: any) => {return new model.FixedConditionWorkRecord({errorAlarmId: fix.errorAlarmId, checkName: fix.checkName, fixConWorkRecordNo: fix.fixConWorkRecordNo, message: fix.message, useAtr: fix.useAtr})});
                        item.dailyAlarmCheckCondition(new model.DailyAlarmCheckCondition(acc.dailyAlarmCheckCondition.conditionToExtractDaily, acc.dailyAlarmCheckCondition.addApplication, [], [], _fixedList));
                        item.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition(acc.schedule4WeekCondition);
                        return item;
                    });
                    
                    _.each(_accList, acc => self.listAlarmCheckCondition.push(acc));
                    if (code) {
                        self.selectedAlarmCheckConditionCode(code);
                    } else {
                        self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[0].code());
                    }
                    self.selectedAlarmCheckConditionCode.valueHasMutated();
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                } else {
                    self.createNewAlarmCheckCondition();
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError({ messageId: error.messageId });
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        createNewAlarmCheckCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedAlarmCheckConditionCode('');
            //self.openKAL003dDialog();
            let category = _.find(ko.toJS(self.cbbItemList), (x: model.ItemModel) => x.code == self.selectedCategory());
            self.selectedAlarmCheckCondition(new model.AlarmCheckConditionByCategory('', '', category, [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));
            self.selectedDataCondition(model.DATA_CONDITION_TO_EXTRACT.ALL);
            self.tabScopeCheck.targetCondition(new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], []));
            self.tabCheckCondition.category(self.selectedCategory());
            self.tabCheckCondition.listWorkRecordExtractingConditions([]);
            self.tabCheckCondition.schedule4WeekCheckCondition(model.SCHEDULE_4_WEEK_CHECK_CONDITION.FOR_ACTUAL_RESULTS_ONLY);
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        registerAlarmCheckCondition() {
            let self = this,
                data: model.AlarmCheckConditionByCategory = new model.AlarmCheckConditionByCategory(self.selectedAlarmCheckCondition().code(), self.selectedAlarmCheckCondition().name(), new model.ItemModel(self.selectedAlarmCheckCondition().category(), self.selectedAlarmCheckCondition().displayCategory), self.selectedAlarmCheckCondition().availableRoles(), self.selectedAlarmCheckCondition().targetCondition());
            data.targetCondition(self.tabScopeCheck.targetCondition());
            data.action(self.screenMode());
            if (data.category() == model.CATEGORY.DAILY) {
                data.dailyAlarmCheckCondition().conditionToExtractDaily(self.selectedDataCondition());
                data.dailyAlarmCheckCondition().addApplication(true);
                data.dailyAlarmCheckCondition().listExtractConditionWorkRecork(self.tabCheckCondition.listWorkRecordExtractingConditions());
                data.dailyAlarmCheckCondition().listFixedExtractConditionWorkRecord(self.tabFixedCondition.listFixedConditionWorkRecord());
            }
            if (data.category() == model.CATEGORY.SCHEDULE_4_WEEK) {
                data.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition(self.tabCheckCondition.schedule4WeekCheckCondition());
            }

            let command: any = ko.toJS(data);
            //$(".ntsDateRange_Component").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                service.registerData(command).done(function() {
                    self.startPage(data.code()).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                        });
                    });
                }).fail(error => {
                    alertError({ messageId: error.messageId });
                }).always(() => {
                    block.clear();
                });
            }
        }

        deleteAlarmCheckCondition() {
            let self = this, data: model.AlarmCheckConditionByCategory = self.selectedAlarmCheckCondition();

            let command: any = ko.toJS(data);

            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let indexItemDelete = _.findIndex(self.listAlarmCheckCondition(), (item: model.AlarmCheckConditionByCategory) => { return item.code() == data.code(); });
                service.deleteData(command).done(function() {
                    self.startPage().done(() => {
                        self.listAlarmCheckCondition.remove(function(item) { return item.code() == data.code(); });
                        if (self.listAlarmCheckCondition().length == 0) {
                            self.createNewAlarmCheckCondition();
                        } else {
                            if (indexItemDelete == self.listAlarmCheckCondition().length) {
                                self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[indexItemDelete - 1].code());
                            } else {
                                self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[indexItemDelete].code());
                            }
                        }
                        info({ messageId: "Msg_16" }).then(() => {
                            //block.clear();
                        });
                    });
                }).fail(error => {
                    alertError({ messageId: error.messageId });
                }).always(() => {
                    block.clear();
                });

            }).ifCancel(() => {
            });
        }

        openKAL003dDialog() {
            let self = this;
            modal("/view/kal/003/d/index.xhtml").onClosed(() => {
                var output = getShared("outputKAL003d");
                if (output) {
                    self.selectedCategory(output);
                }
            });
        }

        private switchCategory(category: number) {
            let self = this;
            block.invisible();
            self.tabCheckCondition.category(category);
            self.startPage().always(() => {
                block.clear();
            });
        }

    }

}