/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.g {

  const API = {
    
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    baseDate: KnockoutObservable<string> = ko.observable("");

    created(params?: any): void {
      const vm = this;
      vm.baseDate(params.baseDate || moment.utc().format("YYYY/MM/DD"));
    }

    mounted(): void {
      const vm = this;
      vm.$nextTick(() => $("#G1_2").focus());
    }

    public processExport() {

    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}
