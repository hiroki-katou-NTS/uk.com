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
        //check condition tab
        tabCheckCondition: tab.CheckConditionTab;

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
            self.tabCheckCondition = new tab.CheckConditionTab(self.selectedCategory());

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
                        self.tabCheckCondition.listWorkRecordExtractingConditions(item.dailyAlarmCheckCondition().listWorkRecordExtractingConditions());
                        self.tabCheckCondition.schedule4WeekCheckCondition(item.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition());
                        self.selectedDataCondition(item.dailyAlarmCheckCondition().conditionToExtractDaily());
                        self.screenMode(model.SCREEN_MODE.UPDATE);
                    }
                }
            });

        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();

            service.getAllData(self.selectedCategory()).done(function(data: Array<any>) {
                if (data && data.length) {

                } else {
                    let category = _.find(ko.toJS(self.cbbItemList), (x: model.ItemModel) => x.code == self.selectedCategory());
                    self.listAlarmCheckCondition([
                        new model.AlarmCheckConditionByCategory('001', 'name1', category, [], new model.AlarmCheckTargetCondition(false, true, false, false, [], ['cls01', 'cls02'], [], [])),
                        new model.AlarmCheckConditionByCategory('002', 'name2', category, [], new model.AlarmCheckTargetCondition(true, false, false, false, ['emp01', 'emp02'], [], [], [])),
                        new model.AlarmCheckConditionByCategory('003', 'name3', category, [], new model.AlarmCheckTargetCondition(false, false, false, true, [], [], [], ['bustype01', 'bustype02'])),
                        new model.AlarmCheckConditionByCategory('004', 'name4', category, [], new model.AlarmCheckTargetCondition(false, false, true, false, [], [], ['job01', 'job02'], []))
                    ]);
                    self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[0].code());
                    self.selectedAlarmCheckConditionCode.valueHasMutated();
                    self.screenMode(model.SCREEN_MODE.UPDATE);
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
            //self.listAlarmCheckCondition.push(self.selectedAlarmCheckCondition());
            let self = this,
                data: model.AlarmCheckConditionByCategory = new model.AlarmCheckConditionByCategory(self.selectedAlarmCheckCondition().code(), self.selectedAlarmCheckCondition().name(), new model.ItemModel(self.selectedAlarmCheckCondition().category(), self.selectedAlarmCheckCondition().displayCategory), self.selectedAlarmCheckCondition().availableRoles(), self.selectedAlarmCheckCondition().targetCondition());
            data.targetCondition(self.tabScopeCheck.targetCondition());
            if (data.category() == model.CATEGORY.DAILY) {
                data.dailyAlarmCheckCondition().conditionToExtractDaily(self.selectedDataCondition());
                data.dailyAlarmCheckCondition().listWorkRecordExtractingConditions(self.tabCheckCondition.listWorkRecordExtractingConditions());
            }
            if (data.category() == model.CATEGORY.SCHEDULE_4_WEEK) {
                data.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition(self.tabCheckCondition.schedule4WeekCheckCondition());
            }

            let command: any = ko.toJS(data);
            //$(".ntsDateRange_Component").trigger("validate");
            //if (!nts.uk.ui.errors.hasError() && data.employeeId) {
            block.invisible();
            service.registerData(command).done(function() {
                let item = _.findIndex(self.listAlarmCheckCondition(), (item: model.AlarmCheckConditionByCategory) => { return item.code() == data.code(); });
                if (item >= 0) {
                    self.listAlarmCheckCondition.replace(self.listAlarmCheckCondition()[item], data);
                } else {
                    self.listAlarmCheckCondition.push(data);
                }
                info({ messageId: "Msg_15" }).then(() => {
                });

            }).fail(error => {
                alertError({ messageId: error.messageId });
            }).always(() => {
                block.clear();
            });
            //}
        }

        deleteAlarmCheckCondition() {
            let self = this, data: model.AlarmCheckConditionByCategory = self.selectedAlarmCheckCondition();

            let command: any = ko.toJS(data);

            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let indexItemDelete = _.findIndex(self.listAlarmCheckCondition(), (item: model.AlarmCheckConditionByCategory) => { return item.code() == data.code(); });
                service.deleteData(command).done(function() {
                    //self.loadRoleSetHolder(self.selectedRoleSet()).done(() => {
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
                    //});
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
            //            switch (category) {
            //                case model.CATEGORY.DAILY:
            //                    self.tabs()[1].enable(true);
            //                    self.tabs()[1].visible(true);
            //                    self.tabs()[2].enable(true);
            //                    self.tabs()[2].visible(true);
            //                    self.tabs()[3].enable(true);
            //                    self.tabs()[3].visible(true);
            //                    break;
            //                case model.CATEGORY.SCHEDULE_4_WEEK:
            //                    self.tabs()[1].enable(false);
            //                    self.tabs()[1].visible(false);
            //                    self.tabs()[2].enable(true);
            //                    self.tabs()[2].visible(true);
            //                    self.tabs()[3].enable(false);
            //                    self.tabs()[3].visible(false);
            //                    break;
            //                default:
            //                    self.tabs()[1].enable(false);
            //                    self.tabs()[1].visible(false);
            //                    self.tabs()[2].enable(false);
            //                    self.tabs()[2].visible(false);
            //                    self.tabs()[3].enable(false);
            //                    self.tabs()[3].visible(false);
            //            }
            self.startPage().always(() => {
                block.clear();
            });
        }

    }

}