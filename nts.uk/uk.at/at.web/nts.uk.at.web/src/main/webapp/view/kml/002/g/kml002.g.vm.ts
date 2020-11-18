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
    countingType: KnockoutObservable<number> = ko.observable(0);   

    limitedItems: KnockoutObservable<string> = ko.observable(null);
    limitedNumber: KnockoutObservable<number> = ko.observable(9999);
    constructor(params: any) {
      super();
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n('KML002_111'), key: 'number', width: 50, formatter: _.escape },
        { headerText: vm.$i18n('KML002_112'), key: 'name', width: 100, formatter: _.escape },
      ]);

      vm.$window.storage('KWL002_SCREEN_G_INPUT').then((data) => {
        if(!_.isNil(data)) {
          vm.countingType(data.countingType);
          if( data.countingType > 0 ) {
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
    }

    mounted() {
      const vm = this;      
      $('#swapList-gridArea1').attr('tabindex', '-1').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.storage('KWL002_SCREEN_G_OUTPUT', null).then(() => {
        vm.$window.close();
      });
    }

    createSelectableItems( listItems: any) {
      const vm = this;
      var array = [];
      _.forEach(listItems, (x) => {
        array.push(new ItemModel(x.number, x.name));
      });
      vm.selectableItems(array);
    }

    proceed() {
      const vm = this;

      //G4_1「選択可能な項目」で項目はなにもない場合。
      if (vm.selectableItems().length <= 0 && vm.currentCodeListSwap().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_37' }).then(() => {
          vm.$window.close();
        })
        return;
      }

      if (vm.currentCodeListSwap().length > vm.limitedNumber() || vm.currentCodeListSwap().length <= 0) {
        let msgId = vm.currentCodeListSwap().length > vm.limitedNumber() ? 'Msg_1837' : 'Msg_1817';
        //「選択された対象項目」で回数集計項目は10項目以上に選択られた。 > 10
        //「選択された対象項目」でなにもない。 = 0
        vm.$dialog.error({ messageId: msgId }).then(() => {
          $('#swapList-gridArea2').attr('tabindex', '-1').focus();
        })
        return;
      }
      //エラーがない場合 - 回数集計情報を登録する
      let selectedNoList = [];
      _.forEach(vm.currentCodeListSwap(), (x) => {
        selectedNoList.push(x.number);
      });

      let params = {
        "type" : vm.countingType(),
	      "selectedNoList": selectedNoList
      };

      vm.$blockui('show');
      vm.$ajax(PATH.timeNumberCounterRegister, params).done(() => {
        vm.$window.storage('KWL002_SCREEN_G_OUTPUT', vm.currentCodeListSwap()).then(() => {
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            vm.$blockui('show');
            vm.$window.close();
          });
        });
      }).fail().always(() => vm.$blockui('hide'));      
    }    

    getTimeNumberCounter() {
      const vm = this;      
      vm.$blockui('show');
      vm.$ajax( PATH.timeNumberCounterGetInfo, { countType :  vm.countingType() }).done((data) => {   
        console.log(data);         
        if(!_.isNil(data)) {          
          if(data.countNumberOfTimeDtos.length > 0) {
            vm.createSelectableItems(data.countNumberOfTimeDtos);            
          } else {
            vm.$dialog.error({ messageId: 'Msg_37' }).then(() => {
              vm.$blockui('hide');
              vm.$window.close();
            });
          }
          
          if(data.numberOfTimeTotalDtos.length > 0) {
            vm.currentCodeListSwap(data.numberOfTimeTotalDtos);            
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