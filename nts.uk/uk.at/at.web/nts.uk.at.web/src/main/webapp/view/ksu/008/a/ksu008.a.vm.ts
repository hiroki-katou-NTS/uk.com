module nts.uk.at.ksu008.a {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {

        options: any;
        treeGrid: any;
        componentName: KnockoutObservable<string> = ko.observable('workplace-group');

        multiple: KnockoutObservable<boolean> = ko.observable(false);
        showBaseDate: KnockoutObservable<boolean> = ko.observable(false);
        date: KnockoutObservable<string> = ko.observable(new Date().toISOString());
        unit: KnockoutObservable<number> = ko.observable(0);

        selectedWkpId: KnockoutObservable<string> = ko.observable(null);
        selectedWkpGroupId: KnockoutObservable<string> = ko.observable(null);

        selectedWkpIds: KnockoutObservableArray<string> = ko.observableArray([]);
        selectedWkpGroupIds: KnockoutObservableArray<string> = ko.observableArray([]);

        result: KnockoutObservable<string> = ko.observable('');

        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        dateTime: KnockoutObservable<string>;
        model: Model = new Model();
        comBOItemList: KnockoutObservableArray<ItemModel>;
        comA731: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        cssRangerYMD: KnockoutObservable<any>;

        constructor() {
            super();
            const self = this;
            self.initKCP011();
            self.multiple.subscribe(value => {
                self.initKCP011();
                self.componentName.valueHasMutated();
            });
            self.unit.subscribe(value => {
                if (value == 1 && $("#workplace-group-pannel input.ntsSearchBox").width() == 0)
                    $("#workplace-group-pannel input.ntsSearchBox").css("width", "auto");
            });

            self.itemList = ko.observableArray([
                new BoxModel(1, self.$i18n("KSU008_9")), //A3_2_1
                new BoxModel(2, self.$i18n("KSU008_10")),//A3_2_2
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);

            self.dateTime = ko.observable(new Date().toISOString());
            self.dateTime.subscribe((data) => {
                if (!data) {
                    self.dateTime(new Date().toISOString());
                }
            });
            //TODO A5_2_1,A5_2_2,A5_2_3
            self.comBOItemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            self.comA731 = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当')
            ]);

            self.selectedCode = ko.observable('1');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            self.roundingRules = ko.observableArray([
                {code: 1, name: self.$i18n("KSU008_18")},//A6_2_1
                {code: 2, name: self.$i18n("KSU008_19")},//A6_2_2
                {code: 3, name: self.$i18n("KSU008_20")}//A6_2_3
            ]);
            self.selectedRuleCode = ko.observable(1);

            let data = {
                2000: {
                    1: [{11: "round-green"}, {12: "round-orange"}, {15: "rect-pink"}],
                    3: [{1: "round-green"}, {2: "round-purple"}, 3]
                },
                2002: {
                    1: [{11: "round-green"}, {12: "round-green"}, {15: "round-green"}],
                    3: [{1: "round-green"}, {2: "round-green"}, {3: "round-green"}]
                }
            };
            self.cssRangerYMD = ko.observable(data);

        }

        setDefault() {
            var self = this;
            nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }

        mounted() {
            const vm = this;
        }

        initKCP011() {
            const self = this;
            self.options = {
                currentIds: self.selectedWkpGroupIds,
                multiple: true,
                tabindex: 2,
                isAlreadySetting: false,
                showEmptyItem: false,
                reloadData: ko.observable(''),
                height: 373,
                selectedMode: 1
            };
        }

    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class Model {
        dispPeriod: KnockoutObservable<any>;
        a733: KnockoutObservable<string>;
        a744: KnockoutObservable<string>;
        a746: KnockoutObservable<string>;

        constructor() {
            var self = this
            self.dispPeriod = ko.observable('31/12/1994~31/12/2021')
            self.a733 = ko.observable('#ffff00');
            self.a744 = ko.observable('#00ff00');
            self.a746 = ko.observable('#0000ff');
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}


