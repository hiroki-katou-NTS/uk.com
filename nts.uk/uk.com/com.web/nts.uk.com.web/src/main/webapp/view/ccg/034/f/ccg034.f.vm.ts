/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.f {
  import getText = nts.uk.resource.getText;
  import model = nts.uk.com.view.ccg034.share.model;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    // System combo box
    selectedSystemType: KnockoutObservable<number> = ko.observable(0);
    systemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    // Menu list
    selectedMenuCode: KnockoutObservable<string> = ko.observable('');
    displayMenuName: KnockoutObservable<string> = ko.observable('');
    menuName: KnockoutObservable<string> = ko.observable('');
    menuList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    menuColumns: any[] = [
      { headerText: getText('CCG034_72'), key: 'code', width: 60 },
      { headerText: getText('CCG034_73'), key: 'name', width: 300 }
    ]
    // Common text attribute
    fontSize: KnockoutObservable<number> = ko.observable(1);
    isBold: KnockoutObservable<boolean> = ko.observable(false);
    horizontalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.HorizontalAlign.LEFT);
    verticalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.VerticalAlign.TOP);
    horizontalAlignList: ItemModel[] = [
      { code: '0', name: getText('CCG034_79') },
      { code: '1', name: getText('CCG034_80') },
      { code: '2', name: getText('CCG034_81') }
    ];
    verticalAlignList: ItemModel[] = [
      { code: '0', name: getText('CCG034_83') },
      { code: '1', name: getText('CCG034_84') },
      { code: '2', name: getText('CCG034_85') }
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