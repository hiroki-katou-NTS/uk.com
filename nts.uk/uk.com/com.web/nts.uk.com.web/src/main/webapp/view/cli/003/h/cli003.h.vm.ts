module nts.uk.com.view.cli003.h.viewmodel {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ScreenModel {
        conditionSets: KnockoutObservableArray<DetailConSet>;
        itemListCbb: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        comboColumns: KnockoutObservableArray<any>;
         
        constructor() {
            var self = this;
            // get param from screen G
            let listDetailConditionSetting = getShared('CLI003GParams_ListSetItemDetail');
            let name = getShared("CLI003GParams_ItemName");
            $('#H1_2').html(name);


            self.itemListCbb.push(new ItemModel('0', getText('Enum_Symbol_Include')));
            self.itemListCbb.push(new ItemModel('1', getText('Enum_Symbol_Equal')));
            self.itemListCbb.push(new ItemModel('2', getText('Enum_Symbol_Different')));

            self.conditionSets = ko.observableArray((function() {
                var list = [];
                if (listDetailConditionSetting && listDetailConditionSetting.length > 0) {
                    for (var i = 0; i < listDetailConditionSetting.length; i++) {
                        var detailConditonSet = listDetailConditionSetting[i];
                        let isUsed = detailConditonSet.isUseCondFlg == 1 ? true : false;
                        list.push(new DetailConSet(detailConditonSet.frame, isUsed, detailConditonSet.sybol, detailConditonSet.condition));
                    }
                } else {
                    for (var i = 0; i < 5; i++) {
                        list.push(new DetailConSet(i, false, '0', ''));
                    }
                }
                return list;
            })());



            $("#H1_2").html(getShared('itemName'));
            self.comboColumns = ko.observableArray([{ prop: 'name' }]);
            
            $("#H2_1 tr").eq(1).find('td:first div').find('input:checkbox').attr('tabindex', '-1');
            $("#H2_1 tr").eq(1).find('td:first div').find('input:checkbox').focus();
            
        }

        closePopup() {
            close();
        }

        submit() {
            let self = this;
            if (self.checkData()) {
                var list = [];
                for (var i = 0; i < self.conditionSets().length; i++) {
                    var detailConditonSet = self.conditionSets()[i];
                    list.push(new ConSet(detailConditonSet.id(), detailConditonSet.isUseCondFlg(), 
                        detailConditonSet.symbolStr(), detailConditonSet.condition()));
                }
                setShared("CLI003GParams_ListSetItemDetailReturn", list);
                close();
            }
        }
        
        checkData() {
            let self = this;
            let flgReturn = true;
            _.forEach(self.conditionSets(), function(item: DetailConSet) {
                if (item.isUseCondFlg() == true) {
                    if (item.condition() === '') {
                        flgReturn = false;
                    }
                }
            });
            if (!flgReturn) {
                alertError({ messageId: "Msg_1203", messageParams: [getText('CLI003_49')]});    
            }
            return flgReturn;
        }

    }

    export class DetailConSet {
       
        id: KnockoutObservable<number>;
        isUseCondFlg: KnockoutObservable<any>;
        symbolStr: KnockoutObservable<string>;
        condition: KnockoutObservable<string>;

        constructor(id :number, isUseCondFlg: any, symbolStr: string, condition: string) {
            var self = this;
            self.id = ko.observable(id);
            self.isUseCondFlg = ko.observable(isUseCondFlg);
            self.symbolStr = ko.observable(symbolStr);
            self.condition = ko.observable(condition);
        }
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    
    export class ConSet {
       
        id: number;
        isUseCondFlg: any;
        symbolStr: string;
        condition: string;

        constructor(id :number, isUseCondFlg: any, symbolStr: string, condition: string) {
            this.id = id;
            this.isUseCondFlg = isUseCondFlg;
            this.symbolStr = symbolStr;
            this.condition = condition;
        }
    }


}






