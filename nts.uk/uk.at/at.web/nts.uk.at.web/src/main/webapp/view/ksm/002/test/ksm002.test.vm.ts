module ksm002.test.viewmodel {
    export class ScreenModel {
        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel2>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservable<any>;
        posibleItems: Array<string>;
        SelectedCode: KnockoutObservable<string>;
        SelectableCode: KnockoutObservable<string>;
        test_true:KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.isMulti = true;
            self.test_true = ko.observable(true);
            self.SelectedCode = ko.observable('2');
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
        check(){
            this.test_true(true);
            
        }
        OpenDialog0072(){
            var self = this;
            let param: IData = {
                isMulti: true,
                selecteds: self.list(self.SelectedCode())
            }
            nts.uk.ui.windows.setShared('KDL007_PARAM', param,true);
            nts.uk.ui.windows.sub.modal('/view/kdl/007/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('KDL007_VALUES');
                let str='';
                _.each(lst, x => str+= x +',');
                self.SelectedCode(str);
                if(lst== null || undefined){
                    self.items();
                }else{
                    let lstItemMapping =  _.map(lst.selecteds , item => {
                        return new model.ItemModel2(item, '');
                });
                self.items(lstItemMapping); 
                }

            })
        }
        OpenDialogC(){
//            var self = this;
            nts.uk.ui.windows.sub.modal('/view/ksm/002/c/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                self.items([]);
                var lst = nts.uk.ui.windows.getShared('KDL007_VALUES');
                let str='';
                _.each(lst, x => str+= x +',');
                self.SelectedCode(str);
                console.log(lst);
                let lstItemMapping =  _.map(lst , item => {
                    return new model.ItemModel2(item, '');
                });
                self.items(lstItemMapping);
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
        interface IData {
        isMulti: boolean,
        posibles: Array<any>,
        selecteds: Array<any>
    }
}
