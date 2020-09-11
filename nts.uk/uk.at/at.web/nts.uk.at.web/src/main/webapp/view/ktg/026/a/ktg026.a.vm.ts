/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg029.a.viewmodel {
  import block = nts.uk.ui.block;
  import getText = nts.uk.resource.getText;
  import info = nts.uk.ui.dialog.info;

  @bean()
  export class ViewModel extends ko.ViewModel {
    year: KnockoutObservable<string> = ko.observable('');
    width: KnockoutObservable<number> = ko.observable(60);
    width2: KnockoutObservable<number> = ko.observable(70);
    textA1_5: KnockoutComputed<string>;
    
    created() {
      const vm = this;
      vm.year = ko.observable('2020');
      vm.textA1_5 = ko.computed(() => {
        return vm.$i18n('KTG027_6', [vm.year(), '2回']);
      });
    }

    mounted() {
      const vm = this;
    }


    // textA1_5(): string {
    //   const vm = this;
    //   return getText('KTG027_6', ['2020', 'abc']);
    // }
  }
}

