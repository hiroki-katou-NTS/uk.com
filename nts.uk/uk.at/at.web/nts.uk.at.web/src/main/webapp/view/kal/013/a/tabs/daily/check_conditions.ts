module nts.uk.at.view.kal013.a.tab {
  export class CheckCondition {

    checkConditions: KnockoutObservable<boolean> = ko.observable(false);
    
    constructor(checkConditions?: boolean) {
      this.checkConditions(checkConditions);
    }
  }
}