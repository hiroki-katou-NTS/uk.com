/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />

module nts.uk.com.cmf001.c {

    let datasource = {
        importableItem: {
            get() {
                return {
                    name: "カード番号",
                    constraint: "半角15文字",
                    required: true,
                };
            }
        },

        itemMapping: {
            get() {
                let res = {
                    source: "CSV",
                    settingCode: "300",
                    itemNo: 1,
                    type: "string",
                    revisingValue: {
                        useSpecifyRange: true,
                        rangeOfValue: {
                            start: 1,
                            end: 4,
                        },
                        usePadding: true,
                        padding: {
                            length: 15,
                            method: 0,
                            methods: (<any>__viewContext).enums.PaddingMethod
                        },
                        useCodeConvert: true,
                        codeConvert: {
                            importWithoutSetting: true,
                            convertDetailsText: "ABC,123",
                        }
                    },
                };

                return (<any> ko).mapping.fromJS(res);
            }
        }
    }

    function renderConfiguredIcon(configured: boolean) {
        if (configured) {
            return '<i data-bind="ntsIcon: { no: 78, width: 20, height: 20 }"></i>';
        } else {
            return '';
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        items: KnockoutObservableArray<MappingItem> = ko.observableArray([]);

        itemsColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: "NO", key: "itemNo", width: 0, hidden: true },
            { headerText: "名称", key: "name", width: 200 },
            { headerText: "", key: "configured", width: 40, formatter: renderConfiguredIcon },
        ]);

        selectedItemNo: KnockoutObservable<number> = ko.observable();

        currentItem: any = ko.observable();

        constructor() {
            super();

            this.items.push(new MappingItem(1, "カード番号", true));
            this.items.push(new MappingItem(2, "年月日", false));
            this.items.push(new MappingItem(3, "時分", false));
            this.items.push(new MappingItem(4, "打刻区分", false));

            this.currentItem({
                def: datasource.importableItem.get(),
                selectedMappingType: ko.observable(1),
                mappingTypes: [
                    { code: 1, name: "CSV" },
                    { code: 2, name: "固定値" },
                ],
                csvMapping: datasource.itemMapping.get(),
                fixedMapping: ko.observable("123"),
            });
        }

        mounted() {
            $("body").removeClass("ko-cloak");
        }
    }

    /**
     * マッピング項目
     */
    class MappingItem {
        itemNo: number;
        name: string;
        configured: string;

        constructor(itemNo: number, name: string, configured: boolean) {
            this.itemNo = itemNo;
            this.name = name;
            this.configured = configured ? "✓" : "";
        }
    }

    /**
     * マッピング設定
     */
    class ReviseItem {
        settingCode: string;
        itemNo: number;
        revisingValue: StringRevise;
    }

    class StringRevise {
        useSpecifyRange: boolean;
        rangeOfValue: Range;
        usePadding: boolean;
        padding: padding;
    }

    class Range {
        start: number;
        end: number;
    }

    class padding {
        length: number;
        method: number;
    }
}