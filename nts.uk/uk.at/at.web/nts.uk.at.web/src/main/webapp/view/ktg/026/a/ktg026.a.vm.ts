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
    
    created() {

    }

    mounted() {

    }
  }
}

