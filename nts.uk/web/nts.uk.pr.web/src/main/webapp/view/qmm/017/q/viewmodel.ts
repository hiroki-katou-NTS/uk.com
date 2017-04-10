module nts.uk.pr.view.qmm017.q {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.items = ko.observableArray([]);

                for (let i = 1; i < 4; i++) {
                    self.items.push(new ItemModel('00' + i, 0));
                }
                self.currentCodeList = ko.observableArray([]);
                self.bindGridListItem();
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