module nts.uk.at.view.kal003.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = kal003.share.model;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        cbbItemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<number>;

        radioItemList: KnockoutObservableArray<ItemModel>;
        selectedId: KnockoutObservable<number>;

        listAlarmCheckCondition: KnockoutObservableArray<model.AlarmCheckConditionByCategory>;
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
                new ItemModel(0, 'スケジュール日次'),
                new ItemModel(1, 'スケジュール週次'),
                new ItemModel(2, 'スケジュール4週'),
                new ItemModel(3, 'スケジュール月次'),
                new ItemModel(4, 'スケジュール年間'),
                new ItemModel(5, '日次'),
                new ItemModel(6, '週次'),
                new ItemModel(7, '月次'),
                new ItemModel(8, '申請承認'),
                new ItemModel(9, '複数月'),
                new ItemModel(10, '任意期間'),
                new ItemModel(11, '年休付与用出勤率'),
                new ItemModel(12, '３６協定'),
                new ItemModel(13, '工数チェック')
            ]);
            self.selectedCode = ko.observable(1);

            self.radioItemList = ko.observableArray([
                new ItemModel(1, '全て'),
                new ItemModel(2, '確認済のデータ'),
                new ItemModel(3, '未確認のデータ')
            ]);
            self.selectedId = ko.observable(1);

            self.listAlarmCheckCondition = ko.observableArray([
                new model.AlarmCheckConditionByCategory('001', 'name1', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], [])),
                new model.AlarmCheckConditionByCategory('002', 'name2', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], [])),
                new model.AlarmCheckConditionByCategory('003', 'name3', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], [])),
                new model.AlarmCheckConditionByCategory('004', 'name4', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], []))
            ]);
            self.selectedAlarmCheckCondition = ko.observable(new model.AlarmCheckConditionByCategory('001', 'name1', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], [])));
        }





        test(): void {
            console.log("success!");
            nts.uk.ui.windows.sub.modal("../../004/b/index.xhtml").onClosed(() => {
                console.log("success!");
            });
        }
    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}