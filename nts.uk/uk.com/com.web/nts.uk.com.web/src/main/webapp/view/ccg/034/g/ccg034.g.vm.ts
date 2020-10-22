/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.g {
  import getText = nts.uk.resource.getText;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    // Link name
    linkName: KnockoutObservable<string> = ko.observable('');
    // URL
    url: KnockoutObservable<string> = ko.observable('');
    // Common text attribute
    fontSize: KnockoutObservable<number> = ko.observable(1);
    isBold: KnockoutObservable<boolean> = ko.observable(false);
    horizontalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.HorizontalAlign.LEFT);
    verticalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.VerticalAlign.TOP);
    horizontalAlignList: ItemModel[] = [
      { code: '0', name: getText('CCG034_94') },
      { code: '1', name: getText('CCG034_95') },
      { code: '2', name: getText('CCG034_96') }
    ];
    verticalAlignList: ItemModel[] = [
      { code: '0', name: getText('CCG034_98') },
      { code: '1', name: getText('CCG034_99') },
      { code: '2', name: getText('CCG034_100') }
    ];

    created(params: any) {

    }

    mounted() {
      const vm = this;

    }


    /**
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export interface ItemModel {
    code: string;
    name: string;
  }
}