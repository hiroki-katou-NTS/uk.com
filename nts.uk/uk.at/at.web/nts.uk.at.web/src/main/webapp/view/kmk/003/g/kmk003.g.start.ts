module nts.uk.at.view.kmk003.g {
    import SettingMethod = a5.SettingMethod;
    import EnumWorkForm = a5.EnumWorkForm;
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if ((screenModel.dataObject().workForm == EnumWorkForm.FLEX) || ((screenModel.dataObject().workForm == EnumWorkForm.REGULAR) && (screenModel.dataObject().settingMethod == SettingMethod.FLOW)))//case flow or flex
            {
                _.defer(() => $('#switch-btn').focus());
            }
            else {
                _.defer(() => $('#selected-actual').focus());
            }
        });
    });
}