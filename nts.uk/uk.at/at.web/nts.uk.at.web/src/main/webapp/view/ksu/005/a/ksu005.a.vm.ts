module nts.uk.at.view.ksu005.a {
    @bean()
    class Ksu005aViewModel extends ko.ViewModel {
        currentScreen: any = null;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        comments: KnockoutObservable<string>;
        constructor() {
            super();
            const self = this;
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            self.selectedCode = ko.observable('1');
            self.comments = ko.observable("並び順社員リスト 社員情報　対象期間...");
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        openDialog(): void {
            let self = this;				
            self.currentScreen = nts.uk.ui.windows.sub.modal('/view/ksu/005/b/index.xhtml');
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