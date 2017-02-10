// TreeGrid Node
module qpp014.g {
    export class ScreenModel {
        //DatePicker
        date_G_INP_001: KnockoutObservable<Date>;
        //combobox
        //G_SEL_001
        itemList_G_SEL_001: KnockoutObservableArray<ItemModel_G_SEL_001>;
        selectedCode_G_SEL_001: KnockoutObservable<string>;
        //G_SEL_002
        itemList_G_SEL_002: KnockoutObservableArray<ItemModel_G_SEL_002>;
        selectedCode_G_SEL_002: KnockoutObservable<string>;
        //switch
        roundingRules_G_SEL_001: KnockoutObservableArray<any>;
        selectedRuleCode_G_SEL_001: any;
        //  numbereditor
        numbereditor_G_INP_002: any;
        constructor() {
            let self = this;
            //DatePicker
            self.date_G_INP_001 = ko.observable(new Date('2016/12/01'));
            //combobox
            //G_SEL_001
            self.itemList_G_SEL_001 = ko.observableArray([
                new ItemModel_G_SEL_001('基本給1', '基本給'),
                new ItemModel_G_SEL_001('基本給2', '役職手当'),
                new ItemModel_G_SEL_001('0003', '基本給')
            ]);
            self.selectedCode_G_SEL_001 = ko.observable('0002');
            //G_SEL_002
            self.itemList_G_SEL_002 = ko.observableArray([
                new ItemModel_G_SEL_002('基本給1', '基本給'),
                new ItemModel_G_SEL_002('基本給2', '役職手当'),
                new ItemModel_G_SEL_002('0003', '基本給')
            ]);
            self.selectedCode_G_SEL_002 = ko.observable('0002');
            //switch
            //SEL_003
            self.roundingRules_G_SEL_003 = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' }
            ]);
            self.selectedRuleCode_G_SEL_003 = ko.observable(1);
            //numbereditor
            self.numbereditor_G_INP_002 = {
                value: ko.observable(12),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    placeholder: "Placeholder for number editor",
                    width: "",
                    textalign: "left"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }
    export class ItemModel_G_SEL_001 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_G_SEL_002 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

};
