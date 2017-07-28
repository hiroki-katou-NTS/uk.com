module nts.uk.at.view.kdl023.b.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;

    export class ScreenModel extends BaseScreenModel {
        public decide(): void {
            let self = this;
            nts.uk.ui.windows.setShared('listDateSetting', self.optionDates());
            self.closeDialog();
        }
    }
}