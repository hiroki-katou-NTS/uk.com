/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt001.a {
  @bean()
  class ViewModel extends ko.ViewModel {

    selectedCode: KnockoutObservable<string> = ko.observable(null);
    currentCodeList: KnockoutObservable<string> = ko.observable(null);
    workList: KnockoutObservableArray<any> = ko.observableArray(null);
    externalCodeList: KnockoutObservableArray<any> = ko.observableArray([]);

    model: KnockoutObservable<ModelItem> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;

      vm.workList([
        { code: 'A0000000001', name: 'Text 1' },
        { code: 'A0000000002', name: 'Text 2' },
        { code: 'A0000000003', name: 'Text 3' },
      ]);

      vm.externalCodeList([
        { code: 'KMT001_36', value: ko.observable('Text 1') },
        { code: 'KMT001_37', value: ko.observable('Text 2') },
        { code: 'KMT001_38', value: ko.observable('Text 3') },
        { code: 'KMT001_39', value: ko.observable('Text 4') },
        { code: 'KMT001_40', value: ko.observable('Text 5') },
      ]);

      let externalCode: any = [
        ko.observable('ド1'), ko.observable('ド2'), ko.observable('ド3'), ko.observable('ド4'), ko.observable('ド5')
      ];
      
      vm.model(new ModelItem('code', 'name', 'Abname', '#ff0000', '2021/02/17', '9999/12/31', 'Remarks', externalCode));
      console.log(vm.model());
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    AddNewWork() {

    }

    RegistrationWork() {

    }

    DeleteWork() {

    }
  }

  export class ModelItem {
    code: KnockoutObservable<string> = ko.observable(null);
    name: KnockoutObservable<string> = ko.observable(null);
    abbreviatedName: KnockoutObservable<string> = ko.observable(null);
    color: KnockoutObservable<string> = ko.observable(null);
    expStartDate: KnockoutObservable<string> = ko.observable(null);
    expEndDate: KnockoutObservable<string> = ko.observable(null);
    remarks: KnockoutObservable<string> = ko.observable(null);
    externalCode: Array<string> = [];

    constructor(code?: string, name?: string, abbreviatedName?: string, color?: string,
      expStartDate?: string, expEndDate?: string, remarks?: string, externalCode?: Array<string>) {
      this.code(code);
      this.name(name);
      this.abbreviatedName(abbreviatedName);
      this.color(color);
      this.expStartDate(expStartDate);
      this.expEndDate(expEndDate);
      this.remarks(remarks);
      this.externalCode = externalCode;
    }
  }
}