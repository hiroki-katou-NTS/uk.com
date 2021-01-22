/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.d.screenModel {

  const API = {
    getFavoriteInformation: "screen/com/ccg005/get-favorite-information",
    save: "ctx/office/favorite/save",
    delete: "ctx/office/favorite/delete"
  };
  @bean()
  export class ViewModel extends ko.ViewModel {
    //favorite
    favoriteList: KnockoutObservableArray<FavoriteSpecifyData> = ko.observableArray([]);
    favoriteName: KnockoutObservable<string> = ko.observable("");
    selectedFavoriteOrder: KnockoutObservable<number> = ko.observable();
    selectedFavorite: KnockoutObservable<FavoriteSpecifyData> = ko.observable();
    //work place name
    choosenWkspNames: KnockoutObservableArray<string> = ko.observableArray([]);
    displayChoosenWkspName: KnockoutObservable<string> = ko.computed(() => {
      return this.choosenWkspNames().join('、');
    });
    //target select
    roundingRules: KnockoutObservableArray<any>;
    selectedRuleCode: KnockoutObservable<number> = ko.observable(TargetSelection.WORKPLACE);
    buttonSelectRequire: KnockoutObservable<boolean> = ko.computed(() => this.selectedRuleCode() === 1);
    displayChoosenWkspNameRequire: KnockoutObservable<boolean> = ko.computed(() => this.selectedRuleCode() === 0);
    //grid column
    columns: KnockoutObservableArray<any>;
    //mode
    mode: KnockoutObservable<number> = ko.observable();
    created() {
      const vm = this;
      vm.callData();
      vm.columns = ko.observableArray([
        { headerText: "id", key: "order", width: 150, hidden: true },
        { headerText: vm.$i18n("CCG005_11"), key: "favoriteName", width: 150 },
      ]);

      vm.roundingRules = ko.observableArray([
        { code: TargetSelection.WORKPLACE, name: vm.$i18n("CCG005_14") },
        { code: TargetSelection.AFFILIATION_WORKPLACE, name: vm.$i18n("CCG005_16") }
      ]);
    }

    mounted() {
      const vm = this;
      if (vm.favoriteList()) {
        vm.selectedFavoriteOrder(0);
      }
      vm.selectedFavoriteOrder.subscribe((order: number) => {
        vm.$errors("clear");
        vm.mode(Mode.UPDATE);
        const currentFavor = _.find(vm.favoriteList(), (item => Number(item.order) === Number(order)));
        if (currentFavor) {
          vm.selectedFavorite(currentFavor);
          vm.favoriteName(currentFavor.favoriteName);
          vm.selectedRuleCode(currentFavor.targetSelection);
          vm.choosenWkspNames(currentFavor.wkspNames);
        }
      });
    }

    private callData() {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(API.getFavoriteInformation).then((data: FavoriteSpecifyData[]) => {
        vm.favoriteList(data);
        vm.$blockui("clear");
      })
    }

    public onClickCancel() {
      this.$window.close();
    }

    public createNewFavorite() {
      const vm = this;
      vm.mode(Mode.INSERT);
      vm.favoriteName("");
      vm.choosenWkspNames([]);
      $("#D5_1").focus();
    }

    public saveFavorite() {
      const vm = this;
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          //re set order for item
          _.map(vm.favoriteList(), (item, index) => {
            item.order = index;
          });
          //new item
          if (vm.mode() === Mode.INSERT) {
            const favoriteSpecify = new FavoriteSpecifyData({
              favoriteName: vm.favoriteName(),
              creatorId: __viewContext.user.employeeId,
              inputDate: moment.utc().toISOString(),
              targetSelection: vm.selectedRuleCode(),
              workplaceId: vm.selectedRuleCode() === TargetSelection.WORKPLACE ? vm.choosenWkspNames() : [],
              order: vm.favoriteList()[vm.favoriteList().length - 1].order + 1,
              wkspNames: vm.choosenWkspNames()
            });
            vm.favoriteList.push(favoriteSpecify);
            //Update UI
            vm.selectedFavoriteOrder(favoriteSpecify.order);
            vm.mode(Mode.UPDATE);
          } else {
            _.map(vm.favoriteList(), (item => {
              if(item.order === Number(vm.selectedFavorite())) {
                item.favoriteName = vm.favoriteName();
                item.targetSelection = vm.selectedRuleCode();
                item.workplaceId = vm.selectedRuleCode() === TargetSelection.WORKPLACE ? vm.choosenWkspNames() : [];
              }
            }));
          }
          //Call API
          vm.$blockui("grayout");
          vm.$ajax(API.save, vm.favoriteList()).then(() => {
            vm.callData();
            vm.$blockui("clear");
            return vm.$dialog.info({ messageId: "Msg_15" });
          });
        }
      });
    }

    public deleteFavorite() {
      const vm = this;
      vm.$dialog.confirm({ messageId: "Msg_18" }).then((result) => {
        if (result === "yes") {
          const currentFavor = _.find(vm.favoriteList(), (item => Number(item.order) === Number(vm.selectedFavorite())));
          if (currentFavor) {
            const favoriteSpecify = new FavoriteSpecifyDelCommand({
              creatorId: currentFavor.creatorId,
              inputDate: currentFavor.inputDate
            });
            vm.$blockui("grayout");
            vm.$ajax(API.delete, favoriteSpecify).then(() => {
              //set selected to 0 when delete
              if(vm.favoriteList()) {
                vm.selectedFavoriteOrder(0);
              }
              vm.callData();
              vm.$blockui("clear");
              return vm.$dialog.info({ messageId: "Msg_16" });
            });
          }
        }
      });
    }
  }

  enum TargetSelection {
    // 職場
    WORKPLACE = 0,

    // 所属職場
    AFFILIATION_WORKPLACE = 1
  }

  enum Mode {
    INSERT = 0,
    UPDATE = 1
  }
  class FavoriteSpecifyData {

    // お気に入り名
    favoriteName: string;

    // 作��D
    creatorId: string;

    // 入力日
    inputDate: string;

    // 対象選�
    targetSelection: number;

    // 職場ID
    workplaceId: string[];

    // 頺
    order: number;

    wkspNames: string[];

    constructor(init?: Partial<FavoriteSpecifyData>) {
      $.extend(this, init);
    }
  }

  class FavoriteSpecifyDelCommand {
    // 作��D
    creatorId: string;

    // 入力日
    inputDate: string;

    constructor(init?: Partial<FavoriteSpecifyDelCommand>) {
      $.extend(this, init);
    }
  }
}
