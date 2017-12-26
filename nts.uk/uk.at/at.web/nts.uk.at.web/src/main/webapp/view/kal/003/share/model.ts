module nts.uk.at.view.kal003.share.model {
    
    export class AlarmCheckConditionByCategory {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        category: KnockoutObservable<string>;
        availableRoles: KnockoutObservableArray<string>;
        targetCondition: KnockoutObservable<AlarmCheckTargetCondition>;
        displayAvailableRoles: KnockoutObservable<string>;
        
        constructor(code: string, name: string, category: string, availableRoles: Array<string>, targetCondition: AlarmCheckTargetCondition) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.category = ko.observable(category);
            this.availableRoles = ko.observableArray(availableRoles);
            this.targetCondition = ko.observable(targetCondition);
            this.displayAvailableRoles = ko.computed(function() {
                return this.availableRoles().join(", ");
            }, this);
        }
    }

    export class AlarmCheckTargetCondition {
        filterByEmployment: KnockoutObservable<boolean>;
        filterByClassification: KnockoutObservable<boolean>;
        filterByJobTitle: KnockoutObservable<boolean>;
        filterByBusinessType: KnockoutObservable<boolean>;
        targetEmployment: KnockoutObservableArray<string>;
        targetClassification: KnockoutObservableArray<string>;
        targetJobTitle: KnockoutObservableArray<string>;
        targetBusinessType: KnockoutObservableArray<string>;
        displayTargetEmployment: KnockoutObservable<string>;
        displayTargetClassification: KnockoutObservable<string>;
        displayTargetJobTitle: KnockoutObservable<string>;
        displayTargetBusinessType: KnockoutObservable<string>;
        
        constructor(fbe: boolean, fbc: boolean, fbjt: boolean, fbbt: boolean, targetEmp: Array<string>, targetCls: Array<string>, targetJob: Array<string>, targetBus: Array<string>) {
            this.filterByEmployment = ko.observable(fbe);
            this.filterByClassification = ko.observable(fbc);
            this.filterByJobTitle = ko.observable(fbjt);
            this.filterByBusinessType = ko.observable(fbbt);
            this.targetEmployment = ko.observableArray(targetEmp);
            this.targetClassification = ko.observableArray(targetCls);
            this.targetJobTitle = ko.observableArray(targetJob);
            this.targetBusinessType =  ko.observableArray(targetBus);
            this.displayTargetEmployment = ko.computed(function() {
                return this.targetEmployment().join(", ");
            }, this);
            this.displayTargetClassification = ko.computed(function() {
                return this.targetClassification().join(", ");
            }, this);
            this.displayTargetJobTitle = ko.computed(function() {
                return this.targetJobTitle().join(", ");
            }, this);
            this.displayTargetBusinessType = ko.computed(function() {
                return this.targetBusinessType().join(", ");
            }, this);
        }
    }

}