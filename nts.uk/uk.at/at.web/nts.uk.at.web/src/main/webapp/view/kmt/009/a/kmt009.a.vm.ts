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

    model: KnockoutObservable<ModelItem> = ko.observable(null);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    listOfRefinedItems: KnockoutObservableArray<RefinedItem> = ko.observableArray([]);

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

      //init mode
      vm.getRegistrationWorkList();
      vm.getWorkList();

      vm.model(new ModelItem());
      vm.addNewRegistrationWork();

      vm.selectedWorkCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        vm.loadRegistrationWork();
      });

      vm.currentCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        vm.loadRegistrationWork();
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    addNewRegistrationWork() {
      const vm = this;
      let currentDate = moment(new Date()).format('YYYY/DD/MM');
      vm.externalCode = [
        ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null)
      ];
      vm.model().update(null, null, currentDate, '9999/12/31');

      vm.isNewMode(true);
      $('#KMT001_21').focus();
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
    }

    deleteRegistrationWork() {
      const vm = this;
      vm.getNextPreviousItem(vm.currentCode());
      /*  vm.$blockui('show');
       vm.$ajax(PATH.getRegistrationWork, { workId: vm.currentCode() }).done((data) => {
         vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
           vm.getNextPreviousItem(vm.currentCode());
           vm.$blockui('hide');
         });
       }).fail((error) => {
         vm.$dialog.error({messageId: error.messageId}).then(() => {
           vm.$blockui('hide');
         });
       }); */
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

      let currentObj = _.find(vm.registrationWorkList(), (x) => { return x.code === vm.currentCode()});
      if( currentObj ) {
        vm.model().update(currentObj.code, currentObj.name, '2021/12/31', '9999/12/31', vm.listOfRefinedItems());
        vm.isNewMode(false);
      } else {
        vm.model().update('', '', '2002/12/31', '9999/12/31', vm.listOfRefinedItems());
        vm.isNewMode(true);
      }
    }

    getRegistrationWorkList() {
      const vm = this;
      let testWorkItems = [
        { code: 'A0000000000000000001', name: '', configured: registered },
        { code: 'A0000000000000000002', name: null, configured: '' },
        { code: 'A0000000000000000003', name: '作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1', configured: registered },
      ];

      _.forEach(testWorkItems, (x) => {
        vm.registrationWorkList.push(new WorkItem(x.code, x.name, x.configured));
      });

      if (_.isNil(vm.currentCode()) && vm.registrationWorkList().length > 0) {
        let firstItem: any = _.head(vm.registrationWorkList());
        vm.currentCode(firstItem.code);
      }

      vm.listOfRefinedItems.push(new RefinedItem('B00000000001', '作業 1', '2021/21/02 ~ 9999/12/31'));
      vm.listOfRefinedItems.push(new RefinedItem('B00000000002', '作業 2', '2021/21/02 ~ 2021/12/31'));
      vm.listOfRefinedItems.push(new RefinedItem('B00000000003', '作業 3', '2021/21/02 ~ 2021/12/31'));
      vm.listOfRefinedItems.push(new RefinedItem('B00000000004', '作業 4', '2021/21/02 ~ 2021/12/31'));

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
      }
    }


    openDialogKDL012() {
      const vm = this;

      vm.$window.modal('/view/kdl/012/a/index.xhtml').done((data) => {
        
      });
    }

    deleteRowItem() {
      const vm = this;
      let newListOfRefinedItems = _.filter(vm.model().listOfRefinedItems(), (x) => { return !_.includes(vm.currentCodeList(), x.code) });
      vm.model().listOfRefinedItems.removeAll();
      vm.model().listOfRefinedItems(newListOfRefinedItems);
      console.log(vm.model());
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

    constructor(code?: string, name?: string, expireDate?: string) {
      this.code = code;
      this.name = name;
      this.display = code + (( !_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
      this.expireDate = expireDate;
    }
  }
}