module nts.uk.at.ksu008.b {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: Model = new Model();
        b22RoundingRules: KnockoutObservableArray<any>;
        b42RoundingRules: KnockoutObservableArray<any>;
        b54RoundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        fileName: KnockoutObservable<string>;
        extensionFileList: KnockoutObservableArray<string>;
        fileId: KnockoutObservable<string>;


        constructor() {
            super();
            const self = this;
            self.b22RoundingRules = ko.observableArray([
                {code: 1, name: self.$i18n("KSU008_37")},//B2_2_1
                {code: 2, name: self.$i18n("KSU008_38")},//B2_2_12
            ]);

            self.b42RoundingRules = ko.observableArray([
                {code: 1, name: '利用する'},//B4_2_1 TODO text from enum Enum_UseAtr_Use
                {code: 0, name: "利用しない"},//B4_2_2 TODO text from enum Enum_UseAtr_NotUse
            ]);
            self.b54RoundingRules = ko.observableArray([
                {code: 1, name: self.$i18n("KSU008_51")}
            ]);
            self.selectedRuleCode = ko.observable(1);
            this.items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for(var j = 0; j < 4; j++) {
                for(var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items.push(new ItemModel(code,code,code));
                }
            }
            this.columns = ko.observableArray([
                { headerText: self.$i18n('KSU008_40'), prop: 'code', width: 100 },
                { headerText: self.$i18n('KSU008_41'), prop: 'name', width: 250 },
                { headerText: self.$i18n('KSU008_42'), prop: 'classification', width: 100 },
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            self.fileName = ko.observable("");
            self.extensionFileList = ko.observableArray(['txt', 'csv', 'TXT', 'CSV']);
            self.fileId = ko.observable(null);

        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }



        mounted() {
            const vm = this;
        }

    }

    class ItemModel {
        code: string;
        name: string;
        classification: string;
        constructor(code: string, name: string, classification: string) {
            this.code = code;
            this.name = name;
            this.classification = classification;
        }
    }

    class Model {
        selectedRuleCode:any;
        b42SelectedRuleCode:any;
        b54SelectedRuleCode:any;
        form9Code:any;
        form9Name:any;
        constructor() {
            var self = this
            self.selectedRuleCode = ko.observable(1);
            self.b42SelectedRuleCode = ko.observable(1);
            self.b54SelectedRuleCode = ko.observable(1);
            self.form9Code = ko.observable("123");
            self.form9Name = ko.observable("test Name");
        }
    }
}


