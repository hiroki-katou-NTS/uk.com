/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />

module nts.uk.com.cmf001.c {

    @bean()
    export class ViewModel extends ko.ViewModel {

        items: KnockoutObservableArray<MappingItem> = ko.observableArray([]);

        itemsColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: "NO", key: "itemNo", width: 0, hidden: true },
            { headerText: "名称", key: "name", width: 200 },
            { headerText: "", key: "configured", width: 40 },
        ]);

        selectedItemNo: KnockoutObservable<number> = ko.observable();

        constructor() {
            super();

            this.items.push(new MappingItem(1, "カード番号", true));
            this.items.push(new MappingItem(2, "年月日", false));
            this.items.push(new MappingItem(3, "時分", false));
            this.items.push(new MappingItem(4, "打刻区分", false));
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
    class Configuration {

    }
}