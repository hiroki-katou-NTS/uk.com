module kaf000.c.viewmodel{
    export class ScreenModel{
        itemListCbb1: KnockoutObservableArray<model.ItemModelCbb1>;
        selectedCode: KnockoutObservable<string>;
        constructor(){
            var self = this;
            self.selectedCode = ko.observable('0003')
            self.itemListCbb1 = ko.observableArray([
                new model.ItemModelCbb1('1', '基本給'),
                new model.ItemModelCbb1('2', '役職手当'),
                new model.ItemModelCbb1('3', '基本給')
            ]);
        } 
    }
    export module model {
        export class ItemModelCbb1 {
            codeCbb1: string;
            nameCbb1: string;
            labelCbb1: string;
    
            constructor(codeCbb1: string, nameCbb1: string) {
                this.codeCbb1 = codeCbb1;
                this.nameCbb1 = nameCbb1;
            }
        }
        
    }
}