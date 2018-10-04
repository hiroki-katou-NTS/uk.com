module kdl002.b.viewmodel {
    export class ScreenModel {
        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel2>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservable<any>;
        posibleItems: Array<string>;
        SelectedCode: KnockoutObservable<string>;
        SelectableCode: KnockoutObservable<string>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.isMulti = true;
            self.SelectedCode = ko.observable('');
            self.SelectableCode = ko.observable('');
            self.items = ko.observableArray([]);
            self.isShowNoSelectRow = ko.observable(false);
            //header
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL002_3"), prop: 'code', width: 70 },
                { headerText: nts.uk.resource.getText("KDL002_4"), prop: 'name', width: 200 ,formatter: _.escape},
            ]);
            self.currentCodeList = ko.observable();
            self.posibleItems = [];
        }
        list(str: string):Array<string>{
            return _.split(str, ',');
        }
        OpenDialog0022(){
            var self = this;
            let lstSelectedCode = self.list(self.SelectedCode());
            let lstSelectableCode = self.list(self.SelectableCode());
            nts.uk.ui.windows.setShared('KDL002_Multiple',true,true);
            //all possible items
            nts.uk.ui.windows.setShared('KDL002_AllItemObj',lstSelectableCode,true);
            nts.uk.ui.windows.setShared('kdl002isSelection',true);
            //selected items
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId',lstSelectedCode,true);
            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                self.SelectedCode();
                let strLstCode = '';
                _.each(lst,function(item, index){
                    if(index == 0){
                        strLstCode = strLstCode + lst[index].code;
                    }else{
                        strLstCode = strLstCode + ',' + lst[index].code;
                    }
                });
                self.SelectedCode(strLstCode);
            })
        }
        //mode single
        OpenDialog002(){
            var self = this;
            let lstSelectedCode = self.list(self.SelectedCode());
            let lstSelectableCode = self.list(self.SelectableCode());
            nts.uk.ui.windows.setShared('KDL002_Multiple',false,true);
            //all possible items
            nts.uk.ui.windows.setShared('KDL002_AllItemObj',lstSelectableCode,true);
             nts.uk.ui.windows.setShared('kdl002isSelection',true);
            //selected items
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId',lstSelectedCode,true);
            nts.uk.ui.windows.setShared('KDL002_isShowNoSelectRow',self.isShowNoSelectRow,true);
            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                self.SelectedCode();
                if (lst.length > 0){
                    self.SelectedCode(lst[0].code);
                }
                
            })
        }
    }
    export module model {
        export class ItemModel2 {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}
