/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl012 {
  
  import getShared = nts.uk.ui.windows.getShared;

  const CONCAT_DATE = ' ～ ';
  const PATH = {
    getWorkFrame: ''
  };

  @bean()
  class ViewModel extends ko.ViewModel {
    items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    currentCode: KnockoutObservable<any> = ko.observable(null);
    currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
    header: KnockoutObservableArray<any> = ko.observableArray([]);    
    
    //input from parent screen
    isMultiple: boolean = true;    
    isShowExpireDate: KnockoutObservable<boolean> = ko.observable(false);
    selectionCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
    referenceDate: KnockoutObservable<string> = ko.observable();
    workFrameNoSelection: KnockoutObservable<number> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;
   
      let object: any = getShared('KDL012') ? getShared('KDL012') : params;

      if( object ) {
        vm.isMultiple = object.isMultiple; //選択モード single or multiple
        vm.isShowExpireDate(object.showExpireDate); //表示モード	show/hide expire date
        vm.referenceDate(object.referenceDate ? object.referenceDate : moment().format('YYYY/MM/DD')); //システム日付        
        vm.workFrameNoSelection(object.workFrameNoSelection);//作業枠NO選択        
        vm.selectionCodeList(object.selectionCodeList);
        vm.currentCodeList(object.selectionCodeList); //初期選択コードリスト
      }      

      if( vm.isShowExpireDate() ) {
        vm.header([
          { headerText: vm.$i18n('KDL012_4'), prop: 'code', width: 200 },
          { headerText: vm.$i18n('KDL012_5'), prop: 'name', width: 330, columnCssClass: 'limited-label', formatter: _.escape  },
          { headerText: vm.$i18n('KDL012_6'), prop: 'expireDate', width: 205 }, 
          { headerText: vm.$i18n('KDL012_7'), prop: 'remark', width: vm.isMultiple ? 200 : 215, formatter: _.escape, columnCssClass: 'limited-label' }
        ]);
      } else  {
        vm.header([
          { headerText: vm.$i18n('KDL012_4'), prop: 'code', width: 200 },
          { headerText: vm.$i18n('KDL012_5'), prop: 'name', width: 350, columnCssClass: 'limited-label', formatter: _.escape  },          
          { headerText: vm.$i18n('KDL012_7'), prop: 'remark', width: vm.isMultiple ? 380 : 405, formatter: _.escape, columnCssClass: 'limited-label' }
        ]);
      }        

      //get data from api
      vm.getWorkFrameSetting();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    proceed() {
      const vm = this;

      let selectionList: Array<any> = _.filter(vm.items(), (x) => { return _.includes(vm.currentCodeList(), x.code)});      
      
      if( selectionList.length === 0 ) {
        vm.$dialog.error({ messageId: 'Msg_1629'}).then(() => { });
      } else {
        //using for CDL023
        let currentCodeList: Array<string> = [];
        _.forEach(selectionList, (x: any) => { currentCodeList.push(x.code); });
        nts.uk.ui.windows.setShared('currentCodeList', currentCodeList); 
        //new
        vm.$window.close({ setShareKDL012: selectionList });
      }
    }

    closeDialog() {
      const vm = this;
      nts.uk.ui.windows.setShared('KDL012Cancel', null); //using for CDL023
      vm.$window.close({ setShareKDL012: null });
    }

    getWorkFrameSetting() {
      const vm = this;
      for (var i = 1; i < 50; i++) {
        var code = i < 10 ? '0' + i : i;
        vm.items.push(new ItemModel(
          'B0000000000000000000' + code, 
          '作業を選択してください ' + code,           
          '2021/12/20',
          '9999/12/31', 
          '作業を選択してください')
        );
      }

      /* vm.$blockui('show');
      vm.$ajax(PATH.getWorkFrame ).done((data) => {        
        if( data ) {
          
        }
        
        vm.$blockui('hide');

      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      }); */

    }
  }

  class ItemModel {
    code: string | number;
    name: string;
    startDate: string;
    endDate: string;
    expireDate: string;
    remark: string;
    constructor(code: string | number, name: string, startDate?: string, endDate?: string, remark?: string) {
      this.code = code;
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
      this.expireDate = startDate + ' ～ ' + endDate;
      this.remark = remark;
    }
  }
}