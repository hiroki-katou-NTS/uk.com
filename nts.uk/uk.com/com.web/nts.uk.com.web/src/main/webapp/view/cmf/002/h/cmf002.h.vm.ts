module nts.uk.com.view.cmf002.h.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.errors;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('基本給2', '基本給', "description 1"),
                new ItemModel('基本給1', '役職手当', "description 2"),
                new ItemModel('基本給3', '基本給', "description 3"),
                 new ItemModel('基本給2', '基本給', "description 1"),
                new ItemModel('基本給1', '役職手当', "description 2"),
                new ItemModel('基本給3', '基本給', "description 3"),
                new ItemModel('基本給3', '基本給', "description 3")
                
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(7);
            self.selectedCode = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);

            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }

        deselectAll() {
            $('#list-box').ntsListBox('deselectAll');
        }

        selectAll() {
            $('#list-box').ntsListBox('selectAll');
        }
        
                /**
        * Close dialog.
        */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }

        //設定
        saveData() {
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
}