module nts.uk.com.view.cmf005.c.vm.ts.k.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
               
        /* screen */
        constructor() {
        }
        
        // Return code / name of selected line
        selectConvertCode() {
//            var self = this;
//            let codeConvert = new model.AcceptanceCodeConvert("", "", 0);
//            if (!_.isEqual(self.selectedConvertCode(), "")){
//                codeConvert = _.find(ko.toJS(self.listConvertCode), (x: model.AcceptanceCodeConvert) => x.dispConvertCode == self.selectedConvertCode());
//            }
//            // 選択された行のコード/名称を返す
//            setShared("cmf005kOutput", { selectedConvertCodeShared: codeConvert});
            nts.uk.ui.windows.close();
        }
        //Cancel and exit
        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
    }

}