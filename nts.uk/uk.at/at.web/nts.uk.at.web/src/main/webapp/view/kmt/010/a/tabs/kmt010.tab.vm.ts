/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kmt010.a.tabs.viewmodel {

  @component({
    name: 'kmt010-tab-work',
    template: `<div class="kmt010-tab-work">
                <div class="mb-10 table">
                  <div class="cell valign-center" data-bind="ntsFormLabel: { required: false }">
                    <span data-bind="text: $vm.$i18n('KMT010_6')"></span>
                  </div>
                </div>
                <div class="button mb-10">
                  <button data-bind="text: $vm.$i18n('KMT010_7'), click: openDialogKDL012">1</button>
                  <button class="danger" data-bind="text: $vm.$i18n('KMT010_8'), click: deleteRowItem, enable: currentCodeList().length &gt; 0">2</button>
                </div> 						
                <div class="gridList">
                  <table id="multi-list" data-bind="ntsGridList: {
                    height: 265,
                    options: tabWorkSetting,
                    optionsValue: 'code',
                    primaryKey: 'code',
                    itemDraggable: false,
                    columns: ko.observableArray([
                      { headerText: $i18n('KMT010_10'), key: 'code', hidden: true },
                      { headerText: $i18n('KMT010_10'), key: 'display', width: 300, columnCssClass: 'limited-label', formatter: _.escape },
                      { headerText: $i18n('KMT010_11'), key: 'expireDate', width: 210, formatter: _.escape  }
                    ]),
                    multiple: true,
                    value: currentCodeList
                  }"></table>
                </div>
              </div>`
  })

  class WorkTab extends ko.ViewModel {
    isNewMode: ko.KnockoutObservable<boolean> = ko.observable(true);  
    currentCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
    tabWorkSetting: KnockoutObservableArray<WorkItem> = ko.observableArray([]);
    workFrameNoSelection: KnockoutObservable<number> = ko.observable(0);
    listOfWorkItems: KnockoutObservableArray<any>= ko.observableArray();
    indexTab: KnockoutObservable<number> = ko.observable(0);

    constructor(params: any) {
      super();
      const vm = this;
      
      if( params) {      
        //input from parent
        vm.indexTab(params.index);
        vm.workFrameNoSelection(params.workFrameNoSelection);           
        vm.listOfWorkItems(params.listOfWorkItems());
        //------------------------------------------------
        let tabWorkSetting: Array<WorkItem> = [];                      
        _.forEach(vm.listOfWorkItems()[vm.indexTab()](), (x) => {         
          tabWorkSetting.push( new WorkItem(x.code, x.name, x.startDate, x.endDate));
        });
        
        vm.tabWorkSetting(tabWorkSetting);
             
      }      
    }
    
    created(params: any) {
      const vm = this;      
    }

    openDialogKDL012() {
      const vm = this;
      //kdl012 input
      let selectionCodeList: Array<string> = [];
      _.forEach(vm.listOfWorkItems(), (x) => {
        selectionCodeList.push(x.code);
      })

      let params = {
        isMultiple: true, //選択モード single or multiple
        showExpireDate: true, //表示モード	show/hide expire date
        referenceDate: moment().format('YYYY/MM/DD'), //システム日付        
        workFrameNoSelection: vm.workFrameNoSelection(), //作業枠NO選択      
        selectionCodeList: selectionCodeList// 初期選択コードリスト
      }

      vm.$window.modal('/view/kdl/012/index.xhtml', params).done((data) => {
        if(data && data.setShareKDL012) {
      
          let newListOfWorkItems: Array<WorkItem> = [];
          _.forEach(data.setShareKDL012, (x) => {
            newListOfWorkItems.push(
              new WorkItem(x.code, x.name, x.startDate, x.endDate, x.remark)
            )
          });     

          //update model
          newListOfWorkItems = _.orderBy(newListOfWorkItems, ['code', 'asc']);
          vm.tabWorkSetting.removeAll();
          vm.tabWorkSetting(newListOfWorkItems);
          vm.listOfWorkItems()[vm.indexTab()](vm.tabWorkSetting());
          vm.listOfWorkItems.valueHasMutated();
        }
      });
    }

    deleteRowItem() {
      const vm = this;
      let newListOfWorkItems = _.filter(vm.tabWorkSetting(), (x) => { return !_.includes(vm.currentCodeList(), x.code) });
      vm.tabWorkSetting.removeAll();
      vm.tabWorkSetting(newListOfWorkItems);      
      vm.listOfWorkItems()[vm.indexTab()](vm.tabWorkSetting());
      vm.listOfWorkItems.valueHasMutated();
    }
  }

  export class ListItem {
    checked: KnockoutObservable<boolean> = ko.observable(false);
    rowId: number;
    name_code: string;
    expiration: string;

    constructor(checked: boolean, rowId: number, name_code: string, expiration: string) {
      this.checked(checked);
      this.rowId = rowId;
      this.name_code = name_code;
      this.expiration = expiration;
    }
  }

  export class WorkItem {
    code: string;
    name: string;
    display: string;
    expireDate: string;
    //remark: string;
    startDate: string;
    endDate: string;

    constructor(code?: string, name?: string, startDate?: string, endDate?: string, remark?: string) {
      this.code = code;
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
      this.display = code + (( !_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
      this.expireDate = startDate + ' ～ ' + endDate;
      //this.remark = remark;
    }
  }
}