module nts.uk.at.view.kmk007.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode1: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        selectedCode3: KnockoutObservable<string>;
        selectedCode4: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.enable = ko.observable(true);
            self.selectedRuleCode = ko.observable(1);
            for (let i = 1; i < 5; i++) {
                self.items.push(new ItemModel('00' + i, '基本給'));
            }

            self.itemList = ko.observableArray([
                new ItemModel('1', '出勤'),
                new ItemModel('2', '休日'),
                new ItemModel('3', '年休'),
                new ItemModel('4', '積立年休'),
                new ItemModel('5', '特別休暇'),
                new ItemModel('6', '欠勤'),
                new ItemModel('7', '代休'),
                new ItemModel('8', '振出'),
                new ItemModel('9', '振休'),
                new ItemModel('10', '時間消化休暇'),
                new ItemModel('11', '連続勤務'),
                new ItemModel('12', '休日出勤'),
                new ItemModel('13', '休職'),
                new ItemModel('14', '休業')
            ]);

            self.selectedCode1 = ko.observable('1');
            self.selectedCode2 = ko.observable('1');
            self.selectedCode3 = ko.observable('1');
            self.selectedCode4 = ko.observable('1');
//            self.selectedCode.subscribe(function(newCode) {
//                if (newCode == '2') {
//                    //$('div[id^="duty-type-set-"]').addClass("display-none");
//                    //$('#duty-type-set-2').removeClass("display-none");
//                    
//                } else {
//                    $('div[id^="duty-type-set-"]').removeClass("display-none");
//                }
//            });

            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMK007_19') },
                { code: '2', name: nts.uk.resource.getText('KMK007_20') }
            ]);
            

            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMK007_7'), key: 'code', width: 80 },
                { headerText: nts.uk.resource.getText('KMK007_8'), key: 'name', width: 150 },
                { headerText: '廃止', key: 'icon', width: 50 }
            ]);

            self.currentCode = ko.observable();

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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