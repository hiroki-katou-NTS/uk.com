/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.c {
  //import common = nts.uk.at.view.kwr003.common; 
  const KWR003_OUTPUT = 'KWR003_OUTPUT';
  const KWR003_B13 = 'KWR003_B_DATA';
  const KWR003_C_INPUT = 'KWR003_C_DATA';
  const KWR003_C_OUTPUT = 'KWR003_C_RETURN';

  @bean()
  class ViewModel extends ko.ViewModel {
    
    oldCode: KnockoutObservable<string> = ko.observable();
    oldName: KnockoutObservable<string> = ko.observable();

    newCode: KnockoutObservable<string> = ko.observable();
    newName: KnockoutObservable<string> = ko.observable();

    constructor(params: any) {
      super();
      let vm = this;

      vm.$window.storage(KWR003_C_INPUT).then((data) => {
        if( !_.isNil(data)) {
          vm.oldCode(data.code);
          vm.oldName(data.name);
          let latsCode: any = !_.isNil(data.latsCode) ? parseInt(data.latsCode) + 1 : '0001';
          vm.newCode(latsCode);
          vm.newName(data.name + '_' + latsCode);
        }
      });      
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;

      $('#KWR003_C23').focus();
    }

    proceed() {
      let vm = this;
     /*  データが先に削除された ＃Msg_1903		
      コードの重複 ＃Msg_1753	 */	

      vm.$window.storage(KWR003_C_OUTPUT, { code: vm.newCode(), name: vm.newName()});
      vm.$window.close();
    }

    cancel() {
      let vm = this;
      vm.$window.storage(KWR003_C_OUTPUT, null);
      vm.$window.close();
    }
  }
}