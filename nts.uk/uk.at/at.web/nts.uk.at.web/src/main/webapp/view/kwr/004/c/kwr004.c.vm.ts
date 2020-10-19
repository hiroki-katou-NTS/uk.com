/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr004.c {

  const KWR004_OUTPUT = 'KWR004_OUTPUT';
  const KWR004_B13 = 'KWR004_B_DATA';
  const KWR004_C_INPUT = 'KWR004_C_DATA';
  const KWR004_C_OUTPUT = 'KWR004_C_RETURN';

  @bean()
  class ViewModel extends ko.ViewModel {
    
    oldCode: KnockoutObservable<string> = ko.observable();
    oldName: KnockoutObservable<string> = ko.observable();

    newCode: KnockoutObservable<string> = ko.observable();
    newName: KnockoutObservable<string> = ko.observable();

    constructor(params: any) {
      super();
      let vm = this;

      vm.$window.storage(KWR004_C_INPUT).then((data) => {        
        if( !_.isNil(data)) {
          vm.oldCode(data.code);
          vm.oldName(data.name);

          vm.newCode(data.code);
          vm.newName(data.name);
        }
      });
      
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;

      $('#KWR004_C23').focus();
    }

    proceed() {
      let vm = this;
      vm.$window.storage(KWR004_C_OUTPUT, { code: vm.newCode(), name: vm.newName()});
      vm.$window.close();
    }

    cancel() {
      let vm = this;
      vm.$window.storage(KWR004_C_OUTPUT, null);
      vm.$window.close();
    }
  }
}