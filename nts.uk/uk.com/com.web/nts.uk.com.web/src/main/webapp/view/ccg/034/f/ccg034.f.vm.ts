/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.f {
  import getText = nts.uk.resource.getText;
  import model = nts.uk.com.view.ccg034.share.model;
  import CCG034D = nts.uk.com.view.ccg034.d;

  // URL API backend
  const API = {
    getMenuList: "sys/portal/standardmenu/findByMenuAndWebMenu"
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    //Data
    partData: CCG034D.PartDataMenu = null;
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
    menuCode: KnockoutObservable<string> = ko.observable('');
    menuClassification: KnockoutObservable<number> = ko.observable(0);
    menuSystemType: KnockoutObservable<number> = ko.observable(0);
    menuList: KnockoutObservableArray<Menu> = ko.observableArray([]);
    filteredMenuList: KnockoutObservableArray<Menu> = ko.observableArray([]);
    menuColumns: any[] = [
      { headerText: '', key: 'id', hidden: true },
      { headerText: getText('CCG034_72'), key: 'code', width: 60 },
      { headerText: getText('CCG034_73'), key: 'name', width: 300 }
    ]
    // Common text attribute
    fontSize: KnockoutObservable<number> = ko.observable(11);
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
      const vm = this;
      vm.partData = params;
    }

    mounted() {
      const vm = this;
      // Binding part data
      vm.horizontalAlign(vm.partData.alignHorizontal);
      vm.verticalAlign(vm.partData.alignVertical);
      vm.menuName(vm.partData.menuName);
      vm.menuCode(vm.partData.menuCode);
      vm.menuClassification(vm.partData.menuClassification);
      vm.menuSystemType(vm.partData.systemType);
      vm.fontSize(vm.partData.fontSize);
      vm.isBold(vm.partData.isBold);

      vm.selectedMenuCode.subscribe(value => {
        const item = _.find(vm.menuList(), { id: value });
        if (item) {
          vm.displayMenuName(item.code + " " + item.name);
          vm.menuName(item.name);
          vm.menuCode(item.code);
          vm.menuClassification(item.menuClassification);
          //Revalidate
          vm.$validate("#F6_2")
        }
      });

      vm.selectedSystemType.subscribe(value => {
        if (Number(value) > 0) {
          vm.filteredMenuList(_.filter(vm.menuList(), { systemType: value - 1 }));
        } else {
          vm.filteredMenuList(vm.menuList());
        }
      });
      vm.findMenuData();
    }

    private findMenuData() {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(API.getMenuList)
        .then((data: any) => {
          vm.menuList(_.map(data, (menu: any) => ({
            id: nts.uk.util.randomId(),
            code: menu.code,
            name: menu.displayName,
            systemType: menu.system,
            menuClassification: menu.classification
          })));
          vm.filteredMenuList(vm.menuList());
        })
        .always(() => vm.$blockui("clear"));
    }

    /**
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
    * Update part data and close dialog
    */
    public updatePartDataAndCloseDialog() {
      const vm = this;
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          // Update part data
          vm.partData.alignHorizontal = vm.horizontalAlign();
          vm.partData.alignVertical = vm.verticalAlign();
          vm.partData.menuCode = vm.menuCode();
          vm.partData.menuName = vm.menuName();
          vm.partData.menuClassification = vm.menuClassification();
          vm.partData.systemType = vm.menuSystemType();
          vm.partData.fontSize = Number(vm.fontSize());
          vm.partData.isBold = vm.isBold();
          // Return data
          vm.$window.close(vm.partData);
        }
      });
    }
  }

  export interface ItemModel {
    code: number;
    name: string;
  }

  export interface Menu {
    id?: string;
    code: string;
    name: string;
    systemType: number;
    menuClassification: number;
  }
}