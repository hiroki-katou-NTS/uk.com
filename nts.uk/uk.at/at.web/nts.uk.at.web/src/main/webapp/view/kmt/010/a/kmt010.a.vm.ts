/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmt010.a {
  @bean()
  class ViewModel extends ko.ViewModel {

    tabsWork: KnockoutObservableArray<any> = ko.observable([]);
    tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
	  selectedTab: KnockoutObservable<string>;

    constructor(params: any) {
      super();
      const vm = this;

      vm.tabs = ko.observableArray([
        {id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)}
      ]);
      vm.selectedTab = ko.observable('tab-2');

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }
  }
}