// TreeGrid Node
module qpp014.j {
    export class ScreenModel {
        //radiogroup
        selectedId_J_SEL_001: KnockoutObservable<number>;
        itemList_J_SEL_001: KnockoutObservableArray<BoxModel_J_SEL_001>;
        //combobox
        //J_SEL_002
        itemList_J_SEL_002: KnockoutObservableArray<ItemModel_J_SEL_002>;
        selectedCode_J_SEL_002: KnockoutObservable<ItemModel_J_SEL_002>;
        //J_SEL_003
        itemList_J_SEL_003: KnockoutObservableArray<ItemModel_J_SEL_002>;
        selectedCode_J_SEL_003: KnockoutObservable<ItemModel_J_SEL_002>;
        //gridview
        items_J_LST_001: KnockoutObservableArray<ItemModel_J_LST_001>;
        currentCode_J_LST_001: KnockoutObservable<any>;
        currentCode_J_SEL_004: KnockoutObservable<any>;
        currentProcessingYm: any;
        dateOfPayment: any;
        isEnable: KnockoutObservable<boolean>;
        isHidden: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            self.itemList_J_SEL_001 = ko.observableArray([
                new BoxModel_J_SEL_001(0, '銀行集計'),
                new BoxModel_J_SEL_001(1, '明細出力')
            ]);
            self.selectedId_J_SEL_001 = ko.observable(0);
            self.itemList_J_SEL_002 = ko.observableArray([
                new ItemModel_J_SEL_002('振込先順 '),
                new ItemModel_J_SEL_002('個人　コード順'),
            ]);
            self.selectedCode_J_SEL_002 = ko.observable(self.itemList_J_SEL_002()[0]);
            self.itemList_J_SEL_003 = ko.observableArray([
                new ItemModel_J_SEL_002('漢字出力'),
                new ItemModel_J_SEL_002('カナ出力'),
            ]);
            self.selectedCode_J_SEL_003 = ko.observable(self.itemList_J_SEL_003()[0]);
            self.isEnable = ko.observable(false);
            //gridview
            //LST_001
            self.items_J_LST_001 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_J_LST_001.push(new ItemModel_J_LST_001('00' + i, '基本給 ' + i, 'des ' + i));
            }
            self.currentCode_J_LST_001 = ko.observable();
            self.currentCode_J_SEL_004 = ko.observable(1);
            self.currentProcessingYm = nts.uk.time.parseYearMonth(nts.uk.ui.windows.getShared("data").currentProcessingYm).format() + "(" +
                nts.uk.time.yearmonthInJapanEmpire(nts.uk.time.parseYearMonth(nts.uk.ui.windows.getShared("data").currentProcessingYm).format()) + ")";
            self.dateOfPayment = ko.observable(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM/DD") +
                "(" + nts.uk.time.yearmonthInJapanEmpire(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM")).toString() +
                moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("DD") + "日)");
            self.isEnable = ko.computed(function() {
                return self.selectedId_J_SEL_001() == 0 ? false : true;
            });
            if (nts.uk.ui.windows.getShared("sparePayAtr") != 3) {
                $('#J_SEL_004').css('display','none');
                $('#J_LBL_009').css('display','none');
            } else {
                $('#J_SEL_004').css('display','');
                $('#J_LBL_009').css('display','');
            }
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
    export class BoxModel_J_SEL_001 {
        id: number;
        name: string;
        constructor(id, name) {
            let self = this;
            self.id = id;
            self.name = name;
        }
    }

    export class ItemModel_J_SEL_002 {
        name: string;

        constructor(name: string) {
            this.name = name;
        }
    }

    export class ItemModel_J_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
};
