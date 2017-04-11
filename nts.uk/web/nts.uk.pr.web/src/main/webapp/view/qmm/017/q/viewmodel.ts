module nts.uk.pr.view.qmm017.q {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;
            formulaContent: string;

            constructor(data) {
                var self = this;
                self.items = ko.observableArray([]);
                self.currentCodeList = ko.observableArray([]);
                self.formulaContent = data.formulaContent;
                self.buildListItemModel(data.itemsBag);
                self.bindGridListItem();
            }
            
            isDuplicated(itemName){
                var self = this;
                let foundItem = _.find(self.items(), function(item){
                    return item.name == itemName;
                });
                return foundItem !== undefined;
            }
            
            buildListItemModel(itemsBag){
                var self = this;
                _.forEach(itemsBag, function(item){
                    if(item.name.indexOf('関数') === -1 && self.formulaContent.indexOf(item.name) !== -1 && !self.isDuplicated(item.name)){
                        self.items.push(new ItemModel(item.name, 0));
                    }
                });
            }

            bindGridListItem() {
                var self = this;
                $("#q_lst_001").igGrid({
                    primaryKey: "code",
                    columns: [
                        { headerText: "計算式の項目名", key: "code", dataType: "string", width: '150px' },
                        { headerText: "値", key: "value", dataType: "number", width: '150px' },
                    ],
                    dataSource: self.items(),
                    height: "200px",
                    width: "330px",
                    features: [
                        {
                            name: "Updating",
                            enableAddRow: false,
                            editMode: "row",
                            enableDeleteRow: false,
                            columnSettings: [
                                { columnKey: "code", editorOptions: { type: "string", disabled: true } },
                            ],
                            editCellEnding: function(evt, ui){
                                var foundItem = _.find(self.items(), function(item){return item.code == ui.rowID});
                                (foundItem.code !== ui.value) ? foundItem.value = ui.value : foundItem.code = ui.value;
                            }
                        }]
                });
                $("[aria-describedby='q_lst_001_code']").css({"backgroundColor": "#CFF1A5"});
            }

            calculationTrial(){
                var self = this;
                let replacedValue = '';
                _.forEach(self.items(), function(item){
                    replacedValue = self.formulaContent.replace(item.code, item.value);
                });
                console.log(replacedValue);    
            }    

        }
    }

    class ItemModel {
        code: string;
        value: number;
        constructor(code: string, value: number) {
            this.code = code;
            this.value = value;
        }
    }
}