module kdl002.b.viewmodel {
    export class ScreenModel {
        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel2>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservable<any>;
        posibleItems: Array<string>;
        SelectedCode: KnockoutObservable<string>;
        SelectableCode: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.isMulti = true;
            self.SelectedCode = ko.observable('');
            self.SelectableCode = ko.observable('');
            self.items = ko.observableArray([]);
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
            //selected items
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId',lstSelectedCode,true);
            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items();
                var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                if(lst == null || lst === undefined) return;
                self.items(lst);
            })
        }
        OpenDialog0072(){
            var self = this;
            nts.uk.ui.windows.setShared('KDL007_Multiple',true,true);
            //all possible items
            nts.uk.ui.windows.setShared('KDL007_AllItemObj',['011','002','003','005','008','035','045','051'],true);
            //selected items
            nts.uk.ui.windows.setShared('KDL007_SelectedItemId',['001','003','008'],true);
            nts.uk.ui.windows.sub.modal('/view/kdl/007/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('SelectedNewItem');
                let lstItemMapping =  _.map(lst , item => {
                        return new model.ItemModel2(item, '');
                });
                self.items(lstItemMapping);
            })
        }
        OpenDialog007(){
            var self = this;
            nts.uk.ui.windows.setShared('KDL007_Multiple',false,true);
            //all possible items
            nts.uk.ui.windows.setShared('KDL007_AllItemObj',['001','002','003','005','008'],true);
            //selected items
            nts.uk.ui.windows.setShared('KDL007_SelectedItemId','004',true);
            nts.uk.ui.windows.sub.modal('/view/kdl/007/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('SelectedNewItem');
                let lstItemMapping =  _.map(lst , item => {
                    return new model.ItemModel2(item, '');
                });
//                let aa = new model.ItemModel2(lst, '');
//                self.items.push(aa);
                self.items(lstItemMapping);
            })
        }
        OpenDialog002(){
            var self = this;
            let lstSelectedCode = self.list(self.SelectedCode());
            let lstSelectableCode = self.list(self.SelectableCode());
            nts.uk.ui.windows.setShared('KDL002_Multiple',false,true);
            //all possible items
            nts.uk.ui.windows.setShared('KDL002_AllItemObj',lstSelectableCode,true);
            //selected items
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId',lstSelectedCode,true);
            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                if(lst == null || lst === undefined) return;
                self.items(lst);
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
