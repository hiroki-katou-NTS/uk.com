/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt09.a {

  const PATH = {
    saveRegistrationWork: '',
    deleteRegistrationWork: '',
    getRegistrationWork: '',
  };

  const registered = '<i class="icon icon icon-78" style="background: url(\'/nts.uk.com.web/view/kcp/share/icon/icon78.png\');"></i>';

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedWorkCode: KnockoutObservable<string> = ko.observable(null);
    currentCode: KnockoutObservable<string> = ko.observable(null);
    registrationWorkList: KnockoutObservableArray<WorkItem> = ko.observableArray([]);
    currentCodeList: KnockoutObservableArray<any> = ko.observableArray(null);
    externalCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
    externalCode: Array<any> = [];
    workList: KnockoutObservable<any> = ko.observable([]);
    gobackLink: KnockoutObservable<string> = ko.observable('#');
    model: KnockoutObservable<ModelItem> = ko.observable(null);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    listOfRefinedItems: KnockoutObservableArray<RefinedItem> = ko.observableArray([]);
    selectionCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
    listRoleType: Array<any> = []; 
    currentDate: string = moment(new Date()).format('YYYY/MM/DD');

    constructor(params: any) {
      super();
      const vm = this;

      vm.externalCodeList([
        { code: 'KMT001_36', value: ko.observable(null) },
        { code: 'KMT001_37', value: ko.observable(null) },
        { code: 'KMT001_38', value: ko.observable(null) },
        { code: 'KMT001_39', value: ko.observable(null) },
        { code: 'KMT001_40', value: ko.observable(null) },
      ]);

      vm.listRoleType = __viewContext.enums.RoleType;
      
      vm.selectedWorkCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        vm.loadRegistrationWork();
      });

      vm.currentCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        if( newValue === null) return;
        vm.loadRegistrationWork();
      });


      //init mode      
      vm.model(new ModelItem());
      vm.getWorkList(); //作業
      vm.getRegistrationWorkList();   //作業一覧
      //vm.addNewRegistrationWork();
           
      //condition 1 - ※１
      //vm.gobackLink('....');
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    addNewRegistrationWork() {
      const vm = this;

      vm.externalCode = [
        ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null)
      ];
      vm.model().update(null, null, vm.currentDate, '9999/12/31', []);

      vm.isNewMode(true);
      $('#KMT009_13').focus();
    }

    saveRegistrationWork() {
      const vm = this;
      /*  vm.$blockui('show');
      vm.$ajax(PATH.saveRegistrationWork, { parrams: null }).done(() => {
        vm.$dialog.info({messageId: 'Mgs_15'}).then(() => {
          vm.getWorkList();
          vm.$blockui('hide');
        });
      }).fail((error) => {
        vm.$dialog.error({messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      }); */

      let index = _.findIndex(vm.registrationWorkList(), (x) => { return x.code === vm.currentCode()});
      if( index > -1 ) {
        vm.registrationWorkList()[index].configured = registered;     
        vm.registrationWorkList.valueHasMutated();
      }   

    }

    deleteRegistrationWork() {
      const vm = this;

      /*  vm.$blockui('show');
      let params = {
        workFrameNo: vm.selectedWorkCode(), //作業枠名						
        workCode: vm.model().code()//作業コード						
      }
      vm.$ajax(PATH.getRegistrationWork, { workId: vm.currentCode() }).done((data) => {
        vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
          //vm.getNextPreviousItem(vm.currentCode());
          vm.getRegistrationWorkList();

          vm.$blockui('hide');
        });
      }).fail((error) => {
        vm.$dialog.error({messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      }); */
      vm.$dialog.confirm({ messageId: 'Msg_18'}).then((result) => {
        if( result === 'yes') {
          vm.model().listOfRefinedItems.removeAll();
          vm.isNewMode(true);

          let index = _.findIndex(vm.registrationWorkList(), (x) => { return x.code === vm.currentCode()});
          if( index > -1 ) {
            vm.registrationWorkList()[index].configured = null;     
            vm.registrationWorkList.valueHasMutated();
          }   
        }
      })      
    }

    loadRegistrationWork() {
      const vm = this;
      /*vm.$blockui('show');
      vm.$ajax(PATH.getRegistrationWork, { workId: value }).done((data) => {
        if(data) {          
          vm.model().update(data);
          vm.isNewMode(false);
        }
        vm.$blockui('hide');
      }).fail((error) => {
        vm.$dialog.error({messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      }); */
      
      vm.getListOfRefinedItems();
      vm.isNewMode( !(vm.listOfRefinedItems().length > 0 ));

      let currentObj = _.find(vm.registrationWorkList(), (x) => { return x.code === vm.currentCode()});
      if( currentObj ) {
        vm.model().update(currentObj.code, currentObj.name, '2002/12/31', '9999/12/31', vm.listOfRefinedItems());            
      }      
    }

    getRegistrationWorkList() {
      const vm = this;
      let testWorkItems = [
        { code: 'A0000000000000000001', name: '作業 1', configured: registered },
        { code: 'A0000000000000000002', name: '作業 2', configured: null },
        { code: 'A0000000000000000003', name: '作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1', configured: registered },
      ];

      _.forEach(testWorkItems, (x) => {
        vm.registrationWorkList.push(new WorkItem(x.code, x.name, x.configured));
      });

      if (_.isNil(vm.currentCode()) && vm.registrationWorkList().length > 0) {
        let firstItem: any = _.head(vm.registrationWorkList());
        vm.currentCode(firstItem.code);
      }      
      
    }

    getListOfRefinedItems() {
      const vm = this;
      let lisItems: Array<RefinedItem> = [];

      for( let i = 0; i < 40; i++) {
        lisItems.push(new RefinedItem('000' + i, '作業 ' + i, '2021/21/02','9999/12/31'));
      }

      vm.listOfRefinedItems.removeAll();
      vm.listOfRefinedItems(lisItems);
    }

    getWorkList() {
      const vm = this;

      vm.workList = ko.observableArray([
        { code: '001', name: '作業 1' },
        { code: '002', name: '作業 2' },
        { code: '003', name: '作業 3' },
      ])
    }

    getNextPreviousItem(codeBeforeRemove: string) {
      const vm = this;
      let currentCode: string = null;
      //delete from DB

      let findIndex = _.findIndex(vm.registrationWorkList(), (x) => { return x.code === codeBeforeRemove });
      if (vm.registrationWorkList().length > 1) {
        if (findIndex === vm.registrationWorkList().length - 1)
          findIndex = findIndex - 1;
        else
          findIndex = findIndex + 1;

        let nextItem = vm.registrationWorkList()[findIndex];
        if (!_.isNil(nextItem)) {
          currentCode = nextItem.code;
        }

        let registrationList = _.filter(vm.registrationWorkList(), (x) => { return x.code !== codeBeforeRemove });
        vm.registrationWorkList.removeAll();
        vm.registrationWorkList(registrationList);
        vm.currentCode(currentCode);
      } else {
        vm.registrationWorkList.removeAll();
        vm.currentCode(null);
        vm.addNewRegistrationWork();
        vm.isNewMode(true);        
      }
    }


    openDialogKDL012() {
      const vm = this;
      //kdl012 input
      let selectionCodeList: Array<string> = [];
      _.forEach(vm.model().listOfRefinedItems(), (x) => {
        selectionCodeList.push(x.code);
      })

      let params = {
        isMultiple: true, //選択モード single or multiple
        showExpireDate: true, //表示モード	show/hide expire date
        referenceDate: moment().format('YYYY/MM/DD'), //システム日付        
        workFrameNoSelection: vm.selectedWorkCode(), //作業枠NO選択      
        selectionCodeList: selectionCodeList// 初期選択コードリスト
      }

      vm.$window.modal('/view/kdl/012/index.xhtml', params).done((data) => {
        if(data && data.setShareKDL012) {
      
          let newListOfRefinedItems: Array<RefinedItem> = [];
          _.forEach(data.setShareKDL012, (x) => {
            newListOfRefinedItems.push(
              new RefinedItem(x.code, x.name, x.startDate, x.endDate, x.remark)
            )
          });     

          //update model
          newListOfRefinedItems = _.orderBy(newListOfRefinedItems, ['code', 'asc']);
          vm.model().listOfRefinedItems.removeAll();
          vm.model().listOfRefinedItems(newListOfRefinedItems);
        }
      });
    }

    deleteRowItem() {
      const vm = this;
      let newListOfRefinedItems = _.filter(vm.model().listOfRefinedItems(), (x) => { return !_.includes(vm.currentCodeList(), x.code) });
      vm.model().listOfRefinedItems.removeAll();
      vm.model().listOfRefinedItems(newListOfRefinedItems);
      
    }

    openDialogCDL023() {
      const vm = this;

      let selectionCodeList: Array<string> = [];
      _.forEach(vm.model().listOfRefinedItems(), (x) => {
        selectionCodeList.push(x.code);
      })

      let params : IObjectDuplication = {
        code: vm.model().code(),
        name: vm.model().name(),
        targetType: vm.selectedWorkCode(),
        itemListSetting: selectionCodeList,
        baseDate: moment('YYYY/MM/DD').toDate(),
        roleType:  0 //SYSTEM_MANAGER
      };

      nts.uk.ui.windows.setShared("CDL023Input", params);
      // open dialog
      nts.uk.ui.windows.sub.modal('com','view/cdl/023/a/index.xhtml').onClosed(() => {
        // show data respond
        let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
      });
      // check has close dialog
      //nts.uk.ui.windows.setShared("CDL023Cancel", true);
    }
  }

  export class ModelItem {
    code: KnockoutObservable<string> = ko.observable(null);
    name: KnockoutObservable<string> = ko.observable(null);
    expStartDate: KnockoutObservable<string> = ko.observable(null);
    expEndDate: KnockoutObservable<string> = ko.observable(null);    
    listOfRefinedItems: KnockoutObservableArray<RefinedItem> = ko.observableArray([]);

    constructor(code?: string, name?: string, expStartDate?: string, expEndDate?: string, listOfRefinedItems?: Array<RefinedItem> ) {
      this.code(code);
      this.name(name);
      this.expStartDate(expStartDate);
      this.expEndDate(expEndDate);
      this.listOfRefinedItems(listOfRefinedItems);
    }

    update(code?: string, name?: string, expStartDate?: string, expEndDate?: string, listOfRefinedItems?: Array<RefinedItem>) {
      this.code(code);
      this.name(name);
      this.expStartDate(expStartDate);
      this.expEndDate(expEndDate);
      this.listOfRefinedItems(listOfRefinedItems);
    }
  }

  export class WorkItem {
    code: string;
    name: string;
    display: string;
    configured: string;

    constructor(code?: string, name?: string, configured?: string) {
      this.code = code;
      this.name = name;
      this.display = code + (( !_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
      this.configured = configured;
    }
  }

  export class RefinedItem {
    code: string;
    name: string;
    display: string;
    expireDate: string;
    remark: string;
    startDate: string;
    endDate: string;

    constructor(code?: string, name?: string, startDate?: string, endDate?: string, remark?: string) {
      this.code = code;
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
      this.display = code + (( !_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
      this.expireDate = startDate + ' ～ ' + endDate;
      this.remark = remark;
    }
  }

  export enum TargetType {
    // 雇用
    EMPLOYMENT = 1,
    // 分類
    CLASSIFICATION = 2,
    // 職位
    JOB_TITLE = 3,
    // 職場
    WORKPLACE = 4,
    // 部門
    DEPARTMENT = 5,
    // 職場個人
    WORKPLACE_PERSONAL = 6,
    // 部門個人
    DEPARTMENT_PERSONAL = 7,
    // ロール
    ROLE = 8,
    // 勤務種別
    WORK_TYPE = 9
  }

  interface IObjectDuplication {
    code: string;
    name: string;
    targetType: string | number;
    itemListSetting: Array<string>;
    baseDate?: Date; // needed when target type: 職場 or 部門 or 職場個人 or 部門個人
    roleType?: number; // needed when target type: ロール
  }

}