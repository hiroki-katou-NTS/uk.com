/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.d.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    listDataMulti: KnockoutObservableArray<any> = ko.observableArray([]);
    selectSingle: KnockoutObservable<string> = ko.observable();
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    columns: KnockoutObservableArray<any>;
    columns2: KnockoutObservableArray<any>;
    items: KnockoutObservableArray<any> = ko.observableArray([]);
    cId: KnockoutObservable<string> = ko.observable("");
    created() {
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n("CCG005_5"), key: "code", width: 100 },
      ]);
      vm.columns2 = ko.observableArray([
        { headerText: vm.$i18n("CCG005_7"), key: "code", width: 50 },
        { headerText: vm.$i18n("CCG005_6"), key: "name", width: 80 },
      ]);

    }

    onClickCancel() {
      this.$window.close();
    }
  }

}
