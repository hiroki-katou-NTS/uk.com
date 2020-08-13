/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmf002.w {

  @bean()
  export class CMF002WViewModel extends ko.ViewModel {
    // W1
    listPeriodSetting: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedPeriodSetting: KnockoutObservable<any> = ko.observable(null);
    // W2
    listStartDateSegment: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedStartDateSegment: KnockoutObservable<any> = ko.observable(null);
    // W4
    startDateAdjustment: KnockoutObservable<any> = ko.observable(null);
    // W8
    baseDateSpecified: KnockoutObservable<any> = ko.observable(null);

    constructor() {
      super();
      const vm = this;
      vm.listPeriodSetting = ko.observableArray([
        { code: '1', name: nts.uk.resource.getText("CMF002_275") },
        { code: '2', name: nts.uk.resource.getText("CMF002_276") },
      ]);
    }

    mounted() {
      const params = nts.uk.ui.windows.getShared('CMF002_W_PARAMS');
      const vm = this;
      vm.selectedPeriodSetting('1');
    }

    /**
     * Close dialog
     */
    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

  }

  // export class ListType {
  //   static EMPLOYMENT = 1;
  //   static Classification = 2;
  //   static JOB_TITLE = 3;
  //   static EMPLOYEE = 4;
  // }

  // export class SelectType {
  //   static SELECT_BY_SELECTED_CODE = 1;
  //   static SELECT_ALL = 2;
  //   static SELECT_FIRST_ITEM = 3;
  //   static NO_SELECT = 4;
  // }

  // class KDL017TableModel {
  //   expirationDate: string;
  //   digestionNumber: string;
  //   digestionDate: string;
  //   digestionHour: string;
  //   occurrenceNumber: string;
  //   occurrenceDate: string;
  //   occurrenceHour: string;

  //   constructor(init?: Partial<KDL017TableModel>) {
  //     (<any>Object).assign(this, init);
  //   }
  // }
}
