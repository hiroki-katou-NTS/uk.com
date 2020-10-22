/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.f {

  @bean()
  class ViewModel extends ko.ViewModel {    

    startTime: KnockoutObservable<number> = ko.observable(13*60); //13*60 -> 13pm
    endTime: KnockoutObservable<number> = ko.observable(16*60); //16*60 -> 6pm

    option: any = new nts.uk.ui.option.TimeEditorOption({});

    constructor(params: any) {
      super();
      const vm = this;  
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted(params: any) {
      const vm = this;
    }

    proceed() {
      const vm = this;
      vm.$window.close();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

  }
}