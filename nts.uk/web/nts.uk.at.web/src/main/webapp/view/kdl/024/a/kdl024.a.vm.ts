module kdl024.a.viewmodel {
    export class ScreenModel {
        contraint: Array<string>;
        //Mode
        isNew: boolean;
        //List
        items: KnockoutObservableArray<Item>;
        currentItem: KnockoutObservable<Item>;
        currentSource: Array<Item>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        //Switch button
        roundingRules: KnockoutObservableArray<any>;
        //Input Code
        isEnableInp: KnockoutObservable<boolean>;
        //Combobox
        itemListCbb: KnockoutObservableArray<ItemModelCbb>;
        itemNameCbb: KnockoutObservable<string>;
        currentCodeCbb: KnockoutObservable<number>
        isEnableCbb: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            //Mode
            self.isNew = false;
            //Switch button 
            self.roundingRules = ko.observableArray([
                { unitId: '0', unitName: '日別' },
                { unitId: '1', unitName: '時間帯別' }
            ]);
            //Input Code
            self.isEnableInp = ko.observable(false);
            //Combobox
            self.itemListCbb = ko.observableArray([
                new ItemModelCbb('0', '時間'),
                new ItemModelCbb('1', '人数'),
                new ItemModelCbb('2', '金額'),
                new ItemModelCbb('3', '数値'),
                new ItemModelCbb('4', '単価')
            ]);
            //Defaut value 
            self.isEnableCbb = ko.observable(true);
            //grid list
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'externalBudgetCode', width: 40 },
                { headerText: '名称', key: 'externalBudgetName', width: 150 }
            ]);
            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new Item({
                externalBudgetCode: '',
                externalBudgetName: '',
                budgetAtr: 0,
                unitAtr: 0
            }))
            self.start();
        }
        //start
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //get list budget   
            service.getListExternalBudget().done(function(lstBudget: any) {
                let _items: Array<TempItem> = [];
                if (lstBudget.length > 0) {
                    self.isNew = false;
                    for (let i in lstBudget) {
                        let item = lstBudget[i];
                        _items.push(new TempItem(item.externalBudgetCode, item.externalBudgetName, item.budgetAtr, item.unitAtr));
                    }
                    //Set sourse to list
                    self.items(_items);
                    //get all data from database to array
                    self.currentSource = _items;
                    //get all data from Server 
                    self.currentItem().setSource(_items);
                    //current Code
                    self.currentItem().externalBudgetCode(lstBudget[0].externalBudgetCode);
                    //Disable Code Input 
                    self.isEnableInp(false);
                } else {
                    self.isNew = true;
                    self.currentSource = _items;
                    self.currentItem().setSource(_items);
                    self.isEnableInp(true);
                    $('#inpCode').focus();
                    dfd.resolve();
                }
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            });
            return dfd.promise();
        }
        //Register Data
        register() {
            var self = this;
            //Mode INSERT
            if (self.isNew) {
                service.insertExternalBudget(self.currentItem()).done(function() {
                    nts.uk.ui.dialog.alert("登録しました。")；
                        //restart
                        self.currentSource.push(new TempItem(
                            self.currentItem().externalBudgetCode(),
                            self.currentItem().externalBudgetName(),
                            self.currentItem().budgetAtr(),
                            self.currentItem().unitAtr()
                        ));
                        //Re sort
                        self.currentSource.sort(function(a,b){
                            if(a.externalBudgetCode < b.externalBudgetCode)
                                return -1;
                            if(a.externalBudgetCode > b.externalBudgetCode)
                                return 1;
                            return 0;   
                        });
                        //Reset list Source 
                        self.items([]);
                        //Re Add list source    
                        self.items(self.currentSource);
                        self.isNew = false;
                        //Disable Input Code
                        self.isEnableInp(false);
                    }).fail(function(res) {
                        $('#inpCode').ntsError('set', res.message);
                    });
            //Mode UPDATE
            } else {
                service.updateExternalBudget(self.currentItem()).done(function() {
                    nts.uk.ui.dialog.alert("登録しました。")；
                    //update list source
                    let currentIndex = _.findIndex(self.currentSource,['externalBudgetCode',self.currentItem().externalBudgetCode()]);
                    if(currentIndex != -1){
                        self.currentSource[currentIndex].externalBudgetName = self.currentItem().externalBudgetName();
                        self.currentSource[currentIndex].budgetAtr = self.currentItem().budgetAtr();
                        self.currentSource[currentIndex].unitAtr = self.currentItem().unitAtr();
                    }
                    self.items([]);
                    //Re Add list source    
                    self.items(self.currentSource);
                    //Restart screen
                    //self.start();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    self.start();
                });
            }
            //enable button Del 
            $("#btnDel").prop('disabled', false);
        }
        //insert new Item 
        addNew() {
            var self = this;
            //current Code, 何にも、項目選択している。
            self.currentItem().externalBudgetCode(null);
            $('#inpName').val('');
            $('#inpCode').val('');            
            self.currentItem().budgetAtr('0');
            self.currentItem().unitAtr(0);
            $("#btnDel").prop('disabled', true);
            //Enable Code Input
            self.isEnableInp(true);
            $('#inpCode').focus();
            //Change mode to NEW
            self.isNew = true;
        }
        //close dialog
        close(){
            nts.uk.ui.windows.close();
        }
        //delete
        del() {
            var self = this;
            nts.uk.ui.dialog.confirm("選択中のデータを削除しますか？").ifYes(function(){
                //削除後処理                
                service.deleteExternalBudget(self.currentItem()).done(function() {
                   nts.uk.ui.dialog.alert("削除しました。")；
                    //get index of selected Item
                    var iIndex = _.findIndex(self.currentSource,['externalBudgetCode',self.currentItem().externalBudgetCode()]);
                    //get max index of list Items
                    var maxIndex :number = self.currentSource.length-1;                
                    //case of : selected item is last item
                    if(iIndex == 0){
                        self.items([]);
                        self.addNew();
                    }else{
                        if(iIndex == maxIndex){
                            //get above item and set selected item 
                            self.currentItem().externalBudgetCode(self.currentSource[maxIndex-1].externalBudgetCode);
                            //Remove the last Item of currentSource               
                            self.currentSource.splice(maxIndex,1);
                        //case of selected item is not last item
                        //最下行でない項目を削除した場合  
                        }else{
                            //get under item and set selected item
                            self.currentItem().externalBudgetCode(self.currentSource[iIndex+1].externalBudgetCode);
                            //remove selected item in current source 
                            self.currentSource.splice(iIndex,1);
                        }
                        //Reset list Source 
                        self.items([]);
                        self.items(self.currentSource);
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    self.start();
                });
            });            
        }
    }
    
    interface IItem {
        externalBudgetCode: string;
        externalBudgetName: string;
        budgetAtr: number;
        unitAtr: number;
    }
    class TempItem {
        externalBudgetCode: string;
        externalBudgetName: string;
        budgetAtr: number;
        unitAtr: number;
        constructor(externalBudgetCode: string, externalBudgetName: string, budgetAtr: number, unitAtr: number) {
            var self = this;
            self.externalBudgetCode = externalBudgetCode;
            self.externalBudgetName = externalBudgetName;
            self.budgetAtr = budgetAtr;
            self.unitAtr = unitAtr;
        }
    }

    //item LIST Budget
    class Item {
        externalBudgetCode: KnockoutObservable<string>;
        externalBudgetName: KnockoutObservable<string>;
        budgetAtr: KnockoutObservable<number>;
        unitAtr: KnockoutObservable<number>;
        listSource: Array<any>;
        constructor(p: IItem) {
            var self = this;
            self.externalBudgetCode = ko.observable(p.externalBudgetCode);
            self.externalBudgetName = ko.observable(p.externalBudgetName);
            self.budgetAtr = ko.observable(p.budgetAtr);
            self.unitAtr = ko.observable(p.unitAtr);

            self.externalBudgetCode.subscribe(function(newValue) {
                var current = _.find(self.listSource, function(item) { return item.externalBudgetCode == newValue; });
                //console.log(current);
                if (current) {
                    self.externalBudgetCode(current.externalBudgetCode);
                    //alert(padZero(current.externalBudgetCode));
                    self.externalBudgetName(current.externalBudgetName);
                    //K hieu t s 
                    //self.budgetAtr(current.budgetAtr.toString());
                    self.unitAtr(current.unitAtr);
                    self.budgetAtr(current.budgetAtr.toString());
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
        constructor(codeCbb: string, nameCbb: string) {
            var self = this;
            self.codeCbb = codeCbb;
            self.nameCbb = nameCbb;
        }
    }

}