module nts.uk.at.view.kal003.a.tab {
    import model = kal003.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScopeCheckTab {
        
        targetCondition: KnockoutObservable<model.AlarmCheckTargetCondition> = ko.observable(new model.AlarmCheckTargetCondition(false, false, false, false, [], [], [], []));
        
        constructor(targetCondition?: model.AlarmCheckTargetCondition) {
            if (targetCondition) {
                this.targetCondition(targetCondition);
            }
            this.targetCondition.subscribe(() => {
                this.targetCondition().targetEmployment.valueHasMutated();
                this.targetCondition().targetClassification.valueHasMutated();
                this.targetCondition().targetJobTitle.valueHasMutated();
                this.targetCondition().targetBusinessType.valueHasMutated();
            });
        }

    }
}