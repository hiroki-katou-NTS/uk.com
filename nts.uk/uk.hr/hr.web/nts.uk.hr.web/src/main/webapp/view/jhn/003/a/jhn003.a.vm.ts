module jhn003.a.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import text = nts.uk.resource.getText;

    export class ViewModel {

        searchInfo: KnockoutObservable<SearchInfo> = ko.observable(new SearchInfo());

        constructor() {
            let self = this;
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        approvalAll() {

        }

        search() {

        }
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        className?: string;
        personInfoCategoryID?: string;
        layoutItemType: IT_CLA_TYPE;
        listItemDf: Array<IItemDefinition>;
    }

    class SearchInfo {
        appDate: KnockoutObservable<any> = ko.observable({});
        inputName: KnockoutObservable<string> = ko.observable('');
        approvalReport: KnockoutObservable<boolean> = ko.observable(true);
        reportItems: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: null, name: "" },
            { code: "0", name: "育児休業申請届" },
            { code: "1", name: "育児短時間勤務申請届" },
            { code: "2", name: "介護休暇届" },
            { code: "3", name: "介護休業申請届" }
        ]);
        reportId: KnockoutObservable<string> = ko.observable('');
        approvalItems: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: null, name: "" },
            { code: "0", name: text("JHN003_A222_4_1_1") },
            { code: "1", name: text("JHN003_A222_4_1_2") },
            { code: "2", name: text("JHN003_A222_4_1_3") },
            { code: "3", name: text("JHN003_A222_4_1_4") },
            { code: "4", name: text("JHN003_A222_4_1_5") },
            { code: "5", name: text("JHN003_A222_4_1_6") }
        ]);
        approvalStatus: KnockoutObservable<string> = ko.observable('');

        constructor() {
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

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }
}