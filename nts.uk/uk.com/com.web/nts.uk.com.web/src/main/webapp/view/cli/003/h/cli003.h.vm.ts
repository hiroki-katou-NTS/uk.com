module nts.uk.com.view.cli003.h.viewmodel {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ScreenModel {
        
        
        constructor() {
            var self = this;
            // get param from screen G
            let listDetailConditionSetting = getShared('CLI003GParams_ListSetItemDetail');
            let name = getShared("CLI003GParams_ItemName");
            $('#H1_2').html(name);
            
            self.items = (function() {
                var list = [];
                if (listDetailConditionSetting && listDetailConditionSetting.length > 0) {
                    for (var i = 0; i < listDetailConditionSetting.length; i++) {
                        var detailConditonSet = listDetailConditionSetting[i];
                        list.push(new DetailConSet(detailConditonSet.frame, detailConditonSet.isUseCondFlg, detailConditonSet.sybol, detailConditonSet.condition));
                    }
                } else {
                    for (var i = 0; i < 5; i++) {
                        list.push(new DetailConSet(i, 0, '0', ''));
                    }
                }
                return list;
            })();
            
             var comboItems = [new ItemModel('0', getText('Enum_Symbol_Include')),
                new ItemModel('1', getText('Enum_Symbol_Equal')),
                new ItemModel('2', getText('Enum_Symbol_Different'))];

            $("#H1_2").html(getShared('itemName'));
            var comboColumns = [{ prop: 'name'}];
            
            $("#H2_1").ntsGrid({
                width: '450px',
                height: '220px',
                dataSource:  self.items,
                hidePrimaryKey: true, 
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: '', key: 'id', dataType: 'number' },
                    { headerText: '', key: 'isUseCondFlg', dataType: 'boolean', width: '50px', ntsControl: 'Checkbox' },
                    { headerText: getText('CLI003_48'), key: 'symbolStr', dataType: 'string', width: '100px', ntsControl: 'Combobox' },
                    { headerText: getText('CLI003_49'), key: 'condition', dataType: 'string', width: '250px', ntsControl: 'TextEditor' }
                ],
                features: [{
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: true
                }],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: 'isUseCondFlg'},
                    { name: 'TextEditor', controlType: 'TextEditor', constraint: { valueType: 'String', required: false}, enable: 'isUseCondFlg'}],
            });
        }

        closePopup() {
            close();
        }

        submit() {
            let self = this;
            if (self.checkData()) {
                let listData = $("#H2_1").igGrid("option", "dataSource");
                setShared("CLI003GParams_ListSetItemDetailReturn", listData);
                close();
            }
        }
        
        checkData() {
            let self = this;
            let listData = $("#H2_1").igGrid("option", "dataSource");
            let flgReturn = true;
            _.forEach(listData, function(item: DetailConSet) {
                if (item.isUseCondFlg == true) {
                    if (item.condition === '') {
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
       
        id: number;
        isUseCondFlg: boolean;
        symbolStr: string;
        condition: string;

        constructor(id :number, isUseCondFlg: boolean, symbolStr: string, condition: string) {
            this.id = id;
            this.isUseCondFlg = isUseCondFlg;
            this.symbolStr = symbolStr;
            this.condition = condition;
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






