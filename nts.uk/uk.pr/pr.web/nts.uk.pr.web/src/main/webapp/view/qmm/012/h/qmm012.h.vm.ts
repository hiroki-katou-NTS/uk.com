module nts.uk.pr.view.qmm012.h.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm012.share.model;

    export class ScreenModel {
        currentSetting: KnockoutObservable<model.ValidityPeriodAndCycleSet> = ko.observable(null);
        validityPeriodAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getValidityPeriodAtr());
        cycleSettingAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getCycleSettingAtr());
        itemAtr: KnockoutObservable<string> = ko.observable("Test1");
        itemNameCode: KnockoutObservable<string> = ko.observable("Test2");
        itemName: KnockoutObservable<string> = ko.observable("Test3");
        
        constructor() {
            this.currentSetting(new model.ValidityPeriodAndCycleSet({
                cycleSettingAtr: 1,
                january: true,
                february: true,
                march: true,
                april: true,
                may: true,
                june: true,
                july: true,
                august: true,
                september: true,
                october: true,
                november: true,
                december: true,
                periodAtr: 1,
                yearPeriodStart: 2015,
                yearPeriodEnd: 2018
            }));
        }

        execution() {

        }

        cancel() {

        }
    }
}