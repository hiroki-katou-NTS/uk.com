module kdl024.a.viewmodel {
    export class ScreenModel {
        contraint: Array<string>;
        items: KnockoutObservableArray<Item>;
        currentItem: KnockoutObservable<Item>;
        currentCode: KnockoutObservable<number>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        //Switch button
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //Combobox
        itemListCbb: KnockoutObservableArray<ItemModelCbb>;
        itemNameCbb: KnockoutObservable<string>;
        currentCodeCbb: KnockoutObservable<number>
        selectedCodeCbb: KnockoutObservable<string>;
        isEnableCbb: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            //Switch button 
            self.roundingRules = ko.observableArray([
                { unitId: '1', unitName: '日別' },
                { unitId: '2', unitName: '時間帯別' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //Combobox
            self.itemListCbb = ko.observableArray([
                new ItemModelCbb('1','時間'),
                new ItemModelCbb('2','人数'),
                new ItemModelCbb('3','金額'),
                new ItemModelCbb('4','数値'),
                new ItemModelCbb('5','単価')
            ]);
            //Defaut value 
            self.selectedCodeCbb = ko.observable('1');
            self.isEnableCbb = ko.observable(true);
            //grid list
            self.currentCode = ko.observable('0');
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'externalBudgetCode', width: 40 },
                { headerText: '名称', key: 'externalBudgetName', width: 150 }
            ]);
            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new Item({
                externalBudgetCode: '',
                externalBudgetName: '',
                attribute: 0,
                unit: 0
            }))
            self.start();
        }
        //start
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //get list budget
            service.getListExternalBudget().done(function(lstBudget: Array<Item>) {
                if (lstBudget.length > 0) {
                    //Set sourse to liSt
                    self.items(lstBudget);
                    //get all data from Server 
                    self.currentItem().setSource(lstBudget);
                    //current Code
                    self.currentItem().externalBudgetCode(lstBudget[0].externalBudgetCode);

                    console.log(self.currentItem().externalBudgetCode());
                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                alert(res);
            });
            return dfd.promise();
        }
    }

    interface IItem {
        externalBudgetCode: string;
        externalBudgetName: string;
        attribute: number;
        unit: number;
    }

    //item LIST Budget
    class Item {
        externalBudgetCode: KnockoutObservable<string>;
        externalBudgetName: KnockoutObservable<string>;
        attribute: KnockoutObservable<number>;
        unit: KnockoutObservable<number>;
        listSource: Array<any>;
        constructor(p: IItem) {
            var self = this;
            self.externalBudgetCode = ko.observable(p.externalBudgetCode);
            self.externalBudgetName = ko.observable(p.externalBudgetName);
            self.attribute = ko.observable(p.attribute);
            self.unit = ko.observable(p.unit);
            console.log(self.externalBudgetCode());

            self.externalBudgetCode.subscribe(function(newValue) {
                var current = _.find(self.listSource, function(item) { return item.externalBudgetCode == newValue; });
                if (current) {
                    self.externalBudgetCode(current.externalBudgetCode);
                    self.externalBudgetName(current.externalBudgetName);
                    self.attribute(current.attribute);
                    self.unit(current.unit);
                }
            });
        }
        setSource(list: Array<any>) {
            var self = this;
            self.listSource = list || [];
        }
    }
    //item Combo Box
    class ItemModelCbb {
        codeCbb: string;
        nameCbb: string;
        constructor(codeCbb:string,nameCbb: string) {
            this.codeCbb = codeCbb;
            this.nameCbb = nameCbb;
        }
    }

}