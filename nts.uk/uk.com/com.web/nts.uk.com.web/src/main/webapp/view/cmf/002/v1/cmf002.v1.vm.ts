module nts.uk.com.view.cmf002.v1.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listCategoryItem: KnockoutObservableArray<model.ItemModel>;
        selectedCategoryItem: KnockoutObservable<number>;
        
        
        constructor() {
            var self = this;
            self.listCategoryItem = ko.observableArray([
                new model.ItemModel(1, "Item 1"),
                new model.ItemModel(2, "Item 2"),
                new model.ItemModel(3, "Item 3"),
                new model.ItemModel(4, "Item 4"),
                new model.ItemModel(5, "Item 5"),
                new model.ItemModel(6, "Item 6"),
                new model.ItemModel(7, "Item 7"),
                new model.ItemModel(8, "Item 8"),
                new model.ItemModel(9, "Item 9"),
                new model.ItemModel(10, "Item 10"),
                new model.ItemModel(11, "Item 11"),
            ]);
            self.selectedCategoryItem = ko.observable(1);
        }
        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
//            service.getCharacterDataFormatSetting().done(function(charFormat) {
//                if (charFormat) {
//                    let characterDataFormatSetting = new model.CharacterDataFormatSetting(charFormat.effectDigitLength, 
//                    charFormat.startDigit, charFormat.endDigit, charFormat.codeEditing, charFormat.codeEditDigit, 
//                    charFormat.codeEditingMethod, charFormat.spaceEditing, charFormat.codeConvertCode, charFormat.nullValueReplace, 
//                    charFormat.valueOfNullValueReplace, charFormat.fixedValue, charFormat.valueOfFixedValue);
//                    self.characterDataFormatSetting(characterDataFormatSetting);
//                }
//                block.clear();
//                dfd.resolve();
//            }).fail(function(error) {
//                alertError(error);
//                block.clear();
//                dfd.reject();
//            });
            return dfd.promise();
        }
        
    }
}