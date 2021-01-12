/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.d.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    selectSingle: KnockoutObservable<string> = ko.observable();
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    columns: KnockoutObservableArray<any>;
    columns2: KnockoutObservableArray<any>;
    items: KnockoutObservableArray<any> = ko.observableArray([]);
    cId: KnockoutObservable<string> = ko.observable("");
    name: KnockoutObservable<string> = ko.observable("");
    option: any =  ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
      textmode: "text",
      width: "200px",
      textalign: "left"
    }))
    option1: any =  ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
      textmode: "text",
      width: "155px",
      textalign: "left"
    }))
    roundingRules: KnockoutObservableArray<any>;
    selectedRuleCode: any;

    created() {
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n("CCG005_5"), key: "code", width: 150 },
      ]);
      vm.columns2 = ko.observableArray([
        { headerText: vm.$i18n("CCG005_7"), key: "code", width: 50 },
        { headerText: vm.$i18n("CCG005_6"), key: "name", width: 80 },
      ]);

      vm.roundingRules = ko.observableArray([
        { code: '1', name: vm.$i18n("CCG005_13") },
        { code: '2', name: vm.$i18n("CCG005_14") },
        { code: '3', name: vm.$i18n("CCG005_16") }
      ]);
    vm.selectedRuleCode = ko.observable(1);
    }

    onClickCancel() {
      this.$window.close();
    }
  }

}
