/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.g {

  const API = {
    export: "com/file/approvalmanagement/workroot/approvers/export"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    baseDate: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD"));

    mounted(): void {
      const vm = this;
      vm.$nextTick(() => $("#G1_2").focus());
    }

    public processExport() {
      const vm = this;
      vm.$validate().then(isValid => {
        if (isValid) {
          const param = {
            baseDate: moment.utc(vm.baseDate(), "YYYY/MM/DD").toISOString()
          };
          vm.$blockui("grayout");
          nts.uk.request.exportFile(API.export, param)
          .then(() => vm.closeDialog())
          .always(() => vm.$blockui("clear"));
        }
      });
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}
