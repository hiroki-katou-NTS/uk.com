/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.a {
  
  import ComponentOption = kcp.share.list.ComponentOption;

  //===============================================
  const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";
  

  @bean()
  class ViewModel extends ko.ViewModel {
    
    // start variable of CCG001
    ccg001ComponentOption: GroupOption;
    // end variable of CCG001

    //panel left
    yearMonth: KnockoutObservable<number> =  ko.observable(202010);;

    //panel right
    rdgSelectedId: KnockoutObservable<number> = ko.observable(0);
    standardSelectedCode: KnockoutObservable<string> = ko.observable(null);
    freeSelectedCode: KnockoutObservable<string> = ko.observable(null);
    
    isEnableSelectedCode: KnockoutObservable<boolean> = ko.observable(true);
    zeroDisplayClassification: KnockoutObservable<number> = ko.observable(0);
    specifyingPageBreaks: KnockoutObservable<number> = ko.observable(0);

    constructor(params: any) {
      super();
      let vm = this;

      vm.rdgSelectedId.subscribe( (value) => {
        vm.isEnableSelectedCode( value === StandardOrFree.Standard );
      });
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;
    }
  }

  export enum StandardOrFree {
    Standard = 0,
    Free = 1
  }

  export enum DisplayClassification {
    Standard = 0,
    Free = 1
  }
  export enum SpecifyingPageBreaks {
    Standard = 0,
    Free = 1
  }
}