/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.b {
  @bean()
  class ViewModel extends ko.ViewModel {

    //model - left
    workplaceGroupCode: KnockoutObservable<string> = ko.observable('WPGC0001');
    workplaceGroupName: KnockoutObservable<string> = ko.observable('WPGC000N');
    historyListItems: KnockoutObservableArray<HistoryItem> = ko.observableArray([]);
    historyCurrentItems: KnockoutObservableArray<any> = ko.observableArray([]);
    historyCurrentObject: KnockoutObservable<HistoryItem> = ko.observable();
    nightShiftOperation: KnockoutObservable<number> = ko.observable(1);
    nightShiftHours1: KnockoutObservable<number> = ko.observable(1800);
    nightShiftHours2: KnockoutObservable<number> = ko.observable(800);

    isNewMode: KnockoutObservable<boolean> = ko.observable(true);

    constructor(params: any) {
      super();
      const vm = this;
      vm.historyListItems.push(new HistoryItem('2021/01/02','9999/12/31', '2021/01/02 ~ 9999/12/31'));
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    closeDialog() {
      const vm = this;
      alert(111);
      vm.$window.close({});
    }

    saveData() {
      const vm = this;
      vm.$window.close({});
    }

    clearData() {
      const vm = this;
      vm.nightShiftHours1(null);
      vm.nightShiftHours2(null);
      //vm.nightShiftOperation(null);
    }

    openDialogScreenC() {
      const vm = this;
      
      let params = {};
      vm.$window.modal('/view/ksm/007/c/index.xhtml', params).done((data) => {

      });
    }

    openDialogScreenD() {
      const vm = this;
      let params = {};
      vm.$window.modal('/view/ksm/007/d/index.xhtml', params).done((data) => {
        
      });
    }
  }


  export class HistoryItem {
    startDate: string;
    endDate: string;
    display: string;
    constructor(
      startDate: string,
      endDate: string,
      display: string) {      
        this.startDate = startDate;
        this.endDate = endDate;
        this.display = display ? display : startDate + '~' + endDate;
    }
  }
}