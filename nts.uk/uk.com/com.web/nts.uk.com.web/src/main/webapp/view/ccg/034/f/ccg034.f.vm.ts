/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.f {
  import getText = nts.uk.resource.getText;
  import model = nts.uk.com.view.ccg034.share.model;

  // URL API backend
  const API = {
    getMenuList: "sys/portal/standardmenu/findByMenuAndWebMenu" 
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    // System combo box
    selectedSystemType: KnockoutObservable<number> = ko.observable(0);
    systemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      { code: 0, name: "すべて" },
      { code: 1, name: "共通" },
      { code: 2, name: "就業" },
      { code: 3, name: "オフィスヘルパー" },
      { code: 4, name: "給与" },
      { code: 5, name: "人事" }
    ]);
    // Menu list
    selectedMenuCode: KnockoutObservable<string> = ko.observable('');
    displayMenuName: KnockoutObservable<string> = ko.observable('日別実績');
    menuName: KnockoutObservable<string> = ko.observable('日別実績確認');
    menuList: KnockoutObservableArray<Menu> = ko.observableArray([]);
    filteredMenuList: KnockoutObservableArray<Menu> = ko.observableArray([]);
    menuColumns: any[] = [
      { headerText: getText('CCG034_72'), key: 'code', width: 60 },
      { headerText: getText('CCG034_73'), key: 'name', width: 300 }
    ]
    // Common text attribute
    fontSize: KnockoutObservable<number> = ko.observable(14);
    isBold: KnockoutObservable<boolean> = ko.observable(false);
    horizontalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.HorizontalAlign.LEFT);
    verticalAlign: KnockoutObservable<number> = ko.observable(nts.uk.com.view.ccg034.share.model.VerticalAlign.TOP);
    horizontalAlignList: ItemModel[] = [
      { code: 0, name: getText('CCG034_79') },
      { code: 1, name: getText('CCG034_80') },
      { code: 2, name: getText('CCG034_81') }
    ];
    verticalAlignList: ItemModel[] = [
      { code: 0, name: getText('CCG034_83') },
      { code: 1, name: getText('CCG034_84') },
      { code: 2, name: getText('CCG034_85') }
    ];

    created(params: any) {

    }

    mounted() {
      const vm = this;
      vm.$blockui("grayout");

      vm.selectedMenuCode.subscribe(value => {
        const item = _.find(vm.menuList(), { code: value });
        if (item) {
          vm.displayMenuName(item.code + " " + "日別実績");
          vm.menuName("日別実績確認");

          //Revalidate
          $("#F6_2").trigger("validate");
        }
      });

      vm.selectedSystemType.subscribe(value => {
        if (Number(value) > 0) {
          vm.filteredMenuList(_.filter(vm.menuList(), { systemType: value-1 }));
        } else {
          vm.filteredMenuList(vm.menuList());
        }
      });
      vm.findMenuData();
    }

    private findMenuData() {
      const vm = this;
      vm.$ajax(API.getMenuList).done((data: any) => {
        vm.menuList(_.map(data, (menu: any) => { return { code: menu.code, name: menu.displayName, systemType: menu.system } }));
        vm.filteredMenuList(vm.menuList());
      }).always(() => vm.$blockui("clear"));
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
    code: number;
    name: string;
  }

  export interface Menu {
    code: string;
    name: string;
    systemType?: number;
  }
}