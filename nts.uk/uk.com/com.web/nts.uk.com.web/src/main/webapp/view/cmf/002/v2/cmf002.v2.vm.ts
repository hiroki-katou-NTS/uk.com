module nts.uk.com.view.cmf002.v2.viewmodel {
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
        listConvertCode: KnockoutObservableArray<model.AcceptanceCodeConvert>
        selectedConvertCode: KnockoutObservable<string>
        constructor() {
            var self = this;
            self.listConvertCode = ko.observableArray([
                new model.AcceptanceCodeConvert("1", "Item 1", 0),
                new model.AcceptanceCodeConvert("2", "Item 2", 0),
                new model.AcceptanceCodeConvert("3", "Item 3", 0),
                new model.AcceptanceCodeConvert("4", "Item 4", 0),
                new model.AcceptanceCodeConvert("5", "Item 5", 0),
                new model.AcceptanceCodeConvert("6", "Item 6", 0),
                new model.AcceptanceCodeConvert("7", "Item 7", 0),
                new model.AcceptanceCodeConvert("8", "Item 8", 0),
                new model.AcceptanceCodeConvert("9", "Item 9", 0),
                new model.AcceptanceCodeConvert("10", "Item 10", 0),
                new model.AcceptanceCodeConvert("11", "Item 11", 0),
            ]);
            self.selectedConvertCode = ko.observable("1");

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