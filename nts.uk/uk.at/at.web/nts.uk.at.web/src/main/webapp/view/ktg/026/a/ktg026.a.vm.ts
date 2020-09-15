/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg029.a.viewmodel {
  import block = nts.uk.ui.block;

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

      vm.year.subscribe(year => {
        
      })
    }



  }
}

