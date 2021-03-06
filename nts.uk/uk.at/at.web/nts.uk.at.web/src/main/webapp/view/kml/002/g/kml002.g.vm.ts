/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.g {

  const PATH = {
    timeNumberCounterGetInfo: 'screen/at/kml002/g/getInfo',
    timeNumberCounterRegister: 'ctx/at/schedule/budget/timeNumberCounter/register'
  }

  @bean()
  class ViewModel extends ko.ViewModel {
    selectableItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    currentCodeListSwap: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

    columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
    gridHeight: KnockoutObservable<number> = ko.observable(285);
    countingType: KnockoutObservable<number> = ko.observable(0);

    limitedItems: KnockoutObservable<string> = ko.observable(null);
    limitedNumber: KnockoutObservable<number> = ko.observable(9999);
    isNumberCounterSetting: KnockoutObservable<any> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n('KML002_111'), key: 'number', width: 50, formatter: _.escape },
        { headerText: vm.$i18n('KML002_112'), key: 'name', width: 100, formatter: _.escape },
      ]);

      vm.$window.storage('KML002_SCREEN_G_INPUT').then((data) => {
        if (!_.isNil(data)) {
          vm.countingType(data.countingType);
          if (data.countingType > 0) {
            vm.limitedItems(vm.$i18n('KML002_114'));
            vm.limitedNumber(10);
          }
          vm.getTimeNumberCounter();
        }
      });
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });

      const userAgent = window.navigator.userAgent;
      let msie = userAgent.match(/Trident.*rv\:11\./);
      if (!_.isNil(msie) && msie.index > -1) {
        vm.gridHeight(290);
      }
    }

    mounted() {
      const vm = this;
      $("#swapList-grid1").igGrid("container").focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.storage('KML002_SCREEN_G_OUTPUT', { setting: vm.isNumberCounterSetting() }).then(() => {
        vm.$window.close();
      });
    }

    createSelectableItems(listItems: any) {
      const vm = this;
      var array = [];
      _.forEach(listItems, (x) => {
        array.push(new ItemModel(x.number, x.name));
      });
      vm.selectableItems(array);
    }

    proceed() {
      const vm = this;

      //G4_1???????????????????????????????????????????????????????????????
      if (vm.selectableItems().length <= 0 && vm.currentCodeListSwap().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_37' }).then(() => {
          vm.closeDialog();
        })
        return;
      }

      if (vm.currentCodeListSwap().length > vm.limitedNumber() || vm.currentCodeListSwap().length <= 0) {
        let msgId = vm.currentCodeListSwap().length > vm.limitedNumber() ? 'Msg_1837' : 'Msg_1817';
        //?????????????????????????????????????????????????????????10????????????????????????????????? > 10
        //?????????????????????????????????????????????????????? = 0
        vm.$dialog.error({ messageId: msgId }).then(() => {
          //$('#swapList-gridArea2').attr('tabindex', '-1').focus();
        })
        return;
      }
      //???????????????????????? - ?????????????????????????????????
      let selectedNoList = [];
      _.forEach(vm.currentCodeListSwap(), (x) => {
        selectedNoList.push(x.number);
      });

      let params = {
        "type": vm.countingType(),
        "selectedNoList": selectedNoList
      };

      vm.$blockui('show');
      vm.$ajax(PATH.timeNumberCounterRegister, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.isNumberCounterSetting(true);
          vm.getTimeNumberCounter();
          vm.$blockui('hide');
        });
      }).fail().always(() => vm.$blockui('hide'));
    }

    getTimeNumberCounter() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(PATH.timeNumberCounterGetInfo, { countType: vm.countingType() }).done((data) => {
        if (!_.isNil(data)) {
          if (data.countNumberOfTimeDtos.length > 0) {
            vm.createSelectableItems(data.countNumberOfTimeDtos);
            
            if (data.numberOfTimeTotalDtos.length > 0) {
              vm.currentCodeListSwap(data.numberOfTimeTotalDtos);
              vm.isNumberCounterSetting(true);
            }
          } else {
            vm.$dialog.error({ messageId: 'Msg_37' }).then(() => {
              vm.closeDialog();
            });
          }
        }
      })
        .fail()
        .always(() => vm.$blockui('hide'));
    }
  }

  export class ItemModel {
    number: number;
    name: string;
    constructor(code: number, name: string) {
      this.number = code;
      this.name = name;
    }
  }
}