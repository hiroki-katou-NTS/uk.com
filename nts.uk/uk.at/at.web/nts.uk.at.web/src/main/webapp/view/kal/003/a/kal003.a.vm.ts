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

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        cbbItemList: KnockoutObservableArray<model.ItemModel>;
        selectedCategory: KnockoutObservable<number>;

        radioItemList: KnockoutObservableArray<model.ItemModel>;
        selectedDataCondition: KnockoutObservable<number>;
        
        screenMode: KnockoutObservable<number>;

        listAlarmCheckCondition: KnockoutObservableArray<model.AlarmCheckConditionByCategory>;
        selectedAlarmCheckConditionCode: KnockoutObservable<string>;
        selectedAlarmCheckCondition: KnockoutObservable<model.AlarmCheckConditionByCategory>;

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL003_15'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL003_16'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.cbbItemList = ko.observableArray([
                new model.ItemModel(0, 'スケジュール日次'),
                new model.ItemModel(1, 'スケジュール週次'),
                new model.ItemModel(2, 'スケジュール4週'),
                new model.ItemModel(3, 'スケジュール月次'),
                new model.ItemModel(4, 'スケジュール年間'),
                new model.ItemModel(5, '日次'),
                new model.ItemModel(6, '週次'),
                new model.ItemModel(7, '月次'),
                new model.ItemModel(8, '申請承認'),
                new model.ItemModel(9, '複数月'),
                new model.ItemModel(10, '任意期間'),
                new model.ItemModel(11, '年休付与用出勤率'),
                new model.ItemModel(12, '３６協定'),
                new model.ItemModel(13, '工数チェック')
            ]);
            self.selectedCategory = ko.observable(model.CATEGORY.DAILY);

            self.radioItemList = ko.observableArray([
                new model.ItemModel(0, '全て'),
                new model.ItemModel(1, '確認済のデータ'),
                new model.ItemModel(2, '未確認のデータ')
            ]);
            self.selectedDataCondition = ko.observable(model.DATA_CONDITION_TO_EXTRACT.ALL);

            self.listAlarmCheckCondition = ko.observableArray([]);
            self.selectedAlarmCheckConditionCode = ko.observable('');
            self.selectedAlarmCheckCondition = ko.observable(new model.AlarmCheckConditionByCategory('', '', 'category1', [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));

            self.selectedAlarmCheckConditionCode.subscribe(function(data: any) {
                if (data) {
                    let item = _.find(self.listAlarmCheckCondition(), (x: model.AlarmCheckConditionByCategory) => x.code() == data);
                    if (item) {
                        self.selectedAlarmCheckCondition(item);
                        self.screenMode(model.SCREEN_MODE.UPDATE);
                    }
                }
            });
            
            self.selectedCategory.subscribe((data) => {
                if (data == model.CATEGORY.DAILY){
                    $("#AA1").show();
                } else {
                    $("#AA1").hide();
                    info({ message: "Not cover this time" }).then(() => {
                        self.selectedCategory(model.CATEGORY.DAILY);
                    });
                }
            });
            
            self.selectedDataCondition.subscribe((data) => {
                self.selectedAlarmCheckCondition().conditionToExtractDaily(data);
            });
            
            self.screenMode = ko.observable(model.SCREEN_MODE.UPDATE);
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            self.listAlarmCheckCondition([
                new model.AlarmCheckConditionByCategory('001', 'name1', 'category1', [], new model.AlarmCheckTargetCondition(false, true, false, false, [], ['cls01', 'cls02'], [], [])),
                new model.AlarmCheckConditionByCategory('002', 'name2', 'category1', [], new model.AlarmCheckTargetCondition(true, false, false, false, ['emp01', 'emp02'], [], [], [])),
                new model.AlarmCheckConditionByCategory('003', 'name3', 'category1', [], new model.AlarmCheckTargetCondition(false, false, false, true, [], [], [], ['bustype01', 'bustype02'])),
                new model.AlarmCheckConditionByCategory('004', 'name4', 'category1', [], new model.AlarmCheckTargetCondition(false, false, true, false, [], [], ['job01', 'job02'], []))
            ]);
            self.selectedAlarmCheckConditionCode(self.listAlarmCheckCondition()[1].code());
            dfd.resolve();
            block.clear();
            return dfd.promise();
        }

        createNewAlarmCheckCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedAlarmCheckConditionCode('');
            self.selectedAlarmCheckCondition(new model.AlarmCheckConditionByCategory('', '', 'category1', [], new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], [])));
            self.selectedDataCondition(model.DATA_CONDITION_TO_EXTRACT.ALL);
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        registerAlarmCheckCondition() {
            let self = this;
            self.listAlarmCheckCondition.push(self.selectedAlarmCheckCondition());
        }

        deleteAlarmCheckCondition() {
            let self = this;

        }
        test(): void {
            console.log("success!");
            modal("../../004/b/index.xhtml").onClosed(() => {
                console.log("success!");
            });
        }
    }

}