module qmm012.h.viewmodel {
    export class ScreenModel {
        //textediter
        texteditor: any;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //Checkbox
        checked: KnockoutObservable<boolean>;
        checked_003: KnockoutObservable<boolean> = ko.observable(false);
        checked_004: KnockoutObservable<boolean> = ko.observable(false);
        checked_005: KnockoutObservable<boolean> = ko.observable(false);
        checked_006: KnockoutObservable<boolean> = ko.observable(false);
        checked_007: KnockoutObservable<boolean> = ko.observable(false);
        checked_008: KnockoutObservable<boolean> = ko.observable(false);
        checked_009: KnockoutObservable<boolean> = ko.observable(false);
        checked_010: KnockoutObservable<boolean> = ko.observable(false);
        checked_011: KnockoutObservable<boolean> = ko.observable(false);
        checked_012: KnockoutObservable<boolean> = ko.observable(false);
        checked_013: KnockoutObservable<boolean> = ko.observable(false);
        checked_014: KnockoutObservable<boolean> = ko.observable(false);
        //gridlist
        gridListItems: KnockoutObservableArray<GridItemModel>;
        columns: KnockoutObservableArray<any>;
        gridListCurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        //Switch
        roundingRules_H_SEL_001: KnockoutObservableArray<any>;
        roundingRules_H_SEL_002: KnockoutObservableArray<any>;
        //001
        selectedRuleCode_H_SEL_001: KnockoutObservable<number> = ko.observable(0);
        //002
        selectedRuleCode_H_SEL_002: KnockoutObservable<number> = ko.observable(0);
        //radiogroup
        enable: KnockoutObservable<boolean> = ko.observable(true);
        //004
        RadioItemList_004: KnockoutObservableArray<any>;
        selectedId_004: KnockoutObservable<number>;
        //currencyeditor
        currencyeditor: any;
        //textarea
        textArea: KnockoutObservable<any>;
        //search box 
        filteredData: any;
        constructor() {
            var self = this;
            //textediter
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "60px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //set Switch Data
            self.roundingRules_H_SEL_001 = ko.observableArray([
                { code: 1, name: '設定する' },
                { code: 0, name: '設定しない' }
            ]);
            //005 006 007 008 009 010

            self.roundingRules_H_SEL_002 = ko.observableArray([
                { code: 1, name: 'する' },
                { code: 0, name: 'しない' }
            ]);



        }

        SubmitDialog() {
            nts.uk.ui.windows.close();
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
        }
    }
    class GridItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }

    }
    class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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


}