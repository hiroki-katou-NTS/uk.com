module kdl024.a.viewmodel {
    export class ScreenModel {
        contraint: Array<string>;
        items: KnockoutObservableArray<Item>;
        currentCode: KnockoutObservable<number>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        //Switch button
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //Combo box
        itemListCbb: KnockoutObservableArray<ItemModelCbb>;
        itemNameCbb: KnockoutObservable<string>;
        currentCodeCbb: KnockoutObservable<number>
        selectedCodeCbb: KnockoutObservable<string>;
        isEnableCbb: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.constraint = ['ResidenceCode', 'PersonId'];
            //Switch button 
            self.roundingRules = ko.observableArray([
                { unitId: '1', unitName: '日別' },
                { unitId: '2', unitName: '時間帯別' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //Combobox
            self.itemListCbb = ko.observableArray([
                new ItemModelCbb('時間'),
                new ItemModelCbb('人数'),
                new ItemModelCbb('金額'),
                new ItemModelCbb('数値'),
                new ItemModelCbb('単価')
            ]);
            //Defaut value 
            self.selectedCodeCbb = ko.observable('時間');
            self.isEnableCbb = ko.observable(true);
            //grid list
            self.currentCode = ko.observable('0');
            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 40 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);

            this.items = ko.observableArray([]);
            for (let i = 1; i < 20; i++) {
                this.items.push(new Item('00' + i, '時間実績'));
            }
            self.start();
        }
        //start
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //get list budget
            service.getListExternalBudget().done(function(lstBudget: Array<Item>) {
                console.log(lstBudget);
                debugger;
                if (lstBuget.length > 0) {
                    alert('pass');
                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                alert(res);
            });
            return dfd.promise();
        }
    }

    //item LIST Budget
    class Item {
        code: string;
        name: string;
        attribute: number;
        unit: number;
        constructor(code: string, name: string, attribute: number, unit: number) {
            this.code = code;
            this.name = name;
            this.attribute = attribute;
            this.unit = unit;
        }
    }
    //item Combo Box
    class ItemModelCbb {
        nameCbb: string;
        labelCbb: string;
        constructor(nameCbb: string) {
            this.nameCbb = nameCbb;
            this.labelCbb = nameCbb;
        }
    }

}