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
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

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
//        //tab fixed check condition
//        tabFixedConMonthly: tab.FixedCheckConMonthlyTab;
        
        
        selectCategoryFromDialog: KnockoutObservable<boolean> = ko.observable(false);
        afterDelete: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            var self = this;

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL003_15'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL003_51'), content: '.tab-content-2', enable: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.DAILY 
                    }, this), visible: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.DAILY 
                    }, this) 
                },
                { id: 'tab-3', title: getText('KAL003_16'), content: '.tab-content-3', enable: ko.computed(() => {
                     return self.selectedCategory() == model.CATEGORY.DAILY
                      || self.selectedCategory() == model.CATEGORY.SCHEDULE_4_WEEK }, this), visible: ko.computed(() => {
                           return self.selectedCategory() == model.CATEGORY.DAILY 
                           || self.selectedCategory() == model.CATEGORY.SCHEDULE_4_WEEK 
                      }, this) 
                },
                { id: 'tab-4', title: getText('KAL003_67'), content: '.tab-content-4', 
                    enable: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.DAILY 
                    }, this), visible: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.DAILY 
                    }, this) 
                },
                { id: 'tab-5', title: getText('KAL003_67'), content: '.tab-content-5', 
                    enable: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.MONTHLY 
                    }, this), visible: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.MONTHLY 
                    }, this) 
                },
                { id: 'tab-6', title: getText('KAL003_67'), content: '.tab-content-6', 
                    enable: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.MONTHLY 
                    }, this), visible: ko.computed(() => { 
                        return self.selectedCategory() == model.CATEGORY.MONTHLY 
                    }, this) 
                }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.tabScopeCheck = new tab.ScopeCheckTab();
            self.tabDailyErrorAlarm = new tab.DailyPerformanceTab();
            self.tabCheckCondition = new tab.CheckConditionTab(self.selectedCategory());
            self.tabFixedCondition = new tab.FixedCheckConditionTab();
//            self.tabFixedConMonthly = new tab.FixedCheckConMonthlyTab();

            self.selectedCategory.subscribe((data) => {
                self.switchCategory(data);
            });

            self.selectedAlarmCheckCondition = ko.observable(new model.AlarmCheckConditionByCategory('', '', new model.ItemModel(0, ""), [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));

            self.selectedAlarmCheckConditionCode.subscribe(function(data: any) {
                self.selectCondition(data);
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
                        let item = new model.AlarmCheckConditionByCategory(acc.code, acc.name, category, [], null);
                        return item;
                    });

                    _.each(_accList, acc => self.listAlarmCheckCondition.push(acc));
                    
                    if (self.selectCategoryFromDialog()) {
                        self.createNewAlarmCheckCondition();
                    } else {
                        if (code) {
                            self.selectedAlarmCheckConditionCode(code);
                        } else {
                            self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[0].code());
                        }
                        self.selectedAlarmCheckConditionCode.valueHasMutated();
                    }
                    
                } else {
                    self.createNewAlarmCheckCondition();
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        private createNewAlarmCheckCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedAlarmCheckConditionCode('');
            let category = _.find(ko.toJS(self.cbbItemList), (x: model.ItemModel) => x.code == self.selectedCategory());
            self.selectedAlarmCheckCondition(new model.AlarmCheckConditionByCategory('', '', category, [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));
            self.selectedDataCondition(model.DATA_CONDITION_TO_EXTRACT.ALL);
            self.tabScopeCheck.targetCondition(new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], []));
            self.tabCheckCondition.category(self.selectedCategory());
            
            if (self.selectedCategory() == model.CATEGORY.DAILY) {
                self.tabCheckCondition.listWorkRecordExtractingConditions([]);
                self.tabDailyErrorAlarm.currentCodeList([]);
                self.tabDailyErrorAlarm.addApplication(false);
                
                service.getAllFixedConData().done((data: Array<any>) => {
                    if (data && data.length) {
                        let _list: Array<model.FixedConditionWorkRecord> = _.map(data, acc => {
                            return new model.FixedConditionWorkRecord({ dailyAlarmConID: "", checkName: acc.fixConWorkRecordName, fixConWorkRecordNo: acc.fixConWorkRecordNo, message: acc.message, useAtr: false });
                        });
                        self.tabFixedCondition.listFixedConditionWorkRecord(_list);
                    }
                });
            }
            
            if (self.selectedCategory() == model.CATEGORY.SCHEDULE_4_WEEK) {
                self.tabCheckCondition.schedule4WeekCheckCondition(0);
            }
            
            self.screenMode(model.SCREEN_MODE.NEW);
            if (self.afterDelete()) {
                self.afterDelete(false);
            } else {
                $("#A3_2").focus();
            }

        }

        registerAlarmCheckCondition() {
            let self = this,
                data: model.AlarmCheckConditionByCategory = new model.AlarmCheckConditionByCategory(self.selectedAlarmCheckCondition().code(), self.selectedAlarmCheckCondition().name(), new model.ItemModel(self.selectedAlarmCheckCondition().category(), self.selectedAlarmCheckCondition().displayCategory), self.selectedAlarmCheckCondition().availableRoles(), self.selectedAlarmCheckCondition().targetCondition());
            
            $(".nts-input").trigger("validate");
            if ($(".nts-input").ntsError("hasError")){ 
                return;
            }
            
            //block.invisible();
            data.targetCondition(self.tabScopeCheck.targetCondition());
            data.action(self.screenMode());
            if (data.category() == model.CATEGORY.DAILY) {
                data.dailyAlarmCheckCondition().conditionToExtractDaily(self.selectedDataCondition());
                data.dailyAlarmCheckCondition().addApplication(self.tabDailyErrorAlarm.addApplication());
                data.dailyAlarmCheckCondition().listErrorAlarmCode(self.tabDailyErrorAlarm.currentCodeList());
                data.dailyAlarmCheckCondition().listExtractConditionWorkRecork(self.tabCheckCondition.listWorkRecordExtractingConditions());
                data.dailyAlarmCheckCondition().listFixedExtractConditionWorkRecord(self.tabFixedCondition.listFixedConditionWorkRecord());
                data.dailyAlarmCheckCondition().listExtractConditionWorkRecork().forEach((x: model.WorkRecordExtractingCondition) => {
                    x.errorAlarmCondition().alCheckTargetCondition().filterByBusinessType = data.targetCondition().filterByBusinessType();
                    x.errorAlarmCondition().alCheckTargetCondition().filterByJobTitle = data.targetCondition().filterByJobTitle();
                    x.errorAlarmCondition().alCheckTargetCondition().filterByEmployment = data.targetCondition().filterByEmployment();
                    x.errorAlarmCondition().alCheckTargetCondition().filterByClassification = data.targetCondition().filterByClassification();
                    x.errorAlarmCondition().alCheckTargetCondition().lstBusinessTypeCode = data.targetCondition().targetBusinessType();
                    x.errorAlarmCondition().alCheckTargetCondition().lstJobTitleId = data.targetCondition().targetJobTitle();
                    x.errorAlarmCondition().alCheckTargetCondition().lstEmploymentCode = data.targetCondition().targetEmployment();
                    x.errorAlarmCondition().alCheckTargetCondition().lstClassificationCode = data.targetCondition().targetClassification();
                });
            }
            if (data.category() == model.CATEGORY.SCHEDULE_4_WEEK) {
                data.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition(self.tabCheckCondition.schedule4WeekCheckCondition());
            }

            let command: any = ko.toJS(data);
            $("#A3_4").trigger("validate");
            $("#check-condition-table .nts-editor.nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                service.registerData(command).done(function() {
                    self.startPage(data.code()).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            if (self.screenMode() == nts.uk.at.view.kal003.share.model.SCREEN_MODE.UPDATE) {
                                $("#A3_4").focus();
                            } else {
                                $("#A3_2").focus();
                            }
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        deleteAlarmCheckCondition() {
            let self = this, data: model.AlarmCheckConditionByCategory = self.selectedAlarmCheckCondition();
            nts.uk.ui.errors.clearAll();
            let command: any = ko.toJS(data);

            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let indexItemDelete = _.findIndex(self.listAlarmCheckCondition(), (item: model.AlarmCheckConditionByCategory) => { return item.code() == data.code(); });
                service.deleteData(command).done(function() {
                    self.startPage().done(() => {
                        self.listAlarmCheckCondition.remove(function(item) { return item.code() == data.code(); });
                        if (self.listAlarmCheckCondition().length == 0) {
                            self.afterDelete(true);
                            self.createNewAlarmCheckCondition();
                        } else {
                            if (indexItemDelete == self.listAlarmCheckCondition().length) {
                                self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[indexItemDelete - 1].code());
                            } else {
                                self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[indexItemDelete].code());
                            }
                        }
                        info({ messageId: "Msg_16" }).then(() => {
                            if (self.screenMode() == nts.uk.at.view.kal003.share.model.SCREEN_MODE.UPDATE) {
                                $("#A3_4").focus();
                            } else {
                                $("#A3_2").focus();
                            }
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });

            }).ifNo(() => {
            });
        }

        openKAL003dDialog() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            modal("/view/kal/003/d/index.xhtml").onClosed(() => {
                var output = getShared("outputKAL003d");
                if (!nts.uk.util.isNullOrUndefined(output)) {
                    self.selectCategoryFromDialog(true);
                    if (self.selectedCategory() != output)
                        self.selectedCategory(output);
                    else
                        self.selectedCategory.valueHasMutated();
                }
            });
        }

        private switchCategory(category: number) {
            let self = this;
            self.tabCheckCondition.category(category);
            self.startPage().done(() => {
                self.selectCategoryFromDialog(false);
            });
        }
        
        private selectCondition(data) {
            let self = this;
            if (data) {
                nts.uk.ui.errors.clearAll();
                block.invisible();
                service.getOneData(self.selectedCategory(), data).done(function(result: any) {
                    if (result) {
                        let category = _.find(ko.toJS(self.cbbItemList), (x: model.ItemModel) => x.code == result.category);
                        let item = new model.AlarmCheckConditionByCategory(result.code, result.name, category, result.availableRoles, new model.AlarmCheckTargetCondition(result.targetCondition.filterByEmployment, result.targetCondition.filterByClassification, result.targetCondition.filterByJobTitle, result.targetCondition.filterByBusinessType, result.targetCondition.targetEmployment, result.targetCondition.targetClassification, result.targetCondition.targetJobTitle, result.targetCondition.targetBusinessType));
                        let _fixedList: Array<model.FixedConditionWorkRecord> = _.map(result.dailyAlarmCheckCondition.listFixedExtractConditionWorkRecord, (fix: model.IFixedConditionWorkRecord) => { return new model.FixedConditionWorkRecord(fix) });
                        let _checkList: Array<model.WorkRecordExtractingCondition> = _.map(result.dailyAlarmCheckCondition.listExtractConditionWorkRecork, (c: model.IWorkRecordExtractingCondition) => {return shareutils.convertTransferDataToWorkRecordExtractingCondition(c)});
                        item.dailyAlarmCheckCondition(new model.DailyAlarmCheckCondition(result.dailyAlarmCheckCondition.conditionToExtractDaily, result.dailyAlarmCheckCondition.addApplication, result.dailyAlarmCheckCondition.listErrorAlarmCode, _checkList, _fixedList));
                        item.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition(result.schedule4WeekCondition);
                        
                        self.selectedAlarmCheckCondition(item);
                        self.tabScopeCheck.targetCondition(item.targetCondition());
                        if (item.category() == model.CATEGORY.SCHEDULE_4_WEEK) {
                            self.tabCheckCondition.schedule4WeekCheckCondition(item.schedule4WeekAlarmCheckCondition().schedule4WeekCheckCondition());
                        }
                        
                        if (item.category() == model.CATEGORY.DAILY) {
                            self.tabCheckCondition.listWorkRecordExtractingConditions(item.dailyAlarmCheckCondition().listExtractConditionWorkRecork());
                            self.selectedDataCondition(item.dailyAlarmCheckCondition().conditionToExtractDaily());
                            self.tabFixedCondition.listFixedConditionWorkRecord(item.dailyAlarmCheckCondition().listFixedExtractConditionWorkRecord());
                            self.tabDailyErrorAlarm.currentCodeList(item.dailyAlarmCheckCondition().listErrorAlarmCode());
                            self.tabDailyErrorAlarm.addApplication(item.dailyAlarmCheckCondition().addApplication());
                        }
                        
                        self.screenMode(model.SCREEN_MODE.UPDATE);
                        $("#A3_4").focus();                    
                    }
                }).fail(function(error) {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

    }

}